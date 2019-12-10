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

import com.github.alexisjehan.javanilla.io.lines.LineReader;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.util.NullableOptional;
import com.github.alexisjehan.javanilla.util.collection.Lists;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>An utility class that provides {@link Iterable} and {@link PrimitiveIterable} tools.</p>
 * @since 1.0.0
 */
public final class Iterables {

	/**
	 * <p>A {@code PrimitiveIterable.OfInt} which gives empties {@code PrimitiveIterator.OfInt}s.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterable.OfInt EMPTY_INT = () -> Iterators.EMPTY_INT;

	/**
	 * <p>A {@code PrimitiveIterable.OfLong} which gives empties {@code PrimitiveIterator.OfLong}s.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterable.OfLong EMPTY_LONG = () -> Iterators.EMPTY_LONG;

	/**
	 * <p>A {@code PrimitiveIterable.OfDouble} which gives empties {@code PrimitiveIterator.OfDouble}s.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterable.OfDouble EMPTY_DOUBLE = () -> Iterators.EMPTY_DOUBLE;

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Iterables() {
		// Not available
	}

	/**
	 * <p>Return an {@code Iterable} which gives empties {@code Iterator}s.</p>
	 * @param <E> the element type
	 * @return an {@code Iterable} which gives empties {@code Iterator}s
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> empty() {
		return Collections::emptyIterator;
	}

	/**
	 * <p>Wrap a {@code PrimitiveIterable.OfInt} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterable the {@code PrimitiveIterable.OfInt} or {@code null}
	 * @return a non-{@code null} {@code PrimitiveIterable.OfInt}
	 * @since 1.2.0
	 */
	public static PrimitiveIterable.OfInt nullToEmpty(final PrimitiveIterable.OfInt primitiveIterable) {
		return nullToDefault(primitiveIterable, EMPTY_INT);
	}

	/**
	 * <p>Wrap a {@code PrimitiveIterable.OfLong} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterable the {@code PrimitiveIterable.OfLong} or {@code null}
	 * @return a non-{@code null} {@code PrimitiveIterable.OfLong}
	 * @since 1.2.0
	 */
	public static PrimitiveIterable.OfLong nullToEmpty(final PrimitiveIterable.OfLong primitiveIterable) {
		return nullToDefault(primitiveIterable, EMPTY_LONG);
	}

	/**
	 * <p>Wrap a {@code PrimitiveIterable.OfDouble} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterable the {@code PrimitiveIterable.OfDouble} or {@code null}
	 * @return a non-{@code null} {@code PrimitiveIterable.OfDouble}
	 * @since 1.2.0
	 */
	public static PrimitiveIterable.OfDouble nullToEmpty(final PrimitiveIterable.OfDouble primitiveIterable) {
		return nullToDefault(primitiveIterable, EMPTY_DOUBLE);
	}

	/**
	 * <p>Wrap an {@code Iterable} replacing {@code null} by an empty one.</p>
	 * @param iterable the {@code Iterable} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@code Iterable}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> nullToEmpty(final Iterable<E> iterable) {
		return nullToDefault(iterable, empty());
	}

	/**
	 * <p>Wrap an {@code Iterable} replacing {@code null} by a default one.</p>
	 * @param iterable the {@code Iterable} or {@code null}
	 * @param defaultIterable the default {@code Iterable}
	 * @param <I> the {@code Iterable} type
	 * @return a non-{@code null} {@code Iterable}
	 * @throws NullPointerException if the default {@code Iterable} is {@code null}
	 * @since 1.1.0
	 */
	public static <I extends Iterable<?>> I nullToDefault(final I iterable, final I defaultIterable) {
		Ensure.notNull("defaultIterable", defaultIterable);
		return null != iterable ? iterable : defaultIterable;
	}

	/**
	 * <p>Decorate an {@code Iterable} which gives {@code Iterator}s so that their {@link Iterator#remove()} method is
	 * not available.</p>
	 * @param iterable the {@code Iterable} to decorate
	 * @param <E> the element type
	 * @return the unmodifiable {@code Iterable}
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> unmodifiable(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		return () -> Iterators.unmodifiable(iterable.iterator());
	}

	/**
	 * <p>Decorate an {@code Iterable} which gives {@code Iterator}s so that their elements are filtered using the given
	 * {@code Predicate}.</p>
	 * @param iterable the {@code Iterable} to decorate
	 * @param filter the filter {@code Predicate}
	 * @param <E> the element type
	 * @return the filtered {@code Iterable}
	 * @throws NullPointerException if the {@code Iterable} or the filter {@code Predicate} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> Iterable<E> filter(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		Ensure.notNull("iterable", iterable);
		Ensure.notNull("filter", filter);
		return () -> Iterators.filter(iterable.iterator(), filter);
	}

	/**
	 * <p>Decorate an {@code Iterable} which gives {@code Iterator}s so that their elements are mapped using the given
	 * {@code Function}.</p>
	 * @param iterable the {@code Iterable} to decorate
	 * @param mapper the mapper {@code Function}
	 * @param <I> the input element type
	 * @param <O> the output element type
	 * @return the mapped {@code Iterable}
	 * @throws NullPointerException if the {@code Iterable} or the mapper {@code Function} is {@code null}
	 * @since 1.0.0
	 */
	public static <I, O> Iterable<O> map(final Iterable<? extends I> iterable, final Function<? super I, ? extends O> mapper) {
		Ensure.notNull("iterable", iterable);
		Ensure.notNull("mapper", mapper);
		return () -> Iterators.map(iterable.iterator(), mapper);
	}

