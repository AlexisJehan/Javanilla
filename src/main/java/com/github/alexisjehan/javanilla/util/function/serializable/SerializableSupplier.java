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
package com.github.alexisjehan.javanilla.util.function.serializable;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * <p>Interface for a {@link Supplier} that is {@link Serializable}.</p>
 * @param <T> the type of results supplied by this supplier
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.function.SerializableSupplier} instead
 * @since 1.4.0
 */
@FunctionalInterface
@Deprecated(since = "1.8.0")
public interface SerializableSupplier<T> extends Supplier<T>, Serializable {

	/**
	 * <p>Create a {@code SerializableSupplier} from the given {@link Supplier}.</p>
	 * @param supplier the {@link Supplier} to convert
	 * @param <T> the type of results supplied by this supplier
	 * @return the created {@code SerializableSupplier}
	 * @throws NullPointerException if the {@link Supplier} is {@code null}
	 * @since 1.4.0
	 */
	static <T> SerializableSupplier<T> of(final Supplier<? extends T> supplier) {
		Ensure.notNull("supplier", supplier);
		return supplier::get;
	}
}