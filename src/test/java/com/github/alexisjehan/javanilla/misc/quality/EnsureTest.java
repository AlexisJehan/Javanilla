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
package com.github.alexisjehan.javanilla.misc.quality;

import com.github.alexisjehan.javanilla.io.InputStreams;
import com.github.alexisjehan.javanilla.io.Readers;
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
import com.github.alexisjehan.javanilla.util.bag.Bags;
import com.github.alexisjehan.javanilla.util.Iterables;
import com.github.alexisjehan.javanilla.util.Iterators;
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
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class EnsureTest {

	@Test
	void testNotNull() {
		final var value = Integer.valueOf(1);
		assertThat(Ensure.notNull("value", value)).isSameAs(value);
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNull("value", null))
				.withMessage("Invalid value (not null expected)");
	}

	@Test
	void testNotNullInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Ensure.notNull(null, 1));
	}

	@Test
	void testNotNullAndNotNullElementsArray() {
		final var array = ObjectArrays.singleton(1);
		assertThat(Ensure.notNullAndNotNullElements("array", array)).isSameAs(array);
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNullAndNotNullElements("array", ObjectArrays.singleton(Integer.class, null)))
				.withMessage("Invalid array element at index 0 (not null expected)");
	}

	@Test
	void testNotNullAndNotNullElementsIterable() {
		final var iterable = Iterables.singleton(1);
		assertThat(Ensure.notNullAndNotNullElements("iterable", iterable)).isSameAs(iterable);
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNullAndNotNullElements("iterable", Iterables.singleton(null)))
				.withMessage("Invalid iterable element at index 0 (not null expected)");
	}

	@Test
	void testNotNullAndNotNullKeys() {
		final var map = Map.of("foo", 1);
		assertThat(Ensure.notNullAndNotNullKeys("map", map)).isSameAs(map);
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNullAndNotNullKeys("map", Collections.singletonMap(null, 1)))
				.withMessage("Invalid map key (not null expected)");
		assertThatCode(() -> Ensure.notNullAndNotNullKeys("map", Collections.singletonMap("foo", null)))
				.doesNotThrowAnyException();
	}

	@Test
	void testNotNullAndNotNullValues() {
		final var map = Map.of("foo", 1);
		assertThat(Ensure.notNullAndNotNullValues("map", map)).isSameAs(map);
		assertThatCode(() -> Ensure.notNullAndNotNullValues("map", Collections.singletonMap(null, 1)))
				.doesNotThrowAnyException();
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNullAndNotNullValues("map", Collections.singletonMap("foo", null)))
				.withMessage("Invalid map value at key foo (not null expected)");
	}

	@Test
	void testNotNullAndNotNullKeysAndValues() {
		final var map = Map.of("foo", 1);
		assertThat(Ensure.notNullAndNotNullKeysAndValues("map", map)).isSameAs(map);
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNullAndNotNullKeysAndValues("map", Collections.singletonMap(null, 1)))
				.withMessage("Invalid map key (not null expected)");
		assertThatNullPointerException()
				.isThrownBy(() -> Ensure.notNullAndNotNullKeysAndValues("map", Collections.singletonMap("foo", null)))
				.withMessage("Invalid map value at key foo (not null expected)");
	}

	@Test
	void testNotNullAndNotEmptyCharSequence() {
		final var charSequence = "1";
		assertThat(Ensure.notNullAndNotEmpty("charSequence", charSequence)).isSameAs(charSequence);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("charSequence", Strings.EMPTY))
				.withMessage("Invalid charSequence: \"\" (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyBooleanArray() {
		final var array = BooleanArrays.singleton(true);
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", BooleanArrays.EMPTY))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyByteArray() {
		final var array = ByteArrays.singleton((byte) 1);
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", ByteArrays.EMPTY))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyShortArray() {
		final var array = ShortArrays.singleton((short) 1);
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", ShortArrays.EMPTY))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyCharArray() {
		final var array = CharArrays.singleton('a');
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", CharArrays.EMPTY))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyIntArray() {
		final var array = IntArrays.singleton(1);
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", IntArrays.EMPTY))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyLongArray() {
		final var array = LongArrays.singleton(1L);
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", LongArrays.EMPTY))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyFloatArray() {
		final var array = FloatArrays.singleton(1.0f);
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", FloatArrays.EMPTY))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyDoubleArray() {
		final var array = DoubleArrays.singleton(1.0d);
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", DoubleArrays.EMPTY))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyArray() {
		final var array = ObjectArrays.singleton(1);
		assertThat(Ensure.notNullAndNotEmpty("array", array)).isSameAs(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("array", ObjectArrays.empty(Integer.class)))
				.withMessage("Invalid array: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyCollection() {
		final var collection = Set.of(1);
		assertThat(Ensure.notNullAndNotEmpty("collection", collection)).isSameAs(collection);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("collection", Set.of()))
				.withMessage("Invalid collection: [] (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyMap() {
		final var map = Map.of("foo", 1);
		assertThat(Ensure.notNullAndNotEmpty("map", map)).isSameAs(map);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("map", Map.of()))
				.withMessage("Invalid map: {} (not empty expected)");
	}

	@Test
	@SuppressWarnings("deprecation")
	void testNotNullAndNotEmptyBagLegacy() {
		final var bag = com.github.alexisjehan.javanilla.util.collection.bags.Bags.singleton("foo", 1);
		assertThat(Ensure.notNullAndNotEmpty("bag", bag)).isSameAs(bag);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("bag", com.github.alexisjehan.javanilla.util.collection.bags.Bags.empty()))
				.withMessage("Invalid bag: {} (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyBag() {
		final var bag = Bags.singleton("foo", 1);
		assertThat(Ensure.notNullAndNotEmpty("bag", bag)).isSameAs(bag);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("bag", Bags.empty()))
				.withMessage("Invalid bag: {} (not empty expected)");
	}

	@Test
	void testNotNullAndNotEmptyIterator() {
		final var iterator = Iterators.singleton(1);
		assertThat(Ensure.notNullAndNotEmpty("iterator", iterator)).isSameAs(iterator);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEmpty("iterator", Collections.emptyIterator()))
				.withMessage("Invalid iterator: " + Collections.emptyIterator() + " (not empty expected)");
	}

	@Test
	void testNotNullAndNotBlank() {
		final var charSequence = "1";
		assertThat(Ensure.notNullAndNotBlank("charSequence", charSequence)).isSameAs(charSequence);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotBlank("charSequence", " "))
				.withMessage("Invalid charSequence: \" \" (not blank expected)");
	}

	@Test
	void testNotNullAndMatches() {
		final var pattern = Pattern.compile("^\\d$");
		final var charSequence = "1";
		assertThat(Ensure.notNullAndMatches("charSequence", charSequence, pattern)).isSameAs(charSequence);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndMatches("charSequence", "a", pattern))
				.withMessage("Invalid charSequence: \"a\" (matching " + pattern + " expected)");
	}

	@Test
	void testNotNullAndMatchesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Ensure.notNullAndMatches("charSequence", "1", null));
	}

	@Test
	void testNotNullAndMarkSupportedInputStream() {
		final var inputStream = InputStreams.buffered(InputStreams.EMPTY);
		assertThat(Ensure.notNullAndMarkSupported("inputStream", inputStream)).isSameAs(inputStream);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndMarkSupported("inputStream", InputStreams.EMPTY))
				.withMessage("Invalid inputStream: " + InputStreams.EMPTY + " (mark supported expected)");
	}

	@Test
	void testNotNullAndMarkSupportedReader() {
		final var reader = Readers.buffered(Readers.EMPTY);
		assertThat(Ensure.notNullAndMarkSupported("reader", reader)).isSameAs(reader);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndMarkSupported("reader", Readers.EMPTY))
				.withMessage("Invalid reader: " + Readers.EMPTY + " (mark supported expected)");
	}

	@Test
	void testNotNullAndExists(@TempDir final Path tmpDirectory) throws IOException {
		final var path = tmpDirectory.resolve("testNotNullAndExists");
		Files.createFile(path);
		assertThat(Ensure.notNullAndExists("path", path)).isSameAs(path);
		Files.delete(path);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndExists("path", path))
				.withMessage("Invalid path: " + path + " (existing expected)");
	}

	@Test
	void testNotNullAndFile(@TempDir final Path tmpDirectory) throws IOException {
		final var path = tmpDirectory.resolve("testNotNullAndFile");
		Files.createFile(path);
		assertThat(Ensure.notNullAndFile("path", path)).isSameAs(path);
		Files.delete(path);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndFile("path", path))
				.withMessage("Invalid path: " + path + " (existing file expected)");
		Files.createDirectory(path);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndFile("path", path))
				.withMessage("Invalid path: " + path + " (existing file expected)");
	}

	@Test
	void testNotNullAndDirectory(@TempDir final Path tmpDirectory) throws IOException {
		final var path = tmpDirectory.resolve("testNotNullAndDirectory");
		Files.createDirectory(path);
		assertThat(Ensure.notNullAndDirectory("path", path)).isSameAs(path);
		Files.delete(path);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndDirectory("path", path))
				.withMessage("Invalid path: " + path + " (existing directory expected)");
		Files.createFile(path);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndDirectory("path", path))
				.withMessage("Invalid path: " + path + " (existing directory expected)");
	}

	@Test
	void testEqualToBoolean() {
		final var value = true;
		assertThat(Ensure.equalTo("value", value, true)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("value", value, false))
				.withMessage("Invalid value: true (equal to false expected)");
	}

	@Test
	void testEqualToByte() {
		final var value = (byte) 1;
		assertThat(Ensure.equalTo("value", value, (byte) 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("value", value, (byte) 0))
				.withMessage("Invalid value: 1 (equal to 0 expected)");
	}

	@Test
	void testEqualToShort() {
		final var value = (short) 1;
		assertThat(Ensure.equalTo("value", value, (short) 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("value", value, (short) 0))
				.withMessage("Invalid value: 1 (equal to 0 expected)");
	}

	@Test
	void testEqualToChar() {
		final var value = 'a';
		assertThat(Ensure.equalTo("value", value, 'a')).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("value", value, '?'))
				.withMessage("Invalid value: 'a' (equal to '?' expected)");
	}

	@Test
	void testEqualToInt() {
		final var value = 1;
		assertThat(Ensure.equalTo("value", value, 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("value", value, 0))
				.withMessage("Invalid value: 1 (equal to 0 expected)");
	}

	@Test
	void testEqualToLong() {
		final var value = 1L;
		assertThat(Ensure.equalTo("value", value, 1L)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("value", value, 0L))
				.withMessage("Invalid value: 1 (equal to 0 expected)");
	}

	@Test
	void testEqualToFloat() {
		final var value = 1.0f;
		assertThat(Ensure.equalTo("value", value, 1.0f)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("value", value, 0.0f))
				.withMessage("Invalid value: 1.0 (equal to 0.0 expected)");
	}

	@Test
	void testEqualToDouble() {
		final var value = 1.0d;
		assertThat(Ensure.equalTo("value", value, 1.0d)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.equalTo("value", value, 0.0d))
				.withMessage("Invalid value: 1.0 (equal to 0.0 expected)");
	}

	@Test
	void testNotNullAndEqualToObject() {
		final var value = Integer.valueOf(1);
		assertThat(Ensure.notNullAndEqualTo("value", value, 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("value", value, 0))
				.withMessage("Invalid value: 1 (equal to 0 expected)");
	}

	@Test
	void testNotNullAndEqualToBooleanArray() {
		final var array = BooleanArrays.singleton(true);
		assertThat(Ensure.notNullAndEqualTo("array", array, BooleanArrays.singleton(true))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, BooleanArrays.singleton(false)))
				.withMessage("Invalid array: [true] (equal to [false] expected)");
	}

	@Test
	void testNotNullAndEqualToByteArray() {
		final var array = ByteArrays.singleton((byte) 1);
		assertThat(Ensure.notNullAndEqualTo("array", array, ByteArrays.singleton((byte) 1))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, ByteArrays.singleton((byte) 0)))
				.withMessage("Invalid array: [1] (equal to [0] expected)");
	}

	@Test
	void testNotNullAndEqualToShortArray() {
		final var array = ShortArrays.singleton((short) 1);
		assertThat(Ensure.notNullAndEqualTo("array", array, ShortArrays.singleton((short) 1))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, ShortArrays.singleton((short) 0)))
				.withMessage("Invalid array: [1] (equal to [0] expected)");
	}

	@Test
	void testNotNullAndEqualToCharArray() {
		final var array = CharArrays.singleton('a');
		assertThat(Ensure.notNullAndEqualTo("array", array, CharArrays.singleton('a'))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, CharArrays.singleton('?')))
				.withMessage("Invalid array: ['a'] (equal to ['?'] expected)");
	}

	@Test
	void testNotNullAndEqualToIntArray() {
		final var array = IntArrays.singleton(1);
		assertThat(Ensure.notNullAndEqualTo("array", array, IntArrays.singleton(1))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, IntArrays.singleton(0)))
				.withMessage("Invalid array: [1] (equal to [0] expected)");
	}

	@Test
	void testNotNullAndEqualToLongArray() {
		final var array = LongArrays.singleton(1L);
		assertThat(Ensure.notNullAndEqualTo("array", array, LongArrays.singleton(1L))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, LongArrays.singleton(0L)))
				.withMessage("Invalid array: [1] (equal to [0] expected)");
	}

	@Test
	void testNotNullAndEqualToFloatArray() {
		final var array = FloatArrays.singleton(1.0f);
		assertThat(Ensure.notNullAndEqualTo("array", array, FloatArrays.singleton(1.0f))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, FloatArrays.singleton(0.0f)))
				.withMessage("Invalid array: [1.0] (equal to [0.0] expected)");
	}

	@Test
	void testNotNullAndEqualToDoubleArray() {
		final var array = DoubleArrays.singleton(1.0d);
		assertThat(Ensure.notNullAndEqualTo("array", array, DoubleArrays.singleton(1.0d))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, DoubleArrays.singleton(0.0d)))
				.withMessage("Invalid array: [1.0] (equal to [0.0] expected)");
	}

	@Test
	void testNotNullAndEqualToArray() {
		final var array = ObjectArrays.singleton(1);
		assertThat(Ensure.notNullAndEqualTo("array", array, ObjectArrays.singleton(1))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndEqualTo("array", array, ObjectArrays.singleton(0)))
				.withMessage("Invalid array: [1] (equal to [0] expected)");
	}

	@Test
	void testNotEqualToBoolean() {
		final var value = true;
		assertThat(Ensure.notEqualTo("value", value, false)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("value", value, true))
				.withMessage("Invalid value: true (not equal to true expected)");
	}

	@Test
	void testNotEqualToByte() {
		final var value = (byte) 1;
		assertThat(Ensure.notEqualTo("value", value, (byte) 0)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("value", value, (byte) 1))
				.withMessage("Invalid value: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotEqualToShort() {
		final var value = (short) 1;
		assertThat(Ensure.notEqualTo("value", value, (short) 0)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("value", value, (short) 1))
				.withMessage("Invalid value: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotEqualToChar() {
		final var value = 'a';
		assertThat(Ensure.notEqualTo("value", value, '?')).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("value", value, 'a'))
				.withMessage("Invalid value: 'a' (not equal to 'a' expected)");
	}

	@Test
	void testNotEqualToInt() {
		final var value = 1;
		assertThat(Ensure.notEqualTo("value", value, 0)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("value", value, 1))
				.withMessage("Invalid value: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotEqualToLong() {
		final var value = 1L;
		assertThat(Ensure.notEqualTo("value", value, 0L)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("value", value, 1L))
				.withMessage("Invalid value: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotEqualToFloat() {
		final var value = 1.0f;
		assertThat(Ensure.notEqualTo("value", value, 0.0f)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("value", value, 1.0f))
				.withMessage("Invalid value: 1.0 (not equal to 1.0 expected)");
	}

	@Test
	void testNotEqualToDouble() {
		final var value = 1.0d;
		assertThat(Ensure.notEqualTo("value", value, 0.0d)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notEqualTo("value", value, 1.0d))
				.withMessage("Invalid value: 1.0 (not equal to 1.0 expected)");
	}

	@Test
	void testNotNullAndNotEqualToObject() {
		final var value = Integer.valueOf(1);
		assertThat(Ensure.notNullAndNotEqualTo("value", value, 0)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("value", value, 1))
				.withMessage("Invalid value: 1 (not equal to 1 expected)");
	}

	@Test
	void testNotNullAndNotEqualToBooleanArray() {
		final var array = BooleanArrays.singleton(true);
		assertThat(Ensure.notNullAndNotEqualTo("array", array, BooleanArrays.singleton(false))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, BooleanArrays.singleton(true)))
				.withMessage("Invalid array: [true] (not equal to [true] expected)");
	}

	@Test
	void testNotNullAndNotEqualToByteArray() {
		final var array = ByteArrays.singleton((byte) 1);
		assertThat(Ensure.notNullAndNotEqualTo("array", array, ByteArrays.singleton((byte) 0))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, ByteArrays.singleton((byte) 1)))
				.withMessage("Invalid array: [1] (not equal to [1] expected)");
	}

	@Test
	void testNotNullAndNotEqualToShortArray() {
		final var array = ShortArrays.singleton((short) 1);
		assertThat(Ensure.notNullAndNotEqualTo("array", array, ShortArrays.singleton((short) 0))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, ShortArrays.singleton((short) 1)))
				.withMessage("Invalid array: [1] (not equal to [1] expected)");
	}

	@Test
	void testNotNullAndNotEqualToCharArray() {
		final var array = CharArrays.singleton('a');
		assertThat(Ensure.notNullAndNotEqualTo("array", array, CharArrays.singleton('?'))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, CharArrays.singleton('a')))
				.withMessage("Invalid array: ['a'] (not equal to ['a'] expected)");
	}

	@Test
	void testNotNullAndNotEqualToIntArray() {
		final var array = IntArrays.singleton(1);
		assertThat(Ensure.notNullAndNotEqualTo("array", array, IntArrays.singleton(0))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, IntArrays.singleton(1)))
				.withMessage("Invalid array: [1] (not equal to [1] expected)");
	}

	@Test
	void testNotNullAndNotEqualToLongArray() {
		final var array = LongArrays.singleton(1L);
		assertThat(Ensure.notNullAndNotEqualTo("array", array, LongArrays.singleton(0L))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, LongArrays.singleton(1L)))
				.withMessage("Invalid array: [1] (not equal to [1] expected)");
	}

	@Test
	void testNotNullAndNotEqualToFloatArray() {
		final var array = FloatArrays.singleton(1.0f);
		assertThat(Ensure.notNullAndNotEqualTo("array", array, FloatArrays.singleton(0.0f))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, FloatArrays.singleton(1.0f)))
				.withMessage("Invalid array: [1.0] (not equal to [1.0] expected)");
	}

	@Test
	void testNotNullAndNotEqualToDoubleArray() {
		final var array = DoubleArrays.singleton(1.0d);
		assertThat(Ensure.notNullAndNotEqualTo("array", array, DoubleArrays.singleton(0.0d))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, DoubleArrays.singleton(1.0d)))
				.withMessage("Invalid array: [1.0] (not equal to [1.0] expected)");
	}

	@Test
	void testNotNullAndNotEqualToArray() {
		final var array = ObjectArrays.singleton(1);
		assertThat(Ensure.notNullAndNotEqualTo("array", array, ObjectArrays.singleton(0))).isEqualTo(array);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndNotEqualTo("array", array, ObjectArrays.singleton(1)))
				.withMessage("Invalid array: [1] (not equal to [1] expected)");
	}

	@Test
	void testLowerThanByte() {
		final var value = (byte) 1;
		assertThat(Ensure.lowerThan("value", value, (byte) 2)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, (byte) 1))
				.withMessage("Invalid value: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, (byte) 0))
				.withMessage("Invalid value: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanShort() {
		final var value = (short) 1;
		assertThat(Ensure.lowerThan("value", value, (short) 2)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, (short) 1))
				.withMessage("Invalid value: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, (short) 0))
				.withMessage("Invalid value: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanChar() {
		final var value = 'a';
		assertThat(Ensure.lowerThan("value", value, 'b')).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 'a'))
				.withMessage("Invalid value: 'a' (lower than 'a' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, '?'))
				.withMessage("Invalid value: 'a' (lower than '?' expected)");
	}

	@Test
	void testLowerThanInt() {
		final var value = 1;
		assertThat(Ensure.lowerThan("value", value, 2)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 1))
				.withMessage("Invalid value: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 0))
				.withMessage("Invalid value: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanLong() {
		final var value = 1L;
		assertThat(Ensure.lowerThan("value", value, 2L)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 1L))
				.withMessage("Invalid value: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 0L))
				.withMessage("Invalid value: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanFloat() {
		final var value = 1.0f;
		assertThat(Ensure.lowerThan("value", value, 2.0f)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 1.0f))
				.withMessage("Invalid value: 1.0 (lower than 1.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 0.0f))
				.withMessage("Invalid value: 1.0 (lower than 0.0 expected)");
	}

	@Test
	void testLowerThanDouble() {
		final var value = 1.0d;
		assertThat(Ensure.lowerThan("value", value, 2.0d)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 1.0d))
				.withMessage("Invalid value: 1.0 (lower than 1.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThan("value", value, 0.0d))
				.withMessage("Invalid value: 1.0 (lower than 0.0 expected)");
	}

	@Test
	void testNotNullAndLowerThan() {
		final var value = Integer.valueOf(1);
		assertThat(Ensure.notNullAndLowerThan("value", value, 2)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndLowerThan("value", value, 1))
				.withMessage("Invalid value: 1 (lower than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndLowerThan("value", value, 0))
				.withMessage("Invalid value: 1 (lower than 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToByte() {
		final var value = (byte) 1;
		assertThat(Ensure.lowerThanOrEqualTo("value", value, (byte) 2)).isEqualTo(value);
		assertThat(Ensure.lowerThanOrEqualTo("value", value, (byte) 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("value", value, (byte) 0))
				.withMessage("Invalid value: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToShort() {
		final var value = (short) 1;
		assertThat(Ensure.lowerThanOrEqualTo("value", value, (short) 2)).isEqualTo(value);
		assertThat(Ensure.lowerThanOrEqualTo("value", value, (short) 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("value", value, (short) 0))
				.withMessage("Invalid value: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToChar() {
		final var value = 'a';
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 'b')).isEqualTo(value);
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 'a')).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("value", value, '?'))
				.withMessage("Invalid value: 'a' (lower than or equal to '?' expected)");
	}

	@Test
	void testLowerThanOrEqualToInt() {
		final var value = 1;
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 2)).isEqualTo(value);
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("value", value, 0))
				.withMessage("Invalid value: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToLong() {
		final var value = 1L;
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 2L)).isEqualTo(value);
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 1L)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("value", value, 0L))
				.withMessage("Invalid value: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testLowerThanOrEqualToFloat() {
		final var value = 1.0f;
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 2.0f)).isEqualTo(value);
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 1.0f)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("value", value, 0.0f))
				.withMessage("Invalid value: 1.0 (lower than or equal to 0.0 expected)");
	}

	@Test
	void testLowerThanOrEqualToDouble() {
		final var value = 1.0d;
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 2.0d)).isEqualTo(value);
		assertThat(Ensure.lowerThanOrEqualTo("value", value, 1.0d)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.lowerThanOrEqualTo("value", value, 0.0d))
				.withMessage("Invalid value: 1.0 (lower than or equal to 0.0 expected)");
	}

	@Test
	void testNotNullAndLowerThanOrEqualTo() {
		final var value = Integer.valueOf(1);
		assertThat(Ensure.notNullAndLowerThanOrEqualTo("value", value, 2)).isEqualTo(value);
		assertThat(Ensure.notNullAndLowerThanOrEqualTo("value", value, 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndLowerThanOrEqualTo("value", value, 0))
				.withMessage("Invalid value: 1 (lower than or equal to 0 expected)");
	}

	@Test
	void testGreaterThanByte() {
		final var value = (byte) 1;
		assertThat(Ensure.greaterThan("value", value, (byte) 0)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, (byte) 1))
				.withMessage("Invalid value: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, (byte) 2))
				.withMessage("Invalid value: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanShort() {
		final var value = (short) 1;
		assertThat(Ensure.greaterThan("value", value, (short) 0)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, (short) 1))
				.withMessage("Invalid value: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, (short) 2))
				.withMessage("Invalid value: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanChar() {
		final var value = 'a';
		assertThat(Ensure.greaterThan("value", value, '?')).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 'a'))
				.withMessage("Invalid value: 'a' (greater than 'a' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 'b'))
				.withMessage("Invalid value: 'a' (greater than 'b' expected)");
	}

	@Test
	void testGreaterThanInt() {
		final var value = 1;
		assertThat(Ensure.greaterThan("value", value, 0)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 1))
				.withMessage("Invalid value: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 2))
				.withMessage("Invalid value: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanLong() {
		final var value = 1L;
		assertThat(Ensure.greaterThan("value", value, 0L)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 1L))
				.withMessage("Invalid value: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 2L))
				.withMessage("Invalid value: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanFloat() {
		final var value = 1.0f;
		assertThat(Ensure.greaterThan("value", value, 0.0f)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 1.0f))
				.withMessage("Invalid value: 1.0 (greater than 1.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 2.0f))
				.withMessage("Invalid value: 1.0 (greater than 2.0 expected)");
	}

	@Test
	void testGreaterThanDouble() {
		final var value = 1.0d;
		assertThat(Ensure.greaterThan("value", value, 0.0d)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 1.0d))
				.withMessage("Invalid value: 1.0 (greater than 1.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThan("value", value, 2.0d))
				.withMessage("Invalid value: 1.0 (greater than 2.0 expected)");
	}

	@Test
	void testNotNullAndGreaterThan() {
		final var value = Integer.valueOf(1);
		assertThat(Ensure.notNullAndGreaterThan("value", value, 0)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndGreaterThan("value", value, 1))
				.withMessage("Invalid value: 1 (greater than 1 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndGreaterThan("value", value, 2))
				.withMessage("Invalid value: 1 (greater than 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToByte() {
		final var value = (byte) 1;
		assertThat(Ensure.greaterThanOrEqualTo("value", value, (byte) 0)).isEqualTo(value);
		assertThat(Ensure.greaterThanOrEqualTo("value", value, (byte) 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("value", value, (byte) 2))
				.withMessage("Invalid value: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToShort() {
		final var value = (short) 1;
		assertThat(Ensure.greaterThanOrEqualTo("value", value, (short) 0)).isEqualTo(value);
		assertThat(Ensure.greaterThanOrEqualTo("value", value, (short) 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("value", value, (short) 2))
				.withMessage("Invalid value: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToChar() {
		final var value = 'a';
		assertThat(Ensure.greaterThanOrEqualTo("value", value, '?')).isEqualTo(value);
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 'a')).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("value", value, 'b'))
				.withMessage("Invalid value: 'a' (greater than or equal to 'b' expected)");
	}

	@Test
	void testGreaterThanOrEqualToInt() {
		final var value = 1;
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 0)).isEqualTo(value);
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("value", value, 2))
				.withMessage("Invalid value: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToLong() {
		final var value = 1L;
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 0L)).isEqualTo(value);
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 1L)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("value", value, 2L))
				.withMessage("Invalid value: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testGreaterThanOrEqualToFloat() {
		final var value = 1.0f;
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 0.0f)).isEqualTo(value);
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 1.0f)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("value", value, 2.0f))
				.withMessage("Invalid value: 1.0 (greater than or equal to 2.0 expected)");
	}

	@Test
	void testGreaterThanOrEqualToDouble() {
		final var value = 1.0d;
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 0.0d)).isEqualTo(value);
		assertThat(Ensure.greaterThanOrEqualTo("value", value, 1.0d)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.greaterThanOrEqualTo("value", value, 2.0d))
				.withMessage("Invalid value: 1.0 (greater than or equal to 2.0 expected)");
	}

	@Test
	void testNotNullAndGreaterThanOrEqualTo() {
		final var value = Integer.valueOf(1);
		assertThat(Ensure.notNullAndGreaterThanOrEqualTo("value", value, 0)).isEqualTo(value);
		assertThat(Ensure.notNullAndGreaterThanOrEqualTo("value", value, 1)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndGreaterThanOrEqualTo("value", value, 2))
				.withMessage("Invalid value: 1 (greater than or equal to 2 expected)");
	}

	@Test
	void testBetweenByte() {
		final var value = (byte) 1;
		assertThat(Ensure.between("value", value, (byte) 0, (byte) 1)).isEqualTo(value);
		assertThat(Ensure.between("value", value, (byte) 1, (byte) 1)).isEqualTo(value);
		assertThat(Ensure.between("value", value, (byte) 1, (byte) 2)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, (byte) 0, (byte) 0))
				.withMessage("Invalid value: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, (byte) 2, (byte) 2))
				.withMessage("Invalid value: 1 (between 2 and 2 expected)");
	}

	@Test
	void testBetweenShort() {
		final var value = (short) 1;
		assertThat(Ensure.between("value", value, (short) 0, (short) 1)).isEqualTo(value);
		assertThat(Ensure.between("value", value, (short) 1, (short) 1)).isEqualTo(value);
		assertThat(Ensure.between("value", value, (short) 1, (short) 2)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, (short) 0, (short) 0))
				.withMessage("Invalid value: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, (short) 2, (short) 2))
				.withMessage("Invalid value: 1 (between 2 and 2 expected)");
	}

	@Test
	void testBetweenChar() {
		final var value = 'a';
		assertThat(Ensure.between("value", value, '?', 'a')).isEqualTo(value);
		assertThat(Ensure.between("value", value, 'a', 'a')).isEqualTo(value);
		assertThat(Ensure.between("value", value, 'a', 'b')).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, '?', '?'))
				.withMessage("Invalid value: 'a' (between '?' and '?' expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 'b', 'b'))
				.withMessage("Invalid value: 'a' (between 'b' and 'b' expected)");
	}

	@Test
	void testBetweenInt() {
		final var value = 1;
		assertThat(Ensure.between("value", value, 0, 1)).isEqualTo(value);
		assertThat(Ensure.between("value", value, 1, 1)).isEqualTo(value);
		assertThat(Ensure.between("value", value, 1, 2)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 0, 0))
				.withMessage("Invalid value: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 2, 2))
				.withMessage("Invalid value: 1 (between 2 and 2 expected)");
	}

	@Test
	void testBetweenLong() {
		final var value = 1L;
		assertThat(Ensure.between("value", value, 0L, 1L)).isEqualTo(value);
		assertThat(Ensure.between("value", value, 1L, 1L)).isEqualTo(value);
		assertThat(Ensure.between("value", value, 1L, 2L)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 0L, 0L))
				.withMessage("Invalid value: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 2L, 2L))
				.withMessage("Invalid value: 1 (between 2 and 2 expected)");
	}

	@Test
	void testBetweenFloat() {
		final var value = 1.0f;
		assertThat(Ensure.between("value", value, 0.0f, 1.0f)).isEqualTo(value);
		assertThat(Ensure.between("value", value, 1.0f, 1.0f)).isEqualTo(value);
		assertThat(Ensure.between("value", value, 1.0f, 2.0f)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 0.0f, 0.0f))
				.withMessage("Invalid value: 1.0 (between 0.0 and 0.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 2.0f, 2.0f))
				.withMessage("Invalid value: 1.0 (between 2.0 and 2.0 expected)");
	}

	@Test
	void testBetweenDouble() {
		final var value = 1.0d;
		assertThat(Ensure.between("value", value, 0.0d, 1.0d)).isEqualTo(value);
		assertThat(Ensure.between("value", value, 1.0d, 1.0d)).isEqualTo(value);
		assertThat(Ensure.between("value", value, 1.0d, 2.0d)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 0.0d, 0.0d))
				.withMessage("Invalid value: 1.0 (between 0.0 and 0.0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.between("value", value, 2.0d, 2.0d))
				.withMessage("Invalid value: 1.0 (between 2.0 and 2.0 expected)");
	}

	@Test
	void testNotNullAndBetween() {
		final var value = Integer.valueOf(1);
		assertThat(Ensure.notNullAndBetween("value", value, 0, 1)).isEqualTo(value);
		assertThat(Ensure.notNullAndBetween("value", value, 1, 1)).isEqualTo(value);
		assertThat(Ensure.notNullAndBetween("value", value, 1, 2)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndBetween("value", value, 0, 0))
				.withMessage("Invalid value: 1 (between 0 and 0 expected)");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.notNullAndBetween("value", value, 2, 2))
				.withMessage("Invalid value: 1 (between 2 and 2 expected)");
	}

	@Test
	void testMultipleOfByte() {
		final var value = (byte) 4;
		assertThat(Ensure.multipleOf("value", value, (byte) 2)).isEqualTo(value);
		assertThat(Ensure.multipleOf("value", value, (byte) 4)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.multipleOf("value", value, (byte) 3))
				.withMessage("Invalid value: 4 (multiple of 3 expected)");
	}

	@Test
	void testMultipleOfShort() {
		final var value = (short) 4;
		assertThat(Ensure.multipleOf("value", value, (short) 2)).isEqualTo(value);
		assertThat(Ensure.multipleOf("value", value, (short) 4)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.multipleOf("value", value, (short) 3))
				.withMessage("Invalid value: 4 (multiple of 3 expected)");
	}

	@Test
	void testMultipleOfChar() {
		final var value = (char) 4;
		assertThat(Ensure.multipleOf("value", value, (char) 2)).isEqualTo(value);
		assertThat(Ensure.multipleOf("value", value, (char) 4)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.multipleOf("value", value, (char) 3))
				.withMessage("Invalid value: " + ToString.toString(value) + " (multiple of " + ToString.toString((char) 3) + " expected)");
	}

	@Test
	void testMultipleOfInt() {
		final var value = 4;
		assertThat(Ensure.multipleOf("value", value, 2)).isEqualTo(value);
		assertThat(Ensure.multipleOf("value", value, 4)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.multipleOf("value", value, 3))
				.withMessage("Invalid value: 4 (multiple of 3 expected)");
	}

	@Test
	void testMultipleOfLong() {
		final var value = 4L;
		assertThat(Ensure.multipleOf("value", value, 2L)).isEqualTo(value);
		assertThat(Ensure.multipleOf("value", value, 4L)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.multipleOf("value", value, 3L))
				.withMessage("Invalid value: 4 (multiple of 3 expected)");
	}

	@Test
	void testMultipleOfFloat() {
		final var value = 4.0f;
		assertThat(Ensure.multipleOf("value", value, 2.0f)).isEqualTo(value);
		assertThat(Ensure.multipleOf("value", value, 4.0f)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.multipleOf("value", value, 3.0f))
				.withMessage("Invalid value: 4.0 (multiple of 3.0 expected)");
	}

	@Test
	void testMultipleOfDouble() {
		final var value = 4.0d;
		assertThat(Ensure.multipleOf("value", value, 2.0d)).isEqualTo(value);
		assertThat(Ensure.multipleOf("value", value, 4.0d)).isEqualTo(value);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> Ensure.multipleOf("value", value, 3.0d))
				.withMessage("Invalid value: 4.0 (multiple of 3.0 expected)");
	}
}