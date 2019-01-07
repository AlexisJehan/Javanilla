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

import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;

/**
 * <p>A {@code Single} is an immutable tuple that is composed of an unique element.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <U> the type of the unique element
 * @since 1.1.0
 */
public final class Single<U> {

	/**
	 * <p>Unique element.</p>
	 * @since 1.1.0
	 */
	private final U unique;

	/**
	 * <p>Standard constructor.</p>
	 * @param unique the unique element or {@code null}
	 * @since 1.1.0
	 */
	public Single(final U unique) {
		this.unique = unique;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Single)) {
			return false;
		}
		final var other = (Single) object;
		return Equals.equals(unique, other.unique);
	}

	@Override
	public int hashCode() {
		return HashCode.hashCode(unique);
	}

	@Override
	public String toString() {
		return "[" + unique + "]";
	}

	/**
	 * <p>Get the unique element of the {@code Single}.</p>
	 * @return the unique element
	 * @since 1.1.0
	 */
	public U getUnique() {
		return unique;
	}

	/**
	 * <p>Vanilla constructor.</p>
	 * @param unique the unique element or {@code null}
	 * @param <U> the type of the unique element
	 * @return the constructed {@code Single}
	 * @since 1.1.0
	 */
	public static <U> Single<U> of(final U unique) {
		return new Single<>(unique);
	}
}