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
package com.github.alexisjehan.javanilla.util.iteration;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>An {@link Iterator} decorator that iterates only over elements within a range from the current position.</p>
 * <p><b>Note</b>: A removed element is still considered in the index.</p>
 * @param <E> the element type
 * @since 1.0.0
 */
public class RangeIterator<E> implements Iterator<E> {

	/**
	 * <p>Delegated {@code Iterator}.</p>
	 * @since 1.0.0
	 */
	private final Iterator<? extends E> iterator;

	/**
	 * <p>The inclusive index of the first element to iterate.</p>
	 * @since 1.0.0
	 */
	private final long fromIndex;

	/**
	 * <p>The inclusive index of the last element to iterate.<p>
	 * @since 1.0.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.0.0
	 */
	private long index = 0L;

	/**
	 * <p>Constructor with a delegated {@code Iterator} and a range from {@code 0} to the given inclusive index.</p>
	 * @param iterator the delegated {@code Iterator}
	 * @param toIndex the inclusive index of the last element to iterate
	 * @throws NullPointerException if the delegated {@code Iterator} is {@code null}
	 * @throws IndexOutOfBoundsException if the index is negative
	 * @since 1.0.0
	 */
	public RangeIterator(final Iterator<? extends E> iterator, final long toIndex) {
		this(iterator, 0L, toIndex);
	}

	/**
	 * <p>Constructor with a delegated {@code Iterator} and a range from an inclusive index to another one.</p>
	 * @param iterator the delegated {@code Iterator}
	 * @param fromIndex the inclusive index of the first element to iterate
	 * @param toIndex the inclusive index of the last element to iterate
	 * @throws NullPointerException if the delegated {@code Iterator} is {@code null}
	 * @throws IndexOutOfBoundsException whether the starting index is negative or greater than the ending one
	 * @since 1.0.0
	 */
	public RangeIterator(final Iterator<? extends E> iterator, final long fromIndex, final long toIndex) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		if (0L > fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (greater than or equal to 0 expected)");
		}
		if (fromIndex > toIndex) {
			throw new IndexOutOfBoundsException("Invalid to index: " + toIndex + " (greater than or equal to the from index expected)");
		}
		this.iterator = iterator;
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

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

	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		++index;
		return iterator.next();
	}

	@Override
	public void remove() {
		iterator.remove();
	}

	/**
	 * <p>Get the inclusive index of the first element to iterate.</p>
	 * @return the inclusive index
	 * @since 1.0.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last element to iterate.<p>
	 * @return the inclusive index
	 * @since 1.0.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}