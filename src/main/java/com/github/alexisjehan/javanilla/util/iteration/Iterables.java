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
package com.github.alexisjehan.javanilla.util.iteration;

import com.github.alexisjehan.javanilla.io.lines.LineReader;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>An utility class that provides {@link Iterable} and {@link PrimitiveIterable} tools.</p>
 * @since 1.0.0
 */
public final class Iterables {

	/**
	 * <p>An {@code int PrimitiveIterable} which gives empties {@code int PrimitiveIterator}s.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterable.OfInt EMPTY_INT = () -> Iterators.EMPTY_INT;

	/**
	 * <p>A {@code long PrimitiveIterable} which gives empties {@code long PrimitiveIterator}s.</p>
	 * @since 1.0.0
	 */
	public static final PrimitiveIterable.OfLong EMPTY_LONG = () -> Iterators.EMPTY_LONG;

	/**
	 * <p>A {@code double PrimitiveIterable} which gives empties {@code double PrimitiveIterator}s.</p>
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
	 * <p>Wrap an {@code Iterable} replacing {@code null} by an empty {@code Iterable}.</p>
	 * @param iterable an {@code Iterable} or {@code null}
	 * @param <E> the element type
	 * @return the non-{@code null} {@code Iterable}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> nullToEmpty(final Iterable<E> iterable) {
		return nullToDefault(iterable, empty());
	}

	/**
	 * <p>Wrap an {@code Iterable} replacing {@code null} by a default {@code Iterable}.</p>
	 * @param iterable an {@code Iterable} or {@code null}
	 * @param defaultIterable the default {@code Iterable}
	 * @param <E> the element type
	 * @return the non-{@code null} {@code Iterable}
	 * @throws NullPointerException if the default {@code Iterable} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> Iterable<E> nullToDefault(final Iterable<E> iterable, final Iterable<E> defaultIterable) {
		if (null == defaultIterable) {
			throw new NullPointerException("Invalid default iterable (not null expected)");
		}
		return null != iterable ? iterable : defaultIterable;
	}

	/**
	 * <p>Wrap an {@code Iterable} which gives {@code Iterator}s whose {@link Iterator#remove()} method is not
	 * available.</p>
	 * @param iterable the {@code Iterable} to wrap
	 * @param <E> the element type
	 * @return the {@code Iterable} which gives unmodifiable {@code Iterator}s
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> unmodifiable(final Iterable<? extends E> iterable) {
		if (null == iterable) {
			throw new NullPointerException("Invalid iterable (not null expected)");
		}
		return () -> Iterators.unmodifiable(iterable.iterator());
	}

	/**
	 * <p>Wrap an {@code Iterable} mapped from the given {@code Iterable} using the provided map {@code Function}.</p>
	 * @param iterable the {@code Iterable} to wrap
	 * @param mapFunction the map {@code Function}
	 * @param <I> the input element type
	 * @param <O> the output element type
	 * @return the mapped {@code Iterable}
	 * @throws NullPointerException whether the {@code Iterable} or the map {@code Function} is {@code null}
	 * @since 1.0.0
	 */
	public static <I, O> Iterable<O> map(final Iterable<? extends I> iterable, final Function<? super I, ? extends O> mapFunction) {
		if (null == iterable) {
			throw new NullPointerException("Invalid iterable (not null expected)");
		}
		if (null == mapFunction) {
			throw new NullPointerException("Invalid map function (not null expected)");
		}
		return () -> Iterators.map(iterable.iterator(), mapFunction);
	}

