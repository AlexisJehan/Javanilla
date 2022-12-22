package com.github.alexisjehan.javanilla.io;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * <p>A {@link Writer} decorator that counts the number of chars written from the current position.</p>
 * @since 1.8.0
 */
public final class CountWriter extends FilterWriter {

	/**
	 * <p>Number of chars written.</p>
	 * @since 1.8.0
	 */
	private long count = 0L;

	/**
	 * <p>Constructor with a {@link Writer} to decorate.</p>
	 * @param writer the {@link Writer} to decorate
	 * @throws NullPointerException if the {@link Writer} is {@code null}
	 * @since 1.8.0
	 */
	public CountWriter(final Writer writer) {
		super(Ensure.notNull("writer", writer));
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
	public void write(final char[] chars, final int offset, final int length) throws IOException {
		Ensure.notNull("chars", chars);
		Ensure.between("offset", offset, 0, chars.length);
		Ensure.between("length", length, 0, chars.length - offset);
		if (0 < length) {
			out.write(chars, offset, length);
			count += length;
		}
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
		if (0 < length) {
			out.write(string, offset, length);
			count += length;
		}
	}

	/**
	 * <p>Get the number of chars written.</p>
	 * @return the number of chars written
	 * @since 1.8.0
	 */
	public long getCount() {
		return count;
	}
}