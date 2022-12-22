package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>An {@link InputStream} decorator that reads only bytes within a range from the current position.</p>
 * @since 1.8.0
 */
public final class RangeInputStream extends FilterInputStream {

	/**
	 * <p>Inclusive index of the first byte to read.</p>
	 * @since 1.8.0
	 */
	private final long fromIndex;

	/**
	 * <p>Inclusive index of the last byte to read.</p>
	 * @since 1.8.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.8.0
	 */
	private long index = 0L;

	/**
	 * <p>Index at the last call of {@link #mark(int)}, or {@code 0} if not called yet.</p>
	 * @since 1.8.0
	 */
	private long markedIndex = 0L;

	/**
	 * <p>Constructor with an {@link InputStream} to decorate and a range from an inclusive index to another one.</p>
	 * @param inputStream the {@link InputStream} to decorate
	 * @param fromIndex the inclusive index of the first byte to read
	 * @param toIndex the inclusive index of the last byte to read
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @throws IllegalArgumentException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.8.0
	 */
	public RangeInputStream(final InputStream inputStream, final long fromIndex, final long toIndex) {
		super(Ensure.notNull("inputStream", inputStream));
		Ensure.greaterThanOrEqualTo("fromIndex", fromIndex, 0L);
		Ensure.greaterThanOrEqualTo("toIndex", toIndex, fromIndex);
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read() throws IOException {
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return -1;
		}
		final var next = in.read();
		if (-1 != next) {
			++index;
		}
		return next;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read(final byte[] buffer, final int offset, final int length) throws IOException {
		Ensure.notNull("buffer", buffer);
		Ensure.between("offset", offset, 0, buffer.length);
		Ensure.between("length", length, 0, buffer.length - offset);
		if (0 == length) {
			return 0;
		}
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return -1;
		}
		final var total = in.read(buffer, offset, Math.min(length, (int) (toIndex - index + 1)));
		if (-1 != total) {
			index += total;
		}
		return total;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long skip(final long number) throws IOException {
		if (0L >= number || toIndex < index) {
			return 0L;
		}
		if (fromIndex > index) {
			index += in.skip(fromIndex - index);
		}
		final var actual = in.skip(number);
		index += actual;
		return actual;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void mark(final int limit) {
		in.mark(limit);
		markedIndex = index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void reset() throws IOException {
		in.reset();
		index = markedIndex;
	}

	/**
	 * <p>Get the inclusive index of the first byte to read.</p>
	 * @return the inclusive starting index
	 * @since 1.8.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last byte to read.</p>
	 * @return the inclusive ending index
	 * @since 1.8.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}