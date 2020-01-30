/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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
package com.github.alexisjehan.javanilla.misc.distances;

import com.github.alexisjehan.javanilla.io.Serializables;
import com.github.alexisjehan.javanilla.lang.Strings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link LevenshteinDistance} unit tests.</p>
 */
final class LevenshteinDistanceTest {

	private static final double INSERTION_COST = 1.0d;
	private static final double DELETION_COST = 2.0d;
	private static final double SUBSTITUTION_COST = 3.0d;

	private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance(INSERTION_COST, DELETION_COST, SUBSTITUTION_COST);

	@Test
	void testDefault() {
		final var levenshteinDistance = LevenshteinDistance.DEFAULT;
		assertThat(levenshteinDistance.getInsertionCost()).isEqualTo(1.0d);
		assertThat(levenshteinDistance.getDeletionCost()).isEqualTo(1.0d);
		assertThat(levenshteinDistance.getSubstitutionCost()).isEqualTo(1.0d);
	}

	@Test
	void testConstructor() {
		final var levenshteinDistance = new LevenshteinDistance(INSERTION_COST, DELETION_COST, SUBSTITUTION_COST);
		assertThat(levenshteinDistance.getInsertionCost()).isEqualTo(INSERTION_COST);
		assertThat(levenshteinDistance.getDeletionCost()).isEqualTo(DELETION_COST);
		assertThat(levenshteinDistance.getSubstitutionCost()).isEqualTo(SUBSTITUTION_COST);
	}

	@Test
	void testConstructorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(0.0d, DELETION_COST, SUBSTITUTION_COST));
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(INSERTION_COST, 0.0d, SUBSTITUTION_COST));
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(INSERTION_COST, DELETION_COST, 0.0d));
	}

	@Test
	void testCalculate() {
		assertThat(LevenshteinDistance.DEFAULT).satisfies(levenshteinDistance -> {
			assertThat(levenshteinDistance.calculate("a", "a")).isEqualTo(0.0d);
			assertThat(levenshteinDistance.calculate("ab", Strings.EMPTY)).isEqualTo(2.0d);
			assertThat(levenshteinDistance.calculate(Strings.EMPTY, "ab")).isEqualTo(2.0d);
			assertThat(levenshteinDistance.calculate("ab", "abc")).isEqualTo(1.0d);
			assertThat(levenshteinDistance.calculate("ab", "a")).isEqualTo(1.0d);
			assertThat(levenshteinDistance.calculate("ab", "ac")).isEqualTo(1.0d);
			assertThat(levenshteinDistance.calculate("foo", "bar")).isEqualTo(3.0d);
			assertThat(levenshteinDistance.calculate("The quick brown fox jumps over the lazy dog", "The five boxing wizards jump quickly")).isEqualTo(31.0d);
		});
		assertThat(new LevenshteinDistance(1.0d, 2.0d, 3.0d)).satisfies(levenshteinDistance -> {
			assertThat(levenshteinDistance.calculate("a", "a")).isEqualTo(0.0d);
			assertThat(levenshteinDistance.calculate("ab", Strings.EMPTY)).isEqualTo(4.0d);
			assertThat(levenshteinDistance.calculate(Strings.EMPTY, "ab")).isEqualTo(2.0d);
			assertThat(levenshteinDistance.calculate("ab", "abc")).isEqualTo(1.0d);
			assertThat(levenshteinDistance.calculate("ab", "a")).isEqualTo(2.0d);
			assertThat(levenshteinDistance.calculate("ab", "ac")).isEqualTo(3.0d);
			assertThat(levenshteinDistance.calculate("foo", "bar")).isEqualTo(9.0d);
			assertThat(levenshteinDistance.calculate("The quick brown fox jumps over the lazy dog", "The five boxing wizards jump quickly")).isEqualTo(68.0d);
		});
	}

	@Test
	void testCalculateInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LevenshteinDistance.DEFAULT.calculate(null, "bar"));
		assertThatNullPointerException().isThrownBy(() -> LevenshteinDistance.DEFAULT.calculate("foo", null));
	}

	@Test
	void testEqualsHashCodeToString() {
		assertThat(levenshteinDistance.equals(levenshteinDistance)).isTrue();
		assertThat(levenshteinDistance).isNotEqualTo(new Object());
		assertThat(new LevenshteinDistance(INSERTION_COST, DELETION_COST, SUBSTITUTION_COST)).satisfies(otherLevenshteinDistance -> {
			assertThat(levenshteinDistance).isNotSameAs(otherLevenshteinDistance);
			assertThat(levenshteinDistance).isEqualTo(otherLevenshteinDistance);
			assertThat(levenshteinDistance).hasSameHashCodeAs(otherLevenshteinDistance);
			assertThat(levenshteinDistance).hasToString(otherLevenshteinDistance.toString());
		});
		assertThat(new LevenshteinDistance(2.0d, DELETION_COST, SUBSTITUTION_COST)).satisfies(otherLevenshteinDistance -> {
			assertThat(levenshteinDistance).isNotSameAs(otherLevenshteinDistance);
			assertThat(levenshteinDistance).isNotEqualTo(otherLevenshteinDistance);
			assertThat(levenshteinDistance.hashCode()).isNotEqualTo(otherLevenshteinDistance.hashCode());
			assertThat(levenshteinDistance.toString()).isNotEqualTo(otherLevenshteinDistance.toString());
		});
		assertThat(new LevenshteinDistance(INSERTION_COST, 3.0d, SUBSTITUTION_COST)).satisfies(otherLevenshteinDistance -> {
			assertThat(levenshteinDistance).isNotSameAs(otherLevenshteinDistance);
			assertThat(levenshteinDistance).isNotEqualTo(otherLevenshteinDistance);
			assertThat(levenshteinDistance.hashCode()).isNotEqualTo(otherLevenshteinDistance.hashCode());
			assertThat(levenshteinDistance.toString()).isNotEqualTo(otherLevenshteinDistance.toString());
		});
		assertThat(new LevenshteinDistance(INSERTION_COST, DELETION_COST, 4.0d)).satisfies(otherLevenshteinDistance -> {
			assertThat(levenshteinDistance).isNotSameAs(otherLevenshteinDistance);
			assertThat(levenshteinDistance).isNotEqualTo(otherLevenshteinDistance);
			assertThat(levenshteinDistance.hashCode()).isNotEqualTo(otherLevenshteinDistance.hashCode());
			assertThat(levenshteinDistance.toString()).isNotEqualTo(otherLevenshteinDistance.toString());
		});
	}

	@Test
	void testGetters() {
		assertThat(levenshteinDistance.getInsertionCost()).isEqualTo(INSERTION_COST);
		assertThat(levenshteinDistance.getDeletionCost()).isEqualTo(DELETION_COST);
		assertThat(levenshteinDistance.getSubstitutionCost()).isEqualTo(SUBSTITUTION_COST);
	}

	@Test
	void testSerializable() {
		assertThat(Serializables.<LevenshteinDistance>deserialize(Serializables.serialize(levenshteinDistance))).isEqualTo(levenshteinDistance);
	}
}