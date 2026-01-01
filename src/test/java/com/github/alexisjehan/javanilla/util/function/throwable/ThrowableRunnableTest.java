/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.atomic.LongAdder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@Deprecated
final class ThrowableRunnableTest {

	static final class FooRunnable implements Runnable {

		private final LongAdder adder = new LongAdder();

		@Override
		public void run() {
			adder.increment();
		}

		int getValue() {
			return adder.intValue();
		}
	}

	static final class FooThrowableRunnable implements ThrowableRunnable<IOException> {

		private final LongAdder adder = new LongAdder();

		@Override
		public void run() {
			adder.increment();
		}

		int getValue() {
			return adder.intValue();
		}
	}

	@Test
	void testRun() {
		final var throwableRunnable = new FooThrowableRunnable();
		final var exceptionThrowableRunnable = (ThrowableRunnable<IOException>) () -> {
			throw new IOException();
		};
		throwableRunnable.run();
		throwableRunnable.run();
		assertThat(throwableRunnable.getValue()).isEqualTo(2);
		assertThatIOException().isThrownBy(exceptionThrowableRunnable::run);
	}

	@Test
	void testUnchecked() {
		final var throwableRunnable = new FooThrowableRunnable();
		final var runnable = ThrowableRunnable.unchecked(throwableRunnable);
		final var exceptionRunnable = ThrowableRunnable.unchecked(() -> {
			throw new IOException();
		});
		runnable.run();
		runnable.run();
		assertThat(throwableRunnable.getValue()).isEqualTo(2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(exceptionRunnable::run);
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableRunnable.unchecked(null));
	}

	@Test
	void testOf() throws Throwable {
		final var runnable = new FooRunnable();
		final var throwableRunnable = ThrowableRunnable.of(runnable);
		final var exceptionThrowableRunnable = ThrowableRunnable.of(() -> {
			throw new UncheckedIOException(new IOException());
		});
		throwableRunnable.run();
		throwableRunnable.run();
		assertThat(runnable.getValue()).isEqualTo(2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(exceptionThrowableRunnable::run);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableRunnable.of(null));
	}
}