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
package com.github.alexisjehan.javanilla.lang;

import com.github.alexisjehan.javanilla.sql.UncheckedSQLException;
import com.github.alexisjehan.javanilla.util.function.throwable.ThrowableRunnable;
import com.github.alexisjehan.javanilla.util.function.throwable.ThrowableSupplier;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p>An utility class that provides {@link Throwable}, {@link Exception} and {@link RuntimeException} tools.</p>
 * @since 1.0.0
 */
public final class Throwables {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Throwables() {
		// Not available
	}

	/**
	 * <p>Tell if a {@code Throwable} is a checked {@code Exception}.</p>
	 * @param throwable the {@code Throwable} to test
	 * @return {@code true} if the {@code Throwable} is a checked {@code Exception}
	 * @throws NullPointerException if the {@code Throwable} is {@code null}
	 * @since 1.1.0
	 */
	public static boolean isChecked(final Throwable throwable) {
		if (null == throwable) {
			throw new NullPointerException("Invalid Throwable (not null expected)");
		}
		return throwable instanceof Exception && !isUnchecked(throwable);
	}

	/**
	 * <p>Tell if a {@code Throwable} is an unchecked {@code Exception}.</p>
	 * @param throwable the {@code Throwable} to test
	 * @return {@code true} if the {@code Throwable} is an unchecked {@code Exception}
	 * @throws NullPointerException if the {@code Throwable} is {@code null}
	 * @since 1.1.0
	 */
	public static boolean isUnchecked(final Throwable throwable) {
		if (null == throwable) {
			throw new NullPointerException("Invalid Throwable (not null expected)");
		}
		return throwable instanceof RuntimeException;
	}

	/**
	 * <p>Execute the given {@code ThrowableRunnable} converting any thrown {@code Throwable} to an unchecked
	 * {@code Exception}.</p>
	 * @param throwableRunnable the {@code ThrowableRunnable} to execute
	 * @throws NullPointerException if the {@code ThrowableRunnable} is {@code null}
	 * @since 1.0.0
	 */
	public static void uncheck(final ThrowableRunnable<?> throwableRunnable) {
		if (null == throwableRunnable) {
			throw new NullPointerException("Invalid ThrowableRunnable (not null expected)");
		}
		ThrowableRunnable.unchecked(throwableRunnable).run();
	}

	/**
	 * <p>Return a result from the given {@code ThrowableSupplier} converting any thrown {@code Throwable}
	 * to an unchecked {@code Exception}.</p>
	 * @param throwableSupplier the {@code ThrowableSupplier} to get the result from
	 * @param <T> the type of results supplied by this supplier
	 * @return the supplied result
	 * @throws NullPointerException if the {@code ThrowableRunnable} is {@code null}
	 * @since 1.0.0
	 */
	public static <T> T uncheck(final ThrowableSupplier<T, ?> throwableSupplier) {
		if (null == throwableSupplier) {
			throw new NullPointerException("Invalid ThrowableSupplier (not null expected)");
		}
		return ThrowableSupplier.unchecked(throwableSupplier).get();
	}

	/**
	 * <p>Wrap and return a {@code Throwable} as an unchecked {@code Exception} if it was not already.</p>
	 * @param throwable the {@code Throwable} to wrap
	 * @return an unchecked {@code Exception}
	 * @throws NullPointerException if the {@code Throwable} is {@code null}
	 * @since 1.0.0
	 */
	public static RuntimeException unchecked(final Throwable throwable) {
		if (null == throwable) {
			throw new NullPointerException("Invalid Throwable (not null expected)");
		}
		if (throwable instanceof RuntimeException) {
			return (RuntimeException) throwable;
		} else if (throwable instanceof IOException) {
			return new UncheckedIOException((IOException) throwable);
		} else if (throwable instanceof SQLException) {
			return new UncheckedSQLException((SQLException) throwable);
		} else if (throwable instanceof InterruptedException) {
			return new UncheckedInterruptedException((InterruptedException) throwable);
		}
		return new RuntimeException(throwable);
	}

	/**
	 * <p>Get the root cause of the given {@code Throwable} by calling {@link Throwable#getCause()} recursively.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if {@code Throwable} causes are making a cycle.</p>
	 * @param throwable the {@code Throwable} to get the root cause from
	 * @return an {@code Optional} {@code Throwable} root cause
	 * @throws NullPointerException if the {@code Throwable} is {@code null}
	 * @since 1.0.0
	 */
	public static Optional<Throwable> getRootCause(final Throwable throwable) {
		if (null == throwable) {
			throw new NullPointerException("Invalid Throwable (not null expected)");
		}
		var cause = throwable;
		while (cause != cause.getCause() && null != cause.getCause()) {
			cause = cause.getCause();
		}
		return cause != throwable ? Optional.of(cause) : Optional.empty();
	}

	/**
	 * <p>Get the {@code List} of causes of the given {@code Throwable} by calling {@link Throwable#getCause()}
	 * recursively.</p>
	 * <p><b>Note</b>: The {@code List} is ordered so that the root cause is at the end.</p>
	 * <p><b>Warning</b>: Can produce an infinite loop if {@code Throwable} causes are making a cycle.</p>
	 * @param throwable the {@code Throwable} to get causes from
	 * @return an unmodifiable {@code List} of {@code Throwable} causes
	 * @throws NullPointerException if the {@code Throwable} is {@code null}
	 * @since 1.0.0
	 */
	public static List<Throwable> getCauses(final Throwable throwable) {
		if (null == throwable) {
			throw new NullPointerException("Invalid Throwable (not null expected)");
		}
		final var causes = new ArrayList<Throwable>();
		var cause = throwable;
		while (cause != cause.getCause() && null != cause.getCause()) {
			cause = cause.getCause();
			causes.add(cause);
		}
		return Collections.unmodifiableList(causes);
	}
}