package com.github.alexisjehan.javanilla.io.line;

import java.io.IOException;

/**
 * <p>A {@link LineReader} decorator that counts the number of lines read from the current position.</p>
 * @since 1.8.0
 */
public final class CountLineReader extends FilterLineReader {

	/**
	 * <p>Number of lines read.</p>
	 * @since 1.8.0
	 */
	private long count = 0L;

	/**
	 * <p>Constructor with a {@link LineReader} to decorate.</p>
	 * @param lineReader the {@link LineReader} to decorate
	 * @throws NullPointerException if the {@link LineReader} is {@code null}
	 * @since 1.8.0
	 */
	public CountLineReader(final LineReader lineReader) {
		super(lineReader);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String read() throws IOException {
		final var line = super.read();
		if (null != line) {
			++count;
		}
		return line;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long skip(final long number) throws IOException {
		if (0L >= number) {
			return 0L;
		}
		final var actual = super.skip(number);
		count += actual;
		return actual;
	}

	/**
	 * <p>Get the number of lines read.</p>
	 * @return the number of lines read
	 * @since 1.8.0
	 */
	public long getCount() {
		return count;
	}
}