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

import java.io.*;

/**
 * <p>An utility class that provides {@link Serializable} tools.</p>
 * @since 1.0.0
 */
public final class Serializables {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Serializables() {
		// Not available
	}

	/**
	 * <p>Serialize the given {@code Serializable} to a {@code byte} array.</p>
	 * @param serializable the {@code Serializable} or {@code null}
	 * @return a {@code byte} array that contains the serialized data
	 * @throws SerializationException might occurs with serialization operations
	 * @since 1.0.0
	 */
	public static byte[] serialize(final Serializable serializable) {
		final var byteArrayOutputStream = new ByteArrayOutputStream();
		serialize(serializable, byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * <p>Serialize the given {@code Serializable} to an {@code OutputStream}.</p>
	 * @param serializable the {@code Serializable} object or {@code null}
	 * @param outputStream the {@code OutputStream} to write into
	 * @throws NullPointerException if the {@code OutputStream} is {@code null}
	 * @throws SerializationException might occurs with serialization or I/O operations
	 * @since 1.0.0
	 */
	public static void serialize(final Serializable serializable, final OutputStream outputStream) {
		if (null == outputStream) {
			throw new NullPointerException("Invalid OutputStream (not null expected)");
		}
		try (final var objectOutputStream = new ObjectOutputStream(outputStream)) {
			objectOutputStream.writeObject(serializable);
		} catch (final Exception e) {
			throw new SerializationException(e);
		}
	}

	/**
	 * <p>Deserialize from the given {@code byte} array to a new {@code Serializable}.</p>
	 * @param bytes the {@code byte} array that contains the serialized data
	 * @param <S> the type of the {@code Serializable}
	 * @return a new {@code Serializable} from the serialized data
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws SerializationException might occurs with serialization operations
	 * @throws ClassCastException if the type is different of the serialized data type
	 * @since 1.0.0
	 */
	public static <S extends Serializable> S deserialize(final byte[] bytes) {
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		return deserialize(new ByteArrayInputStream(bytes));
	}

	/**
	 * <p>Deserialize from the given {@code InputStream} to a new {@code Serializable}.</p>
	 * @param inputStream the {@code InputStream} to read into
	 * @param <S> the type of the {@code Serializable}
	 * @return a new {@code Serializable} from the serialized data
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @throws SerializationException might occurs with serialization or I/O operations
	 * @throws ClassCastException if the type is different of the serialized data type
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <S extends Serializable> S deserialize(final InputStream inputStream) {
		if (null == inputStream) {
			throw new NullPointerException("Invalid InputStream (not null expected)");
		}
		try (final var objectInputStream = new ObjectInputStream(inputStream)) {
			return (S) objectInputStream.readObject();
		} catch (final Exception e) {
			throw new SerializationException(e);
		}
	}
}