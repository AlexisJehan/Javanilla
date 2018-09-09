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

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.util.iteration.Iterables;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.zip.Checksum;

/**
 * <p>A Bloom Filter implementation to test if an element might be in a set or if it does absolutely not.</p>
 * <p>Some static functions are also available to calculate the false positive rate and optimal parameters.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Bloom_filter">https://en.wikipedia.org/wiki/Bloom_filter</a>
 * @param <E> the element type
 * @since 1.2.0
 */
public final class BloomFilter<E> {

	/**
	 * <p>Fixed-length of the bit set.</p>
	 * @since 1.2.0
	 */
	private final int length;

	/**
	 * <p>Hash functions to transform a value to an {@code int} hash.</p>
	 * @since 1.2.0
	 */
	private final ToIntFunction<E>[] hashFunctions;

	/**
	 * <p>Delegated bit set.</p>
	 * @since 1.2.0
	 */
	private final BitSet bits;

	/**
	 * <p>Constructor with a length and multiple {@code Checksum} hash functions.</p>
	 * @param length the length of the Bloom Filter
	 * @param hashFunctions the {@code Checksum} hash functions array used by the Bloom Filter
	 * @throws NullPointerException if the {@code Checksum} hash functions array or any of them is {@code null}
	 * @throws IllegalArgumentException if the length is lower than {@code 1} or if the {@code Checksum} hash functions
	 * array is empty
	 * @since 1.2.0
	 */
	public BloomFilter(final int length, final Checksum... hashFunctions) {
		this(
				length,
				Arrays.stream(Objects.requireNonNull(hashFunctions, "Invalid hash functions (not null expected)"))
						.peek(hashFunction -> {
							if (null == hashFunction) {
								throw new NullPointerException("Invalid hash function (not null expected)");
							}
						})
						.map(hashFunction -> (IntUnaryOperator) value -> {
							hashFunction.reset();
							hashFunction.update(ByteArrays.ofInt(value));
							return (int) hashFunction.getValue();
						})
						.toArray(IntUnaryOperator[]::new)
		);
	}

	/**
	 * <p>Constructor with a length and multiple {@code IntUnaryOperator} hash functions working with
	 * {@link Object#hashCode()}.</p>
	 * @param length the length of the Bloom Filter
	 * @param hashFunctions the {@code IntUnaryOperator} hash functions array used by the Bloom Filter
	 * @throws NullPointerException if the {@code IntUnaryOperator} hash functions array or any of them is {@code null}
	 * @throws IllegalArgumentException if the length is lower than {@code 1} or if the {@code IntUnaryOperator} hash
	 * functions array is empty
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public BloomFilter(final int length, final IntUnaryOperator... hashFunctions) {
		this(
				length,
				Arrays.stream(Objects.requireNonNull(hashFunctions, "Invalid hash functions (not null expected)"))
						.peek(hashFunction -> {
							if (null == hashFunction) {
								throw new NullPointerException("Invalid hash function (not null expected)");
							}
						})
						.map(hashFunction -> (ToIntFunction<E>) value -> hashFunction.applyAsInt(value.hashCode()))
						.toArray(ToIntFunction[]::new)
		);
	}

	/**
	 * <p>Constructor with a length and multiple {@code ToIntFunction} hash functions.</p>
	 * @param length the length of the Bloom Filter
	 * @param hashFunctions the {@code ToIntFunction} hash functions array used by the Bloom Filter
	 * @throws NullPointerException if the {@code ToIntFunction} hash functions array or any of them is {@code null}
	 * @throws IllegalArgumentException if the length is lower than {@code 1} or if the {@code ToIntFunction} hash
	 * functions array is empty
	 * @since 1.2.0
	 */
	@SafeVarargs
	public BloomFilter(final int length, final ToIntFunction<E>... hashFunctions) {
		if (1 > length) {
			throw new IllegalArgumentException("Invalid length: " + length + " (greater than or equal to 1 expected)");
		}
		if (null == hashFunctions) {
			throw new NullPointerException("Invalid hash functions (not null expected)");
		}
		if (0 == hashFunctions.length) {
			throw new IllegalArgumentException("Invalid hash functions (not empty expected)");
		}
		for (final var indexedHashFunction : Iterables.index(Arrays.asList(hashFunctions))) {
			if (null == indexedHashFunction.getElement()) {
				throw new NullPointerException("Invalid hash function at index " + indexedHashFunction.getIndex() + " (not null expected)");
			}
		}
		this.length = length;
		this.hashFunctions = hashFunctions;
		bits = new BitSet(length);
	}

