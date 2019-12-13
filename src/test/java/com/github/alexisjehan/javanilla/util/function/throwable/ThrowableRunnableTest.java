/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ThrowableRunnable} unit tests.</p>
 */
final class ThrowableRunnableTest {

	private static final class FooRunnable implements Runnable {

		private int value;

		@Override
		public void run() {
			++value;
		}
	}

	private static final class FooThrowableRunnable implements ThrowableRunnable<IOException> {

		private int value;

		@Override
		public void run() {
			++value;
		}
	}

	@Test
	void testRun() {
		final var throwableRunnable1 = new FooThrowableRunnable();
		final var throwableRunnable2 = (ThrowableRunnable<IOException>) () -> {
			throw new IOException();
		};
		throwableRunnable1.run();
		throwableRunnable1.run();
		assertThat(throwableRunnable1.value).isEqualTo(2);
		assertThatIOException().isThrownBy(throwableRunnable2::run);
	}

	@Test
	void testUnchecked() {
		final var throwableRunnable = new FooThrowableRunnable();
		final var runnable1 = ThrowableRunnable.unchecked(throwableRunnable);
		final var runnable2 = ThrowableRunnable.unchecked(() -> {
			throw new IOException();
		});
		runnable1.run();
		runnable1.run();
		assertThat(throwableRunnable.value).isEqualTo(2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(runnable2::run);
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableRunnable.unchecked(null));
	}

	@Test
	void testOf() throws Throwable {
		final var runnable = new FooRunnable();
		final var throwableRunnable1 = ThrowableRunnable.of(runnable);
		final var throwableRunnable2 = ThrowableRunnable.of(() -> {
			throw new UncheckedIOException(new IOException());
		});
		throwableRunnable1.run();
		throwableRunnable1.run();
		assertThat(runnable.value).isEqualTo(2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(throwableRunnable2::run);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableRunnable.of(null));
	}
}