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
package com.github.javanilla.misc.distances;

/**
 * <p>Commons vectors {@link Distance}s implementations.</p>
 * @since 1.0
 */
public enum Distances implements Distance {

	/**
	 * <p>The Manhattan distance also known as taxicab metric, rectilinear distance or L<sub>1</sub> distance.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">https://en.wikipedia.org/wiki/Taxicab_geometry</a>
	 * @since 1.0
	 */
	MANHATTAN {
		@Override
		protected double calculateImpl(final double[] vector1, final double[] vector2) {
			var d = 0.0d;
			for (var i = 0; i < vector1.length; ++i) {
				d += Math.abs(vector1[i] - vector2[i]);
			}
			return d;
		}
	},

	/**
	 * <p>The Euclidean distance also known as Pythagorean metric or L<sub>2</sub> distance.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Euclidean_geometry">https://en.wikipedia.org/wiki/Euclidean_geometry</a>
	 * @since 1.0
	 */
	EUCLIDEAN {
		@Override
		protected double calculateImpl(final double[] vector1, final double[] vector2) {
			if (1 == vector1.length) {
				return Math.abs(vector1[0] - vector2[0]);
			}
			return Math.sqrt(SQUARED_EUCLIDEAN.calculateImpl(vector1, vector2));
		}
	},

	/**
	 * <p>The squared Euclidean distance is different from the classical Euclidean distance in the fact it does not
	 * perform the costly square root operation. It should be used for performance usages when distance only need to be
	 * compared.</p>
	 * @since 1.0
	 */
	SQUARED_EUCLIDEAN {
		@Override
		protected double calculateImpl(final double[] vector1, final double[] vector2) {
			var d = 0.0d;
			for (var i = 0; i < vector1.length; ++i) {
				final var t = vector1[i] - vector2[i];
				d += t * t;
			}
			return d;
		}
	},

	/**
	 * <p>The Chebyshev distance also known as Tchebychev distance, maximum metric or L<sub>&infin;</sub> distance.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Chebyshev_distance">https://en.wikipedia.org/wiki/Chebyshev_distance</a>
	 * @since 1.0
	 */
	CHEBYSHEV {
		@Override
		protected double calculateImpl(final double[] vector1, final double[] vector2) {
			var d = 0.0d;
			for (var i = 0; i < vector1.length; ++i) {
				d = Math.max(d, Math.abs(vector1[i] - vector2[i]));
			}
			return d;
		}
	},

	/**
	 * <p>The Hamming distance.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Hamming_distance">https://en.wikipedia.org/wiki/Hamming_distance</a>
	 * @since 1.0
	 */
	HAMMING {
		@Override
		protected double calculateImpl(final double[] vector1, final double[] vector2) {
			var d = 0.0d;
			for (var i = 0; i < vector1.length; ++i) {
				if (0 != Double.compare(vector1[i], vector2[i])) {
					++d;
				}
			}
			return d;
		}
	};

	@Override
	public final double calculate(final double[] vector1, final double[] vector2) {
		if (null == vector1) {
			throw new NullPointerException("Invalid first vector (not null expected)");
		}
		if (null == vector2) {
			throw new NullPointerException("Invalid second vector (not null expected)");
		}
		if (vector1.length != vector2.length) {
			throw new IllegalArgumentException("Distinct vectors dimension: " + vector1.length + " and " + vector2.length + " (same expected)");
		}
		if (1 > vector1.length) {
			throw new IllegalArgumentException("Invalid vectors dimension: " + vector1.length + " (greater than or equal to 1 expected)");
		}
		return calculateImpl(vector1, vector2);
	}

	/**
	 * <p>Calculate a distance between both {@code double array} vectors which have been validated.</p>
	 * @param vector1 the first vector
	 * @param vector2 the second vector
	 * @return the calculated distance
	 * @since 1.0
	 */
	protected abstract double calculateImpl(final double[] vector1, final double[] vector2);
}