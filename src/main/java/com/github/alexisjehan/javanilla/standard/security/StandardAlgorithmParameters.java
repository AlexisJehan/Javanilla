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
package com.github.alexisjehan.javanilla.standard.security;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

/**
 * <p>A {@link AlgorithmParameters} factory to get standard instances without throwing checked exceptions.</p>
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/AlgorithmParameters.html">https://docs.oracle.com/javase/10/docs/api/java/security/AlgorithmParameters.html</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.security.StandardAlgorithmParameters} instead
 * @since 1.6.0
 */
@Deprecated(since = "1.8.0")
public final class StandardAlgorithmParameters {

	/**
	 * <p>Constructor.</p>
	 * @since 1.6.0
	 */
	private StandardAlgorithmParameters() {}

	/**
	 * <p>Get a new "AES" {@link AlgorithmParameters} instance.</p>
	 * @return an "AES" {@link AlgorithmParameters} instance
	 * @since 1.6.0
	 */
	public static AlgorithmParameters getAesInstance() {
		return getInstance("AES");
	}

	/**
	 * <p>Get a new "DES" {@link AlgorithmParameters} instance.</p>
	 * @return a "DES" {@link AlgorithmParameters} instance
	 * @since 1.6.0
	 */
	public static AlgorithmParameters getDesInstance() {
		return getInstance("DES");
	}

	/**
	 * <p>Get a new "DESede" {@link AlgorithmParameters} instance.</p>
	 * @return a "DESede" {@link AlgorithmParameters} instance
	 * @since 1.6.0
	 */
	public static AlgorithmParameters getDesedeInstance() {
		return getInstance("DESede");
	}

	/**
	 * <p>Get a new "DiffieHellman" {@link AlgorithmParameters} instance.</p>
	 * @return a "DiffieHellman" {@link AlgorithmParameters} instance
	 * @since 1.6.0
	 */
	public static AlgorithmParameters getDiffieHellmanInstance() {
		return getInstance("DiffieHellman");
	}

	/**
	 * <p>Get a new "DSA" {@link AlgorithmParameters} instance.</p>
	 * @return a "DSA" {@link AlgorithmParameters} instance
	 * @since 1.6.0
	 */
	public static AlgorithmParameters getDsaInstance() {
		return getInstance("DSA");
	}

	/**
	 * <p>Get a new {@link AlgorithmParameters} instance without throwing {@link NoSuchAlgorithmException}.</p>
	 * @param algorithm the {@link AlgorithmParameters} algorithm
	 * @return a {@link AlgorithmParameters} instance of the provided algorithm
	 * @since 1.6.0
	 */
	private static AlgorithmParameters getInstance(final String algorithm) {
		try {
			return AlgorithmParameters.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}