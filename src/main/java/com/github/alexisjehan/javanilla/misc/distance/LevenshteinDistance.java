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
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;
import com.github.alexisjehan.javanilla.misc.quality.ToString;
import com.github.alexisjehan.javanilla.misc.tuple.Pair;

import java.io.Serializable;

/**
 * The Levenshtein {@link EditDistance} implementation.
 *
 * <p><b>Note</b>: This class is {@link Serializable}.</p>
 *
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Levenshtein_distance">https://en.wikipedia.org/wiki/Levenshtein_distance</a>
 * @since 1.8.0
 */
public final class LevenshteinDistance implements EditDistance, Serializable {

	/**
	 * {@link LevenshteinDistance} instance with default parameters.
	 * @since 1.8.0
	 */
	public static final LevenshteinDistance DEFAULT = new LevenshteinDistance(1.0d, 1.0d, 1.0d);

	/**
	 * Serial version unique ID.
	 * @since 1.8.0
	 */
	private static final long serialVersionUID = 4996498499916400857L;

	/**
	 * Insertion cost.
	 * @since 1.8.0
	 */
	private final double insertionCost;

	/**
	 * Deletion cost.
	 * @since 1.8.0
	 */
	private final double deletionCost;

	/**
	 * Substitution cost.
	 * @since 1.8.0
	 */
	private final double substitutionCost;

	/**
	 * Constructor with customs costs.
	 * @param insertionCost the insertion cost
	 * @param deletionCost the deletion cost
	 * @param substitutionCost the substitution cost
	 * @throws IllegalArgumentException if any cost is lower than or equal to {@code 0}
	 * @since 1.8.0
	 */
	public LevenshteinDistance(final double insertionCost, final double deletionCost, final double substitutionCost) {
		Ensure.greaterThan("insertionCost", insertionCost, 0.0d);
		Ensure.greaterThan("deletionCost", deletionCost, 0.0d);
		Ensure.greaterThan("substitutionCost", substitutionCost, 0.0d);
		this.insertionCost = insertionCost;
		this.deletionCost = deletionCost;
		this.substitutionCost = substitutionCost;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(final CharSequence charSequence1, final CharSequence charSequence2) {
		Ensure.notNull("charSequence1", charSequence1);
		Ensure.notNull("charSequence2", charSequence2);
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
				cost2[j + 1] = StrictMath.min(
						StrictMath.min(
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof LevenshteinDistance)) {
			return false;
		}
		final var other = (LevenshteinDistance) object;
		return Equals.equals(insertionCost, other.insertionCost)
				&& Equals.equals(deletionCost, other.deletionCost)
				&& Equals.equals(substitutionCost, other.substitutionCost);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.of(
				HashCode.hashCode(insertionCost),
				HashCode.hashCode(deletionCost),
				HashCode.hashCode(substitutionCost)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ToString.of(
				this,
				Pair.of("insertionCost", ToString.toString(insertionCost)),
				Pair.of("deletionCost", ToString.toString(deletionCost)),
				Pair.of("substitutionCost", ToString.toString(substitutionCost))
		);
	}

	/**
	 * Get the insertion cost.
	 * @return the insertion cost
	 * @since 1.8.0
	 */
	public double getInsertionCost() {
		return insertionCost;
	}

	/**
	 * Get the deletion cost.
	 * @return the deletion cost
	 * @since 1.8.0
	 */
	public double getDeletionCost() {
		return deletionCost;
	}

	/**
	 * Get the substitution cost.
	 * @return the substitution cost
	 * @since 1.8.0
	 */
	public double getSubstitutionCost() {
		return substitutionCost;
	}
}