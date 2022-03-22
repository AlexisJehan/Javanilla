package com.github.alexisjehan.javanilla.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link SerializationException} unit tests.</p>
 */
final class SerializationExceptionTest {

	private static final IOException CAUSE = new IOException();

	private final SerializationException serializationException = new SerializationException(CAUSE);

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> {
			throw new SerializationException(null);
		});
	}

	@Test
	void testSerializable() {
		assertThat(Serializables.<SerializationException>deserialize(Serializables.serialize(serializationException))).hasSameClassAs(serializationException);
	}
}