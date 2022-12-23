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
package com.github.alexisjehan.javanilla.standard.crypto;

import javax.crypto.KeyAgreement;
import java.security.NoSuchAlgorithmException;

/**
 * <p>A {@link KeyAgreement} factory to get standard instances without throwing checked exceptions.</p>
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/javax/crypto/KeyAgreement.html">https://docs.oracle.com/javase/10/docs/api/javax/crypto/KeyAgreement.html</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.crypto.StandardKeyAgreements} instead
 * @since 1.6.0
 */
@Deprecated(since = "1.8.0")
public final class StandardKeyAgreements {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.6.0
	 */
	private StandardKeyAgreements() {
		// Not available
	}

	/**
	 * <p>Get a new "DiffieHellman" {@link KeyAgreement} instance.</p>
	 * @return a "DiffieHellman" {@link KeyAgreement} instance
	 * @since 1.6.0
	 */
	public static KeyAgreement getDiffieHellmanInstance() {
		return getInstance("DiffieHellman");
	}

	/**
	 * <p>Get a new {@link KeyAgreement} instance without throwing {@link NoSuchAlgorithmException}.</p>
	 * @param algorithm the {@link KeyAgreement} algorithm
	 * @return a {@link KeyAgreement} instance of the provided algorithm
	 * @since 1.6.0
	 */
	private static KeyAgreement getInstance(final String algorithm) {
		try {
			return KeyAgreement.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}