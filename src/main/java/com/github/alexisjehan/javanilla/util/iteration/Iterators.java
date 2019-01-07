/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>An utility class that provides {@link Iterator} and {@link PrimitiveIterator} tools.</p>
 * @since 1.0.0
 */
public final class Iterators {

	/**
	 * <p>An {@link Iterator} that wraps multiple ones as a sequence.</p>
	 * @param <E> the element type
	 * @since 1.0.0
	 */
	private static final class SequenceIterator<E> implements Iterator<E> {

		/**
		 * <p>{@code Iterator} of {@code Iterator}s.</p>
		 * @since 1.0.0
		 */
		private final Iterator<Iterator<? extends E>> iterator;

		/**
		 * <p>Current {@code Iterator}.</p>
		 * @since 1.0.0
		 */
		private Iterator<? extends E> current;

		/**
		 * <p>Private constructor.</p>
		 * @param iterator the {@code Iterator} of {@code Iterator}s
		 * @since 1.0.0
		 */
		private SequenceIterator(final Iterator<Iterator<? extends E>> iterator) {
			this.iterator = iterator;
			nextIterator();
		}

		/**
		 * <p>Set the current {@code Iterator} as the next one from the {@code Iterator}.</p>
		 * @since 1.0.0
		 */
		private void nextIterator() {
			if (iterator.hasNext()) {
				current = iterator.next();
			} else {
				current = null;
			}
		}

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

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return current.next();
		}
	}

	/**
	 * <p>An empty {@code PrimitiveIterator.OfInt}.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterator.OfInt EMPTY_INT = new PrimitiveIterator.OfInt() {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public int nextInt() {
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new IllegalStateException();
		}
	};

	/**
	 * <p>An empty {@code PrimitiveIterator.OfLong}.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterator.OfLong EMPTY_LONG = new PrimitiveIterator.OfLong() {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public long nextLong() {
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new IllegalStateException();
		}
	};

	/**
	 * <p>An empty {@code PrimitiveIterator.OfDouble}.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterator.OfDouble EMPTY_DOUBLE = new PrimitiveIterator.OfDouble() {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public double nextDouble() {
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new IllegalStateException();
		}
	};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Iterators() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code PrimitiveIterator.OfInt} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterator the {@code PrimitiveIterator.OfInt} or {@code null}
	 * @return a non-{@code null} {@code PrimitiveIterator.OfInt}
	 * @since 1.2.0
	 */
	public static PrimitiveIterator.OfInt nullToEmpty(final PrimitiveIterator.OfInt primitiveIterator) {
		return nullToDefault(primitiveIterator, EMPTY_INT);
	}

	/**
	 * <p>Wrap a {@code PrimitiveIterator.OfLong} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterator the {@code PrimitiveIterator.OfLong} or {@code null}
	 * @return a non-{@code null} {@code PrimitiveIterator.OfLong}
	 * @since 1.2.0
	 */
	public static PrimitiveIterator.OfLong nullToEmpty(final PrimitiveIterator.OfLong primitiveIterator) {
		return nullToDefault(primitiveIterator, EMPTY_LONG);
	}

	/**
	 * <p>Wrap a {@code PrimitiveIterator.OfDouble} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterator the {@code PrimitiveIterator.OfDouble} or {@code null}
	 * @return a non-{@code null} {@code PrimitiveIterator.OfDouble}
	 * @since 1.2.0
	 */
	public static PrimitiveIterator.OfDouble nullToEmpty(final PrimitiveIterator.OfDouble primitiveIterator) {
		return nullToDefault(primitiveIterator, EMPTY_DOUBLE);
	}

	/**
	 * <p>Wrap an {@code Iterator} replacing {@code null} by an empty one.</p>
	 * @param iterator the {@code Iterator} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@code Iterator}
	 * @since 1.0.0
	 */
	public static <E> Iterator<E> nullToEmpty(final Iterator<E> iterator) {
		return nullToDefault(iterator, Collections.emptyIterator());
	}

	/**
	 * <p>Wrap a {@code ListIterator} replacing {@code null} by an empty one.</p>
	 * @param listIterator the {@code ListIterator} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@code ListIterator}
	 * @since 1.0.0
	 */
	public static <E> ListIterator<E> nullToEmpty(final ListIterator<E> listIterator) {
		return nullToDefault(listIterator, Collections.emptyListIterator());
	}

	/**
	 * <p>Wrap an {@code Iterator} replacing {@code null} by a default one.</p>
	 * @param iterator the {@code Iterator} or {@code null}
	 * @param defaultIterator the default {@code Iterator}
	 * @param <I> the {@code Iterator} type
	 * @return a non-{@code null} {@code Iterator}
	 * @throws NullPointerException if the default {@code Iterator} is {@code null}
	 * @since 1.1.0
	 */
	public static <I extends Iterator> I nullToDefault(final I iterator, final I defaultIterator) {
		Ensure.notNull("defaultIterator", defaultIterator);
		return null != iterator ? iterator : defaultIterator;
	}

	/**
	 * <p>Wrap an {@code Iterator} replacing an empty one by {@code null}.</p>
	 * @param iterator the {@code Iterator} or {@code null}
	 * @param <I> the {@code Iterator} type
	 * @return a non-empty {@code Iterator} or {@code null}
	 * @since 1.0.0
	 */
	public static <I extends Iterator> I emptyToNull(final I iterator) {
		return emptyToDefault(iterator, null);
	}

	/**
	 * <p>Wrap an {@code Iterator} replacing an empty one by a default one.</p>
	 * @param iterator the {@code Iterator} or {@code null}
	 * @param defaultIterator the default {@code Iterator} or {@code null}
	 * @param <I> the {@code Iterator} type
	 * @return a non-empty {@code Iterator} or {@code null}
	 * @throws IllegalArgumentException if the default {@code Iterator} is empty
	 * @since 1.1.0
	 */
	public static <I extends Iterator> I emptyToDefault(final I iterator, final I defaultIterator) {
		if (null != defaultIterator) {
			Ensure.notNullAndNotEmpty("defaultIterator", defaultIterator);
		}
		return null == iterator || iterator.hasNext() ? iterator : defaultIterator;
	}

	/**
	 * <p>Tell if an {@code Iterator} is empty.</p>
	 * @param iterator the {@code Iterator} to test
	 * @return {@code true} if the {@code Iterator} is empty
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final Iterator iterator) {
		Ensure.notNull("iterator", iterator);
		return !iterator.hasNext();
	}

	/**
	 * <p>Decorate an {@code Iterator} so that its {@link Iterator#remove()} method is not available.</p>
	 * @param iterator the {@code Iterator} to decorate
	 * @param <E> the element type
	 * @return the unmodifiable {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterator<E> unmodifiable(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Collections.emptyIterator();
		}
		return new FilterIterator<>(iterator) {
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * <p>Decorate an {@code Iterator} so that its elements are filtered using the given {@code Predicate}.</p>
	 * @param iterator the {@code Iterator} to decorate
	 * @param filter the filter {@code Predicate}
	 * @param <E> the element type
	 * @return the filtered {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator} or the filter {@code Predicate} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> Iterator<E> filter(final Iterator<? extends E> iterator, final Predicate<? super E> filter) {
		Ensure.notNull("iterator", iterator);
		Ensure.notNull("filter", filter);
		if (!iterator.hasNext()) {
			return Collections.emptyIterator();
		}
		return new Iterator<>() {
			private Single<E> currentSingle;

			private void prepareNext() {
				while (iterator.hasNext()) {
					final var maybeCurrent = iterator.next();
					if (filter.test(maybeCurrent)) {
						currentSingle = Single.of(maybeCurrent);
						break;
					}
				}
			}

			@Override
			public boolean hasNext() {
				if (null != currentSingle) {
					return true;
				}
				prepareNext();
				return null != currentSingle;
			}

			@Override
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				final var nextSingle = currentSingle;
				currentSingle = null;
				return nextSingle.getUnique();
			}

			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}

	/**
	 * <p>Decorate an {@code Iterator} so that its elements are mapped using the given {@code Function}.</p>
	 * @param iterator the {@code Iterator} to decorate
	 * @param mapper the mapper {@code Function}
	 * @param <I> the input element type
	 * @param <O> the output element type
	 * @return the mapped {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator} or the mapper {@code Function} is {@code null}
	 * @since 1.0.0
	 */
	public static <I, O> Iterator<O> map(final Iterator<? extends I> iterator, final Function<? super I, ? extends O> mapper) {
		Ensure.notNull("iterator", iterator);
		Ensure.notNull("mapper", mapper);
		if (!iterator.hasNext()) {
			return Collections.emptyIterator();
		}
		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public O next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return mapper.apply(iterator.next());
			}

			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}

	/**
	 * <p>Decorate an {@code Iterator} so that it returns {@code IndexedElement}s composed of the index from the current
	 * position and the element.</p>
	 * @param iterator the {@code Iterator} to decorate
	 * @param <E> the element type
	 * @return the indexed {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> Iterator<IndexedElement<E>> index(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Collections.emptyIterator();
		}
		return new Iterator<>() {
			private long index = 0L;

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

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
	 * <p>Decorate an {@code Iterator} from the given {@code Supplier} which iterates until an excluded element.</p>
	 * <p><b>Warning</b>: Could result in an infinite loop if the excluded element is never supplied.</p>
	 * @param supplier the {@code Supplier} to decorate
	 * @param excludedElement the excluded element or {@code null}
	 * @param <E> the element type
	 * @return the wrapping {@code Iterator}
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterator<E> until(final Supplier<? extends E> supplier, final E excludedElement) {
		Ensure.notNull("supplier", supplier);
		return new PreparedIterator<>() {
			@Override
			protected boolean isValid(final E next) {
				return !Objects.equals(next, excludedElement);
			}

			@Override
			protected E prepareNext() {
				return supplier.get();
			}
		};
	}

	/**
	 * <p>Iterate an {@code Iterator} from the current position to the end and return the length.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code Iterator} does not end.</p>
	 * @param iterator the {@code Iterator} to iterate
	 * @return the length from the current position
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.1.0
	 */
	public static long length(final Iterator iterator) {
		Ensure.notNull("iterator", iterator);
		var length = 0L;
		while (iterator.hasNext()) {
			iterator.next();
			++length;
		}
		return length;
	}

	/**
	 * <p>Transfer {@code Iterator} elements from the current position to a {@code Collection}.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@code Iterator} is too large.</p>
	 * @param iterator the {@code Iterator} to get elements from
	 * @param collection the {@code Collection} to add elements to
	 * @param <E> the element type
	 * @return the number of elements transferred
	 * @throws NullPointerException if the {@code Iterator} or the {@code Collection} is {@code null}
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
	 * <p>Optionally get the first element of an {@code Iterator} from the current position.</p>
	 * @param iterator the {@code Iterator} to get the first element from
	 * @param <E> the element type
	 * @return a {@code NullableOptional} containing the first element if the {@code Iterator} is not empty
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
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
	 * <p>Optionally get the last element of an {@code Iterator} from the current position.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code Iterator} does not end.</p>
	 * @param iterator the {@code Iterator} to get the last element from
	 * @param <E> the element type
	 * @return a {@code NullableOptional} containing the last element if the {@code Iterator} is not empty
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
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
	 * <p>Iterate and remove elements from an {@code Iterator} if they are contained by the {@code Collection}.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code Iterator} does not end.</p>
	 * @param iterator the {@code Iterator} to remove elements from
	 * @param collection the {@code Collection} that contains elements to remove
	 * @param <E> the element type
	 * @return {@code true} if at least one element has been removed
	 * @throws NullPointerException if the {@code Iterator} or the {@code Collection} is {@code null}
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
	 * <p>Iterate and remove elements from an {@code Iterator} based on the given filter {@code Predicate}.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code Iterator} does not end.</p>
	 * @param iterator the {@code Iterator} to remove elements from
	 * @param filter the filter {@code Predicate}
	 * @param <E> the element type
	 * @return {@code true} if at least one element has been removed
	 * @throws NullPointerException if the {@code Iterator} or the filter {@code Predicate} is {@code null}
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
	 * <p>Concatenate multiple {@code Iterator}s.</p>
	 * @param iterators the {@code Iterator} array to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> Iterator<E> concat(final Iterator<? extends E>... iterators) {
		Ensure.notNullAndNotNullElements("iterators", iterators);
		return concat(List.of(iterators));
	}

	/**
	 * <p>Concatenate multiple {@code Iterator}s.</p>
	 * @param iterators the {@code Iterator} {@code List} to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator} {@code List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterator<E> concat(final List<Iterator<? extends E>> iterators) {
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
	 * <p>Join multiple {@code Iterator}s using an {@code Object} array separator.</p>
	 * @param separator the {@code Object} array separator
	 * @param iterators the {@code Iterator} array to join
	 * @param <E> the element type
	 * @return the joined {@code Iterator}
	 * @throws NullPointerException if the {@code Object} array separator, the {@code Iterator} array or any of them is
	 * {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> Iterator<E> join(final E[] separator, final Iterator<? extends E>... iterators) {
		Ensure.notNullAndNotNullElements("iterators", iterators);
		return join(separator, List.of(iterators));
	}

	/**
	 * <p>Join multiple {@code Iterator}s using an {@code Object} array separator.</p>
	 * @param separator the {@code Object} array separator
	 * @param iterators the {@code Iterator} {@code List} to join
	 * @param <E> the element type
	 * @return the joined {@code Iterator}
	 * @throws NullPointerException if the {@code Object} array separator, the {@code Iterator} {@code List} or any of
	 * them is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterator<E> join(final E[] separator, final List<Iterator<? extends E>> iterators) {
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
	 * <p>Create a {@code PrimitiveIterator.OfInt} from a single {@code int} element.</p>
	 * @param element the {@code int} element to convert
	 * @return the created {@code PrimitiveIterator.OfInt}
	 * @since 1.1.0
	 */
	public static PrimitiveIterator.OfInt singletonInt(final int element) {
		return ofInt(element);
	}

	/**
	 * <p>Create a {@code PrimitiveIterator.OfLong} from a single {@code long} element.</p>
	 * @param element the {@code long} element to convert
	 * @return the created {@code PrimitiveIterator.OfLong}
	 * @since 1.1.0
	 */
	public static PrimitiveIterator.OfLong singletonLong(final long element) {
		return ofLong(element);
	}

	/**
	 * <p>Create a singleton {@code PrimitiveIterator.OfDouble} from a single {@code double} element.</p>
	 * @param element the {@code double} element to convert
	 * @return the created {@code PrimitiveIterator.OfDouble}
	 * @since 1.1.0
	 */
	public static PrimitiveIterator.OfDouble singletonDouble(final double element) {
		return ofDouble(element);
	}

	/**
	 * <p>Create a singleton {@code Iterator} from a single element.</p>
	 * @param element the element to convert
	 * @param <E> the element type
	 * @return the created {@code Iterator}
	 * @since 1.1.0
	 */
	public static <E> Iterator<E> singleton(final E element) {
		return of(element);
	}

	/**
	 * <p>Create a {@code PrimitiveIterator.OfInt} from multiple {@code int} elements.</p>
	 * @param elements the {@code int} elements array to convert
	 * @return the created {@code PrimitiveIterator.OfInt}
	 * @throws NullPointerException if the {@code int} elements array is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterator.OfInt ofInt(final int... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_INT;
		}
		return Arrays.stream(elements).iterator();
	}

	/**
	 * <p>Create a {@code PrimitiveIterator.OfLong} from multiple {@code long} elements.</p>
	 * @param elements the {@code long} elements array to convert
	 * @return the created {@code PrimitiveIterator.OfLong}
	 * @throws NullPointerException if the {@code long} elements array is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterator.OfLong ofLong(final long... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_LONG;
		}
		return Arrays.stream(elements).iterator();
	}

	/**
	 * <p>Create a {@code PrimitiveIterator.OfDouble} from multiple {@code double} elements.</p>
	 * @param elements the {@code double} elements array to convert
	 * @return the created {@code PrimitiveIterator.OfDouble}
	 * @throws NullPointerException if the {@code double} elements array is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterator.OfDouble ofDouble(final double... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_DOUBLE;
		}
		return Arrays.stream(elements).iterator();
	}

	/**
	 * <p>Create an {@code Iterator} from multiple elements.</p>
	 * @param elements the elements array to convert
	 * @param <E> the element type
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the elements array is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> Iterator<E> of(final E... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return Collections.emptyIterator();
		}
		return Arrays.stream(elements).iterator();
	}

	/**
	 * <p>Convert an {@code Iterator} from the current position to a {@code Set}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @param <E> the element type
	 * @return the created {@code Set}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Set<E> toSet(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Collections.emptySet();
		}
		final var set = new HashSet<E>();
		transferTo(iterator, set);
		return set;
	}

	/**
	 * <p>Convert an {@code Iterator} from the current position to a {@code List}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @param <E> the element type
	 * @return the created {@code List}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> List<E> toList(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Collections.emptyList();
		}
		final var list = new ArrayList<E>();
		transferTo(iterator, list);
		return list;
	}

	/**
	 * <p>Create an {@code Iterator} with an {@code InputStream} from the current position.</p>
	 * <p><b>Note</b>: The {@code InputStream} will not be closed.</p>
	 * @param inputStream the {@code InputStream} to convert
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterator<Integer> of(final InputStream inputStream) {
		Ensure.notNull("inputStream", inputStream);
		return until(ThrowableSupplier.unchecked(inputStream::read), -1);
	}

	/**
	 * <p>Convert an {@code Iterator} from the current position to an {@code InputStream}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @return the created {@code InputStream}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static InputStream toInputStream(final Iterator<Integer> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return InputStreams.EMPTY;
		}
		return new InputStream() {
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
	 * <p>Create an {@code Iterator} with a {@code Reader} from the current position.</p>
	 * <p><b>Note</b>: The {@code Reader} will not be closed.</p>
	 * @param reader the {@code Reader} to convert
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterator<Integer> of(final Reader reader) {
		Ensure.notNull("reader", reader);
		return until(ThrowableSupplier.unchecked(reader::read), -1);
	}

	/**
	 * <p>Convert an {@code Iterator} from the current position to a {@code Reader}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @return the created {@code Reader}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static Reader toReader(final Iterator<Integer> iterator) {
		Ensure.notNull("iterator", iterator);
		if (!iterator.hasNext()) {
			return Readers.EMPTY;
		}
		return new Reader() {
			@Override
			public int read() {
				if (iterator.hasNext()) {
					return iterator.next();
				}
				return -1;
			}

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

			@Override
			public void close() {
				// Nothing to do
			}
		};
	}

	/**
	 * <p>Create an {@code Iterator} with a {@code BufferedReader} from the current position.</p>
	 * <p><b>Note</b>: The {@code BufferedReader} will not be closed.</p>
	 * @param bufferedReader the {@code BufferedReader} to convert
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code BufferedReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterator<String> of(final BufferedReader bufferedReader) {
		Ensure.notNull("bufferedReader", bufferedReader);
		return until(ThrowableSupplier.unchecked(bufferedReader::readLine), null);
	}

	/**
	 * <p>Create an {@code Iterator} with a {@code LineReader} from the current position.</p>
	 * <p><b>Note</b>: The {@code LineReader} will not be closed.</p>
	 * @param lineReader the {@code LineReader} to convert
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code LineReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterator<String> of(final LineReader lineReader) {
		Ensure.notNull("lineReader", lineReader);
		return until(ThrowableSupplier.unchecked(lineReader::read), null);
	}
}