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
package examples;

import com.github.alexisjehan.javanilla.misc.distances.Distances;
import com.github.alexisjehan.javanilla.misc.distances.EditDistances;
import com.github.alexisjehan.javanilla.misc.distances.LevenshteinDistance;
import com.github.alexisjehan.javanilla.misc.distances.MinkowskiDistance;

public final class Example07 {

	public static void main(final String... args) {
		System.out.println(Distances.MANHATTAN.calculate(0.0d, 0.0d, 1.0d, 1.0d)); // Prints 2
		final var order = 1;
		System.out.println(new MinkowskiDistance(order).calculate(0.0d, 1.0d, 2.0d, 3.0d)); // Prints 4
		System.out.println(EditDistances.HAMMING.calculate("foo", "for")); // Prints 1
		System.out.println(new LevenshteinDistance().calculate("append", "apple")); // Prints 3
	}
}