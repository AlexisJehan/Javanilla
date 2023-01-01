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
package com.github.alexisjehan.javanilla.util.function.throwable;

import com.github.alexisjehan.javanilla.lang.Throwables;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.util.function.Procedure;
import internal.ExcludeFromJacocoGeneratedReport;

/**
 * <p>Interface for a {@link Procedure} that may throw a {@link Throwable}.</p>
 * @param <X> the type of the {@link Throwable}
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.function.ThrowableProcedure} instead
 * @since 1.7.0
 */
@FunctionalInterface
@Deprecated(since = "1.8.0")
public interface ThrowableProcedure<X extends Throwable> {

	/**
	 * <p>Performs this operation.</p>
	 * @throws X may throw a {@link Throwable}
	 * @since 1.7.0
	 */
	void execute() throws X;

	/**
	 * <p>Converts the given {@link ThrowableProcedure} to a {@link Procedure} that may throw an unchecked
	 * {@link Throwable}.</p>
	 * @param throwableProcedure the {@link ThrowableProcedure} to convert
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Procedure}
	 * @throws NullPointerException if the {@link ThrowableProcedure} is {@code null}
	 * @since 1.7.0
	 */
	static <X extends Throwable> Procedure unchecked(final ThrowableProcedure<? extends X> throwableProcedure) {
		Ensure.notNull("throwableProcedure", throwableProcedure);
		return () -> {
			try {
				throwableProcedure.execute();
			} catch (final Throwable e) {
				throw Throwables.unchecked(e);
			}
		};
	}

	/**
	 * <p>Converts the given {@link ThrowableProcedure} to a {@link Procedure} that may throw a sneaky
	 * {@link Throwable}.</p>
	 * @param throwableProcedure the {@link ThrowableProcedure} to convert
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Procedure}
	 * @throws NullPointerException if the {@link ThrowableProcedure} is {@code null}
	 * @since 1.7.0
	 */
	static <X extends Throwable> Procedure sneaky(final ThrowableProcedure<? extends X> throwableProcedure) {
		Ensure.notNull("throwableProcedure", throwableProcedure);
		return new Procedure() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			@ExcludeFromJacocoGeneratedReport
			public void execute() {
				try {
					throwableProcedure.execute();
				} catch (final Throwable e) {
					Throwables.sneakyThrow(e);
				}
			}
		};
	}

	/**
	 * <p>Create a {@link ThrowableProcedure} from the given {@link Procedure}.</p>
	 * @param procedure the {@link Procedure} to convert
	 * @param <X> the type of the {@link Throwable}
	 * @return the created {@link ThrowableProcedure}
	 * @throws NullPointerException if the {@link Procedure} is {@code null}
	 * @since 1.7.0
	 */
	static <X extends Throwable> ThrowableProcedure<X> of(final Procedure procedure) {
		Ensure.notNull("procedure", procedure);
		return procedure::execute;
	}
}