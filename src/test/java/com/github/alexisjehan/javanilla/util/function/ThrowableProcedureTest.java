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
package com.github.alexisjehan.javanilla.util.function;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.atomic.LongAdder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ThrowableProcedureTest {

	static final class FooProcedure implements Procedure {

		private final LongAdder adder = new LongAdder();

		@Override
		public void execute() {
			adder.increment();
		}

		int getValue() {
			return adder.intValue();
		}
	}

	static final class FooThrowableProcedure implements ThrowableProcedure<IOException> {

		private final LongAdder adder = new LongAdder();

		@Override
		public void execute() {
			adder.increment();
		}

		int getValue() {
			return adder.intValue();
		}
	}

	@Test
	void testRun() {
		final var throwableProcedure = new FooThrowableProcedure();
		final var exceptionThrowableProcedure = (ThrowableProcedure<IOException>) () -> {
			throw new IOException();
		};
		throwableProcedure.execute();
		throwableProcedure.execute();
		assertThat(throwableProcedure.getValue()).isEqualTo(2);
		assertThatIOException().isThrownBy(exceptionThrowableProcedure::execute);
	}

	@Test
	void testUnchecked() {
		final var throwableProcedure = new FooThrowableProcedure();
		final var procedure = ThrowableProcedure.unchecked(throwableProcedure);
		final var exceptionProcedure = ThrowableProcedure.unchecked(() -> {
			throw new IOException();
		});
		procedure.execute();
		procedure.execute();
		assertThat(throwableProcedure.getValue()).isEqualTo(2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(exceptionProcedure::execute);
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableProcedure.unchecked(null));
	}

	@Test
	void testSneaky() {
		final var throwableProcedure = new FooThrowableProcedure();
		final var procedure = ThrowableProcedure.sneaky(throwableProcedure);
		final var exceptionProcedure = ThrowableProcedure.sneaky(() -> {
			throw new IOException();
		});
		procedure.execute();
		procedure.execute();
		assertThat(throwableProcedure.getValue()).isEqualTo(2);
		assertThatExceptionOfType(IOException.class).isThrownBy(exceptionProcedure::execute);
	}

	@Test
	void testSneakyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableProcedure.sneaky(null));
	}

	@Test
	void testOf() throws Throwable {
		final var procedure = new FooProcedure();
		final var throwableProcedure = ThrowableProcedure.of(procedure);
		final var exceptionThrowableProcedure = ThrowableProcedure.of(() -> {
			throw new UncheckedIOException(new IOException());
		});
		throwableProcedure.execute();
		throwableProcedure.execute();
		assertThat(procedure.getValue()).isEqualTo(2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(exceptionThrowableProcedure::execute);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableProcedure.of(null));
	}
}