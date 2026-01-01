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
package com.github.alexisjehan.javanilla.misc.distances;

import com.github.alexisjehan.javanilla.io.Serializables;
import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.offset;

@Deprecated
final class MinkowskiDistanceTest {

	private static final int ORDER = 1;

	private final MinkowskiDistance minkowskiDistance = new MinkowskiDistance(ORDER);

	@Test
	void testConstructor() {
		assertThat(new MinkowskiDistance(ORDER)).satisfies(otherMinkowskiDistance -> assertThat(otherMinkowskiDistance.getOrder()).isEqualTo(ORDER));
	}

	@Test
	void testConstructorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new MinkowskiDistance(0));
	}

	@Test
	void testCalculate() {
		assertThat(new MinkowskiDistance(1)).satisfies(otherMinkowskiDistance -> {
			assertThat(otherMinkowskiDistance.calculate(0.0d, 0.0d)).isZero();
			assertThat(otherMinkowskiDistance.calculate(0.0d, 10.0d)).isEqualTo(10.0d);
			assertThat(otherMinkowskiDistance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
			assertThat(otherMinkowskiDistance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0d)).isEqualTo(9.0d);
		});
		assertThat(new MinkowskiDistance(2)).satisfies(otherMinkowskiDistance -> {
			assertThat(otherMinkowskiDistance.calculate(0.0d, 0.0d)).isZero();
			assertThat(otherMinkowskiDistance.calculate(0.0d, 10.0d)).isEqualTo(10.0d);
			assertThat(otherMinkowskiDistance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
			assertThat(otherMinkowskiDistance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isCloseTo(6.708d, offset(0.001d));
		});
		assertThat(new MinkowskiDistance(3)).satisfies(otherMinkowskiDistance -> {
			assertThat(otherMinkowskiDistance.calculate(0.0d, 0.0d)).isZero();
			assertThat(otherMinkowskiDistance.calculate(0.0d, 10.0d)).isCloseTo(10.0d, offset(0.1d));
			assertThat(otherMinkowskiDistance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
			assertThat(otherMinkowskiDistance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isCloseTo(6.240d, offset(0.001d));
		});
	}

	@Test
	void testCalculateInvalid() {
		assertThatNullPointerException().isThrownBy(() -> minkowskiDistance.calculate(null, DoubleArrays.singleton(1.0d)));
		assertThatNullPointerException().isThrownBy(() -> minkowskiDistance.calculate(DoubleArrays.singleton(1.0d), null));
		assertThatIllegalArgumentException().isThrownBy(() -> minkowskiDistance.calculate(DoubleArrays.singleton(1.0d), DoubleArrays.of(1.0d, 2.0d)));
		assertThatIllegalArgumentException().isThrownBy(() -> minkowskiDistance.calculate(DoubleArrays.EMPTY, DoubleArrays.EMPTY));
	}

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(minkowskiDistance.equals(minkowskiDistance)).isTrue();
		assertThat(minkowskiDistance).isNotEqualTo(new Object());
		assertThat(new MinkowskiDistance(ORDER)).satisfies(otherMinkowskiDistance -> {
			assertThat(otherMinkowskiDistance).isNotSameAs(minkowskiDistance);
			assertThat(otherMinkowskiDistance).isEqualTo(minkowskiDistance);
			assertThat(otherMinkowskiDistance).hasSameHashCodeAs(minkowskiDistance);
			assertThat(otherMinkowskiDistance).hasToString(minkowskiDistance.toString());
		});
		assertThat(new MinkowskiDistance(2)).satisfies(otherMinkowskiDistance -> {
			assertThat(otherMinkowskiDistance).isNotSameAs(minkowskiDistance);
			assertThat(otherMinkowskiDistance).isNotEqualTo(minkowskiDistance);
			assertThat(otherMinkowskiDistance).doesNotHaveSameHashCodeAs(minkowskiDistance);
			assertThat(otherMinkowskiDistance).doesNotHaveToString(minkowskiDistance.toString());
		});
	}

	@Test
	void testGetOrder() {
		assertThat(minkowskiDistance.getOrder()).isEqualTo(ORDER);
	}

	@Test
	void testSerializable() {
		assertThat(Serializables.<MinkowskiDistance>deserialize(Serializables.serialize(minkowskiDistance))).isEqualTo(minkowskiDistance);
	}
}