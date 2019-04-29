/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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

/**
 * <p>{@link MinkowskiDistance} unit tests.</p>
 */
final class MinkowskiDistanceTest {

	@Test
	void testConstructor() {
		assertThat(new MinkowskiDistance(1).getOrder()).isEqualTo(1);
	}

	@Test
	void testConstructorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new MinkowskiDistance(0));
	}

	@Test
	void testCalculate() {
		{
			final var minkowskiDistance = new MinkowskiDistance(1);
			assertThat(minkowskiDistance.calculate(0.0d, 0.0d)).isEqualTo(0.0d);
			assertThat(minkowskiDistance.calculate(0.0d, 10.0d)).isEqualTo(10.0d);
			assertThat(minkowskiDistance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
			assertThat(minkowskiDistance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0d)).isEqualTo(9.0d);
		}
		{
			final var minkowskiDistance = new MinkowskiDistance(2);
			assertThat(minkowskiDistance.calculate(0.0d, 0.0d)).isEqualTo(0.0d);
			assertThat(minkowskiDistance.calculate(0.0d, 10.0d)).isEqualTo(10.0d);
			assertThat(minkowskiDistance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
			assertThat(minkowskiDistance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isCloseTo(6.708d, offset(0.001d));
		}
		{
			final var minkowskiDistance = new MinkowskiDistance(3);
			assertThat(minkowskiDistance.calculate(0.0d, 0.0d)).isEqualTo(0.0d);
			assertThat(minkowskiDistance.calculate(0.0d, 10.0d)).isCloseTo(10.0d, offset(0.1d));
			assertThat(minkowskiDistance.calculate(0.0d, 1.5d, 0.0d, -1.5d)).isEqualTo(3.0d);
			assertThat(minkowskiDistance.calculate(0.0d, 1.5d, 3.0d, 0.0d, -1.5d, -3.0)).isCloseTo(6.240d, offset(0.001d));
		}
	}

	@Test
	void testCalculateInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new MinkowskiDistance(1).calculate(null, DoubleArrays.singleton(1.0d)));
		assertThatNullPointerException().isThrownBy(() -> new MinkowskiDistance(1).calculate(DoubleArrays.singleton(1.0d), null));
		assertThatIllegalArgumentException().isThrownBy(() -> new MinkowskiDistance(1).calculate(DoubleArrays.singleton(1.0d), DoubleArrays.of(1.0d, 2.0d)));
		assertThatIllegalArgumentException().isThrownBy(() -> new MinkowskiDistance(1).calculate(DoubleArrays.EMPTY, DoubleArrays.EMPTY));
	}

	@Test
	void testEqualsHashCodeToString() {
		final var minkowskiDistance = new MinkowskiDistance(1);
		assertThat(minkowskiDistance).isEqualTo(minkowskiDistance);
		assertThat(minkowskiDistance).isNotEqualTo(1);
		{
			final var otherMinkowskiDistance = new MinkowskiDistance(1);
			assertThat(otherMinkowskiDistance).isEqualTo(minkowskiDistance);
			assertThat(otherMinkowskiDistance).hasSameHashCodeAs(minkowskiDistance);
			assertThat(otherMinkowskiDistance).hasToString(minkowskiDistance.toString());
		}
		{
			final var otherMinkowskiDistance = new MinkowskiDistance(2);
			assertThat(otherMinkowskiDistance).isNotEqualTo(minkowskiDistance);
			assertThat(otherMinkowskiDistance.hashCode()).isNotEqualTo(minkowskiDistance.hashCode());
			assertThat(otherMinkowskiDistance.toString()).isNotEqualTo(minkowskiDistance.toString());
		}
	}

	@Test
	void testSerializable() {
		final var minkowskiDistance = new MinkowskiDistance(2);
		assertThat(Serializables.<MinkowskiDistance>deserialize(Serializables.serialize(minkowskiDistance))).isEqualTo(minkowskiDistance);
	}
}