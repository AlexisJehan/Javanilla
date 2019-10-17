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

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Enumeration to create or convert case stylized {@code String}s, usually used for naming conventions.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Letter_case#Special_case_styles">https://en.wikipedia.org/wiki/Letter_case#Special_case_styles</a>
 * @since 1.5.0
 */
public enum CaseStyle {

	/**
	 * <p>The camel case (aka. lower camel case) style.</p>
	 * <p>Example: {@code fooBar}</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Camel_case">https://en.wikipedia.org/wiki/Camel_case</a>
	 * @since 1.5.0
	 */
	CAMEL {
		@Override
		protected String of(final List<String> tokens) {
			final var stringBuilder = new StringBuilder();
			final var iterator = tokens.iterator();
			stringBuilder.append(iterator.next());
			while (iterator.hasNext()) {
				stringBuilder.append(Strings.capitalize(iterator.next()));
			}
			return stringBuilder.toString();
		}

		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return CaseStyle.tokenize(charSequence, Character::isUpperCase, false);
		}
	},

	/**
	 * <p>The Pascal case (aka. upper camel case) style.</p>
	 * <p>Example: {@code FooBar}</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Pascal_case">https://en.wikipedia.org/wiki/Pascal_case</a>
	 * @since 1.5.0
	 */
	PASCAL {
		@Override
		protected String of(final List<String> tokens) {
			return tokens
					.stream()
					.map(Strings::capitalize)
					.collect(Collectors.joining());
		}

		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return CAMEL.tokenize(charSequence);
		}
	},

	/**
	 * <p>The snake case (aka. pothole case) style.</p>
	 * <p>Example: {@code foo_bar}</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Snake_case">https://en.wikipedia.org/wiki/Snake_case</a>
	 * @since 1.5.0
	 */
	SNAKE {
		@Override
		protected String of(final List<String> tokens) {
			return String.join("_", tokens);
		}

		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return CaseStyle.tokenize(charSequence, Predicate.isEqual('_'), true);
		}
	},

	/**
	 * <p>The macro case (aka. screaming snake case) style.</p>
	 * <p>Example: {@code FOO_BAR}</p>
	 * @since 1.5.0
	 */
	MACRO {
		@Override
		protected String of(final List<String> tokens) {
			return SNAKE.of(tokens).toUpperCase();
		}

		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return SNAKE.tokenize(charSequence);
		}
	},

	/**
	 * <p>The kebab case (aka. spinal case, param case, Lisp case or dash case) style.</p>
	 * <p>Example: {@code foo-bar}</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Kebab_case">https://en.wikipedia.org/wiki/Kebab_case</a>
	 * @since 1.5.0
	 */
	KEBAB {
		@Override
		protected String of(final List<String> tokens) {
			return String.join("-", tokens);
		}

		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return CaseStyle.tokenize(charSequence, Predicate.isEqual('-'), true);
		}
	},

	/**
	 * <p>The Cobol case (aka. train case) style.</p>
	 * <p>Example: {@code FOO-BAR}</p>
	 * @since 1.5.0
	 */
	COBOL {
		@Override
		protected String of(final List<String> tokens) {
			return KEBAB.of(tokens).toUpperCase();
		}

		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return KEBAB.tokenize(charSequence);
		}
	};

	/**
	 * <p>Create a stylized {@code String} from the given {@code CharSequence} using the current case style.</p>
	 * @param charSequence the {@code CharSequence} to convert
	 * @return a stylized {@code String}
	 * @throws NullPointerException if the {@code CharSequence} is {@code null}
	 * @since 1.5.0
	 */
	public final String of(final CharSequence charSequence) {
		Ensure.notNull("charSequence", charSequence);
		final var tokens = tokenize(charSequence, Character::isWhitespace, true);
		if (tokens.isEmpty()) {
			return Strings.EMPTY;
		}
		return of(tokens);
	}

	/**
	 * <p>Create a stylized {@code String} from the given already stylized {@code CharSequence} using the current case
	 * style.</p>
	 * @param charSequence the stylized {@code CharSequence} to convert
	 * @param caseStyle the {@code CaseStyle} of the given {@code CharSequence}
	 * @return a stylized {@code String}
	 * @throws NullPointerException if the {@code CharSequence} or the {@code CaseStyle} is {@code null}
	 * @since 1.5.0
	 */
	public final String of(final CharSequence charSequence, final CaseStyle caseStyle) {
		Ensure.notNull("charSequence", charSequence);
		Ensure.notNull("caseStyle", caseStyle);
		final var tokens = caseStyle.tokenize(charSequence);
		if (tokens.isEmpty()) {
			return Strings.EMPTY;
		}
		return of(tokens);
	}

	/**
	 * <p>Create a stylized {@code String} from the given {@code List} of tokens.</p>
	 * @param tokens the {@code List} of tokens
	 * @return a stylized {@code String}
	 * @since 1.5.0
	 */
	protected abstract String of(final List<String> tokens);

	/**
	 * <p>Tokenize the given {@code CharSequence} using the current case style.</p>
	 * @param charSequence the {@code CharSequence} to tokenize
	 * @return a {@code List} of tokens from the {@code CharSequence}
	 * @since 1.5.0
	 */
	protected abstract List<String> tokenize(final CharSequence charSequence);

	/**
	 * <p>Tokenize the given {@code CharSequence} using a delimiter {@code Predicate}.</p>
	 * @param charSequence the {@code CharSequence} to tokenize
	 * @param delimiterPredicate the delimiter {@code Predicate}
	 * @param exclude {@code true} if the current delimiter should be excluded from the next token
	 * @return a {@code List} of tokens from the {@code CharSequence}
	 * @since 1.5.0
	 */
	private static List<String> tokenize(final CharSequence charSequence, final Predicate<Character> delimiterPredicate, final boolean exclude) {
		final var length = charSequence.length();
		if (0 == length) {
			return List.of();
		}
		final var tokens = new ArrayList<String>();
		var index = 0;
		for (var i = exclude ? 0 : 1; i < length; ++i) {
			if (delimiterPredicate.test(charSequence.charAt(i))) {
				tokens.add(charSequence.subSequence(index, i).toString().toLowerCase());
				index = i + (exclude ? 1 : 0);
			}
		}
		tokens.add(charSequence.subSequence(index, length).toString().toLowerCase());
		return tokens;
	}
}