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

import java.util.function.BiPredicate;

/**
 * <p>Interface for a {@link BiPredicate} that may throw a {@link Throwable}.</p>
 * @param <T> the type of the first argument to the predicate
 * @param <U> the type of the second argument the predicate
 * @param <X> the type of the {@link Throwable}
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableBiPredicate<T, U, X extends Throwable> {

	/**
	 * <p>Evaluates this predicate on the given arguments.</p>
	 * @param t the first input argument
	 * @param u the second input argument
	 * @return {@code true} if the input arguments match the predicate, otherwise {@code false}
	 * @throws X may throw a {@link Throwable}
	 * @since 1.0.0
	 */
	boolean test(final T t, final U u) throws X;

	/**
	 * <p>Returns a composed {@link ThrowableBiPredicate} that represents a short-circuiting logical AND of this
	 * predicate and another.</p>
	 * @param other a {@link ThrowableBiPredicate} that will be logically-ANDed with this predicate
	 * @return a composed {@link ThrowableBiPredicate} that represents the short-circuiting logical AND of this
	 *         predicate and the other predicate
	 * @throws NullPointerException if the other {@link ThrowableBiPredicate} is {@code null}
	 * @since 1.0.0
	 */
	default ThrowableBiPredicate<T, U, X> and(final ThrowableBiPredicate<? super T, ? super U, ? extends X> other) {
		Ensure.notNull("other", other);
		return (t, u) -> test(t, u) && other.test(t, u);
	}

	/**
	 * <p>Returns a {@link ThrowableBiPredicate} that represents the logical negation of this predicate.</p>
	 * @return a {@link ThrowableBiPredicate} that represents the logical negation of this predicate
	 * @since 1.0.0
	 */
	default ThrowableBiPredicate<T, U, X> negate() {
		return (t, u) -> !test(t, u);
	}

	/**
	 * <p>Returns a composed {@link ThrowableBiPredicate} that represents a short-circuiting logical OR of this
	 * predicate and another.</p>
	 * @param other a {@link ThrowableBiPredicate} that will be logically-ORed with this predicate
	 * @return a composed {@link ThrowableBiPredicate} that represents the short-circuiting logical OR of this predicate
	 *         and the other predicate
	 * @throws NullPointerException if the other {@link ThrowableBiPredicate} is {@code null}
	 * @since 1.0.0
	 */
	default ThrowableBiPredicate<T, U, X> or(final ThrowableBiPredicate<? super T, ? super U, ? extends X> other) {
		Ensure.notNull("other", other);
		return (t, u) -> test(t, u) || other.test(t, u);
	}

	/**
	 * <p>Converts the given {@link ThrowableBiPredicate} to a {@link BiPredicate} that may throw an unchecked
	 * {@link Throwable}.</p>
	 * @param throwableBiPredicate the {@link ThrowableBiPredicate} to convert
	 * @param <T> the type of the first argument to the predicate
	 * @param <U> the type of the second argument the predicate
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link BiPredicate}
	 * @throws NullPointerException if the {@link ThrowableBiPredicate} is {@code null}
	 * @since 1.0.0
	 */
	static <T, U, X extends Throwable> BiPredicate<T, U> unchecked(final ThrowableBiPredicate<? super T, ? super U, ? extends X> throwableBiPredicate) {
		Ensure.notNull("throwableBiPredicate", throwableBiPredicate);
		return (t, u) -> {
			try {
				return throwableBiPredicate.test(t, u);
			} catch (final Throwable e) {
				throw Throwables.unchecked(e);
			}
		};
	}

	/**
	 * <p>Converts the given {@link ThrowableBiPredicate} to a {@link BiPredicate} that may throw a sneaky
	 * {@link Throwable}.</p>
	 * @param throwableBiPredicate the {@link ThrowableBiPredicate} to convert
	 * @param <T> the type of the first argument to the predicate
	 * @param <U> the type of the second argument the predicate
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link BiPredicate}
	 * @throws NullPointerException if the {@link ThrowableBiPredicate} is {@code null}
	 * @since 1.7.0
	 */
	static <T, U, X extends Throwable> BiPredicate<T, U> sneaky(final ThrowableBiPredicate<? super T, ? super U, ? extends X> throwableBiPredicate) {
		Ensure.notNull("throwableBiPredicate", throwableBiPredicate);
		return new BiPredicate<>() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			@ExcludeFromJacocoGeneratedReport
			public boolean test(final T t, final U u) {
				try {
					return throwableBiPredicate.test(t, u);
				} catch (final Throwable e) {
					Throwables.sneakyThrow(e);
					throw new AssertionError(e);
				}
			}
		};
	}

	/**
	 * <p>Create a {@link ThrowableBiPredicate} from the given {@link BiPredicate}.</p>
	 * @param biPredicate the {@link BiPredicate} to convert
	 * @param <T> the type of the first argument to the predicate
	 * @param <U> the type of the second argument the predicate
	 * @param <X> the type of the {@link Throwable}
	 * @return the created {@link ThrowableBiPredicate}
	 * @throws NullPointerException if the {@link BiPredicate} is {@code null}
	 * @since 1.0.0
	 */
	static <T, U, X extends Throwable> ThrowableBiPredicate<T, U, X> of(final BiPredicate<? super T, ? super U> biPredicate) {
		Ensure.notNull("biPredicate", biPredicate);
		return biPredicate::test;
	}
}