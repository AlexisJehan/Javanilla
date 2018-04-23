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
package com.github.javanilla.lang.array;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

/**
 * <p>An utility class that provides {@code byte array} tools.</p>
 * @since 1.0
 */
public final class ByteArrays {

	/**
	 * <p>Chars used for hexadecimal {@code String} conversion.</p>
	 * @since 1.0
	 */
	private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * <p>An empty {@code byte array}.</p>
	 * @since 1.0
	 */
	public static final byte[] EMPTY = new byte[0];

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private ByteArrays() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code byte array} replacing {@code null} by an empty {@code byte array}.</p>
	 * @param array a {@code byte array} or {@code null}
	 * @return a non-{@code null} {@code byte array}
	 * @since 1.0
	 */
	public static byte[] nullToEmpty(final byte[] array) {
		return null != array ? array : EMPTY;
	}

	/**
	 * <p>Wrap a {@code byte array} replacing an empty one by {@code null}.</p>
	 * @param array a {@code byte array} or {@code null}
	 * @return a non-empty {@code byte array} or {@code null}
	 * @since 1.0
	 */
	public static byte[] emptyToNull(final byte[] array) {
		return null != array && 0 != array.length ? array : null;
	}

	/**
	 * <p>Get the first index of the {@code byte} value in the given {@code byte array}.</p>
	 * @param array the {@code byte array} to look into
	 * @param value the {@code byte} value to search
	 * @return the first index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @since 1.0
	 */
	public static int indexOf(final byte[] array, final byte value) {
		return indexOf(array, value, 0);
	}

	/**
	 * <p>Get the first index of the {@code byte} value in the given {@code byte array} starting from the provided
	 * index.</p>
	 * @param array the {@code byte array} to look into
	 * @param value the {@code byte} value to search
	 * @param fromIndex the index to start from
	 * @return the first index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IndexOutOfBoundsException if the index to start from is not valid
	 * @since 1.0
	 */
	public static int indexOf(final byte[] array, final byte value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 > fromIndex || array.length <= fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
		}
		for (var i = fromIndex; i < array.length; ++i) {
			if (value == array[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>Get the last index of the {@code byte} value in the given {@code byte array}.</p>
	 * @param array the {@code byte array} to look into
	 * @param value the {@code byte} value to search
	 * @return the last index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @since 1.0
	 */
	public static int lastIndexOf(final byte[] array, final byte value) {
		return lastIndexOf(array, value, 0);
	}

	/**
	 * <p>Get the last index of the {@code byte} value in the given {@code byte array} starting from the provided
	 * index.</p>
	 * @param array the {@code byte array} to look into
	 * @param value the {@code byte} value to search
	 * @param fromIndex the index to start from
	 * @return the last index of the {@code byte} value if found, {@code -1} otherwise
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IndexOutOfBoundsException if the index to start from is not valid
	 * @since 1.0
	 */
	public static int lastIndexOf(final byte[] array, final byte value, final int fromIndex) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 > fromIndex || array.length <= fromIndex) {
			throw new IndexOutOfBoundsException("Invalid from index: " + fromIndex + " (between 0 and " + (array.length - 1) + " expected)");
		}
		for (var i = array.length - 1; i > fromIndex; --i) {
			if (value == array[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>Tell if the {@code byte array} contains the given {@code byte} value.</p>
	 * @param array the {@code byte array} to look into
	 * @param value the {@code byte} value to search
	 * @return {@code true} if the given {@code byte} value is contained by the {@code byte array}
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @since 1.0
	 */
	public static boolean contains(final byte[] array, final byte value) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var element : array) {
			if (value == element) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>Tell if the {@code byte array} contains only the given {@code byte} value.</p>
	 * @param array the {@code byte array} to look into
	 * @param value the {@code byte} value to search
	 * @return {@code true} if the given {@code byte} value is the only value contained by the {@code byte array}
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @since 1.0
	 */
	public static boolean containsOnly(final byte[] array, final byte value) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (0 == array.length) {
			return false;
		}
		for (final var element : array) {
			if (value != element) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if the {@code byte array} contains any of given {@code byte} values.</p>
	 * @param array the {@code byte array} to look into
	 * @param values {@code byte} values to search
	 * @return {@code true} if any of given {@code byte} values is contained by the {@code byte array}
	 * @throws NullPointerException if the {@code byte array} or {@code byte} values are {@code null}
	 * @since 1.0
	 */
	public static boolean containsAny(final byte[] array, final byte... values) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == array.length || 0 == values.length) {
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
	 * <p>Tell if the {@code byte array} contains all of given {@code byte} values.</p>
	 * @param array the {@code byte array} to look into
	 * @param values {@code byte} values to search
	 * @return {@code true} if all of given {@code byte} values are contained by the {@code byte array}
	 * @throws NullPointerException if the {@code byte array} or {@code byte} values are {@code null}
	 * @since 1.0
	 */
	public static boolean containsAll(final byte[] array, final byte... values) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == array.length || 0 == values.length) {
			return false;
		}
		for (final var value : values) {
			var found = false;
			for (final var element : array) {
				if (value == element) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Concatenate multiple {@code byte array}s.</p>
	 * @param arrays {@code byte array}s to concatenate
	 * @return the concatenated {@code byte array}
	 * @throws NullPointerException if the array or any of the {@code byte array}s is {@code null}
	 * @since 1.0
	 */
	public static byte[] concat(final byte[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return concat(Arrays.asList(arrays));
	}

	/**
	 * <p>Concatenate a list of {@code byte array}s.</p>
	 * @param arrays {@code byte array}s to concatenate
	 * @return the concatenated {@code byte array}
	 * @throws NullPointerException if the {@code byte array} list or any of the {@code byte array}s is {@code null}
	 * @since 1.0
	 */
	public static byte[] concat(final List<byte[]> arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		for (final var array : arrays) {
			if (null == array) {
				throw new NullPointerException("Invalid array (not null expected)");
			}
		}
		if (arrays.isEmpty()) {
			return EMPTY;
		}
		if (1 == arrays.size()) {
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
	 * <p>Join multiple {@code byte array}s using a {@code byte array} separator.</p>
	 * @param separator the {@code byte array} sequence to add between each joined {@code byte array}
	 * @param arrays {@code byte array}s to join
	 * @return the joined {@code byte array}
	 * @throws NullPointerException if the separator, the array or any of the {@code byte array}s is {@code null}
	 * @since 1.0
	 */
	public static byte[] join(final byte[] separator, final byte[]... arrays) {
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		return join(separator, Arrays.asList(arrays));
	}

	/**
	 * <p>Join a list of {@code byte array}s using a {@code byte array} separator.</p>
	 * @param separator the {@code byte array} sequence to add between each joined {@code byte array}
	 * @param arrays {@code byte array}s to join
	 * @return the joined {@code byte array}
	 * @throws NullPointerException if the separator, the {@code byte array} list or any of the {@code byte array}s is
	 * {@code null}
	 * @since 1.0
	 */
	public static byte[] join(final byte[] separator, final List<byte[]> arrays) {
		if (null == separator) {
			throw new NullPointerException("Invalid separator (not null expected)");
		}
		if (null == arrays) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		for (final var array : arrays) {
			if (null == array) {
				throw new NullPointerException("Invalid array (not null expected)");
			}
		}
		if (0 == separator.length) {
			return concat(arrays);
		}
		if (arrays.isEmpty()) {
			return EMPTY;
		}
		if (1 == arrays.size()) {
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
	 * <p>Create a {@code byte array} using given {@code byte} values.</p>
	 * @param values {@code byte} values
	 * @return the created {@code byte array}
	 * @throws NullPointerException if {@code byte} values are {@code null}
	 * @since 1.0
	 */
	public static byte[] of(final byte... values) {
		if (null == values) {
			throw new NullPointerException("Invalid values (not null expected)");
		}
		if (0 == values.length) {
			return EMPTY;
		}
		return values;
	}

	/**
	 * <p>Create a {@code byte array} from a {@code boolean} value.</p>
	 * @param value the {@code boolean} value
	 * @return the created {@code byte array}
	 * @since 1.0
	 */
	public static byte[] ofBoolean(final boolean value) {
		return of(value ? (byte) 1 : (byte) 0);
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code boolean} value.</p>
	 * @param array the {@code byte array} to convert
	 * @return the converted {@code boolean} value
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static boolean toBoolean(final byte[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (1 != array.length) {
			throw new IllegalArgumentException("Invalid array length: " + array.length + " (1 expected)");
		}
		return (byte) 1 == array[0];
	}

	/**
	 * <p>Create a {@code byte array} from a {@code short} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param value the {@code short} value
	 * @return the created {@code byte array}
	 * @since 1.0
	 */
	public static byte[] ofShort(final short value) {
		return ofShort(value, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte array} from a {@code short} value using the given {@code ByteOrder}.</p>
	 * @param value the {@code short} value
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte array}
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0
	 */
	public static byte[] ofShort(final short value, final ByteOrder order) {
		if (null == order) {
			throw new NullPointerException("Invalid byte order (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of((byte) (value >> 8), (byte) value);
		}
		return of((byte) value, (byte) (value >> 8));
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code short} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @return the converted {@code short} value
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static short toShort(final byte[] array) {
		return toShort(array, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code short} value using the given {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code short} value
	 * @throws NullPointerException whether the {@code byte array} or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static short toShort(final byte[] array, final ByteOrder order) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == order) {
			throw new NullPointerException("Invalid byte order (not null expected)");
		}
		if (Short.BYTES != array.length) {
			throw new IllegalArgumentException("Invalid array length: " + array.length + " (" + Short.BYTES + " expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (short) (
					(array[0] & 0xff) << 8
							| (array[1] & 0xff)
			);
		}
		return (short) (
				(array[0] & 0xff)
						| (array[1] & 0xff) << 8
		);
	}

	/**
	 * <p>Create a {@code byte array} from a {@code char} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param value the {@code char} value
	 * @return the created {@code byte array}
	 * @since 1.0
	 */
	public static byte[] ofChar(final char value) {
		return ofChar(value, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte array} from a {@code char} value using the given {@code ByteOrder}.</p>
	 * @param value the {@code char} value
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte array}
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0
	 */
	public static byte[] ofChar(final char value, final ByteOrder order) {
		if (null == order) {
			throw new NullPointerException("Invalid byte order (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of((byte) (value >> 8), (byte) value);
		}
		return of((byte) value, (byte) (value >> 8));
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code char} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @return the converted {@code char} value
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static char toChar(final byte[] array) {
		return toChar(array, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code char} value using the given {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code char} value
	 * @throws NullPointerException whether the {@code byte array} or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static char toChar(final byte[] array, final ByteOrder order) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == order) {
			throw new NullPointerException("Invalid byte order (not null expected)");
		}
		if (Character.BYTES != array.length) {
			throw new IllegalArgumentException("Invalid array length: " + array.length + " (" + Character.BYTES + " expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (char) (
					(array[0] & 0xff) << 8
							| (array[1] & 0xff)
			);
		}
		return (char) (
				(array[0] & 0xff)
						| (array[1] & 0xff) << 8
		);
	}

	/**
	 * <p>Create a {@code byte array} from an {@code int} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param value the {@code int} value
	 * @return the created {@code byte array}
	 * @since 1.0
	 */
	public static byte[] ofInt(final int value) {
		return ofInt(value, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte array} from an {@code int} value using the given {@code ByteOrder}.</p>
	 * @param value the {@code int} value
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte array}
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0
	 */
	public static byte[] ofInt(final int value, final ByteOrder order) {
		if (null == order) {
			throw new NullPointerException("Invalid byte order (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of((byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value);
		}
		return of((byte) value, (byte) (value >> 8), (byte) (value >> 16), (byte) (value >> 24));
	}

	/**
	 * <p>Convert a {@code byte array} back to an {@code int} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @return the converted {@code int} value
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static int toInt(final byte[] array) {
		return toInt(array, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte array} back to an {@code int} value using the given {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code int} value
	 * @throws NullPointerException whether the {@code byte array} or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static int toInt(final byte[] array, final ByteOrder order) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == order) {
			throw new NullPointerException("Invalid byte order (not null expected)");
		}
		if (Integer.BYTES != array.length) {
			throw new IllegalArgumentException("Invalid array length: " + array.length + " (" + Integer.BYTES + " expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (array[0] & 0xff) << 24
					| (array[1] & 0xff) << 16
					| (array[2] & 0xff) << 8
					| (array[3] & 0xff);
		}
		return (array[0] & 0xff)
				| (array[1] & 0xff) << 8
				| (array[2] & 0xff) << 16
				| (array[3] & 0xff) << 24;
	}

	/**
	 * <p>Create a {@code byte array} from a {@code long} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param value the {@code long} value
	 * @return the created {@code byte array}
	 * @since 1.0
	 */
	public static byte[] ofLong(final long value) {
		return ofLong(value, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte array} from a {@code long} value using the given {@code ByteOrder}.</p>
	 * @param value the {@code long} value
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte array}
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0
	 */
	public static byte[] ofLong(final long value, final ByteOrder order) {
		if (null == order) {
			throw new NullPointerException("Invalid byte order (not null expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return of((byte) (value >> 56), (byte) (value >> 48), (byte) (value >> 40), (byte) (value >> 32), (byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value);
		}
		return of((byte) value, (byte) (value >> 8), (byte) (value >> 16), (byte) (value >> 24), (byte) (value >> 32), (byte) (value >> 40), (byte) (value >> 48), (byte) (value >> 56));
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code long} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @return the converted {@code long} value
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static long toLong(final byte[] array) {
		return toLong(array, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code long} value using the given {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code long} value
	 * @throws NullPointerException whether the {@code byte array} or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static long toLong(final byte[] array, final ByteOrder order) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		if (null == order) {
			throw new NullPointerException("Invalid byte order (not null expected)");
		}
		if (Long.BYTES != array.length) {
			throw new IllegalArgumentException("Invalid array length: " + array.length + " (" + Long.BYTES + " expected)");
		}
		if (ByteOrder.BIG_ENDIAN.equals(order)) {
			return (long) (array[0] & 0xff) << 56
					| ((long) array[1] & 0xff) << 48
					| ((long) array[2] & 0xff) << 40
					| ((long) array[3] & 0xff) << 32
					| ((long) array[4] & 0xff) << 24
					| ((long) array[5] & 0xff) << 16
					| ((long) array[6] & 0xff) << 8
					| ((long) array[7] & 0xff);
		}
		return ((long) array[0] & 0xff)
				| ((long) array[1] & 0xff) << 8
				| ((long) array[2] & 0xff) << 16
				| ((long) array[3] & 0xff) << 24
				| ((long) array[4] & 0xff) << 32
				| ((long) array[5] & 0xff) << 40
				| ((long) array[6] & 0xff) << 48
				| ((long) array[7] & 0xff) << 56;
	}

	/**
	 * <p>Create a {@code byte array} from a {@code float} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param value the {@code float} value
	 * @return the created {@code byte array}
	 * @since 1.0
	 */
	public static byte[] ofFloat(final float value) {
		return ofFloat(value, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte array} from a {@code float} value using the given {@code ByteOrder}.</p>
	 * @param value the {@code float} value
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte array}
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0
	 */
	public static byte[] ofFloat(final float value, final ByteOrder order) {
		return ofInt(Float.floatToIntBits(value), order);
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code float} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @return the converted {@code float} value
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static float toFloat(final byte[] array) {
		return toFloat(array, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code float} value using the given {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code float} value
	 * @throws NullPointerException whether the {@code byte array} or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static float toFloat(final byte[] array, final ByteOrder order) {
		return Float.intBitsToFloat(toInt(array, order));
	}

	/**
	 * <p>Create a {@code byte array} from a {@code double} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param value the {@code double} value
	 * @return the created {@code byte array}
	 * @since 1.0
	 */
	public static byte[] ofDouble(final double value) {
		return ofDouble(value, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Create a {@code byte array} from a {@code double} value using the given {@code ByteOrder}.</p>
	 * @param value the {@code double} value
	 * @param order the {@code ByteOrder} to use
	 * @return the created {@code byte array}
	 * @throws NullPointerException if the {@code ByteOrder} is {@code null}
	 * @since 1.0
	 */
	public static byte[] ofDouble(final double value, final ByteOrder order) {
		return ofLong(Double.doubleToLongBits(value), order);
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code double} value using {@link ByteOrder#nativeOrder()}
	 * {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @return the converted {@code double} value
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static double toDouble(final byte[] array) {
		return toDouble(array, ByteOrder.nativeOrder());
	}

	/**
	 * <p>Convert a {@code byte array} back to a {@code double} value using the given {@code ByteOrder}.</p>
	 * @param array the {@code byte array} to convert
	 * @param order the {@code ByteOrder} to use
	 * @return the converted {@code double} value
	 * @throws NullPointerException whether the {@code byte array} or the {@code ByteOrder} is {@code null}
	 * @throws IllegalArgumentException if the {@code byte array} length is not valid
	 * @since 1.0
	 */
	public static double toDouble(final byte[] array, final ByteOrder order) {
		return Double.longBitsToDouble(toLong(array, order));
	}

	/**
	 * <p>Create a {@code byte array} from a hexadecimal {@code String} value.</p>
	 * <p><b>Note</b>: If the hexadecimal {@code String} value starts with {@code 0x} it will be stripped.</p>
	 * <p><b>Note</b>: The hexadecimal {@code String} value case does not matter.</p>
	 * @param hexString the hexadecimal {@code String} value
	 * @return the created {@code byte array}
	 * @throws NullPointerException if the hexadecimal {@code String} is {@code null}
	 * @throws IllegalArgumentException if the hexadecimal {@code String} value length is not a multiple of 2
	 * @since 1.0
	 */
	public static byte[] ofHexString(final String hexString) {
		if (null == hexString) {
			throw new NullPointerException("Invalid hexadecimal string (not null expected)");
		}
		final String input;
		if (hexString.startsWith("0x") && 2 < hexString.length()) {
			input = hexString.substring(2).toLowerCase();
		} else {
			input = hexString.toLowerCase();
		}
		final var length = input.length();
		if (0 != length % 2) {
			throw new IllegalArgumentException("Invalid hex string length: " + length + " (multiple of 2 expected)");
		}
		final var output = new byte[length / 2];
		for(var i = 0; i < length; i += 2) {
			final var c1 = input.charAt(i);
			final var i1 = CharArrays.indexOf(HEX_CHARS, c1);
			if (-1 == i1) {
				throw new IllegalArgumentException("Invalid hex char: " + c1);
			}
			final var c2 = input.charAt(i + 1);
			final var i2 = CharArrays.indexOf(HEX_CHARS, c2);
			if (-1 == i2) {
				throw new IllegalArgumentException("Invalid hex char: " + c2);
			}
			output[i / 2] = (byte) (i1 << 4 | i2);
		}
		return output;
	}

	/**
	 * <p>Convert a {@code byte array} to a hexadecimal {@code String} value.</p>
	 * <p><b>Note</b>: The hexadecimal {@code String} value will be in lowercase.</p>
	 * @param array the {@code byte array} to convert
	 * @return the converted hexadecimal {@code String} value
	 * @throws NullPointerException if the {@code byte array} is {@code null}
	 * @since 1.0
	 */
	public static String toHexString(final byte[] array) {
		if (null == array) {
			throw new NullPointerException("Invalid array (not null expected)");
		}
		final var output = new char[array.length * 2];
		for (var i = 0; i < array.length; ++i) {
			final var v = Byte.toUnsignedInt(array[i]);
			output[i * 2] = HEX_CHARS[v >>> 4];
			output[i * 2 + 1] = HEX_CHARS[v & 0x0f];
		}
		return new String(output);
	}
}