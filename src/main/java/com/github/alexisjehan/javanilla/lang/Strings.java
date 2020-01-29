/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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
package com.github.alexisjehan.javanilla.lang;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>An utility class that provides {@link String} and {@link CharSequence} tools.</p>
 * @since 1.0.0
 */
public final class Strings {

	/**
	 * <p>An empty {@link String}.</p>
	 * @since 1.0.0
	 */
	public static final String EMPTY = "";

	/**
	 * <p>Default {@code char} quote {@code char}.</p>
	 * @since 1.2.0
	 */
	private static final char DEFAULT_CHAR_QUOTE = '\'';

	/**
	 * <p>Default {@link CharSequence} quote {@code char}.</p>
	 * @since 1.1.0
	 */
	private static final char DEFAULT_CHAR_SEQUENCE_QUOTE = '"';

	/**
	 * <p>Default escape {@code char}.</p>
	 * @since 1.1.0
	 */
	private static final char DEFAULT_ESCAPE = '\\';

	/**
	 * <p>Default padding {@code char}.</p>
	 * @since 1.2.0
	 */
	private static final char DEFAULT_PADDING = ' ';

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Strings() {
		// Not available
	}

	/**
	 * <p>Wrap a {@link CharSequence} replacing {@code null} by an empty one.</p>
	 * @param charSequence the {@link CharSequence} or {@code null}
	 * @return a non-{@code null} {@link CharSequence}
	 * @since 1.0.0
	 */
	public static CharSequence nullToEmpty(final CharSequence charSequence) {
		return nullToDefault(charSequence, EMPTY);
	}

	/**
	 * <p>Wrap a {@link String} replacing {@code null} by an empty one.</p>
	 * @param string the {@link String} or {@code null}
	 * @return a non-{@code null} {@link String}
	 * @since 1.2.0
	 */
	public static String nullToEmpty(final String string) {
		return nullToDefault(string, EMPTY);
	}

	/**
	 * <p>Wrap a {@link CharSequence} replacing {@code null} by a default one.</p>
	 * @param charSequence the {@link CharSequence} or {@code null}
	 * @param defaultCharSequence the default {@link CharSequence}
	 * @param <C> the {@link CharSequence} type
	 * @return a non-{@code null} {@link CharSequence}
	 * @throws NullPointerException if the default {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static <C extends CharSequence> C nullToDefault(final C charSequence, final C defaultCharSequence) {
		Ensure.notNull("defaultCharSequence", defaultCharSequence);
		return null != charSequence ? charSequence : defaultCharSequence;
	}

	/**
	 * <p>Wrap a {@link CharSequence} replacing an empty one by {@code null}.</p>
	 * @param charSequence the {@link CharSequence} or {@code null}
	 * @param <C> the {@link CharSequence} type
	 * @return a non-empty {@link CharSequence} or {@code null}
	 * @since 1.0.0
	 */
	public static <C extends CharSequence> C emptyToNull(final C charSequence) {
		return emptyToDefault(charSequence, null);
	}

	/**
	 * <p>Wrap a {@link CharSequence} replacing an empty one by a default {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} or {@code null}
	 * @param defaultCharSequence the default {@link CharSequence} or {@code null}
	 * @param <C> the {@link CharSequence} type
	 * @return a non-empty {@link CharSequence} or {@code null}
	 * @throws IllegalArgumentException if the default {@link CharSequence} is empty
	 * @since 1.1.0
	 */
	public static <C extends CharSequence> C emptyToDefault(final C charSequence, final C defaultCharSequence) {
		if (null != defaultCharSequence) {
			Ensure.notNullAndNotEmpty("defaultCharSequence", defaultCharSequence);
		}
		return null == charSequence || !isEmpty(charSequence) ? charSequence : defaultCharSequence;
	}

	/**
	 * <p>Wrap a {@link CharSequence} replacing a blank one by an empty {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} or {@code null}
	 * @return a non-blank {@link CharSequence} or {@code null}
	 * @since 1.0.0
	 */
	public static CharSequence blankToEmpty(final CharSequence charSequence) {
		return blankToDefault(charSequence, EMPTY);
	}

	/**
	 * <p>Wrap a {@link String} replacing a blank one by an empty {@link String}.</p>
	 * @param string the {@link String} or {@code null}
	 * @return a non-blank {@link String} or {@code null}
	 * @since 1.2.0
	 */
	public static String blankToEmpty(final String string) {
		return blankToDefault(string, EMPTY);
	}

	/**
	 * <p>Wrap a {@link CharSequence} replacing a blank one by {@code null}.</p>
	 * @param charSequence the {@link CharSequence} or {@code null}
	 * @param <C> the {@link CharSequence} type
	 * @return a non-blank {@link CharSequence} or {@code null}
	 * @since 1.0.0
	 */
	public static <C extends CharSequence> C blankToNull(final C charSequence) {
		return blankToDefault(charSequence, null);
	}

