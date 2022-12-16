/*
 * MIT License
 *
 * Copyright (c) 2018-2022 Alexis Jehan
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

import java.util.function.BiPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class SerializableBiPredicateTest {

	@Test
	void testTest() {
		final var serializableBiPredicate = (SerializableBiPredicate<Integer, Integer>) (t, u) -> t >= u;
		assertThat(serializableBiPredicate.test(1, 2)).isFalse();
		assertThat(serializableBiPredicate.test(3, 3)).isTrue();
	}

	@Test
	void testAnd() {
		final var fooSerializableBiPredicate = (SerializableBiPredicate<Integer, Integer>) (t, u) -> t >= u;
		final var barSerializableBiPredicate = (SerializableBiPredicate<Integer, Integer>) (t, u) -> t <= u;
		assertThat(fooSerializableBiPredicate.and(barSerializableBiPredicate).test(1, 2)).isFalse();
		assertThat(fooSerializableBiPredicate.and(barSerializableBiPredicate).test(3, 3)).isTrue();
		assertThat(barSerializableBiPredicate.and(fooSerializableBiPredicate).test(1, 2)).isFalse();
		assertThat(barSerializableBiPredicate.and(fooSerializableBiPredicate).test(3, 3)).isTrue();
		assertThat(fooSerializableBiPredicate.and(fooSerializableBiPredicate).test(1, 2)).isFalse();
		assertThat(fooSerializableBiPredicate.and(fooSerializableBiPredicate).test(3, 3)).isTrue();
		assertThat(barSerializableBiPredicate.and(barSerializableBiPredicate).test(1, 2)).isTrue();
		assertThat(barSerializableBiPredicate.and(barSerializableBiPredicate).test(3, 3)).isTrue();
	}

	@Test
	void testAndInvalid() {
		final var serializableBiPredicate = (SerializableBiPredicate<Integer, Integer>) (t, u) -> t >= u;
		assertThatNullPointerException().isThrownBy(() -> serializableBiPredicate.and(null));
	}

	@Test
	void testNegate() {
		final var serializableBiPredicate = (SerializableBiPredicate<Integer, Integer>) (t, u) -> t >= u;
		assertThat(serializableBiPredicate.negate().test(1, 2)).isTrue();
		assertThat(serializableBiPredicate.negate().test(3, 3)).isFalse();
	}

	@Test
	void testOr() {
		final var fooSerializableBiPredicate = (SerializableBiPredicate<Integer, Integer>) (t, u) -> t >= u;
		final var barSerializableBiPredicate = (SerializableBiPredicate<Integer, Integer>) (t, u) -> t <= u;
		assertThat(fooSerializableBiPredicate.or(barSerializableBiPredicate).test(1, 2)).isTrue();
		assertThat(fooSerializableBiPredicate.or(barSerializableBiPredicate).test(3, 3)).isTrue();
		assertThat(barSerializableBiPredicate.or(fooSerializableBiPredicate).test(1, 2)).isTrue();
		assertThat(barSerializableBiPredicate.or(fooSerializableBiPredicate).test(3, 3)).isTrue();
		assertThat(fooSerializableBiPredicate.or(fooSerializableBiPredicate).test(1, 2)).isFalse();
		assertThat(fooSerializableBiPredicate.or(fooSerializableBiPredicate).test(3, 3)).isTrue();
		assertThat(barSerializableBiPredicate.or(barSerializableBiPredicate).test(1, 2)).isTrue();
		assertThat(barSerializableBiPredicate.or(barSerializableBiPredicate).test(3, 3)).isTrue();
	}

	@Test
	void testOrInvalid() {
		final var serializableBiPredicate = (SerializableBiPredicate<Integer, Integer>) (t, u) -> t >= u;
		assertThatNullPointerException().isThrownBy(() -> serializableBiPredicate.or(null));
	}

	@Test
	void testOf() {
		final var serializableBiPredicate = SerializableBiPredicate.of((BiPredicate<Integer, Integer>) (t, u) -> t >= u);
		assertThat(serializableBiPredicate.test(1, 2)).isFalse();
		assertThat(serializableBiPredicate.test(3, 3)).isTrue();
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> SerializableBiPredicate.of(null));
	}

	@Test
	void testSerializable() {
		final var deserializedSerializableBiPredicate = Serializables.<SerializableBiPredicate<Integer, Integer>>deserialize(
				Serializables.serialize(
						(SerializableBiPredicate<Integer, Integer>) (t, u) -> t >= u
				)
		);
		assertThat(deserializedSerializableBiPredicate.test(1, 2)).isFalse();
		assertThat(deserializedSerializableBiPredicate.test(3, 3)).isTrue();
	}
}