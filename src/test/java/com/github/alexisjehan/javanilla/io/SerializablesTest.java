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

package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.tuples.SerializablePair;
import com.github.alexisjehan.javanilla.misc.tuples.SerializableSingle;
import com.github.alexisjehan.javanilla.misc.tuples.Single;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Serializables} unit tests.</p>
 */
final class SerializablesTest {

	private static final SerializablePair<String, Integer> FOO = SerializablePair.of("foo", 1);

	@Test
	void testSerializeDeserialize() {
		assertThat(Serializables.<SerializablePair<String, Integer>>deserialize(Serializables.serialize(FOO))).isEqualTo(FOO);
		assertThat(Serializables.<SerializablePair<String, Integer>>deserialize(Serializables.serialize(null))).isNull();

		// Serializable containing a non-serializable attribute
		assertThatExceptionOfType(SerializationException.class)
				.isThrownBy(() -> Serializables.serialize(new ArrayList<>(List.of(Single.of("bar")))))
				.withCauseInstanceOf(NotSerializableException.class);

		// Different type used for serialization and deserialization
		assertThatExceptionOfType(ClassCastException.class)
				.isThrownBy(() -> Serializables.<SerializableSingle<String>>deserialize(Serializables.serialize(FOO)).getUnique());

		// Corruption
		final var serialized = Serializables.serialize(FOO);
		serialized[0] = (byte) 1;
		assertThatExceptionOfType(SerializationException.class)
				.isThrownBy(() -> Serializables.deserialize(serialized))
				.withCauseInstanceOf(StreamCorruptedException.class);
	}

	@Test
	void testSerializeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Serializables.serialize(null, FOO));
	}

	@Test
	void testDeserializeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Serializables.deserialize((byte[]) null));
		assertThatNullPointerException().isThrownBy(() -> Serializables.deserialize((InputStream) null));
	}
}