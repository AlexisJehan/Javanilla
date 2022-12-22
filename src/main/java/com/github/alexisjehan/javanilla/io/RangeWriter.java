package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * <p>A {@link Writer} decorator that writes only chars within a range from the current position.</p>
 * @since 1.8.0
 */
public final class RangeWriter extends FilterWriter {

	/**
	 * <p>Inclusive index of the first char to write.</p>
	 * @since 1.8.0
	 */
	private final long fromIndex;

	/**
	 * <p>Inclusive index of the last char to write.</p>
	 * @since 1.8.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.8.0
	 */
	private long index = 0L;

	/**
	 * <p>Constructor with a {@link Writer} to decorate and a range from an inclusive index to another one.</p>
	 * @param writer the {@link Writer} to decorate
	 * @param fromIndex the inclusive index of the first char to write
	 * @param toIndex the inclusive index of the last char to write
	 * @throws NullPointerException if the {@link Writer} is {@code null}
	 * @throws IllegalArgumentException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.8.0
	 */
	public RangeWriter(final Writer writer, final long fromIndex, final long toIndex) {
		super(Ensure.notNull("writer", writer));
		Ensure.greaterThanOrEqualTo("fromIndex", fromIndex, 0L);
		Ensure.greaterThanOrEqualTo("toIndex", toIndex, fromIndex);
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final int i) throws IOException {
		if (fromIndex <= index && toIndex >= index) {
			out.write(i);
		}
		++index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final char[] chars, final int offset, final int length) throws IOException {
		Ensure.notNull("chars", chars);
		Ensure.between("offset", offset, 0, chars.length);
		Ensure.between("length", length, 0, chars.length - offset);
		if (0 == length) {
			return;
		}
		if (fromIndex <= index + length && toIndex >= index) {
			out.write(chars, offset + (fromIndex > index ? (int) (fromIndex - index) : 0), Math.min(length, toIndex != index ? (int) (toIndex - index) : 1));
		}
		index += length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final String string, final int offset, final int length) throws IOException {
		Ensure.notNull("string", string);
		final var stringLength = string.length();
		Ensure.between("offset", offset, 0, stringLength);
		Ensure.between("length", length, 0, stringLength - offset);
		if (0 == length) {
			return;
		}
		if (fromIndex <= index + length && toIndex >= index) {
			out.write(string, offset + (fromIndex > index ? (int) (fromIndex - index) : 0), Math.min(length, toIndex != index ? (int) (toIndex - index) : 1));
		}
		index += length;
	}

	/**
	 * <p>Get the inclusive index of the first char to write.</p>
	 * @return the inclusive starting index
	 * @since 1.8.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last char to write.</p>
	 * @return the inclusive ending index
	 * @since 1.8.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}