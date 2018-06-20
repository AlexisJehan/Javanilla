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
 * <p>{@link SerializableSingle} unit tests.</p>
 */
final class SerializableSingleTest {

	@Test
	void testNull() {
		final var serializableSingle1 = new SerializableSingle<>(1);
		final var serializableSingle2 = new SerializableSingle<>(2);
		final var serializableSingle3 = new SerializableSingle<>(null);
		assertThat(serializableSingle1.getUnique()).isNotNull();
		assertThat(serializableSingle2.getUnique()).isNotNull();
		assertThat(serializableSingle3.getUnique()).isNull();
		assertThat(serializableSingle1).isEqualTo(SerializableSingle.of(1));
		assertThat(serializableSingle2).isEqualTo(SerializableSingle.of(2));
		assertThat(serializableSingle3).isEqualTo(SerializableSingle.of(null));
		assertThat(serializableSingle1).isNotEqualTo(serializableSingle2);
		assertThat(serializableSingle2).isNotEqualTo(serializableSingle3);
		assertThat(serializableSingle3).isNotEqualTo(serializableSingle1);
	}

	@Test
	void testSame() {
		final var serializableSingle = new SerializableSingle<>(1);
		assertThat(serializableSingle.getUnique()).isEqualTo(1);
		assertThat(serializableSingle).isEqualTo(serializableSingle);
		assertThat(serializableSingle).isEqualTo(SerializableSingle.of(1));
		assertThat(serializableSingle.hashCode()).isEqualTo(SerializableSingle.of(1).hashCode());
		assertThat(serializableSingle.toString()).isEqualTo(SerializableSingle.of(1).toString());
	}

	@Test
	void testNotSame() {
		final var serializableSingle = new SerializableSingle<>(1);
		assertThat(serializableSingle.getUnique()).isNotEqualTo(2);
		assertThat(serializableSingle).isNotEqualTo(null);
		assertThat(serializableSingle).isNotEqualTo(SerializableSingle.of(2));
		assertThat(serializableSingle).isNotEqualTo(SerializablePair.of(1, 2));
		assertThat(serializableSingle).isNotEqualTo(SerializableTriple.of(1, 2, 3));
		assertThat(serializableSingle.hashCode()).isNotEqualTo(SerializableSingle.of(2).hashCode());
		assertThat(serializableSingle.toString()).isNotEqualTo(SerializableSingle.of(2).toString());
	}

	@Test
	void testToSingle() {
		final var serializableSingle = new SerializableSingle<>(1);
		final var single = serializableSingle.toSingle();
		assertThat(single.getUnique()).isEqualTo(serializableSingle.getUnique());
	}

	@Test
	void testSerializable() {
		final var serializableSingle = new SerializableSingle<>(1);
		assertThat(Serializables.<SerializableSingle>deserialize(Serializables.serialize(serializableSingle))).isEqualTo(serializableSingle);
	}
}