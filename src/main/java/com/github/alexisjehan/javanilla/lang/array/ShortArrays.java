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
		if (null == defaultArray) {
			throw new NullPointerException("Invalid default array (not null expected)");
		}
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
		if (null != defaultArray && 0 == defaultArray.length) {
			throw new IllegalArgumentException("Invalid default array (not empty expected)");
		}
		return null == array || 0 != array.length ? array : defaultArray;
	}

	/**
	 * <p>Tell if a {@code short} array is empty.</p>
	 * @param array the {@code short} array to test
	 * @return {@code true} if the {@code short} array is empty
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final short[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return 0 == array.length;
	}

	/**
	 * <p>Get the first index of the {@code short} value in the {@code short} array.</p>
	 * @param array the {@code short} array to lookup
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
	 * @param array the {@code short} array to lookup
	 * @param value the {@code short} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code short} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final short[] array, final short value, final int fromIndex) {
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
	 * <p>Get the last index of the {@code short} value in the {@code short} array.</p>
	 * @param array the {@code short} array to lookup
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
	 * @param array the {@code short} array to lookup
	 * @param value the {@code short} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code short} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final short[] array, final short value, final int fromIndex) {
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
	 * <p>Tell if the {@code short} array contains any of given {@code short} values at least one.</p>
	 * @param array the {@code short} array to test
	 * @param values {@code short} values to test
	 * @return {@code true} if any of given {@code short} values is contained at least once by the {@code short} array
	 * @throws NullPointerException if the {@code short} array or the {@code short} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final short[] array, final short... values) {
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
	 * <p>Tell if the {@code short} array contains all of given {@code short} values at least one.</p>
	 * @param array the {@code short} array to test
	 * @param values {@code short} values to test
	 * @return {@code true} if all of given {@code short} values are contained at least once by the {@code short} array
	 * @throws NullPointerException if the {@code short} array or the {@code short} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final short[] array, final short... values) {
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
	 * <p>Tell if the {@code short} array contains each given {@code short} value only once.</p>
	 * @param array the {@code short} array to test
	 * @param values {@code short} values to test
	 * @return {@code true} if each of given {@code short} values are contained only once by the {@code short} array
	 * @throws NullPointerException if the {@code short} array or the {@code short} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final short[] array, final short... values) {
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
	 * <p>Tell if the {@code short} array contains only given {@code short} values at least one.</p>
	 * @param array the {@code short} array to test
	 * @param values {@code short} values to test
	 * @return {@code true} if given {@code short} values are only values contained by the {@code short} array
	 * @throws NullPointerException if the {@code short} array or the {@code short} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final short[] array, final short... values) {
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
	 * <p>Shuffle values in the given {@code short} array using the Fisher-Yates algorithm.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @param array the {@code short} array to shuffle
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.2.0
	 */
	public static void shuffle(final short[] array) {
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
	 * <p>Reverse values in the given {@code short} array.</p>
	 * @param array the {@code short} array to reverse
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final short[] array) {
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
	 * <p>Reorder values in the given {@code short} array using provided indexes.</p>
	 * @param array the {@code short} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code short} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code short} array and indexes array lengths are not the same or if
	 * indexes are not distinct
	 * @throws IndexOutOfBoundsException if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final short[] array, final int... indexes) {
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
	 * <p>Swap two values in the given {@code short} array using their indexes.</p>
	 * @param array the {@code short} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @throws IndexOutOfBoundsException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final short[] array, final int index1, final int index2) {
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
	 * <p>Concatenate multiple {@code short} arrays.</p>
	 * @param arrays the {@code short} array array to concatenate
	 * @return the concatenated {@code short} array
	 * @throws NullPointerException if the {@code short} array array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static short[] concat(final short[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		return concat(Arrays.asList(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code short} arrays.</p>
	 * @param arrays the {@code short} array {@code List} to concatenate
	 * @return the concatenated {@code short} array
	 * @throws NullPointerException if the {@code short} array {@code List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static short[] concat(final List<short[]> arrays) {
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
	 * is {@code null}
	 * @since 1.0.0
	 */
	public static short[] join(final short[] separator, final short[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		return join(separator, Arrays.asList(arrays));
	}

	/**
	 * <p>Join multiple {@code short} arrays using a {@code short} array separator.</p>
	 * @param separator the {@code short} array separator
	 * @param arrays the {@code short} array {@code List} to join
	 * @return the joined {@code short} array
	 * @throws NullPointerException if the {@code short} array separator, the {@code short} array {@code List} or any
	 * of them is {@code null}
	 * @since 1.0.0
	 */
	public static short[] join(final short[] separator, final List<short[]> arrays) {
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
	 * <p>Create a {@code short} array from a single {@code short}.</p>
	 * @param s the {@code short} to convert
	 * @return the created {@code short} array
	 * @since 1.1.0
	 */
	public static short[] singleton(final short s) {
		return of(s);
	}

	/**
	 * <p>Create a {@code short} array from multiple {@code short}s.</p>
	 * @param shorts the {@code short} array to convert
	 * @return the created {@code short} array
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.0.0
	 */
	public static short[] of(final short... shorts) {
		if (null == shorts) {
			throw new NullPointerException("Invalid shorts (not null expected)");
		}
		if (0 == shorts.length) {
			return EMPTY;
		}
		return shorts;
	}

	/**
	 * <p>Create a {@code short} array from a boxed {@code Short} array.</p>
	 * @param boxedShorts the boxed {@code Short} array to convert
	 * @return the created {@code short} array
	 * @throws NullPointerException if the boxed {@code Short} array is {@code null}
	 * @since 1.2.0
	 */
	public static short[] of(final Short[] boxedShorts) {
		if (null == boxedShorts) {
			throw new NullPointerException("Invalid Shorts (not null expected)");
		}
		if (0 == boxedShorts.length) {
			return EMPTY;
		}
		final var shorts = new short[boxedShorts.length];
		for (var i = 0; i < shorts.length; ++i) {
			shorts[i] = boxedShorts[i];
		}
		return shorts;
	}

	/**
	 * <p>Convert a {@code short} array to a boxed {@code Short} array.</p>
	 * @param shorts the {@code short} array to convert
	 * @return the created boxed {@code Short} array
	 * @throws NullPointerException if the {@code short} array is {@code null}
	 * @since 1.2.0
	 */
	public static Short[] toBoxed(final short[] shorts) {
		if (null == shorts) {
			throw new NullPointerException("Invalid shorts (not null expected)");
		}
		final var boxedShorts = new Short[shorts.length];
		for (var i = 0; i < boxedShorts.length; ++i) {
			boxedShorts[i] = shorts[i];
		}
		return boxedShorts;
	}
}