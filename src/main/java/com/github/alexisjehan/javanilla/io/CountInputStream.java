package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>An {@link InputStream} decorator that counts the number of bytes read from the current position.</p>
 * @since 1.8.0
 */
public final class CountInputStream extends FilterInputStream {

	/**
	 * <p>Number of bytes read.</p>
	 * @since 1.8.0
	 */
	private long count = 0L;

	/**
	 * <p>Number of bytes read at the last call of {@link #mark(int)}, or {@code 0} if not called yet.</p>
	 * @since 1.8.0
	 */
	private long markedCount = 0L;

	/**
	 * <p>Constructor with an {@link InputStream} to decorate.</p>
	 * @param inputStream the {@link InputStream} to decorate
	 * @throws NullPointerException if the {@link InputStream} is {@code null}
	 * @since 1.8.0
	 */
	public CountInputStream(final InputStream inputStream) {
		super(Ensure.notNull("inputStream", inputStream));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read() throws IOException {
		final var next = in.read();
		if (-1 != next) {
			++count;
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
		final var total = in.read(buffer, offset, length);
		if (-1 != total) {
			count += total;
		}
		return total;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long skip(final long number) throws IOException {
		if (0L >= number) {
			return 0L;
		}
		final var actual = in.skip(number);
		count += actual;
		return actual;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void mark(final int limit) {
		in.mark(limit);
		markedCount = count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void reset() throws IOException {
		in.reset();
		count = markedCount;
	}

	/**
	 * <p>Get the number of bytes read.</p>
	 * @return the number of bytes read
	 * @since 1.8.0
	 */
	public long getCount() {
		return count;
	}
}