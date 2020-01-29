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

import java.util.function.Consumer;

/**
 * <p>Interface for a {@link Consumer} that may throw a {@link Throwable}.</p>
 * @param <T> the type of the input to the operation
 * @param <X> the type of the {@link Throwable}
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableConsumer<T, X extends Throwable> {

	/**
	 * <p>Performs this operation on the given argument.</p>
	 * @param t the input argument
	 * @throws X may throw a {@link Throwable}
	 * @since 1.0.0
	 */
	void accept(final T t) throws X;

	/**
	 * <p>Returns a composed {@link ThrowableConsumer} that performs, in sequence, this operation followed by the after
	 * operation.</p>
	 * @param after the {@link ThrowableConsumer} operation to perform after this operation
	 * @return a composed {@link ThrowableConsumer} that performs in sequence this operation followed by the after
	 *         operation
	 * @throws NullPointerException if the after {@link ThrowableConsumer} is {@code null}
	 * @since 1.0.0
	 */
	default ThrowableConsumer<T, X> andThen(final ThrowableConsumer<? super T, ? extends X> after) {
		Ensure.notNull("after", after);
		return t -> {
			accept(t);
			after.accept(t);
		};
	}

	/**
	 * <p>Converts the given {@link ThrowableConsumer} to a {@link Consumer} that may throw an unchecked
	 * {@link Throwable}.</p>
	 * @param throwableConsumer the {@link ThrowableConsumer} to convert
	 * @param <T> the type of the input to the operation
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Consumer}
	 * @throws NullPointerException if the {@link ThrowableConsumer} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> Consumer<T> unchecked(final ThrowableConsumer<? super T, ? extends X> throwableConsumer) {
		Ensure.notNull("throwableConsumer", throwableConsumer);
		return t -> {
			try {
				throwableConsumer.accept(t);
			} catch (final Throwable e) {
				throw Throwables.unchecked(e);
			}
		};
	}

	/**
	 * <p>Create a {@link ThrowableConsumer} from the given {@link Consumer}.</p>
	 * @param consumer the {@link Consumer} to convert
	 * @param <T> the type of the input to the operation
	 * @param <X> the type of the {@link Throwable}
	 * @return the created {@link ThrowableConsumer}
	 * @throws NullPointerException if the {@link Consumer} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> ThrowableConsumer<T, X> of(final Consumer<? super T> consumer) {
		Ensure.notNull("consumer", consumer);
		return consumer::accept;
	}
}