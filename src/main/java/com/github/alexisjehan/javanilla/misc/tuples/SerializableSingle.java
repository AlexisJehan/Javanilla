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

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>A {@code SerializableSingle} is an immutable tuple that is composed of an unique {@link Serializable} object.</p>
 * <p><b>Note</b>: This class is serializable.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)} and {@link #hashCode()} methods.</p>
 * @param <U> the type of the unique object
 * @see Single
 * @since 1.1.0
 */
public final class SerializableSingle<U extends Serializable> implements Serializable {

	/**
	 * <p>Serial version unique ID.</p>
	 * @since 1.1.0
	 */
	private static final long serialVersionUID = 4040327707639074966L;

	/**
	 * <p>Unique object.</p>
	 * @since 1.1.0
	 */
	private final U unique;

	/**
	 * <p>Standard constructor.</p>
	 * @param unique the unique object or {@code null}
	 * @since 1.1.0
	 */
	public SerializableSingle(final U unique) {
		this.unique = unique;
	}

	/**
	 * <p>Get the unique object of the {@code SerializableSingle}.</p>
	 * @return the unique object
	 * @since 1.1.0
	 */
	public U getUnique() {
		return unique;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof SerializableSingle)) {
			return false;
		}
		final var other = (SerializableSingle) object;
		return Objects.equals(unique, other.unique);
	}

	@Override
	public int hashCode() {
		return Objects.hash(unique);
	}

	@Override
	public String toString() {
		return "[" + unique + "]";
	}

	/**
	 * <p>Converts the current {@code SerializableSingle} to a {@code Single}.</p>
	 * @return the converted {@code Single}
	 * @since 1.1.0
	 */
	public Single<U> toSingle() {
		return new Single<>(unique);
	}

	/**
	 * <p>Vanilla constructor.</p>
	 * @param unique the unique object or {@code null}
	 * @param <U> the type of the unique object
	 * @return the constructed {@code SerializableSingle}
	 * @since 1.1.0
	 */
	public static <U extends Serializable> SerializableSingle<U> of(final U unique) {
		return new SerializableSingle<>(unique);
	}
}