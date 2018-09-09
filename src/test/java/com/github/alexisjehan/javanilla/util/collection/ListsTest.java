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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Lists} unit tests.</p>
 */
final class ListsTest {

	@Test
	void testNullToEmpty() {
		assertThat(Lists.nullToEmpty(null)).isEmpty();
		assertThat(Lists.nullToEmpty(Collections.emptyList())).isEmpty();
		assertThat(Lists.nullToEmpty(Collections.singletonList("foo"))).containsExactly("foo");
	}

	@Test
	void testNullToDefault() {
		assertThat(Lists.nullToDefault(null, Collections.singletonList("bar"))).containsExactly("bar");
		assertThat(Lists.nullToDefault(Collections.emptyList(), Collections.singletonList("bar"))).isEmpty();
		assertThat(Lists.nullToDefault(Collections.singletonList("foo"), Collections.singletonList("bar"))).containsExactly("foo");
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.nullToDefault(Collections.singletonList("foo"), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Lists.emptyToNull(null)).isNull();
		assertThat(Lists.emptyToNull(Collections.emptyList())).isNull();
		assertThat(Lists.emptyToNull(Collections.singletonList("foo"))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Lists.emptyToDefault(null, Collections.singletonList("bar"))).isNull();
		assertThat(Lists.emptyToDefault(Collections.emptyList(), Collections.singletonList("bar"))).containsExactly("bar");
		assertThat(Lists.emptyToDefault(Collections.singletonList("foo"), Collections.singletonList("bar"))).containsExactly("foo");
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Lists.emptyToDefault(Collections.singletonList("foo"), Collections.emptyList()));
	}

	@Test
	void testGetFirst() {
		assertThat(Lists.getFirst(Collections.emptyList()).isEmpty()).isTrue();
		assertThat(Lists.getFirst(Collections.singletonList(null)).get()).isNull();
		assertThat(Lists.getFirst(List.of(1, 2, 3)).get()).isEqualTo(1);
	}

	@Test
	void testGetFirstInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.getFirst(null));
	}

	@Test
	void testGetLast() {
		assertThat(Lists.getLast(Collections.emptyList()).isEmpty()).isTrue();
		assertThat(Lists.getLast(Collections.singletonList(null)).get()).isNull();
		assertThat(Lists.getLast(List.of(1, 2, 3)).get()).isEqualTo(3);
	}

	@Test
	void testGetLastInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.getLast(null));
	}
}