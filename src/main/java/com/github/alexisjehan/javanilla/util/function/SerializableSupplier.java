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
package com.github.alexisjehan.javanilla.util.function;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Interface for a {@link Supplier} that is {@link Serializable}.
 *
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is {@link #get()}.</p>
 * @param <T> the type of results supplied by this supplier
 * @since 1.8.0
 */
@FunctionalInterface
public interface SerializableSupplier<T> extends Supplier<T>, Serializable {

	/**
	 * Create a {@code SerializableSupplier} from the given {@link Supplier}.
	 * @param supplier the {@link Supplier} to convert
	 * @param <T> the type of results supplied by this supplier
	 * @return the created {@code SerializableSupplier}
	 * @throws NullPointerException if the {@link Supplier} is {@code null}
	 * @since 1.8.0
	 */
	static <T> SerializableSupplier<T> of(final Supplier<? extends T> supplier) {
		Ensure.notNull("supplier", supplier);
		return supplier::get;
	}
}