	/**
	 * <p>Wrap an {@code Iterable} filtered from the given {@code Iterable} using the provided filter
	 * {@code Predicate}.</p>
	 * @param iterable the {@code Iterable} to wrap
	 * @param filterPredicate the filter {@code Predicate}
	 * @param <E> the element type
	 * @return the filtered {@code Iterable}
	 * @throws NullPointerException whether the {@code Iterable} or the filter {@code Predicate} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> Iterable<E> filter(final Iterable<? extends E> iterable, final Predicate<? super E> filterPredicate) {
		if (null == iterable) {
			throw new NullPointerException("Invalid iterable (not null expected)");
		}
		if (null == filterPredicate) {
			throw new NullPointerException("Invalid filter predicate (not null expected)");
		}
		return () -> Iterators.filter(iterable.iterator(), filterPredicate);
	}

	/**
	 * <p>Iterate over an {@code Iterable} and return the length.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if the {@code Iterable} does not end.</p>
	 * @param iterable the {@code Iterable} to iterate
	 * @param <E> the element type
	 * @return the length
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> long length(final Iterable<E> iterable) {
		if (null == iterable) {
			throw new NullPointerException("Invalid iterable (not null expected)");
		}
		if (iterable instanceof Collection) {
			return ((Collection) iterable).size();
		}
		return Iterators.length(iterable.iterator());
	}

	/**
	 * <p>Transfer {@code Iterable}'s elements to a {@code Collection}.</p>
	 * <p><b>Warning</b>: Can produce a memory overflow if the {@code Iterable} is too large.</p>
	 * @param iterable the {@code Iterable} to transfer elements from
	 * @param collection the {@code Collection} to transfer elements to
	 * @param <E> the element type
	 * @return the number of elements transferred
	 * @throws NullPointerException whether the {@code Iterable} or the {@code Collection} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> long transferTo(final Iterable<? extends E> iterable, final Collection<? super E> collection) {
		if (null == iterable) {
			throw new NullPointerException("Invalid iterable (not null expected)");
		}
		if (null == collection) {
			throw new NullPointerException("Invalid collection (not null expected)");
		}
		var n = 0L;
		for (final var element : iterable) {
			collection.add(element);
			++n;
		}
		return n;
	}

	/**
	 * <p>Concatenate multiple {@code Iterable}s.</p>
	 * @param iterables {@code Iterable}s to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@code Iterable}
	 * @throws NullPointerException whether the {@code Iterable}s array or any of the {@code Iterable}s is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> Iterable<E> concat(final Iterable<? extends E>... iterables) {
		if (null == iterables) {
			throw new NullPointerException("Invalid iterables (not null expected)");
		}
		return concat(Arrays.asList(iterables));
	}

	/**
	 * <p>Concatenate a list of {@code Iterable}s.</p>
	 * @param iterables {@code Iterable}s to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@code Iterable}
	 * @throws NullPointerException whether the {@code Iterable}s list or any of the {@code Iterable}s is {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> concat(final List<Iterable<? extends E>> iterables) {
		if (null == iterables) {
			throw new NullPointerException("Invalid iterables (not null expected)");
		}
		var i = 0;
		for (final var iterable : iterables) {
			if (null == iterable) {
				throw new NullPointerException("Invalid iterable at index " + i + " (not null expected)");
			}
			++i;
		}
		if (iterables.isEmpty()) {
			return empty();
		}
		if (1 == iterables.size()) {
			return (Iterable<E>) iterables.get(0);
		}
		return () -> Iterators.concat(iterables.stream().map(Iterable::iterator).collect(Collectors.toList()));
	}

	/**
	 * <p>Join multiple {@code Iterable}s using a {@code generic array} separator.</p>
	 * @param separator the {@code generic array} sequence to add between each joined {@code Iterable}
	 * @param iterables {@code Iterable}s to join
	 * @param <E> the element type
	 * @return the joined {@code Iterable}
	 * @throws NullPointerException whether the separator, the {@code Iterable}s array or any of the {@code Iterable}s
	 * is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> Iterable<E> join(final E[] separator, final Iterable<? extends E>... iterables) {
		if (null == iterables) {
			throw new NullPointerException("Invalid iterables (not null expected)");
		}
		return join(separator, Arrays.asList(iterables));
	}

	/**
	 * <p>Join a list of {@code Iterable}s using a {@code generic array} separator.</p>
	 * @param separator the {@code generic array} sequence to add between each joined {@code Iterable}
	 * @param iterables {@code Iterable}s to join
	 * @param <E> the element type
	 * @return the joined {@code Iterable}
	 * @throws NullPointerException whether the separator, the {@code Iterable}s list or any of the {@code Iterable}s is
	 * {@code null}
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> join(final E[] separator, final List<Iterable<? extends E>> iterables) {
		if (null == separator) {
			throw new NullPointerException("Invalid separator (not null expected)");
		}
		if (null == iterables) {
			throw new NullPointerException("Invalid iterables (not null expected)");
		}
		var i = 0;
		for (final var iterable : iterables) {
			if (null == iterable) {
				throw new NullPointerException("Invalid iterable at index " + i + " (not null expected)");
			}
			++i;
		}
		if (0 == separator.length) {
			return concat(iterables);
		}
		if (iterables.isEmpty()) {
			return empty();
		}
		if (1 == iterables.size()) {
			return (Iterable<E>) iterables.get(0);
		}
		return () -> Iterators.join(separator, iterables.stream().map(Iterable::iterator).collect(Collectors.toList()));
	}

	/**
	 * <p>Create a singleton {@code PrimitiveIterable} using the given {@code int} value.</p>
	 * @param value the {@code int} value
	 * @return the created singleton {@code PrimitiveIterable}
	 * @since 1.1.0
	 */
	public static PrimitiveIterable.OfInt singleton(final int value) {
		return () -> Iterators.ofInt(value);
	}

