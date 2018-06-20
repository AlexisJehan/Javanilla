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

import com.github.alexisjehan.javanilla.io.bytes.InputStreams;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Serializables} unit tests.</p>
 */
final class SerializablesTest {

	private static class Foo implements Serializable {

		private static final long serialVersionUID = -4162937710652709737L;

		private final String name;
		private final int value;

		Foo(final String name, final int value) {
			this.name = name;
			this.value = value;
		}
	}

	private static class Bar implements Serializable {

		private static final long serialVersionUID = 2341804969668383253L;

		@SuppressWarnings("unused")
		private final Object object;

		Bar(final Object object) {
			this.object = object;
		}
	}

	@Test
	void testSerializeDeserializeSimple() {
		final var foo = new Foo("foo", 1);
		final var otherFoo = Serializables.<Foo>deserialize(Serializables.serialize(foo));

		// Not the same object instance
		assertThat(otherFoo).isNotEqualTo(foo);

		// But same type and attributes
		assertThat(otherFoo.getClass()).isEqualTo(foo.getClass());
		assertThat(otherFoo.name).isEqualTo(foo.name);
		assertThat(otherFoo.value).isEqualTo(foo.value);
	}

	@Test
	void testSerializeDeserializeInvalidType() {
		assertThatExceptionOfType(ClassCastException.class).isThrownBy(() -> Serializables.<Bar>deserialize(Serializables.serialize(new Foo("foo", 1))));
	}

	@Test
	void testSerializeDeserializeNull() {
		final var bytes = Serializables.<Foo>serialize(null);

		// Not the good class type, but still working for a null value apparently
		assertThat(Serializables.<Bar>deserialize(bytes)).isNull();

		assertThat(Serializables.<Foo>deserialize(bytes)).isNull();
	}

	@Test
	void testSerializeNull() {
		assertThatNullPointerException().isThrownBy(() -> Serializables.serialize(new Foo("foo", 1), null));
	}

	@Test
	void testSerializeNotSerializable() {
		assertThatExceptionOfType(SerializationException.class)
				.isThrownBy(() -> Serializables.serialize(new Bar(InputStreams.EMPTY)))
				.withCauseInstanceOf(NotSerializableException.class);
	}

	@Test
	void testDeserializeNull() {
		assertThatNullPointerException().isThrownBy(() -> Serializables.deserialize((byte[]) null));
		assertThatNullPointerException().isThrownBy(() -> Serializables.deserialize((InputStream) null));
	}

	@Test
	void testDeserializeCorrupted() {
		final var bytes = Serializables.serialize(new Foo("foo", 1));
		bytes[0] = (byte) 1;
		assertThatExceptionOfType(SerializationException.class)
				.isThrownBy(() -> Serializables.deserialize(bytes))
				.withCauseInstanceOf(StreamCorruptedException.class);
	}
}