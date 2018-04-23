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
package com.github.javanilla.util.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.PrimitiveIterator;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 * <p>A base type for primitive specializations of {@link Iterable}. Specialized subtypes are provided for {@code int},
 * {@code long}, and {@code double} values.</p>
 * <p><b>Note</b>: This class is in continuation of {@link PrimitiveIterator}.</p>
 * @param <T> the type of elements returned by the provided PrimitiveIterator
 * @param <C> the type of primitive consumer
 * @since 1.0
 */
public interface PrimitiveIterable<T, C> extends Iterable<T> {

	/**
	 * <p>Performs the given action for each element of the {@code Iterable} until all elements have been processed or
	 * the action throws an exception. Unless otherwise specified by the implementing class, actions are performed in
	 * the order of iteration (if an iteration order is specified). Exceptions thrown by the action are relayed to the
	 * caller.</p>
	 * @param action The action to be performed for each element
	 * @throws NullPointerException if the specified action is {@code null}
	 * @since 1.0
	 */
	@SuppressWarnings("overloads")
	void forEach(final C action);

	/**
	 * <p>An {@code Iterable} specialized for {@code int} values.</p>
	 * @since 1.0
	 */
	interface OfInt extends PrimitiveIterable<Integer, IntConsumer> {

		@NotNull
		@Override
		PrimitiveIterator.OfInt iterator();

		@Override
		default void forEach(final IntConsumer action) {
			if (null == action) {
				throw new NullPointerException("Invalid action (not null expected)");
			}
			for (final var t : this) {
				action.accept(t);
			}
		}
	}

	/**
	 * <p>An {@code Iterable} specialized for {@code long} values.</p>
	 * @since 1.0
	 */
	interface OfLong extends PrimitiveIterable<Long, LongConsumer> {

		@NotNull
		@Override
		PrimitiveIterator.OfLong iterator();

		@Override
		default void forEach(final LongConsumer action) {
			if (null == action) {
				throw new NullPointerException("Invalid action (not null expected)");
			}
			for (final var t : this) {
				action.accept(t);
			}
		}
	}

	/**
	 * <p>An {@code Iterable} specialized for {@code double} values.</p>
	 * @since 1.0
	 */
	interface OfDouble extends PrimitiveIterable<Double, DoubleConsumer> {

		@NotNull
		@Override
		PrimitiveIterator.OfDouble iterator();

		@Override
		default void forEach(final DoubleConsumer action) {
			if (null == action) {
				throw new NullPointerException("Invalid action (not null expected)");
			}
			for (final var t : this) {
				action.accept(t);
			}
		}
	}
}