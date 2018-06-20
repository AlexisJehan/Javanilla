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
package com.github.alexisjehan.javanilla.util.function;

import com.github.alexisjehan.javanilla.lang.Strings;

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
	 * <p>Wrap a {@code Supplier} so that it only returns a value once. If the {@code Supplier} is supplied more than
	 * once a {@code IllegalStateException} is thrown.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param <T> the type of results supplied by this supplier
	 * @return the {@code Supplier} which returns a value once
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.0.0
	 */
	public static <T> Supplier<T> once(final Supplier<? extends T> supplier) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
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
	 * <p>Wrap a {@code Supplier} so that the first supplied value is cached for next times.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param <T> the type of results supplied by this supplier
	 * @return the {@code Supplier} which caches the first value
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.0.0
	 */
	public static <T> Supplier<T> cached(final Supplier<? extends T> supplier) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		return new Supplier<>() {
			private T value;
			private boolean isSupplied = false;

			@Override
			public T get() {
				if (!isSupplied) {
					value = supplier.get();
					isSupplied = true;
				}
				return value;
			}
		};
	}

	/**
	 * <p>Wrap a {@code Supplier} so that it caches the value supplied for the given {@code Duration} using
	 * {@link Clock#systemUTC()} before to refresh it.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param duration the cache {@code Duration}
	 * @param <T> the type of results supplied by this supplier
	 * @return the {@code Supplier} which caches values for a {@code Duration}
	 * @throws NullPointerException whether the {@code Supplier} or the {@code Duration} is {@code null}
	 * @throws IllegalArgumentException if the {@code Duration} is negative
	 * @since 1.1.0
	 */
	public static <T> Supplier<T> cached(final Supplier<? extends T> supplier, final Duration duration) {
		return cached(supplier, duration, Clock.systemUTC());
	}

	/**
	 * <p>Wrap a {@code Supplier} so that it caches the value supplied for the given {@code Duration} using the provided
	 * {@code Clock} before to refresh it.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param duration the cache {@code Duration}
	 * @param clock the {@code Clock}
	 * @param <T> the type of results supplied by this supplier
	 * @return the {@code Supplier} which caches values for a {@code Duration}
	 * @throws NullPointerException whether the {@code Supplier}, the {@code Duration} or the {@code Clock} is
	 * {@code null}
	 * @throws IllegalArgumentException if the {@code Duration} is negative
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Supplier<T> cached(final Supplier<? extends T> supplier, final Duration duration, final Clock clock) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		if (null == duration) {
			throw new NullPointerException("Invalid duration (not null expected)");
		}
		if (null == clock) {
			throw new NullPointerException("Invalid clock (not null expected)");
		}
		if (duration.isNegative()) {
			throw new IllegalArgumentException("Invalid duration: " + Strings.quote(duration) + " (zero or positive expected)");
		}
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
	 * <p>Wrap a {@code Supplier} so that it caches the value supplied for the given amount of times before to refresh
	 * it.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param times the cache amount of times
	 * @param <T> the type of results supplied by this supplier
	 * @return the {@code Supplier} which caches values for an amount of times
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @throws IllegalArgumentException if the amount of times is negative
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Supplier<T> cached(final Supplier<? extends T> supplier, final int times) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		if (0 > times) {
			throw new IllegalArgumentException("Invalid times: " + times + " (greater than or equal to 0 expected)");
		}
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
	 * <p>Wrap a {@code Supplier} so that it caches the value supplied and refresh it when the {@code BooleanSupplier}
	 * supplies {@code true}.</p>
	 * @param supplier the {@code Supplier} to wrap
	 * @param refreshSupplier the refresh {@code BooleanSupplier}
	 * @param <T> the type of results supplied by this supplier
	 * @return the {@code Supplier} which caches values until the refresh {@code BooleanSupplier} supplies {@code true}
	 * @throws NullPointerException whether the {@code Supplier} or the {@code BooleanSupplier} is {@code null}
	 * @since 1.1.0
	 */
	public static <T> Supplier<T> cached(final Supplier<? extends T> supplier, final BooleanSupplier refreshSupplier) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		if (null == refreshSupplier) {
			throw new NullPointerException("Invalid refresh supplier (not null expected)");
		}
		return new Supplier<>() {
			private T value;
			private boolean isSupplied = false;

			@Override
			public T get() {
				if (!isSupplied || refreshSupplier.getAsBoolean()) {
					value = supplier.get();
					isSupplied = true;
				}
				return value;
			}
		};
	}
}