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
package com.github.alexisjehan.javanilla.misc;

import com.github.alexisjehan.javanilla.lang.Strings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * <p>{@link CaseStyle} unit tests.</p>
 */
final class CaseStyleTest {

	@Test
	void testOfCamel() {
		final var caseStyle = CaseStyle.CAMEL;
		assertThat(caseStyle.of(Strings.EMPTY)).isEmpty();
		assertThat(caseStyle.of("foo")).isEqualTo("foo");
		assertThat(caseStyle.of("foo bar")).isEqualTo("fooBar");
		assertThat(caseStyle.of("Foo Bar")).isEqualTo("fooBar");
		assertThat(caseStyle.of("FOO BAR")).isEqualTo("fooBar");
		assertThat(caseStyle.of(Strings.EMPTY, caseStyle)).isEmpty();
		assertThat(caseStyle.of("fooBar", CaseStyle.CAMEL)).isEqualTo("fooBar");
		assertThat(caseStyle.of("FooBar", CaseStyle.PASCAL)).isEqualTo("fooBar");
		assertThat(caseStyle.of("foo_bar", CaseStyle.SNAKE)).isEqualTo("fooBar");
		assertThat(caseStyle.of("FOO_BAR", CaseStyle.MACRO)).isEqualTo("fooBar");
		assertThat(caseStyle.of("foo-bar", CaseStyle.KEBAB)).isEqualTo("fooBar");
		assertThat(caseStyle.of("FOO-BAR", CaseStyle.COBOL)).isEqualTo("fooBar");
	}

	@Test
	void testOfPascal() {
		final var caseStyle = CaseStyle.PASCAL;
		assertThat(caseStyle.of(Strings.EMPTY)).isEmpty();
		assertThat(caseStyle.of("foo")).isEqualTo("Foo");
		assertThat(caseStyle.of("foo bar")).isEqualTo("FooBar");
		assertThat(caseStyle.of("Foo Bar")).isEqualTo("FooBar");
		assertThat(caseStyle.of("FOO BAR")).isEqualTo("FooBar");
		assertThat(caseStyle.of(Strings.EMPTY, caseStyle)).isEmpty();
		assertThat(caseStyle.of("fooBar", CaseStyle.CAMEL)).isEqualTo("FooBar");
		assertThat(caseStyle.of("FooBar", CaseStyle.PASCAL)).isEqualTo("FooBar");
		assertThat(caseStyle.of("foo_bar", CaseStyle.SNAKE)).isEqualTo("FooBar");
		assertThat(caseStyle.of("FOO_BAR", CaseStyle.MACRO)).isEqualTo("FooBar");
		assertThat(caseStyle.of("foo-bar", CaseStyle.KEBAB)).isEqualTo("FooBar");
		assertThat(caseStyle.of("FOO-BAR", CaseStyle.COBOL)).isEqualTo("FooBar");
	}

	@Test
	void testOfSnake() {
		final var caseStyle = CaseStyle.SNAKE;
		assertThat(caseStyle.of(Strings.EMPTY)).isEmpty();
		assertThat(caseStyle.of("foo")).isEqualTo("foo");
		assertThat(caseStyle.of("foo bar")).isEqualTo("foo_bar");
		assertThat(caseStyle.of("Foo Bar")).isEqualTo("foo_bar");
		assertThat(caseStyle.of("FOO BAR")).isEqualTo("foo_bar");
		assertThat(caseStyle.of(Strings.EMPTY, caseStyle)).isEmpty();
		assertThat(caseStyle.of("fooBar", CaseStyle.CAMEL)).isEqualTo("foo_bar");
		assertThat(caseStyle.of("FooBar", CaseStyle.PASCAL)).isEqualTo("foo_bar");
		assertThat(caseStyle.of("foo_bar", CaseStyle.SNAKE)).isEqualTo("foo_bar");
		assertThat(caseStyle.of("FOO_BAR", CaseStyle.MACRO)).isEqualTo("foo_bar");
		assertThat(caseStyle.of("foo-bar", CaseStyle.KEBAB)).isEqualTo("foo_bar");
		assertThat(caseStyle.of("FOO-BAR", CaseStyle.COBOL)).isEqualTo("foo_bar");
	}

