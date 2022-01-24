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

import com.github.alexisjehan.javanilla.lang.Comparables;
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
import com.github.alexisjehan.javanilla.util.collection.bags.Bag;
import com.github.alexisjehan.javanilla.util.iteration.Iterables;
import com.github.alexisjehan.javanilla.util.iteration.Iterators;

import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>An utility class that provides multiple functions to validate arguments, throwing {@link NullPointerException} or
 * {@link IllegalArgumentException} if they are not valid.</p>
 * @since 1.3.0
 */
public final class Ensure {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.3.0
	 */
	private Ensure() {
		// Not available
	}

	/**
	 * <p>Ensure the value is not {@code null}.</p>
	 * @param name the name of the value
	 * @param value the value to validate
	 * @param <V> the value type
	 * @return the validated value
	 * @throws NullPointerException if the name or the value is {@code null}
	 * @since 1.3.0
	 */
	public static <V> V notNull(final String name, final V value) {
		if (null == name) {
			throw new NullPointerException("Invalid name (not null expected)");
		}
		if (null == value) {
			throw new NullPointerException("Invalid " + name + " (not null expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the array and its elements are not {@code null}.</p>
	 * @param name the name of the array
	 * @param array the array to validate
	 * @param <E> the element type
	 * @return the validated array
	 * @throws NullPointerException if the name, the array or any of its elements is {@code null}
	 * @since 1.3.0
	 */
	public static <E> E[] notNullAndNotNullElements(final String name, final E[] array) {
		notNull(name, array);
		for (var i = 0; i < array.length; ++i) {
			notNull(name + " element at index " + i, array[i]);
		}
		return array;
	}

	/**
	 * <p>Ensure the {@link Iterable} and its elements are not {@code null}.</p>
	 * @param name the name of the {@link Iterable}
	 * @param iterable the {@link Iterable} to validate
	 * @param <I> the {@link Iterable} type
	 * @return the validated {@link Iterable}
	 * @throws NullPointerException if the name, the {@link Iterable} or any of its elements is {@code null}
	 * @since 1.3.0
	 */
	public static <I extends Iterable<?>> I notNullAndNotNullElements(final String name, final I iterable) {
		notNull(name, iterable);
		for (final var indexedElement : Iterables.index(iterable)) {
			notNull(name + " element at index " + indexedElement.getIndex(), indexedElement.getElement());
		}
		return iterable;
	}

	/**
	 * <p>Ensure the {@link CharSequence} is not {@code null} and not empty.</p>
	 * @param name the name of the {@link CharSequence}
	 * @param charSequence the {@link CharSequence} to validate
	 * @param <C> the {@link CharSequence} type
	 * @return the validated {@link CharSequence}
	 * @throws NullPointerException if the name or the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@link CharSequence} is empty
	 * @since 1.3.0
	 */
	public static <C extends CharSequence> C notNullAndNotEmpty(final String name, final C charSequence) {
		notNull(name, charSequence);
		if (Strings.isEmpty(charSequence)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(charSequence) + " (not empty expected)");
		}
		return charSequence;
	}

	/**
	 * <p>Ensure the {@code boolean} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@code boolean} array
	 * @param array the {@code boolean} array to validate
	 * @return the validated {@code boolean} array
	 * @throws NullPointerException if the name or the {@code boolean} array is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} array is empty
	 * @since 1.3.0
	 */
	public static boolean[] notNullAndNotEmpty(final String name, final boolean[] array) {
		notNull(name, array);
		if (BooleanArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code byte} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@code byte} array
	 * @param array the {@code byte} array to validate
	 * @return the validated {@code byte} array
	 * @throws NullPointerException if the name or the {@code byte} array is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array is empty
	 * @since 1.3.0
	 */
	public static byte[] notNullAndNotEmpty(final String name, final byte[] array) {
		notNull(name, array);
		if (ByteArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code short} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@code short} array
	 * @param array the {@code short} array to validate
	 * @return the validated {@code short} array
	 * @throws NullPointerException if the name or the {@code short} array is {@code null}
	 * @throws IllegalArgumentException if the {@code short} array is empty
	 * @since 1.3.0
	 */
	public static short[] notNullAndNotEmpty(final String name, final short[] array) {
		notNull(name, array);
		if (ShortArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code char} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@code char} array
	 * @param array the {@code char} array to validate
	 * @return the validated {@code char} array
	 * @throws NullPointerException if the name or the {@code char} array is {@code null}
	 * @throws IllegalArgumentException if the {@code char} array is empty
	 * @since 1.3.0
	 */
	public static char[] notNullAndNotEmpty(final String name, final char[] array) {
		notNull(name, array);
		if (CharArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code int} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@code int} array
	 * @param array the {@code int} array to validate
	 * @return the validated {@code int} array
	 * @throws NullPointerException if the name or the {@code int} array is {@code null}
	 * @throws IllegalArgumentException if the {@code int} array is empty
	 * @since 1.3.0
	 */
	public static int[] notNullAndNotEmpty(final String name, final int[] array) {
		notNull(name, array);
		if (IntArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code long} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@code long} array
	 * @param array the {@code long} array to validate
	 * @return the validated {@code long} array
	 * @throws NullPointerException if the name or the {@code long} array is {@code null}
	 * @throws IllegalArgumentException if the {@code long} array is empty
	 * @since 1.3.0
	 */
	public static long[] notNullAndNotEmpty(final String name, final long[] array) {
		notNull(name, array);
		if (LongArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code float} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@code float} array
	 * @param array the {@code float} array to validate
	 * @return the validated {@code float} array
	 * @throws NullPointerException if the name or the {@code float} array is {@code null}
	 * @throws IllegalArgumentException if the {@code float} array is empty
	 * @since 1.3.0
	 */
	public static float[] notNullAndNotEmpty(final String name, final float[] array) {
		notNull(name, array);
		if (FloatArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code double} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@code double} array
	 * @param array the {@code double} array to validate
	 * @return the validated {@code double} array
	 * @throws NullPointerException if the name or the {@code double} array is {@code null}
	 * @throws IllegalArgumentException if the {@code double} array is empty
	 * @since 1.3.0
	 */
	public static double[] notNullAndNotEmpty(final String name, final double[] array) {
		notNull(name, array);
		if (DoubleArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@link Object} array is not {@code null} and not empty.</p>
	 * @param name the name of the {@link Object} array
	 * @param array the {@link Object} array to validate
	 * @param <E> the element type
	 * @return the validated array
	 * @throws NullPointerException if the name or the {@link Object} array is {@code null}
	 * @throws IllegalArgumentException if the {@link Object} array is empty
	 * @since 1.3.0
	 */
	public static <E> E[] notNullAndNotEmpty(final String name, final E[] array) {
		notNull(name, array);
		if (ObjectArrays.isEmpty(array)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not empty expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@link Collection} is not {@code null} and not empty.</p>
	 * @param name the name of the {@link Collection}
	 * @param collection the {@link Collection} to validate
	 * @param <C> the {@link Collection} type
	 * @return the validated {@link Collection}
	 * @throws NullPointerException if the name or the {@link Collection} is {@code null}
	 * @throws IllegalArgumentException if the {@link Collection} is empty
	 * @since 1.3.0
	 */
	public static <C extends Collection<?>> C notNullAndNotEmpty(final String name, final C collection) {
		notNull(name, collection);
		if (collection.isEmpty()) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(collection) + " (not empty expected)");
		}
		return collection;
	}

	/**
	 * <p>Ensure the {@link Map} is not {@code null} and not empty.</p>
	 * @param name the name of the {@link Map}
	 * @param map the {@link Map} to validate
	 * @param <M> the {@link Map} type
	 * @return the validated {@link Map}
	 * @throws NullPointerException if the name or the {@link Map} is {@code null}
	 * @throws IllegalArgumentException if the {@link Map} is empty
	 * @since 1.3.0
	 */
	public static <M extends Map<?, ?>> M notNullAndNotEmpty(final String name, final M map) {
		notNull(name, map);
		if (map.isEmpty()) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(map) + " (not empty expected)");
		}
		return map;
	}

