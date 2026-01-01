/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
 * A utility class that provides {@code long} array tools.
 * @since 1.0.0
 */
public final class LongArrays {

	/**
	 * An empty {@code long} array.
	 * @since 1.0.0
	 */
	public static final long[] EMPTY = {};

	/**
	 * Constructor.
	 * @since 1.0.0
	 */
	private LongArrays() {}

	/**
	 * Wrap a {@code long} array replacing {@code null} by an empty one.
	 * @param array the {@code long} array or {@code null}
	 * @return a non-{@code null} {@code long} array
	 * @since 1.0.0
	 */
	public static long[] nullToEmpty(final long[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * Wrap a {@code long} array replacing {@code null} by a default one.
	 * @param array the {@code long} array or {@code null}
	 * @param defaultArray the default {@code long} array
	 * @return a non-{@code null} {@code long} array
	 * @throws NullPointerException if the default {@code long} array is {@code null}
	 * @since 1.1.0
	 */
	public static long[] nullToDefault(final long[] array, final long[] defaultArray) {
		Ensure.notNull("defaultArray", defaultArray);
		return null != array ? array : defaultArray;
	}

	/**
	 * Wrap a {@code long} array replacing an empty one by {@code null}.
	 * @param array the {@code long} array or {@code null}
	 * @return a non-empty {@code long} array or {@code null}
	 * @since 1.0.0
	 */
	public static long[] emptyToNull(final long[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * Wrap a {@code long} array replacing an empty one by a default {@code long} array.
	 * @param array the {@code long} array or {@code null}
	 * @param defaultArray the default {@code long} array or {@code null}
	 * @return a non-empty {@code long} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code long} array is empty
	 * @since 1.1.0
	 */
	public static long[] emptyToDefault(final long[] array, final long[] defaultArray) {
		if (null != defaultArray) {
			Ensure.notNullAndNotEmpty("defaultArray", defaultArray);
		}
		return null == array || !isEmpty(array) ? array : defaultArray;
	}

	/**
	 * Add a {@code long} value at the end of the given {@code long} array.
	 * @param array the {@code long} array to add to
	 * @param value the {@code long} value to add
	 * @return a {@code long} array with the added {@code long} value
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @since 1.4.0
	 */
	public static long[] add(final long[] array, final long value) {
		Ensure.notNull("array", array);
		return add(array, value, array.length);
	}

	/**
	 * Add a {@code long} value at the provided index of the given {@code long} array.
	 * @param array the {@code long} array to add to
	 * @param index the index of the {@code long} value
	 * @param value the {@code long} value to add
	 * @return a {@code long} array with the added {@code long} value
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @deprecated since 1.6.0, use {@link #add(long[], long, int)} instead
	 * @since 1.4.0
	 */
	@Deprecated(since = "1.6.0")
	public static long[] add(final long[] array, final int index, final long value) {
		return add(array, value, index);
	}

	/**
	 * Add a {@code long} value at the provided index of the given {@code long} array.
	 * @param array the {@code long} array to add to
	 * @param value the {@code long} value to add
	 * @param index the index of the {@code long} value
	 * @return a {@code long} array with the added {@code long} value
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @since 1.6.0
	 */
	public static long[] add(final long[] array, final long value, final int index) {
		Ensure.notNull("array", array);
		Ensure.between("index", index, 0, array.length);
		final var result = new long[array.length + 1];
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
	 * Remove a {@code long} value at the provided index of the given {@code long} array.
	 * @param array the {@code long} array to remove from
	 * @param index the index of the {@code long} value
	 * @return a {@code long} array with the removed {@code long} value
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @throws IllegalArgumentException if the {@code long} array is empty or if the index is not valid
	 * @since 1.4.0
	 */
	public static long[] remove(final long[] array, final int index) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.between("index", index, 0, array.length - 1);
		final var result = new long[array.length - 1];
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		if (index < array.length - 1) {
			System.arraycopy(array, index + 1, result, index, array.length - index - 1);
		}
		return result;
	}

	/**
	 * Concatenate multiple {@code long} arrays.
	 * @param arrays {@code long} arrays to concatenate
	 * @return the concatenated {@code long} array
	 * @throws NullPointerException if {@code long} arrays or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static long[] concat(final long[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(List.of(arrays));
	}

	/**
	 * Concatenate multiple {@code long} arrays.
	 * @param arrays the {@code long} array {@link List} to concatenate
	 * @return the concatenated {@code long} array
	 * @throws NullPointerException if the {@code long} array {@link List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static long[] concat(final List<long[]> arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new long[arrays.stream().mapToInt(array -> array.length).sum()];
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * Join multiple {@code long} arrays using a {@code long} array separator.
	 * @param separator the {@code long} array separator
	 * @param arrays {@code long} arrays to join
	 * @return the joined {@code long} array
	 * @throws NullPointerException if the {@code long} array separator, {@code long} arrays or any of them is
	 *         {@code null}
	 * @since 1.0.0
	 */
	public static long[] join(final long[] separator, final long[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * Join multiple {@code long} arrays using a {@code long} array separator.
	 * @param separator the {@code long} array separator
	 * @param arrays the {@code long} array {@link List} to join
	 * @return the joined {@code long} array
	 * @throws NullPointerException if the {@code long} array separator, the {@code long} array {@link List} or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	public static long[] join(final long[] separator, final List<long[]> arrays) {
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
		final var result = new long[arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length];
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
	 * Tell if the {@code long} array contains any of given {@code long} values at least one.
	 * @param array the {@code long} array to test
	 * @param values {@code long} values to test
	 * @return {@code true} if any of given {@code long} values is contained at least once by the {@code long} array
	 * @throws NullPointerException if the {@code long} array or the {@code long} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code long} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final long[] array, final long... values) {
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
	 * Tell if the {@code long} array contains all of given {@code long} values at least one.
	 * @param array the {@code long} array to test
	 * @param values {@code long} values to test
	 * @return {@code true} if all of given {@code long} values are contained at least once by the {@code long} array
	 * @throws NullPointerException if the {@code long} array or the {@code long} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code long} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final long[] array, final long... values) {
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
	 * Tell if the {@code long} array contains each given {@code long} value only once.
	 * @param array the {@code long} array to test
	 * @param values {@code long} values to test
	 * @return {@code true} if each of given {@code long} values are contained only once by the {@code long} array
	 * @throws NullPointerException if the {@code long} array or the {@code long} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code long} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final long[] array, final long... values) {
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
	 * Tell if the {@code long} array contains only given {@code long} values at least one.
	 * @param array the {@code long} array to test
	 * @param values {@code long} values to test
	 * @return {@code true} if given {@code long} values are only values contained by the {@code long} array
	 * @throws NullPointerException if the {@code long} array or the {@code long} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code long} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final long[] array, final long... values) {
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
	 * Get the first index of the {@code long} value in the {@code long} array.
	 * @param array the {@code long} array to iterate
	 * @param value the {@code long} value to search
	 * @return the first index of the {@code long} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final long[] array, final long value) {
		return indexOf(array, value, 0);
	}

	/**
	 * Get the first index of the {@code long} value in the {@code long} array starting from the given index.
	 * @param array the {@code long} array to iterate
	 * @param value the {@code long} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code long} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final long[] array, final long value, final int fromIndex) {
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
	 * Get the last index of the {@code long} value in the {@code long} array.
	 * @param array the {@code long} array to iterate
	 * @param value the {@code long} value to search
	 * @return the last index of the {@code long} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final long[] array, final long value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * Get the last index of the {@code long} value in the {@code long} array starting from the given index.
	 * @param array the {@code long} array to iterate
	 * @param value the {@code long} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code long} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final long[] array, final long value, final int fromIndex) {
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
	 * Calculate the number of occurrences of the {@code long} value in the {@code long} array.
	 * @param array the {@code long} array to iterate
	 * @param value the {@code long} value of the frequency to calculate
	 * @return the frequency of the {@code long} value
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final long[] array, final long value) {
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
	 * Reverse values in the given {@code long} array.
	 * @param array the {@code long} array to reverse
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final long[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * Reorder values in the given {@code long} array using provided indexes.
	 * @param array the {@code long} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code long} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code long} array is empty, if the {@code long} array length is not equal to
	 *         the indexes array length, if indexes are not distinct or if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final long[] array, final int... indexes) {
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
	 * Shuffle values in the given {@code long} array following the Fisher-Yates algorithm.
	 * @param array the {@code long} array to shuffle
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @deprecated since 1.6.0, for security purposes, use {@link #shuffle(long[], Random)} with
	 *             {@link java.security.SecureRandom} instead
	 * @since 1.2.0
	 */
	@Deprecated(since = "1.6.0")
	public static void shuffle(final long[] array) {
		shuffle(array, ThreadLocalRandom.current());
	}

	/**
	 * Shuffle values in the given {@code long} array using the provided {@code Random} object following the
	 * Fisher-Yates algorithm.
	 * @param array the {@code long} array to shuffle
	 * @param random the {@code Random} object to use
	 * @throws NullPointerException if the {@code long} array or the {@code Random} object is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.6.0
	 */
	public static void shuffle(final long[] array, final Random random) {
		Ensure.notNull("array", array);
		Ensure.notNull("random", random);
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * Swap two values in the given {@code long} array using their indexes.
	 * @param array the {@code long} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @throws IllegalArgumentException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final long[] array, final int index1, final int index2) {
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
	 * Tell if a {@code long} array is empty.
	 * @param array the {@code long} array to test
	 * @return {@code true} if the {@code long} array is empty
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final long[] array) {
		Ensure.notNull("array", array);
		return 0 == array.length;
	}

	/**
	 * Create a {@code long} array from a single {@code long} value.
	 * @param value the {@code long} value to convert
	 * @return the created {@code long} array
	 * @since 1.1.0
	 */
	public static long[] singleton(final long value) {
		return of(value);
	}

	/**
	 * Create a {@code long} array from multiple {@code long} values.
	 * @param values {@code long} values to convert
	 * @return the created {@code long} array
	 * @throws NullPointerException if the {@code long} values array is {@code null}
	 * @since 1.0.0
	 */
	public static long[] of(final long... values) {
		Ensure.notNull("values", values);
		if (isEmpty(values)) {
			return EMPTY;
		}
		return values;
	}

	/**
	 * Create a {@code long} array from a boxed {@link Long} array.
	 * @param boxedArray the boxed {@link Long} array to convert
	 * @return the created {@code long} array
	 * @throws NullPointerException if the boxed {@link Long} array is {@code null}
	 * @since 1.2.0
	 */
	public static long[] of(final Long[] boxedArray) {
		Ensure.notNull("boxedArray", boxedArray);
		if (ObjectArrays.isEmpty(boxedArray)) {
			return EMPTY;
		}
		final var array = new long[boxedArray.length];
		for (var i = 0; i < array.length; ++i) {
			array[i] = boxedArray[i];
		}
		return array;
	}

	/**
	 * Convert a {@code long} array to a boxed {@link Long} array.
	 * @param array the {@code long} array to convert
	 * @return the created boxed {@link Long} array
	 * @throws NullPointerException if the {@code long} array is {@code null}
	 * @since 1.2.0
	 */
	public static Long[] toBoxed(final long[] array) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return ObjectArrays.empty(Long.class);
		}
		final var boxedArray = new Long[array.length];
		Arrays.setAll(boxedArray, i -> array[i]);
		return boxedArray;
	}
}