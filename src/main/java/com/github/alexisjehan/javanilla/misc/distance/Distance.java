/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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
package com.github.alexisjehan.javanilla.misc.distance;

import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;

/**
 * Interface for distance/heuristic/similarity functions that work on {@code double} vectors.
 *
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is
 * {@link #calculate(double[], double[])}.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Similarity_measure">https://en.wikipedia.org/wiki/Similarity_measure</a>
 * @since 1.8.0
 */
@FunctionalInterface
public interface Distance {

	/**
	 * Calculate a distance between unidimensional {@code double} vectors.
	 * @param x1 the {@code x} coordinate of the first vector
	 * @param x2 the {@code x} coordinate of the second vector
	 * @return the calculated distance
	 * @since 1.8.0
	 */
	default double calculate(final double x1, final double x2) {
		return calculate(DoubleArrays.of(x1), DoubleArrays.of(x2));
	}

	/**
	 * Calculate a distance between two-dimensional {@code double} vectors.
	 * @param x1 the {@code x} coordinate of the first vector
	 * @param y1 the {@code y} coordinate of the first vector
	 * @param x2 the {@code x} coordinate of the second vector
	 * @param y2 the {@code y} coordinate of the second vector
	 * @return the calculated distance
	 * @since 1.8.0
	 */
	default double calculate(final double x1, final double y1, final double x2, final double y2) {
		return calculate(DoubleArrays.of(x1, y1), DoubleArrays.of(x2, y2));
	}

	/**
	 * Calculate a distance between three-dimensional {@code double} vectors.
	 * @param x1 the {@code x} coordinate of the first vector
	 * @param y1 the {@code y} coordinate of the first vector
	 * @param z1 the {@code z} coordinate of the first vector
	 * @param x2 the {@code x} coordinate of the second vector
	 * @param y2 the {@code y} coordinate of the second vector
	 * @param z2 the {@code z} coordinate of the second vector
	 * @return the calculated distance
	 * @since 1.8.0
	 */
	default double calculate(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
		return calculate(DoubleArrays.of(x1, y1, z1), DoubleArrays.of(x2, y2, z2));
	}

	/**
	 * Calculate a distance between both {@code double} vectors.
	 * @param vector1 the first vector
	 * @param vector2 the second vector
	 * @return the calculated distance
	 * @throws NullPointerException if any {@code double} vector is {@code null}
	 * @throws IllegalArgumentException if any {@code double} vector is empty or if both have different dimensions
	 * @since 1.8.0
	 */
	double calculate(double[] vector1, double[] vector2);
}