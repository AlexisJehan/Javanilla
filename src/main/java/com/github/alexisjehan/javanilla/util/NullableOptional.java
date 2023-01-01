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
package com.github.alexisjehan.javanilla.util;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;
import com.github.alexisjehan.javanilla.util.stream.Streamable;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * <p>A class based on {@link Optional} but which accepts nullable values. Empty and {@code null} are distinct, this is
 * useful for example to return optional values when {@code null} is allowed such a "get" method for a collection.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <T> the type of the value
 * @since 1.1.0
 */
public final class NullableOptional<T> implements Streamable<T> {

	/**
	 * <p>Common instance for {@link #empty()}.</p>
	 * @since 1.1.0
	 */
	private static final NullableOptional<?> EMPTY = new NullableOptional<>();

	/**
	 * <p>Nullable value or {@code null} if not present.</p>
	 * @since 1.1.0
	 */
	private final T value;

	/**
	 * <p>Whether or not a value is present.</p>
	 * @since 1.1.0
	 */
	private final boolean isEmpty;

	/**
	 * <p>Private constructor for an empty instance.</p>
	 * @since 1.1.0
	 */
	private NullableOptional() {
		value = null;
		isEmpty = true;
	}

	/**
	 * <p>Private constructor for an instance with the given nullable value.</p>
	 * @param value the nullable value
	 * @since 1.1.0
	 */
	private NullableOptional(final T value) {
		this.value = value;
		isEmpty = false;
	}

	/**
	 * <p>Get the nullable value or throw a {@link NoSuchElementException} if the value is not present.</p>
	 * @return the nullable value if present
	 * @throws NoSuchElementException if no value is present
	 * @since 1.1.0
	 */
	public T get() {
		if (isEmpty) {
			throw new NoSuchElementException("No value present");
		}
		return value;
	}

	/**
	 * <p>Tell if a value is present.</p>
	 * @return {@code true} if a value is present
	 * @since 1.1.0
	 */
	public boolean isPresent() {
		return !isEmpty;
	}

	/**
	 * <p>Tell if no value is present.</p>
	 * @return {@code true} if no value is present
	 * @since 1.1.0
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * <p>If a value is present, performs the given action with the value, otherwise does nothing.</p>
	 * @param action the action to be performed, if a value is present
	 * @throws NullPointerException if the action is {@code null}
	 * @since 1.1.0
	 */
	public void ifPresent(final Consumer<? super T> action) {
		Ensure.notNull("action", action);
		if (!isEmpty) {
			action.accept(value);
		}
	}

	/**
	 * <p>If a value is present, performs the given action with the value, otherwise performs the given empty-based
	 * action.</p>
	 * @param action the action to be performed, if a value is present
	 * @param emptyAction the empty-based action to be performed, if no value is present
	 * @throws NullPointerException if the action or the empty-based action is {@code null}
	 * @since 1.1.0
	 */
	public void ifPresentOrElse(final Consumer<? super T> action, final Runnable emptyAction) {
		Ensure.notNull("action", action);
		Ensure.notNull("emptyAction", emptyAction);
		if (!isEmpty) {
			action.accept(value);
		} else {
			emptyAction.run();
		}
	}

	/**
	 * <p>If a value is present, and the value matches the given filter {@link Predicate}, returns a
	 * {@link NullableOptional} describing the value, otherwise returns an empty {@link NullableOptional}.</p>
	 * @param filter the filter {@link Predicate} to apply to a value, if present
	 * @return a {@link NullableOptional} describing the value of this {@link NullableOptional}, if a value is present
	 *         and the value matches the given filter {@link Predicate}, otherwise an empty {@link NullableOptional}
	 * @throws NullPointerException if the filter {@link Predicate} is {@code null}
	 * @since 1.1.0
	 */
	public NullableOptional<T> filter(final Predicate<? super T> filter) {
		Ensure.notNull("filter", filter);
		if (isEmpty) {
			return this;
		}
		return filter.test(value) ? this : empty();
	}

	/**
	 * <p>If a value is present, returns a {@link NullableOptional} describing the result of applying the given mapper
	 * {@link Function} to the value, otherwise returns an empty {@link NullableOptional}.</p>
	 * @param mapper the mapper {@link Function} to apply to a value, if present
	 * @param <U> the type of the value returned from the mapper {@link Function}
	 * @return a {@link NullableOptional} describing the result of applying a mapper {@link Function} to the value of
	 *         this {@link NullableOptional}, if a value is present, otherwise an empty {@link NullableOptional}
	 * @throws NullPointerException if the mapper {@link Function} is {@code null}
	 * @since 1.1.0
	 */
	public <U> NullableOptional<U> map(final Function<? super T, ? extends U> mapper) {
		Ensure.notNull("mapper", mapper);
		if (isEmpty) {
			return empty();
		}
		return of(mapper.apply(value));
	}

