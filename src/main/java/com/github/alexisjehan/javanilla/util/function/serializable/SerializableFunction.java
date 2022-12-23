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
package com.github.alexisjehan.javanilla.util.function.serializable;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;
import java.util.function.Function;

/**
 * <p>Interface for a {@link Function} that is {@link Serializable}.</p>
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.function.SerializableFunction} instead
 * @since 1.4.0
 */
@FunctionalInterface
@Deprecated(since = "1.8.0")
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {

	/**
	 * <p>Returns a composed {@link SerializableFunction} that first applies the before function to its input, and then
	 * applies this function to the result.</p>
	 * @param before the {@link Function} to apply before this function is applied
	 * @param <V> the type of input to the before function, and to the composed function
	 * @return a composed {@link SerializableFunction} that first applies the before function and then applies this
	 *         function
	 * @throws NullPointerException if the before {@link Function} is {@code null}
	 * @since 1.4.0
	 */
	@Override
	default <V> SerializableFunction<V, R> compose(final Function<? super V, ? extends T> before) {
		Ensure.notNull("before", before);
		return v -> apply(before.apply(v));
	}

	/**
	 * <p>Returns a composed {@link SerializableFunction} that first applies this function to its input, and then
	 * applies the after function to the result.</p>
	 * @param after the {@link Function} to apply after this function is applied
	 * @param <V> the type of output of the after function, and of the composed function
	 * @return a composed {@link SerializableFunction} that first applies this function and then applies the after
	 *         function
	 * @throws NullPointerException if the after {@link Function} is {@code null}
	 * @since 1.4.0
	 */
	@Override
	default <V> SerializableFunction<T, V> andThen(final Function<? super R, ? extends V> after) {
		Ensure.notNull("after", after);
		return t -> after.apply(apply(t));
	}

	/**
	 * <p>Returns a {@link SerializableFunction} that always returns its input argument.</p>
	 * @param <T> the type of the input and output objects to the function
	 * @return a {@link SerializableFunction} that always returns its input argument
	 * @since 1.4.0
	 */
	static <T> SerializableFunction<T, T> identity() {
		return t -> t;
	}

	/**
	 * <p>Create a {@link SerializableFunction} from the given {@link Function}.</p>
	 * @param function the {@link Function} to convert
	 * @param <T> the type of the input to the function
	 * @param <R> the type of the result of the function
	 * @return the created {@link SerializableFunction}
	 * @throws NullPointerException if the {@link Function} is {@code null}
	 * @since 1.4.0
	 */
	static <T, R> SerializableFunction<T, R> of(final Function<? super T, ? extends R> function) {
		Ensure.notNull("function", function);
		return function::apply;
	}
}