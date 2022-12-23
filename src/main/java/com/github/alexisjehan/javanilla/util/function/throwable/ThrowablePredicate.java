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
package com.github.alexisjehan.javanilla.util.function.throwable;

import com.github.alexisjehan.javanilla.lang.Throwables;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import internal.ExcludeFromJacocoGeneratedReport;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * <p>Interface for a {@link Predicate} that may throw a {@link Throwable}.</p>
 * @param <T> the type of the input to the predicate
 * @param <X> the type of the {@link Throwable}
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.function.ThrowablePredicate} instead
 * @since 1.0.0
 */
@FunctionalInterface
@Deprecated(since = "1.8.0")
public interface ThrowablePredicate<T, X extends Throwable> {

	/**
	 * <p>Evaluates this predicate on the given argument.</p>
	 * @param t the input argument
	 * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
	 * @throws X may throw a {@link Throwable}
	 * @since 1.0.0
	 */
	boolean test(final T t) throws X;

	/**
	 * <p>Returns a composed {@link ThrowablePredicate} that represents a short-circuiting logical AND of this predicate
	 * and another.</p>
	 * @param other a {@link ThrowablePredicate} that will be logically-ANDed with this predicate
	 * @return a composed {@link ThrowablePredicate} that represents the short-circuiting logical AND of this predicate
	 *         and the other predicate
	 * @throws NullPointerException if the other {@link ThrowablePredicate} is {@code null}
	 * @since 1.0.0
	 */
	default ThrowablePredicate<T, X> and(final ThrowablePredicate<? super T, ? extends X> other) {
		Ensure.notNull("other", other);
		return t -> test(t) && other.test(t);
	}

	/**
	 * <p>Returns a {@link ThrowablePredicate} that represents the logical negation of this predicate.</p>
	 * @return a {@link ThrowablePredicate} that represents the logical negation of this predicate
	 * @since 1.0.0
	 */
	default ThrowablePredicate<T, X> negate() {
		return t -> !test(t);
	}

	/**
	 * <p>Returns a composed {@link ThrowablePredicate} that represents a short-circuiting logical OR of this predicate
	 * and another.</p>
	 * @param other a {@link ThrowablePredicate} that will be logically-ORed with this predicate
	 * @return a composed {@link ThrowablePredicate} that represents the short-circuiting logical OR of this predicate
	 *         and the other predicate
	 * @throws NullPointerException if the other {@link ThrowablePredicate} is {@code null}
	 * @since 1.0.0
	 */
	default ThrowablePredicate<T, X> or(final ThrowablePredicate<? super T, ? extends X> other) {
		Ensure.notNull("other", other);
		return t -> test(t) || other.test(t);
	}

	/**
	 * <p>Returns a {@link ThrowablePredicate} that tests if two arguments are equal according to
	 * {@link Objects#equals(Object, Object)}.</p>
	 * @param <T> the type of arguments to the predicate
	 * @param targetRef the object reference with which to compare for equality, which may be {@code null}
	 * @return a {@link ThrowablePredicate} that tests if two arguments are equal according to
	 *         {@link Objects#equals(Object, Object)}
	 * @since 1.4.0
	 */
	static <T> Predicate<T> isEqual(final Object targetRef) {
		return null == targetRef
				? Objects::isNull
				: targetRef::equals;
	}

	/**
	 * <p>Converts the given {@link ThrowablePredicate} to a {@link Predicate} that may throw an unchecked
	 * {@link Throwable}.</p>
	 * @param throwablePredicate the {@link ThrowablePredicate} to convert
	 * @param <T> the type of the input to the predicate
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Predicate}
	 * @throws NullPointerException if the {@link ThrowablePredicate} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> Predicate<T> unchecked(final ThrowablePredicate<? super T, ? extends X> throwablePredicate) {
		Ensure.notNull("throwablePredicate", throwablePredicate);
		return t -> {
			try {
				return throwablePredicate.test(t);
			} catch (final Throwable e) {
				throw Throwables.unchecked(e);
			}
		};
	}

	/**
	 * <p>Converts the given {@link ThrowablePredicate} to a {@link Predicate} that may throw a sneaky
	 * {@link Throwable}.</p>
	 * @param throwablePredicate the {@link ThrowablePredicate} to convert
	 * @param <T> the type of the input to the predicate
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Predicate}
	 * @throws NullPointerException if the {@link ThrowablePredicate} is {@code null}
	 * @since 1.7.0
	 */
	static <T, X extends Throwable> Predicate<T> sneaky(final ThrowablePredicate<? super T, ? extends X> throwablePredicate) {
		Ensure.notNull("throwablePredicate", throwablePredicate);
		return new Predicate<>() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			@ExcludeFromJacocoGeneratedReport
			public boolean test(final T t) {
				try {
					return throwablePredicate.test(t);
				} catch (final Throwable e) {
					Throwables.sneakyThrow(e);
					throw new AssertionError(e);
				}
			}
		};
	}

	/**
	 * <p>Create a {@link ThrowablePredicate} from the given {@link Predicate}.</p>
	 * @param predicate the {@link Predicate} to convert
	 * @param <T> the type of the input to the predicate
	 * @param <X> the type of the {@link Throwable}
	 * @return the created {@link ThrowablePredicate}
	 * @throws NullPointerException if the {@link Predicate} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> ThrowablePredicate<T, X> of(final Predicate<? super T> predicate) {
		Ensure.notNull("predicate", predicate);
		return predicate::test;
	}
}