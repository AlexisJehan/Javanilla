/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.collection;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * <p>An utility class that provides {@link Set} tools.</p>
 * @since 1.0.0
 */
public final class Sets {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Sets() {
		// Not available
	}

	/**
	 * <p>Wrap a {@link Set} replacing {@code null} by an empty one.</p>
	 * @param set the {@link Set} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-{@code null} {@link Set}
	 * @since 1.0.0
	 */
	public static <E> Set<E> nullToEmpty(final Set<E> set) {
		return nullToDefault(set, Set.of());
	}

	/**
	 * <p>Wrap a {@link SortedSet} replacing {@code null} by an empty one.</p>
	 * @param sortedSet the {@link SortedSet} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-{@code null} {@link SortedSet}
	 * @since 1.0.0
	 */
	public static <E> SortedSet<E> nullToEmpty(final SortedSet<E> sortedSet) {
		return nullToDefault(sortedSet, Collections.emptySortedSet());
	}

	/**
	 * <p>Wrap a {@link NavigableSet} replacing {@code null} by an empty one.</p>
	 * @param navigableSet the {@link NavigableSet} or {@code null}
	 * @param <E> the type of elements maintained by the set
	 * @return a non-{@code null} {@link NavigableSet}
	 * @since 1.0.0
	 */
	public static <E> NavigableSet<E> nullToEmpty(final NavigableSet<E> navigableSet) {
		return nullToDefault(navigableSet, Collections.emptyNavigableSet());
	}

	/**
	 * <p>Wrap a {@link Set} replacing {@code null} by a default one.</p>
	 * @param set the {@link Set} or {@code null}
	 * @param defaultSet the default {@link Set}
	 * @param <S> the {@link Set} type
	 * @return a non-{@code null} {@link Set}
	 * @throws NullPointerException if the default {@link Set} is {@code null}
	 * @since 1.1.0
	 */
	public static <S extends Set<?>> S nullToDefault(final S set, final S defaultSet) {
		Ensure.notNull("defaultSet", defaultSet);
		return null != set ? set : defaultSet;
	}

	/**
	 * <p>Wrap a {@link Set} replacing an empty one by {@code null}.</p>
	 * @param set the {@link Set} or {@code null}
	 * @param <S> the {@link Set} type
	 * @return a non-empty {@link Set} or {@code null}
	 * @since 1.0.0
	 */
	public static <S extends Set<?>> S emptyToNull(final S set) {
		return emptyToDefault(set, null);
	}

	/**
	 * <p>Wrap a {@link Set} replacing an empty one by a default {@link Set}.</p>
	 * @param set the {@link Set} or {@code null}
	 * @param defaultSet the default {@link Set} or {@code null}
	 * @param <S> the {@link Set} type
	 * @return a non-empty {@link Set} or {@code null}
	 * @throws IllegalArgumentException if the default {@link Set} is empty
	 * @since 1.1.0
	 */
	public static <S extends Set<?>> S emptyToDefault(final S set, final S defaultSet) {
		if (null != defaultSet) {
			Ensure.notNullAndNotEmpty("defaultSet", defaultSet);
		}
		return null == set || !set.isEmpty() ? set : defaultSet;
	}

	/**
	 * <p>Unify multiple {@link Set}s.</p>
	 * @param sets the {@link Set} array to unify
	 * @param <E> the type of elements maintained by the set
	 * @return the union {@link Set}
	 * @throws NullPointerException if the {@link Set} array or any of them is {@code null}
	 * @deprecated since 1.6.0, use {@link #unify(Set[])} instead
	 * @since 1.3.0
	 */
	@SafeVarargs
	@Deprecated(since = "1.6.0")
	@SuppressWarnings("varargs")
	public static <E> Set<E> union(final Set<? extends E>... sets) {
		return unify(sets);
	}

	/**
	 * <p>Unify multiple {@link Set}s.</p>
	 * @param sets the {@link Set} {@link Collection} to unify
	 * @param <E> the type of elements maintained by the set
	 * @return the union {@link Set}
	 * @throws NullPointerException if the {@link Set} {@link Collection} or any of them is {@code null}
	 * @deprecated since 1.6.0, use {@link #unify(Collection)} instead
	 * @since 1.3.0
	 */
	@Deprecated(since = "1.6.0")
	public static <E> Set<E> union(final Collection<? extends Set<? extends E>> sets) {
		return unify(sets);
	}

	/**
	 * <p>Unify multiple {@link Set}s.</p>
	 * @param sets the {@link Set} array to unify
	 * @param <E> the type of elements maintained by the set
	 * @return the union {@link Set}
	 * @throws NullPointerException if the {@link Set} array or any of them is {@code null}
	 * @since 1.6.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Set<E> unify(final Set<? extends E>... sets) {
		Ensure.notNullAndNotNullElements("sets", sets);
		return unify(List.of(sets));
	}

	/**
	 * <p>Unify multiple {@link Set}s.</p>
	 * @param sets the {@link Set} {@link Collection} to unify
	 * @param <E> the type of elements maintained by the set
	 * @return the union {@link Set}
	 * @throws NullPointerException if the {@link Set} {@link Collection} or any of them is {@code null}
	 * @since 1.6.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Set<E> unify(final Collection<? extends Set<? extends E>> sets) {
		Ensure.notNullAndNotNullElements("sets", sets);
		final var size = sets.size();
		if (0 == size) {
			return Set.of();
		}
		if (1 == size) {
			return (Set<E>) sets.iterator().next();
		}
		final var result = new HashSet<E>();
		for (final var set : sets) {
			result.addAll(set);
		}
		return result;
	}

	/**
	 * <p>Intersect multiple {@link Set}s.</p>
	 * @param sets the {@link Set} array to intersect
	 * @param <E> the type of elements maintained by the set
	 * @return the intersection {@link Set}
	 * @throws NullPointerException if the {@link Set} array or any of them is {@code null}
	 * @since 1.3.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Set<E> intersect(final Set<? extends E>... sets) {
		Ensure.notNullAndNotNullElements("sets", sets);
		return intersect(List.of(sets));
	}

	/**
	 * <p>Intersect multiple {@link Set}s.</p>
	 * @param sets the {@link Set} {@link Collection} to intersect
	 * @param <E> the type of elements maintained by the set
	 * @return the intersection {@link Set}
	 * @throws NullPointerException if the {@link Set} {@link Collection} or any of them is {@code null}
	 * @since 1.3.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> Set<E> intersect(final Collection<? extends Set<? extends E>> sets) {
		Ensure.notNullAndNotNullElements("sets", sets);
		final var size = sets.size();
		if (0 == size) {
			return Set.of();
		}
		if (1 == size) {
			return (Set<E>) sets.iterator().next();
		}
		final var iterator = sets.iterator();
		final var set = new HashSet<E>(iterator.next());
		while (iterator.hasNext()) {
			set.retainAll(iterator.next());
		}
		return set;
	}

	/**
	 * <p>Create an ordered {@link Set} from multiple elements.</p>
	 * @param elements the elements array to convert
	 * @param <E> the type of elements maintained by the set
	 * @return the created ordered {@link Set}
	 * @throws NullPointerException if the elements array is {@code null}
	 * @since 1.0.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> Set<E> ofOrdered(final E... elements) {
		Ensure.notNull("elements", elements);
		if (0 == elements.length) {
			return Set.of();
		}
		if (1 == elements.length) {
			return Set.of(elements[0]);
		}
		final var set = new LinkedHashSet<E>(elements.length);
		Collections.addAll(set, elements);
		return set;
	}
}