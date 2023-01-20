/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.Iterables} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.8.0")
public final class Iterables {

	/**
	 * <p>A {@link PrimitiveIterable.OfInt} which gives empties {@link PrimitiveIterator.OfInt}s.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterable.OfInt EMPTY_INT = () -> Iterators.EMPTY_INT;

	/**
	 * <p>A {@link PrimitiveIterable.OfLong} which gives empties {@link PrimitiveIterator.OfLong}s.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterable.OfLong EMPTY_LONG = () -> Iterators.EMPTY_LONG;

	/**
	 * <p>A {@link PrimitiveIterable.OfDouble} which gives empties {@link PrimitiveIterator.OfDouble}s.</p>
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
	 * <p>Return an {@link Iterable} which gives empties {@link Iterator}s.</p>
	 * @param <E> the element type
	 * @return an {@link Iterable} which gives empties {@link Iterator}s
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> empty() {
		return Collections::emptyIterator;
	}

	/**
	 * <p>Wrap a {@link PrimitiveIterable.OfInt} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterable the {@link PrimitiveIterable.OfInt} or {@code null}
	 * @return a non-{@code null} {@link PrimitiveIterable.OfInt}
	 * @since 1.2.0
	 */
	public static PrimitiveIterable.OfInt nullToEmpty(final PrimitiveIterable.OfInt primitiveIterable) {
		return nullToDefault(primitiveIterable, EMPTY_INT);
	}

	/**
	 * <p>Wrap a {@link PrimitiveIterable.OfLong} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterable the {@link PrimitiveIterable.OfLong} or {@code null}
	 * @return a non-{@code null} {@link PrimitiveIterable.OfLong}
	 * @since 1.2.0
	 */
	public static PrimitiveIterable.OfLong nullToEmpty(final PrimitiveIterable.OfLong primitiveIterable) {
		return nullToDefault(primitiveIterable, EMPTY_LONG);
	}

	/**
	 * <p>Wrap a {@link PrimitiveIterable.OfDouble} replacing {@code null} by an empty one.</p>
	 * @param primitiveIterable the {@link PrimitiveIterable.OfDouble} or {@code null}
	 * @return a non-{@code null} {@link PrimitiveIterable.OfDouble}
	 * @since 1.2.0
	 */
	public static PrimitiveIterable.OfDouble nullToEmpty(final PrimitiveIterable.OfDouble primitiveIterable) {
		return nullToDefault(primitiveIterable, EMPTY_DOUBLE);
	}

	/**
	 * <p>Wrap an {@link Iterable} replacing {@code null} by an empty one.</p>
	 * @param iterable the {@link Iterable} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@link Iterable}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> nullToEmpty(final Iterable<E> iterable) {
		return nullToDefault(iterable, empty());
	}

	/**
	 * <p>Wrap an {@link Iterable} replacing {@code null} by a default one.</p>
	 * @param iterable the {@link Iterable} or {@code null}
	 * @param defaultIterable the default {@link Iterable}
	 * @param <I> the {@link Iterable} type
	 * @return a non-{@code null} {@link Iterable}
	 * @throws NullPointerException if the default {@link Iterable} is {@code null}
	 * @since 1.1.0
	 */
	public static <I extends Iterable<?>> I nullToDefault(final I iterable, final I defaultIterable) {
		Ensure.notNull("defaultIterable", defaultIterable);
		return null != iterable ? iterable : defaultIterable;
	}

