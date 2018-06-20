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
package com.github.alexisjehan.javanilla.util.collection;

import com.github.alexisjehan.javanilla.util.NullableOptional;

import java.util.Collections;
import java.util.List;

/**
 * <p>An utility class that provides {@link List} tools.</p>
 * @since 1.0.0
 */
public final class Lists {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Lists() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code List} replacing {@code null} by an empty {@code List}.</p>
	 * @param list a {@code List} or {@code null}
	 * @param <E> the type of elements in the list
	 * @return the non-{@code null} {@code List}
	 * @since 1.0.0
	 */
	public static <E> List<E> nullToEmpty(final List<E> list) {
		return nullToDefault(list, Collections.emptyList());
	}

	/**
	 * <p>Wrap a {@code List} replacing {@code null} by a default {@code List}.</p>
	 * @param list a {@code List} or {@code null}
	 * @param defaultList the default {@code List}
	 * @param <E> the type of elements in the list
	 * @return the non-{@code null} {@code List}
	 * @throws NullPointerException if the default {@code List} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> List<E> nullToDefault(final List<E> list, final List<E> defaultList) {
		if (null == defaultList) {
			throw new NullPointerException("Invalid default list (not null expected)");
		}
		return null != list ? list : defaultList;
	}

	/**
	 * <p>Wrap a {@code List} replacing an empty one by {@code null}.</p>
	 * @param list a {@code List} or {@code null}
	 * @param <E> the type of elements in the list
	 * @return the non-empty {@code List} or {@code null}
	 * @since 1.0.0
	 */
	public static <E> List<E> emptyToNull(final List<E> list) {
		return emptyToDefault(list, null);
	}

	/**
	 * <p>Wrap a {@code List} replacing an empty one by a default {@code List}.</p>
	 * @param list a {@code List} or {@code null}
	 * @param defaultList the default {@code List} or {@code null}
	 * @param <E> the type of elements in the list
	 * @return the non-empty {@code List} or {@code null}
	 * @throws IllegalArgumentException if the default {@code List} is empty
	 * @since 1.1.0
	 */
	public static <E> List<E> emptyToDefault(final List<E> list, final List<E> defaultList) {
		if (null != defaultList && defaultList.isEmpty()) {
			throw new IllegalArgumentException("Invalid default list (not empty expected)");
		}
		if (null == list) {
			return null;
		}
		return !list.isEmpty() ? list : defaultList;
	}

	/**
	 * <p>Optionally return the first element of a {@code List}.</p>
	 * @param list the {@code List} to get the first element from
	 * @param <E> the type of elements in the list
	 * @return a {@code NullableOptional} containing the first element if the {@code List} is not empty
	 * @throws NullPointerException if the {@code List} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> NullableOptional<E> getFirst(final List<E> list) {
		if (null == list) {
			throw new NullPointerException("Invalid list (not null expected)");
		}
		if (list.isEmpty()) {
			return NullableOptional.empty();
		}
		return NullableOptional.of(list.get(0));
	}

	/**
	 * <p>Optionally return the last element of a {@code List}.</p>
	 * @param list the {@code List} to get the last element from
	 * @param <E> the type of elements in the list
	 * @return a {@code NullableOptional} containing the last element if the {@code List} is not empty
	 * @throws NullPointerException if the {@code List} is {@code null}
	 * @since 1.1.0
	 */
	public static <E> NullableOptional<E> getLast(final List<E> list) {
		if (null == list) {
			throw new NullPointerException("Invalid list (not null expected)");
		}
		if (list.isEmpty()) {
			return NullableOptional.empty();
		}
		return NullableOptional.of(list.get(list.size() - 1));
	}
}