/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * <p>{@link StandardMessageDigests} unit tests.</p>
 */
final class StandardMessageDigestsTest {

	@Test
	void testGetMd5Instance() {
		assertThat(StandardMessageDigests.getMd5Instance().getAlgorithm()).isEqualTo("MD5");
	}

	@Test
	void testGetSha1Instance() {
		assertThat(StandardMessageDigests.getSha1Instance().getAlgorithm()).isEqualTo("SHA-1");
	}

	@Test
	void testGetSha256Instance() {
		assertThat(StandardMessageDigests.getSha256Instance().getAlgorithm()).isEqualTo("SHA-256");
	}

	@Test
	void testGetInstanceUnreachable() throws NoSuchMethodException {
		final var method = StandardMessageDigests.class.getDeclaredMethod("getInstance", String.class);
		method.setAccessible(true);
		assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> {
			try {
				method.invoke(null, "?");
			} catch (final InvocationTargetException e) {
				throw e.getTargetException();
			}
		}).withCauseInstanceOf(NoSuchAlgorithmException.class);
	}
}