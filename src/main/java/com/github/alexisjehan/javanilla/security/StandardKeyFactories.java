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
package com.github.alexisjehan.javanilla.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

/**
 * A {@link KeyFactory} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/KeyFactory.html">https://docs.oracle.com/javase/10/docs/api/java/security/KeyFactory.html</a>
 * @since 1.8.0
 */
public final class StandardKeyFactories {

	/**
	 * Constructor.
	 * @since 1.8.0
	 */
	private StandardKeyFactories() {}

	/**
	 * Get a new "DiffieHellman" {@link KeyFactory} instance.
	 * @return a "DiffieHellman" {@link KeyFactory} instance
	 * @since 1.8.0
	 */
	public static KeyFactory getDiffieHellmanInstance() {
		return getInstance("DiffieHellman");
	}

	/**
	 * Get a new "DSA" {@link KeyFactory} instance.
	 * @return a "DSA" {@link KeyFactory} instance
	 * @since 1.8.0
	 */
	public static KeyFactory getDsaInstance() {
		return getInstance("DSA");
	}

	/**
	 * Get a new "RSA" {@link KeyFactory} instance.
	 * @return an "RSA" {@link KeyFactory} instance
	 * @since 1.8.0
	 */
	public static KeyFactory getRsaInstance() {
		return getInstance("RSA");
	}

	/**
	 * Get a new {@link KeyFactory} instance without throwing {@link NoSuchAlgorithmException}.
	 * @param algorithm the {@link KeyFactory} algorithm
	 * @return a {@link KeyFactory} instance of the provided algorithm
	 * @since 1.8.0
	 */
	private static KeyFactory getInstance(final String algorithm) {
		try {
			return KeyFactory.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}