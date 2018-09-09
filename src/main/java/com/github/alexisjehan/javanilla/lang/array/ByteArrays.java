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
package com.github.alexisjehan.javanilla.lang.array;

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.util.iteration.Iterables;

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
	 * <p>{@code char} array used for binary {@code String} conversion.</p>
	 * @since 1.2.0
	 */
	private static final char[] BINARY_CHARS = {'0', '1'};

	/**
	 * <p>{@code char} array used for hexadecimal {@code String} conversion.</p>
	 * @since 1.0.0
	 */
	private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * <p>An empty {@code byte} array.</p>
	 * @since 1.0.0
	 */
	public static final byte[] EMPTY = {};

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
		if (null == defaultArray) {
			throw new NullPointerException("Invalid default array (not null expected)");
		}
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
		if (null != defaultArray && 0 == defaultArray.length) {
			throw new IllegalArgumentException("Invalid default array (not empty expected)");
		}
		return null == array || 0 != array.length ? array : defaultArray;
	}

	/**
	 * <p>Tell if a {@code byte} array is empty.</p>
	 * @param array the {@code byte} array to test
	 * @return {@code true} if the {@code byte} array is empty
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final byte[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return 0 == array.length;
	}

	/**
	 * <p>Get the first index of the {@code byte} value in the {@code byte} array.</p>
	 * @param array the {@code byte} array to lookup
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
	 * @param array the {@code byte} array to lookup
	 * @param value the {@code byte} value to search
	 * @param fromIndex the starting index
	 * @return the first index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int indexOf(final byte[] array, final byte value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 < array.length) {
			if (0 > fromIndex || array.length - 1 < fromIndex) {
				throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
			}
			for (var i = fromIndex; i < array.length; ++i) {
				if (value == array[i]) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * <p>Get the last index of the {@code byte} value in the {@code byte} array.</p>
	 * @param array the {@code byte} array to lookup
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
	 * @param array the {@code byte} array to lookup
	 * @param value the {@code byte} value to search
	 * @param fromIndex the starting index
	 * @return the last index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @throws IndexOutOfBoundsException if the starting index is not valid
	 * @since 1.0.0
	 */
	public static int lastIndexOf(final byte[] array, final byte value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 < array.length) {
			if (0 > fromIndex || array.length - 1 < fromIndex) {
				throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
			}
			for (var i = array.length - 1; i > fromIndex; --i) {
				if (value == array[i]) {
					return i;
				}
			}
		}
		return -1;
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
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			throw new IllegalArgumentException("Invalid values (not empty expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var value : values) {
			for (final var element : array) {
				if (value == element) {
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
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			throw new IllegalArgumentException("Invalid values (not empty expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var value : values) {
			var contained = false;
			for (final var element : array) {
				if (value == element) {
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
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			throw new IllegalArgumentException("Invalid values (not empty expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var value : values) {
			var contained = false;
			for (final var element : array) {
				if (value == element) {
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
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			throw new IllegalArgumentException("Invalid values (not empty expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var element : array) {
			var contained = false;
			for (final var value : values) {
				if (value == element) {
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
	 * <p>Shuffle values in the given {@code byte} array using the Fisher-Yates algorithm.</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle</a>
	 * @param array the {@code byte} array to shuffle
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static void shuffle(final byte[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
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
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
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
	 * @throws IllegalArgumentException if {@code byte} array and indexes array lengths are not the same or if
	 * indexes are not distinct
	 * @throws IndexOutOfBoundsException if any index is not valid
	 * @since 1.2.0
	 */
	public static void reorder(final byte[] array, final int... indexes) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == indexes) {
			throw new NullPointerException("Invalid indexes (not null expected)");
		}
		if (array.length != indexes.length) {
			throw new IllegalArgumentException("Invalid array and indexes lengths: " + array.length + " and " + indexes.length + " (same expected)");
		}
		if (array.length != Arrays.stream(indexes).distinct().count()) {
			throw new IllegalArgumentException("Invalid indexes (distinct expected)");
		}
		if (1 < array.length) {
			for (var i = 0; i < array.length; ++i) {
				var j = indexes[i];
				if (0 > j || array.length - 1 < j) {
					throw new IndexOutOfBoundsException("Invalid index: " + j + " (between 0 and " + (array.length - 1) + " expected)");
				}
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
	 * @throws IndexOutOfBoundsException if any index is not valid
	 * @since 1.2.0
	 */
	public static void swap(final byte[] array, final int index1, final int index2) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 > index1 || array.length - 1 < index1) {
			throw new IndexOutOfBoundsException("Invalid first index: " + index1 + " (between 0 and " + (array.length - 1) + " expected)");
		}
		if (0 > index2 || array.length - 1 < index2) {
			throw new IndexOutOfBoundsException("Invalid second index: " + index2 + " (between 0 and " + (array.length - 1) + " expected)");
		}
		if (index1 != index2) {
			final var value = array[index1];
			array[index1] = array[index2];
			array[index2] = value;
		}
	}

	/**
	 * <p>Concatenate multiple {@code byte} arrays.</p>
	 * @param arrays the {@code byte} array array to concatenate
	 * @return the concatenated {@code byte} array
	 * @throws NullPointerException if the {@code byte} array array or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] concat(final byte[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		return concat(Arrays.asList(arrays));
	}

	/**
	 * <p>Concatenate multiple {@code byte} arrays.</p>
	 * @param arrays the {@code byte} array {@code List} to concatenate
	 * @return the concatenated {@code byte} array
	 * @throws NullPointerException if the {@code byte} array {@code List} or any of them is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] concat(final List<byte[]> arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		for (final var indexedArray : Iterables.index(arrays)) {
			if (null == indexedArray.getElement()) {
				throw new NullPointerException("Invalid array at index " + indexedArray.getIndex() + " (not null expected)");
			}
		}
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
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		return join(separator, Arrays.asList(arrays));
	}

	/**
	 * <p>Join multiple {@code byte} arrays using a {@code byte} array separator.</p>
	 * @param separator the {@code byte} array separator
	 * @param arrays the {@code byte} array {@code List} to join
	 * @return the joined {@code byte} array
	 * @throws NullPointerException if the {@code byte} array separator, the {@code byte} array {@code List} or any of
	 * them is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] join(final byte[] separator, final List<byte[]> arrays) {
		if (null == separator) {
			throw new NullPointerException("Invalid separator (not null expected)");
		}
		if (null == arrays) {
			throw new NullPointerException("Invalid arrays (not null expected)");
		}
		for (final var indexedArray : Iterables.index(arrays)) {
			if (null == indexedArray.getElement()) {
				throw new NullPointerException("Invalid array at index " + indexedArray.getIndex() + " (not null expected)");
			}
		}
		if (0 == separator.length) {
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
	 * <p>Create a {@code byte} array from a single {@code byte}.</p>
	 * @param b the {@code byte} to convert
	 * @return the created {@code byte} array
	 * @since 1.1.0
	 */
	public static byte[] singleton(final byte b) {
		return of(b);
	}

	/**
	 * <p>Create a {@code byte} array from multiple {@code byte}s.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.0.0
	 */
	public static byte[] of(final byte... bytes) {
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (0 == bytes.length) {
			return EMPTY;
		}
		return bytes;
	}

	/**
	 * <p>Create a {@code byte} array from a boxed {@code Byte} array.</p>
	 * @param boxedBytes the boxed {@code Byte} array to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the boxed {@code Byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static byte[] of(final Byte[] boxedBytes) {
		if (null == boxedBytes) {
			throw new NullPointerException("Invalid Bytes (not null expected)");
		}
		if (0 == boxedBytes.length) {
			return EMPTY;
		}
		final var bytes = new byte[boxedBytes.length];
		for (var i = 0; i < bytes.length; ++i) {
			bytes[i] = boxedBytes[i];
		}
		return bytes;
	}

	/**
	 * <p>Convert a {@code byte} array to a boxed {@code Byte} array.</p>
	 * @param bytes the {@code byte} array to convert
	 * @return the created boxed {@code Byte} array
	 * @throws NullPointerException if the {@code byte} array is {@code null}
	 * @since 1.2.0
	 */
	public static Byte[] toBoxed(final byte[] bytes) {
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		final var boxedBytes = new Byte[bytes.length];
		for (var i = 0; i < boxedBytes.length; ++i) {
			boxedBytes[i] = bytes[i];
		}
		return boxedBytes;
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
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (1 != bytes.length) {
			throw new IllegalArgumentException("Invalid bytes length: " + bytes.length + " (equal to 1 expected)");
		}
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
		if (null == order) {
			throw new NullPointerException("Invalid ByteOrder (not null expected)");
		}
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
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (Short.BYTES != bytes.length) {
			throw new IllegalArgumentException("Invalid bytes length: " + bytes.length + " (equal to " + Short.BYTES + " expected)");
		}
		if (null == order) {
			throw new NullPointerException("Invalid ByteOrder (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (short) (
					(bytes[0] & 0xff) << 8
							| (bytes[1] & 0xff)
			);
		}
		return (short) (
				(bytes[0] & 0xff)
						| (bytes[1] & 0xff) << 8
		);
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
		if (null == order) {
			throw new NullPointerException("Invalid ByteOrder (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of(
					(byte) (c >> 8),
					(byte) c
			);
		}
		return of(
				(byte) c,
				(byte) (c >> 8)
		);
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
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (Character.BYTES != bytes.length) {
			throw new IllegalArgumentException("Invalid bytes length: " + bytes.length + " (equal to " + Character.BYTES + " expected)");
		}
		if (null == order) {
			throw new NullPointerException("Invalid ByteOrder (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (char) (
					(bytes[0] & 0xff) << 8
							| (bytes[1] & 0xff)
			);
		}
		return (char) (
				(bytes[0] & 0xff)
						| (bytes[1] & 0xff) << 8
		);
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
		if (null == order) {
			throw new NullPointerException("Invalid ByteOrder (not null expected)");
		}
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
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (Integer.BYTES != bytes.length) {
			throw new IllegalArgumentException("Invalid bytes length: " + bytes.length + " (equal to " + Integer.BYTES + " expected)");
		}
		if (null == order) {
			throw new NullPointerException("Invalid ByteOrder (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (bytes[0] & 0xff) << 24
					| (bytes[1] & 0xff) << 16
					| (bytes[2] & 0xff) << 8
					| (bytes[3] & 0xff);
		}
		return (bytes[0] & 0xff)
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
		if (null == order) {
			throw new NullPointerException("Invalid ByteOrder (not null expected)");
		}
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
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (Long.BYTES != bytes.length) {
			throw new IllegalArgumentException("Invalid bytes length: " + bytes.length + " (equal to " + Long.BYTES + " expected)");
		}
		if (null == order) {
			throw new NullPointerException("Invalid ByteOrder (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (long) (bytes[0] & 0xff) << 56
					| ((long) bytes[1] & 0xff) << 48
					| ((long) bytes[2] & 0xff) << 40
					| ((long) bytes[3] & 0xff) << 32
					| ((long) bytes[4] & 0xff) << 24
					| ((long) bytes[5] & 0xff) << 16
					| ((long) bytes[6] & 0xff) << 8
					| ((long) bytes[7] & 0xff);
		}
		return ((long) bytes[0] & 0xff)
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
	 * @throws IllegalArgumentException if the binary {@code CharSequence} length is not a multiple of {@code 2} or if
	 * any {@code char} is not valid
	 * @since 1.2.0
	 */
	public static byte[] ofBinaryString(final CharSequence binaryCharSequence) {
		if (null == binaryCharSequence) {
			throw new NullPointerException("Invalid binary CharSequence (not null expected)");
		}
		final var length = binaryCharSequence.length();
		if (0 != length % 8) {
			throw new IllegalArgumentException("Invalid binary CharSequence length: " + length + " (multiple of 8 expected)");
		}
		if (0 == length) {
			return EMPTY;
		}
		final var bytes = new byte[length / 8];
		for (var i = 0; i < length; ++i) {
			final var c = binaryCharSequence.charAt(i);
			final var p = CharArrays.indexOf(BINARY_CHARS, c);
			if (-1 == p) {
				throw new IllegalArgumentException("Invalid binary char: " + Strings.quote(c));
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
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (0 == bytes.length) {
			return Strings.EMPTY;
		}
		final var binaryChars = new char[8 * bytes.length];
		for (var i = 0; i < bytes.length; ++i) {
			final var b = Byte.toUnsignedInt(bytes[i]);
			binaryChars[8 * i] = BINARY_CHARS[b >>> 7 & 0b00000001];
			binaryChars[8 * i + 1] = BINARY_CHARS[b >>> 6 & 0b00000001];
			binaryChars[8 * i + 2] = BINARY_CHARS[b >>> 5 & 0b00000001];
			binaryChars[8 * i + 3] = BINARY_CHARS[b >>> 4 & 0b00000001];
			binaryChars[8 * i + 4] = BINARY_CHARS[b >>> 3 & 0b00000001];
			binaryChars[8 * i + 5] = BINARY_CHARS[b >>> 2 & 0b00000001];
			binaryChars[8 * i + 6] = BINARY_CHARS[b >>> 1 & 0b00000001];
			binaryChars[8 * i + 7] = BINARY_CHARS[b & 0x01];
		}
		return new String(binaryChars);
	}

	/**
	 * <p>Create a {@code byte} array from a hexadecimal {@code CharSequence}.</p>
	 * <p><b>Note</b>: The hexadecimal {@code CharSequence} value case does not matter.</p>
	 * @param hexCharSequence the hexadecimal {@code CharSequence} to convert
	 * @return the created {@code byte} array
	 * @throws NullPointerException if the hexadecimal {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the hexadecimal {@code CharSequence} length is not a multiple of {@code 2} or
	 * if any {@code char} is not valid
	 * @since 1.0.0
	 */
	public static byte[] ofHexString(final CharSequence hexCharSequence) {
		if (null == hexCharSequence) {
			throw new NullPointerException("Invalid hexadecimal CharSequence (not null expected)");
		}
		final var length = hexCharSequence.length();
		if (0 != length % 2) {
			throw new IllegalArgumentException("Invalid hexadecimal CharSequence length: " + length + " (multiple of 2 expected)");
		}
		if (0 == length) {
			return EMPTY;
		}
		final var bytes = new byte[length / 2];
		for (var i = 0; i < length; ++i) {
			final var c = hexCharSequence.charAt(i);
			final var p = CharArrays.indexOf(HEX_CHARS, Character.toLowerCase(c));
			if (-1 == p) {
				throw new IllegalArgumentException("Invalid hexadecimal char: " + Strings.quote(c));
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
	public static String toHexString(final byte[] bytes) {
		if (null == bytes) {
			throw new NullPointerException("Invalid bytes (not null expected)");
		}
		if (0 == bytes.length) {
			return Strings.EMPTY;
		}
		final var hexChars = new char[2 * bytes.length];
		for (var i = 0; i < bytes.length; ++i) {
			final var b = Byte.toUnsignedInt(bytes[i]);
			hexChars[i * 2] = HEX_CHARS[b >>> 4 & 0b00001111];
			hexChars[i * 2 + 1] = HEX_CHARS[b & 0b00001111];
		}
		return new String(hexChars);
	}
}