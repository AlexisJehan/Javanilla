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

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>An utility class that provides {@code double} array tools.</p>
 * @since 1.0.0
 */
public final class DoubleArrays {

	/**
	 * <p>An empty {@code double} array.</p>
	 * @since 1.0.0
	 */
	public static final double[] EMPTY = {};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private DoubleArrays() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code double} array replacing {@code null} by an empty one.</p>
	 * @param array the {@code double} array or {@code null}
	 * @return a non-{@code null} {@code double} array
	 * @since 1.0.0
	 */
	public static double[] nullToEmpty(final double[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * <p>Wrap a {@code double} array replacing {@code null} by a default one.</p>
	 * @param array the {@code double} array or {@code null}
	 * @param defaultArray the default {@code double} array
	 * @return a non-{@code null} {@code double} array
	 * @throws NullPointerException if the default {@code double} array is {@code null}
	 * @since 1.1.0
	 */
	public static double[] nullToDefault(final double[] array, final double[] defaultArray) {
		Ensure.notNull("defaultArray", defaultArray);
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap a {@code double} array replacing an empty one by {@code null}.</p>
	 * @param array the {@code double} array or {@code null}
	 * @return a non-empty {@code double} array or {@code null}
	 * @since 1.0.0
	 */
	public static double[] emptyToNull(final double[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap a {@code double} array replacing an empty one by a default {@code double} array.</p>
	 * @param array the {@code double} array or {@code null}
	 * @param defaultArray the default {@code double} array or {@code null}
	 * @return a non-empty {@code double} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code double} array is empty
	 * @since 1.1.0
	 */
	public static double[] emptyToDefault(final double[] array, final double[] defaultArray) {
		if (null != defaultArray) {
			Ensure.notNullAndNotEmpty("defaultArray", defaultArray);
		}
		return null == array || !isEmpty(array) ? array : defaultArray;
	}

	/**
	 * <p>Tell if a {@code double} array is empty.</p>
	 * @param array the {@code double} array to test
	 * @return {@code true} if the {@code double} array is empty
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final double[] array) {
		Ensure.notNull("array", array);
		return 0 == array.length;
	}

	/**
	 * <p>Tell if the {@code double} array contains any of given {@code double} values at least one.</p>
	 * @param array the {@code double} array to test
	 * @param values {@code double} values to test
	 * @return {@code true} if any of given {@code double} values is contained at least once by the {@code double} array
	 * @throws NullPointerException if the {@code double} array or the {@code double} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code double} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final double[] array, final double... values) {
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
	 * <p>Tell if the {@code double} array contains all of given {@code double} values at least one.</p>
	 * @param array the {@code double} array to test
	 * @param values {@code double} values to test
	 * @return {@code true} if all of given {@code double} values are contained at least once by the {@code double}
	 * array
	 * @throws NullPointerException if the {@code double} array or the {@code double} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code double} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final double[] array, final double... values) {
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
	 * <p>Tell if the {@code double} array contains each given {@code double} value only once.</p>
	 * @param array the {@code double} array to test
	 * @param values {@code double} values to test
	 * @return {@code true} if each of given {@code double} values are contained only once by the {@code double} array
	 * @throws NullPointerException if the {@code double} array or the {@code double} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code double} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final double[] array, final double... values) {
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
	 * <p>Tell if the {@code double} array contains only given {@code double} values at least one.</p>
	 * @param array the {@code double} array to test
	 * @param values {@code double} values to test
	 * @return {@code true} if given {@code double} values are only values contained by the {@code double} array
	 * @throws NullPointerException if the {@code double} array or the {@code double} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code double} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final double[] array, final double... values) {
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
	 * <p>Get the first index of the {@code double} value in the {@code double} array.</p>
	 * @param array the {@code double} array to iterate
	 * @param value the {@code double} value to search
	 * @return the first index of the {@code double} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final double[] array, final double value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code double} value in the {@code double} array starting from the given index.</p>
	 * @param array the {@code double} array to iterate
	 * @param value the {@code double} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code double} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final double[] array, final double value, final int fromIndex) {
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
	 * <p>Get the last index of the {@code double} value in the {@code double} array.</p>
	 * @param array the {@code double} array to iterate
	 * @param value the {@code double} value to search
	 * @return the last index of the {@code double} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final double[] array, final double value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code double} value in the {@code double} array starting from the given index.</p>
	 * @param array the {@code double} array to iterate
	 * @param value the {@code double} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code double} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final double[] array, final double value, final int fromIndex) {
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
	 * <p>Calculate the number of occurrences of the {@code double} value in the {@code double} array.</p>
	 * @param array the {@code double} array to iterate
	 * @param value the {@code double} value of the frequency to calculate
	 * @return the frequency of the {@code double} value
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final double[] array, final double value) {
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
	 * <p>Shuffle values in the given {@code double} array using the Fisher-Yates algorithm.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @param array the {@code double} array to shuffle
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @since 1.2.0
	 */
	public static void shuffle(final double[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			final var random = ThreadLocalRandom.current();
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * <p>Reverse values in the given {@code double} array.</p>
	 * @param array the {@code double} array to reverse
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final double[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * <p>Reorder values in the given {@code double} array using provided indexes.</p>
	 * @param array the {@code double} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code double} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code double} array is empty, if the {@code double} array length is not
	 * equal to the indexes array length, if indexes are not distinct or if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final double[] array, final int... indexes) {
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
	 * <p>Swap two values in the given {@code double} array using their indexes.</p>
	 * @param array the {@code double} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @throws IllegalArgumentException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final double[] array, final int index1, final int index2) {
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
	 * <p>Concatenate multiple {@code double} arrays.</p>
	 * @param arrays the {@code double} array array to concatenate
	 * @return the concatenated {@code double} array
	 * @throws NullPointerException if the {@code double} array array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static double[] concat(final double[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(List.of(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code double} arrays.</p>
	 * @param arrays the {@code double} array {@code List} to concatenate
	 * @return the concatenated {@code double} array
	 * @throws NullPointerException if the {@code double} array {@code List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static double[] concat(final List<double[]> arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new double[arrays.stream().mapToInt(array -> array.length).sum()];
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * <p>Join multiple {@code double} arrays using a {@code double} array separator.</p>
	 * @param separator the {@code double} array separator
	 * @param arrays the {@code double} array array to join
	 * @return the joined {@code double} array
	 * @throws NullPointerException if the {@code double} array separator, the {@code double} array array or any of them
	 * is {@code null}
	 * @since 1.0.0
	 */
	public static double[] join(final double[] separator, final double[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * <p>Join multiple {@code double} arrays using a {@code double} array separator.</p>
	 * @param separator the {@code double} array separator
	 * @param arrays the {@code double} array {@code List} to join
	 * @return the joined {@code double} array
	 * @throws NullPointerException if the {@code double} array separator, the {@code double} array {@code List} or any
	 * of them is {@code null}
	 * @since 1.0.0
	 */
	public static double[] join(final double[] separator, final List<double[]> arrays) {
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
		final var result = new double[arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length];
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
	 * <p>Create a {@code double} array from a single {@code double}.</p>
	 * @param d the {@code double} to convert
	 * @return the created {@code double} array
	 * @since 1.1.0
	 */
	public static double[] singleton(final double d) {
		return of(d);
	}

	/**
	 * <p>Create a {@code double} array from multiple {@code double}s.</p>
	 * @param doubles the {@code double} array to convert
	 * @return the created {@code double} array
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @since 1.0.0
	 */
	public static double[] of(final double... doubles) {
		Ensure.notNull("doubles", doubles);
		if (isEmpty(doubles)) {
			return EMPTY;
		}
		return doubles;
	}

	/**
	 * <p>Create a {@code double} array from a boxed {@code Double} array.</p>
	 * @param boxedDoubles the boxed {@code Double} array to convert
	 * @return the created {@code double} array
	 * @throws NullPointerException if the boxed {@code Double} array is {@code null}
	 * @since 1.2.0
	 */
	public static double[] of(final Double[] boxedDoubles) {
		Ensure.notNull("boxedDoubles", boxedDoubles);
		if (ObjectArrays.isEmpty(boxedDoubles)) {
			return EMPTY;
		}
		final var doubles = new double[boxedDoubles.length];
		for (var i = 0; i < doubles.length; ++i) {
			doubles[i] = boxedDoubles[i];
		}
		return doubles;
	}

	/**
	 * <p>Convert a {@code double} array to a boxed {@code Double} array.</p>
	 * @param doubles the {@code double} array to convert
	 * @return the created boxed {@code Double} array
	 * @throws NullPointerException if the {@code double} array is {@code null}
	 * @since 1.2.0
	 */
	public static Double[] toBoxed(final double[] doubles) {
		Ensure.notNull("doubles", doubles);
		if (isEmpty(doubles)) {
			return ObjectArrays.empty(Double.class);
		}
		final var boxedDoubles = new Double[doubles.length];
		for (var i = 0; i < boxedDoubles.length; ++i) {
			boxedDoubles[i] = doubles[i];
		}
		return boxedDoubles;
	}
}