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
		assertThat(StringFormatter.DEFAULT).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.getLocale()).isSameAs(Locale.getDefault());
			assertThat(otherStringFormatter.getFloatPrecision()).isEqualTo(StringFormatter.DEFAULT_FLOAT_PRECISION);
			assertThat(otherStringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
		});
	}

	@Test
	void testConstructor() {
		assertThat(new StringFormatter(LOCALE)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.getLocale()).isSameAs(LOCALE);
			assertThat(otherStringFormatter.getFloatPrecision()).isEqualTo(StringFormatter.DEFAULT_FLOAT_PRECISION);
			assertThat(otherStringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
		});
		assertThat(new StringFormatter(LOCALE, FLOAT_PRECISION)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.getLocale()).isSameAs(LOCALE);
			assertThat(otherStringFormatter.getFloatPrecision()).isEqualTo(FLOAT_PRECISION);
			assertThat(otherStringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
		});
		assertThat(new StringFormatter(LOCALE, FLOAT_PRECISION, STRICT_PRECISION)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.getLocale()).isSameAs(LOCALE);
			assertThat(otherStringFormatter.getFloatPrecision()).isEqualTo(FLOAT_PRECISION);
			assertThat(otherStringFormatter.hasStrictPrecision()).isEqualTo(STRICT_PRECISION);
		});
	}

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new StringFormatter(null));
		assertThatIllegalArgumentException().isThrownBy(() -> new StringFormatter(LOCALE, -1));
	}

	@Test
	void testFormatLong() {
		assertThat(new StringFormatter(Locale.US)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.format(0L)).isEqualTo("0");
			assertThat(otherStringFormatter.format(-0L)).isEqualTo("0");
			assertThat(otherStringFormatter.format(10L)).isEqualTo("10");
			assertThat(otherStringFormatter.format(-10L)).isEqualTo("-10");
			assertThat(otherStringFormatter.format(1_234_567_890_123_456_789L)).isEqualTo("1,234,567,890,123,456,789");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.format(0L)).isEqualTo("0");
			assertThat(otherStringFormatter.format(-0L)).isEqualTo("0");
			assertThat(otherStringFormatter.format(10L)).isEqualTo("10");
			assertThat(otherStringFormatter.format(-10L)).isEqualTo("-10");
			if (13 > Runtime.version().feature()) {
				// Before Java 13
				assertThat(otherStringFormatter.format(1_234_567_890_123_456_789L)).isEqualTo("1 234 567 890 123 456 789");
			} else {
				// Since Java 13
				assertThat(otherStringFormatter.format(1_234_567_890_123_456_789L)).isEqualTo("1 234 567 890 123 456 789");
			}
		});
	}

	@Test
	void testFormatDouble() {
		assertThat(new StringFormatter(Locale.US)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.format(0.0d)).isEqualTo("0");
			assertThat(otherStringFormatter.format(-0.0d)).isEqualTo("-0");
			assertThat(otherStringFormatter.format(10.0d)).isEqualTo("10");
			assertThat(otherStringFormatter.format(-10.0d)).isEqualTo("-10");
			assertThat(otherStringFormatter.format(10.014d)).isEqualTo("10.01");
			assertThat(otherStringFormatter.format(10.015d)).isEqualTo("10.02");
			assertThat(otherStringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.format(0.0d)).isEqualTo("0");
			assertThat(otherStringFormatter.format(-0.0d)).isEqualTo("-0");
			assertThat(otherStringFormatter.format(10.0d)).isEqualTo("10");
			assertThat(otherStringFormatter.format(-10.0d)).isEqualTo("-10");
			assertThat(otherStringFormatter.format(10.014d)).isEqualTo("10,01");
			assertThat(otherStringFormatter.format(10.015d)).isEqualTo("10,02");
			if (13 > Runtime.version().feature()) {
				// Before Java 13
				assertThat(otherStringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1 234 567 890,12");
			} else {
				// Since Java 13
				assertThat(otherStringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1 234 567 890,12");
			}
		});
		assertThat(new StringFormatter(Locale.US, 5)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.format(0.0d)).isEqualTo("0");
			assertThat(otherStringFormatter.format(-0.0d)).isEqualTo("-0");
			assertThat(otherStringFormatter.format(10.0d)).isEqualTo("10");
			assertThat(otherStringFormatter.format(-10.0d)).isEqualTo("-10");
			assertThat(otherStringFormatter.format(10.014d)).isEqualTo("10.014");
			assertThat(otherStringFormatter.format(10.015d)).isEqualTo("10.015");
			assertThat(otherStringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12346");
		});
		assertThat(new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.format(0.0d)).isEqualTo("0.00");
			assertThat(otherStringFormatter.format(-0.0d)).isEqualTo("-0.00");
			assertThat(otherStringFormatter.format(10.0d)).isEqualTo("10.00");
			assertThat(otherStringFormatter.format(-10.0d)).isEqualTo("-10.00");
			assertThat(otherStringFormatter.format(10.014d)).isEqualTo("10.01");
			assertThat(otherStringFormatter.format(10.015d)).isEqualTo("10.02");
			assertThat(otherStringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12");
		});
	}

	@Test
	void testFormatBytes() {
		assertThat(new StringFormatter(Locale.US)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatBytes(10L)).isEqualTo("10B");
			assertThat(otherStringFormatter.formatBytes(-10L)).isEqualTo("-10B");
			assertThat(otherStringFormatter.formatBytes(1_023L)).isEqualTo("1,023B");
			assertThat(otherStringFormatter.formatBytes(1_024L)).isEqualTo("1KiB");
			assertThat(otherStringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.5KiB");
			assertThat(otherStringFormatter.formatBytes(31_141_888L)).isEqualTo("29.7MiB");
			assertThat(otherStringFormatter.formatBytes(1_288_802_304L)).isEqualTo("1.2GiB");
			assertThat(otherStringFormatter.formatBytes(1_234_567_890_123_456_789L)).isEqualTo("1.07EiB");
			assertThat(otherStringFormatter.formatBytes(10L, StringFormatter.BytePrefix.SI)).isEqualTo("10B");
			assertThat(otherStringFormatter.formatBytes(-10L, StringFormatter.BytePrefix.SI)).isEqualTo("-10B");
			assertThat(otherStringFormatter.formatBytes(999L, StringFormatter.BytePrefix.SI)).isEqualTo("999B");
			assertThat(otherStringFormatter.formatBytes(1_000L, StringFormatter.BytePrefix.SI)).isEqualTo("1kB");
			assertThat(otherStringFormatter.formatBytes(1_024L + 512L, StringFormatter.BytePrefix.SI)).isEqualTo("1.54kB");
			assertThat(otherStringFormatter.formatBytes(31_141_888L, StringFormatter.BytePrefix.SI)).isEqualTo("31.14MB");
			assertThat(otherStringFormatter.formatBytes(1_288_802_304L, StringFormatter.BytePrefix.SI)).isEqualTo("1.29GB");
			assertThat(otherStringFormatter.formatBytes(1_234_567_890_123_456_789L, StringFormatter.BytePrefix.SI)).isEqualTo("1.23EB");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatBytes(10L)).isEqualTo("10 B");
			assertThat(otherStringFormatter.formatBytes(-10L)).isEqualTo("-10 B");
			if (13 > Runtime.version().feature()) {
				// Before Java 13
				assertThat(otherStringFormatter.formatBytes(1_023L)).isEqualTo("1 023 B");
			} else {
				// Since Java 13
				assertThat(otherStringFormatter.formatBytes(1_023L)).isEqualTo("1 023 B");
			}
			assertThat(otherStringFormatter.formatBytes(1_024L)).isEqualTo("1 KiB");
			assertThat(otherStringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1,5 KiB");
			assertThat(otherStringFormatter.formatBytes(31_141_888L)).isEqualTo("29,7 MiB");
			assertThat(otherStringFormatter.formatBytes(1_288_802_304L)).isEqualTo("1,2 GiB");
			assertThat(otherStringFormatter.formatBytes(1_234_567_890_123_456_789L)).isEqualTo("1,07 EiB");
			assertThat(otherStringFormatter.formatBytes(10L, StringFormatter.BytePrefix.SI)).isEqualTo("10 B");
			assertThat(otherStringFormatter.formatBytes(-10L, StringFormatter.BytePrefix.SI)).isEqualTo("-10 B");
			assertThat(otherStringFormatter.formatBytes(999L, StringFormatter.BytePrefix.SI)).isEqualTo("999 B");
			assertThat(otherStringFormatter.formatBytes(1_000L, StringFormatter.BytePrefix.SI)).isEqualTo("1 kB");
			assertThat(otherStringFormatter.formatBytes(1_024L + 512L, StringFormatter.BytePrefix.SI)).isEqualTo("1,54 kB");
			assertThat(otherStringFormatter.formatBytes(31_141_888L, StringFormatter.BytePrefix.SI)).isEqualTo("31,14 MB");
			assertThat(otherStringFormatter.formatBytes(1_288_802_304L, StringFormatter.BytePrefix.SI)).isEqualTo("1,29 GB");
			assertThat(otherStringFormatter.formatBytes(1_234_567_890_123_456_789L, StringFormatter.BytePrefix.SI)).isEqualTo("1,23 EB");
		});
		assertThat(new StringFormatter(Locale.US, 5)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatBytes(10L)).isEqualTo("10B");
			assertThat(otherStringFormatter.formatBytes(-10L)).isEqualTo("-10B");
			assertThat(otherStringFormatter.formatBytes(1_023L)).isEqualTo("1,023B");
			assertThat(otherStringFormatter.formatBytes(1_024L)).isEqualTo("1KiB");
			assertThat(otherStringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.5KiB");
			assertThat(otherStringFormatter.formatBytes(31_141_888L)).isEqualTo("29.69922MiB");
			assertThat(otherStringFormatter.formatBytes(1_288_802_304L)).isEqualTo("1.20029GiB");
			assertThat(otherStringFormatter.formatBytes(1_234_567_890_123_456_789L)).isEqualTo("1.07082EiB");
			assertThat(otherStringFormatter.formatBytes(10L, StringFormatter.BytePrefix.SI)).isEqualTo("10B");
			assertThat(otherStringFormatter.formatBytes(-10L, StringFormatter.BytePrefix.SI)).isEqualTo("-10B");
			assertThat(otherStringFormatter.formatBytes(999L, StringFormatter.BytePrefix.SI)).isEqualTo("999B");
			assertThat(otherStringFormatter.formatBytes(1_000L, StringFormatter.BytePrefix.SI)).isEqualTo("1kB");
			assertThat(otherStringFormatter.formatBytes(1_024L + 512L, StringFormatter.BytePrefix.SI)).isEqualTo("1.536kB");
			assertThat(otherStringFormatter.formatBytes(31_141_888L, StringFormatter.BytePrefix.SI)).isEqualTo("31.14189MB");
			assertThat(otherStringFormatter.formatBytes(1_288_802_304L, StringFormatter.BytePrefix.SI)).isEqualTo("1.2888GB");
			assertThat(otherStringFormatter.formatBytes(1_234_567_890_123_456_789L, StringFormatter.BytePrefix.SI)).isEqualTo("1.23457EB");
		});
		assertThat(new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatBytes(10L)).isEqualTo("10.00B");
			assertThat(otherStringFormatter.formatBytes(-10L)).isEqualTo("-10.00B");
			assertThat(otherStringFormatter.formatBytes(1_023L)).isEqualTo("1,023.00B");
			assertThat(otherStringFormatter.formatBytes(1_024L)).isEqualTo("1.00KiB");
			assertThat(otherStringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.50KiB");
			assertThat(otherStringFormatter.formatBytes(31_141_888L)).isEqualTo("29.70MiB");
			assertThat(otherStringFormatter.formatBytes(1_288_802_304L)).isEqualTo("1.20GiB");
			assertThat(otherStringFormatter.formatBytes(1_234_567_890_123_456_789L)).isEqualTo("1.07EiB");
			assertThat(otherStringFormatter.formatBytes(10L, StringFormatter.BytePrefix.SI)).isEqualTo("10.00B");
			assertThat(otherStringFormatter.formatBytes(-10L, StringFormatter.BytePrefix.SI)).isEqualTo("-10.00B");
			assertThat(otherStringFormatter.formatBytes(999L, StringFormatter.BytePrefix.SI)).isEqualTo("999.00B");
			assertThat(otherStringFormatter.formatBytes(1_000L, StringFormatter.BytePrefix.SI)).isEqualTo("1.00kB");
			assertThat(otherStringFormatter.formatBytes(1_024L + 512L, StringFormatter.BytePrefix.SI)).isEqualTo("1.54kB");
			assertThat(otherStringFormatter.formatBytes(31_141_888L, StringFormatter.BytePrefix.SI)).isEqualTo("31.14MB");
			assertThat(otherStringFormatter.formatBytes(1_288_802_304L, StringFormatter.BytePrefix.SI)).isEqualTo("1.29GB");
			assertThat(otherStringFormatter.formatBytes(1_234_567_890_123_456_789L, StringFormatter.BytePrefix.SI)).isEqualTo("1.23EB");
		});
	}

	@Test
	void testFormatBytesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> stringFormatter.formatBytes(0L, null));
	}

	// https://programming.guide/worlds-most-copied-so-snippet.html
	@Test
	void testFormatBytesIssue() {
		assertThat(new StringFormatter(Locale.US, 1, true)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatBytes(999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("1.0MB");
			assertThat(otherStringFormatter.formatBytes(999_949_999_999_999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("999.9PB");
			assertThat(otherStringFormatter.formatBytes(Long.MIN_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("-9.2EB");
			assertThat(otherStringFormatter.formatBytes(Long.MAX_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("9.2EB");
		});
		assertThat(new StringFormatter(Locale.US, 2, true)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatBytes(999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("1.00MB");
			assertThat(otherStringFormatter.formatBytes(999_994_999_999_999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("999.99PB");
			assertThat(otherStringFormatter.formatBytes(Long.MIN_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("-9.22EB");
			assertThat(otherStringFormatter.formatBytes(Long.MAX_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("9.22EB");
		});
		assertThat(new StringFormatter(Locale.US, 11, true)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatBytes(999_999L, StringFormatter.BytePrefix.SI)).isEqualTo("0.99999900000MB");
			assertThat(otherStringFormatter.formatBytes(999_999_999_999_994_999L, StringFormatter.BytePrefix.SI)).isEqualTo("999.99999999999PB");
			assertThat(otherStringFormatter.formatBytes(Long.MIN_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("-9.22337203685EB");
			assertThat(otherStringFormatter.formatBytes(Long.MAX_VALUE, StringFormatter.BytePrefix.SI)).isEqualTo("9.22337203685EB");
		});
	}

	@Test
	void testFormatPercent() {
		assertThat(new StringFormatter(Locale.US)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0%");
			assertThat(otherStringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10%");
			assertThat(otherStringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33%");
			assertThat(otherStringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66%");
			assertThat(otherStringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.9%");
			assertThat(otherStringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100%");
			assertThat(otherStringFormatter.formatPercent(StrictMath.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99%");
			assertThat(otherStringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100%");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0 %");
			assertThat(otherStringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10 %");
			assertThat(otherStringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33,33 %");
			assertThat(otherStringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66,66 %");
			assertThat(otherStringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99,9 %");
			assertThat(otherStringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100 %");
			assertThat(otherStringFormatter.formatPercent(StrictMath.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99,99 %");
			assertThat(otherStringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100 %");
		});
		assertThat(new StringFormatter(Locale.US, 5)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0%");
			assertThat(otherStringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10%");
			assertThat(otherStringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33333%");
			assertThat(otherStringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66666%");
			assertThat(otherStringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.9%");
			assertThat(otherStringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100%");
			assertThat(otherStringFormatter.formatPercent(StrictMath.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99999%");
			assertThat(otherStringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100%");
		});
		assertThat(new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0.00%");
			assertThat(otherStringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10.00%");
			assertThat(otherStringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33%");
			assertThat(otherStringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66%");
			assertThat(otherStringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.90%");
			assertThat(otherStringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100.00%");
			assertThat(otherStringFormatter.formatPercent(StrictMath.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99%");
			assertThat(otherStringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100.00%");
		});
	}

	@Test
	void testFormatPercentInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> stringFormatter.formatPercent(-1, 10));
		assertThatIllegalArgumentException().isThrownBy(() -> stringFormatter.formatPercent(11, 10));
	}

	@Test
	void testFormatCurrency() {
		assertThat(new StringFormatter(Locale.US)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatCurrency(0.0d)).isEqualTo("$0");
			assertThat(otherStringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0");
			assertThat(otherStringFormatter.formatCurrency(10.0d)).isEqualTo("$10");
			assertThat(otherStringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10");
			assertThat(otherStringFormatter.formatCurrency(10.014d)).isEqualTo("$10.01");
			assertThat(otherStringFormatter.formatCurrency(10.015d)).isEqualTo("$10.02");
			assertThat(otherStringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12");
		});
		assertThat(new StringFormatter(Locale.FRANCE)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatCurrency(0.0d)).isEqualTo("0 €");
			assertThat(otherStringFormatter.formatCurrency(-0.0d)).isEqualTo("-0 €");
			assertThat(otherStringFormatter.formatCurrency(10.0d)).isEqualTo("10 €");
			assertThat(otherStringFormatter.formatCurrency(-10.0d)).isEqualTo("-10 €");
			assertThat(otherStringFormatter.formatCurrency(10.014d)).isEqualTo("10,01 €");
			assertThat(otherStringFormatter.formatCurrency(10.015d)).isEqualTo("10,02 €");
			if (13 > Runtime.version().feature()) {
				// Before Java 13
				assertThat(otherStringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-1 234 567 890,12 €");
			} else {
				// Since Java 13
				assertThat(otherStringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-1 234 567 890,12 €");
			}
		});
		assertThat(new StringFormatter(Locale.US, 5)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatCurrency(0.0d)).isEqualTo("$0");
			assertThat(otherStringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0");
			assertThat(otherStringFormatter.formatCurrency(10.0d)).isEqualTo("$10");
			assertThat(otherStringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10");
			assertThat(otherStringFormatter.formatCurrency(10.014d)).isEqualTo("$10.014");
			assertThat(otherStringFormatter.formatCurrency(10.015d)).isEqualTo("$10.015");
			assertThat(otherStringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12346");
		});
		assertThat(new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter.formatCurrency(0.0d)).isEqualTo("$0.00");
			assertThat(otherStringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0.00");
			assertThat(otherStringFormatter.formatCurrency(10.0d)).isEqualTo("$10.00");
			assertThat(otherStringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10.00");
			assertThat(otherStringFormatter.formatCurrency(10.014d)).isEqualTo("$10.01");
			assertThat(otherStringFormatter.formatCurrency(10.015d)).isEqualTo("$10.02");
			assertThat(otherStringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12");
		});
	}

	@Test
	void testEqualsAndHashCodeAndToString() {
		assertThat(stringFormatter.equals(stringFormatter)).isTrue();
		assertThat(stringFormatter).isNotEqualTo(new Object());
		assertThat(new StringFormatter(LOCALE, FLOAT_PRECISION, STRICT_PRECISION)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter).isNotSameAs(stringFormatter);
			assertThat(otherStringFormatter).isEqualTo(stringFormatter);
			assertThat(otherStringFormatter).hasSameHashCodeAs(stringFormatter);
			assertThat(otherStringFormatter).hasToString(stringFormatter.toString());
		});
		assertThat(new StringFormatter(Locale.FRANCE, FLOAT_PRECISION, STRICT_PRECISION)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter).isNotSameAs(stringFormatter);
			assertThat(otherStringFormatter).isNotEqualTo(stringFormatter);
			assertThat(otherStringFormatter).doesNotHaveSameHashCodeAs(stringFormatter);
			assertThat(otherStringFormatter).doesNotHaveToString(stringFormatter.toString());
		});
		assertThat(new StringFormatter(LOCALE, 5, STRICT_PRECISION)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter).isNotSameAs(stringFormatter);
			assertThat(otherStringFormatter).isNotEqualTo(stringFormatter);
			assertThat(otherStringFormatter).doesNotHaveSameHashCodeAs(stringFormatter);
			assertThat(otherStringFormatter).doesNotHaveToString(stringFormatter.toString());
		});
		assertThat(new StringFormatter(LOCALE, FLOAT_PRECISION, true)).satisfies(otherStringFormatter -> {
			assertThat(otherStringFormatter).isNotSameAs(stringFormatter);
			assertThat(otherStringFormatter).isNotEqualTo(stringFormatter);
			assertThat(otherStringFormatter).doesNotHaveSameHashCodeAs(stringFormatter);
			assertThat(otherStringFormatter).doesNotHaveToString(stringFormatter.toString());
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