	/**
	 * <p>Decorate an {@code Iterable} which gives {@code Iterator}s so that they return {@code IndexedElement}s
	 * composed of the index and the element.</p>
	 * @param iterable the {@code Iterable} to decorate
	 * @param <E> the element type
	 * @return the indexed {@code Iterable}
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> Iterable<IndexedElement<E>> index(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		return () -> Iterators.index(iterable.iterator());
	}

	/**
	 * <p>Iterate an {@code Iterable} to the end and return the length.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code Iterable} does not end.</p>
	 * @param iterable the {@code Iterable} to iterate
	 * @return the length
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.1.0
	 */
	public static long length(final Iterable<?> iterable) {
		Ensure.notNull("iterable", iterable);
		if (iterable instanceof Collection) {
			return ((Collection<?>) iterable).size();
		}
		return Iterators.length(iterable.iterator());
	}

	/**
	 * <p>Transfer {@code Iterable} elements to a {@code Collection}.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@code Iterable} is too large.</p>
	 * @param iterable the {@code Iterable} to get elements from
	 * @param collection the {@code Collection} to add elements to
	 * @param <E> the element type
	 * @return the number of elements transferred
	 * @throws NullPointerException if the {@code Iterable} or the {@code Collection} is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> long transferTo(final Iterable<? extends E> iterable, final Collection<? super E> collection) {
		Ensure.notNull("iterable", iterable);
		Ensure.notNull("collection", collection);
		if (iterable instanceof Collection) {
			final var iterableCollection = (Collection<? extends E>) iterable;
			collection.addAll(iterableCollection);
			return iterableCollection.size();
		}
		return Iterators.transferTo(iterable.iterator(), collection);
	}

	/**
	 * <p>Optionally get the first element of an {@code Iterable}.</p>
	 * @param iterable the {@code Iterable} to get the first element from
	 * @param <E> the element type
	 * @return a {@code NullableOptional} containing the first element if the {@code Iterable} is not empty
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> NullableOptional<E> getOptionalFirst(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		if (iterable instanceof List) {
			return Lists.getOptionalFirst((List<E>) iterable);
		}
		return Iterators.getOptionalFirst(iterable.iterator());
	}

	/**
	 * <p>Optionally get the last element of an {@code Iterable}.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code Iterable} does not end.</p>
	 * @param iterable the {@code Iterable} to get the last element from
	 * @param <E> the element type
	 * @return a {@code NullableOptional} containing the last element if the {@code Iterable} is not empty
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> NullableOptional<E> getOptionalLast(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		if (iterable instanceof List) {
			return Lists.getOptionalLast((List<E>) iterable);
		}
		return Iterators.getOptionalLast(iterable.iterator());
	}

	/**
	 * <p>Concatenate multiple {@code Iterable}s.</p>
	 * @param iterables the {@code Iterable} array to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@code Iterable}
	 * @throws NullPointerException if the {@code Iterable} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Iterable<E> concat(final Iterable<? extends E>... iterables) {
		Ensure.notNullAndNotNullElements("iterables", iterables);
		return concat(List.of(iterables));
	}

	/**
	 * <p>Concatenate multiple {@code Iterable}s.</p>
	 * @param iterables the {@code Iterable} {@code List} to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@code Iterable}
	 * @throws NullPointerException if the {@code Iterable} {@code List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> concat(final List<? extends Iterable<? extends E>> iterables) {
		Ensure.notNullAndNotNullElements("iterables", iterables);
		final var size = iterables.size();
		if (0 == size) {
			return empty();
		}
		if (1 == size) {
			return (Iterable<E>) iterables.get(0);
		}
		return () -> Iterators.concat(iterables.stream().map(Iterable::iterator).collect(Collectors.toList()));
	}

	/**
	 * <p>Join multiple {@code Iterable}s using an {@code Object} array separator.</p>
	 * @param separator the {@code Object} array separator
	 * @param iterables the {@code Iterable} array to join
	 * @param <E> the element type
	 * @return the joined {@code Iterable}
	 * @throws NullPointerException if the {@code Object} array separator, the {@code Iterable} array or any of them is
	 * {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Iterable<E> join(final E[] separator, final Iterable<? extends E>... iterables) {
		Ensure.notNullAndNotNullElements("iterables", iterables);
		return join(separator, List.of(iterables));
	}

	/**
	 * <p>Join multiple {@code Iterable}s using an {@code Object} array separator.</p>
	 * @param separator the {@code Object} array separator
	 * @param iterables the {@code Iterable} {@code List} to join
	 * @param <E> the element type
	 * @return the joined {@code Iterable}
	 * @throws NullPointerException if the {@code Object} array separator, the {@code Iterable} {@code List} or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> join(final E[] separator, final List<? extends Iterable<? extends E>> iterables) {
		Ensure.notNull("iterables", iterables);
		Ensure.notNullAndNotNullElements("iterables", iterables);
		if (0 == separator.length) {
			return concat(iterables);
		}
		final var size = iterables.size();
		if (0 == size) {
			return empty();
		}
		if (1 == size) {
			return (Iterable<E>) iterables.get(0);
		}
		return () -> Iterators.join(separator, iterables.stream().map(Iterable::iterator).collect(Collectors.toList()));
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfInt} from a single {@code int} element.</p>
	 * @param element the {@code int} element to convert
	 * @return the created {@code PrimitiveIterable.OfInt}
	 * @since 1.1.0
	 */
	public static PrimitiveIterable.OfInt singletonInt(final int element) {
		return ofInt(element);
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfLong} from a single {@code long} element.</p>
	 * @param element the {@code long} element to convert
	 * @return the created {@code PrimitiveIterable.OfLong}
	 * @since 1.1.0
	 */
	public static PrimitiveIterable.OfLong singletonLong(final long element) {
		return ofLong(element);
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfDouble} from a single {@code double} element.</p>
	 * @param element the {@code double} element to convert
	 * @return the created {@code PrimitiveIterable.OfDouble}
	 * @since 1.1.0
	 */
	public static PrimitiveIterable.OfDouble singletonDouble(final double element) {
		return ofDouble(element);
	}

