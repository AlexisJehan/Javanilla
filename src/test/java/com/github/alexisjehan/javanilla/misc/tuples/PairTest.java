/*
MIT License

Copyright (c) 2018 Alexis Jehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.github.alexisjehan.javanilla.misc.tuples;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * <p>{@link Pair} unit tests.</p>
 */
final class PairTest {

	@Test
	void testNull() {
		final var pair1 = new Pair<>(1, null);
		final var pair2 = new Pair<>(null, 2);
		final var pair3 = new Pair<>(null, null);
		assertThat(pair1.getFirst()).isNotNull();
		assertThat(pair1.getSecond()).isNull();
		assertThat(pair2.getFirst()).isNull();
		assertThat(pair2.getSecond()).isNotNull();
		assertThat(pair3.getFirst()).isNull();
		assertThat(pair3.getSecond()).isNull();
		assertThat(pair1).isEqualTo(Pair.of(1, null));
		assertThat(pair2).isEqualTo(Pair.of(null, 2));
		assertThat(pair3).isEqualTo(Pair.of(null, null));
		assertThat(pair1).isNotEqualTo(pair2);
		assertThat(pair2).isNotEqualTo(pair3);
		assertThat(pair3).isNotEqualTo(pair1);
	}

	@Test
	void testSame() {
		final var pair = new Pair<>(1, 2);
		assertThat(pair.getFirst()).isEqualTo(1);
		assertThat(pair.getSecond()).isEqualTo(2);
		assertThat(pair).isEqualTo(pair);
		assertThat(pair).isEqualTo(Pair.of(1, 2));
		assertThat(pair.hashCode()).isEqualTo(Pair.of(1, 2).hashCode());
		assertThat(pair.toString()).isEqualTo(Pair.of(1, 2).toString());
	}

	@Test
	void testNotSame() {
		final var pair = new Pair<>(1, 2);
		assertThat(pair.getFirst()).isNotEqualTo(2);
		assertThat(pair.getSecond()).isNotEqualTo(1);
		assertThat(pair).isNotEqualTo(null);
		assertThat(pair).isNotEqualTo(Triple.of(1, 2, 3));
		assertThat(pair).isNotEqualTo(Pair.of(1, 3));
		assertThat(pair.hashCode()).isNotEqualTo(Pair.of(1, 3).hashCode());
		assertThat(pair.toString()).isNotEqualTo(Pair.of(1, 3).toString());
	}

	@Test
	void testToMutableEntry() {
		final var pair = new Pair<>(1, 2);
		final var entry = pair.toMutableEntry();
		assertThat(entry.getKey()).isEqualTo(pair.getFirst());
		assertThat(entry.getValue()).isEqualTo(pair.getSecond());
		entry.setValue(3);
		assertThat(entry.getKey()).isEqualTo(pair.getFirst());
		assertThat(entry.getValue()).isNotEqualTo(pair.getSecond());
	}

	@Test
	void testToImmutableEntry() {
		final var pair = new Pair<>(1, 2);
		final var entry = pair.toImmutableEntry();
		assertThat(entry.getKey()).isEqualTo(pair.getFirst());
		assertThat(entry.getValue()).isEqualTo(pair.getSecond());
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> entry.setValue(3));
		assertThat(entry.getKey()).isEqualTo(pair.getFirst());
		assertThat(entry.getValue()).isEqualTo(pair.getSecond());
	}
}