	/**
	 * <p>Decorate an {@link Iterable} which gives {@link Iterator}s so that their {@link Iterator#remove()} method is
	 * not available.</p>
	 * @param iterable the {@link Iterable} to decorate
	 * @param <E> the element type
	 * @return the unmodifiable {@link Iterable}
	 * @throws NullPointerException if the {@link Iterable} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> unmodifiable(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		return () -> Iterators.unmodifiable(iterable.iterator());
	}

	/**
	 * <p>Decorate an {@link Iterable} which gives {@link Iterator}s so that they return {@link IndexedElement}s
	 * composed of the index and the element.</p>
	 * @param iterable the {@link Iterable} to decorate
	 * @param <E> the element type
	 * @return the indexed {@link Iterable}
	 * @throws NullPointerException if the {@link Iterable} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> Iterable<IndexedElement<E>> index(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		return () -> Iterators.index(iterable.iterator());
	}

	/**
	 * <p>Decorate an {@link Iterable} which gives {@link Iterator}s so that their elements are filtered using the given
	 * {@link Predicate}.</p>
	 * @param iterable the {@link Iterable} to decorate
	 * @param filter the filter {@link Predicate}
	 * @param <E> the element type
	 * @return the filtered {@link Iterable}
	 * @throws NullPointerException if the {@link Iterable} or the filter {@link Predicate} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> Iterable<E> filter(final Iterable<? extends E> iterable, final Predicate<? super E> filter) {
		Ensure.notNull("iterable", iterable);
		Ensure.notNull("filter", filter);
		return () -> Iterators.filter(iterable.iterator(), filter);
	}

	/**
	 * <p>Decorate an {@link Iterable} which gives {@link Iterator}s so that their elements are mapped using the given
	 * {@link Function}.</p>
	 * @param iterable the {@link Iterable} to decorate
	 * @param mapper the mapper {@link Function}
	 * @param <I> the input element type
	 * @param <O> the output element type
	 * @return the mapped {@link Iterable}
	 * @throws NullPointerException if the {@link Iterable} or the mapper {@link Function} is {@code null}
	 * @since 1.0.0
	 */
	public static <I, O> Iterable<O> map(final Iterable<? extends I> iterable, final Function<? super I, ? extends O> mapper) {
		Ensure.notNull("iterable", iterable);
		Ensure.notNull("mapper", mapper);
		return () -> Iterators.map(iterable.iterator(), mapper);
	}

	/**
	 * <p>Concatenate multiple {@link Iterable}s.</p>
	 * @param iterables the {@link Iterable} array to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@link Iterable}
	 * @throws NullPointerException if the {@link Iterable} array or any of them is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Iterable<E> concat(final Iterable<? extends E>... iterables) {
		Ensure.notNullAndNotNullElements("iterables", iterables);
		return concat(List.of(iterables));
	}

	/**
	 * <p>Concatenate multiple {@link Iterable}s.</p>
	 * @param iterables the {@link Iterable} {@link List} to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@link Iterable}
	 * @throws NullPointerException if the {@link Iterable} {@link List} or any of them is {@code null}
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
	 * <p>Join multiple {@link Iterable}s using an {@link Object} array separator.</p>
	 * @param separator the {@link Object} array separator
	 * @param iterables the {@link Iterable} array to join
	 * @param <E> the element type
	 * @return the joined {@link Iterable}
	 * @throws NullPointerException if the {@link Object} array separator, the {@link Iterable} array or any of them is
	 *         {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Iterable<E> join(final E[] separator, final Iterable<? extends E>... iterables) {
		Ensure.notNullAndNotNullElements("iterables", iterables);
		return join(separator, List.of(iterables));
	}

	/**
	 * <p>Join multiple {@link Iterable}s using an {@link Object} array separator.</p>
	 * @param separator the {@link Object} array separator
	 * @param iterables the {@link Iterable} {@link List} to join
	 * @param <E> the element type
	 * @return the joined {@link Iterable}
	 * @throws NullPointerException if the {@link Object} array separator, the {@link Iterable} {@link List} or any of
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
	 * <p>Iterate an {@link Iterable} to the end and return the length.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@link Iterable} does not end.</p>
	 * @param iterable the {@link Iterable} to iterate
	 * @return the length
	 * @throws NullPointerException if the {@link Iterable} is {@code null}
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
	 * <p>Transfer {@link Iterable} elements to a {@link Collection}.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@link Iterable} is too large.</p>
	 * @param iterable the {@link Iterable} to get elements from
	 * @param collection the {@link Collection} to add elements to
	 * @param <E> the element type
	 * @return the number of elements transferred
	 * @throws NullPointerException if the {@link Iterable} or the {@link Collection} is {@code null}
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
	 * <p>Optionally get the first element of an {@link Iterable}.</p>
	 * @param iterable the {@link Iterable} to get the first element from
	 * @param <E> the element type
	 * @return a {@link NullableOptional} containing the first element if the {@link Iterable} is not empty
	 * @throws NullPointerException if the {@link Iterable} is {@code null}
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> NullableOptional<E> getOptionalFirst(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		if (iterable instanceof List) {
			return Lists.getOptionalFirst((List<? extends E>) iterable);
		}
		return Iterators.getOptionalFirst(iterable.iterator());
	}

	/**
	 * <p>Optionally get the last element of an {@link Iterable}.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@link Iterable} does not end.</p>
	 * @param iterable the {@link Iterable} to get the last element from
	 * @param <E> the element type
	 * @return a {@link NullableOptional} containing the last element if the {@link Iterable} is not empty
	 * @throws NullPointerException if the {@link Iterable} is {@code null}
	 * @since 1.2.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> NullableOptional<E> getOptionalLast(final Iterable<? extends E> iterable) {
		Ensure.notNull("iterable", iterable);
		if (iterable instanceof List) {
			return Lists.getOptionalLast((List<? extends E>) iterable);
		}
		return Iterators.getOptionalLast(iterable.iterator());
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfInt} from a single {@code int} element.</p>
	 * @param element the {@code int} element to convert
	 * @return the created {@link PrimitiveIterable.OfInt}
	 * @since 1.6.0
	 */
	public static PrimitiveIterable.OfInt singleton(final int element) {
		return ofInts(element);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfLong} from a single {@code long} element.</p>
	 * @param element the {@code long} element to convert
	 * @return the created {@link PrimitiveIterable.OfLong}
	 * @since 1.6.0
	 */
	public static PrimitiveIterable.OfLong singleton(final long element) {
		return ofLongs(element);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfDouble} from a single {@code double} element.</p>
	 * @param element the {@code double} element to convert
	 * @return the created {@link PrimitiveIterable.OfDouble}
	 * @since 1.6.0
	 */
	public static PrimitiveIterable.OfDouble singleton(final double element) {
		return ofDoubles(element);
	}

