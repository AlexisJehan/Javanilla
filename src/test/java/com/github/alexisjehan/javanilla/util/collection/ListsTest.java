/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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

@Deprecated
final class ListsTest {

	private static final Integer[] ELEMENTS = ObjectArrays.of(1, 2, 3);

	@Test
	void testNullToEmpty() {
		assertThat(Lists.nullToEmpty(null)).isEmpty();
		assertThat(Lists.nullToEmpty(List.of())).isEmpty();
		assertThat(Lists.nullToEmpty(List.of(ELEMENTS))).containsExactly(ELEMENTS);
	}

	@Test
	void testNullToDefault() {
		assertThat(Lists.nullToDefault(null, List.of(0))).containsExactly(0);
		assertThat(Lists.nullToDefault(List.of(), List.of(0))).isEmpty();
		assertThat(Lists.nullToDefault(List.of(ELEMENTS), List.of(0))).containsExactly(ELEMENTS);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.nullToDefault(List.of(ELEMENTS), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Lists.emptyToNull((List<Integer>) null)).isNull();
		assertThat(Lists.emptyToNull(List.of())).isNull();
		assertThat(Lists.emptyToNull(List.of(ELEMENTS))).containsExactly(ELEMENTS);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Lists.emptyToDefault(null, List.of(0))).isNull();
		assertThat(Lists.emptyToDefault(List.of(), List.of(0))).containsExactly(0);
		assertThat(Lists.emptyToDefault(List.of(ELEMENTS), List.of(0))).containsExactly(ELEMENTS);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Lists.emptyToDefault(List.of(ELEMENTS), List.of()));
	}

	@Test
	void testConcat() {
		assertThat(Lists.concat()).isEmpty();
		assertThat(Lists.concat(List.of(ELEMENTS[0]))).containsExactly(ELEMENTS[0]);
		assertThat(Lists.concat(List.of(ELEMENTS[0]), List.of(ELEMENTS[1]), List.of(ELEMENTS[2]))).containsExactly(ELEMENTS);
		assertThat(Lists.concat(List.of(List.of(ELEMENTS[0]), List.of(ELEMENTS[1]), List.of(ELEMENTS[2])))).containsExactly(ELEMENTS);
	}

	@Test
	void testConcatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.concat((List<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.concat((List<Integer>) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.concat((List<List<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.concat(Collections.singletonList(null)));
	}

	@Test
	void testJoin() {
		assertThat(Lists.join(ObjectArrays.empty(Integer.class), List.of(ELEMENTS[0]), List.of(ELEMENTS[1]), List.of(ELEMENTS[2]))).containsExactly(ELEMENTS);
		assertThat(Lists.join(ObjectArrays.singleton(0))).isEmpty();
		assertThat(Lists.join(ObjectArrays.singleton(0), List.of(ELEMENTS[0]))).containsExactly(1);
		assertThat(Lists.join(ObjectArrays.singleton(0), List.of(ELEMENTS[0]), List.of(ELEMENTS[1]), List.of(ELEMENTS[2]))).containsExactly(ELEMENTS[0], 0, ELEMENTS[1], 0, ELEMENTS[2]);
		assertThat(Lists.join(ObjectArrays.singleton(0), List.of(List.of(ELEMENTS[0]), List.of(ELEMENTS[1]), List.of(ELEMENTS[2])))).containsExactly(ELEMENTS[0], 0, ELEMENTS[1], 0, ELEMENTS[2]);
	}

	@Test
	void testJoinInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.join(null, List.of(ELEMENTS)));
		assertThatNullPointerException().isThrownBy(() -> Lists.join(ObjectArrays.singleton(0), (List<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.join(ObjectArrays.singleton(0), (List<Integer>) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.join(ObjectArrays.singleton(0), (List<List<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Lists.join(ObjectArrays.singleton(0), Collections.singletonList(null)));
	}

	@Test
	void testGetOptionalFirst() {
		assertThat(Lists.getOptionalFirst(List.of()).isEmpty()).isTrue();
		assertThat(Lists.getOptionalFirst(Collections.singletonList(null)).get()).isNull();
		assertThat(Lists.getOptionalFirst(new LinkedList<>(List.of(ELEMENTS))).get()).isEqualTo(ELEMENTS[0]);
		assertThat(Lists.getOptionalFirst(new ArrayList<>(List.of(ELEMENTS))).get()).isEqualTo(ELEMENTS[0]);
	}

	@Test
	void testGetOptionalFirstInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.getOptionalFirst(null));
	}

	@Test
	void testGetOptionalLast() {
		assertThat(Lists.getOptionalLast(List.of()).isEmpty()).isTrue();
		assertThat(Lists.getOptionalLast(Collections.singletonList(null)).get()).isNull();
		assertThat(Lists.getOptionalLast(new LinkedList<>(List.of(ELEMENTS))).get()).isEqualTo(ELEMENTS[2]);
		assertThat(Lists.getOptionalLast(new ArrayList<>(List.of(ELEMENTS))).get()).isEqualTo(ELEMENTS[2]);
	}

	@Test
	void testGetOptionalLastInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Lists.getOptionalLast(null));
	}
}