/*
MIT License

Copyright (c) 2018 Alexis Jehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.github.alexisjehan.javanilla.sql;

import java.sql.SQLException;
import java.util.Objects;

/**
 * <p>Wrapped {@link SQLException} as an unchecked exception.</p>
 * <p><b>Note</b>: This class is serializable.</p>
 * @since 1.0.0
 */
public final class UncheckedSQLException extends RuntimeException {

	/**
	 * <p>Serial version unique ID.</p>
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 1820971183636467050L;

	/**
	 * <p>Constructor with a checked cause.</p>
	 * @param cause the checked cause
	 * @throws NullPointerException if the cause is {@code null}
	 * @since 1.0.0
	 */
	public UncheckedSQLException(final SQLException cause) {
		super(Objects.requireNonNull(cause, "Invalid cause (not null expected)"));
	}

	@Override
	public SQLException getCause() {
		return (SQLException) super.getCause();
	}
}