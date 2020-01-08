/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.iteration;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link RangeIterator} unit tests.</p>
 */
final class RangeIteratorTest {

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeIterator<>(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeIterator<>(Iterators.singleton(1), -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeIterator<>(Iterators.singleton(1), 1L, 0L));
	}

	@Test
	void testNext() {
		{
			final var rangeIterator = new RangeIterator<>(Iterators.of(1, 2, 3), 0L, 0L);
			assertThat(rangeIterator.getFromIndex()).isEqualTo(0L);
			assertThat(rangeIterator.getToIndex()).isEqualTo(0L);
			assertThat(rangeIterator.hasNext()).isTrue();
			assertThat(rangeIterator.next()).isEqualTo(1);
			assertThat(rangeIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
		}
		{
			final var rangeIterator = new RangeIterator<>(Iterators.of(1, 2, 3), 1L, 1L);
			assertThat(rangeIterator.getFromIndex()).isEqualTo(1L);
			assertThat(rangeIterator.getToIndex()).isEqualTo(1L);
			assertThat(rangeIterator.hasNext()).isTrue();
			assertThat(rangeIterator.next()).isEqualTo(2);
			assertThat(rangeIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
		}
		{
			final var rangeIterator = new RangeIterator<>(Iterators.of(1, 2, 3), 0L, 10L);
			assertThat(rangeIterator.getFromIndex()).isEqualTo(0L);
			assertThat(rangeIterator.getToIndex()).isEqualTo(10L);
			assertThat(rangeIterator.hasNext()).isTrue();
			assertThat(rangeIterator.next()).isEqualTo(1);
			assertThat(rangeIterator.hasNext()).isTrue();
			assertThat(rangeIterator.next()).isEqualTo(2);
			assertThat(rangeIterator.hasNext()).isTrue();
			assertThat(rangeIterator.next()).isEqualTo(3);
			assertThat(rangeIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
		}
		{
			final var rangeIterator = new RangeIterator<>(Iterators.of(1, 2, 3), 10L, 10L);
			assertThat(rangeIterator.getFromIndex()).isEqualTo(10L);
			assertThat(rangeIterator.getToIndex()).isEqualTo(10L);
			assertThat(rangeIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
		}
	}

	@Test
	void testRemove() {
		final var list = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5));
		final var rangeIterator = new RangeIterator<>(list.iterator(), 2L, 4L);
		while (rangeIterator.hasNext()) {
			rangeIterator.next();
			rangeIterator.remove();
		}
		assertThat(list).containsExactly(0, 1, 5);
		assertThat(rangeIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
	}
}