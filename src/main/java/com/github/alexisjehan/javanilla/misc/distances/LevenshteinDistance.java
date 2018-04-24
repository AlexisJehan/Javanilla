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

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>The Levenshtein {@link EditDistance} implementation.</p>
 * <p><b>Note</b>: This class is serializable.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)} and {@link #hashCode()} methods.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Levenshtein_distance">https://en.wikipedia.org/wiki/Levenshtein_distance</a>
 * @since 1.0
 */
public final class LevenshteinDistance implements EditDistance, Serializable {

	/**
	 * <p>Serial version unique ID.</p>
	 * @since 1.0
	 */
	private static final long serialVersionUID = 760842934378384426L;

	/**
	 * <p>The insertion cost.</p>
	 * @since 1.0
	 */
	private final double insertionCost;

	/**
	 * <p>The deletion cost.</p>
	 * @since 1.0
	 */
	private final double deletionCost;

	/**
	 * <p>The substitution cost.</p>
	 * @since 1.0
	 */
	private final double substitutionCost;

	/**
	 * <p>Default constructor with each cost at {@code 1}.</p>
	 * @since 1.0
	 */
	public LevenshteinDistance() {
		this(1.0d, 1.0d, 1.0d);
	}

	/**
	 * <p>Constructor with customs costs.</p>
	 * @param insertionCost the insertion cost
	 * @param deletionCost the deletion cost
	 * @param substitutionCost the substitution cost
	 * @throws IllegalArgumentException if any cost is lower than or equal to 0
	 * @since 1.0
	 */
	public LevenshteinDistance(final double insertionCost, final double deletionCost, final double substitutionCost) {
		if (0.0d >= insertionCost) {
			throw new IllegalArgumentException("Invalid insertion cost: " + insertionCost + " (greater than 0 expected)");
		}
		if (0.0d >= deletionCost) {
			throw new IllegalArgumentException("Invalid deletion cost: " + deletionCost + " (greater than 0 expected)");
		}
		if (0.0d >= substitutionCost) {
			throw new IllegalArgumentException("Invalid substitution cost: " + substitutionCost + " (greater 0 expected)");
		}
		this.insertionCost = insertionCost;
		this.deletionCost = deletionCost;
		this.substitutionCost = substitutionCost;
	}

	@Override
	public double calculate(final CharSequence charSequence1, final CharSequence charSequence2) {
		if (null == charSequence1) {
			throw new NullPointerException("Invalid first char sequence (not null expected)");
		}
		if (null == charSequence2) {
			throw new NullPointerException("Invalid second char sequence (not null expected)");
		}
		if (charSequence1.equals(charSequence2)) {
			return 0.0d;
		}
		final var length1 = charSequence1.length();
		final var length2 = charSequence2.length();
		if (0 == length1) {
			return insertionCost * length2;
		}
		if (0 == length2) {
			return deletionCost * length1;
		}
		var cost1 = new double[length2 + 1];
		var cost2 = new double[length2 + 1];
		for (var i = 1; i < cost1.length; ++i) {
			cost1[i] = cost1[i - 1] + insertionCost;
		}
		for (var i = 0; i < length1; ++i) {
			cost2[0] = cost1[0] + deletionCost;
			for (var j = 0; j < length2; ++j) {
				cost2[j + 1] = Math.min(
						Math.min(
								cost2[j] + insertionCost,
								cost1[j + 1] + deletionCost
						),
						cost1[j] + (charSequence1.charAt(i) != charSequence2.charAt(j) ? substitutionCost : 0.0d)
				);
			}
			final var swap = cost1;
			cost1 = cost2;
			cost2 = swap;
		}
		return cost1[length2];
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof LevenshteinDistance)) {
			return false;
		}
		final var other = (LevenshteinDistance) object;
		return Objects.equals(insertionCost, other.insertionCost)
				&& Objects.equals(deletionCost, other.deletionCost)
				&& Objects.equals(substitutionCost, other.substitutionCost);
	}

	@Override
	public int hashCode() {
		return Objects.hash(insertionCost, deletionCost, substitutionCost);
	}

	/**
	 * <p>Get the insertion cost.</p>
	 * @return the insertion cost
	 * @since 1.0
	 */
	public double getInsertionCost() {
		return insertionCost;
	}

	/**
	 * <p>Get the deletion cost.</p>
	 * @return the deletion cost
	 * @since 1.0
	 */
	public double getDeletionCost() {
		return deletionCost;
	}

	/**
	 * <p>Get the substitution cost.</p>
	 * @return the substitution cost
	 * @since 1.0
	 */
	public double getSubstitutionCost() {
		return substitutionCost;
	}
}