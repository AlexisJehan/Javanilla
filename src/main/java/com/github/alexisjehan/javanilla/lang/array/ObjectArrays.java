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

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * <p>An utility class that provides {@code generic array} tools.</p>
 * @since 1.0.0
 */
public final class ObjectArrays {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private ObjectArrays() {
		// Not available
	}

	/**
	 * <p>Return an empty {@code generic array}.</p>
	 * @param type the generic class type
	 * @param <E> the generic type
	 * @return An empty {@code generic array}
	 * @throws NullPointerException if the generic class type is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] empty(final Class<E> type) {
		if (null == type) {
			throw new NullPointerException("Invalid class type (not null expected)");
		}
		return (E[]) Array.newInstance(type, 0);
	}

	/**
	 * <p>Wrap a {@code generic array} replacing {@code null} by an empty {@code generic array}.</p>
	 * @param array a {@code generic array} or {@code null}
	 * @param type the generic class type
	 * @param <E> the generic type
	 * @return the non-{@code null} {@code generic array}
	 * @since 1.0.0
	 */
	public static <E> E[] nullToEmpty(final E[] array, final Class<E> type) {
		return nullToDefault(array, empty(type));
	}

	/**
	 * <p>Wrap a {@code generic array} replacing {@code null} by a default {@code generic array}.</p>
	 * @param array a {@code generic array} or {@code null}
	 * @param defaultArray the default {@code generic array}
	 * @param <E> the generic type
	 * @return the non-{@code null} {@code generic array}
	 * @throws NullPointerException if the default {@code generic array} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> E[] nullToDefault(final E[] array, final E[] defaultArray) {
		if (null == defaultArray) {
			throw new NullPointerException("Invalid default array (not null expected)");
		}
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap a {@code generic array} replacing an empty one by {@code null}.</p>
	 * @param array a {@code generic array} or {@code null}
	 * @param <E> the generic type
	 * @return the non-empty {@code generic array} or {@code null}
	 * @since 1.0.0
	 */
	public static <E> E[] emptyToNull(final E[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap a {@code generic array} replacing an empty one by a default {@code generic array}.</p>
	 * @param array a {@code generic array} or {@code null}
	 * @param defaultArray the default {@code generic array} or {@code null}
	 * @param <E> the generic type
	 * @return the non-empty {@code generic array} or {@code null}
	 * @throws IllegalArgumentException if the default {@code generic array} is empty
	 * @since 1.1.0
	 */
	public static <E> E[] emptyToDefault(final E[] array, final E[] defaultArray) {
		if (null != defaultArray && 0 == defaultArray.length) {
			throw new IllegalArgumentException("Invalid default array (not empty expected)");
		}
		if (null == array) {
			return null;
		}
		return 0 != array.length ? array : defaultArray;
	}

	/**
	 * <p>Get the first index of the {@code generic} value in the given {@code generic array}.</p>
	 * @param array the {@code generic array} to look into
	 * @param value the {@code generic} value to search or {@code null}
	 * @param <E> the generic type
	 * @return the first index of the {@code generic} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code generic array} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> int indexOf(final E[] array, final E value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code generic} value in the given {@code generic array} starting from the provided
	 * index.</p>
	 * @param array the {@code generic array} to look into
	 * @param value the {@code generic} value to search or {@code null}
	 * @param fromIndex the index to start from
	 * @param <E> the generic type
	 * @return the first index of the {@code generic} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code generic array} is {@code null}
	 * @throws IndexOutOfBoundsException if the index to start from is not valid
	 * @since 1.0.0
	 */
	public static <E> int indexOf(final E[] array, final E value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 > fromIndex || array.length <= fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
		}
		for (var i = fromIndex; i < array.length; ++i) {
			if (Objects.equals(value, array[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>Get the last index of the {@code generic} value in the given {@code generic array}.</p>
	 * @param array the {@code generic array} to look into
	 * @param value the {@code generic} value to search or {@code null}
	 * @param <E> the generic type
	 * @return the last index of the {@code generic} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code generic array} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> int lastIndexOf(final E[] array, final E value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code generic} value in the given {@code generic array} starting from the provided
	 * index.</p>
	 * @param array the {@code generic array} to look into
	 * @param value the {@code generic} value to search or {@code null}
	 * @param fromIndex the index to start from
	 * @param <E> the generic type
	 * @return the last index of the {@code generic} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code generic array} is {@code null}
	 * @throws IndexOutOfBoundsException if the index to start from is not valid
	 * @since 1.0.0
	 */
	public static <E> int lastIndexOf(final E[] array, final E value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 > fromIndex || array.length <= fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
		}
		for (var i = array.length - 1; i > fromIndex; --i) {
			if (Objects.equals(value, array[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>Tell if the {@code generic array} contains the given {@code generic} value at least once.</p>
	 * @param array the {@code generic array} to look into
	 * @param value the {@code generic} value to search or {@code null}
	 * @param <E> the generic type
	 * @return {@code true} if the given {@code generic} value is contained by the {@code generic array}
	 * @throws NullPointerException if the {@code generic array} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> boolean contains(final E[] array, final E value) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var element : array) {
			if (Objects.equals(value, element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>Tell of the {@code generic array} contains the given {@code generic} value only once.</p>
	 * @param array the {@code generic array} to look into
	 * @param value the {@code generic} value to search
	 * @param <E> the generic type
	 * @return {@code true} if the given {@code generic} value is contained only once by the {@code generic array}
	 * @throws NullPointerException if the {@code generic array} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> boolean containsOnce(final E[] array, final E value) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 == array.length) {
			return false;
		}
		var found = false;
		for (final var element : array) {
			if (Objects.equals(value, element)) {
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
	 * <p>Tell if the {@code generic array} contains only the given {@code generic} value.</p>
	 * @param array the {@code generic array} to look into
	 * @param value the {@code generic} value to search or {@code null}
	 * @param <E> the generic type
	 * @return {@code true} if the given {@code generic} value is the only value contained by the {@code generic array}
	 * @throws NullPointerException if the {@code generic array} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> boolean containsOnly(final E[] array, final E value) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var element : array) {
			if (!Objects.equals(value, element)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if the {@code generic array} contains any of given {@code generic} values.</p>
	 * @param array the {@code generic array} to look into
	 * @param values {@code generic} or {@code null} values to search
	 * @param <E> the generic type
	 * @return {@code true} if any of given {@code generic} values is contained by the {@code generic array}
	 * @throws NullPointerException whether the {@code generic array} or {@code generic} values is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> boolean containsAny(final E[] array, final E... values) {
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
				if (Objects.equals(value, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>Tell if the {@code generic array} contains all of given {@code generic} values.</p>
	 * @param array the {@code generic array} to look into
	 * @param values {@code generic} or {@code null} values to search
	 * @param <E> the generic type
	 * @return {@code true} if all of given {@code generic} values are contained by the {@code generic array}
	 * @throws NullPointerException whether the {@code generic array} or {@code generic} values is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> boolean containsAll(final E[] array, final E... values) {
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
				if (Objects.equals(value, element)) {
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
	 * <p>Create a singleton {@code generic array} using the given {@code generic} value.</p>
	 * @param value the {@code generic} value
	 * @param <E> the generic type
	 * @return the created singleton {@code generic array}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] singleton(final E value) {
		final var array = (E[]) Array.newInstance(value.getClass(), 1);
		array[0] = value;
		return array;
	}

	/**
	 * <p>Create a {@code generic array} using given {@code generic} values.</p>
	 * @param values {@code generic} values
	 * @param <E> the generic type
	 * @return the created {@code generic array}
	 * @throws NullPointerException if {@code generic} values are {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> E[] of(final E... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		return values;
	}
}