	/**
	 * <p>Create a {@code Iterable} from a single element.</p>
	 * @param element the element to convert
	 * @param <E> the element type
	 * @return the created {@code Iterable}
	 * @since 1.1.0
	 */
	public static <E> Iterable<E> singleton(final E element) {
		return of(element);
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfInt} from multiple {@code int} elements.</p>
	 * @param elements the {@code int} elements array to convert
	 * @return the created {@code PrimitiveIterable.OfInt}
	 * @throws NullPointerException if the {@code int} elements array is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfInt ofInt(final int... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_INT;
		}
		return () -> Iterators.ofInt(elements);
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfLong} from multiple {@code long} elements.</p>
	 * @param elements the {@code long} elements array to convert
	 * @return the created {@code PrimitiveIterable.OfLong}
	 * @throws NullPointerException if the {@code long} elements array is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfLong ofLong(final long... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_LONG;
		}
		return () -> Iterators.ofLong(elements);
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfDouble} from multiple {@code double} elements.</p>
	 * @param elements the {@code double} elements array to convert
	 * @return the created {@code PrimitiveIterable.OfDouble}
	 * @throws NullPointerException if the {@code double} elements array is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfDouble ofDouble(final double... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_DOUBLE;
		}
		return () -> Iterators.ofDouble(elements);
	}

	/**
	 * <p>Create an {@code Iterable} from multiple elements.</p>
	 * @param elements the elements array to convert
	 * @param <E> the element type
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if the elements array is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Iterable<E> of(final E... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return empty();
		}
		return () -> Iterators.of(elements);
	}

	/**
	 * <p>Convert an {@code Iterable} to a {@code Set}.</p>
	 * @param iterable the {@code Iterable} to convert
	 * @param <E> the element type
	 * @return the created {@code Set}
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Set<E> toSet(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		if (iterable instanceof Set) {
			return (Set<E>) iterable;
		}
		return Iterators.toSet(iterable.iterator());
	}

	/**
	 * <p>Convert an {@code Iterable} to a {@code List}.</p>
	 * @param iterable the {@code Iterable} to convert
	 * @param <E> the element type
	 * @return the created {@code List}
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> toList(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		if (iterable instanceof List) {
			return (List<E>) iterable;
		}
		return Iterators.toList(iterable.iterator());
	}

	/**
	 * <p>Create an {@code Iterable} by wrapping an {@code InputStream} from the current position.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@code InputStream} will not be closed.</p>
	 * @param inputStream the {@code InputStream} to wrap
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<Integer> wrap(final InputStream inputStream) {
		return wrap(Iterators.of(inputStream));
	}

	/**
	 * <p>Create an {@code Iterable} by wrapping a {@code Reader} from the current position.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@code Reader} will not be closed.</p>
	 * @param reader the {@code Reader} to wrap
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<Integer> wrap(final Reader reader) {
		return wrap(Iterators.of(reader));
	}

	/**
	 * <p>Create an {@code Iterable} by wrapping a {@code BufferedReader} from the current position.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@code BufferedReader} will not be closed.</p>
	 * @param bufferedReader the {@code BufferedReader} to wrap
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if the {@code BufferedReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<String> wrap(final BufferedReader bufferedReader) {
		return wrap(Iterators.of(bufferedReader));
	}

	/**
	 * <p>Create an {@code Iterable} by wrapping a {@code LineReader} from the current position.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@code LineReader} will not be closed.</p>
	 * @param lineReader the {@code LineReader} to wrap
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if the {@code LineReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<String> wrap(final LineReader lineReader) {
		return wrap(Iterators.of(lineReader));
	}

	/**
	 * <p>Create an {@code Iterable} by wrapping a {@code Stream}.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * @param stream the {@code Stream} to wrap
	 * @param <E> the element type
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if the {@code Stream} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> Iterable<E> wrap(final Stream<? extends E> stream) {
		Ensure.notNull("stream", stream);
		return wrap(stream.iterator());
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfInt} by wrapping a {@code PrimitiveIterator.OfInt} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@code PrimitiveIterator.OfInt} can only be obtained once unlike
	 * {@code PrimitiveIterable.OfInt} default behavior.</p>
	 * @param iterator the {@code PrimitiveIterator.OfInt} to wrap
	 * @return the created {@code PrimitiveIterable.OfInt}
	 * @throws NullPointerException if the {@code PrimitiveIterator.OfInt} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfInt wrap(final PrimitiveIterator.OfInt iterator) {
		Ensure.notNull("iterator", iterator);
		return new PrimitiveIterable.OfInt() {
			private boolean obtained = false;

			@Override
			public PrimitiveIterator.OfInt iterator() {
				if (obtained) {
					throw new IllegalStateException("This PrimitiveIterator.OfInt can only be obtained once.");
				}
				obtained = true;
				return iterator;
			}
		};
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfLong} by wrapping a {@code PrimitiveIterator.OfLong} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@code PrimitiveIterator.OfLong} can only be obtained once unlike
	 * {@code PrimitiveIterable.OfLong} default behavior.</p>
	 * @param iterator the {@code PrimitiveIterator.OfLong} to wrap
	 * @return the created {@code PrimitiveIterable.OfLong}
	 * @throws NullPointerException if the {@code PrimitiveIterator.OfLong} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfLong wrap(final PrimitiveIterator.OfLong iterator) {
		Ensure.notNull("iterator", iterator);
		return new PrimitiveIterable.OfLong() {
			private boolean obtained = false;

			@Override
			public PrimitiveIterator.OfLong iterator() {
				if (obtained) {
					throw new IllegalStateException("This PrimitiveIterator.OfLong can only be obtained once.");
				}
				obtained = true;
				return iterator;
			}
		};
	}

	/**
	 * <p>Create a {@code PrimitiveIterable.OfDouble} by wrapping a {@code PrimitiveIterator.OfDouble} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@code PrimitiveIterator.OfDouble} can only be obtained once unlike
	 * {@code PrimitiveIterable.OfDouble} default behavior.</p>
	 * @param iterator the {@code PrimitiveIterator.OfDouble} to wrap
	 * @return the created {@code PrimitiveIterable.OfDouble}
	 * @throws NullPointerException if the {@code PrimitiveIterator.OfDouble} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfDouble wrap(final PrimitiveIterator.OfDouble iterator) {
		Ensure.notNull("iterator", iterator);
		return new PrimitiveIterable.OfDouble() {
			private boolean obtained = false;

			@Override
			public PrimitiveIterator.OfDouble iterator() {
				if (obtained) {
					throw new IllegalStateException("This PrimitiveIterator.OfDouble can only be obtained once.");
				}
				obtained = true;
				return iterator;
			}
		};
	}

	/**
	 * <p>Create an {@code Iterable} by wrapping an {@code Iterator} from the current position.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * @param iterator the {@code Iterator} to wrap
	 * @param <E> the element type
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> wrap(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		return new Iterable<>() {
			private boolean obtained = false;

			@Override
			@SuppressWarnings("unchecked")
			public Iterator<E> iterator() {
				if (obtained) {
					throw new IllegalStateException("This Iterator can only be obtained once.");
				}
				obtained = true;
				return (Iterator<E>) iterator;
			}
		};
	}
}