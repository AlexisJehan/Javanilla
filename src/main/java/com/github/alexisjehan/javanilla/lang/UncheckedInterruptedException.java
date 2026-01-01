/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.lang;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

/**
 * Wrapped {@link InterruptedException} as an unchecked exception.
 *
 * <p><b>Note</b>: This class is {@link java.io.Serializable}.</p>
 * @deprecated since 1.6.0, should not be used anymore
 * @since 1.0.0
 */
@Deprecated(since = "1.6.0")
public final class UncheckedInterruptedException extends RuntimeException {

	/**
	 * Serial version unique ID.
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -8171736130465480678L;

	/**
	 * Constructor with a checked cause.
	 * @param cause the checked cause
	 * @throws NullPointerException if the cause is {@code null}
	 * @since 1.0.0
	 */
	public UncheckedInterruptedException(final InterruptedException cause) {
		super(Ensure.notNull("cause", cause));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized InterruptedException getCause() {
		return (InterruptedException) super.getCause();
	}
}