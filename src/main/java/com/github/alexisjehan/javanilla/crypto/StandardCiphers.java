/*
MIT License

Copyright (c) 2018 Alexis Jehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.github.alexisjehan.javanilla.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>A {@link Cipher} factory to get standard instances without throwing checked exceptions.</p>
 * @since 1.0.0
 */
public final class StandardCiphers {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private StandardCiphers() {
		// Not available
	}

	/**
	 * <p>Get a new "AES/CBC/NoPadding" {@code Cipher} instance.</p>
	 * @return a "AES/CBC/NoPadding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getAesCbcInstance() {
		return getInstance("AES/CBC/NoPadding");
	}

	/**
	 * <p>Get a new "AES/CBC/PKCS5Padding" {@code Cipher} instance.</p>
	 * @return a "AES/CBC/PKCS5Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getAesCbcPkcs5Instance() {
		return getInstance("AES/CBC/PKCS5Padding");
	}

	/**
	 * <p>Get a new "AES/ECB/NoPadding" {@code Cipher} instance.</p>
	 * @return a "AES/ECB/NoPadding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getAesEcbInstance() {
		return getInstance("AES/ECB/NoPadding");
	}

	/**
	 * <p>Get a new "AES/ECB/PKCS5Padding" {@code Cipher} instance.</p>
	 * @return a "AES/ECB/PKCS5Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getAesEcbPkcs5Instance() {
		return getInstance("AES/ECB/PKCS5Padding");
	}

	/**
	 * <p>Get a new "AES/GCM/NoPadding" {@code Cipher} instance.</p>
	 * @return a "AES/GCM/NoPadding" {@code Cipher} instance
	 * @since 1.3.1
	 */
	public static Cipher getAesGcmInstance() {
		return getInstance("AES/GCM/NoPadding");
	}

	/**
	 * <p>Get a new "DES/CBC/NoPadding" {@code Cipher} instance.</p>
	 * @return a "DES/CBC/NoPadding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getDesCbcInstance() {
		return getInstance("DES/CBC/NoPadding");
	}

	/**
	 * <p>Get a new "DES/CBC/PKCS5Padding" {@code Cipher} instance.</p>
	 * @return a "DES/CBC/PKCS5Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getDesCbcPkcs5Instance() {
		return getInstance("DES/CBC/PKCS5Padding");
	}

	/**
	 * <p>Get a new "DES/ECB/NoPadding" {@code Cipher} instance.</p>
	 * @return a "DES/ECB/NoPadding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getDesEcbInstance() {
		return getInstance("DES/ECB/NoPadding");
	}

	/**
	 * <p>Get a new "DES/ECB/PKCS5Padding" {@code Cipher} instance.</p>
	 * @return a "DES/ECB/PKCS5Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getDesEcbPkcs5Instance() {
		return getInstance("DES/ECB/PKCS5Padding");
	}

	/**
	 * <p>Get a new "DESede/CBC/NoPadding" {@code Cipher} instance.</p>
	 * @return a "DESede/CBC/NoPadding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getTripleDesCbcInstance() {
		return getInstance("DESede/CBC/NoPadding");
	}

	/**
	 * <p>Get a new "DESede/CBC/PKCS5Padding" {@code Cipher} instance.</p>
	 * @return a "DESede/CBC/PKCS5Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getTripleDesCbcPkcs5Instance() {
		return getInstance("DESede/CBC/PKCS5Padding");
	}

	/**
	 * <p>Get a new "DESede/ECB/NoPadding" {@code Cipher} instance.</p>
	 * @return a "DESede/ECB/NoPadding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getTripleDesEcbInstance() {
		return getInstance("DESede/ECB/NoPadding");
	}

	/**
	 * <p>Get a new "DESede/ECB/PKCS5Padding" {@code Cipher} instance.</p>
	 * @return a "DESede/ECB/PKCS5Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getTripleDesEcbPkcs5Instance() {
		return getInstance("DESede/ECB/PKCS5Padding");
	}

	/**
	 * <p>Get a new "RSA/ECB/PKCS1Padding" {@code Cipher} instance.</p>
	 * @return a "RSA/ECB/PKCS1Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getRsaEcbPkcs1Instance() {
		return getInstance("RSA/ECB/PKCS1Padding");
	}

	/**
	 * <p>Get a new "RSA/ECB/OAEPWithSHA-1AndMGF1Padding" {@code Cipher} instance.</p>
	 * @return a "RSA/ECB/OAEPWithSHA-1AndMGF1Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getRsaEcbOaepSha1AndMgf1Instance() {
		return getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
	}

	/**
	 * <p>Get a new "RSA/ECB/OAEPWithSHA-256AndMGF1Padding" {@code Cipher} instance.</p>
	 * @return a "RSA/ECB/OAEPWithSHA-256AndMGF1Padding" {@code Cipher} instance
	 * @since 1.0.0
	 */
	public static Cipher getRsaEcbOaepSha256AndMgf1Instance() {
		return getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
	}

	/**
	 * <p>Get a new {@code Cipher} instance without throwing {@code NoSuchAlgorithmException} or
	 * {@code NoSuchPaddingException}.</p>
	 * @param algorithm the {@code Cipher} algorithm
	 * @return a {@code Cipher} instance of the provided algorithm
	 * @since 1.0.0
	 */
	private static Cipher getInstance(final String algorithm) {
		try {
			return Cipher.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new AssertionError(e);
		}
	}
}