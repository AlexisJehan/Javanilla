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

package com.github.alexisjehan.javanilla.util.function;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Functions} unit tests.</p>
 */
final class FunctionsTest {

	@Test
	void testCache() {
		final var cachedFunction = Functions.cache(new Function<Integer, Integer>() {
			private final Map<Integer, LongAdder> map = new HashMap<>();

			@Override
			public Integer apply(final Integer i) {
				final var adder = map.computeIfAbsent(i, key -> new LongAdder());
				adder.increment();
				return adder.intValue();
			}
		});
		assertThat(cachedFunction.apply(1)).isEqualTo(1);
		assertThat(cachedFunction.apply(1)).isEqualTo(1);
		assertThat(cachedFunction.apply(2)).isEqualTo(1);
		assertThat(cachedFunction.apply(2)).isEqualTo(1);
	}

	@Test
	void testCacheInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Functions.cache(null));
	}
}