/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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

/**
 * <p>{@link StringFormatter} unit tests.</p>
 */
final class StringFormatterTest {

	@Test
	void testDefault() {
		final var stringFormatter = StringFormatter.DEFAULT;
		assertThat(stringFormatter.getLocale()).isSameAs(Locale.getDefault());
		assertThat(stringFormatter.getFloatPrecision()).isEqualTo(StringFormatter.DEFAULT_FLOAT_PRECISION);
		assertThat(stringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
	}

	@Test
	void testConstructorLocale() {
		final var stringFormatter = new StringFormatter(Locale.US);
		assertThat(stringFormatter.getLocale()).isSameAs(Locale.US);
		assertThat(stringFormatter.getFloatPrecision()).isEqualTo(StringFormatter.DEFAULT_FLOAT_PRECISION);
		assertThat(stringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
	}

	@Test
	void testConstructorLocaleAndFloatPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, 10);
		assertThat(stringFormatter.getLocale()).isSameAs(Locale.US);
		assertThat(stringFormatter.getFloatPrecision()).isEqualTo(10);
		assertThat(stringFormatter.hasStrictPrecision()).isEqualTo(StringFormatter.DEFAULT_STRICT_PRECISION);
	}

	@Test
	void testCompleteConstructor() {
		final var stringFormatter = new StringFormatter(Locale.US, 10, true);
		assertThat(stringFormatter.getLocale()).isSameAs(Locale.US);
		assertThat(stringFormatter.getFloatPrecision()).isEqualTo(10);
		assertThat(stringFormatter.hasStrictPrecision()).isTrue();
	}

	@Test
	void testConstructorInvalid() {
		assertThatNullPointerException().isThrownBy(() -> new StringFormatter(null));
		assertThatIllegalArgumentException().isThrownBy(() -> new StringFormatter(Locale.getDefault(), -1));
	}

	@Test
	void testFormatLong() {
		final var stringFormatter = new StringFormatter(Locale.US);
		assertThat(stringFormatter.format(0L)).isEqualTo("0");
		assertThat(stringFormatter.format(-0L)).isEqualTo("0");
		assertThat(stringFormatter.format(10L)).isEqualTo("10");
		assertThat(stringFormatter.format(-10L)).isEqualTo("-10");
		assertThat(stringFormatter.format(1_234_567_890_123_456_789L)).isEqualTo("1,234,567,890,123,456,789");
	}

	@Test
	void testFormatLongLocale() {
		final var stringFormatter = new StringFormatter(Locale.FRANCE);
		assertThat(stringFormatter.format(0L)).isEqualTo("0");
		assertThat(stringFormatter.format(-0L)).isEqualTo("0");
		assertThat(stringFormatter.format(10L)).isEqualTo("10");
		assertThat(stringFormatter.format(-10L)).isEqualTo("-10");
		assertThat(stringFormatter.format(1_234_567_890_123_456_789L)).isEqualTo("1\u00a0234\u00a0567\u00a0890\u00a0123\u00a0456\u00a0789");
	}

	@Test
	void testFormatDouble() {
		final var stringFormatter = new StringFormatter(Locale.US);
		assertThat(stringFormatter.format(0.0d)).isEqualTo("0");
		assertThat(stringFormatter.format(-0.0d)).isEqualTo("-0");
		assertThat(stringFormatter.format(10.0d)).isEqualTo("10");
		assertThat(stringFormatter.format(-10.0d)).isEqualTo("-10");
		assertThat(stringFormatter.format(10.014d)).isEqualTo("10.01");
		assertThat(stringFormatter.format(10.015d)).isEqualTo("10.02");
		assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12");
	}

