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
package com.github.alexisjehan.javanilla.util.bag;

import com.github.alexisjehan.javanilla.util.NullableOptional;

import java.util.Map;
import java.util.Set;

final class BagTest extends AbstractBagTest {

	@Override
	<E> Bag<E> newBag() {
		return new Bag<>() {
			private final Bag<E> delegate = new MapBag<>();

			@Override
			public void add(final E element, final long quantity) {
				delegate.add(element, quantity);
			}

			@Override
			public boolean remove(final E element, final long quantity) {
				return delegate.remove(element, quantity);
			}

			@Override
			public void clear() {
				delegate.clear();
			}

			@Override
			public long count(final E element) {
				return delegate.count(element);
			}

			@Override
			public long distinct() {
				return delegate.distinct();
			}

			@Override
			public long size() {
				return delegate.size();
			}

			@Override
			public NullableOptional<E> min() {
				return delegate.min();
			}

			@Override
			public NullableOptional<E> max() {
				return delegate.max();
			}

			@Override
			public boolean equals(final Object object) {
				return delegate.equals(object);
			}

			@Override
			public int hashCode() {
				return delegate.hashCode();
			}

			@Override
			public String toString() {
				return delegate.toString();
			}

			@Override
			public Set<E> toSet() {
				return delegate.toSet();
			}

			@Override
			public Map<E, Long> toMap() {
				return delegate.toMap();
			}
		};
	}
}