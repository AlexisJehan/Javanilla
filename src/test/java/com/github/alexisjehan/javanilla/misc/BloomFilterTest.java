/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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
package com.github.alexisjehan.javanilla.misc;

import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class BloomFilterTest {

	private static final int LENGTH = 10;
	private static final Checksum[] CHECKSUM_HASH_FUNCTIONS = ObjectArrays.of(
			new CRC32()
	);
	private static final IntUnaryOperator[] INT_UNARY_OPERATOR_HASH_FUNCTIONS = ObjectArrays.of(
			IntUnaryOperator.identity()
	);
	private static final ToIntFunction<Object>[] TO_INT_FUNCTION_HASH_FUNCTIONS = ObjectArrays.of(
			Object::hashCode
	);

	@Test
	void testConstructorChecksumImmutable() {
		final var hashFunctions = CHECKSUM_HASH_FUNCTIONS.clone();
		final var bloomFilter = new BloomFilter<>(LENGTH, hashFunctions);
		bloomFilter.add("foo");
		hashFunctions[0] = null;
		assertThat(bloomFilter.mightContains("foo")).isTrue();
	}

	@Test
	void testConstructorChecksumInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(0, CHECKSUM_HASH_FUNCTIONS));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(LENGTH, (Checksum[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(LENGTH, ObjectArrays.empty(Checksum.class)));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(LENGTH, (Checksum) null));
	}

	@Test
	void testConstructorIntUnaryOperatorImmutable() {
		final var hashFunctions = INT_UNARY_OPERATOR_HASH_FUNCTIONS.clone();
		final var bloomFilter = new BloomFilter<>(LENGTH, hashFunctions);
		bloomFilter.add("foo");
		hashFunctions[0] = null;
		assertThat(bloomFilter.mightContains("foo")).isTrue();
	}

	@Test
	void testConstructorIntUnaryOperatorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(0, INT_UNARY_OPERATOR_HASH_FUNCTIONS));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(LENGTH, (IntUnaryOperator[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(LENGTH, ObjectArrays.empty(IntUnaryOperator.class)));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(LENGTH, (IntUnaryOperator) null));
	}

	@Test
	void testConstructorToIntFunctionImmutable() {
		final var hashFunctions = TO_INT_FUNCTION_HASH_FUNCTIONS.clone();
		final var bloomFilter = new BloomFilter<>(LENGTH, hashFunctions);
		bloomFilter.add("foo");
		hashFunctions[0] = null;
		assertThat(bloomFilter.mightContains("foo")).isTrue();
	}

	@Test
	@SuppressWarnings("unchecked")
	void testConstructorToIntFunctionInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(0, TO_INT_FUNCTION_HASH_FUNCTIONS));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(LENGTH, (ToIntFunction<String>[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(LENGTH, ObjectArrays.empty(ToIntFunction.class)));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(LENGTH, (ToIntFunction<String>) null));
	}

	@Test
	void testAddAndMightContains() {
		assertThat(new BloomFilter<>(LENGTH, CHECKSUM_HASH_FUNCTIONS)).satisfies(bloomFilter -> {
			assertThat(bloomFilter.mightContains("foo")).isFalse();
			assertThat(bloomFilter.mightContains("bar")).isFalse();
			bloomFilter.add("foo");
			assertThat(bloomFilter.mightContains("foo")).isTrue();
			assertThat(bloomFilter.mightContains("bar")).isFalse();
			bloomFilter.add("bar");
			assertThat(bloomFilter.mightContains("foo")).isTrue();
			assertThat(bloomFilter.mightContains("bar")).isTrue();
		});

		// Collision
		assertThat(new BloomFilter<>(LENGTH, (Object value) -> 0)).satisfies(bloomFilter -> {
			assertThat(bloomFilter.mightContains("foo")).isFalse();
			assertThat(bloomFilter.mightContains("bar")).isFalse();
			bloomFilter.add("foo");
			assertThat(bloomFilter.mightContains("foo")).isTrue();
			assertThat(bloomFilter.mightContains("bar")).isTrue();
		});
	}

	@Test
	void testClear() {
		final var bloomFilter = new BloomFilter<>(LENGTH, CHECKSUM_HASH_FUNCTIONS);
		assertThat(bloomFilter.mightContains("foo")).isFalse();
		assertThat(bloomFilter.mightContains("bar")).isFalse();
		bloomFilter.add("foo");
		assertThat(bloomFilter.mightContains("foo")).isTrue();
		assertThat(bloomFilter.mightContains("bar")).isFalse();
		bloomFilter.add("bar");
		assertThat(bloomFilter.mightContains("foo")).isTrue();
		assertThat(bloomFilter.mightContains("bar")).isTrue();
		bloomFilter.clear();
		assertThat(bloomFilter.mightContains("foo")).isFalse();
		assertThat(bloomFilter.mightContains("bar")).isFalse();
		bloomFilter.add("bar");
		assertThat(bloomFilter.mightContains("foo")).isFalse();
		assertThat(bloomFilter.mightContains("bar")).isTrue();
		bloomFilter.add("foo");
		assertThat(bloomFilter.mightContains("foo")).isTrue();
		assertThat(bloomFilter.mightContains("bar")).isTrue();
	}

	@Test
	void testGetters() {
		final var bloomFilter = new BloomFilter<>(LENGTH, CHECKSUM_HASH_FUNCTIONS);
		assertThat(bloomFilter.getLength()).isEqualTo(LENGTH);
		assertThat(bloomFilter.getNumberOfHashFunctions()).isEqualTo(1);
	}

	@Test
	void testCalculateFalsePositiveRate() {
		assertThat(BloomFilter.calculateFalsePositiveRate(1, 3, 100)).isEqualTo(1.0d);
		assertThat(BloomFilter.calculateFalsePositiveRate(10, 3, 0)).isZero();
		assertThat(BloomFilter.calculateFalsePositiveRate(10, 3, 100)).isBetween(0.0d, 1.0d);
	}

	@Test
	void testCalculateFalsePositiveRateInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateFalsePositiveRate(0, 3, 100));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateFalsePositiveRate(10, 0, 100));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateFalsePositiveRate(10, 3, -1));
	}

	@Test
	void testCalculateOptimalLength() {
		assertThat(BloomFilter.calculateOptimalLength(0, 1.0d)).isZero();
		assertThat(BloomFilter.calculateOptimalLength(1, 1.0d)).isZero();
		assertThat(BloomFilter.calculateOptimalLength(100, 0.0d)).isEqualTo(Double.POSITIVE_INFINITY);
		assertThat(BloomFilter.calculateOptimalLength(100, 0.5d)).isBetween(0.0d, Double.POSITIVE_INFINITY);
		assertThat(BloomFilter.calculateOptimalLength(100, 1.0d)).isZero();
	}

	@Test
	void testCalculateOptimalLengthInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptimalLength(-1, 0.5d));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptimalLength(100, -0.1d));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptimalLength(100, 1.1d));
	}

	@Test
	void testCalculateOptimalNumberOfHashFunctions() {
		assertThat(BloomFilter.calculateOptimalNumberOfHashFunctions(10, 0)).isZero();
		assertThat(BloomFilter.calculateOptimalNumberOfHashFunctions(10, 1)).isGreaterThan(0.0d);
	}

	@Test
	void testCalculateOptimalNumberOfHashFunctionsInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptimalNumberOfHashFunctions(0, 100));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptimalNumberOfHashFunctions(10, -1));
	}
}