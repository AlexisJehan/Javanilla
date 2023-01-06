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
package com.github.alexisjehan.javanilla.standard.crypto;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Deprecated
final class StandardCiphersTest {

	@Test
	void testGetAesCbcNoPaddingInstance() {
		assertThat(StandardCiphers.getAesCbcNoPaddingInstance().getAlgorithm()).isEqualTo("AES/CBC/NoPadding");
	}

	@Test
	void testGetAesCbcPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getAesCbcPkcs5PaddingInstance().getAlgorithm()).isEqualTo("AES/CBC/PKCS5Padding");
	}

	@Test
	void testGetAesEcbNoPaddingInstance() {
		assertThat(StandardCiphers.getAesEcbNoPaddingInstance().getAlgorithm()).isEqualTo("AES/ECB/NoPadding");
	}

	@Test
	void testGetAesEcbPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getAesEcbPkcs5PaddingInstance().getAlgorithm()).isEqualTo("AES/ECB/PKCS5Padding");
	}

	@Test
	void testGetAesGcmNoPaddingInstance() {
		assertThat(StandardCiphers.getAesGcmNoPaddingInstance().getAlgorithm()).isEqualTo("AES/GCM/NoPadding");
	}

	@Test
	void testGetDesCbcNoPaddingInstance() {
		assertThat(StandardCiphers.getDesCbcNoPaddingInstance().getAlgorithm()).isEqualTo("DES/CBC/NoPadding");
	}

	@Test
	void testGetDesCbcPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getDesCbcPkcs5PaddingInstance().getAlgorithm()).isEqualTo("DES/CBC/PKCS5Padding");
	}

	@Test
	void testGetDesEcbNoPaddingInstance() {
		assertThat(StandardCiphers.getDesEcbNoPaddingInstance().getAlgorithm()).isEqualTo("DES/ECB/NoPadding");
	}

	@Test
	void testGetDesEcbPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getDesEcbPkcs5PaddingInstance().getAlgorithm()).isEqualTo("DES/ECB/PKCS5Padding");
	}

	@Test
	void testGetDesedeCbcNoPaddingInstance() {
		assertThat(StandardCiphers.getDesedeCbcNoPaddingInstance().getAlgorithm()).isEqualTo("DESede/CBC/NoPadding");
	}

	@Test
	void testGetDesedeCbcPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getDesedeCbcPkcs5PaddingInstance().getAlgorithm()).isEqualTo("DESede/CBC/PKCS5Padding");
	}

	@Test
	void testGetDesedeEcbNoPaddingInstance() {
		assertThat(StandardCiphers.getDesedeEcbNoPaddingInstance().getAlgorithm()).isEqualTo("DESede/ECB/NoPadding");
	}

	@Test
	void testGetDesedeEcbPkcs5PaddingInstance() {
		assertThat(StandardCiphers.getDesedeEcbPkcs5PaddingInstance().getAlgorithm()).isEqualTo("DESede/ECB/PKCS5Padding");
	}

	@Test
	void testGetRsaEcbPkcs1PaddingInstance() {
		assertThat(StandardCiphers.getRsaEcbPkcs1PaddingInstance().getAlgorithm()).isEqualTo("RSA/ECB/PKCS1Padding");
	}

	@Test
	void testGetRsaEcbOaepWithSha1AndMgf1PaddingInstance() {
		assertThat(StandardCiphers.getRsaEcbOaepWithSha1AndMgf1PaddingInstance().getAlgorithm()).isEqualTo("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
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