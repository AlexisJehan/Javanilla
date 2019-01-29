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
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowableRunnable} unit tests.</p>
 */
final class ThrowableRunnableTest {

	@Test
	void testUnchecked() throws IOException {
		final var list = new ArrayList<>();
		final var throwableRunnable1 = (ThrowableRunnable<IOException>) () -> list.add(1);
		throwableRunnable1.run();
		ThrowableRunnable.unchecked(throwableRunnable1).run();
		assertThat(list).contains(1, 1);

		final var throwableRunnable2 = (ThrowableRunnable<IOException>) () -> {
			throw new IOException();
		};
		final var runnable = ThrowableRunnable.unchecked(throwableRunnable2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(runnable::run);
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableRunnable.unchecked(null));
	}

	@Test
	void testOf() {
		final var runnable = (Runnable) () -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwableRunnable = ThrowableRunnable.of(runnable);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(throwableRunnable::run);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableRunnable.of(null));
	}
}