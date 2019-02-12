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
package com.github.alexisjehan.javanilla.misc.quality;

import com.github.alexisjehan.javanilla.io.bytes.InputStreams;
import com.github.alexisjehan.javanilla.io.chars.Readers;
import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.lang.array.BooleanArrays;
import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import com.github.alexisjehan.javanilla.lang.array.DoubleArrays;
import com.github.alexisjehan.javanilla.lang.array.FloatArrays;
import com.github.alexisjehan.javanilla.lang.array.IntArrays;
import com.github.alexisjehan.javanilla.lang.array.LongArrays;
import com.github.alexisjehan.javanilla.lang.array.ObjectArrays;
import com.github.alexisjehan.javanilla.lang.array.ShortArrays;
import com.github.alexisjehan.javanilla.util.collection.bags.Bags;
import com.github.alexisjehan.javanilla.util.iteration.Iterables;
import com.github.alexisjehan.javanilla.util.iteration.Iterators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Ensure} unit tests.</p>
 */
final class EnsureTest {

	@Test
	void testNotNull() {
		final var foo = Integer.valueOf(1);
		assertThat(Ensure.notNull("foo", foo)).isSameAs(foo);
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNull("foo", null))
				.withMessage("Invalid foo (not null expected)");
	}

	@Test
	void testNotNullInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Ensure.notNull(null, 1));
	}

	@Test
	void testNotNullAndNotNullElementsArray() {
		final var foo = ObjectArrays.singleton(1);
		assertThat(Ensure.notNullAndNotNullElements("foo", foo)).isSameAs(foo);
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNullAndNotNullElements("foo", ObjectArrays.singleton(Integer.class, null)))
				.withMessage("Invalid foo element at index 0 (not null expected)");
	}

	@Test
	void testNotNullAndNotNullElementsIterable() {
		final var foo = Iterables.singleton(1);
		assertThat(Ensure.notNullAndNotNullElements("foo", foo)).isSameAs(foo);
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNullAndNotNullElements("foo", Iterables.singleton(null)))
				.withMessage("Invalid foo element at index 0 (not null expected)");
	}

	@Test
	void testNotNullAndNotEmptyCharSequence() {
		final var foo = "1";
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", Strings.EMPTY))
				.withMessage("Invalid foo: \"\" (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyBooleanArray() {
		final var foo = BooleanArrays.singleton(true);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", BooleanArrays.EMPTY))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyByteArray() {
		final var foo = ByteArrays.singleton((byte) 1);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", ByteArrays.EMPTY))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyShortArray() {
		final var foo = ShortArrays.singleton((short) 1);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", ShortArrays.EMPTY))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyCharArray() {
		final var foo = CharArrays.singleton('a');
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", CharArrays.EMPTY))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyIntArray() {
		final var foo = IntArrays.singleton(1);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", IntArrays.EMPTY))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyLongArray() {
		final var foo = LongArrays.singleton(1L);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", LongArrays.EMPTY))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyFloatArray() {
		final var foo = FloatArrays.singleton(1.0f);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", FloatArrays.EMPTY))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyDoubleArray() {
		final var foo = DoubleArrays.singleton(1.0d);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", DoubleArrays.EMPTY))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyArray() {
		final var foo = ObjectArrays.singleton(1);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", ObjectArrays.empty(Integer.class)))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyCollection() {
		final var foo = Set.of(1);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", Set.of()))
				.withMessage("Invalid foo: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyMap() {
		final var foo = Map.of("foo", 1);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", Map.of()))
				.withMessage("Invalid foo: {} (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyBag() {
		final var foo = Bags.singleton("foo", 1);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", Bags.empty()))
				.withMessage("Invalid foo: {} (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyIterator() {
		final var foo = Iterators.singleton(1);
		assertThat(Ensure.notNullAndNotEmpty("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("foo", Collections.emptyIterator()))
				.withMessage("Invalid foo: " + Collections.emptyIterator() + " (not empty expected)");
	}

	@Test
	void testNotNullAndNotBlank() {
		final var foo = "1";
		assertThat(Ensure.notNullAndNotBlank("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotBlank("foo", " "))
				.withMessage("Invalid foo: \" \" (not blank expected)");
	}

	@Test
	void testNotNullAndMatches() {
		final var pattern = Pattern.compile("^[0-9]$");
		final var foo = "1";
		assertThat(Ensure.notNullAndMatches("foo", foo, pattern)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndMatches("foo", "a", pattern))
				.withMessage("Invalid foo: \"a\" (matching " + pattern + " expected)");
	}

	@Test
	void testNotNullAndMatchesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Ensure.notNullAndMatches("foo", "1", null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNotNullAndMarkSupportedInputStream() {
		final var foo = InputStreams.buffered(InputStreams.EMPTY);
		assertThat(Ensure.notNullAndMarkSupported("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndMarkSupported("foo", InputStreams.EMPTY))
				.withMessage("Invalid foo: " + InputStreams.EMPTY + " (mark supported expected)");
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNotNullAndMarkSupportedReader() {
		final var foo = Readers.buffered(Readers.EMPTY);
		assertThat(Ensure.notNullAndMarkSupported("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndMarkSupported("foo", Readers.EMPTY))
				.withMessage("Invalid foo: " + Readers.EMPTY + " (mark supported expected)");
	}

	@Test
	void testNotNullAndExists(@TempDir final Path tmpDirectory) throws IOException {
		final var foo = tmpDirectory.resolve("testNotNullAndExists");
		Files.createFile(foo);
		assertThat(Ensure.notNullAndExists("foo", foo)).isSameAs(foo);
		Files.delete(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndExists("foo", foo))
				.withMessage("Invalid foo: " + foo + " (existing expected)");
	}

	@Test
	void testNotNullAndFile(@TempDir final Path tmpDirectory) throws IOException {
		final var foo = tmpDirectory.resolve("testNotNullAndFile");
		Files.createFile(foo);
		assertThat(Ensure.notNullAndFile("foo", foo)).isSameAs(foo);
		Files.delete(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndFile("foo", foo))
				.withMessage("Invalid foo: " + foo + " (existing file expected)");
		Files.createDirectory(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndFile("foo", foo))
				.withMessage("Invalid foo: " + foo + " (existing file expected)");
	}

	@Test
	void testNotNullAndDirectory(@TempDir final Path tmpDirectory) throws IOException {
		final var foo = tmpDirectory.resolve("testNotNullAndDirectory");
		Files.createDirectory(foo);
		assertThat(Ensure.notNullAndDirectory("foo", foo)).isSameAs(foo);
		Files.delete(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndDirectory("foo", foo))
				.withMessage("Invalid foo: " + foo + " (existing directory expected)");
		Files.createFile(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndDirectory("foo", foo))
				.withMessage("Invalid foo: " + foo + " (existing directory expected)");
	}

	@Test
	void testEqualToBoolean() {
		final var foo = true;
		assertThat(Ensure.equalTo("foo", foo, true)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", foo, false))
				.withMessage("Invalid foo: true (equal to false expected)");
	}

	@Test
	void testEqualToByte() {
		final var foo = (byte) 1;
		assertThat(Ensure.equalTo("foo", foo, (byte) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", foo, (byte) 0))
				.withMessage("Invalid foo: 1 (equal to 0 expected)");
	}

	@Test
	void testEqualToShort() {
		final var foo = (short) 1;
		assertThat(Ensure.equalTo("foo", foo, (short) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", foo, (short) 0))
				.withMessage("Invalid foo: 1 (equal to 0 expected)");
	}

	@Test
	void testEqualToChar() {
		final var foo = 'a';
		assertThat(Ensure.equalTo("foo", foo, 'a')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", foo, '?'))
				.withMessage("Invalid foo: 'a' (equal to '?' expected)");
	}

	@Test
	void testEqualToInt() {
		final var foo = 1;
		assertThat(Ensure.equalTo("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", foo, 0))
				.withMessage("Invalid foo: 1 (equal to 0 expected)");
	}

	@Test
	void testEqualToLong() {
		final var foo = 1L;
		assertThat(Ensure.equalTo("foo", foo, 1L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", foo, 0L))
				.withMessage("Invalid foo: 1 (equal to 0 expected)");
	}

	@Test
	void testEqualToFloat() {
		final var foo = 1.0f;
		assertThat(Ensure.equalTo("foo", foo, 1.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", foo, 0.0f))
				.withMessage("Invalid foo: 1.0 (equal to 0.0 expected)");
	}

	@Test
	void testEqualToDouble() {
		final var foo = 1.0d;
		assertThat(Ensure.equalTo("foo", foo, 1.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", foo, 0.0d))
				.withMessage("Invalid foo: 1.0 (equal to 0.0 expected)");
	}

	@Test
	void testNotNullAndEqualToObject() {
		final var foo = Integer.valueOf(1);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, 0))
				.withMessage("Invalid foo: 1 (equal to 0 expected)");
	}

	@Test
	void testNotNullAndEqualToBooleanArray() {
		final var foo = BooleanArrays.singleton(true);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, BooleanArrays.singleton(true))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, BooleanArrays.singleton(false)))
				.withMessage("Invalid foo: [true] (equal to [false] expected)");
	}

	@Test
	void testNotNullAndEqualToByteArray() {
		final var foo = ByteArrays.singleton((byte) 1);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, ByteArrays.singleton((byte) 1))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, ByteArrays.singleton((byte) 0)))
				.withMessage("Invalid foo: [1] (equal to [0] expected)");
	}

	@Test
	void testNotNullAndEqualToShortArray() {
		final var foo = ShortArrays.singleton((short) 1);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, ShortArrays.singleton((short) 1))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, ShortArrays.singleton((short) 0)))
				.withMessage("Invalid foo: [1] (equal to [0] expected)");
	}

	@Test
	void testNotNullAndEqualToCharArray() {
		final var foo = CharArrays.singleton('a');
		assertThat(Ensure.notNullAndEqualTo("foo", foo, CharArrays.singleton('a'))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, CharArrays.singleton('?')))
				.withMessage("Invalid foo: ['a'] (equal to ['?'] expected)");
	}

	@Test
	void testNotNullAndEqualToIntArray() {
		final var foo = IntArrays.singleton(1);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, IntArrays.singleton(1))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, IntArrays.singleton(0)))
				.withMessage("Invalid foo: [1] (equal to [0] expected)");
	}

	@Test
	void testNotNullAndEqualToLongArray() {
		final var foo = LongArrays.singleton(1L);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, LongArrays.singleton(1L))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, LongArrays.singleton(0L)))
				.withMessage("Invalid foo: [1] (equal to [0] expected)");
	}

	@Test
	void testNotNullAndEqualToFloatArray() {
		final var foo = FloatArrays.singleton(1.0f);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, FloatArrays.singleton(1.0f))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, FloatArrays.singleton(0.0f)))
				.withMessage("Invalid foo: [1.0] (equal to [0.0] expected)");
	}

	@Test
	void testNotNullAndEqualToDoubleArray() {
		final var foo = DoubleArrays.singleton(1.0d);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, DoubleArrays.singleton(1.0d))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, DoubleArrays.singleton(0.0d)))
				.withMessage("Invalid foo: [1.0] (equal to [0.0] expected)");
	}

	@Test
	void testNotNullAndEqualToArray() {
		final var foo = ObjectArrays.singleton(1);
		assertThat(Ensure.notNullAndEqualTo("foo", foo, ObjectArrays.singleton(1))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("foo", foo, ObjectArrays.singleton(0)))
				.withMessage("Invalid foo: [1] (equal to [0] expected)");
	}

	@Test
	void testNotEqualToBoolean() {
		final var foo = true;
		assertThat(Ensure.notEqualTo("foo", foo, false)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("foo", foo, true))
				.withMessage("Invalid foo: true (not equal to true expected)");
	}

	@Test
	void testNotEqualToByte() {
		final var foo = (byte) 1;
		assertThat(Ensure.notEqualTo("foo", foo, (byte) 0)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("foo", foo, (byte) 1))
				.withMessage("Invalid foo: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotEqualToShort() {
		final var foo = (short) 1;
		assertThat(Ensure.notEqualTo("foo", foo, (short) 0)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("foo", foo, (short) 1))
				.withMessage("Invalid foo: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotEqualToChar() {
		final var foo = 'a';
		assertThat(Ensure.notEqualTo("foo", foo, '?')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("foo", foo, 'a'))
				.withMessage("Invalid foo: 'a' (not equal to 'a' expected)");
	}

	@Test
	void testNotEqualToInt() {
		final var foo = 1;
		assertThat(Ensure.notEqualTo("foo", foo, 0)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("foo", foo, 1))
				.withMessage("Invalid foo: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotEqualToLong() {
		final var foo = 1L;
		assertThat(Ensure.notEqualTo("foo", foo, 0L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("foo", foo, 1L))
				.withMessage("Invalid foo: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotEqualToFloat() {
		final var foo = 1.0f;
		assertThat(Ensure.notEqualTo("foo", foo, 0.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("foo", foo, 1.0f))
				.withMessage("Invalid foo: 1.0 (not equal to 1.0 expected)");
	}

	@Test
	void testNotEqualToDouble() {
		final var foo = 1.0d;
		assertThat(Ensure.notEqualTo("foo", foo, 0.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("foo", foo, 1.0d))
				.withMessage("Invalid foo: 1.0 (not equal to 1.0 expected)");
	}

	@Test
	void testNotNullAndNotEqualToObject() {
		final var foo = Integer.valueOf(1);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, 0)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, 1))
				.withMessage("Invalid foo: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotNullAndNotEqualToBooleanArray() {
		final var foo = BooleanArrays.singleton(true);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, BooleanArrays.singleton(false))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, BooleanArrays.singleton(true)))
				.withMessage("Invalid foo: [true] (not equal to [true] expected)");
	}

	@Test
	void testNotNullAndNotEqualToByteArray() {
		final var foo = ByteArrays.singleton((byte) 1);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, ByteArrays.singleton((byte) 0))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, ByteArrays.singleton((byte) 1)))
				.withMessage("Invalid foo: [1] (not equal to [1] expected)");
	}

	@Test
	void testNotNullAndNotEqualToShortArray() {
		final var foo = ShortArrays.singleton((short) 1);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, ShortArrays.singleton((short) 0))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, ShortArrays.singleton((short) 1)))
				.withMessage("Invalid foo: [1] (not equal to [1] expected)");
	}

	@Test
	void testNotNullAndNotEqualToCharArray() {
		final var foo = CharArrays.singleton('a');
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, CharArrays.singleton('?'))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, CharArrays.singleton('a')))
				.withMessage("Invalid foo: ['a'] (not equal to ['a'] expected)");
	}

	@Test
	void testNotNullAndNotEqualToIntArray() {
		final var foo = IntArrays.singleton(1);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, IntArrays.singleton(0))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, IntArrays.singleton(1)))
				.withMessage("Invalid foo: [1] (not equal to [1] expected)");
	}

	@Test
	void testNotNullAndNotEqualToLongArray() {
		final var foo = LongArrays.singleton(1L);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, LongArrays.singleton(0L))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, LongArrays.singleton(1L)))
				.withMessage("Invalid foo: [1] (not equal to [1] expected)");
	}

	@Test
	void testNotNullAndNotEqualToFloatArray() {
		final var foo = FloatArrays.singleton(1.0f);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, FloatArrays.singleton(0.0f))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, FloatArrays.singleton(1.0f)))
				.withMessage("Invalid foo: [1.0] (not equal to [1.0] expected)");
	}

	@Test
	void testNotNullAndNotEqualToDoubleArray() {
		final var foo = DoubleArrays.singleton(1.0d);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, DoubleArrays.singleton(0.0d))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, DoubleArrays.singleton(1.0d)))
				.withMessage("Invalid foo: [1.0] (not equal to [1.0] expected)");
	}

	@Test
	void testNotNullAndNotEqualToArray() {
		final var foo = ObjectArrays.singleton(1);
		assertThat(Ensure.notNullAndNotEqualTo("foo", foo, ObjectArrays.singleton(0))).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("foo", foo, ObjectArrays.singleton(1)))
				.withMessage("Invalid foo: [1] (not equal to [1] expected)");
	}

	@Test
	void testLowerThanByte() {
		final var foo = (byte) 1;
		assertThat(Ensure.lowerThan("foo", foo, (byte) 2)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, (byte) 1))
				.withMessage("Invalid foo: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, (byte) 0))
				.withMessage("Invalid foo: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanShort() {
		final var foo = (short) 1;
		assertThat(Ensure.lowerThan("foo", foo, (short) 2)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, (short) 1))
				.withMessage("Invalid foo: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, (short) 0))
				.withMessage("Invalid foo: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanChar() {
		final var foo = 'a';
		assertThat(Ensure.lowerThan("foo", foo, 'b')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 'a'))
				.withMessage("Invalid foo: 'a' (lower than 'a' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, '?'))
				.withMessage("Invalid foo: 'a' (lower than '?' expected)");
	}

	@Test
	void testLowerThanInt() {
		final var foo = 1;
		assertThat(Ensure.lowerThan("foo", foo, 2)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 1))
				.withMessage("Invalid foo: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 0))
				.withMessage("Invalid foo: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanLong() {
		final var foo = 1L;
		assertThat(Ensure.lowerThan("foo", foo, 2L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 1L))
				.withMessage("Invalid foo: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 0L))
				.withMessage("Invalid foo: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanFloat() {
		final var foo = 1.0f;
		assertThat(Ensure.lowerThan("foo", foo, 2.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 1.0f))
				.withMessage("Invalid foo: 1.0 (lower than 1.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 0.0f))
				.withMessage("Invalid foo: 1.0 (lower than 0.0 expected)");
	}

	@Test
	void testLowerThanDouble() {
		final var foo = 1.0d;
		assertThat(Ensure.lowerThan("foo", foo, 2.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 1.0d))
				.withMessage("Invalid foo: 1.0 (lower than 1.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", foo, 0.0d))
				.withMessage("Invalid foo: 1.0 (lower than 0.0 expected)");
	}

	@Test
	void testNotNullAndLowerThan() {
		final var foo = Integer.valueOf(1);
		assertThat(Ensure.notNullAndLowerThan("foo", foo, 2)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndLowerThan("foo", foo, 1))
				.withMessage("Invalid foo: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndLowerThan("foo", foo, 0))
				.withMessage("Invalid foo: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToByte() {
		final var foo = (byte) 1;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, (byte) 2)).isEqualTo(foo);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, (byte) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", foo, (byte) 0))
				.withMessage("Invalid foo: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToShort() {
		final var foo = (short) 1;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, (short) 2)).isEqualTo(foo);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, (short) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", foo, (short) 0))
				.withMessage("Invalid foo: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToChar() {
		final var foo = 'a';
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 'b')).isEqualTo(foo);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 'a')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", foo, '?'))
				.withMessage("Invalid foo: 'a' (lower than or equal to '?' expected)");
	}

	@Test
	void testLowerThanOrEqualToInt() {
		final var foo = 1;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 2)).isEqualTo(foo);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", foo, 0))
				.withMessage("Invalid foo: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToLong() {
		final var foo = 1L;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 2L)).isEqualTo(foo);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 1L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", foo, 0L))
				.withMessage("Invalid foo: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToFloat() {
		final var foo = 1.0f;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 2.0f)).isEqualTo(foo);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 1.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", foo, 0.0f))
				.withMessage("Invalid foo: 1.0 (lower than or equal to 0.0 expected)");
	}

	@Test
	void testLowerThanOrEqualToDouble() {
		final var foo = 1.0d;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 2.0d)).isEqualTo(foo);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo, 1.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", foo, 0.0d))
				.withMessage("Invalid foo: 1.0 (lower than or equal to 0.0 expected)");
	}

	@Test
	void testNotNullAndLowerThanOrEqualTo() {
		final var foo = Integer.valueOf(1);
		assertThat(Ensure.notNullAndLowerThanOrEqualTo("foo", foo, 2)).isEqualTo(foo);
		assertThat(Ensure.notNullAndLowerThanOrEqualTo("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndLowerThanOrEqualTo("foo", foo, 0))
				.withMessage("Invalid foo: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testGreaterThanByte() {
		final var foo = (byte) 1;
		assertThat(Ensure.greaterThan("foo", foo, (byte) 0)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, (byte) 1))
				.withMessage("Invalid foo: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, (byte) 2))
				.withMessage("Invalid foo: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanShort() {
		final var foo = (short) 1;
		assertThat(Ensure.greaterThan("foo", foo, (short) 0)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, (short) 1))
				.withMessage("Invalid foo: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, (short) 2))
				.withMessage("Invalid foo: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanChar() {
		final var foo = 'a';
		assertThat(Ensure.greaterThan("foo", foo, '?')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 'a'))
				.withMessage("Invalid foo: 'a' (greater than 'a' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 'b'))
				.withMessage("Invalid foo: 'a' (greater than 'b' expected)");
	}

	@Test
	void testGreaterThanInt() {
		final var foo = 1;
		assertThat(Ensure.greaterThan("foo", foo, 0)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 1))
				.withMessage("Invalid foo: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 2))
				.withMessage("Invalid foo: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanLong() {
		final var foo = 1L;
		assertThat(Ensure.greaterThan("foo", foo, 0L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 1L))
				.withMessage("Invalid foo: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 2L))
				.withMessage("Invalid foo: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanFloat() {
		final var foo = 1.0f;
		assertThat(Ensure.greaterThan("foo", foo, 0.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 1.0f))
				.withMessage("Invalid foo: 1.0 (greater than 1.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 2.0f))
				.withMessage("Invalid foo: 1.0 (greater than 2.0 expected)");
	}

	@Test
	void testGreaterThanDouble() {
		final var foo = 1.0d;
		assertThat(Ensure.greaterThan("foo", foo, 0.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 1.0d))
				.withMessage("Invalid foo: 1.0 (greater than 1.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", foo, 2.0d))
				.withMessage("Invalid foo: 1.0 (greater than 2.0 expected)");
	}

	@Test
	void testNotNullAndGreaterThan() {
		final var foo = Integer.valueOf(1);
		assertThat(Ensure.notNullAndGreaterThan("foo", foo, 0)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndGreaterThan("foo", foo, 1))
				.withMessage("Invalid foo: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndGreaterThan("foo", foo, 2))
				.withMessage("Invalid foo: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToByte() {
		final var foo = (byte) 1;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, (byte) 0)).isEqualTo(foo);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, (byte) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", foo, (byte) 2))
				.withMessage("Invalid foo: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToShort() {
		final var foo = (short) 1;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, (short) 0)).isEqualTo(foo);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, (short) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", foo, (short) 2))
				.withMessage("Invalid foo: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToChar() {
		final var foo = 'a';
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, '?')).isEqualTo(foo);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 'a')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", foo, 'b'))
				.withMessage("Invalid foo: 'a' (greater than or equal to 'b' expected)");
	}

	@Test
	void testGreaterThanOrEqualToInt() {
		final var foo = 1;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 0)).isEqualTo(foo);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", foo, 2))
				.withMessage("Invalid foo: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToLong() {
		final var foo = 1L;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 0L)).isEqualTo(foo);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 1L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", foo, 2L))
				.withMessage("Invalid foo: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToFloat() {
		final var foo = 1.0f;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 0.0f)).isEqualTo(foo);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 1.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", foo, 2.0f))
				.withMessage("Invalid foo: 1.0 (greater than or equal to 2.0 expected)");
	}

	@Test
	void testGreaterThanOrEqualToDouble() {
		final var foo = 1.0d;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 0.0d)).isEqualTo(foo);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo, 1.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", foo, 2.0d))
				.withMessage("Invalid foo: 1.0 (greater than or equal to 2.0 expected)");
	}

	@Test
	void testNotNullAndGreaterThanOrEqualTo() {
		final var foo = Integer.valueOf(1);
		assertThat(Ensure.notNullAndGreaterThanOrEqualTo("foo", foo, 0)).isEqualTo(foo);
		assertThat(Ensure.notNullAndGreaterThanOrEqualTo("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndGreaterThanOrEqualTo("foo", foo, 2))
				.withMessage("Invalid foo: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testBetweenByte() {
		final var foo = (byte) 1;
		assertThat(Ensure.between("foo", foo, (byte) 0, (byte) 1)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, (byte) 1, (byte) 1)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, (byte) 1, (byte) 2)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, (byte) 0, (byte) 0))
				.withMessage("Invalid foo: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, (byte) 2, (byte) 2))
				.withMessage("Invalid foo: 1 (between 2 and 2 expected)");
	}

	@Test
	void testBetweenShort() {
		final var foo = (short) 1;
		assertThat(Ensure.between("foo", foo, (short) 0, (short) 1)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, (short) 1, (short) 1)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, (short) 1, (short) 2)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, (short) 0, (short) 0))
				.withMessage("Invalid foo: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, (short) 2, (short) 2))
				.withMessage("Invalid foo: 1 (between 2 and 2 expected)");
	}

	@Test
	void testBetweenChar() {
		final var foo = 'a';
		assertThat(Ensure.between("foo", foo, '?', 'a')).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 'a', 'a')).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 'a', 'b')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, '?', '?'))
				.withMessage("Invalid foo: 'a' (between '?' and '?' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 'b', 'b'))
				.withMessage("Invalid foo: 'a' (between 'b' and 'b' expected)");
	}

	@Test
	void testBetweenInt() {
		final var foo = 1;
		assertThat(Ensure.between("foo", foo, 0, 1)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 1, 1)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 1, 2)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 0, 0))
				.withMessage("Invalid foo: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 2, 2))
				.withMessage("Invalid foo: 1 (between 2 and 2 expected)");
	}

	@Test
	void testBetweenLong() {
		final var foo = 1L;
		assertThat(Ensure.between("foo", foo, 0L, 1L)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 1L, 1L)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 1L, 2L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 0L, 0L))
				.withMessage("Invalid foo: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 2L, 2L))
				.withMessage("Invalid foo: 1 (between 2 and 2 expected)");
	}

	@Test
	void testBetweenFloat() {
		final var foo = 1.0f;
		assertThat(Ensure.between("foo", foo, 0.0f, 1.0f)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 1.0f, 1.0f)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 1.0f, 2.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 0.0f, 0.0f))
				.withMessage("Invalid foo: 1.0 (between 0.0 and 0.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 2.0f, 2.0f))
				.withMessage("Invalid foo: 1.0 (between 2.0 and 2.0 expected)");
	}

	@Test
	void testBetweenDouble() {
		final var foo = 1.0d;
		assertThat(Ensure.between("foo", foo, 0.0d, 1.0d)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 1.0d, 1.0d)).isEqualTo(foo);
		assertThat(Ensure.between("foo", foo, 1.0d, 2.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 0.0d, 0.0d))
				.withMessage("Invalid foo: 1.0 (between 0.0 and 0.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", foo, 2.0d, 2.0d))
				.withMessage("Invalid foo: 1.0 (between 2.0 and 2.0 expected)");
	}

	@Test
	void testNotNullAndBetween() {
		final var foo = Integer.valueOf(1);
		assertThat(Ensure.notNullAndBetween("foo", foo, 0, 1)).isEqualTo(foo);
		assertThat(Ensure.notNullAndBetween("foo", foo, 1, 1)).isEqualTo(foo);
		assertThat(Ensure.notNullAndBetween("foo", foo, 1, 2)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndBetween("foo", foo, 0, 0))
				.withMessage("Invalid foo: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndBetween("foo", foo, 2, 2))
				.withMessage("Invalid foo: 1 (between 2 and 2 expected)");
	}
}