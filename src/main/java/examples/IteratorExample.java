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

import com.github.alexisjehan.javanilla.util.iteration.BatchIterator;
import com.github.alexisjehan.javanilla.util.iteration.CountIterator;
import com.github.alexisjehan.javanilla.util.iteration.Iterables;
import com.github.alexisjehan.javanilla.util.iteration.Iterators;

public final class IteratorExample {

	private IteratorExample() {
		// Not available
	}

	public static void main(final String... args) {
		// Iterator to iterate over groups of integers
		final var batchSize = 3;
		final var batchIterator = new BatchIterator<>(
				Iterators.ofInt(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
				batchSize
		);

		// Iterator that counts iterated groups
		final var countIterator = new CountIterator<>(batchIterator);

		// Wrap the Iterator to be used in a foreach-style loop for a better readability
		for (final var list : Iterables.wrap(countIterator)) {
			System.out.println(list); // Prints [1, 2, 3], [4, 5, 6], [7, 8, 9] and [10]
		}
		System.out.println(countIterator.getCount()); // Prints 4
	}
}