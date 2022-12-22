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
	private long count = 0L;

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