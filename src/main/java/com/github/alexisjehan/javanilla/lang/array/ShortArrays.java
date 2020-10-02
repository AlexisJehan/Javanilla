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
 * <p>An utility class that provides {@code short} array tools.</p>
 * @since 1.0.0
 */
public final class ShortArrays {

	/**
	 * <p>An empty {@code short} array.</p>
	 * @since 1.0.0
	 */
	public static final short[] EMPTY = {};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private ShortArrays() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code short} array replacing {@code null} by an empty one.</p>
	 * @param array the {@code short} array or {@code null}
	 * @return a non-{@code null} {@code short} array
	 * @since 1.0.0
	 */
	public static short[] nullToEmpty(final short[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * <p>Wrap a {@code short} array replacing {@code null} by a default one.</p>
	 * @param array the {@code short} array or {@code null}
	 * @param defaultArray the default {@code short} array
	 * @return a non-{@code null} {@code short} array
	 * @throws NullPointerException if the default {@code short} array is {@code null}
	 * @since 1.1.0
	 */
	public static short[] nullToDefault(final short[] array, final short[] defaultArray) {
		Ensure.notNull("defaultArray", defaultArray);
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap a {@code short} array replacing an empty one by {@code null}.</p>
	 * @param array the {@code short} array or {@code null}
	 * @return a non-empty {@code short} array or {@code null}
	 * @since 1.0.0
	 */
	public static short[] emptyToNull(final short[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap a {@code short} array replacing an empty one by a default {@code short} array.</p>
	 * @param array the {@code short} array or {@code null}
	 * @param defaultArray the default {@code short} array or {@code null}
	 * @return a non-empty {@code short} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code short} array is empty
	 * @since 1.1.0
	 */
	public static short[] emptyToDefault(final short[] array, final short[] defaultArray) {
		if (null != defaultArray) {
			Ensure.notNullAndNotEmpty("defaultArray", defaultArray);
		}
		return null == array || !isEmpty(array) ? array : defaultArray;
	}

	/**
	 * <p>Tell if a {@code short} array is empty.</p>
	 * @param array the {@code short} array to test
	 * @return {@code true} if the {@code short} array is empty
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final short[] array) {
		Ensure.notNull("array", array);
		return 0 == array.length;
	}

	/**
	 * <p>Tell if the {@code short} array contains any of given {@code short} values at least one.</p>
	 * @param array the {@code short} array to test
	 * @param values {@code short} values to test
	 * @return {@code true} if any of given {@code short} values is contained at least once by the {@code short} array
	 * @throws NullPointerException if the {@code short} array or the {@code short} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final short[] array, final short... values) {
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
	 * <p>Tell if the {@code short} array contains all of given {@code short} values at least one.</p>
	 * @param array the {@code short} array to test
	 * @param values {@code short} values to test
	 * @return {@code true} if all of given {@code short} values are contained at least once by the {@code short} array
	 * @throws NullPointerException if the {@code short} array or the {@code short} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final short[] array, final short... values) {
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
	 * <p>Tell if the {@code short} array contains each given {@code short} value only once.</p>
	 * @param array the {@code short} array to test
	 * @param values {@code short} values to test
	 * @return {@code true} if each of given {@code short} values are contained only once by the {@code short} array
	 * @throws NullPointerException if the {@code short} array or the {@code short} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final short[] array, final short... values) {
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
	 * <p>Tell if the {@code short} array contains only given {@code short} values at least one.</p>
	 * @param array the {@code short} array to test
	 * @param values {@code short} values to test
	 * @return {@code true} if given {@code short} values are only values contained by the {@code short} array
	 * @throws NullPointerException if the {@code short} array or the {@code short} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final short[] array, final short... values) {
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
	 * <p>Get the first index of the {@code short} value in the {@code short} array.</p>
	 * @param array the {@code short} array to iterate
	 * @param value the {@code short} value to search
	 * @return the first index of the {@code short} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final short[] array, final short value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code short} value in the {@code short} array starting from the given index.</p>
	 * @param array the {@code short} array to iterate
	 * @param value the {@code short} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code short} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final short[] array, final short value, final int fromIndex) {
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
	 * <p>Get the last index of the {@code short} value in the {@code short} array.</p>
	 * @param array the {@code short} array to iterate
	 * @param value the {@code short} value to search
	 * @return the last index of the {@code short} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final short[] array, final short value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code short} value in the {@code short} array starting from the given index.</p>
	 * @param array the {@code short} array to iterate
	 * @param value the {@code short} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code short} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final short[] array, final short value, final int fromIndex) {
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
	 * <p>Calculate the number of occurrences of the {@code short} value in the {@code short} array.</p>
	 * @param array the {@code short} array to iterate
	 * @param value the {@code short} value of the frequency to calculate
	 * @return the frequency of the {@code short} value
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final short[] array, final short value) {
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
	 * <p>Shuffle values in the given {@code short} array following the Fisher-Yates algorithm.</p>
	 * @param array the {@code short} array to shuffle
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.2.0
	 * @deprecated for security purposes, use {@link #shuffle(short[], Random)} with
	 *             {@link java.security.SecureRandom} instead
	 */
	@Deprecated(since = "1.6.0")
	public static void shuffle(final short[] array) {
		shuffle(array, ThreadLocalRandom.current());
	}

	/**
	 * <p>Shuffle values in the given {@code short} array using the provided {@code Random} object following the
	 * Fisher-Yates algorithm.</p>
	 * @param array the {@code short} array to shuffle
	 * @param random the {@code Random} object to use
	 * @throws NullPointerException if the {@code short} array or the {@code Random} object is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.6.0
	 */
	public static void shuffle(final short[] array, final Random random) {
		Ensure.notNull("array", array);
		Ensure.notNull("random", random);
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * <p>Reverse values in the given {@code short} array.</p>
	 * @param array the {@code short} array to reverse
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final short[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * <p>Reorder values in the given {@code short} array using provided indexes.</p>
	 * @param array the {@code short} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code short} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code short} array is empty, if the {@code short} array length is not equal
	 *         to the indexes array length, if indexes are not distinct or if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final short[] array, final int... indexes) {
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
	 * <p>Swap two values in the given {@code short} array using their indexes.</p>
	 * @param array the {@code short} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @throws IllegalArgumentException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final short[] array, final int index1, final int index2) {
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
	 * <p>Add a {@code short} value at the end of the given {@code short} array.</p>
	 * @param array the {@code short} array to add to
	 * @param value the {@code short} value to add
	 * @return a {@code short} array with the added {@code short} value
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.4.0
	 */
	public static short[] add(final short[] array, final short value) {
		Ensure.notNull("array", array);
		return add(array, array.length, value);
	}

	/**
	 * <p>Add a {@code short} value at the provided index of the given {@code short} array.</p>
	 * @param array the {@code short} array to add to
	 * @param index the index of the {@code short} value
	 * @param value the {@code short} value to add
	 * @return a {@code short} array with the added {@code short} value
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @since 1.4.0
	 */
	public static short[] add(final short[] array, final int index, final short value) {
		Ensure.notNull("array", array);
		Ensure.between("index", index, 0, array.length);
		final var result = new short[array.length + 1];
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
	 * <p>Remove a {@code short} value at the provided index of the given {@code short} array.</p>
	 * @param array the {@code short} array to remove from
	 * @param index the index of the {@code short} value
	 * @return a {@code short} array with the removed {@code short} value
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} array is empty or if the index is not valid
	 * @since 1.4.0
	 */
	public static short[] remove(final short[] array, final int index) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.between("index", index, 0, array.length - 1);
		final var result = new short[array.length - 1];
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		if (index < array.length - 1) {
			System.arraycopy(array, index + 1, result, index, array.length - index - 1);
		}
		return result;
	}

	/**
	 * <p>Concatenate multiple {@code short} arrays.</p>
	 * @param arrays the {@code short} array array to concatenate
	 * @return the concatenated {@code short} array
	 * @throws NullPointerException if the {@code short} array array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static short[] concat(final short[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(List.of(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code short} arrays.</p>
	 * @param arrays the {@code short} array {@link List} to concatenate
	 * @return the concatenated {@code short} array
	 * @throws NullPointerException if the {@code short} array {@link List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static short[] concat(final List<short[]> arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new short[arrays.stream().mapToInt(array -> array.length).sum()];
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * <p>Join multiple {@code short} arrays using a {@code short} array separator.</p>
	 * @param separator the {@code short} array separator
	 * @param arrays the {@code short} array array to join
	 * @return the joined {@code short} array
	 * @throws NullPointerException if the {@code short} array separator, the {@code short} array array or any of them
	 *         is {@code null}
	 * @since 1.0.0
	 */
	public static short[] join(final short[] separator, final short[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * <p>Join multiple {@code short} arrays using a {@code short} array separator.</p>
	 * @param separator the {@code short} array separator
	 * @param arrays the {@code short} array {@link List} to join
	 * @return the joined {@code short} array
	 * @throws NullPointerException if the {@code short} array separator, the {@code short} array {@link List} or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	public static short[] join(final short[] separator, final List<short[]> arrays) {
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
		final var result = new short[arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length];
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
	 * <p>Create a {@code short} array from a single {@code short} value.</p>
	 * @param value the {@code short} value to convert
	 * @return the created {@code short} array
	 * @since 1.1.0
	 */
	public static short[] singleton(final short value) {
		return of(value);
	}

	/**
	 * <p>Create a {@code short} array from multiple {@code short} values.</p>
	 * @param values {@code short} values to convert
	 * @return the created {@code short} array
	 * @throws NullPointerException if the {@code short} values array is {@code null}
	 * @since 1.0.0
	 */
	public static short[] of(final short... values) {
		Ensure.notNull("values", values);
		if (isEmpty(values)) {
			return EMPTY;
		}
		return values;
	}

	/**
	 * <p>Create a {@code short} array from a boxed {@link Short} array.</p>
	 * @param boxedArray the boxed {@link Short} array to convert
	 * @return the created {@code short} array
	 * @throws NullPointerException if the boxed {@link Short} array is {@code null}
	 * @since 1.2.0
	 */
	public static short[] of(final Short[] boxedArray) {
		Ensure.notNull("boxedArray", boxedArray);
		if (ObjectArrays.isEmpty(boxedArray)) {
			return EMPTY;
		}
		final var array = new short[boxedArray.length];
		for (var i = 0; i < array.length; ++i) {
			array[i] = boxedArray[i];
		}
		return array;
	}

	/**
	 * <p>Convert a {@code short} array to a boxed {@link Short} array.</p>
	 * @param array the {@code short} array to convert
	 * @return the created boxed {@link Short} array
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.2.0
	 */
	public static Short[] toBoxed(final short[] array) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return ObjectArrays.empty(Short.class);
		}
		final var boxedArray = new Short[array.length];
		for (var i = 0; i < boxedArray.length; ++i) {
			boxedArray[i] = array[i];
		}
		return boxedArray;
	}
}