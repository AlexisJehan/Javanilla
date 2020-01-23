/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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

import com.github.alexisjehan.javanilla.sql.UncheckedSQLException;
import com.github.alexisjehan.javanilla.util.function.throwable.ThrowableRunnable;
import com.github.alexisjehan.javanilla.util.function.throwable.ThrowableSupplier;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Throwables} unit tests.</p>
 */
final class ThrowablesTest {

	@Test
	void testIsChecked() {
		assertThat(Throwables.isChecked(new Exception())).isTrue();
		assertThat(Throwables.isChecked(new RuntimeException())).isFalse();
		assertThat(Throwables.isChecked(new Error())).isFalse();
	}

	@Test
	void testIsCheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.isChecked(null));
	}

	@Test
	void testIsUnchecked() {
		assertThat(Throwables.isUnchecked(new Exception())).isFalse();
		assertThat(Throwables.isUnchecked(new RuntimeException())).isTrue();
		assertThat(Throwables.isUnchecked(new Error())).isFalse();
	}

	@Test
	void testIsUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.isUnchecked(null));
	}

	@Test
	void testUncheckThrowableRunnable() {
		Throwables.uncheck(() -> {});
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Throwables.uncheck(() -> {
			throw new IOException();
		}));
	}

	@Test
	void testUncheckThrowableRunnableInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.uncheck((ThrowableRunnable<IOException>) null));
	}

	@Test
	void testUncheckThrowableSupplier() {
		Throwables.uncheck(() -> true);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> Throwables.uncheck((ThrowableSupplier<?, IOException>) () -> {
			throw new IOException();
		}));
	}

	@Test
	void testUncheckThrowableSupplierInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.uncheck((ThrowableSupplier<Integer, IOException>) null));
	}

	@Test
	void testUnchecked() {
		assertThat(Throwables.unchecked(new IOException())).isInstanceOf(UncheckedIOException.class);
		assertThat(Throwables.unchecked(new SQLException())).isInstanceOf(UncheckedSQLException.class);
		assertThat(Throwables.unchecked(new InterruptedException())).isInstanceOf(UncheckedInterruptedException.class);
		assertThat(Throwables.unchecked(new CloneNotSupportedException())).isInstanceOf(RuntimeException.class);
		assertThat(Throwables.unchecked(new Exception())).isInstanceOf(RuntimeException.class);
		assertThat(Throwables.unchecked(new RuntimeException())).isInstanceOf(RuntimeException.class);
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.unchecked(null));
	}

	@Test
	void testGetOptionalRootCause() {
		assertThat(new IOException()).satisfies(rootException -> {
			final var exception = new UncheckedIOException((IOException) rootException);
			assertThat(Throwables.getOptionalRootCause(rootException)).isEmpty();
			assertThat(Throwables.getOptionalRootCause(exception)).hasValue(rootException);
			assertThat(Throwables.getOptionalRootCause(new RuntimeException(exception))).hasValue(rootException);
		});

		// Cycle
		assertThat(new IOException()).satisfies(rootException -> {
			@SuppressWarnings("serial")
			final var exception = new RuntimeException(new UncheckedIOException((IOException) rootException)) {
				@Override
				public Throwable getCause() {
					return this;
				}
			};
			assertThat(Throwables.getOptionalRootCause(exception)).isEmpty();
		});
	}

	@Test
	void testGetOptionalRootCauseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.getOptionalRootCause(null));
	}

	@Test
	void testGetCauses() {
		assertThat(new IOException()).satisfies(rootException -> {
			final var exception = new UncheckedIOException((IOException) rootException);
			assertThat(Throwables.getCauses(new RuntimeException(exception))).containsExactly(exception, rootException);
		});

		// Cycle
		assertThat(new IOException()).satisfies(rootException -> {
			@SuppressWarnings("serial")
			final var exception = new RuntimeException(new UncheckedIOException((IOException) rootException)) {
				@Override
				public Throwable getCause() {
					return this;
				}
			};
			assertThat(Throwables.getCauses(exception)).isEmpty();
		});
	}

	@Test
	void testGetCausesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Throwables.getCauses(null));
	}
}