/*
MIT License

Copyright (c) 2018 Alexis Jehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.github.alexisjehan.javanilla.lang.array;

import com.github.alexisjehan.javanilla.util.iteration.Iterables;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>An utility class that provides {@code Object} array tools.</p>
 * @since 1.0.0
 */
public final class ObjectArrays {

	/**
	 * <p>Map that associates a class type to a cached empty {@code Object} array.</p>
	 * @since 1.2.0
	 */
	private static final Map<Class, Object> cachedEmpties = new WeakHashMap<>();

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private ObjectArrays() {
		// Not available
	}

	/**
	 * <p>Return an empty {@code Object} array.</p>
	 * @param classType the {@code Object} class type
	 * @param <E> the {@code Object} type
	 * @return an empty {@code Object} array
	 * @throws NullPointerException if the {@code Object} class type is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] empty(final Class<E> classType) {
		if (null == classType) {
			throw new NullPointerException("Invalid class type (not null expected)");
		}
		return (E[]) cachedEmpties.computeIfAbsent(classType, type -> Array.newInstance(type, 0));
	}

	/**
	 * <p>Wrap an {@code Object} array replacing {@code null} by an empty one.</p>
	 * @param classType the {@code Object} class type
	 * @param array the {@code Object} array or {@code null}
	 * @param <E> the {@code Object} type
	 * @return a non-{@code null} {@code Object} array
	 * @throws NullPointerException if the {@code Object} class type is {@code null}
	 * @since 1.0.0
	 */
	public static <E> E[] nullToEmpty(final Class<E> classType, final E[] array) {
		return nullToDefault(array, empty(classType));
	}

