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
package com.github.javanilla.util.function.throwable;

import com.github.javanilla.lang.Throwables;

import java.util.function.Predicate;

/**
 * <p>Interface for a {@link Predicate} that may throw a {@link Throwable}.</p>
 * @param <T> the type of the input to the predicate
 * @param <X> the type of the {@code Throwable}
 * @since 1.0
 */
@FunctionalInterface
public interface ThrowablePredicate<T, X extends Throwable> {

	/**
	 * <p>Evaluates this predicate on the given argument.</p>
	 * @param t the input argument
	 * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
	 * @throws X may throw a {@code Throwable}
	 * @since 1.0
	 */
	boolean test(final T t) throws X;

	/**
	 * <p>Returns a composed {@code ThrowablePredicate} that represents a short-circuiting logical AND of this predicate
	 * and another.</p>
	 * @param other a {@code ThrowablePredicate} that will be logically-ANDed with this predicate
	 * @return a composed {@code ThrowablePredicate} that represents the short-circuiting logical AND of this predicate
	 * and the other predicate
	 * @throws NullPointerException if the other {@code ThrowablePredicate} is {@code null}
	 * @since 1.0
	 */
	default ThrowablePredicate<T, X> and(final ThrowablePredicate<? super T, ? extends X> other) {
		if (null == other) {
			throw new NullPointerException("Invalid other (not null expected)");
		}
		return t -> test(t) && other.test(t);
	}

	/**
	 * <p>Returns a {@code ThrowablePredicate} that represents the logical negation of this predicate.</p>
	 * @return a {@code ThrowablePredicate} that represents the logical negation of this predicate
	 * @since 1.0
	 */
	default ThrowablePredicate<T, X> negate() {
		return t -> !test(t);
	}

	/**
	 * <p>Returns a composed {@code ThrowablePredicate} that represents a short-circuiting logical OR of this predicate
	 * and another.</p>
	 * @param other a {@code ThrowablePredicate} that will be logically-ORed with this predicate
	 * @return a composed {@code ThrowablePredicate} that represents the short-circuiting logical OR of this predicate
	 * and the other predicate
	 * @throws NullPointerException if the other {@code ThrowablePredicate} is {@code null}
	 * @since 1.0
	 */
	default ThrowablePredicate<T, X> or(final ThrowablePredicate<? super T, ? extends X> other) {
		if (null == other) {
			throw new NullPointerException("Invalid other (not null expected)");
		}
		return t -> test(t) || other.test(t);
	}

	/**
	 * <p>Converts the given {@code ThrowablePredicate} to a {@code Predicate} that may throw an unchecked
	 * {@code Throwable}.</p>
	 * @param throwablePredicate the given {@code ThrowablePredicate}
	 * @param <T> the type of the input to the predicate
	 * @param <X> the type of the {@code Throwable}
	 * @return the converted {@code Predicate}
	 * @throws NullPointerException if the {@code ThrowablePredicate} is {@code null}
	 * @since 1.0
	 */
	static <T, X extends Throwable> Predicate<T> unchecked(final ThrowablePredicate<? super T, ? extends X> throwablePredicate) {
		if (null == throwablePredicate) {
			throw new NullPointerException("Invalid throwable predicate (not null expected)");
		}
		return t -> {
			try {
				return throwablePredicate.test(t);
			} catch (final Throwable x) {
				throw Throwables.unchecked(x);
			}
		};
	}

	/**
	 * <p>Create a {@code ThrowablePredicate} that throws nothing from the given {@code Predicate}.</p>
	 * @param predicate the given {@code Predicate}
	 * @param <T> the type of the input to the predicate
	 * @param <X> the type of the {@code Throwable}
	 * @return the created {@code ThrowablePredicate}
	 * @throws NullPointerException if the {@code Predicate} is {@code null}
	 * @since 1.0
	 */
	static <T, X extends Throwable> ThrowablePredicate<T, X> of(final Predicate<? super T> predicate) {
		if (null == predicate) {
			throw new NullPointerException("Invalid predicate (not null expected)");
		}
		return predicate::test;
	}
}