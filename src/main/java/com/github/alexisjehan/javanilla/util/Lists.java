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
package com.github.alexisjehan.javanilla.util;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>An utility class that provides {@link List} tools.</p>
 * @since 1.8.0
 */
public final class Lists {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.8.0
	 */
	private Lists() {
		// Not available
	}

	/**
	 * <p>Wrap a {@link List} replacing {@code null} by an empty one.</p>
	 * @param list the {@link List} or {@code null}
	 * @param <E> the type of elements in the list
	 * @return a non-{@code null} {@link List}
	 * @since 1.8.0
	 */
	public static <E> List<E> nullToEmpty(final List<E> list) {
		return nullToDefault(list, List.of());
	}

	/**
	 * <p>Wrap a {@link List} replacing {@code null} by a default one.</p>
	 * @param list the {@link List} or {@code null}
	 * @param defaultList the default {@link List}
	 * @param <L> the {@link List} type
	 * @return a non-{@code null} {@link List}
	 * @throws NullPointerException if the default {@link List} is {@code null}
	 * @since 1.8.0
	 */
	public static <L extends List<?>> L nullToDefault(final L list, final L defaultList) {
		Ensure.notNull("defaultList", defaultList);
		return null != list ? list : defaultList;
	}

	/**
	 * <p>Wrap a {@link List} replacing an empty one by {@code null}.</p>
	 * @param list the {@link List} or {@code null}
	 * @param <L> the {@link List} type
	 * @return a non-empty {@link List} or {@code null}
	 * @since 1.8.0
	 */
	public static <L extends List<?>> L emptyToNull(final L list) {
		return emptyToDefault(list, null);
	}

	/**
	 * <p>Wrap a {@link List} replacing an empty one by a default {@link List}.</p>
	 * @param list the {@link List} or {@code null}
	 * @param defaultList the default {@link List} or {@code null}
	 * @param <L> the {@link List} type
	 * @return a non-empty {@link List} or {@code null}
	 * @throws IllegalArgumentException if the default {@link List} is empty
	 * @since 1.8.0
	 */
	public static <L extends List<?>> L emptyToDefault(final L list, final L defaultList) {
		if (null != defaultList) {
			Ensure.notNullAndNotEmpty("defaultList", defaultList);
		}
		return null == list || !list.isEmpty() ? list : defaultList;
	}

	/**
	 * <p>Concatenate multiple {@link List}s.</p>
	 * @param lists the {@link List} array to concatenate
	 * @param <E> the type of elements in the list
	 * @return the concatenated {@link List}
	 * @throws NullPointerException if the {@link List} array or any of them is {@code null}
	 * @since 1.8.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> List<E> concat(final List<? extends E>... lists) {
		Ensure.notNullAndNotNullElements("lists", lists);
		return concat(List.of(lists));
	}

	/**
	 * <p>Concatenate multiple {@link List}s.</p>
	 * @param lists the {@link List} {@link List} to concatenate
	 * @param <E> the type of elements in the list
	 * @return the concatenated {@link List}
	 * @throws NullPointerException if the {@link List} {@link List} or any of them is {@code null}
	 * @since 1.8.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> concat(final List<? extends List<? extends E>> lists) {
		Ensure.notNullAndNotNullElements("lists", lists);
		final var size = lists.size();
		if (0 == size) {
			return List.of();
		}
		if (1 == size) {
			return (List<E>) lists.get(0);
		}
		return lists.stream().flatMap(List::stream).collect(Collectors.toList());
	}

	/**
	 * <p>Join multiple {@link List}s using an {@link Object} array separator.</p>
	 * @param separator the {@link Object} array separator
	 * @param lists the {@link List} array to join
	 * @param <E> the type of elements in the list
	 * @return the joined {@link List}
	 * @throws NullPointerException if the {@link Object} array separator, the {@link List} array or any of them is
	 *         {@code null}
	 * @since 1.8.0
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <E> List<E> join(final E[] separator, final List<? extends E>... lists) {
		Ensure.notNullAndNotNullElements("lists", lists);
		return join(separator, List.of(lists));
	}

	/**
	 * <p>Join multiple {@link List}s using an {@link Object} array separator.</p>
	 * @param separator the {@link Object} array separator
	 * @param lists the {@link List} {@link List} to join
	 * @param <E> the type of elements in the list
	 * @return the joined {@link List}
	 * @throws NullPointerException if the {@link Object} array separator, the {@link List} {@link List} or any of them
	 *         is {@code null}
	 * @since 1.8.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> join(final E[] separator, final List<? extends List<? extends E>> lists) {
		Ensure.notNull("separator", separator);
		Ensure.notNullAndNotNullElements("lists", lists);
		if (0 == separator.length) {
			return concat(lists);
		}
		final var size = lists.size();
		if (0 == size) {
			return List.of();
		}
		if (1 == size) {
			return (List<E>) lists.get(0);
		}
		final var list = new ArrayList<E>(lists.stream().mapToInt(List::size).sum() + (lists.size() - 1) * separator.length);
		final var iterator = lists.iterator();
		list.addAll(iterator.next());
		while (iterator.hasNext()) {
			Collections.addAll(list, separator);
			list.addAll(iterator.next());
		}
		return list;
	}

	/**
	 * <p>Optionally get the first element of a {@link List}.</p>
	 * @param list the {@link List} to get the first element from
	 * @param <E> the type of elements in the list
	 * @return a {@link NullableOptional} containing the first element if the {@link List} is not empty
	 * @throws NullPointerException if the {@link List} is {@code null}
	 * @since 1.8.0
	 */
	public static <E> NullableOptional<E> getOptionalFirst(final List<? extends E> list) {
		Ensure.notNull("list", list);
		if (list.isEmpty()) {
			return NullableOptional.empty();
		}
		if (list instanceof LinkedList) {
			return NullableOptional.of(((LinkedList<? extends E>) list).getFirst());
		}
		return NullableOptional.of(list.get(0));
	}

	/**
	 * <p>Optionally get the last element of a {@link List}.</p>
	 * @param list the {@link List} to get the last element from
	 * @param <E> the type of elements in the list
	 * @return a {@link NullableOptional} containing the last element if the {@link List} is not empty
	 * @throws NullPointerException if the {@link List} is {@code null}
	 * @since 1.8.0
	 */
	public static <E> NullableOptional<E> getOptionalLast(final List<? extends E> list) {
		Ensure.notNull("list", list);
		if (list.isEmpty()) {
			return NullableOptional.empty();
		}
		if (list instanceof LinkedList) {
			return NullableOptional.of(((LinkedList<? extends E>) list).getLast());
		}
		return NullableOptional.of(list.get(list.size() - 1));
	}
}