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
package com.github.javanilla.util.function;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link Predicates} unit tests.</p>
 */
final class PredicatesTest {

	@Test
	void testIntAlwaysTrue() {
		assertThat(Predicates.INT_ALWAYS_TRUE.test(1)).isTrue();
	}

	@Test
	void testIntAlwaysFalse() {
		assertThat(Predicates.INT_ALWAYS_FALSE.test(1)).isFalse();
	}

	@Test
	void testLongAlwaysTrue() {
		assertThat(Predicates.LONG_ALWAYS_TRUE.test(1L)).isTrue();
	}

	@Test
	void testLongAlwaysFalse() {
		assertThat(Predicates.LONG_ALWAYS_FALSE.test(1L)).isFalse();
	}

	@Test
	void testDoubleAlwaysTrue() {
		assertThat(Predicates.DOUBLE_ALWAYS_TRUE.test(1.0d)).isTrue();
	}

	@Test
	void testDoubleAlwaysFalse() {
		assertThat(Predicates.DOUBLE_ALWAYS_FALSE.test(1.0d)).isFalse();
	}

	@Test
	void testAlwaysTrue() {
		assertThat(Predicates.alwaysTrue().test(1)).isTrue();
	}

	@Test
	void testAlwaysFalse() {
		assertThat(Predicates.alwaysFalse().test(1)).isFalse();
	}

	@Test
	void testBiAlwaysTrue() {
		assertThat(Predicates.biAlwaysTrue().test(1, 2)).isTrue();
	}

	@Test
	void testBiAlwaysFalse() {
		assertThat(Predicates.biAlwaysFalse().test(1, 2)).isFalse();
	}
}