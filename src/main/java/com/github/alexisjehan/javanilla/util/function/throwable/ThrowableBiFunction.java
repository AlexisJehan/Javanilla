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
package com.github.alexisjehan.javanilla.util.function.throwable;

import com.github.alexisjehan.javanilla.lang.Throwables;

import java.util.function.BiFunction;

/**
 * <p>Interface for a {@link BiFunction} that may throw a {@link Throwable}.</p>
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <X> the type of the {@code Throwable}
 * @since 1.0
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R, X extends Throwable> {

	/**
	 * <p>Applies this function to the given arguments.</p>
	 * @param t the first function argument
	 * @param u the second function argument
	 * @return the function result
	 * @throws X may throw a {@code Throwable}
	 * @since 1.0
	 */
	R apply(final T t, final U u) throws X;

	/**
	 * <p>Returns a composed {@code ThrowableBiFunction} that first applies this function to its input, and then applies
	 * the after function to the result.</p>
	 * @param after the {@code ThrowableBiFunction} to apply after this function is applied
	 * @param <V> the type of output of the after function, and of the composed function
	 * @return a composed {@code ThrowableBiFunction} that first applies this function and then applies the after
	 * function
	 * @throws NullPointerException if the after {@code ThrowableBiFunction} is {@code null}
	 * @since 1.0
	 */
	default <V> ThrowableBiFunction<T, U, V, X> andThen(final ThrowableFunction<? super R, ? extends V, ? extends X> after) {
		if (null == after) {
			throw new NullPointerException("Invalid after (not null expected)");
		}
		return (t, u) -> after.apply(apply(t, u));
	}

	/**
	 * <p>Converts the given {@code ThrowableBiFunction} to a {@code BiFunction} that may throw an unchecked
	 * {@code Throwable}.</p>
	 * @param throwableBiFunction the given {@code ThrowableBiFunction}
	 * @param <T> the type of the first argument to the function
	 * @param <U> the type of the second argument to the function
	 * @param <R> the type of the result of the function
	 * @param <X> the type of the {@code Throwable}
	 * @return the converted {@code BiFunction}
	 * @throws NullPointerException if the {@code ThrowableBiFunction} is {@code null}
	 * @since 1.0
	 */
	static <T, U, R, X extends Throwable> BiFunction<T, U, R> unchecked(final ThrowableBiFunction<? super T, ? super U, ? extends R, ? extends X> throwableBiFunction) {
		if (null == throwableBiFunction) {
			throw new NullPointerException("Invalid throwable bi function (not null expected)");
		}
		return (t, u) -> {
			try {
				return throwableBiFunction.apply(t, u);
			} catch (final Throwable x) {
				throw Throwables.unchecked(x);
			}
		};
	}

	/**
	 * <p>Create a {@code ThrowableBiFunction} that throws nothing from the given {@code BiFunction}.</p>
	 * @param biFunction the given {@code BiFunction}
	 * @param <T> the type of the first argument to the function
	 * @param <U> the type of the second argument to the function
	 * @param <R> the type of the result of the function
	 * @param <X> the type of the {@code Throwable}
	 * @return the created {@code ThrowableBiFunction}
	 * @throws NullPointerException if the {@code BiFunction} is {@code null}
	 * @since 1.0
	 */
	static <T, U, R, X extends Throwable> ThrowableBiFunction<T, U, R, X> of(final BiFunction<? super T, ? super U, ? extends R> biFunction) {
		if (null == biFunction) {
			throw new NullPointerException("Invalid bi function (not null expected)");
		}
		return biFunction::apply;
	}
}