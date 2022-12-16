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
import com.github.alexisjehan.javanilla.util.function.Procedure;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAdder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class SerializableProcedureTest {

	private static final class FooProcedure implements Procedure {

		private final LongAdder adder = new LongAdder();

		@Override
		public void execute() {
			adder.increment();
		}

		private int getValue() {
			return adder.intValue();
		}
	}

	@SuppressWarnings("serial")
	private static final class FooSerializableProcedure implements SerializableProcedure {

		private final LongAdder adder = new LongAdder();

		@Override
		public void execute() {
			adder.increment();
		}

		private int getValue() {
			return adder.intValue();
		}
	}

	@Test
	void testRun() {
		final var serializableProcedure = new FooSerializableProcedure();
		serializableProcedure.execute();
		serializableProcedure.execute();
		assertThat(serializableProcedure.getValue()).isEqualTo(2);
	}

	@Test
	void testOf() {
		final var procedure = new FooProcedure();
		final var serializableProcedure = SerializableProcedure.of(procedure);
		serializableProcedure.execute();
		serializableProcedure.execute();
		assertThat(procedure.getValue()).isEqualTo(2);
	}

	@Test
	void testOfInvalid() {
		assertThatNullPointerException().isThrownBy(() -> SerializableProcedure.of(null));
	}

	@Test
	void testSerializable() {
		final var deserializedSerializableProcedure = Serializables.<FooSerializableProcedure>deserialize(
				Serializables.serialize(
						new FooSerializableProcedure()
				)
		);
		deserializedSerializableProcedure.execute();
		deserializedSerializableProcedure.execute();
		assertThat(deserializedSerializableProcedure.getValue()).isEqualTo(2);
	}
}