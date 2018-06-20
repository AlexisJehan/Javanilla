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

import java.util.function.Consumer;

/**
 * <p>Interface for a {@link Consumer} that may throw a {@link Throwable}.</p>
 * @param <T> the type of the input to the operation
 * @param <X> the type of the {@code Throwable}
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableConsumer<T, X extends Throwable> {

	/**
	 * <p>Performs this operation on the given argument.</p>
	 * @param t the input argument
	 * @throws X may throw a {@code Throwable}
	 * @since 1.0.0
	 */
	void accept(final T t) throws X;

	/**
	 * <p>Returns a composed {@code ThrowableConsumer} that performs, in sequence, this operation followed by the after
	 * operation.</p>
	 * @param after the {@code ThrowableConsumer} operation to perform after this operation
	 * @return a composed {@code ThrowableConsumer} that performs in sequence this operation followed by the after
	 * operation
	 * @throws NullPointerException if the after {@code ThrowableConsumer} is {@code null}
	 * @since 1.0.0
	 */
	default ThrowableConsumer<T, X> andThen(final ThrowableConsumer<? super T, ? extends X> after) {
		if (null == after) {
			throw new NullPointerException("Invalid after (not null expected)");
		}
		return t -> {
			accept(t);
			after.accept(t);
		};
	}

	/**
	 * <p>Converts the given {@code ThrowableConsumer} to a {@code Consumer} that may throw an unchecked
	 * {@code Throwable}.</p>
	 * @param throwableConsumer the given {@code ThrowableConsumer}
	 * @param <T> the type of the input to the operation
	 * @param <X> the type of the {@code Throwable}
	 * @return the converted {@code Consumer}
	 * @throws NullPointerException if the {@code ThrowableConsumer} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> Consumer<T> unchecked(final ThrowableConsumer<? super T, ? extends X> throwableConsumer) {
		if (null == throwableConsumer) {
			throw new NullPointerException("Invalid throwable consumer (not null expected)");
		}
		return t -> {
			try {
				throwableConsumer.accept(t);
			} catch (final Throwable x) {
				throw Throwables.unchecked(x);
			}
		};
	}

	/**
	 * <p>Create a {@code ThrowableConsumer} that throws nothing from the given {@code Consumer}.</p>
	 * @param consumer the given {@code Consumer}
	 * @param <T> the type of the input to the operation
	 * @param <X> the type of the {@code Throwable}
	 * @return the created {@code ThrowableConsumer}
	 * @throws NullPointerException if the {@code Consumer} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> ThrowableConsumer<T, X> of(final Consumer<? super T> consumer) {
		if (null == consumer) {
			throw new NullPointerException("Invalid consumer (not null expected)");
		}
		return consumer::accept;
	}
}