	/**
	 * <p>Wrap an {@code Object} array replacing {@code null} by a default one.</p>
	 * @param array the {@code Object} array or {@code null}
	 * @param defaultArray the default {@code Object} array
	 * @param <E> the {@code Object} type
	 * @return a non-{@code null} {@code Object} array
	 * @throws NullPointerException if the default {@code Object} array is {@code null}
	 * @since 1.1.0
	 */
	public static <E> E[] nullToDefault(final E[] array, final E[] defaultArray) {
		if (null == defaultArray) {
			throw new NullPointerException("Invalid default array (not null expected)");
		}
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap an {@code Object} array replacing an empty one by {@code null}.</p>
	 * @param array the {@code Object} array or {@code null}
	 * @param <E> the {@code Object} type
	 * @return a non-empty {@code Object} array or {@code null}
	 * @since 1.0.0
	 */
	public static <E> E[] emptyToNull(final E[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap an {@code Object} array replacing an empty one by a default {@code Object} array.</p>
	 * @param array the {@code Object} array or {@code null}
	 * @param defaultArray the default {@code Object} array or {@code null}
	 * @param <E> the {@code Object} type
	 * @return a non-empty {@code Object} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code Object} array is empty
	 * @since 1.1.0
	 */
	public static <E> E[] emptyToDefault(final E[] array, final E[] defaultArray) {
		if (null != defaultArray && 0 == defaultArray.length) {
			throw new IllegalArgumentException("Invalid default array (not empty expected)");
		}
		return null == array || 0 != array.length ? array : defaultArray;
	}

	/**
	 * <p>Tell if an {@code Object} array is empty.</p>
	 * @param array the {@code Object} array to test
	 * @return {@code true} if the {@code Object} array is empty
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final Object[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return 0 == array.length;
	}

	/**
	 * <p>Get the first index of the {@code Object} value in the {@code Object} array.</p>
	 * @param array the {@code Object} array to lookup
	 * @param value the {@code Object} value to search
	 * @return the first index of the {@code Object} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final Object[] array, final Object value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code Object} value in the {@code Object} array starting from the given index.</p>
	 * @param array the {@code Object} array to lookup
	 * @param value the {@code Object} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code Object} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final Object[] array, final Object value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 < array.length) {
			if (0 > fromIndex || array.length - 1 < fromIndex) {
				throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
			}
			for (var i = fromIndex; i < array.length; ++i) {
				if (Objects.equals(value, array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * <p>Get the last index of the {@code Object} value in the {@code Object} array.</p>
	 * @param array the {@code Object} array to lookup
	 * @param value the {@code Object} value to search
	 * @return the last index of the {@code Object} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final Object[] array, final Object value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code Object} value in the {@code Object} array starting from the given index.</p>
	 * @param array the {@code Object} array to lookup
	 * @param value the {@code Object} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code Object} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final Object[] array, final Object value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 < array.length) {
			if (0 > fromIndex || array.length - 1 < fromIndex) {
				throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
			}
			for (var i = array.length - 1; i > fromIndex; --i) {
				if (Objects.equals(value, array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * <p>Tell if the {@code Object} array contains any of given {@code Object} values at least one.</p>
	 * @param array the {@code Object} array to test
	 * @param values {@code Object} values to test
	 * @return {@code true} if any of given {@code Object} values is contained at least once by the {@code Object} array
	 * @throws NullPointerException if the {@code Object} array or the {@code Object} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code Object} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final Object[] array, final Object... values) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			throw new IllegalArgumentException("Invalid values (not empty expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var value : values) {
			for (final var element : array) {
				if (Objects.equals(value, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>Tell if the {@code Object} array contains all of given {@code Object} values at least one.</p>
	 * @param array the {@code Object} array to test
	 * @param values {@code Object} values to test
	 * @return {@code true} if all of given {@code Object} values are contained at least once by the {@code Object}
	 * array
	 * @throws NullPointerException if the {@code Object} array or the {@code Object} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code Object} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final Object[] array, final Object... values) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			throw new IllegalArgumentException("Invalid values (not empty expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var value : values) {
			var contained = false;
			for (final var element : array) {
				if (Objects.equals(value, element)) {
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
	 * <p>Tell if the {@code Object} array contains each given {@code Object} value only once.</p>
	 * @param array the {@code Object} array to test
	 * @param values {@code Object} values to test
	 * @return {@code true} if each of given {@code Object} values are contained only once by the {@code Object} array
	 * @throws NullPointerException if the {@code Object} array or the {@code Object} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code Object} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final Object[] array, final Object... values) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			throw new IllegalArgumentException("Invalid values (not empty expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var value : values) {
			var contained = false;
			for (final var element : array) {
				if (Objects.equals(value, element)) {
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
	 * <p>Tell if the {@code Object} array contains only given {@code Object} values at least one.</p>
	 * @param array the {@code Object} array to test
	 * @param values {@code Object} values to test
	 * @return {@code true} if given {@code Object} values are only values contained by the {@code Object} array
	 * @throws NullPointerException if the {@code Object} array or the {@code Object} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code Object} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final Object[] array, final Object... values) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			throw new IllegalArgumentException("Invalid values (not empty expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var element : array) {
			var contained = false;
			for (final var value : values) {
				if (Objects.equals(value, element)) {
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
	 * <p>Shuffle values in the given {@code Object} array using the Fisher-Yates algorithm.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @param array the {@code Object} array to shuffle
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @since 1.2.0
	 */
	public static void shuffle(final Object[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (1 < array.length) {
			final var random = ThreadLocalRandom.current();
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * <p>Reverse values in the given {@code Object} array.</p>
	 * @param array the {@code Object} array to reverse
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final Object[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * <p>Reorder values in the given {@code Object} array using provided indexes.</p>
	 * @param array the {@code Object} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code Object} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code Object} array and indexes array lengths are not the same or if
	 * indexes are not distinct
	 * @throws IndexOutOfBoundsException if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final Object[] array, final int... indexes) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == indexes) {
			throw new NullPointerException("Invalid indexes (not null expected)");
		}
		if (array.length != indexes.length) {
			throw new IllegalArgumentException("Invalid array and indexes lengths: " + array.length + " and " + indexes.length + " (same expected)");
		}
		if (array.length != Arrays.stream(indexes).distinct().count()) {
			throw new IllegalArgumentException("Invalid indexes (distinct expected)");
		}
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				var j = indexes[i];
				if (0 > j || array.length - 1 < j) {
					throw new IndexOutOfBoundsException("Invalid index: " + j + " (between 0 and " + (array.length - 1) + " expected)");
				}
				while (j < i) {
					j = indexes[j];
				}
				swap(array, i, j);
			}
		}
	}

	/**
	 * <p>Swap two values in the given {@code Object} array using their indexes.</p>
	 * @param array the {@code Object} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @throws IndexOutOfBoundsException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final Object[] array, final int index1, final int index2) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 > index1 || array.length - 1 < index1) {
			throw new IndexOutOfBoundsException("Invalid first index: " + index1 + " (between 0 and " + (array.length - 1) + " expected)");
		}
		if (0 > index2 || array.length - 1 < index2) {
			throw new IndexOutOfBoundsException("Invalid second index: " + index2 + " (between 0 and " + (array.length - 1) + " expected)");
		}
		if (index1 != index2) {
			final var value = array[index1];
			array[index1] = array[index2];
			array[index2] = value;
		}
	}

	/**
	 * <p>Concatenate multiple {@code Object} arrays.</p>
	 * @param classType the {@code Object} class type
	 * @param arrays the {@code Object} array array to concatenate
	 * @param <E> the {@code Object} type
	 * @return the concatenated {@code Object} array
	 * @throws NullPointerException if the {@code Object} class type, the {@code Object} array array or any of them is
	 * {@code null}
	 * @since 1.2.0
	 */
	@SafeVarargs
	public static <E> E[] concat(final Class<E> classType, final E[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		return concat(classType, Arrays.asList(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code Object} arrays.</p>
	 * @param classType the {@code Object} class type
	 * @param arrays the {@code Object} array {@code List} to concatenate
	 * @param <E> the {@code Object} type
	 * @return the concatenated {@code Object} array
	 * @throws NullPointerException if the {@code Object} class type, the {@code Object} array {@code List} or any of
	 * them is {@code null}
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] concat(final Class<E> classType, final List<E[]> arrays) {
		if (null == classType) {
			throw new NullPointerException("Invalid class type (not null expected)");
		}
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		for (final var indexedArray : Iterables.index(arrays)) {
			if (null == indexedArray.getElement()) {
				throw new NullPointerException("Invalid array at index " + indexedArray.getIndex() + " (not null expected)");
			}
		}
		final var size = arrays.size();
		if (0 == size) {
			return empty(classType);
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = (E[]) Array.newInstance(classType, arrays.stream().mapToInt(array -> array.length).sum());
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * <p>Join multiple {@code Object} arrays using an {@code Object} array separator.</p>
	 * @param classType the {@code Object} class type
	 * @param separator the {@code Object} array separator
	 * @param arrays the {@code Object} array array to join
	 * @param <E> the {@code Object} type
	 * @return the joined {@code Object} array
	 * @throws NullPointerException if the {@code Object} class type, the {@code Object} array separator, the
	 * {@code Object} array array or any of them is {@code null}
	 * @since 1.2.0
	 */
	@SafeVarargs
	public static <E> E[] join(final Class<E> classType, final E[] separator, final E[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		return join(classType, separator, Arrays.asList(arrays));
	}

	/**
	 * <p>Join multiple {@code Object} arrays using an {@code Object} array separator.</p>
	 * @param classType the {@code Object} class type
	 * @param separator the {@code Object} array separator
	 * @param arrays the {@code Object} array {@code List} to join
	 * @param <E> the {@code Object} type
	 * @return the joined {@code Object} array
	 * @throws NullPointerException if the {@code Object} class type, the {@code Object} array separator, the
	 * {@code Object} array {@code List} or any of them is {@code null}
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] join(final Class<E> classType, final E[] separator, final List<E[]> arrays) {
		if (null == classType) {
			throw new NullPointerException("Invalid class type (not null expected)");
		}
		if (null == separator) {
			throw new NullPointerException("Invalid separator (not null expected)");
		}
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		for (final var indexedArray : Iterables.index(arrays)) {
			if (null == indexedArray.getElement()) {
				throw new NullPointerException("Invalid array at index " + indexedArray.getIndex() + " (not null expected)");
			}
		}
		if (0 == separator.length) {
			return concat(classType, arrays);
		}
		final var size = arrays.size();
		if (0 == size) {
			return empty(classType);
		}
		if (1 == size) {
			return arrays.get(0);
		}
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
	 * <p>Create an {@code Object} array from a single {@code Object}.</p>
	 * @param object the {@code Object} to convert
	 * @param <E> the {@code Object} type
	 * @return the created {@code Object} array
	 * @throws NullPointerException if the {@code Object} is {@code null}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] singleton(final E object) {
		if (null == object) {
			throw new NullPointerException("Invalid Object (not null expected)");
		}
		return singleton((Class<E>) object.getClass(), object);
	}

	/**
	 * <p>Create an {@code Object} array from a single {@code Object} or {@code null}.</p>
	 * @param classType the {@code Object} class type
	 * @param object the {@code Object} to convert or {@code null}
	 * @param <E> the {@code Object} type
	 * @return the created {@code Object} array
	 * @throws NullPointerException if the {@code Object} class type is {@code null}
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] singleton(final Class<E> classType, final E object) {
		if (null == classType) {
			throw new NullPointerException("Invalid class type (not null expected)");
		}
		final var array = (E[]) Array.newInstance(classType, 1);
		array[0] = object;
		return array;
	}

	/**
	 * <p>Create an {@code Object} array from multiple {@code Object}s.</p>
	 * @param objects the {@code Object} array to convert
	 * @param <E> the {@code Object} type
	 * @return the created {@code Object} array
	 * @throws NullPointerException if the {@code Object} array is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> E[] of(final E... objects) {
		if (null == objects) {
			throw new NullPointerException("Invalid Objects (not null expected)");
		}
		return objects;
	}
}