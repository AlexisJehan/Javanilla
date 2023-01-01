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
package com.github.alexisjehan.javanilla.security;

import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 * <p>A {@link KeyStore} factory to get standard instances without throwing checked exceptions.</p>
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/java/security/KeyStore.html">https://docs.oracle.com/javase/10/docs/api/java/security/KeyStore.html</a>
 * @since 1.8.0
 */
public final class StandardKeyStores {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.8.0
	 */
	private StandardKeyStores() {
		// Not available
	}

	/**
	 * <p>Get a new "PKCS12" {@link KeyStore} instance.</p>
	 * @return a "PKCS12" {@link KeyStore} instance
	 * @since 1.8.0
	 */
	public static KeyStore getPkcs12Instance() {
		return getInstance("PKCS12");
	}

	/**
	 * <p>Get a new {@link KeyStore} instance without throwing {@link KeyStoreException}.</p>
	 * @param type the {@link KeyStore} type
	 * @return a {@link KeyStore} instance of the provided type
	 * @since 1.8.0
	 */
	private static KeyStore getInstance(final String type) {
		try {
			return KeyStore.getInstance(type);
		} catch (final KeyStoreException e) {
			throw new AssertionError(e);
		}
	}
}