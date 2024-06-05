/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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

import com.github.alexisjehan.javanilla.lang.Throwables;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import internal.ExcludeFromJacocoGeneratedReport;

import java.util.function.BiFunction;

/**
 * <p>Interface for a {@link BiFunction} that may throw a {@link Throwable}.</p>
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is
 * {@link #apply(Object, Object)}.</p>
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <X> the type of the {@link Throwable}
 * @since 1.8.0
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R, X extends Throwable> {

	/**
	 * <p>Applies this function to the given arguments.</p>
	 * @param t the first function argument
	 * @param u the second function argument
	 * @return the function result
	 * @throws X may throw a {@link Throwable}
	 * @since 1.8.0
	 */
	R apply(T t, U u) throws X;

	/**
	 * <p>Returns a composed {@code ThrowableBiFunction} that first applies this function to its input, and then applies
	 * the after function to the result.</p>
	 * @param after the {@link ThrowableFunction} to apply after this function is applied
	 * @param <V> the type of output of the after function, and of the composed function
	 * @return a composed {@code ThrowableBiFunction} that first applies this function and then applies the after
	 *         function
	 * @throws NullPointerException if the after {@link ThrowableFunction} is {@code null}
	 * @since 1.8.0
	 */
	default <V> ThrowableBiFunction<T, U, V, X> andThen(final ThrowableFunction<? super R, ? extends V, ? extends X> after) {
		Ensure.notNull("after", after);
		return (t, u) -> after.apply(apply(t, u));
	}

	/**
	 * <p>Converts the given {@code ThrowableBiFunction} to a {@link BiFunction} that may throw an unchecked
	 * {@link Throwable}.</p>
	 * @param throwableBiFunction the {@code ThrowableBiFunction} to convert
	 * @param <T> the type of the first argument to the function
	 * @param <U> the type of the second argument to the function
	 * @param <R> the type of the result of the function
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link BiFunction}
	 * @throws NullPointerException if the {@code ThrowableBiFunction} is {@code null}
	 * @since 1.8.0
	 */
	static <T, U, R, X extends Throwable> BiFunction<T, U, R> unchecked(final ThrowableBiFunction<? super T, ? super U, ? extends R, ? extends X> throwableBiFunction) {
		Ensure.notNull("throwableBiFunction", throwableBiFunction);
		return (t, u) -> {
			try {
				return throwableBiFunction.apply(t, u);
			} catch (final Throwable e) {
				throw Throwables.unchecked(e);
			}
		};
	}

	/**
	 * <p>Converts the given {@code ThrowableBiFunction} to a {@link BiFunction} that may throw a sneaky
	 * {@link Throwable}.</p>
	 * @param throwableBiFunction the {@code ThrowableBiFunction} to convert
	 * @param <T> the type of the first argument to the function
	 * @param <U> the type of the second argument to the function
	 * @param <R> the type of the result of the function
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link BiFunction}
	 * @throws NullPointerException if the {@code ThrowableBiFunction} is {@code null}
	 * @since 1.8.0
	 */
	static <T, U, R, X extends Throwable> BiFunction<T, U, R> sneaky(final ThrowableBiFunction<? super T, ? super U, ? extends R, ? extends X> throwableBiFunction) {
		Ensure.notNull("throwableBiFunction", throwableBiFunction);
		return new BiFunction<>() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			@ExcludeFromJacocoGeneratedReport
			public R apply(final T t, final U u) {
				try {
					return throwableBiFunction.apply(t, u);
				} catch (final Throwable e) {
					Throwables.sneakyThrow(e);
					throw new AssertionError(e);
				}
			}
		};
	}

	/**
	 * <p>Create a {@code ThrowableBiFunction} from the given {@link BiFunction}.</p>
	 * @param biFunction the {@link BiFunction} to convert
	 * @param <T> the type of the first argument to the function
	 * @param <U> the type of the second argument to the function
	 * @param <R> the type of the result of the function
	 * @param <X> the type of the {@link Throwable}
	 * @return the created {@code ThrowableBiFunction}
	 * @throws NullPointerException if the {@link BiFunction} is {@code null}
	 * @since 1.8.0
	 */
	static <T, U, R, X extends Throwable> ThrowableBiFunction<T, U, R, X> of(final BiFunction<? super T, ? super U, ? extends R> biFunction) {
		Ensure.notNull("biFunction", biFunction);
		return biFunction::apply;
	}
}