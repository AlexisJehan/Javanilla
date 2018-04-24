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
package com.github.alexisjehan.javanilla.util.function;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Suppliers} unit tests.</p>
 */
final class SuppliersTest {

	@Test
	void testCached() {
		final var cachedSupplier = Suppliers.cached(new Supplier<>() {
			private int i = 0;

			@Override
			public Integer get() {
				return ++i;
			}
		});
		assertThat(cachedSupplier.get()).isEqualTo(1);
		assertThat(cachedSupplier.get()).isEqualTo(1);
	}

	@Test
	void testCachedNull() {
		assertThatNullPointerException().isThrownBy(() -> Suppliers.cached(null));
	}

	@Test
	void testOnce() {
		final var onceSupplier = Suppliers.once(() -> 1);
		assertThat(onceSupplier.get()).isEqualTo(1);
		assertThatIllegalStateException().isThrownBy(onceSupplier::get);
	}

	@Test
	void testOnceNull() {
		assertThatNullPointerException().isThrownBy(() -> Suppliers.once(null));
	}
}