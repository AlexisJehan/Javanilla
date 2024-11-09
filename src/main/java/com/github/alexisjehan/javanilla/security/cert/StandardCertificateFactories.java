/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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
package com.github.alexisjehan.javanilla.security.cert;

import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * A {@link CertificateFactory} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/cert/CertificateFactory.html">https://docs.oracle.com/javase/10/docs/api/java/security/cert/CertificateFactory.html</a>
 * @since 1.8.0
 */
public final class StandardCertificateFactories {

	/**
	 * Constructor.
	 * @since 1.8.0
	 */
	private StandardCertificateFactories() {}

	/**
	 * Get a new "X.509" {@link CertificateFactory} instance.
	 * @return a "X.509" {@link CertificateFactory} instance
	 * @since 1.8.0
	 */
	public static CertificateFactory getX509Instance() {
		return getInstance("X.509");
	}

	/**
	 * Get a new {@link CertificateFactory} instance without throwing {@link CertificateException}.
	 * @param type the {@link CertificateFactory} type
	 * @return a {@link CertificateFactory} instance of the provided type
	 * @since 1.8.0
	 */
	private static CertificateFactory getInstance(final String type) {
		try {
			return CertificateFactory.getInstance(type);
		} catch (final CertificateException e) {
			throw new AssertionError(e);
		}
	}
}