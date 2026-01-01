/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.crypto;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * A {@link KeyGenerator} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/javax/crypto/KeyGenerator.html">https://docs.oracle.com/javase/10/docs/api/javax/crypto/KeyGenerator.html</a>
 * @since 1.8.0
 */
public final class StandardKeyGenerators {

	/**
	 * Constructor.
	 * @since 1.8.0
	 */
	private StandardKeyGenerators() {}

	/**
	 * Get a new "AES" {@link KeyGenerator} instance.
	 * @return an "AES" {@link KeyGenerator} instance
	 * @since 1.8.0
	 */
	public static KeyGenerator getAesInstance() {
		return getInstance("AES");
	}

	/**
	 * Get a new "DES" {@link KeyGenerator} instance.
	 * @return a "DES" {@link KeyGenerator} instance
	 * @since 1.8.0
	 */
	public static KeyGenerator getDesInstance() {
		return getInstance("DES");
	}

	/**
	 * Get a new "DESede" {@link KeyGenerator} instance.
	 * @return a "DESede" {@link KeyGenerator} instance
	 * @since 1.8.0
	 */
	public static KeyGenerator getDesedeInstance() {
		return getInstance("DESede");
	}

	/**
	 * Get a new "HmacSHA1" {@link KeyGenerator} instance.
	 * @return a "HmacSHA1" {@link KeyGenerator} instance
	 * @since 1.8.0
	 */
	public static KeyGenerator getHmacSha1Instance() {
		return getInstance("HmacSHA1");
	}

	/**
	 * Get a new "HmacSHA256" {@link KeyGenerator} instance.
	 * @return a "HmacSHA256" {@link KeyGenerator} instance
	 * @since 1.8.0
	 */
	public static KeyGenerator getHmacSha256Instance() {
		return getInstance("HmacSHA256");
	}

	/**
	 * Get a new {@link KeyGenerator} instance without throwing {@link NoSuchAlgorithmException}.
	 * @param algorithm the {@link KeyGenerator} algorithm
	 * @return a {@link KeyGenerator} instance of the provided algorithm
	 * @since 1.8.0
	 */
	private static KeyGenerator getInstance(final String algorithm) {
		try {
			return KeyGenerator.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}