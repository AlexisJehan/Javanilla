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
package com.github.alexisjehan.javanilla.security.cert;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPathBuilder;

/**
 * <p>A {@link CertPathBuilder} factory to get standard instances without throwing checked exceptions.</p>
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/cert/CertPathBuilder.html">https://docs.oracle.com/javase/10/docs/api/java/security/cert/CertPathBuilder.html</a>
 * @since 1.8.0
 */
public final class StandardCertPathBuilders {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.8.0
	 */
	private StandardCertPathBuilders() {
		// Not available
	}

	/**
	 * <p>Get a new "PKIX" {@link CertPathBuilder} instance.</p>
	 * @return a "PKIX" {@link CertPathBuilder} instance
	 * @since 1.8.0
	 */
	public static CertPathBuilder getPkixInstance() {
		return getInstance("PKIX");
	}

	/**
	 * <p>Get a new {@link CertPathBuilder} instance without throwing {@link NoSuchAlgorithmException}.</p>
	 * @param algorithm the {@link CertPathBuilder} algorithm
	 * @return a {@link CertPathBuilder} instance of the provided algorithm
	 * @since 1.8.0
	 */
	private static CertPathBuilder getInstance(final String algorithm) {
		try {
			return CertPathBuilder.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}