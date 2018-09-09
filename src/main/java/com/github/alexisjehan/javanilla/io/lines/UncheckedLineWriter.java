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
package com.github.alexisjehan.javanilla.io.lines;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * <p>A {@link LineWriter} decorator that throws {@link UncheckedIOException}s instead of {@link IOException}s.</p>
 * @since 1.2.0
 */
public final class UncheckedLineWriter extends FilterLineWriter {

	/**
	 * <p>Constructor with a {@code LineWriter} to decorate.</p>
	 * @param lineWriter the {@code LineWriter} to decorate
	 * @throws NullPointerException if the {@code LineWriter} is {@code null}
	 * @since 1.2.0
	 */
	public UncheckedLineWriter(final LineWriter lineWriter) {
		super(lineWriter);
	}

	@Override
	public void write(final String line) {
		try {
			lineWriter.write(line);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void newLine() {
		try {
			lineWriter.newLine();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void flush() {
		try {
			lineWriter.flush();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void close() {
		try {
			lineWriter.close();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}