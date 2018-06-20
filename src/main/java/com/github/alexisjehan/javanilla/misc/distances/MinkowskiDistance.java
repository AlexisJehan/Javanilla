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
package com.github.alexisjehan.javanilla.misc.distances;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>The Minkowski {@link Distance} implementation.</p>
 * <p><b>Note</b>: This class is serializable.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)} and {@link #hashCode()} methods.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Minkowski_distance">https://en.wikipedia.org/wiki/Minkowski_distance</a>
 * @since 1.0.0
 */
public final class MinkowskiDistance implements Distance, Serializable {

	/**
	 * <p>Serial version unique ID.</p>
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -4275713249812379264L;

	/**
	 * <p>The order.</p>
	 * @since 1.0.0
	 */
	private final int order;

	/**
	 * <p>Constructor with a custom order.</p>
	 * @param order the order
	 * @throws IllegalArgumentException if the order is lower than {@code 1}
	 * @since 1.0.0
	 */
	public MinkowskiDistance(final int order) {
		if (1 > order) {
			throw new IllegalArgumentException("Invalid order: " + order + " (greater than or equal to 1 expected)");
		}
		this.order = order;
	}

	@Override
	public double calculate(final double[] vector1, final double[] vector2) {
		if (null == vector1) {
			throw new NullPointerException("Invalid first vector (not null expected)");
		}
		if (null == vector2) {
			throw new NullPointerException("Invalid second vector (not null expected)");
		}
		if (vector1.length != vector2.length) {
			throw new IllegalArgumentException("Distinct vectors length: " + vector1.length + " and " + vector2.length + " (same expected)");
		}
		if (1 > vector1.length) {
			throw new IllegalArgumentException("Invalid vectors dimension: " + vector1.length + " (greater than or equal to 1 expected)");
		}
		var s = 0.0d;
		for (var i = 0; i < vector1.length; ++i) {
			s += Math.pow(Math.abs(vector1[i] - vector2[i]), order);
		}
		return Math.pow(s, 1d / order);
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MinkowskiDistance)) {
			return false;
		}
		final var other = (MinkowskiDistance) object;
		return Objects.equals(order, other.order);
	}

	@Override
	public int hashCode() {
		return Objects.hash(order);
	}

	/**
	 * <p>Get the order.</p>
	 * @return the order
	 * @since 1.0.0
	 */
	public int getOrder() {
		return order;
	}
}