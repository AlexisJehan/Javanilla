/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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
package com.github.alexisjehan.javanilla.misc;

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;
import com.github.alexisjehan.javanilla.misc.quality.ToString;
import com.github.alexisjehan.javanilla.misc.tuple.Pair;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * An immutable formatter to pretty display values of several types as {@link String}s.
 *
 * <p><b>Note</b>: This class is {@link Serializable}.</p>
 *
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @since 1.0.0
 */
public final class StringFormatter implements Serializable {

	/**
	 * Byte representation prefixes.
	 * @since 1.0.0
	 */
	public enum BytePrefix {
		/**
		 * SI prefix with a base of {@code 1000}.
		 * @see <a href="https://en.wikipedia.org/wiki/SI_prefix">https://en.wikipedia.org/wiki/SI_prefix</a>
		 * @since 1.0.0
		 */
		SI(1_000),

		/**
		 * Binary prefix with a base of {@code 1024}.
		 * @see <a href="https://en.wikipedia.org/wiki/Binary_prefix">https://en.wikipedia.org/wiki/Binary_prefix</a>
		 * @since 1.0.0
		 */
		BINARY(1_024);

		/**
		 * Base value.
		 * @since 1.0.0
		 */
		private final int base;

		/**
		 * Enumeration constructor.
		 * @param base the base value
		 * @since 1.0.0
		 */
		BytePrefix(final int base) {
			this.base = base;
		}

		/**
		 * Get the base.
		 * @return the base
		 * @since 1.9.0
		 */
		int getBase() {
			return base;
		}
	}

	/**
	 * {@link StringFormatter} instance with default parameters.
	 * @since 1.3.0
	 */
	public static final StringFormatter DEFAULT = new StringFormatter(Locale.getDefault());

	/**
	 * Default float precision.
	 * @since 1.0.0
	 */
	static final int DEFAULT_FLOAT_PRECISION = 2;

	/**
	 * Default value to enable the strict precision or not.
	 * @since 1.0.0
	 */
	static final boolean DEFAULT_STRICT_PRECISION = false;

