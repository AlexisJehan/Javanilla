/*
 * MIT License
 *
 * Copyright (c) 2018-2021 Alexis Jehan
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

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;

/**
 * <p>A {@link Mac} factory to get standard instances without throwing checked exceptions.</p>
 * @since 1.1.0
 */
public final class StandardMacs {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.1.0
	 */
	private StandardMacs() {
		// Not available
	}

	/**
	 * <p>Get a new "HmacMD5" {@link Mac} instance.</p>
	 * @return a "HmacMD5" {@link Mac} instance
	 * @since 1.1.0
	 */
	public static Mac getHmacMd5Instance() {
		return getInstance("HmacMD5");
	}

	/**
	 * <p>Get a new "HmacSHA1" {@link Mac} instance.</p>
	 * @return a "HmacSHA1" {@link Mac} instance
	 * @since 1.1.0
	 */
	public static Mac getHmacSha1Instance() {
		return getInstance("HmacSHA1");
	}

	/**
	 * <p>Get a new "HmacSHA256" {@link Mac} instance.</p>
	 * @return a "HmacSHA256" {@link Mac} instance
	 * @since 1.1.0
	 */
	public static Mac getHmacSha256Instance() {
		return getInstance("HmacSHA256");
	}

	/**
	 * <p>Get a new {@link Mac} instance without throwing {@link NoSuchAlgorithmException}.</p>
	 * @param algorithm the {@link Mac} algorithm
	 * @return a {@link Mac} instance of the provided algorithm
	 * @since 1.1.0
	 */
	private static Mac getInstance(final String algorithm) {
		try {
			return Mac.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}