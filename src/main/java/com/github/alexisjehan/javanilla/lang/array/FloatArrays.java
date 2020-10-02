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
 * <p>An utility class that provides {@code float} array tools.</p>
 * @since 1.0.0
 */
public final class FloatArrays {

	/**
	 * <p>An empty {@code float} array.</p>
	 * @since 1.0.0
	 */
	public static final float[] EMPTY = {};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private FloatArrays() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code float} array replacing {@code null} by an empty one.</p>
	 * @param array the {@code float} array or {@code null}
	 * @return a non-{@code null} {@code float} array
	 * @since 1.0.0
	 */
	public static float[] nullToEmpty(final float[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * <p>Wrap a {@code float} array replacing {@code null} by a default one.</p>
	 * @param array the {@code float} array or {@code null}
	 * @param defaultArray the default {@code float} array
	 * @return a non-{@code null} {@code float} array
	 * @throws NullPointerException if the default {@code float} array is {@code null}
	 * @since 1.1.0
	 */
	public static float[] nullToDefault(final float[] array, final float[] defaultArray) {
		Ensure.notNull("defaultArray", defaultArray);
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap a {@code float} array replacing an empty one by {@code null}.</p>
	 * @param array the {@code float} array or {@code null}
	 * @return a non-empty {@code float} array or {@code null}
	 * @since 1.0.0
	 */
	public static float[] emptyToNull(final float[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap a {@code float} array replacing an empty one by a default {@code float} array.</p>
	 * @param array the {@code float} array or {@code null}
	 * @param defaultArray the default {@code float} array or {@code null}
	 * @return a non-empty {@code float} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code float} array is empty
	 * @since 1.1.0
	 */
	public static float[] emptyToDefault(final float[] array, final float[] defaultArray) {
		if (null != defaultArray) {
			Ensure.notNullAndNotEmpty("defaultArray", defaultArray);
		}
		return null == array || !isEmpty(array) ? array : defaultArray;
	}

	/**
	 * <p>Tell if a {@code float} array is empty.</p>
	 * @param array the {@code float} array to test
	 * @return {@code true} if the {@code float} array is empty
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final float[] array) {
		Ensure.notNull("array", array);
		return 0 == array.length;
	}

	/**
	 * <p>Tell if the {@code float} array contains any of given {@code float} values at least one.</p>
	 * @param array the {@code float} array to test
	 * @param values {@code float} values to test
	 * @return {@code true} if any of given {@code float} values is contained at least once by the {@code float} array
	 * @throws NullPointerException if the {@code float} array or the {@code float} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code float} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final float[] array, final float... values) {
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
	 * <p>Tell if the {@code float} array contains all of given {@code float} values at least one.</p>
	 * @param array the {@code float} array to test
	 * @param values {@code float} values to test
	 * @return {@code true} if all of given {@code float} values are contained at least once by the {@code float} array
	 * @throws NullPointerException if the {@code float} array or the {@code float} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code float} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final float[] array, final float... values) {
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
	 * <p>Tell if the {@code float} array contains each given {@code float} value only once.</p>
	 * @param array the {@code float} array to test
	 * @param values {@code float} values to test
	 * @return {@code true} if each of given {@code float} values are contained only once by the {@code float} array
	 * @throws NullPointerException if the {@code float} array or the {@code float} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code float} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final float[] array, final float... values) {
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
	 * <p>Tell if the {@code float} array contains only given {@code float} values at least one.</p>
	 * @param array the {@code float} array to test
	 * @param values {@code float} values to test
	 * @return {@code true} if given {@code float} values are only values contained by the {@code float} array
	 * @throws NullPointerException if the {@code float} array or the {@code float} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code float} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final float[] array, final float... values) {
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
	 * <p>Get the first index of the {@code float} value in the {@code float} array.</p>
	 * @param array the {@code float} array to iterate
	 * @param value the {@code float} value to search
	 * @return the first index of the {@code float} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final float[] array, final float value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code float} value in the {@code float} array starting from the given index.</p>
	 * @param array the {@code float} array to iterate
	 * @param value the {@code float} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code float} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final float[] array, final float value, final int fromIndex) {
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
	 * <p>Get the last index of the {@code float} value in the {@code float} array.</p>
	 * @param array the {@code float} array to iterate
	 * @param value the {@code float} value to search
	 * @return the last index of the {@code float} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final float[] array, final float value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code float} value in the {@code float} array starting from the given index.</p>
	 * @param array the {@code float} array to iterate
	 * @param value the {@code float} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code float} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final float[] array, final float value, final int fromIndex) {
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
	 * <p>Calculate the number of occurrences of the {@code float} value in the {@code float} array.</p>
	 * @param array the {@code float} array to iterate
	 * @param value the {@code float} value of the frequency to calculate
	 * @return the frequency of the {@code float} value
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final float[] array, final float value) {
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
	 * <p>Shuffle values in the given {@code float} array following the Fisher-Yates algorithm.</p>
	 * @param array the {@code float} array to shuffle
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.2.0
	 * @deprecated for security purposes, use {@link #shuffle(float[], Random)} with
	 *             {@link java.security.SecureRandom} instead
	 */
	@Deprecated(since = "1.6.0")
	public static void shuffle(final float[] array) {
		shuffle(array, ThreadLocalRandom.current());
	}

	/**
	 * <p>Shuffle values in the given {@code float} array using the provided {@code Random} object following the
	 * Fisher-Yates algorithm.</p>
	 * @param array the {@code float} array to shuffle
	 * @param random the {@code Random} object to use
	 * @throws NullPointerException if the {@code float} array or the {@code Random} object is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.6.0
	 */
	public static void shuffle(final float[] array, final Random random) {
		Ensure.notNull("array", array);
		Ensure.notNull("random", random);
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * <p>Reverse values in the given {@code float} array.</p>
	 * @param array the {@code float} array to reverse
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final float[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * <p>Reorder values in the given {@code float} array using provided indexes.</p>
	 * @param array the {@code float} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code float} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code float} array is empty, if the {@code float} array length is not equal
	 *         to the indexes array length, if indexes are not distinct or if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final float[] array, final int... indexes) {
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
	 * <p>Swap two values in the given {@code float} array using their indexes.</p>
	 * @param array the {@code float} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @throws IllegalArgumentException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final float[] array, final int index1, final int index2) {
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
	 * <p>Add a {@code float} value at the end of the given {@code float} array.</p>
	 * @param array the {@code float} array to add to
	 * @param value the {@code float} value to add
	 * @return a {@code float} array with the added {@code float} value
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @since 1.4.0
	 */
	public static float[] add(final float[] array, final float value) {
		Ensure.notNull("array", array);
		return add(array, array.length, value);
	}

	/**
	 * <p>Add a {@code float} value at the provided index of the given {@code float} array.</p>
	 * @param array the {@code float} array to add to
	 * @param index the index of the {@code float} value
	 * @param value the {@code float} value to add
	 * @return a {@code float} array with the added {@code float} value
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @since 1.4.0
	 */
	public static float[] add(final float[] array, final int index, final float value) {
		Ensure.notNull("array", array);
		Ensure.between("index", index, 0, array.length);
		final var result = new float[array.length + 1];
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
	 * <p>Remove a {@code float} value at the provided index of the given {@code float} array.</p>
	 * @param array the {@code float} array to remove from
	 * @param index the index of the {@code float} value
	 * @return a {@code float} array with the removed {@code float} value
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @throws IllegalArgumentException if the {@code float} array is empty or if the index is not valid
	 * @since 1.4.0
	 */
	public static float[] remove(final float[] array, final int index) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.between("index", index, 0, array.length - 1);
		final var result = new float[array.length - 1];
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		if (index < array.length - 1) {
			System.arraycopy(array, index + 1, result, index, array.length - index - 1);
		}
		return result;
	}

	/**
	 * <p>Concatenate multiple {@code float} arrays.</p>
	 * @param arrays the {@code float} array array to concatenate
	 * @return the concatenated {@code float} array
	 * @throws NullPointerException if the {@code float} array array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static float[] concat(final float[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(List.of(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code float} arrays.</p>
	 * @param arrays the {@code float} array {@link List} to concatenate
	 * @return the concatenated {@code float} array
	 * @throws NullPointerException if the {@code float} array {@link List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static float[] concat(final List<float[]> arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new float[arrays.stream().mapToInt(array -> array.length).sum()];
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * <p>Join multiple {@code float} arrays using a {@code float} array separator.</p>
	 * @param separator the {@code float} array separator
	 * @param arrays the {@code float} array array to join
	 * @return the joined {@code float} array
	 * @throws NullPointerException if the {@code float} array separator, the {@code float} array array or any of them
	 *         is {@code null}
	 * @since 1.0.0
	 */
	public static float[] join(final float[] separator, final float[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * <p>Join multiple {@code float} arrays using a {@code float} array separator.</p>
	 * @param separator the {@code float} array separator
	 * @param arrays the {@code float} array {@link List} to join
	 * @return the joined {@code float} array
	 * @throws NullPointerException if the {@code float} array separator, the {@code float} array {@link List} or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	public static float[] join(final float[] separator, final List<float[]> arrays) {
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
		final var result = new float[arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length];
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
	 * <p>Create a {@code float} array from a single {@code float} value.</p>
	 * @param value the {@code float} value to convert
	 * @return the created {@code float} array
	 * @since 1.1.0
	 */
	public static float[] singleton(final float value) {
		return of(value);
	}

	/**
	 * <p>Create a {@code float} array from multiple {@code float} values.</p>
	 * @param values {@code float} values to convert
	 * @return the created {@code float} array
	 * @throws NullPointerException if the {@code float} values array is {@code null}
	 * @since 1.0.0
	 */
	public static float[] of(final float... values) {
		Ensure.notNull("values", values);
		if (isEmpty(values)) {
			return EMPTY;
		}
		return values;
	}

	/**
	 * <p>Create a {@code float} array from a boxed {@link Float} array.</p>
	 * @param boxedArray the boxed {@link Float} array to convert
	 * @return the created {@code float} array
	 * @throws NullPointerException if the boxed {@link Float} array is {@code null}
	 * @since 1.2.0
	 */
	public static float[] of(final Float[] boxedArray) {
		Ensure.notNull("boxedArray", boxedArray);
		if (ObjectArrays.isEmpty(boxedArray)) {
			return EMPTY;
		}
		final var array = new float[boxedArray.length];
		for (var i = 0; i < array.length; ++i) {
			array[i] = boxedArray[i];
		}
		return array;
	}

	/**
	 * <p>Convert a {@code float} array to a boxed {@link Float} array.</p>
	 * @param array the {@code float} array to convert
	 * @return the created boxed {@link Float} array
	 * @throws NullPointerException if the {@code float} array is {@code null}
	 * @since 1.2.0
	 */
	public static Float[] toBoxed(final float[] array) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return ObjectArrays.empty(Float.class);
		}
		final var boxedArray = new Float[array.length];
		Arrays.setAll(boxedArray, i -> array[i]);
		return boxedArray;
	}
}