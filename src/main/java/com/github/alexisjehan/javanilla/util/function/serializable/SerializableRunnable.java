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
package com.github.alexisjehan.javanilla.util.function.serializable;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;

import java.io.Serializable;

/**
 * <p>Interface for a {@link Runnable} that is {@link Serializable}.</p>
 * <p><b>Note</b>: This interface is a {@link FunctionalInterface} whose abstract method is {@link #run()}.</p>
 * @deprecated since 1.7.0, use {@link SerializableProcedure} instead
 * @since 1.4.0
 */
@FunctionalInterface
@Deprecated(since = "1.7.0")
public interface SerializableRunnable extends Runnable, Serializable {

	/**
	 * <p>Create a {@code SerializableRunnable} from the given {@link Runnable}.</p>
	 * @param runnable the {@link Runnable} to convert
	 * @return the created {@code SerializableRunnable}
	 * @throws NullPointerException if the {@link Runnable} is {@code null}
	 * @since 1.4.0
	 */
	static SerializableRunnable of(final Runnable runnable) {
		Ensure.notNull("runnable", runnable);
		return runnable::run;
	}
}