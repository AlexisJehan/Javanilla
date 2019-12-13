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
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link ThrowableBiConsumer} unit tests.</p>
 */
final class ThrowableBiConsumerTest {

	@Test
	void testAccept() throws IOException {
		final var throwableBiConsumer1 = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u + 1);
		final var throwableBiConsumer2 = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		{
			final var list = new ArrayList<Integer>();
			throwableBiConsumer1.accept(list, 1);
			throwableBiConsumer1.accept(list, 3);
			assertThat(list).containsExactly(2, 4);
		}
		{
			final var list = new ArrayList<Integer>();
			assertThatIOException().isThrownBy(() -> throwableBiConsumer2.accept(list, 1));
			assertThatIOException().isThrownBy(() -> throwableBiConsumer2.accept(list, 3));
			assertThat(list).isEmpty();
		}
	}

	@Test
	void testAndThen() throws IOException {
		final var throwableBiConsumer1 = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u + 1);
		final var throwableBiConsumer2 = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u - 1);
		final var throwableBiConsumer3 = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		{
			final var list = new ArrayList<Integer>();
			throwableBiConsumer1.andThen(throwableBiConsumer2).accept(list, 1);
			throwableBiConsumer1.andThen(throwableBiConsumer2).accept(list, 3);
			assertThat(list).containsExactly(2, 0, 4, 2);
		}
		{
			final var list = new ArrayList<Integer>();
			throwableBiConsumer2.andThen(throwableBiConsumer1).accept(list, 1);
			throwableBiConsumer2.andThen(throwableBiConsumer1).accept(list, 3);
			assertThat(list).containsExactly(0, 2, 2, 4);
		}
		{
			final var list = new ArrayList<Integer>();
			assertThatIOException().isThrownBy(() -> throwableBiConsumer1.andThen(throwableBiConsumer3).accept(list, 1));
			assertThatIOException().isThrownBy(() -> throwableBiConsumer1.andThen(throwableBiConsumer3).accept(list, 3));
			assertThatIOException().isThrownBy(() -> throwableBiConsumer3.andThen(throwableBiConsumer1).accept(list, 1));
			assertThatIOException().isThrownBy(() -> throwableBiConsumer3.andThen(throwableBiConsumer1).accept(list, 3));
			assertThat(list).containsExactly(2, 4);
		}
	}

	@Test
	void testAndThenInvalid() {
		final var throwableBiConsumer = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableBiConsumer.andThen(null));
	}

	@Test
	void testUnchecked() {
		final var throwableBiConsumer1 = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> t.add(u + 1);
		final var throwableBiConsumer2 = (ThrowableBiConsumer<List<Integer>, Integer, IOException>) (t, u) -> {
			throw new IOException();
		};
		{
			final var list = new ArrayList<Integer>();
			ThrowableBiConsumer.unchecked(throwableBiConsumer1).accept(list, 1);
			ThrowableBiConsumer.unchecked(throwableBiConsumer1).accept(list, 3);
			assertThat(list).containsExactly(2, 4);
		}
		{
			final var list = new ArrayList<Integer>();
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowableBiConsumer.unchecked(throwableBiConsumer2).accept(list, 1));
			assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> ThrowableBiConsumer.unchecked(throwableBiConsumer2).accept(list, 3));
			assertThat(list).isEmpty();
		}
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiConsumer.unchecked(null));
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