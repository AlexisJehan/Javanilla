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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class SerializableBiFunctionTest {

	@Test
	void testApply() {
		final var serializableBiFunction = (SerializableBiFunction<Integer, Integer, Integer>) Integer::sum;
		assertThat(serializableBiFunction.apply(1, 2)).isEqualTo(3);
		assertThat(serializableBiFunction.apply(3, 3)).isEqualTo(6);
	}

	@Test
	void testAndThen() {
		final var serializableBiFunction = (SerializableBiFunction<Integer, Integer, Integer>) Integer::sum;
		final var serializableFunction = (SerializableFunction<Integer, Integer>) t -> -t;
		assertThat(serializableBiFunction.andThen(serializableFunction).apply(1, 2)).isEqualTo(-3);
		assertThat(serializableBiFunction.andThen(serializableFunction).apply(3, 3)).isEqualTo(-6);
	}

	@Test
	void testAndThenInvalid() {
		final var serializableBiFunction = (SerializableBiFunction<Integer, Integer, Integer>) Integer::sum;
		assertThatNullPointerException().isThrownBy(() -> serializableBiFunction.andThen(null));
	}

	@Test
	void testOf() {
		final var serializableBiFunction = SerializableBiFunction.of(Integer::sum);
		assertThat(serializableBiFunction.apply(1, 2)).isEqualTo(3);
		assertThat(serializableBiFunction.apply(3, 3)).isEqualTo(6);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> SerializableBiFunction.of(null));
	}

	@Test
	void testSerializable() {
		final var deserializedSerializableBiFunction = Serializables.<SerializableBiFunction<Integer, Integer, Integer>>deserialize(
				Serializables.serialize(
						(SerializableBiFunction<Integer, Integer, Integer>) Integer::sum
				)
		);
		assertThat(deserializedSerializableBiFunction.apply(1, 2)).isEqualTo(3);
		assertThat(deserializedSerializableBiFunction.apply(3, 3)).isEqualTo(6);
	}
}