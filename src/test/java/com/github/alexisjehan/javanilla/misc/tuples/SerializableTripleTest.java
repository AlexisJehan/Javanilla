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
 * <p>{@link SerializableTriple} unit tests.</p>
 */
final class SerializableTripleTest {

	private static final Integer FIRST = 1;
	private static final Integer SECOND = 2;
	private static final Integer THIRD = null;

	private final SerializableTriple<Integer, Integer, Integer> serializableTriple = new SerializableTriple<>(FIRST, SECOND, THIRD);

	@Test
	void testEqualsHashCodeToString() {
		assertThat(serializableTriple.equals(serializableTriple)).isTrue();
		assertThat(serializableTriple).isNotEqualTo(new Object());
		assertThat(SerializableTriple.of(FIRST, SECOND, THIRD)).satisfies(otherSerializableTriple -> {
			assertThat(serializableTriple).isNotSameAs(otherSerializableTriple);
			assertThat(serializableTriple).isEqualTo(otherSerializableTriple);
			assertThat(serializableTriple).hasSameHashCodeAs(otherSerializableTriple);
			assertThat(serializableTriple).hasToString(otherSerializableTriple.toString());
		});
		assertThat(SerializableTriple.of(null, SECOND, THIRD)).satisfies(otherSerializableTriple -> {
			assertThat(serializableTriple).isNotSameAs(otherSerializableTriple);
			assertThat(serializableTriple).isNotEqualTo(otherSerializableTriple);
			assertThat(serializableTriple).doesNotHaveSameHashCodeAs(otherSerializableTriple);
			assertThat(serializableTriple).doesNotHaveToString(otherSerializableTriple.toString());
		});
		assertThat(SerializableTriple.of(FIRST, null, THIRD)).satisfies(otherSerializableTriple -> {
			assertThat(serializableTriple).isNotSameAs(otherSerializableTriple);
			assertThat(serializableTriple).isNotEqualTo(otherSerializableTriple);
			assertThat(serializableTriple).doesNotHaveSameHashCodeAs(otherSerializableTriple);
			assertThat(serializableTriple).doesNotHaveToString(otherSerializableTriple.toString());
		});
		assertThat(SerializableTriple.of(FIRST, SECOND, 3)).satisfies(otherSerializableTriple -> {
			assertThat(serializableTriple).isNotSameAs(otherSerializableTriple);
			assertThat(serializableTriple).isNotEqualTo(otherSerializableTriple);
			assertThat(serializableTriple).doesNotHaveSameHashCodeAs(otherSerializableTriple);
			assertThat(serializableTriple).doesNotHaveToString(otherSerializableTriple.toString());
		});
	}

	@Test
	void testToTriple() {
		final var triple = serializableTriple.toTriple();
		assertThat(serializableTriple.getFirst()).isEqualTo(triple.getFirst());
		assertThat(serializableTriple.getSecond()).isEqualTo(triple.getSecond());
		assertThat(serializableTriple.getThird()).isEqualTo(triple.getThird());
	}

	@Test
	void testGetters() {
		assertThat(serializableTriple.getFirst()).isEqualTo(FIRST);
		assertThat(serializableTriple.getSecond()).isEqualTo(SECOND);
		assertThat(serializableTriple.getThird()).isEqualTo(THIRD);
	}

	@Test
	void testSerializable() {
		assertThat(Serializables.<SerializableTriple<Integer, Integer, Integer>>deserialize(Serializables.serialize(serializableTriple))).isEqualTo(serializableTriple);
	}
}