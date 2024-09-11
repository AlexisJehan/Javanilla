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
final class SingleTest {

	private static final Integer UNIQUE = 1;

	private final Single<Integer> single = new Single<>(UNIQUE);

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(single.equals(single)).isTrue();
		assertThat(single).isNotEqualTo(new Object());
		assertThat(Single.of(UNIQUE)).satisfies(otherSingle -> {
			assertThat(otherSingle).isNotSameAs(single);
			assertThat(otherSingle).isEqualTo(single);
			assertThat(otherSingle).hasSameHashCodeAs(single);
			assertThat(otherSingle).hasToString(single.toString());
		});
		assertThat(Single.of(null)).satisfies(otherSingle -> {
			assertThat(otherSingle).isNotSameAs(single);
			assertThat(otherSingle).isNotEqualTo(single);
			assertThat(otherSingle).doesNotHaveSameHashCodeAs(single);
			assertThat(otherSingle).doesNotHaveToString(single.toString());
		});
	}

	@Test
	void testGetUnique() {
		assertThat(single.getUnique()).isEqualTo(UNIQUE);
	}
}