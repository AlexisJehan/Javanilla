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
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ThrowableConsumer} unit tests.</p>
 */
final class ThrowableConsumerTest {

	@Test
	void testAccept() throws IOException {
		final var throwableConsumer1 = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() + 1);
		final var throwableConsumer2 = (ThrowableConsumer<List<Integer>, IOException>) t -> {
			throw new IOException();
		};
		{
			final var list = new ArrayList<Integer>();
			throwableConsumer1.accept(list);
			throwableConsumer1.accept(list);
			assertThat(list).containsExactly(1, 2);
		}
		{
			final var list = new ArrayList<Integer>();
			assertThatIOException().isThrownBy(() -> throwableConsumer2.accept(list));
			assertThatIOException().isThrownBy(() -> throwableConsumer2.accept(list));
			assertThat(list).isEmpty();
		}
	}

	@Test
	void testAndThen() throws IOException {
		final var throwableConsumer1 = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() + 1);
		final var throwableConsumer2 = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() - 1);
		final var throwableConsumer3 = (ThrowableConsumer<List<Integer>, IOException>) t -> {
			throw new IOException();
		};
		{
			final var list = new ArrayList<Integer>();
			throwableConsumer1.andThen(throwableConsumer2).accept(list);
			throwableConsumer1.andThen(throwableConsumer2).accept(list);
			assertThat(list).containsExactly(1, 0, 3, 2);
		}
		{
			final var list = new ArrayList<Integer>();
			throwableConsumer2.andThen(throwableConsumer1).accept(list);
			throwableConsumer2.andThen(throwableConsumer1).accept(list);
			assertThat(list).containsExactly(-1, 2, 1, 4);
		}
		{
			final var list = new ArrayList<Integer>();
			assertThatIOException().isThrownBy(() -> throwableConsumer1.andThen(throwableConsumer3).accept(list));
			assertThatIOException().isThrownBy(() -> throwableConsumer3.andThen(throwableConsumer1).accept(list));
			assertThat(list).containsExactly(1);
		}
	}

	@Test
	void testAndThenInvalid() {
		final var throwableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableConsumer.andThen(null));
	}

	@Test
	void testUnchecked() {
		final var throwableConsumer1 = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() + 1);
		final var throwableConsumer2 = (ThrowableConsumer<List<Integer>, IOException>) t -> {
			throw new IOException();
		};
		{
			final var list = new ArrayList<Integer>();
			ThrowableConsumer.unchecked(throwableConsumer1).accept(list);
			ThrowableConsumer.unchecked(throwableConsumer1).accept(list);
			assertThat(list).containsExactly(1, 2);
		}
		{
			final var list = new ArrayList<Integer>();
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowableConsumer.unchecked(throwableConsumer2).accept(list));
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowableConsumer.unchecked(throwableConsumer2).accept(list));
			assertThat(list).isEmpty();
		}
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableConsumer.unchecked(null));
	}

	@Test
	void testOf() throws Throwable {
		final var throwableConsumer = ThrowableConsumer.of((Consumer<List<Integer>>) t -> t.add(t.size() + 1));
		final var list = new ArrayList<Integer>();
		throwableConsumer.accept(list);
		throwableConsumer.accept(list);
		assertThat(list).containsExactly(1, 2);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableConsumer.of(null));
	}
}