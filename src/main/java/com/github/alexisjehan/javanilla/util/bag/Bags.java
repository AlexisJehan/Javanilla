/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;
import com.github.alexisjehan.javanilla.util.NullableOptional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * A utility class that provides {@link Bag} tools.
 * @since 1.8.0
 */
public final class Bags {

	/**
	 * Class for an immutable singleton {@link Bag} with only one element in any quantity.
	 *
	 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and
	 * {@link #toString()} methods.</p>
	 * @param <E> the element type
	 * @since 1.8.0
	 */
	private static final class SingletonBag<E> implements Bag<E> {

		/**
		 * Single element of the {@link Bag}.
		 * @since 1.8.0
		 */
		private final E element;

		/**
		 * Quantity of the element.
		 * @since 1.8.0
		 */
		private final long quantity;

		/**
		 * Constructor with an element and its quantity.
		 * @param element the single element
		 * @param quantity the quantity of the element
		 * @since 1.8.0
		 */
		SingletonBag(final E element, final long quantity) {
			this.element = element;
			this.quantity = quantity;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void add(final E element) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void add(final E element, final long quantity) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean remove(final E element) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean remove(final E element, final long quantity) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean removeAll(final E element) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long count(final E element) {
			return Equals.equals(this.element, element) ? quantity : 0L;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long distinct() {
			return 1L;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long size() {
			return quantity;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public NullableOptional<E> min() {
			return NullableOptional.of(element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public NullableOptional<E> max() {
			return NullableOptional.of(element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			}
			if (!(object instanceof Bag)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			final var other = (Bag<Object>) object;
			return 1L == other.distinct()
					&& quantity == other.count(element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			return HashCode.of(
					HashCode.hashCode(element),
					HashCode.hashCode(quantity)
			);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "{" + element + "=" + quantity + "}";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Set<E> toSet() {
			return Collections.singleton(element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Map<E, Long> toMap() {
			return Collections.singletonMap(element, quantity);
		}
	}

	/**
	 * An immutable empty {@link Bag}.
	 * @since 1.8.0
	 */
	private static final Bag<?> EMPTY = new Bag<>() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void add(final Object element) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void add(final Object element, final long quantity) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean remove(final Object element) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean remove(final Object element, final long quantity) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean removeAll(final Object element) {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long count(final Object element) {
			return 0L;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long distinct() {
			return 0L;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long size() {
			return 0L;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public NullableOptional<Object> min() {
			return NullableOptional.empty();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public NullableOptional<Object> max() {
			return NullableOptional.empty();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			}
			if (!(object instanceof Bag)) {
				return false;
			}
			return ((Bag<?>) object).isEmpty();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			return 0;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "{}";
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Set<Object> toSet() {
			return Set.of();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Map<Object, Long> toMap() {
			return Map.of();
		}
	};

	/**
	 * Constructor.
	 * @since 1.8.0
	 */
	private Bags() {}

	/**
	 * Return an immutable empty {@link Bag}.
	 * @param <E> the element type
	 * @return an immutable empty {@link Bag}
	 * @since 1.8.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Bag<E> empty() {
		return (Bag<E>) EMPTY;
	}

	/**
	 * Wrap a {@link Bag} replacing {@code null} by an empty one.
	 * @param bag the {@link Bag} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@link Bag}
	 * @since 1.8.0
	 */
	public static <E> Bag<E> nullToEmpty(final Bag<E> bag) {
		return nullToDefault(bag, empty());
	}

	/**
	 * Wrap a {@link Bag} replacing {@code null} by a default one.
	 * @param bag the {@link Bag} or {@code null}
	 * @param defaultBag the default {@link Bag}
	 * @param <B> the {@link Bag} type
	 * @return a non-{@code null} {@link Bag}
	 * @throws NullPointerException if the default {@link Bag} is {@code null}
	 * @since 1.8.0
	 */
	public static <B extends Bag<?>> B nullToDefault(final B bag, final B defaultBag) {
		Ensure.notNull("defaultBag", defaultBag);
		return null != bag ? bag : defaultBag;
	}

	/**
	 * Wrap a {@link Bag} replacing an empty one by {@code null}.
	 * @param bag the {@link Bag} or {@code null}
	 * @param <B> the {@link Bag} type
	 * @return a non-empty {@link Bag} or {@code null}
	 * @since 1.8.0
	 */
	public static <B extends Bag<?>> B emptyToNull(final B bag) {
		return emptyToDefault(bag, null);
	}

	/**
	 * Wrap a {@link Bag} replacing an empty one by a default {@link Bag}.
	 * @param bag the {@link Bag} or {@code null}
	 * @param defaultBag the default {@link Bag} or {@code null}
	 * @param <B> the {@link Bag} type
	 * @return a non-empty {@link Bag} or {@code null}
	 * @throws IllegalArgumentException if the default {@link Bag} is empty
	 * @since 1.8.0
	 */
	public static <B extends Bag<?>> B emptyToDefault(final B bag, final B defaultBag) {
		if (null != defaultBag) {
			Ensure.notNullAndNotEmpty("defaultBag", defaultBag);
		}
		return null == bag || !bag.isEmpty() ? bag : defaultBag;
	}

	/**
	 * Decorate a {@link Bag} by returning an immutable view of it.
	 * @param bag the {@link Bag} to decorate
	 * @param <E> the element type
	 * @return an immutable view of the {@link Bag}
	 * @throws NullPointerException if the {@link Bag} is {@code null}
	 * @since 1.8.0
	 */
	public static <E> Bag<E> unmodifiable(final Bag<E> bag) {
		Ensure.notNull("bag", bag);
		return new FilterBag<>(bag) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void add(final E element) {
				throw new UnsupportedOperationException();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void add(final E element, final long quantity) {
				throw new UnsupportedOperationException();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean remove(final E element) {
				throw new UnsupportedOperationException();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean remove(final E element, final long quantity) {
				throw new UnsupportedOperationException();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean removeAll(final E element) {
				throw new UnsupportedOperationException();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void clear() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Create a {@link Bag} from a single element and a quantity of {@code 1}.
	 * @param element the element to convert
	 * @param <E> the element type
	 * @return the created {@link Bag}
	 * @since 1.8.0
	 */
	public static <E> Bag<E> singleton(final E element) {
		return singleton(element, 1L);
	}

	/**
	 * Create a {@link Bag} from a single element in the given quantity.
	 * @param element the element to convert
	 * @param quantity the quantity of the element
	 * @param <E> the element type
	 * @return the created {@link Bag}
	 * @throws IllegalArgumentException if the quantity is lower than {@code 0}
	 * @since 1.8.0
	 */
	public static <E> Bag<E> singleton(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		if (0 == quantity) {
			return empty();
		}
		return new SingletonBag<>(element, quantity);
	}

	/**
	 * Create an immutable {@link Bag} from multiple elements.
	 * @param elements the elements array to convert
	 * @param <E> the element type
	 * @return the created immutable {@link Bag}
	 * @throws NullPointerException if the elements array is {@code null}
	 * @since 1.8.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Bag<E> of(final E... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return empty();
		}
		if (1 == elements.length) {
			return singleton(elements[0]);
		}
		return unmodifiable(new MapBag<>(Arrays.asList(elements)));
	}
}