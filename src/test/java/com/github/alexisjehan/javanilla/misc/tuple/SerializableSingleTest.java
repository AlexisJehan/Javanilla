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

import com.github.alexisjehan.javanilla.io.Serializables;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class SerializableSingleTest {

	private static final Integer UNIQUE = 1;

	private final SerializableSingle<Integer> serializableSingle = new SerializableSingle<>(UNIQUE);

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(serializableSingle.equals(serializableSingle)).isTrue();
		assertThat(serializableSingle).isNotEqualTo(new Object());
		assertThat(SerializableSingle.of(UNIQUE)).satisfies(otherSerializableSingle -> {
			assertThat(otherSerializableSingle).isNotSameAs(serializableSingle);
			assertThat(otherSerializableSingle).isEqualTo(serializableSingle);
			assertThat(otherSerializableSingle).hasSameHashCodeAs(serializableSingle);
			assertThat(otherSerializableSingle).hasToString(serializableSingle.toString());
		});
		assertThat(SerializableSingle.of(null)).satisfies(otherSerializableSingle -> {
			assertThat(otherSerializableSingle).isNotSameAs(serializableSingle);
			assertThat(otherSerializableSingle).isNotEqualTo(serializableSingle);
			assertThat(otherSerializableSingle).doesNotHaveSameHashCodeAs(serializableSingle);
			assertThat(otherSerializableSingle).doesNotHaveToString(serializableSingle.toString());
		});
	}

	@Test
	void testToSingle() {
		final var single = serializableSingle.toSingle();
		assertThat(serializableSingle.getUnique()).isEqualTo(single.getUnique());
	}

	@Test
	void testGetUnique() {
		assertThat(serializableSingle.getUnique()).isEqualTo(UNIQUE);
	}

	@Test
	void testSerializable() {
		assertThat(Serializables.<SerializableSingle<Integer>>deserialize(Serializables.serialize(serializableSingle))).isEqualTo(serializableSingle);
	}
}