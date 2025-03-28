/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.Arrays;
import java.util.BitSet;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.zip.Checksum;

/**
 * A Bloom Filter implementation to test if an element might be in a set or if it does absolutely not.
 *
 * <p><b>Note</b>: Some static functions are also available to calculate the false positive rate and optimal
 * parameters.</p>
 * @param <E> the element type
 * @see <a href="https://en.wikipedia.org/wiki/Bloom_filter">https://en.wikipedia.org/wiki/Bloom_filter</a>
 * @since 1.2.0
 */
public final class BloomFilter<E> {

	/**
	 * Fixed-length of the bit set.
	 * @since 1.2.0
	 */
	private final int length;

	/**
	 * Hash functions to transform a value to an {@code int} hash.
	 * @since 1.2.0
	 */
	private final ToIntFunction<E>[] hashFunctions;

	/**
	 * Delegated bit set.
	 * @since 1.2.0
	 */
	private final BitSet bits;

	/**
	 * Constructor with a length and multiple {@link Checksum} hash functions.
	 * @param length the length of the Bloom Filter
	 * @param hashFunctions the {@link Checksum} hash functions array used by the Bloom Filter
	 * @throws NullPointerException if the {@link Checksum} hash functions array or any of them is {@code null}
	 * @throws IllegalArgumentException if the length is lower than {@code 1} or if the {@link Checksum} hash functions
	 *         array is empty
	 * @since 1.2.0
	 */
	public BloomFilter(final int length, final Checksum... hashFunctions) {
		this(
				length,
				Arrays.stream(Ensure.notNullAndNotNullElements("hashFunctions", hashFunctions))
						.map(hashFunction -> (IntUnaryOperator) value -> {
							hashFunction.reset();
							hashFunction.update(ByteArrays.of(value));
							return (int) hashFunction.getValue();
						})
						.toArray(IntUnaryOperator[]::new)
		);
	}

	/**
	 * Constructor with a length and multiple {@link IntUnaryOperator} hash functions, working with
	 * {@link Object#hashCode()}.
	 * @param length the length of the Bloom Filter
	 * @param hashFunctions the {@link IntUnaryOperator} hash functions array used by the Bloom Filter
	 * @throws NullPointerException if the {@link IntUnaryOperator} hash functions array or any of them is {@code null}
	 * @throws IllegalArgumentException if the length is lower than {@code 1} or if the {@link IntUnaryOperator} hash
	 *         functions array is empty
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public BloomFilter(final int length, final IntUnaryOperator... hashFunctions) {
		this(
				length,
				Arrays.stream(Ensure.notNullAndNotNullElements("hashFunctions", hashFunctions))
						.map(hashFunction -> (ToIntFunction<E>) value -> hashFunction.applyAsInt(value.hashCode()))
						.toArray(ToIntFunction[]::new)
		);
	}

	/**
	 * Constructor with a length and multiple {@link ToIntFunction} hash functions.
	 * @param length the length of the Bloom Filter
	 * @param hashFunctions the {@link ToIntFunction} hash functions array used by the Bloom Filter
	 * @throws NullPointerException if the {@link ToIntFunction} hash functions array or any of them is {@code null}
	 * @throws IllegalArgumentException if the length is lower than {@code 1} or if the {@link ToIntFunction} hash
	 *         functions array is empty
	 * @since 1.2.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public BloomFilter(final int length, final ToIntFunction<E>... hashFunctions) {
		Ensure.greaterThanOrEqualTo("length", length, 1);
		Ensure.notNullAndNotEmpty("hashFunctions", hashFunctions);
		Ensure.notNullAndNotNullElements("hashFunctions", hashFunctions);
		this.length = length;
		this.hashFunctions = hashFunctions.clone();
		bits = new BitSet(length);
	}

	/**
	 * Add an element to the Bloom Filter.
	 *
	 * <p><b>Note</b>: A {@code null} element may be restricted depending of hash functions implementations.</p>
	 * @param element the element to add to the Bloom Filter
	 * @since 1.2.0
	 */
	public void add(final E element) {
		for (final var hashFunction : hashFunctions) {
			bits.set(StrictMath.abs(hashFunction.applyAsInt(element)) % length, true);
		}
	}

