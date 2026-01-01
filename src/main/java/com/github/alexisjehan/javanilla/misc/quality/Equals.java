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
package com.github.alexisjehan.javanilla.misc.quality;

import java.util.Arrays;
import java.util.Objects;

/**
 * A utility class that provides a unique interface for equality check. Using it you don't have to take care of the type
 * to choose a specific implementation. Unlike {@link Objects#equals(Object, Object)}, primitives are not boxed for
 * performance improvements.
 *
 * <p><b>Note</b>: Deep equality is not supported.</p>
 * @since 1.3.0
 */
public final class Equals {

	/**
	 * Constructor.
	 * @since 1.3.0
	 */
	private Equals() {}

	/**
	 * Check the equality between {@code boolean} values.
	 * @param value1 the first {@code boolean} value
	 * @param value2 the second {@code boolean} value
	 * @return {@code true} if both {@code boolean} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final boolean value1, final boolean value2) {
		return value1 == value2;
	}

	/**
	 * Check the equality between {@code byte} values.
	 * @param value1 the first {@code byte} value
	 * @param value2 the second {@code byte} value
	 * @return {@code true} if both {@code byte} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final byte value1, final byte value2) {
		return value1 == value2;
	}

	/**
	 * Check the equality between {@code short} values.
	 * @param value1 the first {@code short} value
	 * @param value2 the second {@code short} value
	 * @return {@code true} if both {@code short} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final short value1, final short value2) {
		return value1 == value2;
	}

	/**
	 * Check the equality between {@code char} values.
	 * @param value1 the first {@code char} value
	 * @param value2 the second {@code char} value
	 * @return {@code true} if both {@code char} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final char value1, final char value2) {
		return value1 == value2;
	}

	/**
	 * Check the equality between {@code int} values.
	 * @param value1 the first {@code int} value
	 * @param value2 the second {@code int} value
	 * @return {@code true} if both {@code int} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final int value1, final int value2) {
		return value1 == value2;
	}

	/**
	 * Check the equality between {@code long} values.
	 * @param value1 the first {@code long} value
	 * @param value2 the second {@code long} value
	 * @return {@code true} if both {@code long} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final long value1, final long value2) {
		return value1 == value2;
	}

	/**
	 * Check the equality between {@code float} values.
	 * @param value1 the first {@code float} value
	 * @param value2 the second {@code float} value
	 * @return {@code true} if both {@code float} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final float value1, final float value2) {
		return 0 == Float.compare(value1, value2);
	}

	/**
	 * Check the equality between {@code double} values.
	 * @param value1 the first {@code double} value
	 * @param value2 the second {@code double} value
	 * @return {@code true} if both {@code double} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final double value1, final double value2) {
		return 0 == Double.compare(value1, value2);
	}

	/**
	 * Check the equality between {@link Object} values.
	 * @param value1 the first {@link Object} value or {@code null}
	 * @param value2 the second {@link Object} value or {@code null}
	 * @return {@code true} if both {@link Object} values are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final Object value1, final Object value2) {
		return Objects.equals(value1, value2);
	}

	/**
	 * Check the equality between {@code boolean} arrays.
	 * @param array1 the first {@code boolean} array or {@code null}
	 * @param array2 the second {@code boolean} array or {@code null}
	 * @return {@code true} if both {@code boolean} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final boolean[] array1, final boolean[] array2) {
		return Arrays.equals(array1, array2);
	}

	/**
	 * Check the equality between {@code byte} arrays.
	 * @param array1 the first {@code byte} array or {@code null}
	 * @param array2 the second {@code byte} array or {@code null}
	 * @return {@code true} if both {@code byte} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final byte[] array1, final byte[] array2) {
		return Arrays.equals(array1, array2);
	}

	/**
	 * Check the equality between {@code short} arrays.
	 * @param array1 the first {@code short} array or {@code null}
	 * @param array2 the second {@code short} array or {@code null}
	 * @return {@code true} if both {@code short} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final short[] array1, final short[] array2) {
		return Arrays.equals(array1, array2);
	}

	/**
	 * Check the equality between {@code char} arrays.
	 * @param array1 the first {@code char} array or {@code null}
	 * @param array2 the second {@code char} array or {@code null}
	 * @return {@code true} if both {@code char} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final char[] array1, final char[] array2) {
		return Arrays.equals(array1, array2);
	}

	/**
	 * Check the equality between {@code int} arrays.
	 * @param array1 the first {@code int} array or {@code null}
	 * @param array2 the second {@code int} array or {@code null}
	 * @return {@code true} if both {@code int} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final int[] array1, final int[] array2) {
		return Arrays.equals(array1, array2);
	}

	/**
	 * Check the equality between {@code long} arrays.
	 * @param array1 the first {@code long} array or {@code null}
	 * @param array2 the second {@code long} array or {@code null}
	 * @return {@code true} if both {@code long} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final long[] array1, final long[] array2) {
		return Arrays.equals(array1, array2);
	}

	/**
	 * Check the equality between {@code float} arrays.
	 * @param array1 the first {@code float} array or {@code null}
	 * @param array2 the second {@code float} array or {@code null}
	 * @return {@code true} if both {@code float} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final float[] array1, final float[] array2) {
		return Arrays.equals(array1, array2);
	}

	/**
	 * Check the equality between {@code double} arrays.
	 * @param array1 the first {@code double} array or {@code null}
	 * @param array2 the second {@code double} array or {@code null}
	 * @return {@code true} if both {@code double} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final double[] array1, final double[] array2) {
		return Arrays.equals(array1, array2);
	}

	/**
	 * Check the equality between {@link Object} arrays.
	 * @param array1 the first {@link Object} array or {@code null}
	 * @param array2 the second {@link Object} array or {@code null}
	 * @return {@code true} if both {@link Object} arrays are equal
	 * @since 1.3.0
	 */
	public static boolean equals(final Object[] array1, final Object[] array2) {
		return Arrays.equals(array1, array2);
	}
}