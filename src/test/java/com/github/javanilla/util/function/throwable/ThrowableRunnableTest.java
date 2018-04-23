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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowableRunnable} unit tests.</p>
 */
final class ThrowableRunnableTest {

	@Test
	void testSimple() {
		final ThrowableRunnable<IOException> throwableRunnable = () -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(throwableRunnable::run);
	}

	@Test
	void testUnchecked() {
		final var list = new ArrayList<>();
		final ThrowableRunnable<IOException> throwableRunnable1 = () -> list.add(1);
		try {
			throwableRunnable1.run();
		} catch (final IOException e) {
			fail(e.getMessage());
		}
		ThrowableRunnable.unchecked(throwableRunnable1).run();
		assertThat(list).contains(1, 1);

		final ThrowableRunnable<IOException> throwableRunnable2 = () -> {
			throw new IOException();
		};
		final var runnable = ThrowableRunnable.unchecked(throwableRunnable2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(runnable::run);
	}

	@Test
	void testUncheckedNull() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableRunnable.unchecked(null));
	}

	@Test
	void testOf() {
		final Runnable runnable = () -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwableRunnable = ThrowableRunnable.of(runnable);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(throwableRunnable::run);
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableRunnable.of(null));
	}
}