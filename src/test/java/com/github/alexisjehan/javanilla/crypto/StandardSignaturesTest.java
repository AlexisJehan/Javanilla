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
package com.github.alexisjehan.javanilla.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Deprecated
final class StandardSignaturesTest {

	@Test
	void testGetSha1WithDsaInstance() {
		assertThat(StandardSignatures.getSha1WithDsaInstance().getAlgorithm()).isEqualTo("SHA1withDSA");
	}

	@Test
	void testGetSha256WithDsaInstance() {
		assertThat(StandardSignatures.getSha256WithDsaInstance().getAlgorithm()).isEqualTo("SHA256withDSA");
	}

	@Test
	void testGetSha1WithRsaInstance() {
		assertThat(StandardSignatures.getSha1WithRsaInstance().getAlgorithm()).isEqualTo("SHA1withRSA");
	}

	@Test
	void testGetSha256WithRsaInstance() {
		assertThat(StandardSignatures.getSha256WithRsaInstance().getAlgorithm()).isEqualTo("SHA256withRSA");
	}
}