	/**
	 * <p>Create a {@code PrimitiveIterable} from {@code int} values.</p>
	 * @param values {@code int} values to convert
	 * @return the created {@code PrimitiveIterable}
	 * @throws NullPointerException if {@code int} values are {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfInt ofInt(final int... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY_INT;
		}
		return () -> Iterators.ofInt(values);
	}

	/**
	 * <p>Create a singleton {@code PrimitiveIterable} using the given {@code long} value.</p>
	 * @param value the {@code long} value
	 * @return the created singleton {@code PrimitiveIterable}
	 * @since 1.1.0
	 */
	public static PrimitiveIterable.OfLong singleton(final long value) {
		return () -> Iterators.ofLong(value);
	}

	/**
	 * <p>Create a {@code PrimitiveIterable} from {@code long} values.</p>
	 * @param values {@code long} values to convert
	 * @return the created {@code PrimitiveIterable}
	 * @throws NullPointerException if {@code long} values are {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfLong ofLong(final long... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY_LONG;
		}
		return () -> Iterators.ofLong(values);
	}

	/**
	 * <p>Create a singleton {@code PrimitiveIterable} using the given {@code double} value.</p>
	 * @param value the {@code double} value
	 * @return the created singleton {@code PrimitiveIterable}
	 * @since 1.1.0
	 */
	public static PrimitiveIterable.OfDouble singleton(final double value) {
		return () -> Iterators.ofDouble(value);
	}

	/**
	 * <p>Create a {@code PrimitiveIterable} from {@code double} values.</p>
	 * @param values {@code double} values to convert
	 * @return the created {@code PrimitiveIterable}
	 * @throws NullPointerException if {@code double} values are {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfDouble ofDouble(final double... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY_DOUBLE;
		}
		return () -> Iterators.ofDouble(values);
	}

	/**
	 * <p>Create a singleton {@code Iterable} using the given {@code generic} value.</p>
	 * @param value the {@code generic} value
	 * @param <E> the element type
	 * @return the created singleton {@code Iterable}
	 * @since 1.1.0
	 */
	public static <E> Iterable<E> singleton(final E value) {
		return () -> Iterators.singleton(value);
	}

	/**
	 * <p>Create an {@code Iterable} from {@code generic} values.</p>
	 * @param values {@code generic} values to convert
	 * @param <E> the element type
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if {@code generic} values are {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	public static <E> Iterable<E> of(final E... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return empty();
		}
		return () -> Iterators.of(values);
	}

	/**
	 * <p>Convert an {@code Iterable} to a modifiable {@code Set}.</p>
	 * @param iterable the {@code Iterable} to convert
	 * @param <E> the element type
	 * @return the created modifiable {@code Set}
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Set<E> toSet(final Iterable<? extends E> iterable) {
		if (null == iterable) {
			throw new NullPointerException("Invalid iterable (not null expected)");
		}
		final var set = new HashSet<E>();
		transferTo(iterable, set);
		return set;
	}

	/**
	 * <p>Convert an {@code Iterable} to a {@code List}.</p>
	 * @param iterable the {@code Iterable} to convert
	 * @param <E> the element type
	 * @return the created modifiable {@code List}
	 * @throws NullPointerException if the {@code Iterable} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> List<E> toList(final Iterable<? extends E> iterable) {
		if (null == iterable) {
			throw new NullPointerException("Invalid iterable (not null expected)");
		}
		final var list = new ArrayList<E>();
		transferTo(iterable, list);
		return list;
	}

	/**
	 * <p>Create an {@code Iterable} by wrapping an {@code InputStream} from his current position.</p>
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
	 * <p>Create an {@code Iterable} by wrapping a {@code Reader} from his current position.</p>
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
	 * <p>Create an {@code Iterator} by wrapping a {@code BufferedReader} from his current position.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@code BufferedReader} will not be closed.</p>
	 * @param bufferedReader the {@code BufferedReader} to wrap
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code BufferedReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<String> wrap(final BufferedReader bufferedReader) {
		return wrap(Iterators.of(bufferedReader));
	}

	/**
	 * <p>Create an {@code Iterator} by wrapping a {@code LineReader} from his current position.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * <p><b>Note</b>: The {@code LineReader} will not be closed.</p>
	 * @param lineReader the {@code LineReader} to wrap
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code LineReader} is {@code null}
	 * @since 1.0.0
	 */
	public static Iterable<String> wrap(final LineReader lineReader) {
		return wrap(Iterators.of(lineReader));
	}

