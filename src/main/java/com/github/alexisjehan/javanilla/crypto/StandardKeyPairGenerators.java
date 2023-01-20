/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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

import java.security.KeyPairGenerator;

/**
 * <p>A {@link KeyPairGenerator} factory to get standard instances without throwing checked exceptions.</p>
 * @deprecated since 1.6.0, use {@link com.github.alexisjehan.javanilla.standard.security.StandardKeyPairGenerators}
 *             instead
 * @since 1.3.1
 */
@Deprecated(since = "1.6.0")
public final class StandardKeyPairGenerators {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.3.1
	 */
	private StandardKeyPairGenerators() {
		// Not available
	}

	/**
	 * <p>Get a new "DiffieHellman" {@link KeyPairGenerator} instance.</p>
	 * @return a "DiffieHellman" {@link KeyPairGenerator} instance
	 * @since 1.3.1
	 */
	public static KeyPairGenerator getDiffieHellmanInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardKeyPairGenerators.getDiffieHellmanInstance();
	}

	/**
	 * <p>Get a new "DSA" {@link KeyPairGenerator} instance.</p>
	 * @return a "DSA" {@link KeyPairGenerator} instance
	 * @since 1.3.1
	 */
	public static KeyPairGenerator getDsaInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardKeyPairGenerators.getDsaInstance();
	}

	/**
	 * <p>Get a new "RSA" {@link KeyPairGenerator} instance.</p>
	 * @return an "RSA" {@link KeyPairGenerator} instance
	 * @since 1.3.1
	 */
	public static KeyPairGenerator getRsaInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardKeyPairGenerators.getRsaInstance();
	}
}