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
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowableConsumer} unit tests.</p>
 */
final class ThrowableConsumerTest {

	@Test
	void testAndThen() throws IOException {
		final var list = new ArrayList<>();
		final ThrowableConsumer<Integer, IOException> throwableConsumer1 = list::add;
		throwableConsumer1.andThen(t -> list.add(t + 1)).accept(1);
		assertThat(list).contains(1, 2);

		list.clear();
		final ThrowableConsumer<Integer, IOException> throwableConsumer2 = t -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableConsumer1.andThen(throwableConsumer2).accept(1));
		assertThat(list).contains(1);
	}

	@Test
	void testAndThenInvalid() {
		final ThrowableConsumer<Integer, IOException> throwableConsumer = t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableConsumer.andThen(null));
	}

	@Test
	void testUnchecked() throws IOException {
		final var list = new ArrayList<>();
		final ThrowableConsumer<Integer, IOException> throwableConsumer1 = list::add;
		throwableConsumer1.accept(1);
		ThrowableConsumer.unchecked(throwableConsumer1).accept(1);
		assertThat(list).contains(1, 1);

		final ThrowableConsumer<Integer, IOException> throwableConsumer2 = t -> {
			throw new IOException();
		};
		final var consumer = ThrowableConsumer.unchecked(throwableConsumer2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> consumer.accept(1));
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableConsumer.unchecked(null));
	}

	@Test
	void testOf() {
		final Consumer<Integer> consumer = t -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwableConsumer = ThrowableConsumer.of(consumer);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> throwableConsumer.accept(1));
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableConsumer.of(null));
	}
}