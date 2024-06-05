/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.collection.bags;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

/**
 * <p>A {@link Bag} decorator that limits the maximum number of distinct elements contained. If the limit is reached
 * then an element with a minimum occurrence is totally removed.</p>
 * <p><b>Note</b>: This class implements its own {@link #toString()} method.</p>
 * @param <E> the element type
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.bag.LimitedBag} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class LimitedBag<E> extends FilterBag<E> {

	/**
	 * <p>Maximum number of distinct elements to be contained.</p>
	 * @since 1.0.0
	 */
	private final int limit;

	/**
	 * <p>Constructor with a {@link Bag} to decorate and a limit.</p>
	 * @param bag the {@link Bag} to decorate
	 * @param limit the maximum number of distinct elements to be contained
	 * @throws IllegalArgumentException if the limit is lower than {@code 2}
	 * @since 1.0.0
	 */
	public LimitedBag(final Bag<E> bag, final int limit) {
		super(bag);
		Ensure.greaterThanOrEqualTo("limit", limit, 2L);
		this.limit = limit;
		while (limit < distinct()) {
			remove(min().orElseThrow(AssertionError::new));
		}
	}

	/**
	 * <p>Add the element to the {@link Bag} in the given quantity.</p>
	 * <p><b>Note</b>: If the limit is reached then an element with a minimum occurrence is totally removed.</p>
	 * <p><b>Note</b>: A {@code null} element may be restricted depending of the implementation.</p>
	 * @param element the element to add
	 * @param quantity the quantity of the element to add
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.0.0
	 */
	@Override
	public void add(final E element, final long quantity) {
		if (!containsAny(element) && limit <= distinct()) {
			remove(min().orElseThrow(AssertionError::new));
		}
		super.add(element, quantity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString() + "[" + limit + "]";
	}

	/**
	 * <p>Get the limit.</p>
	 * @return the limit
	 * @since 1.6.0
	 */
	public int getLimit() {
		return limit;
	}
}