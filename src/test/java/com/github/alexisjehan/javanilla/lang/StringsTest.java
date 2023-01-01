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
package com.github.alexisjehan.javanilla.lang;

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

final class StringsTest {

	@Test
	void testEmpty() {
		assertThat(Strings.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmptyCharSequence() {
		assertThat(Strings.nullToEmpty((CharSequence) null)).isEmpty();
		assertThat(Strings.nullToEmpty((CharSequence) Strings.EMPTY)).isEmpty();
		assertThat(Strings.nullToEmpty((CharSequence) "foo")).isEqualTo("foo");
	}

	@Test
	void testNullToEmptyString() {
		assertThat(Strings.nullToEmpty(null)).isEmpty();
		assertThat(Strings.nullToEmpty(Strings.EMPTY)).isEmpty();
		assertThat(Strings.nullToEmpty("foo")).isEqualTo("foo");
	}

	@Test
	void testNullToDefault() {
		assertThat(Strings.nullToDefault(null, "bar")).isEqualTo("bar");
		assertThat(Strings.nullToDefault(Strings.EMPTY, "bar")).isEmpty();
		assertThat(Strings.nullToDefault("foo", "bar")).isEqualTo("foo");
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.nullToDefault("foo", null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Strings.emptyToNull((CharSequence) null)).isNull();
		assertThat(Strings.emptyToNull(Strings.EMPTY)).isNull();
		assertThat(Strings.emptyToNull("foo")).isEqualTo("foo");
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Strings.emptyToDefault(null, "bar")).isNull();
		assertThat(Strings.emptyToDefault(Strings.EMPTY, "bar")).isEqualTo("bar");
		assertThat(Strings.emptyToDefault("foo", "bar")).isEqualTo("foo");
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.emptyToDefault("foo", Strings.EMPTY));
	}

	@Test
	void testBlankToNull() {
		assertThat(Strings.blankToNull((CharSequence) null)).isNull();
		assertThat(Strings.blankToNull(Strings.EMPTY)).isEmpty();
		assertThat(Strings.blankToNull(" ")).isNull();
		assertThat(Strings.blankToNull("foo")).isEqualTo("foo");
	}

	@Test
	void testBlankToEmptyCharSequence() {
		assertThat(Strings.blankToEmpty((CharSequence) null)).isNull();
		assertThat(Strings.blankToEmpty((CharSequence) Strings.EMPTY)).isEmpty();
		assertThat(Strings.blankToEmpty((CharSequence) " ")).isEmpty();
		assertThat(Strings.blankToEmpty((CharSequence) "foo")).isEqualTo("foo");
	}

	@Test
	void testBlankToEmptyString() {
		assertThat(Strings.blankToEmpty(null)).isNull();
		assertThat(Strings.blankToEmpty(Strings.EMPTY)).isEmpty();
		assertThat(Strings.blankToEmpty(" ")).isEmpty();
		assertThat(Strings.blankToEmpty("foo")).isEqualTo("foo");
	}

	@Test
	void testBlankToDefault() {
		assertThat(Strings.blankToDefault(null, "bar")).isNull();
		assertThat(Strings.blankToDefault(Strings.EMPTY, "bar")).isEmpty();
		assertThat(Strings.blankToDefault(" ", "bar")).isEqualTo("bar");
		assertThat(Strings.blankToDefault("foo", "bar")).isEqualTo("foo");
	}

