/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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

import com.github.alexisjehan.javanilla.misc.distances.Distances;
import com.github.alexisjehan.javanilla.misc.distances.EditDistances;
import com.github.alexisjehan.javanilla.misc.distances.LevenshteinDistance;
import com.github.alexisjehan.javanilla.misc.distances.MinkowskiDistance;

public final class DistanceExample {

	private DistanceExample() {
		// Not available
	}

	public static void main(final String... args) {
		final var x1 = 0.0d;
		final var y1 = 0.0d;
		final var x2 = 1.0d;
		final var y2 = 2.0d;
		System.out.println(Distances.MANHATTAN.calculate(x1, y1, x2, y2)); // Prints 3
		final var order = 1;
		System.out.println(new MinkowskiDistance(order).calculate(x1, y1, x2, y2)); // Prints 3
		System.out.println(EditDistances.HAMMING.calculate("foo", "for")); // Prints 1
		System.out.println(LevenshteinDistance.DEFAULT.calculate("append", "apple")); // Prints 3
	}
}