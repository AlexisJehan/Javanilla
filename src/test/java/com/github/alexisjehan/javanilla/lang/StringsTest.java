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
package com.github.alexisjehan.javanilla.lang;

import com.github.alexisjehan.javanilla.lang.array.ByteArrays;
import com.github.alexisjehan.javanilla.lang.array.CharArrays;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Strings} unit tests.</p>
 */
final class StringsTest {

	private static final String STRING = "abc";

	@Test
	void testEmpty() {
		assertThat(Strings.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmptyCharSequence() {
		assertThat(Strings.nullToEmpty((CharSequence) null)).isEmpty();
		assertThat(Strings.nullToEmpty((CharSequence) Strings.EMPTY)).isEmpty();
		assertThat(Strings.nullToEmpty((CharSequence) STRING)).isEqualTo(STRING);
	}

	@Test
	void testNullToEmptyString() {
		assertThat(Strings.nullToEmpty(null)).isEmpty();
		assertThat(Strings.nullToEmpty(Strings.EMPTY)).isEmpty();
		assertThat(Strings.nullToEmpty(STRING)).isEqualTo(STRING);
	}

	@Test
	void testNullToDefault() {
		assertThat(Strings.nullToDefault(null, "-")).isEqualTo("-");
		assertThat(Strings.nullToDefault(Strings.EMPTY, "-")).isEmpty();
		assertThat(Strings.nullToDefault(STRING, "-")).isEqualTo(STRING);
	}

	@Test
	void testNullToDefaultInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.nullToDefault(STRING, null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Strings.emptyToNull((CharSequence) null)).isNull();
		assertThat(Strings.emptyToNull(Strings.EMPTY)).isNull();
		assertThat(Strings.emptyToNull(STRING)).isEqualTo(STRING);
	}

	@Test
	void testEmptyToDefault() {
		assertThat(Strings.emptyToDefault(null, "-")).isNull();
		assertThat(Strings.emptyToDefault(Strings.EMPTY, "-")).isEqualTo("-");
		assertThat(Strings.emptyToDefault(STRING, "-")).isEqualTo(STRING);
	}

	@Test
	void testEmptyToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.emptyToDefault(STRING, Strings.EMPTY));
	}

	@Test
	void testBlankToNull() {
		assertThat(Strings.blankToNull((CharSequence) null)).isNull();
		assertThat(Strings.blankToNull(" ")).isNull();
		assertThat(Strings.blankToNull(STRING)).isEqualTo(STRING);
	}

	@Test
	void testBlankToEmptyCharSequence() {
		assertThat(Strings.blankToEmpty((CharSequence) null)).isNull();
		assertThat(Strings.blankToEmpty((CharSequence) " ")).isEmpty();
		assertThat(Strings.blankToEmpty((CharSequence) STRING)).isEqualTo(STRING);
	}

	@Test
	void testBlankToEmptyString() {
		assertThat(Strings.blankToEmpty(null)).isNull();
		assertThat(Strings.blankToEmpty(" ")).isEmpty();
		assertThat(Strings.blankToEmpty(STRING)).isEqualTo(STRING);
	}

	@Test
	void testBlankToDefault() {
		assertThat(Strings.blankToDefault(null, "-")).isNull();
		assertThat(Strings.blankToDefault(" ", "-")).isEqualTo("-");
		assertThat(Strings.blankToDefault(STRING, "-")).isEqualTo(STRING);
	}

	@Test
	void testBlankToDefaultInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.blankToDefault(STRING, " "));
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
		assertThat(Strings.quote('f')).isEqualTo("'f'");
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
		assertThat(Strings.unquoteChar("'f'")).isEqualTo('f');
		assertThat(Strings.unquoteChar("'\\\\'")).isEqualTo('\\');
		assertThat(Strings.unquoteChar("'\\'", '\'', '\'')).isEqualTo('\\');
		assertThat(Strings.unquoteChar("''''", '\'', '\'')).isEqualTo('\'');
	}

	@Test
	void testUnquoteCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.unquoteChar(null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquoteChar("f"));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquoteChar("\"f'"));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquoteChar("'f\""));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquoteChar("'fo'"));
	}

	@Test
	void testUnquote() {
		assertThat(Strings.unquote("\"\"")).isEqualTo(Strings.EMPTY);
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
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.unquote("f"));
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
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.split('x', STRING, 1));
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
		assertThatNullPointerException().isThrownBy(() -> Strings.split(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.split(STRING, null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.split(STRING, STRING, 1));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testRepeatChar() {
		assertThat(Strings.repeat('x', 0)).isEmpty();
		assertThat(Strings.repeat('x', 1)).isEqualTo("x");
		assertThat(Strings.repeat('x', 3)).isEqualTo("xxx");
	}

	@Test
	@SuppressWarnings("deprecation")
	void testRepeatCharInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.repeat('x', -1));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testRepeatCharSequence() {
		assertThat(Strings.repeat(Strings.EMPTY, 3)).isEmpty();
		assertThat(Strings.repeat("xX", 0)).isEmpty();
		assertThat(Strings.repeat("xX", 1)).isEqualTo("xX");
		assertThat(Strings.repeat("xX", 3)).isEqualTo("xXxXxX");
	}

	@Test
	@SuppressWarnings("deprecation")
	void testRepeatCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.repeat(null, 1));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.repeat(STRING, -1));
	}

	@Test
	void testPadLeftChar() {
		assertThat(Strings.padLeft(Strings.EMPTY, 2)).isEqualTo("  ");
		assertThat(Strings.padLeft("foo", 10)).isEqualTo("       foo");
		assertThat(Strings.padLeft("foo", 10, 'x')).isEqualTo("xxxxxxxfoo");
		assertThat(Strings.padLeft("foo", 2, 'x')).isEqualTo("foo");
	}

	@Test
	void testPadLeftCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft(null, 1, 'x'));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padLeft(STRING, -1));
	}

	@Test
	void testPadLeftCharSequence() {
		assertThat(Strings.padLeft(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padLeft(Strings.EMPTY, 2, "xXx")).isEqualTo("xX");
		assertThat(Strings.padLeft("foo", 10, "xX")).isEqualTo("xXxXxXxfoo");
		assertThat(Strings.padLeft("foo", 11, "xX")).isEqualTo("xXxXxXxXfoo");
		assertThat(Strings.padLeft("foo", 2, "xX")).isEqualTo("foo");
		assertThat(Strings.padLeft("foo", 10, Strings.EMPTY)).isEqualTo("foo");
	}

	@Test
	void testPadLeftCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft(null, 1, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft(STRING, 1, null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padLeft(STRING, -1, STRING));
	}

	@Test
	void testPadRightChar() {
		assertThat(Strings.padRight(Strings.EMPTY, 2, 'x')).isEqualTo("xx");
		assertThat(Strings.padRight("foo", 10)).isEqualTo("foo       ");
		assertThat(Strings.padRight("foo", 10, 'x')).isEqualTo("fooxxxxxxx");
		assertThat(Strings.padRight("foo", 2, 'x')).isEqualTo("foo");
	}

	@Test
	void testPadRightCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight(null, 1, 'x'));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padRight(STRING, -1));
	}

	@Test
	void testPadRightCharSequence() {
		assertThat(Strings.padRight(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padRight(Strings.EMPTY, 2, "xXx")).isEqualTo("xX");
		assertThat(Strings.padRight("foo", 10, "xX")).isEqualTo("fooxXxXxXx");
		assertThat(Strings.padRight("foo", 11, "xX")).isEqualTo("fooxXxXxXxX");
		assertThat(Strings.padRight("foo", 2, "xX")).isEqualTo("foo");
		assertThat(Strings.padRight("foo", 10, Strings.EMPTY)).isEqualTo("foo");
	}

	@Test
	void testPadRightCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight(null, 1, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight(STRING, 1, null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padRight(STRING, -1, STRING));
	}

	@Test
	void testPadBothChar() {
		assertThat(Strings.padBoth(Strings.EMPTY, 2, 'x')).isEqualTo("xx");
		assertThat(Strings.padBoth("foo", 10)).isEqualTo("   foo    ");
		assertThat(Strings.padBoth("foo", 11)).isEqualTo("    foo    ");
		assertThat(Strings.padBoth("foo", 10, 'x')).isEqualTo("xxxfooxxxx");
		assertThat(Strings.padBoth("foo", 11, 'x')).isEqualTo("xxxxfooxxxx");
		assertThat(Strings.padBoth("foo", 2, 'x')).isEqualTo("foo");
	}

	@Test
	void testPadBothCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth(null, 1, 'x'));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padBoth(STRING, -1));
	}

	@Test
	void testPadBothCharSequence() {
		assertThat(Strings.padBoth(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padBoth(Strings.EMPTY, 2, "xXx")).isEqualTo("xX");
		assertThat(Strings.padBoth("foo", 10, "xX")).isEqualTo("xXxfooxXxX");
		assertThat(Strings.padBoth("foo", 11, "xX")).isEqualTo("xXxXfooxXxX");
		assertThat(Strings.padBoth("foo", 2, "xX")).isEqualTo("foo");
		assertThat(Strings.padBoth("foo", 10, Strings.EMPTY)).isEqualTo("foo");
	}

	@Test
	void testPadBothCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth(null, 1, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth(STRING, 1, null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padBoth(STRING, -1, STRING));
	}

	@Test
	void testReplaceFirstChar() {
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
		assertThat(Strings.replaceFirst("12345123450", "123", "__")).isEqualTo("__45123450");
		assertThat(Strings.replaceFirst("12345123450", "234", "__")).isEqualTo("1__5123450");
		assertThat(Strings.replaceFirst("12345123450", "50", "__")).isEqualTo("123451234__");
		assertThat(Strings.replaceFirst("12345123450", "xX", "__")).isEqualTo("12345123450");
		assertThat(Strings.replaceFirst("12345123450", Strings.EMPTY, "__")).isEqualTo("12345123450");
		assertThat(Strings.replaceFirst("1", "xX", "__")).isEqualTo("1");
	}

	@Test
	void testReplaceFirstCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst(null, STRING, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst(STRING, null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst(STRING, STRING, null));
	}

	@Test
	void testReplaceLastChar() {
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
		assertThat(Strings.replaceLast("01234512345", "234", "__")).isEqualTo("0123451__5");
		assertThat(Strings.replaceLast("01234512345", "345", "__")).isEqualTo("01234512__");
		assertThat(Strings.replaceLast("01234512345", "01", "__")).isEqualTo("__234512345");
		assertThat(Strings.replaceLast("01234512345", "xX", "__")).isEqualTo("01234512345");
		assertThat(Strings.replaceLast("01234512345", Strings.EMPTY, "__")).isEqualTo("01234512345");
		assertThat(Strings.replaceLast("1", "xX", "__")).isEqualTo("1");
	}

	@Test
	void testReplaceLastCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast(null, STRING, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast(STRING, null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast(STRING, STRING, null));
	}

	@Test
	void testRemoveStartChar() {
		assertThat(Strings.removeStart("abc", 'z')).isEqualTo("abc");
		assertThat(Strings.removeStart("zabc", 'z')).isEqualTo("abc");
	}

	@Test
	void testRemoveStartCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStart(null, 'z'));
	}

	@Test
	void testRemoveStartCharSequence() {
		assertThat(Strings.removeStart("abc", "zzz")).isEqualTo("abc");
		assertThat(Strings.removeStart("abc", Strings.EMPTY)).isEqualTo("abc");
		assertThat(Strings.removeStart("abc", "zzzzzz")).isEqualTo("abc");
		assertThat(Strings.removeStart("zzzabc", "zzz")).isEqualTo("abc");
		assertThat(Strings.removeStart("zzzabc", "ZzZ")).isEqualTo("zzzabc");
	}

	@Test
	void testRemoveStartCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStart(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStart(STRING, null));
	}

	@Test
	void testRemoveStartIgnoreCase() {
		assertThat(Strings.removeStartIgnoreCase("abc", "zzz")).isEqualTo("abc");
		assertThat(Strings.removeStartIgnoreCase("abc", Strings.EMPTY)).isEqualTo("abc");
		assertThat(Strings.removeStartIgnoreCase("abc", "zzzzzz")).isEqualTo("abc");
		assertThat(Strings.removeStartIgnoreCase("zzzabc", "zzz")).isEqualTo("abc");
		assertThat(Strings.removeStartIgnoreCase("zzzabc", "ZzZ")).isEqualTo("abc");
	}

	@Test
	void testRemoveStartIgnoreCaseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStartIgnoreCase(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStartIgnoreCase(STRING, null));
	}

	@Test
	void testRemoveEndChar() {
		assertThat(Strings.removeEnd("abc", 'z')).isEqualTo("abc");
		assertThat(Strings.removeEnd("abcz", 'z')).isEqualTo("abc");
	}

	@Test
	void testRemoveEndCharInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEnd(null, 'z'));
	}

	@Test
	void testRemoveEndCharSequence() {
		assertThat(Strings.removeEnd("abc", "zzz")).isEqualTo("abc");
		assertThat(Strings.removeEnd("abc", Strings.EMPTY)).isEqualTo("abc");
		assertThat(Strings.removeEnd("abc", "zzzzzz")).isEqualTo("abc");
		assertThat(Strings.removeEnd("abczzz", "zzz")).isEqualTo("abc");
		assertThat(Strings.removeEnd("abczzz", "ZzZ")).isEqualTo("abczzz");
	}

	@Test
	void testRemoveEndCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEnd(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEnd(STRING, null));
	}

	@Test
	void testRemoveEndIgnoreCase() {
		assertThat(Strings.removeEndIgnoreCase("abc", "zzz")).isEqualTo("abc");
		assertThat(Strings.removeEndIgnoreCase("abc", Strings.EMPTY)).isEqualTo("abc");
		assertThat(Strings.removeEndIgnoreCase("abc", "zzzzzz")).isEqualTo("abc");
		assertThat(Strings.removeEndIgnoreCase("abczzz", "zzz")).isEqualTo("abc");
		assertThat(Strings.removeEndIgnoreCase("abczzz", "ZzZ")).isEqualTo("abc");
	}

	@Test
	void testRemoveEndIgnoreCaseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEndIgnoreCase(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEndIgnoreCase(STRING, null));
	}

	@Test
	void testConcatMerge() {
		assertThat(Strings.concatMerge(Strings.EMPTY, "456789")).isEqualTo("456789");
		assertThat(Strings.concatMerge("123456", "456789")).isEqualTo("123456789");
		assertThat(Strings.concatMerge("123", "456")).isEqualTo("123456");
		assertThat(Strings.concatMerge("1234560", "456123")).isEqualTo("1234560456123");
		assertThat(Strings.concatMerge("1234567", "456")).isEqualTo("1234567456");
		assertThat(Strings.concatMerge("123456", Strings.EMPTY)).isEqualTo("123456");
	}

	@Test
	void testConcatMergeInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.concatMerge(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.concatMerge(STRING, null));
	}

	@Test
	void testContainsIgnoreCase() {
		assertThat(Strings.containsIgnoreCase("foo", Strings.EMPTY)).isTrue();
		assertThat(Strings.containsIgnoreCase("foo", "fooo")).isFalse();
		assertThat(Strings.containsIgnoreCase("foo", "ou")).isFalse();
		assertThat(Strings.containsIgnoreCase("foo", "bar")).isFalse();
		assertThat(Strings.containsIgnoreCase("foo", "fo")).isTrue();
		assertThat(Strings.containsIgnoreCase("foo", "FO")).isTrue();
		assertThat(Strings.containsIgnoreCase("foo", "FOO")).isTrue();
	}

	@Test
	void testContainsIgnoreCaseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.containsIgnoreCase(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.containsIgnoreCase(STRING, null));
	}

	@Test
	void testStartsWithIgnoreCase() {
		assertThat(Strings.startsWithIgnoreCase("foo", Strings.EMPTY)).isTrue();
		assertThat(Strings.startsWithIgnoreCase("foo", "fooo")).isFalse();
		assertThat(Strings.startsWithIgnoreCase("foo", "ou")).isFalse();
		assertThat(Strings.startsWithIgnoreCase("foo", "bar")).isFalse();
		assertThat(Strings.startsWithIgnoreCase("foo", "fo")).isTrue();
		assertThat(Strings.startsWithIgnoreCase("foo", "FO")).isTrue();
		assertThat(Strings.startsWithIgnoreCase("foo", "FOO")).isTrue();
	}

	@Test
	void testStartsWithIgnoreCaseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.startsWithIgnoreCase(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.startsWithIgnoreCase(STRING, null));
	}

	@Test
	void testEndsWithIgnoreCase() {
		assertThat(Strings.endsWithIgnoreCase("foo", Strings.EMPTY)).isTrue();
		assertThat(Strings.endsWithIgnoreCase("foo", "fooo")).isFalse();
		assertThat(Strings.endsWithIgnoreCase("foo", "ou")).isFalse();
		assertThat(Strings.endsWithIgnoreCase("foo", "bar")).isFalse();
		assertThat(Strings.endsWithIgnoreCase("foo", "oo")).isTrue();
		assertThat(Strings.endsWithIgnoreCase("foo", "OO")).isTrue();
		assertThat(Strings.endsWithIgnoreCase("foo", "FOO")).isTrue();
	}

	@Test
	void testEndsWithIgnoreCaseInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.endsWithIgnoreCase(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.endsWithIgnoreCase(STRING, null));
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
		assertThat(Strings.frequency("foofoo", "ou")).isZero();
	}

	@Test
	void testFrequencyCharSequenceInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.frequency(null, STRING));
		assertThatNullPointerException().isThrownBy(() -> Strings.frequency(STRING, null));
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.frequency(STRING, Strings.EMPTY));
	}

	@Test
	void testIsEmpty() {
		assertThat(Strings.isEmpty(Strings.EMPTY)).isTrue();
		assertThat(Strings.isEmpty(STRING)).isFalse();
	}

	@Test
	void testIsEmptyInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isEmpty(null));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testIsBlank() {
		assertThat(Strings.isBlank(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBlank(" ")).isTrue();
		assertThat(Strings.isBlank(STRING)).isFalse();
		assertThat(Strings.isBlank(" \t\n\r")).isTrue();
		assertThat(Strings.isBlank(" \t\n\r" + STRING)).isFalse();
	}

	@Test
	@SuppressWarnings("deprecation")
	void testIsBlankInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBlank(null));
	}

	@Test
	void testIsBoolean() {
		assertThat(Strings.isBoolean(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBoolean("foo")).isFalse();
		assertThat(Strings.isBoolean("true")).isTrue();
		assertThat(Strings.isBoolean("false")).isTrue();
	}

	@Test
	void testIsBooleanInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBoolean(null));
	}

	@Test
	void testIsShort() {
		assertThat(Strings.isShort(Strings.EMPTY)).isFalse();
		assertThat(Strings.isShort("foo")).isFalse();
		assertThat(Strings.isShort(Short.toString(Short.MAX_VALUE))).isTrue();
		assertThat(Strings.isShort(Short.toString(Short.MIN_VALUE))).isTrue();
		assertThat(Strings.isShort(Integer.toString(Short.MAX_VALUE + 1))).isFalse();
		assertThat(Strings.isShort(Integer.toString(Short.MIN_VALUE - 1))).isFalse();
	}

	@Test
	void testIsShortInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isShort(null));
	}

	@Test
	void testIsInt() {
		assertThat(Strings.isInt(Strings.EMPTY)).isFalse();
		assertThat(Strings.isInt("foo")).isFalse();
		assertThat(Strings.isInt(Integer.toString(Integer.MAX_VALUE))).isTrue();
		assertThat(Strings.isInt(Integer.toString(Integer.MIN_VALUE))).isTrue();
		assertThat(Strings.isInt(Long.toString(Integer.MAX_VALUE + 1L))).isFalse();
		assertThat(Strings.isInt(Long.toString(Integer.MIN_VALUE - 1L))).isFalse();
	}

	@Test
	void testIsIntInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isInt(null));
	}

	@Test
	void testIsLong() {
		assertThat(Strings.isLong(Strings.EMPTY)).isFalse();
		assertThat(Strings.isLong("foo")).isFalse();
		assertThat(Strings.isLong(Long.toString(Long.MAX_VALUE))).isTrue();
		assertThat(Strings.isLong(Long.toString(Long.MIN_VALUE))).isTrue();
		assertThat(Strings.isLong(Double.toString(Long.MAX_VALUE + 1.0d))).isFalse();
		assertThat(Strings.isLong(Double.toString(Long.MIN_VALUE - 1.0d))).isFalse();
	}

	@Test
	void testIsLongInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isLong(null));
	}

	@Test
	void testIsFloat() {
		assertThat(Strings.isFloat(Strings.EMPTY)).isFalse();
		assertThat(Strings.isFloat("foo")).isFalse();
		assertThat(Strings.isFloat(Float.toString(Float.MAX_VALUE))).isTrue();
		assertThat(Strings.isFloat(Float.toString(Float.MIN_VALUE))).isTrue();
		assertThat(Strings.isFloat(Float.toString(Float.POSITIVE_INFINITY))).isTrue();
		assertThat(Strings.isFloat(Float.toString(Float.NEGATIVE_INFINITY))).isTrue();
		assertThat(Strings.isFloat(Float.toString(Float.NaN))).isTrue();
	}

	@Test
	void testIsFloatInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isFloat(null));
	}

	@Test
	void testIsDouble() {
		assertThat(Strings.isDouble(Strings.EMPTY)).isFalse();
		assertThat(Strings.isDouble("foo")).isFalse();
		assertThat(Strings.isDouble(Double.toString(Double.MAX_VALUE))).isTrue();
		assertThat(Strings.isDouble(Double.toString(Double.MIN_VALUE))).isTrue();
		assertThat(Strings.isDouble(Double.toString(Double.POSITIVE_INFINITY))).isTrue();
		assertThat(Strings.isDouble(Double.toString(Double.NEGATIVE_INFINITY))).isTrue();
		assertThat(Strings.isDouble(Double.toString(Double.NaN))).isTrue();
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
		assertThat(Strings.isBase64(Strings.EMPTY)).isFalse();

		// With padding
		assertThat(Strings.isBase64("Zg")).isFalse();
		assertThat(Strings.isBase64("Zg=")).isFalse();
		assertThat(Strings.isBase64("Zg==")).isTrue();
		assertThat(Strings.isBase64("Zg===")).isFalse();
		assertThat(Strings.isBase64("ZgZg")).isTrue();

		// Without padding
		assertThat(Strings.isBase64("Zg", false)).isTrue();
		assertThat(Strings.isBase64("Zg=", false)).isFalse();
		assertThat(Strings.isBase64("Zg==", false)).isFalse();
		assertThat(Strings.isBase64("Zg===", false)).isFalse();
		assertThat(Strings.isBase64("ZgZg", false)).isTrue();

		assertThat(Strings.isBase64("Zg!?")).isFalse();
		assertThat(Strings.isBase64("Zg|:")).isFalse();
		assertThat(Strings.isBase64("==Zg")).isFalse();
		assertThat(Strings.isBase64("Zg=+")).isFalse();
		assertThat(Strings.isBase64("Zg==")).isTrue();
		assertThat(Strings.isBase64("Zm8=")).isTrue();
		assertThat(Strings.isBase64("+/==")).isTrue();
		assertThat(Strings.isBase64("-_==")).isFalse();
	}

	@Test
	void testIsBase64Invalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBase64(null));
	}

	@Test
	void testIsBase64Url() {
		assertThat(Strings.isBase64Url(Strings.EMPTY)).isFalse();

		// With padding
		assertThat(Strings.isBase64Url("Zg")).isFalse();
		assertThat(Strings.isBase64Url("Zg=")).isFalse();
		assertThat(Strings.isBase64Url("Zg==")).isTrue();
		assertThat(Strings.isBase64Url("Zg===")).isFalse();
		assertThat(Strings.isBase64Url("ZgZg")).isTrue();

		// Without padding
		assertThat(Strings.isBase64Url("Zg", false)).isTrue();
		assertThat(Strings.isBase64Url("Zg=", false)).isFalse();
		assertThat(Strings.isBase64Url("Zg==", false)).isFalse();
		assertThat(Strings.isBase64Url("Zg===", false)).isFalse();
		assertThat(Strings.isBase64Url("ZgZg", false)).isTrue();

		assertThat(Strings.isBase64Url("Zg!?")).isFalse();
		assertThat(Strings.isBase64Url("Zg|:")).isFalse();
		assertThat(Strings.isBase64Url("==Zg")).isFalse();
		assertThat(Strings.isBase64Url("Zg=-")).isFalse();
		assertThat(Strings.isBase64Url("Zg==")).isTrue();
		assertThat(Strings.isBase64Url("Zm8=")).isTrue();
		assertThat(Strings.isBase64Url("+/==")).isFalse();
		assertThat(Strings.isBase64Url("-_==")).isTrue();
	}

	@Test
	void testIsBase64UrlInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBase64Url(null));
	}

	@Test
	void testOfBytes() {
		assertThat(Strings.of(ByteArrays.EMPTY)).isEmpty();
		assertThat(Strings.of("abc".getBytes())).isEqualTo("abc");
		assertThat(Strings.of(StandardCharsets.ISO_8859_1, "abc".getBytes(StandardCharsets.ISO_8859_1))).isEqualTo("abc");

		// Not the same charset
		assertThat(Strings.of(StandardCharsets.ISO_8859_1, "abc".getBytes(StandardCharsets.UTF_16))).isNotEqualTo("abc");
	}

	@Test
	void testOfBytesInvalid() {
		assertThatNullPointerException().isThrownBy(() -> Strings.of((byte[]) null));
		assertThatNullPointerException().isThrownBy(() -> Strings.of(null, "abc".getBytes()));
	}

	@Test
	void testOfChars() {
		assertThat(Strings.of(CharArrays.EMPTY)).isEmpty();
		assertThat(Strings.of("abc".toCharArray())).isEqualTo("abc");
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
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.toChar(STRING));
	}
}