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
package com.github.alexisjehan.javanilla.net.ssl;

import javax.net.ssl.TrustManagerFactory;
import java.security.NoSuchAlgorithmException;

/**
 * <p>A {@link TrustManagerFactory} factory to get standard instances without throwing checked exceptions.</p>
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/javax/net/ssl/TrustManagerFactory.html">https://docs.oracle.com/javase/10/docs/api/javax/net/ssl/TrustManagerFactory.html</a>
 * @since 1.8.0
 */
public final class StandardTrustManagerFactories {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.8.0
	 */
	private StandardTrustManagerFactories() {
		// Not available
	}

	/**
	 * <p>Get a new "PKIX" {@link TrustManagerFactory} instance.</p>
	 * @return a "PKIX" {@link TrustManagerFactory} instance
	 * @since 1.8.0
	 */
	public static TrustManagerFactory getPkixInstance() {
		return getInstance("PKIX");
	}

	/**
	 * <p>Get a new {@link TrustManagerFactory} instance without throwing {@link NoSuchAlgorithmException}.</p>
	 * @param algorithm the {@link TrustManagerFactory} algorithm
	 * @return a {@link TrustManagerFactory} instance of the provided algorithm
	 * @since 1.8.0
	 */
	private static TrustManagerFactory getInstance(final String algorithm) {
		try {
			return TrustManagerFactory.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}