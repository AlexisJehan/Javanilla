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

import java.util.function.Supplier;

/**
 * <p>Interface for a {@link Supplier} that may throw a {@link Throwable}.</p>
 * @param <T> the type of results supplied by this supplier
 * @param <X> the type of the {@code Throwable}
 * @since 1.0.0
 */
@FunctionalInterface
public interface ThrowableSupplier<T, X extends Throwable> {

	/**
	 * <p>Gets a result.</p>
	 * @return the result supplied
	 * @throws X may throw a {@code Throwable}
	 * @since 1.0.0
	 */
	T get() throws X;

	/**
	 * <p>Converts the given {@code ThrowableSupplier} to a {@code Supplier} that may throw an unchecked
	 * {@code Throwable}.</p>
	 * @param throwableSupplier the given {@code ThrowableSupplier}
	 * @param <T> the type of results supplied by this supplier
	 * @param <X> the type of the {@code Throwable}
	 * @return the converted {@code Supplier}
	 * @throws NullPointerException if the {@code ThrowableSupplier} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> Supplier<T> unchecked(final ThrowableSupplier<? extends T, ? extends X> throwableSupplier) {
		if (null == throwableSupplier) {
			throw new NullPointerException("Invalid throwable supplier (not null expected)");
		}
		return () -> {
			try {
				return throwableSupplier.get();
			} catch (final Throwable x) {
				throw Throwables.unchecked(x);
			}
		};
	}

	/**
	 * <p>Create a {@code ThrowableSupplier} that throws nothing from the given {@code Supplier}.</p>
	 * @param supplier the given {@code Supplier}
	 * @param <T> the type of results supplied by this supplier
	 * @param <X> the type of the {@code Throwable}
	 * @return the created {@code ThrowableSupplier}
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.0.0
	 */
	static <T, X extends Throwable> ThrowableSupplier<T, X> of(final Supplier<? extends T> supplier) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		return supplier::get;
	}
}