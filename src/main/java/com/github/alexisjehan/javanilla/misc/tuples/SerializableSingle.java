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
package com.github.alexisjehan.javanilla.misc.tuples;

import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;

import java.io.Serializable;

/**
 * A {@link SerializableSingle} is an immutable tuple that is composed of a unique {@link Serializable} element.
 *
 * <p><b>Note</b>: This class is {@link Serializable}.</p>
 *
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <U> the type of the unique element
 * @see Single
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.misc.tuple.SerializableSingle} instead
 * @since 1.1.0
 */
@Deprecated(since = "1.8.0")
public final class SerializableSingle<U extends Serializable> implements Serializable {

	/**
	 * Serial version unique ID.
	 * @since 1.1.0
	 */
	private static final long serialVersionUID = 1158249110499712760L;

	/**
	 * Unique {@link Serializable} element.
	 * @since 1.1.0
	 */
	private final U unique;

	/**
	 * Standard constructor.
	 * @param unique the unique {@link Serializable} element or {@code null}
	 * @since 1.1.0
	 */
	public SerializableSingle(final U unique) {
		this.unique = unique;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof SerializableSingle)) {
			return false;
		}
		final var other = (SerializableSingle<?>) object;
		return Equals.equals(unique, other.unique);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.hashCode(unique);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "[" + unique + "]";
	}

	/**
	 * Converts the current {@code SerializableSingle} to a {@link Single}.
	 * @return the converted {@link Single}
	 * @since 1.1.0
	 */
	public Single<U> toSingle() {
		return new Single<>(unique);
	}

	/**
	 * Get the unique {@link Serializable} element of the {@code SerializableSingle}.
	 * @return the unique {@link Serializable} element
	 * @since 1.1.0
	 */
	public U getUnique() {
		return unique;
	}

	/**
	 * Handy constructor.
	 * @param unique the unique {@link Serializable} element or {@code null}
	 * @param <U> the type of the unique serializable element
	 * @return the constructed {@code SerializableSingle}
	 * @since 1.1.0
	 */
	public static <U extends Serializable> SerializableSingle<U> of(final U unique) {
		return new SerializableSingle<>(unique);
	}
}