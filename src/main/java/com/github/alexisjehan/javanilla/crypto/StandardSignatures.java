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

import java.security.Signature;

/**
 * <p>A {@link Signature} factory to get standard instances without throwing checked exceptions.</p>
 * @deprecated since 1.6.0, use {@link com.github.alexisjehan.javanilla.standard.security.StandardSignatures} instead
 * @since 1.3.1
 */
@Deprecated(since = "1.6.0")
public final class StandardSignatures {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.3.1
	 */
	private StandardSignatures() {
		// Not available
	}

	/**
	 * <p>Get a new "SHA1withDSA" {@link Signature} instance.</p>
	 * @return a "SHA1withDSA" {@link Signature} instance
	 * @deprecated since 1.6.0, use {@link com.github.alexisjehan.javanilla.standard.security.StandardSignatures#getSha1WithDsaInstance()} instead
	 * @since 1.3.1
	 */
	@Deprecated(since = "1.6.0")
	public static Signature getSha1WithDsaInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardSignatures.getSha1WithDsaInstance();
	}

	/**
	 * <p>Get a new "SHA256withDSA" {@link Signature} instance.</p>
	 * @return a "SHA256withDSA" {@link Signature} instance
	 * @deprecated since 1.6.0, use {@link com.github.alexisjehan.javanilla.standard.security.StandardSignatures#getSha256WithDsaInstance()} instead
	 * @since 1.3.1
	 */
	@Deprecated(since = "1.6.0")
	public static Signature getSha256WithDsaInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardSignatures.getSha256WithDsaInstance();
	}

	/**
	 * <p>Get a new "SHA1withRSA" {@link Signature} instance.</p>
	 * @return a "SHA1withRSA" {@link Signature} instance
	 * @deprecated since 1.6.0, use {@link com.github.alexisjehan.javanilla.standard.security.StandardSignatures#getSha1WithRsaInstance()} instead
	 * @since 1.3.1
	 */
	@Deprecated(since = "1.6.0")
	public static Signature getSha1WithRsaInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardSignatures.getSha1WithRsaInstance();
	}

	/**
	 * <p>Get a new "SHA256withRSA" {@link Signature} instance.</p>
	 * @return a "SHA256withRSA" {@link Signature} instance
	 * @deprecated since 1.6.0, use {@link com.github.alexisjehan.javanilla.standard.security.StandardSignatures#getSha256WithRsaInstance()} instead
	 * @since 1.3.1
	 */
	@Deprecated(since = "1.6.0")
	public static Signature getSha256WithRsaInstance() {
		return com.github.alexisjehan.javanilla.standard.security.StandardSignatures.getSha256WithRsaInstance();
	}
}