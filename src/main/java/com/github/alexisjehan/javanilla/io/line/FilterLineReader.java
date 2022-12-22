package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.IOException;

/**
 * <p>An abstract {@link LineReader} filter to create decorators.</p>
 * @since 1.8.0
 */
public abstract class FilterLineReader extends LineReader {

	/**
	 * <p>Delegated {@link LineReader}.</p>
	 * @since 1.8.0
	 */
	protected final LineReader lineReader;

	/**
	 * <p>Constructor with a {@link LineReader} to decorate.</p>
	 * @param lineReader the {@link LineReader} to decorate
	 * @throws NullPointerException if the {@link LineReader} is {@code null}
	 * @since 1.8.0
	 */
	protected FilterLineReader(final LineReader lineReader) {
		Ensure.notNull("lineReader", lineReader);
		this.lineReader = lineReader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String read() throws IOException {
		return lineReader.read();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long skip(final long number) throws IOException {
		return lineReader.skip(number);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		lineReader.close();
	}
}