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
package com.github.alexisjehan.javanilla.misc.distances;

/**
 * <p>Commons {@link EditDistance}s implementations.</p>
 * @since 1.0.0
 */
public enum EditDistances implements EditDistance {

	/**
	 * <p>The Longest Common Subsequence (LCS) distance.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Longest_common_subsequence_problem">https://en.wikipedia.org/wiki/Longest_common_subsequence_problem</a>
	 * @since 1.0.0
	 */
	LCS {
		@Override
		protected double calculateImpl(final CharSequence charSequence1, final int length1, final CharSequence charSequence2, final int length2) {
			final var lengths = new int[length1 + 1][length2 + 1];
			for (var i = 0; i < length1; ++i) {
				for (var j = 0; j < length2; ++j) {
					lengths[i + 1][j + 1] = charSequence1.charAt(i) == charSequence2.charAt(j)
							? lengths[i][j] + 1
							: Math.max(lengths[i + 1][j], lengths[i][j + 1]);
				}
			}
			return lengths[length1][length2];
		}
	},

	/**
	 * <p>The Hamming distance.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Hamming_distance">https://en.wikipedia.org/wiki/Hamming_distance</a>
	 * @since 1.0.0
	 */
	HAMMING {
		@Override
		protected double calculateImpl(final CharSequence charSequence1, final int length1, final CharSequence charSequence2, final int length2) {
			if (length1 != length2) {
				throw new IllegalArgumentException("Invalid CharSequence lengths: " + length1 + " and " + length2 + " (same expected)");
			}
			if (charSequence1.equals(charSequence2)) {
				return 0.0d;
			}
			var result = 0;
			for (var i = 0; i < length1; ++i) {
				if (charSequence1.charAt(i) != charSequence2.charAt(i)) {
					++result;
				}
			}
			return result;
		}
	};

	@Override
	public final double calculate(final CharSequence charSequence1, final CharSequence charSequence2) {
		if (null == charSequence1) {
			throw new NullPointerException("Invalid first CharSequence (not null expected)");
		}
		if (null == charSequence2) {
			throw new NullPointerException("Invalid second CharSequence (not null expected)");
		}
		return calculateImpl(charSequence1, charSequence1.length(), charSequence2, charSequence2.length());
	}

	/**
	 * <p>Calculate an edit distance between both {@code CharSequence}s which have been validated.</p>
	 * @param charSequence1 the first {@code CharSequence}
	 * @param length1 the length of the first {@code CharSequence}
	 * @param charSequence2 the second {@code CharSequence}
	 * @param length2 the length of the second {@code CharSequence}
	 * @return the calculated edit distance
	 * @since 1.0.0
	 */
	protected abstract double calculateImpl(final CharSequence charSequence1, final int length1, final CharSequence charSequence2, final int length2);
}