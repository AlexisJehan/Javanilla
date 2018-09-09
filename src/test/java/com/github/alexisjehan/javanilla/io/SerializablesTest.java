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
package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.tuples.Pair;
import com.github.alexisjehan.javanilla.misc.tuples.SerializablePair;
import com.github.alexisjehan.javanilla.misc.tuples.SerializableSingle;
import com.github.alexisjehan.javanilla.misc.tuples.Single;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Serializables} unit tests.</p>
 */
final class SerializablesTest {

	@Test
	void testSerializeDeserialize() {
		assertThat(Serializables.<SerializablePair>deserialize(Serializables.serialize(null))).isNull();
		final var foo = SerializablePair.of("foo", 1);
		final var deserializedFoo = Serializables.deserialize(Serializables.serialize(foo));
		assertThat(deserializedFoo).isNotSameAs(foo);
		assertThat(deserializedFoo).isEqualTo(foo);
	}

	@Test
	void testSerializeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Serializables.serialize(SerializablePair.of("foo", 1), null));
	}

	@Test
	void testDeserializeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Serializables.deserialize((byte[]) null));
		assertThatNullPointerException().isThrownBy(() -> Serializables.deserialize((InputStream) null));
	}

	@Test
	void testSerializeDeserializeInvalid() {
		// Serializable containing a non-serializable attribute
		assertThatExceptionOfType(SerializationException.class)
				.isThrownBy(() -> Serializables.serialize(Pair.of(Single.of("foo"), Single.of(1)).toImmutableEntry()))
				.withCauseInstanceOf(NotSerializableException.class);

		// Different type used for serialization and deserialization
		assertThatExceptionOfType(ClassCastException.class)
				.isThrownBy(() -> Serializables.<SerializableSingle>deserialize(Serializables.serialize(SerializablePair.of("foo", 1))));

		// Corrupted
		final var serialized = Serializables.serialize(SerializablePair.of("foo", 1));
		serialized[0] = (byte) 1;
		assertThatExceptionOfType(SerializationException.class)
				.isThrownBy(() -> Serializables.deserialize(serialized))
				.withCauseInstanceOf(StreamCorruptedException.class);
	}
}