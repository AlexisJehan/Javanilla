/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.alexisjehan.javanilla.lang.array;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.ToString;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class that provides {@link Object} array tools.
 * @since 1.0.0
 */
public final class ObjectArrays {

	/**
	 * Map that associates a {@link Class} type to a cached empty {@link Object} array.
	 * @since 1.2.0
	 */
	private static final Map<Class<?>, Object> cachedEmpties = new WeakHashMap<>();

	/**
	 * Constructor.
	 * @since 1.0.0
	 */
	private ObjectArrays() {}

	/**
	 * Return an empty {@link Object} array.
	 * @param classType the {@link Class} type
	 * @param <E> the {@link Object} type
	 * @return an empty {@link Object} array
	 * @throws NullPointerException if the {@link Class} type is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] empty(final Class<E> classType) {
		Ensure.notNull("classType", classType);
		return (E[]) cachedEmpties.computeIfAbsent(classType, type -> Array.newInstance(type, 0));
	}

	/**
	 * Wrap an {@link Object} array replacing {@code null} by an empty one.
	 * @param classType the {@link Class} type
	 * @param array the {@link Object} array or {@code null}
	 * @param <E> the {@link Object} type
	 * @return a non-{@code null} {@link Object} array
	 * @throws NullPointerException if the {@link Class} type is {@code null}
	 * @since 1.0.0
	 */
	public static <E> E[] nullToEmpty(final Class<E> classType, final E[] array) {
		return nullToDefault(array, empty(classType));
	}

	/**
	 * Wrap an {@link Object} array replacing {@code null} by a default one.
	 * @param array the {@link Object} array or {@code null}
	 * @param defaultArray the default {@link Object} array
	 * @param <E> the {@link Object} type
	 * @return a non-{@code null} {@link Object} array
	 * @throws NullPointerException if the default {@link Object} array is {@code null}
	 * @since 1.1.0
	 */
	public static <E> E[] nullToDefault(final E[] array, final E[] defaultArray) {
		Ensure.notNull("defaultArray", defaultArray);
		return null != array ? array : defaultArray;
	}

