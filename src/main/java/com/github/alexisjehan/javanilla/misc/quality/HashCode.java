/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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
 * <p>An utility class that provides an unique interface for hash code calculation. Using it you don't have to take care
 * of the type to choose a specific implementation. Unlike {@link Objects#hash(Object...)}, primitives are not boxed for
 * performance improvements.</p>
 * @since 1.3.0
 */
public final class HashCode {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.3.0
	 */
	private HashCode() {
		// Not available
	}

	/**
	 * <p>Calculate the hash code of a {@code boolean} value.</p>
	 * @param value the {@code boolean} value
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final boolean value) {
		return Boolean.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of a {@code byte} value.</p>
	 * @param value the {@code byte} value
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final byte value) {
		return Byte.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of a {@code short} value.</p>
	 * @param value the {@code short} value
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final short value) {
		return Short.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of a {@code char} value.</p>
	 * @param value the {@code char} value
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final char value) {
		return Character.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of an {@code int} value.</p>
	 * @param value the {@code int} value
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final int value) {
		return Integer.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of a {@code long} value.</p>
	 * @param value the {@code long} value
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final long value) {
		return Long.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of a {@code float} value.</p>
	 * @param value the {@code float} value
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final float value) {
		return Float.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of a {@code double} value.</p>
	 * @param value the {@code double} value
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final double value) {
		return Double.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of an {@link Object} value.</p>
	 * @param value the {@link Object} value or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final Object value) {
		return Objects.hashCode(value);
	}

	/**
	 * <p>Calculate the hash code of a {@code boolean} array.</p>
	 * @param array the {@code boolean} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final boolean[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of a {@code byte} array.</p>
	 * @param array the {@code byte} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final byte[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of a {@code short} array.</p>
	 * @param array the {@code short} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final short[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of a {@code char} array.</p>
	 * @param array the {@code char} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final char[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of an {@code int} array.</p>
	 * @param array the {@code int} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final int[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of a {@code long} array.</p>
	 * @param array the {@code long} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final long[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of a {@code float} array.</p>
	 * @param array the {@code float} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final float[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of a {@code double} array.</p>
	 * @param array the {@code double} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final double[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of an {@link Object} array.</p>
	 * @param array the {@link Object} array or {@code null}
	 * @return the calculated hash code
	 * @since 1.3.0
	 */
	public static int hashCode(final Object[] array) {
		return Arrays.hashCode(array);
	}

	/**
	 * <p>Calculate the hash code of multiple ones.</p>
	 * @param hashCodes the hash code array
	 * @return the calculated hash code
	 * @throws NullPointerException if the hash code array is {@code null}
	 * @throws IllegalArgumentException if the hash code array is empty
	 * @since 1.3.0
	 */
	public static int of(final int... hashCodes) {
		Ensure.notNullAndNotEmpty("hashCodes", hashCodes);
		return Arrays.hashCode(hashCodes);
	}
}