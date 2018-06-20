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

/**
 * <p>An {@link LineReader} decorator that reads only lines within a range from the current position.</p>
 * @since 1.0.0
 */
public final class RangeLineReader extends FilterLineReader {

	/**
	 * <p>The inclusive index of the first line to read.</p>
	 * @since 1.0.0
	 */
	private final long fromIndex;

	/**
	 * <p>The inclusive index of the last line to read.<p>
	 * @since 1.0.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.0.0
	 */
	private long index = 0L;

	/**
	 * <p>Constructor with a delegated {@code LineReader} and a range from {@code 0} to the given inclusive index.</p>
	 * @param lineReader the delegated {@code LineReader}
	 * @param toIndex the inclusive index of the last line to read
	 * @throws NullPointerException if the {@code LineReader} is {@code null}
	 * @throws IndexOutOfBoundsException if the index is negative
	 * @since 1.0.0
	 */
	public RangeLineReader(final LineReader lineReader, final long toIndex) {
		this(lineReader, 0L, toIndex);
	}

	/**
	 * <p>Constructor with a delegated {@code LineReader} and a range from an inclusive index to another one.</p>
	 * @param lineReader the delegated {@code LineReader}
	 * @param fromIndex the inclusive index of the first line to read
	 * @param toIndex the inclusive index of the last line to read
	 * @throws NullPointerException if the {@code LineReader} is {@code null}
	 * @throws IndexOutOfBoundsException whether the starting index is negative or greater than the ending one
	 * @since 1.0.0
	 */
	public RangeLineReader(final LineReader lineReader, final long fromIndex, final long toIndex) {
		super(lineReader);
		if (0L > fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (greater than or equal to 0 expected)");
		}
		if (fromIndex > toIndex) {
			throw new IndexOutOfBoundsException("Invalid to index: " + toIndex + " (greater than or equal to the from index expected)");
		}
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public String read() throws IOException {
		if (fromIndex > index) {
			index += super.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return null;
		}
		final var line = super.read();
		if (null != line) {
			++index;
		}
		return line;
	}

	@Override
	public long skip(final long n) throws IOException {
		final var s = super.skip(n);
		index += s;
		return s;
	}

	/**
	 * <p>Get the inclusive index of the first line to read.</p>
	 * @return the inclusive index
	 * @since 1.0.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last line to read.<p>
	 * @return the inclusive index
	 * @since 1.0.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}