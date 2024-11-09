/*
 * MIT License
 *
 * Copyright (c) 2018-2024 Alexis Jehan
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
 * Enumeration to create or convert case stylized {@link String}s, usually used for naming conventions.
 * @see <a href="https://en.wikipedia.org/wiki/Letter_case#Special_case_styles">https://en.wikipedia.org/wiki/Letter_case#Special_case_styles</a>
 * @since 1.5.0
 */
public enum CaseStyle {

	/**
	 * The camel case (aka. lower camel case) style.
	 *
	 * <p>Example: {@code fooBar}</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Camel_case">https://en.wikipedia.org/wiki/Camel_case</a>
	 * @since 1.5.0
	 */
	CAMEL {
		/**
		 * {@inheritDoc}
		 */
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return CaseStyle.tokenize(charSequence, Character::isUpperCase, false);
		}
	},

	/**
	 * The Pascal case (aka. upper camel case) style.
	 *
	 * <p>Example: {@code FooBar}</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Pascal_case">https://en.wikipedia.org/wiki/Pascal_case</a>
	 * @since 1.5.0
	 */
	PASCAL {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String of(final List<String> tokens) {
			return tokens
					.stream()
					.map(Strings::capitalize)
					.collect(Collectors.joining());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return CAMEL.tokenize(charSequence);
		}
	},

	/**
	 * The snake case (aka. pothole case) style.
	 *
	 * <p>Example: {@code foo_bar}</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Snake_case">https://en.wikipedia.org/wiki/Snake_case</a>
	 * @since 1.5.0
	 */
	SNAKE {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String of(final List<String> tokens) {
			return String.join("_", tokens);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return CaseStyle.tokenize(charSequence, Predicate.isEqual('_'), true);
		}
	},

	/**
	 * The macro case (aka. screaming snake case) style.
	 *
	 * <p>Example: {@code FOO_BAR}</p>
	 * @since 1.5.0
	 */
	MACRO {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String of(final List<String> tokens) {
			return SNAKE.of(tokens).toUpperCase();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return SNAKE.tokenize(charSequence);
		}
	},

	/**
	 * The kebab case (aka. spinal case, param case, Lisp case or dash case) style.
	 *
	 * <p>Example: {@code foo-bar}</p>
	 * @see <a href="https://en.wikipedia.org/wiki/Kebab_case">https://en.wikipedia.org/wiki/Kebab_case</a>
	 * @since 1.5.0
	 */
	KEBAB {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String of(final List<String> tokens) {
			return String.join("-", tokens);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return CaseStyle.tokenize(charSequence, Predicate.isEqual('-'), true);
		}
	},

	/**
	 * The Cobol case (aka. train case) style.
	 *
	 * <p>Example: {@code FOO-BAR}</p>
	 * @since 1.5.0
	 */
	COBOL {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String of(final List<String> tokens) {
			return KEBAB.of(tokens).toUpperCase();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected List<String> tokenize(final CharSequence charSequence) {
			return KEBAB.tokenize(charSequence);
		}
	};

	/**
	 * Create a stylized {@link String} from the given {@link CharSequence} using the current case style.
	 * @param charSequence the {@link CharSequence} to convert
	 * @return a stylized {@link String}
	 * @throws NullPointerException if the {@link CharSequence} is {@code null}
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
	 * Create a stylized {@link String} from the given already stylized {@link CharSequence} using the current case
	 * style.
	 * @param charSequence the stylized {@link CharSequence} to convert
	 * @param caseStyle the {@code CaseStyle} of the given {@link CharSequence}
	 * @return a stylized {@link String}
	 * @throws NullPointerException if the {@link CharSequence} or the {@code CaseStyle} is {@code null}
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
	 * Create a stylized {@link String} from the given {@link List} of tokens.
	 * @param tokens the {@link List} of tokens
	 * @return a stylized {@link String}
	 * @since 1.5.0
	 */
	protected abstract String of(List<String> tokens);

	/**
	 * Tokenize the given {@link CharSequence} using the current case style.
	 * @param charSequence the {@link CharSequence} to tokenize
	 * @return a {@link List} of tokens from the {@link CharSequence}
	 * @since 1.5.0
	 */
	protected abstract List<String> tokenize(CharSequence charSequence);

	/**
	 * Tokenize the given {@link CharSequence} using a delimiter {@link Predicate}.
	 * @param charSequence the {@link CharSequence} to tokenize
	 * @param delimiterPredicate the delimiter {@link Predicate}
	 * @param exclude {@code true} if the current delimiter should be excluded from the next token
	 * @return a {@link List} of tokens from the {@link CharSequence}
	 * @since 1.5.0
	 */
	static List<String> tokenize(final CharSequence charSequence, final Predicate<Character> delimiterPredicate, final boolean exclude) {
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