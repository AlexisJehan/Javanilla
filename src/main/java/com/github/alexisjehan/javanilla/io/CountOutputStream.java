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
package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>An {@link OutputStream} decorator that counts the number of bytes written from the current position.</p>
 * @since 1.8.0
 */
public final class CountOutputStream extends FilterOutputStream {

	/**
	 * <p>Number of bytes written.</p>
	 * @since 1.8.0
	 */
	private long count;

	/**
	 * <p>Constructor with an {@link OutputStream} to decorate.</p>
	 * @param outputStream the {@link OutputStream} to decorate
	 * @throws NullPointerException if the {@link OutputStream} is {@code null}
	 * @since 1.8.0
	 */
	public CountOutputStream(final OutputStream outputStream) {
		super(Ensure.notNull("outputStream", outputStream));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final int i) throws IOException {
		out.write(i);
		++count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final byte[] bytes, final int offset, final int length) throws IOException {
		Ensure.notNull("bytes", bytes);
		Ensure.between("offset", offset, 0, bytes.length);
		Ensure.between("length", length, 0, bytes.length - offset);
		if (0 < length) {
			out.write(bytes, offset, length);
			count += length;
		}
	}

	/**
	 * <p>Get the number of bytes written.</p>
	 * @return the number of bytes written
	 * @since 1.8.0
	 */
	public long getCount() {
		return count;
	}
}