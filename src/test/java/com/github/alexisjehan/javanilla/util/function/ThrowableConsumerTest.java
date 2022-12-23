/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.function;

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

final class ThrowableConsumerTest {

	@Test
	void testAccept() throws IOException {
		final var throwableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() + 1);
		final var exceptionThrowableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> {
			throw new IOException();
		};
		final var list = new ArrayList<Integer>();
		throwableConsumer.accept(list);
		throwableConsumer.accept(list);
		assertThat(list).containsExactly(1, 2);
		list.clear();
		assertThatIOException().isThrownBy(() -> exceptionThrowableConsumer.accept(list));
		assertThatIOException().isThrownBy(() -> exceptionThrowableConsumer.accept(list));
		assertThat(list).isEmpty();
	}

	@Test
	void testAndThen() throws IOException {
		final var fooThrowableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() + 1);
		final var barThrowableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() - 1);
		final var exceptionThrowableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> {
			throw new IOException();
		};
		final var list = new ArrayList<Integer>();
		fooThrowableConsumer.andThen(barThrowableConsumer).accept(list);
		fooThrowableConsumer.andThen(barThrowableConsumer).accept(list);
		assertThat(list).containsExactly(1, 0, 3, 2);
		list.clear();
		barThrowableConsumer.andThen(fooThrowableConsumer).accept(list);
		barThrowableConsumer.andThen(fooThrowableConsumer).accept(list);
		assertThat(list).containsExactly(-1, 2, 1, 4);
		list.clear();
		assertThatIOException().isThrownBy(() -> fooThrowableConsumer.andThen(exceptionThrowableConsumer).accept(list));
		assertThatIOException().isThrownBy(() -> exceptionThrowableConsumer.andThen(fooThrowableConsumer).accept(list));
		assertThat(list).containsExactly(1);
	}

	@Test
	void testAndThenInvalid() {
		final var throwableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() + 1);
		assertThatNullPointerException().isThrownBy(() -> throwableConsumer.andThen(null));
	}

	@Test
	void testUnchecked() {
		final var throwableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() + 1);
		final var exceptionThrowableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> {
			throw new IOException();
		};
		final var list = new ArrayList<Integer>();
		ThrowableConsumer.unchecked(throwableConsumer).accept(list);
		ThrowableConsumer.unchecked(throwableConsumer).accept(list);
		assertThat(list).containsExactly(1, 2);
		list.clear();
		assertThat(ThrowableConsumer.unchecked(exceptionThrowableConsumer)).satisfies(uncheckedExceptionThrowableConsumer -> {
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableConsumer.accept(list));
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableConsumer.accept(list));
		});
		assertThat(list).isEmpty();
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableConsumer.unchecked(null));
	}

	@Test
	void testSneaky() {
		final var throwableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> t.add(t.size() + 1);
		final var exceptionThrowableConsumer = (ThrowableConsumer<List<Integer>, IOException>) t -> {
			throw new IOException();
		};
		final var list = new ArrayList<Integer>();
		ThrowableConsumer.sneaky(throwableConsumer).accept(list);
		ThrowableConsumer.sneaky(throwableConsumer).accept(list);
		assertThat(list).containsExactly(1, 2);
		list.clear();
		assertThat(ThrowableConsumer.sneaky(exceptionThrowableConsumer)).satisfies(sneakyExceptionThrowableConsumer -> {
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableConsumer.accept(list));
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableConsumer.accept(list));
		});
		assertThat(list).isEmpty();
	}

	@Test
	void testSneakyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableConsumer.sneaky(null));
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