	@Test
	void testBlankToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.blankToDefault("foo", " "));
	}

	@Test
	void testCapitalize() {
		assertThat(Strings.capitalize(Strings.EMPTY)).isEmpty();
		assertThat(Strings.capitalize("a")).isEqualTo("A");
		assertThat(Strings.capitalize("A")).isEqualTo("A");
		assertThat(Strings.capitalize("foo")).isEqualTo("Foo");
		assertThat(Strings.capitalize("FOO")).isEqualTo("Foo");
	}

	@Test
	void testCapitalizeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.capitalize(null));
	}

	@Test
	void testQuoteChar() {
		assertThat(Strings.quote('a')).isEqualTo("'a'");
		assertThat(Strings.quote('\\')).isEqualTo("'\\\\'");
		assertThat(Strings.quote('\\', '\'', '\'')).isEqualTo("'\\'");
		assertThat(Strings.quote('\'', '\'', '\'')).isEqualTo("''''");
	}

	@Test
	void testQuoteCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.quote(null));
	}

	@Test
	void testQuoteCharSequence() {
		assertThat(Strings.quote(Strings.EMPTY)).isEqualTo("\"\"");
		assertThat(Strings.quote("foo")).isEqualTo("\"foo\"");
		assertThat(Strings.quote("f\"oo")).isEqualTo("\"f\\\"oo\"");
		assertThat(Strings.quote("f\\oo")).isEqualTo("\"f\\\\oo\"");
		assertThat(Strings.quote("f\\\"oo")).isEqualTo("\"f\\\\\\\"oo\"");
		assertThat(Strings.quote("f\"o\no")).isEqualTo("\"f\\\"o\no\"");
		assertThat(Strings.quote("f\"o\\\no")).isEqualTo("\"f\\\"o\\\\\no\"");
		assertThat(Strings.quote("f\\oo", '"', '"')).isEqualTo("\"f\\oo\"");
		assertThat(Strings.quote("f\"oo", '"', '"')).isEqualTo("\"f\"\"oo\"");
	}

	@Test
	void testQuoteCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.quote(null));
	}

	@Test
	void testUnquoteChar() {
		assertThat(Strings.unquoteChar("'a'")).isEqualTo('a');
		assertThat(Strings.unquoteChar("'\\\\'")).isEqualTo('\\');
		assertThat(Strings.unquoteChar("'\\'", '\'', '\'')).isEqualTo('\\');
		assertThat(Strings.unquoteChar("''''", '\'', '\'')).isEqualTo('\'');
	}

	@Test
	void testUnquoteCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.unquoteChar(null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquoteChar("a"));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquoteChar("\"a'"));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquoteChar("'a\""));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquoteChar("'foo'"));
	}

	@Test
	void testUnquote() {
		assertThat(Strings.unquote("\"\"")).isEmpty();
		assertThat(Strings.unquote("\"foo\"")).isEqualTo("foo");
		assertThat(Strings.unquote("\"f\\\"oo\"")).isEqualTo("f\"oo");
		assertThat(Strings.unquote("\"f\\\\oo\"")).isEqualTo("f\\oo");
		assertThat(Strings.unquote("\"f\\\\\\\"oo\"")).isEqualTo("f\\\"oo");
		assertThat(Strings.unquote("\"f\\\"o\no\"")).isEqualTo("f\"o\no");
		assertThat(Strings.unquote("\"f\\\"o\\\\\no\"")).isEqualTo("f\"o\\\no");
		assertThat(Strings.unquote("\"f\\oo\"", '"', '"')).isEqualTo("f\\oo");
		assertThat(Strings.unquote("\"f\"\"oo\"", '"', '"')).isEqualTo("f\"oo");
	}

	@Test
	void testUnquoteInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.unquote(null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquote(Strings.EMPTY));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquote("foo"));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquote("'foo\""));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquote("\"foo'"));
	}

	@Test
	void testSplitChar() {
		assertThat(Strings.split('x', Strings.EMPTY)).containsExactly(Strings.EMPTY);
		assertThat(Strings.split('x', "x")).containsExactly(Strings.EMPTY, Strings.EMPTY);
		assertThat(Strings.split('x', "xx")).containsExactly(Strings.EMPTY, Strings.EMPTY, Strings.EMPTY);
		assertThat(Strings.split('x', "foo")).containsExactly("foo");
		assertThat(Strings.split('x', "fooxbar")).containsExactly("foo", "bar");
		assertThat(Strings.split('x', "xfooxbarx")).containsExactly(Strings.EMPTY, "foo", "bar", Strings.EMPTY);
		assertThat(Strings.split('x', "xfooxbarx", 2)).containsExactly(Strings.EMPTY, "fooxbarx");
		assertThat(Strings.split('x', "xfooxbarx", 3)).containsExactly(Strings.EMPTY, "foo", "barx");
		assertThat(Strings.split('x', "xfooxbarx", 4)).containsExactly(Strings.EMPTY, "foo", "bar", Strings.EMPTY);
		assertThat(Strings.split('x', "xfooxbarx", 5)).containsExactly(Strings.EMPTY, "foo", "bar", Strings.EMPTY);
	}

	@Test
	void testSplitCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.split('x', null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.split('x', "foo", 1));
	}

	@Test
	void testSplitCharSequence() {
		assertThat(Strings.split(Strings.EMPTY, "foo")).containsExactly("foo");
		assertThat(Strings.split("xX", Strings.EMPTY)).containsExactly(Strings.EMPTY);
		assertThat(Strings.split("xX", "xX")).containsExactly(Strings.EMPTY, Strings.EMPTY);
		assertThat(Strings.split("xX", "xXxX")).containsExactly(Strings.EMPTY, Strings.EMPTY, Strings.EMPTY);
		assertThat(Strings.split("xX", "foo")).containsExactly("foo");
		assertThat(Strings.split("xX", "fooxXbar")).containsExactly("foo", "bar");
		assertThat(Strings.split("xX", "xfooxXbarxX")).containsExactly("xfoo", "bar", Strings.EMPTY);
		assertThat(Strings.split("xX", "xXfooxbarxX")).containsExactly(Strings.EMPTY, "fooxbar", Strings.EMPTY);
		assertThat(Strings.split("xX", "xXfooxXbarx")).containsExactly(Strings.EMPTY, "foo", "barx");
		assertThat(Strings.split("xX", "xXfooxXbarxX")).containsExactly(Strings.EMPTY, "foo", "bar", Strings.EMPTY);
		assertThat(Strings.split("xX", "xXfooxXbarxX", 2)).containsExactly(Strings.EMPTY, "fooxXbarxX");
		assertThat(Strings.split("xX", "xXfooxXbarxX", 3)).containsExactly(Strings.EMPTY, "foo", "barxX");
		assertThat(Strings.split("xX", "xXfooxXbarxX", 4)).containsExactly(Strings.EMPTY, "foo", "bar", Strings.EMPTY);
		assertThat(Strings.split("xX", "xXfooxXbarxX", 5)).containsExactly(Strings.EMPTY, "foo", "bar", Strings.EMPTY);
	}

	@Test
	void testSplitCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.split(null, "foo"));
		assertThatNullPointerException().isThrownBy(() -> Strings.split("xX", null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.split("xX", "foo", 1));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testRepeatChar() {
		assertThat(Strings.repeat('a', 0)).isEmpty();
		assertThat(Strings.repeat('a', 1)).isEqualTo("a");
		assertThat(Strings.repeat('a', 3)).isEqualTo("aaa");
	}

	@Test
	@SuppressWarnings("deprecation")
	void testRepeatCharInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.repeat('a', -1));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testRepeatCharSequence() {
		assertThat(Strings.repeat(Strings.EMPTY, 3)).isEmpty();
		assertThat(Strings.repeat("foo", 0)).isEmpty();
		assertThat(Strings.repeat("foo", 1)).isEqualTo("foo");
		assertThat(Strings.repeat("foo", 3)).isEqualTo("foofoofoo");
	}

	@Test
	@SuppressWarnings("deprecation")
	void testRepeatCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.repeat(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.repeat("foo", -1));
	}

	@Test
	void testPadLeftChar() {
		assertThat(Strings.padLeft(Strings.EMPTY, 2)).isEqualTo("  ");
		assertThat(Strings.padLeft(Strings.EMPTY, 2, 'x')).isEqualTo("xx");
		assertThat(Strings.padLeft("foo", 2)).isEqualTo("foo");
		assertThat(Strings.padLeft("foo", 2, 'x')).isEqualTo("foo");
		assertThat(Strings.padLeft("foo", 10)).isEqualTo("       foo");
		assertThat(Strings.padLeft("foo", 10, 'x')).isEqualTo("xxxxxxxfoo");
		assertThat(Strings.padLeft("foo", 11)).isEqualTo("        foo");
		assertThat(Strings.padLeft("foo", 11, 'x')).isEqualTo("xxxxxxxxfoo");
	}

	@Test
	void testPadLeftCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft(null, 1, 'x'));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padLeft("foo", -1));
	}

	@Test
	void testPadLeftCharSequence() {
		assertThat(Strings.padLeft(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padLeft(Strings.EMPTY, 2, "xXxX")).isEqualTo("xX");
		assertThat(Strings.padLeft("foo", 2, Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.padLeft("foo", 2, "xX")).isEqualTo("foo");
		assertThat(Strings.padLeft("foo", 10, Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.padLeft("foo", 10, "xX")).isEqualTo("xXxXxXxfoo");
		assertThat(Strings.padLeft("foo", 11, "xX")).isEqualTo("xXxXxXxXfoo");
	}

	@Test
	void testPadLeftCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft(null, 1, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft("foo", 1, null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padLeft("foo", -1, "xX"));
	}

	@Test
	void testPadRightChar() {
		assertThat(Strings.padRight(Strings.EMPTY, 2)).isEqualTo("  ");
		assertThat(Strings.padRight(Strings.EMPTY, 2, 'x')).isEqualTo("xx");
		assertThat(Strings.padRight("foo", 2)).isEqualTo("foo");
		assertThat(Strings.padRight("foo", 2, 'x')).isEqualTo("foo");
		assertThat(Strings.padRight("foo", 10)).isEqualTo("foo       ");
		assertThat(Strings.padRight("foo", 10, 'x')).isEqualTo("fooxxxxxxx");
		assertThat(Strings.padRight("foo", 11)).isEqualTo("foo        ");
		assertThat(Strings.padRight("foo", 11, 'x')).isEqualTo("fooxxxxxxxx");
	}

	@Test
	void testPadRightCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight(null, 1, 'x'));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padRight("foo", -1));
	}

	@Test
	void testPadRightCharSequence() {
		assertThat(Strings.padRight(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padRight(Strings.EMPTY, 2, "xXxX")).isEqualTo("xX");
		assertThat(Strings.padRight("foo", 2, Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.padRight("foo", 2, "xX")).isEqualTo("foo");
		assertThat(Strings.padRight("foo", 10, Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.padRight("foo", 10, "xX")).isEqualTo("fooxXxXxXx");
		assertThat(Strings.padRight("foo", 11, "xX")).isEqualTo("fooxXxXxXxX");
	}

	@Test
	void testPadRightCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight(null, 1, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight("foo", 1, null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padRight("foo", -1, "xX"));
	}

	@Test
	void testPadBothChar() {
		assertThat(Strings.padBoth(Strings.EMPTY, 2)).isEqualTo("  ");
		assertThat(Strings.padBoth(Strings.EMPTY, 2, 'x')).isEqualTo("xx");
		assertThat(Strings.padBoth("foo", 2)).isEqualTo("foo");
		assertThat(Strings.padBoth("foo", 2, 'x')).isEqualTo("foo");
		assertThat(Strings.padBoth("foo", 10)).isEqualTo("   foo    ");
		assertThat(Strings.padBoth("foo", 10, 'x')).isEqualTo("xxxfooxxxx");
		assertThat(Strings.padBoth("foo", 11)).isEqualTo("    foo    ");
		assertThat(Strings.padBoth("foo", 11, 'x')).isEqualTo("xxxxfooxxxx");
	}

	@Test
	void testPadBothCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth(null, 1, 'x'));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padBoth("foo", -1));
	}

	@Test
	void testPadBothCharSequence() {
		assertThat(Strings.padBoth(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padBoth(Strings.EMPTY, 2, "xXxX")).isEqualTo("xX");
		assertThat(Strings.padBoth("foo", 2, Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.padBoth("foo", 2, "xX")).isEqualTo("foo");
		assertThat(Strings.padBoth("foo", 10, Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.padBoth("foo", 10, "xX")).isEqualTo("xXxfooxXxX");
		assertThat(Strings.padBoth("foo", 11, "xX")).isEqualTo("xXxXfooxXxX");
	}

	@Test
	void testPadBothCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth(null, 1, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth("foo", 1, null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padBoth("foo", -1, "xX"));
	}

	@Test
	void testReplaceFirstChar() {
		assertThat(Strings.replaceFirst(Strings.EMPTY, '1', '_')).isEmpty();
		assertThat(Strings.replaceFirst("12345123450", '1', '_')).isEqualTo("_2345123450");
		assertThat(Strings.replaceFirst("12345123450", '2', '_')).isEqualTo("1_345123450");
		assertThat(Strings.replaceFirst("12345123450", '0', '_')).isEqualTo("1234512345_");
		assertThat(Strings.replaceFirst("12345123450", 'x', '_')).isEqualTo("12345123450");
	}

	@Test
	void testReplaceFirstCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst(null, 'x', '_'));
	}

	@Test
	void testReplaceFirstCharSequence() {
		assertThat(Strings.replaceFirst(Strings.EMPTY, "123", "__")).isEmpty();
		assertThat(Strings.replaceFirst("1", "123", "__")).isEqualTo("1");
		assertThat(Strings.replaceFirst("12345123450", Strings.EMPTY, "__")).isEqualTo("12345123450");
		assertThat(Strings.replaceFirst("12345123450", "123", "__")).isEqualTo("__45123450");
		assertThat(Strings.replaceFirst("12345123450", "234", "__")).isEqualTo("1__5123450");
		assertThat(Strings.replaceFirst("12345123450", "50", "__")).isEqualTo("123451234__");
		assertThat(Strings.replaceFirst("12345123450", "xX", "__")).isEqualTo("12345123450");
	}

	@Test
	void testReplaceFirstCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst(null, "xX", "__"));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst("12345123450", null, "__"));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst("12345123450", "xX", null));
	}

	@Test
	void testReplaceLastChar() {
		assertThat(Strings.replaceLast(Strings.EMPTY, '1', '_')).isEmpty();
		assertThat(Strings.replaceLast("01234512345", '1', '_')).isEqualTo("012345_2345");
		assertThat(Strings.replaceLast("01234512345", '2', '_')).isEqualTo("0123451_345");
		assertThat(Strings.replaceLast("01234512345", '0', '_')).isEqualTo("_1234512345");
		assertThat(Strings.replaceLast("01234512345", 'x', '_')).isEqualTo("01234512345");
	}

	@Test
	void testReplaceLastCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast(null, 'x', '_'));
	}

	@Test
	void testReplaceLastCharSequence() {
		assertThat(Strings.replaceLast(Strings.EMPTY, "234", "__")).isEmpty();
		assertThat(Strings.replaceLast("1", "234", "__")).isEqualTo("1");
		assertThat(Strings.replaceLast("01234512345", Strings.EMPTY, "__")).isEqualTo("01234512345");
		assertThat(Strings.replaceLast("01234512345", "234", "__")).isEqualTo("0123451__5");
		assertThat(Strings.replaceLast("01234512345", "345", "__")).isEqualTo("01234512__");
		assertThat(Strings.replaceLast("01234512345", "01", "__")).isEqualTo("__234512345");
		assertThat(Strings.replaceLast("01234512345", "xX", "__")).isEqualTo("01234512345");
	}

	@Test
	void testReplaceLastCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast(null, "xX", "__"));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast("01234512345", null, "__"));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast("01234512345", "xX", null));
	}

	@Test
	void testSubstringBeforeChar() {
		assertThat(Strings.substringBefore(Strings.EMPTY, '1')).isEmpty();
		assertThat(Strings.substringBefore("12345123450", '1')).isEmpty();
		assertThat(Strings.substringBefore("12345123450", '2')).isEqualTo("1");
		assertThat(Strings.substringBefore("12345123450", '0')).isEqualTo("1234512345");
		assertThat(Strings.substringBefore("12345123450", 'x')).isEqualTo("12345123450");
	}

	@Test
	void testSubstringBeforeCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.substringBefore(null, 'x'));
	}

	@Test
	void testSubstringBeforeCharSequence() {
		assertThat(Strings.substringBefore(Strings.EMPTY, "123")).isEmpty();
		assertThat(Strings.substringBefore("1", "123")).isEqualTo("1");
		assertThat(Strings.substringBefore("12345123450", Strings.EMPTY)).isEqualTo("12345123450");
		assertThat(Strings.substringBefore("12345123450", "123")).isEmpty();
		assertThat(Strings.substringBefore("12345123450", "234")).isEqualTo("1");
		assertThat(Strings.substringBefore("12345123450", "50")).isEqualTo("123451234");
		assertThat(Strings.substringBefore("12345123450", "xX")).isEqualTo("12345123450");
	}

	@Test
	void testSubstringBeforeCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.substringBefore(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.substringBefore("12345123450", null));
	}

	@Test
	void testSubstringAfterChar() {
		assertThat(Strings.substringAfter(Strings.EMPTY, '1')).isEmpty();
		assertThat(Strings.substringAfter("01234512345", '1')).isEqualTo("2345");
		assertThat(Strings.substringAfter("01234512345", '2')).isEqualTo("345");
		assertThat(Strings.substringAfter("01234512345", '0')).isEqualTo("1234512345");
		assertThat(Strings.substringAfter("01234512345", 'x')).isEqualTo("01234512345");
	}

	@Test
	void testSubstringAfterCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.substringAfter(null, 'x'));
	}

	@Test
	void testSubstringAfterCharSequence() {
		assertThat(Strings.substringAfter(Strings.EMPTY, "234")).isEmpty();
		assertThat(Strings.substringAfter("1", "234")).isEqualTo("1");
		assertThat(Strings.substringAfter("01234512345", Strings.EMPTY)).isEqualTo("01234512345");
		assertThat(Strings.substringAfter("01234512345", "234")).isEqualTo("5");
		assertThat(Strings.substringAfter("01234512345", "345")).isEmpty();
		assertThat(Strings.substringAfter("01234512345", "01")).isEqualTo("234512345");
		assertThat(Strings.substringAfter("01234512345", "xX")).isEqualTo("01234512345");
	}

	@Test
	void testSubstringAfterCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.substringAfter(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.substringAfter("01234512345", null));
	}

	@Test
	void testRemoveStartChar() {
		assertThat(Strings.removeStart(Strings.EMPTY, 'x')).isEmpty();
		assertThat(Strings.removeStart("foo", 'x')).isEqualTo("foo");
		assertThat(Strings.removeStart("xfoo", 'x')).isEqualTo("foo");
		assertThat(Strings.removeStart("xfoo", 'X')).isEqualTo("xfoo");
		assertThat(Strings.removeStart("Xfoo", 'x')).isEqualTo("Xfoo");
		assertThat(Strings.removeStart("Xfoo", 'X')).isEqualTo("foo");
		assertThat(Strings.removeStart("xxfoo", 'x')).isEqualTo("xfoo");
	}

	@Test
	void testRemoveStartCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStart(null, 'x'));
	}

	@Test
	void testRemoveStartCharSequence() {
		assertThat(Strings.removeStart(Strings.EMPTY, "xX")).isEmpty();
		assertThat(Strings.removeStart("foo", Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.removeStart("foo", "xX")).isEqualTo("foo");
		assertThat(Strings.removeStart("foo", "xXxX")).isEqualTo("foo");
		assertThat(Strings.removeStart("xXfoo", "xX")).isEqualTo("foo");
		assertThat(Strings.removeStart("xXfoo", "Xx")).isEqualTo("xXfoo");
		assertThat(Strings.removeStart("Xxfoo", "xX")).isEqualTo("Xxfoo");
		assertThat(Strings.removeStart("Xxfoo", "Xx")).isEqualTo("foo");
		assertThat(Strings.removeStart("xXxXfoo", "xX")).isEqualTo("xXfoo");
	}

	@Test
	void testRemoveStartCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStart(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStart("foo", null));
	}

	@Test
	void testRemoveStartIgnoreCaseChar() {
		assertThat(Strings.removeStartIgnoreCase(Strings.EMPTY, 'x')).isEmpty();
		assertThat(Strings.removeStartIgnoreCase("foo", 'x')).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("xfoo", 'x')).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("xfoo", 'X')).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("Xfoo", 'x')).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("Xfoo", 'X')).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("xxfoo", 'x')).isEqualTo("xfoo");
	}

	@Test
	void testRemoveStartIgnoreCaseCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStartIgnoreCase(null, 'x'));
	}

	@Test
	void testRemoveStartIgnoreCaseCharSequence() {
		assertThat(Strings.removeStartIgnoreCase(Strings.EMPTY, "xX")).isEmpty();
		assertThat(Strings.removeStartIgnoreCase("foo", Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("foo", "xX")).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("foo", "xXxX")).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("xXfoo", "xX")).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("xXfoo", "Xx")).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("Xxfoo", "xX")).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("Xxfoo", "Xx")).isEqualTo("foo");
		assertThat(Strings.removeStartIgnoreCase("xXxXfoo", "xX")).isEqualTo("xXfoo");
	}

	@Test
	void testRemoveStartIgnoreCaseCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStartIgnoreCase(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStartIgnoreCase("foo", null));
	}

	@Test
	void testRemoveEndChar() {
		assertThat(Strings.removeEnd(Strings.EMPTY, 'x')).isEmpty();
		assertThat(Strings.removeEnd("foo", 'x')).isEqualTo("foo");
		assertThat(Strings.removeEnd("foox", 'x')).isEqualTo("foo");
		assertThat(Strings.removeEnd("foox", 'X')).isEqualTo("foox");
		assertThat(Strings.removeEnd("fooX", 'x')).isEqualTo("fooX");
		assertThat(Strings.removeEnd("fooX", 'X')).isEqualTo("foo");
		assertThat(Strings.removeEnd("fooxx", 'x')).isEqualTo("foox");
	}

	@Test
	void testRemoveEndCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEnd(null, 'x'));
	}

	@Test
	void testRemoveEndCharSequence() {
		assertThat(Strings.removeEnd(Strings.EMPTY, "xX")).isEmpty();
		assertThat(Strings.removeEnd("foo", Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.removeEnd("foo", "xX")).isEqualTo("foo");
		assertThat(Strings.removeEnd("foo", "xXxX")).isEqualTo("foo");
		assertThat(Strings.removeEnd("fooxX", "xX")).isEqualTo("foo");
		assertThat(Strings.removeEnd("fooxX", "Xx")).isEqualTo("fooxX");
		assertThat(Strings.removeEnd("fooXx", "xX")).isEqualTo("fooXx");
		assertThat(Strings.removeEnd("fooXx", "Xx")).isEqualTo("foo");
		assertThat(Strings.removeEnd("fooxXxX", "xX")).isEqualTo("fooxX");
	}

	@Test
	void testRemoveEndCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEnd(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEnd("foo", null));
	}

	@Test
	void testRemoveEndIgnoreCaseChar() {
		assertThat(Strings.removeEndIgnoreCase(Strings.EMPTY, 'x')).isEmpty();
		assertThat(Strings.removeEndIgnoreCase("foo", 'x')).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("foox", 'x')).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("foox", 'X')).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("fooX", 'x')).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("fooX", 'X')).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("fooxx", 'x')).isEqualTo("foox");
	}

	@Test
	void testRemoveEndIgnoreCaseCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEndIgnoreCase(null, 'x'));
	}

	@Test
	void testRemoveEndIgnoreCaseCharSequence() {
		assertThat(Strings.removeEndIgnoreCase(Strings.EMPTY, "xX")).isEmpty();
		assertThat(Strings.removeEndIgnoreCase("foo", Strings.EMPTY)).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("foo", "xX")).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("foo", "xXxX")).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("fooxX", "xX")).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("fooxX", "Xx")).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("fooXx", "xX")).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("fooXx", "Xx")).isEqualTo("foo");
		assertThat(Strings.removeEndIgnoreCase("fooxXxX", "xX")).isEqualTo("fooxX");
	}

	@Test
	void testRemoveEndIgnoreCaseCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEndIgnoreCase(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEndIgnoreCase("foo", null));
	}

	@Test
	void testConcatMerge() {
		assertThat(Strings.concatMerge(Strings.EMPTY, "456")).isEqualTo("456");
		assertThat(Strings.concatMerge("123", Strings.EMPTY)).isEqualTo("123");
		assertThat(Strings.concatMerge("123", "456")).isEqualTo("123456");
		assertThat(Strings.concatMerge("123456", "456789")).isEqualTo("123456789");
		assertThat(Strings.concatMerge("1234560", "456123")).isEqualTo("1234560456123");
		assertThat(Strings.concatMerge("1234567", "456")).isEqualTo("1234567456");
	}

	@Test
	void testConcatMergeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.concatMerge(null, "456"));
		assertThatNullPointerException().isThrownBy(() -> Strings.concatMerge("123", null));
	}

	@Test
	void testContains() {
		assertThat(Strings.contains(Strings.EMPTY, 'x')).isFalse();
		assertThat(Strings.contains("foo", 'x')).isFalse();
		assertThat(Strings.contains("foxo", 'x')).isTrue();
		assertThat(Strings.contains("foxo", 'X')).isFalse();
		assertThat(Strings.contains("foXo", 'x')).isFalse();
		assertThat(Strings.contains("foXo", 'X')).isTrue();
	}

	@Test
	void testContainsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.contains(null, 'x'));
	}

	@Test
	void testContainsIgnoreCaseChar() {
		assertThat(Strings.containsIgnoreCase(Strings.EMPTY, 'x')).isFalse();
		assertThat(Strings.containsIgnoreCase("foo", 'x')).isFalse();
		assertThat(Strings.containsIgnoreCase("foxo", 'x')).isTrue();
		assertThat(Strings.containsIgnoreCase("foxo", 'X')).isTrue();
		assertThat(Strings.containsIgnoreCase("foXo", 'x')).isTrue();
		assertThat(Strings.containsIgnoreCase("foXo", 'X')).isTrue();
	}

	@Test
	void testContainsIgnoreCaseCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.containsIgnoreCase(null, 'x'));
	}

	@Test
	void testContainsIgnoreCaseCharSequence() {
		assertThat(Strings.containsIgnoreCase(Strings.EMPTY, "xX")).isFalse();
		assertThat(Strings.containsIgnoreCase("foo", Strings.EMPTY)).isTrue();
		assertThat(Strings.containsIgnoreCase("foo", "xX")).isFalse();
		assertThat(Strings.containsIgnoreCase("foxXo", "xX")).isTrue();
		assertThat(Strings.containsIgnoreCase("foxXo", "Xx")).isTrue();
		assertThat(Strings.containsIgnoreCase("foXxo", "xX")).isTrue();
		assertThat(Strings.containsIgnoreCase("foXxo", "Xx")).isTrue();
		assertThat(Strings.containsIgnoreCase("foxo", "xX")).isFalse();
		assertThat(Strings.containsIgnoreCase("foox", "xX")).isFalse();
		assertThat(Strings.containsIgnoreCase("fooxX", "xX")).isTrue();
	}

	@Test
	void testContainsIgnoreCaseCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.containsIgnoreCase(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.containsIgnoreCase("foo", null));
	}

	@Test
	void testStartsWith() {
		assertThat(Strings.startsWith(Strings.EMPTY, 'x')).isFalse();
		assertThat(Strings.startsWith("foo", 'x')).isFalse();
		assertThat(Strings.startsWith("xfoo", 'x')).isTrue();
		assertThat(Strings.startsWith("xfoo", 'X')).isFalse();
		assertThat(Strings.startsWith("Xfoo", 'x')).isFalse();
		assertThat(Strings.startsWith("Xfoo", 'X')).isTrue();
	}

	@Test
	void testStartsWithInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.startsWith(null, 'x'));
	}

	@Test
	void testStartsWithIgnoreCaseChar() {
		assertThat(Strings.startsWithIgnoreCase(Strings.EMPTY, 'x')).isFalse();
		assertThat(Strings.startsWithIgnoreCase("foo", 'x')).isFalse();
		assertThat(Strings.startsWithIgnoreCase("xfoo", 'x')).isTrue();
		assertThat(Strings.startsWithIgnoreCase("xfoo", 'X')).isTrue();
		assertThat(Strings.startsWithIgnoreCase("Xfoo", 'x')).isTrue();
		assertThat(Strings.startsWithIgnoreCase("Xfoo", 'X')).isTrue();
	}

	@Test
	void testStartsWithIgnoreCaseCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.startsWithIgnoreCase(null, 'x'));
	}

	@Test
	void testStartsWithIgnoreCaseCharSequence() {
		assertThat(Strings.startsWithIgnoreCase(Strings.EMPTY, "xX")).isFalse();
		assertThat(Strings.startsWithIgnoreCase("foo", Strings.EMPTY)).isTrue();
		assertThat(Strings.startsWithIgnoreCase("foo", "xX")).isFalse();
		assertThat(Strings.startsWithIgnoreCase("xXfoo", "xX")).isTrue();
		assertThat(Strings.startsWithIgnoreCase("xXfoo", "Xx")).isTrue();
		assertThat(Strings.startsWithIgnoreCase("Xxfoo", "xX")).isTrue();
		assertThat(Strings.startsWithIgnoreCase("Xxfoo", "Xx")).isTrue();
	}

	@Test
	void testStartsWithIgnoreCaseCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.startsWithIgnoreCase(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.startsWithIgnoreCase("foo", null));
	}

	@Test
	void testEndsWith() {
		assertThat(Strings.endsWith(Strings.EMPTY, 'x')).isFalse();
		assertThat(Strings.endsWith("foo", 'x')).isFalse();
		assertThat(Strings.endsWith("foox", 'x')).isTrue();
		assertThat(Strings.endsWith("foox", 'X')).isFalse();
		assertThat(Strings.endsWith("fooX", 'x')).isFalse();
		assertThat(Strings.endsWith("fooX", 'X')).isTrue();
	}

	@Test
	void testEndsWithInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.endsWith(null, 'x'));
	}

	@Test
	void testEndsWithIgnoreCaseChar() {
		assertThat(Strings.endsWithIgnoreCase(Strings.EMPTY, 'x')).isFalse();
		assertThat(Strings.endsWithIgnoreCase("foo", 'x')).isFalse();
		assertThat(Strings.endsWithIgnoreCase("foox", 'x')).isTrue();
		assertThat(Strings.endsWithIgnoreCase("foox", 'X')).isTrue();
		assertThat(Strings.endsWithIgnoreCase("fooX", 'x')).isTrue();
		assertThat(Strings.endsWithIgnoreCase("fooX", 'X')).isTrue();
	}

	@Test
	void testEndsWithIgnoreCaseCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.endsWithIgnoreCase(null, 'x'));
	}

	@Test
	void testEndsWithIgnoreCaseCharSequence() {
		assertThat(Strings.endsWithIgnoreCase(Strings.EMPTY, "xX")).isFalse();
		assertThat(Strings.endsWithIgnoreCase("foo", Strings.EMPTY)).isTrue();
		assertThat(Strings.endsWithIgnoreCase("foo", "xX")).isFalse();
		assertThat(Strings.endsWithIgnoreCase("fooxX", "xX")).isTrue();
		assertThat(Strings.endsWithIgnoreCase("fooxX", "Xx")).isTrue();
		assertThat(Strings.endsWithIgnoreCase("fooXx", "xX")).isTrue();
		assertThat(Strings.endsWithIgnoreCase("fooXx", "Xx")).isTrue();
	}

	@Test
	void testEndsWithIgnoreCaseCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.endsWithIgnoreCase(null, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.endsWithIgnoreCase("foo", null));
	}

	@Test
	void testFrequencyChar() {
		assertThat(Strings.frequency(Strings.EMPTY, 'a')).isZero();
		assertThat(Strings.frequency("foo", 'a')).isZero();
		assertThat(Strings.frequency("foo", 'f')).isEqualTo(1);
		assertThat(Strings.frequency("foo", 'o')).isEqualTo(2);
	}

	@Test
	void testFrequencyCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.frequency(null, 'a'));
	}

	@Test
	void testFrequencyCharSequence() {
		assertThat(Strings.frequency(Strings.EMPTY, "foo")).isZero();
		assertThat(Strings.frequency("foo", "bar")).isZero();
		assertThat(Strings.frequency("foo", "foo")).isEqualTo(1);
		assertThat(Strings.frequency("foofoo", "foo")).isEqualTo(2);
		assertThat(Strings.frequency("foofoo", "ox")).isZero();
	}

	@Test
	void testFrequencyCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.frequency(null, "foo"));
		assertThatNullPointerException().isThrownBy(() -> Strings.frequency("foo", null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.frequency("foo", Strings.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(Strings.isEmpty(Strings.EMPTY)).isTrue();
		assertThat(Strings.isEmpty("foo")).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isEmpty(null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testIsBlank() {
		assertThat(Strings.isBlank(" ")).isTrue();
		assertThat(Strings.isBlank(" \t\n\r")).isTrue();
		assertThat(Strings.isBlank(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBlank("foo")).isFalse();
		assertThat(Strings.isBlank(" \t\n\r" + "foo")).isFalse();
	}

	@Test
	@SuppressWarnings("deprecation")
	void testIsBlankInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBlank(null));
	}

	@Test
	void testIsBoolean() {
		assertThat(Strings.isBoolean("true")).isTrue();
		assertThat(Strings.isBoolean("false")).isTrue();
		assertThat(Strings.isBoolean(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBoolean("foo")).isFalse();
	}

	@Test
	void testIsBooleanInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBoolean(null));
	}

	@Test
	void testIsShort() {
		assertThat(Strings.isShort(Short.toString(Short.MAX_VALUE))).isTrue();
		assertThat(Strings.isShort(Short.toString(Short.MIN_VALUE))).isTrue();
		assertThat(Strings.isShort(Strings.EMPTY)).isFalse();
		assertThat(Strings.isShort("foo")).isFalse();
		assertThat(Strings.isShort(Integer.toString(Short.MAX_VALUE + 1))).isFalse();
		assertThat(Strings.isShort(Integer.toString(Short.MIN_VALUE - 1))).isFalse();
	}

	@Test
	void testIsShortInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isShort(null));
	}

	@Test
	void testIsInt() {
		assertThat(Strings.isInt(Integer.toString(Integer.MAX_VALUE))).isTrue();
		assertThat(Strings.isInt(Integer.toString(Integer.MIN_VALUE))).isTrue();
		assertThat(Strings.isInt(Strings.EMPTY)).isFalse();
		assertThat(Strings.isInt("foo")).isFalse();
		assertThat(Strings.isInt(Long.toString(Integer.MAX_VALUE + 1L))).isFalse();
		assertThat(Strings.isInt(Long.toString(Integer.MIN_VALUE - 1L))).isFalse();
	}

	@Test
	void testIsIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isInt(null));
	}

	@Test
	void testIsLong() {
		assertThat(Strings.isLong(Long.toString(Long.MAX_VALUE))).isTrue();
		assertThat(Strings.isLong(Long.toString(Long.MIN_VALUE))).isTrue();
		assertThat(Strings.isLong(Strings.EMPTY)).isFalse();
		assertThat(Strings.isLong("foo")).isFalse();
		assertThat(Strings.isLong(Double.toString(Long.MAX_VALUE + 1.0d))).isFalse();
		assertThat(Strings.isLong(Double.toString(Long.MIN_VALUE - 1.0d))).isFalse();
	}

	@Test
	void testIsLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isLong(null));
	}

	@Test
	void testIsFloat() {
		assertThat(Strings.isFloat(Float.toString(Float.MAX_VALUE))).isTrue();
		assertThat(Strings.isFloat(Float.toString(Float.MIN_VALUE))).isTrue();
		assertThat(Strings.isFloat(Float.toString(Float.POSITIVE_INFINITY))).isTrue();
		assertThat(Strings.isFloat(Float.toString(Float.NEGATIVE_INFINITY))).isTrue();
		assertThat(Strings.isFloat(Float.toString(Float.NaN))).isTrue();
		assertThat(Strings.isFloat(Strings.EMPTY)).isFalse();
		assertThat(Strings.isFloat("foo")).isFalse();
	}

	@Test
	void testIsFloatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isFloat(null));
	}

	@Test
	void testIsDouble() {
		assertThat(Strings.isDouble(Double.toString(Double.MAX_VALUE))).isTrue();
		assertThat(Strings.isDouble(Double.toString(Double.MIN_VALUE))).isTrue();
		assertThat(Strings.isDouble(Double.toString(Double.POSITIVE_INFINITY))).isTrue();
		assertThat(Strings.isDouble(Double.toString(Double.NEGATIVE_INFINITY))).isTrue();
		assertThat(Strings.isDouble(Double.toString(Double.NaN))).isTrue();
		assertThat(Strings.isDouble(Strings.EMPTY)).isFalse();
		assertThat(Strings.isDouble("foo")).isFalse();
	}

	@Test
	void testIsDoubleInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isDouble(null));
	}

	@Test
	void testIsBinary() {
		assertThat(Strings.isBinary("00000000")).isTrue();
		assertThat(Strings.isBinary("00000000", true)).isTrue();
		assertThat(Strings.isBinary("11111111")).isTrue();
		assertThat(Strings.isBinary("11111111", true)).isTrue();
		assertThat(Strings.isBinary("0000111111110000")).isTrue();
		assertThat(Strings.isBinary("00001111 11110000", true)).isTrue();
		assertThat(Strings.isBinary("00001111111100001111000000001111")).isTrue();
		assertThat(Strings.isBinary("00001111 11110000 11110000 00001111", true)).isTrue();
		assertThat(Strings.isBinary(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBinary(Strings.EMPTY, true)).isFalse();
		assertThat(Strings.isBinary("0000000")).isFalse();
		assertThat(Strings.isBinary("0000000", true)).isFalse();
		assertThat(Strings.isBinary("0000000!")).isFalse();
		assertThat(Strings.isBinary("0000000!", true)).isFalse();
		assertThat(Strings.isBinary("0000000~")).isFalse();
		assertThat(Strings.isBinary("0000000~", true)).isFalse();
		assertThat(Strings.isBinary("00000000?11111111")).isFalse();
		assertThat(Strings.isBinary("00000000?11111111", true)).isFalse();
	}

	@Test
	void testIsBinaryInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBinary(null));
	}

	@Test
	void testIsOctal() {
		assertThat(Strings.isOctal("000")).isTrue();
		assertThat(Strings.isOctal("000", true)).isTrue();
		assertThat(Strings.isOctal("377")).isTrue();
		assertThat(Strings.isOctal("377", true)).isTrue();
		assertThat(Strings.isOctal("017360")).isTrue();
		assertThat(Strings.isOctal("017 360", true)).isTrue();
		assertThat(Strings.isOctal("017360360017")).isTrue();
		assertThat(Strings.isOctal("017 360 360 017", true)).isTrue();
		assertThat(Strings.isOctal(Strings.EMPTY)).isFalse();
		assertThat(Strings.isOctal(Strings.EMPTY, true)).isFalse();
		assertThat(Strings.isOctal("00")).isFalse();
		assertThat(Strings.isOctal("00", true)).isFalse();
		assertThat(Strings.isOctal("00!")).isFalse();
		assertThat(Strings.isOctal("00!", true)).isFalse();
		assertThat(Strings.isOctal("00~")).isFalse();
		assertThat(Strings.isOctal("00~", true)).isFalse();
		assertThat(Strings.isOctal("000?377")).isFalse();
		assertThat(Strings.isOctal("000?377", true)).isFalse();
	}

	@Test
	void testIsOctalInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isOctal(null));
	}

	@Test
	void testIsDecimal() {
		assertThat(Strings.isDecimal("000")).isTrue();
		assertThat(Strings.isDecimal("000", true)).isTrue();
		assertThat(Strings.isDecimal("255")).isTrue();
		assertThat(Strings.isDecimal("255", true)).isTrue();
		assertThat(Strings.isDecimal("015240")).isTrue();
		assertThat(Strings.isDecimal("015 240", true)).isTrue();
		assertThat(Strings.isDecimal("015240240015")).isTrue();
		assertThat(Strings.isDecimal("015 240 240 015", true)).isTrue();
		assertThat(Strings.isDecimal(Strings.EMPTY)).isFalse();
		assertThat(Strings.isDecimal(Strings.EMPTY, true)).isFalse();
		assertThat(Strings.isDecimal("00")).isFalse();
		assertThat(Strings.isDecimal("00", true)).isFalse();
		assertThat(Strings.isDecimal("00!")).isFalse();
		assertThat(Strings.isDecimal("00!", true)).isFalse();
		assertThat(Strings.isDecimal("00~")).isFalse();
		assertThat(Strings.isDecimal("00~", true)).isFalse();
		assertThat(Strings.isDecimal("000?255")).isFalse();
		assertThat(Strings.isDecimal("000?255", true)).isFalse();
	}

	@Test
	void testIsDecimalInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isDecimal(null));
	}

	@Test
	void testIsHexadecimal() {
		assertThat(Strings.isHexadecimal("00")).isTrue();
		assertThat(Strings.isHexadecimal("00", true)).isTrue();
		assertThat(Strings.isHexadecimal("ff")).isTrue();
		assertThat(Strings.isHexadecimal("ff", true)).isTrue();
		assertThat(Strings.isHexadecimal("FF")).isTrue();
		assertThat(Strings.isHexadecimal("FF", true)).isTrue();
		assertThat(Strings.isHexadecimal("0ff0")).isTrue();
		assertThat(Strings.isHexadecimal("0f f0", true)).isTrue();
		assertThat(Strings.isHexadecimal("0FF0")).isTrue();
		assertThat(Strings.isHexadecimal("0F F0", true)).isTrue();
		assertThat(Strings.isHexadecimal("0ff0f00f")).isTrue();
		assertThat(Strings.isHexadecimal("0f f0 f0 0f", true)).isTrue();
		assertThat(Strings.isHexadecimal("0FF0F00F")).isTrue();
		assertThat(Strings.isHexadecimal("0F F0 F0 0F", true)).isTrue();
		assertThat(Strings.isHexadecimal(Strings.EMPTY)).isFalse();
		assertThat(Strings.isHexadecimal(Strings.EMPTY, true)).isFalse();
		assertThat(Strings.isHexadecimal("0")).isFalse();
		assertThat(Strings.isHexadecimal("0", true)).isFalse();
		assertThat(Strings.isHexadecimal("0!")).isFalse();
		assertThat(Strings.isHexadecimal("0!", true)).isFalse();
		assertThat(Strings.isHexadecimal("0~")).isFalse();
		assertThat(Strings.isHexadecimal("0~", true)).isFalse();
		assertThat(Strings.isHexadecimal("00?ff")).isFalse();
		assertThat(Strings.isHexadecimal("00?ff", true)).isFalse();
		assertThat(Strings.isHexadecimal("00?FF")).isFalse();
		assertThat(Strings.isHexadecimal("00?FF", true)).isFalse();
	}

	@Test
	void testIsHexadecimalInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isHexadecimal(null));
	}

	@Test
	void testIsBase64() {
		assertThat(Strings.isBase64("Zg==")).isTrue();
		assertThat(Strings.isBase64("Zg", false)).isTrue();
		assertThat(Strings.isBase64("ZgZg")).isTrue();
		assertThat(Strings.isBase64("ZgZg", false)).isTrue();
		assertThat(Strings.isBase64("Zm8=")).isTrue();
		assertThat(Strings.isBase64("Zm8", false)).isTrue();
		assertThat(Strings.isBase64("+/==")).isTrue();
		assertThat(Strings.isBase64("+/", false)).isTrue();
		assertThat(Strings.isBase64(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBase64(Strings.EMPTY, false)).isFalse();
		assertThat(Strings.isBase64("Zg")).isFalse();
		assertThat(Strings.isBase64("Zg==", false)).isFalse();
		assertThat(Strings.isBase64("Zg=")).isFalse();
		assertThat(Strings.isBase64("Zg=", false)).isFalse();
		assertThat(Strings.isBase64("Zg===")).isFalse();
		assertThat(Strings.isBase64("Zg===", false)).isFalse();
		assertThat(Strings.isBase64("Zg!?")).isFalse();
		assertThat(Strings.isBase64("Zg!?", false)).isFalse();
		assertThat(Strings.isBase64("Zg|:")).isFalse();
		assertThat(Strings.isBase64("Zg|:", false)).isFalse();
		assertThat(Strings.isBase64("==Zg")).isFalse();
		assertThat(Strings.isBase64("==Zg", false)).isFalse();
		assertThat(Strings.isBase64("Zg=+")).isFalse();
		assertThat(Strings.isBase64("Zg=+", false)).isFalse();
		assertThat(Strings.isBase64("-_==")).isFalse();
		assertThat(Strings.isBase64("-_", false)).isFalse();
	}

	@Test
	void testIsBase64Invalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBase64(null));
	}

	@Test
	void testIsBase64Url() {
		assertThat(Strings.isBase64Url("Zg==")).isTrue();
		assertThat(Strings.isBase64Url("Zg", false)).isTrue();
		assertThat(Strings.isBase64Url("ZgZg")).isTrue();
		assertThat(Strings.isBase64Url("ZgZg", false)).isTrue();
		assertThat(Strings.isBase64Url("Zm8=")).isTrue();
		assertThat(Strings.isBase64Url("Zm8", false)).isTrue();
		assertThat(Strings.isBase64Url("-_==")).isTrue();
		assertThat(Strings.isBase64Url("-_", false)).isTrue();
		assertThat(Strings.isBase64Url(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBase64Url(Strings.EMPTY, false)).isFalse();
		assertThat(Strings.isBase64Url("Zg")).isFalse();
		assertThat(Strings.isBase64Url("Zg==", false)).isFalse();
		assertThat(Strings.isBase64Url("Zg=")).isFalse();
		assertThat(Strings.isBase64Url("Zg=", false)).isFalse();
		assertThat(Strings.isBase64Url("Zg===")).isFalse();
		assertThat(Strings.isBase64Url("Zg===", false)).isFalse();
		assertThat(Strings.isBase64Url("Zg!?")).isFalse();
		assertThat(Strings.isBase64Url("Zg!?", false)).isFalse();
		assertThat(Strings.isBase64Url("Zg|:")).isFalse();
		assertThat(Strings.isBase64Url("Zg|:", false)).isFalse();
		assertThat(Strings.isBase64Url("==Zg")).isFalse();
		assertThat(Strings.isBase64Url("==Zg", false)).isFalse();
		assertThat(Strings.isBase64Url("Zg=-")).isFalse();
		assertThat(Strings.isBase64Url("Zg=-", false)).isFalse();
		assertThat(Strings.isBase64Url("+/==")).isFalse();
		assertThat(Strings.isBase64Url("+/", false)).isFalse();
	}

	@Test
	void testIsBase64UrlInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBase64Url(null));
	}

	@Test
	void testOfBytes() {
		assertThat(Strings.of(ByteArrays.EMPTY)).isEmpty();
		assertThat(Strings.of("foo".getBytes())).isEqualTo("foo");
		assertThat(Strings.of(StandardCharsets.ISO_8859_1, "foo".getBytes(StandardCharsets.ISO_8859_1))).isEqualTo("foo");

		// Not the same charset
		assertThat(Strings.of(StandardCharsets.ISO_8859_1, "foo".getBytes(StandardCharsets.UTF_16))).isNotEqualTo("foo");
	}

	@Test
	void testOfBytesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.of((byte[]) null));
		assertThatNullPointerException().isThrownBy(() -> Strings.of(null, "foo".getBytes()));
	}

	@Test
	void testOfChars() {
		assertThat(Strings.of(CharArrays.EMPTY)).isEmpty();
		assertThat(Strings.of("foo".toCharArray())).isEqualTo("foo");
	}

	@Test
	void testOfCharsInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.of((char[]) null));
	}

	@Test
	void testToChar() {
		assertThat(Strings.toChar("a")).isEqualTo('a');
	}

	@Test
	void testToCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.toChar(null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.toChar(Strings.EMPTY));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.toChar("foo"));
	}
}