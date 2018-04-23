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

import java.util.Objects;

/**
 * <p>A {@link Bag} decorator that limits the maximum number of distinct elements contained. If the limit is reached
 * then an element with a minimum occurrence is totally removed.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)} and {@link #hashCode()} methods.</p>
 * @param <E> the element type
 * @since 1.0
 */
public final class LimitedBag<E> extends FilterBag<E> {

	/**
	 * <p>The maximum number of distinct elements to be contained.</p>
	 * @since 1.0
	 */
	private final int limit;

	/**
	 * <p>Constructor with a delegated {@code Bag} and a limit.</p>
	 * @param bag the delegated {@code Bag}
	 * @param limit the maximum number of distinct elements to be contained
	 * @throws IllegalArgumentException if the limit if lower than 2
	 * @since 1.0
	 */
	public LimitedBag(final Bag<E> bag, final int limit) {
		super(bag);
		if (2 > limit) {
			throw new IllegalArgumentException("Invalid limit: " + limit + " (greater than or equal to 2 expected)");
		}
		this.limit = limit;
		while (limit < bag.distinct()) {
			bag.remove(bag.min().orElseThrow(AssertionError::new));
		}
	}

	/**
	 * <p>Add the element to the {@code Bag} in the given quantity.</p>
	 * <p><b>Note</b>: If the limit is reached then an element with a minimum occurrence is totally removed.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the implementation.</p>
	 * @param element the element to add
	 * @param quantity how many instances of the element should be added
	 * @return {@code true} if the element has been successfully added
	 * @throws IllegalArgumentException if the quantity is negative
	 * @since 1.0
	 */
	@Override
	public boolean add(final E element, final long quantity) {
		if (!bag.containsAny(element) && limit <= bag.distinct()) {
			bag.remove(bag.min().orElseThrow(AssertionError::new));
		}
		return bag.add(element, quantity);
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof LimitedBag)) {
			return false;
		}
		final var other = (LimitedBag) object;
		return Objects.equals(bag, other.bag)
				&& Objects.equals(limit, other.limit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bag, limit);
	}

	@Override
	public String toString() {
		return bag.toString() + '[' + limit + ']';
	}
}