/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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

import com.github.alexisjehan.javanilla.lang.Throwables;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import internal.ExcludeFromJacocoGeneratedReport;

import java.util.function.Supplier;

/**
 * <p>Interface for a {@link Supplier} that may throw a {@link Throwable}.</p>
 * @param <T> the type of results supplied by this supplier
 * @param <X> the type of the {@link Throwable}
 * @since 1.8.0
 */
@FunctionalInterface
public interface ThrowableSupplier<T, X extends Throwable> {

	/**
	 * <p>Gets a result.</p>
	 * @return the result supplied
	 * @throws X may throw a {@link Throwable}
	 * @since 1.8.0
	 */
	T get() throws X;

	/**
	 * <p>Converts the given {@code ThrowableSupplier} to a {@link Supplier} that may throw an unchecked
	 * {@link Throwable}.</p>
	 * @param throwableSupplier the {@code ThrowableSupplier} to convert
	 * @param <T> the type of results supplied by this supplier
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Supplier}
	 * @throws NullPointerException if the {@code ThrowableSupplier} is {@code null}
	 * @since 1.8.0
	 */
	static <T, X extends Throwable> Supplier<T> unchecked(final ThrowableSupplier<? extends T, ? extends X> throwableSupplier) {
		Ensure.notNull("throwableSupplier", throwableSupplier);
		return () -> {
			try {
				return throwableSupplier.get();
			} catch (final Throwable e) {
				throw Throwables.unchecked(e);
			}
		};
	}

	/**
	 * <p>Converts the given {@code ThrowableSupplier} to a {@link Supplier} that may throw a sneaky
	 * {@link Throwable}.</p>
	 * @param throwableSupplier the {@code ThrowableSupplier} to convert
	 * @param <T> the type of results supplied by this supplier
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Supplier}
	 * @throws NullPointerException if the {@code ThrowableSupplier} is {@code null}
	 * @since 1.8.0
	 */
	static <T, X extends Throwable> Supplier<T> sneaky(final ThrowableSupplier<? extends T, ? extends X> throwableSupplier) {
		Ensure.notNull("throwableSupplier", throwableSupplier);
		return new Supplier<>() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			@ExcludeFromJacocoGeneratedReport
			public T get() {
				try {
					return throwableSupplier.get();
				} catch (final Throwable e) {
					Throwables.sneakyThrow(e);
					throw new AssertionError(e);
				}
			}
		};
	}

	/**
	 * <p>Create a {@code ThrowableSupplier} from the given {@link Supplier}.</p>
	 * @param supplier the {@link Supplier} to convert
	 * @param <T> the type of results supplied by this supplier
	 * @param <X> the type of the {@link Throwable}
	 * @return the created {@code ThrowableSupplier}
	 * @throws NullPointerException if the {@link Supplier} is {@code null}
	 * @since 1.8.0
	 */
	static <T, X extends Throwable> ThrowableSupplier<T, X> of(final Supplier<? extends T> supplier) {
		Ensure.notNull("supplier", supplier);
		return supplier::get;
	}
}