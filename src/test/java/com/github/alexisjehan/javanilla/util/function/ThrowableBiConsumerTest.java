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
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ThrowableBiConsumerTest {

	@Test
	void testAccept() throws IOException {
		final var throwableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u + 1);
		final var exceptionThrowableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		final var list = new ArrayList<Integer>();
		throwableBiConsumer.accept(list, 1);
		throwableBiConsumer.accept(list, 3);
		assertThat(list).containsExactly(2, 4);
		list.clear();
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiConsumer.accept(list, 1));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiConsumer.accept(list, 3));
		assertThat(list).isEmpty();
	}

	@Test
	void testAndThen() throws IOException {
		final var fooThrowableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u + 1);
		final var barThrowableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u - 1);
		final var exceptionThrowableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		final var list = new ArrayList<Integer>();
		fooThrowableBiConsumer.andThen(barThrowableBiConsumer).accept(list, 1);
		fooThrowableBiConsumer.andThen(barThrowableBiConsumer).accept(list, 3);
		assertThat(list).containsExactly(2, 0, 4, 2);
		list.clear();
		barThrowableBiConsumer.andThen(fooThrowableBiConsumer).accept(list, 1);
		barThrowableBiConsumer.andThen(fooThrowableBiConsumer).accept(list, 3);
		assertThat(list).containsExactly(0, 2, 2, 4);
		list.clear();
		assertThatIOException().isThrownBy(() -> fooThrowableBiConsumer.andThen(exceptionThrowableBiConsumer).accept(list, 1));
		assertThatIOException().isThrownBy(() -> fooThrowableBiConsumer.andThen(exceptionThrowableBiConsumer).accept(list, 3));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiConsumer.andThen(fooThrowableBiConsumer).accept(list, 1));
		assertThatIOException().isThrownBy(() -> exceptionThrowableBiConsumer.andThen(fooThrowableBiConsumer).accept(list, 3));
		assertThat(list).containsExactly(2, 4);
	}

	@Test
	void testAndThenInvalid() {
		final var throwableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u + 1);
		assertThatNullPointerException().isThrownBy(() -> throwableBiConsumer.andThen(null));
	}

	@Test
	void testUnchecked() {
		final var throwableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u + 1);
		final var exceptionThrowableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		final var list = new ArrayList<Integer>();
		ThrowableBiConsumer.unchecked(throwableBiConsumer).accept(list, 1);
		ThrowableBiConsumer.unchecked(throwableBiConsumer).accept(list, 3);
		assertThat(list).containsExactly(2, 4);
		list.clear();
		assertThat(ThrowableBiConsumer.unchecked(exceptionThrowableBiConsumer)).satisfies(uncheckedExceptionThrowableBiConsumer -> {
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableBiConsumer.accept(list, 1));
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> uncheckedExceptionThrowableBiConsumer.accept(list, 3));
		});
		assertThat(list).isEmpty();
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiConsumer.unchecked(null));
	}

	@Test
	void testSneaky() {
		final var throwableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u + 1);
		final var exceptionThrowableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		final var list = new ArrayList<Integer>();
		ThrowableBiConsumer.sneaky(throwableBiConsumer).accept(list, 1);
		ThrowableBiConsumer.sneaky(throwableBiConsumer).accept(list, 3);
		assertThat(list).containsExactly(2, 4);
		list.clear();
		assertThat(ThrowableBiConsumer.sneaky(exceptionThrowableBiConsumer)).satisfies(sneakyExceptionThrowableBiConsumer -> {
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableBiConsumer.accept(list, 1));
			assertThatExceptionOfType(IOException.class).isThrownBy(() -> sneakyExceptionThrowableBiConsumer.accept(list, 3));
		});
		assertThat(list).isEmpty();
	}

	@Test
	void testSneakyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiConsumer.sneaky(null));
	}

	@Test
	void testOf() throws Throwable {
		final var throwableBiConsumer = ThrowableBiConsumer.of((BiConsumer<List<Integer>, Integer>) (t, u) -> t.add(u + 1));
		final var list = new ArrayList<Integer>();
		throwableBiConsumer.accept(list, 1);
		throwableBiConsumer.accept(list, 3);
		assertThat(list).containsExactly(2, 4);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiConsumer.of(null));
	}
}