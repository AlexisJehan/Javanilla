/*
MIT License

Copyright (c) 2018 Alexis Jehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

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
	void testNotNullAndMarkSupportedInputStream() {
		final var foo = InputStreams.buffered(InputStreams.EMPTY);
		assertThat(Ensure.notNullAndMarkSupported("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndMarkSupported("foo", InputStreams.EMPTY))
				.withMessage("Invalid foo: " + InputStreams.EMPTY + " (mark supported expected)");
	}

	@Test
	void testNotNullAndMarkSupportedReader() {
		final var foo = Readers.buffered(Readers.EMPTY);
		assertThat(Ensure.notNullAndMarkSupported("foo", foo)).isSameAs(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndMarkSupported("foo", Readers.EMPTY))
				.withMessage("Invalid foo: " + Readers.EMPTY + " (mark supported expected)");
	}

	@Test
	@ExtendWith(TempDirectory.class)
	void testNotNullAndExists(@TempDirectory.TempDir final Path tmpDirectory) throws IOException {
		final var foo = tmpDirectory.resolve("testNotNullAndExists");
		Files.createFile(foo);
		assertThat(Ensure.notNullAndExists("foo", foo)).isSameAs(foo);
		Files.delete(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndExists("foo", foo))
				.withMessage("Invalid foo: " + foo + " (existing expected)");
	}

	@Test
	@ExtendWith(TempDirectory.class)
	void testNotNullAndFile(@TempDirectory.TempDir final Path tmpDirectory) throws IOException {
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
	@ExtendWith(TempDirectory.class)
	void testNotNullAndDirectory(@TempDirectory.TempDir final Path tmpDirectory) throws IOException {
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
				.isThrownBy(() -> Ensure.equalTo("foo", true, false))
				.withMessage("Invalid foo: true (equal to false expected)");
	}

	@Test
	void testEqualToByte() {
		final var foo = (byte) 1;
		assertThat(Ensure.equalTo("foo", foo, (byte) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", (byte) 0, (byte) 1))
				.withMessage("Invalid foo: 0 (equal to 1 expected)");
	}

	@Test
	void testEqualToShort() {
		final var foo = (short) 1;
		assertThat(Ensure.equalTo("foo", foo, (short) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", (short) 0, (short) 1))
				.withMessage("Invalid foo: 0 (equal to 1 expected)");
	}

	@Test
	void testEqualToChar() {
		final var foo = 'a';
		assertThat(Ensure.equalTo("foo", foo, 'a')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", '?', 'a'))
				.withMessage("Invalid foo: '?' (equal to 'a' expected)");
	}

	@Test
	void testEqualToInt() {
		final var foo = 1;
		assertThat(Ensure.equalTo("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", 0, 1))
				.withMessage("Invalid foo: 0 (equal to 1 expected)");
	}

	@Test
	void testEqualToLong() {
		final var foo = 1L;
		assertThat(Ensure.equalTo("foo", foo, 1L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", 0L, 1L))
				.withMessage("Invalid foo: 0L (equal to 1L expected)");
	}

	@Test
	void testEqualToFloat() {
		final var foo = 1.0f;
		assertThat(Ensure.equalTo("foo", foo, 1.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", 0.0f, 1.0f))
				.withMessage("Invalid foo: 0.0f (equal to 1.0f expected)");
	}

	@Test
	void testEqualToDouble() {
		final var foo = 1.0d;
		assertThat(Ensure.equalTo("foo", foo, 1.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("foo", 0.0d, 1.0d))
				.withMessage("Invalid foo: 0.0d (equal to 1.0d expected)");
	}

	@Test
	void testLowerThanByte() {
		final var foo = (byte) 0;
		assertThat(Ensure.lowerThan("foo", foo, (byte) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", (byte) 1, (byte) 1))
				.withMessage("Invalid foo: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", (byte) 2, (byte) 1))
				.withMessage("Invalid foo: 2 (lower than 1 expected)");
	}

	@Test
	void testLowerThanShort() {
		final var foo = (short) 0;
		assertThat(Ensure.lowerThan("foo", foo, (short) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", (short) 1, (short) 1))
				.withMessage("Invalid foo: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", (short) 2, (short) 1))
				.withMessage("Invalid foo: 2 (lower than 1 expected)");
	}

	@Test
	void testLowerThanChar() {
		final var foo = '?';
		assertThat(Ensure.lowerThan("foo", foo, 'a')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 'a', 'a'))
				.withMessage("Invalid foo: 'a' (lower than 'a' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 'b', 'a'))
				.withMessage("Invalid foo: 'b' (lower than 'a' expected)");
	}

	@Test
	void testLowerThanInt() {
		final var foo = 0;
		assertThat(Ensure.lowerThan("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 1, 1))
				.withMessage("Invalid foo: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 2, 1))
				.withMessage("Invalid foo: 2 (lower than 1 expected)");
	}

	@Test
	void testLowerThanLong() {
		final var foo = 0L;
		assertThat(Ensure.lowerThan("foo", foo, 1L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 1L, 1L))
				.withMessage("Invalid foo: 1L (lower than 1L expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 2L, 1L))
				.withMessage("Invalid foo: 2L (lower than 1L expected)");
	}

	@Test
	void testLowerThanFloat() {
		final var foo = 0.0f;
		assertThat(Ensure.lowerThan("foo", foo, 1.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 1.0f, 1.0f))
				.withMessage("Invalid foo: 1.0f (lower than 1.0f expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 2.0f, 1.0f))
				.withMessage("Invalid foo: 2.0f (lower than 1.0f expected)");
	}

	@Test
	void testLowerThanDouble() {
		final var foo = 0.0d;
		assertThat(Ensure.lowerThan("foo", foo, 1.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 1.0d, 1.0d))
				.withMessage("Invalid foo: 1.0d (lower than 1.0d expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("foo", 2.0d, 1.0d))
				.withMessage("Invalid foo: 2.0d (lower than 1.0d expected)");
	}

	@Test
	void testLowerThanOrEqualToByte() {
		final var foo1 = (byte) 0;
		final var foo2 = (byte) 1;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo1, (byte) 1)).isEqualTo(foo1);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo2, (byte) 1)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", (byte) 2, (byte) 1))
				.withMessage("Invalid foo: 2 (lower than or equal to 1 expected)");
	}

	@Test
	void testLowerThanOrEqualToShort() {
		final var foo1 = (short) 0;
		final var foo2 = (short) 1;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo1, (short) 1)).isEqualTo(foo1);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo2, (short) 1)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", (short) 2, (short) 1))
				.withMessage("Invalid foo: 2 (lower than or equal to 1 expected)");
	}

	@Test
	void testLowerThanOrEqualToChar() {
		final var foo1 = '?';
		final var foo2 = 'a';
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo1, 'a')).isEqualTo(foo1);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo2, 'a')).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", 'b', 'a'))
				.withMessage("Invalid foo: 'b' (lower than or equal to 'a' expected)");
	}

	@Test
	void testLowerThanOrEqualToInt() {
		final var foo1 = 0;
		final var foo2 = 1;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo1, 1)).isEqualTo(foo1);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo2, 1)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", 2, 1))
				.withMessage("Invalid foo: 2 (lower than or equal to 1 expected)");
	}

	@Test
	void testLowerThanOrEqualToLong() {
		final var foo1 = 0L;
		final var foo2 = 1L;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo1, 1L)).isEqualTo(foo1);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo2, 1L)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", 2L, 1L))
				.withMessage("Invalid foo: 2L (lower than or equal to 1L expected)");
	}

	@Test
	void testLowerThanOrEqualToFloat() {
		final var foo1 = 0.0f;
		final var foo2 = 1.0f;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo1, 1.0f)).isEqualTo(foo1);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo2, 1.0f)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", 2.0f, 1.0f))
				.withMessage("Invalid foo: 2.0f (lower than or equal to 1.0f expected)");
	}

	@Test
	void testLowerThanOrEqualToDouble() {
		final var foo1 = 0.0d;
		final var foo2 = 1.0d;
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo1, 1.0d)).isEqualTo(foo1);
		assertThat(Ensure.lowerThanOrEqualTo("foo", foo2, 1.0d)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("foo", 2.0d, 1.0d))
				.withMessage("Invalid foo: 2.0d (lower than or equal to 1.0d expected)");
	}

	@Test
	void testGreaterThanByte() {
		final var foo = (byte) 2;
		assertThat(Ensure.greaterThan("foo", foo, (byte) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", (byte) 0, (byte) 1))
				.withMessage("Invalid foo: 0 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", (byte) 1, (byte) 1))
				.withMessage("Invalid foo: 1 (greater than 1 expected)");
	}

	@Test
	void testGreaterThanShort() {
		final var foo = (short) 2;
		assertThat(Ensure.greaterThan("foo", foo, (short) 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", (short) 0, (short) 1))
				.withMessage("Invalid foo: 0 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", (short) 1, (short) 1))
				.withMessage("Invalid foo: 1 (greater than 1 expected)");
	}

	@Test
	void testGreaterThanChar() {
		final var foo = 'b';
		assertThat(Ensure.greaterThan("foo", foo, 'a')).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", '?', 'a'))
				.withMessage("Invalid foo: '?' (greater than 'a' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 'a', 'a'))
				.withMessage("Invalid foo: 'a' (greater than 'a' expected)");
	}

	@Test
	void testGreaterThanInt() {
		final var foo = 2;
		assertThat(Ensure.greaterThan("foo", foo, 1)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 0, 1))
				.withMessage("Invalid foo: 0 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 1, 1))
				.withMessage("Invalid foo: 1 (greater than 1 expected)");
	}

	@Test
	void testGreaterThanLong() {
		final var foo = 2L;
		assertThat(Ensure.greaterThan("foo", foo, 1L)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 0L, 1L))
				.withMessage("Invalid foo: 0L (greater than 1L expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 1L, 1L))
				.withMessage("Invalid foo: 1L (greater than 1L expected)");
	}

	@Test
	void testGreaterThanFloat() {
		final var foo = 2.0f;
		assertThat(Ensure.greaterThan("foo", foo, 1.0f)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 0.0f, 1.0f))
				.withMessage("Invalid foo: 0.0f (greater than 1.0f expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 1.0f, 1.0f))
				.withMessage("Invalid foo: 1.0f (greater than 1.0f expected)");
	}

	@Test
	void testGreaterThanDouble() {
		final var foo = 2.0d;
		assertThat(Ensure.greaterThan("foo", foo, 1.0d)).isEqualTo(foo);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 0.0d, 1.0d))
				.withMessage("Invalid foo: 0.0d (greater than 1.0d expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("foo", 1.0d, 1.0d))
				.withMessage("Invalid foo: 1.0d (greater than 1.0d expected)");
	}

	@Test
	void testGreaterThanOrEqualToByte() {
		final var foo1 = (byte) 2;
		final var foo2 = (byte) 1;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo1, (byte) 1)).isEqualTo(foo1);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo2, (byte) 1)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", (byte) 0, (byte) 1))
				.withMessage("Invalid foo: 0 (greater than or equal to 1 expected)");
	}

	@Test
	void testGreaterThanOrEqualToShort() {
		final var foo1 = (short) 2;
		final var foo2 = (short) 1;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo1, (short) 1)).isEqualTo(foo1);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo2, (short) 1)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", (short) 0, (short) 1))
				.withMessage("Invalid foo: 0 (greater than or equal to 1 expected)");
	}

	@Test
	void testGreaterThanOrEqualToChar() {
		final var foo1 = 'b';
		final var foo2 = 'a';
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo1, 'a')).isEqualTo(foo1);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo2, 'a')).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", '?', 'a'))
				.withMessage("Invalid foo: '?' (greater than or equal to 'a' expected)");
	}

	@Test
	void testGreaterThanOrEqualToInt() {
		final var foo1 = 2;
		final var foo2 = 1;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo1, 1)).isEqualTo(foo1);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo2, 1)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", 0, 1))
				.withMessage("Invalid foo: 0 (greater than or equal to 1 expected)");
	}

	@Test
	void testGreaterThanOrEqualToLong() {
		final var foo1 = 2L;
		final var foo2 = 1L;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo1, 1L)).isEqualTo(foo1);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo2, 1L)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", 0L, 1L))
				.withMessage("Invalid foo: 0L (greater than or equal to 1L expected)");
	}

	@Test
	void testGreaterThanOrEqualToFloat() {
		final var foo1 = 2.0f;
		final var foo2 = 1.0f;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo1, 1.0f)).isEqualTo(foo1);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo2, 1.0f)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", 0.0f, 1.0f))
				.withMessage("Invalid foo: 0.0f (greater than or equal to 1.0f expected)");
	}

	@Test
	void testGreaterThanOrEqualToDouble() {
		final var foo1 = 2.0d;
		final var foo2 = 1.0d;
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo1, 1.0d)).isEqualTo(foo1);
		assertThat(Ensure.greaterThanOrEqualTo("foo", foo2, 1.0d)).isEqualTo(foo2);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("foo", 0.0d, 1.0d))
				.withMessage("Invalid foo: 0.0d (greater than or equal to 1.0d expected)");
	}

	@Test
	void testBetweenByte() {
		final var foo1 = (byte) 1;
		final var foo2 = (byte) 2;
		final var foo3 = (byte) 3;
		assertThat(Ensure.between("foo", foo1, (byte) 1, (byte) 3)).isEqualTo(foo1);
		assertThat(Ensure.between("foo", foo2, (byte) 1, (byte) 3)).isEqualTo(foo2);
		assertThat(Ensure.between("foo", foo3, (byte) 1, (byte) 3)).isEqualTo(foo3);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", (byte) 0, (byte) 1, (byte) 3))
				.withMessage("Invalid foo: 0 (between 1 and 3 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", (byte) 4, (byte) 1, (byte) 3))
				.withMessage("Invalid foo: 4 (between 1 and 3 expected)");
	}

	@Test
	void testBetweenShort() {
		final var foo1 = (short) 1;
		final var foo2 = (short) 2;
		final var foo3 = (short) 3;
		assertThat(Ensure.between("foo", foo1, (short) 1, (short) 3)).isEqualTo(foo1);
		assertThat(Ensure.between("foo", foo2, (short) 1, (short) 3)).isEqualTo(foo2);
		assertThat(Ensure.between("foo", foo3, (short) 1, (short) 3)).isEqualTo(foo3);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", (short) 0, (short) 1, (short) 3))
				.withMessage("Invalid foo: 0 (between 1 and 3 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", (short) 4, (short) 1, (short) 3))
				.withMessage("Invalid foo: 4 (between 1 and 3 expected)");
	}

	@Test
	void testBetweenChar() {
		final var foo1 = 'a';
		final var foo2 = 'b';
		final var foo3 = 'c';
		assertThat(Ensure.between("foo", foo1, 'a', 'c')).isEqualTo(foo1);
		assertThat(Ensure.between("foo", foo2, 'a', 'c')).isEqualTo(foo2);
		assertThat(Ensure.between("foo", foo3, 'a', 'c')).isEqualTo(foo3);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", '?', 'a', 'c'))
				.withMessage("Invalid foo: '?' (between 'a' and 'c' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 'd', 'a', 'c'))
				.withMessage("Invalid foo: 'd' (between 'a' and 'c' expected)");
	}

	@Test
	void testBetweenInt() {
		final var foo1 = 1;
		final var foo2 = 2;
		final var foo3 = 3;
		assertThat(Ensure.between("foo", foo1, 1, 3)).isEqualTo(foo1);
		assertThat(Ensure.between("foo", foo2, 1, 3)).isEqualTo(foo2);
		assertThat(Ensure.between("foo", foo3, 1, 3)).isEqualTo(foo3);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 0, 1, 3))
				.withMessage("Invalid foo: 0 (between 1 and 3 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 4, 1, 3))
				.withMessage("Invalid foo: 4 (between 1 and 3 expected)");
	}

	@Test
	void testBetweenLong() {
		final var foo1 = 1L;
		final var foo2 = 2L;
		final var foo3 = 3L;
		assertThat(Ensure.between("foo", foo1, 1L, 3L)).isEqualTo(foo1);
		assertThat(Ensure.between("foo", foo2, 1L, 3L)).isEqualTo(foo2);
		assertThat(Ensure.between("foo", foo3, 1L, 3L)).isEqualTo(foo3);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 0L, 1L, 3L))
				.withMessage("Invalid foo: 0L (between 1L and 3L expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 4L, 1L, 3L))
				.withMessage("Invalid foo: 4L (between 1L and 3L expected)");
	}

	@Test
	void testBetweenFloat() {
		final var foo1 = 1.0f;
		final var foo2 = 2.0f;
		final var foo3 = 3.0f;
		assertThat(Ensure.between("foo", foo1, 1.0f, 3.0f)).isEqualTo(foo1);
		assertThat(Ensure.between("foo", foo2, 1.0f, 3.0f)).isEqualTo(foo2);
		assertThat(Ensure.between("foo", foo3, 1.0f, 3.0f)).isEqualTo(foo3);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 0.0f, 1.0f, 3.0f))
				.withMessage("Invalid foo: 0.0f (between 1.0f and 3.0f expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 4.0f, 1.0f, 3.0f))
				.withMessage("Invalid foo: 4.0f (between 1.0f and 3.0f expected)");
	}

	@Test
	void testBetweenDouble() {
		final var foo1 = 1.0d;
		final var foo2 = 2.0d;
		final var foo3 = 3.0d;
		assertThat(Ensure.between("foo", foo1, 1.0d, 3.0d)).isEqualTo(foo1);
		assertThat(Ensure.between("foo", foo2, 1.0d, 3.0d)).isEqualTo(foo2);
		assertThat(Ensure.between("foo", foo3, 1.0d, 3.0d)).isEqualTo(foo3);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 0.0d, 1.0d, 3.0d))
				.withMessage("Invalid foo: 0.0d (between 1.0d and 3.0d expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("foo", 4.0d, 1.0d, 3.0d))
				.withMessage("Invalid foo: 4.0d (between 1.0d and 3.0d expected)");
	}
}