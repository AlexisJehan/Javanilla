/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link LevenshteinDistance} unit tests.</p>
 */
final class LevenshteinDistanceTest {

	@Test
	void testConstructor() {
		{
			final var levenshteinDistance = LevenshteinDistance.DEFAULT;
			assertThat(levenshteinDistance.getInsertionCost()).isEqualTo(1.0d);
			assertThat(levenshteinDistance.getDeletionCost()).isEqualTo(1.0d);
			assertThat(levenshteinDistance.getSubstitutionCost()).isEqualTo(1.0d);
		}
		{
			final var levenshteinDistance = new LevenshteinDistance(1.0d, 2.0d, 3.0d);
			assertThat(levenshteinDistance.getInsertionCost()).isEqualTo(1.0d);
			assertThat(levenshteinDistance.getDeletionCost()).isEqualTo(2.0d);
			assertThat(levenshteinDistance.getSubstitutionCost()).isEqualTo(3.0d);
		}
	}

	@Test
	void testConstructorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(0.0d, 1.0d, 1.0d));
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(1.0d, 0.0d, 1.0d));
		assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(1.0d, 1.0d, 0.0d));
	}

	@Test
	void testCalculate() {
		{
			final var levenshteinDistance = LevenshteinDistance.DEFAULT;
			assertThat(levenshteinDistance.calculate("a", "a")).isEqualTo(0.0d);
			assertThat(levenshteinDistance.calculate("ab", "")).isEqualTo(2.0d);
			assertThat(levenshteinDistance.calculate("", "ab")).isEqualTo(2.0d);
			assertThat(levenshteinDistance.calculate("ab", "abc")).isEqualTo(1.0d);
			assertThat(levenshteinDistance.calculate("ab", "a")).isEqualTo(1.0d);
			assertThat(levenshteinDistance.calculate("ab", "ac")).isEqualTo(1.0d);
			assertThat(levenshteinDistance.calculate("foo", "bar")).isEqualTo(3.0d);
			assertThat(levenshteinDistance.calculate("The quick brown fox jumps over the lazy dog", "The five boxing wizards jump quickly")).isEqualTo(31.0d);
		}
		{
			final var levenshteinDistance = new LevenshteinDistance(1.0d, 2.0d, 3.0d);
			assertThat(levenshteinDistance.calculate("a", "a")).isEqualTo(0.0d);
			assertThat(levenshteinDistance.calculate("ab", "")).isEqualTo(4.0d);
			assertThat(levenshteinDistance.calculate("", "ab")).isEqualTo(2.0d);
			assertThat(levenshteinDistance.calculate("ab", "abc")).isEqualTo(1.0d);
			assertThat(levenshteinDistance.calculate("ab", "a")).isEqualTo(2.0d);
			assertThat(levenshteinDistance.calculate("ab", "ac")).isEqualTo(3.0d);
			assertThat(levenshteinDistance.calculate("foo", "bar")).isEqualTo(9.0d);
			assertThat(levenshteinDistance.calculate("The quick brown fox jumps over the lazy dog", "The five boxing wizards jump quickly")).isEqualTo(68.0d);
		}
	}

	@Test
	void testCalculateInvalid() {
		assertThatNullPointerException().isThrownBy(() -> LevenshteinDistance.DEFAULT.calculate(null, "foo"));
		assertThatNullPointerException().isThrownBy(() -> LevenshteinDistance.DEFAULT.calculate("foo", null));
	}

	@Test
	void testEqualsHashCodeToString() {
		final var levenshteinDistance = LevenshteinDistance.DEFAULT;
		assertThat(levenshteinDistance).isEqualTo(levenshteinDistance);
		assertThat(levenshteinDistance).isNotEqualTo(1);
		{
			final var otherLevenshteinDistance = new LevenshteinDistance(1.0d, 1.0d, 1.0d);
			assertThat(otherLevenshteinDistance).isEqualTo(levenshteinDistance);
			assertThat(otherLevenshteinDistance).hasSameHashCodeAs(levenshteinDistance);
			assertThat(otherLevenshteinDistance).hasToString(levenshteinDistance.toString());
		}
		{
			final var otherLevenshteinDistance = new LevenshteinDistance(2.0d, 1.0d, 1.0d);
			assertThat(otherLevenshteinDistance).isNotEqualTo(levenshteinDistance);
			assertThat(otherLevenshteinDistance.hashCode()).isNotEqualTo(levenshteinDistance.hashCode());
			assertThat(otherLevenshteinDistance.toString()).isNotEqualTo(levenshteinDistance.toString());
		}
		{
			final var otherLevenshteinDistance = new LevenshteinDistance(1.0d, 2.0d, 1.0d);
			assertThat(otherLevenshteinDistance).isNotEqualTo(levenshteinDistance);
			assertThat(otherLevenshteinDistance.hashCode()).isNotEqualTo(levenshteinDistance.hashCode());
			assertThat(otherLevenshteinDistance.toString()).isNotEqualTo(levenshteinDistance.toString());
		}
		{
			final var otherLevenshteinDistance = new LevenshteinDistance(1.0d, 1.0d, 2.0d);
			assertThat(otherLevenshteinDistance).isNotEqualTo(levenshteinDistance);
			assertThat(otherLevenshteinDistance.hashCode()).isNotEqualTo(levenshteinDistance.hashCode());
			assertThat(otherLevenshteinDistance.toString()).isNotEqualTo(levenshteinDistance.toString());
		}
	}

	@Test
	void testSerializable() {
		final var levenshteinDistance = new LevenshteinDistance(1.0d, 2.0d, 3.0d);
		assertThat(Serializables.<LevenshteinDistance>deserialize(Serializables.serialize(levenshteinDistance))).isEqualTo(levenshteinDistance);
	}
}