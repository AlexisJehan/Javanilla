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

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link ThrowableSupplier} unit tests.</p>
 */
final class ThrowableSupplierTest {

	@Test
	void testUnchecked() throws IOException {
		final ThrowableSupplier<Integer, IOException> throwableSupplier1 = () -> 1;
		assertThat(throwableSupplier1.get()).isEqualTo(1);
		assertThat(ThrowableSupplier.unchecked(throwableSupplier1).get()).isEqualTo(1);

		final ThrowableSupplier<?, IOException> throwableSupplier2 = () -> {
			throw new IOException();
		};
		final var supplier = ThrowableSupplier.unchecked(throwableSupplier2);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(supplier::get);
	}

	@Test
	void testUncheckedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableSupplier.unchecked(null));
	}

	@Test
	void testOf() {
		final Supplier<?> supplier = () -> {
			throw new UncheckedIOException(new IOException());
		};
		final var throwableSupplier = ThrowableSupplier.of(supplier);
		assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(throwableSupplier::get);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> ThrowableSupplier.of(null));
	}
}