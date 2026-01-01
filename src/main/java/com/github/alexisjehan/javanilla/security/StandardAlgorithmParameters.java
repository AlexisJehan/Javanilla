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
package com.github.alexisjehan.javanilla.security;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

/**
 * A {@link AlgorithmParameters} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/AlgorithmParameters.html">https://docs.oracle.com/javase/10/docs/api/java/security/AlgorithmParameters.html</a>
 * @since 1.8.0
 */
public final class StandardAlgorithmParameters {

	/**
	 * Constructor.
	 * @since 1.8.0
	 */
	private StandardAlgorithmParameters() {}

	/**
	 * Get a new "AES" {@link AlgorithmParameters} instance.
	 * @return an "AES" {@link AlgorithmParameters} instance
	 * @since 1.8.0
	 */
	public static AlgorithmParameters getAesInstance() {
		return getInstance("AES");
	}

	/**
	 * Get a new "DES" {@link AlgorithmParameters} instance.
	 * @return a "DES" {@link AlgorithmParameters} instance
	 * @since 1.8.0
	 */
	public static AlgorithmParameters getDesInstance() {
		return getInstance("DES");
	}

	/**
	 * Get a new "DESede" {@link AlgorithmParameters} instance.
	 * @return a "DESede" {@link AlgorithmParameters} instance
	 * @since 1.8.0
	 */
	public static AlgorithmParameters getDesedeInstance() {
		return getInstance("DESede");
	}

	/**
	 * Get a new "DiffieHellman" {@link AlgorithmParameters} instance.
	 * @return a "DiffieHellman" {@link AlgorithmParameters} instance
	 * @since 1.8.0
	 */
	public static AlgorithmParameters getDiffieHellmanInstance() {
		return getInstance("DiffieHellman");
	}

	/**
	 * Get a new "DSA" {@link AlgorithmParameters} instance.
	 * @return a "DSA" {@link AlgorithmParameters} instance
	 * @since 1.8.0
	 */
	public static AlgorithmParameters getDsaInstance() {
		return getInstance("DSA");
	}

	/**
	 * Get a new {@link AlgorithmParameters} instance without throwing {@link NoSuchAlgorithmException}.
	 * @param algorithm the {@link AlgorithmParameters} algorithm
	 * @return a {@link AlgorithmParameters} instance of the provided algorithm
	 * @since 1.8.0
	 */
	private static AlgorithmParameters getInstance(final String algorithm) {
		try {
			return AlgorithmParameters.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}