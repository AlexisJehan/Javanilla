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
package com.github.alexisjehan.javanilla.util.function;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * Interface for a {@link BiConsumer} that is {@link Serializable}.
 *
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is
 * {@link #accept(Object, Object)}.</p>
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @since 1.8.0
 */
@FunctionalInterface
public interface SerializableBiConsumer<T, U> extends BiConsumer<T, U>, Serializable {

	/**
	 * Returns a composed {@code SerializableBiConsumer} that performs, in sequence, this operation followed by the
	 * after operation.
	 * @param after the {@link BiConsumer} operation to perform after this operation
	 * @return a composed {@code SerializableBiConsumer} that performs in sequence this operation followed by the after
	 *         operation
	 * @throws NullPointerException if the after {@link BiConsumer} is {@code null}
	 * @since 1.8.0
	 */
	@Override
	default SerializableBiConsumer<T, U> andThen(final BiConsumer<? super T, ? super U> after) {
		Ensure.notNull("after", after);
		return (t, u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}

	/**
	 * Create a {@code SerializableBiConsumer} from the given {@link BiConsumer}.
	 * @param biConsumer the {@link BiConsumer} to convert
	 * @param <T> the type of the first argument to the operation
	 * @param <U> the type of the second argument to the operation
	 * @return the created {@code SerializableBiConsumer}
	 * @throws NullPointerException if the {@link BiConsumer} is {@code null}
	 * @since 1.8.0
	 */
	static <T, U> SerializableBiConsumer<T, U> of(final BiConsumer<? super T, ? super U> biConsumer) {
		Ensure.notNull("biConsumer", biConsumer);
		return biConsumer::accept;
	}
}