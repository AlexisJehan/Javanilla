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
package com.github.alexisjehan.javanilla.crypto;

import java.security.KeyFactory;

/**
 * A {@link KeyFactory} factory to get standard instances without throwing checked exceptions.
 * @deprecated since 1.6.0, use {@link com.github.alexisjehan.javanilla.standard.security.StandardKeyFactories} instead
 * @since 1.3.1
 */
@Deprecated(since = "1.6.0")
public final class StandardKeyFactories {

	/**
	 * Constructor.
	 * @since 1.3.1
	 */
	private StandardKeyFactories() {}

	/**
	 * Get a new "DiffieHellman" {@link KeyFactory} instance.
	 * @return a "DiffieHellman" {@link KeyFactory} instance
	 * @since 1.3.1
	 */
	public static KeyFactory getDiffieHellmanInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardKeyFactories.getDiffieHellmanInstance();
	}

	/**
	 * Get a new "DSA" {@link KeyFactory} instance.
	 * @return a "DSA" {@link KeyFactory} instance
	 * @since 1.3.1
	 */
	public static KeyFactory getDsaInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardKeyFactories.getDsaInstance();
	}

	/**
	 * Get a new "RSA" {@link KeyFactory} instance.
	 * @return an "RSA" {@link KeyFactory} instance
	 * @since 1.3.1
	 */
	public static KeyFactory getRsaInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardKeyFactories.getRsaInstance();
	}
}