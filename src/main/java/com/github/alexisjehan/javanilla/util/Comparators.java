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
package com.github.alexisjehan.javanilla.util;

import java.util.Comparator;

/**
 * <p>An utility class that provides {@link Comparator} and {@link Comparable} tools.</p>
 * @since 1.0.0
 */
public final class Comparators {

	/**
	 * <p>A {@code CharSequence} {@code Comparator} that takes numbers into consideration.</p>
	 * <p><b>Example</b>: {@code ["example2", "example1", "example11"]} would be sorted like
	 * {@code ["example1", "example2", "example11"]} and not {@code ["example1", "example11", "example2"]}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<CharSequence> NUMBER_AWARE = (charSequence1, charSequence2) -> {
		if (null == charSequence1) {
			throw new NullPointerException("Invalid first CharSequence (not null expected)");
		}
		if (null == charSequence2) {
			throw new NullPointerException("Invalid second CharSequence (not null expected)");
		}
		final var length1 = charSequence1.length();
		final var length2 = charSequence2.length();
		var k1 = 0;
		var k2 = 0;
		while (true) {
			if (k1 == length1) {
				return k2 == length2 ? 0 : -1;
			}
			if (k2 == length2) {
				return 1;
			}
			if ('0' <= charSequence1.charAt(k1) && '9' >= charSequence1.charAt(k1) && '0' <= charSequence2.charAt(k2) && '9' >= charSequence2.charAt(k2)) {
				while (k1 < length1 && '0' == charSequence1.charAt(k1)) {
					++k1;
				}
				var n1 = 0;
				while (k1 + n1 < length1 && '0' <= charSequence1.charAt(k1 + n1) && '9' >= charSequence1.charAt(k1 + n1)) {
					++n1;
				}
				while (k2 < length2 && '0' == charSequence2.charAt(k2)) {
					++k2;
				}
				var n2 = 0;
				while (k2 + n2 < length2 && '0' <= charSequence2.charAt(k2 + n2) && '9' >= charSequence2.charAt(k2 + n2)) {
					++n2;
				}
				if (n1 > n2) {
					return 1;
				}
				if (n2 > n1) {
					return -1;
				}
				if (k1 == length1) {
					return k2 == length2 ? 0 : -1;
				}
				if (k2 == length2) {
					return 1;
				}
			}
			if (charSequence1.charAt(k1) != charSequence2.charAt(k2)) {
				return Integer.compare(charSequence1.charAt(k1), charSequence2.charAt(k2));
			}
			++k1;
			++k2;
		}
	};

	/**
	 * <p>A {@code boolean} array {@code Comparator}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<boolean[]> BOOLEAN_ARRAYS = (array1, array2) -> {
		if (null == array1) {
			throw new NullPointerException("Invalid first array (not null expected)");
		}
		if (null == array2) {
			throw new NullPointerException("Invalid second array (not null expected)");
		}
		for (var i = 0; i < array1.length && i < array2.length; ++i) {
			if (array1[i] != array2[i]) {
				return array1[i] ? 1 : -1;
			}
		}
		return Integer.compare(array1.length, array2.length);
	};

	/**
	 * <p>A signed {@code byte} array {@code Comparator}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<byte[]> SIGNED_BYTE_ARRAYS = (array1, array2) -> {
		if (null == array1) {
			throw new NullPointerException("Invalid first array (not null expected)");
		}
		if (null == array2) {
			throw new NullPointerException("Invalid second array (not null expected)");
		}
		for (var i = 0; i < array1.length && i < array2.length; ++i) {
			if (array1[i] != array2[i]) {
				return array1[i] > array2[i] ? 1 : -1;
			}
		}
		return Integer.compare(array1.length, array2.length);
	};

	/**
	 * <p>An unsigned {@code byte} array {@code Comparator}.</p>
	 * <p><b>Note</b>: {@code byte}s are compared using {@link Byte#toUnsignedInt(byte)}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<byte[]> UNSIGNED_BYTE_ARRAYS = (array1, array2) -> {
		if (null == array1) {
			throw new NullPointerException("Invalid first array (not null expected)");
		}
		if (null == array2) {
			throw new NullPointerException("Invalid second array (not null expected)");
		}
		for (var i = 0; i < array1.length && i < array2.length; ++i) {
			final var b1 = Byte.toUnsignedInt(array1[i]);
			final var b2 = Byte.toUnsignedInt(array2[i]);
			if (b1 != b2) {
				return b1 > b2 ? 1 : -1;
			}
		}
		return Integer.compare(array1.length, array2.length);
	};

	/**
	 * <p>A {@code short} array {@code Comparator}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<short[]> SHORT_ARRAYS = (array1, array2) -> {
		if (null == array1) {
			throw new NullPointerException("Invalid first array (not null expected)");
		}
		if (null == array2) {
			throw new NullPointerException("Invalid second array (not null expected)");
		}
		for (var i = 0; i < array1.length && i < array2.length; ++i) {
			if (array1[i] != array2[i]) {
				return array1[i] > array2[i] ? 1 : -1;
			}
		}
		return Integer.compare(array1.length, array2.length);
	};

	/**
	 * <p>An {@code int} array {@code Comparator}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<int[]> INT_ARRAYS = (array1, array2) -> {
		if (null == array1) {
			throw new NullPointerException("Invalid first array (not null expected)");
		}
		if (null == array2) {
			throw new NullPointerException("Invalid second array (not null expected)");
		}
		for (var i = 0; i < array1.length && i < array2.length; ++i) {
			if (array1[i] != array2[i]) {
				return array1[i] > array2[i] ? 1 : -1;
			}
		}
		return Integer.compare(array1.length, array2.length);
	};

	/**
	 * <p>A {@code long} array {@code Comparator}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<long[]> LONG_ARRAYS = (array1, array2) -> {
		if (null == array1) {
			throw new NullPointerException("Invalid first array (not null expected)");
		}
		if (null == array2) {
			throw new NullPointerException("Invalid second array (not null expected)");
		}
		for (var i = 0; i < array1.length && i < array2.length; ++i) {
			if (array1[i] != array2[i]) {
				return array1[i] > array2[i] ? 1 : -1;
			}
		}
		return Integer.compare(array1.length, array2.length);
	};

	/**
	 * <p>A {@code float} array {@code Comparator}.</p>
	 * <p><b>Note</b>: {@code float}s are compared using {@link Float#floatToIntBits(float)}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<float[]> FLOAT_ARRAYS = (array1, array2) -> {
		if (null == array1) {
			throw new NullPointerException("Invalid first array (not null expected)");
		}
		if (null == array2) {
			throw new NullPointerException("Invalid second array (not null expected)");
		}
		for (var i = 0; i < array1.length && i < array2.length; ++i) {
			final var f1 = Float.floatToIntBits(array1[i]);
			final var f2 = Float.floatToIntBits(array2[i]);
			if (f1 != f2) {
				return f1 > f2 ? 1 : -1;
			}
		}
		return Integer.compare(array1.length, array2.length);
	};

	/**
	 * <p>A {@code double} array {@code Comparator}.</p>
	 * <p><b>Note</b>: {@code double}s are compared using {@link Double#doubleToLongBits(double)}.</p>
	 * @since 1.0.0
	 */
	public static final Comparator<double[]> DOUBLE_ARRAYS = (array1, array2) -> {
		if (null == array1) {
			throw new NullPointerException("Invalid first array (not null expected)");
		}
		if (null == array2) {
			throw new NullPointerException("Invalid second array (not null expected)");
		}
		for (var i = 0; i < array1.length && i < array2.length; ++i) {
			final var d1 = Double.doubleToLongBits(array1[i]);
			final var d2 = Double.doubleToLongBits(array2[i]);
			if (d1 != d2) {
				return d1 > d2 ? 1 : -1;
			}
		}
		return Integer.compare(array1.length, array2.length);
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Comparators() {
		// Not available
	}

	/**
	 * <p>Create a {@code Comparator} for {@code Comparable}s arrays.</p>
	 * @param <T> the type of {@code Comparable}s in arrays
	 * @return the created {@code Comparable} arrays {@code Comparator}
	 * @since 1.0.0
	 */
	public static <T extends Comparable<T>> Comparator<T[]> array() {
		return (array1, array2) -> {
			if (null == array1) {
				throw new NullPointerException("Invalid first array (not null expected)");
			}
			if (null == array2) {
				throw new NullPointerException("Invalid second array (not null expected)");
			}
			for (var i = 0; i < array1.length && i < array2.length; ++i) {
				final var r = array1[i].compareTo(array2[i]);
				if (0 != r) {
					return r;
				}
			}
			return Integer.compare(array1.length, array2.length);
		};
	}

	/**
	 * <p>Create a {@code Comparator} for arrays using the given {@code Comparator} for elements.</p>
	 * @param comparator the elements {@code Comparator}
	 * @param <T> the type of elements in arrays
	 * @return the created arrays {@code Comparator}
	 * @since 1.0.0
	 */
	public static <T> Comparator<T[]> array(final Comparator<T> comparator) {
		return (array1, array2) -> {
			if (null == array1) {
				throw new NullPointerException("Invalid first array (not null expected)");
			}
			if (null == array2) {
				throw new NullPointerException("Invalid second array (not null expected)");
			}
			for (var i = 0; i < array1.length && i < array2.length; ++i) {
				final var r = comparator.compare(array1[i], array2[i]);
				if (0 != r) {
					return r;
				}
			}
			return Integer.compare(array1.length, array2.length);
		};
	}
}