	/**
	 * <p>Create a {@link Iterable} from a single element.</p>
	 * @param element the element to convert
	 * @param <E> the element type
	 * @return the created {@link Iterable}
	 * @since 1.1.0
	 */
	public static <E> Iterable<E> singleton(final E element) {
		return of(element);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfInt} from a single {@code int} element.</p>
	 * @param element the {@code int} element to convert
	 * @return the created {@link PrimitiveIterable.OfInt}
	 * @deprecated since 1.6.0, use {@link #singleton(int)} instead
	 * @since 1.1.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterable.OfInt singletonInt(final int element) {
		return singleton(element);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfLong} from a single {@code long} element.</p>
	 * @param element the {@code long} element to convert
	 * @return the created {@link PrimitiveIterable.OfLong}
	 * @deprecated since 1.6.0, use {@link #singleton(long)} instead
	 * @since 1.1.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterable.OfLong singletonLong(final long element) {
		return singleton(element);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfDouble} from a single {@code double} element.</p>
	 * @param element the {@code double} element to convert
	 * @return the created {@link PrimitiveIterable.OfDouble}
	 * @deprecated since 1.6.0, use {@link #singleton(double)} instead
	 * @since 1.1.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterable.OfDouble singletonDouble(final double element) {
		return singleton(element);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfInt} from multiple {@code int} elements.</p>
	 * @param elements the {@code int} elements array to convert
	 * @return the created {@link PrimitiveIterable.OfInt}
	 * @throws NullPointerException if the {@code int} elements array is {@code null}
	 * @deprecated since 1.6.0, use {@link #ofInts(int...)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterable.OfInt ofInt(final int... elements) {
		return ofInts(elements);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfInt} from multiple {@code int} elements.</p>
	 * @param elements the {@code int} elements array to convert
	 * @return the created {@link PrimitiveIterable.OfInt}
	 * @throws NullPointerException if the {@code int} elements array is {@code null}
	 * @since 1.6.0
	 */
	public static PrimitiveIterable.OfInt ofInts(final int... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_INT;
		}
		return () -> Iterators.ofInts(elements);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfLong} from multiple {@code long} elements.</p>
	 * @param elements the {@code long} elements array to convert
	 * @return the created {@link PrimitiveIterable.OfLong}
	 * @throws NullPointerException if the {@code long} elements array is {@code null}
	 * @deprecated since 1.6.0, use {@link #ofLongs(long...)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterable.OfLong ofLong(final long... elements) {
		return ofLongs(elements);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfLong} from multiple {@code long} elements.</p>
	 * @param elements the {@code long} elements array to convert
	 * @return the created {@link PrimitiveIterable.OfLong}
	 * @throws NullPointerException if the {@code long} elements array is {@code null}
	 * @since 1.6.0
	 */
	public static PrimitiveIterable.OfLong ofLongs(final long... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_LONG;
		}
		return () -> Iterators.ofLongs(elements);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfDouble} from multiple {@code double} elements.</p>
	 * @param elements the {@code double} elements array to convert
	 * @return the created {@link PrimitiveIterable.OfDouble}
	 * @throws NullPointerException if the {@code double} elements array is {@code null}
	 * @deprecated since 1.6.0, use {@link #ofDoubles(double...)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static PrimitiveIterable.OfDouble ofDouble(final double... elements) {
		return ofDoubles(elements);
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfDouble} from multiple {@code double} elements.</p>
	 * @param elements the {@code double} elements array to convert
	 * @return the created {@link PrimitiveIterable.OfDouble}
	 * @throws NullPointerException if the {@code double} elements array is {@code null}
	 * @since 1.6.0
	 */
	public static PrimitiveIterable.OfDouble ofDoubles(final double... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return EMPTY_DOUBLE;
		}
		return () -> Iterators.ofDoubles(elements);
	}

