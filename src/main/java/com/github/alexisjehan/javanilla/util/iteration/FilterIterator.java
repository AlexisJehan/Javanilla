/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>An abstract {@link Iterator} filter to create decorators from the current position.</p>
 * @param <E> the element type
 * @since 1.2.0
 */
public abstract class FilterIterator<E> implements Iterator<E> {

	/**
	 * <p>Delegated {@link Iterator}.</p>
	 * @since 1.2.0
	 */
	protected final Iterator<? extends E> iterator;

	/**
	 * <p>Constructor with an {@link Iterator} to decorate.</p>
	 * @param iterator the {@link Iterator} to decorate
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.2.0
	 */
	protected FilterIterator(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		this.iterator = iterator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
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
		return iterator.next();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove() {
		iterator.remove();
	}
}