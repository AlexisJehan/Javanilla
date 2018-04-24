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

import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;

/**
 * <p>Interface for distance/heuristic/similarity functions that work on {@code double array} vectors.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Similarity_measure">https://en.wikipedia.org/wiki/Similarity_measure</a>
 * @since 1.0
 */
@FunctionalInterface
public interface Distance {

	/**
	 * <p>Calculate a distance between unidimensional {@code double array} vectors.</p>
	 * @param x1 the {@code x} coordinate of the first vector
	 * @param x2 the {@code x} coordinate of the second vector
	 * @return the calculated distance
	 * @since 1.0
	 */
	default double calculate(final double x1, final double x2) {
		return calculate(DoubleArrays.of(x1), DoubleArrays.of(x2));
	}

	/**
	 * <p>Calculate a distance between two-dimensional {@code double array} vectors.</p>
	 * @param x1 the {@code x} coordinate of the first vector
	 * @param y1 the {@code y} coordinate of the first vector
	 * @param x2 the {@code x} coordinate of the second vector
	 * @param y2 the {@code y} coordinate of the second vector
	 * @return the calculated distance
	 * @since 1.0
	 */
	default double calculate(final double x1, final double y1, final double x2, final double y2) {
		return calculate(DoubleArrays.of(x1, y1), DoubleArrays.of(x2, y2));
	}

	/**
	 * <p>Calculate a distance between three-dimensional {@code double array} vectors.</p>
	 * @param x1 the {@code x} coordinate of the first vector
	 * @param y1 the {@code y} coordinate of the first vector
	 * @param z1 the {@code z} coordinate of the first vector
	 * @param x2 the {@code x} coordinate of the second vector
	 * @param y2 the {@code y} coordinate of the second vector
	 * @param z2 the {@code z} coordinate of the second vector
	 * @return the calculated distance
	 * @since 1.0
	 */
	default double calculate(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
		return calculate(DoubleArrays.of(x1, y1, z1), DoubleArrays.of(x2, y2, z2));
	}

	/**
	 * <p>Calculate a distance between both {@code double array} vectors.</p>
	 * @param vector1 the first vector
	 * @param vector2 the second vector
	 * @return the calculated distance
	 * @throws NullPointerException if any vector is {@code null}
	 * @throws IllegalArgumentException if vectors' length is different or lower than {@code 1}
	 * @since 1.0
	 */
	double calculate(final double[] vector1, final double[] vector2);
}