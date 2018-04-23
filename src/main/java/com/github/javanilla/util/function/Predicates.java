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
package com.github.javanilla.util.function;

import java.util.function.*;

/**
 * <p>An utility class that provides {@link Predicate}, {@link BiPredicate}, {@link IntPredicate}, {@link LongPredicate}
 * and {@link DoublePredicate} tools.</p>
 * @since 1.0
 */
public final class Predicates {

	/**
	 * <p>An {@code IntPredicate} that always return {@code true}.</p>
	 * @since 1.0
	 */
	public static final IntPredicate INT_ALWAYS_TRUE = i -> true;

	/**
	 * <p>An {@code IntPredicate} that always return {@code false}.</p>
	 * @since 1.0
	 */
	public static final IntPredicate INT_ALWAYS_FALSE = i -> false;

	/**
	 * <p>A {@code LongPredicate} that always return {@code true}.</p>
	 * @since 1.0
	 */
	public static final LongPredicate LONG_ALWAYS_TRUE = l -> true;

	/**
	 * <p>A {@code LongPredicate} that always return {@code false}.</p>
	 * @since 1.0
	 */
	public static final LongPredicate LONG_ALWAYS_FALSE = l -> false;

	/**
	 * <p>A {@code DoublePredicate} that always return {@code true}.</p>
	 * @since 1.0
	 */
	public static final DoublePredicate DOUBLE_ALWAYS_TRUE = d -> true;

	/**
	 * <p>A {@code DoublePredicate} that always return {@code false}.</p>
	 * @since 1.0
	 */
	public static final DoublePredicate DOUBLE_ALWAYS_FALSE = d -> false;

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private Predicates() {
		// Not available
	}

	/**
	 * <p>Create a {@code Predicate} of the given type that always return {@code true}.</p>
	 * @param <T> the type of the input to the predicate
	 * @return the created {@code Predicate}
	 * @since 1.0
	 */
	public static <T> Predicate<T> alwaysTrue() {
		return x -> true;
	}

	/**
	 * <p>Create a {@code Predicate} of the given type that always return {@code false}.</p>
	 * @param <T> the type of the input to the predicate
	 * @return the created {@code Predicate}
	 * @since 1.0
	 */
	public static <T> Predicate<T> alwaysFalse() {
		return x -> false;
	}

	/**
	 * <p>Create a {@code BiPredicate} of given types that always return {@code true}.</p>
	 * @param <T> the type of the first argument to the predicate
	 * @param <U> the type of the second argument the predicate
	 * @return the created {@code BiPredicate}
	 * @since 1.0
	 */
	public static <T, U> BiPredicate<T, U> biAlwaysTrue() {
		return (x, y) -> true;
	}

	/**
	 * <p>Create a {@code BiPredicate} of given types that always return {@code false}.</p>
	 * @param <T> the type of the first argument to the predicate
	 * @param <U> the type of the second argument the predicate
	 * @return the created {@code BiPredicate}
	 * @since 1.0
	 */
	public static <T, U> BiPredicate<T, U> biAlwaysFalse() {
		return (x, y) -> false;
	}
}