package com.github.alexisjehan.javanilla.io.line;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.IOException;

/**
 * <p>A {@link LineReader} decorator that reads only lines within a range from the current position.</p>
 * @since 1.8.0
 */
public final class RangeLineReader extends FilterLineReader {

	/**
	 * <p>Inclusive index of the first line to read.</p>
	 * @since 1.8.0
	 */
	private final long fromIndex;

	/**
	 * <p>Inclusive index of the last line to read.</p>
	 * @since 1.8.0
	 */
	private final long toIndex;

	/**
	 * <p>Current index.</p>
	 * @since 1.8.0
	 */
	private long index = 0L;

	/**
	 * <p>Constructor with a {@link LineReader} to decorate and a range from an inclusive index to another one.</p>
	 * @param lineReader the {@link LineReader} to decorate
	 * @param fromIndex the inclusive index of the first line to read
	 * @param toIndex the inclusive index of the last line to read
	 * @throws NullPointerException if the {@link LineReader} is {@code null}
	 * @throws IllegalArgumentException if the starting index is lower than {@code 0} or greater than the ending one
	 * @since 1.8.0
	 */
	public RangeLineReader(final LineReader lineReader, final long fromIndex, final long toIndex) {
		super(lineReader);
		Ensure.greaterThanOrEqualTo("fromIndex", fromIndex, 0L);
		Ensure.greaterThanOrEqualTo("toIndex", toIndex, fromIndex);
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String read() throws IOException {
		if (fromIndex > index) {
			index += super.skip(fromIndex - index);
		}
		if (toIndex < index) {
			return null;
		}
		final var line = super.read();
		if (null != line) {
			++index;
		}
		return line;
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
			index += super.skip(fromIndex - index);
		}
		final var actual = super.skip(number);
		index += actual;
		return actual;
	}

	/**
	 * <p>Get the inclusive index of the first line to read.</p>
	 * @return the inclusive starting index
	 * @since 1.8.0
	 */
	public long getFromIndex() {
		return fromIndex;
	}

	/**
	 * <p>Get the inclusive index of the last line to read.</p>
	 * @return the inclusive ending index
	 * @since 1.8.0
	 */
	public long getToIndex() {
		return toIndex;
	}
}