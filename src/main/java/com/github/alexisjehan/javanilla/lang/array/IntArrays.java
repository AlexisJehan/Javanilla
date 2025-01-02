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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class that provides {@code int} array tools.
 * @since 1.0.0
 */
public final class IntArrays {

	/**
	 * An empty {@code int} array.
	 * @since 1.0.0
	 */
	public static final int[] EMPTY = {};

	/**
	 * Constructor.
	 * @since 1.0.0
	 */
	private IntArrays() {}

	/**
	 * Wrap an {@code int} array replacing {@code null} by an empty one.
	 * @param array the {@code int} array or {@code null}
	 * @return a non-{@code null} {@code int} array
	 * @since 1.0.0
	 */
	public static int[] nullToEmpty(final int[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * Wrap an {@code int} array replacing {@code null} by a default one.
	 * @param array the {@code int} array or {@code null}
	 * @param defaultArray the default {@code int} array
	 * @return a non-{@code null} {@code int} array
	 * @throws NullPointerException if the default {@code int} array is {@code null}
	 * @since 1.1.0
	 */
	public static int[] nullToDefault(final int[] array, final int[] defaultArray) {
		Ensure.notNull("defaultArray", defaultArray);
		return null != array ? array : defaultArray;
	}

	/**
	 * Wrap an {@code int} array replacing an empty one by {@code null}.
	 * @param array the {@code int} array or {@code null}
	 * @return a non-empty {@code int} array or {@code null}
	 * @since 1.0.0
	 */
	public static int[] emptyToNull(final int[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * Wrap an {@code int} array replacing an empty one by a default {@code int} array.
	 * @param array the {@code int} array or {@code null}
	 * @param defaultArray the default {@code int} array or {@code null}
	 * @return a non-empty {@code int} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code int} array is empty
	 * @since 1.1.0
	 */
	public static int[] emptyToDefault(final int[] array, final int[] defaultArray) {
		if (null != defaultArray) {
			Ensure.notNullAndNotEmpty("defaultArray", defaultArray);
		}
		return null == array || !isEmpty(array) ? array : defaultArray;
	}

	/**
	 * Add an {@code int} value at the end of the given {@code int} array.
	 * @param array the {@code int} array to add to
	 * @param value the {@code int} value to add
	 * @return an {@code int} array with the added {@code int} value
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.4.0
	 */
	public static int[] add(final int[] array, final int value) {
		Ensure.notNull("array", array);
		return add(array, value, array.length);
	}

	/**
	 * Add an {@code int} value at the provided index of the given {@code int} array.
	 * @param array the {@code int} array to add to
	 * @param value the {@code int} value to add
	 * @param index the index of the {@code int} value
	 * @return an {@code int} array with the added {@code int} value
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @since 1.8.0
	 */
	public static int[] add(final int[] array, final int value, final int index) {
		Ensure.notNull("array", array);
		Ensure.between("index", index, 0, array.length);
		final var result = new int[array.length + 1];
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
	 * Add an {@code int} value at the provided index of the given {@code int} array.
	 * @param array the {@code int} array to add to
	 * @param value the {@code int} value to add
	 * @param index the index of the {@code int} value
	 * @return an {@code int} array with the added {@code int} value
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @deprecated since 1.8.0, use {@link #add(int[], int, int)} instead
	 * @since 1.6.0
	 */
	@Deprecated(since = "1.8.0")
	public static int[] addTemporary(final int[] array, final int value, final int index) {
		return add(array, value, index);
	}

	/**
	 * Remove an {@code int} value at the provided index of the given {@code int} array.
	 * @param array the {@code int} array to remove from
	 * @param index the index of the {@code int} value
	 * @return an {@code int} array with the removed {@code int} value
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} array is empty or if the index is not valid
	 * @since 1.4.0
	 */
	public static int[] remove(final int[] array, final int index) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.between("index", index, 0, array.length - 1);
		final var result = new int[array.length - 1];
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		if (index < array.length - 1) {
			System.arraycopy(array, index + 1, result, index, array.length - index - 1);
		}
		return result;
	}

	/**
	 * Concatenate multiple {@code int} arrays.
	 * @param arrays {@code int} arrays to concatenate
	 * @return the concatenated {@code int} array
	 * @throws NullPointerException if {@code int} arrays or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static int[] concat(final int[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(List.of(arrays));
	}

	/**
	 * Concatenate multiple {@code int} arrays.
	 * @param arrays the {@code int} array {@link List} to concatenate
	 * @return the concatenated {@code int} array
	 * @throws NullPointerException if the {@code int} array {@link List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static int[] concat(final List<int[]> arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new int[arrays.stream().mapToInt(array -> array.length).sum()];
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * Join multiple {@code int} arrays using an {@code int} array separator.
	 * @param separator the {@code int} array separator
	 * @param arrays {@code int} arrays to join
	 * @return the joined {@code int} array
	 * @throws NullPointerException if the {@code int} array separator, {@code int} arrays or any of them is
	 *         {@code null}
	 * @since 1.0.0
	 */
	public static int[] join(final int[] separator, final int[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * Join multiple {@code int} arrays using an {@code int} array separator.
	 * @param separator the {@code int} array separator
	 * @param arrays the {@code int} array {@link List} to join
	 * @return the joined {@code int} array
	 * @throws NullPointerException if the {@code int} array separator, the {@code int} array {@link List} or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	public static int[] join(final int[] separator, final List<int[]> arrays) {
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
		final var result = new int[arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length];
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
	 * Tell if the {@code int} array contains any of given {@code int} values at least one.
	 * @param array the {@code int} array to test
	 * @param values {@code int} values to test
	 * @return {@code true} if any of given {@code int} values is contained at least once by the {@code int} array
	 * @throws NullPointerException if the {@code int} array or the {@code int} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final int[] array, final int... values) {
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
	 * Tell if the {@code int} array contains all of given {@code int} values at least one.
	 * @param array the {@code int} array to test
	 * @param values {@code int} values to test
	 * @return {@code true} if all of given {@code int} values are contained at least once by the {@code int} array
	 * @throws NullPointerException if the {@code int} array or the {@code int} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final int[] array, final int... values) {
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
	 * Tell if the {@code int} array contains each given {@code int} value only once.
	 * @param array the {@code int} array to test
	 * @param values {@code int} values to test
	 * @return {@code true} if each of given {@code int} values are contained only once by the {@code int} array
	 * @throws NullPointerException if the {@code int} array or the {@code int} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final int[] array, final int... values) {
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
	 * Tell if the {@code int} array contains only given {@code int} values at least one.
	 * @param array the {@code int} array to test
	 * @param values {@code int} values to test
	 * @return {@code true} if given {@code int} values are only values contained by the {@code int} array
	 * @throws NullPointerException if the {@code int} array or the {@code int} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final int[] array, final int... values) {
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
	 * Get the first index of the {@code int} value in the {@code int} array.
	 * @param array the {@code int} array to iterate
	 * @param value the {@code int} value to search
	 * @return the first index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final int[] array, final int value) {
		return indexOf(array, value, 0);
	}

	/**
	 * Get the first index of the {@code int} value in the {@code int} array starting from the given index.
	 * @param array the {@code int} array to iterate
	 * @param value the {@code int} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final int[] array, final int value, final int fromIndex) {
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
	 * Get the last index of the {@code int} value in the {@code int} array.
	 * @param array the {@code int} array to iterate
	 * @param value the {@code int} value to search
	 * @return the last index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final int[] array, final int value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * Get the last index of the {@code int} value in the {@code int} array starting from the given index.
	 * @param array the {@code int} array to iterate
	 * @param value the {@code int} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final int[] array, final int value, final int fromIndex) {
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
	 * Calculate the number of occurrences of the {@code int} value in the {@code int} array.
	 * @param array the {@code int} array to iterate
	 * @param value the {@code int} value of the frequency to calculate
	 * @return the frequency of the {@code int} value
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final int[] array, final int value) {
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
	 * Reverse values in the given {@code int} array.
	 * @param array the {@code int} array to reverse
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final int[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * Reorder values in the given {@code int} array using provided indexes.
	 * @param array the {@code int} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code int} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code int} array is empty, if the {@code int} array length is not equal to
	 *         the indexes array length, if indexes are not distinct or if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final int[] array, final int... indexes) {
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
	 * Shuffle values in the given {@code int} array following the Fisher-Yates algorithm.
	 * @param array the {@code int} array to shuffle
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @deprecated since 1.6.0, for security purposes, use {@link #shuffle(int[], Random)} with
	 *             {@link java.security.SecureRandom} instead
	 * @since 1.2.0
	 */
	@Deprecated(since = "1.6.0")
	public static void shuffle(final int[] array) {
		shuffle(array, ThreadLocalRandom.current());
	}

	/**
	 * Shuffle values in the given {@code int} array using the provided {@code Random} object following the
	 * Fisher-Yates algorithm.
	 * @param array the {@code int} array to shuffle
	 * @param random the {@code Random} object to use
	 * @throws NullPointerException if the {@code int} array or the {@code Random} object is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.6.0
	 */
	public static void shuffle(final int[] array, final Random random) {
		Ensure.notNull("array", array);
		Ensure.notNull("random", random);
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * Swap two values in the given {@code int} array using their indexes.
	 * @param array the {@code int} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IllegalArgumentException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final int[] array, final int index1, final int index2) {
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
	 * Tell if an {@code int} array is empty.
	 * @param array the {@code int} array to test
	 * @return {@code true} if the {@code int} array is empty
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final int[] array) {
		Ensure.notNull("array", array);
		return 0 == array.length;
	}

	/**
	 * Create an {@code int} array from a single {@code int} value.
	 * @param value the {@code int} value to convert
	 * @return the created {@code int} array
	 * @since 1.1.0
	 */
	public static int[] singleton(final int value) {
		return of(value);
	}

	/**
	 * Create an {@code int} array from multiple {@code int} values.
	 * @param values {@code int} values to convert
	 * @return the created {@code int} array
	 * @throws NullPointerException if the {@code int} values array is {@code null}
	 * @since 1.0.0
	 */
	public static int[] of(final int... values) {
		Ensure.notNull("values", values);
		if (isEmpty(values)) {
			return EMPTY;
		}
		return values;
	}

	/**
	 * Create an {@code int} array from a boxed {@link Integer} array.
	 * @param boxedArray the boxed {@link Integer} array to convert
	 * @return the created {@code int} array
	 * @throws NullPointerException if the boxed {@link Integer} array is {@code null}
	 * @since 1.2.0
	 */
	public static int[] of(final Integer[] boxedArray) {
		Ensure.notNull("boxedArray", boxedArray);
		if (ObjectArrays.isEmpty(boxedArray)) {
			return EMPTY;
		}
		final var array = new int[boxedArray.length];
		for (var i = 0; i < array.length; ++i) {
			array[i] = boxedArray[i];
		}
		return array;
	}

	/**
	 * Convert an {@code int} array to a boxed {@link Integer} array.
	 * @param array the {@code int} array to convert
	 * @return the created boxed {@link Integer} array
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.2.0
	 */
	public static Integer[] toBoxed(final int[] array) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return ObjectArrays.empty(Integer.class);
		}
		final var boxedArray = new Integer[array.length];
		Arrays.setAll(boxedArray, i -> array[i]);
		return boxedArray;
	}
}