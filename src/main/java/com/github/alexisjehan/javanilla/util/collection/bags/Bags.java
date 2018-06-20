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
package com.github.alexisjehan.javanilla.util.collection.bags;

import com.github.alexisjehan.javanilla.util.NullableOptional;

import java.util.*;

/**
 * <p>An utility class that provides {@link Bags} tools.</p>
 * @since 1.0.0
 */
public final class Bags {

	/**
	 * <p>{@code SingletonBag} is an immutable {@code Bag} with only one element with any quantity.</p>
	 * <p><b>Note</b>: This class implements its own {@link #equals(Object)} and {@link #hashCode()} methods.</p>
	 * @param <E> the element type
	 * @since 1.0.0
	 */
	private static class SingletonBag<E> implements Bag<E> {

		/**
		 * <p>The single element of the {@code Bag}.</p>
		 * @since 1.0.0
		 */
		private final E element;

		/**
		 * <p>The element's quantity.</p>
		 * @since 1.0.0
		 */
		private final long quantity;

		/**
		 * <p>Constructor with an element and its quantity.</p>
		 * @param element the single element
		 * @param quantity the element's quantity
		 * @since 1.0.0
		 */
		private SingletonBag(final E element, final long quantity) {
			this.element = element;
			this.quantity = quantity;
		}

		@Override
		public boolean add(final E element, final long quantity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(final E element, final long quantity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Override
		public long count(final E element) {
			return Objects.equals(this.element, element) ? quantity : 0L;
		}

		@Override
		public long distinct() {
			return 1L;
		}

		@Override
		public long size() {
			return quantity;
		}

		@Override
		public NullableOptional<E> min() {
			return NullableOptional.of(element);
		}

		@Override
		public NullableOptional<E> max() {
			return NullableOptional.of(element);
		}

		@Override
		public Set<E> toSet() {
			return Collections.singleton(element);
		}

		@Override
		public Map<E, Long> toMap() {
			return Collections.singletonMap(element, quantity);
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			}
			if (!(object instanceof Bag)) {
				return false;
			}
			final var other = (Bag) object;
			return 1L == other.distinct()
					&& quantity == other.count(element);
		}

		@Override
		public int hashCode() {
			return Objects.hash(element, quantity);
		}

		@Override
		public String toString() {
			return "{" + element + "=" + quantity + "}";
		}
	}

	/**
	 * <p>An empty immutable {@code Bag}.</p>
	 * @since 1.0.0
	 */
	private static final Bag EMPTY = new Bag() {
		@Override
		public boolean add(final Object element, final long quantity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(final Object element, final long quantity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Override
		public long count(final Object element) {
			return 0L;
		}

		@Override
		public long distinct() {
			return 0L;
		}

		@Override
		public long size() {
			return 0L;
		}

		@Override
		public NullableOptional min() {
			return NullableOptional.empty();
		}

		@Override
		public NullableOptional max() {
			return NullableOptional.empty();
		}

		@Override
		public Set toSet() {
			return Collections.emptySet();
		}

		@Override
		public Map toMap() {
			return Collections.emptyMap();
		}

		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			}
			if (!(object instanceof Bag)) {
				return false;
			}
			return ((Bag) object).isEmpty();
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public String toString() {
			return "{}";
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Bags() {
		// Not available
	}

	/**
	 * <p>Return an empty immutable {@code Bag}.</p>
	 * @param <E> the element type
	 * @return An empty immutable {@code Bag}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Bag<E> empty() {
		return (Bag<E>) EMPTY;
	}

	/**
	 * <p>Wrap a {@code Bag} replacing {@code null} by an empty {@code Bag}.</p>
	 * @param bag a {@code Bag} or {@code null}
	 * @param <E> the element type
	 * @return the non-{@code null} {@code Bag}
	 * @since 1.0.0
	 */
	public static <E> Bag<E> nullToEmpty(final Bag<E> bag) {
		return nullToDefault(bag, empty());
	}

	/**
	 * <p>Wrap a {@code Bag} replacing {@code null} by a default {@code Bag}.</p>
	 * @param bag a {@code Bag} or {@code null}
	 * @param defaultBag the default {@code Bag}
	 * @param <E> the element type
	 * @return the non-{@code null} {@code Bag}
	 * @throws NullPointerException if the default {@code Bag} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> Bag<E> nullToDefault(final Bag<E> bag, final Bag<E> defaultBag) {
		if (null == defaultBag) {
			throw new NullPointerException("Invalid default bag (not null expected)");
		}
		return null != bag ? bag : defaultBag;
	}

	/**
	 * <p>Wrap a {@code Bag} replacing an empty one by {@code null}.</p>
	 * @param bag a {@code Bag} or {@code null}
	 * @param <E> the element type
	 * @return the non-empty {@code Bag} or {@code null}
	 * @since 1.0.0
	 */
	public static <E> Bag<E> emptyToNull(final Bag<E> bag) {
		return emptyToDefault(bag, null);
	}

	/**
	 * <p>Wrap a {@code Bag} replacing an empty one by a default {@code Bag}.</p>
	 * @param bag a {@code Bag} or {@code null}
	 * @param defaultBag the default {@code Bag} or {@code null}
	 * @param <E> the element type
	 * @return the non-empty {@code Bag} or {@code null}
	 * @throws IllegalArgumentException if the default {@code Bag} is empty
	 * @since 1.1.0
	 */
	public static <E> Bag<E> emptyToDefault(final Bag<E> bag, final Bag<E> defaultBag) {
		if (null != defaultBag && defaultBag.isEmpty()) {
			throw new IllegalArgumentException("Invalid default bag (not empty expected)");
		}
		if (null == bag) {
			return null;
		}
		return !bag.isEmpty() ? bag : defaultBag;
	}

	/**
	 * <p>Wrap a {@code Bag} by returning an immutable view of it.</p>
	 * @param bag the {@code Bag} to wrap
	 * @param <E> the element type
	 * @return an immutable view of the {@code Bag}
	 * @throws NullPointerException if the {@code Bag} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Bag<E> unmodifiable(final Bag<E> bag) {
		if (null == bag) {
			throw new NullPointerException("Invalid bag (not null expected)");
		}
		return new FilterBag<>(bag) {
			@Override
			public boolean add(final E element, final long quantity) {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean remove(final E element, final long quantity) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void clear() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * <p>Create a singleton {@code Bag} with the single element once.</p>
	 * @param element the single element
	 * @param <E> the element type
	 * @return the created singleton {@code Bag}
	 * @since 1.0.0
	 */
	public static <E> Bag<E> singleton(final E element) {
		return singleton(element, 1L);
	}

	/**
	 * <p>Create a singleton {@code Bag} with the single element in the given quantity.</p>
	 * @param element the single element
	 * @param quantity the element's quantity
	 * @param <E> the element type
	 * @return the created singleton {@code Bag}
	 * @throws IllegalArgumentException if the quantity is negative
	 * @since 1.0.0
	 */
	public static <E> Bag<E> singleton(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
		if (0 == quantity) {
			return empty();
		}
		return new SingletonBag<>(element, quantity);
	}

	/**
	 * <p>Create an immutable {@code Bag} with given elements.</p>
	 * @param elements {@code Bag}'s elements
	 * @param <E> the element type
	 * @return the created immutable {@code Bag}
	 * @throws NullPointerException if the elements {@code array} is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> Bag<E> of(final E... elements) {
		if (null == elements) {
			throw new NullPointerException("Invalid elements (not null expected)");
		}
		return unmodifiable(new MapBag<>(Arrays.asList(elements)));
	}
}