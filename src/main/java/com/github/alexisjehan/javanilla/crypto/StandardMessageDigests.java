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
package com.github.alexisjehan.javanilla.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>A {@link MessageDigest} factory to get standard instances without throwing checked exceptions.</p>
 * @since 1.0.0
 */
public final class StandardMessageDigests {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private StandardMessageDigests() {
		// Not available
	}

	/**
	 * <p>Get a new "MD5" {@link MessageDigest} instance.</p>
	 * @return a "MD5" {@link MessageDigest} instance
	 * @since 1.0.0
	 */
	public static MessageDigest getMd5Instance() {
		return getInstance("MD5");
	}

	/**
	 * <p>Get a new "SHA-1" {@link MessageDigest} instance.</p>
	 * @return a "SHA-1" {@link MessageDigest} instance
	 * @since 1.0.0
	 */
	public static MessageDigest getSha1Instance() {
		return getInstance("SHA-1");
	}

	/**
	 * <p>Get a new "SHA-256" {@link MessageDigest} instance.</p>
	 * @return a "SHA-256" {@link MessageDigest} instance
	 * @since 1.0.0
	 */
	public static MessageDigest getSha256Instance() {
		return getInstance("SHA-256");
	}

	/**
	 * <p>Get a new {@link MessageDigest} instance without throwing {@link NoSuchAlgorithmException}.</p>
	 * @param algorithm the {@link MessageDigest} algorithm
	 * @return a {@link MessageDigest} instance of the provided algorithm
	 * @since 1.0.0
	 */
	private static MessageDigest getInstance(final String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}