	/**
	 * <p>Create an {@link Iterable} from multiple elements.</p>
	 * @param elements the elements array to convert
	 * @param <E> the element type
	 * @return the created {@link Iterable}
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
	 * <p>Convert an {@link Iterable} to a {@link Set}.</p>
	 * @param iterable the {@link Iterable} to convert
	 * @param <E> the element type
	 * @return the created {@link Set}
	 * @throws NullPointerException if the {@link Iterable} is {@code null}
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
	 * <p>Convert an {@link Iterable} to a {@link List}.</p>
	 * @param iterable the {@link Iterable} to convert
	 * @param <E> the element type
	 * @return the created {@link List}
	 * @throws NullPointerException if the {@link Iterable} is {@code null}
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
	 * <p>Create an {@link Iterable} by wrapping an {@link InputStream} from the current position.</p>
	 * <p><b>Warning</b>: The {@link Iterator} can only be obtained once unlike {@link Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@link InputStream} will not be closed.</p>
	 * @param inputStream the {@link InputStream} to wrap
	 * @return the created {@link Iterable}
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<Integer> wrap(final InputStream inputStream) {
		return wrap(Iterators.of(inputStream));
	}

	/**
	 * <p>Create an {@link Iterable} by wrapping a {@link Reader} from the current position.</p>
	 * <p><b>Warning</b>: The {@link Iterator} can only be obtained once unlike {@link Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@link Reader} will not be closed.</p>
	 * @param reader the {@link Reader} to wrap
	 * @return the created {@link Iterable}
	 * @throws NullPointerException if the {@link Reader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<Integer> wrap(final Reader reader) {
		return wrap(Iterators.of(reader));
	}

	/**
	 * <p>Create an {@link Iterable} by wrapping a {@link BufferedReader} from the current position.</p>
	 * <p><b>Warning</b>: The {@link Iterator} can only be obtained once unlike {@link Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@link BufferedReader} will not be closed.</p>
	 * @param bufferedReader the {@link BufferedReader} to wrap
	 * @return the created {@link Iterable}
	 * @throws NullPointerException if the {@link BufferedReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<String> wrap(final BufferedReader bufferedReader) {
		return wrap(Iterators.of(bufferedReader));
	}

	/**
	 * <p>Create an {@link Iterable} by wrapping a {@link LineReader} from the current position.</p>
	 * <p><b>Warning</b>: The {@link Iterator} can only be obtained once unlike {@link Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@link LineReader} will not be closed.</p>
	 * @param lineReader the {@link LineReader} to wrap
	 * @return the created {@link Iterable}
	 * @throws NullPointerException if the {@link LineReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<String> wrap(final LineReader lineReader) {
		return wrap(Iterators.of(lineReader));
	}

	/**
	 * <p>Create an {@link Iterable} by wrapping a {@link Stream}.</p>
	 * <p><b>Warning</b>: The {@link Iterator} can only be obtained once unlike {@link Iterable} default behavior.</p>
	 * @param stream the {@link Stream} to wrap
	 * @param <E> the element type
	 * @return the created {@link Iterable}
	 * @throws NullPointerException if the {@link Stream} is {@code null}
	 * @since 1.2.0
	 */
	public static <E> Iterable<E> wrap(final Stream<? extends E> stream) {
		Ensure.notNull("stream", stream);
		return wrap(stream.iterator());
	}

