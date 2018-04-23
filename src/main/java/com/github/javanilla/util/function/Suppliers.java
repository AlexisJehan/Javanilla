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
package com.github.javanilla.util.function;

import java.util.function.Supplier;

/**
 * <p>An utility class that provides {@link Supplier} tools.</p>
 * @since 1.0
 */
public final class Suppliers {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private Suppliers() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code Supplier} which caches the first value supplied from the given {@code Supplier}.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param <T> the type of results supplied by this supplier
	 * @return the caching {@code Supplier}
	 * @throws NullPointerException if the given {@code Supplier} is {@code null}
	 * @since 1.0
	 */
	public static <T> Supplier<T> cached(final Supplier<? extends T> supplier) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		return new Supplier<>() {
			private Supplier<? extends T> localSupplier = supplier;
			private T value;

			@Override
			public T get() {
				if (null == localSupplier) {
					return value;
				}
				value = localSupplier.get();
				localSupplier = null;
				return value;
			}
		};
	}

	/**
	 * <p>Wrap a {@code Supplier} which only returns a value once from the given {@code Supplier}.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param <T> the type of results supplied by this supplier
	 * @return the {@code Supplier} which returns only one value
	 * @throws NullPointerException if the given {@code Supplier} is {@code null}
	 * @since 1.0
	 */
	public static <T> Supplier<T> once(final Supplier<? extends T> supplier) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		return new Supplier<>() {
			private Supplier<? extends T> localSupplier = supplier;

			@Override
			public T get() {
				if (null == localSupplier) {
					throw new IllegalStateException("A result cannot be obtained more than once");
				}
				final var value = localSupplier.get();
				localSupplier = null;
				return value;
			}
		};
	}
}