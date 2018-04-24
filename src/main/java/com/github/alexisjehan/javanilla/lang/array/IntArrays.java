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

import java.util.Arrays;
import java.util.List;

/**
 * <p>An utility class that provides {@code int array} tools.</p>
 * @since 1.0
 */
public final class IntArrays {

	/**
	 * <p>An empty {@code int array}.</p>
	 * @since 1.0
	 */
	public static final int[] EMPTY = new int[0];

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private IntArrays() {
		// Not available
	}

	/**
	 * <p>Wrap an {@code int array} replacing {@code null} by an empty {@code int array}.</p>
	 * @param array an {@code int array} or {@code null}
	 * @return a non-{@code null} {@code int array}
	 * @since 1.0
	 */
	public static int[] nullToEmpty(final int[] array) {
		return null != array ? array : EMPTY;
	}

	/**
	 * <p>Wrap an {@code int array} replacing an empty one by {@code null}.</p>
	 * @param array an {@code int array} or {@code null}
	 * @return a non-empty {@code int array} or {@code null}
	 * @since 1.0
	 */
	public static int[] emptyToNull(final int[] array) {
		return null != array && 0 != array.length ? array : null;
	}

	/**
	 * <p>Get the first index of the {@code int} value in the given {@code int array}.</p>
	 * @param array the {@code int array} to look into
	 * @param value the {@code int} value to search
	 * @return the first index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int array} is {@code null}
	 * @since 1.0
	 */
	public static int indexOf(final int[] array, final int value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code int} value in the given {@code int array} starting from the provided
	 * index.</p>
	 * @param array the {@code int array} to look into
	 * @param value the {@code int} value to search
	 * @param fromIndex the index to start from
	 * @return the first index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int array} is {@code null}
	 * @throws IndexOutOfBoundsException if the index to start from is not valid
	 * @since 1.0
	 */
	public static int indexOf(final int[] array, final int value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 > fromIndex || array.length <= fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
		}
		for (var i = fromIndex; i < array.length; ++i) {
			if (value == array[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>Get the last index of the {@code int} value in the given {@code int array}.</p>
	 * @param array the {@code int array} to look into
	 * @param value the {@code int} value to search
	 * @return the last index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int array} is {@code null}
	 * @since 1.0
	 */
	public static int lastIndexOf(final int[] array, final int value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code int} value in the given {@code int array} starting from the provided
	 * index.</p>
	 * @param array the {@code int array} to look into
	 * @param value the {@code int} value to search
	 * @param fromIndex the index to start from
	 * @return the last index of the {@code int} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code int array} is {@code null}
	 * @throws IndexOutOfBoundsException if the index to start from is not valid
	 * @since 1.0
	 */
	public static int lastIndexOf(final int[] array, final int value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 > fromIndex || array.length <= fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
		}
		for (var i = array.length - 1; i > fromIndex; --i) {
			if (value == array[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>Tell if the {@code int array} contains the given {@code int} value.</p>
	 * @param array the {@code int array} to look into
	 * @param value the {@code int} value to search
	 * @return {@code true} if the given {@code int} value is contained by the {@code int array}
	 * @throws NullPointerException if the {@code int array} is {@code null}
	 * @since 1.0
	 */
	public static boolean contains(final int[] array, final int value) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var element : array) {
			if (value == element) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>Tell if the {@code int array} contains only the given {@code int} value.</p>
	 * @param array the {@code int array} to look into
	 * @param value the {@code int} value to search
	 * @return {@code true} if the given {@code int} value is the only value contained by the {@code int array}
	 * @throws NullPointerException if the {@code int array} is {@code null}
	 * @since 1.0
	 */
	public static boolean containsOnly(final int[] array, final int value) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var element : array) {
			if (value != element) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if the {@code int array} contains any of given {@code int} values.</p>
	 * @param array the {@code int array} to look into
	 * @param values {@code int} values to search
	 * @return {@code true} if any of given {@code int} values is contained by the {@code int array}
	 * @throws NullPointerException if the {@code int array} or {@code int} values are {@code null}
	 * @since 1.0
	 */
	public static boolean containsAny(final int[] array, final int... values) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == array.length || 0 == values.length) {
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
	 * <p>Tell if the {@code int array} contains all of given {@code int} values.</p>
	 * @param array the {@code int array} to look into
	 * @param values {@code int} values to search
	 * @return {@code true} if all of given {@code int} values are contained by the {@code int array}
	 * @throws NullPointerException if the {@code int array} or {@code int} values are {@code null}
	 * @since 1.0
	 */
	public static boolean containsAll(final int[] array, final int... values) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == array.length || 0 == values.length) {
			return false;
		}
		for (final var value : values) {
			var found = false;
			for (final var element : array) {
				if (value == element) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Concatenate multiple {@code int array}s.</p>
	 * @param arrays {@code int array}s to concatenate
	 * @return the concatenated {@code int array}
	 * @throws NullPointerException if the array or any of the {@code int array}s is {@code null}
	 * @since 1.0
	 */
	public static int[] concat(final int[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return concat(Arrays.asList(arrays));
	}

	/**
	 * <p>Concatenate a list of {@code int array}s.</p>
	 * @param arrays {@code int array}s to concatenate
	 * @return the concatenated {@code int array}
	 * @throws NullPointerException if the {@code int array} list or any of the {@code int array}s is {@code null}
	 * @since 1.0
	 */
	public static int[] concat(final List<int[]> arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		for (final var array : arrays) {
			if (null == array) {
				throw new NullPointerException("Invalid array (not null expected)");
			}
		}
		if (arrays.isEmpty()) {
			return EMPTY;
		}
		if (1 == arrays.size()) {
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
	 * <p>Join multiple {@code int array}s using an {@code int array} separator.</p>
	 * @param separator the {@code int array} sequence to add between each joined {@code int array}
	 * @param arrays {@code int array}s to join
	 * @return the joined {@code int array}
	 * @throws NullPointerException if the separator, the array or any of the {@code int array}s is {@code null}
	 * @since 1.0
	 */
	public static int[] join(final int[] separator, final int[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return join(separator, Arrays.asList(arrays));
	}

	/**
	 * <p>Join a list of {@code int array}s using an {@code int array} separator.</p>
	 * @param separator the {@code int array} sequence to add between each joined {@code int array}
	 * @param arrays {@code int array}s to join
	 * @return the joined {@code int array}
	 * @throws NullPointerException if the separator, the {@code int array} list or any of the {@code int array}s is
	 * {@code null}
	 * @since 1.0
	 */
	public static int[] join(final int[] separator, final List<int[]> arrays) {
		if (null == separator) {
			throw new NullPointerException("Invalid separator (not null expected)");
		}
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		for (final var array : arrays) {
			if (null == array) {
				throw new NullPointerException("Invalid array (not null expected)");
			}
		}
		if (0 == separator.length) {
			return concat(arrays);
		}
		if (arrays.isEmpty()) {
			return EMPTY;
		}
		if (1 == arrays.size()) {
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
	 * <p>Create a {@code int array} using given {@code int} values.</p>
	 * @param values {@code int} values
	 * @return the created {@code int array}
	 * @throws NullPointerException if {@code int} values are {@code null}
	 * @since 1.0
	 */
	public static int[] of(final int... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY;
		}
		return values;
	}
}