	/**
	 * <p>Ensure the {@link Bag} is not {@code null} and not empty.</p>
	 * @param name the name of the {@link Bag}
	 * @param bag the {@link Bag} to validate
	 * @param <B> the {@link Bag} type
	 * @return the validated {@link Bag}
	 * @throws NullPointerException if the name or the {@link Bag} is {@code null}
	 * @throws IllegalArgumentException if the {@link Bag} is empty
	 * @since 1.3.0
	 */
	public static <B extends Bag<?>> B notNullAndNotEmpty(final String name, final B bag) {
		notNull(name, bag);
		if (bag.isEmpty()) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(bag) + " (not empty expected)");
		}
		return bag;
	}

	/**
	 * <p>Ensure the {@link Iterator} is not {@code null} and not empty.</p>
	 * @param name the name of the {@link Iterator}
	 * @param iterator the {@link Iterator} to validate
	 * @param <I> the {@link Iterator} type
	 * @return the validated {@link Iterator}
	 * @throws NullPointerException if the name or the {@link Iterator} is {@code null}
	 * @throws IllegalArgumentException if the {@link Iterator} is empty
	 * @since 1.3.0
	 */
	public static <I extends Iterator<?>> I notNullAndNotEmpty(final String name, final I iterator) {
		notNull(name, iterator);
		if (Iterators.isEmpty(iterator)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(iterator) + " (not empty expected)");
		}
		return iterator;
	}

	/**
	 * <p>Ensure the {@link CharSequence} is not {@code null} and not blank.</p>
	 * @param name the name of the {@link CharSequence}
	 * @param charSequence the {@link CharSequence} to validate
	 * @param <C> the {@link CharSequence} type
	 * @return the validated {@link CharSequence}
	 * @throws NullPointerException if the name or the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@link CharSequence} is blank
	 * @since 1.3.0
	 */
	@SuppressWarnings("deprecation")
	public static <C extends CharSequence> C notNullAndNotBlank(final String name, final C charSequence) {
		notNull(name, charSequence);
		if (Strings.isBlank(charSequence)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(charSequence) + " (not blank expected)");
		}
		return charSequence;
	}

	/**
	 * <p>Ensure the {@link CharSequence} is not {@code null} and matches the given {@link Pattern}.</p>
	 * @param name the name of the {@link CharSequence}
	 * @param charSequence the {@link CharSequence} to validate
	 * @param pattern the {@link Pattern}
	 * @param <C> the {@link CharSequence} type
	 * @return the validated {@link CharSequence}
	 * @throws NullPointerException if the name, the {@link CharSequence} or the {@link Pattern} is {@code null}
	 * @throws IllegalArgumentException if the {@link CharSequence} does not match the {@link Pattern}
	 * @since 1.4.0
	 */
	public static <C extends CharSequence> C notNullAndMatches(final String name, final C charSequence, final Pattern pattern) {
		notNull(name, charSequence);
		notNull("pattern", pattern);
		if (!pattern.matcher(charSequence).matches()) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(charSequence) + " (matching " + ToString.toString(pattern) + " expected)");
		}
		return charSequence;
	}

	/**
	 * <p>Ensure the {@link InputStream} is not {@code null} and has mark supported.</p>
	 * @param name the name of the {@link InputStream}
	 * @param inputStream the {@link InputStream} to validate
	 * @param <I> the {@link InputStream} type
	 * @return the validated {@link InputStream}
	 * @throws NullPointerException if the name or the {@link InputStream} is {@code null}
	 * @throws IllegalArgumentException if the {@link InputStream} does not have mark supported
	 * @since 1.3.0
	 */
	public static <I extends InputStream> I notNullAndMarkSupported(final String name, final I inputStream) {
		notNull(name, inputStream);
		if (!inputStream.markSupported()) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(inputStream) + " (mark supported expected)");
		}
		return inputStream;
	}

	/**
	 * <p>Ensure the {@link Reader} is not {@code null} and has mark supported.</p>
	 * @param name the name of the {@link Reader}
	 * @param reader the {@link Reader} to validate
	 * @param <R> the {@link Reader} type
	 * @return the validated {@link Reader}
	 * @throws NullPointerException if the name or the {@link Reader} is {@code null}
	 * @throws IllegalArgumentException if the {@link Reader} does not have mark supported
	 * @since 1.3.0
	 */
	public static <R extends Reader> R notNullAndMarkSupported(final String name, final R reader) {
		notNull(name, reader);
		if (!reader.markSupported()) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(reader) + " (mark supported expected)");
		}
		return reader;
	}

	/**
	 * <p>Ensure the {@link Path} is not {@code null} and exists.</p>
	 * @param name the name of the {@link Path}
	 * @param path the {@link Path} to validate
	 * @param <P> the {@link Path} type
	 * @return the validated {@link Path}
	 * @throws NullPointerException if the name or the {@link Path} is {@code null}
	 * @throws IllegalArgumentException if the {@link Path} does not exist
	 * @since 1.3.0
	 */
	public static <P extends Path> P notNullAndExists(final String name, final P path) {
		notNull(name, path);
		if (!Files.exists(path)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(path) + " (existing expected)");
		}
		return path;
	}

	/**
	 * <p>Ensure the {@link Path} is not {@code null}, exists and is a file.</p>
	 * @param name the name of the {@link Path}
	 * @param path the {@link Path} to validate
	 * @param <P> the {@link Path} type
	 * @return the validated {@link Path}
	 * @throws NullPointerException if the name or the {@link Path} is {@code null}
	 * @throws IllegalArgumentException if the {@link Path} does not exist or is not a file
	 * @since 1.3.0
	 */
	public static <P extends Path> P notNullAndFile(final String name, final P path) {
		notNull(name, path);
		if (!Files.isRegularFile(path)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(path) + " (existing file expected)");
		}
		return path;
	}

	/**
	 * <p>Ensure the {@link Path} is not {@code null}, exists and is a directory.</p>
	 * @param name the name of the {@link Path}
	 * @param path the {@link Path} to validate
	 * @param <P> the {@link Path} type
	 * @return the validated {@link Path}
	 * @throws NullPointerException if the name or the {@link Path} is {@code null}
	 * @throws IllegalArgumentException if the {@link Path} does not exist or is not a directory
	 * @since 1.3.0
	 */
	public static <P extends Path> P notNullAndDirectory(final String name, final P path) {
		notNull(name, path);
		if (!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(path) + " (existing directory expected)");
		}
		return path;
	}

	/**
	 * <p>Ensure the {@code boolean} value is equal to the other one.</p>
	 * @param name the name of the {@code boolean} value
	 * @param value the {@code boolean} value to validate
	 * @param other the other {@code boolean} value
	 * @return the validated {@code boolean} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} value is not equal to the other one
	 * @since 1.3.0
	 */
	public static boolean equalTo(final String name, final boolean value, final boolean other) {
		notNull("name", name);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code byte} value is equal to the other one.</p>
	 * @param name the name of the {@code byte} value
	 * @param value the {@code byte} value to validate
	 * @param other the other {@code byte} value
	 * @return the validated {@code byte} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} value is not equal to the other one
	 * @since 1.3.0
	 */
	public static byte equalTo(final String name, final byte value, final byte other) {
		notNull("name", name);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code short} value is equal to the other one.</p>
	 * @param name the name of the {@code short} value
	 * @param value the {@code short} value to validate
	 * @param other the other {@code short} value
	 * @return the validated {@code short} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code short} value is not equal to the other one
	 * @since 1.3.0
	 */
	public static short equalTo(final String name, final short value, final short other) {
		notNull("name", name);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code char} value is equal to the other one.</p>
	 * @param name the name of the {@code char} value
	 * @param value the {@code char} value to validate
	 * @param other the other {@code char} value
	 * @return the validated {@code char} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code char} value is not equal to the other one
	 * @since 1.3.0
	 */
	public static char equalTo(final String name, final char value, final char other) {
		notNull("name", name);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code int} value is equal to the other one.</p>
	 * @param name the name of the {@code int} value
	 * @param value the {@code int} value to validate
	 * @param other the other {@code int} value
	 * @return the validated {@code int} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code int} value is not equal to the other one
	 * @since 1.3.0
	 */
	public static int equalTo(final String name, final int value, final int other) {
		notNull("name", name);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code long} value is equal to the other one.</p>
	 * @param name the name of the {@code long} value
	 * @param value the {@code long} value to validate
	 * @param other the other {@code long} value
	 * @return the validated {@code long} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code long} value is not equal to the other one
	 * @since 1.3.0
	 */
	public static long equalTo(final String name, final long value, final long other) {
		notNull("name", name);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code float} value is equal to the other one.</p>
	 * @param name the name of the {@code float} value
	 * @param value the {@code float} value to validate
	 * @param other the other {@code float} value
	 * @return the validated {@code float} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code float} value is not equal to the other one
	 * @since 1.3.0
	 */
	public static float equalTo(final String name, final float value, final float other) {
		notNull("name", name);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code double} value is equal to the other one.</p>
	 * @param name the name of the {@code double} value
	 * @param value the {@code double} value to validate
	 * @param other the other {@code double} value
	 * @return the validated {@code double} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code double} value is not equal to the other one
	 * @since 1.3.0
	 */
	public static double equalTo(final String name, final double value, final double other) {
		notNull("name", name);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the value is not {@code null} and equal to the other one.</p>
	 * @param name the name of the value
	 * @param value the value to validate
	 * @param other the other value
	 * @param <V> the value type
	 * @return the validated value
	 * @throws NullPointerException if the name, the value or the other one is {@code null}
	 * @throws IllegalArgumentException if the value is not equal to the other one
	 * @since 1.3.1
	 */
	public static <V> V notNullAndEqualTo(final String name, final V value, final Object other) {
		notNull(name, value);
		notNull("other", other);
		if (!Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code boolean} array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the {@code boolean} array
	 * @param array the {@code boolean} array to validate
	 * @param other the other {@code boolean} array
	 * @return the validated {@code boolean} array
	 * @throws NullPointerException if the name, the {@code boolean} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} array is not equal to the other one
	 * @since 1.3.1
	 */
	public static boolean[] notNullAndEqualTo(final String name, final boolean[] array, final boolean[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code byte} array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the {@code byte} array
	 * @param array the {@code byte} array to validate
	 * @param other the other {@code byte} array
	 * @return the validated {@code byte} array
	 * @throws NullPointerException if the name, the {@code byte} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array is not equal to the other one
	 * @since 1.3.1
	 */
	public static byte[] notNullAndEqualTo(final String name, final byte[] array, final byte[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code short} array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the {@code short} array
	 * @param array the {@code short} array to validate
	 * @param other the other {@code short} array
	 * @return the validated {@code short} array
	 * @throws NullPointerException if the name, the {@code short} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code short} array is not equal to the other one
	 * @since 1.3.1
	 */
	public static short[] notNullAndEqualTo(final String name, final short[] array, final short[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code char} array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the {@code char} array
	 * @param array the {@code char} array to validate
	 * @param other the other {@code char} array
	 * @return the validated {@code char} array
	 * @throws NullPointerException if the name, the {@code char} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code char} array is not equal to the other one
	 * @since 1.3.1
	 */
	public static char[] notNullAndEqualTo(final String name, final char[] array, final char[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code int} array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the {@code int} array
	 * @param array the {@code int} array to validate
	 * @param other the other {@code int} array
	 * @return the validated {@code int} array
	 * @throws NullPointerException if the name, the {@code int} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code int} array is not equal to the other one
	 * @since 1.3.1
	 */
	public static int[] notNullAndEqualTo(final String name, final int[] array, final int[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code long} array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the {@code long} array
	 * @param array the {@code long} array to validate
	 * @param other the other {@code long} array
	 * @return the validated {@code long} array
	 * @throws NullPointerException if the name, the {@code long} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code long} array is not equal to the other one
	 * @since 1.3.1
	 */
	public static long[] notNullAndEqualTo(final String name, final long[] array, final long[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code float} array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the {@code float} array
	 * @param array the {@code float} array to validate
	 * @param other the other {@code float} array
	 * @return the validated {@code float} array
	 * @throws NullPointerException if the name, the {@code float} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code float} array is not equal to the other one
	 * @since 1.3.1
	 */
	public static float[] notNullAndEqualTo(final String name, final float[] array, final float[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code double} array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the {@code double} array
	 * @param array the {@code double} array to validate
	 * @param other the other {@code double} array
	 * @return the validated {@code double} array
	 * @throws NullPointerException if the name, the {@code double} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code double} array is not equal to the other one
	 * @since 1.3.1
	 */
	public static double[] notNullAndEqualTo(final String name, final double[] array, final double[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the array is not {@code null} and equal to the other one.</p>
	 * @param name the name of the array
	 * @param array the array to validate
	 * @param other the other array
	 * @param <E> the element type
	 * @return the validated array
	 * @throws NullPointerException if the name, the array or the other one is {@code null}
	 * @throws IllegalArgumentException if the array is not equal to the other one
	 * @since 1.3.1
	 */
	public static <E> E[] notNullAndEqualTo(final String name, final E[] array, final E[] other) {
		notNull(name, array);
		notNull("other", other);
		if (!Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code boolean} value is not equal to the other one.</p>
	 * @param name the name of the {@code boolean} value
	 * @param value the {@code boolean} value to validate
	 * @param other the other {@code boolean} value
	 * @return the validated {@code boolean} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} value is equal to the other one
	 * @since 1.3.1
	 */
	public static boolean notEqualTo(final String name, final boolean value, final boolean other) {
		notNull("name", name);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code byte} value is not equal to the other one.</p>
	 * @param name the name of the {@code byte} value
	 * @param value the {@code byte} value to validate
	 * @param other the other {@code byte} value
	 * @return the validated {@code byte} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} value is equal to the other one
	 * @since 1.3.1
	 */
	public static byte notEqualTo(final String name, final byte value, final byte other) {
		notNull("name", name);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code short} value is not equal to the other one.</p>
	 * @param name the name of the {@code short} value
	 * @param value the {@code short} value to validate
	 * @param other the other {@code short} value
	 * @return the validated {@code short} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code short} value is equal to the other one
	 * @since 1.3.1
	 */
	public static short notEqualTo(final String name, final short value, final short other) {
		notNull("name", name);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code char} value is not equal to the other one.</p>
	 * @param name the name of the {@code char} value
	 * @param value the {@code char} value to validate
	 * @param other the other {@code char} value
	 * @return the validated {@code char} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code char} value is equal to the other one
	 * @since 1.3.1
	 */
	public static char notEqualTo(final String name, final char value, final char other) {
		notNull("name", name);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code int} value is not equal to the other one.</p>
	 * @param name the name of the {@code int} value
	 * @param value the {@code int} value to validate
	 * @param other the other {@code int} value
	 * @return the validated {@code int} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code int} value is equal to the other one
	 * @since 1.3.1
	 */
	public static int notEqualTo(final String name, final int value, final int other) {
		notNull("name", name);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code long} value is not equal to the other one.</p>
	 * @param name the name of the {@code long} value
	 * @param value the {@code long} value to validate
	 * @param other the other {@code long} value
	 * @return the validated {@code long} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code long} value is equal to the other one
	 * @since 1.3.1
	 */
	public static long notEqualTo(final String name, final long value, final long other) {
		notNull("name", name);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code float} value is not equal to the other one.</p>
	 * @param name the name of the {@code float} value
	 * @param value the {@code float} value to validate
	 * @param other the other {@code float} value
	 * @return the validated {@code float} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code float} value is equal to the other one
	 * @since 1.3.1
	 */
	public static float notEqualTo(final String name, final float value, final float other) {
		notNull("name", name);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code double} value is not equal to the other one.</p>
	 * @param name the name of the {@code double} value
	 * @param value the {@code double} value to validate
	 * @param other the other {@code double} value
	 * @return the validated {@code double} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code double} value is equal to the other one
	 * @since 1.3.1
	 */
	public static double notEqualTo(final String name, final double value, final double other) {
		notNull("name", name);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the value is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the value
	 * @param value the value to validate
	 * @param other the other value
	 * @param <V> the value type
	 * @return the validated value
	 * @throws NullPointerException if the name, the value or the other one is {@code null}
	 * @throws IllegalArgumentException if the value is equal to the other one
	 * @since 1.3.1
	 */
	public static <V> V notNullAndNotEqualTo(final String name, final V value, final Object other) {
		notNull(name, value);
		notNull("other", other);
		if (Equals.equals(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code boolean} array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the {@code boolean} array
	 * @param array the {@code boolean} array to validate
	 * @param other the other {@code boolean} array
	 * @return the validated {@code boolean} array
	 * @throws NullPointerException if the name, the {@code boolean} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code boolean} array is equal to the other one
	 * @since 1.3.1
	 */
	public static boolean[] notNullAndNotEqualTo(final String name, final boolean[] array, final boolean[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code byte} array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the {@code byte} array
	 * @param array the {@code byte} array to validate
	 * @param other the other {@code byte} array
	 * @return the validated {@code byte} array
	 * @throws NullPointerException if the name, the {@code byte} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} array is equal to the other one
	 * @since 1.3.1
	 */
	public static byte[] notNullAndNotEqualTo(final String name, final byte[] array, final byte[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code short} array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the {@code short} array
	 * @param array the {@code short} array to validate
	 * @param other the other {@code short} array
	 * @return the validated {@code short} array
	 * @throws NullPointerException if the name, the {@code short} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code short} array is equal to the other one
	 * @since 1.3.1
	 */
	public static short[] notNullAndNotEqualTo(final String name, final short[] array, final short[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code char} array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the {@code char} array
	 * @param array the {@code char} array to validate
	 * @param other the other {@code char} array
	 * @return the validated {@code char} array
	 * @throws NullPointerException if the name, the {@code char} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code char} array is equal to the other one
	 * @since 1.3.1
	 */
	public static char[] notNullAndNotEqualTo(final String name, final char[] array, final char[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code int} array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the {@code int} array
	 * @param array the {@code int} array to validate
	 * @param other the other {@code int} array
	 * @return the validated {@code int} array
	 * @throws NullPointerException if the name, the {@code int} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code int} array is equal to the other one
	 * @since 1.3.1
	 */
	public static int[] notNullAndNotEqualTo(final String name, final int[] array, final int[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code long} array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the {@code long} array
	 * @param array the {@code long} array to validate
	 * @param other the other {@code long} array
	 * @return the validated {@code long} array
	 * @throws NullPointerException if the name, the {@code long} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code long} array is equal to the other one
	 * @since 1.3.1
	 */
	public static long[] notNullAndNotEqualTo(final String name, final long[] array, final long[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code float} array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the {@code float} array
	 * @param array the {@code float} array to validate
	 * @param other the other {@code float} array
	 * @return the validated {@code float} array
	 * @throws NullPointerException if the name, the {@code float} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code float} array is equal to the other one
	 * @since 1.3.1
	 */
	public static float[] notNullAndNotEqualTo(final String name, final float[] array, final float[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code double} array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the {@code double} array
	 * @param array the {@code double} array to validate
	 * @param other the other {@code double} array
	 * @return the validated {@code double} array
	 * @throws NullPointerException if the name, the {@code double} array or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@code double} array is equal to the other one
	 * @since 1.3.1
	 */
	public static double[] notNullAndNotEqualTo(final String name, final double[] array, final double[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the array is not {@code null} and not equal to the other one.</p>
	 * @param name the name of the array
	 * @param array the array to validate
	 * @param other the other array
	 * @param <E> the element type
	 * @return the validated array
	 * @throws NullPointerException if the name, the array or the other one is {@code null}
	 * @throws IllegalArgumentException if the array is equal to the other one
	 * @since 1.3.1
	 */
	public static <E> E[] notNullAndNotEqualTo(final String name, final E[] array, final E[] other) {
		notNull(name, array);
		notNull("other", other);
		if (Equals.equals(array, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(array) + " (not equal to " + ToString.toString(other) + " expected)");
		}
		return array;
	}

	/**
	 * <p>Ensure the {@code byte} value is lower than the other one.</p>
	 * @param name the name of the {@code byte} value
	 * @param value the {@code byte} value to validate
	 * @param other the other {@code byte} value
	 * @return the validated {@code byte} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} value is greater than or equal to the other one
	 * @since 1.3.0
	 */
	public static byte lowerThan(final String name, final byte value, final byte other) {
		notNull("name", name);
		if (other <= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code short} value is lower than the other one.</p>
	 * @param name the name of the {@code short} value
	 * @param value the {@code short} value to validate
	 * @param other the other {@code short} value
	 * @return the validated {@code short} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code short} value is greater than or equal to the other one
	 * @since 1.3.0
	 */
	public static short lowerThan(final String name, final short value, final short other) {
		notNull("name", name);
		if (other <= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code char} value is lower than the other one.</p>
	 * @param name the name of the {@code char} value
	 * @param value the {@code char} value to validate
	 * @param other the other {@code char} value
	 * @return the validated {@code char} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code char} value is greater than or equal to the other one
	 * @since 1.3.0
	 */
	public static char lowerThan(final String name, final char value, final char other) {
		notNull("name", name);
		if (other <= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code int} value is lower than the other one.</p>
	 * @param name the name of the {@code int} value
	 * @param value the {@code int} value to validate
	 * @param other the other {@code int} value
	 * @return the validated {@code int} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code int} value is greater than or equal to the other one
	 * @since 1.3.0
	 */
	public static int lowerThan(final String name, final int value, final int other) {
		notNull("name", name);
		if (other <= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code long} value is lower than the other one.</p>
	 * @param name the name of the {@code long} value
	 * @param value the {@code long} value to validate
	 * @param other the other {@code long} value
	 * @return the validated {@code long} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code long} value is greater than or equal to the other one
	 * @since 1.3.0
	 */
	public static long lowerThan(final String name, final long value, final long other) {
		notNull("name", name);
		if (other <= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code float} value is lower than the other one.</p>
	 * @param name the name of the {@code float} value
	 * @param value the {@code float} value to validate
	 * @param other the other {@code float} value
	 * @return the validated {@code float} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code float} value is greater than or equal to the other one
	 * @since 1.3.0
	 */
	public static float lowerThan(final String name, final float value, final float other) {
		notNull("name", name);
		if (other <= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code double} value is lower than the other one.</p>
	 * @param name the name of the {@code double} value
	 * @param value the {@code double} value to validate
	 * @param other the other {@code double} value
	 * @return the validated {@code double} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code double} value is greater than or equal to the other one
	 * @since 1.3.0
	 */
	public static double lowerThan(final String name, final double value, final double other) {
		notNull("name", name);
		if (other <= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@link Comparable} value is not {@code null} and lower than the other one.</p>
	 * @param name the name of the {@link Comparable} value
	 * @param value the {@link Comparable} value to validate
	 * @param other the other {@link Comparable} value
	 * @param <C> the {@link Comparable} value type
	 * @return the validated {@link Comparable} value
	 * @throws NullPointerException if the name, the {@link Comparable} value or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@link Comparable} value is greater than or equal to the other one
	 * @since 1.3.1
	 */
	public static <C extends Comparable<C>> C notNullAndLowerThan(final String name, final C value, final C other) {
		notNull(name, value);
		notNull("other", other);
		if (!Comparables.isLowerThan(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code byte} value is lower than or equal to the other one.</p>
	 * @param name the name of the {@code byte} value
	 * @param value the {@code byte} value to validate
	 * @param other the other {@code byte} value
	 * @return the validated {@code byte} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} value is greater than the other one
	 * @since 1.3.0
	 */
	public static byte lowerThanOrEqualTo(final String name, final byte value, final byte other) {
		notNull("name", name);
		if (other < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code short} value is lower than or equal to the other one.</p>
	 * @param name the name of the {@code short} value
	 * @param value the {@code short} value to validate
	 * @param other the other {@code short} value
	 * @return the validated {@code short} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code short} value is greater than the other one
	 * @since 1.3.0
	 */
	public static short lowerThanOrEqualTo(final String name, final short value, final short other) {
		notNull("name", name);
		if (other < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code char} value is lower than or equal to the other one.</p>
	 * @param name the name of the {@code char} value
	 * @param value the {@code char} value to validate
	 * @param other the other {@code char} value
	 * @return the validated {@code char} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code char} value is greater than the other one
	 * @since 1.3.0
	 */
	public static char lowerThanOrEqualTo(final String name, final char value, final char other) {
		notNull("name", name);
		if (other < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code int} value is lower than or equal to the other one.</p>
	 * @param name the name of the {@code int} value
	 * @param value the {@code int} value to validate
	 * @param other the other {@code int} value
	 * @return the validated {@code int} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code int} value is greater than the other one
	 * @since 1.3.0
	 */
	public static int lowerThanOrEqualTo(final String name, final int value, final int other) {
		notNull("name", name);
		if (other < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code long} value is lower than or equal to the other one.</p>
	 * @param name the name of the {@code long} value
	 * @param value the {@code long} value to validate
	 * @param other the other {@code long} value
	 * @return the validated {@code long} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code long} value is greater than the other one
	 * @since 1.3.0
	 */
	public static long lowerThanOrEqualTo(final String name, final long value, final long other) {
		notNull("name", name);
		if (other < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code float} value is lower than or equal to the other one.</p>
	 * @param name the name of the {@code float} value
	 * @param value the {@code float} value to validate
	 * @param other the other {@code float} value
	 * @return the validated {@code float} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code float} value is greater than the other one
	 * @since 1.3.0
	 */
	public static float lowerThanOrEqualTo(final String name, final float value, final float other) {
		notNull("name", name);
		if (other < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code double} value is lower than or equal to the other one.</p>
	 * @param name the name of the {@code double} value
	 * @param value the {@code double} value to validate
	 * @param other the other {@code double} value
	 * @return the validated {@code double} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code double} value is greater than the other one
	 * @since 1.3.0
	 */
	public static double lowerThanOrEqualTo(final String name, final double value, final double other) {
		notNull("name", name);
		if (other < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@link Comparable} value is not {@code null} and lower than or equal to the other one.</p>
	 * @param name the name of the {@link Comparable} value
	 * @param value the {@link Comparable} value to validate
	 * @param other the other {@link Comparable} value
	 * @param <C> the {@link Comparable} value type
	 * @return the validated {@link Comparable} value
	 * @throws NullPointerException if the name, the {@link Comparable} value or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@link Comparable} value is greater than the other one
	 * @since 1.3.1
	 */
	public static <C extends Comparable<C>> C notNullAndLowerThanOrEqualTo(final String name, final C value, final C other) {
		notNull(name, value);
		notNull("other", other);
		if (!Comparables.isLowerThanOrEqualTo(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (lower than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code byte} value is greater than the other one.</p>
	 * @param name the name of the {@code byte} value
	 * @param value the {@code byte} value to validate
	 * @param other the other {@code byte} value
	 * @return the validated {@code byte} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} value is lower than or equal to the other one
	 * @since 1.3.0
	 */
	public static byte greaterThan(final String name, final byte value, final byte other) {
		notNull("name", name);
		if (other >= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code short} value is greater than the other one.</p>
	 * @param name the name of the {@code short} value
	 * @param value the {@code short} value to validate
	 * @param other the other {@code short} value
	 * @return the validated {@code short} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code short} value is lower than or equal to the other one
	 * @since 1.3.0
	 */
	public static short greaterThan(final String name, final short value, final short other) {
		notNull("name", name);
		if (other >= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code char} value is greater than the other one.</p>
	 * @param name the name of the {@code char} value
	 * @param value the {@code char} value to validate
	 * @param other the other {@code char} value
	 * @return the validated {@code char} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code char} value is lower than or equal to the other one
	 * @since 1.3.0
	 */
	public static char greaterThan(final String name, final char value, final char other) {
		notNull("name", name);
		if (other >= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code int} value is greater than the other one.</p>
	 * @param name the name of the {@code int} value
	 * @param value the {@code int} value to validate
	 * @param other the other {@code int} value
	 * @return the validated {@code int} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code int} value is lower than or equal to the other one
	 * @since 1.3.0
	 */
	public static int greaterThan(final String name, final int value, final int other) {
		notNull("name", name);
		if (other >= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code long} value is greater than the other one.</p>
	 * @param name the name of the {@code long} value
	 * @param value the {@code long} value to validate
	 * @param other the other {@code long} value
	 * @return the validated {@code long} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code long} value is lower than or equal to the other one
	 * @since 1.3.0
	 */
	public static long greaterThan(final String name, final long value, final long other) {
		notNull("name", name);
		if (other >= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code float} value is greater than the other one.</p>
	 * @param name the name of the {@code float} value
	 * @param value the {@code float} value to validate
	 * @param other the other {@code float} value
	 * @return the validated {@code float} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code float} value is lower than or equal to the other one
	 * @since 1.3.0
	 */
	public static float greaterThan(final String name, final float value, final float other) {
		notNull("name", name);
		if (other >= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code double} value is greater than the other one.</p>
	 * @param name the name of the {@code double} value
	 * @param value the {@code double} value to validate
	 * @param other the other {@code double} value
	 * @return the validated {@code double} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code double} value is lower than or equal to the other one
	 * @since 1.3.0
	 */
	public static double greaterThan(final String name, final double value, final double other) {
		notNull("name", name);
		if (other >= value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@link Comparable} value is not {@code null} and greater than the other one.</p>
	 * @param name the name of the {@link Comparable} value
	 * @param value the {@link Comparable} value to validate
	 * @param other the other {@link Comparable} value
	 * @param <C> the {@link Comparable} value type
	 * @return the validated {@link Comparable} value
	 * @throws NullPointerException if the name, the {@link Comparable} value or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@link Comparable} value is lower than or equal to the other one
	 * @since 1.3.1
	 */
	public static <C extends Comparable<C>> C notNullAndGreaterThan(final String name, final C value, final C other) {
		notNull(name, value);
		notNull("other", other);
		if (!Comparables.isGreaterThan(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code byte} value is greater than or equal to the other one.</p>
	 * @param name the name of the {@code byte} value
	 * @param value the {@code byte} value to validate
	 * @param other the other {@code byte} value
	 * @return the validated {@code byte} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} value is lower than the other one
	 * @since 1.3.0
	 */
	public static byte greaterThanOrEqualTo(final String name, final byte value, final byte other) {
		notNull("name", name);
		if (other > value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code short} value is greater than or equal to the other one.</p>
	 * @param name the name of the {@code short} value
	 * @param value the {@code short} value to validate
	 * @param other the other {@code short} value
	 * @return the validated {@code short} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code short} value is lower than the other one
	 * @since 1.3.0
	 */
	public static short greaterThanOrEqualTo(final String name, final short value, final short other) {
		notNull("name", name);
		if (other > value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code char} value is greater than or equal to the other one.</p>
	 * @param name the name of the {@code char} value
	 * @param value the {@code char} value to validate
	 * @param other the other {@code char} value
	 * @return the validated {@code char} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code char} value is lower than the other one
	 * @since 1.3.0
	 */
	public static char greaterThanOrEqualTo(final String name, final char value, final char other) {
		notNull("name", name);
		if (other > value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code int} value is greater than or equal to the other one.</p>
	 * @param name the name of the {@code int} value
	 * @param value the {@code int} value to validate
	 * @param other the other {@code int} value
	 * @return the validated {@code int} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code int} value is lower than the other one
	 * @since 1.3.0
	 */
	public static int greaterThanOrEqualTo(final String name, final int value, final int other) {
		notNull("name", name);
		if (other > value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code long} value is greater than or equal to the other one.</p>
	 * @param name the name of the {@code long} value
	 * @param value the {@code long} value to validate
	 * @param other the other {@code long} value
	 * @return the validated {@code long} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code long} value is lower than the other one
	 * @since 1.3.0
	 */
	public static long greaterThanOrEqualTo(final String name, final long value, final long other) {
		notNull("name", name);
		if (other > value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code float} value is greater than or equal to the other one.</p>
	 * @param name the name of the {@code float} value
	 * @param value the {@code float} value to validate
	 * @param other the other {@code float} value
	 * @return the validated {@code float} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code float} value is lower than the other one
	 * @since 1.3.0
	 */
	public static float greaterThanOrEqualTo(final String name, final float value, final float other) {
		notNull("name", name);
		if (other > value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code double} value is greater than or equal to the other one.</p>
	 * @param name the name of the {@code double} value
	 * @param value the {@code double} value to validate
	 * @param other the other {@code double} value
	 * @return the validated {@code double} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code double} value is lower than the other one
	 * @since 1.3.0
	 */
	public static double greaterThanOrEqualTo(final String name, final double value, final double other) {
		notNull("name", name);
		if (other > value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@link Comparable} value is not {@code null} and greater than or equal to the other one.</p>
	 * @param name the name of the {@link Comparable} value
	 * @param value the {@link Comparable} value to validate
	 * @param other the other {@link Comparable} value
	 * @param <C> the {@link Comparable} value type
	 * @return the validated {@link Comparable} value
	 * @throws NullPointerException if the name, the {@link Comparable} value or the other one is {@code null}
	 * @throws IllegalArgumentException if the {@link Comparable} value is lower than the other one
	 * @since 1.3.1
	 */
	public static <C extends Comparable<C>> C notNullAndGreaterThanOrEqualTo(final String name, final C value, final C other) {
		notNull(name, value);
		notNull("other", other);
		if (!Comparables.isGreaterThanOrEqualTo(value, other)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (greater than or equal to " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code byte} value is between both other ones.</p>
	 * @param name the name of the {@code byte} value
	 * @param value the {@code byte} value to validate
	 * @param from the from {@code byte} value
	 * @param to the to {@code byte} value
	 * @return the validated {@code byte} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} value is not between both other ones
	 * @since 1.3.0
	 */
	public static byte between(final String name, final byte value, final byte from, final byte to) {
		notNull("name", name);
		if (from > value || to < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (between " + ToString.toString(from) + " and " + ToString.toString(to) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code short} value is between both other ones.</p>
	 * @param name the name of the {@code short} value
	 * @param value the {@code short} value to validate
	 * @param from the from {@code short} value
	 * @param to the to {@code short} value
	 * @return the validated {@code short} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code short} value is not between both other ones
	 * @since 1.3.0
	 */
	public static short between(final String name, final short value, final short from, final short to) {
		notNull("name", name);
		if (from > value || to < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (between " + ToString.toString(from) + " and " + ToString.toString(to) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code char} value is between both other ones.</p>
	 * @param name the name of the {@code char} value
	 * @param value the {@code char} value to validate
	 * @param from the from {@code char} value
	 * @param to the to {@code char} value
	 * @return the validated {@code char} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code char} value is not between both other ones
	 * @since 1.3.0
	 */
	public static char between(final String name, final char value, final char from, final char to) {
		notNull("name", name);
		if (from > value || to < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (between " + ToString.toString(from) + " and " + ToString.toString(to) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code int} value is between both other ones.</p>
	 * @param name the name of the {@code int} value
	 * @param value the {@code int} value to validate
	 * @param from the from {@code int} value
	 * @param to the to {@code int} value
	 * @return the validated {@code int} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code int} value is not between both other ones
	 * @since 1.3.0
	 */
	public static int between(final String name, final int value, final int from, final int to) {
		notNull("name", name);
		if (from > value || to < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (between " + ToString.toString(from) + " and " + ToString.toString(to) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code long} value is between both other ones.</p>
	 * @param name the name of the {@code long} value
	 * @param value the {@code long} value to validate
	 * @param from the from {@code long} value
	 * @param to the to {@code long} value
	 * @return the validated {@code long} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code long} value is not between both other ones
	 * @since 1.3.0
	 */
	public static long between(final String name, final long value, final long from, final long to) {
		notNull("name", name);
		if (from > value || to < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (between " + ToString.toString(from) + " and " + ToString.toString(to) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code float} value is between both other ones.</p>
	 * @param name the name of the {@code float} value
	 * @param value the {@code float} value to validate
	 * @param from the from {@code float} value
	 * @param to the to {@code float} value
	 * @return the validated {@code float} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code float} value is not between both other ones
	 * @since 1.3.0
	 */
	public static float between(final String name, final float value, final float from, final float to) {
		notNull("name", name);
		if (from > value || to < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (between " + ToString.toString(from) + " and " + ToString.toString(to) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code double} value is between both other ones.</p>
	 * @param name the name of the {@code double} value
	 * @param value the {@code double} value to validate
	 * @param from the from {@code double} value
	 * @param to the to {@code double} value
	 * @return the validated {@code double} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code double} value is not between both other ones
	 * @since 1.3.0
	 */
	public static double between(final String name, final double value, final double from, final double to) {
		notNull("name", name);
		if (from > value || to < value) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (between " + ToString.toString(from) + " and " + ToString.toString(to) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@link Comparable} value is not {@code null} and between both other ones.</p>
	 * @param name the name of the {@link Comparable} value
	 * @param value the {@link Comparable} value to validate
	 * @param from the from {@link Comparable} value
	 * @param to the to {@link Comparable} value
	 * @param <C> the {@link Comparable} value type
	 * @return the validated {@link Comparable} value
	 * @throws NullPointerException if the name, the {@link Comparable} value, or any of both other ones is {@code null}
	 * @throws IllegalArgumentException if the {@link Comparable} value is not between both other ones
	 * @since 1.3.1
	 */
	public static <C extends Comparable<C>> C notNullAndBetween(final String name, final C value, final C from, final C to) {
		notNull(name, value);
		notNull("from", from);
		notNull("to", to);
		if (!Comparables.isBetween(value, from, to)) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (between " + ToString.toString(from) + " and " + ToString.toString(to) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code byte} value is a multiple of the other one.</p>
	 * @param name the name of the {@code byte} value
	 * @param value the {@code byte} value to validate
	 * @param other the other {@code byte} value
	 * @return the validated {@code byte} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code byte} value is not a multiple of the other one
	 * @since 1.5.0
	 */
	public static byte multipleOf(final String name, final byte value, final byte other) {
		notNull("name", name);
		if (0 != value % other) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (multiple of " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code short} value is a multiple of the other one.</p>
	 * @param name the name of the {@code short} value
	 * @param value the {@code short} value to validate
	 * @param other the other {@code short} value
	 * @return the validated {@code short} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code short} value is not a multiple of the other one
	 * @since 1.5.0
	 */
	public static short multipleOf(final String name, final short value, final short other) {
		notNull("name", name);
		if (0 != value % other) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (multiple of " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code char} value is a multiple of the other one.</p>
	 * @param name the name of the {@code char} value
	 * @param value the {@code char} value to validate
	 * @param other the other {@code char} value
	 * @return the validated {@code char} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code char} value is not a multiple of the other one
	 * @since 1.5.0
	 */
	public static char multipleOf(final String name, final char value, final char other) {
		notNull("name", name);
		if (0 != value % other) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (multiple of " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code int} value is a multiple of the other one.</p>
	 * @param name the name of the {@code int} value
	 * @param value the {@code int} value to validate
	 * @param other the other {@code int} value
	 * @return the validated {@code int} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code int} value is not a multiple of the other one
	 * @since 1.5.0
	 */
	public static int multipleOf(final String name, final int value, final int other) {
		notNull("name", name);
		if (0 != value % other) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (multiple of " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code long} value is a multiple of the other one.</p>
	 * @param name the name of the {@code long} value
	 * @param value the {@code long} value to validate
	 * @param other the other {@code long} value
	 * @return the validated {@code long} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code long} value is not a multiple of the other one
	 * @since 1.5.0
	 */
	public static long multipleOf(final String name, final long value, final long other) {
		notNull("name", name);
		if (0 != value % other) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (multiple of " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code float} value is a multiple of the other one.</p>
	 * @param name the name of the {@code float} value
	 * @param value the {@code float} value to validate
	 * @param other the other {@code float} value
	 * @return the validated {@code float} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code float} value is not a multiple of the other one
	 * @since 1.5.0
	 */
	public static float multipleOf(final String name, final float value, final float other) {
		notNull("name", name);
		if (0 != value % other) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (multiple of " + ToString.toString(other) + " expected)");
		}
		return value;
	}

	/**
	 * <p>Ensure the {@code double} value is a multiple of the other one.</p>
	 * @param name the name of the {@code double} value
	 * @param value the {@code double} value to validate
	 * @param other the other {@code double} value
	 * @return the validated {@code double} value
	 * @throws NullPointerException if the name is {@code null}
	 * @throws IllegalArgumentException if the {@code double} value is not a multiple of the other one
	 * @since 1.5.0
	 */
	public static double multipleOf(final String name, final double value, final double other) {
		notNull("name", name);
		if (0 != value % other) {
			throw new IllegalArgumentException("Invalid " + name + ": " + ToString.toString(value) + " (multiple of " + ToString.toString(other) + " expected)");
		}
		return value;
	}
}