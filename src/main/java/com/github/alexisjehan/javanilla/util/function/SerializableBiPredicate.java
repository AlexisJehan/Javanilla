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
package com.github.alexisjehan.javanilla.util.function;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;
import java.util.function.BiPredicate;

/**
 * Interface for a {@link BiPredicate} that is {@link Serializable}.
 *
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is
 * {@link #test(Object, Object)}.</p>
 * @param <T> the type of the first argument to the predicate
 * @param <U> the type of the second argument the predicate
 * @since 1.8.0
 */
@FunctionalInterface
public interface SerializableBiPredicate<T, U> extends BiPredicate<T, U>, Serializable {

	/**
	 * Returns a composed {@code SerializableBiPredicate} that represents a short-circuiting logical AND of this
	 * predicate and another.
	 * @param other a {@link BiPredicate} that will be logically-ANDed with this predicate
	 * @return a composed {@code SerializableBiPredicate} that represents the short-circuiting logical AND of this
	 *         predicate and the other predicate
	 * @throws NullPointerException if the other {@link BiPredicate} is {@code null}
	 * @since 1.8.0
	 */
	@Override
	default SerializableBiPredicate<T, U> and(final BiPredicate<? super T, ? super U> other) {
		Ensure.notNull("other", other);
		return (t, u) -> test(t, u) && other.test(t, u);
	}

	/**
	 * Returns a {@code SerializableBiPredicate} that represents the logical negation of this predicate.
	 * @return a {@code SerializableBiPredicate} that represents the logical negation of this predicate
	 * @since 1.8.0
	 */
	@Override
	default SerializableBiPredicate<T, U> negate() {
		return (t, u) -> !test(t, u);
	}

	/**
	 * Returns a composed {@code SerializableBiPredicate} that represents a short-circuiting logical OR of this
	 * predicate and another.
	 * @param other a {@link BiPredicate} that will be logically-ORed with this predicate
	 * @return a composed {@code SerializableBiPredicate} that represents the short-circuiting logical OR of this
	 *         predicate and the other predicate
	 * @throws NullPointerException if the other {@link BiPredicate} is {@code null}
	 * @since 1.8.0
	 */
	@Override
	default SerializableBiPredicate<T, U> or(final BiPredicate<? super T, ? super U> other) {
		Ensure.notNull("other", other);
		return (t, u) -> test(t, u) || other.test(t, u);
	}

	/**
	 * Create a {@code SerializableBiPredicate} from the given {@link BiPredicate}.
	 * @param biPredicate the {@link BiPredicate} to convert
	 * @param <T> the type of the first argument to the predicate
	 * @param <U> the type of the second argument the predicate
	 * @return the created {@code SerializableBiPredicate}
	 * @throws NullPointerException if the {@link BiPredicate} is {@code null}
	 * @since 1.8.0
	 */
	static <T, U> SerializableBiPredicate<T, U> of(final BiPredicate<? super T, ? super U> biPredicate) {
		Ensure.notNull("biPredicate", biPredicate);
		return biPredicate::test;
	}
}