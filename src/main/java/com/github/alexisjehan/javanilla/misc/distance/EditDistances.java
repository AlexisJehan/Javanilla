/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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
package com.github.alexisjehan.javanilla.misc.distance;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

/**
 * Commons {@link EditDistance}s implementations.
 * @since 1.8.0
 */
public enum EditDistances implements EditDistance {

	/**
	 * The Longest Common Subsequence (LCS) distance.
	 * @see <a href="https://en.wikipedia.org/wiki/Longest_common_subsequence_problem">https://en.wikipedia.org/wiki/Longest_common_subsequence_problem</a>
	 * @since 1.8.0
	 */
	LCS {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected double calculateImpl(final CharSequence charSequence1, final int length1, final CharSequence charSequence2, final int length2) {
			final var lengths = new int[length1 + 1][length2 + 1];
			for (var i = 0; i < length1; ++i) {
				for (var j = 0; j < length2; ++j) {
					lengths[i + 1][j + 1] = charSequence1.charAt(i) == charSequence2.charAt(j)
							? lengths[i][j] + 1
							: StrictMath.max(lengths[i + 1][j], lengths[i][j + 1]);
				}
			}
			return lengths[length1][length2];
		}
	},

	/**
	 * The Hamming distance.
	 * @see <a href="https://en.wikipedia.org/wiki/Hamming_distance">https://en.wikipedia.org/wiki/Hamming_distance</a>
	 * @since 1.8.0
	 */
	HAMMING {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected double calculateImpl(final CharSequence charSequence1, final int length1, final CharSequence charSequence2, final int length2) {
			Ensure.equalTo("length2", length2, length1);
			if (charSequence1.equals(charSequence2)) {
				return 0.0d;
			}
			var editDistance = 0.0d;
			for (var i = 0; i < length1; ++i) {
				if (charSequence1.charAt(i) != charSequence2.charAt(i)) {
					++editDistance;
				}
			}
			return editDistance;
		}
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double calculate(final CharSequence charSequence1, final CharSequence charSequence2) {
		Ensure.notNull("charSequence1", charSequence1);
		Ensure.notNull("charSequence2", charSequence2);
		return calculateImpl(charSequence1, charSequence1.length(), charSequence2, charSequence2.length());
	}

	/**
	 * Calculate an edit distance between both {@link CharSequence}s which have been validated.
	 * @param charSequence1 the first {@link CharSequence}
	 * @param length1 the length of the first {@link CharSequence}
	 * @param charSequence2 the second {@link CharSequence}
	 * @param length2 the length of the second {@link CharSequence}
	 * @return the calculated edit distance
	 * @since 1.8.0
	 */
	protected abstract double calculateImpl(CharSequence charSequence1, int length1, CharSequence charSequence2, int length2);
}