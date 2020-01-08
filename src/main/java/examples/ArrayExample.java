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

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;
import com.github.alexisjehan.javanilla.lang.array.FloatArrays;
import com.github.alexisjehan.javanilla.lang.array.IntArrays;
import com.github.alexisjehan.javanilla.lang.array.LongArrays;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;

import java.nio.ByteOrder;

public final class ArrayExample {

	private ArrayExample() {
		// Not available
	}

	public static void main(final String... args) {
		IntArrays.of(1, 2, 3); // Similar to new int[] {1, 2, 3};
		ObjectArrays.of("foo", "bar"); // Similar to new String[] {"foo", "bar"};
		LongArrays.concat(LongArrays.of(1L, 2L, 3L), LongArrays.of(4L, 5L, 6L)); // 1, 2, 3, 4, 5, 6
		final var separator = CharArrays.singleton(' ');
		CharArrays.join(separator, CharArrays.of('f', 'o', 'o'), CharArrays.of('b', 'a', 'r')); // 'f', 'o', 'o', ' ', 'b', 'a', 'r'
		FloatArrays.containsAll(FloatArrays.of(0.0f, 1.0f, 2.0f), 2.0f, 3.0f); // False
		FloatArrays.containsAll(FloatArrays.of(1.0f, 2.0f, 3.0f), 2.0f, 3.0f); // True
		DoubleArrays.indexOf(DoubleArrays.of(1.0d, 2.0d, 3.0d), 2.0d); // 1
		ByteArrays.toHexadecimalString(
				ByteArrays.concat(
						ByteArrays.of((byte) 0x00, (byte) 0xff),
						ByteArrays.ofDouble(3.14d, ByteOrder.BIG_ENDIAN),
						ByteArrays.ofHexadecimalString("0xff"),
						"foo".getBytes()
				)
		); // 00ff40091eb851eb851fff666f6f
	}
}