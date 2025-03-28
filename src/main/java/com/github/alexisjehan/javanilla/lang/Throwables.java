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
package com.github.alexisjehan.javanilla.lang;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.util.function.throwable.ThrowableRunnable;
import com.github.alexisjehan.javanilla.util.function.throwable.ThrowableSupplier;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A utility class that provides {@link Throwable}, {@link Exception} and {@link RuntimeException} tools.
 * @since 1.0.0
 */
public final class Throwables {

	/**
	 * Constructor.
	 * @since 1.0.0
	 */
	private Throwables() {}

	/**
	 * Wrap and return a {@link Throwable} as an unchecked {@link Exception} if it was not already.
	 * @param throwable the {@link Throwable} to wrap
	 * @return an unchecked {@link Exception}
	 * @throws NullPointerException if the {@link Throwable} is {@code null}
	 * @since 1.0.0
	 */
	public static RuntimeException unchecked(final Throwable throwable) {
		Ensure.notNull("throwable", throwable);
		if (throwable instanceof RuntimeException) {
			return (RuntimeException) throwable;
		} else if (throwable instanceof IOException) {
			return new UncheckedIOException((IOException) throwable);
		}
		return new RuntimeException(throwable);
	}

	/**
	 * Execute the given {@link ThrowableRunnable} converting any thrown {@link Throwable} to an unchecked
	 * {@link Exception}.
	 * @param throwableRunnable the {@link ThrowableRunnable} to execute
	 * @throws NullPointerException if the {@link ThrowableRunnable} is {@code null}
	 * @deprecated since 1.7.0, should not be used anymore
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.7.0")
	public static void uncheck(final ThrowableRunnable<?> throwableRunnable) {
		Ensure.notNull("throwableRunnable", throwableRunnable);
		ThrowableRunnable.unchecked(throwableRunnable).run();
	}

	/**
	 * Return a result from the given {@link ThrowableSupplier} converting any thrown {@link Throwable} to an unchecked
	 * {@link Exception}.
	 * @param throwableSupplier the {@link ThrowableSupplier} to get the result from
	 * @param <T> the type of results supplied by this supplier
	 * @return the supplied result
	 * @throws NullPointerException if the {@link ThrowableRunnable} is {@code null}
	 * @deprecated since 1.7.0, should not be used anymore
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.7.0")
	public static <T> T uncheck(final ThrowableSupplier<T, ?> throwableSupplier) {
		Ensure.notNull("throwableSupplier", throwableSupplier);
		return ThrowableSupplier.unchecked(throwableSupplier).get();
	}

	/**
	 * Sneaky throw the given {@link Throwable}.
	 * @param throwable the {@link Throwable} to sneaky throw
	 * @param <E> the type of the {@link Throwable}
	 * @throws E the given {@link Throwable}
	 * @throws NullPointerException if the {@link Throwable} is {@code null}
	 * @since 1.7.0
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Throwable> void sneakyThrow(final Throwable throwable) throws E {
		Ensure.notNull("throwable", throwable);
		throw (E) throwable;
	}

	/**
	 * Optionally get the root cause of the given {@link Throwable} by calling {@link Throwable#getCause()} recursively.
	 *
	 * <p><b>Warning</b>: Can produce an infinite loop if {@link Throwable} causes are making a cycle.</p>
	 * @param throwable the {@link Throwable} to get the root cause from
	 * @return an {@link Optional} {@link Throwable} root cause
	 * @throws NullPointerException if the {@link Throwable} is {@code null}
	 * @since 1.0.0
	 */
	public static Optional<Throwable> getOptionalRootCause(final Throwable throwable) {
		Ensure.notNull("throwable", throwable);
		var cause = throwable;
		while (cause != cause.getCause() && null != cause.getCause()) {
			cause = cause.getCause();
		}
		return cause != throwable ? Optional.of(cause) : Optional.empty();
	}

	/**
	 * Get the {@link List} of causes of the given {@link Throwable} by calling {@link Throwable#getCause()}
	 * recursively.
	 *
	 * <p><b>Note</b>: The {@link List} is ordered so that the root cause is at the end.</p>
	 *
	 * <p><b>Warning</b>: Can produce an infinite loop if {@link Throwable} causes are making a cycle.</p>
	 * @param throwable the {@link Throwable} to get causes from
	 * @return a {@link List} of {@link Throwable} causes
	 * @throws NullPointerException if the {@link Throwable} is {@code null}
	 * @since 1.0.0
	 */
	public static List<Throwable> getCauses(final Throwable throwable) {
		Ensure.notNull("throwable", throwable);
		final var causes = new ArrayList<Throwable>();
		var cause = throwable;
		while (cause != cause.getCause() && null != cause.getCause()) {
			cause = cause.getCause();
			causes.add(cause);
		}
		return causes;
	}

	/**
	 * Tell if a {@link Throwable} is a checked {@link Exception}.
	 * @param throwable the {@link Throwable} to test
	 * @return {@code true} if the {@link Throwable} is a checked {@link Exception}
	 * @throws NullPointerException if the {@link Throwable} is {@code null}
	 * @deprecated since 1.6.0, use {@link #isCheckedException(Throwable)} instead
	 * @since 1.1.0
	 */
	@Deprecated(since = "1.6.0")
	public static boolean isChecked(final Throwable throwable) {
		return isCheckedException(throwable);
	}

	/**
	 * Tell if a {@link Throwable} is a checked {@link Exception}.
	 * @param throwable the {@link Throwable} to test
	 * @return {@code true} if the {@link Throwable} is a checked {@link Exception}
	 * @throws NullPointerException if the {@link Throwable} is {@code null}
	 * @since 1.6.0
	 */
	public static boolean isCheckedException(final Throwable throwable) {
		Ensure.notNull("throwable", throwable);
		return throwable instanceof Exception && !isUncheckedException(throwable);
	}

	/**
	 * Tell if a {@link Throwable} is an unchecked {@link Exception}.
	 * @param throwable the {@link Throwable} to test
	 * @return {@code true} if the {@link Throwable} is an unchecked {@link Exception}
	 * @throws NullPointerException if the {@link Throwable} is {@code null}
	 * @deprecated since 1.6.0, use {@link #isUncheckedException(Throwable)} instead
	 * @since 1.1.0
	 */
	@Deprecated(since = "1.6.0")
	public static boolean isUnchecked(final Throwable throwable) {
		return isUncheckedException(throwable);
	}

	/**
	 * Tell if a {@link Throwable} is an unchecked {@link Exception}.
	 * @param throwable the {@link Throwable} to test
	 * @return {@code true} if the {@link Throwable} is an unchecked {@link Exception}
	 * @throws NullPointerException if the {@link Throwable} is {@code null}
	 * @since 1.6.0
	 */
	public static boolean isUncheckedException(final Throwable throwable) {
		Ensure.notNull("throwable", throwable);
		return throwable instanceof RuntimeException;
	}
}