	@Test
	void testOfMacro() {
		final var caseStyle = CaseStyle.MACRO;
		assertThat(caseStyle.of(Strings.EMPTY)).isEmpty();
		assertThat(caseStyle.of("foo")).isEqualTo("FOO");
		assertThat(caseStyle.of("foo bar")).isEqualTo("FOO_BAR");
		assertThat(caseStyle.of("Foo Bar")).isEqualTo("FOO_BAR");
		assertThat(caseStyle.of("FOO BAR")).isEqualTo("FOO_BAR");
		assertThat(caseStyle.of(Strings.EMPTY, caseStyle)).isEmpty();
		assertThat(caseStyle.of("fooBar", CaseStyle.CAMEL)).isEqualTo("FOO_BAR");
		assertThat(caseStyle.of("FooBar", CaseStyle.PASCAL)).isEqualTo("FOO_BAR");
		assertThat(caseStyle.of("foo_bar", CaseStyle.SNAKE)).isEqualTo("FOO_BAR");
		assertThat(caseStyle.of("FOO_BAR", CaseStyle.MACRO)).isEqualTo("FOO_BAR");
		assertThat(caseStyle.of("foo-bar", CaseStyle.KEBAB)).isEqualTo("FOO_BAR");
		assertThat(caseStyle.of("FOO-BAR", CaseStyle.COBOL)).isEqualTo("FOO_BAR");
	}

	@Test
	void testOfKebab() {
		final var caseStyle = CaseStyle.KEBAB;
		assertThat(caseStyle.of(Strings.EMPTY)).isEmpty();
		assertThat(caseStyle.of("foo")).isEqualTo("foo");
		assertThat(caseStyle.of("foo bar")).isEqualTo("foo-bar");
		assertThat(caseStyle.of("Foo Bar")).isEqualTo("foo-bar");
		assertThat(caseStyle.of("FOO BAR")).isEqualTo("foo-bar");
		assertThat(caseStyle.of(Strings.EMPTY, caseStyle)).isEmpty();
		assertThat(caseStyle.of("fooBar", CaseStyle.CAMEL)).isEqualTo("foo-bar");
		assertThat(caseStyle.of("FooBar", CaseStyle.PASCAL)).isEqualTo("foo-bar");
		assertThat(caseStyle.of("foo_bar", CaseStyle.SNAKE)).isEqualTo("foo-bar");
		assertThat(caseStyle.of("FOO_BAR", CaseStyle.MACRO)).isEqualTo("foo-bar");
		assertThat(caseStyle.of("foo-bar", CaseStyle.KEBAB)).isEqualTo("foo-bar");
		assertThat(caseStyle.of("FOO-BAR", CaseStyle.COBOL)).isEqualTo("foo-bar");
	}

	@Test
	void testOfCobol() {
		final var caseStyle = CaseStyle.COBOL;
		assertThat(caseStyle.of(Strings.EMPTY)).isEmpty();
		assertThat(caseStyle.of("foo")).isEqualTo("FOO");
		assertThat(caseStyle.of("foo bar")).isEqualTo("FOO-BAR");
		assertThat(caseStyle.of("Foo Bar")).isEqualTo("FOO-BAR");
		assertThat(caseStyle.of("FOO BAR")).isEqualTo("FOO-BAR");
		assertThat(caseStyle.of(Strings.EMPTY, caseStyle)).isEmpty();
		assertThat(caseStyle.of("fooBar", CaseStyle.CAMEL)).isEqualTo("FOO-BAR");
		assertThat(caseStyle.of("FooBar", CaseStyle.PASCAL)).isEqualTo("FOO-BAR");
		assertThat(caseStyle.of("foo_bar", CaseStyle.SNAKE)).isEqualTo("FOO-BAR");
		assertThat(caseStyle.of("FOO_BAR", CaseStyle.MACRO)).isEqualTo("FOO-BAR");
		assertThat(caseStyle.of("foo-bar", CaseStyle.KEBAB)).isEqualTo("FOO-BAR");
		assertThat(caseStyle.of("FOO-BAR", CaseStyle.COBOL)).isEqualTo("FOO-BAR");
	}

	@Test
	void testOfInvalid() {
		final var caseStyle = CaseStyle.CAMEL;
		assertThatNullPointerException().isThrownBy(() -> caseStyle.of((CharSequence) null));
		assertThatNullPointerException().isThrownBy(() -> caseStyle.of(null, caseStyle));
		assertThatNullPointerException().isThrownBy(() -> caseStyle.of("foo", null));
	}
}