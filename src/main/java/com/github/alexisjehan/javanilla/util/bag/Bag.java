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
package com.github.alexisjehan.javanilla.util.bag;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.util.NullableOptional;

import java.util.Map;
import java.util.Set;

/**
 * A {@link Bag} also known as a multiset is a collection that associates a quantity to each distinct element. It could
 * be used for structures such as histograms or occurrence vectors.
 *
 * <p><b>Note</b>: {@link Bag} does not extend the {@link java.util.Collection} interface for API design reasons.</p>
 * @param <E> the element type
 * @see <a href="https://en.wikipedia.org/wiki/Multiset">https://en.wikipedia.org/wiki/Multiset</a>
 * @since 1.8.0
 */
public interface Bag<E> {

	/**
	 * Add the element to the {@code Bag} once.
	 *
	 * <p><b>Note</b>: A {@code null} element may be restricted depending of the implementation.</p>
	 * @param element the element to add
	 * @since 1.8.0
	 */
	default void add(final E element) {
		add(element, 1L);
	}

	/**
	 * Add the element to the {@code Bag} in the given quantity.
	 *
	 * <p><b>Note</b>: A {@code null} element may be restricted depending of the implementation.</p>
	 * @param element the element to add
	 * @param quantity the quantity of the element to add
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.8.0
	 */
	void add(E element, long quantity);

	/**
	 * Remove the element from the {@code Bag} once.
	 * @param element the element to remove
	 * @return {@code true} if the element has been successfully removed
	 * @since 1.8.0
	 */
	default boolean remove(final E element) {
		return remove(element, 1L);
	}

	/**
	 * Remove the element from the {@code Bag} in the given quantity. If the quantity is greater than the actual one
	 * then the element is totally removed.
	 * @param element the element to remove
	 * @param quantity the quantity of the element to remove
	 * @return {@code true} if the element has been successfully removed
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.8.0
	 */
	boolean remove(E element, long quantity);

	/**
	 * Totally remove the element from the {@code Bag}.
	 * @param element the element to remove
	 * @return {@code true} if the element has been successfully removed
	 * @since 1.8.0
	 */
	default boolean removeAll(final E element) {
		return remove(element, Long.MAX_VALUE);
	}

	/**
	 * Clear the {@code Bag} by removing all contained elements.
	 * @since 1.8.0
	 */
	void clear();

	/**
	 * Tell if the {@code Bag} is empty, in case it contains no element.
	 * @return {@code true} if the {@code Bag} is empty
	 * @since 1.8.0
	 */
	default boolean isEmpty() {
		return 0L == size();
	}

	/**
	 * Tell if the {@code Bag} contains the element at least once.
	 * @param element the element to test
	 * @return {@code true} if the {@code Bag} contains the element at least once
	 * @since 1.8.0
	 */
	default boolean containsAny(final E element) {
		return containsAtLeast(element, 1L);
	}

	/**
	 * Tell if the {@code Bag} contains the element exactly in the given quantity.
	 * @param element the element to test
	 * @param quantity the quantity to test
	 * @return {@code true} if the {@code Bag} contains the element exactly in the given quantity
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.8.0
	 */
	default boolean containsExactly(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		return quantity == count(element);
	}

	/**
	 * Tell if the {@code Bag} contains the element at least in the given quantity.
	 * @param element the element to test
	 * @param quantity the quantity to test
	 * @return {@code true} if the {@code Bag} contains the element at least in the given quantity
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.8.0
	 */
	default boolean containsAtLeast(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		if (0L == quantity) {
			return true;
		}
		return quantity <= count(element);
	}

	/**
	 * Tell if the {@code Bag} contains the element at most in the given quantity.
	 * @param element the element to test
	 * @param quantity the quantity to test
	 * @return {@code true} if the {@code Bag} contains the element at most in the given quantity
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.8.0
	 */
	default boolean containsAtMost(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		return quantity >= count(element);
	}

	/**
	 * Count the actual quantity of the element.
	 * @param element the element to count
	 * @return the actual quantity of the element
	 * @since 1.8.0
	 */
	long count(E element);

	/**
	 * Get the number of distinct elements.
	 * @return the number of distinct elements
	 * @since 1.8.0
	 */
	long distinct();

	/**
	 * Get the total size of the {@code Bag}.
	 * @return the total size of the {@code Bag}
	 * @since 1.8.0
	 */
	long size();

	/**
	 * Get a {@link NullableOptional} of the element with the minimum quantity.
	 * @return a {@link NullableOptional} of the element with the minimum quantity
	 * @since 1.8.0
	 */
	NullableOptional<E> min();

	/**
	 * Get a {@link NullableOptional} of the element with the maximum quantity.
	 * @return a {@link NullableOptional} of the element with the maximum quantity
	 * @since 1.8.0
	 */
	NullableOptional<E> max();

	/**
	 * Create a {@link Set} with distinct elements from the {@code Bag}.
	 * @return the created {@link Set}
	 * @since 1.8.0
	 */
	Set<E> toSet();

	/**
	 * Create a {@link Map} which associates elements from the {@code Bag} to their quantity.
	 * @return the created {@link Map}
	 * @since 1.8.0
	 */
	Map<E, Long> toMap();
}