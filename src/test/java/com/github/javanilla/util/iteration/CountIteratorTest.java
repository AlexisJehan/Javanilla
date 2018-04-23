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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link CountIterator} unit tests.</p>
 */
final class CountIteratorTest {

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new CountIterator<>(null));
	}

	@Test
	void testNext() {
		final var countIterator = new CountIterator<>(Iterators.of(1, 2, 3));
		assertThat(countIterator.getCount()).isEqualTo(0L);
		assertThat(countIterator.next()).isEqualTo(1);
		assertThat(countIterator.getCount()).isEqualTo(1L);
		assertThat(countIterator.next()).isEqualTo(2);
		assertThat(countIterator.getCount()).isEqualTo(2L);
		assertThat(countIterator.next()).isEqualTo(3);
		assertThat(countIterator.getCount()).isEqualTo(3L);
		assertThat(countIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(countIterator::next);
	}

	@Test
	void testRemove() {
		final var list = new ArrayList<>(Arrays.asList(1, 2, 3));
		final var countIterator = new CountIterator<>(list.iterator());
		assertThat(countIterator.getCount()).isEqualTo(0L);
		countIterator.next();
		assertThat(countIterator.getCount()).isEqualTo(1L);
		assertThat(list).hasSize(3);
		countIterator.remove();
		assertThat(countIterator.getCount()).isEqualTo(1L);
		assertThat(list).hasSize(2);
	}
}