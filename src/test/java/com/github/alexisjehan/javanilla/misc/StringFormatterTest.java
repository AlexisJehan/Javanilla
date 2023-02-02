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
package com.github.alexisjehan.javanilla.misc;

import com.github.alexisjehan.javanilla.io.Serializables;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class StringFormatterTest {

	private static final Locale LOCALE = Locale.US;
	private static final int FLOAT_PRECISION = StringFormatter.DEFAULT_FLOAT_PRECISION;
	private static final boolean STRICT_PRECISION = StringFormatter.DEFAULT_STRICT_PRECISION;

	private final StringFormatter stringFormatter = new StringFormatter(LOCALE, FLOAT_PRECISION, STRICT_PRECISION);

	@Test
	void testDefault() {
		final var stringFormatter = StringFormatter.DEFAULT;
		assertThat(stringFormatter.getLocale()).isSameAs(Locale.getDefault());
		assertThat(stringFormatter.getFloatPrecision()).isEqualTo(StringFormatter.DEFAULT_FLOAT_PRECISION);
		assertThat(stringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
	}

	@Test
	void testConstructor() {
		assertThat(new StringFormatter(LOCALE)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.getLocale()).isSameAs(LOCALE);
			assertThat(stringFormatter.getFloatPrecision()).isEqualTo(StringFormatter.DEFAULT_FLOAT_PRECISION);
			assertThat(stringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
		});
		assertThat(new StringFormatter(LOCALE, FLOAT_PRECISION)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.getLocale()).isSameAs(LOCALE);
			assertThat(stringFormatter.getFloatPrecision()).isEqualTo(FLOAT_PRECISION);
			assertThat(stringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
		});
		assertThat(new StringFormatter(LOCALE, FLOAT_PRECISION, STRICT_PRECISION)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.getLocale()).isSameAs(LOCALE);
			assertThat(stringFormatter.getFloatPrecision()).isEqualTo(FLOAT_PRECISION);
			assertThat(stringFormatter.hasStrictPrecision()).isEqualTo(STRICT_PRECISION);
		});
	}

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new StringFormatter(null));
		assertThatIllegalArgumentException().isThrownBy(() -> new StringFormatter(LOCALE, -1));
	}

	@Test
	void testFormatLong() {
		assertThat(new StringFormatter(Locale.US)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.format(0L)).isEqualTo("0");
			assertThat(stringFormatter.format(-0L)).isEqualTo("0");
			assertThat(stringFormatter.format(10L)).isEqualTo("10");
			assertThat(stringFormatter.format(-10L)).isEqualTo("-10");
			assertThat(stringFormatter.format(1_234_567_890_123_456_789L)).isEqualTo("1,234,567,890,123,456,789");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.format(0L)).isEqualTo("0");
			assertThat(stringFormatter.format(-0L)).isEqualTo("0");
			assertThat(stringFormatter.format(10L)).isEqualTo("10");
			assertThat(stringFormatter.format(-10L)).isEqualTo("-10");
			if (13 > Runtime.version().feature()) {
				// Before Java 13
				assertThat(stringFormatter.format(1_234_567_890_123_456_789L)).isEqualTo("1\u00a0234\u00a0567\u00a0890\u00a0123\u00a0456\u00a0789");
			} else {
				// Since Java 13
				assertThat(stringFormatter.format(1_234_567_890_123_456_789L)).isEqualTo("1\u202f234\u202f567\u202f890\u202f123\u202f456\u202f789");
			}
		});
	}

	@Test
	void testFormatDouble() {
		assertThat(new StringFormatter(Locale.US)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.format(0.0d)).isEqualTo("0");
			assertThat(stringFormatter.format(-0.0d)).isEqualTo("-0");
			assertThat(stringFormatter.format(10.0d)).isEqualTo("10");
			assertThat(stringFormatter.format(-10.0d)).isEqualTo("-10");
			assertThat(stringFormatter.format(10.014d)).isEqualTo("10.01");
			assertThat(stringFormatter.format(10.015d)).isEqualTo("10.02");
			assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.format(0.0d)).isEqualTo("0");
			assertThat(stringFormatter.format(-0.0d)).isEqualTo("-0");
			assertThat(stringFormatter.format(10.0d)).isEqualTo("10");
			assertThat(stringFormatter.format(-10.0d)).isEqualTo("-10");
			assertThat(stringFormatter.format(10.014d)).isEqualTo("10,01");
			assertThat(stringFormatter.format(10.015d)).isEqualTo("10,02");
			if (13 > Runtime.version().feature()) {
				// Before Java 13
				assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1\u00a0234\u00a0567\u00a0890,12");
			} else {
				// Since Java 13
				assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1\u202f234\u202f567\u202f890,12");
			}
		});
		assertThat(new StringFormatter(Locale.US, 5)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.format(0.0d)).isEqualTo("0");
			assertThat(stringFormatter.format(-0.0d)).isEqualTo("-0");
			assertThat(stringFormatter.format(10.0d)).isEqualTo("10");
			assertThat(stringFormatter.format(-10.0d)).isEqualTo("-10");
			assertThat(stringFormatter.format(10.014d)).isEqualTo("10.014");
			assertThat(stringFormatter.format(10.015d)).isEqualTo("10.015");
			assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12346");
		});
		assertThat(new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.format(0.0d)).isEqualTo("0.00");
			assertThat(stringFormatter.format(-0.0d)).isEqualTo("-0.00");
			assertThat(stringFormatter.format(10.0d)).isEqualTo("10.00");
			assertThat(stringFormatter.format(-10.0d)).isEqualTo("-10.00");
			assertThat(stringFormatter.format(10.014d)).isEqualTo("10.01");
			assertThat(stringFormatter.format(10.015d)).isEqualTo("10.02");
			assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12");
		});
	}

	@Test
	void testFormatBytes() {
		assertThat(new StringFormatter(Locale.US)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatBytes(10L)).isEqualTo("10B");
			assertThat(stringFormatter.formatBytes(-10L)).isEqualTo("-10B");
			assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1,023B");
			assertThat(stringFormatter.formatBytes(1_024L)).isEqualTo("1KiB");
			assertThat(stringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.5KiB");
			assertThat(stringFormatter.formatBytes(31_141_888L)).isEqualTo("29.7MiB");
			assertThat(stringFormatter.formatBytes(1_288_802_304L)).isEqualTo("1.2GiB");
			assertThat(stringFormatter.formatBytes(1_234_567_890_123_456_789L)).isEqualTo("1.07EiB");
			assertThat(stringFormatter.formatBytes(10L, StringFormatter.BytePrefix.SI)).isEqualTo("10B");
			assertThat(stringFormatter.formatBytes(-10L, StringFormatter.BytePrefix.SI)).isEqualTo("-10B");
			assertThat(stringFormatter.formatBytes(999L, StringFormatter.BytePrefix.SI)).isEqualTo("999B");
			assertThat(stringFormatter.formatBytes(1_000L, StringFormatter.BytePrefix.SI)).isEqualTo("1kB");
			assertThat(stringFormatter.formatBytes(1_024L + 512L, StringFormatter.BytePrefix.SI)).isEqualTo("1.54kB");
			assertThat(stringFormatter.formatBytes(31_141_888L, StringFormatter.BytePrefix.SI)).isEqualTo("31.14MB");
			assertThat(stringFormatter.formatBytes(1_288_802_304L, StringFormatter.BytePrefix.SI)).isEqualTo("1.29GB");
			assertThat(stringFormatter.formatBytes(1_234_567_890_123_456_789L, StringFormatter.BytePrefix.SI)).isEqualTo("1.23EB");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatBytes(10L)).isEqualTo("10\u00a0B");
			assertThat(stringFormatter.formatBytes(-10L)).isEqualTo("-10\u00a0B");
			if (13 > Runtime.version().feature()) {
				// Before Java 13
				assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1\u00a0023\u00a0B");
			} else {
				// Since Java 13
				assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1\u202f023\u00a0B");
			}
			assertThat(stringFormatter.formatBytes(1_024L)).isEqualTo("1\u00a0KiB");
			assertThat(stringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1,5\u00a0KiB");
			assertThat(stringFormatter.formatBytes(31_141_888L)).isEqualTo("29,7\u00a0MiB");
			assertThat(stringFormatter.formatBytes(1_288_802_304L)).isEqualTo("1,2\u00a0GiB");
			assertThat(stringFormatter.formatBytes(1_234_567_890_123_456_789L)).isEqualTo("1,07\u00a0EiB");
			assertThat(stringFormatter.formatBytes(10L, StringFormatter.BytePrefix.SI)).isEqualTo("10\u00a0B");
			assertThat(stringFormatter.formatBytes(-10L, StringFormatter.BytePrefix.SI)).isEqualTo("-10\u00a0B");
			assertThat(stringFormatter.formatBytes(999L, StringFormatter.BytePrefix.SI)).isEqualTo("999\u00a0B");
			assertThat(stringFormatter.formatBytes(1_000L, StringFormatter.BytePrefix.SI)).isEqualTo("1\u00a0kB");
			assertThat(stringFormatter.formatBytes(1_024L + 512L, StringFormatter.BytePrefix.SI)).isEqualTo("1,54\u00a0kB");
			assertThat(stringFormatter.formatBytes(31_141_888L, StringFormatter.BytePrefix.SI)).isEqualTo("31,14\u00a0MB");
			assertThat(stringFormatter.formatBytes(1_288_802_304L, StringFormatter.BytePrefix.SI)).isEqualTo("1,29\u00a0GB");
			assertThat(stringFormatter.formatBytes(1_234_567_890_123_456_789L, StringFormatter.BytePrefix.SI)).isEqualTo("1,23\u00a0EB");
		});
		assertThat(new StringFormatter(Locale.US, 5)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatBytes(10L)).isEqualTo("10B");
			assertThat(stringFormatter.formatBytes(-10L)).isEqualTo("-10B");
			assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1,023B");
			assertThat(stringFormatter.formatBytes(1_024L)).isEqualTo("1KiB");
			assertThat(stringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.5KiB");
			assertThat(stringFormatter.formatBytes(31_141_888L)).isEqualTo("29.69922MiB");
			assertThat(stringFormatter.formatBytes(1_288_802_304L)).isEqualTo("1.20029GiB");
			assertThat(stringFormatter.formatBytes(1_234_567_890_123_456_789L)).isEqualTo("1.07082EiB");
			assertThat(stringFormatter.formatBytes(10L, StringFormatter.BytePrefix.SI)).isEqualTo("10B");
			assertThat(stringFormatter.formatBytes(-10L, StringFormatter.BytePrefix.SI)).isEqualTo("-10B");
			assertThat(stringFormatter.formatBytes(999L, StringFormatter.BytePrefix.SI)).isEqualTo("999B");
			assertThat(stringFormatter.formatBytes(1_000L, StringFormatter.BytePrefix.SI)).isEqualTo("1kB");
			assertThat(stringFormatter.formatBytes(1_024L + 512L, StringFormatter.BytePrefix.SI)).isEqualTo("1.536kB");
			assertThat(stringFormatter.formatBytes(31_141_888L, StringFormatter.BytePrefix.SI)).isEqualTo("31.14189MB");
			assertThat(stringFormatter.formatBytes(1_288_802_304L, StringFormatter.BytePrefix.SI)).isEqualTo("1.2888GB");
			assertThat(stringFormatter.formatBytes(1_234_567_890_123_456_789L, StringFormatter.BytePrefix.SI)).isEqualTo("1.23457EB");
		});
		assertThat(new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatBytes(10L)).isEqualTo("10.00B");
			assertThat(stringFormatter.formatBytes(-10L)).isEqualTo("-10.00B");
			assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1,023.00B");
			assertThat(stringFormatter.formatBytes(1_024L)).isEqualTo("1.00KiB");
			assertThat(stringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.50KiB");
			assertThat(stringFormatter.formatBytes(31_141_888L)).isEqualTo("29.70MiB");
			assertThat(stringFormatter.formatBytes(1_288_802_304L)).isEqualTo("1.20GiB");
			assertThat(stringFormatter.formatBytes(1_234_567_890_123_456_789L)).isEqualTo("1.07EiB");
			assertThat(stringFormatter.formatBytes(10L, StringFormatter.BytePrefix.SI)).isEqualTo("10.00B");
			assertThat(stringFormatter.formatBytes(-10L, StringFormatter.BytePrefix.SI)).isEqualTo("-10.00B");
			assertThat(stringFormatter.formatBytes(999L, StringFormatter.BytePrefix.SI)).isEqualTo("999.00B");
			assertThat(stringFormatter.formatBytes(1_000L, StringFormatter.BytePrefix.SI)).isEqualTo("1.00kB");
			assertThat(stringFormatter.formatBytes(1_024L + 512L, StringFormatter.BytePrefix.SI)).isEqualTo("1.54kB");
			assertThat(stringFormatter.formatBytes(31_141_888L, StringFormatter.BytePrefix.SI)).isEqualTo("31.14MB");
			assertThat(stringFormatter.formatBytes(1_288_802_304L, StringFormatter.BytePrefix.SI)).isEqualTo("1.29GB");
			assertThat(stringFormatter.formatBytes(1_234_567_890_123_456_789L, StringFormatter.BytePrefix.SI)).isEqualTo("1.23EB");
		});
	}

	@Test
	void testFormatBytesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> StringFormatter.DEFAULT.formatBytes(0L, null));
	}

	// https://programming.guide/worlds-most-copied-so-snippet.html
	@Test
	void testFormatBytesIssue() {
		assertThat(new StringFormatter(Locale.US, 1, true)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatBytes(999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("1.0MB");
			assertThat(stringFormatter.formatBytes(999_949_999_999_999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("999.9PB");
			assertThat(stringFormatter.formatBytes(Long.MIN_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("-9.2EB");
			assertThat(stringFormatter.formatBytes(Long.MAX_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("9.2EB");
		});
		assertThat(new StringFormatter(Locale.US, 2, true)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatBytes(999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("1.00MB");
			assertThat(stringFormatter.formatBytes(999_994_999_999_999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("999.99PB");
			assertThat(stringFormatter.formatBytes(Long.MIN_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("-9.22EB");
			assertThat(stringFormatter.formatBytes(Long.MAX_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("9.22EB");
		});
		assertThat(new StringFormatter(Locale.US, 11, true)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatBytes(999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("0.99999900000MB");
			assertThat(stringFormatter.formatBytes(999_999_999_999_994_999L, StringFormatter.BytePrefix.SI)).isEqualTo("999.99999999999PB");
			assertThat(stringFormatter.formatBytes(Long.MIN_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("-9.22337203685EB");
			assertThat(stringFormatter.formatBytes(Long.MAX_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("9.22337203685EB");
		});
	}

	@Test
	void testFormatPercent() {
		assertThat(new StringFormatter(Locale.US)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0%");
			assertThat(stringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10%");
			assertThat(stringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33%");
			assertThat(stringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66%");
			assertThat(stringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.9%");
			assertThat(stringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100%");
			assertThat(stringFormatter.formatPercent(StrictMath.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99%");
			assertThat(stringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100%");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0\u00a0%");
			assertThat(stringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10\u00a0%");
			assertThat(stringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33,33\u00a0%");
			assertThat(stringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66,66\u00a0%");
			assertThat(stringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99,9\u00a0%");
			assertThat(stringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100\u00a0%");
			assertThat(stringFormatter.formatPercent(StrictMath.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99,99\u00a0%");
			assertThat(stringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100\u00a0%");
		});
		assertThat(new StringFormatter(Locale.US, 5)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0%");
			assertThat(stringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10%");
			assertThat(stringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33333%");
			assertThat(stringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66666%");
			assertThat(stringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.9%");
			assertThat(stringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100%");
			assertThat(stringFormatter.formatPercent(StrictMath.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99999%");
			assertThat(stringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100%");
		});
		assertThat(new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0.00%");
			assertThat(stringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10.00%");
			assertThat(stringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33%");
			assertThat(stringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66%");
			assertThat(stringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.90%");
			assertThat(stringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100.00%");
			assertThat(stringFormatter.formatPercent(StrictMath.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99%");
			assertThat(stringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100.00%");
		});
	}

	@Test
	void testFormatPercentInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> StringFormatter.DEFAULT.formatPercent(-1, 10));
		assertThatIllegalArgumentException().isThrownBy(() -> StringFormatter.DEFAULT.formatPercent(11, 10));
	}

	@Test
	void testFormatCurrency() {
		assertThat(new StringFormatter(Locale.US)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatCurrency(0.0d)).isEqualTo("$0");
			assertThat(stringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0");
			assertThat(stringFormatter.formatCurrency(10.0d)).isEqualTo("$10");
			assertThat(stringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10");
			assertThat(stringFormatter.formatCurrency(10.014d)).isEqualTo("$10.01");
			assertThat(stringFormatter.formatCurrency(10.015d)).isEqualTo("$10.02");
			assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatCurrency(0.0d)).isEqualTo("0\u00a0€");
			assertThat(stringFormatter.formatCurrency(-0.0d)).isEqualTo("-0\u00a0€");
			assertThat(stringFormatter.formatCurrency(10.0d)).isEqualTo("10\u00a0€");
			assertThat(stringFormatter.formatCurrency(-10.0d)).isEqualTo("-10\u00a0€");
			assertThat(stringFormatter.formatCurrency(10.014d)).isEqualTo("10,01\u00a0€");
			assertThat(stringFormatter.formatCurrency(10.015d)).isEqualTo("10,02\u00a0€");
			if (13 > Runtime.version().feature()) {
				// Before Java 13
				assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-1\u00a0234\u00a0567\u00a0890,12\u00a0€");
			} else {
				// Since Java 13
				assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-1\u202f234\u202f567\u202f890,12\u00a0€");
			}
		});
		assertThat(new StringFormatter(Locale.US, 5)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatCurrency(0.0d)).isEqualTo("$0");
			assertThat(stringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0");
			assertThat(stringFormatter.formatCurrency(10.0d)).isEqualTo("$10");
			assertThat(stringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10");
			assertThat(stringFormatter.formatCurrency(10.014d)).isEqualTo("$10.014");
			assertThat(stringFormatter.formatCurrency(10.015d)).isEqualTo("$10.015");
			assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12346");
		});
		assertThat(new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true)).satisfies(stringFormatter -> {
			assertThat(stringFormatter.formatCurrency(0.0d)).isEqualTo("$0.00");
			assertThat(stringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0.00");
			assertThat(stringFormatter.formatCurrency(10.0d)).isEqualTo("$10.00");
			assertThat(stringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10.00");
			assertThat(stringFormatter.formatCurrency(10.014d)).isEqualTo("$10.01");
			assertThat(stringFormatter.formatCurrency(10.015d)).isEqualTo("$10.02");
			assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12");
		});
	}

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(stringFormatter.equals(stringFormatter)).isTrue();
		assertThat(stringFormatter).isNotEqualTo(new Object());
		assertThat(new StringFormatter(LOCALE, FLOAT_PRECISION, STRICT_PRECISION)).satisfies(otherStringFormatter -> {
			assertThat(stringFormatter).isNotSameAs(otherStringFormatter);
			assertThat(stringFormatter).isEqualTo(otherStringFormatter);
			assertThat(stringFormatter).hasSameHashCodeAs(otherStringFormatter);
			assertThat(stringFormatter).hasToString(otherStringFormatter.toString());
		});
		assertThat(new StringFormatter(Locale.FRANCE, FLOAT_PRECISION, STRICT_PRECISION)).satisfies(otherStringFormatter -> {
			assertThat(stringFormatter).isNotSameAs(otherStringFormatter);
			assertThat(stringFormatter).isNotEqualTo(otherStringFormatter);
			assertThat(stringFormatter).doesNotHaveSameHashCodeAs(otherStringFormatter);
			assertThat(stringFormatter).doesNotHaveToString(otherStringFormatter.toString());
		});
		assertThat(new StringFormatter(LOCALE, 5, STRICT_PRECISION)).satisfies(otherStringFormatter -> {
			assertThat(stringFormatter).isNotSameAs(otherStringFormatter);
			assertThat(stringFormatter).isNotEqualTo(otherStringFormatter);
			assertThat(stringFormatter).doesNotHaveSameHashCodeAs(otherStringFormatter);
			assertThat(stringFormatter).doesNotHaveToString(otherStringFormatter.toString());
		});
		assertThat(new StringFormatter(LOCALE, FLOAT_PRECISION, true)).satisfies(otherStringFormatter -> {
			assertThat(stringFormatter).isNotSameAs(otherStringFormatter);
			assertThat(stringFormatter).isNotEqualTo(otherStringFormatter);
			assertThat(stringFormatter).doesNotHaveSameHashCodeAs(otherStringFormatter);
			assertThat(stringFormatter).doesNotHaveToString(otherStringFormatter.toString());
		});
	}

	@Test
	void testGetters() {
		assertThat(stringFormatter.getLocale()).isEqualTo(LOCALE);
		assertThat(stringFormatter.getFloatPrecision()).isEqualTo(FLOAT_PRECISION);
		assertThat(stringFormatter.hasStrictPrecision()).isEqualTo(STRICT_PRECISION);
	}

	@Test
	void testSerializable() {
		assertThat(Serializables.<StringFormatter>deserialize(Serializables.serialize(stringFormatter))).isEqualTo(stringFormatter);
	}
}