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
package com.github.alexisjehan.javanilla.util.function;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.ToString;
import com.github.alexisjehan.javanilla.misc.tuples.Single;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * <p>An utility class that provides {@link Supplier} tools.</p>
 * @since 1.0.0
 */
public final class Suppliers {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Suppliers() {
		// Not available
	}

	/**
	 * <p>Decorate a {@code Supplier} so that it only returns a value once. If the {@code Supplier} is supplied more
	 * than once an {@code IllegalStateException} is thrown.</p>
	 * @param supplier the {@code Supplier} to decorate
	 * @param <T> the type of results supplied by this supplier
	 * @return the {@code Supplier} which returns a value once
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.0.0
	 */
	public static <T> Supplier<T> once(final Supplier<? extends T> supplier) {
		Ensure.notNull("supplier", supplier);
		return new Supplier<>() {
			private boolean isSupplied = false;

			@Override
			public T get() {
				if (isSupplied) {
					throw new IllegalStateException("A result cannot be supplied more than once");
				}
				isSupplied = true;
				return supplier.get();
			}
		};
	}

	/**
	 * <p>Decorate a {@code Supplier} so that the first supplied value is cached for next times.</p>
	 * @param supplier the {@code Supplier} to decorate
	 * @param <T> the type of results supplied by this supplier
	 * @return the cached {@code Supplier}
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.0.0
	 */
	public static <T> Supplier<T> cache(final Supplier<? extends T> supplier) {
		Ensure.notNull("supplier", supplier);
		return new Supplier<>() {
			private Single<? extends T> cache;

			@Override
			public T get() {
				if (null == cache) {
					cache = Single.of(supplier.get());
				}
				return cache.getUnique();
			}
		};
	}

	/**
	 * <p>Decorate a {@code Supplier} so that it caches the value supplied for the given {@code Duration} using
	 * {@link Clock#systemUTC()} before to refresh it.</p>
	 * @param supplier the {@code Supplier} to decorate
	 * @param duration the cache {@code Duration}
	 * @param <T> the type of results supplied by this supplier
	 * @return the cached {@code Supplier}
	 * @throws NullPointerException if the {@code Supplier} or the {@code Duration} is {@code null}
	 * @throws IllegalArgumentException if the {@code Duration} is negative
	 * @since 1.1.0
	 */
	public static <T> Supplier<T> cache(final Supplier<? extends T> supplier, final Duration duration) {
		return cache(supplier, duration, Clock.systemUTC());
	}

	/**
	 * <p>Decorate a {@code Supplier} so that it caches the value supplied for the given {@code Duration} using the
	 * provided {@code Clock} before to refresh it.</p>
	 * @param supplier the {@code Supplier} to decorate
	 * @param duration the cache {@code Duration}
	 * @param clock the {@code Clock} to use
	 * @param <T> the type of results supplied by this supplier
	 * @return the cached {@code Supplier}
	 * @throws NullPointerException if the {@code Supplier}, the {@code Duration} or the {@code Clock} is {@code null}
	 * @throws IllegalArgumentException if the {@code Duration} is negative
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Supplier<T> cache(final Supplier<? extends T> supplier, final Duration duration, final Clock clock) {
		Ensure.notNull("supplier", supplier);
		Ensure.notNull("duration", duration);
		if (duration.isNegative()) {
			throw new IllegalArgumentException("Invalid duration: " + ToString.toString(duration) + " (zero or positive expected)");
		}
		Ensure.notNull("clock", clock);
		if (duration.isZero()) {
			return (Supplier<T>) supplier;
		}
		return new Supplier<>() {
			private T value;
			private Instant lastSupplied;

			@Override
			public T get() {
				final var now = Instant.now(clock);
				if (null == lastSupplied || 0 >= duration.compareTo(Duration.between(lastSupplied, now))) {
					value = supplier.get();
					lastSupplied = now;
				}
				return value;
			}
		};
	}

	/**
	 * <p>Decorate a {@code Supplier} so that it caches the value supplied for the given amount of times before to
	 * refresh it.</p>
	 * @param supplier the {@code Supplier} to decorate
	 * @param times the cache amount of times
	 * @param <T> the type of results supplied by this supplier
	 * @return the cached {@code Supplier}
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @throws IllegalArgumentException if the amount of times is lower than {@code 0}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Supplier<T> cache(final Supplier<? extends T> supplier, final int times) {
		Ensure.notNull("supplier", supplier);
		Ensure.greaterThanOrEqualTo("times", times, 0);
		if (0 == times) {
			return (Supplier<T>) supplier;
		}
		return new Supplier<>() {
			private T value;
			private int remaining = -1;

			@Override
			public T get() {
				if (-1 == remaining || 0 == remaining--) {
					value = supplier.get();
					remaining = times;
				}
				return value;
			}
		};
	}

	/**
	 * <p>Decorate a {@code Supplier} so that it caches the value supplied and refresh it when the
	 * {@code BooleanSupplier} supplies {@code true}.</p>
	 * @param supplier the {@code Supplier} to decorate
	 * @param booleanSupplier the {@code BooleanSupplier}
	 * @param <T> the type of results supplied by this supplier
	 * @return the cached {@code Supplier}
	 * @throws NullPointerException if the {@code Supplier} or the {@code BooleanSupplier} is {@code null}
	 * @since 1.1.0
	 */
	public static <T> Supplier<T> cache(final Supplier<? extends T> supplier, final BooleanSupplier booleanSupplier) {
		Ensure.notNull("supplier", supplier);
		Ensure.notNull("booleanSupplier", booleanSupplier);
		return new Supplier<>() {
			private T value;
			private boolean isSupplied = false;

			@Override
			public T get() {
				if (!isSupplied || booleanSupplier.getAsBoolean()) {
					value = supplier.get();
					isSupplied = true;
				}
				return value;
			}
		};
	}
}