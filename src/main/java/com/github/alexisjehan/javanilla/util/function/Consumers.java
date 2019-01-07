/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * <p>An utility class that provides {@link Consumer} tools.</p>
 * @since 1.1.0
 */
public final class Consumers {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.1.0
	 */
	private Consumers() {
		// Not available
	}

	/**
	 * <p>Decorate a {@code Consumer} so that it only accepts a value once. If the {@code Consumer} is consumed more
	 * than once an {@code IllegalStateException} is thrown.</p>
	 * @param consumer the {@code Consumer} to decorate
	 * @param <T> the type of the input to the operation
	 * @return the {@code Consumer} which accepts a value once
	 * @throws NullPointerException if the {@code Consumer} is {@code null}
	 * @since 1.1.0
	 */
	public static <T> Consumer<T> once(final Consumer<? super T> consumer) {
		Ensure.notNull("consumer", consumer);
		return new Consumer<>() {
			private boolean isConsumed = false;

			@Override
			public void accept(final T t) {
				if (isConsumed) {
					throw new IllegalStateException("An input cannot be consumed more than once");
				}
				isConsumed = true;
				consumer.accept(t);
			}
		};
	}

	/**
	 * <p>Decorate a {@code Consumer} so that it only accepts distinct values.</p>
	 * @param consumer the {@code Consumer} to decorate
	 * @param <T> the type of the input to the operation
	 * @return the {@code Consumer} which accepts distinct values
	 * @throws NullPointerException if the {@code Consumer} is {@code null}
	 * @since 1.2.0
	 */
	public static <T> Consumer<T> distinct(final Consumer<? super T> consumer) {
		Ensure.notNull("consumer", consumer);
		return new Consumer<>() {
			private final Set<T> set = new HashSet<>();

			@Override
			public void accept(final T t) {
				if (set.add(t)) {
					consumer.accept(t);
				}
			}
		};
	}
}