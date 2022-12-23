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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@SuppressWarnings("deprecation")
final class SerializableBiConsumerTest {

	@Test
	void testAccept() {
		final var serializableBiConsumer = (SerializableBiConsumer<List<Integer>, Integer>) (t, u) -> t.add(u + 1);
		final var list = new ArrayList<Integer>();
		serializableBiConsumer.accept(list, 1);
		serializableBiConsumer.accept(list, 3);
		assertThat(list).containsExactly(2, 4);
	}

	@Test
	void testAndThen() {
		final var fooSerializableBiConsumer = (SerializableBiConsumer<List<Integer>, Integer>) (t, u) -> t.add(u + 1);
		final var barSerializableBiConsumer = (SerializableBiConsumer<List<Integer>, Integer>) (t, u) -> t.add(u - 1);
		final var list = new ArrayList<Integer>();
		fooSerializableBiConsumer.andThen(barSerializableBiConsumer).accept(list, 1);
		fooSerializableBiConsumer.andThen(barSerializableBiConsumer).accept(list, 3);
		assertThat(list).containsExactly(2, 0, 4, 2);
		list.clear();
		barSerializableBiConsumer.andThen(fooSerializableBiConsumer).accept(list, 1);
		barSerializableBiConsumer.andThen(fooSerializableBiConsumer).accept(list, 3);
		assertThat(list).containsExactly(0, 2, 2, 4);
	}

	@Test
	void testAndThenInvalid() {
		final var serializableBiConsumer = (SerializableBiConsumer<List<Integer>, Integer>) (t, u) -> t.add(u + 1);
		assertThatNullPointerException().isThrownBy(() -> serializableBiConsumer.andThen(null));
	}

	@Test
	void testOf() {
		final var serializableBiConsumer = SerializableBiConsumer.of((BiConsumer<List<Integer>, Integer>) (t, u) -> t.add(u + 1));
		final var list = new ArrayList<Integer>();
		serializableBiConsumer.accept(list, 1);
		serializableBiConsumer.accept(list, 3);
		assertThat(list).containsExactly(2, 4);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> SerializableBiConsumer.of(null));
	}

	@Test
	void testSerializable() {
		final var deserializedSerializableBiConsumer = Serializables.<SerializableBiConsumer<List<Integer>, Integer>>deserialize(
				Serializables.serialize(
						(SerializableBiConsumer<List<Integer>, Integer>) (t, u) -> t.add(u + 1)
				)
		);
		final var list = new ArrayList<Integer>();
		deserializedSerializableBiConsumer.accept(list, 1);
		deserializedSerializableBiConsumer.accept(list, 3);
		assertThat(list).containsExactly(2, 4);
	}
}