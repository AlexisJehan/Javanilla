/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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

import com.github.alexisjehan.javanilla.misc.StringFormatter;

import java.util.Locale;

public final class StringFormatterExample {

	private StringFormatterExample() {}

	public static void main(final String... args) {
		final var floatPrecision = 3;
		final var stringFormatter = new StringFormatter(Locale.US, floatPrecision);

		System.out.println(stringFormatter.format(1_234_567L)); // Prints 1,234,567

		System.out.println(stringFormatter.formatBytes(1_234_567L)); // Prints 1.177MiB
		System.out.println(stringFormatter.formatBytes(1_234_567L, StringFormatter.BytePrefix.SI)); // Prints 1.235MB

		final var progression = 1.0d;
		final var total = 3.0d;
		System.out.println(stringFormatter.formatPercent(progression, total)); // Prints 33.333%

		System.out.println(stringFormatter.formatCurrency(123.456789d)); // Prints $123.457
	}
}