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
package com.github.javanilla.util.function.throwable;

import com.github.javanilla.lang.Throwables;

/**
 * <p>Interface for a {@link Runnable} that may throw a {@link Throwable}.</p>
 * @param <X> the type of the {@code Throwable}
 * @since 1.0
 */
@FunctionalInterface
public interface ThrowableRunnable<X extends Throwable> {

	/**
	 * <p>Take any action whatsoever.</p>
	 * @throws X may throw a {@code Throwable}
	 * @since 1.0
	 */
	void run() throws X;

	/**
	 * <p>Converts the given {@code ThrowableRunnable} to a {@code Runnable} that may throw an unchecked
	 * {@code Throwable}.</p>
	 * @param throwableRunnable the given {@code ThrowableRunnable}
	 * @param <X> the type of the {@code Throwable}
	 * @return the converted {@code Runnable}
	 * @throws NullPointerException if the {@code ThrowableRunnable} is {@code null}
	 * @since 1.0
	 */
	static <X extends Throwable> Runnable unchecked(final ThrowableRunnable<? extends X> throwableRunnable) {
		if (null == throwableRunnable) {
			throw new NullPointerException("Invalid throwable runnable (not null expected)");
		}
		return () -> {
			try {
				throwableRunnable.run();
			} catch (final Throwable x) {
				throw Throwables.unchecked(x);
			}
		};
	}

	/**
	 * <p>Create a {@code ThrowableRunnable} that throws nothing from the given {@code Runnable}.</p>
	 * @param runnable the given {@code Runnable}
	 * @param <X> the type of the {@code Throwable}
	 * @return the created {@code ThrowableRunnable}
	 * @throws NullPointerException if the {@code Runnable} is {@code null}
	 * @since 1.0
	 */
	static <X extends Throwable> ThrowableRunnable<X> of(final Runnable runnable) {
		if (null == runnable) {
			throw new NullPointerException("Invalid runnable (not null expected)");
		}
		return runnable::run;
	}
}