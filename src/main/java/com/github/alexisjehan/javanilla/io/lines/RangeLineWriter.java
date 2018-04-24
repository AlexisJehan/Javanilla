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
 * <p>An {@link LineWriter} decorator that writes only lines within a range from the current position.</p>
 * @since 1.0
 */
public final class RangeLineWriter extends FilterLineWriter {

	/**
	 * <p>The inclusive index of the first line to write.</p>
	 * @since 1.0
	 */
	private final long fromIndex;

	/**
	 * <p>The inclusive index of the last line to write.<p>
	 * @since 1.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.0
	 */
	private long index = 0L;

	/**
	 * <p>Constructor with a delegated {@code LineWriter} and a range from {@code 0} to the given inclusive index.</p>
	 * @param lineWriter the delegated {@code LineWriter}
	 * @param toIndex the inclusive index of the last line to write
	 * @throws NullPointerException if the delegated {@code LineWriter} is {@code null}
	 * @throws IndexOutOfBoundsException if the index is negative
	 * @since 1.0
	 */
	public RangeLineWriter(final LineWriter lineWriter, final long toIndex) {
		this(lineWriter, 0L, toIndex);
	}

	/**
	 * <p>Constructor with a delegated {@code LineWriter} and a range from an inclusive index to another one.</p>
	 * @param lineWriter the delegated {@code LineWriter}
	 * @param fromIndex the inclusive index of the first line to write
	 * @param toIndex the inclusive index of the last line to write
	 * @throws NullPointerException if the delegated {@code LineWriter} is {@code null}
	 * @throws IndexOutOfBoundsException whether the starting index is negative or greater than the ending one
	 * @since 1.0
	 */
	public RangeLineWriter(final LineWriter lineWriter, final long fromIndex, final long toIndex) {
		super(lineWriter);
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
	public void write(final String line) throws IOException {
		if (fromIndex <= index && toIndex >= index) {
			super.write(line);
		}
		++index;
	}

	@Override
	public void newLine() throws IOException {
		if (fromIndex <= index && toIndex >= index) {
			super.newLine();
		}
		++index;
	}

	/**
	 * <p>Get the index of the first line to write.</p>
	 * @return the inclusive index of the first line to write
	 * @since 1.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the index of the last line to write.<p>
	 * @return the inclusive index of the last line to write
	 * @since 1.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}