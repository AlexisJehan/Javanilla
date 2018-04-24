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

import java.util.Collections;
import java.util.List;

/**
 * <p>An utility class that provides {@link List} tools.</p>
 * @since 1.0
 */
public final class Lists {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private Lists() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code List} replacing {@code null} by an empty {@code List}.</p>
	 * @param list a {@code List} or {@code null}
	 * @param <E> the type of elements in the list
	 * @return a non-{@code null} {@code List}
	 * @since 1.0
	 */
	public static <E> List<E> nullToEmpty(final List<E> list) {
		return null != list ? list : Collections.emptyList();
	}

	/**
	 * <p>Wrap a {@code List} replacing an empty one by {@code null}.</p>
	 * @param list a {@code List} or {@code null}
	 * @param <E> the type of elements in the list
	 * @return a non-empty {@code List} or {@code null}
	 * @since 1.0
	 */
	public static <E> List<E> emptyToNull(final List<E> list) {
		return null != list && !list.isEmpty() ? list : null;
	}
}