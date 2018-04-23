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
package com.github.javanilla.misc.tuples;

import java.util.Objects;

/**
 * <p>A {@code Triple} is an immutable tuple that is composed of three objects.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)} and {@link #hashCode()} methods.</p>
 * @param <F> the type of the first object
 * @param <S> the type of the second object
 * @param <T> the type of the third object
 * @since 1.0
 */
public final class Triple<F, S, T> {

	/**
	 * <p>First object.</p>
	 * @since 1.0
	 */
	private final F first;

	/**
	 * <p>Second object.</p>
	 * @since 1.0
	 */
	private final S second;

	/**
	 * <p>Third object.</p>
	 * @since 1.0
	 */
	private final T third;

	/**
	 * <p>Standard constructor.</p>
	 * @param first the first object or {@code null}
	 * @param second the second object or {@code null}
	 * @param third the third object or {@code null}
	 * @since 1.0
	 */
	public Triple(final F first, final S second, final T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	/**
	 * <p>Get the first object of the {@code Triple}.</p>
	 * @return the first object
	 * @since 1.0
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * <p>Get the second object of the {@code Triple}.</p>
	 * @return the second object
	 * @since 1.0
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * <p>Get the third object of the {@code Triple}.</p>
	 * @return the third object
	 * @since 1.0
	 */
	public T getThird() {
		return third;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Triple)) {
			return false;
		}
		final var other = (Triple) object;
		return Objects.equals(first, other.first)
				&& Objects.equals(second, other.second)
				&& Objects.equals(third, other.third);
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second, third);
	}

	@Override
	public String toString() {
		return "[" + first + ", " + second + ", " + third + "]";
	}

	/**
	 * <p>Vanilla constructor.</p>
	 * @param first the first object or {@code null}
	 * @param second the second object or {@code null}
	 * @param third the third object or {@code null}
	 * @param <F> the type of the first object
	 * @param <S> the type of the second object
	 * @param <T> the type of the third object
	 * @return the constructed {@code Triple}
	 * @since 1.0
	 */
	public static <F, S, T> Triple<F, S, T> of(final F first, final S second, final T third) {
		return new Triple<>(first, second, third);
	}
}