	@Test
	void testFormatDoubleLocale() {
		final var stringFormatter = new StringFormatter(Locale.FRANCE);
		assertThat(stringFormatter.format(0.0d)).isEqualTo("0");
		assertThat(stringFormatter.format(-0.0d)).isEqualTo("-0");
		assertThat(stringFormatter.format(10.0d)).isEqualTo("10");
		assertThat(stringFormatter.format(-10.0d)).isEqualTo("-10");
		assertThat(stringFormatter.format(10.014d)).isEqualTo("10,01");
		assertThat(stringFormatter.format(10.015d)).isEqualTo("10,02");
		assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1\u00a0234\u00a0567\u00a0890,12");
	}

	@Test
	void testFormatDoubleFloatPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, 5);
		assertThat(stringFormatter.format(0.0d)).isEqualTo("0");
		assertThat(stringFormatter.format(-0.0d)).isEqualTo("-0");
		assertThat(stringFormatter.format(10.0d)).isEqualTo("10");
		assertThat(stringFormatter.format(-10.0d)).isEqualTo("-10");
		assertThat(stringFormatter.format(10.014d)).isEqualTo("10.014");
		assertThat(stringFormatter.format(10.015d)).isEqualTo("10.015");
		assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12346");
	}

	@Test
	void testFormatDoubleStrictPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true);
		assertThat(stringFormatter.format(0.0d)).isEqualTo("0.00");
		assertThat(stringFormatter.format(-0.0d)).isEqualTo("-0.00");
		assertThat(stringFormatter.format(10.0d)).isEqualTo("10.00");
		assertThat(stringFormatter.format(-10.0d)).isEqualTo("-10.00");
		assertThat(stringFormatter.format(10.014d)).isEqualTo("10.01");
		assertThat(stringFormatter.format(10.015d)).isEqualTo("10.02");
		assertThat(stringFormatter.format(-1_234_567_890.123456789d)).isEqualTo("-1,234,567,890.12");
	}

	@Test
	void testFormatBytes() {
		final var stringFormatter = new StringFormatter(Locale.US);
		assertThat(stringFormatter.formatBytes(10L)).isEqualTo("10B");
		assertThat(stringFormatter.formatBytes(-10L)).isEqualTo("-10B");
		assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1,023B");
		assertThat(stringFormatter.formatBytes(1_024L)).isEqualTo("1kiB");
		assertThat(stringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.5kiB");
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
	}

	@Test
	void testFormatBytesLocale() {
		final var stringFormatter = new StringFormatter(Locale.FRANCE);
		assertThat(stringFormatter.formatBytes(10L)).isEqualTo("10\u00a0B");
		assertThat(stringFormatter.formatBytes(-10L)).isEqualTo("-10\u00a0B");
		assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1\u00a0023\u00a0B");
		assertThat(stringFormatter.formatBytes(1_024L)).isEqualTo("1\u00a0kiB");
		assertThat(stringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1,5\u00a0kiB");
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
	}

	@Test
	void testFormatBytesFloatPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, 5);
		assertThat(stringFormatter.formatBytes(10L)).isEqualTo("10B");
		assertThat(stringFormatter.formatBytes(-10L)).isEqualTo("-10B");
		assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1,023B");
		assertThat(stringFormatter.formatBytes(1_024L)).isEqualTo("1kiB");
		assertThat(stringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.5kiB");
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
	}

	@Test
	void testFormatBytesStrictPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true);
		assertThat(stringFormatter.formatBytes(10L)).isEqualTo("10.00B");
		assertThat(stringFormatter.formatBytes(-10L)).isEqualTo("-10.00B");
		assertThat(stringFormatter.formatBytes(1_023L)).isEqualTo("1,023.00B");
		assertThat(stringFormatter.formatBytes(1_024L)).isEqualTo("1.00kiB");
		assertThat(stringFormatter.formatBytes(1_024L + 512L)).isEqualTo("1.50kiB");
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
	}

	@Test
	void testFormatBytesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> StringFormatter.DEFAULT.formatBytes(0L, null));
	}

	@Test
	void testFormatPercent() {
		final var stringFormatter = new StringFormatter(Locale.US);
		assertThat(stringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0%");
		assertThat(stringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10%");
		assertThat(stringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33%");
		assertThat(stringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66%");
		assertThat(stringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.9%");
		assertThat(stringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100%");
		assertThat(stringFormatter.formatPercent(Math.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99%");
		assertThat(stringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100%");
	}

	@Test
	void testFormatPercentLocale() {
		final var stringFormatter = new StringFormatter(Locale.FRANCE);
		assertThat(stringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0\u00a0%");
		assertThat(stringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10\u00a0%");
		assertThat(stringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33,33\u00a0%");
		assertThat(stringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66,66\u00a0%");
		assertThat(stringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99,9\u00a0%");
		assertThat(stringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100\u00a0%");
		assertThat(stringFormatter.formatPercent(Math.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99,99\u00a0%");
		assertThat(stringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100\u00a0%");
	}

	@Test
	void testFormatPercentFloatPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, 5);
		assertThat(stringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0%");
		assertThat(stringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10%");
		assertThat(stringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33333%");
		assertThat(stringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66666%");
		assertThat(stringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.9%");
		assertThat(stringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100%");
		assertThat(stringFormatter.formatPercent(Math.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99999%");
		assertThat(stringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100%");
	}

	@Test
	void testFormatPercentStrictPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true);
		assertThat(stringFormatter.formatPercent(0.0d, 1.0d)).isEqualTo("0.00%");
		assertThat(stringFormatter.formatPercent(1.0d, 10.0d)).isEqualTo("10.00%");
		assertThat(stringFormatter.formatPercent(1.0d, 3.0d)).isEqualTo("33.33%");
		assertThat(stringFormatter.formatPercent(2.0d, 3.0d)).isEqualTo("66.66%");
		assertThat(stringFormatter.formatPercent(9.99d, 10.0d)).isEqualTo("99.90%");
		assertThat(stringFormatter.formatPercent(10.0d, 10.0d)).isEqualTo("100.00%");
		assertThat(stringFormatter.formatPercent(Math.nextDown(Double.MAX_VALUE), Double.MAX_VALUE)).isEqualTo("99.99%");
		assertThat(stringFormatter.formatPercent(Double.MAX_VALUE, Double.MAX_VALUE)).isEqualTo("100.00%");
	}

	@Test
	void testFormatPercentInvalid() {
		final var stringFormatter = StringFormatter.DEFAULT;
		assertThatIllegalArgumentException().isThrownBy(() -> stringFormatter.formatPercent(-1, 10));
		assertThatIllegalArgumentException().isThrownBy(() -> stringFormatter.formatPercent(11, 10));
	}

	@Test
	void testFormatCurrency() {
		final var stringFormatter = new StringFormatter(Locale.US);
		assertThat(stringFormatter.formatCurrency(0.0d)).isEqualTo("$0");
		assertThat(stringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0");
		assertThat(stringFormatter.formatCurrency(10.0d)).isEqualTo("$10");
		assertThat(stringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10");
		assertThat(stringFormatter.formatCurrency(10.014d)).isEqualTo("$10.01");
		assertThat(stringFormatter.formatCurrency(10.015d)).isEqualTo("$10.02");
		assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12");
	}

	@Test
	void testFormatCurrencyLocale() {
		final var stringFormatter = new StringFormatter(Locale.FRANCE);
		assertThat(stringFormatter.formatCurrency(0.0d)).isEqualTo("0\u00a0€");
		assertThat(stringFormatter.formatCurrency(-0.0d)).isEqualTo("-0\u00a0€");
		assertThat(stringFormatter.formatCurrency(10.0d)).isEqualTo("10\u00a0€");
		assertThat(stringFormatter.formatCurrency(-10.0d)).isEqualTo("-10\u00a0€");
		assertThat(stringFormatter.formatCurrency(10.014d)).isEqualTo("10,01\u00a0€");
		assertThat(stringFormatter.formatCurrency(10.015d)).isEqualTo("10,02\u00a0€");
		assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-1\u00a0234\u00a0567\u00a0890,12\u00a0€");
	}

	@Test
	void testFormatCurrencyFloatPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, 5);
		assertThat(stringFormatter.formatCurrency(0.0d)).isEqualTo("$0");
		assertThat(stringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0");
		assertThat(stringFormatter.formatCurrency(10.0d)).isEqualTo("$10");
		assertThat(stringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10");
		assertThat(stringFormatter.formatCurrency(10.014d)).isEqualTo("$10.014");
		assertThat(stringFormatter.formatCurrency(10.015d)).isEqualTo("$10.015");
		assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12346");
	}

	@Test
	void testFormatCurrencyStrictPrecision() {
		final var stringFormatter = new StringFormatter(Locale.US, StringFormatter.DEFAULT_FLOAT_PRECISION, true);
		assertThat(stringFormatter.formatCurrency(0.0d)).isEqualTo("$0.00");
		assertThat(stringFormatter.formatCurrency(-0.0d)).isEqualTo("-$0.00");
		assertThat(stringFormatter.formatCurrency(10.0d)).isEqualTo("$10.00");
		assertThat(stringFormatter.formatCurrency(-10.0d)).isEqualTo("-$10.00");
		assertThat(stringFormatter.formatCurrency(10.014d)).isEqualTo("$10.01");
		assertThat(stringFormatter.formatCurrency(10.015d)).isEqualTo("$10.02");
		assertThat(stringFormatter.formatCurrency(-1_234_567_890.123456789d)).isEqualTo("-$1,234,567,890.12");
	}

	@Test
	void testEqualsHashCodeToString() {
		final var stringFormatter = StringFormatter.DEFAULT;
		assertThat(stringFormatter).isEqualTo(stringFormatter);
		assertThat(stringFormatter).isNotEqualTo(1);
		{
			final var otherStringFormatter = new StringFormatter(Locale.getDefault());
			assertThat(stringFormatter).isNotSameAs(otherStringFormatter);
			assertThat(stringFormatter).isEqualTo(otherStringFormatter);
			assertThat(stringFormatter).hasSameHashCodeAs(otherStringFormatter);
			assertThat(stringFormatter).hasToString(otherStringFormatter.toString());
		}
		{
			// Locale.FRENCH instead of Locale.FRANCE to work correctly with the default Locale
			final var otherStringFormatter = new StringFormatter(Locale.FRENCH);
			assertThat(stringFormatter).isNotSameAs(otherStringFormatter);
			assertThat(stringFormatter).isNotEqualTo(otherStringFormatter);
			assertThat(stringFormatter.hashCode()).isNotEqualTo(otherStringFormatter.hashCode());
			assertThat(stringFormatter.toString()).isNotEqualTo(otherStringFormatter.toString());
		}
		{
			final var otherStringFormatter = new StringFormatter(Locale.getDefault(), 5);
			assertThat(stringFormatter).isNotSameAs(otherStringFormatter);
			assertThat(stringFormatter).isNotEqualTo(otherStringFormatter);
			assertThat(stringFormatter.hashCode()).isNotEqualTo(otherStringFormatter.hashCode());
			assertThat(stringFormatter.toString()).isNotEqualTo(otherStringFormatter.toString());
		}
		{
			final var otherStringFormatter = new StringFormatter(Locale.getDefault(), StringFormatter.DEFAULT_FLOAT_PRECISION, true);
			assertThat(stringFormatter).isNotSameAs(otherStringFormatter);
			assertThat(stringFormatter).isNotEqualTo(otherStringFormatter);
			assertThat(stringFormatter.hashCode()).isNotEqualTo(otherStringFormatter.hashCode());
			assertThat(stringFormatter.toString()).isNotEqualTo(otherStringFormatter.toString());
		}
	}

	@Test
	void testSerializable() {
		final var stringFormatter = new StringFormatter(Locale.US, 10, true);
		assertThat(Serializables.<StringFormatter>deserialize(Serializables.serialize(stringFormatter))).isEqualTo(stringFormatter);
	}
}