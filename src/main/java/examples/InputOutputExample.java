/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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

import com.github.alexisjehan.javanilla.io.InputStreams;
import com.github.alexisjehan.javanilla.io.OutputStreams;
import com.github.alexisjehan.javanilla.io.RangeOutputStream;
import com.github.alexisjehan.javanilla.util.iteration.Iterables;

import java.io.IOException;

public final class InputOutputExample {

	private InputOutputExample() {
		// Not available
	}

	public static void main(final String... args) throws IOException {
		final var optionalInputStream = InputStreams.EMPTY;
		final var fileOutputStream = OutputStreams.EMPTY;
		final var sampleOutputStream = OutputStreams.EMPTY;

		// Read from an optional InputStream and then from another one which gives 0x00 and 0xff bytes
		final var concatInputStream = InputStreams.concat(
				InputStreams.nullToEmpty(optionalInputStream),
				InputStreams.of((byte) 0x00, (byte) 0xff)
		);

		// Write to both a buffered file OutputStream and a sampling one
		final var fromIndex = 0L;
		final var toIndex = 100L;
		final var teeOutputStream = OutputStreams.tee(
				OutputStreams.buffered(fileOutputStream),
				new RangeOutputStream(sampleOutputStream, fromIndex, toIndex) // Write only the 100 firsts bytes
		);

		// Wrap the InputStream so that it could be used in a foreach-style loop for a better readability
		for (final var i : Iterables.wrap(concatInputStream)) {
			teeOutputStream.write(i);
		}
		teeOutputStream.flush();
	}
}