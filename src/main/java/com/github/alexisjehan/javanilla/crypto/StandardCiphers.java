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

import javax.crypto.Cipher;

/**
 * <p>A {@link Cipher} factory to get standard instances without throwing checked exceptions.</p>
 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers} instead
 * @since 1.0.0
 */
@Deprecated(since = "1.6.0")
public final class StandardCiphers {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private StandardCiphers() {
		// Not available
	}

	/**
	 * <p>Get a new "AES/CBC/NoPadding" {@link Cipher} instance.</p>
	 * @return an "AES/CBC/NoPadding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getAesCbcNoPaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getAesCbcInstance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getAesCbcNoPaddingInstance();
	}

	/**
	 * <p>Get a new "AES/CBC/PKCS5Padding" {@link Cipher} instance.</p>
	 * @return an "AES/CBC/PKCS5Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getAesCbcPkcs5PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getAesCbcPkcs5Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getAesCbcPkcs5PaddingInstance();
	}

	/**
	 * <p>Get a new "AES/ECB/NoPadding" {@link Cipher} instance.</p>
	 * @return an "AES/ECB/NoPadding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getAesEcbNoPaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getAesEcbInstance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getAesEcbNoPaddingInstance();
	}

	/**
	 * <p>Get a new "AES/ECB/PKCS5Padding" {@link Cipher} instance.</p>
	 * @return an "AES/ECB/PKCS5Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getAesEcbPkcs5PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getAesEcbPkcs5Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getAesEcbPkcs5PaddingInstance();
	}

	/**
	 * <p>Get a new "AES/GCM/NoPadding" {@link Cipher} instance.</p>
	 * @return an "AES/GCM/NoPadding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getAesGcmNoPaddingInstance()} instead
	 * @since 1.3.1
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getAesGcmInstance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getAesGcmNoPaddingInstance();
	}

	/**
	 * <p>Get a new "DES/CBC/NoPadding" {@link Cipher} instance.</p>
	 * @return a "DES/CBC/NoPadding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getDesCbcNoPaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getDesCbcInstance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getDesCbcNoPaddingInstance();
	}

	/**
	 * <p>Get a new "DES/CBC/PKCS5Padding" {@link Cipher} instance.</p>
	 * @return a "DES/CBC/PKCS5Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getDesCbcPkcs5PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getDesCbcPkcs5Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getDesCbcPkcs5PaddingInstance();
	}

	/**
	 * <p>Get a new "DES/ECB/NoPadding" {@link Cipher} instance.</p>
	 * @return a "DES/ECB/NoPadding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getDesEcbNoPaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getDesEcbInstance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getDesEcbNoPaddingInstance();
	}

	/**
	 * <p>Get a new "DES/ECB/PKCS5Padding" {@link Cipher} instance.</p>
	 * @return a "DES/ECB/PKCS5Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getDesEcbPkcs5PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getDesEcbPkcs5Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getDesEcbPkcs5PaddingInstance();
	}

	/**
	 * <p>Get a new "DESede/CBC/NoPadding" {@link Cipher} instance.</p>
	 * @return a "DESede/CBC/NoPadding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getDesedeCbcNoPaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getTripleDesCbcInstance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getDesedeCbcNoPaddingInstance();
	}

	/**
	 * <p>Get a new "DESede/CBC/PKCS5Padding" {@link Cipher} instance.</p>
	 * @return a "DESede/CBC/PKCS5Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getDesedeCbcPkcs5PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getTripleDesCbcPkcs5Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getDesedeCbcPkcs5PaddingInstance();
	}

	/**
	 * <p>Get a new "DESede/ECB/NoPadding" {@link Cipher} instance.</p>
	 * @return a "DESede/ECB/NoPadding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getDesedeEcbNoPaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getTripleDesEcbInstance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getDesedeEcbNoPaddingInstance();
	}

	/**
	 * <p>Get a new "DESede/ECB/PKCS5Padding" {@link Cipher} instance.</p>
	 * @return a "DESede/ECB/PKCS5Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getDesedeEcbPkcs5PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getTripleDesEcbPkcs5Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getDesedeEcbPkcs5PaddingInstance();
	}

	/**
	 * <p>Get a new "RSA/ECB/PKCS1Padding" {@link Cipher} instance.</p>
	 * @return a "RSA/ECB/PKCS1Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getRsaEcbPkcs1PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getRsaEcbPkcs1Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getRsaEcbPkcs1PaddingInstance();
	}

	/**
	 * <p>Get a new "RSA/ECB/OAEPWithSHA-1AndMGF1Padding" {@link Cipher} instance.</p>
	 * @return a "RSA/ECB/OAEPWithSHA-1AndMGF1Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getRsaEcbOaepWithSha1AndMgf1PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getRsaEcbOaepSha1AndMgf1Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getRsaEcbOaepWithSha1AndMgf1PaddingInstance();
	}

	/**
	 * <p>Get a new "RSA/ECB/OAEPWithSHA-256AndMGF1Padding" {@link Cipher} instance.</p>
	 * @return a "RSA/ECB/OAEPWithSHA-256AndMGF1Padding" {@link Cipher} instance
	 * @deprecated use {@link com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers#getRsaEcbOaepWithSha256AndMgf1PaddingInstance()} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static Cipher getRsaEcbOaepSha256AndMgf1Instance() {
		return com.github.alexisjehan.javanilla.standard.crypto.StandardCiphers.getRsaEcbOaepWithSha256AndMgf1PaddingInstance();
	}
}