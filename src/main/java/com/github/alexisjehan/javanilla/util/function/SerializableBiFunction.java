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
package com.github.alexisjehan.javanilla.util.function;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <p>Interface for a {@link BiFunction} that is {@link Serializable}.</p>
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @since 1.8.0
 */
@FunctionalInterface
public interface SerializableBiFunction<T, U, R> extends BiFunction<T, U, R>, Serializable {

	/**
	 * <p>Returns a composed {@link SerializableBiFunction} that first applies this function to its input, and then
	 * applies the after function to the result.</p>
	 * @param after the {@link Function} to apply after this function is applied
	 * @param <V> the type of output of the after function, and of the composed function
	 * @return a composed {@link SerializableBiFunction} that first applies this function and then applies the after
	 *         function
	 * @throws NullPointerException if the after {@link Function} is {@code null}
	 * @since 1.8.0
	 */
	@Override
	default <V> SerializableBiFunction<T, U, V> andThen(final Function<? super R, ? extends V> after) {
		Ensure.notNull("after", after);
		return (t, u) -> after.apply(apply(t, u));
	}

	/**
	 * <p>Create a {@link SerializableBiFunction} from the given {@link BiFunction}.</p>
	 * @param biFunction the {@link BiFunction} to convert
	 * @param <T> the type of the first argument to the function
	 * @param <U> the type of the second argument to the function
	 * @param <R> the type of the result of the function
	 * @return the created {@link SerializableBiFunction}
	 * @throws NullPointerException if the {@link BiFunction} is {@code null}
	 * @since 1.8.0
	 */
	static <T, U, R> SerializableBiFunction<T, U, R> of(final BiFunction<? super T, ? super U, ? extends R> biFunction) {
		Ensure.notNull("biFunction", biFunction);
		return biFunction::apply;
	}
}