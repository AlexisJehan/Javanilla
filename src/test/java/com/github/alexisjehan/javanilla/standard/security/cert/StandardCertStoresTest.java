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
package com.github.alexisjehan.javanilla.standard.security.cert;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CollectionCertStoreParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Deprecated
final class StandardCertStoresTest {

	@Test
	void testGetInstanceCollection() {
		assertThat(StandardCertStores.getInstance(new CollectionCertStoreParameters()).getType()).isEqualTo("Collection");
	}

	@Test
	void testGetInstanceUnreachable() throws NoSuchMethodException {
		final var method = StandardCertStores.class.getDeclaredMethod("getInstance", String.class, CertStoreParameters.class);
		method.setAccessible(true);
		assertThatExceptionOfType(InvocationTargetException.class)
				.isThrownBy(() -> method.invoke(null, "?", null))
				.withCauseExactlyInstanceOf(AssertionError.class)
				.withRootCauseExactlyInstanceOf(NoSuchAlgorithmException.class);
	}
}