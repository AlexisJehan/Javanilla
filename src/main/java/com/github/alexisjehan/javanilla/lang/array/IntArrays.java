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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>An utility class that provides {@code int} array tools.</p>
 * @since 1.0.0
 */
public final class IntArrays {

	/**
	 * <p>An empty {@code int} array.</p>
	 * @since 1.0.0
	 */
	public static final int[] EMPTY = {};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private IntArrays() {
		// Not available
	}

	/**
	 * <p>Wrap an {@code int} array replacing {@code null} by an empty one.</p>
	 * @param array the {@code int} array or {@code null}
	 * @return a non-{@code null} {@code int} array
	 * @since 1.0.0
	 */
	public static int[] nullToEmpty(final int[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * <p>Wrap an {@code int} array replacing {@code null} by a default one.</p>
	 * @param array the {@code int} array or {@code null}
	 * @param defaultArray the default {@code int} array
	 * @return a non-{@code null} {@code int} array
	 * @throws NullPointerException if the default {@code int} array is {@code null}
	 * @since 1.1.0
	 */
	public static int[] nullToDefault(final int[] array, final int[] defaultArray) {
		if (null == defaultArray) {
			throw new NullPointerException("Invalid default array (not null expected)");
		}
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap an {@code int} array replacing an empty one by {@code null}.</p>
	 * @param array the {@code int} array or {@code null}
	 * @return a non-empty {@code int} array or {@code null}
	 * @since 1.0.0
	 */
	public static int[] emptyToNull(final int[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap an {@code int} array replacing an empty one by a default {@code int} array.</p>
	 * @param array the {@code int} array or {@code null}
	 * @param defaultArray the default {@code int} array or {@code null}
	 * @return a non-empty {@code int} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code int} array is empty
	 * @since 1.1.0
	 */
	public static int[] emptyToDefault(final int[] array, final int[] defaultArray) {
		if (null != defaultArray && 0 == defaultArray.length) {
			throw new IllegalArgumentException("Invalid default array (not empty expected)");
		}
		return null == array || 0 != array.length ? array : defaultArray;
	}

	/**
	 * <p>Tell if an {@code int} array is empty.</p>
	 * @param array the {@code int} array to test
	 * @return {@code true} if the {@code int} array is empty
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final int[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return 0 == array.length;
	}

	/**
	 * <p>Get the first index of the {@code int} value in the {@code int} array.</p>
	 * @param array the {@code int} array to lookup
	 * @param value the {@code int} value to search
	 * @return the first index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final int[] array, final int value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code int} value in the {@code int} array starting from the given index.</p>
	 * @param array the {@code int} array to lookup
	 * @param value the {@code int} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final int[] array, final int value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 < array.length) {
			if (0 > fromIndex || array.length - 1 < fromIndex) {
				throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
			}
			for (var i = fromIndex; i < array.length; ++i) {
				if (value == array[i]) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * <p>Get the last index of the {@code int} value in the {@code int} array.</p>
	 * @param array the {@code int} array to lookup
	 * @param value the {@code int} value to search
	 * @return the last index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final int[] array, final int value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code int} value in the {@code int} array starting from the given index.</p>
	 * @param array the {@code int} array to lookup
	 * @param value the {@code int} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final int[] array, final int value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 < array.length) {
			if (0 > fromIndex || array.length - 1 < fromIndex) {
				throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
			}
			for (var i = array.length - 1; i > fromIndex; --i) {
				if (value == array[i]) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * <p>Tell if the {@code int} array contains any of given {@code int} values at least one.</p>
	 * @param array the {@code int} array to test
	 * @param values {@code int} values to test
	 * @return {@code true} if any of given {@code int} values is contained at least once by the {@code int} array
	 * @throws NullPointerException if the {@code int} array or the {@code int} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final int[] array, final int... values) {
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
				if (value == element) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>Tell if the {@code int} array contains all of given {@code int} values at least one.</p>
	 * @param array the {@code int} array to test
	 * @param values {@code int} values to test
	 * @return {@code true} if all of given {@code int} values are contained at least once by the {@code int} array
	 * @throws NullPointerException if the {@code int} array or the {@code int} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final int[] array, final int... values) {
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
				if (value == element) {
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
	 * <p>Tell if the {@code int} array contains each given {@code int} value only once.</p>
	 * @param array the {@code int} array to test
	 * @param values {@code int} values to test
	 * @return {@code true} if each of given {@code int} values are contained only once by the {@code int} array
	 * @throws NullPointerException if the {@code int} array or the {@code int} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final int[] array, final int... values) {
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
				if (value == element) {
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
	 * <p>Tell if the {@code int} array contains only given {@code int} values at least one.</p>
	 * @param array the {@code int} array to test
	 * @param values {@code int} values to test
	 * @return {@code true} if given {@code int} values are only values contained by the {@code int} array
	 * @throws NullPointerException if the {@code int} array or the {@code int} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final int[] array, final int... values) {
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
				if (value == element) {
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
	 * <p>Shuffle values in the given {@code int} array using the Fisher-Yates algorithm.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @param array the {@code int} array to shuffle
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.2.0
	 */
	public static void shuffle(final int[] array) {
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
	 * <p>Reverse values in the given {@code int} array.</p>
	 * @param array the {@code int} array to reverse
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final int[] array) {
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
	 * <p>Reorder values in the given {@code int} array using provided indexes.</p>
	 * @param array the {@code int} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code int} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code int} array and indexes array lengths are not the same or if
	 * indexes are not distinct
	 * @throws IndexOutOfBoundsException if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final int[] array, final int... indexes) {
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
	 * <p>Swap two values in the given {@code int} array using their indexes.</p>
	 * @param array the {@code int} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @throws IndexOutOfBoundsException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final int[] array, final int index1, final int index2) {
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
	 * <p>Concatenate multiple {@code int} arrays.</p>
	 * @param arrays the {@code int} array array to concatenate
	 * @return the concatenated {@code int} array
	 * @throws NullPointerException if the {@code int} array array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static int[] concat(final int[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		return concat(Arrays.asList(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code int} arrays.</p>
	 * @param arrays the {@code int} array {@code List} to concatenate
	 * @return the concatenated {@code int} array
	 * @throws NullPointerException if the {@code int} array {@code List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static int[] concat(final List<int[]> arrays) {
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
	 * <p>Join multiple {@code int} arrays using an {@code int} array separator.</p>
	 * @param separator the {@code int} array separator
	 * @param arrays the {@code int} array array to join
	 * @return the joined {@code int} array
	 * @throws NullPointerException if the {@code int} array separator, the {@code int} array array or any of them is
	 * {@code null}
	 * @since 1.0.0
	 */
	public static int[] join(final int[] separator, final int[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		return join(separator, Arrays.asList(arrays));
	}

	/**
	 * <p>Join multiple {@code int} arrays using an {@code int} array separator.</p>
	 * @param separator the {@code int} array separator
	 * @param arrays the {@code int} array {@code List} to join
	 * @return the joined {@code int} array
	 * @throws NullPointerException if the {@code int} array separator, the {@code int} array {@code List} or any of
	 * them is {@code null}
	 * @since 1.0.0
	 */
	public static int[] join(final int[] separator, final List<int[]> arrays) {
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
	 * <p>Create an {@code int} array from a single {@code int}.</p>
	 * @param i the {@code int} to convert
	 * @return the created {@code int} array
	 * @since 1.1.0
	 */
	public static int[] singleton(final int i) {
		return of(i);
	}

	/**
	 * <p>Create an {@code int} array from multiple {@code int}s.</p>
	 * @param ints the {@code int} array to convert
	 * @return the created {@code int} array
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.0.0
	 */
	public static int[] of(final int... ints) {
		if (null == ints) {
			throw new NullPointerException("Invalid ints (not null expected)");
		}
		if (0 == ints.length) {
			return EMPTY;
		}
		return ints;
	}

	/**
	 * <p>Create an {@code int} array from a boxed {@code Integer} array.</p>
	 * @param boxedIntegers the boxed {@code Integer} array to convert
	 * @return the created {@code int} array
	 * @throws NullPointerException if the boxed {@code Integer} array is {@code null}
	 * @since 1.2.0
	 */
	public static int[] of(final Integer[] boxedIntegers) {
		if (null == boxedIntegers) {
			throw new NullPointerException("Invalid Integers (not null expected)");
		}
		if (0 == boxedIntegers.length) {
			return EMPTY;
		}
		final var ints = new int[boxedIntegers.length];
		for (var i = 0; i < ints.length; ++i) {
			ints[i] = boxedIntegers[i];
		}
		return ints;
	}

	/**
	 * <p>Convert an {@code int} array to a boxed {@code Integer} array.</p>
	 * @param ints the {@code int} array to convert
	 * @return the created boxed {@code Integer} array
	 * @throws NullPointerException if the {@code int} array is {@code null}
	 * @since 1.2.0
	 */
	public static Integer[] toBoxed(final int[] ints) {
		if (null == ints) {
			throw new NullPointerException("Invalid ints (not null expected)");
		}
		final var boxedIntegers = new Integer[ints.length];
		for (var i = 0; i < boxedIntegers.length; ++i) {
			boxedIntegers[i] = ints[i];
		}
		return boxedIntegers;
	}
}