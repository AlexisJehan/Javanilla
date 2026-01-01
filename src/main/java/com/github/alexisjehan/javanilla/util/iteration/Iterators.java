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
package com.github.alexisjehan.javanilla.util.iteration;

import com.github.alexisjehan.javanilla.io.bytes.InputStreams;
import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.io.lines.LineReader;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.tuples.Single;
import com.github.alexisjehan.javanilla.util.NullableOptional;
import com.github.alexisjehan.javanilla.util.function.throwable.ThrowableSupplier;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A utility class that provides {@link Iterator} and {@link PrimitiveIterator} tools.
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.Iterators} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class Iterators {

	/**
	 * An {@link Iterator} that wraps multiple ones as a sequence.
	 * @param <E> the element type
	 * @since 1.0.0
	 */
	private static final class SequenceIterator<E> implements Iterator<E> {

		/**
		 * {@link Iterator} of {@link Iterator}s.
		 * @since 1.0.0
		 */
		private final Iterator<? extends Iterator<? extends E>> iterator;

		/**
		 * Current {@link Iterator}.
		 * @since 1.0.0
		 */
		private Iterator<? extends E> current;

		/**
		 * Constructor.
		 * @param iterator the {@link Iterator} of {@link Iterator}s
		 * @since 1.0.0
		 */
		SequenceIterator(final Iterator<? extends Iterator<? extends E>> iterator) {
			this.iterator = iterator;
			nextIterator();
		}

		/**
		 * Set the current {@link Iterator} as the next one from the {@link Iterator}.
		 * @since 1.0.0
		 */
		private void nextIterator() {
			if (iterator.hasNext()) {
				current = iterator.next();
			} else {
				current = null;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			if (null == current) {
				return false;
			}
			do {
				if (current.hasNext()) {
					return true;
				}
				nextIterator();
			} while (null != current);
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return current.next();
		}
	}

	/**
	 * An empty {@link PrimitiveIterator.OfInt}.
	 * @since 1.0.0
	 */
	public static final PrimitiveIterator.OfInt EMPTY_INT = new PrimitiveIterator.OfInt() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int nextInt() {
			throw new NoSuchElementException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			throw new IllegalStateException();
		}
	};

	/**
	 * An empty {@link PrimitiveIterator.OfLong}.
	 * @since 1.0.0
	 */
	public static final PrimitiveIterator.OfLong EMPTY_LONG = new PrimitiveIterator.OfLong() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long nextLong() {
			throw new NoSuchElementException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			throw new IllegalStateException();
		}
	};

	/**
	 * An empty {@link PrimitiveIterator.OfDouble}.
	 * @since 1.0.0
	 */
	public static final PrimitiveIterator.OfDouble EMPTY_DOUBLE = new PrimitiveIterator.OfDouble() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double nextDouble() {
			throw new NoSuchElementException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			throw new IllegalStateException();
		}
	};

	/**
	 * Constructor.
	 * @since 1.0.0
	 */
	private Iterators() {}

	/**
	 * Wrap a {@link PrimitiveIterator.OfInt} replacing {@code null} by an empty one.
	 * @param primitiveIterator the {@link PrimitiveIterator.OfInt} or {@code null}
	 * @return a non-{@code null} {@link PrimitiveIterator.OfInt}
	 * @since 1.2.0
	 */
	public static PrimitiveIterator.OfInt nullToEmpty(final PrimitiveIterator.OfInt primitiveIterator) {
		return nullToDefault(primitiveIterator, EMPTY_INT);
	}

	/**
	 * Wrap a {@link PrimitiveIterator.OfLong} replacing {@code null} by an empty one.
	 * @param primitiveIterator the {@link PrimitiveIterator.OfLong} or {@code null}
	 * @return a non-{@code null} {@link PrimitiveIterator.OfLong}
	 * @since 1.2.0
	 */
	public static PrimitiveIterator.OfLong nullToEmpty(final PrimitiveIterator.OfLong primitiveIterator) {
		return nullToDefault(primitiveIterator, EMPTY_LONG);
	}

	/**
	 * Wrap a {@link PrimitiveIterator.OfDouble} replacing {@code null} by an empty one.
	 * @param primitiveIterator the {@link PrimitiveIterator.OfDouble} or {@code null}
	 * @return a non-{@code null} {@link PrimitiveIterator.OfDouble}
	 * @since 1.2.0
	 */
	public static PrimitiveIterator.OfDouble nullToEmpty(final PrimitiveIterator.OfDouble primitiveIterator) {
		return nullToDefault(primitiveIterator, EMPTY_DOUBLE);
	}

	/**
	 * Wrap an {@link Iterator} replacing {@code null} by an empty one.
	 * @param iterator the {@link Iterator} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@link Iterator}
	 * @since 1.0.0
	 */
	public static <E> Iterator<E> nullToEmpty(final Iterator<E> iterator) {
		return nullToDefault(iterator, Collections.emptyIterator());
	}

	/**
	 * Wrap a {@link ListIterator} replacing {@code null} by an empty one.
	 * @param listIterator the {@link ListIterator} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@link ListIterator}
	 * @since 1.0.0
	 */
	public static <E> ListIterator<E> nullToEmpty(final ListIterator<E> listIterator) {
		return nullToDefault(listIterator, Collections.emptyListIterator());
	}

	/**
	 * Wrap an {@link Iterator} replacing {@code null} by a default one.
	 * @param iterator the {@link Iterator} or {@code null}
	 * @param defaultIterator the default {@link Iterator}
	 * @param <I> the {@link Iterator} type
	 * @return a non-{@code null} {@link Iterator}
	 * @throws NullPointerException if the default {@link Iterator} is {@code null}
	 * @since 1.1.0
	 */
	public static <I extends Iterator<?>> I nullToDefault(final I iterator, final I defaultIterator) {
		Ensure.notNull("defaultIterator", defaultIterator);
		return null != iterator ? iterator : defaultIterator;
	}

	/**
	 * Wrap an {@link Iterator} replacing an empty one by {@code null}.
	 * @param iterator the {@link Iterator} or {@code null}
	 * @param <I> the {@link Iterator} type
	 * @return a non-empty {@link Iterator} or {@code null}
	 * @since 1.0.0
	 */
	public static <I extends Iterator<?>> I emptyToNull(final I iterator) {
		return emptyToDefault(iterator, null);
	}

	/**
	 * Wrap an {@link Iterator} replacing an empty one by a default one.
	 * @param iterator the {@link Iterator} or {@code null}
	 * @param defaultIterator the default {@link Iterator} or {@code null}
	 * @param <I> the {@link Iterator} type
	 * @return a non-empty {@link Iterator} or {@code null}
	 * @throws IllegalArgumentException if the default {@link Iterator} is empty
	 * @since 1.1.0
	 */
	public static <I extends Iterator<?>> I emptyToDefault(final I iterator, final I defaultIterator) {
		if (null != defaultIterator) {
			Ensure.notNullAndNotEmpty("defaultIterator", defaultIterator);
		}
		return null == iterator || iterator.hasNext() ? iterator : defaultIterator;
	}

	/**
	 * Decorate an {@link Iterator} so that its {@link Iterator#remove()} method is not available.
	 * @param iterator the {@link Iterator} to decorate
	 * @param <E> the element type
	 * @return the unmodifiable {@link Iterator}
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterator<E> unmodifiable(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Collections.emptyIterator();
		}
		return new FilterIterator<>(iterator) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Decorate an {@link Iterator} so that it returns {@link IndexedElement}s composed of the index from the current
	 * position and the element.
	 * @param iterator the {@link Iterator} to decorate
	 * @param <E> the element type
	 * @return the indexed {@link Iterator}
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> Iterator<IndexedElement<E>> index(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Collections.emptyIterator();
		}
		return new Iterator<>() {

			/**
			 * Current index.
			 * @since 1.2.0
			 */
			private long index;

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
			public IndexedElement<E> next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return new IndexedElement<>(index++, iterator.next());
			}
		};
	}

	/**
	 * Decorate an {@link Iterator} so that its elements are filtered using the given {@link Predicate}.
	 * @param iterator the {@link Iterator} to decorate
	 * @param filter the filter {@link Predicate}
	 * @param <E> the element type
	 * @return the filtered {@link Iterator}
	 * @throws NullPointerException if the {@link Iterator} or the filter {@link Predicate} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> Iterator<E> filter(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		Ensure.notNull("iterator", iterator);
		Ensure.notNull("filter", filter);
		if (!iterator.hasNext()) {
			return Collections.emptyIterator();
		}
		return new Iterator<>() {

			/**
			 * {@link Single} of the current value.
			 * @since 1.1.0
			 */
			private Single<E> currentSingle;

			/**
			 * Prepare the next value.
			 * @since 1.1.0
			 */
			private void prepareNext() {
				while (iterator.hasNext()) {
					final var maybeCurrent = iterator.next();
					if (filter.test(maybeCurrent)) {
						currentSingle = Single.of(maybeCurrent);
						break;
					}
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean hasNext() {
				if (null != currentSingle) {
					return true;
				}
				prepareNext();
				return null != currentSingle;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				final var nextSingle = currentSingle;
				currentSingle = null;
				return nextSingle.getUnique();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}

	/**
	 * Decorate an {@link Iterator} so that its elements are mapped using the given {@link Function}.
	 * @param iterator the {@link Iterator} to decorate
	 * @param mapper the mapper {@link Function}
	 * @param <I> the input element type
	 * @param <O> the output element type
	 * @return the mapped {@link Iterator}
	 * @throws NullPointerException if the {@link Iterator} or the mapper {@link Function} is {@code null}
	 * @since 1.0.0
	 */
	public static <I, O> Iterator<O> map(final Iterator<? extends I> iterator, final Function<? super I, ? extends O> mapper) {
		Ensure.notNull("iterator", iterator);
		Ensure.notNull("mapper", mapper);
		if (!iterator.hasNext()) {
			return Collections.emptyIterator();
		}
		return new Iterator<>() {

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
			public O next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return mapper.apply(iterator.next());
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}

	/**
	 * Concatenate multiple {@link Iterator}s.
	 * @param iterators the {@link Iterator} array to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@link Iterator}
	 * @throws NullPointerException if the {@link Iterator} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Iterator<E> concat(final Iterator<? extends E>... iterators) {
		Ensure.notNullAndNotNullElements("iterators", iterators);
		return concat(List.of(iterators));
	}

	/**
	 * Concatenate multiple {@link Iterator}s.
	 * @param iterators the {@link Iterator} {@link List} to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@link Iterator}
	 * @throws NullPointerException if the {@link Iterator} {@link List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterator<E> concat(final List<? extends Iterator<? extends E>> iterators) {
		Ensure.notNullAndNotNullElements("iterators", iterators);
		final var size = iterators.size();
		if (0 == size) {
			return Collections.emptyIterator();
		}
		if (1 == size) {
			return (Iterator<E>) iterators.get(0);
		}
		return new SequenceIterator<>(iterators.iterator());
	}

	/**
	 * Join multiple {@link Iterator}s using an {@link Object} array separator.
	 * @param separator the {@link Object} array separator
	 * @param iterators the {@link Iterator} array to join
	 * @param <E> the element type
	 * @return the joined {@link Iterator}
	 * @throws NullPointerException if the {@link Object} array separator, the {@link Iterator} array or any of them is
	 *         {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Iterator<E> join(final E[] separator, final Iterator<? extends E>... iterators) {
		Ensure.notNullAndNotNullElements("iterators", iterators);
		return join(separator, List.of(iterators));
	}

	/**
	 * Join multiple {@link Iterator}s using an {@link Object} array separator.
	 * @param separator the {@link Object} array separator
	 * @param iterators the {@link Iterator} {@link List} to join
	 * @param <E> the element type
	 * @return the joined {@link Iterator}
	 * @throws NullPointerException if the {@link Object} array separator, the {@link Iterator} {@link List} or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterator<E> join(final E[] separator, final List<? extends Iterator<? extends E>> iterators) {
		Ensure.notNull("separator", separator);
		Ensure.notNullAndNotNullElements("iterators", iterators);
		if (0 == separator.length) {
			return concat(iterators);
		}
		final var size = iterators.size();
		if (0 == size) {
			return Collections.emptyIterator();
		}
		if (1 == size) {
			return (Iterator<E>) iterators.get(0);
		}
		final var list = new ArrayList<Iterator<? extends E>>(2 * size - 1);
		final var iterator = iterators.iterator();
		list.add(iterator.next());
		while (iterator.hasNext()) {
			list.add(of(separator));
			list.add(iterator.next());
		}
		return new SequenceIterator<>(list.iterator());
	}

	/**
	 * Decorate an {@link Iterator} from the given {@link Supplier} which iterates until an excluded element.
	 *
	 * <p><b>Warning</b>: Could result in an infinite loop if the excluded element is never supplied.</p>
	 * @param supplier the {@link Supplier} to decorate
	 * @param excludedElement the excluded element or {@code null}
	 * @param <E> the element type
	 * @return the wrapping {@link Iterator}
	 * @throws NullPointerException if the {@link Supplier} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterator<E> until(final Supplier<? extends E> supplier, final E excludedElement) {
		Ensure.notNull("supplier", supplier);
		return new PreparedIterator<>() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected boolean isValid(final E next) {
				return !Equals.equals(next, excludedElement);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected E prepareNext() {
				return supplier.get();
			}
		};
	}

	/**
	 * Iterate and remove elements from an {@link Iterator} if they are contained by the {@link Collection}.
	 *
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@link Iterator} does not end.</p>
	 * @param iterator the {@link Iterator} to remove elements from
	 * @param collection the {@link Collection} that contains elements to remove
	 * @param <E> the element type
	 * @return {@code true} if at least one element has been removed
	 * @throws NullPointerException if the {@link Iterator} or the {@link Collection} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> boolean removeAll(final Iterator<? extends E> iterator, final Collection<? super E> collection) {
		Ensure.notNull("iterator", iterator);
		Ensure.notNull("collection", collection);
		if (collection.isEmpty()) {
			return false;
		}
		return removeIf(iterator, collection::contains);
	}

	/**
	 * Iterate and remove elements from an {@link Iterator} based on the given filter {@link Predicate}.
	 *
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@link Iterator} does not end.</p>
	 * @param iterator the {@link Iterator} to remove elements from
	 * @param filter the filter {@link Predicate}
	 * @param <E> the element type
	 * @return {@code true} if at least one element has been removed
	 * @throws NullPointerException if the {@link Iterator} or the filter {@link Predicate} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> boolean removeIf(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		Ensure.notNull("iterator", iterator);
		Ensure.notNull("filter", filter);
		if (!iterator.hasNext()) {
			return false;
		}
		var result = false;
		do {
			if (filter.test(iterator.next())) {
				iterator.remove();
				result = true;
			}
		} while (iterator.hasNext());
		return result;
	}

	/**
	 * Iterate an {@link Iterator} from the current position to the end and return the length.
	 *
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@link Iterator} does not end.</p>
	 * @param iterator the {@link Iterator} to iterate
	 * @return the length from the current position
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.1.0
	 */
	public static long length(final Iterator<?> iterator) {
		Ensure.notNull("iterator", iterator);
		var length = 0L;
		while (iterator.hasNext()) {
			iterator.next();
			++length;
		}
		return length;
	}

	/**
	 * Transfer {@link Iterator} elements from the current position to a {@link Collection}.
	 *
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@link Iterator} is too large.</p>
	 * @param iterator the {@link Iterator} to get elements from
	 * @param collection the {@link Collection} to add elements to
	 * @param <E> the element type
	 * @return the number of elements transferred
	 * @throws NullPointerException if the {@link Iterator} or the {@link Collection} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> long transferTo(final Iterator<? extends E> iterator, final Collection<? super E> collection) {
		Ensure.notNull("iterator", iterator);
		Ensure.notNull("collection", collection);
		var transferred = 0L;
		while (iterator.hasNext()) {
			collection.add(iterator.next());
			++transferred;
		}
		return transferred;
	}

	/**
	 * Optionally get the first element of an {@link Iterator} from the current position.
	 * @param iterator the {@link Iterator} to get the first element from
	 * @param <E> the element type
	 * @return a {@link NullableOptional} containing the first element if the {@link Iterator} is not empty
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> NullableOptional<E> getOptionalFirst(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return NullableOptional.empty();
		}
		return NullableOptional.of(iterator.next());
	}

	/**
	 * Optionally get the last element of an {@link Iterator} from the current position.
	 *
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@link Iterator} does not end.</p>
	 * @param iterator the {@link Iterator} to get the last element from
	 * @param <E> the element type
	 * @return a {@link NullableOptional} containing the last element if the {@link Iterator} is not empty
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> NullableOptional<E> getOptionalLast(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return NullableOptional.empty();
		}
		var element = iterator.next();
		while (iterator.hasNext()) {
			element = iterator.next();
		}
		return NullableOptional.of(element);
	}

	/**
	 * Tell if an {@link Iterator} is empty.
	 * @param iterator the {@link Iterator} to test
	 * @return {@code true} if the {@link Iterator} is empty
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final Iterator<?> iterator) {
		Ensure.notNull("iterator", iterator);
		return !iterator.hasNext();
	}

	/**
	 * Create a {@link PrimitiveIterator.OfInt} from a single {@code int} element.
	 * @param element the {@code int} element to convert
	 * @return the created {@link PrimitiveIterator.OfInt}
	 * @since 1.6.0
	 */
	public static PrimitiveIterator.OfInt singleton(final int element) {
		return ofInts(element);
	}

	/**
	 * Create a {@link PrimitiveIterator.OfLong} from a single {@code long} element.
	 * @param element the {@code long} element to convert
	 * @return the created {@link PrimitiveIterator.OfLong}
	 * @since 1.6.0
	 */
	public static PrimitiveIterator.OfLong singleton(final long element) {
		return ofLongs(element);
	}

	/**
	 * Create a singleton {@link PrimitiveIterator.OfDouble} from a single {@code double} element.
	 * @param element the {@code double} element to convert
	 * @return the created {@link PrimitiveIterator.OfDouble}
	 * @since 1.6.0
	 */
	public static PrimitiveIterator.OfDouble singleton(final double element) {
		return ofDoubles(element);
	}

	/**
	 * Create a singleton {@link Iterator} from a single element.
	 * @param element the element to convert
	 * @param <E> the element type
	 * @return the created {@link Iterator}
	 * @since 1.1.0
	 */
	public static <E> Iterator<E> singleton(final E element) {
		return of(element);
	}

	/**
	 * Create a {@link PrimitiveIterator.OfInt} from a single {@code int} element.
	 * @param element the {@code int} element to convert
	 * @return the created {@link PrimitiveIterator.OfInt}
	 * @deprecated since 1.6.0, use {@link #singleton(int)} instead
	 * @since 1.1.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterator.OfInt singletonInt(final int element) {
		return singleton(element);
	}

	/**
	 * Create a {@link PrimitiveIterator.OfLong} from a single {@code long} element.
	 * @param element the {@code long} element to convert
	 * @return the created {@link PrimitiveIterator.OfLong}
	 * @deprecated since 1.6.0, use {@link #singleton(long)} instead
	 * @since 1.1.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterator.OfLong singletonLong(final long element) {
		return singleton(element);
	}

	/**
	 * Create a singleton {@link PrimitiveIterator.OfDouble} from a single {@code double} element.
	 * @param element the {@code double} element to convert
	 * @return the created {@link PrimitiveIterator.OfDouble}
	 * @deprecated since 1.6.0, use {@link #singleton(double)} instead
	 * @since 1.1.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterator.OfDouble singletonDouble(final double element) {
		return singleton(element);
	}

	/**
	 * Create a {@link PrimitiveIterator.OfInt} from multiple {@code int} elements.
	 * @param elements the {@code int} elements array to convert
	 * @return the created {@link PrimitiveIterator.OfInt}
	 * @throws NullPointerException if the {@code int} elements array is {@code null}
	 * @deprecated since 1.6.0, use {@link #ofInts(int...)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterator.OfInt ofInt(final int... elements) {
		return ofInts(elements);
	}

	/**
	 * Create a {@link PrimitiveIterator.OfInt} from multiple {@code int} elements.
	 * @param elements the {@code int} elements array to convert
	 * @return the created {@link PrimitiveIterator.OfInt}
	 * @throws NullPointerException if the {@code int} elements array is {@code null}
	 * @since 1.6.0
	 */
	public static PrimitiveIterator.OfInt ofInts(final int... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_INT;
		}
		return Arrays.stream(elements).iterator();
	}

	/**
	 * Create a {@link PrimitiveIterator.OfLong} from multiple {@code long} elements.
	 * @param elements the {@code long} elements array to convert
	 * @return the created {@link PrimitiveIterator.OfLong}
	 * @throws NullPointerException if the {@code long} elements array is {@code null}
	 * @deprecated since 1.6.0, use {@link #ofLongs(long...)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterator.OfLong ofLong(final long... elements) {
		return ofLongs(elements);
	}

	/**
	 * Create a {@link PrimitiveIterator.OfLong} from multiple {@code long} elements.
	 * @param elements the {@code long} elements array to convert
	 * @return the created {@link PrimitiveIterator.OfLong}
	 * @throws NullPointerException if the {@code long} elements array is {@code null}
	 * @since 1.6.0
	 */
	public static PrimitiveIterator.OfLong ofLongs(final long... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_LONG;
		}
		return Arrays.stream(elements).iterator();
	}

	/**
	 * Create a {@link PrimitiveIterator.OfDouble} from multiple {@code double} elements.
	 * @param elements the {@code double} elements array to convert
	 * @return the created {@link PrimitiveIterator.OfDouble}
	 * @throws NullPointerException if the {@code double} elements array is {@code null}
	 * @deprecated since 1.6.0, use {@link #ofDoubles(double...)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterator.OfDouble ofDouble(final double... elements) {
		return ofDoubles(elements);
	}

	/**
	 * Create a {@link PrimitiveIterator.OfDouble} from multiple {@code double} elements.
	 * @param elements the {@code double} elements array to convert
	 * @return the created {@link PrimitiveIterator.OfDouble}
	 * @throws NullPointerException if the {@code double} elements array is {@code null}
	 * @since 1.6.0
	 */
	public static PrimitiveIterator.OfDouble ofDoubles(final double... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_DOUBLE;
		}
		return Arrays.stream(elements).iterator();
	}

	/**
	 * Create an {@link Iterator} from multiple elements.
	 * @param elements the elements array to convert
	 * @param <E> the element type
	 * @return the created {@link Iterator}
	 * @throws NullPointerException if the elements array is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Iterator<E> of(final E... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return Collections.emptyIterator();
		}
		return Arrays.stream(elements).iterator();
	}

	/**
	 * Create an {@link Iterator} with an {@link InputStream} from the current position.
	 *
	 * <p><b>Note</b>: The {@link InputStream} will not be closed.</p>
	 * @param inputStream the {@link InputStream} to convert
	 * @return the created {@link Iterator}
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterator<Integer> of(final InputStream inputStream) {
		Ensure.notNull("inputStream", inputStream);
		return until(ThrowableSupplier.unchecked(inputStream::read), -1);
	}

	/**
	 * Create an {@link Iterator} with a {@link Reader} from the current position.
	 *
	 * <p><b>Note</b>: The {@link Reader} will not be closed.</p>
	 * @param reader the {@link Reader} to convert
	 * @return the created {@link Iterator}
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterator<Integer> of(final Reader reader) {
		Ensure.notNull("reader", reader);
		return until(ThrowableSupplier.unchecked(reader::read), -1);
	}

	/**
	 * Create an {@link Iterator} with a {@link BufferedReader} from the current position.
	 *
	 * <p><b>Note</b>: The {@link BufferedReader} will not be closed.</p>
	 * @param bufferedReader the {@link BufferedReader} to convert
	 * @return the created {@link Iterator}
	 * @throws NullPointerException if the {@link BufferedReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterator<String> of(final BufferedReader bufferedReader) {
		Ensure.notNull("bufferedReader", bufferedReader);
		return until(ThrowableSupplier.unchecked(bufferedReader::readLine), null);
	}

	/**
	 * Create an {@link Iterator} with a {@link LineReader} from the current position.
	 *
	 * <p><b>Note</b>: The {@link LineReader} will not be closed.</p>
	 * @param lineReader the {@link LineReader} to convert
	 * @return the created {@link Iterator}
	 * @throws NullPointerException if the {@link LineReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterator<String> of(final LineReader lineReader) {
		Ensure.notNull("lineReader", lineReader);
		return until(ThrowableSupplier.unchecked(lineReader::read), null);
	}

	/**
	 * Convert an {@link Iterator} from the current position to a {@link Set}.
	 * @param iterator the {@link Iterator} to convert
	 * @param <E> the element type
	 * @return the created {@link Set}
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Set<E> toSet(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Set.of();
		}
		final var set = new HashSet<E>();
		transferTo(iterator, set);
		return set;
	}

	/**
	 * Convert an {@link Iterator} from the current position to a {@link List}.
	 * @param iterator the {@link Iterator} to convert
	 * @param <E> the element type
	 * @return the created {@link List}
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> List<E> toList(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return List.of();
		}
		final var list = new ArrayList<E>();
		transferTo(iterator, list);
		return list;
	}

	/**
	 * Convert an {@link Iterator} from the current position to an {@link InputStream}.
	 * @param iterator the {@link Iterator} to convert
	 * @return the created {@link InputStream}
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream toInputStream(final Iterator<Integer> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return InputStreams.EMPTY;
		}
		return new InputStream() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public int read() {
				if (iterator.hasNext()) {
					return iterator.next();
				}
				return -1;
			}
		};
	}

	/**
	 * Convert an {@link Iterator} from the current position to a {@link Reader}.
	 * @param iterator the {@link Iterator} to convert
	 * @return the created {@link Reader}
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader toReader(final Iterator<Integer> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Readers.EMPTY;
		}
		return new Reader() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public int read() {
				if (iterator.hasNext()) {
					return iterator.next();
				}
				return -1;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public int read(final char[] buffer, final int offset, final int length) {
				Ensure.notNull("buffer", buffer);
				Ensure.between("offset", offset, 0, buffer.length);
				Ensure.between("length", length, 0, buffer.length - offset);
				if (0 == length) {
					return 0;
				}
				if (!iterator.hasNext()) {
					return -1;
				}
				var n = 0;
				do {
					buffer[offset + n++] = (char) iterator.next().intValue();
				} while (n < length && iterator.hasNext());
				return n;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void close() {
				// Empty
			}
		};
	}
}