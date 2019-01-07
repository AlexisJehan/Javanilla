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
package com.github.alexisjehan.javanilla.misc.tuples;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link Single} unit tests.</p>
 */
final class SingleTest {

	@Test
	void testNull() {
		final var single1 = new Single<>(1);
		final var single2 = new Single<>(2);
		final var single3 = new Single<>(null);
		assertThat(single1.getUnique()).isNotNull();
		assertThat(single2.getUnique()).isNotNull();
		assertThat(single3.getUnique()).isNull();
		assertThat(single1).isEqualTo(Single.of(1));
		assertThat(single2).isEqualTo(Single.of(2));
		assertThat(single3).isEqualTo(Single.of(null));
		assertThat(single1).isNotEqualTo(single2);
		assertThat(single2).isNotEqualTo(single3);
		assertThat(single3).isNotEqualTo(single1);
	}

	@Test
	void testSame() {
		final var single = new Single<>(1);
		assertThat(single.getUnique()).isEqualTo(1);
		assertThat(single).isEqualTo(single);
		assertThat(single).isEqualTo(Single.of(1));
		assertThat(single.hashCode()).isEqualTo(Single.of(1).hashCode());
		assertThat(single.toString()).isEqualTo(Single.of(1).toString());
	}

	@Test
	void testNotSame() {
		final var single = new Single<>(1);
		assertThat(single.getUnique()).isNotEqualTo(2);
		assertThat(single).isNotEqualTo(null);
		assertThat(single).isNotEqualTo(Single.of(2));
		assertThat(single).isNotEqualTo(Pair.of(1, 2));
		assertThat(single).isNotEqualTo(Triple.of(1, 2, 3));
		assertThat(single.hashCode()).isNotEqualTo(Single.of(2).hashCode());
		assertThat(single.toString()).isNotEqualTo(Single.of(2).toString());
	}
}