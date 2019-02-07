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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link SerializableRunnable} unit tests.</p>
 */
final class SerializableRunnableTest {

	private static final class FooRunnable implements Runnable {

		private int value;

		@Override
		public void run() {
			++value;
		}
	}

	private static final class FooSerializableRunnable implements SerializableRunnable {

		private static final long serialVersionUID = 5040906744868003263L;

		private int value;

		@Override
		public void run() {
			++value;
		}
	}

	@Test
	void testRun() {
		final var serializableRunnable = new FooSerializableRunnable();
		serializableRunnable.run();
		serializableRunnable.run();
		assertThat(serializableRunnable.value).isEqualTo(2);
	}

	@Test
	void testOf() {
		final var runnable = new FooRunnable();
		final var serializableRunnable = SerializableRunnable.of(runnable);
		serializableRunnable.run();
		serializableRunnable.run();
		assertThat(runnable.value).isEqualTo(2);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> SerializableRunnable.of(null));
	}

	@Test
	void testSerializable() {
		final var serializableRunnable = Serializables.<FooSerializableRunnable>deserialize(
				Serializables.serialize(
						new FooSerializableRunnable()
				)
		);
		serializableRunnable.run();
		serializableRunnable.run();
		assertThat(serializableRunnable.value).isEqualTo(2);
	}
}