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
package com.github.alexisjehan.javanilla.util.collection;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link Lists} unit tests.</p>
 */
final class ListsTest {

	@Test
	void testNullToEmpty() {
		assertThat(Lists.nullToEmpty(null)).isEmpty();
		assertThat(Lists.nullToEmpty(Collections.emptyList())).isEmpty();
		assertThat(Lists.nullToEmpty(Collections.singletonList("foo"))).isNotEmpty();
	}

	@Test
	void testEmptyToNull() {
		assertThat(Lists.emptyToNull(null)).isNull();
		assertThat(Lists.emptyToNull(Collections.emptyList())).isNull();
		assertThat(Lists.emptyToNull(Collections.singletonList("foo"))).isNotNull();
	}
}