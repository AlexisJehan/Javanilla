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
package com.github.alexisjehan.javanilla.misc.tuple;

import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;

/**
 * A {@link Pair} is an immutable tuple that is composed of two elements.
 *
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <F> the type of the first element
 * @param <S> the type of the second element
 * @since 1.8.0
 */
public final class Pair<F, S> {

	/**
	 * First element.
	 * @since 1.8.0
	 */
	private final F first;

	/**
	 * Second element.
	 * @since 1.8.0
	 */
	private final S second;

	/**
	 * Standard constructor.
	 * @param first the first element or {@code null}
	 * @param second the second element or {@code null}
	 * @since 1.8.0
	 */
	public Pair(final F first, final S second) {
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
		if (!(object instanceof Pair)) {
			return false;
		}
		final var other = (Pair<?, ?>) object;
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
	 * Get the first element of the {@code Pair}.
	 * @return the first element
	 * @since 1.8.0
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * Get the second element of the {@code Pair}.
	 * @return the second element
	 * @since 1.8.0
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * Handy constructor.
	 * @param first the first element or {@code null}
	 * @param second the second element or {@code null}
	 * @param <F> the type of the first element
	 * @param <S> the type of the second element
	 * @return the constructed {@code Pair}
	 * @since 1.8.0
	 */
	public static <F, S> Pair<F, S> of(final F first, final S second) {
		return new Pair<>(first, second);
	}
}