	/**
	 * <p>Create a {@link PrimitiveIterable.OfInt} by wrapping a {@link PrimitiveIterator.OfInt} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@link PrimitiveIterator.OfInt} can only be obtained once unlike
	 * {@link PrimitiveIterable.OfInt} default behavior.</p>
	 * @param iterator the {@link PrimitiveIterator.OfInt} to wrap
	 * @return the created {@link PrimitiveIterable.OfInt}
	 * @throws NullPointerException if the {@link PrimitiveIterator.OfInt} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfInt wrap(final PrimitiveIterator.OfInt iterator) {
		Ensure.notNull("iterator", iterator);
		return new PrimitiveIterable.OfInt() {

			/**
			 * <p>Whether or not the {@link PrimitiveIterator.OfInt} has already been obtained.</p>
			 * @since 1.0.0
			 */
			private boolean obtained;

			/**
			 * {@inheritDoc}
			 */
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
	 * <p>Create a {@link PrimitiveIterable.OfLong} by wrapping a {@link PrimitiveIterator.OfLong} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@link PrimitiveIterator.OfLong} can only be obtained once unlike
	 * {@link PrimitiveIterable.OfLong} default behavior.</p>
	 * @param iterator the {@link PrimitiveIterator.OfLong} to wrap
	 * @return the created {@link PrimitiveIterable.OfLong}
	 * @throws NullPointerException if the {@link PrimitiveIterator.OfLong} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfLong wrap(final PrimitiveIterator.OfLong iterator) {
		Ensure.notNull("iterator", iterator);
		return new PrimitiveIterable.OfLong() {

			/**
			 * <p>Whether or not the {@link PrimitiveIterator.OfLong} has already been obtained.</p>
			 * @since 1.0.0
			 */
			private boolean obtained;

			/**
			 * {@inheritDoc}
			 */
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
	 * <p>Create a {@link PrimitiveIterable.OfDouble} by wrapping a {@link PrimitiveIterator.OfDouble} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@link PrimitiveIterator.OfDouble} can only be obtained once unlike
	 * {@link PrimitiveIterable.OfDouble} default behavior.</p>
	 * @param iterator the {@link PrimitiveIterator.OfDouble} to wrap
	 * @return the created {@link PrimitiveIterable.OfDouble}
	 * @throws NullPointerException if the {@link PrimitiveIterator.OfDouble} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfDouble wrap(final PrimitiveIterator.OfDouble iterator) {
		Ensure.notNull("iterator", iterator);
		return new PrimitiveIterable.OfDouble() {

			/**
			 * <p>Whether or not the {@link PrimitiveIterator.OfDouble} has already been obtained.</p>
			 * @since 1.0.0
			 */
			private boolean obtained;

			/**
			 * {@inheritDoc}
			 */
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
	 * <p>Create an {@link Iterable} by wrapping an {@link Iterator} from the current position.</p>
	 * <p><b>Warning</b>: The {@link Iterator} can only be obtained once unlike {@link Iterable} default behavior.</p>
	 * @param iterator the {@link Iterator} to wrap
	 * @param <E> the element type
	 * @return the created {@link Iterable}
	 * @throws NullPointerException if the {@link Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> wrap(final Iterator<? extends E> iterator) {
		Ensure.notNull("iterator", iterator);
		return new Iterable<>() {

			/**
			 * <p>Whether or not the {@link Iterator} has already been obtained.</p>
			 * @since 1.0.0
			 */
			private boolean obtained;

			/**
			 * {@inheritDoc}
			 */
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