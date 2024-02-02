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
package com.github.alexisjehan.javanilla.lang.array;

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.ToString;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
	 * <p>{@code char} array used for binary, octal, decimal and hexadecimal {@link String} conversion.</p>
	 * @since 1.7.0
	 */
	private static final char[] BASE_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * <p>Default value for using spacing or not.</p>
	 * @since 1.7.0
	 */
	private static final boolean DEFAULT_WITH_SPACING = false;

	/**
	 * <p>Constructor.</p>
	 * @since 1.0.0
	 */
	private ByteArrays() {}

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
	 * <p>Add a {@code byte} value at the end of the given {@code byte} array.</p>
	 * @param array the {@code byte} array to add to
	 * @param value the {@code byte} value to add
	 * @return a {@code byte} array with the added {@code byte} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.4.0
	 */
	public static byte[] add(final byte[] array, final byte value) {
		Ensure.notNull("array", array);
		return add(array, value, array.length);
	}

	/**
	 * <p>Add a {@code byte} value at the provided index of the given {@code byte} array.</p>
	 * @param array the {@code byte} array to add to
	 * @param index the index of the {@code byte} value
	 * @param value the {@code byte} value to add
	 * @return a {@code byte} array with the added {@code byte} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @deprecated since 1.6.0, use {@link #add(byte[], byte, int)} instead
	 * @since 1.4.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] add(final byte[] array, final int index, final byte value) {
		return add(array, value, index);
	}

	/**
	 * <p>Add a {@code byte} value at the provided index of the given {@code byte} array.</p>
	 * @param array the {@code byte} array to add to
	 * @param value the {@code byte} value to add
	 * @param index the index of the {@code byte} value
	 * @return a {@code byte} array with the added {@code byte} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the index is not valid
	 * @since 1.6.0
	 */
	public static byte[] add(final byte[] array, final byte value, final int index) {
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
	 * @param arrays {@code byte} arrays to concatenate
	 * @return the concatenated {@code byte} array
	 * @throws NullPointerException if {@code byte} arrays or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] concat(final byte[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return concat(List.of(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code byte} arrays.</p>
	 * @param arrays the {@code byte} array {@link List} to concatenate
	 * @return the concatenated {@code byte} array
	 * @throws NullPointerException if the {@code byte} array {@link List} or any of them is {@code null}
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
	 * @param arrays {@code byte} arrays to join
	 * @return the joined {@code byte} array
	 * @throws NullPointerException if the {@code byte} array separator, {@code byte} arrays or any of them is
	 *         {@code null}
	 * @since 1.0.0
	 */
	public static byte[] join(final byte[] separator, final byte[]... arrays) {
		Ensure.notNullAndNotNullElements("arrays", arrays);
		return join(separator, List.of(arrays));
	}

	/**
	 * <p>Join multiple {@code byte} arrays using a {@code byte} array separator.</p>
	 * @param separator the {@code byte} array separator
	 * @param arrays the {@code byte} array {@link List} to join
	 * @return the joined {@code byte} array
	 * @throws NullPointerException if the {@code byte} array separator, the {@code byte} array {@link List} or any of
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
	 * <p>Shuffle values in the given {@code byte} array following the Fisher-Yates algorithm.</p>
	 * @param array the {@code byte} array to shuffle
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @deprecated since 1.6.0, for security purposes, use {@link #shuffle(byte[], Random)} with
	 *             {@link java.security.SecureRandom} instead
	 * @since 1.2.0
	 */
	@Deprecated(since = "1.6.0")
	public static void shuffle(final byte[] array) {
		shuffle(array, ThreadLocalRandom.current());
	}

	/**
	 * <p>Shuffle values in the given {@code byte} array using the provided {@code Random} object following the
	 * Fisher-Yates algorithm.</p>
	 * @param array the {@code byte} array to shuffle
	 * @param random the {@code Random} object to use
	 * @throws NullPointerException if the {@code byte} array or the {@code Random} object is {@code null}
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @since 1.6.0
	 */
	public static void shuffle(final byte[] array, final Random random) {
		Ensure.notNull("array", array);
		Ensure.notNull("random", random);
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				swap(array, i, random.nextInt(i + 1));
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
	 * <p>Create a {@code byte} array from a single {@code byte} value.</p>
	 * @param value the {@code byte} value to convert
	 * @return the created {@code byte} array
	 * @since 1.1.0
	 */
	public static byte[] singleton(final byte value) {
		return new byte[] {value};
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
	 * <p>Create a {@code byte} array from a boxed {@link Byte} array.</p>
	 * @param boxedArray the boxed {@link Byte} array to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the boxed {@link Byte} array is {@code null}
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
	 * <p>Create a {@code byte} array from a {@code boolean}.</p>
	 * @param b the {@code boolean} to convert
	 * @return the created {@code byte} array
	 * @since 1.6.0
	 */
	public static byte[] of(final boolean b) {
		return singleton(b ? (byte) 1 : (byte) 0);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code short} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param s the {@code short} to convert
	 * @return the created {@code byte} array
	 * @since 1.6.0
	 */
	public static byte[] of(final short s) {
		return of(s, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code short} using a custom {@link ByteOrder}.</p>
	 * @param s the {@code short} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @since 1.6.0
	 */
	public static byte[] of(final short s, final ByteOrder order) {
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of(
					(byte) (s >> 8),
					(byte) s
			);
		} else {
			return of(
					(byte) s,
					(byte) (s >> 8)
			);
		}
	}

	/**
	 * <p>Create a {@code byte} array from a {@code char} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param c the {@code char} to convert
	 * @return the created {@code byte} array
	 * @since 1.6.0
	 */
	public static byte[] of(final char c) {
		return of(c, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code char} using a custom {@link ByteOrder}.</p>
	 * @param c the {@code char} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @since 1.6.0
	 */
	public static byte[] of(final char c, final ByteOrder order) {
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of(
					(byte) (c >> 8),
					(byte) c
			);
		} else {
			return of(
					(byte) c,
					(byte) (c >> 8)
			);
		}
	}

	/**
	 * <p>Create a {@code byte} array from an {@code int} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param i the {@code int} to convert
	 * @return the created {@code byte} array
	 * @since 1.6.0
	 */
	public static byte[] of(final int i) {
		return of(i, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from an {@code int} using a custom {@link ByteOrder}.</p>
	 * @param i the {@code int} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @since 1.6.0
	 */
	public static byte[] of(final int i, final ByteOrder order) {
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of(
					(byte) (i >> 24),
					(byte) (i >> 16),
					(byte) (i >> 8),
					(byte) i
			);
		} else {
			return of(
					(byte) i,
					(byte) (i >> 8),
					(byte) (i >> 16),
					(byte) (i >> 24)
			);
		}
	}

	/**
	 * <p>Create a {@code byte} array from a {@code long} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param l the {@code long} to convert
	 * @return the created {@code byte} array
	 * @since 1.6.0
	 */
	public static byte[] of(final long l) {
		return of(l, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code long} using a custom {@link ByteOrder}.</p>
	 * @param l the {@code long} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @since 1.6.0
	 */
	public static byte[] of(final long l, final ByteOrder order) {
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
		} else {
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
	}

	/**
	 * <p>Create a {@code byte} array from a {@code float} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param f the {@code float} to convert
	 * @return the created {@code byte} array
	 * @since 1.6.0
	 */
	public static byte[] of(final float f) {
		return of(f, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code float} using a custom {@link ByteOrder}.</p>
	 * @param f the {@code float} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @since 1.6.0
	 */
	public static byte[] of(final float f, final ByteOrder order) {
		return of(Float.floatToIntBits(f), order);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code double} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param d the {@code double} to convert
	 * @return the created {@code byte} array
	 * @since 1.6.0
	 */
	public static byte[] of(final double d) {
		return of(d, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte} array from a {@code double} using a custom {@link ByteOrder}.</p>
	 * @param d the {@code double} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @since 1.6.0
	 */
	public static byte[] of(final double d, final ByteOrder order) {
		return of(Double.doubleToLongBits(d), order);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code boolean}.</p>
	 * @param b the {@code boolean} to convert
	 * @return the created {@code byte} array
	 * @deprecated since 1.6.0, use {@link #of(boolean)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofBoolean(final boolean b) {
		return of(b);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code short} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param s the {@code short} to convert
	 * @return the created {@code byte} array
	 * @deprecated since 1.6.0, use {@link #of(short)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofShort(final short s) {
		return of(s);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code short} using a custom {@link ByteOrder}.</p>
	 * @param s the {@code short} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @deprecated since 1.6.0, use {@link #of(short, ByteOrder)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofShort(final short s, final ByteOrder order) {
		return of(s, order);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code char} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param c the {@code char} to convert
	 * @return the created {@code byte} array
	 * @deprecated since 1.6.0, use {@link #of(char)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofChar(final char c) {
		return of(c);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code char} using a custom {@link ByteOrder}.</p>
	 * @param c the {@code char} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @deprecated since 1.6.0, use {@link #of(char, ByteOrder)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofChar(final char c, final ByteOrder order) {
		return of(c, order);
	}

	/**
	 * <p>Create a {@code byte} array from an {@code int} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param i the {@code int} to convert
	 * @return the created {@code byte} array
	 * @deprecated since 1.6.0, use {@link #of(int)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofInt(final int i) {
		return of(i);
	}

	/**
	 * <p>Create a {@code byte} array from an {@code int} using a custom {@link ByteOrder}.</p>
	 * @param i the {@code int} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @deprecated since 1.6.0, use {@link #of(int, ByteOrder)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofInt(final int i, final ByteOrder order) {
		return of(i, order);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code long} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param l the {@code long} to convert
	 * @return the created {@code byte} array
	 * @deprecated since 1.6.0, use {@link #of(long)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofLong(final long l) {
		return of(l);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code long} using a custom {@link ByteOrder}.</p>
	 * @param l the {@code long} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @deprecated since 1.6.0, use {@link #of(long, ByteOrder)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofLong(final long l, final ByteOrder order) {
		return of(l, order);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code float} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param f the {@code float} to convert
	 * @return the created {@code byte} array
	 * @deprecated since 1.6.0, use {@link #of(float)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofFloat(final float f) {
		return of(f);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code float} using a custom {@link ByteOrder}.</p>
	 * @param f the {@code float} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @deprecated since 1.6.0, use {@link #of(float, ByteOrder)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofFloat(final float f, final ByteOrder order) {
		return of(f, order);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code double} using {@link ByteOrder#nativeOrder()}.</p>
	 * @param d the {@code double} to convert
	 * @return the created {@code byte} array
	 * @deprecated since 1.6.0, use {@link #of(double)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofDouble(final double d) {
		return of(d);
	}

	/**
	 * <p>Create a {@code byte} array from a {@code double} using a custom {@link ByteOrder}.</p>
	 * @param d the {@code double} to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@link ByteOrder} is {@code null}
	 * @deprecated since 1.6.0, use {@link #of(double, ByteOrder)} instead
	 * @since 1.0.0
	 */
	@Deprecated(since = "1.6.0")
	public static byte[] ofDouble(final double d, final ByteOrder order) {
		return of(d, order);
	}

	/**
	 * <p>Create a {@code byte} array from a binary {@link CharSequence}.</p>
	 * @param binaryCharSequence the binary {@link CharSequence} to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the binary {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the binary {@link CharSequence} length is not a multiple of {@code 8} or if
	 *         any {@code char} is not valid
	 * @since 1.2.0
	 */
	public static byte[] ofBinaryString(final CharSequence binaryCharSequence) {
		return ofBinaryString(binaryCharSequence, DEFAULT_WITH_SPACING);
	}

	/**
	 * <p>Create a {@code byte} array from a binary {@link CharSequence} with spacing or not.</p>
	 * @param binaryCharSequence the binary {@link CharSequence} to convert
	 * @param withSpacing {@code true} if the binary {@link CharSequence} value has spacing
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the binary {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the binary {@link CharSequence} length is not a multiple of {@code 8} or if
	 *         any {@code char} is not valid
	 * @since 1.7.0
	 */
	public static byte[] ofBinaryString(final CharSequence binaryCharSequence, final boolean withSpacing) {
		Ensure.notNull("binaryCharSequence", binaryCharSequence);
		final var length = binaryCharSequence.length();
		if (0 == length) {
			return EMPTY;
		}
		final var spacing = withSpacing ? 1 : 0;
		Ensure.multipleOf("binaryCharSequence length", length + spacing, 8 + spacing);
		final var bytes = new byte[(length + spacing) / (8 + spacing)];
		for (var i = 0; i < length; ++i) {
			final var c = binaryCharSequence.charAt(i);
			if (withSpacing && 8 == i % 9) {
				if (' ' != c) {
					throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (space expected)");
				}
				continue;
			}
			final var b = CharArrays.indexOf(BASE_CHARS, c);
			if (-1 == b) {
				throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (binary expected)");
			}
			bytes[i / (8 + spacing)] = (byte) ((bytes[i / (8 + spacing)] << 1) | b);
		}
		return bytes;
	}

	/**
	 * <p>Create a {@code byte} array from an octal {@link CharSequence}.</p>
	 * @param octalCharSequence the octal {@link CharSequence} to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the octal {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the octal {@link CharSequence} length is not a multiple of {@code 3} or if
	 *         any {@code char} is not valid
	 * @since 1.7.0
	 */
	public static byte[] ofOctalString(final CharSequence octalCharSequence) {
		return ofOctalString(octalCharSequence, DEFAULT_WITH_SPACING);
	}

	/**
	 * <p>Create a {@code byte} array from an octal {@link CharSequence} with spacing or not.</p>
	 * @param octalCharSequence the octal {@link CharSequence} to convert
	 * @param withSpacing {@code true} if the octal {@link CharSequence} value has spacing
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the octal {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the octal {@link CharSequence} length is not a multiple of {@code 3} or if
	 *         any {@code char} is not valid
	 * @since 1.7.0
	 */
	public static byte[] ofOctalString(final CharSequence octalCharSequence, final boolean withSpacing) {
		Ensure.notNull("octalCharSequence", octalCharSequence);
		final var length = octalCharSequence.length();
		if (0 == length) {
			return EMPTY;
		}
		final var spacing = withSpacing ? 1 : 0;
		Ensure.multipleOf("octalCharSequence length", length + spacing, 3 + spacing);
		final var bytes = new byte[(length + spacing) / (3 + spacing)];
		for (var i = 0; i < length; ++i) {
			final var c = octalCharSequence.charAt(i);
			if (withSpacing && 3 == i % 4) {
				if (' ' != c) {
					throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (space expected)");
				}
				continue;
			}
			final var b = CharArrays.indexOf(BASE_CHARS, c);
			if (-1 == b) {
				throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (octal expected)");
			}
			bytes[i / (3 + spacing)] = (byte) ((bytes[i / (3 + spacing)] << 3) | b);
		}
		return bytes;
	}

	/**
	 * <p>Create a {@code byte} array from a decimal {@link CharSequence}.</p>
	 * @param decimalCharSequence the decimal {@link CharSequence} to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the decimal {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the decimal {@link CharSequence} length is not a multiple of {@code 3} or if
	 *         any {@code char} is not valid
	 * @since 1.7.0
	 */
	public static byte[] ofDecimalString(final CharSequence decimalCharSequence) {
		return ofDecimalString(decimalCharSequence, DEFAULT_WITH_SPACING);
	}

	/**
	 * <p>Create a {@code byte} array from a decimal {@link CharSequence} with spacing or not.</p>
	 * @param decimalCharSequence the decimal {@link CharSequence} to convert
	 * @param withSpacing {@code true} if the decimal {@link CharSequence} value has spacing
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the decimal {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the decimal {@link CharSequence} length is not a multiple of {@code 3} or if
	 *         any {@code char} is not valid
	 * @since 1.7.0
	 */
	public static byte[] ofDecimalString(final CharSequence decimalCharSequence, final boolean withSpacing) {
		Ensure.notNull("decimalCharSequence", decimalCharSequence);
		final var length = decimalCharSequence.length();
		if (0 == length) {
			return EMPTY;
		}
		final var spacing = withSpacing ? 1 : 0;
		Ensure.multipleOf("decimalCharSequence length", length + spacing, 3 + spacing);
		final var bytes = new byte[(length + spacing) / (3 + spacing)];
		for (var i = 0; i < length; ++i) {
			final var c = decimalCharSequence.charAt(i);
			if (withSpacing && 3 == i % 4) {
				if (' ' != c) {
					throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (space expected)");
				}
				continue;
			}
			final var b = CharArrays.indexOf(BASE_CHARS, c);
			if (-1 == b) {
				throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (decimal expected)");
			}
			bytes[i / (3 + spacing)] = (byte) (bytes[i / (3 + spacing)] * 10 + b);
		}
		return bytes;
	}

	/**
	 * <p>Create a {@code byte} array from a hexadecimal {@link CharSequence}.</p>
	 * <p><b>Note</b>: The case does not matter.</p>
	 * @param hexadecimalCharSequence the hexadecimal {@link CharSequence} to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the hexadecimal {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the hexadecimal {@link CharSequence} length is not a multiple of {@code 2} or
	 *         if any {@code char} is not valid
	 * @since 1.0.0
	 */
	public static byte[] ofHexadecimalString(final CharSequence hexadecimalCharSequence) {
		return ofHexadecimalString(hexadecimalCharSequence, DEFAULT_WITH_SPACING);
	}

	/**
	 * <p>Create a {@code byte} array from a hexadecimal {@link CharSequence} with spacing or not.</p>
	 * <p><b>Note</b>: The case does not matter.</p>
	 * @param hexadecimalCharSequence the hexadecimal {@link CharSequence} to convert
	 * @param withSpacing {@code true} if the hexadecimal {@link CharSequence} value has spacing
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the hexadecimal {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the hexadecimal {@link CharSequence} length is not a multiple of {@code 2} or
	 *         if any {@code char} is not valid
	 * @since 1.7.0
	 */
	public static byte[] ofHexadecimalString(final CharSequence hexadecimalCharSequence, final boolean withSpacing) {
		Ensure.notNull("hexadecimalCharSequence", hexadecimalCharSequence);
		final var length = hexadecimalCharSequence.length();
		if (0 == length) {
			return EMPTY;
		}
		final var spacing = withSpacing ? 1 : 0;
		Ensure.multipleOf("hexadecimalCharSequence length", length + spacing, 2 + spacing);
		final var bytes = new byte[(length + spacing) / (2 + spacing)];
		for (var i = 0; i < length; ++i) {
			final var c = hexadecimalCharSequence.charAt(i);
			if (withSpacing && 2 == i % 3) {
				if (' ' != c) {
					throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (space expected)");
				}
				continue;
			}
			final var b = CharArrays.indexOf(BASE_CHARS, Character.toLowerCase(c));
			if (-1 == b) {
				throw new IllegalArgumentException("Invalid char: " + ToString.toString(c) + " (hexadecimal expected)");
			}
			bytes[i / (2 + spacing)] = (byte) ((bytes[i / (2 + spacing)] << 4) | b);
		}
		return bytes;
	}

	/**
	 * <p>Convert a {@code byte} array to a boxed {@link Byte} array.</p>
	 * @param array the {@code byte} array to convert
	 * @return the created boxed {@link Byte} array
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static Byte[] toBoxed(final byte[] array) {
		Ensure.notNull("array", array);
		if (isEmpty(array)) {
			return ObjectArrays.empty(Byte.class);
		}
		final var boxedArray = new Byte[array.length];
		Arrays.setAll(boxedArray, i -> array[i]);
		return boxedArray;
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
	 * <p>Convert a {@code byte} array back to a {@code short} value using {@link ByteOrder#nativeOrder()}
	 * {@link ByteOrder}.</p>
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
	 * <p>Convert a {@code byte} array back to a {@code short} value using the given {@link ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the converted {@code short} value
	 * @throws NullPointerException if the {@code byte} array or the {@link ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static short toShort(final byte[] bytes, final ByteOrder order) {
		Ensure.notNull("bytes", bytes);
		Ensure.equalTo("bytes length", bytes.length, Short.BYTES);
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (short) ((bytes[0] & 0xff) << 8 | bytes[1] & 0xff);
		} else {
			return (short) (bytes[0] & 0xff | (bytes[1] & 0xff) << 8);
		}
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code char} value using {@link ByteOrder#nativeOrder()}
	 * {@link ByteOrder}.</p>
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
	 * <p>Convert a {@code byte} array back to a {@code char} value using the given {@link ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the converted {@code char} value
	 * @throws NullPointerException if the {@code byte} array or the {@link ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static char toChar(final byte[] bytes, final ByteOrder order) {
		Ensure.notNull("bytes", bytes);
		Ensure.equalTo("bytes length", bytes.length, Character.BYTES);
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (char) ((bytes[0] & 0xff) << 8 | bytes[1] & 0xff);
		} else {
			return (char) (bytes[0] & 0xff | (bytes[1] & 0xff) << 8);
		}
	}

	/**
	 * <p>Convert a {@code byte} array back to an {@code int} value using {@link ByteOrder#nativeOrder()}
	 * {@link ByteOrder}.</p>
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
	 * <p>Convert a {@code byte} array back to an {@code int} value using the given {@link ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the converted {@code int} value
	 * @throws NullPointerException if the {@code byte} array or the {@link ByteOrder} is {@code null}
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
		} else {
			return bytes[0] & 0xff
					| (bytes[1] & 0xff) << 8
					| (bytes[2] & 0xff) << 16
					| (bytes[3] & 0xff) << 24;
		}
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code long} value using {@link ByteOrder#nativeOrder()}
	 * {@link ByteOrder}.</p>
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
	 * <p>Convert a {@code byte} array back to a {@code long} value using the given {@link ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the converted {@code long} value
	 * @throws NullPointerException if the {@code byte} array or the {@link ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static long toLong(final byte[] bytes, final ByteOrder order) {
		Ensure.notNull("bytes", bytes);
		Ensure.equalTo("bytes length", bytes.length, Long.BYTES);
		Ensure.notNull("order", order);
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return ((long) bytes[0] & 0xff) << 56
					| ((long) bytes[1] & 0xff) << 48
					| ((long) bytes[2] & 0xff) << 40
					| ((long) bytes[3] & 0xff) << 32
					| ((long) bytes[4] & 0xff) << 24
					| ((long) bytes[5] & 0xff) << 16
					| ((long) bytes[6] & 0xff) << 8
					| (long) bytes[7] & 0xff;
		} else {
			return (long) bytes[0] & 0xff
					| ((long) bytes[1] & 0xff) << 8
					| ((long) bytes[2] & 0xff) << 16
					| ((long) bytes[3] & 0xff) << 24
					| ((long) bytes[4] & 0xff) << 32
					| ((long) bytes[5] & 0xff) << 40
					| ((long) bytes[6] & 0xff) << 48
					| ((long) bytes[7] & 0xff) << 56;
		}
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code float} value using {@link ByteOrder#nativeOrder()}
	 * {@link ByteOrder}.</p>
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
	 * <p>Convert a {@code byte} array back to a {@code float} value using the given {@link ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the converted {@code float} value
	 * @throws NullPointerException if the {@code byte} array or the {@link ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static float toFloat(final byte[] bytes, final ByteOrder order) {
		return Float.intBitsToFloat(toInt(bytes, order));
	}

	/**
	 * <p>Convert a {@code byte} array back to a {@code double} value using {@link ByteOrder#nativeOrder()}
	 * {@link ByteOrder}.</p>
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
	 * <p>Convert a {@code byte} array back to a {@code double} value using the given {@link ByteOrder}.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param order the {@link ByteOrder} to use
	 * @return the converted {@code double} value
	 * @throws NullPointerException if the {@code byte} array or the {@link ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array length is not valid
	 * @since 1.0.0
	 */
	public static double toDouble(final byte[] bytes, final ByteOrder order) {
		return Double.longBitsToDouble(toLong(bytes, order));
	}

	/**
	 * <p>Convert a {@code byte} array to a binary {@link String} value.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted binary {@link String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static String toBinaryString(final byte[] bytes) {
		return toBinaryString(bytes, DEFAULT_WITH_SPACING);
	}

	/**
	 * <p>Convert a {@code byte} array to a binary {@link String} value with spacing or not.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param withSpacing {@code true} if the binary {@link String} value must have spacing
	 * @return the converted binary {@link String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.7.0
	 */
	public static String toBinaryString(final byte[] bytes, final boolean withSpacing) {
		Ensure.notNull("bytes", bytes);
		if (isEmpty(bytes)) {
			return Strings.EMPTY;
		}
		final var chars = new char[(withSpacing ? 9 : 8) * bytes.length - (withSpacing ? 1 : 0)];
		for (var i = 0; i < bytes.length; ++i) {
			final var b = Byte.toUnsignedInt(bytes[i]);
			chars[(withSpacing ? 9 : 8) * i] = BASE_CHARS[b >>> 7 & 0b00000001];
			chars[(withSpacing ? 9 : 8) * i + 1] = BASE_CHARS[b >>> 6 & 0b00000001];
			chars[(withSpacing ? 9 : 8) * i + 2] = BASE_CHARS[b >>> 5 & 0b00000001];
			chars[(withSpacing ? 9 : 8) * i + 3] = BASE_CHARS[b >>> 4 & 0b00000001];
			chars[(withSpacing ? 9 : 8) * i + 4] = BASE_CHARS[b >>> 3 & 0b00000001];
			chars[(withSpacing ? 9 : 8) * i + 5] = BASE_CHARS[b >>> 2 & 0b00000001];
			chars[(withSpacing ? 9 : 8) * i + 6] = BASE_CHARS[b >>> 1 & 0b00000001];
			chars[(withSpacing ? 9 : 8) * i + 7] = BASE_CHARS[b & 0b00000001];
			if (withSpacing && i + 1 < bytes.length) {
				chars[9 * i + 8] = ' ';
			}
		}
		return new String(chars);
	}

	/**
	 * <p>Convert a {@code byte} array to an octal {@link String} value.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted octal {@link String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.7.0
	 */
	public static String toOctalString(final byte[] bytes) {
		return toOctalString(bytes, DEFAULT_WITH_SPACING);
	}

	/**
	 * <p>Convert a {@code byte} array to an octal {@link String} value with spacing or not.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param withSpacing {@code true} if the octal {@link String} value must have spacing
	 * @return the converted octal {@link String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.7.0
	 */
	public static String toOctalString(final byte[] bytes, final boolean withSpacing) {
		Ensure.notNull("bytes", bytes);
		if (isEmpty(bytes)) {
			return Strings.EMPTY;
		}
		final var chars = new char[(withSpacing ? 4 : 3) * bytes.length - (withSpacing ? 1 : 0)];
		for (var i = 0; i < bytes.length; ++i) {
			final var b = Byte.toUnsignedInt(bytes[i]);
			chars[(withSpacing ? 4 : 3) * i] = BASE_CHARS[b >>> 6 & 0007];
			chars[(withSpacing ? 4 : 3) * i + 1] = BASE_CHARS[b >>> 3 & 0007];
			chars[(withSpacing ? 4 : 3) * i + 2] = BASE_CHARS[b & 0007];
			if (withSpacing && i + 1 < bytes.length) {
				chars[4 * i + 3] = ' ';
			}
		}
		return new String(chars);
	}

	/**
	 * <p>Convert a {@code byte} array to a decimal {@link String} value.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted decimal {@link String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.7.0
	 */
	public static String toDecimalString(final byte[] bytes) {
		return toDecimalString(bytes, DEFAULT_WITH_SPACING);
	}

	/**
	 * <p>Convert a {@code byte} array to a decimal {@link String} value with spacing or not.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param withSpacing {@code true} if the decimal {@link String} value must have spacing
	 * @return the converted decimal {@link String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.7.0
	 */
	public static String toDecimalString(final byte[] bytes, final boolean withSpacing) {
		Ensure.notNull("bytes", bytes);
		if (isEmpty(bytes)) {
			return Strings.EMPTY;
		}
		final var chars = new char[(withSpacing ? 4 : 3) * bytes.length - (withSpacing ? 1 : 0)];
		for (var i = 0; i < bytes.length; ++i) {
			final var b = Byte.toUnsignedInt(bytes[i]);
			chars[(withSpacing ? 4 : 3) * i] = BASE_CHARS[b / 100 % 10];
			chars[(withSpacing ? 4 : 3) * i + 1] = BASE_CHARS[b / 10 % 10];
			chars[(withSpacing ? 4 : 3) * i + 2] = BASE_CHARS[b % 10];
			if (withSpacing && i + 1 < bytes.length) {
				chars[4 * i + 3] = ' ';
			}
		}
		return new String(chars);
	}

	/**
	 * <p>Convert a {@code byte} array to a hexadecimal {@link String} value.</p>
	 * <p><b>Note</b>: The hexadecimal {@link String} value will be in lowercase.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the converted hexadecimal {@link String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.0.0
	 */
	public static String toHexadecimalString(final byte[] bytes) {
		return toHexadecimalString(bytes, DEFAULT_WITH_SPACING);
	}

	/**
	 * <p>Convert a {@code byte} array to a hexadecimal {@link String} value with spacing or not.</p>
	 * <p><b>Note</b>: The hexadecimal {@link String} value will be in lowercase.</p>
	 * @param bytes the {@code byte} array to convert
	 * @param withSpacing {@code true} if the hexadecimal {@link String} value must have spacing
	 * @return the converted hexadecimal {@link String} value
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.7.0
	 */
	public static String toHexadecimalString(final byte[] bytes, final boolean withSpacing) {
		Ensure.notNull("bytes", bytes);
		if (isEmpty(bytes)) {
			return Strings.EMPTY;
		}
		final var chars = new char[(withSpacing ? 3 : 2) * bytes.length - (withSpacing ? 1 : 0)];
		for (var i = 0; i < bytes.length; ++i) {
			final var b = Byte.toUnsignedInt(bytes[i]);
			chars[(withSpacing ? 3 : 2) * i] = BASE_CHARS[b >>> 4 & 0x0f];
			chars[(withSpacing ? 3 : 2) * i + 1] = BASE_CHARS[b & 0x0f];
			if (withSpacing && i + 1 < bytes.length) {
				chars[3 * i + 2] = ' ';
			}
		}
		return new String(chars);
	}
}