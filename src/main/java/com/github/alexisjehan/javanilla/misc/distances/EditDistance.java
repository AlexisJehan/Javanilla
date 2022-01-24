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
package com.github.alexisjehan.javanilla.misc.distances;

/**
 * <p>Interface for edit distance functions that work on {@link CharSequence}s.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Edit_distance">https://en.wikipedia.org/wiki/Edit_distance</a>
 * @see <a href="https://en.wikipedia.org/wiki/String_metric">https://en.wikipedia.org/wiki/String_metric</a>
 * @since 1.0.0
 */
@FunctionalInterface
public interface EditDistance {

	/**
	 * <p>Calculate an edit distance between both {@link CharSequence}s.</p>
	 * @param charSequence1 the first {@link CharSequence}
	 * @param charSequence2 the second {@link CharSequence}
	 * @return the calculated edit distance
	 * @throws NullPointerException if any {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	double calculate(final CharSequence charSequence1, final CharSequence charSequence2);
}