/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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
 * <p>An {@link Iterator} whose next element is prepared when the last one is returned. The end is reached when the next
 * element is not valid based on {@link #isValid(Object)}.</p>
 * @param <E> the element type
 * @since 1.0.0
 */
public abstract class PreparedIterator<E> implements Iterator<E> {

	/**
	 * <p>Whether the next element has been initialized or not.</p>
	 * @since 1.0.0
	 */
	private boolean initialized = false;

	/**
	 * <p>Prepared next element.</p>
	 * @since 1.0.0
	 */
	private E next;

	/**
	 * <p>Constructor.</p>
	 * @since 1.7.0
	 */
	protected PreparedIterator() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean hasNext() {
		if (!initialized) {
			next = prepareNext();
			initialized = true;
		}
		return isValid(next);
	}

	/**
	 * <p>Indicates if the prepared next element is valid, if not there is no more element.</p>
	 * @param next the prepared next element
	 * @return {@code true} if the prepared next element is valid
	 * @since 1.0.0
	 */
	protected abstract boolean isValid(final E next);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		final var current = next;
		next = prepareNext();
		return current;
	}

	/**
	 * <p>Return the next element to be returned after the current one.</p>
	 * <p><b>Note</b>: This method should not be called by the inherited class.</p>
	 * @return the next element, valid or not
	 * @since 1.0.0
	 */
	protected abstract E prepareNext();
}