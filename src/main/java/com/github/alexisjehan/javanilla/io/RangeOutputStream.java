package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>An {@link OutputStream} decorator that writes only bytes within a range from the current position.</p>
 * @since 1.8.0
 */
public final class RangeOutputStream extends FilterOutputStream {

	/**
	 * <p>Inclusive index of the first byte to write.</p>
	 * @since 1.8.0
	 */
	private final long fromIndex;

	/**
	 * <p>Inclusive index of the last byte to write.</p>
	 * @since 1.8.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.8.0
	 */
	private long index = 0L;

	/**
	 * <p>Constructor with an {@link OutputStream} to decorate and a range from an inclusive index to another one.</p>
	 * @param outputStream the {@link OutputStream} to decorate
	 * @param fromIndex the inclusive index of the first byte to write
	 * @param toIndex the inclusive index of the last byte to write
	 * @throws NullPointerException if the {@link OutputStream} is {@code null}
	 * @throws IllegalArgumentException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.8.0
	 */
	public RangeOutputStream(final OutputStream outputStream, final long fromIndex, final long toIndex) {
		super(Ensure.notNull("outputStream", outputStream));
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
	public void write(final byte[] bytes, final int offset, final int length) throws IOException {
		Ensure.notNull("bytes", bytes);
		Ensure.between("offset", offset, 0, bytes.length);
		Ensure.between("length", length, 0, bytes.length - offset);
		if (0 == length) {
			return;
		}
		if (fromIndex <= index + length && toIndex >= index) {
			out.write(bytes, offset + (fromIndex > index ? (int) (fromIndex - index) : 0), Math.min(length, toIndex != index ? (int) (toIndex - index) : 1));
		}
		index += length;
	}

	/**
	 * <p>Get the inclusive index of the first byte to write.</p>
	 * @return the inclusive starting index
	 * @since 1.8.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the first last to write.</p>
	 * @return the inclusive ending index
	 * @since 1.8.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}