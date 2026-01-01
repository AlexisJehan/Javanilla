/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
import internal.ExcludeFromJacocoGeneratedReport;

import java.util.function.Consumer;

/**
 * Interface for a {@link Consumer} that may throw a {@link Throwable}.
 *
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is {@link #accept(Object)}.</p>
 * @param <T> the type of the input to the operation
 * @param <X> the type of the {@link Throwable}
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.function.ThrowableConsumer} instead
 * @since 1.0.0
 */
@FunctionalInterface
@Deprecated(since = "1.8.0")
public interface ThrowableConsumer<T, X extends Throwable> {

	/**
	 * Performs this operation on the given argument.
	 * @param t the input argument
	 * @throws X may throw a {@link Throwable}
	 * @since 1.0.0
	 */
	void accept(T t) throws X;

	/**
	 * Returns a composed {@code ThrowableConsumer} that performs, in sequence, this operation followed by the after
	 * operation.
	 * @param after the {@code ThrowableConsumer} operation to perform after this operation
	 * @return a composed {@code ThrowableConsumer} that performs in sequence this operation followed by the after
	 *         operation
	 * @throws NullPointerException if the after {@code ThrowableConsumer} is {@code null}
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
	 * Converts the given {@code ThrowableConsumer} to a {@link Consumer} that may throw an unchecked {@link Throwable}.
	 * @param throwableConsumer the {@code ThrowableConsumer} to convert
	 * @param <T> the type of the input to the operation
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Consumer}
	 * @throws NullPointerException if the {@code ThrowableConsumer} is {@code null}
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
	 * Converts the given {@code ThrowableConsumer} to a {@link Consumer} that may throw a sneaky {@link Throwable}.
	 * @param throwableConsumer the {@code ThrowableConsumer} to convert
	 * @param <T> the type of the input to the operation
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Consumer}
	 * @throws NullPointerException if the {@code ThrowableConsumer} is {@code null}
	 * @since 1.7.0
	 */
	static <T, X extends Throwable> Consumer<T> sneaky(final ThrowableConsumer<? super T, ? extends X> throwableConsumer) {
		Ensure.notNull("throwableConsumer", throwableConsumer);
		return new Consumer<>() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			@ExcludeFromJacocoGeneratedReport
			public void accept(final T t) {
				try {
					throwableConsumer.accept(t);
				} catch (final Throwable e) {
					Throwables.sneakyThrow(e);
				}
			}
		};
	}

	/**
	 * Create a {@code ThrowableConsumer} from the given {@link Consumer}.
	 * @param consumer the {@link Consumer} to convert
	 * @param <T> the type of the input to the operation
	 * @param <X> the type of the {@link Throwable}
	 * @return the created {@code ThrowableConsumer}
	 * @throws NullPointerException if the {@link Consumer} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> ThrowableConsumer<T, X> of(final Consumer<? super T> consumer) {
		Ensure.notNull("consumer", consumer);
		return consumer::accept;
	}
}