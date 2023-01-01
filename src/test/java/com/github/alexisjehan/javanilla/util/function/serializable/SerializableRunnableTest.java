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
package com.github.alexisjehan.javanilla.util.function.serializable;

import com.github.alexisjehan.javanilla.io.Serializables;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAdder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@SuppressWarnings("deprecation")
final class SerializableRunnableTest {

	private static final class FooRunnable implements Runnable {

		private final LongAdder adder = new LongAdder();

		@Override
		public void run() {
			adder.increment();
		}

		private int getValue() {
			return adder.intValue();
		}
	}

	@SuppressWarnings("serial")
	private static final class FooSerializableRunnable implements SerializableRunnable {

		private final LongAdder adder = new LongAdder();

		@Override
		public void run() {
			adder.increment();
		}

		private int getValue() {
			return adder.intValue();
		}
	}

	@Test
	void testRun() {
		final var serializableRunnable = new FooSerializableRunnable();
		serializableRunnable.run();
		serializableRunnable.run();
		assertThat(serializableRunnable.getValue()).isEqualTo(2);
	}

	@Test
	void testOf() {
		final var runnable = new FooRunnable();
		final var serializableRunnable = SerializableRunnable.of(runnable);
		serializableRunnable.run();
		serializableRunnable.run();
		assertThat(runnable.getValue()).isEqualTo(2);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> SerializableRunnable.of(null));
	}

	@Test
	void testSerializable() {
		final var deserializedSerializableRunnable = Serializables.<FooSerializableRunnable>deserialize(
				Serializables.serialize(
						new FooSerializableRunnable()
				)
		);
		deserializedSerializableRunnable.run();
		deserializedSerializableRunnable.run();
		assertThat(deserializedSerializableRunnable.getValue()).isEqualTo(2);
	}
}