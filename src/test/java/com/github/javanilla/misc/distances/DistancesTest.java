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

import com.github.javanilla.lang.array.DoubleArrays;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link Distances} unit tests.</p>
 */
final class DistancesTest {

	@Test
	void testCalculateManhattan() {
		final var distance = Distances.MANHATTAN;
		assertThat(distance.calculate(0.0d, 0.0d)).isEqualTo(0.0d);
		assertThat(distance.calculate(0.0d, 10.0d)).isEqualTo(10.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isEqualTo(9.0d);
	}

	@Test
	void testCalculateEuclidean() {
		final var distance = Distances.EUCLIDEAN;
		assertThat(distance.calculate(0.0d, 0.0d)).isEqualTo(0.0d);
		assertThat(distance.calculate(0.0d, 10.0d)).isEqualTo(10.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isEqualTo(Math.sqrt(45.0d));
	}

	@Test
	void testCalculateSquaredEuclidean() {
		final var distance = Distances.SQUARED_EUCLIDEAN;
		assertThat(distance.calculate(0.0d, 0.0d)).isEqualTo(0.0d);
		assertThat(distance.calculate(0.0d, 10.0d)).isEqualTo(100.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(9.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isEqualTo(45.0d);
	}

	@Test
	void testCalculateChebyshev() {
		final var distance = Distances.CHEBYSHEV;
		assertThat(distance.calculate(0.0d, 0.0d)).isEqualTo(0.0d);
		assertThat(distance.calculate(0.0d, 10.0d)).isEqualTo(10.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isEqualTo(6.0d);
	}

	@Test
	void testCalculateHamming() {
		final var distance = Distances.HAMMING;
		assertThat(distance.calculate(0.0d, 0.0d)).isEqualTo(0.0d);
		assertThat(distance.calculate(0.0d, 10.0d)).isEqualTo(1.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(1.0d);
		assertThat(distance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isEqualTo(2.0d);
	}

	@Test
	void testCalculateNull() {
		assertThatNullPointerException().isThrownBy(() -> Distances.MANHATTAN.calculate(null, DoubleArrays.EMPTY));
		assertThatNullPointerException().isThrownBy(() -> Distances.MANHATTAN.calculate(DoubleArrays.EMPTY, null));
	}

	@Test
	void testCalculateInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Distances.MANHATTAN.calculate(DoubleArrays.of(1d), DoubleArrays.of(1d, 2d)));
		assertThatIllegalArgumentException().isThrownBy(() -> Distances.MANHATTAN.calculate(DoubleArrays.EMPTY, DoubleArrays.EMPTY));
	}
}