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
package com.github.alexisjehan.javanilla.misc.distance;

import com.github.alexisjehan.javanilla.lang.Strings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class EditDistancesTest {

	@Test
	void testCalculateLcs() {
		final var editDistance = EditDistances.LCS;
		assertThat(editDistance.calculate("a", "a")).isEqualTo(1.0d);
		assertThat(editDistance.calculate("ab", Strings.EMPTY)).isZero();
		assertThat(editDistance.calculate(Strings.EMPTY, "ab")).isZero();
		assertThat(editDistance.calculate("ab", "abc")).isEqualTo(2.0d);
		assertThat(editDistance.calculate("ab", "a")).isEqualTo(1.0d);
		assertThat(editDistance.calculate("ab", "ac")).isEqualTo(1.0d);
		assertThat(editDistance.calculate("foo", "bar")).isZero();
		assertThat(editDistance.calculate("The quick brown fox jumps over the lazy dog", "The five boxing wizards jump quickly")).isEqualTo(18.0d);
	}

	@Test
	void testCalculateHamming() {
		final var editDistance = EditDistances.HAMMING;
		assertThat(editDistance.calculate("a", "a")).isZero();
		assertThat(editDistance.calculate("ab", "ac")).isEqualTo(1.0d);
		assertThat(editDistance.calculate("foo", "bar")).isEqualTo(3.0d);
	}

	@Test
	void testCalculateHammingInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> EditDistances.HAMMING.calculate("ab", "a"));
	}

	@Test
	void testCalculateInvalid() {
		assertThatNullPointerException().isThrownBy(() -> EditDistances.LCS.calculate(null, "bar"));
		assertThatNullPointerException().isThrownBy(() -> EditDistances.LCS.calculate("foo", null));
	}
}