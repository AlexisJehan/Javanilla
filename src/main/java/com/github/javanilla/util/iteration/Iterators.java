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

import com.github.javanilla.io.lines.LineReader;
import com.github.javanilla.util.function.throwable.ThrowableSupplier;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>An utility class that provides {@link Iterator} and {@link PrimitiveIterator} tools.</p>
 * @since 1.0
 */
public final class Iterators {

	/**
	 * <p>An {@link Iterator} that wraps multiple ones as a sequence.</p>
	 * @param <E> the element type
	 * @since 1.0
	 */
	private static class SequenceIterator<E> implements Iterator<E> {

		/**
		 * <p>{@code Iterator} of wrapped {@code Iterator}s.</p>
		 * @since 1.0
		 */
		private final Iterator<Iterator<? extends E>> iterator;

		/**
		 * <p>Current {@code Iterator}.</p>
		 * @since 1.0
		 */
		private Iterator<? extends E> current;

		/**
		 * <p>Private constructor.</p>
		 * @param iterable the {@code Iterator} iterable
		 * @since 1.0
		 */
		private SequenceIterator(final Iterable<Iterator<? extends E>> iterable) {
			iterator = iterable.iterator();
			nextIterator();
		}

		/**
		 * <p>Set the current {@code Iterator} as the next one from the iterator.</p>
		 * @since 1.0
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
	 * <p>An empty {@code int PrimitiveIterator}.</p>
	 * @since 1.0
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
	 * <p>An empty {@code long PrimitiveIterator}.</p>
	 * @since 1.0
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
	 * <p>An empty {@code double PrimitiveIterator}.</p>
	 * @since 1.0
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
	 * @since 1.0
	 */
	private Iterators() {
		// Not available
	}

	/**
	 * <p>Wrap an {@code Iterator} replacing {@code null} by an empty {@code Iterator}.</p>
	 * @param iterator an {@code Iterator} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@code Iterator}
	 * @since 1.0
	 */
	public static <E> Iterator<E> nullToEmpty(final Iterator<E> iterator) {
		return null != iterator ? iterator : Collections.emptyIterator();
	}

	/**
	 * <p>Wrap an {@code ListIterator} replacing {@code null} by an empty {@code ListIterator}.</p>
	 * @param listIterator an {@code ListIterator} or {@code null}
	 * @param <E> the element type
	 * @return a non-{@code null} {@code ListIterator}
	 * @since 1.0
	 */
	public static <E> ListIterator<E> nullToEmpty(final ListIterator<E> listIterator) {
		return null != listIterator ? listIterator : Collections.emptyListIterator();
	}

