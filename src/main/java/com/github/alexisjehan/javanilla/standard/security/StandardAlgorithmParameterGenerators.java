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
package com.github.alexisjehan.javanilla.standard.security;

import java.security.AlgorithmParameterGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * <p>A {@link AlgorithmParameterGenerator} factory to get standard instances without throwing checked exceptions.</p>
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/AlgorithmParameterGenerator.html">https://docs.oracle.com/javase/10/docs/api/java/security/AlgorithmParameterGenerator.html</a>
 * @since 1.6.0
 */
public final class StandardAlgorithmParameterGenerators {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.6.0
	 */
	private StandardAlgorithmParameterGenerators() {
		// Not available
	}

	/**
	 * <p>Get a new "DiffieHellman" {@link AlgorithmParameterGenerator} instance.</p>
	 * @return a "DiffieHellman" {@link AlgorithmParameterGenerator} instance
	 * @since 1.6.0
	 */
	public static AlgorithmParameterGenerator getDiffieHellmanInstance() {
		return getInstance("DiffieHellman");
	}

	/**
	 * <p>Get a new "DSA" {@link AlgorithmParameterGenerator} instance.</p>
	 * @return a "DSA" {@link AlgorithmParameterGenerator} instance
	 * @since 1.6.0
	 */
	public static AlgorithmParameterGenerator getDsaInstance() {
		return getInstance("DSA");
	}

	/**
	 * <p>Get a new {@link AlgorithmParameterGenerator} instance without throwing {@link NoSuchAlgorithmException}.</p>
	 * @param algorithm the {@link AlgorithmParameterGenerator} algorithm
	 * @return a {@link AlgorithmParameterGenerator} instance of the provided algorithm
	 * @since 1.6.0
	 */
	private static AlgorithmParameterGenerator getInstance(final String algorithm) {
		try {
			return AlgorithmParameterGenerator.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}