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
package com.github.alexisjehan.javanilla.util;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class BatchIteratorTest {

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new BatchIterator<>(null, 3));
		assertThatIllegalArgumentException().isThrownBy(() -> new BatchIterator<>(Iterators.singleton(1), 0));
	}

	@Test
	void testNext() {
		final var batchIterator = new BatchIterator<>(Iterators.of(1, 2, 3, 4, 5, 6, 7, 8), 3);
		assertThat(batchIterator.getBatchSize()).isEqualTo(3);
		assertThat(batchIterator.hasNext()).isTrue();
		assertThat(batchIterator.next()).containsExactly(1, 2, 3);
		assertThat(batchIterator.hasNext()).isTrue();
		assertThat(batchIterator.next()).containsExactly(4, 5, 6);
		assertThat(batchIterator.hasNext()).isTrue();
		assertThat(batchIterator.next()).containsExactly(7, 8);
		assertThat(batchIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(batchIterator::next);
	}
}