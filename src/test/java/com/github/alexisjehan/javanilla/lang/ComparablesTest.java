/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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
package com.github.alexisjehan.javanilla.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class ComparablesTest {

	@Test
	void testIsEqualTo() {
		assertThat(Comparables.isEqualTo(1, 0)).isFalse();
		assertThat(Comparables.isEqualTo(1, 1)).isTrue();
	}

	@Test
	void testIsEqualToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparables.isEqualTo(null, 1));
		assertThatNullPointerException().isThrownBy(() -> Comparables.isEqualTo(1, null));
	}

	@Test
	void testIsNotEqualTo() {
		assertThat(Comparables.isNotEqualTo(1, 0)).isTrue();
		assertThat(Comparables.isNotEqualTo(1, 1)).isFalse();
	}

	@Test
	void testIsNotEqualToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparables.isNotEqualTo(null, 1));
		assertThatNullPointerException().isThrownBy(() -> Comparables.isNotEqualTo(1, null));
	}

	@Test
	void testIsLowerThan() {
		assertThat(Comparables.isLowerThan(1, 0)).isFalse();
		assertThat(Comparables.isLowerThan(1, 1)).isFalse();
		assertThat(Comparables.isLowerThan(1, 2)).isTrue();
	}

	@Test
	void testIsLowerThanInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparables.isLowerThan(null, 1));
		assertThatNullPointerException().isThrownBy(() -> Comparables.isLowerThan(1, null));
	}

	@Test
	void testIsLowerThanOrEqualTo() {
		assertThat(Comparables.isLowerThanOrEqualTo(1, 0)).isFalse();
		assertThat(Comparables.isLowerThanOrEqualTo(1, 1)).isTrue();
		assertThat(Comparables.isLowerThanOrEqualTo(1, 2)).isTrue();
	}

	@Test
	void testIsLowerThanOrEqualToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparables.isLowerThanOrEqualTo(null, 1));
		assertThatNullPointerException().isThrownBy(() -> Comparables.isLowerThanOrEqualTo(1, null));
	}

	@Test
	void testIsGreaterThan() {
		assertThat(Comparables.isGreaterThan(1, 0)).isTrue();
		assertThat(Comparables.isGreaterThan(1, 1)).isFalse();
		assertThat(Comparables.isGreaterThan(1, 2)).isFalse();
	}

	@Test
	void testIsGreaterThanInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparables.isGreaterThan(null, 1));
		assertThatNullPointerException().isThrownBy(() -> Comparables.isGreaterThan(1, null));
	}

	@Test
	void testIsGreaterThanOrEqualTo() {
		assertThat(Comparables.isGreaterThanOrEqualTo(1, 0)).isTrue();
		assertThat(Comparables.isGreaterThanOrEqualTo(1, 1)).isTrue();
		assertThat(Comparables.isGreaterThanOrEqualTo(1, 2)).isFalse();
	}

	@Test
	void testIsGreaterThanOrEqualToInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparables.isGreaterThanOrEqualTo(null, 1));
		assertThatNullPointerException().isThrownBy(() -> Comparables.isGreaterThanOrEqualTo(1, null));
	}

	@Test
	void testIsBetween() {
		assertThat(Comparables.isBetween(1, 0, 0)).isFalse();
		assertThat(Comparables.isBetween(1, 0, 1)).isTrue();
		assertThat(Comparables.isBetween(1, 1, 1)).isTrue();
		assertThat(Comparables.isBetween(1, 1, 2)).isTrue();
		assertThat(Comparables.isBetween(1, 2, 2)).isFalse();
	}

	@Test
	void testIsBetweenInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Comparables.isBetween(null, 1, 1));
		assertThatNullPointerException().isThrownBy(() -> Comparables.isBetween(1, null, 1));
		assertThatNullPointerException().isThrownBy(() -> Comparables.isBetween(1, 1, null));
	}
}