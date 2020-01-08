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
 * <p>{@link Triple} unit tests.</p>
 */
final class TripleTest {

	@Test
	void testNull() {
		final var triple1 = new Triple<>(1, 2, null);
		final var triple2 = new Triple<>(null, 2, 3);
		final var triple3 = new Triple<>(null, null, null);
		assertThat(triple1.getFirst()).isNotNull();
		assertThat(triple1.getSecond()).isNotNull();
		assertThat(triple1.getThird()).isNull();
		assertThat(triple2.getFirst()).isNull();
		assertThat(triple2.getSecond()).isNotNull();
		assertThat(triple2.getThird()).isNotNull();
		assertThat(triple3.getFirst()).isNull();
		assertThat(triple3.getSecond()).isNull();
		assertThat(triple3.getThird()).isNull();
		assertThat(triple1).isEqualTo(Triple.of(1, 2, null));
		assertThat(triple2).isEqualTo(Triple.of(null, 2, 3));
		assertThat(triple3).isEqualTo(Triple.of(null, null, null));
		assertThat(triple1).isNotEqualTo(triple2);
		assertThat(triple2).isNotEqualTo(triple3);
		assertThat(triple3).isNotEqualTo(triple1);
	}

	@Test
	void testSame() {
		final var triple = new Triple<>(1, 2, 3);
		assertThat(triple.getFirst()).isEqualTo(1);
		assertThat(triple.getSecond()).isEqualTo(2);
		assertThat(triple.getThird()).isEqualTo(3);
		assertThat(triple).isEqualTo(triple);
		assertThat(triple).isEqualTo(Triple.of(1, 2, 3));
		assertThat(triple.hashCode()).isEqualTo(Triple.of(1, 2, 3).hashCode());
		assertThat(triple.toString()).isEqualTo(Triple.of(1, 2, 3).toString());
	}

	@Test
	void testNotSame() {
		final var triple = new Triple<>(1, 2, 3);
		assertThat(triple.getFirst()).isNotEqualTo(2);
		assertThat(triple.getSecond()).isNotEqualTo(3);
		assertThat(triple.getThird()).isNotEqualTo(1);
		assertThat(triple).isNotEqualTo(null);
		assertThat(triple).isNotEqualTo(Single.of(1));
		assertThat(triple).isNotEqualTo(Pair.of(1, 2));
		assertThat(triple).isNotEqualTo(Triple.of(1, 2, 4));
		assertThat(triple.hashCode()).isNotEqualTo(Triple.of(1, 2, 4).hashCode());
		assertThat(triple.toString()).isNotEqualTo(Triple.of(1, 2, 4).toString());
	}
}