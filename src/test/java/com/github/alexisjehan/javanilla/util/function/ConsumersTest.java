/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ConsumersTest {

	@Test
	void testOnce() {
		final var onceConsumer = Consumers.once(input -> {});
		onceConsumer.accept(1);
		assertThatIllegalStateException().isThrownBy(() -> onceConsumer.accept(1));
	}

	@Test
	void testOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Consumers.once(null));
	}

	@Test
	void testDistinct() {
		final var list = new ArrayList<>();
		final var distinctConsumer = Consumers.distinct(list::add);
		distinctConsumer.accept(1);
		assertThat(list).containsExactly(1);
		distinctConsumer.accept(2);
		assertThat(list).containsExactly(1, 2);
		distinctConsumer.accept(2);
		assertThat(list).containsExactly(1, 2);
		distinctConsumer.accept(3);
		assertThat(list).containsExactly(1, 2, 3);
	}

	@Test
	void testDistinctInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Consumers.distinct(null));
	}
}