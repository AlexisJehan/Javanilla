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
package com.github.alexisjehan.javanilla.util.function.serializable;

import com.github.alexisjehan.javanilla.io.Serializables;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link SerializablePredicate} unit tests.</p>
 */
final class SerializablePredicateTest {

	@Test
	void testTest() {
		final var serializablePredicate = (SerializablePredicate<Integer>) t -> t >= 2;
		assertThat(serializablePredicate.test(1)).isFalse();
		assertThat(serializablePredicate.test(3)).isTrue();
	}

	@Test
	void testAnd() {
		final var serializablePredicate1 = (SerializablePredicate<Integer>) t -> t >= 2;
		final var serializablePredicate2 = (SerializablePredicate<Integer>) t -> t <= 2;
		assertThat(serializablePredicate1.and(serializablePredicate2).test(1)).isFalse();
		assertThat(serializablePredicate1.and(serializablePredicate2).test(3)).isFalse();
		assertThat(serializablePredicate2.and(serializablePredicate1).test(1)).isFalse();
		assertThat(serializablePredicate2.and(serializablePredicate1).test(3)).isFalse();
		assertThat(serializablePredicate1.and(serializablePredicate1).test(1)).isFalse();
		assertThat(serializablePredicate1.and(serializablePredicate1).test(3)).isTrue();
		assertThat(serializablePredicate2.and(serializablePredicate2).test(1)).isTrue();
		assertThat(serializablePredicate2.and(serializablePredicate2).test(3)).isFalse();
	}

	@Test
	void testAndInvalid() {
		final var serializablePredicate = (SerializablePredicate<Integer>) t -> t >= 2;
		assertThatNullPointerException().isThrownBy(() -> serializablePredicate.and(null));
	}

	@Test
	void testNegate() {
		final var serializablePredicate = (SerializablePredicate<Integer>) t -> t >= 2;
		assertThat(serializablePredicate.negate().test(1)).isTrue();
		assertThat(serializablePredicate.negate().test(3)).isFalse();
	}

	@Test
	void testOr() {
		final var serializablePredicate1 = (SerializablePredicate<Integer>) t -> t >= 2;
		final var serializablePredicate2 = (SerializablePredicate<Integer>) t -> t <= 2;
		assertThat(serializablePredicate1.or(serializablePredicate2).test(1)).isTrue();
		assertThat(serializablePredicate1.or(serializablePredicate2).test(3)).isTrue();
		assertThat(serializablePredicate2.or(serializablePredicate1).test(1)).isTrue();
		assertThat(serializablePredicate2.or(serializablePredicate1).test(3)).isTrue();
		assertThat(serializablePredicate1.or(serializablePredicate1).test(1)).isFalse();
		assertThat(serializablePredicate1.or(serializablePredicate1).test(3)).isTrue();
		assertThat(serializablePredicate2.or(serializablePredicate2).test(1)).isTrue();
		assertThat(serializablePredicate2.or(serializablePredicate2).test(3)).isFalse();
	}

	@Test
	void testOrInvalid() {
		final var serializablePredicate = (SerializablePredicate<Integer>) t -> t >= 2;
		assertThatNullPointerException().isThrownBy(() -> serializablePredicate.or(null));
	}

	@Test
	void testIsEqual() {
		final var serializablePredicate1 = SerializablePredicate.isEqual(1);
		final var serializablePredicate2 = SerializablePredicate.isEqual(null);
		assertThat(serializablePredicate1.test(null)).isFalse();
		assertThat(serializablePredicate1.test(1)).isTrue();
		assertThat(serializablePredicate2.test(null)).isTrue();
		assertThat(serializablePredicate2.test(3)).isFalse();
	}

	@Test
	void testOf() {
		final var serializablePredicate = SerializablePredicate.of((Predicate<Integer>) t -> t >= 2);
		assertThat(serializablePredicate.test(1)).isFalse();
		assertThat(serializablePredicate.test(3)).isTrue();
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> SerializablePredicate.of(null));
	}

	@Test
	void testSerializable() {
		final var serializablePredicate = Serializables.<SerializablePredicate<Integer>>deserialize(
				Serializables.serialize(
						(SerializablePredicate<Integer>) t -> t >= 2
				)
		);
		assertThat(serializablePredicate.test(1)).isFalse();
		assertThat(serializablePredicate.test(3)).isTrue();
	}
}