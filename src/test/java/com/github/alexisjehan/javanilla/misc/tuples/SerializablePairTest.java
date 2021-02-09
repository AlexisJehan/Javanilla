/*
 * MIT License
 *
 * Copyright (c) 2018-2021 Alexis Jehan
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

import com.github.alexisjehan.javanilla.io.Serializables;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link SerializablePair} unit tests.</p>
 */
final class SerializablePairTest {

	private static final Integer FIRST = 1;
	private static final Integer SECOND = null;

	private final SerializablePair<Integer, Integer> serializablePair = new SerializablePair<>(FIRST, SECOND);

	@Test
	void testEqualsHashCodeToString() {
		assertThat(serializablePair.equals(serializablePair)).isTrue();
		assertThat(serializablePair).isNotEqualTo(new Object());
		assertThat(SerializablePair.of(FIRST, SECOND)).satisfies(otherSerializablePair -> {
			assertThat(serializablePair).isNotSameAs(otherSerializablePair);
			assertThat(serializablePair).isEqualTo(otherSerializablePair);
			assertThat(serializablePair).hasSameHashCodeAs(otherSerializablePair);
			assertThat(serializablePair).hasToString(otherSerializablePair.toString());
		});
		assertThat(SerializablePair.of(null, SECOND)).satisfies(otherSerializablePair -> {
			assertThat(serializablePair).isNotSameAs(otherSerializablePair);
			assertThat(serializablePair).isNotEqualTo(otherSerializablePair);
			assertThat(serializablePair).doesNotHaveSameHashCodeAs(otherSerializablePair);
			assertThat(serializablePair).doesNotHaveToString(otherSerializablePair.toString());
		});
		assertThat(SerializablePair.of(FIRST, 2)).satisfies(otherSerializablePair -> {
			assertThat(serializablePair).isNotSameAs(otherSerializablePair);
			assertThat(serializablePair).isNotEqualTo(otherSerializablePair);
			assertThat(serializablePair).doesNotHaveSameHashCodeAs(otherSerializablePair);
			assertThat(serializablePair).doesNotHaveToString(otherSerializablePair.toString());
		});
	}

	@Test
	void testToPair() {
		final var pair = serializablePair.toPair();
		assertThat(serializablePair.getFirst()).isEqualTo(pair.getFirst());
		assertThat(serializablePair.getSecond()).isEqualTo(pair.getSecond());
	}

	@Test
	void testGetters() {
		assertThat(serializablePair.getFirst()).isEqualTo(FIRST);
		assertThat(serializablePair.getSecond()).isEqualTo(SECOND);
	}

	@Test
	void testSerializable() {
		assertThat(Serializables.<SerializablePair<Integer, Integer>>deserialize(Serializables.serialize(serializablePair))).isEqualTo(serializablePair);
	}
}