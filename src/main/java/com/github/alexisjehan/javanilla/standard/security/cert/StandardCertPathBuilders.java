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

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPathBuilder;

/**
 * A {@link CertPathBuilder} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/cert/CertPathBuilder.html">https://docs.oracle.com/javase/10/docs/api/java/security/cert/CertPathBuilder.html</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.security.cert.StandardCertPathBuilders} instead
 * @since 1.6.0
 */
@Deprecated(since = "1.8.0")
public final class StandardCertPathBuilders {

	/**
	 * Constructor.
	 * @since 1.6.0
	 */
	private StandardCertPathBuilders() {}

	/**
	 * Get a new "PKIX" {@link CertPathBuilder} instance.
	 * @return a "PKIX" {@link CertPathBuilder} instance
	 * @since 1.6.0
	 */
	public static CertPathBuilder getPkixInstance() {
		return getInstance("PKIX");
	}

	/**
	 * Get a new {@link CertPathBuilder} instance without throwing {@link NoSuchAlgorithmException}.
	 * @param algorithm the {@link CertPathBuilder} algorithm
	 * @return a {@link CertPathBuilder} instance of the provided algorithm
	 * @since 1.6.0
	 */
	private static CertPathBuilder getInstance(final String algorithm) {
		try {
			return CertPathBuilder.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}