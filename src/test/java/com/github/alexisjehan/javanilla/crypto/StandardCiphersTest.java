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
package com.github.alexisjehan.javanilla.crypto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link StandardCiphers} unit tests.</p>
 */
@SuppressWarnings("deprecation")
final class StandardCiphersTest {

	@Test
	void testGetAesCbcInstance() {
		assertThat(StandardCiphers.getAesCbcInstance().getAlgorithm()).isEqualTo("AES/CBC/NoPadding");
	}

	@Test
	void testGetAesCbcPkcs5Instance() {
		assertThat(StandardCiphers.getAesCbcPkcs5Instance().getAlgorithm()).isEqualTo("AES/CBC/PKCS5Padding");
	}

	@Test
	void testGetAesEcbInstance() {
		assertThat(StandardCiphers.getAesEcbInstance().getAlgorithm()).isEqualTo("AES/ECB/NoPadding");
	}

	@Test
	void testGetAesEcbPkcs5Instance() {
		assertThat(StandardCiphers.getAesEcbPkcs5Instance().getAlgorithm()).isEqualTo("AES/ECB/PKCS5Padding");
	}

	@Test
	void testGetAesGcmInstance() {
		assertThat(StandardCiphers.getAesGcmInstance().getAlgorithm()).isEqualTo("AES/GCM/NoPadding");
	}

	@Test
	void testGetDesCbcInstance() {
		assertThat(StandardCiphers.getDesCbcInstance().getAlgorithm()).isEqualTo("DES/CBC/NoPadding");
	}

	@Test
	void testGetDesCbcPkcs5Instance() {
		assertThat(StandardCiphers.getDesCbcPkcs5Instance().getAlgorithm()).isEqualTo("DES/CBC/PKCS5Padding");
	}

	@Test
	void testGetDesEcbInstance() {
		assertThat(StandardCiphers.getDesEcbInstance().getAlgorithm()).isEqualTo("DES/ECB/NoPadding");
	}

	@Test
	void testGetDesEcbPkcs5Instance() {
		assertThat(StandardCiphers.getDesEcbPkcs5Instance().getAlgorithm()).isEqualTo("DES/ECB/PKCS5Padding");
	}

	@Test
	void testGetTripleDesCbcInstance() {
		assertThat(StandardCiphers.getTripleDesCbcInstance().getAlgorithm()).isEqualTo("DESede/CBC/NoPadding");
	}

	@Test
	void testGetTripleDesCbcPkcs5Instance() {
		assertThat(StandardCiphers.getTripleDesCbcPkcs5Instance().getAlgorithm()).isEqualTo("DESede/CBC/PKCS5Padding");
	}

	@Test
	void testGetTripleDesEcbInstance() {
		assertThat(StandardCiphers.getTripleDesEcbInstance().getAlgorithm()).isEqualTo("DESede/ECB/NoPadding");
	}

	@Test
	void testGetTripleDesEcbPkcs5Instance() {
		assertThat(StandardCiphers.getTripleDesEcbPkcs5Instance().getAlgorithm()).isEqualTo("DESede/ECB/PKCS5Padding");
	}

	@Test
	void testGetRsaEcbPkcs1Instance() {
		assertThat(StandardCiphers.getRsaEcbPkcs1Instance().getAlgorithm()).isEqualTo("RSA/ECB/PKCS1Padding");
	}

	@Test
	void testGetRsaEcbOaepSha1AndMgf1Instance() {
		assertThat(StandardCiphers.getRsaEcbOaepSha1AndMgf1Instance().getAlgorithm()).isEqualTo("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
	}

	@Test
	void testGetRsaEcbOaepSha256AndMgf1Instance() {
		assertThat(StandardCiphers.getRsaEcbOaepSha256AndMgf1Instance().getAlgorithm()).isEqualTo("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
	}
}