/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
import java.util.function.Consumer;

/**
 * Interface for a {@link Consumer} that is {@link Serializable}.
 *
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is {@link #accept(Object)}.</p>
 * @param <T> the type of the input to the operation
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.function.SerializableConsumer} instead
 * @since 1.4.0
 */
@FunctionalInterface
@Deprecated(since = "1.8.0")
public interface SerializableConsumer<T> extends Consumer<T>, Serializable {

	/**
	 * Returns a composed {@code SerializableConsumer} that performs, in sequence, this operation followed by the after
	 * operation.
	 * @param after the {@link Consumer} operation to perform after this operation
	 * @return a composed {@code SerializableConsumer} that performs in sequence this operation followed by the after
	 *         operation
	 * @throws NullPointerException if the after {@link Consumer} is {@code null}
	 * @since 1.4.0
	 */
	@Override
	default SerializableConsumer<T> andThen(final Consumer<? super T> after) {
		Ensure.notNull("after", after);
		return t -> {
			accept(t);
			after.accept(t);
		};
	}

	/**
	 * Create a {@code SerializableConsumer} from the given {@link Consumer}.
	 * @param consumer the {@link Consumer} to convert
	 * @param <T> the type of the input to the operation
	 * @return the created {@code SerializableConsumer}
	 * @throws NullPointerException if the {@link Consumer} is {@code null}
	 * @since 1.4.0
	 */
	static <T> SerializableConsumer<T> of(final Consumer<? super T> consumer) {
		Ensure.notNull("consumer", consumer);
		return consumer::accept;
	}
}