/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.function.serializable;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.util.function.Procedure;

import java.io.Serializable;

/**
 * Interface for a {@link Procedure} that is {@link Serializable}.
 *
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is {@link #execute()}.</p>
 * @deprecated since 1.8.0, use {@link com.github.alexisjehan.javanilla.util.function.SerializableProcedure} instead
 * @since 1.7.0
 */
@FunctionalInterface
@Deprecated(since = "1.8.0")
public interface SerializableProcedure extends Procedure, Serializable {

	/**
	 * Create a {@code SerializableProcedure} from the given {@link Procedure}.
	 * @param procedure the {@link Procedure} to convert
	 * @return the created {@code SerializableProcedure}
	 * @throws NullPointerException if the {@link Procedure} is {@code null}
	 * @since 1.7.0
	 */
	static SerializableProcedure of(final Procedure procedure) {
		Ensure.notNull("procedure", procedure);
		return procedure::execute;
	}
}