	/**
	 * Units suffixes.
	 * @since 1.0.0
	 */
	private static final char[] UNITS = {'k', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y'};

	/**
	 * Default {@link BytePrefix}.
	 * @since 1.0.0
	 */
	private static final BytePrefix DEFAULT_BYTE_PREFIX = BytePrefix.BINARY;

	/**
	 * Serial version unique ID.
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -908532502532051210L;

	/**
	 * {@link Locale} instance.
	 * @since 1.0.0
	 */
	private final Locale locale;

	/**
	 * Float precision.
	 * @since 1.0.0
	 */
	private final int floatPrecision;

	/**
	 * Whether to enable the strict precision.
	 * @since 1.0.0
	 */
	private final boolean strictPrecision;

	/**
	 * Formatter for {@code long} values.
	 * @since 1.0.0
	 */
	private final NumberFormat longFormatter;

	/**
	 * Formatter for {@code double} values.
	 * @since 1.0.0
	 */
	private final DecimalFormat doubleFormatter;

	/**
	 * Formatter for percent values.
	 * @since 1.0.0
	 */
	private final NumberFormat percentFormatter;

	/**
	 * Formatter for currency values.
	 * @since 1.0.0
	 */
	private final NumberFormat currencyFormatter;

	/**
	 * {@link Locale} delimiter.
	 * @since 1.0.0
	 */
	private final String localeDelimiter;

	/**
	 * Constructor with a custom {@link Locale} and the default float precision.
	 * @param locale the custom {@link Locale}
	 * @throws NullPointerException if the {@link Locale} is {@code null}
	 * @since 1.0.0
	 */
	public StringFormatter(final Locale locale) {
		this(locale, DEFAULT_FLOAT_PRECISION);
	}

	/**
	 * Constructor with custom {@link Locale} and float precision, and the default strict precision parameter.
	 * @param locale the custom {@link Locale}
	 * @param floatPrecision the custom float precision
	 * @throws NullPointerException if the {@link Locale} is {@code null}
	 * @throws IllegalArgumentException if the float precision is lower than {@code 0}
	 * @since 1.0.0
	 */
	public StringFormatter(final Locale locale, final int floatPrecision) {
		this(locale, floatPrecision, DEFAULT_STRICT_PRECISION);
	}

	/**
	 * Complete constructor with custom {@link Locale}, float precision and strict precision parameter.
	 * @param locale the custom {@link Locale}
	 * @param floatPrecision the custom float precision
	 * @param strictPrecision the custom strict precision parameter
	 * @throws NullPointerException if the {@link Locale} is {@code null}
	 * @throws IllegalArgumentException if the float precision is lower than {@code 0}
	 * @since 1.0.0
	 */
	@SuppressWarnings("deprecation")
	public StringFormatter(final Locale locale, final int floatPrecision, final boolean strictPrecision) {
		Ensure.notNull("locale", locale);
		Ensure.greaterThanOrEqualTo("floatPrecision", floatPrecision, 0);
		this.locale = locale;
		this.floatPrecision = floatPrecision;
		this.strictPrecision = strictPrecision;
		longFormatter = NumberFormat.getIntegerInstance(locale);
		doubleFormatter = new DecimalFormat("###,##0." + Strings.repeat(strictPrecision ? '0' : '#', floatPrecision), new DecimalFormatSymbols(locale));
		percentFormatter = NumberFormat.getPercentInstance(locale);
		percentFormatter.setMaximumFractionDigits(floatPrecision);
		percentFormatter.setRoundingMode(RoundingMode.DOWN);
		if (strictPrecision) {
			percentFormatter.setMinimumFractionDigits(floatPrecision);
		} else {
			percentFormatter.setMinimumFractionDigits(0);
		}
		currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		currencyFormatter.setMaximumFractionDigits(floatPrecision);
		if (strictPrecision) {
			currencyFormatter.setMinimumFractionDigits(floatPrecision);
		} else {
			currencyFormatter.setMinimumFractionDigits(0);
		}
		final var s = NumberFormat.getPercentInstance(locale).format(0L);
		localeDelimiter = s.substring(s.indexOf('0') + 1, s.indexOf('%'));
	}

	/**
	 * Format a {@code int}/{@code long} value as a pretty {@link String}.
	 *
	 * <p><b>Note</b>: The {@link Locale} attribute is used.</p>
	 * @param value the {@code long} value
	 * @return the {@code long} {@link String} representation
	 * @since 1.0.0
	 */
	public String format(final long value) {
		return longFormatter.format(value);
	}

	/**
	 * Format a {@code float}/{@code double} value as a pretty {@link String}.
	 *
	 * <p><b>Note</b>: {@link Locale}, float precision and strict precision parameter attributes are used.</p>
	 * @param value the {@code double} value
	 * @return the {@code double} {@link String} representation
	 * @since 1.0.0
	 */
	public String format(final double value) {
		return doubleFormatter.format(value);
	}

	/**
	 * Format a number of bytes value such as a file size as a pretty {@link String} using the default
	 * {@link BytePrefix}.
	 *
	 * <p><b>Note</b>: {@link Locale}, float precision and strict precision parameter attributes are used.</p>
	 * @param value the number of bytes value
	 * @return the number of bytes {@link String} representation
	 * @since 1.0.0
	 */
	public String formatBytes(final long value) {
		return formatBytes(value, DEFAULT_BYTE_PREFIX);
	}

	/**
	 * Format a number of bytes value such as a file size as a pretty {@link String} using a custom {@link BytePrefix}.
	 *
	 * <p><b>Note</b>: {@link Locale}, float precision and strict precision parameter attributes are used.</p>
	 * @param value the number of bytes value
	 * @param bytePrefix the {@link BytePrefix} to use
	 * @return the number of bytes {@link String} representation
	 * @throws NullPointerException if the {@link BytePrefix} is {@code null}
	 * @see <a href="https://stackoverflow.com/a/3758880">https://stackoverflow.com/a/3758880</a>
	 * @since 1.0.0
	 */
	@SuppressWarnings("strictfp")
	public strictfp String formatBytes(final long value, final BytePrefix bytePrefix) {
		Ensure.notNull("bytePrefix", bytePrefix);
		final var absValue = Long.MIN_VALUE != value ? StrictMath.abs(value) : Long.MAX_VALUE;
		final var base = bytePrefix.getBase();
		if (base > absValue) {
			return format((double) value) + localeDelimiter + "B";
		}
		var exponent = (int) (StrictMath.log(absValue) / StrictMath.log(base));
		final var threshold = (long) (StrictMath.pow(base, exponent) * (base - 5.0d / StrictMath.pow(10.0d, floatPrecision + 1.0d)));
		if (6 > exponent && threshold <= absValue) {
			++exponent;
		}
		final var unit = UNITS[exponent - 1];
		final long fixedValue;
		if (4 < exponent) {
			fixedValue = value / base;
			exponent -= 1;
		} else {
			fixedValue = value;
		}
		return format(fixedValue / StrictMath.pow(base, exponent))
				+ localeDelimiter
				+ (BytePrefix.BINARY == bytePrefix ? Character.toUpperCase(unit) + "i" : unit)
				+ "B";
	}

	/**
	 * Format a percent value as a pretty {@link String}.
	 *
	 * <p><b>Note</b>: {@link Locale}, float precision and strict precision parameter attributes are used.</p>
	 * @param progression the progression value
	 * @param total the total value
	 * @return the percent {@link String} representation
	 * @throws IllegalArgumentException if the progression is lower than {@code 0} or greater than the total
	 * @since 1.0.0
	 */
	public String formatPercent(final double progression, final double total) {
		Ensure.between("progression", progression, 0.0d, total);
		return percentFormatter.format(progression / total);
	}

	/**
	 * Format a currency value as a pretty {@link String}, the currency type is the one from the {@link Locale}.
	 *
	 * <p><b>Note</b>: {@link Locale}, float precision and strict precision parameter attributes are used.</p>
	 * @param value the currency value
	 * @return the currency {@link String} representation
	 * @since 1.0.0
	 */
	public String formatCurrency(final double value) {
		return currencyFormatter.format(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof StringFormatter)) {
			return false;
		}
		final var other = (StringFormatter) object;
		return Equals.equals(locale, other.locale)
				&& Equals.equals(floatPrecision, other.floatPrecision)
				&& Equals.equals(strictPrecision, other.strictPrecision);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.of(
				HashCode.hashCode(locale),
				HashCode.hashCode(floatPrecision),
				HashCode.hashCode(strictPrecision)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ToString.of(
				this,
				Pair.of("locale", ToString.toString(locale)),
				Pair.of("floatPrecision", ToString.toString(floatPrecision)),
				Pair.of("strictPrecision", ToString.toString(strictPrecision))
		);
	}

	/**
	 * Get the {@link Locale}.
	 * @return the {@link Locale}
	 * @since 1.0.0
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Get the float precision.
	 * @return the float precision
	 * @since 1.0.0
	 */
	public int getFloatPrecision() {
		return floatPrecision;
	}

	/**
	 * Whether the strict precision is enabled.
	 * @return {@code true} if the strict precision is enabled
	 * @since 1.0.0
	 */
	public boolean hasStrictPrecision() {
		return strictPrecision;
	}
}