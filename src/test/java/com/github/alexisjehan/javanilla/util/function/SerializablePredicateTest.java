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
package com.github.alexisjehan.javanilla.util.function;

import com.github.alexisjehan.javanilla.io.Serializables;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class SerializablePredicateTest {

	@Test
	void testTest() {
		final var serializablePredicate = (SerializablePredicate<Integer>) t -> t >= 2;
		assertThat(serializablePredicate.test(1)).isFalse();
		assertThat(serializablePredicate.test(3)).isTrue();
	}

	@Test
	void testAnd() {
		final var fooSerializablePredicate = (SerializablePredicate<Integer>) t -> t >= 2;
		final var barSerializablePredicate = (SerializablePredicate<Integer>) t -> t <= 2;
		assertThat(fooSerializablePredicate.and(barSerializablePredicate).test(1)).isFalse();
		assertThat(fooSerializablePredicate.and(barSerializablePredicate).test(3)).isFalse();
		assertThat(barSerializablePredicate.and(fooSerializablePredicate).test(1)).isFalse();
		assertThat(barSerializablePredicate.and(fooSerializablePredicate).test(3)).isFalse();
		assertThat(fooSerializablePredicate.and(fooSerializablePredicate).test(1)).isFalse();
		assertThat(fooSerializablePredicate.and(fooSerializablePredicate).test(3)).isTrue();
		assertThat(barSerializablePredicate.and(barSerializablePredicate).test(1)).isTrue();
		assertThat(barSerializablePredicate.and(barSerializablePredicate).test(3)).isFalse();
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
		final var fooSerializablePredicate = (SerializablePredicate<Integer>) t -> t >= 2;
		final var barSerializablePredicate = (SerializablePredicate<Integer>) t -> t <= 2;
		assertThat(fooSerializablePredicate.or(barSerializablePredicate).test(1)).isTrue();
		assertThat(fooSerializablePredicate.or(barSerializablePredicate).test(3)).isTrue();
		assertThat(barSerializablePredicate.or(fooSerializablePredicate).test(1)).isTrue();
		assertThat(barSerializablePredicate.or(fooSerializablePredicate).test(3)).isTrue();
		assertThat(fooSerializablePredicate.or(fooSerializablePredicate).test(1)).isFalse();
		assertThat(fooSerializablePredicate.or(fooSerializablePredicate).test(3)).isTrue();
		assertThat(barSerializablePredicate.or(barSerializablePredicate).test(1)).isTrue();
		assertThat(barSerializablePredicate.or(barSerializablePredicate).test(3)).isFalse();
	}

	@Test
	void testOrInvalid() {
		final var serializablePredicate = (SerializablePredicate<Integer>) t -> t >= 2;
		assertThatNullPointerException().isThrownBy(() -> serializablePredicate.or(null));
	}

	@Test
	void testIsEqual() {
		final var fooSerializablePredicate = SerializablePredicate.isEqual(1);
		final var barSerializablePredicate = SerializablePredicate.isEqual(null);
		assertThat(fooSerializablePredicate.test(null)).isFalse();
		assertThat(fooSerializablePredicate.test(1)).isTrue();
		assertThat(barSerializablePredicate.test(null)).isTrue();
		assertThat(barSerializablePredicate.test(3)).isFalse();
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
		final var deserializedSerializablePredicate = Serializables.<SerializablePredicate<Integer>>deserialize(
				Serializables.serialize(
						(SerializablePredicate<Integer>) t -> t >= 2
				)
		);
		assertThat(deserializedSerializablePredicate.test(1)).isFalse();
		assertThat(deserializedSerializablePredicate.test(3)).isTrue();
	}
}