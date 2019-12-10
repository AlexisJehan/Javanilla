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
package com.github.alexisjehan.javanilla.lang.array;

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.ToString;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>An utility class that provides {@code byte} array tools.</p>
 * @since 1.0.0
 */
public final class ByteArrays {

	/**
	 * <p>An empty {@code byte} array.</p>
	 * @since 1.0.0
	 */
	public static final byte[] EMPTY = {};

	/**
	 * <p>{@code char} array used for binary {@code String} conversion.</p>
	 * @since 1.2.0
	 */
	private static final char[] BINARY_CHARS = {'0', '1'};

	/**
	 * <p>{@code char} array used for hexadecimal {@code String} conversion.</p>
	 * @since 1.0.0
	 */
	private static final char[] HEXADECIMAL_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private ByteArrays() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code byte} array replacing {@code null} by an empty one.</p>
	 * @param array the {@code byte} array or {@code null}
	 * @return a non-{@code null} {@code byte} array
	 * @since 1.0.0
	 */
	public static byte[] nullToEmpty(final byte[] array) {
		return nullToDefault(array, EMPTY);
	}

	/**
	 * <p>Wrap a {@code byte} array replacing {@code null} by a default one.</p>
	 * @param array the {@code byte} array or {@code null}
	 * @param defaultArray the default {@code byte} array
	 * @return a non-{@code null} {@code byte} array
	 * @throws NullPointerException if the default {@code byte} array is {@code null}
	 * @since 1.1.0
	 */
	public static byte[] nullToDefault(final byte[] array, final byte[] defaultArray) {
		Ensure.notNull("defaultArray", defaultArray);
		return null != array ? array : defaultArray;
	}

	/**
	 * <p>Wrap a {@code byte} array replacing an empty one by {@code null}.</p>
	 * @param array the {@code byte} array or {@code null}
	 * @return a non-empty {@code byte} array or {@code null}
	 * @since 1.0.0
	 */
	public static byte[] emptyToNull(final byte[] array) {
		return emptyToDefault(array, null);
	}

	/**
	 * <p>Wrap a {@code byte} array replacing an empty one by a default {@code byte} array.</p>
	 * @param array the {@code byte} array or {@code null}
	 * @param defaultArray the default {@code byte} array or {@code null}
	 * @return a non-empty {@code byte} array or {@code null}
	 * @throws IllegalArgumentException if the default {@code byte} array is empty
	 * @since 1.1.0
	 */
	public static byte[] emptyToDefault(final byte[] array, final byte[] defaultArray) {
		if (null != defaultArray) {
			Ensure.notNullAndNotEmpty("defaultArray", defaultArray);
		}
		return null == array || !isEmpty(array) ? array : defaultArray;
	}

	/**
	 * <p>Tell if a {@code byte} array is empty.</p>
	 * @param array the {@code byte} array to test
	 * @return {@code true} if the {@code byte} array is empty
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final byte[] array) {
		Ensure.notNull("array", array);
		return 0 == array.length;
	}

	/**
	 * <p>Tell if the {@code byte} array contains any of given {@code byte} values at least one.</p>
	 * @param array the {@code byte} array to test
	 * @param values {@code byte} values to test
	 * @return {@code true} if any of given {@code byte} values is contained at least once by the {@code byte} array
	 * @throws NullPointerException if the {@code byte} array or the {@code byte} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAny(final byte[] array, final byte... values) {
		Ensure.notNull("array", array);
		Ensure.notNullAndNotEmpty("values", values);
		if (isEmpty(array)) {
			return false;
		}
		for (final var value : values) {
			for (final var element : array) {
				if (Equals.equals(value, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>Tell if the {@code byte} array contains all of given {@code byte} values at least one.</p>
	 * @param array the {@code byte} array to test
	 * @param values {@code byte} values to test
	 * @return {@code true} if all of given {@code byte} values are contained at least once by the {@code byte} array
	 * @throws NullPointerException if the {@code byte} array or the {@code byte} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsAll(final byte[] array, final byte... values) {
		Ensure.notNull("array", array);
		Ensure.notNullAndNotEmpty("values", values);
		if (isEmpty(array)) {
			return false;
		}
		for (final var value : values) {
			var contained = false;
			for (final var element : array) {
				if (Equals.equals(value, element)) {
					contained = true;
					break;
				}
			}
			if (!contained) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if the {@code byte} array contains each given {@code byte} value only once.</p>
	 * @param array the {@code byte} array to test
	 * @param values {@code byte} values to test
	 * @return {@code true} if each of given {@code byte} values are contained only once by the {@code byte} array
	 * @throws NullPointerException if the {@code byte} array or the {@code byte} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} values array is empty
	 * @since 1.1.0
	 */
	public static boolean containsOnce(final byte[] array, final byte... values) {
		Ensure.notNull("array", array);
		Ensure.notNullAndNotEmpty("values", values);
		if (isEmpty(array)) {
			return false;
		}
		for (final var value : values) {
			var contained = false;
			for (final var element : array) {
				if (Equals.equals(value, element)) {
					if (contained) {
						return false;
					}
					contained = true;
				}
			}
			if (!contained) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if the {@code byte} array contains only given {@code byte} values at least one.</p>
	 * @param array the {@code byte} array to test
	 * @param values {@code byte} values to test
	 * @return {@code true} if given {@code byte} values are only values contained by the {@code byte} array
	 * @throws NullPointerException if the {@code byte} array or the {@code byte} values array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} values array is empty
	 * @since 1.0.0
	 */
	public static boolean containsOnly(final byte[] array, final byte... values) {
		Ensure.notNull("array", array);
		Ensure.notNullAndNotEmpty("values", values);
		if (isEmpty(array)) {
			return false;
		}
		for (final var element : array) {
			var contained = false;
			for (final var value : values) {
				if (Equals.equals(value, element)) {
					contained = true;
					break;
				}
			}
			if (!contained) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Get the first index of the {@code byte} value in the {@code byte} array.</p>
	 * @param array the {@code byte} array to iterate
	 * @param value the {@code byte} value to search
	 * @return the first index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.0.0
	 */
	public static int indexOf(final byte[] array, final byte value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code byte} value in the {@code byte} array starting from the given index.</p>
	 * @param array the {@code byte} array to iterate
	 * @param value the {@code byte} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final byte[] array, final byte value, final int fromIndex) {
		Ensure.notNull("array", array);
		if (!isEmpty(array)) {
			Ensure.between("fromIndex", fromIndex, 0, array.length - 1);
			for (var i = fromIndex; i < array.length; ++i) {
				if (Equals.equals(value, array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * <p>Get the last index of the {@code byte} value in the {@code byte} array.</p>
	 * @param array the {@code byte} array to iterate
	 * @param value the {@code byte} value to search
	 * @return the last index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final byte[] array, final byte value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code byte} value in the {@code byte} array starting from the given index.</p>
	 * @param array the {@code byte} array to iterate
	 * @param value the {@code byte} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final byte[] array, final byte value, final int fromIndex) {
		Ensure.notNull("array", array);
		if (!isEmpty(array)) {
			Ensure.between("fromIndex", fromIndex, 0, array.length - 1);
			for (var i = array.length - 1; i > fromIndex; --i) {
				if (Equals.equals(value, array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * <p>Calculate the number of occurrences of the {@code byte} value in the {@code byte} array.</p>
	 * @param array the {@code byte} array to iterate
	 * @param value the {@code byte} value of the frequency to calculate
	 * @return the frequency of the {@code byte} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final byte[] array, final byte value) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return 0;
		}
		var frequency = 0;
		for (final var element : array) {
			if (Equals.equals(value, element)) {
				++frequency;
			}
		}
		return frequency;
	}

	/**
	 * <p>Shuffle values in the given {@code byte} array using the Fisher-Yates algorithm.</p>
	 * @param array the {@code byte} array to shuffle
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.2.0
	 */
	public static void shuffle(final byte[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			final var random = ThreadLocalRandom.current();
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
			}
		}
	}

	/**
	 * <p>Reverse values in the given {@code byte} array.</p>
	 * @param array the {@code byte} array to reverse
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static void reverse(final byte[] array) {
		Ensure.notNull("array", array);
		if (1 < array.length) {
			for (var i = 0; i < array.length / 2; ++i) {
				swap(array, i, array.length - i - 1);
			}
		}
	}

	/**
	 * <p>Reorder values in the given {@code byte} array using provided indexes.</p>
	 * @param array the {@code byte} array to reorder
	 * @param indexes indexes to use
	 * @throws NullPointerException if the {@code byte} array or the indexes array is {@code null}
	 * @throws IllegalArgumentException if {@code byte} array is empty, if the {@code byte} array length is not equal to
	 *         the indexes array length, if indexes are not distinct or if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final byte[] array, final int... indexes) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.notNull("indexes", indexes);
		Ensure.equalTo("indexes length", indexes.length, array.length);
		if (indexes.length != Arrays.stream(indexes).distinct().count()) {
			throw new IllegalArgumentException("Invalid indexes: " + ToString.toString(indexes) + " (distinct expected)");
		}
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				var j = indexes[i];
				Ensure.between("index", j, 0, array.length - 1);
				while (j < i) {
					j = indexes[j];
				}
				swap(array, i, j);
			}
		}
	}

	/**
	 * <p>Swap two values in the given {@code byte} array using their indexes.</p>
	 * @param array the {@code byte} array to swap
	 * @param index1 the index of the first value
	 * @param index2 the index of the second value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final byte[] array, final int index1, final int index2) {
		Ensure.notNull("array", array);
		Ensure.between("index1", index1, 0, array.length - 1);
		Ensure.between("index2", index2, 0, array.length - 1);
		if (index1 != index2) {
			final var value = array[index1];
			array[index1] = array[index2];
			array[index2] = value;
		}
	}

	/**
	 * <p>Add a {@code byte} value at the end of the given {@code byte} array.</p>
	 * @param array the {@code byte} array to add to
	 * @param value the {@code byte} value to add
	 * @return a {@code byte} array with the added {@code byte} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.4.0
	 */
	public static byte[] add(final byte[] array, final byte value) {
		Ensure.notNull("array", array);
		return add(array, array.length, value);
	}

	/**
	 * <p>Add a {@code byte} value at the provided index of the given {@code byte} array.</p>
	 * @param array the {@code byte} array to add to
	 * @param index the index of the {@code byte} value
	 * @param value the {@code byte} value to add
	 * @return a {@code byte} array with the added {@code byte} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @since 1.4.0
	 */
	public static byte[] add(final byte[] array, final int index, final byte value) {
		Ensure.notNull("array", array);
		Ensure.between("index", index, 0, array.length);
		final var result = new byte[array.length + 1];
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		result[index] = value;
		if (index < array.length) {
			System.arraycopy(array, index, result, index + 1, array.length - index);
		}
		return result;
	}

	/**
	 * <p>Remove a {@code byte} value at the provided index of the given {@code byte} array.</p>
	 * @param array the {@code byte} array to remove from
	 * @param index the index of the {@code byte} value
	 * @return a {@code byte} array with the removed {@code byte} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array is empty or if the index is not valid
	 * @since 1.4.0
	 */
	public static byte[] remove(final byte[] array, final int index) {
		Ensure.notNullAndNotEmpty("array", array);
		Ensure.between("index", index, 0, array.length - 1);
		final var result = new byte[array.length - 1];
		if (0 < index) {
			System.arraycopy(array, 0, result, 0, index);
		}
		if (index < array.length - 1) {
			System.arraycopy(array, index + 1, result, index, array.length - index - 1);
		}
		return result;
	}

	/**
	 * <p>Concatenate multiple {@code byte} arrays.</p>
	 * @param arrays the {@code byte} array array to concatenate
	 * @return the concatenated {@code byte} array
	 * @throws NullPointerException if the {@code byte} array array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] concat(final byte[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(List.of(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code byte} arrays.</p>
	 * @param arrays the {@code byte} array {@code List} to concatenate
	 * @return the concatenated {@code byte} array
	 * @throws NullPointerException if the {@code byte} array {@code List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] concat(final List<byte[]> arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new byte[arrays.stream().mapToInt(array -> array.length).sum()];
		var offset = 0;
		for (final var array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * <p>Join multiple {@code byte} arrays using a {@code byte} array separator.</p>
	 * @param separator the {@code byte} array separator
	 * @param arrays the {@code byte} array array to join
	 * @return the joined {@code byte} array
	 * @throws NullPointerException if the {@code byte} array separator, the {@code byte} array array or any of them is
	 * {@code null}
	 * @since 1.0.0
	 */
	public static byte[] join(final byte[] separator, final byte[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * <p>Join multiple {@code byte} arrays using a {@code byte} array separator.</p>
	 * @param separator the {@code byte} array separator
	 * @param arrays the {@code byte} array {@code List} to join
	 * @return the joined {@code byte} array
	 * @throws NullPointerException if the {@code byte} array separator, the {@code byte} array {@code List} or any of
	 *         them is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] join(final byte[] separator, final List<byte[]> arrays) {
		Ensure.notNull("separator", separator);
		Ensure.notNullAndNotNullElements("arrays", arrays);
		if (isEmpty(separator)) {
			return concat(arrays);
		}
		final var size = arrays.size();
		if (0 == size) {
			return EMPTY;
		}
		if (1 == size) {
			return arrays.get(0);
		}
		final var result = new byte[arrays.stream().mapToInt(array -> array.length).sum() + (arrays.size() - 1) * separator.length];
		final var iterator = arrays.iterator();
		var array = iterator.next();
		System.arraycopy(array, 0, result, 0, array.length);
		var offset = array.length;
		while (iterator.hasNext()) {
			System.arraycopy(separator, 0, result, offset, separator.length);
			offset += separator.length;
			array = iterator.next();
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * <p>Create a {@code byte} array from a single {@code byte} value.</p>
	 * @param value the {@code byte} value to convert
	 * @return the created {@code byte} array
	 * @since 1.1.0
	 */
	public static byte[] singleton(final byte value) {
		return of(value);
	}

	/**
	 * <p>Create a {@code byte} array from multiple {@code byte} values.</p>
	 * @param values {@code byte} values to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@code byte} values array is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] of(final byte... values) {
		Ensure.notNull("values", values);
		if (isEmpty(values)) {
			return EMPTY;
		}
		return values;
	}

	/**
	 * <p>Create a {@code byte} array from a boxed {@code Byte} array.</p>
	 * @param boxedArray the boxed {@code Byte} array to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the boxed {@code Byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static byte[] of(final Byte[] boxedArray) {
		Ensure.notNull("boxedArray", boxedArray);
		if (ObjectArrays.isEmpty(boxedArray)) {
			return EMPTY;
		}
		final var array = new byte[boxedArray.length];
		for (var i = 0; i < array.length; ++i) {
			array[i] = boxedArray[i];
		}
		return array;
	}

	/**
	 * <p>Convert a {@code byte} array to a boxed {@code Byte} array.</p>
	 * @param array the {@code byte} array to convert
	 * @return the created boxed {@code Byte} array
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static Byte[] toBoxed(final byte[] array) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return ObjectArrays.empty(Byte.class);
		}
		final var boxedArray = new Byte[array.length];
		for (var i = 0; i < boxedArray.length; ++i) {
			boxedArray[i] = array[i];
		}
		return boxedArray;
	}

	/**
	 * <p>Create a {@code byte} array from a {@code boolean}.</p>
	 * @param b the {@code boolean} to convert
	 * @return the created {@code byte} array
	 * @since 1.0.0
	 */
	public static byte[] ofBoolean(final boolean b) {
		return singleton(b ? (byte) 1 : (byte) 0);
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code boolean} value.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted {@code boolean} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static boolean toBoolean(final byte[] bytes) {
		Ensure.notNull("bytes", bytes);
		Ensure.equalTo("bytes length", bytes.length, 1);
		return (byte) 1 == bytes[0];
	}

	/**
	 * <p>Create a {@code byte} array from a {@code short} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param s the {@code short} to convert
	 * @return the created {@code byte} array
	 * @since 1.0.0
	 */
	public static byte[] ofShort(final short s) {
		return ofShort(s, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code short} using a custom {@code ByteOrder}.</p>
	 * @param s the {@code short} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] ofShort(final short s, final ByteOrder order) {
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of(
					(byte) (s >> 8),
					(byte) s
			);
		}
		return of(
				(byte) s,
				(byte) (s >> 8)
		);
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code short} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted {@code short} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static short toShort(final byte[] bytes) {
		return toShort(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code short} value using the given {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code short} value
	 * @throws NullPointerException if the {@code byte} array or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static short toShort(final byte[] bytes, final ByteOrder order) {
		Ensure.notNull("bytes", bytes);
		Ensure.equalTo("bytes length", bytes.length, Short.BYTES);
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (short) ((bytes[0] & 0xff) << 8 | bytes[1] & 0xff);
		}
		return (short) (bytes[0] & 0xff | (bytes[1] & 0xff) << 8);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code char} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param c the {@code char} to convert
	 * @return the created {@code byte} array
	 * @since 1.0.0
	 */
	public static byte[] ofChar(final char c) {
		return ofChar(c, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code char} using a custom {@code ByteOrder}.</p>
	 * @param c the {@code char} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] ofChar(final char c, final ByteOrder order) {
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of((byte) (c >> 8), (byte) c);
		}
		return of((byte) c, (byte) (c >> 8));
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code char} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted {@code char} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static char toChar(final byte[] bytes) {
		return toChar(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code char} value using the given {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code char} value
	 * @throws NullPointerException if the {@code byte} array or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static char toChar(final byte[] bytes, final ByteOrder order) {
		Ensure.notNull("bytes", bytes);
		Ensure.equalTo("bytes length", bytes.length, Character.BYTES);
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (char) ((bytes[0] & 0xff) << 8 | bytes[1] & 0xff);
		}
		return (char) (bytes[0] & 0xff | (bytes[1] & 0xff) << 8);
	}

	/**
	 * <p>Create a {@code byte} array from an {@code int} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param i the {@code int} to convert
	 * @return the created {@code byte} array
	 * @since 1.0.0
	 */
	public static byte[] ofInt(final int i) {
		return ofInt(i, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from an {@code int} using a custom {@code ByteOrder}.</p>
	 * @param i the {@code int} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] ofInt(final int i, final ByteOrder order) {
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of(
					(byte) (i >> 24),
					(byte) (i >> 16),
					(byte) (i >> 8),
					(byte) i
			);
		}
		return of(
				(byte) i,
				(byte) (i >> 8),
				(byte) (i >> 16),
				(byte) (i >> 24)
		);
	}

	/**
	 * <p>Convert a {@code byte} array back to an {@code int} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted {@code int} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static int toInt(final byte[] bytes) {
		return toInt(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte} array back to an {@code int} value using the given {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code int} value
	 * @throws NullPointerException if the {@code byte} array or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static int toInt(final byte[] bytes, final ByteOrder order) {
		Ensure.notNull("bytes", bytes);
		Ensure.equalTo("bytes length", bytes.length, Integer.BYTES);
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (bytes[0] & 0xff) << 24
					| (bytes[1] & 0xff) << 16
					| (bytes[2] & 0xff) << 8
					| bytes[3] & 0xff;
		}
		return bytes[0] & 0xff
				| (bytes[1] & 0xff) << 8
				| (bytes[2] & 0xff) << 16
				| (bytes[3] & 0xff) << 24;
	}

	/**
	 * <p>Create a {@code byte} array from a {@code long} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param l the {@code long} to convert
	 * @return the created {@code byte} array
	 * @since 1.0.0
	 */
	public static byte[] ofLong(final long l) {
		return ofLong(l, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code long} using a custom {@code ByteOrder}.</p>
	 * @param l the {@code long} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] ofLong(final long l, final ByteOrder order) {
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of(
					(byte) (l >> 56),
					(byte) (l >> 48),
					(byte) (l >> 40),
					(byte) (l >> 32),
					(byte) (l >> 24),
					(byte) (l >> 16),
					(byte) (l >> 8),
					(byte) l
			);
		}
		return of(
				(byte) l,
				(byte) (l >> 8),
				(byte) (l >> 16),
				(byte) (l >> 24),
				(byte) (l >> 32),
				(byte) (l >> 40),
				(byte) (l >> 48),
				(byte) (l >> 56)
		);
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code long} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted {@code long} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static long toLong(final byte[] bytes) {
		return toLong(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code long} value using the given {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code long} value
	 * @throws NullPointerException if the {@code byte} array or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static long toLong(final byte[] bytes, final ByteOrder order) {
		Ensure.notNull("bytes", bytes);
		Ensure.equalTo("bytes length", bytes.length, Long.BYTES);
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (long) (bytes[0] & 0xff) << 56
					| ((long) bytes[1] & 0xff) << 48
					| ((long) bytes[2] & 0xff) << 40
					| ((long) bytes[3] & 0xff) << 32
					| ((long) bytes[4] & 0xff) << 24
					| ((long) bytes[5] & 0xff) << 16
					| ((long) bytes[6] & 0xff) << 8
					| (long) bytes[7] & 0xff;
		}
		return (long) bytes[0] & 0xff
				| ((long) bytes[1] & 0xff) << 8
				| ((long) bytes[2] & 0xff) << 16
				| ((long) bytes[3] & 0xff) << 24
				| ((long) bytes[4] & 0xff) << 32
				| ((long) bytes[5] & 0xff) << 40
				| ((long) bytes[6] & 0xff) << 48
				| ((long) bytes[7] & 0xff) << 56;
	}

	/**
	 * <p>Create a {@code byte} array from a {@code float} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param f the {@code float} to convert
	 * @return the created {@code byte} array
	 * @since 1.0.0
	 */
	public static byte[] ofFloat(final float f) {
		return ofFloat(f, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code float} using a custom {@code ByteOrder}.</p>
	 * @param f the {@code float} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] ofFloat(final float f, final ByteOrder order) {
		return ofInt(Float.floatToIntBits(f), order);
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code float} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted {@code float} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static float toFloat(final byte[] bytes) {
		return toFloat(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code float} value using the given {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code float} value
	 * @throws NullPointerException if the {@code byte} array or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static float toFloat(final byte[] bytes, final ByteOrder order) {
		return Float.intBitsToFloat(toInt(bytes, order));
	}

	/**
	 * <p>Create a {@code byte} array from a {@code double} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param d the {@code double} to convert
	 * @return the created {@code byte} array
	 * @since 1.0.0
	 */
	public static byte[] ofDouble(final double d) {
		return ofDouble(d, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code double} using a custom {@code ByteOrder}.</p>
	 * @param d the {@code double} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] ofDouble(final double d, final ByteOrder order) {
		return ofLong(Double.doubleToLongBits(d), order);
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code double} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted {@code double} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static double toDouble(final byte[] bytes) {
		return toDouble(bytes, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code double} value using the given {@code ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code double} value
	 * @throws NullPointerException if the {@code byte} array or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static double toDouble(final byte[] bytes, final ByteOrder order) {
		return Double.longBitsToDouble(toLong(bytes, order));
	}

	/**
	 * <p>Create a {@code byte} array from a binary {@code CharSequence}.</p>
	 * @param binaryCharSequence the binary {@code CharSequence} to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the binary {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the binary {@code CharSequence} length is not a multiple of {@code 8} or if
	 *         any {@code char} is not valid
	 * @since 1.2.0
	 */
	public static byte[] ofBinaryString(final CharSequence binaryCharSequence) {
		Ensure.notNull("binaryCharSequence", binaryCharSequence);
		final var length = binaryCharSequence.length();
		Ensure.multipleOf("binaryCharSequence length", length, 8);
		if (0 == length) {
			return EMPTY;
		}
		final var bytes = new byte[length / 8];
		for (var i = 0; i < length; ++i) {
			final var c = binaryCharSequence.charAt(i);
			final var p = CharArrays.indexOf(BINARY_CHARS, c);
			if (-1 == p) {
				throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (binary expected)");
			}
			bytes[i / 8] <<= 1;
			bytes[i / 8] |= p;
		}
		return bytes;
	}

	/**
	 * <p>Convert a {@code byte} array to a binary {@code String} value.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted binary {@code String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static String toBinaryString(final byte[] bytes) {
		Ensure.notNull("bytes", bytes);
		if (isEmpty(bytes)) {
			return Strings.EMPTY;
		}
		final var binaryChars = new char[8 * bytes.length];
		for (var i = 0; i < bytes.length; ++i) {
			final var v = Byte.toUnsignedInt(bytes[i]);
			binaryChars[8 * i] = BINARY_CHARS[v >>> 7 & 0b00000001];
			binaryChars[8 * i + 1] = BINARY_CHARS[v >>> 6 & 0b00000001];
			binaryChars[8 * i + 2] = BINARY_CHARS[v >>> 5 & 0b00000001];
			binaryChars[8 * i + 3] = BINARY_CHARS[v >>> 4 & 0b00000001];
			binaryChars[8 * i + 4] = BINARY_CHARS[v >>> 3 & 0b00000001];
			binaryChars[8 * i + 5] = BINARY_CHARS[v >>> 2 & 0b00000001];
			binaryChars[8 * i + 6] = BINARY_CHARS[v >>> 1 & 0b00000001];
			binaryChars[8 * i + 7] = BINARY_CHARS[v & 0x01];
		}
		return new String(binaryChars);
	}

	/**
	 * <p>Create a {@code byte} array from a hexadecimal {@code CharSequence}.</p>
	 * <p><b>Note</b>: The hexadecimal {@code CharSequence} value case does not matter.</p>
	 * @param hexadecimalCharSequence the hexadecimal {@code CharSequence} to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the hexadecimal {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the hexadecimal {@code CharSequence} length is not a multiple of {@code 2} or
	 *         if any {@code char} is not valid
	 * @since 1.0.0
	 */
	public static byte[] ofHexadecimalString(final CharSequence hexadecimalCharSequence) {
		Ensure.notNull("hexadecimalCharSequence", hexadecimalCharSequence);
		final var length = hexadecimalCharSequence.length();
		Ensure.multipleOf("hexadecimalCharSequence length", length, 2);
		if (0 == length) {
			return EMPTY;
		}
		final var bytes = new byte[length / 2];
		for (var i = 0; i < length; ++i) {
			final var c = hexadecimalCharSequence.charAt(i);
			final var p = CharArrays.indexOf(HEXADECIMAL_CHARS, Character.toLowerCase(c));
			if (-1 == p) {
				throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (hexadecimal expected)");
			}
			bytes[i / 2] <<= 4;
			bytes[i / 2] |= p;
		}
		return bytes;
	}

	/**
	 * <p>Convert a {@code byte} array to a hexadecimal {@code String} value.</p>
	 * <p><b>Note</b>: The hexadecimal {@code String} value will be in lowercase.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted hexadecimal {@code String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.0.0
	 */
	public static String toHexadecimalString(final byte[] bytes) {
		Ensure.notNull("bytes", bytes);
		if (isEmpty(bytes)) {
			return Strings.EMPTY;
		}
		final var hexChars = new char[2 * bytes.length];
		for (var i = 0; i < bytes.length; ++i) {
			final var v = Byte.toUnsignedInt(bytes[i]);
			hexChars[i * 2] = HEXADECIMAL_CHARS[v >>> 4 & 0b00001111];
			hexChars[i * 2 + 1] = HEXADECIMAL_CHARS[v & 0b00001111];
		}
		return new String(hexChars);
	}
}