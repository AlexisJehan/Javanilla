/*
 * MIT License
 *
 * Copyright (c) 2018-2021 Alexis Jehan
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

import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link CountIterator} unit tests.</p>
 */
final class CountIteratorTest {

	private static final Integer[] ELEMENTS = ObjectArrays.of(1, 2, 3);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new CountIterator<>(null));
	}

	@Test
	void testNext() {
		final var countIterator = new CountIterator<>(Iterators.of(ELEMENTS));
		assertThat(countIterator.getCount()).isZero();
		assertThat(countIterator.hasNext()).isTrue();
		assertThat(countIterator.next()).isEqualTo(ELEMENTS[0]);
		assertThat(countIterator.getCount()).isEqualTo(1L);
		assertThat(countIterator.hasNext()).isTrue();
		assertThat(countIterator.next()).isEqualTo(ELEMENTS[1]);
		assertThat(countIterator.getCount()).isEqualTo(2L);
		assertThat(countIterator.hasNext()).isTrue();
		assertThat(countIterator.next()).isEqualTo(ELEMENTS[2]);
		assertThat(countIterator.getCount()).isEqualTo(3L);
		assertThat(countIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(countIterator::next);
		assertThat(countIterator.getCount()).isEqualTo(3L);
	}

	@Test
	void testRemove() {
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var countIterator = new CountIterator<>(list.iterator());
		assertThat(countIterator.getCount()).isZero();
		countIterator.next();
		assertThat(countIterator.getCount()).isEqualTo(1L);
		assertThat(list).containsExactly(ELEMENTS);
		countIterator.remove();
		assertThat(countIterator.getCount()).isEqualTo(1L);
		assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
	}
}