	/**
	 * <p>Create an {@code int PrimitiveIterable} by wrapping an {@code int PrimitiveIterator} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@code PrimitiveIterator} can only be obtained once unlike {@code PrimitiveIterable}
	 * default behavior.</p>
	 * @param iterator the {@code PrimitiveIterator} to wrap
	 * @return the created {@code PrimitiveIterable}
	 * @throws NullPointerException if the {@code PrimitiveIterator} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfInt wrap(final PrimitiveIterator.OfInt iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		return new PrimitiveIterable.OfInt() {
			private boolean obtained = false;

			@NotNull
			@Override
			public PrimitiveIterator.OfInt iterator() {
				if (!obtained) {
					obtained = true;
					return iterator;
				}
				throw new IllegalStateException("This wrapped iterator can only be obtained once.");
			}
		};
	}

	/**
	 * <p>Create a {@code long PrimitiveIterable} by wrapping a {@code long PrimitiveIterator} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@code PrimitiveIterator} can only be obtained once unlike {@code PrimitiveIterable}
	 * default behavior.</p>
	 * @param iterator the {@code PrimitiveIterator} to wrap
	 * @return the created {@code PrimitiveIterable}
	 * @throws NullPointerException if the {@code PrimitiveIterator} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfLong wrap(final PrimitiveIterator.OfLong iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		return new PrimitiveIterable.OfLong() {
			private boolean obtained = false;

			@NotNull
			@Override
			public PrimitiveIterator.OfLong iterator() {
				if (!obtained) {
					obtained = true;
					return iterator;
				}
				throw new IllegalStateException("This wrapped iterator can only be obtained once.");
			}
		};
	}

	/**
	 * <p>Create a {@code double PrimitiveIterable} by wrapping a {@code double PrimitiveIterator} from his current
	 * position.</p>
	 * <p><b>Warning</b>: The {@code PrimitiveIterator} can only be obtained once unlike {@code PrimitiveIterable}
	 * default behavior.</p>
	 * @param iterator the {@code PrimitiveIterator} to wrap
	 * @return the created {@code PrimitiveIterable}
	 * @throws NullPointerException if the {@code PrimitiveIterator} is {@code null}
	 * @since 1.0.0
	 */
	public static PrimitiveIterable.OfDouble wrap(final PrimitiveIterator.OfDouble iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		return new PrimitiveIterable.OfDouble() {
			private boolean obtained = false;

			@NotNull
			@Override
			public PrimitiveIterator.OfDouble iterator() {
				if (!obtained) {
					obtained = true;
					return iterator;
				}
				throw new IllegalStateException("This wrapped iterator can only be obtained once.");
			}
		};
	}

	/**
	 * <p>Create an {@code Iterable} by wrapping an {@code Iterator} from his current position.</p>
	 * <p><b>Warning</b>: The {@code Iterator} can only be obtained once unlike {@code Iterable} default behavior.</p>
	 * @param iterator the {@code Iterator} to wrap
	 * @param <E> the element type
	 * @return the created {@code Iterable}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0.0
	 */
	public static <E> Iterable<E> wrap(final Iterator<? extends E> iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		return new Iterable<>() {
			private boolean obtained = false;

			@NotNull
			@Override
			@SuppressWarnings("unchecked")
			public Iterator<E> iterator() {
				if (!obtained) {
					obtained = true;
					return (Iterator<E>) iterator;
				}
				throw new IllegalStateException("This wrapped iterator can only be obtained once.");
			}
		};
	}
}