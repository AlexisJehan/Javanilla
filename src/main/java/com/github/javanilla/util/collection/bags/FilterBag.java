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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * <p>An abstract {@link Bag} filter to create decorators.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)} and {@link #hashCode()} methods.</p>
 * @param <E> the element type
 * @since 1.0
 */
public abstract class FilterBag<E> implements Bag<E> {

	/**
	 * <p>Delegated {@code Bag}.</p>
	 * @since 1.0
	 */
	protected final Bag<E> bag;

	/**
	 * <p>Constructor with a delegated {@code Bag}.</p>
	 * @param bag the delegated {@code Bag}
	 * @throws NullPointerException if the delegated {@code Bag} is {@code null}
	 * @since 1.0
	 */
	protected FilterBag(final Bag<E> bag) {
		if (null == bag) {
			throw new NullPointerException("Invalid bag (not null expected)");
		}
		this.bag = bag;
	}

	@Override
	public boolean add(final E element, final long quantity) {
		return bag.add(element, quantity);
	}

	@Override
	public boolean remove(final E element, final long quantity) {
		return bag.remove(element, quantity);
	}

	@Override
	public boolean removeAll(final E element) {
		return bag.removeAll(element);
	}

	@Override
	public void clear() {
		bag.clear();
	}

	@Override
	public boolean isEmpty() {
		return bag.isEmpty();
	}

	@Override
	public boolean containsAny(final E element) {
		return bag.containsAny(element);
	}

	@Override
	public boolean containsExactly(final E element, final long quantity) {
		return bag.containsExactly(element, quantity);
	}

	@Override
	public boolean containsAtLeast(final E element, final long quantity) {
		return bag.containsAtLeast(element, quantity);
	}

	@Override
	public boolean containsAtMost(final E element, final long quantity) {
		return bag.containsAtMost(element, quantity);
	}

	@Override
	public Optional<E> min() {
		return bag.min();
	}

	@Override
	public Optional<E> max() {
		return bag.max();
	}

	@Override
	public long count(final E element) {
		return bag.count(element);
	}

	@Override
	public long distinct() {
		return bag.distinct();
	}

	@Override
	public long size() {
		return bag.size();
	}

	@Override
	public Set<E> toSet() {
		return bag.toSet();
	}

	@Override
	public Map<E, Long> toMap() {
		return bag.toMap();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof FilterBag)) {
			return false;
		}
		final var other = (FilterBag) object;
		return Objects.equals(bag, other.bag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bag);
	}

	@Override
	public String toString() {
		return bag.toString();
	}
}