	/**
	 * Wrap an {@link Object} array replacing an empty one by {@code null}.
	 * @param array the {@link Object} array or {@code null}
	 * @param <E> the {@link Object} type
	 * @return a non-empty {@link Object} array or {@code null}
	 * @since 1.0.0
	 */
	public static <E> E[] emptyToNull(final E[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * Wrap an {@link Object} array replacing an empty one by a default {@link Object} array.
	 * @param array the {@link Object} array or {@code null}
	 * @param defaultArray the default {@link Object} array or {@code null}
	 * @param <E> the {@link Object} type
	 * @return a non-empty {@link Object} array or {@code null}
	 * @throws IllegalArgumentException if the default {@link Object} array is empty
	 * @since 1.1.0
	 */
	public static <E> E[] emptyToDefault(final E[] array, final E[] defaultArray) {
		if (null != defaultArray) {
			Ensure.notNullAndNotEmpty("defaultArray", defaultArray);
		}
		return null == array || !isEmpty(array) ? array : defaultArray;
	}

	/**
	 * Add an {@link Object} value at the end of the given {@link Object} array.
	 * @param array the {@link Object} array to add to
	 * @param value the {@link Object} value to add
	 * @param <E> the {@link Object} type
	 * @return an {@link Object} array with the added {@link Object} value
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @since 1.4.0
	 */
	public static <E> E[] add(final E[] array, final E value) {
		Ensure.notNull("array", array);
		return add(array, value, array.length);
	}

	/**
	 * Add an {@link Object} value at the provided index of the given {@link Object} array.
	 * @param array the {@link Object} array to add to
	 * @param index the index of the {@link Object} value
	 * @param value the {@link Object} value to add
	 * @param <E> the {@link Object} type
	 * @return an {@link Object} array with the added {@link Object} value
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @deprecated since 1.6.0, use {@link #add(Object[], Object, int)} instead
	 * @since 1.4.0
	 */
	@Deprecated(since = "1.6.0")
	public static <E> E[] add(final E[] array, final int index, final E value) {
		return add(array, value, index);
	}

	/**
	 * Add an {@link Object} value at the provided index of the given {@link Object} array.
	 * @param array the {@link Object} array to add to
	 * @param value the {@link Object} value to add
	 * @param index the index of the {@link Object} value
	 * @param <E> the {@link Object} type
	 * @return an {@link Object} array with the added {@link Object} value
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @since 1.6.0
	 */
	public static <E> E[] add(final E[] array, final E value, final int index) {
		Ensure.notNull("array", array);
		Ensure.between("index", index, 0, array.length);
		@SuppressWarnings("unchecked")
		final var result = (E[]) Array.newInstance(array.getClass().getComponentType(), array.length + 1);
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		result[index] = value;
		if (index < array.length) {
			System.arraycopy(array, index, result, index + 1, array.length - index);
		}
		return result;
	}

	/**
	 * Remove an {@link Object} value at the provided index of the given {@link Object} array.
	 * @param array the {@link Object} array to remove from
	 * @param index the index of the {@link Object} value
	 * @param <E> the {@link Object} type
	 * @return an {@link Object} array with the removed {@link Object} value
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @throws IllegalArgumentException if the {@link Object} array is empty or if the index is not valid
	 * @since 1.4.0
	 */
	public static <E> E[] remove(final E[] array, final int index) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.between("index", index, 0, array.length - 1);
		@SuppressWarnings("unchecked")
		final var result = (E[]) Array.newInstance(array.getClass().getComponentType(), array.length - 1);
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		if (index < array.length - 1) {
			System.arraycopy(array, index + 1, result, index, array.length - index - 1);
		}
		return result;
	}

	/**
	 * Concatenate multiple {@link Object} arrays.
	 * @param classType the {@link Class} type
	 * @param arrays {@link Object} arrays to concatenate
	 * @param <E> the {@link Object} type
	 * @return the concatenated {@link Object} array
	 * @throws NullPointerException if the {@link Object} {@link Class} type, {@link Object} arrays or any of them is
	 *         {@code null}
	 * @since 1.2.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> E[] concat(final Class<E> classType, final E[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(classType, List.of(arrays));
	}

	/**
	 * Concatenate multiple {@link Object} arrays.
	 * @param classType the {@link Class} type
	 * @param arrays the {@link Object} array {@link List} to concatenate
	 * @param <E> the {@link Object} type
	 * @return the concatenated {@link Object} array
	 * @throws NullPointerException if the {@link Class} type, the {@link Object} array {@link List} or any of them is
	 *         {@code null}
	 * @since 1.2.0
	 */
	public static <E> E[] concat(final Class<E> classType, final List<E[]> arrays) {
		Ensure.notNull("classType", classType);
		Ensure.notNullAndNotNullElements("arrays", arrays);
		final var size = arrays.size();
		if (0 == size) {
			return empty(classType);
		}
		if (1 == size) {
			return arrays.get(0);
		}
		@SuppressWarnings("unchecked")
		final var result = (E[]) Array.newInstance(classType, arrays.stream().mapToInt(array -> array.length).sum());
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * Join multiple {@link Object} arrays using an {@link Object} array separator.
	 * @param classType the {@link Class} type
	 * @param separator the {@link Object} array separator
	 * @param arrays {@link Object} arrays to join
	 * @param <E> the {@link Object} type
	 * @return the joined {@link Object} array
	 * @throws NullPointerException if the {@link Class} type, the {@link Object} array separator, {@link Object} arrays
	 *         or any of them is {@code null}
	 * @deprecated since 1.4.0, use {@link #join(Object[], Object[][])} instead
	 * @since 1.2.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	@Deprecated(since = "1.4.0")
	public static <E> E[] join(final Class<E> classType, final E[] separator, final E[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(classType, separator, List.of(arrays));
	}

	/**
	 * Join multiple {@link Object} arrays using an {@link Object} array separator.
	 * @param classType the {@link Class} type
	 * @param separator the {@link Object} array separator
	 * @param arrays the {@link Object} array {@link List} to join
	 * @param <E> the {@link Object} type
	 * @return the joined {@link Object} array
	 * @throws NullPointerException if the {@link Class} type, the {@link Object} array separator, the {@link Object}
	 *         array {@link List} or any of them is {@code null}
	 * @deprecated since 1.4.0, use {@link #join(Object[], List)} instead
	 * @since 1.2.0
	 */
	@Deprecated(since = "1.4.0")
	public static <E> E[] join(final Class<E> classType, final E[] separator, final List<E[]> arrays) {
		Ensure.notNull("classType", classType);
		Ensure.notNull("separator", separator);
		Ensure.notNullAndNotNullElements("arrays", arrays);
		if (isEmpty(separator)) {
			return concat(classType, arrays);
		}
		final var size = arrays.size();
		if (0 == size) {
			return empty(classType);
		}
		if (1 == size) {
			return arrays.get(0);
		}
		@SuppressWarnings("unchecked")
		final var result = (E[]) Array.newInstance(classType, arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length);
		final var iterator = arrays.iterator();
		var array = iterator.next();
		System.arraycopy(array, 0, result, 0, array.length);
		var offset = array.length;
		while (iterator.hasNext()) {
			System.arraycopy(separator, 0, result, offset, separator.length);
			offset += separator.length;
			array = iterator.next();
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * Join multiple {@link Object} arrays using an {@link Object} array separator.
	 * @param separator the {@link Object} array separator
	 * @param arrays {@link Object} arrays to join
	 * @param <E> the {@link Object} type
	 * @return the joined {@link Object} array
	 * @throws NullPointerException if the {@link Object} array separator, {@link Object} arrays or any of them is
	 *         {@code null}
	 * @since 1.4.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> E[] join(final E[] separator, final E[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * Join multiple {@link Object} arrays using an {@link Object} array separator.
	 * @param separator the {@link Object} array separator
	 * @param arrays the {@link Object} array {@link List} to join
	 * @param <E> the {@link Object} type
	 * @return the joined {@link Object} array
	 * @throws NullPointerException if the {@link Object} array separator, the {@link Object} array {@link List} or any
	 *         of them is {@code null}
	 * @since 1.4.0
	 */
	public static <E> E[] join(final E[] separator, final List<E[]> arrays) {
		Ensure.notNull("separator", separator);
		Ensure.notNullAndNotNullElements("arrays", arrays);
		@SuppressWarnings("unchecked")
		final var classType = (Class<E>) separator.getClass().getComponentType();
		if (isEmpty(separator)) {
			return concat(classType, arrays);
		}
		final var size = arrays.size();
		if (0 == size) {
			return empty(classType);
		}
		if (1 == size) {
			return arrays.get(0);
		}
		@SuppressWarnings("unchecked")
		final var result = (E[]) Array.newInstance(classType, arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length);
		final var iterator = arrays.iterator();
		var array = iterator.next();
		System.arraycopy(array, 0, result, 0, array.length);
		var offset = array.length;
		while (iterator.hasNext()) {
			System.arraycopy(separator, 0, result, offset, separator.length);
			offset += separator.length;
			array = iterator.next();
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * Tell if the {@link Object} array contains any of given {@link Object} values at least one.
	 * @param array the {@link Object} array to test
	 * @param values {@link Object} values to test
	 * @return {@code true} if any of given {@link Object} values is contained at least once by the {@link Object} array
	 * @throws NullPointerException if the {@link Object} array or the {@link Object} values array is {@code null}
	 * @throws IllegalArgumentException if the {@link Object} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final Object[] array, final Object... values) {
		Ensure.notNull("array", array);
		Ensure.notNullAndNotEmpty("values", values);
		if (isEmpty(array)) {
			return false;
		}
		for (final var value : values) {
			for (final var element : array) {
				if (Equals.equals(value, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Tell if the {@link Object} array contains all of given {@link Object} values at least one.
	 * @param array the {@link Object} array to test
	 * @param values {@link Object} values to test
	 * @return {@code true} if all of given {@link Object} values are contained at least once by the {@link Object}
	 *         array
	 * @throws NullPointerException if the {@link Object} array or the {@link Object} values array is {@code null}
	 * @throws IllegalArgumentException if the {@link Object} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final Object[] array, final Object... values) {
		Ensure.notNull("array", array);
		Ensure.notNullAndNotEmpty("values", values);
		if (isEmpty(array)) {
			return false;
		}
		for (final var value : values) {
			var contained = false;
			for (final var element : array) {
				if (Equals.equals(value, element)) {
					contained = true;
					break;
				}
			}
			if (!contained) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Tell if the {@link Object} array contains each given {@link Object} value only once.
	 * @param array the {@link Object} array to test
	 * @param values {@link Object} values to test
	 * @return {@code true} if each of given {@link Object} values are contained only once by the {@link Object} array
	 * @throws NullPointerException if the {@link Object} array or the {@link Object} values array is {@code null}
	 * @throws IllegalArgumentException if the {@link Object} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final Object[] array, final Object... values) {
		Ensure.notNull("array", array);
		Ensure.notNullAndNotEmpty("values", values);
		if (isEmpty(array)) {
			return false;
		}
		for (final var value : values) {
			var contained = false;
			for (final var element : array) {
				if (Equals.equals(value, element)) {
					if (contained) {
						return false;
					}
					contained = true;
				}
			}
			if (!contained) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Tell if the {@link Object} array contains only given {@link Object} values at least one.
	 * @param array the {@link Object} array to test
	 * @param values {@link Object} values to test
	 * @return {@code true} if given {@link Object} values are only values contained by the {@link Object} array
	 * @throws NullPointerException if the {@link Object} array or the {@link Object} values array is {@code null}
	 * @throws IllegalArgumentException if the {@link Object} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final Object[] array, final Object... values) {
		Ensure.notNull("array", array);
		Ensure.notNullAndNotEmpty("values", values);
		if (isEmpty(array)) {
			return false;
		}
		for (final var element : array) {
			var contained = false;
			for (final var value : values) {
				if (Equals.equals(value, element)) {
					contained = true;
					break;
				}
			}
			if (!contained) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the first index of the {@link Object} value in the {@link Object} array.
	 * @param array the {@link Object} array to iterate
	 * @param value the {@link Object} value to search
	 * @return the first index of the {@link Object} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final Object[] array, final Object value) {
		return indexOf(array, value, 0);
	}

	/**
	 * Get the first index of the {@link Object} value in the {@link Object} array starting from the given index.
	 * @param array the {@link Object} array to iterate
	 * @param value the {@link Object} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@link Object} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final Object[] array, final Object value, final int fromIndex) {
		Ensure.notNull("array", array);
		if (!isEmpty(array)) {
			Ensure.between("fromIndex", fromIndex, 0, array.length - 1);
			for (var i = fromIndex; i < array.length; ++i) {
				if (Equals.equals(value, array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Get the last index of the {@link Object} value in the {@link Object} array.
	 * @param array the {@link Object} array to iterate
	 * @param value the {@link Object} value to search
	 * @return the last index of the {@link Object} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final Object[] array, final Object value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * Get the last index of the {@link Object} value in the {@link Object} array starting from the given index.
	 * @param array the {@link Object} array to iterate
	 * @param value the {@link Object} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@link Object} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final Object[] array, final Object value, final int fromIndex) {
		Ensure.notNull("array", array);
		if (!isEmpty(array)) {
			Ensure.between("fromIndex", fromIndex, 0, array.length - 1);
			for (var i = array.length - 1; i > fromIndex; --i) {
				if (Equals.equals(value, array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Calculate the number of occurrences of the {@link Object} value in the {@link Object} array.
	 * @param array the {@link Object} array to iterate
	 * @param value the {@link Object} value of the frequency to calculate
	 * @return the frequency of the {@link Object} value
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final Object[] array, final Object value) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return 0;
		}
		var frequency = 0;
		for (final var element : array) {
			if (Equals.equals(value, element)) {
				++frequency;
			}
		}
		return frequency;
	}

	/**
	 * Reverse values in the given {@link Object} array.
	 * @param array the {@link Object} array to reverse
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final Object[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * Reorder values in the given {@link Object} array using provided indexes.
	 * @param array the {@link Object} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@link Object} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@link Object} array is empty, if the {@link Object} array length is not
	 *         equal to the indexes array length, if indexes are not distinct or if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final Object[] array, final int... indexes) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.notNull("indexes", indexes);
		Ensure.equalTo("indexes length", indexes.length, array.length);
		if (array.length != Arrays.stream(indexes).distinct().count()) {
			throw new IllegalArgumentException("Invalid indexes: " + ToString.toString(indexes) + " (distinct expected)");
		}
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				var j = indexes[i];
				Ensure.between("index", j, 0, array.length - 1);
				while (j < i) {
					j = indexes[j];
				}
				swap(array, i, j);
			}
		}
	}

	/**
	 * Shuffle values in the given {@link Object} array following the Fisher-Yates algorithm.
	 * @param array the {@link Object} array to shuffle
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @deprecated since 1.6.0, for security purposes, use {@link #shuffle(Object[], Random)} with
	 *             {@link java.security.SecureRandom} instead
	 * @since 1.2.0
	 */
	@Deprecated(since = "1.6.0")
	public static void shuffle(final Object[] array) {
		shuffle(array, ThreadLocalRandom.current());
	}

	/**
	 * Shuffle values in the given {@link Object} array using the provided {@code Random} object following the
	 * Fisher-Yates algorithm.
	 * @param array the {@link Object} array to shuffle
	 * @param random the {@code Random} object to use
	 * @throws NullPointerException if the {@link Object} array or the {@code Random} object is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.6.0
	 */
	public static void shuffle(final Object[] array, final Random random) {
		Ensure.notNull("array", array);
		Ensure.notNull("random", random);
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * Swap two values in the given {@link Object} array using their indexes.
	 * @param array the {@link Object} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @throws IllegalArgumentException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final Object[] array, final int index1, final int index2) {
		Ensure.notNull("array", array);
		Ensure.between("index1", index1, 0, array.length - 1);
		Ensure.between("index2", index2, 0, array.length - 1);
		if (index1 != index2) {
			final var value = array[index1];
			array[index1] = array[index2];
			array[index2] = value;
		}
	}

	/**
	 * Tell if an {@link Object} array is empty.
	 * @param array the {@link Object} array to test
	 * @return {@code true} if the {@link Object} array is empty
	 * @throws NullPointerException if the {@link Object} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final Object[] array) {
		Ensure.notNull("array", array);
		return 0 == array.length;
	}

	/**
	 * Create an {@link Object} array from a single {@link Object} value.
	 * @param value the {@link Object} value to convert
	 * @param <E> the {@link Object} type
	 * @return the created {@link Object} array
	 * @throws NullPointerException if the {@link Object} value is {@code null}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] singleton(final E value) {
		Ensure.notNull("value", value);
		return singleton((Class<E>) value.getClass(), value);
	}

	/**
	 * Create an {@link Object} array from a single {@link Object} value or {@code null}.
	 * @param classType the {@link Class} type
	 * @param value the {@link Object} value to convert or {@code null}
	 * @param <E> the {@link Object} type
	 * @return the created {@link Object} array
	 * @throws NullPointerException if the {@link Class} type is {@code null}
	 * @since 1.2.0
	 */
	public static <E> E[] singleton(final Class<E> classType, final E value) {
		Ensure.notNull("classType", classType);
		@SuppressWarnings("unchecked")
		final var array = (E[]) Array.newInstance(classType, 1);
		array[0] = value;
		return array;
	}

	/**
	 * Create an {@link Object} array from multiple {@link Object} values.
	 * @param values {@link Object} values to convert
	 * @param <E> the {@link Object} type
	 * @return the created {@link Object} array
	 * @throws NullPointerException if the {@link Object} values array is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> E[] of(final E... values) {
		Ensure.notNull("values", values);
		return values;
	}
}