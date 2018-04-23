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
package com.github.javanilla.util.iteration;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link RangeIterator} unit tests.</p>
 */
final class RangeIteratorTest {

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new RangeIterator<>(null, 6L, 20L));
	}

	@Test
	void testConstructorInvalid() {
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeIterator<>(Collections.emptyIterator(), -5L, 20L));
		assertThatExceptionOfType(IndexOutOfBoundsException.class).isThrownBy(() -> new RangeIterator<>(Collections.emptyIterator(), 10L, 5L));
	}

	@Test
	void testNextRange() {
		final var rangeIterator = new RangeIterator<>(Iterators.of(0, 1, 2, 3, 4, 5), 2L, 4L);
		assertThat(rangeIterator.getFromIndex()).isEqualTo(2L);
		assertThat(rangeIterator.getToIndex()).isEqualTo(4L);
		assertThat(rangeIterator).containsExactly(2, 3, 4);
		assertThat(rangeIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
	}

	@Test
	void testNextAll() {
		final var rangeIterator = new RangeIterator<>(Iterators.of(0, 1, 2, 3, 4, 5), 2000L);
		assertThat(rangeIterator.getFromIndex()).isEqualTo(0L);
		assertThat(rangeIterator.getToIndex()).isEqualTo(2000L);
		assertThat(rangeIterator).containsExactly(0, 1, 2, 3, 4, 5);
		assertThat(rangeIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
	}

	@Test
	void testNextOut() {
		final var rangeIterator = new RangeIterator<>(Iterators.of(0, 1, 2, 3, 4, 5), 1000L, 2000L);
		assertThat(rangeIterator.getFromIndex()).isEqualTo(1000L);
		assertThat(rangeIterator.getToIndex()).isEqualTo(2000L);
		assertThat(rangeIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
	}

	@Test
	void testRemove() {
		final var list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
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