/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link Iterator} decorator that iterates only elements within a range from the current position.
 *
 * <p><b>Note</b>: A removed element is still considered in the index.</p>
 * @param <E> the element type
 * @since 1.8.0
 */
public final class RangeIterator<E> extends FilterIterator<E> {

	/**
	 * Inclusive index of the first element to iterate.
	 * @since 1.8.0
	 */
	private final long fromIndex;

	/**
	 * Inclusive index of the last element to iterate.
	 * @since 1.8.0
	 */
	private final long toIndex;

	/**
	 * Current index.
	 * @since 1.8.0
	 */
	private long index;

	/**
	 * Constructor with an {@link Iterator} to decorate and a range from an inclusive index to another one.
	 * @param iterator the {@link Iterator} to decorate
	 * @param fromIndex the inclusive index of the first element to iterate
	 * @param toIndex the inclusive index of the last element to iterate
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @throws IllegalArgumentException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.8.0
	 */
	public RangeIterator(final Iterator<? extends E> iterator, final long fromIndex, final long toIndex) {
		super(iterator);
		Ensure.greaterThanOrEqualTo("fromIndex", fromIndex, 0L);
		Ensure.greaterThanOrEqualTo("toIndex", toIndex, fromIndex);
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		while (fromIndex > index && iterator.hasNext()) {
			++index;
			iterator.next();
		}
		if (toIndex < index) {
			return false;
		}
		return iterator.hasNext();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		++index;
		return iterator.next();
	}

	/**
	 * Get the inclusive index of the first element to iterate.
	 * @return the inclusive starting index
	 * @since 1.8.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * Get the inclusive index of the last element to iterate.
	 * @return the inclusive ending index
	 * @since 1.8.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}