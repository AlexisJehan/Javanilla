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
package com.github.alexisjehan.javanilla.util;

import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;
import com.github.alexisjehan.javanilla.misc.quality.ToString;
import com.github.alexisjehan.javanilla.misc.tuple.Pair;

/**
 * <p>An element from an {@link java.util.Iterator} returned by {@link Iterables#index(Iterable)} or
 * {@link Iterators#index(java.util.Iterator)} which is associated to an index.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <E> the element type
 * @since 1.8.0
 */
public final class IndexedElement<E> {

	/**
	 * <p>Associated index.</p>
	 * @since 1.8.0
	 */
	private final long index;

	/**
	 * <p>Nullable element.</p>
	 * @since 1.8.0
	 */
	private final E element;

	/**
	 * <p>Constructor with an index and an element.</p>
	 * @param index the index of the element
	 * @param element the element or {@code null}
	 * @since 1.8.0
	 */
	IndexedElement(final long index, final E element) {
		this.index = index;
		this.element = element;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof IndexedElement)) {
			return false;
		}
		final var other = (IndexedElement<?>) object;
		return Equals.equals(index, other.index)
				&& Equals.equals(element, other.element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.of(
				HashCode.hashCode(index),
				HashCode.hashCode(element)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ToString.of(
				this,
				Pair.of("index", ToString.toString(index)),
				Pair.of("element", ToString.toString(element))
		);
	}

	/**
	 * <p>Get the index of the element.</p>
	 * @return the index
	 * @since 1.8.0
	 */
	public long getIndex() {
		return index;
	}

	/**
	 * <p>Get the element.</p>
	 * @return the element or {@code null}
	 * @since 1.8.0
	 */
	public E getElement() {
		return element;
	}
}