	/**
	 * <p>If a value is present, returns the result of applying the given {@link NullableOptional}-bearing mapper
	 * {@link Function} to the value, otherwise returns an empty {@link NullableOptional}.</p>
	 * <p>This method is similar to {@link #map(Function)}, but the mapper {@link Function} is one whose result is
	 * already a {@link NullableOptional}, and if invoked, {@code flatMap} does not wrap it within an additional
	 * {@link NullableOptional}.</p>
	 * @param mapper the mapper {@link Function} to apply to a value, if present
	 * @param <U> the type of value of the {@link NullableOptional} returned by the mapper {@link Function}
	 * @return the result of applying an {@link NullableOptional}-bearing mapper {@link Function} to the value of this
	 *         {@link NullableOptional}, if a value is present, otherwise an empty {@link NullableOptional}
	 * @throws NullPointerException if the mapper {@link Function} is {@code null}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public <U> NullableOptional<U> flatMap(final Function<? super T, ? extends NullableOptional<? extends U>> mapper) {
		Ensure.notNull("mapper", mapper);
		if (isEmpty) {
			return empty();
		}
		return Objects.requireNonNull((NullableOptional<U>) mapper.apply(value));
	}

	/**
	 * <p>If a value is present, returns a {@link NullableOptional} describing the value, otherwise returns an
	 * {@link NullableOptional} produced by the {@link Supplier}.</p>
	 * @param supplier the {@link Supplier} that produces an {@link NullableOptional} to be returned
	 * @return returns a {@link NullableOptional} describing the value of this {@link NullableOptional}, if a value is
	 *         present, otherwise a {@link NullableOptional} produced by the {@link Supplier}
	 * @throws NullPointerException if the {@link Supplier} is {@code null}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public NullableOptional<T> or(final Supplier<? extends NullableOptional<? extends T>> supplier) {
		Ensure.notNull("supplier", supplier);
		if (!isEmpty) {
			return this;
		}
		return Objects.requireNonNull((NullableOptional<T>) supplier.get());
	}

	/**
	 * <p>If a value is present, returns a sequential {@link Stream} containing only that value, otherwise returns an
	 * empty {@link Stream}.</p>
	 * @return the optional value as a {@link Stream}
	 * @since 1.1.0
	 */
	@Override
	public Stream<T> stream() {
		if (isEmpty) {
			return Stream.empty();
		}
		return Stream.of(value);
	}

	/**
	 * <p>If a value is present, returns the value, otherwise returns the given other value.</p>
	 * @param other the value to be returned, if no value is present or {@code null}
	 * @return the value, if present, otherwise the given other value
	 * @since 1.1.0
	 */
	public T orElse(final T other) {
		return !isEmpty ? value : other;
	}

	/**
	 * <p>If a value is present, returns the value, otherwise returns the result produced by the {@link Supplier}.</p>
	 * @param supplier the {@link Supplier} that produces a value to be returned
	 * @return the value, if present, otherwise the result produced by the {@link Supplier}
	 * @throws NullPointerException if the {@link Supplier} is {@code null}
	 * @since 1.1.0
	 */
	public T orElseGet(final Supplier<? extends T> supplier) {
		Ensure.notNull("supplier", supplier);
		return !isEmpty ? value : supplier.get();
	}

	/**
	 * <p>If a value is present, returns the value, otherwise throws {@link NoSuchElementException}.</p>
	 * @return the value, if present
	 * @throws NoSuchElementException if no value is present
	 * @since 1.3.1
	 */
	public T orElseThrow() {
		return get();
	}

	/**
	 * <p>If a value is present, returns the value, otherwise throws a {@link Throwable} produced by the
	 * {@link Supplier}.</p>
	 * @param throwableSupplier the {@link Supplier} that produces a {@link Throwable} to be thrown
	 * @param <X> type of the {@link Throwable} to be thrown
	 * @return the value, if present
	 * @throws X if no value is present
	 * @throws NullPointerException if the {@link Throwable} {@link Supplier} is {@code null}
	 * @since 1.1.0
	 */
	public <X extends Throwable> T orElseThrow(final Supplier<? extends X> throwableSupplier) throws X {
		Ensure.notNull("throwableSupplier", throwableSupplier);
		if (!isEmpty) {
			return value;
		}
		throw throwableSupplier.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof NullableOptional)) {
			return false;
		}
		final var other = (NullableOptional<?>) object;
		return Equals.equals(value, other.value)
				&& Equals.equals(isEmpty, other.isEmpty);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.of(
				HashCode.hashCode(value),
				HashCode.hashCode(isEmpty)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + (!isEmpty ? "[" + value + "]" : ".empty");
	}

	/**
	 * <p>Converts the current {@link NullableOptional} to an {@link Optional} using
	 * {@link Optional#ofNullable(Object)}.</p>
	 * @return the created {@link Optional}
	 * @since 1.1.0
	 */
	public Optional<T> toOptional() {
		return Optional.ofNullable(value);
	}

	/**
	 * <p>Returns an empty {@link NullableOptional} instance. No value is present for this {@link NullableOptional}.</p>
	 * @param <T> the type of the non-existent value
	 * @return an empty {@link NullableOptional}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> NullableOptional<T> empty() {
		return (NullableOptional<T>) EMPTY;
	}

	/**
	 * <p>Returns a {@link NullableOptional} describing the given nullable value.</p>
	 * @param value the value to describe or {@code null}
	 * @param <T> the type of the value
	 * @return a {@link NullableOptional} with the value present
	 * @since 1.1.0
	 */
	public static <T> NullableOptional<T> of(final T value) {
		return new NullableOptional<>(value);
	}

	/**
	 * <p>Create a {@link NullableOptional} with an {@link Optional}.</p>
	 * @param optional the {@link Optional} to convert
	 * @param <T> the type of the value
	 * @return the created {@link NullableOptional}
	 * @since 1.1.0
	 */
	public static <T> NullableOptional<T> ofOptional(final Optional<T> optional) {
		Ensure.notNull("optional", optional);
		return optional.map(NullableOptional::of).orElseGet(NullableOptional::empty);
	}
}