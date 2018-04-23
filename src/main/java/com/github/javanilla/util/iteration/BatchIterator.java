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
package com.github.javanilla.util.iteration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <p>An {@link Iterator} decorator that returns elements in batch from the current position.</p>
 * @param <E> the element type
 * @since 1.0
 */
public final class BatchIterator<E> implements Iterator<List<E>> {

	/**
	 * <p>Delegated {@code Iterator}.</p>
	 * @since 1.0
	 */
	private final Iterator<? extends E> iterator;

	/**
	 * <p>Maximum size of the batch.</p>
	 * @since 1.0
	 */
	private final int batchSize;

	/**
	 * <p>List that contains batch elements.</p>
	 * @since 1.0
	 */
	private final List<E> batch;

	/**
	 * <p>Constructor with a delegated {@code Iterator} and a batch size.</p>
	 * @param iterator the delegated {@code Iterator}
	 * @param batchSize the batch size
	 * @throws NullPointerException if the delegated {@code Iterator} is {@code null}
	 * @throws IllegalArgumentException if the batch size is lower than {@code 1}
	 * @since 1.0
	 */
	public BatchIterator(final Iterator<? extends E> iterator, final int batchSize) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		if (1 > batchSize) {
			throw new IllegalArgumentException("Invalid batch size: " + batchSize + " (greater than or equal to 1 expected");
		}
		this.iterator = iterator;
		this.batchSize = batchSize;
		batch = new ArrayList<>(batchSize);
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public List<E> next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		batch.clear();
		for (var i = 0; i < batchSize && iterator.hasNext(); ++i) {
			batch.add(iterator.next());
		}
		return Collections.unmodifiableList(batch);
	}

	/**
	 * <p>Get the maximum batch size.</p>
	 * @return the maximum batch size
	 * @since 1.0
	 */
	public int getBatchSize() {
		return batchSize;
	}
}