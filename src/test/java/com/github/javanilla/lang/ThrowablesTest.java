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
package com.github.javanilla.lang;

import com.github.javanilla.sql.UncheckedSQLException;
import com.github.javanilla.util.function.throwable.ThrowableRunnable;
import com.github.javanilla.util.function.throwable.ThrowableSupplier;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * <p>{@link Throwables} unit tests.</p>
 */
final class ThrowablesTest {

	@Test
	void testUncheckRunnable() {
		try {
			Throwables.uncheck(() -> {});
		} catch (final UncheckedIOException e) {
			fail(e.getMessage());
		}
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Throwables.uncheck(() -> {
			throw new IOException();
		}));
	}

	@Test
	void testUncheckRunnableNull() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.uncheck((ThrowableRunnable<?>) null));
	}

	@Test
	void testUncheckSupplier() {
		try {
			Throwables.uncheck(() -> true);
		} catch (final UncheckedIOException e) {
			fail(e.getMessage());
		}
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Throwables.uncheck(() -> {
			if (1 == 0) {
				return null;
			}
			throw new IOException();
		}));
	}

	@Test
	void testUncheckSupplierNull() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.uncheck((ThrowableSupplier<Integer, ?>) null));
	}

	@Test
	void testUnchecked() {
		final var ioException = new IOException();
		assertThat(Throwables.unchecked(ioException)).isInstanceOf(UncheckedIOException.class);
		assertThat(Throwables.unchecked(ioException).getCause()).isSameAs(ioException);

		final var sqlException = new SQLException();
		assertThat(Throwables.unchecked(sqlException)).isInstanceOf(UncheckedSQLException.class);
		assertThat(Throwables.unchecked(sqlException).getCause()).isSameAs(sqlException);

		final var interruptedException = new InterruptedException();
		assertThat(Throwables.unchecked(interruptedException)).isInstanceOf(UncheckedInterruptedException.class);
		assertThat(Throwables.unchecked(interruptedException).getCause()).isSameAs(interruptedException);

		final var cloneNotSupportedException = new CloneNotSupportedException();
		assertThat(Throwables.unchecked(cloneNotSupportedException)).isInstanceOf(RuntimeException.class);
		assertThat(Throwables.unchecked(cloneNotSupportedException).getCause()).isSameAs(cloneNotSupportedException);

		final var exception = new Exception();
		assertThat(Throwables.unchecked(exception)).isInstanceOf(RuntimeException.class);
		assertThat(Throwables.unchecked(exception).getCause()).isSameAs(exception);

		final var runtimeException = new RuntimeException(exception);
		assertThat(Throwables.unchecked(runtimeException)).isInstanceOf(RuntimeException.class);
		assertThat(Throwables.unchecked(runtimeException).getCause()).isSameAs(exception);
	}

	@Test
	void testUncheckedNull() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.unchecked(null));
	}

	@Test
	void testGetRootCause() {
		final var rootException = new IOException();
		final var uncheckedIOException = new UncheckedIOException(rootException);
		final var runtimeException = new RuntimeException(uncheckedIOException);
		assertThat(Throwables.getRootCause(runtimeException)).isSameAs(rootException);
	}

	@Test
	void testGetRootCauseLoop() {
		final var rootException = new IOException();
		final var exception = new RuntimeException(new UncheckedIOException(rootException)) {
			private static final long serialVersionUID = 1906364748162718410L;

			@Override
			public Throwable getCause() {
				return this;
			}
		};
		assertThat(Throwables.getRootCause(exception)).isNotSameAs(rootException);
	}

	@Test
	void testGetRootCauseNull() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.getRootCause(null));
	}

	@Test
	void testGetCauses() {
		final var rootException = new IOException();
		final var uncheckedIOException = new UncheckedIOException(rootException);
		final var runtimeException = new RuntimeException(uncheckedIOException);
		assertThat(Throwables.getCauses(runtimeException)).containsExactly(uncheckedIOException, rootException);
	}

	@Test
	void testGetCausesLoop() {
		final var rootException = new IOException();
		final var exception = new RuntimeException(new UncheckedIOException(rootException)) {
			private static final long serialVersionUID = 5619462499484288864L;

			@Override
			public Throwable getCause() {
				return this;
			}
		};
		assertThat(Throwables.getCauses(exception)).isEmpty();
	}

	@Test
	void testGetCausesNull() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.getCauses(null));
	}
}