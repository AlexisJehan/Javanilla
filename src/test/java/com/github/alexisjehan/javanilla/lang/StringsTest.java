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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link Strings} unit tests.</p>
 */
final class StringsTest {

	@Test
	void testEmpty() {
		assertThat(Strings.EMPTY).isEmpty();
	}

	@Test
	void testNullToEmpty() {
		assertThat(Strings.nullToEmpty(null)).isEmpty();
		assertThat(Strings.nullToEmpty(Strings.EMPTY)).isEmpty();
		assertThat(Strings.nullToEmpty("test")).isNotEmpty();
	}

	@Test
	void testNullToDefault() {
		assertThat(Strings.nullToDefault(null, "<null>")).isEqualTo("<null>");
		assertThat(Strings.nullToDefault(Strings.EMPTY, "<null>")).isEmpty();
		assertThat(Strings.nullToDefault("test", "<null>")).isNotEqualTo("<null>");
	}

	@Test
	void testNullToDefaultNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.nullToDefault(Strings.EMPTY, null));
	}

	@Test
	void testEmptyToNull() {
		assertThat(Strings.emptyToNull(Strings.EMPTY)).isNull();
		assertThat(Strings.emptyToNull(null)).isNull();
		assertThat(Strings.emptyToNull("test")).isNotNull();
	}

	@Test
	void testBlankToEmpty() {
		assertThat(Strings.blankToEmpty(null)).isEmpty();
		assertThat(Strings.blankToEmpty(Strings.EMPTY)).isEmpty();
		assertThat(Strings.blankToEmpty(" ")).isEmpty();
		assertThat(Strings.blankToEmpty(" \t\n\r")).isEmpty();
		assertThat(Strings.blankToEmpty(" \t\n\rx")).isNotEmpty();
	}

	@Test
	void testBlankToNull() {
		assertThat(Strings.blankToNull(null)).isNull();
		assertThat(Strings.blankToNull(Strings.EMPTY)).isNull();
		assertThat(Strings.blankToNull(" ")).isNull();
		assertThat(Strings.blankToNull(" \t\n\r")).isNull();
		assertThat(Strings.blankToNull(" \t\n\rx")).isNotEmpty();
	}

	@Test
	void testIsBlank() {
		assertThat(Strings.isBlank(Strings.EMPTY)).isTrue();
		assertThat(Strings.isBlank(" ")).isTrue();
		assertThat(Strings.isBlank("x")).isFalse();
		assertThat(Strings.isBlank(" \t\n\r")).isTrue();
		assertThat(Strings.isBlank(" \t\n\rx")).isFalse();
	}

	@Test
	void testIsBlankNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBlank(null));
	}

	@Test
	void testIsHex() {
		assertThat(Strings.isHex(Strings.EMPTY)).isFalse();
		assertThat(Strings.isHex("00f")).isFalse();
		assertThat(Strings.isHex("00!?")).isFalse();
		assertThat(Strings.isHex("00ff")).isTrue();
		assertThat(Strings.isHex("FF00")).isTrue();
		assertThat(Strings.isHex("0xff")).isTrue();
		assertThat(Strings.isHex("0x")).isFalse();
	}

	@Test
	void testIsHexNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isHex(null));
	}

	@Test
	void testIsBase64() {
		assertThat(Strings.isBase64(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBase64("Zg")).isFalse();
		assertThat(Strings.isBase64("Zg!?")).isFalse();
		assertThat(Strings.isBase64("Zg|:")).isFalse();
		assertThat(Strings.isBase64("==Zg")).isFalse();
		assertThat(Strings.isBase64("Zg=+")).isFalse();
		assertThat(Strings.isBase64("Zg==")).isTrue();
		assertThat(Strings.isBase64("Zm8=")).isTrue();
		assertThat(Strings.isBase64("+/==")).isTrue();
	}

	@Test
	void testIsBase64Null() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBase64(null));
	}

	@Test
	void testIsBase64Url() {
		assertThat(Strings.isBase64Url(Strings.EMPTY)).isFalse();
		assertThat(Strings.isBase64Url("Zg")).isFalse();
		assertThat(Strings.isBase64Url("Zg!?")).isFalse();
		assertThat(Strings.isBase64Url("Zg|:")).isFalse();
		assertThat(Strings.isBase64Url("==Zg")).isFalse();
		assertThat(Strings.isBase64Url("Zg=-")).isFalse();
		assertThat(Strings.isBase64Url("Zg==")).isTrue();
		assertThat(Strings.isBase64Url("Zm8=")).isTrue();
		assertThat(Strings.isBase64Url("-_==")).isTrue();
	}

	@Test
	void testIsBase64UrlNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.isBase64Url(null));
	}

	@Test
	void testRepeatChar() {
		assertThat(Strings.repeat('x', 0)).isEmpty();
		assertThat(Strings.repeat('x', 1)).isEqualTo("x");
		assertThat(Strings.repeat('x', 3)).isEqualTo("xxx");
	}

	@Test
	void testRepeatCharInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.repeat('x', -1));
	}

	@Test
	void testRepeatCharSequence() {
		assertThat(Strings.repeat("xX", 0)).isEmpty();
		assertThat(Strings.repeat("xX", 1)).isEqualTo("xX");
		assertThat(Strings.repeat("xX", 3)).isEqualTo("xXxXxX");
		assertThat(Strings.repeat(Strings.EMPTY, 3)).isEmpty();
	}

	@Test
	void testRepeatCharSequenceNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.repeat(null, 1));
	}

	@Test
	void testRepeatCharSequenceInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.repeat("xX", -1));
	}

	@Test
	void testPadLeftChar() {
		assertThat(Strings.padLeft("test", 10)).isEqualTo("      test");
		assertThat(Strings.padLeft("test", 10, 'x')).isEqualTo("xxxxxxtest");
		assertThat(Strings.padLeft("test", 2, 'x')).isEqualTo("test");
		assertThat(Strings.padLeft(Strings.EMPTY, 2, 'x')).isEqualTo("xx");
	}

	@Test
	void testPadLeftCharNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft(null, 1, 'x'));
	}

	@Test
	void testPadLeftCharInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padLeft("test", -1));
	}

	@Test
	void testPadLeftCharSequence() {
		assertThat(Strings.padLeft("test", 10, "xX")).isEqualTo("xXxXxXtest");
		assertThat(Strings.padLeft("test", 11, "xX")).isEqualTo("xXxXxXxtest");
		assertThat(Strings.padLeft("test", 2, "xX")).isEqualTo("test");
		assertThat(Strings.padLeft("test", 10, Strings.EMPTY)).isEqualTo("test");
		assertThat(Strings.padLeft(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padLeft(Strings.EMPTY, 2, "xXx")).isEqualTo("xX");
	}

	@Test
	void testPadLeftCharSequenceNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft(null, 1, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.padLeft("test", 1, null));
	}

	@Test
	void testPadLeftCharSequenceInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padLeft("test", -1, "xX"));
	}

	@Test
	void testPadRightChar() {
		assertThat(Strings.padRight("test", 10)).isEqualTo("test      ");
		assertThat(Strings.padRight("test", 10, 'x')).isEqualTo("testxxxxxx");
		assertThat(Strings.padRight("test", 2, 'x')).isEqualTo("test");
		assertThat(Strings.padRight(Strings.EMPTY, 2, 'x')).isEqualTo("xx");
	}

	@Test
	void testPadRightCharNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight(null, 1, 'x'));
	}

	@Test
	void testPadRightCharInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padRight("test", -1));
	}

	@Test
	void testPadRightCharSequence() {
		assertThat(Strings.padRight("test", 10, "xX")).isEqualTo("testxXxXxX");
		assertThat(Strings.padRight("test", 11, "xX")).isEqualTo("testxXxXxXx");
		assertThat(Strings.padRight("test", 2, "xX")).isEqualTo("test");
		assertThat(Strings.padRight("test", 10, Strings.EMPTY)).isEqualTo("test");
		assertThat(Strings.padRight(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padRight(Strings.EMPTY, 2, "xXx")).isEqualTo("xX");
	}

	@Test
	void testPadRightCharSequenceNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight(null, 1, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.padRight("test", 1, null));
	}

	@Test
	void testPadRightCharSequenceInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padRight("test", -1, "xX"));
	}

	@Test
	void testPadBothChar() {
		assertThat(Strings.padBoth("test", 10)).isEqualTo("   test   ");
		assertThat(Strings.padBoth("test", 11)).isEqualTo("   test    ");
		assertThat(Strings.padBoth("test", 10, 'x')).isEqualTo("xxxtestxxx");
		assertThat(Strings.padBoth("test", 11, 'x')).isEqualTo("xxxtestxxxx");
		assertThat(Strings.padBoth("test", 2, 'x')).isEqualTo("test");
		assertThat(Strings.padBoth(Strings.EMPTY, 2, 'x')).isEqualTo("xx");
	}

	@Test
	void testPadBothCharNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth(null, 1, 'x'));
	}

	@Test
	void testPadBothCharInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padBoth("test", -1));
	}

	@Test
	void testPadBothCharSequence() {
		assertThat(Strings.padBoth("test", 10, "xX")).isEqualTo("xXxtestxXx");
		assertThat(Strings.padBoth("test", 11, "xX")).isEqualTo("xXxtestxXxX");
		assertThat(Strings.padBoth("test", 2, "xX")).isEqualTo("test");
		assertThat(Strings.padBoth("test", 10, Strings.EMPTY)).isEqualTo("test");
		assertThat(Strings.padBoth(Strings.EMPTY, 2, "xX")).isEqualTo("xX");
		assertThat(Strings.padBoth(Strings.EMPTY, 2, "xXx")).isEqualTo("xX");
	}

	@Test
	void testPadBothCharSequenceNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth(null, 1, "xX"));
		assertThatNullPointerException().isThrownBy(() -> Strings.padBoth("test", 1, null));
	}

	@Test
	void testPadBothCharSequenceInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.padBoth("test", -1, "xX"));
	}

	@Test
	void testReplaceFirstChar() {
		assertThat(Strings.replaceFirst("12345123450", '1', '_')).isEqualTo("_2345123450");
		assertThat(Strings.replaceFirst("12345123450", '2', '_')).isEqualTo("1_345123450");
		assertThat(Strings.replaceFirst("12345123450", '0', '_')).isEqualTo("1234512345_");
		assertThat(Strings.replaceFirst("12345123450", 'x', '_')).isEqualTo("12345123450");
	}

	@Test
	void testReplaceFirstCharNull() {
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
	void testReplaceFirstCharSequenceNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst(null, "xX", "__"));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst("12345123450", null, "__"));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceFirst("12345123450", "xX", null));
	}

	@Test
	void testReplaceLastChar() {
		assertThat(Strings.replaceLast("01234512345", '1', '_')).isEqualTo("012345_2345");
		assertThat(Strings.replaceLast("01234512345", '2', '_')).isEqualTo("0123451_345");
		assertThat(Strings.replaceLast("01234512345", '0', '_')).isEqualTo("_1234512345");
		assertThat(Strings.replaceLast("01234512345", 'x', '_')).isEqualTo("01234512345");
	}

	@Test
	void testReplaceLastCharNull() {
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
	void testReplaceLastCharSequenceNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast(null, "xX", "__"));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast("01234512345", null, "__"));
		assertThatNullPointerException().isThrownBy(() -> Strings.replaceLast("01234512345", "xX", null));
	}

	@Test
	void testRemoveStartChar() {
		assertThat(Strings.removeStart("abc", 'z')).isEqualTo("abc");
		assertThat(Strings.removeStart("zabc", 'z')).isEqualTo("abc");
	}

	@Test
	void testRemoveStartCharNull() {
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
	void testRemoveStartCharSequenceNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStart(null, "zzz"));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStart("abc", null));
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
	void testRemoveStartIgnoreCaseNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStartIgnoreCase(null, "zzz"));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeStartIgnoreCase("abc", null));
	}

	@Test
	void testRemoveEndChar() {
		assertThat(Strings.removeEnd("abc", 'z')).isEqualTo("abc");
		assertThat(Strings.removeEnd("abcz", 'z')).isEqualTo("abc");
	}

	@Test
	void testRemoveEndCharNull() {
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
	void testRemoveEndCharSequenceNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEnd(null, "zzz"));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEnd("abc", null));
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
	void testRemoveEndIgnoreCaseNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEndIgnoreCase(null, "zzz"));
		assertThatNullPointerException().isThrownBy(() -> Strings.removeEndIgnoreCase("abc", null));
	}

	@Test
	void testConcatMerge() {
		assertThat(Strings.concatMerge("123456", "456789")).isEqualTo("123456789");
		assertThat(Strings.concatMerge("123", "456")).isEqualTo("123456");
		assertThat(Strings.concatMerge("1234560", "456123")).isEqualTo("1234560456123");
		assertThat(Strings.concatMerge("1234567", "456")).isEqualTo("1234567456");
		assertThat(Strings.concatMerge("123456", Strings.EMPTY)).isEqualTo("123456");
		assertThat(Strings.concatMerge(Strings.EMPTY, "456789")).isEqualTo("456789");
	}

	@Test
	void testConcatMergeNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.concatMerge(null, "456789"));
		assertThatNullPointerException().isThrownBy(() -> Strings.concatMerge("123456", null));
	}

	@Test
	void testToChar() {
		assertThat(Strings.toChar("a")).isEqualTo('a');
	}

	@Test
	void testToCharNull() {
		assertThatNullPointerException().isThrownBy(() -> Strings.toChar(null));
	}

	@Test
	void testToCharInvalid() {
		assertThatIllegalArgumentException().isThrownBy(() -> Strings.toChar(Strings.EMPTY));
	}
}