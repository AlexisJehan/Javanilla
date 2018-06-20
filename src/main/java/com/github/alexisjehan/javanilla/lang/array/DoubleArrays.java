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
 * <p>An utility class that provides {@code double array} tools.</p>
 * @since 1.0.0
 */
public final class DoubleArrays {

	/**
	 * <p>An empty {@code double array}.</p>
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
	 * <p>Wrap a {@code double array} replacing {@code null} by an empty {@code double array}.</p>
	 * @param array a {@code double array} or {@code null}
	 * @return the non-{@code null} {@code double array}
	 * @since 1.0.0
	 */
	public static double[] nullToEmpty(final double[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * <p>Wrap a {@code double array} replacing {@code null} by a default {@code double array}.</p>
	 * @param array a {@code double array} or {@code null}
	 * @param defaultArray the default {@code double array}
	 * @return the non-{@code null} {@code double array}
	 * @throws NullPointerException if the default {@code double array} is {@code null}
	 * @since 1.1.0
	 */
	public static double[] nullToDefault(final double[] array, final double[] defaultArray) {
		if (null == defaultArray) {
			throw new NullPointerException("Invalid default array (not null expected)");
		}
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap a {@code double array} replacing an empty one by {@code null}.</p>
	 * @param array a {@code double array} or {@code null}
	 * @return the non-empty {@code double array} or {@code null}
	 * @since 1.0.0
	 */
	public static double[] emptyToNull(final double[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap a {@code double array} replacing an empty one by a default {@code double array}.</p>
	 * @param array a {@code double array} or {@code null}
	 * @param defaultArray the default {@code double array} or {@code null}
	 * @return the non-empty {@code double array} or {@code null}
	 * @throws IllegalArgumentException if the default {@code double array} is empty
	 * @since 1.1.0
	 */
	public static double[] emptyToDefault(final double[] array, final double[] defaultArray) {
		if (null != defaultArray && 0 == defaultArray.length) {
			throw new IllegalArgumentException("Invalid default array (not empty expected)");
		}
		if (null == array) {
			return null;
		}
		return 0 != array.length ? array : defaultArray;
	}

	/**
	 * <p>Get the first index of the {@code double} value in the given {@code double array}.</p>
	 * @param array the {@code double array} to look into
	 * @param value the {@code double} value to search
	 * @return the first index of the {@code double} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code double array} is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final double[] array, final double value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code double} value in the given {@code double array} starting from the provided
	 * index.</p>
	 * @param array the {@code double array} to look into
	 * @param value the {@code double} value to search
	 * @param fromIndex the index to start from
	 * @return the first index of the {@code double} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code double array} is {@code null}
	 * @throws IndexOutOfBoundsException if the index to start from is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final double[] array, final double value, final int fromIndex) {
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
	 * <p>Get the last index of the {@code double} value in the given {@code double array}.</p>
	 * @param array the {@code double array} to look into
	 * @param value the {@code double} value to search
	 * @return the last index of the {@code double} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code double array} is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final double[] array, final double value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code double} value in the given {@code double array} starting from the provided
	 * index.</p>
	 * @param array the {@code double array} to look into
	 * @param value the {@code double} value to search
	 * @param fromIndex the index to start from
	 * @return the last index of the {@code double} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code double array} is {@code null}
	 * @throws IndexOutOfBoundsException if the index to start from is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final double[] array, final double value, final int fromIndex) {
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
	 * <p>Tell if the {@code double array} contains the given {@code double} value at least once.</p>
	 * @param array the {@code double array} to look into
	 * @param value the {@code double} value to search
	 * @return {@code true} if the given {@code double} value is contained by the {@code double array}
	 * @throws NullPointerException if the {@code double array} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean contains(final double[] array, final double value) {
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
	 * <p>Tell of the {@code double array} contains the given {@code double} value only once.</p>
	 * @param array the {@code double array} to look into
	 * @param value the {@code double} value to search
	 * @return {@code true} if the given {@code double} value is contained only once by the {@code double array}
	 * @throws NullPointerException if the {@code double array} is {@code null}
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final double[] array, final double value) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 == array.length) {
			return false;
		}
		var found = false;
		for (final var element : array) {
			if (value == element) {
				if (!found) {
					found = true;
				} else {
					return false;
				}
			}
		}
		return found;
	}

	/**
	 * <p>Tell if the {@code double array} contains only the given {@code double} value.</p>
	 * @param array the {@code double array} to look into
	 * @param value the {@code double} value to search
	 * @return {@code true} if the given {@code double} value is the only value contained by the {@code double array}
	 * @throws NullPointerException if the {@code double array} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final double[] array, final double value) {
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
	 * <p>Tell if the {@code double array} contains any of given {@code double} values.</p>
	 * @param array the {@code double array} to look into
	 * @param values {@code double} values to search
	 * @return {@code true} if any of given {@code double} values is contained by the {@code double array}
	 * @throws NullPointerException whether the {@code double array} or {@code double} values is {@code null}
	 * @since 1.0.0
	 */
	public static boolean containsAny(final double[] array, final double... values) {
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
	 * <p>Tell if the {@code double array} contains all of given {@code double} values.</p>
	 * @param array the {@code double array} to look into
	 * @param values {@code double} values to search
	 * @return {@code true} if all of given {@code double} values are contained by the {@code double array}
	 * @throws NullPointerException whether the {@code double array} or {@code double} values is {@code null}
	 * @since 1.0.0
	 */
	public static boolean containsAll(final double[] array, final double... values) {
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
	 * <p>Concatenate multiple {@code double array}s.</p>
	 * @param arrays {@code double array}s to concatenate
	 * @return the concatenated {@code double array}
	 * @throws NullPointerException whether the array or any of the {@code double array}s is {@code null}
	 * @since 1.0.0
	 */
	public static double[] concat(final double[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return concat(Arrays.asList(arrays));
	}

	/**
	 * <p>Concatenate a list of {@code double array}s.</p>
	 * @param arrays {@code double array}s to concatenate
	 * @return the concatenated {@code double array}
	 * @throws NullPointerException whether the {@code double array} list or any of the {@code double array}s is
	 * {@code null}
	 * @since 1.0.0
	 */
	public static double[] concat(final List<double[]> arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		var i = 0;
		for (final var array : arrays) {
			if (null == array) {
				throw new NullPointerException("Invalid array at index " + i + " (not null expected)");
			}
			++i;
		}
		if (arrays.isEmpty()) {
			return EMPTY;
		}
		if (1 == arrays.size()) {
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
	 * <p>Join multiple {@code double array}s using a {@code double array} separator.</p>
	 * @param separator the {@code double array} sequence to add between each joined {@code double array}
	 * @param arrays {@code double array}s to join
	 * @return the joined {@code double array}
	 * @throws NullPointerException whether the separator, the array or any of the {@code double array}s is {@code null}
	 * @since 1.0.0
	 */
	public static double[] join(final double[] separator, final double[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return join(separator, Arrays.asList(arrays));
	}

	/**
	 * <p>Join a list of {@code double array}s using a {@code double array} separator.</p>
	 * @param separator the {@code double array} sequence to add between each joined {@code double array}
	 * @param arrays {@code double array}s to join
	 * @return the joined {@code double array}
	 * @throws NullPointerException whether the separator, the {@code double array} list or any of the
	 * {@code double array}s is {@code null}
	 * @since 1.0.0
	 */
	public static double[] join(final double[] separator, final List<double[]> arrays) {
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
	 * <p>Create a singleton {@code double array} using the given {@code double} value.</p>
	 * @param value the {@code double} value
	 * @return the created singleton {@code double array}
	 * @since 1.1.0
	 */
	public static double[] singleton(final double value) {
		return of(value);
	}

	/**
	 * <p>Create a {@code double array} using given {@code double} values.</p>
	 * @param values {@code double} values
	 * @return the created {@code double array}
	 * @throws NullPointerException if {@code double} values are {@code null}
	 * @since 1.0.0
	 */
	public static double[] of(final double... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY;
		}
		return values;
	}
}