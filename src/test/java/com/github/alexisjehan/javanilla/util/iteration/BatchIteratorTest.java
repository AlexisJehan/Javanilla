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
package com.github.alexisjehan.javanilla.util.iteration;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

/**
 * <p>{@link BatchIterator} unit tests.</p>
 */
final class BatchIteratorTest {

	@Test
	void testConstructorNull() {
		assertThatNullPointerException().isThrownBy(() -> new BatchIterator<>(null, 3));
	}

	@Test
	void testConstructorInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BatchIterator<>(Collections.emptyIterator(), 0));
	}

	@Test
	void testNext() {
		final var batchIterator = new BatchIterator<>(Iterators.of(1, 2, 3, 4, 5, 6, 7, 8), 3);
		assertThat(batchIterator.getBatchSize()).isEqualTo(3);
		assertThat(batchIterator.next()).containsExactly(1, 2, 3);
		assertThat(batchIterator.next()).containsExactly(4, 5, 6);
		assertThat(batchIterator.next()).containsExactly(7, 8);
		assertThat(batchIterator.hasNext()).isFalse();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(batchIterator::next);
	}
}