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
package com.github.alexisjehan.javanilla.standard.net.ssl;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 * <p>A {@link SSLContext} factory to get standard instances without throwing checked exceptions.</p>
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/javax/net/ssl/SSLContext.html">https://docs.oracle.com/javase/10/docs/api/javax/net/ssl/SSLContext.html</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.net.ssl.StandardSslContexts} instead
 * @since 1.6.0
 */
@Deprecated(since = "1.8.0")
public final class StandardSslContexts {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.6.0
	 */
	private StandardSslContexts() {
		// Not available
	}

	/**
	 * <p>Get a new "TLSv1" {@link SSLContext} instance.</p>
	 * @return a "TLSv1" {@link SSLContext} instance
	 * @since 1.6.0
	 */
	public static SSLContext getTlsV1Instance() {
		return getInstance("TLSv1");
	}

	/**
	 * <p>Get a new "TLSv1.1" {@link SSLContext} instance.</p>
	 * @return a "TLSv1.1" {@link SSLContext} instance
	 * @since 1.6.0
	 */
	public static SSLContext getTlsV11Instance() {
		return getInstance("TLSv1.1");
	}

	/**
	 * <p>Get a new "TLSv1.2" {@link SSLContext} instance.</p>
	 * @return a "TLSv1.2" {@link SSLContext} instance
	 * @since 1.6.0
	 */
	public static SSLContext getTlsV12Instance() {
		return getInstance("TLSv1.2");
	}

	/**
	 * <p>Get a new {@link SSLContext} instance without throwing {@link NoSuchAlgorithmException}.</p>
	 * @param protocol the {@link SSLContext} protocol
	 * @return a {@link SSLContext} instance of the provided protocol
	 * @since 1.6.0
	 */
	private static SSLContext getInstance(final String protocol) {
		try {
			return SSLContext.getInstance(protocol);
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
	}
}