	/**
	 * <p>Wrap an {@code Iterator} whose {@link Iterator#remove()} method is not available.</p>
	 * @param iterator the {@code Iterator} to wrap
	 * @param <E> the element type
	 * @return the unmodifiable {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0
	 */
	public static <E> Iterator<E> unmodifiable(final Iterator<? extends E> iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return iterator.next();
			}
		};
	}

	/**
	 * <p>Wrap an {@code Iterator} mapped from the given {@code Iterator} using the provided mapping
	 * {@code Function}.</p>
	 * @param iterator the {@code Iterator} to wrap
	 * @param mappingFunction the mapping {@code Function}
	 * @param <I> the input element type
	 * @param <O> the output element type
	 * @return the mapped {@code Iterator}
	 * @throws NullPointerException whether the {@code Iterator} or the mapping {@code Function} is {@code null}
	 * @since 1.0
	 */
	public static <I, O> Iterator<O> map(final Iterator<? extends I> iterator, final Function<? super I, ? extends O> mappingFunction) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		if (null == mappingFunction) {
			throw new NullPointerException("Invalid mapping function (not null expected)");
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
				return mappingFunction.apply(iterator.next());
			}

			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}

	/**
	 * <p>Wrap an {@code Iterator} from the given {@code Supplier} which iterates until an excluded bound value.</p>
	 * <p><b>Warning</b>: Could result in an infinite loop if the bound value is never supplied.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param bound the bound value or {@code null}
	 * @param <E> the element type
	 * @return the wrapped {@code Iterator}
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.0
	 */
	public static <E> Iterator<E> until(final Supplier<? extends E> supplier, final E bound) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		return new PreparedIterator<>() {
			@Override
			protected boolean isValid(final E next) {
				return bound != next && (null == bound || !bound.equals(next));
			}

			@Override
			protected E prepareNext() {
				return supplier.get();
			}
		};
	}

	/**
	 * <p>Transfer {@code Iterator}'s elements from it current position to a {@code Collection}.</p>
	 * <p><b>Warning</b>: Could result in an memory overflow if the {@code Iterator} does not end.</p>
	 * @param iterator the {@code Iterator} to transfer elements from
	 * @param collection the {@code Collection} to transfer elements to
	 * @param <E> the element type
	 * @return the number of elements transferred
	 * @throws NullPointerException whether the {@code Iterator} or the {@code Collection} is {@code null}
	 * @since 1.0
	 */
	public static <E> long transferTo(final Iterator<? extends E> iterator, final Collection<? super E> collection) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		if (null == collection) {
			throw new NullPointerException("Invalid collection (not null expected)");
		}
		var n = 0L;
		while (iterator.hasNext()) {
			collection.add(iterator.next());
			++n;
		}
		return n;
	}

	/**
	 * <p>Concatenate multiple {@code Iterator}s.</p>
	 * @param iterators {@code Iterator}s to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator}s array or any of the {@code Iterator}s is {@code null}
	 * @since 1.0
	 */
	@SafeVarargs
	public static <E> Iterator<E> concat(final Iterator<? extends E>... iterators) {
		if (null == iterators) {
			throw new NullPointerException("Invalid iterators (not null expected)");
		}
		return concat(Arrays.asList(iterators));
	}

	/**
	 * <p>Concatenate a list of {@code Iterator}s.</p>
	 * @param iterators {@code Iterator}s to concatenate
	 * @param <E> the element type
	 * @return the concatenated {@code Iterator}
	 * @throws NullPointerException if the {@code Iterator}s list or any of the {@code Iterator}s is {@code null}
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterator<E> concat(final List<Iterator<? extends E>> iterators) {
		if (null == iterators) {
			throw new NullPointerException("Invalid iterators (not null expected)");
		}
		for (final var iterator : iterators) {
			if (null == iterator) {
				throw new NullPointerException("Invalid iterator (not null expected)");
			}
		}
		if (iterators.isEmpty()) {
			return Collections.emptyIterator();
		}
		if (1 == iterators.size()) {
			return (Iterator<E>) iterators.get(0);
		}
		return new SequenceIterator<>(iterators);
	}

	/**
	 * <p>Join multiple {@code Iterator}s using a {@code generic array} separator.</p>
	 * @param separator the {@code generic array} sequence to add between each joined {@code Iterator}
	 * @param iterators {@code Iterator}s to join
	 * @param <E> the element type
	 * @return the joined {@code Iterator}
	 * @throws NullPointerException if the separator, the {@code Iterator}s array or any of the {@code Iterator}s is
	 * {@code null}
	 * @since 1.0
	 */
	@SafeVarargs
	public static <E> Iterator<E> join(final E[] separator, final Iterator<? extends E>... iterators) {
		if (null == iterators) {
			throw new NullPointerException("Invalid iterators (not null expected)");
		}
		return join(separator, Arrays.asList(iterators));
	}

	/**
	 * <p>Join a list of {@code Iterator}s using a {@code generic array} separator.</p>
	 * @param separator the {@code generic array} sequence to add between each joined {@code Iterator}
	 * @param iterators {@code Iterator}s to join
	 * @param <E> the element type
	 * @return the joined {@code Iterator}
	 * @throws NullPointerException if the separator, the {@code Iterator}s list or any of the {@code Iterator}s is
	 * {@code null}
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterator<E> join(final E[] separator, final List<Iterator<? extends E>> iterators) {
		if (null == separator) {
			throw new NullPointerException("Invalid separator (not null expected)");
		}
		if (null == iterators) {
			throw new NullPointerException("Invalid iterators (not null expected)");
		}
		for (final var iterator : iterators) {
			if (null == iterator) {
				throw new NullPointerException("Invalid iterator (not null expected)");
			}
		}
		if (0 == separator.length) {
			return concat(iterators);
		}
		if (iterators.isEmpty()) {
			return Collections.emptyIterator();
		}
		if (1 == iterators.size()) {
			return (Iterator<E>) iterators.get(0);
		}
		final var list = new ArrayList<Iterator<? extends E>>();
		final var iterator = iterators.iterator();
		list.add(iterator.next());
		while (iterator.hasNext()) {
			list.add(of(separator));
			list.add(iterator.next());
		}
		return new SequenceIterator<>(list);
	}

	/**
	 * <p>Create a {@code PrimitiveIterator} from {@code int} values.</p>
	 * @param values {@code int} values to convert
	 * @return the created {@code PrimitiveIterator}
	 * @throws NullPointerException if {@code int} values are {@code null}
	 * @since 1.0
	 */
	public static PrimitiveIterator.OfInt ofInt(final int... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY_INT;
		}
		return Arrays.stream(values).iterator();
	}

	/**
	 * <p>Create a {@code PrimitiveIterator} from {@code long} values.</p>
	 * @param values {@code long} values to convert
	 * @return the created {@code PrimitiveIterator}
	 * @throws NullPointerException if {@code long} values are {@code null}
	 * @since 1.0
	 */
	public static PrimitiveIterator.OfLong ofLong(final long... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY_LONG;
		}
		return Arrays.stream(values).iterator();
	}

	/**
	 * <p>Create a {@code PrimitiveIterator} from {@code double} values.</p>
	 * @param values {@code double} values to convert
	 * @return the created {@code PrimitiveIterator}
	 * @throws NullPointerException if {@code double} values are {@code null}
	 * @since 1.0
	 */
	public static PrimitiveIterator.OfDouble ofDouble(final double... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY_DOUBLE;
		}
		return Arrays.stream(values).iterator();
	}

	/**
	 * <p>Create an {@code Iterator} from {@code generic} values.</p>
	 * @param values {@code generic} values to convert
	 * @param <E> the element type
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if {@code generic} values are {@code null}
	 * @since 1.0
	 */
	@SafeVarargs
	public static <E> Iterator<E> of(final E... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return Collections.emptyIterator();
		}
		return Arrays.stream(values).iterator();
	}

	/**
	 * <p>Convert an {@code Iterator} from it current position to an unmodifiable {@code Set}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @param <E> the element type
	 * @return the created unmodifiable {@code Set}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0
	 */
	public static <E> Set<E> toSet(final Iterator<? extends E> iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		if (!iterator.hasNext()) {
			return Collections.emptySet();
		}
		final var set = new HashSet<E>();
		transferTo(iterator, set);
		return Collections.unmodifiableSet(set);
	}

	/**
	 * <p>Convert an {@code Iterator} from it current position to an unmodifiable {@code List}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @param <E> the element type
	 * @return the created unmodifiable {@code List}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0
	 */
	public static <E> List<E> toList(final Iterator<? extends E> iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		if (!iterator.hasNext()) {
			return Collections.emptyList();
		}
		final var list = new ArrayList<E>();
		transferTo(iterator, list);
		return Collections.unmodifiableList(list);
	}

	/**
	 * <p>Convert an {@code Iterator} from it current position to an {@code Enumeration}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @param <E> the element type
	 * @return the created {@code Enumeration}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0
	 */
	public static <E> Enumeration<E> toEnumeration(final Iterator<? extends E> iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
		}
		return new Enumeration<>() {
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public E nextElement() {
				return iterator.next();
			}

			@Override
			@SuppressWarnings("unchecked")
			public Iterator<E> asIterator() {
				return (Iterator<E>) iterator;
			}
		};
	}

	/**
	 * <p>Create an {@code Iterator} with an {@code InputStream} from his current position.</p>
	 * <p><b>Note</b>: The {@code InputStream} will not be closed.</p>
	 * @param inputStream the {@code InputStream} to convert
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code InputStream} is {@code null}
	 * @since 1.0
	 */
	public static Iterator<Integer> of(final InputStream inputStream) {
		if (null == inputStream) {
			throw new NullPointerException("Invalid input stream (not null expected)");
		}
		return until(ThrowableSupplier.unchecked(inputStream::read), -1);
	}

	/**
	 * <p>Convert an {@code Iterator} from it current position to an {@code InputStream}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @return the created {@code InputStream}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0
	 */
	public static InputStream toInputStream(final Iterator<Integer> iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
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
	 * <p>Create an {@code Iterator} with a {@code Reader} from his current position.</p>
	 * <p><b>Note</b>: The {@code Reader} will not be closed.</p>
	 * @param reader the {@code Reader} to convert
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code Reader} is {@code null}
	 * @since 1.0
	 */
	public static Iterator<Integer> of(final Reader reader) {
		if (null == reader) {
			throw new NullPointerException("Invalid reader (not null expected)");
		}
		return until(ThrowableSupplier.unchecked(reader::read), -1);
	}

	/**
	 * <p>Convert an {@code Iterator} from it current position to a {@code Reader}.</p>
	 * @param iterator the {@code Iterator} to convert
	 * @return the created {@code Reader}
	 * @throws NullPointerException if the {@code Iterator} is {@code null}
	 * @since 1.0
	 */
	public static Reader toReader(final Iterator<Integer> iterator) {
		if (null == iterator) {
			throw new NullPointerException("Invalid iterator (not null expected)");
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
			public int read(@NotNull final char[] cbuf, final int off, final int len) {
				if (0 > off || off > cbuf.length || 0 > len || off + len > cbuf.length) {
					throw new IndexOutOfBoundsException();
				}
				if (0 == len) {
					return 0;
				}
				if (!iterator.hasNext()) {
					return -1;
				}
				var n = 0;
				do {
					cbuf[off + n++] = (char) iterator.next().intValue();
				} while (n < len && iterator.hasNext());
				return n;
			}

			@Override
			public void close() {
				// Nothing to do
			}
		};
	}

	/**
	 * <p>Create an {@code Iterator} with a {@code BufferedReader} from his current position.</p>
	 * <p><b>Note</b>: The {@code BufferedReader} will not be closed.</p>
	 * @param bufferedReader the {@code BufferedReader} to convert
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code BufferedReader} is {@code null}
	 * @since 1.0
	 */
	public static Iterator<String> of(final BufferedReader bufferedReader) {
		if (null == bufferedReader) {
			throw new NullPointerException("Invalid buffered reader (not null expected)");
		}
		return until(ThrowableSupplier.unchecked(bufferedReader::readLine), null);
	}

	/**
	 * <p>Create an {@code Iterator} with a {@code LineReader} from his current position.</p>
	 * <p><b>Note</b>: The {@code LineReader} will not be closed.</p>
	 * @param lineReader the {@code LineReader} to convert
	 * @return the created {@code Iterator}
	 * @throws NullPointerException if the {@code LineReader} is {@code null}
	 * @since 1.0
	 */
	public static Iterator<String> of(final LineReader lineReader) {
		if (null == lineReader) {
			throw new NullPointerException("Invalid line reader (not null expected)");
		}
		return until(ThrowableSupplier.unchecked(lineReader::read), null);
	}
}