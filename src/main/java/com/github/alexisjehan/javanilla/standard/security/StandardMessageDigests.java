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
package com.github.alexisjehan.javanilla.standard.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A {@link MessageDigest} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/MessageDigest.html">https://docs.oracle.com/javase/10/docs/api/java/security/MessageDigest.html</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.security.StandardMessageDigests} instead
 * @since 1.6.0
 */
@Deprecated(since = "1.8.0")
public final class StandardMessageDigests {

	/**
	 * Constructor.
	 * @since 1.6.0
	 */
	private StandardMessageDigests() {}

	/**
	 * Get a new "MD5" {@link MessageDigest} instance.
	 * @return a "MD5" {@link MessageDigest} instance
	 * @since 1.6.0
	 */
	public static MessageDigest getMd5Instance() {
		return getInstance("MD5");
	}

	/**
	 * Get a new "SHA-1" {@link MessageDigest} instance.
	 * @return a "SHA-1" {@link MessageDigest} instance
	 * @since 1.6.0
	 */
	public static MessageDigest getSha1Instance() {
		return getInstance("SHA-1");
	}

	/**
	 * Get a new "SHA-256" {@link MessageDigest} instance.
	 * @return a "SHA-256" {@link MessageDigest} instance
	 * @since 1.6.0
	 */
	public static MessageDigest getSha256Instance() {
		return getInstance("SHA-256");
	}

	/**
	 * Get a new {@link MessageDigest} instance without throwing {@link NoSuchAlgorithmException}.
	 * @param algorithm the {@link MessageDigest} algorithm
	 * @return a {@link MessageDigest} instance of the provided algorithm
	 * @since 1.6.0
	 */
	private static MessageDigest getInstance(final String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}