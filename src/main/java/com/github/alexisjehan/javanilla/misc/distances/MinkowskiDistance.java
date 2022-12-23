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
package com.github.alexisjehan.javanilla.misc.distances;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;
import com.github.alexisjehan.javanilla.misc.quality.ToString;
import com.github.alexisjehan.javanilla.misc.tuples.Pair;

import java.io.Serializable;

/**
 * <p>The Minkowski {@link Distance} implementation.</p>
 * <p><b>Note</b>: This class is serializable.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Minkowski_distance">https://en.wikipedia.org/wiki/Minkowski_distance</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.misc.distance.MinkowskiDistance} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class MinkowskiDistance implements Distance, Serializable {

	/**
	 * <p>Serial version unique ID.</p>
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 5196415401667280648L;

	/**
	 * <p>Distance order.</p>
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
		Ensure.greaterThanOrEqualTo("order", order, 1);
		this.order = order;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(final double[] vector1, final double[] vector2) {
		Ensure.notNullAndNotEmpty("vector1", vector1);
		Ensure.notNullAndNotEmpty("vector2", vector2);
		Ensure.equalTo("vector2 length", vector2.length, vector1.length);
		var distance = 0.0d;
		for (var i = 0; i < vector1.length; ++i) {
			distance += Math.pow(Math.abs(vector1[i] - vector2[i]), order);
		}
		return Math.pow(distance, 1.0d / order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MinkowskiDistance)) {
			return false;
		}
		final var other = (MinkowskiDistance) object;
		return Equals.equals(order, other.order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.hashCode(order);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ToString.of(
				this,
				Pair.of("order", ToString.toString(order))
		);
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