	/**
	 * <p>Add an element to the Bloom Filter.</p>
	 * <p><b>Note</b>: A {@code null} element may be restricted depending of hash functions implementations.</p>
	 * @param element the element to add to the Bloom Filter
	 * @since 1.2.0
	 */
	public void add(final E element) {
		for (final var hashFunction : hashFunctions) {
			bits.set(Math.abs(hashFunction.applyAsInt(element)) % length, true);
		}
	}

	/**
	 * <p>Test if an element might be contained by the Bloom Filter or absolutely not.</p>
	 * @param element the element to test
	 * @return {@code true} if the element might be contained by the Bloom Filter
	 * @since 1.2.0
	 */
	public boolean mightContains(final E element) {
		for (final var hashFunction : hashFunctions) {
			if (!bits.get(Math.abs(hashFunction.applyAsInt(element)) % length)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Clear the Bloom Filter by setting all bits back to {@code 0}.</p>
	 * @since 1.2.0
	 */
	public void clear() {
		bits.clear();
	}

	/**
	 * <p>Get the length of the Bloom Filter.</p>
	 * @return the length
	 * @since 1.2.0
	 */
	public int getLength() {
		return length;
	}

	/**
	 * <p>Get the number of hash functions used by the Bloom Filter.</p>
	 * @return the number of hash functions
	 * @since 1.2.0
	 */
	public int getNumberOfHashFunctions() {
		return hashFunctions.length;
	}

	/**
	 * <p>Calculate the false positive rate (p).</p>
	 * @param m the length of the Bloom Filter
	 * @param k the number of hash functions used by the Bloom Filter
	 * @param n the expected number of elements to be added
	 * @return the false positive rate (p)
	 * @throws IllegalArgumentException if the length, the number of hash functions or the expected number of elements
	 * is not valid
	 * @since 1.2.0
	 */
	public static double calculateFalsePositiveRate(final int m, final int k, final int n) {
		if (1 > m) {
			throw new IllegalArgumentException("Invalid length (m): " + m + " (greater than or equal to 1 expected)");
		}
		if (1 > k) {
			throw new IllegalArgumentException("Invalid number of hash functions (k): " + k + " (greater than or equal to 1 expected)");
		}
		if (0 > n) {
			throw new IllegalArgumentException("Invalid expected number of elements (n): " + n + " (greater than or equal to 0 expected)");
		}
		return Math.pow(1.0d - Math.exp(-k * n / (double) m), k);
	}

	/**
	 * <p>Calculate the optimal length of the Bloom Filter (m).</p>
	 * @param n the expected number of elements to be added
	 * @param p the acceptable false positive rate (between {@code 0} and {@code 1})
	 * @return the optimal length (m)
	 * @throws IllegalArgumentException if the expected number of elements or the acceptable false positive rate is not
	 * valid
	 * @since 1.2.0
	 */
	public static double calculateOptionalLength(final int n, final double p) {
		if (0 > n) {
			throw new IllegalArgumentException("Invalid expected number of elements (n): " + n + " (greater than or equal to 0 expected)");
		}
		if (0.0d > p || 1.0d < p) {
			throw new IllegalArgumentException("Invalid acceptable false positive rate (p): " + p + " (between 0 and 1 expected)");
		}
		return Math.abs(-n * Math.log(p) / Math.pow(Math.log(2.0d), 2.0d));
	}

	/**
	 * <p>Calculate the optimal number of hash functions used by the Bloom Filter (k).</p>
	 * @param m the length of the Bloom Filter
	 * @param n the expected number of elements to be added
	 * @return the optimal number of hash functions (k)
	 * @throws IllegalArgumentException if the length or the expected number of elements is not valid
	 * @since 1.2.0
	 */
	public static double calculateOptionalNumberOfHashFunctions(final int m, final int n) {
		if (1 > m) {
			throw new IllegalArgumentException("Invalid length (m): " + m + " (greater than or equal to 1 expected)");
		}
		if (0 > n) {
			throw new IllegalArgumentException("Invalid expected number of elements (n): " + n + " (greater than or equal to 0 expected)");
		}
		if (0 == n) {
			return 0.0d;
		}
		return m / (double) n * Math.log(2.0d);
	}
}