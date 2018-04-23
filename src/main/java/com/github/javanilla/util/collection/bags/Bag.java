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
package com.github.javanilla.util.collection.bags;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * <p>A {@code Bag} is a collection that associate a quantity to each distinct element. It could be use for structures
 * such as histograms or occurrence vectors.</p>
 * <p><b>Note</b>: {@code Bag} does not extend the {@link Collection} interface for API design reasons.</p>
 * @param <E> the element type
 * @since 1.0
 */
public interface Bag<E> {

	/**
	 * <p>Add the element to the {@code Bag} once.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to add
	 * @return {@code true} if the element has been successfully added
	 * @since 1.0
	 */
	default boolean add(final E element) {
		return add(element, 1L);
	}

	/**
	 * <p>Add the element to the {@code Bag} in the given quantity.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to add
	 * @param quantity how many instances of the element should be added
	 * @return {@code true} if the element has been successfully added
	 * @throws IllegalArgumentException if the quantity is negative
	 * @since 1.0
	 */
	boolean add(final E element, final long quantity);

	/**
	 * <p>Remove the element from the {@code Bag} once.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to remove
	 * @return {@code true} if the element has been successfully removed
	 * @since 1.0
	 */
	default boolean remove(final E element) {
		return remove(element, 1L);
	}

	/**
	 * <p>Remove the element from the {@code Bag} in the given quantity. If the quantity is greater than the actual one
	 * then the element is totally removed.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to remove
	 * @param quantity how many instances of the element should be removed
	 * @return {@code true} if the element has been successfully removed
	 * @throws IllegalArgumentException if the quantity is negative
	 * @since 1.0
	 */
	boolean remove(final E element, final long quantity);

	/**
	 * <p>Totally remove the element from the {@code Bag}.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to remove
	 * @return {@code true} if the element has been successfully removed
	 * @since 1.0
	 */
	default boolean removeAll(final E element) {
		return remove(element, Long.MAX_VALUE);
	}

	/**
	 * <p>Clear the {@code Bag} by removing all contained elements.</p>
	 * @since 1.0
	 */
	void clear();

	/**
	 * <p>Tell if the {@code Bag} is empty in case it contains no element.</p>
	 * @return {@code true} if the {@code Bag} is empty
	 * @since 1.0
	 */
	default boolean isEmpty() {
		return 0L == size();
	}

	/**
	 * <p>Tell if the {@code Bag} contains the element at least once.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to test
	 * @return {@code true} if the {@code Bag} contains the element at least once
	 * @since 1.0
	 */
	default boolean containsAny(final E element) {
		return containsAtLeast(element, 1L);
	}

	/**
	 * <p>Tell if the {@code Bag} contains the element exactly in the given quantity.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to test
	 * @param quantity the quantity to check
	 * @return {@code true} if the {@code Bag} contains the element exactly in the given quantity
	 * @throws IllegalArgumentException if the quantity is negative
	 * @since 1.0
	 */
	default boolean containsExactly(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
		return quantity == count(element);
	}

	/**
	 * <p>Tell if the {@code Bag} contains the element at least in the given quantity.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to test
	 * @param quantity the quantity to check
	 * @return {@code true} if the {@code Bag} contains the element at least in the given quantity
	 * @throws IllegalArgumentException if the quantity is negative
	 * @since 1.0
	 */
	default boolean containsAtLeast(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
		if (0L == quantity) {
			return true;
		}
		return quantity <= count(element);
	}

	/**
	 * <p>Tell if the {@code Bag} contains the element at most in the given quantity.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to test
	 * @param quantity the quantity to check
	 * @return {@code true} if the {@code Bag} contains the element at most in the given quantity
	 * @throws IllegalArgumentException if the quantity is negative
	 * @since 1.0
	 */
	default boolean containsAtMost(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
		return quantity >= count(element);
	}

	/**
	 * <p>Count the actual quantity of the element.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to count
	 * @return the actual quantity
	 * @since 1.0
	 */
	long count(final E element);

	/**
	 * <p>Get the number of distinct elements.</p>
	 * @return the number of distinct elements
	 * @since 1.0
	 */
	long distinct();

	/**
	 * <p>Get the total size of the {@code Bag}.</p>
	 * @return the total size of the {@code Bag}
	 * @since 1.0
	 */
	long size();

	/**
	 * <p>Get an {@code Optional} of the element with the minimum quantity.</p>
	 * @return an {@code Optional} of the element with the minimum quantity
	 * @since 1.0
	 */
	Optional<E> min();

	/**
	 * <p>Get an {@code Optional} of the element with the maximum quantity.</p>
	 * @return an {@code Optional} of the element with the maximum quantity
	 * @since 1.0
	 */
	Optional<E> max();

	/**
	 * <p>Create a {@code Set} with distinct elements from the {@code Bag}.</p>
	 * @return the created {@code Set}
	 * @since 1.0
	 */
	Set<E> toSet();

	/**
	 * <p>Create a {@code Map} which associates elements from the {@code Bag} to their quantity.</p>
	 * @return the created {@code Map}
	 * @since 1.0
	 */
	Map<E, Long> toMap();
}