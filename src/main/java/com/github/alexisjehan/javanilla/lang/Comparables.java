/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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
package com.github.alexisjehan.javanilla.lang;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

/**
 * A utility class that provides {@link Comparable} tools.
 * @since 1.3.1
 */
public final class Comparables {

	/**
	 * Constructor.
	 * @since 1.3.1
	 */
	private Comparables() {}

	/**
	 * Tell if a {@link Comparable} is equal to another one.
	 * @param comparable the {@link Comparable}
	 * @param other the other {@link Comparable}
	 * @param <T> the type of objects that this object may be compared to
	 * @return {@code true} if the {@link Comparable} is equal to the other one
	 * @throws NullPointerException if any {@link Comparable} is {@code null}
	 * @since 1.3.1
	 */
	public static <T extends Comparable<T>> boolean isEqualTo(final T comparable, final T other) {
		Ensure.notNull("comparable", comparable);
		Ensure.notNull("other", other);
		return 0 == comparable.compareTo(other);
	}

	/**
	 * Tell if a {@link Comparable} is not equal to another one.
	 * @param comparable the {@link Comparable}
	 * @param other the other {@link Comparable}
	 * @param <T> the type of objects that this object may be compared to
	 * @return {@code true} if the {@link Comparable} is not equal to the other one
	 * @throws NullPointerException if any {@link Comparable} is {@code null}
	 * @since 1.3.1
	 */
	public static <T extends Comparable<T>> boolean isNotEqualTo(final T comparable, final T other) {
		Ensure.notNull("comparable", comparable);
		Ensure.notNull("other", other);
		return 0 != comparable.compareTo(other);
	}

	/**
	 * Tell if a {@link Comparable} is lower than another one.
	 * @param comparable the {@link Comparable}
	 * @param other the other {@link Comparable}
	 * @param <T> the type of objects that this object may be compared to
	 * @return {@code true} if the {@link Comparable} is lower than the other one
	 * @throws NullPointerException if any {@link Comparable} is {@code null}
	 * @since 1.3.1
	 */
	public static <T extends Comparable<T>> boolean isLowerThan(final T comparable, final T other) {
		Ensure.notNull("comparable", comparable);
		Ensure.notNull("other", other);
		return 0 > comparable.compareTo(other);
	}

	/**
	 * Tell if a {@link Comparable} is lower than or equal to another one.
	 * @param comparable the {@link Comparable}
	 * @param other the other {@link Comparable}
	 * @param <T> the type of objects that this object may be compared to
	 * @return {@code true} if the {@link Comparable} is lower than or equal to the other one
	 * @throws NullPointerException if any {@link Comparable} is {@code null}
	 * @since 1.3.1
	 */
	public static <T extends Comparable<T>> boolean isLowerThanOrEqualTo(final T comparable, final T other) {
		Ensure.notNull("comparable", comparable);
		Ensure.notNull("other", other);
		return 0 >= comparable.compareTo(other);
	}

	/**
	 * Tell if a {@link Comparable} is greater than another one.
	 * @param comparable the {@link Comparable}
	 * @param other the other {@link Comparable}
	 * @param <T> the type of objects that this object may be compared to
	 * @return {@code true} if the {@link Comparable} is greater than the other one
	 * @throws NullPointerException if any {@link Comparable} is {@code null}
	 * @since 1.3.1
	 */
	public static <T extends Comparable<T>> boolean isGreaterThan(final T comparable, final T other) {
		Ensure.notNull("comparable", comparable);
		Ensure.notNull("other", other);
		return 0 < comparable.compareTo(other);
	}

	/**
	 * Tell if a {@link Comparable} is greater than or equal to another one.
	 * @param comparable the {@link Comparable}
	 * @param other the other {@link Comparable}
	 * @param <T> the type of objects that this object may be compared to
	 * @return {@code true} if the {@link Comparable} is greater than or equal to the other one
	 * @throws NullPointerException if any {@link Comparable} is {@code null}
	 * @since 1.3.1
	 */
	public static <T extends Comparable<T>> boolean isGreaterThanOrEqualTo(final T comparable, final T other) {
		Ensure.notNull("comparable", comparable);
		Ensure.notNull("other", other);
		return 0 <= comparable.compareTo(other);
	}

	/**
	 * Tell if a {@link Comparable} is between both other ones.
	 * @param comparable the {@link Comparable}
	 * @param from the from {@link Comparable}
	 * @param to the to {@link Comparable}
	 * @param <T> the type of objects that this object may be compared to
	 * @return {@code true} if the {@link Comparable} is between both other ones
	 * @throws NullPointerException if any {@link Comparable} is {@code null}
	 * @since 1.3.1
	 */
	public static <T extends Comparable<T>> boolean isBetween(final T comparable, final T from, final T to) {
		Ensure.notNull("comparable", comparable);
		Ensure.notNull("from", from);
		Ensure.notNull("to", to);
		return 0 <= comparable.compareTo(from) && 0 >= comparable.compareTo(to);
	}
}