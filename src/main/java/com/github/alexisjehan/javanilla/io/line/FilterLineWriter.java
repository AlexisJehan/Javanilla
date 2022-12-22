package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.IOException;

/**
 * <p>An abstract {@link LineWriter} filter to create decorators.</p>
 * @since 1.8.0
 */
public abstract class FilterLineWriter extends LineWriter {

	/**
	 * <p>Delegated {@link LineWriter}.</p>
	 * @since 1.8.0
	 */
	protected final LineWriter lineWriter;

	/**
	 * <p>Constructor with a {@link LineWriter} to decorate.</p>
	 * @param lineWriter the {@link LineWriter} to decorate
	 * @throws NullPointerException if the {@link LineWriter} is {@code null}
	 * @since 1.8.0
	 */
	protected FilterLineWriter(final LineWriter lineWriter) {
		Ensure.notNull("lineWriter", lineWriter);
		this.lineWriter = lineWriter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final String line) throws IOException {
		lineWriter.write(line);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newLine() throws IOException {
		lineWriter.newLine();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() throws IOException {
		lineWriter.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		lineWriter.close();
	}
}