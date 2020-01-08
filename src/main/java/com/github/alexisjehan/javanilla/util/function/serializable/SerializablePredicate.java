/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.function.serializable;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <p>Interface for a {@link Predicate} that is {@link Serializable}.</p>
 * @param <T> the type of the input to the predicate
 * @since 1.4.0
 */
@FunctionalInterface
public interface SerializablePredicate<T> extends Predicate<T>, Serializable {

	/**
	 * <p>Returns a composed {@code SerializablePredicate} that represents a short-circuiting logical AND of this
	 * predicate and another.</p>
	 * @param other a {@code Predicate} that will be logically-ANDed with this predicate
	 * @return a composed {@code SerializablePredicate} that represents the short-circuiting logical AND of this
	 *         predicate and the other predicate
	 * @throws NullPointerException if the other {@code Predicate} is {@code null}
	 * @since 1.4.0
	 */
	@Override
	default SerializablePredicate<T> and(final Predicate<? super T> other) {
		Ensure.notNull("other", other);
		return t -> test(t) && other.test(t);
	}

	/**
	 * <p>Returns a {@code SerializablePredicate} that represents the logical negation of this predicate.</p>
	 * @return a {@code SerializablePredicate} that represents the logical negation of this predicate
	 * @since 1.4.0
	 */
	@Override
	default SerializablePredicate<T> negate() {
		return t -> !test(t);
	}

	/**
	 * <p>Returns a composed {@code SerializablePredicate} that represents a short-circuiting logical OR of this
	 * predicate and another.</p>
	 * @param other a {@code Predicate} that will be logically-ORed with this predicate
	 * @return a composed {@code SerializablePredicate} that represents the short-circuiting logical OR of this
	 *         predicate and the other predicate
	 * @throws NullPointerException if the other {@code Predicate} is {@code null}
	 * @since 1.4.0
	 */
	@Override
	default SerializablePredicate<T> or(final Predicate<? super T> other) {
		Ensure.notNull("other", other);
		return t -> test(t) || other.test(t);
	}

	/**
	 * <p>Returns a {@code SerializablePredicate} that tests if two arguments are equal according to
	 * {@link Objects#equals(Object, Object)}.</p>
	 * @param <T> the type of arguments to the predicate
	 * @param targetRef the object reference with which to compare for equality, which may be {@code null}
	 * @return a {@code SerializablePredicate} that tests if two arguments are equal according to
	 * {@link Objects#equals(Object, Object)}
	 * @since 1.4.0
	 */
	static <T> SerializablePredicate<T> isEqual(final Object targetRef) {
		return null == targetRef
				? Objects::isNull
				: targetRef::equals;
	}

	/**
	 * <p>Create a {@code SerializablePredicate} from the given {@code Predicate}.</p>
	 * @param predicate the {@code Predicate} to convert
	 * @param <T> the type of the input to the predicate
	 * @return the created {@code SerializablePredicate}
	 * @throws NullPointerException if the {@code Predicate} is {@code null}
	 * @since 1.4.0
	 */
	static <T> SerializablePredicate<T> of(final Predicate<? super T> predicate) {
		Ensure.notNull("predicate", predicate);
		return predicate::test;
	}
}