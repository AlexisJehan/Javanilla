/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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

import com.github.alexisjehan.javanilla.io.bytes.InputStreams;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

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
	 * <p>Serialize the given {@link Serializable} to a {@code byte} array.</p>
	 * @param serializable the {@link Serializable} or {@code null}
	 * @return a {@code byte} array that contains the serialized data
	 * @throws SerializationException might occurs with serialization operations
	 * @since 1.0.0
	 */
	public static byte[] serialize(final Serializable serializable) {
		final var byteArrayOutputStream = new ByteArrayOutputStream();
		serialize(byteArrayOutputStream, serializable);
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * <p>Serialize the given {@link Serializable} to an {@link OutputStream}.</p>
	 * @param outputStream the {@link OutputStream} to write into
	 * @param serializable the {@link Serializable} or {@code null}
	 * @throws NullPointerException if the {@link OutputStream} is {@code null}
	 * @throws SerializationException might occurs with serialization or I/O operations
	 * @since 1.0.0
	 */
	public static void serialize(final OutputStream outputStream, final Serializable serializable) {
		Ensure.notNull("outputStream", outputStream);
		try (final var objectOutputStream = new ObjectOutputStream(outputStream)) {
			objectOutputStream.writeObject(serializable);
		} catch (final Exception e) {
			throw new SerializationException(e);
		}
	}

	/**
	 * <p>Deserialize from the given {@code byte} array to a new {@link Serializable}.</p>
	 * @param bytes the {@code byte} array that contains the serialized data
	 * @param <S> the type of the {@link Serializable}
	 * @return a new {@link Serializable} from the serialized data
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws SerializationException might occurs with serialization operations
	 * @since 1.0.0
	 */
	public static <S extends Serializable> S deserialize(final byte[] bytes) {
		Ensure.notNull("bytes", bytes);
		return deserialize(InputStreams.of(bytes));
	}

	/**
	 * <p>Deserialize from the given {@link InputStream} to a new {@link Serializable}.</p>
	 * @param inputStream the {@link InputStream} to read into
	 * @param <S> the type of the {@link Serializable}
	 * @return a new {@link Serializable} from the serialized data
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @throws SerializationException might occurs with serialization or I/O operations
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <S extends Serializable> S deserialize(final InputStream inputStream) {
		Ensure.notNull("inputStream", inputStream);
		try (final var objectInputStream = new ObjectInputStream(inputStream)) {
			return (S) objectInputStream.readObject();
		} catch (final Exception e) {
			throw new SerializationException(e);
		}
	}
}