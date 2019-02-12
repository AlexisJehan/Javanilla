/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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
package com.github.alexisjehan.javanilla.misc.quality;

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.misc.tuples.Pair;

import java.util.Objects;

/**
 * <p>An utility class that provides an unique interface to create {@code String} representations. Using it you don't
 * have to take care of the type to choose a specific implementation.</p>
 * @since 1.3.0
 */
public final class ToString {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.3.0
	 */
	private ToString() {
		// Not available
	}

	/**
	 * <p>Create a {@code String} representing the {@code boolean} value.</p>
	 * @param value the {@code boolean} value
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final boolean value) {
		return Boolean.toString(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code byte} value.</p>
	 * @param value the {@code byte} value
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final byte value) {
		return Byte.toString(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code short} value.</p>
	 * @param value the {@code short} value
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final short value) {
		return Short.toString(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code char} value.</p>
	 * @param value the {@code char} value
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final char value) {
		return Strings.quote(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code int} value.</p>
	 * @param value the {@code int} value
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final int value) {
		return Integer.toString(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code long} value.</p>
	 * @param value the {@code long} value
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final long value) {
		return Long.toString(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code float} value.</p>
	 * @param value the {@code float} value
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final float value) {
		return Float.toString(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code double} value.</p>
	 * @param value the {@code double} value
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final double value) {
		return Double.toString(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code CharSequence} value.</p>
	 * @param value the {@code CharSequence} value or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final CharSequence value) {
		return null != value ? Strings.quote(value) : "null";
	}

	/**
	 * <p>Create a {@code String} representing the {@code Object} value.</p>
	 * @param value the {@code Object} value or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final Object value) {
		return Objects.toString(value);
	}

	/**
	 * <p>Create a {@code String} representing the {@code boolean} array.</p>
	 * @param array the {@code boolean} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final boolean[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code byte} array.</p>
	 * @param array the {@code byte} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final byte[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code short} array.</p>
	 * @param array the {@code short} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final short[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code char} array.</p>
	 * @param array the {@code char} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final char[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code int} array.</p>
	 * @param array the {@code int} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final int[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code long} array.</p>
	 * @param array the {@code long} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final long[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code float} array.</p>
	 * @param array the {@code float} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final float[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code double} array.</p>
	 * @param array the {@code double} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final double[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code CharSequence} array.</p>
	 * @param array the {@code CharSequence} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final CharSequence[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing the {@code Object} array.</p>
	 * @param array the {@code Object} array or {@code null}
	 * @return the created {@code String} representation
	 * @since 1.3.0
	 */
	public static String toString(final Object[] array) {
		if (null == array) {
			return "null";
		}
		if (0 == array.length) {
			return "[]";
		}
		final var builder = new StringBuilder();
		builder.append('[')
				.append(toString(array[0]));
		for (var i = 1; i < array.length; ++i) {
			builder.append(", ")
					.append(toString(array[i]));
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * <p>Create a {@code String} representing an {@code Object} and its attributes if any.</p>
	 * @param object the {@code Object}
	 * @param toStrings the {@code String} representation array with an attribute name and its {@code String}
	 * representation
	 * @return the created {@code String} representation
	 * @throws NullPointerException if the {@code Object} or the {@code String} representation array or any of them is
	 * {@code null}
	 * @since 1.3.0
	 */
	@SafeVarargs
	public static String of(final Object object, final Pair<String, String>... toStrings) {
		Ensure.notNull("object", object);
		Ensure.notNullAndNotNullElements("toStrings", toStrings);
		final var builder = new StringBuilder();
		builder.append(object.getClass().getSimpleName());
		if (0 < toStrings.length) {
			builder.append('{')
					.append(toStrings[0].getFirst())
					.append('=')
					.append(toStrings[0].getSecond());
			for (var i = 1; i < toStrings.length; ++i) {
				builder.append(", ")
						.append(toStrings[i].getFirst())
						.append('=')
						.append(toStrings[i].getSecond());
			}
			builder.append('}');
		} else {
			builder.append('@')
					.append(object.hashCode());
		}
		return builder.toString();
	}
}