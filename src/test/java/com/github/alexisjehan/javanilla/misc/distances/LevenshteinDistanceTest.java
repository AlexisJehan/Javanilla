/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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

@Deprecated
final class LevenshteinDistanceTest {

	private static final double INSERTION_COST = 1.0d;
	private static final double DELETION_COST = 2.0d;
	private static final double SUBSTITUTION_COST = 3.0d;

	private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance(INSERTION_COST, DELETION_COST, SUBSTITUTION_COST);

	@Test
	void testDefault() {
		assertThat(LevenshteinDistance.DEFAULT).satisfies(otherLevenshteinDistance -> {
			assertThat(otherLevenshteinDistance.getInsertionCost()).isEqualTo(1.0d);
			assertThat(otherLevenshteinDistance.getDeletionCost()).isEqualTo(1.0d);
			assertThat(otherLevenshteinDistance.getSubstitutionCost()).isEqualTo(1.0d);
		});
	}

	@Test
	void testConstructor() {
		assertThat(new LevenshteinDistance(INSERTION_COST, DELETION_COST, SUBSTITUTION_COST)).satisfies(otherLevenshteinDistance -> {
			assertThat(otherLevenshteinDistance.getInsertionCost()).isEqualTo(INSERTION_COST);
			assertThat(otherLevenshteinDistance.getDeletionCost()).isEqualTo(DELETION_COST);
			assertThat(otherLevenshteinDistance.getSubstitutionCost()).isEqualTo(SUBSTITUTION_COST);
		});
	}

	@Test
	void testConstructorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(0.0d, DELETION_COST, SUBSTITUTION_COST));
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(INSERTION_COST, 0.0d, SUBSTITUTION_COST));
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(INSERTION_COST, DELETION_COST, 0.0d));
	}

	@Test
	void testCalculate() {
		assertThat(LevenshteinDistance.DEFAULT).satisfies(otherLevenshteinDistance -> {
			assertThat(otherLevenshteinDistance.calculate("a", "a")).isZero();
			assertThat(otherLevenshteinDistance.calculate("ab", Strings.EMPTY)).isEqualTo(2.0d);
			assertThat(otherLevenshteinDistance.calculate(Strings.EMPTY, "ab")).isEqualTo(2.0d);
			assertThat(otherLevenshteinDistance.calculate("ab", "abc")).isEqualTo(1.0d);
			assertThat(otherLevenshteinDistance.calculate("ab", "a")).isEqualTo(1.0d);
			assertThat(otherLevenshteinDistance.calculate("ab", "ac")).isEqualTo(1.0d);
			assertThat(otherLevenshteinDistance.calculate("foo", "bar")).isEqualTo(3.0d);
			assertThat(otherLevenshteinDistance.calculate("The quick brown fox jumps over the lazy dog", "The five boxing wizards jump quickly")).isEqualTo(31.0d);
		});
		assertThat(new LevenshteinDistance(1.0d, 2.0d, 3.0d)).satisfies(otherLevenshteinDistance -> {
			assertThat(otherLevenshteinDistance.calculate("a", "a")).isZero();
			assertThat(otherLevenshteinDistance.calculate("ab", Strings.EMPTY)).isEqualTo(4.0d);
			assertThat(otherLevenshteinDistance.calculate(Strings.EMPTY, "ab")).isEqualTo(2.0d);
			assertThat(otherLevenshteinDistance.calculate("ab", "abc")).isEqualTo(1.0d);
			assertThat(otherLevenshteinDistance.calculate("ab", "a")).isEqualTo(2.0d);
			assertThat(otherLevenshteinDistance.calculate("ab", "ac")).isEqualTo(3.0d);
			assertThat(otherLevenshteinDistance.calculate("foo", "bar")).isEqualTo(9.0d);
			assertThat(otherLevenshteinDistance.calculate("The quick brown fox jumps over the lazy dog", "The five boxing wizards jump quickly")).isEqualTo(68.0d);
		});
	}

	@Test
	void testCalculateInvalid() {
		assertThatNullPointerException().isThrownBy(() -> levenshteinDistance.calculate(null, "bar"));
		assertThatNullPointerException().isThrownBy(() -> levenshteinDistance.calculate("foo", null));
	}

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(levenshteinDistance.equals(levenshteinDistance)).isTrue();
		assertThat(levenshteinDistance).isNotEqualTo(new Object());
		assertThat(new LevenshteinDistance(INSERTION_COST, DELETION_COST, SUBSTITUTION_COST)).satisfies(otherLevenshteinDistance -> {
			assertThat(otherLevenshteinDistance).isNotSameAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).isEqualTo(levenshteinDistance);
			assertThat(otherLevenshteinDistance).hasSameHashCodeAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).hasToString(levenshteinDistance.toString());
		});
		assertThat(new LevenshteinDistance(2.0d, DELETION_COST, SUBSTITUTION_COST)).satisfies(otherLevenshteinDistance -> {
			assertThat(otherLevenshteinDistance).isNotSameAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).isNotEqualTo(levenshteinDistance);
			assertThat(otherLevenshteinDistance).doesNotHaveSameHashCodeAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).doesNotHaveToString(levenshteinDistance.toString());
		});
		assertThat(new LevenshteinDistance(INSERTION_COST, 3.0d, SUBSTITUTION_COST)).satisfies(otherLevenshteinDistance -> {
			assertThat(otherLevenshteinDistance).isNotSameAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).isNotEqualTo(levenshteinDistance);
			assertThat(otherLevenshteinDistance).doesNotHaveSameHashCodeAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).doesNotHaveToString(levenshteinDistance.toString());
		});
		assertThat(new LevenshteinDistance(INSERTION_COST, DELETION_COST, 4.0d)).satisfies(otherLevenshteinDistance -> {
			assertThat(otherLevenshteinDistance).isNotSameAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).isNotEqualTo(levenshteinDistance);
			assertThat(otherLevenshteinDistance).doesNotHaveSameHashCodeAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).doesNotHaveToString(levenshteinDistance.toString());
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