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
package com.github.alexisjehan.javanilla.util.function.throwable;

import com.github.alexisjehan.javanilla.misc.tuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowableBiConsumer} unit tests.</p>
 */
final class ThrowableBiConsumerTest {

	@Test
	void testSimple() {
		final ThrowableBiConsumer<Integer, Float, IOException> throwableBiConsumer = (t, u) -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableBiConsumer.accept(1, 2.3f));
	}

	@Test
	void testAndThen() {
		final var list = new ArrayList<>();
		final ThrowableBiConsumer<Integer, Float, IOException> throwableBiConsumer1 = (t, u) -> list.add(Pair.of(t, u));
		try {
			throwableBiConsumer1.andThen((t, u) -> list.add(Pair.of(t + 1, u + 1.1f))).accept(1, 2.3f);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
		assertThat(list).contains(Pair.of(1, 2.3f), Pair.of(2, 3.4f));

		list.clear();
		final ThrowableBiConsumer<Integer, Float, IOException> throwableBiConsumer2 = (t, u) -> {
			throw new IOException();
		};
		assertThatIOException().isThrownBy(() -> throwableBiConsumer1.andThen(throwableBiConsumer2).accept(1, 2.3f));
		assertThat(list).contains(Pair.of(1, 2.3f));
	}

	@Test
	void testAndThenNull() {
		final ThrowableBiConsumer<Integer, Float, IOException> throwableBiConsumer = (t, u) -> {
			throw new IOException();
		};
		assertThatNullPointerException().isThrownBy(() -> throwableBiConsumer.andThen(null));
	}

	@Test
	void testUnchecked() {
		final var list = new ArrayList<>();
		final ThrowableBiConsumer<Integer, Float, IOException> throwableBiConsumer1 = (t, u) -> list.add(Pair.of(t, u));
		try {
			throwableBiConsumer1.accept(1, 2.3f);
		} catch (final IOException e) {
			fail(e.getMessage());
		}
		ThrowableBiConsumer.unchecked(throwableBiConsumer1).accept(1, 2.3f);
		assertThat(list).contains(Pair.of(1, 2.3f), Pair.of(1, 2.3f));

		final ThrowableBiConsumer<Integer, Float, IOException> throwableBiConsumer2 = (t, u) -> {
			throw new IOException();
		};
		final var consumer = ThrowableBiConsumer.unchecked(throwableBiConsumer2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> consumer.accept(1, 2.3f));
	}

	@Test
	void testUncheckedNull() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiConsumer.unchecked(null));
	}

	@Test
	void testOf() {
		final BiConsumer<Integer, Float> biConsumer = (t, u) -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwableBiConsumer = ThrowableBiConsumer.of(biConsumer);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> throwableBiConsumer.accept(1, 2.3f));
	}

	@Test
	void testOfNull() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableBiConsumer.of(null));
	}
}