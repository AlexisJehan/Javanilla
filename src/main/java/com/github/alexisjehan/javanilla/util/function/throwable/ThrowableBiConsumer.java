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
package com.github.alexisjehan.javanilla.util.function.throwable;

import com.github.alexisjehan.javanilla.lang.Throwables;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.function.BiConsumer;

/**
 * <p>Interface for a {@link BiConsumer} that may throw a {@link Throwable}.</p>
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <X> the type of the {@code Throwable}
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableBiConsumer<T, U, X extends Throwable> {

	/**
	 * <p>Performs this operation on the given arguments.</p>
	 * @param t the first input argument
	 * @param u the second input argument
	 * @throws X may throw a {@code Throwable}
	 * @since 1.0.0
	 */
	void accept(final T t, final U u) throws X;

	/**
	 * <p>Returns a composed {@code ThrowableBiConsumer} that performs, in sequence, this operation followed by the
	 * after operation.</p>
	 * @param after the {@code ThrowableBiConsumer} operation to perform after this operation
	 * @return a composed {@code ThrowableBiConsumer} that performs in sequence this operation followed by the after
	 *         operation
	 * @throws NullPointerException if the after {@code ThrowableBiConsumer} is {@code null}
	 * @since 1.0.0
	 */
	default ThrowableBiConsumer<T, U, X> andThen(final ThrowableBiConsumer<? super T, ? super U, ? extends X> after) {
		Ensure.notNull("after", after);
		return (t, u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}

	/**
	 * <p>Converts the given {@code ThrowableBiConsumer} to a {@code BiConsumer} that may throw an unchecked
	 * {@code Throwable}.</p>
	 * @param throwableBiConsumer the {@code ThrowableBiConsumer} to convert
	 * @param <T> the type of the first argument to the operation
	 * @param <U> the type of the second argument to the operation
	 * @param <X> the type of the {@code Throwable}
	 * @return the converted {@code BiConsumer}
	 * @throws NullPointerException if the {@code ThrowableBiConsumer} is {@code null}
	 * @since 1.0.0
	 */
	static <T, U, X extends Throwable> BiConsumer<T, U> unchecked(final ThrowableBiConsumer<? super T, ? super U, ? extends X> throwableBiConsumer) {
		Ensure.notNull("throwableBiConsumer", throwableBiConsumer);
		return (t, u) -> {
			try {
				throwableBiConsumer.accept(t, u);
			} catch (final Throwable e) {
				throw Throwables.unchecked(e);
			}
		};
	}

	/**
	 * <p>Create a {@code ThrowableBiConsumer} from the given {@code BiConsumer}.</p>
	 * @param biConsumer the {@code BiConsumer} to convert
	 * @param <T> the type of the first argument to the operation
	 * @param <U> the type of the second argument to the operation
	 * @param <X> the type of the {@code Throwable}
	 * @return the created {@code ThrowableBiConsumer}
	 * @throws NullPointerException if the {@code BiConsumer} is {@code null}
	 * @since 1.0.0
	 */
	static <T, U, X extends Throwable> ThrowableBiConsumer<T, U, X> of(final BiConsumer<? super T, ? super U> biConsumer) {
		Ensure.notNull("biConsumer", biConsumer);
		return biConsumer::accept;
	}
}