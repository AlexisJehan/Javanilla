/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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
package com.github.alexisjehan.javanilla.misc.tuple;

import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;

import java.io.Serializable;

/**
 * <p>A {@link SerializablePair} is an immutable tuple that is composed of two {@link Serializable} elements.</p>
 * <p><b>Note</b>: This class is serializable.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <F> the type of the first serializable element
 * @param <S> the type of the second serializable element
 * @see Pair
 * @since 1.8.0
 */
public final class SerializablePair<F extends Serializable, S extends Serializable> implements Serializable {

	/**
	 * <p>Serial version unique ID.</p>
	 * @since 1.8.0
	 */
	private static final long serialVersionUID = 4737117460762935391L;

	/**
	 * <p>First {@link Serializable} element.</p>
	 * @since 1.8.0
	 */
	private final F first;

	/**
	 * <p>Second {@link Serializable} element.</p>
	 * @since 1.8.0
	 */
	private final S second;

	/**
	 * <p>Standard constructor.</p>
	 * @param first the first {@link Serializable} element or {@code null}
	 * @param second the second {@link Serializable} element or {@code null}
	 * @since 1.8.0
	 */
	public SerializablePair(final F first, final S second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof SerializablePair)) {
			return false;
		}
		final var other = (SerializablePair<?, ?>) object;
		return Equals.equals(first, other.first)
				&& Equals.equals(second, other.second);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.of(
				HashCode.hashCode(first),
				HashCode.hashCode(second)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "[" + first + ", " + second + "]";
	}

	/**
	 * <p>Converts the current {@link SerializablePair} to a {@link Pair}.</p>
	 * @return the converted {@link Pair}
	 * @since 1.8.0
	 */
	public Pair<F, S> toPair() {
		return new Pair<>(first, second);
	}

	/**
	 * <p>Get the first {@link Serializable} element of the {@link SerializablePair}.</p>
	 * @return the first {@link Serializable} element
	 * @since 1.8.0
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * <p>Get the second {@link Serializable} element of the {@link SerializablePair}.</p>
	 * @return the second {@link Serializable} element
	 * @since 1.8.0
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * <p>Handy constructor.</p>
	 * @param first the first {@link Serializable} element or {@code null}
	 * @param second the second {@link Serializable} element or {@code null}
	 * @param <F> the type of the first serializable element
	 * @param <S> the type of the second serializable element
	 * @return the constructed {@link SerializablePair}
	 * @since 1.8.0
	 */
	public static <F extends Serializable, S extends Serializable> SerializablePair<F, S> of(final F first, final S second) {
		return new SerializablePair<>(first, second);
	}
}