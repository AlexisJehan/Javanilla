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

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <p>An {@link Iterator} decorator that returns elements in batch from the current position.</p>
 * @param <E> the element type
 * @since 1.0.0
 */
public final class BatchIterator<E> implements Iterator<List<E>> {

	/**
	 * <p>Delegated {@link Iterator}.</p>
	 * @since 1.0.0
	 */
	private final Iterator<? extends E> iterator;

	/**
	 * <p>Size of the batch.</p>
	 * <p><b>Note</b>: The last one could be smaller.</p>
	 * @since 1.0.0
	 */
	private final int batchSize;

	/**
	 * <p>{@link List} that contains batch elements.</p>
	 * @since 1.0.0
	 */
	private final List<E> batch;

	/**
	 * <p>Constructor with an {@link Iterator} to decorate and a batch size.</p>
	 * @param iterator the {@link Iterator} to decorate
	 * @param batchSize the batch size
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @throws IllegalArgumentException if the batch size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public BatchIterator(final Iterator<? extends E> iterator, final int batchSize) {
		Ensure.notNull("iterator", iterator);
		Ensure.greaterThanOrEqualTo("batchSize", batchSize, 1);
		this.iterator = iterator;
		this.batchSize = batchSize;
		batch = new ArrayList<>(batchSize);
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
	public List<E> next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		batch.clear();
		for (var i = 0; i < batchSize && iterator.hasNext(); ++i) {
			batch.add(iterator.next());
		}
		return List.copyOf(batch);
	}

	/**
	 * <p>Get the batch size.</p>
	 * @return the batch size
	 * @since 1.0.0
	 */
	public int getBatchSize() {
		return batchSize;
	}
}