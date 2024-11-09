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
package com.github.alexisjehan.javanilla.standard.crypto;

import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;

/**
 * A {@link SecretKeyFactory} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/javax/crypto/SecretKeyFactory.html">https://docs.oracle.com/javase/10/docs/api/javax/crypto/SecretKeyFactory.html</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.crypto.StandardSecretKeyFactories} instead
 * @since 1.6.0
 */
@Deprecated(since = "1.8.0")
public final class StandardSecretKeyFactories {

	/**
	 * Constructor.
	 * @since 1.6.0
	 */
	private StandardSecretKeyFactories() {}

	/**
	 * Get a new "DES" {@link SecretKeyFactory} instance.
	 * @return a "DES" {@link SecretKeyFactory} instance
	 * @since 1.6.0
	 */
	public static SecretKeyFactory getDesInstance() {
		return getInstance("DES");
	}

	/**
	 * Get a new "DESede" {@link SecretKeyFactory} instance.
	 * @return a "DESede" {@link SecretKeyFactory} instance
	 * @since 1.6.0
	 */
	public static SecretKeyFactory getDesedeInstance() {
		return getInstance("DESede");
	}

	/**
	 * Get a new {@link SecretKeyFactory} instance without throwing {@link NoSuchAlgorithmException}.
	 * @param algorithm the {@link SecretKeyFactory} algorithm
	 * @return a {@link SecretKeyFactory} instance of the provided algorithm
	 * @since 1.6.0
	 */
	private static SecretKeyFactory getInstance(final String algorithm) {
		try {
			return SecretKeyFactory.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}