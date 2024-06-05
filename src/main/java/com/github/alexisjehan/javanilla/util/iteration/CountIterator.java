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
package com.github.alexisjehan.javanilla.util.iteration;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>An {@link Iterator} decorator that counts the number of elements iterated from the current position.</p>
 * <p><b>Note</b>: A removed element is still considered in the count.</p>
 * @param <E> the element type
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.CountIterator} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class CountIterator<E> extends FilterIterator<E> {

	/**
	 * <p>Number of elements iterated.</p>
	 * @since 1.0.0
	 */
	private long count;

	/**
	 * <p>Constructor with an {@link Iterator} to decorate.</p>
	 * @param iterator the {@link Iterator} to decorate
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public CountIterator(final Iterator<? extends E> iterator) {
		super(iterator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		++count;
		return iterator.next();
	}

	/**
	 * <p>Get the number of elements iterated.</p>
	 * @return the number of elements iterated
	 * @since 1.0.0
	 */
	public long getCount() {
		return count;
	}
}