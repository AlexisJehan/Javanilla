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
package com.github.javanilla.lang;

/**
 * <p>An utility class that provides {@link String} and {@link CharSequence} tools.</p>
 * @since 1.0
 */
public final class Strings {

	/**
	 * <p>An empty {@code String}.</p>
	 * @since 1.0
	 */
	public static final String EMPTY = "";

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private Strings() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing {@code null} by an empty {@code String}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @return a non-{@code null} {@code String}
	 * @since 1.0
	 */
	public static String nullToEmpty(final CharSequence charSequence) {
		return null != charSequence ? charSequence.toString() : EMPTY;
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing {@code null} by a default {@code String}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @param defaultString a default {@code String}
	 * @return a non-{@code null} {@code String}
	 * @throws NullPointerException if the default {@code String} is {@code null}
	 * @since 1.0
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
	 * @return a non-empty {@code String} or {@code null}
	 * @since 1.0
	 */
	public static String emptyToNull(final CharSequence charSequence) {
		return null != charSequence && 0 != charSequence.length() ? charSequence.toString() : null;
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing {@code null} or blank by an empty {@code String}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @return a non-{@code null} and non-blank {@code String}
	 * @since 1.0
	 */
	public static String blankToEmpty(final CharSequence charSequence) {
		return null != charSequence && !isBlank(charSequence) ? charSequence.toString() : EMPTY;
	}

	/**
	 * <p>Wrap a {@code CharSequence} replacing an empty or a blank one by {@code null}.</p>
	 * @param charSequence a {@code CharSequence} or {@code null}
	 * @return a non-empty and non-blank {@code String} or {@code null}
	 * @since 1.0
	 */
	public static String blankToNull(final CharSequence charSequence) {
		return null != charSequence && !isBlank(charSequence) ? charSequence.toString() : null;
	}

	/**
	 * <p>Tell if a {@code CharSequence} is empty or blank.</p>
	 * <p><b>Note</b>: A {@code char} is blank or not based on {@link Character#isWhitespace(char)}.</p>
	 * @param charSequence the {@code CharSequence}
	 * @return {@code true} if the {@code CharSequence} is empty or blank
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0
	 */
	public static boolean isBlank(final CharSequence charSequence) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		if (0 == length) {
			return true;
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
	 * @param charSequence the {@code CharSequence}
	 * @return {@code true} if the {@code CharSequence} is in hexadecimal
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0
	 */
	public static boolean isHex(final CharSequence charSequence) {
		if (null == charSequence) {
			throw new NullPointerException("Invalid char sequence (not null expected)");
		}
		final var length = charSequence.length();
		if (0 == length) {
			return false;
		}
		final CharSequence cleanedCharSequence;
		if ('0' == charSequence.charAt(0) && 'x' == charSequence.charAt(1) && 2 < length) {
			cleanedCharSequence = charSequence.subSequence(2, length);
		} else {
			cleanedCharSequence = charSequence;
		}
		final var cleanedLength = cleanedCharSequence.length();
		if (0 != cleanedLength % 2) {
			return false;
		}
		for (var i = 0; i < cleanedLength; ++i) {
			final var c = cleanedCharSequence.charAt(i);
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
	 * @since 1.0
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
	 * <p>Tell if a {@code CharSequence} is in base 64 for URLs.</p>
	 * @param charSequence the {@code CharSequence}
	 * @return {@code true} if the {@code CharSequence} is in base 64 for URLs
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.0
	 */
	public static boolean isBase64Url(final CharSequence charSequence) {
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
		return true;
	}

	/**
	 * <p>Create a {@code String} by repeating a {@code char}.</p>
	 * @param c the {@code char} to repeat
	 * @param times number of times to repeat
	 * @return the result {@code String}
	 * @throws IllegalArgumentException if the number of times is lower than 0
	 * @since 1.0
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
	 * @param times number of times to repeat
	 * @return the result {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the number of times is lower than 0
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @throws IllegalArgumentException if the padding size is lower than 1
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * @since 1.0
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
	 * <p>Convert a {@code CharSequence} with a length of {@code 1} to a {@code char}.</p>
	 * @param charSequence the {@code CharSequence} to convert
	 * @return the converted char
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @throws IllegalArgumentException if the {@code CharSequence}'s length is different than {@code 1}
	 * @since 1.0
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