	/**
	 * Test if an element might be contained by the Bloom Filter or absolutely not.
	 *
	 * <p><b>Note</b>: A {@code null} element may be restricted depending of hash functions implementations.</p>
	 * @param element the element to test
	 * @return {@code true} if the element might be contained by the Bloom Filter
	 * @since 1.2.0
	 */
	public boolean mightContains(final E element) {
		for (final var hashFunction : hashFunctions) {
			if (!bits.get(StrictMath.abs(hashFunction.applyAsInt(element)) % length)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Clear the Bloom Filter by setting all bits back to {@code 0}.
	 * @since 1.2.0
	 */
	public void clear() {
		bits.clear();
	}

	/**
	 * Get the length of the Bloom Filter.
	 * @return the length
	 * @since 1.2.0
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Get the number of hash functions used by the Bloom Filter.
	 * @return the number of hash functions
	 * @since 1.2.0
	 */
	public int getNumberOfHashFunctions() {
		return hashFunctions.length;
	}

	/**
	 * Calculate the false positive rate (p).
	 * @param length the length of the Bloom Filter
	 * @param numberOfHashFunctions the number of hash functions used by the Bloom Filter
	 * @param expectedNumberOfElements the expected number of elements to be added
	 * @return the false positive rate (p)
	 * @throws IllegalArgumentException if the length, the number of hash functions or the expected number of elements
	 *         is not valid
	 * @since 1.2.0
	 */
	public static double calculateFalsePositiveRate(final int length, final int numberOfHashFunctions, final int expectedNumberOfElements) {
		Ensure.greaterThanOrEqualTo("length", length, 1);
		Ensure.greaterThanOrEqualTo("numberOfHashFunctions", numberOfHashFunctions, 1);
		Ensure.greaterThanOrEqualTo("expectedNumberOfElements", expectedNumberOfElements, 0);
		return StrictMath.pow(1.0d - StrictMath.exp(-numberOfHashFunctions * expectedNumberOfElements / (double) length), numberOfHashFunctions);
	}

	/**
	 * Calculate the optimal length of the Bloom Filter (m).
	 * @param expectedNumberOfElements the expected number of elements to be added
	 * @param acceptableFalsePositiveRate the acceptable false positive rate (between {@code 0} and {@code 1})
	 * @return the optimal length (m)
	 * @throws IllegalArgumentException if the expected number of elements or the acceptable false positive rate is not
	 *         valid
	 * @since 1.2.0
	 */
	public static double calculateOptimalLength(final int expectedNumberOfElements, final double acceptableFalsePositiveRate) {
		Ensure.greaterThanOrEqualTo("expectedNumberOfElements", expectedNumberOfElements, 0);
		Ensure.between("acceptableFalsePositiveRate", acceptableFalsePositiveRate, 0.0d, 1.0d);
		return StrictMath.abs(-expectedNumberOfElements * StrictMath.log(acceptableFalsePositiveRate) / StrictMath.pow(StrictMath.log(2.0d), 2.0d));
	}

	/**
	 * Calculate the optimal number of hash functions used by the Bloom Filter (k).
	 * @param length the length of the Bloom Filter
	 * @param expectedNumberOfElements the expected number of elements to be added
	 * @return the optimal number of hash functions (k)
	 * @throws IllegalArgumentException if the length or the expected number of elements is not valid
	 * @since 1.2.0
	 */
	public static double calculateOptimalNumberOfHashFunctions(final int length, final int expectedNumberOfElements) {
		Ensure.greaterThanOrEqualTo("length", length, 1);
		Ensure.greaterThanOrEqualTo("expectedNumberOfElements", expectedNumberOfElements, 0);
		if (0 == expectedNumberOfElements) {
			return 0.0d;
		}
		return length / (double) expectedNumberOfElements * StrictMath.log(2.0d);
	}
}