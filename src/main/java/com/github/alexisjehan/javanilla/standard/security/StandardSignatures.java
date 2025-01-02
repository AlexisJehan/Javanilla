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
package com.github.alexisjehan.javanilla.standard.security;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * A {@link Signature} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/Signature.html">https://docs.oracle.com/javase/10/docs/api/java/security/Signature.html</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.security.StandardSignatures} instead
 * @since 1.6.0
 */
@Deprecated(since = "1.8.0")
public final class StandardSignatures {

	/**
	 * Constructor.
	 * @since 1.6.0
	 */
	private StandardSignatures() {}

	/**
	 * Get a new "SHA1withDSA" {@link Signature} instance.
	 * @return a "SHA1withDSA" {@link Signature} instance
	 * @since 1.6.0
	 */
	public static Signature getSha1WithDsaInstance() {
		return getInstance("SHA1withDSA");
	}

	/**
	 * Get a new "SHA256withDSA" {@link Signature} instance.
	 * @return a "SHA256withDSA" {@link Signature} instance
	 * @since 1.6.0
	 */
	public static Signature getSha256WithDsaInstance() {
		return getInstance("SHA256withDSA");
	}

	/**
	 * Get a new "SHA1withRSA" {@link Signature} instance.
	 * @return a "SHA1withRSA" {@link Signature} instance
	 * @since 1.6.0
	 */
	public static Signature getSha1WithRsaInstance() {
		return getInstance("SHA1withRSA");
	}

	/**
	 * Get a new "SHA256withRSA" {@link Signature} instance.
	 * @return a "SHA256withRSA" {@link Signature} instance
	 * @since 1.6.0
	 */
	public static Signature getSha256WithRsaInstance() {
		return getInstance("SHA256withRSA");
	}

	/**
	 * Get a new {@link Signature} instance without throwing {@link NoSuchAlgorithmException}.
	 * @param algorithm the {@link Signature} algorithm
	 * @return a {@link Signature} instance of the provided algorithm
	 * @since 1.6.0
	 */
	private static Signature getInstance(final String algorithm) {
		try {
			return Signature.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}