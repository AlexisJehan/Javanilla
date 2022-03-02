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
package com.github.alexisjehan.javanilla.standard.security.cert;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CollectionCertStoreParameters;

/**
 * <p>A {@link CertStore} factory to get standard instances without throwing checked exceptions.</p>
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/cert/CertStore.html">https://docs.oracle.com/javase/10/docs/api/java/security/cert/CertStore.html</a>
 * @since 1.6.0
 */
public final class StandardCertStores {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.6.0
	 */
	private StandardCertStores() {
		// Not available
	}

	/**
	 * <p>Get a new "Collection" {@link CertStore} instance.</p>
	 * @param params {@link CertStore} parameters
	 * @return a "Collection" {@link CertStore} instance of provided parameters
	 * @throws NullPointerException if {@link CertStore} parameters are {@code null}
	 * @since 1.6.0
	 */
	public static CertStore getInstance(final CollectionCertStoreParameters params) {
		Ensure.notNull("params", params);
		return getInstance("Collection", params);
	}

	/**
	 * <p>Get a new {@link CertStore} instance without throwing {@link InvalidAlgorithmParameterException} or
	 * {@link NoSuchAlgorithmException}.</p>
	 * @param type the {@link CertStore} type
	 * @param params {@link CertStore} parameters
	 * @return a {@link CertStore} instance of provided type and parameters
	 * @since 1.6.0
	 */
	private static CertStore getInstance(final String type, final CertStoreParameters params) {
		try {
			return CertStore.getInstance(type, params);
		} catch (final InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}