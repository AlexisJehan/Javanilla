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
package com.github.alexisjehan.javanilla.util;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.PrimitiveIterator;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 * A base type for primitive specializations of {@link Iterable}. Specialized subtypes are provided for {@code int},
 * {@code long}, and {@code double} values.
 *
 * <p><b>Note</b>: This interface is based on {@link PrimitiveIterator}.</p>
 * @param <T> the type of elements returned by the provided PrimitiveIterator
 * @param <C> the type of primitive consumer
 * @since 1.8.0
 */
public interface PrimitiveIterable<T, C> extends Iterable<T> {

	/**
	 * Performs the given action for each element of the {@link Iterable} until all elements have been processed or the
	 * action throws an {@link Exception}. Unless otherwise specified by the implementing class, actions are performed
	 * in the order of iteration (if an iteration order is specified). {@link Exception}s thrown by the action are
	 * relayed to the caller.
	 * @param action the action to be performed for each element
	 * @throws NullPointerException if the specified action is {@code null}
	 * @since 1.8.0
	 */
	void forEach(C action);

	/**
	 * An {@link Iterable} specialized for {@code int} values.
	 *
	 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is
	 * {@link #forEach(IntConsumer)}.</p>
	 * @since 1.8.0
	 */
	@FunctionalInterface
	interface OfInt extends PrimitiveIterable<Integer, IntConsumer> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		PrimitiveIterator.OfInt iterator();

		/**
		 * {@inheritDoc}
		 */
		@Override
		@SuppressWarnings("overloads")
		default void forEach(final IntConsumer action) {
			Ensure.notNull("action", action);
			for (final var t : this) {
				action.accept(t);
			}
		}
	}

	/**
	 * An {@link Iterable} specialized for {@code long} values.
	 *
	 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is
	 * {@link #forEach(LongConsumer)}.</p>
	 * @since 1.8.0
	 */
	@FunctionalInterface
	interface OfLong extends PrimitiveIterable<Long, LongConsumer> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		PrimitiveIterator.OfLong iterator();

		/**
		 * {@inheritDoc}
		 */
		@Override
		@SuppressWarnings("overloads")
		default void forEach(final LongConsumer action) {
			Ensure.notNull("action", action);
			for (final var t : this) {
				action.accept(t);
			}
		}
	}

	/**
	 * An {@link Iterable} specialized for {@code double} values.
	 *
	 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is
	 * {@link #forEach(DoubleConsumer)}.</p>
	 * @since 1.8.0
	 */
	@FunctionalInterface
	interface OfDouble extends PrimitiveIterable<Double, DoubleConsumer> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		PrimitiveIterator.OfDouble iterator();

		/**
		 * {@inheritDoc}
		 */
		@Override
		@SuppressWarnings("overloads")
		default void forEach(final DoubleConsumer action) {
			Ensure.notNull("action", action);
			for (final var t : this) {
				action.accept(t);
			}
		}
	}
}