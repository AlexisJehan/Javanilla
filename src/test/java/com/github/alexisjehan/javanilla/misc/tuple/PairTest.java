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
package com.github.alexisjehan.javanilla.misc.tuple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class PairTest {

	private static final Integer FIRST = 1;

	private static final Integer SECOND = null;

	private final Pair<Integer, Integer> pair = new Pair<>(FIRST, SECOND);

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(pair.equals(pair)).isTrue();
		assertThat(pair).isNotEqualTo(new Object());
		assertThat(Pair.of(FIRST, SECOND)).satisfies(otherPair -> {
			assertThat(otherPair).isNotSameAs(pair);
			assertThat(otherPair).isEqualTo(pair);
			assertThat(otherPair).hasSameHashCodeAs(pair);
			assertThat(otherPair).hasToString(pair.toString());
		});
		assertThat(Pair.of(null, SECOND)).satisfies(otherPair -> {
			assertThat(otherPair).isNotSameAs(pair);
			assertThat(otherPair).isNotEqualTo(pair);
			assertThat(otherPair).doesNotHaveSameHashCodeAs(pair);
			assertThat(otherPair).doesNotHaveToString(pair.toString());
		});
		assertThat(Pair.of(FIRST, 2)).satisfies(otherPair -> {
			assertThat(otherPair).isNotSameAs(pair);
			assertThat(otherPair).isNotEqualTo(pair);
			assertThat(otherPair).doesNotHaveSameHashCodeAs(pair);
			assertThat(otherPair).doesNotHaveToString(pair.toString());
		});
	}

	@Test
	void testGetters() {
		assertThat(pair.getFirst()).isEqualTo(FIRST);
		assertThat(pair.getSecond()).isEqualTo(SECOND);
	}
}