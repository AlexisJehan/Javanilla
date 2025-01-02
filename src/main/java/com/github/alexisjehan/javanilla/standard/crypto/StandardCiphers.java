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
package com.github.alexisjehan.javanilla.standard.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

/**
 * A {@link Cipher} factory to get standard instances without throwing checked exceptions.
 * @see <a href="https://docs.oracle.com/javase/10/docs/api/javax/crypto/Cipher.html">https://docs.oracle.com/javase/10/docs/api/javax/crypto/Cipher.html</a>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.crypto.StandardCiphers} instead
 * @since 1.6.0
 */
@Deprecated(since = "1.8.0")
public final class StandardCiphers {

	/**
	 * Constructor.
	 * @since 1.6.0
	 */
	private StandardCiphers() {}

	/**
	 * Get a new "AES/CBC/NoPadding" {@link Cipher} instance.
	 * @return an "AES/CBC/NoPadding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getAesCbcNoPaddingInstance() {
		return getInstance("AES/CBC/NoPadding");
	}

	/**
	 * Get a new "AES/CBC/PKCS5Padding" {@link Cipher} instance.
	 * @return an "AES/CBC/PKCS5Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getAesCbcPkcs5PaddingInstance() {
		return getInstance("AES/CBC/PKCS5Padding");
	}

	/**
	 * Get a new "AES/ECB/NoPadding" {@link Cipher} instance.
	 * @return an "AES/ECB/NoPadding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getAesEcbNoPaddingInstance() {
		return getInstance("AES/ECB/NoPadding");
	}

	/**
	 * Get a new "AES/ECB/PKCS5Padding" {@link Cipher} instance.
	 * @return an "AES/ECB/PKCS5Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getAesEcbPkcs5PaddingInstance() {
		return getInstance("AES/ECB/PKCS5Padding");
	}

	/**
	 * Get a new "AES/GCM/NoPadding" {@link Cipher} instance.
	 * @return an "AES/GCM/NoPadding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getAesGcmNoPaddingInstance() {
		return getInstance("AES/GCM/NoPadding");
	}

	/**
	 * Get a new "DES/CBC/NoPadding" {@link Cipher} instance.
	 * @return a "DES/CBC/NoPadding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getDesCbcNoPaddingInstance() {
		return getInstance("DES/CBC/NoPadding");
	}

	/**
	 * Get a new "DES/CBC/PKCS5Padding" {@link Cipher} instance.
	 * @return a "DES/CBC/PKCS5Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getDesCbcPkcs5PaddingInstance() {
		return getInstance("DES/CBC/PKCS5Padding");
	}

	/**
	 * Get a new "DES/ECB/NoPadding" {@link Cipher} instance.
	 * @return a "DES/ECB/NoPadding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getDesEcbNoPaddingInstance() {
		return getInstance("DES/ECB/NoPadding");
	}

	/**
	 * Get a new "DES/ECB/PKCS5Padding" {@link Cipher} instance.
	 * @return a "DES/ECB/PKCS5Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getDesEcbPkcs5PaddingInstance() {
		return getInstance("DES/ECB/PKCS5Padding");
	}

	/**
	 * Get a new "DESede/CBC/NoPadding" {@link Cipher} instance.
	 * @return a "DESede/CBC/NoPadding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getDesedeCbcNoPaddingInstance() {
		return getInstance("DESede/CBC/NoPadding");
	}

	/**
	 * Get a new "DESede/CBC/PKCS5Padding" {@link Cipher} instance.
	 * @return a "DESede/CBC/PKCS5Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getDesedeCbcPkcs5PaddingInstance() {
		return getInstance("DESede/CBC/PKCS5Padding");
	}

	/**
	 * Get a new "DESede/ECB/NoPadding" {@link Cipher} instance.
	 * @return a "DESede/ECB/NoPadding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getDesedeEcbNoPaddingInstance() {
		return getInstance("DESede/ECB/NoPadding");
	}

	/**
	 * Get a new "DESede/ECB/PKCS5Padding" {@link Cipher} instance.
	 * @return a "DESede/ECB/PKCS5Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getDesedeEcbPkcs5PaddingInstance() {
		return getInstance("DESede/ECB/PKCS5Padding");
	}

	/**
	 * Get a new "RSA/ECB/PKCS1Padding" {@link Cipher} instance.
	 * @return an "RSA/ECB/PKCS1Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getRsaEcbPkcs1PaddingInstance() {
		return getInstance("RSA/ECB/PKCS1Padding");
	}

	/**
	 * Get a new "RSA/ECB/OAEPWithSHA-1AndMGF1Padding" {@link Cipher} instance.
	 * @return an "RSA/ECB/OAEPWithSHA-1AndMGF1Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getRsaEcbOaepWithSha1AndMgf1PaddingInstance() {
		return getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
	}

	/**
	 * Get a new "RSA/ECB/OAEPWithSHA-256AndMGF1Padding" {@link Cipher} instance.
	 * @return an "RSA/ECB/OAEPWithSHA-256AndMGF1Padding" {@link Cipher} instance
	 * @since 1.6.0
	 */
	public static Cipher getRsaEcbOaepWithSha256AndMgf1PaddingInstance() {
		return getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
	}

	/**
	 * Get a new {@link Cipher} instance without throwing {@link NoSuchAlgorithmException} or
	 * {@link NoSuchPaddingException}.
	 * @param transformation the {@link Cipher} transformation
	 * @return a {@link Cipher} instance of the provided transformation
	 * @since 1.6.0
	 */
	private static Cipher getInstance(final String transformation) {
		try {
			return Cipher.getInstance(transformation);
		} catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new AssertionError(e);
		}
	}
}