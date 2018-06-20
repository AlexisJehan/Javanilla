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
package com.github.alexisjehan.javanilla.lang;

import java.util.regex.Matcher;

/**
 * <p>An utility class that provides {@link String} and {@link CharSequence} tools.</p>
 * @since 1.0.0
 */
public final class Strings {

	/**
	 * <p>The default quote {@code char}.</p>
	 * @since 1.1.0
	 */
	private static final char DEFAULT_QUOTE = '"';

	/**
	 * <p>The default escape {@code char}.</p>
	 * @since 1.1.0
	 */
	private static final char DEFAULT_ESCAPE = '\\';

	/**
	 * <p>An empty {@code String}.</p>
	 * @since 1.0.0
	 */
	public static final String EMPTY = "";

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0.0
	 */
	private Strings() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing {@code null} by an empty {@code String}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @return the non-{@code null} {@code String}
	 * @since 1.0.0
	 */
	public static String nullToEmpty(final CharSequence charSequence) {
		return nullToDefault(charSequence, EMPTY);
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing {@code null} by a default {@code String}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @param defaultString the default {@code String}
	 * @return the non-{@code null} {@code String}
	 * @throws NullPointerException if the default {@code String} is {@code null}
	 * @since 1.0.0
	 */
	public static String nullToDefault(final CharSequence charSequence, final String defaultString) {
		if (null == defaultString) {
			throw new NullPointerException("Invalid default string (not null expected)");
		}
		return null != charSequence ? charSequence.toString() : defaultString;
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing an empty one by {@code null}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @return the non-empty {@code String} or {@code null}
	 * @since 1.0.0
	 */
	public static String emptyToNull(final CharSequence charSequence) {
		return emptyToDefault(charSequence, null);
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing an empty one by a default {@code String}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @param defaultString the default {@code String} or {@code null}
	 * @return the non-empty {@code String} or {@code null}
	 * @throws IllegalArgumentException if the default {@code String} is empty
	 * @since 1.1.0
	 */
	public static String emptyToDefault(final CharSequence charSequence, final String defaultString) {
		if (null != defaultString && defaultString.isEmpty()) {
			throw new IllegalArgumentException("Invalid default string (not empty expected)");
		}
		if (null == charSequence) {
			return null;
		}
		return 0 != charSequence.length() ? charSequence.toString() : defaultString;
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing a blank one by {@code null}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @return the non-blank {@code String} or {@code null}
	 * @since 1.0.0
	 */
	public static String blankToNull(final CharSequence charSequence) {
		return blankToDefault(charSequence, null);
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing a blank one by an empty {@code String}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @return the non-blank {@code String} or {@code null}
	 * @since 1.0.0
	 */
	public static String blankToEmpty(final CharSequence charSequence) {
		return blankToDefault(charSequence, EMPTY);
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing a blank one by a default {@code String}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @param defaultString the default {@code String} or {@code null}
	 * @return the non-blank {@code String} or {@code null}
	 * @throws IllegalArgumentException if the default {@code String} is blank
	 * @since 1.1.0
	 */
	public static String blankToDefault(final CharSequence charSequence, final String defaultString) {
		if (null != defaultString && isBlank(defaultString)) {
			throw new IllegalArgumentException("Invalid default string: " + quote(defaultString) + " (not blank expected)");
		}
		if (null == charSequence) {
			return null;
		}
		return !isBlank(charSequence) ? charSequence.toString() : defaultString;
	}

	/**
	 * <p>Tell if a {@code CharSequence} is blank.</p>
	 * <p><b>Note</b>: A {@code char} is blank or not based on {@link Character#isWhitespace(char)}.</p>
	 * <p><b>Note</b>: An empty {@code CharSequence} is not considered as blank.</p>
	 * @param charSequence the {@code CharSequence}
	 * @return {@code true} if the {@code CharSequence} is blank
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean isBlank(final CharSequence charSequence) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
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
	 * <p>Tell if a {@code CharSequence} is in hexadecimal.</p>
	 * <p><b>Note</b>: The hexadecimal {@code String} value case does not matter.</p>
	 * @param charSequence the {@code CharSequence}
	 * @return {@code true} if the {@code CharSequence} is in hexadecimal
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean isHex(final CharSequence charSequence) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		if (0 == length || 0 != length % 2) {
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
	 * <p>Tell if a {@code CharSequence} is in base 64.</p>
	 * @param charSequence the {@code CharSequence}
	 * @return {@code true} if the {@code CharSequence} is in base 64
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean isBase64(final CharSequence charSequence) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		if (0 == length || 0 != length % 4) {
			return false;
		}
		var padding = false;
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if (('a' > c || 'z' < c) && ('A' > c || 'Z' < c) && ('0' > c || '9' < c) && '+' != c && '/' != c) {
				if ('=' != c || i < length - 2) {
					return false;
				} else if (!padding) {
					padding = true;
				}
			} else if (padding) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Tell if a {@code CharSequence} is in base 64 for URLs and file names.</p>
	 * @param charSequence the {@code CharSequence}
	 * @return {@code true} if the {@code CharSequence} is in base 64 for URLs
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static boolean isBase64Url(final CharSequence charSequence) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		var padding = false;
		for (var i = 0; i < length; ++i) {
			final var c = charSequence.charAt(i);
			if (('a' > c || 'z' < c) && ('A' > c || 'Z' < c) && ('0' > c || '9' < c) && '-' != c && '_' != c) {
				if ('=' != c || i < length - 2) {
					return false;
				} else if (!padding) {
					padding = true;
				}
			} else if (padding) {
				return false;
			}
		}
		return !padding || 0 == length % 4;
	}

	/**
	 * <p>Quote an {@code Object} using {@link Object#toString()} and default quote and escape chars.</p>
	 * @param object the {@code Object} to quote
	 * @return the quoted {@code Object}
	 * @throws NullPointerException if the {@code Object} is {@code null}
	 * @since 1.1.0
	 */
	public static String quote(final Object object) {
		return quote(object, DEFAULT_QUOTE, DEFAULT_ESCAPE);
	}

	/**
	 * <p>Quote an {@code Object} using {@link Object#toString()} and custom quote and escape chars.</p>
	 * @param object the {@code Object} to quote
	 * @param quote the quote {@code char}
	 * @param escape the escape {@code char}
	 * @return the quoted {@code Object}
	 * @throws NullPointerException if the {@code Object} is {@code null}
	 * @since 1.1.0
	 */
	public static String quote(final Object object, final char quote, final char escape) {
		if (null == object) {
			throw new NullPointerException("Invalid string (not null expected)");
		}
		return quote(object.toString(), quote, escape);
	}

	/**
	 * <p>Quote a {@code String} using default quote and escape chars.</p>
	 * @param string the {@code String} to quote
	 * @return the quoted {@code String}
	 * @throws NullPointerException if the {@code String} is {@code null}
	 * @since 1.1.0
	 */
	public static String quote(final String string) {
		return quote(string, DEFAULT_QUOTE, DEFAULT_ESCAPE);
	}

	/**
	 * <p>Quote a {@code String} using custom quote and escape chars.</p>
	 * @param string the {@code String} to quote
	 * @param quote the quote {@code char}
	 * @param escape the escape {@code char}
	 * @return the quoted {@code String}
	 * @throws NullPointerException if the {@code String} is {@code null}
	 * @since 1.1.0
	 */
	public static String quote(final String string, final char quote, final char escape) {
		if (null == string) {
			throw new NullPointerException("Invalid string (not null expected)");
		}
		return quote + string.replaceAll(Character.toString(quote), Matcher.quoteReplacement(of(escape, quote))) + quote;
	}

	/**
	 * <p>Unquote a previously quoted {@code String} using default quote and escape chars.</p>
	 * @param string the quoted {@code String} to unquote
	 * @return the unquoted {@code String}
	 * @throws NullPointerException if the {@code String} is {@code null}
	 * @throws IllegalArgumentException whether the {@code String} length is lower than {@code 2} or the first or last
	 * {@code char} is different of the quote {@code char}
	 * @since 1.1.0
	 */
	public static String unquote(final String string) {
		return unquote(string, DEFAULT_QUOTE, DEFAULT_ESCAPE);
	}

	/**
	 * <p>Unquote a previously quoted {@code String} using custom quote and escape chars.</p>
	 * @param string the quoted {@code String} to unquote
	 * @param quote the quote {@code char}
	 * @param escape the escape {@code char}
	 * @return the unquoted {@code String}
	 * @throws NullPointerException if the {@code String} is {@code null}
	 * @throws IllegalArgumentException whether the {@code String} length is lower than {@code 2} or the first or last
	 * {@code char} is different of the quote {@code char}
	 * @since 1.1.0
	 */
	public static String unquote(final String string, final char quote, final char escape) {
		if (null == string) {
			throw new NullPointerException("Invalid string (not null expected)");
		}
		final var length = string.length();
		if (2 > length) {
			throw new IllegalArgumentException("Invalid string length: " + length + " (greater than or equal to 2 expected)");
		}
		if (quote != string.charAt(0) || quote != string.charAt(length - 1)) {
			throw new IllegalArgumentException("Invalid string: " + string + " (first and last chars equal to '" + quote + "' expected)");
		}
		return string.substring(1, length - 1).replaceAll(Matcher.quoteReplacement(of(escape, quote)), Character.toString(quote));
	}

	/**
	 * <p>Create a {@code String} by repeating a {@code char}.</p>
	 * @param c the {@code char} to repeat
	 * @param times the number of times to repeat
	 * @return the result {@code String}
	 * @throws IllegalArgumentException if the number of times is lower than {@code 0}
	 * @since 1.0.0
	 */
	public static String repeat(final char c, final int times) {
		if (0 > times) {
			throw new IllegalArgumentException("Invalid repeat times: " + times + " (greater than or equal to 0 expected)");
		}
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
	 * <p>Create a {@code String} by repeating a {@code CharSequence}.</p>
	 * @param charSequence the {@code CharSequence} to repeat
	 * @param times the number of times to repeat
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the number of times is lower than {@code 0}
	 * @since 1.0.0
	 */
	public static String repeat(final CharSequence charSequence, final int times) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (0 > times) {
			throw new IllegalArgumentException("Invalid repeat times: " + times + " (greater than or equal to 0 expected)");
		}
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
	 * <p>Create a {@code String} by padding a {@code CharSequence} on the left to fit the given size with spaces.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padLeft(final CharSequence charSequence, final int size) {
		return padLeft(charSequence, size, ' ');
	}

	/**
	 * <p>Create a {@code String} by padding a {@code CharSequence} on the left to fit the given size with the provided
	 * padding {@code char}.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @param padding the padding {@code char}
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padLeft(final CharSequence charSequence, final int size, final char padding) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (1 > size) {
			throw new IllegalArgumentException("Invalid pad size: " + size + " (greater than or equal to 1 expected)");
		}
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
	 * <p>Create a {@code String} by padding a {@code CharSequence} on the left to fit the given size with the provided
	 * padding {@code CharSequence}.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @param padding the padding {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence} or the padding {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padLeft(final CharSequence charSequence, final int size, final CharSequence padding) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == padding) {
			throw new NullPointerException("Invalid padding (not null expected)");
		}
		if (1 > size) {
			throw new IllegalArgumentException("Invalid pad size: " + size + " (greater than or equal to 1 expected)");
		}
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
	 * <p>Create a {@code String} by padding a {@code CharSequence} on the right to fit the given size with spaces.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padRight(final CharSequence charSequence, final int size) {
		return padRight(charSequence, size, ' ');
	}

	/**
	 * <p>Create a {@code String} by padding a {@code CharSequence} on the right to fit the given size with the provided
	 * padding {@code char}.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @param padding the padding {@code char}
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padRight(final CharSequence charSequence, final int size, final char padding) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (1 > size) {
			throw new IllegalArgumentException("Invalid pad size: " + size + " (greater than or equal to 1 expected)");
		}
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
	 * <p>Create a {@code String} by padding a {@code CharSequence} on the right to fit the given size with the provided
	 * padding {@code CharSequence}.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @param padding the padding {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence} or the padding {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padRight(final CharSequence charSequence, final int size, final CharSequence padding) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == padding) {
			throw new NullPointerException("Invalid padding (not null expected)");
		}
		if (1 > size) {
			throw new IllegalArgumentException("Invalid pad size: " + size + " (greater than or equal to 1 expected)");
		}
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
	 * <p>Create a {@code String} by padding a {@code CharSequence} on both sides to fit the given size with spaces.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padBoth(final CharSequence charSequence, final int size) {
		return padBoth(charSequence, size, ' ');
	}

	/**
	 * <p>Create a {@code String} by padding a {@code CharSequence} on both sides to fit the given size with the
	 * provided padding {@code char}.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @param padding the padding {@code char}
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padBoth(final CharSequence charSequence, final int size, final char padding) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (1 > size) {
			throw new IllegalArgumentException("Invalid pad size: " + size + " (greater than or equal to 1 expected)");
		}
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
	 * <p>Create a {@code String} by padding a {@code CharSequence} on both sides to fit the given size with the
	 * provided padding {@code CharSequence}.</p>
	 * @param charSequence the {@code CharSequence} to pad
	 * @param size the size to fit
	 * @param padding the padding {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence} or the padding {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the padding size is lower than {@code 1}
	 * @since 1.0.0
	 */
	public static String padBoth(final CharSequence charSequence, final int size, final CharSequence padding) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == padding) {
			throw new NullPointerException("Invalid padding (not null expected)");
		}
		if (1 > size) {
			throw new IllegalArgumentException("Invalid pad size: " + size + " (greater than or equal to 1 expected)");
		}
		final var length = charSequence.length();
		final var paddingLength = padding.length();
		if (0 == length) {
			return repeat(padding, size / paddingLength) + padding.subSequence(0, size % paddingLength);
		}
		if (size <= length || 0 == paddingLength) {
			return charSequence.toString();
		}
		return repeat(padding, ((size - length) / 2) / paddingLength)
				+ padding.subSequence(0, ((size - length) / 2) % paddingLength)
				+ charSequence
				+ repeat(padding, ((size - length) / 2 + (size - length) % 2) / paddingLength)
				+ padding.subSequence(0, ((size - length) / 2 + (size - length) % 2) % paddingLength);
	}

	/**
	 * <p>Replace the first occurrence of the given target {@code char} if found in the {@code CharSequence} by a
	 * replacement {@code char}.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the target {@code char}
	 * @param replacement the replacement {@code char}
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static String replaceFirst(final CharSequence charSequence, final char target, final char replacement) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		for (var i = 0; i < length; ++i) {
			if (target == charSequence.charAt(i)) {
				return charSequence.subSequence(0, i) + Character.toString(replacement) + charSequence.subSequence(i + 1, length);
			}
		}
		return charSequence.toString();
	}

	/**
	 * <p>Replace the first occurrence of the given target if found in the {@code CharSequence} by a replacement.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the target {@code CharSequence}
	 * @param replacement the replacement {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence}, the target or the replacement is {@code null}
	 * @since 1.0.0
	 */
	public static String replaceFirst(final CharSequence charSequence, final CharSequence target, final CharSequence replacement) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == target) {
			throw new NullPointerException("Invalid target (not null expected)");
		}
		if (null == replacement) {
			throw new NullPointerException("Invalid replacement (not null expected)");
		}
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
	 * <p>Replace the last occurrence of the given target {@code char} if found in the {@code CharSequence} by a
	 * replacement {@code char}.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the target {@code char}
	 * @param replacement the replacement {@code char}
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static String replaceLast(final CharSequence charSequence, final char target, final char replacement) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		for (var i = length - 1; i >= 0; --i) {
			if (target == charSequence.charAt(i)) {
				return charSequence.subSequence(0, i) + Character.toString(replacement) + charSequence.subSequence(i + 1, length);
			}
		}
		return charSequence.toString();
	}

	/**
	 * <p>Replace the last occurrence of the given target if found in the {@code CharSequence} by a replacement.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the target {@code CharSequence}
	 * @param replacement the replacement {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence}, the target or the replacement is {@code null}
	 * @since 1.0.0
	 */
	public static String replaceLast(final CharSequence charSequence, final CharSequence target, final CharSequence replacement) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == target) {
			throw new NullPointerException("Invalid target (not null expected)");
		}
		if (null == replacement) {
			throw new NullPointerException("Invalid replacement (not null expected)");
		}
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
	 * <p>Remove the given leading target {@code char} if the {@code CharSequence} starts with it.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the leading target {@code char}
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static String removeStart(final CharSequence charSequence, final char target) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (target == charSequence.charAt(0)) {
			return charSequence.subSequence(1, charSequence.length()).toString();
		}
		return charSequence.toString();
	}

	/**
	 * <p>Remove the given leading target if the {@code CharSequence} starts with it.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the leading target {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence} or the leading target is {@code null}
	 * @since 1.0.0
	 */
	public static String removeStart(final CharSequence charSequence, final CharSequence target) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == target) {
			throw new NullPointerException("Invalid target (not null expected)");
		}
		final var length = charSequence.length();
		final var targetLength = target.length();
		if (0 == targetLength || length < targetLength) {
			return charSequence.toString();
		}
		for (var i = 0; i < targetLength; ++i) {
			if (charSequence.charAt(i) != target.charAt(i)) {
				return charSequence.toString();
			}
		}
		return charSequence.subSequence(targetLength, length).toString();
	}

	/**
	 * <p>Remove the given leading target if the {@code CharSequence} starts with it ignoring the case.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the leading target {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence} or the leading target is {@code null}
	 * @since 1.0.0
	 */
	public static String removeStartIgnoreCase(final CharSequence charSequence, final CharSequence target) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == target) {
			throw new NullPointerException("Invalid target (not null expected)");
		}
		final var length = charSequence.length();
		final var targetLength = target.length();
		if (0 == targetLength || length < targetLength) {
			return charSequence.toString();
		}
		for (var i = 0; i < targetLength; ++i) {
			if (Character.toLowerCase(charSequence.charAt(i)) != Character.toLowerCase(target.charAt(i))) {
				return charSequence.toString();
			}
		}
		return charSequence.subSequence(targetLength, length).toString();
	}

	/**
	 * <p>Remove the given ending target {@code char} if the {@code CharSequence} ends with it.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the ending target {@code char}
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0.0
	 */
	public static String removeEnd(final CharSequence charSequence, final char target) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		if (target == charSequence.charAt(length - 1)) {
			return charSequence.subSequence(0, length - 1).toString();
		}
		return charSequence.toString();
	}

	/**
	 * <p>Remove the given ending target if the {@code CharSequence} ends with it.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the ending target {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence} or the ending target is {@code null}
	 * @since 1.0.0
	 */
	public static String removeEnd(final CharSequence charSequence, final CharSequence target) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == target) {
			throw new NullPointerException("Invalid target (not null expected)");
		}
		final var length = charSequence.length();
		final var targetLength = target.length();
		if (0 == targetLength || length < targetLength) {
			return charSequence.toString();
		}
		for (var i = 1; i <= targetLength; ++i) {
			if (charSequence.charAt(length - i) != target.charAt(targetLength - i)) {
				return charSequence.toString();
			}
		}
		return charSequence.subSequence(0, length - targetLength).toString();
	}

	/**
	 * <p>Remove the given ending target if the {@code CharSequence} ends with it ignoring the case.</p>
	 * @param charSequence the {@code CharSequence}
	 * @param target the ending target {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the {@code CharSequence} or the ending target is {@code null}
	 * @since 1.0.0
	 */
	public static String removeEndIgnoreCase(final CharSequence charSequence, final CharSequence target) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		if (null == target) {
			throw new NullPointerException("Invalid target (not null expected)");
		}
		final var length = charSequence.length();
		final var targetLength = target.length();
		if (0 == targetLength || length < targetLength) {
			return charSequence.toString();
		}
		for (var i = 1; i <= targetLength; ++i) {
			if (Character.toLowerCase(charSequence.charAt(length - i)) != Character.toLowerCase(target.charAt(targetLength - i))) {
				return charSequence.toString();
			}
		}
		return charSequence.subSequence(0, length - targetLength).toString();
	}

	/**
	 * <p>Concatenate two {@code CharSequence}s by merging their commons {@code char}s.</p>
	 * <p><b>Example</b>: {@code 123456} and {@code 456789} would result in {@code 123456789}.</p>
	 * @param start the start {@code CharSequence}
	 * @param end the end {@code CharSequence}
	 * @return the result {@code String}
	 * @throws NullPointerException whether the start or the end is {@code null}
	 * @since 1.0.0
	 */
	public static String concatMerge(final CharSequence start, final CharSequence end) {
		if (null == start) {
			throw new NullPointerException("Invalid start (not null expected)");
		}
		if (null == end) {
			throw new NullPointerException("Invalid end (not null expected)");
		}
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
	 * <p>Create a {@code String} with a {@code char array}.</p>
	 * @param chars the {@code char array} to convert
	 * @return the created {@code String}
	 * @throws NullPointerException if the {@code char array} is {@code null}
	 * @since 1.1.0
	 */
	public static String of(final char... chars) {
		if (null == chars) {
			throw new NullPointerException("Invalid chars (not null expected)");
		}
		if (0 == chars.length) {
			return EMPTY;
		}
		return String.valueOf(chars);
	}

	/**
	 * <p>Convert a {@code CharSequence} with a length of {@code 1} to a {@code char}.</p>
	 * @param charSequence the {@code CharSequence} to convert
	 * @return the converted char
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@code CharSequence}'s length is different than {@code 1}
	 * @since 1.0.0
	 */
	public static char toChar(final CharSequence charSequence) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		if (1 != length) {
			throw new IllegalArgumentException("Invalid char sequence length: " + length + " (equal to 1 expected)");
		}
		return charSequence.charAt(0);
	}
}