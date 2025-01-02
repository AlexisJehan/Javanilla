/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package examples;

import com.github.alexisjehan.javanilla.io.Readers;
import com.github.alexisjehan.javanilla.io.Writers;
import com.github.alexisjehan.javanilla.io.line.LineReader;
import com.github.alexisjehan.javanilla.io.line.LineSeparator;
import com.github.alexisjehan.javanilla.io.line.LineWriter;

import java.io.IOException;

public final class LineExample {

	private LineExample() {}

	public static void main(final String... args) throws IOException {
		final var windowsFilePath = Writers.EMPTY;

		// Convert a String with Unix line separators to the Windows format removing the extra new line
		final var unixFilePath = Readers.of("foo\nbar\n");
		final var ignoreTerminatingNewLine = true;
		try (var lineReader = new LineReader(unixFilePath, LineSeparator.LF, ignoreTerminatingNewLine)) {
			final var appendTerminatingNewLine = false;
			try (var lineWriter = new LineWriter(windowsFilePath, LineSeparator.CR_LF, appendTerminatingNewLine)) {
				// Transfers all lines from the LineReader to the LineWriter
				final var transferred = lineReader.transferTo(lineWriter);
				System.out.println(transferred); // Prints 2
			}
		}
	}
}