/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
 * A {@link SerializableTriple} is an immutable tuple that is composed of three {@link Serializable} elements.
 *
 * <p><b>Note</b>: This class is {@link Serializable}.</p>
 *
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <F> the type of the first serializable element
 * @param <S> the type of the second serializable element
 * @param <T> the type of the third serializable element
 * @see Triple
 * @since 1.8.0
 */
public final class SerializableTriple<F extends Serializable, S extends Serializable, T extends Serializable> implements Serializable {

	/**
	 * Serial version unique ID.
	 * @since 1.8.0
	 */
	private static final long serialVersionUID = -5456025928605051531L;

	/**
	 * First {@link Serializable} element.
	 * @since 1.8.0
	 */
	private final F first;

	/**
	 * Second {@link Serializable} element.
	 * @since 1.8.0
	 */
	private final S second;

	/**
	 * Third {@link Serializable} element.
	 * @since 1.8.0
	 */
	private final T third;

	/**
	 * Standard constructor.
	 * @param first the first {@link Serializable} element or {@code null}
	 * @param second the second {@link Serializable} element or {@code null}
	 * @param third the third {@link Serializable} element or {@code null}
	 * @since 1.8.0
	 */
	public SerializableTriple(final F first, final S second, final T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof SerializableTriple)) {
			return false;
		}
		final var other = (SerializableTriple<?, ?, ?>) object;
		return Equals.equals(first, other.first)
				&& Equals.equals(second, other.second)
				&& Equals.equals(third, other.third);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.of(
				HashCode.hashCode(first),
				HashCode.hashCode(second),
				HashCode.hashCode(third)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "[" + first + ", " + second + ", " + third + "]";
	}

	/**
	 * Converts the current {@code SerializableTriple} to a {@link Triple}.
	 * @return the converted {@link Triple}
	 * @since 1.8.0
	 */
	public Triple<F, S, T> toTriple() {
		return new Triple<>(first, second, third);
	}

	/**
	 * Get the first {@link Serializable} element of the {@code SerializableTriple}.
	 * @return the first {@link Serializable} element
	 * @since 1.8.0
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * Get the second {@link Serializable} element of the {@code SerializableTriple}.
	 * @return the second {@link Serializable} element
	 * @since 1.8.0
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * Get the third {@link Serializable} element of the {@code SerializableTriple}.
	 * @return the third {@link Serializable} element
	 * @since 1.8.0
	 */
	public T getThird() {
		return third;
	}

	/**
	 * Handy constructor.
	 * @param first the first {@link Serializable} element or {@code null}
	 * @param second the second {@link Serializable} element or {@code null}
	 * @param third the third {@link Serializable} element or {@code null}
	 * @param <F> the type of the first serializable element
	 * @param <S> the type of the second serializable element
	 * @param <T> the type of the third serializable element
	 * @return the constructed {@code SerializableTriple}
	 * @since 1.8.0
	 */
	public static <F extends Serializable, S extends Serializable, T extends Serializable> SerializableTriple<F, S, T> of(final F first, final S second, final T third) {
		return new SerializableTriple<>(first, second, third);
	}
}