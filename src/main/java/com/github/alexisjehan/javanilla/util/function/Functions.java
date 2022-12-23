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
import com.github.alexisjehan.javanilla.misc.tuple.Single;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>An utility class that provides {@link Function} tools.</p>
 * @since 1.3.0
 */
public final class Functions {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.3.0
	 */
	private Functions() {
		// Not available
	}

	/**
	 * <p>Decorate a {@link Function} so that the result of each applied values is cached for next times.</p>
	 * @param function the {@link Function} to decorate
	 * @param <T> the type of the input to the function
	 * @param <R> the type of the result of the function
	 * @return the cached {@link Function}
	 * @throws NullPointerException if the {@link Function} is {@code null}
	 * @since 1.3.0
	 */
	public static <T, R> Function<T, R> cache(final Function<? super T, ? extends R> function) {
		Ensure.notNull("function", function);
		return new Function<>() {

			/**
			 * <p>Map of cached values.</p>
			 * @since 1.3.0
			 */
			private final Map<T, Single<? extends R>> cache = new HashMap<>();

			/**
			 * {@inheritDoc}
			 */
			@Override
			public R apply(final T t) {
				return cache.computeIfAbsent(t, ignored -> Single.of(function.apply(t))).getUnique();
			}
		};
	}
}