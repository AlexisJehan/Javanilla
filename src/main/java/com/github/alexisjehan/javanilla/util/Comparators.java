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

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.Comparator;

/**
 * <p>An utility class that provides {@link Comparator} tools.</p>
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
		if (charSequence1 == charSequence2) {
			return 0;
		}
		if (null == charSequence1) {
			return -1;
		}
		if (null == charSequence2) {
			return 1;
		}
		final var length1 = charSequence1.length();
		final var length2 = charSequence2.length();
		var k1 = 0;
		var k2 = 0;
		while (true) {
			if (k1 == length1 && k2 == length2) {
				return 0;
			}
			if (k1 == length1) {
				return -1;
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
				if (k1 == length1 && k2 == length2) {
					return 0;
				}
				if (k1 == length1) {
					return -1;
				}
				if (k2 == length2) {
					return 1;
				}
			}
			if (charSequence1.charAt(k1) != charSequence2.charAt(k2)) {
				return charSequence1.charAt(k1) > charSequence2.charAt(k2) ? 1 : -1;
			}
			++k1;
			++k2;
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Comparators() {
		// Not available
	}

	/**
	 * <p>Decorate a {@code Comparator} to replace negative and positive results respectively by {@code -1} and
	 * {@code 1}.</p>
	 * @param comparator the {@code Comparator} to normalize
	 * @param <T> the type of objects that may be compared by this comparator
	 * @return a normalized {@code Comparator}
	 * @throws NullPointerException if the {@code Comparator} is {@code null}
	 * @since 1.3.0
	 */
	public static <T> Comparator<T> normalize(final Comparator<T> comparator) {
		Ensure.notNull("comparator", comparator);
		return (object1, object2) ->  {
			final var result = comparator.compare(object1, object2);
			if (0 == result) {
				return 0;
			}
			return 0 < result ? 1 : -1;
		};
	}
}