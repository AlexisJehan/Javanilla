package com.github.alexisjehan.javanilla.io.line;

import java.io.IOException;

/**
 * <p>A {@link LineWriter} decorator that counts the number of lines written from the current position.</p>
 * @since 1.8.0
 */
public final class CountLineWriter extends FilterLineWriter {

	/**
	 * <p>Number of lines written.</p>
	 * @since 1.8.0
	 */
	private long count = 0L;

	/**
	 * <p>Constructor with a {@link LineWriter} to decorate.</p>
	 * @param lineWriter the {@link LineWriter} to decorate
	 * @throws NullPointerException if the {@link LineWriter} is {@code null}
	 * @since 1.8.0
	 */
	public CountLineWriter(final LineWriter lineWriter) {
		super(lineWriter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final String line) throws IOException {
		super.write(line);
		++count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newLine() throws IOException {
		super.newLine();
		++count;
	}

	/**
	 * <p>Get the number of lines written.</p>
	 * @return the number of lines written
	 * @since 1.8.0
	 */
	public long getCount() {
		return count;
	}
}