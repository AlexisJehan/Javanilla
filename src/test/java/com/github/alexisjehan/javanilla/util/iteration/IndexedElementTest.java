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

package com.github.alexisjehan.javanilla.util.iteration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link IndexedElement} unit tests.</p>
 */
final class IndexedElementTest {

	@Test
	void testEqualsHashCodeToString() {
		final var indexedElement = new IndexedElement<>(1L, "foo");
		assertThat(indexedElement).isEqualTo(indexedElement);
		assertThat(indexedElement).isNotEqualTo(1);
		{
			final var otherIndexedElement = new IndexedElement<>(1L, "foo");
			assertThat(indexedElement).isNotSameAs(otherIndexedElement);
			assertThat(indexedElement).isEqualTo(otherIndexedElement);
			assertThat(indexedElement).hasSameHashCodeAs(otherIndexedElement);
			assertThat(indexedElement).hasToString(otherIndexedElement.toString());
		}
		{
			final var otherIndexedElement = new IndexedElement<>(2L, "foo");
			assertThat(indexedElement).isNotSameAs(otherIndexedElement);
			assertThat(indexedElement).isNotEqualTo(otherIndexedElement);
			assertThat(indexedElement.hashCode()).isNotEqualTo(otherIndexedElement.hashCode());
			assertThat(indexedElement.toString()).isNotEqualTo(otherIndexedElement.toString());
		}
		{
			final var otherIndexedElement = new IndexedElement<>(1L, "bar");
			assertThat(indexedElement).isNotSameAs(otherIndexedElement);
			assertThat(indexedElement).isNotEqualTo(otherIndexedElement);
			assertThat(indexedElement.hashCode()).isNotEqualTo(otherIndexedElement.hashCode());
			assertThat(indexedElement.toString()).isNotEqualTo(otherIndexedElement.toString());
		}
	}

	@Test
	void testGetIndex() {
		assertThat(new IndexedElement<>(1L, "foo").getIndex()).isEqualTo(1L);
	}

	@Test
	void testGetElement() {
		assertThat(new IndexedElement<>(1L, "foo").getElement()).isEqualTo("foo");
	}
}