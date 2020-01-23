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

import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
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

	private static final Integer[] ELEMENTS = ObjectArrays.of(1, 2, 3);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new RangeIterator<>(null, 0L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeIterator<>(Iterators.of(ELEMENTS), -1L, 0L));
		assertThatIllegalArgumentException().isThrownBy(() -> new RangeIterator<>(Iterators.of(ELEMENTS), 1L, 0L));
	}

	@Test
	@SuppressWarnings("unchecked")
	void testNext() {
		assertThat((Object) new RangeIterator<>(Iterators.of(ELEMENTS), 0L, 0L)).satisfies(rangeIterator -> {
			assertThat(((RangeIterator<Integer>) rangeIterator).getFromIndex()).isEqualTo(0L);
			assertThat(((RangeIterator<Integer>) rangeIterator).getToIndex()).isEqualTo(0L);
			assertThat(((RangeIterator<Integer>) rangeIterator).hasNext()).isTrue();
			assertThat(((RangeIterator<Integer>) rangeIterator).next()).isEqualTo(ELEMENTS[0]);
			assertThat(((RangeIterator<Integer>) rangeIterator).hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(((RangeIterator<Integer>) rangeIterator)::next);
		});
		assertThat((Object) new RangeIterator<>(Iterators.of(ELEMENTS), 1L, 1L)).satisfies(rangeIterator -> {
			assertThat(((RangeIterator<Integer>) rangeIterator).getFromIndex()).isEqualTo(1L);
			assertThat(((RangeIterator<Integer>) rangeIterator).getToIndex()).isEqualTo(1L);
			assertThat(((RangeIterator<Integer>) rangeIterator).hasNext()).isTrue();
			assertThat(((RangeIterator<Integer>) rangeIterator).next()).isEqualTo(ELEMENTS[1]);
			assertThat(((RangeIterator<Integer>) rangeIterator).hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(((RangeIterator<Integer>) rangeIterator)::next);
		});
		assertThat((Object) new RangeIterator<>(Iterators.of(ELEMENTS), 0L, 10L)).satisfies(rangeIterator -> {
			assertThat(((RangeIterator<Integer>) rangeIterator).getFromIndex()).isEqualTo(0L);
			assertThat(((RangeIterator<Integer>) rangeIterator).getToIndex()).isEqualTo(10L);
			assertThat(((RangeIterator<Integer>) rangeIterator).hasNext()).isTrue();
			assertThat(((RangeIterator<Integer>) rangeIterator).next()).isEqualTo(ELEMENTS[0]);
			assertThat(((RangeIterator<Integer>) rangeIterator).hasNext()).isTrue();
			assertThat(((RangeIterator<Integer>) rangeIterator).next()).isEqualTo(ELEMENTS[1]);
			assertThat(((RangeIterator<Integer>) rangeIterator).hasNext()).isTrue();
			assertThat(((RangeIterator<Integer>) rangeIterator).next()).isEqualTo(ELEMENTS[2]);
			assertThat(((RangeIterator<Integer>) rangeIterator).hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(((RangeIterator<Integer>) rangeIterator)::next);
		});
		assertThat(new RangeIterator<>(Iterators.of(ELEMENTS), 10L, 10L)).satisfies(rangeIterator -> {
			assertThat(((RangeIterator<Integer>) rangeIterator).getFromIndex()).isEqualTo(10L);
			assertThat(((RangeIterator<Integer>) rangeIterator).getToIndex()).isEqualTo(10L);
			assertThat(rangeIterator.hasNext()).isFalse();
			assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(((RangeIterator<Integer>) rangeIterator)::next);
		});
	}

	@Test
	void testRemove() {
		final var list = new ArrayList<>(List.of(ELEMENTS));
		final var rangeIterator = new RangeIterator<>(list.iterator(), 0L, 0L);
		while (rangeIterator.hasNext()) {
			rangeIterator.next();
			rangeIterator.remove();
		}
		assertThat(list).containsExactly(ELEMENTS[1], ELEMENTS[2]);
		assertThat(rangeIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(rangeIterator::next);
	}
}