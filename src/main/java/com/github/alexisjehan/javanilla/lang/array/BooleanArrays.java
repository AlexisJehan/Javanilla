/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>An utility class that provides {@code boolean} array tools.</p>
 * @since 1.0.0
 */
public final class BooleanArrays {

	/**
	 * <p>An empty {@code boolean} array.</p>
	 * @since 1.0.0
	 */
	public static final boolean[] EMPTY = {};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private BooleanArrays() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code boolean} array replacing {@code null} by an empty one.</p>
	 * @param array the {@code boolean} array or {@code null}
	 * @return a non-{@code null} {@code boolean} array
	 * @since 1.0.0
	 */
	public static boolean[] nullToEmpty(final boolean[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * <p>Wrap a {@code boolean} array replacing {@code null} by a default one.</p>
	 * @param array the {@code boolean} array or {@code null}
	 * @param defaultArray the default {@code boolean} array
	 * @return a non-{@code null} {@code boolean} array
	 * @throws NullPointerException if the default {@code boolean} array is {@code null}
	 * @since 1.1.0
	 */
	public static boolean[] nullToDefault(final boolean[] array, final boolean[] defaultArray) {
		Ensure.notNull("defaultArray", defaultArray);
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap a {@code boolean} array replacing an empty one by {@code null}.</p>
	 * @param array the {@code boolean} array or {@code null}
	 * @return a non-empty {@code boolean} array or {@code null}
	 * @since 1.0.0
	 */
	public static boolean[] emptyToNull(final boolean[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap a {@code boolean} array replacing an empty one by a default {@code boolean} array.</p>
	 * @param array the {@code boolean} array or {@code null}
	 * @param defaultArray the default {@code boolean} array or {@code null}
	 * @return a non-empty {@code boolean} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code boolean} array is empty
	 * @since 1.1.0
	 */
	public static boolean[] emptyToDefault(final boolean[] array, final boolean[] defaultArray) {
		if (null != defaultArray) {
			Ensure.notNullAndNotEmpty("defaultArray", defaultArray);
		}
		return null == array || !isEmpty(array) ? array : defaultArray;
	}

	/**
	 * <p>Tell if a {@code boolean} array is empty.</p>
	 * @param array the {@code boolean} array to test
	 * @return {@code true} if the {@code boolean} array is empty
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final boolean[] array) {
		Ensure.notNull("array", array);
		return 0 == array.length;
	}

	/**
	 * <p>Tell if the {@code boolean} array contains any of given {@code boolean} values at least one.</p>
	 * @param array the {@code boolean} array to test
	 * @param values {@code boolean} values to test
	 * @return {@code true} if any of given {@code boolean} values is contained at least once by the {@code boolean}
	 *         array
	 * @throws NullPointerException if the {@code boolean} array or the {@code boolean} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final boolean[] array, final boolean... values) {
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
	 * <p>Tell if the {@code boolean} array contains all of given {@code boolean} values at least one.</p>
	 * @param array the {@code boolean} array to test
	 * @param values {@code boolean} values to test
	 * @return {@code true} if all of given {@code boolean} values are contained at least once by the {@code boolean}
	 *         array
	 * @throws NullPointerException if the {@code boolean} array or the {@code boolean} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final boolean[] array, final boolean... values) {
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
	 * <p>Tell if the {@code boolean} array contains each given {@code boolean} value only once.</p>
	 * @param array the {@code boolean} array to test
	 * @param values {@code boolean} values to test
	 * @return {@code true} if each of given {@code boolean} values are contained only once by the {@code boolean} array
	 * @throws NullPointerException if the {@code boolean} array or the {@code boolean} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final boolean[] array, final boolean... values) {
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
	 * <p>Tell if the {@code boolean} array contains only given {@code boolean} values at least one.</p>
	 * @param array the {@code boolean} array to test
	 * @param values {@code boolean} values to test
	 * @return {@code true} if given {@code boolean} values are only values contained by the {@code boolean} array
	 * @throws NullPointerException if the {@code boolean} array or the {@code boolean} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final boolean[] array, final boolean... values) {
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
	 * <p>Get the first index of the {@code boolean} value in the {@code boolean} array.</p>
	 * @param array the {@code boolean} array to iterate
	 * @param value the {@code boolean} value to search
	 * @return the first index of the {@code boolean} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final boolean[] array, final boolean value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code boolean} value in the {@code boolean} array starting from the given
	 * index.</p>
	 * @param array the {@code boolean} array to iterate
	 * @param value the {@code boolean} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code boolean} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final boolean[] array, final boolean value, final int fromIndex) {
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
	 * <p>Get the last index of the {@code boolean} value in the {@code boolean} array.</p>
	 * @param array the {@code boolean} array to iterate
	 * @param value the {@code boolean} value to search
	 * @return the last index of the {@code boolean} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final boolean[] array, final boolean value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code boolean} value in the {@code boolean} array starting from the given
	 * index.</p>
	 * @param array the {@code boolean} array to iterate
	 * @param value the {@code boolean} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code boolean} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final boolean[] array, final boolean value, final int fromIndex) {
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
	 * <p>Calculate the number of occurrences of the {@code boolean} value in the {@code boolean} array.</p>
	 * @param array the {@code boolean} array to iterate
	 * @param value the {@code boolean} value of the frequency to calculate
	 * @return the frequency of the {@code boolean} value
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final boolean[] array, final boolean value) {
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
	 * <p>Shuffle values in the given {@code boolean} array following the Fisher-Yates algorithm.</p>
	 * @param array the {@code boolean} array to shuffle
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.2.0
	 * @deprecated for security purposes, use {@link #shuffle(boolean[], Random)} with
	 *             {@link java.security.SecureRandom} instead
	 */
	@Deprecated(since = "1.6.0")
	public static void shuffle(final boolean[] array) {
		shuffle(array, ThreadLocalRandom.current());
	}

	/**
	 * <p>Shuffle values in the given {@code boolean} array using the provided {@code Random} object following the
	 * Fisher-Yates algorithm.</p>
	 * @param array the {@code boolean} array to shuffle
	 * @param random the {@code Random} object to use
	 * @throws NullPointerException if the {@code boolean} array or the {@code Random} object is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.6.0
	 */
	public static void shuffle(final boolean[] array, final Random random) {
		Ensure.notNull("array", array);
		Ensure.notNull("random", random);
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * <p>Reverse values in the given {@code boolean} array.</p>
	 * @param array the {@code boolean} array to reverse
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final boolean[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * <p>Reorder values in the given {@code boolean} array using provided indexes.</p>
	 * @param array the {@code boolean} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code boolean} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code boolean} array is empty, if the {@code boolean} array length is not
	 *         equal to the indexes array length, if indexes are not distinct or if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final boolean[] array, final int... indexes) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.notNull("indexes", indexes);
		Ensure.equalTo("indexes length", indexes.length, array.length);
		if (indexes.length != Arrays.stream(indexes).distinct().count()) {
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
	 * <p>Swap two values in the given {@code boolean} array using their indexes.</p>
	 * @param array the {@code boolean} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @throws IllegalArgumentException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final boolean[] array, final int index1, final int index2) {
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
	 * <p>Add a {@code boolean} value at the end of the given {@code boolean} array.</p>
	 * @param array the {@code boolean} array to add to
	 * @param value the {@code boolean} value to add
	 * @return a {@code boolean} array with the added {@code boolean} value
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @since 1.4.0
	 */
	public static boolean[] add(final boolean[] array, final boolean value) {
		Ensure.notNull("array", array);
		return add(array, array.length, value);
	}

	/**
	 * <p>Add a {@code boolean} value at the provided index of the given {@code boolean} array.</p>
	 * @param array the {@code boolean} array to add to
	 * @param index the index of the {@code boolean} value
	 * @param value the {@code boolean} value to add
	 * @return a {@code boolean} array with the added {@code boolean} value
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @since 1.4.0
	 */
	public static boolean[] add(final boolean[] array, final int index, final boolean value) {
		Ensure.notNull("array", array);
		Ensure.between("index", index, 0, array.length);
		final var result = new boolean[array.length + 1];
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
	 * <p>Remove a {@code boolean} value at the provided index of the given {@code boolean} array.</p>
	 * @param array the {@code boolean} array to remove from
	 * @param index the index of the {@code boolean} value
	 * @return a {@code boolean} array with the removed {@code boolean} value
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} array is empty or if the index is not valid
	 * @since 1.4.0
	 */
	public static boolean[] remove(final boolean[] array, final int index) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.between("index", index, 0, array.length - 1);
		final var result = new boolean[array.length - 1];
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		if (index < array.length - 1) {
			System.arraycopy(array, index + 1, result, index, array.length - index - 1);
		}
		return result;
	}

	/**
	 * <p>Concatenate multiple {@code boolean} arrays.</p>
	 * @param arrays the {@code boolean} array array to concatenate
	 * @return the concatenated {@code boolean} array
	 * @throws NullPointerException if the {@code boolean} array array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static boolean[] concat(final boolean[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(List.of(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code boolean} arrays.</p>
	 * @param arrays the {@code boolean} array {@link List} to concatenate
	 * @return the concatenated {@code boolean} array
	 * @throws NullPointerException if the {@code boolean} array {@link List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static boolean[] concat(final List<boolean[]> arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new boolean[arrays.stream().mapToInt(array -> array.length).sum()];
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * <p>Join multiple {@code boolean} arrays using a {@code boolean} array separator.</p>
	 * @param separator the {@code boolean} array separator
	 * @param arrays the {@code boolean} array array to join
	 * @return the joined {@code boolean} array
	 * @throws NullPointerException if the {@code boolean} array separator, the {@code boolean} array array or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	public static boolean[] join(final boolean[] separator, final boolean[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * <p>Join multiple {@code boolean} arrays using a {@code boolean} array separator.</p>
	 * @param separator the {@code boolean} array separator
	 * @param arrays the {@code boolean} array {@link List} to join
	 * @return the joined {@code boolean} array
	 * @throws NullPointerException if the {@code boolean} array separator, the {@code boolean} array {@link List} or
	 *         any of them is {@code null}
	 * @since 1.0.0
	 */
	public static boolean[] join(final boolean[] separator, final List<boolean[]> arrays) {
		Ensure.notNull("separator", separator);
		Ensure.notNullAndNotNullElements("arrays", arrays);
		if (isEmpty(separator)) {
			return concat(arrays);
		}
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new boolean[arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length];
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
	 * <p>Create a {@code boolean} array from a single {@code boolean} value.</p>
	 * @param value the {@code boolean} value to convert
	 * @return the created {@code boolean} array
	 * @since 1.1.0
	 */
	public static boolean[] singleton(final boolean value) {
		return of(value);
	}

	/**
	 * <p>Create a {@code boolean} array from multiple {@code boolean} values.</p>
	 * @param values {@code boolean} values to convert
	 * @return the created {@code boolean} array
	 * @throws NullPointerException if the {@code boolean} values array is {@code null}
	 * @since 1.0.0
	 */
	public static boolean[] of(final boolean... values) {
		Ensure.notNull("values", values);
		if (isEmpty(values)) {
			return EMPTY;
		}
		return values;
	}

	/**
	 * <p>Create a {@code boolean} array from a boxed {@link Boolean} array.</p>
	 * @param boxedArray the boxed {@link Boolean} array to convert
	 * @return the created {@code boolean} array
	 * @throws NullPointerException if the boxed {@link Boolean} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean[] of(final Boolean[] boxedArray) {
		Ensure.notNull("boxedArray", boxedArray);
		if (ObjectArrays.isEmpty(boxedArray)) {
			return EMPTY;
		}
		final var array = new boolean[boxedArray.length];
		for (var i = 0; i < array.length; ++i) {
			array[i] = boxedArray[i];
		}
		return array;
	}

	/**
	 * <p>Convert a {@code boolean} array to a boxed {@link Boolean} array.</p>
	 * @param array the {@code boolean} array to convert
	 * @return the created boxed {@link Boolean} array
	 * @throws NullPointerException if the {@code boolean} array is {@code null}
	 * @since 1.2.0
	 */
	public static Boolean[] toBoxed(final boolean[] array) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return ObjectArrays.empty(Boolean.class);
		}
		final var boxedArray = new Boolean[array.length];
		Arrays.setAll(boxedArray, i -> array[i]);
		return boxedArray;
	}
}