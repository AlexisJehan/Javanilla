/*
 * MIT License
 *
 * Copyright (c) 2018-2023 Alexis Jehan
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

import com.github.alexisjehan.javanilla.lang.Throwables;
import com.github.alexisjehan.javanilla.util.function.ThrowableProcedure;
import com.github.alexisjehan.javanilla.util.function.ThrowableSupplier;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class ThrowableExample {

	private ThrowableExample() {
		// Not available
	}

	public static void main(final String... args) {
		// Sleep 5 seconds and throw an unchecked Exception if the thread is interrupted, no try/catch required
		final var millis = 5_000L;
		ThrowableProcedure.unchecked(() -> Thread.sleep(millis)).execute();

		// Still no try/catch required, but this time keep the original checked exception
		ThrowableSupplier.sneaky(() -> {
			throw new IOException("A checked Exception inside a lambda");
		});

		try {
			throw new UncheckedIOException(new IOException());
		} catch (final Exception e) {
			System.out.println(Throwables.isUncheckedException(e)); // Prints true
			System.out.println(Throwables.getOptionalRootCause(e).orElseThrow().getClass().getName()); // Prints java.io.IOException
		}
	}
}