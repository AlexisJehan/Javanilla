/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.lang.array.ByteArrays;

import java.util.Base64;

public final class StringExample {

	private StringExample() {
		// Not available
	}

	@SuppressWarnings("deprecation")
	public static void main(final String... args) {
		System.out.println(Strings.blankToEmpty("   ")); // Prints an empty String
		System.out.println(Strings.quote("A quoted String with an escaped \" double quote")); // Prints "A quoted String with an escaped \" double quote"
		final var times = 5;
		System.out.println(Strings.repeat("xX", times)); // Prints "xXxXxXxXxX"
		final var size = 5;
		System.out.println(Strings.padLeft("foo", size)); // Prints "  foo"
		final var suffix = 'o';
		System.out.println(Strings.removeEnd("foo", suffix)); // Prints "fo"
		final var target = 'o';
		final var replacement = 'r';
		System.out.println(Strings.replaceLast("foo", target, replacement)); // Prints "for"
		System.out.println(Strings.concatMerge("Once upon a time ...", "... the end")); // Prints "Once upon a time ... the end"
		System.out.println(Strings.isHexadecimal(ByteArrays.toHexadecimalString("foo".getBytes())) ? "yes" : "no"); // Prints "yes"
		final var withPadding = true;
		System.out.println(Strings.isBase64(Base64.getEncoder().encodeToString("foo".getBytes()), withPadding) ? "yes" : "no"); // Prints "yes"
	}
}