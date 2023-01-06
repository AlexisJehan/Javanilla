/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

final class StandardCiphersTest {

	@Test
	@Deprecated
	void testGetAesCbcInstance() {
		assertThat(StandardCiphers.getAesCbcInstance().getAlgorithm()).isEqualTo("AES/CBC/NoPadding");
	}

	@Test
	void testGetAesCbcNoPaddingInstance() {
		assertThat(StandardCiphers.getAesCbcNoPaddingInstance().getAlgorithm()).isEqualTo("AES/CBC/NoPadding");
	}

	@Test
	@Deprecated
	void testGetAesCbcPkcs5Instance() {
		assertThat(StandardCiphers.getAesCbcPkcs5Instance().getAlgorithm()).isEqualTo("AES/CBC/PKCS5Padding");
	}

	@Test
	void testGetAesCbcPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getAesCbcPkcs5PaddingInstance().getAlgorithm()).isEqualTo("AES/CBC/PKCS5Padding");
	}

	@Test
	@Deprecated
	void testGetAesEcbInstance() {
		assertThat(StandardCiphers.getAesEcbInstance().getAlgorithm()).isEqualTo("AES/ECB/NoPadding");
	}

	@Test
	void testGetAesEcbNoPaddingInstance() {
		assertThat(StandardCiphers.getAesEcbNoPaddingInstance().getAlgorithm()).isEqualTo("AES/ECB/NoPadding");
	}

	@Test
	@Deprecated
	void testGetAesEcbPkcs5Instance() {
		assertThat(StandardCiphers.getAesEcbPkcs5Instance().getAlgorithm()).isEqualTo("AES/ECB/PKCS5Padding");
	}

	@Test
	void testGetAesEcbPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getAesEcbPkcs5PaddingInstance().getAlgorithm()).isEqualTo("AES/ECB/PKCS5Padding");
	}

	@Test
	@Deprecated
	void testGetAesGcmInstance() {
		assertThat(StandardCiphers.getAesGcmInstance().getAlgorithm()).isEqualTo("AES/GCM/NoPadding");
	}

	@Test
	void testGetAesGcmNoPaddingInstance() {
		assertThat(StandardCiphers.getAesGcmNoPaddingInstance().getAlgorithm()).isEqualTo("AES/GCM/NoPadding");
	}

	@Test
	@Deprecated
	void testGetDesCbcInstance() {
		assertThat(StandardCiphers.getDesCbcInstance().getAlgorithm()).isEqualTo("DES/CBC/NoPadding");
	}

	@Test
	void testGetDesCbcNoPaddingInstance() {
		assertThat(StandardCiphers.getDesCbcNoPaddingInstance().getAlgorithm()).isEqualTo("DES/CBC/NoPadding");
	}

	@Test
	@Deprecated
	void testGetDesCbcPkcs5Instance() {
		assertThat(StandardCiphers.getDesCbcPkcs5Instance().getAlgorithm()).isEqualTo("DES/CBC/PKCS5Padding");
	}

	@Test
	void testGetDesCbcPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getDesCbcPkcs5PaddingInstance().getAlgorithm()).isEqualTo("DES/CBC/PKCS5Padding");
	}

	@Test
	@Deprecated
	void testGetDesEcbInstance() {
		assertThat(StandardCiphers.getDesEcbInstance().getAlgorithm()).isEqualTo("DES/ECB/NoPadding");
	}

	@Test
	void testGetDesEcbNoPaddingInstance() {
		assertThat(StandardCiphers.getDesEcbNoPaddingInstance().getAlgorithm()).isEqualTo("DES/ECB/NoPadding");
	}

	@Test
	@Deprecated
	void testGetDesEcbPkcs5Instance() {
		assertThat(StandardCiphers.getDesEcbPkcs5Instance().getAlgorithm()).isEqualTo("DES/ECB/PKCS5Padding");
	}

	@Test
	void testGetDesEcbPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getDesEcbPkcs5PaddingInstance().getAlgorithm()).isEqualTo("DES/ECB/PKCS5Padding");
	}

	@Test
	@Deprecated
	void testGetTripleDesCbcInstance() {
		assertThat(StandardCiphers.getTripleDesCbcInstance().getAlgorithm()).isEqualTo("DESede/CBC/NoPadding");
	}

	@Test
	void testGetDesedeCbcNoPaddingInstance() {
		assertThat(StandardCiphers.getDesedeCbcNoPaddingInstance().getAlgorithm()).isEqualTo("DESede/CBC/NoPadding");
	}

	@Test
	@Deprecated
	void testGetTripleDesCbcPkcs5Instance() {
		assertThat(StandardCiphers.getTripleDesCbcPkcs5Instance().getAlgorithm()).isEqualTo("DESede/CBC/PKCS5Padding");
	}

	@Test
	void testGetDesedeCbcPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getDesedeCbcPkcs5PaddingInstance().getAlgorithm()).isEqualTo("DESede/CBC/PKCS5Padding");
	}

	@Test
	@Deprecated
	void testGetTripleDesEcbInstance() {
		assertThat(StandardCiphers.getTripleDesEcbInstance().getAlgorithm()).isEqualTo("DESede/ECB/NoPadding");
	}

	@Test
	void testGetDesedeEcbNoPaddingInstance() {
		assertThat(StandardCiphers.getDesedeEcbNoPaddingInstance().getAlgorithm()).isEqualTo("DESede/ECB/NoPadding");
	}

	@Test
	@Deprecated
	void testGetTripleDesEcbPkcs5Instance() {
		assertThat(StandardCiphers.getTripleDesEcbPkcs5Instance().getAlgorithm()).isEqualTo("DESede/ECB/PKCS5Padding");
	}

	@Test
	void testGetDesedeEcbPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getDesedeEcbPkcs5PaddingInstance().getAlgorithm()).isEqualTo("DESede/ECB/PKCS5Padding");
	}

	@Test
	@Deprecated
	void testGetRsaEcbPkcs1Instance() {
		assertThat(StandardCiphers.getRsaEcbPkcs1Instance().getAlgorithm()).isEqualTo("RSA/ECB/PKCS1Padding");
	}

	@Test
	void testGetRsaEcbPkcs1PaddingInstance() {
		assertThat(StandardCiphers.getRsaEcbPkcs1PaddingInstance().getAlgorithm()).isEqualTo("RSA/ECB/PKCS1Padding");
	}

	@Test
	@Deprecated
	void testGetRsaEcbOaepSha1AndMgf1Instance() {
		assertThat(StandardCiphers.getRsaEcbOaepSha1AndMgf1Instance().getAlgorithm()).isEqualTo("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
	}

	@Test
	void testGetRsaEcbOaepWithSha1AndMgf1PaddingInstance() {
		assertThat(StandardCiphers.getRsaEcbOaepWithSha1AndMgf1PaddingInstance().getAlgorithm()).isEqualTo("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
	}

	@Test
	@Deprecated
	void testGetRsaEcbOaepSha256AndMgf1Instance() {
		assertThat(StandardCiphers.getRsaEcbOaepSha256AndMgf1Instance().getAlgorithm()).isEqualTo("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
	}

	@Test
	void testGetRsaEcbOaepWithSha256AndMgf1PaddingInstance() {
		assertThat(StandardCiphers.getRsaEcbOaepWithSha256AndMgf1PaddingInstance().getAlgorithm()).isEqualTo("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
	}

	@Test
	void testGetInstanceUnreachable() throws NoSuchMethodException {
		final var method = StandardCiphers.class.getDeclaredMethod("getInstance", String.class);
		method.setAccessible(true);
		assertThatExceptionOfType(InvocationTargetException.class)
				.isThrownBy(() -> method.invoke(null, "?"))
				.withCauseExactlyInstanceOf(AssertionError.class)
				.withRootCauseExactlyInstanceOf(NoSuchAlgorithmException.class);
	}
}