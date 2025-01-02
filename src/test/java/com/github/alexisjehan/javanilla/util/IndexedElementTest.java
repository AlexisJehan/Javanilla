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

import static org.assertj.core.api.Assertions.assertThat;

final class IndexedElementTest {

	private static final long INDEX = 1L;

	private static final String ELEMENT = "foo";

	private final IndexedElement<String> indexedElement = new IndexedElement<>(INDEX, ELEMENT);

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(indexedElement.equals(indexedElement)).isTrue();
		assertThat(indexedElement).isNotEqualTo(new Object());
		assertThat(new IndexedElement<>(INDEX, ELEMENT)).satisfies(otherIndexedElement -> {
			assertThat(otherIndexedElement).isNotSameAs(indexedElement);
			assertThat(otherIndexedElement).isEqualTo(indexedElement);
			assertThat(otherIndexedElement).hasSameHashCodeAs(indexedElement);
			assertThat(otherIndexedElement).hasToString(indexedElement.toString());
		});
		assertThat(new IndexedElement<>(2L, ELEMENT)).satisfies(otherIndexedElement -> {
			assertThat(otherIndexedElement).isNotSameAs(indexedElement);
			assertThat(otherIndexedElement).isNotEqualTo(indexedElement);
			assertThat(otherIndexedElement).doesNotHaveSameHashCodeAs(indexedElement);
			assertThat(otherIndexedElement).doesNotHaveToString(indexedElement.toString());
		});
		assertThat(new IndexedElement<>(INDEX, "bar")).satisfies(otherIndexedElement -> {
			assertThat(otherIndexedElement).isNotSameAs(indexedElement);
			assertThat(otherIndexedElement).isNotEqualTo(indexedElement);
			assertThat(otherIndexedElement).doesNotHaveSameHashCodeAs(indexedElement);
			assertThat(otherIndexedElement).doesNotHaveToString(indexedElement.toString());
		});
	}

	@Test
	void testGetters() {
		assertThat(indexedElement.getIndex()).isEqualTo(INDEX);
		assertThat(indexedElement.getElement()).isEqualTo(ELEMENT);
	}
}