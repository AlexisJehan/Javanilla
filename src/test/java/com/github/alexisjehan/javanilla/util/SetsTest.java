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
package com.github.alexisjehan.javanilla.util;

import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class SetsTest {

	private static final Integer[] ELEMENTS = ObjectArrays.of(1, 2, 3);

	@Test
	void testNullToEmptySet() {
		assertThat(Sets.nullToEmpty((Set<Integer>) null)).isEmpty();
		assertThat(Sets.nullToEmpty(Set.of())).isEmpty();
		assertThat(Sets.nullToEmpty(Set.of(ELEMENTS))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testNullToEmptySortedSet() {
		assertThat(Sets.nullToEmpty((SortedSet<Integer>) null)).isEmpty();
		assertThat(Sets.nullToEmpty((SortedSet<Integer>) new TreeSet<>(Set.<Integer>of()))).isEmpty();
		assertThat(Sets.nullToEmpty((SortedSet<Integer>) new TreeSet<>(Set.of(ELEMENTS)))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testNullToEmptyNavigableSet() {
		assertThat(Sets.nullToEmpty(null)).isEmpty();
		assertThat(Sets.nullToEmpty(new TreeSet<>(Set.<Integer>of()))).isEmpty();
		assertThat(Sets.nullToEmpty(new TreeSet<>(Set.of(ELEMENTS)))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testNullToDefault() {
		assertThat(Sets.nullToDefault(null, Set.of(0))).containsExactlyInAnyOrder(0);
		assertThat(Sets.nullToDefault(Set.of(), Set.of(0))).isEmpty();
		assertThat(Sets.nullToDefault(Set.of(ELEMENTS), Set.of(0))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.nullToDefault(Set.of(ELEMENTS), null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Sets.emptyToNull((Set<Integer>) null)).isNull();
		assertThat(Sets.emptyToNull(Set.of())).isNull();
		assertThat(Sets.emptyToNull(Set.of(ELEMENTS))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Sets.emptyToDefault(null, Set.of(0))).isNull();
		assertThat(Sets.emptyToDefault(Set.of(), Set.of(0))).containsExactlyInAnyOrder(0);
		assertThat(Sets.emptyToDefault(Set.of(ELEMENTS), Set.of(0))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Sets.emptyToDefault(Set.of(ELEMENTS), Set.of()));
	}

	@Test
	void testUnify() {
		assertThat(Sets.unify()).isEmpty();
		assertThat(Sets.unify(Set.of(ELEMENTS[0]))).containsExactlyInAnyOrder(ELEMENTS[0]);
		assertThat(Sets.unify(Set.of(ELEMENTS[0], ELEMENTS[1]), Set.of(ELEMENTS[1], ELEMENTS[2]))).containsExactlyInAnyOrder(ELEMENTS);
		assertThat(Sets.unify(Set.of(Set.of(ELEMENTS[0], ELEMENTS[1]), Set.of(ELEMENTS[1], ELEMENTS[2])))).containsExactlyInAnyOrder(ELEMENTS);
	}

	@Test
	void testUnifyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.unify((Set<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.unify((Set<Integer>) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.unify((Collection<Set<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.unify(Collections.singleton(null)));
	}

	@Test
	void testIntersect() {
		assertThat(Sets.intersect()).isEmpty();
		assertThat(Sets.intersect(Set.of(ELEMENTS[0]))).containsExactlyInAnyOrder(ELEMENTS[0]);
		assertThat(Sets.intersect(Set.of(ELEMENTS[0], ELEMENTS[1]), Set.of(ELEMENTS[1], ELEMENTS[2]))).containsExactlyInAnyOrder(ELEMENTS[1]);
		assertThat(Sets.intersect(Set.of(Set.of(ELEMENTS[0], ELEMENTS[1]), Set.of(ELEMENTS[1], ELEMENTS[2])))).containsExactlyInAnyOrder(ELEMENTS[1]);
	}

	@Test
	void testIntersectInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Set<Integer>[]) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Set<Integer>) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect((Collection<Set<Integer>>) null));
		assertThatNullPointerException().isThrownBy(() -> Sets.intersect(Collections.singleton(null)));
	}

	@Test
	void testOfOrdered() {
		assertThat(Sets.ofOrdered()).isEmpty();
		assertThat(Sets.ofOrdered(ELEMENTS[0])).containsExactly(ELEMENTS[0]);
		assertThat(Sets.ofOrdered(ELEMENTS)).containsExactly(ELEMENTS);
	}

	@Test
	void testOfOrderedInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Sets.ofOrdered((Integer[]) null));
	}
}