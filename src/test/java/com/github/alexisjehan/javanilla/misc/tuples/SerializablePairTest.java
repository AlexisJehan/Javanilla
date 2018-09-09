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

import com.github.alexisjehan.javanilla.io.Serializables;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link SerializablePair} unit tests.</p>
 */
final class SerializablePairTest {

	@Test
	void testNull() {
		final var serializablePair1 = new SerializablePair<>(1, null);
		final var serializablePair2 = new SerializablePair<>(null, 2);
		final var serializablePair3 = new SerializablePair<>(null, null);
		assertThat(serializablePair1.getFirst()).isNotNull();
		assertThat(serializablePair1.getSecond()).isNull();
		assertThat(serializablePair2.getFirst()).isNull();
		assertThat(serializablePair2.getSecond()).isNotNull();
		assertThat(serializablePair3.getFirst()).isNull();
		assertThat(serializablePair3.getSecond()).isNull();
		assertThat(serializablePair1).isEqualTo(SerializablePair.of(1, null));
		assertThat(serializablePair2).isEqualTo(SerializablePair.of(null, 2));
		assertThat(serializablePair3).isEqualTo(SerializablePair.of(null, null));
		assertThat(serializablePair1).isNotEqualTo(serializablePair2);
		assertThat(serializablePair2).isNotEqualTo(serializablePair3);
		assertThat(serializablePair3).isNotEqualTo(serializablePair1);
	}

	@Test
	void testSame() {
		final var serializablePair = new SerializablePair<>(1, 2);
		assertThat(serializablePair.getFirst()).isEqualTo(1);
		assertThat(serializablePair.getSecond()).isEqualTo(2);
		assertThat(serializablePair).isEqualTo(serializablePair);
		assertThat(serializablePair).isEqualTo(SerializablePair.of(1, 2));
		assertThat(serializablePair.hashCode()).isEqualTo(SerializablePair.of(1, 2).hashCode());
		assertThat(serializablePair.toString()).isEqualTo(SerializablePair.of(1, 2).toString());
	}

	@Test
	void testNotSame() {
		final var serializablePair = new SerializablePair<>(1, 2);
		assertThat(serializablePair.getFirst()).isNotEqualTo(2);
		assertThat(serializablePair.getSecond()).isNotEqualTo(1);
		assertThat(serializablePair).isNotEqualTo(null);
		assertThat(serializablePair).isNotEqualTo(SerializableSingle.of(1));
		assertThat(serializablePair).isNotEqualTo(SerializablePair.of(1, 3));
		assertThat(serializablePair).isNotEqualTo(SerializableTriple.of(1, 2, 3));
		assertThat(serializablePair.hashCode()).isNotEqualTo(SerializablePair.of(1, 3).hashCode());
		assertThat(serializablePair.toString()).isNotEqualTo(SerializablePair.of(1, 3).toString());
	}

	@Test
	void testToPair() {
		final var serializablePair = new SerializablePair<>(1, 2);
		final var pair = serializablePair.toPair();
		assertThat(pair.getFirst()).isEqualTo(serializablePair.getFirst());
		assertThat(pair.getSecond()).isEqualTo(serializablePair.getSecond());
	}

	@Test
	void testSerializable() {
		final var serializablePair = new SerializablePair<>(1, 2);
		assertThat(Serializables.<SerializablePair>deserialize(Serializables.serialize(serializablePair))).isEqualTo(serializablePair);
	}
}