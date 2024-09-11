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
package com.github.alexisjehan.javanilla.misc.tuples;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Deprecated
final class TripleTest {

	private static final Integer FIRST = 1;
	private static final Integer SECOND = 2;
	private static final Integer THIRD = null;

	private final Triple<Integer, Integer, Integer> triple = new Triple<>(FIRST, SECOND, THIRD);

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(triple.equals(triple)).isTrue();
		assertThat(triple).isNotEqualTo(new Object());
		assertThat(Triple.of(FIRST, SECOND, THIRD)).satisfies(otherTriple -> {
			assertThat(otherTriple).isNotSameAs(triple);
			assertThat(otherTriple).isEqualTo(triple);
			assertThat(otherTriple).hasSameHashCodeAs(triple);
			assertThat(otherTriple).hasToString(triple.toString());
		});
		assertThat(Triple.of(null, SECOND, THIRD)).satisfies(otherTriple -> {
			assertThat(otherTriple).isNotSameAs(triple);
			assertThat(otherTriple).isNotEqualTo(triple);
			assertThat(otherTriple).doesNotHaveSameHashCodeAs(triple);
			assertThat(otherTriple).doesNotHaveToString(triple.toString());
		});
		assertThat(Triple.of(FIRST, null, THIRD)).satisfies(otherTriple -> {
			assertThat(otherTriple).isNotSameAs(triple);
			assertThat(otherTriple).isNotEqualTo(triple);
			assertThat(otherTriple).doesNotHaveSameHashCodeAs(triple);
			assertThat(otherTriple).doesNotHaveToString(triple.toString());
		});
		assertThat(Triple.of(FIRST, SECOND, 3)).satisfies(otherTriple -> {
			assertThat(otherTriple).isNotSameAs(triple);
			assertThat(otherTriple).isNotEqualTo(triple);
			assertThat(otherTriple).doesNotHaveSameHashCodeAs(triple);
			assertThat(otherTriple).doesNotHaveToString(triple.toString());
		});
	}

	@Test
	void testGetters() {
		assertThat(triple.getFirst()).isEqualTo(FIRST);
		assertThat(triple.getSecond()).isEqualTo(SECOND);
		assertThat(triple.getThird()).isEqualTo(THIRD);
	}
}