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
package com.github.alexisjehan.javanilla.util;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Comparators} unit tests.</p>
 */
final class ComparatorsTest {

	@Test
	void testNumberAware() {
		assertThat(Comparator.<String>naturalOrder().compare("foo1", "foo1")).isEqualTo(Comparators.NUMBER_AWARE.compare("foo1", "foo1"));
		assertThat(Comparator.<String>naturalOrder().compare("foo1", "foo2")).isEqualTo(Comparators.NUMBER_AWARE.compare("foo1", "foo2"));

		// Different result compared to natural order
		assertThat(Comparator.<String>naturalOrder().compare("foo11", "foo2")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo11", "foo2")).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare(null, null)).isEqualTo(0);
		assertThat(Comparators.NUMBER_AWARE.compare(null, "foo1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo2", null)).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare("", "")).isEqualTo(0);
		assertThat(Comparators.NUMBER_AWARE.compare("", "foo1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo2", "")).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo12", "fooabc")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foode", "foo345")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo12", "foo*-+")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo#$", "foo345")).isEqualTo(-1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo0a0", "foo0a1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0a1", "foo0a0")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0*0", "foo0*1")).isEqualTo(-1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo0*1", "foo0*0")).isEqualTo(1);

		assertThat(Comparators.NUMBER_AWARE.compare("foo010", "foo010")).isEqualTo(0);
		assertThat(Comparators.NUMBER_AWARE.compare("foo010a", "foo010")).isEqualTo(1);
		assertThat(Comparators.NUMBER_AWARE.compare("foo010", "foo010a")).isEqualTo(-1);

		// Avoid internal cache
		assertThat(Comparators.NUMBER_AWARE.compare(new String("foo"), new String("foo"))).isEqualTo(0);
		assertThat(Comparators.NUMBER_AWARE.compare(new String("foo010"), new String("foo010"))).isEqualTo(0);
	}

	@Test
	void testNormalize() {
		final var comparator = (Comparator<Integer>) (i1, i2) -> {
			if (i1.equals(i2)) {
				return 0;
			}
			return i1 - i2;
		};
		assertThat(comparator.compare(0, 0)).isEqualTo(0);
		assertThat(comparator.compare(0, 10)).isEqualTo(-10);
		assertThat(comparator.compare(10, 0)).isEqualTo(10);
		final var normalizedComparator = Comparators.normalize(comparator);
		assertThat(normalizedComparator.compare(0, 0)).isEqualTo(0);
		assertThat(normalizedComparator.compare(0, 10)).isEqualTo(-1);
		assertThat(normalizedComparator.compare(10, 0)).isEqualTo(1);
	}

	@Test
	void testNormalizeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparators.normalize(null));
	}
}