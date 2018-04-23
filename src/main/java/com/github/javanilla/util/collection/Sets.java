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
package com.github.javanilla.util.collection;

import java.util.*;

/**
 * <p>An utility class that provides {@link Set} tools.</p>
 * @since 1.0
 */
public final class Sets {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private Sets() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code Set} replacing {@code null} by an empty {@code Set}.</p>
	 * @param set a {@code Set} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-{@code null} {@code Set}
	 * @since 1.0
	 */
	public static <E> Set<E> nullToEmpty(final Set<E> set) {
		return null != set ? set : Collections.emptySet();
	}

	/**
	 * <p>Wrap a {@code SortedSet} replacing {@code null} by an empty {@code SortedSet}.</p>
	 * @param sortedSet a {@code SortedSet} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-{@code null} {@code SortedSet}
	 * @since 1.0
	 */
	public static <E> SortedSet<E> nullToEmpty(final SortedSet<E> sortedSet) {
		return null != sortedSet ? sortedSet : Collections.emptySortedSet();
	}

	/**
	 * <p>Wrap a {@code NavigableSet} replacing {@code null} by an empty {@code NavigableSet}.</p>
	 * @param navigableSet a {@code NavigableSet} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-{@code null} {@code NavigableSet}
	 * @since 1.0
	 */
	public static <E> NavigableSet<E> nullToEmpty(final NavigableSet<E> navigableSet) {
		return null != navigableSet ? navigableSet : Collections.emptyNavigableSet();
	}

	/**
	 * <p>Wrap a {@code Set} replacing an empty one by {@code null}.</p>
	 * @param set a {@code Set} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-empty {@code Set} or {@code null}
	 * @since 1.0
	 */
	public static <E> Set<E> emptyToNull(final Set<E> set) {
		return null != set && !set.isEmpty() ? set : null;
	}

	/**
	 * <p>Wrap a {@code SortedSet} replacing an empty one by {@code null}.</p>
	 * @param sortedSet a {@code SortedSet} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-empty {@code SortedSet} or {@code null}
	 * @since 1.0
	 */
	public static <E> SortedSet<E> emptyToNull(final SortedSet<E> sortedSet) {
		return null != sortedSet && !sortedSet.isEmpty() ? sortedSet : null;
	}

	/**
	 * <p>Wrap a {@code NavigableSet} replacing an empty one by {@code null}.</p>
	 * @param navigableSet a {@code NavigableSet} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-empty {@code NavigableSet} or {@code null}
	 * @since 1.0
	 */
	public static <E> NavigableSet<E> emptyToNull(final NavigableSet<E> navigableSet) {
		return null != navigableSet && !navigableSet.isEmpty() ? navigableSet : null;
	}

	/**
	 * <p>Create an ordered {@code Set} with an array of elements.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the {@code Set} implementation.</p>
	 * @param elements elements of the {@code Set}
	 * @param <E> the type of elements maintained by the set
	 * @return the created ordered {@code Set}
	 * @throws NullPointerException if the elements array is {@code null}
	 * @since 1.0
	 */
	@SafeVarargs
	public static <E> Set<E> ofOrdered(final E... elements) {
		if (null == elements) {
			throw new NullPointerException("Invalid elements (not null expected)");
		}
		if (0 == elements.length) {
			return Collections.emptySet();
		} else if (1 == elements.length) {
			return Collections.singleton(elements[0]);
		}
		final var set = new LinkedHashSet<E>(elements.length);
		Collections.addAll(set, elements);
		return Collections.unmodifiableSet(set);
	}
}