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

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Suppliers} unit tests.</p>
 */
final class SuppliersTest {

	@Test
	void testOnce() {
		final var onceSupplier = Suppliers.once(() -> 1);
		assertThat(onceSupplier.get()).isEqualTo(1);
		assertThatIllegalStateException().isThrownBy(onceSupplier::get);
	}

	@Test
	void testOnceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Suppliers.once(null));
	}

	@Test
	void testCache() {
		final var cachedSupplier = Suppliers.cache(new Supplier<>() {
			private int i = 0;

			@Override
			public Integer get() {
				return ++i;
			}
		});
		assertThat(cachedSupplier.get()).isEqualTo(1);
		assertThat(cachedSupplier.get()).isEqualTo(1);
	}

	@Test
	void testCacheInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Suppliers.cache(null));
	}

	@Test
	void testCacheDuration() {
		{
			final var cachedSupplier = Suppliers.cache(new Supplier<>() {
				private int i = 0;

				@Override
				public Integer get() {
					return ++i;
				}
			}, Duration.ZERO);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(2);
			assertThat(cachedSupplier.get()).isEqualTo(3);
		}
		{
			final var cachedSupplier = Suppliers.cache(new Supplier<>() {
				private int i = 0;

				@Override
				public Integer get() {
					return ++i;
				}
			}, Duration.ofMinutes(2L), new Clock() {
				private Instant nextInstant = Instant.now(Clock.systemUTC());

				@Override
				public ZoneId getZone() {
					return Clock.systemUTC().getZone();
				}

				@Override
				public Clock withZone(final ZoneId zone) {
					return Clock.systemUTC().withZone(zone);
				}

				@Override
				public Instant instant() {
					final var instant = nextInstant;
					nextInstant = instant.plus(1L, ChronoUnit.MINUTES);
					return instant;
				}
			});
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(2);
			assertThat(cachedSupplier.get()).isEqualTo(2);
			assertThat(cachedSupplier.get()).isEqualTo(3);
			assertThat(cachedSupplier.get()).isEqualTo(3);
		}
	}

	@Test
	void testCacheDurationInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Suppliers.cache(null, Duration.ofMillis(1L)));
		assertThatNullPointerException().isThrownBy(() -> Suppliers.cache(() -> 1, (Duration) null));
		assertThatNullPointerException().isThrownBy(() -> Suppliers.cache(() -> 1, Duration.ofMillis(1L), null));
		assertThatIllegalArgumentException().isThrownBy(() -> Suppliers.cache(() -> 1, Duration.ofMinutes(-1L)));
	}

	@Test
	void testCacheTimes() {
		{
			final var cachedSupplier = Suppliers.cache(new Supplier<>() {
				private int i = 0;

				@Override
				public Integer get() {
					return ++i;
				}
			}, 0);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(2);
			assertThat(cachedSupplier.get()).isEqualTo(3);
		}
		{
			final var cachedSupplier = Suppliers.cache(new Supplier<>() {
				private int i = 0;

				@Override
				public Integer get() {
					return ++i;
				}
			}, 2);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(2);
			assertThat(cachedSupplier.get()).isEqualTo(2);
			assertThat(cachedSupplier.get()).isEqualTo(2);
			assertThat(cachedSupplier.get()).isEqualTo(3);
		}
	}

	@Test
	void testCacheTimesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Suppliers.cache(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> Suppliers.cache(() -> 1, -1));
	}

	@Test
	void testCacheBooleanSupplier() {
		{
			final var cachedSupplier = Suppliers.cache(new Supplier<>() {
				private int i = 0;

				@Override
				public Integer get() {
					return ++i;
				}
			}, () -> true);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(2);
			assertThat(cachedSupplier.get()).isEqualTo(3);
		}
		{
			final var cachedSupplier = Suppliers.cache(new Supplier<>() {
				private int i = 0;

				@Override
				public Integer get() {
					return ++i;
				}
			}, () -> false);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(1);
			assertThat(cachedSupplier.get()).isEqualTo(1);
		}
	}

	@Test
	void testCacheBooleanSupplierInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Suppliers.cache(null, () -> true));
		assertThatNullPointerException().isThrownBy(() -> Suppliers.cache(() -> 1, (BooleanSupplier) null));
	}
}