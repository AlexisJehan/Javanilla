/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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

/**
 * Interface for a {@link Runnable} that may throw a {@link Throwable}.
 *
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is {@link #run()}.</p>
 * @param <X> the type of the {@link Throwable}
 * @deprecated since 1.7.0, use {@link ThrowableProcedure} instead
 * @since 1.0.0
 */
@FunctionalInterface
@Deprecated(since = "1.7.0")
public interface ThrowableRunnable<X extends Throwable> {

	/**
	 * Take any action whatsoever.
	 * @throws X may throw a {@link Throwable}
	 * @since 1.0.0
	 */
	void run() throws X;

	/**
	 * Converts the given {@code ThrowableRunnable} to a {@link Runnable} that may throw an unchecked {@link Throwable}.
	 * @param throwableRunnable the {@code ThrowableRunnable} to convert
	 * @param <X> the type of the {@link Throwable}
	 * @return the converted {@link Runnable}
	 * @throws NullPointerException if the {@code ThrowableRunnable} is {@code null}
	 * @since 1.0.0
	 */
	static <X extends Throwable> Runnable unchecked(final ThrowableRunnable<? extends X> throwableRunnable) {
		Ensure.notNull("throwableRunnable", throwableRunnable);
		return () -> {
			try {
				throwableRunnable.run();
			} catch (final Throwable e) {
				throw Throwables.unchecked(e);
			}
		};
	}

	/**
	 * Create a {@code ThrowableRunnable} from the given {@link Runnable}.
	 * @param runnable the {@link Runnable} to convert
	 * @param <X> the type of the {@link Throwable}
	 * @return the created {@code ThrowableRunnable}
	 * @throws NullPointerException if the {@link Runnable} is {@code null}
	 * @since 1.0.0
	 */
	static <X extends Throwable> ThrowableRunnable<X> of(final Runnable runnable) {
		Ensure.notNull("runnable", runnable);
		return runnable::run;
	}
}