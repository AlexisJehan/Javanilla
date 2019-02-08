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
package com.github.alexisjehan.javanilla.util.function.serializable;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * <p>Interface for a {@link Consumer} that is {@link Serializable}.</p>
 * @param <T> the type of the input to the operation
 * @since 1.4.0
 */
@FunctionalInterface
public interface SerializableConsumer<T> extends Consumer<T>, Serializable {

	/**
	 * <p>Returns a composed {@code SerializableConsumer} that performs, in sequence, this operation followed by the
	 * after operation.</p>
	 * @param after the {@code Consumer} operation to perform after this operation
	 * @return a composed {@code SerializableConsumer} that performs in sequence this operation followed by the after
	 * operation
	 * @throws NullPointerException if the after {@code Consumer} is {@code null}
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
	 * <p>Create a {@code SerializableConsumer} from the given {@code Consumer}.</p>
	 * @param consumer the {@code Consumer} to convert
	 * @param <T> the type of the input to the operation
	 * @return the created {@code SerializableConsumer}
	 * @throws NullPointerException if the {@code Consumer} is {@code null}
	 * @since 1.4.0
	 */
	static <T> SerializableConsumer<T> of(final Consumer<? super T> consumer) {
		Ensure.notNull("consumer", consumer);
		return consumer::accept;
	}
}