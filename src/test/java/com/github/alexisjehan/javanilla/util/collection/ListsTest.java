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

import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
		assertThat(Lists.emptyToNull((List<?>) null)).isNull();
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
	void testGetOptionalFirst() {
		assertThat(Lists.getOptionalFirst(Collections.emptyList()).isEmpty()).isTrue();
		assertThat(Lists.getOptionalFirst(Collections.singletonList(null)).get()).isNull();
		assertThat(Lists.getOptionalFirst(new LinkedList<>(List.of(1, 2, 3))).get()).isEqualTo(1);
		assertThat(Lists.getOptionalFirst(new ArrayList<>(List.of(1, 2, 3))).get()).isEqualTo(1);
	}

	@Test
	void testGetOptionalFirstInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.getOptionalFirst(null));
	}

	@Test
	void testGetOptionalLast() {
		assertThat(Lists.getOptionalLast(Collections.emptyList()).isEmpty()).isTrue();
		assertThat(Lists.getOptionalLast(Collections.singletonList(null)).get()).isNull();
		assertThat(Lists.getOptionalLast(new LinkedList<>(List.of(1, 2, 3))).get()).isEqualTo(3);
		assertThat(Lists.getOptionalLast(new ArrayList<>(List.of(1, 2, 3))).get()).isEqualTo(3);
	}

	@Test
	void testGetOptionalLastInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.getOptionalLast(null));
	}

	@Test
	void testConcat() {
		assertThat(Lists.concat()).isEmpty();
		assertThat(Lists.concat(List.of(1))).containsExactly(1);
		assertThat(Lists.concat(List.of(1), List.of(2))).containsExactly(1, 2);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.concat((List<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.concat((List<List<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.concat((List<?>) null));
	}

	@Test
	void testJoin() {
		assertThat(Lists.join(ObjectArrays.empty(Integer.class), List.of(1), List.of(2))).containsExactly(1, 2);
		assertThat(Lists.join(ObjectArrays.singleton(0))).isEmpty();
		assertThat(Lists.join(ObjectArrays.singleton(0), List.of(1))).containsExactly(1);
		assertThat(Lists.join(ObjectArrays.singleton(0), List.of(1), List.of(2))).containsExactly(1, 0, 2);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.join(null, List.of(1)));
		assertThatNullPointerException().isThrownBy(() -> Lists.join(ObjectArrays.singleton(0), (List<?>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.join(ObjectArrays.singleton(0), (List<List<?>>) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.join(ObjectArrays.singleton(0), (List<?>) null));
	}
}