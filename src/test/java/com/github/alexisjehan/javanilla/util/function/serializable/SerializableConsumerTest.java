/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link SerializableConsumer} unit tests.</p>
 */
final class SerializableConsumerTest {

	@Test
	void testAccept() {
		final var serializableConsumer = (SerializableConsumer<List<Integer>>) t -> t.add(t.size() + 1);
		final var list = new ArrayList<Integer>();
		serializableConsumer.accept(list);
		serializableConsumer.accept(list);
		assertThat(list).containsExactly(1, 2);
	}

	@Test
	void testAndThen() {
		final var serializableConsumer1 = (SerializableConsumer<List<Integer>>) t -> t.add(t.size() + 1);
		final var serializableConsumer2 = (SerializableConsumer<List<Integer>>) t -> t.add(t.size() - 1);
		{
			final var list = new ArrayList<Integer>();
			serializableConsumer1.andThen(serializableConsumer2).accept(list);
			serializableConsumer1.andThen(serializableConsumer2).accept(list);
			assertThat(list).containsExactly(1, 0, 3, 2);
		}
		{
			final var list = new ArrayList<Integer>();
			serializableConsumer2.andThen(serializableConsumer1).accept(list);
			serializableConsumer2.andThen(serializableConsumer1).accept(list);
			assertThat(list).containsExactly(-1, 2, 1, 4);
		}
	}

	@Test
	void testAndThenInvalid() {
		final var serializableConsumer = (SerializableConsumer<List<Integer>>) t -> t.add(t.size() + 1);
		assertThatNullPointerException().isThrownBy(() -> serializableConsumer.andThen(null));
	}

	@Test
	void testOf() {
		final var serializableConsumer = SerializableConsumer.of((Consumer<List<Integer>>) t -> t.add(t.size() + 1));
		final var list = new ArrayList<Integer>();
		serializableConsumer.accept(list);
		serializableConsumer.accept(list);
		assertThat(list).containsExactly(1, 2);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> SerializableConsumer.of(null));
	}

	@Test
	void testSerializable() {
		final var serializableConsumer = Serializables.<SerializableConsumer<List<Integer>>>deserialize(
				Serializables.serialize(
						(SerializableConsumer<List<Integer>>) t -> t.add(t.size() + 1)
				)
		);
		final var list = new ArrayList<Integer>();
		serializableConsumer.accept(list);
		serializableConsumer.accept(list);
		assertThat(list).containsExactly(1, 2);
	}
}