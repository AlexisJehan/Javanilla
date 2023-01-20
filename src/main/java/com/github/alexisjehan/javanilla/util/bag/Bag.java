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
package com.github.alexisjehan.javanilla.util.bag;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.util.NullableOptional;

import java.util.Map;
import java.util.Set;

/**
 * <p>A {@link Bag} also known as a multiset is a collection that associates a quantity to each distinct element. It
 * could be use for structures such as histograms or occurrence vectors.</p>
 * <p><b>Note</b>: {@link Bag} does not extend the {@link java.util.Collection} interface for API design reasons.</p>
 * @param <E> the element type
 * @see <a href="https://en.wikipedia.org/wiki/Multiset">https://en.wikipedia.org/wiki/Multiset</a>
 * @since 1.8.0
 */
public interface Bag<E> {

	/**
	 * <p>Add the element to the {@code Bag} once.</p>
	 * <p><b>Note</b>: A {@code null} element may be restricted depending of the implementation.</p>
	 * @param element the element to add
	 * @since 1.8.0
	 */
	default void add(final E element) {
		add(element, 1L);
	}

	/**
	 * <p>Add the element to the {@code Bag} in the given quantity.</p>
	 * <p><b>Note</b>: A {@code null} element may be restricted depending of the implementation.</p>
	 * @param element the element to add
	 * @param quantity the quantity of the element to add
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.8.0
	 */
	void add(E element, long quantity);

	/**
	 * <p>Remove the element from the {@code Bag} once.</p>
	 * @param element the element to remove
	 * @return {@code true} if the element has been successfully removed
	 * @since 1.8.0
	 */
	default boolean remove(final E element) {
		return remove(element, 1L);
	}

	/**
	 * <p>Remove the element from the {@code Bag} in the given quantity. If the quantity is greater than the actual one
	 * then the element is totally removed.</p>
	 * @param element the element to remove
	 * @param quantity the quantity of the element to remove
	 * @return {@code true} if the element has been successfully removed
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.8.0
	 */
	boolean remove(E element, long quantity);

	/**
	 * <p>Totally remove the element from the {@code Bag}.</p>
	 * @param element the element to remove
	 * @return {@code true} if the element has been successfully removed
	 * @since 1.8.0
	 */
	default boolean removeAll(final E element) {
		return remove(element, Long.MAX_VALUE);
	}

	/**
	 * <p>Clear the {@code Bag} by removing all contained elements.</p>
	 * @since 1.8.0
	 */
	void clear();

	/**
	 * <p>Tell if the {@code Bag} is empty, in case it contains no element.</p>
	 * @return {@code true} if the {@code Bag} is empty
	 * @since 1.8.0
	 */
	default boolean isEmpty() {
		return 0L == size();
	}

	/**
	 * <p>Tell if the {@code Bag} contains the element at least once.</p>
	 * @param element the element to test
	 * @return {@code true} if the {@code Bag} contains the element at least once
	 * @since 1.8.0
	 */
	default boolean containsAny(final E element) {
		return containsAtLeast(element, 1L);
	}

	/**
	 * <p>Tell if the {@code Bag} contains the element exactly in the given quantity.</p>
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
	 * <p>Tell if the {@code Bag} contains the element at least in the given quantity.</p>
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
	 * <p>Tell if the {@code Bag} contains the element at most in the given quantity.</p>
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
	 * <p>Count the actual quantity of the element.</p>
	 * @param element the element to count
	 * @return the actual quantity of the element
	 * @since 1.8.0
	 */
	long count(E element);

	/**
	 * <p>Get the number of distinct elements.</p>
	 * @return the number of distinct elements
	 * @since 1.8.0
	 */
	long distinct();

	/**
	 * <p>Get the total size of the {@code Bag}.</p>
	 * @return the total size of the {@code Bag}
	 * @since 1.8.0
	 */
	long size();

	/**
	 * <p>Get a {@link NullableOptional} of the element with the minimum quantity.</p>
	 * @return a {@link NullableOptional} of the element with the minimum quantity
	 * @since 1.8.0
	 */
	NullableOptional<E> min();

	/**
	 * <p>Get a {@link NullableOptional} of the element with the maximum quantity.</p>
	 * @return a {@link NullableOptional} of the element with the maximum quantity
	 * @since 1.8.0
	 */
	NullableOptional<E> max();

	/**
	 * <p>Create a {@link Set} with distinct elements from the {@code Bag}.</p>
	 * @return the created {@link Set}
	 * @since 1.8.0
	 */
	Set<E> toSet();

	/**
	 * <p>Create a {@link Map} which associates elements from the {@code Bag} to their quantity.</p>
	 * @return the created {@link Map}
	 * @since 1.8.0
	 */
	Map<E, Long> toMap();
}