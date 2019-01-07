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

import com.github.alexisjehan.javanilla.io.Serializables;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link SerializableTriple} unit tests.</p>
 */
final class SerializableTripleTest {

	@Test
	void testNull() {
		final var serializableTriple1 = new SerializableTriple<>(1, 2, null);
		final var serializableTriple2 = new SerializableTriple<>(null, 2, 3);
		final var serializableTriple3 = new SerializableTriple<>(null, null, null);
		assertThat(serializableTriple1.getFirst()).isNotNull();
		assertThat(serializableTriple1.getSecond()).isNotNull();
		assertThat(serializableTriple1.getThird()).isNull();
		assertThat(serializableTriple2.getFirst()).isNull();
		assertThat(serializableTriple2.getSecond()).isNotNull();
		assertThat(serializableTriple2.getThird()).isNotNull();
		assertThat(serializableTriple3.getFirst()).isNull();
		assertThat(serializableTriple3.getSecond()).isNull();
		assertThat(serializableTriple3.getThird()).isNull();
		assertThat(serializableTriple1).isEqualTo(SerializableTriple.of(1, 2, null));
		assertThat(serializableTriple2).isEqualTo(SerializableTriple.of(null, 2, 3));
		assertThat(serializableTriple3).isEqualTo(SerializableTriple.of(null, null, null));
		assertThat(serializableTriple1).isNotEqualTo(serializableTriple2);
		assertThat(serializableTriple2).isNotEqualTo(serializableTriple3);
		assertThat(serializableTriple3).isNotEqualTo(serializableTriple1);
	}

	@Test
	void testSame() {
		final var serializableTriple = new SerializableTriple<>(1, 2, 3);
		assertThat(serializableTriple.getFirst()).isEqualTo(1);
		assertThat(serializableTriple.getSecond()).isEqualTo(2);
		assertThat(serializableTriple.getThird()).isEqualTo(3);
		assertThat(serializableTriple).isEqualTo(serializableTriple);
		assertThat(serializableTriple).isEqualTo(SerializableTriple.of(1, 2, 3));
		assertThat(serializableTriple.hashCode()).isEqualTo(SerializableTriple.of(1, 2, 3).hashCode());
		assertThat(serializableTriple.toString()).isEqualTo(SerializableTriple.of(1, 2, 3).toString());
	}

	@Test
	void testNotSame() {
		final var serializableTriple = new SerializableTriple<>(1, 2, 3);
		assertThat(serializableTriple.getFirst()).isNotEqualTo(2);
		assertThat(serializableTriple.getSecond()).isNotEqualTo(3);
		assertThat(serializableTriple.getThird()).isNotEqualTo(1);
		assertThat(serializableTriple).isNotEqualTo(null);
		assertThat(serializableTriple).isNotEqualTo(SerializableSingle.of(1));
		assertThat(serializableTriple).isNotEqualTo(SerializablePair.of(1, 2));
		assertThat(serializableTriple).isNotEqualTo(SerializableTriple.of(1, 2, 4));
		assertThat(serializableTriple.hashCode()).isNotEqualTo(SerializableTriple.of(1, 2, 4).hashCode());
		assertThat(serializableTriple.toString()).isNotEqualTo(SerializableTriple.of(1, 2, 4).toString());
	}

	@Test
	void testToTriple() {
		final var serializableTriple = new SerializableTriple<>(1, 2, 3);
		final var triple = serializableTriple.toTriple();
		assertThat(triple.getFirst()).isEqualTo(serializableTriple.getFirst());
		assertThat(triple.getSecond()).isEqualTo(serializableTriple.getSecond());
		assertThat(triple.getThird()).isEqualTo(serializableTriple.getThird());
	}

	@Test
	void testSerializable() {
		final var serializableTriple = new SerializableTriple<>(1, 2, 3);
		assertThat(Serializables.<SerializableTriple>deserialize(Serializables.serialize(serializableTriple))).isEqualTo(serializableTriple);
	}
}