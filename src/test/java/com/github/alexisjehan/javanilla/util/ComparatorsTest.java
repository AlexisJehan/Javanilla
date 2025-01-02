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

import com.github.alexisjehan.javanilla.lang.Strings;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ComparatorsTest {

	@Test
	void testNumberAware() {
		assertThat(Comparator.<String>naturalOrder().compare("foo1", "foo1")).isEqualTo(Comparators.NUMBER_AWARE.compare("foo1", "foo1"));
		assertThat(Comparator.<String>naturalOrder().compare("foo1", "foo2")).isEqualTo(Comparators.NUMBER_AWARE.compare("foo1", "foo2"));

		// Different from natural order
		assertThat(Comparator.<String>naturalOrder().compare("foo11", "foo2")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo11", "foo2")).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare(null, null)).isZero();
		assertThat(Comparators.NUMBER_AWARE.compare(null, "foo1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo2", null)).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare(Strings.EMPTY, Strings.EMPTY)).isZero();
		assertThat(Comparators.NUMBER_AWARE.compare(Strings.EMPTY, "foo1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo2", Strings.EMPTY)).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo12", "fooabc")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foode", "foo345")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo12", "foo*-+")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo#$", "foo345")).isEqualTo(-1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo0a0", "foo0a1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0a1", "foo0a0")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0*0", "foo0*1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0*1", "foo0*0")).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo010", "foo010")).isZero();
		assertThat(Comparators.NUMBER_AWARE.compare("foo010a", "foo010")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo010", "foo010a")).isEqualTo(-1);

		// Avoid internal cache
		assertThat(Comparators.NUMBER_AWARE.compare(new StringBuilder("foo"), new StringBuilder("foo"))).isZero();
		assertThat(Comparators.NUMBER_AWARE.compare(new StringBuilder("foo010"), new StringBuilder("foo010"))).isZero();
	}

	@Test
	void testNormalize() {
		assertThat((Comparator<Integer>) (i1, i2) -> {
			if (i1.equals(i2)) {
				return 0;
			}
			return i1 - i2;
		}).satisfies(comparator -> {
			assertThat(comparator.compare(0, 0)).isZero();
			assertThat(comparator.compare(0, 10)).isEqualTo(-10);
			assertThat(comparator.compare(10, 0)).isEqualTo(10);
			assertThat(Comparators.normalize(comparator)).satisfies(normalizedComparator -> {
				assertThat(normalizedComparator.compare(0, 0)).isZero();
				assertThat(normalizedComparator.compare(0, 10)).isEqualTo(-1);
				assertThat(normalizedComparator.compare(10, 0)).isEqualTo(1);
			});
		});
	}

	@Test
	void testNormalizeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.normalize(null));
	}
}