	/**
	 * <p>Wrap a {@link CharSequence} replacing a blank one by a default {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} or {@code null}
	 * @param defaultCharSequence the default {@link CharSequence} or {@code null}
	 * @param <C> the {@link CharSequence} type
	 * @return a non-blank {@link CharSequence} or {@code null}
	 * @throws IllegalArgumentException if the default {@link CharSequence} is blank
	 * @since 1.1.0
	 */
	public static <C extends CharSequence> C blankToDefault(final C charSequence, final C defaultCharSequence) {
		if (null != defaultCharSequence) {
			Ensure.notNullAndNotBlank("defaultCharSequence", defaultCharSequence);
		}
		return null == charSequence || !isBlank(charSequence) ? charSequence : defaultCharSequence;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is empty.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is empty
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isEmpty(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		return 0 == charSequence.length();
	}

	/**
	 * <p>Tell if a {@link CharSequence} is blank.</p>
	 * <p><b>Note</b>: A {@code char} is blank or not based on {@link Character#isWhitespace(char)}.</p>
	 * <p><b>Note</b>: An empty {@link CharSequence} is not considered as blank.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is blank
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 * @deprecated since Java 11, use {@code String#isBlank()} instead
	 */
	@Deprecated(since = "1.4.0")
	public static boolean isBlank(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		for (var i = 0; i < length; ++i) {
			if (!Character.isWhitespace(charSequence.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a {@code boolean} representation compatible with
	 * {@link Boolean#toString(boolean)}.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is a {@code boolean} representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isBoolean(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		return "true".contentEquals(charSequence) || "false".contentEquals(charSequence);
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a {@code short} representation compatible with
	 * {@link Short#toString(short)}.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is a {@code short} representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isShort(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		if (0 == charSequence.length()) {
			return false;
		}
		try {
			Short.parseShort(charSequence.toString());
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is an {@code int} representation compatible with
	 * {@link Integer#toString(int)}.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is an {@code int} representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isInt(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		if (0 == charSequence.length()) {
			return false;
		}
		try {
			Integer.parseInt(charSequence, 0, charSequence.length(), 10);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a {@code long} representation compatible with
	 * {@link Long#toString(long)}.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is a {@code long} representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isLong(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		if (0 == charSequence.length()) {
			return false;
		}
		try {
			Long.parseLong(charSequence, 0, charSequence.length(), 10);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a {@code float} representation compatible with
	 * {@link Float#toString(float)}.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is a {@code float} representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isFloat(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		if (0 == charSequence.length()) {
			return false;
		}
		try {
			Float.parseFloat(charSequence.toString());
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a {@code double} representation compatible with
	 * {@link Double#toString(double)}.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is a {@code double} representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isDouble(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		if (0 == charSequence.length()) {
			return false;
		}
		try {
			Double.parseDouble(charSequence.toString());
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a binary representation.</p>
	 * <p><b>Note</b>: Whitespaces are not allowed.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is a binary representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isBinary(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if ('0' != c && '1' != c) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is an octal representation.</p>
	 * <p><b>Note</b>: Whitespaces are not allowed.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is an octal representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isOctal(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if ('0' > c || '7' < c) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a decimal representation.</p>
	 * <p><b>Note</b>: Whitespaces are not allowed.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is a decimal representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.2.0
	 */
	public static boolean isDecimal(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if ('0' > c || '9' < c) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a hexadecimal representation.</p>
	 * <p><b>Note</b>: Whitespaces are not allowed.</p>
	 * <p><b>Note</b>: The case does not matter.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @return {@code true} if the {@link CharSequence} is a hexadecimal representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean isHexadecimal(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if (('a' > c || 'f' < c) && ('A' > c || 'F' < c) && ('0' > c || '9' < c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a base 64 representation with or without padding.</p>
	 * <p><b>Note</b>: Whitespaces are not allowed.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @param withPadding {@code true} if the base 64 representation must have padding
	 * @return {@code true} if the {@link CharSequence} is a base 64 representation
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean isBase64(final CharSequence charSequence, final boolean withPadding) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		var padding = false;
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if (('a' > c || 'z' < c) && ('A' > c || 'Z' < c) && ('0' > c || '9' < c) && '+' != c && '/' != c) {
				if (!withPadding || '=' != c || i < length - 2) {
					return false;
				} else if (!padding) {
					padding = true;
				}
			} else if (padding) {
				return false;
			}
		}
		return !withPadding || 0 == length % 4;
	}

	/**
	 * <p>Tell if a {@link CharSequence} is a base 64 representation for URLs and filenames with or without padding.</p>
	 * <p><b>Note</b>: Whitespaces are not allowed.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @param withPadding {@code true} if the base 64 representation for URLs and filenames must have padding
	 * @return {@code true} if the {@link CharSequence} is a base 64 representation for URLs and filenames
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean isBase64Url(final CharSequence charSequence, final boolean withPadding) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		var padding = false;
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if (('a' > c || 'z' < c) && ('A' > c || 'Z' < c) && ('0' > c || '9' < c) && '-' != c && '_' != c) {
				if (!withPadding || '=' != c || i < length - 2) {
					return false;
				} else if (!padding) {
					padding = true;
				}
			} else if (padding) {
				return false;
			}
		}
		return !withPadding || 0 == length % 4;
	}

	/**
	 * <p>Test if a {@link CharSequence} contains a target {@link CharSequence} ignoring the case.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @param target the target {@link CharSequence}
	 * @return {@code true} if the {@link CharSequence} contains the target {@link CharSequence} ignoring the case
	 * @throws NullPointerException if the {@link CharSequence} or the target {@link CharSequence} is {@code null}
	 * @since 1.3.0
	 */
	public static boolean containsIgnoreCase(final CharSequence charSequence, final CharSequence target) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("target", target);
		final var targetLength = target.length();
		if (0 == targetLength) {
			return true;
		}
		final var length = charSequence.length();
		if (length < targetLength) {
			return false;
		}
		for (var i = 0; i < length - targetLength + 1; ++i) {
			if (Character.toLowerCase(charSequence.charAt(i)) == Character.toLowerCase(target.charAt(0))) {
				var j = 1;
				while (i + j < length && j < targetLength && Character.toLowerCase(charSequence.charAt(i + j)) == Character.toLowerCase(target.charAt(j))) {
					++j;
				}
				if (j == targetLength) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>Test if a {@link CharSequence} starts with a prefix {@link CharSequence} ignoring the case.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @param prefix the prefix {@link CharSequence}
	 * @return {@code true} if the {@link CharSequence} starts with the prefix {@link CharSequence} ignoring the case
	 * @throws NullPointerException if the {@link CharSequence} or the prefix {@link CharSequence} is {@code null}
	 * @since 1.3.0
	 */
	public static boolean startsWithIgnoreCase(final CharSequence charSequence, final CharSequence prefix) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("prefix", prefix);
		final var prefixLength = prefix.length();
		if (0 == prefixLength) {
			return true;
		}
		final var length = charSequence.length();
		if (length < prefixLength) {
			return false;
		}
		for (var i = 0; i < prefixLength; ++i) {
			if (Character.toUpperCase(charSequence.charAt(i)) != Character.toUpperCase(prefix.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Test if a {@link CharSequence} ends with a suffix {@link CharSequence} ignoring the case.</p>
	 * @param charSequence the {@link CharSequence} to test
	 * @param suffix the suffix {@link CharSequence}
	 * @return {@code true} if the {@link CharSequence} ends with the suffix {@link CharSequence} ignoring the case
	 * @throws NullPointerException if the {@link CharSequence} or the suffix {@link CharSequence} is {@code null}
	 * @since 1.3.0
	 */
	public static boolean endsWithIgnoreCase(final CharSequence charSequence, final CharSequence suffix) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("suffix", suffix);
		final var suffixLength = suffix.length();
		if (0 == suffixLength) {
			return true;
		}
		final var length = charSequence.length();
		if (length < suffixLength) {
			return false;
		}
		final var offset = length - suffixLength;
		for (var i = 0; i < suffixLength; ++i) {
			if (Character.toUpperCase(charSequence.charAt(offset + i)) != Character.toUpperCase(suffix.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Calculate the number of occurrences of the target {@code char} in the {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to iterate
	 * @param target the target {@code char} of the frequency to calculate
	 * @return the frequency of the {@code char}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.3.0
	 */
	public static int frequency(final CharSequence charSequence, final char target) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return 0;
		}
		var frequency = 0;
		for (var i = 0; i < length; ++i) {
			if (target == charSequence.charAt(i)) {
				++frequency;
			}
		}
		return frequency;
	}

	/**
	 * <p>Calculate the number of occurrences of the target {@link CharSequence} in the {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to iterate
	 * @param target the target {@link CharSequence} of the frequency to calculate
	 * @return the frequency of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} or the target {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the target {@link CharSequence} is empty
	 * @since 1.3.0
	 */
	public static int frequency(final CharSequence charSequence, final CharSequence target) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNullAndNotEmpty("target", target);
		final var length = charSequence.length();
		final var targetLength = target.length();
		if (length < targetLength) {
			return 0;
		}
		var frequency = 0;
		for (var i = 0; i < length - targetLength + 1; ++i) {
			if (charSequence.charAt(i) == target.charAt(0)) {
				var j = 1;
				while (i + j < length && j < targetLength && charSequence.charAt(i + j) == target.charAt(j)) {
					++j;
				}
				if (j == targetLength) {
					++frequency;
				}
			}
		}
		return frequency;
	}

	/**
	 * <p>Capitalize a {@link CharSequence} so that the first {@code char} is in uppercase and the others in
	 * lowercase.</p>
	 * @param charSequence the {@link CharSequence} to capitalize
	 * @return a capitalized {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.5.0
	 */
	public static String capitalize(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (0 == length) {
			return EMPTY;
		}
		if (1 == length) {
			return Character.toString(Character.toUpperCase(charSequence.charAt(0)));
		}
		return Character.toUpperCase(charSequence.charAt(0)) + charSequence.subSequence(1, length).toString().toLowerCase();
	}

	/**
	 * <p>Quote a {@code char} using default quote and escape {@code char}s.</p>
	 * <p><b>Note</b>: If the {@code char} is whether the quote or the escape {@code char} it is escaped.</p>
	 * @param c the {@code char} to quote
	 * @return a quoted {@link String} of the {@code char}
	 * @since 1.2.0
	 */
	public static String quote(final char c) {
		return quote(c, DEFAULT_CHAR_QUOTE, DEFAULT_ESCAPE);
	}

	/**
	 * <p>Quote a {@code char} using custom quote and escape {@code char}s.</p>
	 * <p><b>Note</b>: If the {@code char} is whether the quote or the escape {@code char} it is escaped.</p>
	 * @param c the {@code char} to quote
	 * @param quote the quote {@code char}
	 * @param escape the escape {@code char}
	 * @return a quoted {@link String} of the {@code char}
	 * @since 1.2.0
	 */
	public static String quote(final char c, final char quote, final char escape) {
		if (quote == c || escape == c) {
			return of(quote, escape, c, quote);
		}
		return of(quote, c, quote);
	}

	/**
	 * <p>Quote a {@link CharSequence} using default quote and escape {@code char}s.</p>
	 * <p><b>Note</b>: If any {@code char} is whether the quote or the escape {@code char} it is escaped.</p>
	 * @param charSequence the {@link CharSequence} to quote
	 * @return a quoted {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.1.0
	 */
	public static String quote(final CharSequence charSequence) {
		return quote(charSequence, DEFAULT_CHAR_SEQUENCE_QUOTE, DEFAULT_ESCAPE);
	}

	/**
	 * <p>Quote a {@link CharSequence} using custom quote and escape {@code char}s.</p>
	 * <p><b>Note</b>: If any {@code char} is whether the quote or the escape {@code char} it is escaped.</p>
	 * @param charSequence the {@link CharSequence} to quote
	 * @param quote the quote {@code char}
	 * @param escape the escape {@code char}
	 * @return a quoted {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.1.0
	 */
	public static String quote(final CharSequence charSequence, final char quote, final char escape) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		final var builder = new StringBuilder(length + 2);
		builder.append(quote);
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if (quote == c || escape == c) {
				builder.append(escape);
			}
			builder.append(c);
		}
		builder.append(quote);
		return builder.toString();
	}

	/**
	 * <p>Unquote a {@code char} using default quote and escape {@code char}s.</p>
	 * @param charSequence the {@link CharSequence} of the quoted {@code char}
	 * @return an unquoted {@code char}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@link CharSequence} is not a quoted {@code char}
	 * @since 1.2.0
	 */
	public static char unquoteChar(final CharSequence charSequence) {
		return unquoteChar(charSequence, DEFAULT_CHAR_QUOTE, DEFAULT_ESCAPE);
	}

	/**
	 * <p>Unquote a {@code char} using custom quote and escape {@code char}s.</p>
	 * @param charSequence the {@link CharSequence} of the quoted {@code char}
	 * @param quote the quote {@code char}
	 * @param escape the escape {@code char}
	 * @return an unquoted {@code char}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@link CharSequence} is not a quoted {@code char}
	 * @since 1.2.0
	 */
	public static char unquoteChar(final CharSequence charSequence, final char quote, final char escape) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		Ensure.between("charSequence length", length, 3, 4);
		Ensure.equalTo("charSequence first char", charSequence.charAt(0), quote);
		Ensure.equalTo("charSequence last char", charSequence.charAt(length - 1), quote);
		if (4 == length) {
			Ensure.equalTo("charSequence escape char", charSequence.charAt(1), escape);
			return charSequence.charAt(2);
		}
		return charSequence.charAt(1);
	}

	/**
	 * <p>Unquote a {@link CharSequence} using default quote and escape {@code char}s.</p>
	 * @param charSequence the {@link CharSequence} of the quoted {@link CharSequence}
	 * @return an unquoted {@link String}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@link CharSequence} is not a quoted {@link CharSequence}
	 * @since 1.1.0
	 */
	public static String unquote(final CharSequence charSequence) {
		return unquote(charSequence, DEFAULT_CHAR_SEQUENCE_QUOTE, DEFAULT_ESCAPE);
	}

	/**
	 * <p>Unquote a {@link CharSequence} using custom quote and escape {@code char}s.</p>
	 * @param charSequence the {@link CharSequence} of the quoted {@link CharSequence}
	 * @param quote the quote {@code char}
	 * @param escape the escape {@code char}
	 * @return an unquoted {@link String}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@link CharSequence} is not a quoted {@link CharSequence}
	 * @since 1.1.0
	 */
	public static String unquote(final CharSequence charSequence, final char quote, final char escape) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		Ensure.greaterThanOrEqualTo("charSequence length", length, 2);
		Ensure.equalTo("charSequence first char", charSequence.charAt(0), quote);
		Ensure.equalTo("charSequence last char", charSequence.charAt(length - 1), quote);
		final var builder = new StringBuilder(length - 2);
		var escaped = false;
		for (var i = 1; i < length - 1; ++i) {
			final var c = charSequence.charAt(i);
			if (!escaped && escape == c) {
				escaped = true;
			} else {
				builder.append(c);
				escaped = false;
			}
		}
		return builder.toString();
	}

	/**
	 * <p>Split a {@link CharSequence} using a {@code char} separator.</p>
	 * @param separator the {@code char} separator
	 * @param charSequence the {@link CharSequence} to split
	 * @return a {@link List} of splitted {@link String}s
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.3.1
	 */
	public static List<String> split(final char separator, final CharSequence charSequence) {
		return split(separator, charSequence, Integer.MAX_VALUE);
	}

	/**
	 * <p>Split a {@link CharSequence} using a {@code char} separator with a limit.</p>
	 * @param separator the {@code char} separator
	 * @param charSequence the {@link CharSequence} to split
	 * @param limit the maximum number of splitted {@link String}s
	 * @return a {@link List} of splitted {@link String}s
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the limit is lower than {@code 2}
	 * @since 1.4.0
	 */
	public static List<String> split(final char separator, final CharSequence charSequence, final int limit) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("limit", limit, 2);
		final var length = charSequence.length();
		if (0 == length) {
			return List.of(EMPTY);
		}
		final var result = new ArrayList<String>();
		var i = 0;
		for (var j = 0; j < length; ++j) {
			if (separator == charSequence.charAt(j)) {
				result.add(charSequence.subSequence(i, j).toString());
				i = j + 1;
				if (limit == result.size() + 1) {
					break;
				}
			}
		}
		result.add(charSequence.subSequence(i, length).toString());
		return result;
	}

	/**
	 * <p>Split a {@link CharSequence} using a {@link CharSequence} separator.</p>
	 * <p><b>Note</b>: This implementation in not based on regular expressions unlike the standard Java one.</p>
	 * @param separator the {@link CharSequence} separator
	 * @param charSequence the {@link CharSequence} to split
	 * @return a {@link List} of splitted {@link String}s
	 * @throws NullPointerException if the {@link CharSequence} separator or the {@link CharSequence} is {@code null}
	 * @since 1.3.1
	 */
	public static List<String> split(final CharSequence separator, final CharSequence charSequence) {
		return split(separator, charSequence, Integer.MAX_VALUE);
	}

	/**
	 * <p>Split a {@link CharSequence} using a {@link CharSequence} separator with a limit.</p>
	 * <p><b>Note</b>: This implementation in not based on regular expressions unlike the standard Java one.</p>
	 * @param separator the {@link CharSequence} separator
	 * @param charSequence the {@link CharSequence} to split
	 * @param limit the maximum number of splitted {@link String}s
	 * @return a {@link List} of splitted {@link String}s
	 * @throws NullPointerException if the {@link CharSequence} separator or the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the limit is lower than {@code 2}
	 * @since 1.4.0
	 */
	public static List<String> split(final CharSequence separator, final CharSequence charSequence, final int limit) {
		Ensure.notNull("separator", separator);
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("limit", limit, 2);
		final var separatorLength = separator.length();
		if (0 == separatorLength) {
			return List.of(charSequence.toString());
		}
		final var length = charSequence.length();
		if (0 == length) {
			return List.of(EMPTY);
		}
		final var result = new ArrayList<String>();
		var i = 0;
		for (var j = 0; j < length; ++j) {
			if (separator.charAt(0) == charSequence.charAt(j)) {
				var k = 1;
				while (k < separatorLength && j + k < length && separator.charAt(k) == charSequence.charAt(j + k)) {
					++k;
				}
				if (k == separatorLength) {
					result.add(charSequence.subSequence(i, j).toString());
					i = j + separatorLength;
					if (limit == result.size() + 1) {
						break;
					}
				}
			}
		}
		result.add(charSequence.subSequence(i, length).toString());
		return result;
	}

	/**
	 * <p>Repeat a {@code char}.</p>
	 * @param c the {@code char} to repeat
	 * @param times the number of times to repeat
	 * @return a {@link String} of the repeated {@code char}
	 * @throws IllegalArgumentException if the number of times is lower than {@code 0}
	 * @since 1.0.0
	 * @deprecated since Java 11, use {@code String#repeat(int)} instead
	 */
	@Deprecated(since = "1.4.0")
	public static String repeat(final char c, final int times) {
		Ensure.greaterThanOrEqualTo("times", times, 0);
		if (0 == times) {
			return EMPTY;
		}
		if (1 == times) {
			return Character.toString(c);
		}
		final var builder = new StringBuilder(times);
		var i = 0;
		while (i++ < times) {
			builder.append(c);
		}
		return builder.toString();
	}

	/**
	 * <p>Repeat a {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to repeat
	 * @param times the number of times to repeat
	 * @return a {@link String} of the repeated {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the number of times is lower than {@code 0}
	 * @since 1.0.0
	 * @deprecated since Java 11, use {@code String#repeat(int)} instead
	 */
	@Deprecated(since = "1.4.0")
	public static String repeat(final CharSequence charSequence, final int times) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("times", times, 0);
		final var length = charSequence.length();
		if (0 == times || 0 == length) {
			return EMPTY;
		}
		if (1 == times) {
			return charSequence.toString();
		}
		final var builder = new StringBuilder(times * length);
		var i = 0;
		while (i++ < times) {
			builder.append(charSequence);
		}
		return builder.toString();
	}

	/**
	 * <p>Pad a {@link CharSequence} on the left with the default padding {@code char}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @return a left-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padLeft(final CharSequence charSequence, final int size) {
		return padLeft(charSequence, size, DEFAULT_PADDING);
	}

	/**
	 * <p>Pad a {@link CharSequence} on the left with the given padding {@code char}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @param padding the padding {@code char}
	 * @return a left-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padLeft(final CharSequence charSequence, final int size, final char padding) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("size", size, 1);
		final var length = charSequence.length();
		if (0 == length) {
			return repeat(padding, size);
		}
		if (size <= length) {
			return charSequence.toString();
		}
		return repeat(padding, size - length) + charSequence;
	}

	/**
	 * <p>Pad a {@link CharSequence} on the left with the given padding {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @param padding the padding {@link CharSequence}
	 * @return a left-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padLeft(final CharSequence charSequence, final int size, final CharSequence padding) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("size", size, 1);
		Ensure.notNull("padding", padding);
		final var length = charSequence.length();
		final var paddingLength = padding.length();
		if (0 == length) {
			return repeat(padding, size / paddingLength) + padding.subSequence(0, size % paddingLength);
		}
		if (size <= length || 0 == paddingLength) {
			return charSequence.toString();
		}
		return repeat(padding, (size - length) / paddingLength)
				+ padding.subSequence(0, (size - length) % paddingLength)
				+ charSequence;
	}

	/**
	 * <p>Pad a {@link CharSequence} on the right with the default padding {@code char}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @return a right-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padRight(final CharSequence charSequence, final int size) {
		return padRight(charSequence, size, DEFAULT_PADDING);
	}

	/**
	 * <p>Pad a {@link CharSequence} on the right with the given padding {@code char}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @param padding the padding {@code char}
	 * @return a right-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padRight(final CharSequence charSequence, final int size, final char padding) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("size", size, 1);
		final var length = charSequence.length();
		if (0 == length) {
			return repeat(padding, size);
		}
		if (size <= length) {
			return charSequence.toString();
		}
		return charSequence + repeat(padding, size - length);
	}

	/**
	 * <p>Pad a {@link CharSequence} on the right with the given padding {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @param padding the padding {@link CharSequence}
	 * @return a right-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padRight(final CharSequence charSequence, final int size, final CharSequence padding) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("size", size, 1);
		Ensure.notNull("padding", padding);
		final var length = charSequence.length();
		final var paddingLength = padding.length();
		if (0 == length) {
			return repeat(padding, size / paddingLength) + padding.subSequence(0, size % paddingLength);
		}
		if (size <= length || 0 == paddingLength) {
			return charSequence.toString();
		}
		return charSequence
				+ repeat(padding, (size - length) / paddingLength)
				+ padding.subSequence(0, (size - length) % paddingLength);
	}

	/**
	 * <p>Pad a {@link CharSequence} on both left and right with the default padding {@code char}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @return a left/right-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padBoth(final CharSequence charSequence, final int size) {
		return padBoth(charSequence, size, DEFAULT_PADDING);
	}

	/**
	 * <p>Pad a {@link CharSequence} on both left and right with the given padding {@code char}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @param padding the padding {@code char}
	 * @return a left/right-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padBoth(final CharSequence charSequence, final int size, final char padding) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("size", size, 1);
		final var length = charSequence.length();
		if (0 == length) {
			return repeat(padding, size);
		}
		if (size <= length) {
			return charSequence.toString();
		}
		return repeat(padding, (size - length) / 2)
				+ charSequence
				+ repeat(padding, (size - length) / 2 + (size - length) % 2);
	}

	/**
	 * <p>Pad a {@link CharSequence} on both left and right with the given padding {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to pad
	 * @param size the padding size
	 * @param padding the padding {@link CharSequence}
	 * @return a left/right-padded {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padBoth(final CharSequence charSequence, final int size, final CharSequence padding) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.greaterThanOrEqualTo("size", size, 1);
		Ensure.notNull("padding", padding);
		final var length = charSequence.length();
		final var paddingLength = padding.length();
		if (0 == length) {
			return repeat(padding, size / paddingLength) + padding.subSequence(0, size % paddingLength);
		}
		if (size <= length || 0 == paddingLength) {
			return charSequence.toString();
		}
		return repeat(padding, (size - length) / 2 / paddingLength)
				+ padding.subSequence(0, (size - length) / 2 % paddingLength)
				+ charSequence
				+ repeat(padding, ((size - length) / 2 + (size - length) % 2) / paddingLength)
				+ padding.subSequence(0, ((size - length) / 2 + (size - length) % 2) % paddingLength);
	}

	/**
	 * <p>Replace the first occurrence of the target {@code char} by a replacement one if found in the
	 * {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to replace from
	 * @param target the target {@code char}
	 * @param replacement the replacement {@code char}
	 * @return a replaced {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static String replaceFirst(final CharSequence charSequence, final char target, final char replacement) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		for (var i = 0; i < length; ++i) {
			if (target == charSequence.charAt(i)) {
				return charSequence.subSequence(0, i) + Character.toString(replacement) + charSequence.subSequence(i + 1, length);
			}
		}
		return charSequence.toString();
	}

	/**
	 * <p>Replace the first occurrence of the target {@link CharSequence} by a replacement one if found in the
	 * {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to replace from
	 * @param target the target {@link CharSequence}
	 * @param replacement the replacement {@link CharSequence}
	 * @return a replaced {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence}, the target or the replacement is {@code null}
	 * @since 1.0.0
	 */
	public static String replaceFirst(final CharSequence charSequence, final CharSequence target, final CharSequence replacement) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("target", target);
		Ensure.notNull("replacement", replacement);
		final var length = charSequence.length();
		final var targetLength = target.length();
		if (0 == targetLength || length < targetLength) {
			return charSequence.toString();
		}
		for (var i = 0; i < length - targetLength + 1; ++i) {
			if (charSequence.charAt(i) == target.charAt(0)) {
				var j = 1;
				while (i + j < length && j < targetLength && charSequence.charAt(i + j) == target.charAt(j)) {
					++j;
				}
				if (j == targetLength) {
					return charSequence.subSequence(0, i) + replacement.toString() + charSequence.subSequence(i + j, length);
				}
			}
		}
		return charSequence.toString();
	}

	/**
	 * <p>Replace the last occurrence of the target {@code char} by a replacement one if found in the
	 * {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to replace from
	 * @param target the target {@code char}
	 * @param replacement the replacement {@code char}
	 * @return a replaced {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static String replaceLast(final CharSequence charSequence, final char target, final char replacement) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		for (var i = length - 1; i >= 0; --i) {
			if (target == charSequence.charAt(i)) {
				return charSequence.subSequence(0, i) + Character.toString(replacement) + charSequence.subSequence(i + 1, length);
			}
		}
		return charSequence.toString();
	}

	/**
	 * <p>Replace the last occurrence of the target {@link CharSequence} by a replacement one if found in the
	 * {@link CharSequence}.</p>
	 * @param charSequence the {@link CharSequence} to replace from
	 * @param target the target {@link CharSequence}
	 * @param replacement the replacement {@link CharSequence}
	 * @return a replaced {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence}, the target or the replacement is {@code null}
	 * @since 1.0.0
	 */
	public static String replaceLast(final CharSequence charSequence, final CharSequence target, final CharSequence replacement) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("target", target);
		Ensure.notNull("replacement", replacement);
		final var length = charSequence.length();
		final var targetLength = target.length();
		if (0 == targetLength || length < targetLength) {
			return charSequence.toString();
		}
		for (var i = length - 1; i >= 0; --i) {
			if (charSequence.charAt(i) == target.charAt(targetLength - 1)) {
				var j = 1;
				while (i - j >= 0 && j < targetLength && charSequence.charAt(i - j) == target.charAt(targetLength - j - 1)) {
					++j;
				}
				if (j == targetLength) {
					return charSequence.subSequence(0, i - j + 1) + replacement.toString() + charSequence.subSequence(i + 1, length);
				}
			}
		}
		return charSequence.toString();
	}

	/**
	 * <p>Remove the prefix {@code char} if the {@link CharSequence} starts with it.</p>
	 * @param charSequence the {@link CharSequence} to remove from
	 * @param prefix the prefix {@code char}
	 * @return a stripped {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static String removeStart(final CharSequence charSequence, final char prefix) {
		Ensure.notNull("charSequence", charSequence);
		if (prefix == charSequence.charAt(0)) {
			return charSequence.subSequence(1, charSequence.length()).toString();
		}
		return charSequence.toString();
	}

	/**
	 * <p>Remove the prefix {@link CharSequence} if the {@link CharSequence} starts with it.</p>
	 * @param charSequence the {@link CharSequence} to remove from
	 * @param prefix the prefix {@link CharSequence}
	 * @return a stripped {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} or the prefix is {@code null}
	 * @since 1.0.0
	 */
	public static String removeStart(final CharSequence charSequence, final CharSequence prefix) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("prefix", prefix);
		final var length = charSequence.length();
		final var prefixLength = prefix.length();
		if (0 == prefixLength || length < prefixLength) {
			return charSequence.toString();
		}
		for (var i = 0; i < prefixLength; ++i) {
			if (charSequence.charAt(i) != prefix.charAt(i)) {
				return charSequence.toString();
			}
		}
		return charSequence.subSequence(prefixLength, length).toString();
	}

	/**
	 * <p>Remove the prefix {@link CharSequence} if the {@link CharSequence} starts with it ignoring the case.</p>
	 * @param charSequence the {@link CharSequence} to remove from
	 * @param prefix the prefix {@link CharSequence}
	 * @return a stripped {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} or the prefix is {@code null}
	 * @since 1.0.0
	 */
	public static String removeStartIgnoreCase(final CharSequence charSequence, final CharSequence prefix) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("prefix", prefix);
		final var length = charSequence.length();
		final var prefixLength = prefix.length();
		if (0 == prefixLength || length < prefixLength) {
			return charSequence.toString();
		}
		for (var i = 0; i < prefixLength; ++i) {
			if (Character.toLowerCase(charSequence.charAt(i)) != Character.toLowerCase(prefix.charAt(i))) {
				return charSequence.toString();
			}
		}
		return charSequence.subSequence(prefixLength, length).toString();
	}

	/**
	 * <p>Remove the suffix {@code char} if the {@link CharSequence} ends with it.</p>
	 * @param charSequence the {@link CharSequence} to remove from
	 * @param suffix the suffix {@code char}
	 * @return a stripped {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static String removeEnd(final CharSequence charSequence, final char suffix) {
		Ensure.notNull("charSequence", charSequence);
		final var length = charSequence.length();
		if (suffix == charSequence.charAt(length - 1)) {
			return charSequence.subSequence(0, length - 1).toString();
		}
		return charSequence.toString();
	}

	/**
	 * <p>Remove the suffix {@link CharSequence} if the {@link CharSequence} ends with it.</p>
	 * @param charSequence the {@link CharSequence} to remove from
	 * @param suffix the suffix {@link CharSequence}
	 * @return a stripped {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} or the suffix is {@code null}
	 * @since 1.0.0
	 */
	public static String removeEnd(final CharSequence charSequence, final CharSequence suffix) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("suffix", suffix);
		final var length = charSequence.length();
		final var suffixLength = suffix.length();
		if (0 == suffixLength || length < suffixLength) {
			return charSequence.toString();
		}
		for (var i = 1; i <= suffixLength; ++i) {
			if (charSequence.charAt(length - i) != suffix.charAt(suffixLength - i)) {
				return charSequence.toString();
			}
		}
		return charSequence.subSequence(0, length - suffixLength).toString();
	}

	/**
	 * <p>Remove the suffix {@link CharSequence} if the {@link CharSequence} ends with it ignoring the case.</p>
	 * @param charSequence the {@link CharSequence} to remove from
	 * @param suffix the suffix {@link CharSequence}
	 * @return a stripped {@link String} of the {@link CharSequence}
	 * @throws NullPointerException if the {@link CharSequence} or the suffix is {@code null}
	 * @since 1.0.0
	 */
	public static String removeEndIgnoreCase(final CharSequence charSequence, final CharSequence suffix) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("suffix", suffix);
		final var length = charSequence.length();
		final var suffixLength = suffix.length();
		if (0 == suffixLength || length < suffixLength) {
			return charSequence.toString();
		}
		for (var i = 1; i <= suffixLength; ++i) {
			if (Character.toLowerCase(charSequence.charAt(length - i)) != Character.toLowerCase(suffix.charAt(suffixLength - i))) {
				return charSequence.toString();
			}
		}
		return charSequence.subSequence(0, length - suffixLength).toString();
	}

	/**
	 * <p>Concatenate two {@link CharSequence}s by merging their commons {@code char}s.</p>
	 * <p><b>Example01</b>: {@code 123456} and {@code 456789} would result in {@code 123456789}.</p>
	 * @param start the start {@link CharSequence}
	 * @param end the end {@link CharSequence}
	 * @return a merged {@link String} of both {@link CharSequence}s
	 * @throws NullPointerException if the start or the end is {@code null}
	 * @since 1.0.0
	 */
	public static String concatMerge(final CharSequence start, final CharSequence end) {
		Ensure.notNull("start", start);
		Ensure.notNull("end", end);
		final var startLength = start.length();
		if (0 == startLength) {
			return end.toString();
		}
		final var endLength = end.length();
		if (0 == endLength) {
			return start.toString();
		}
		for (var i = 0; i < startLength; ++i) {
			if (start.charAt(i) == end.charAt(0)) {
				var j = 1;
				while (i + j < startLength && j < endLength && start.charAt(i + j) == end.charAt(j)) {
					++j;
				}
				if (i + j == startLength) {
					return start.subSequence(0, i).toString() + end;
				}
			}
		}
		return start.toString() + end;
	}

	/**
	 * <p>Create a {@link String} from multiple {@code char}s.</p>
	 * @param chars the {@code char} array to convert
	 * @return the created {@link String}
	 * @throws NullPointerException if the {@code char} array is {@code null}
	 * @since 1.1.0
	 */
	public static String of(final char... chars) {
		Ensure.notNull("chars", chars);
		if (0 == chars.length) {
			return EMPTY;
		}
		return new String(chars);
	}

	/**
	 * <p>Convert a {@link CharSequence} with a length of {@code 1} to a {@code char}.</p>
	 * @param charSequence the {@link CharSequence} to convert
	 * @return the converted {@code char}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@link CharSequence} length is not equal to {@code 1}
	 * @since 1.0.0
	 */
	public static char toChar(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.equalTo("charSequence length", charSequence.length(), 1);
		return charSequence.charAt(0);
	}
}