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
package com.github.alexisjehan.javanilla.misc.tuples;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link Single} unit tests.</p>
 */
final class SingleTest {

	private static final Integer UNIQUE = 1;

	private final Single<Integer> single = new Single<>(UNIQUE);

	@Test
	void testEqualsHashCodeToString() {
		assertThat(single).isEqualTo(single);
		assertThat(single).isNotEqualTo(1);
		assertThat(Single.of(UNIQUE)).satisfies(otherSingle -> {
			assertThat(single).isNotSameAs(otherSingle);
			assertThat(single).isEqualTo(otherSingle);
			assertThat(single).hasSameHashCodeAs(otherSingle);
			assertThat(single).hasToString(otherSingle.toString());
		});
		assertThat(Single.of(null)).satisfies(otherSingle -> {
			assertThat(single).isNotSameAs(otherSingle);
			assertThat(single).isNotEqualTo(otherSingle);
			assertThat(single.hashCode()).isNotEqualTo(otherSingle.hashCode());
			assertThat(single.toString()).isNotEqualTo(otherSingle.toString());
		});
	}

	@Test
	void testGetUnique() {
		assertThat(single.getUnique()).isEqualTo(UNIQUE);
	}
}