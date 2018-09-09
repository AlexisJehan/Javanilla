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

/**
 * <p>{@link StringFormatter} unit tests.</p>
 */
final class BloomFilterTest {

	@Test
	void testConstructorChecksum() {
		final var bloomFilter = new BloomFilter<>(1, new CRC32());
		assertThat(bloomFilter.getLength()).isEqualTo(1);
		assertThat(bloomFilter.getNumberOfHashFunctions()).isEqualTo(1);
		assertThat(bloomFilter.mightContains("foo")).isFalse();
		bloomFilter.add("foo");
		assertThat(bloomFilter.mightContains("foo")).isTrue();
	}

	@Test
	void testConstructorChecksumInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(0, new CRC32()));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(1, (Checksum[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(1, ObjectArrays.empty(Checksum.class)));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(1, (Checksum) null));
	}

	@Test
	void testConstructorIntUnaryOperator() {
		final var bloomFilter = new BloomFilter<>(1, IntUnaryOperator.identity());
		assertThat(bloomFilter.getLength()).isEqualTo(1);
		assertThat(bloomFilter.getNumberOfHashFunctions()).isEqualTo(1);
		assertThat(bloomFilter.mightContains("foo")).isFalse();
		bloomFilter.add("foo");
		assertThat(bloomFilter.mightContains("foo")).isTrue();
	}

	@Test
	void testConstructorIntUnaryOperatorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(0, IntUnaryOperator.identity()));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(1, (IntUnaryOperator[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(1, ObjectArrays.empty(IntUnaryOperator.class)));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(1, (IntUnaryOperator) null));
	}

	@Test
	void testConstructorToIntFunction() {
		final var bloomFilter = new BloomFilter<>(1, String::hashCode);
		assertThat(bloomFilter.getLength()).isEqualTo(1);
		assertThat(bloomFilter.getNumberOfHashFunctions()).isEqualTo(1);
		assertThat(bloomFilter.mightContains("foo")).isFalse();
		bloomFilter.add("foo");
		assertThat(bloomFilter.mightContains("foo")).isTrue();
	}

	@Test
	@SuppressWarnings("unchecked")
	void testConstructorToIntFunctionInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(0, String::hashCode));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(1, (ToIntFunction<String>[]) null));
		assertThatIllegalArgumentException().isThrownBy(() -> new BloomFilter<>(1, ObjectArrays.empty(ToIntFunction.class)));
		assertThatNullPointerException().isThrownBy(() -> new BloomFilter<>(1, (ToIntFunction<String>) null));
	}

	@Test
	void testAddMightContains() {
		{
			final var bloomFilter = new BloomFilter<>(10, String::hashCode, value -> 0);
			assertThat(bloomFilter.getLength()).isEqualTo(10);
			assertThat(bloomFilter.getNumberOfHashFunctions()).isEqualTo(2);
			assertThat(bloomFilter.mightContains("foo")).isFalse();
			assertThat(bloomFilter.mightContains("bar")).isFalse();
			bloomFilter.add("foo");
			assertThat(bloomFilter.mightContains("foo")).isTrue();
			assertThat(bloomFilter.mightContains("bar")).isFalse();
			bloomFilter.add("bar");
			assertThat(bloomFilter.mightContains("foo")).isTrue();
			assertThat(bloomFilter.mightContains("bar")).isTrue();
		}
		{
			// Collision
			final var bloomFilter = new BloomFilter<>(10, (String value) -> 0);
			assertThat(bloomFilter.getLength()).isEqualTo(10);
			assertThat(bloomFilter.getNumberOfHashFunctions()).isEqualTo(1);
			assertThat(bloomFilter.mightContains("foo")).isFalse();
			assertThat(bloomFilter.mightContains("bar")).isFalse();
			bloomFilter.add("foo");
			assertThat(bloomFilter.mightContains("foo")).isTrue();
			assertThat(bloomFilter.mightContains("bar")).isTrue();
			bloomFilter.add("bar");
			assertThat(bloomFilter.mightContains("foo")).isTrue();
			assertThat(bloomFilter.mightContains("bar")).isTrue();
		}
	}

	@Test
	void testClear() {
		final var bloomFilter = new BloomFilter<>(10, String::hashCode);
		assertThat(bloomFilter.getLength()).isEqualTo(10);
		assertThat(bloomFilter.getNumberOfHashFunctions()).isEqualTo(1);
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
		bloomFilter.add("foo");
		assertThat(bloomFilter.mightContains("foo")).isTrue();
		assertThat(bloomFilter.mightContains("bar")).isFalse();
		bloomFilter.add("bar");
		assertThat(bloomFilter.mightContains("foo")).isTrue();
		assertThat(bloomFilter.mightContains("bar")).isTrue();
	}

	@Test
	void testCalculateFalsePositiveRate() {
		assertThat(BloomFilter.calculateFalsePositiveRate(1, 3, 100)).isEqualTo(1.0d);
		assertThat(BloomFilter.calculateFalsePositiveRate(10, 3, 0)).isEqualTo(0.0d);
		assertThat(BloomFilter.calculateFalsePositiveRate(10, 3, 100)).isBetween(0.0d, 1.0d);
	}

	@Test
	void testCalculateFalsePositiveRateInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateFalsePositiveRate(0, 3, 100));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateFalsePositiveRate(10, 0, 100));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateFalsePositiveRate(10, 3, -1));
	}

	@Test
	void testCalculateOptionalLength() {
		assertThat(BloomFilter.calculateOptionalLength(0, 1.0d)).isEqualTo(0.0d);
		assertThat(BloomFilter.calculateOptionalLength(1, 1.0d)).isEqualTo(0.0d);
		assertThat(BloomFilter.calculateOptionalLength(100, 0.0d)).isEqualTo(Double.POSITIVE_INFINITY);
		assertThat(BloomFilter.calculateOptionalLength(100, 0.5d)).isBetween(0.0d, Double.POSITIVE_INFINITY);
		assertThat(BloomFilter.calculateOptionalLength(100, 1.0d)).isEqualTo(0.0d);
	}

	@Test
	void testCalculateOptionalLengthInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptionalLength(-1, 0.5d));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptionalLength(100, -0.1d));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptionalLength(100, 1.1d));
	}

	@Test
	void testCalculateOptionalNumberOfHashFunctions() {
		assertThat(BloomFilter.calculateOptionalNumberOfHashFunctions(10, 0)).isEqualTo(0.0d);
		assertThat(BloomFilter.calculateOptionalNumberOfHashFunctions(10, 1)).isGreaterThan(0.0d);
	}

	@Test
	void testCalculateOptionalNumberOfHashFunctionsInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptionalNumberOfHashFunctions(0, 100));
		assertThatIllegalArgumentException().isThrownBy(() -> BloomFilter.calculateOptionalNumberOfHashFunctions(10, -1));
	}
}