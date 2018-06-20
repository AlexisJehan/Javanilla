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
package com.github.alexisjehan.javanilla.util;

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
 * useful for example to return optional values when {@code null} is allowed such as a "get" method for a
 * collection.</p>
 * @param <T> the type of the value
 * @since 1.1.0
 */
public final class NullableOptional<T> implements Streamable<T> {

	/**
	 * <p>Common instance for {@link #empty()}.</p>
	 * @since 1.1.0
	 */
	private static final NullableOptional EMPTY = new NullableOptional();

	/**
	 * <p>The nullable value or {@code null} if not present.</p>
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
	 * <p>Private constructor for a present instance with the given nullable value.</p>
	 * @param value the nullable value
	 * @since 1.1.0
	 */
	private NullableOptional(final T value) {
		this.value = value;
		isEmpty = false;
	}

	/**
	 * <p>Get the nullable value or throw a {@code NoSuchElementException} if the value is not present.</p>
	 * @return the nullable value if present
	 * @throws NoSuchElementException if no value is present
	 * @since 1.1.0
	 */
	public T get() {
		if (isEmpty()) {
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
		if (null == action) {
			throw new NullPointerException("Invalid action (not null expected)");
		}
		if (isPresent()) {
			action.accept(value);
		}
	}

	/**
	 * <p>If a value is present, performs the given action with the value, otherwise performs the given empty-based
	 * action.</p>
	 * @param action the action to be performed, if a value is present
	 * @param emptyAction the empty-based action to be performed, if no value is present
	 * @throws NullPointerException whether the action or the empty-based action is {@code null}
	 * @since 1.1.0
	 */
	public void ifPresentOrElse(final Consumer<? super T> action, final Runnable emptyAction) {
		if (null == action) {
			throw new NullPointerException("Invalid action (not null expected)");
		}
		if (null == emptyAction) {
			throw new NullPointerException("Invalid empty action (not null expected)");
		}
		if (isPresent()) {
			action.accept(value);
		} else {
			emptyAction.run();
		}
	}

	/**
	 * <p>If a value is present, and the value matches the given predicate, returns a {@code NullableOptional}
	 * describing the value, otherwise returns an empty {@code NullableOptional}.</p>
	 * @param predicate the predicate to apply to a value, if present
	 * @return a {@code NullableOptional} describing the value of this {@code NullableOptional}, if a value is present
	 * and the value matches the given predicate, otherwise an empty {@code NullableOptional}
	 * @throws NullPointerException if the {@code Predicate} is {@code null}
	 * @since 1.1.0
	 */
	public NullableOptional<T> filter(final Predicate<? super T> predicate) {
		if (null == predicate) {
			throw new NullPointerException("Invalid predicate (not null expected)");
		}
		if (isEmpty()) {
			return this;
		}
		return predicate.test(value) ? this : empty();
	}

	/**
	 * <p>If a value is present, returns a {@code NullableOptional} describing the result of applying the given mapping
	 * function to the value, otherwise returns an empty {@code NullableOptional}.</p>
	 * @param mapper the mapping function to apply to a value, if present
	 * @param <U> the type of the value returned from the mapping function
	 * @return a {@code NullableOptional} describing the result of applying a mapping function to the value of this
	 * {@code NullableOptional}, if a value is present, otherwise an empty {@code NullableOptional}
	 * @throws NullPointerException if the mapper {@code Function} is {@code null}
	 * @since 1.1.0
	 */
	public <U> NullableOptional<U> map(final Function<? super T, ? extends U> mapper) {
		if (null == mapper) {
			throw new NullPointerException("Invalid mapper (not null expected)");
		}
		if (isEmpty()) {
			return empty();
		}
		return NullableOptional.of(mapper.apply(value));
	}

	/**
	 * <p>If a value is present, returns the result of applying the given {@code NullableOptional}-bearing mapping
	 * function to the value, otherwise returns an empty {@code NullableOptional}.</p>
	 * <p>This method is similar to {@link #map(Function)}, but the mapping function is one whose result is already a
	 * {@code NullableOptional}, and if invoked, {@code flatMap} does not wrap it within an additional
	 * {@code NullableOptional}.
	 * @param mapper the mapping function to apply to a value, if present
	 * @param <U> the type of value of the {@code NullableOptional} returned by the mapping function
	 * @return the result of applying an {@code NullableOptional}-bearing mapping function to the value of this
	 * {@code NullableOptional}, if a value is present, otherwise an empty {@code NullableOptional}
	 * @throws NullPointerException if the mapper {@code Function} is {@code null}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public <U> NullableOptional<U> flatMap(final Function<? super T, ? extends NullableOptional<? extends U>> mapper) {
		if (null == mapper) {
			throw new NullPointerException("Invalid mapper (not null expected)");
		}
		if (isEmpty()) {
			return empty();
		}
		return Objects.requireNonNull((NullableOptional<U>) mapper.apply(value), "Invalid nullable optional (not null expected)");
	}

	/**
	 * <p>If a value is present, returns a {@code NullableOptional} describing the value, otherwise returns an
	 * {@code NullableOptional} produced by the supplying function.</p>
	 * @param supplier the supplying function that produces an {@code NullableOptional} to be returned
	 * @return returns a {@code NullableOptional} describing the value of this {@code NullableOptional}, if a value is
	 * present, otherwise a {@code NullableOptional} produced by the supplying function
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public NullableOptional<T> or(final Supplier<? extends NullableOptional<? extends T>> supplier) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		if (isPresent()) {
			return this;
		}
		return Objects.requireNonNull((NullableOptional<T>) supplier.get(), "Invalid nullable optional (not null expected)");
	}

	/**
	 * <p>If a value is present, returns a sequential {@code Stream} containing only that value, otherwise returns an
	 * empty {@code Stream}.</p>
	 * @return the optional value as a {@code Stream}
	 * @since 1.1.0
	 */
	@Override
	public Stream<T> stream() {
		if (isEmpty()) {
			return Stream.empty();
		}
		return Stream.of(value);
	}

	/**
	 * <p>f a value is present, returns the value, otherwise returns the given other value.</p>
	 * @param other the value to be returned, if no value is present or {@code null}
	 * @return the value, if present, otherwise the given other value
	 * @since 1.1.0
	 */
	public T orElse(final T other) {
		return isPresent() ? value : other;
	}

	/**
	 * <p>If a value is present, returns the value, otherwise returns the result produced by the supplying function.</p>
	 * @param supplier the supplying function that produces a value to be returned
	 * @return the value, if present, otherwise the result produced by the supplying function
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.1.0
	 */
	public T orElseGet(final Supplier<? extends T> supplier) {
		if (null == supplier) {
			throw new NullPointerException("Invalid supplier (not null expected)");
		}
		return isPresent() ? value : supplier.get();
	}

	/**
	 * <p>If a value is present, returns the value, otherwise throws an exception produced by the exception supplying
	 * function.</p>
	 * @param exceptionSupplier the supplying function that produces an exception to be thrown
	 * @param <X> type of the exception to be thrown
	 * @return the value, if present
	 * @throws X if no value is present
	 * @throws NullPointerException if the exception {@code Supplier} is {@code null}
	 * @since 1.1.0
	 */
	public <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
		if (null == exceptionSupplier) {
			throw new NullPointerException("Invalid exception supplier (not null expected)");
		}
		if (isPresent()) {
			return value;
		}
		throw exceptionSupplier.get();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof NullableOptional)) {
			return false;
		}
		final var other = (NullableOptional) object;
		return Objects.equals(value, other.value)
				&& Objects.equals(isEmpty, other.isEmpty);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, isEmpty);
	}

	@Override
	public String toString() {
		return isPresent()
				? "NullableOptional[" + value + "]"
				: "NullableOptional.empty";
	}

	/**
	 * <p>Converts the current {@code NullableOptional} to an {@code Optional} using
	 * {@link Optional#ofNullable(Object)}.</p>
	 * @return the created {@code Optional}
	 * @since 1.1.0
	 */
	public Optional<T> toOptional() {
		return Optional.ofNullable(value);
	}

	/**
	 * <p>Returns an empty {@code NullableOptional} instance. No value is present for this {@code NullableOptional}.</p>
	 * @param <T> the type of the non-existent value
	 * @return an empty {@code NullableOptional}
	 * @since 1.1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> NullableOptional<T> empty() {
		return (NullableOptional<T>) EMPTY;
	}

	/**
	 * <p>Returns a {@code NullableOptional} describing the given nullable value.</p>
	 * @param value the value to describe or {@code null}
	 * @param <T> the type of the value
	 * @return a {@code NullableOptional} with the value present
	 * @since 1.1.0
	 */
	public static <T> NullableOptional<T> of(final T value) {
		return new NullableOptional<>(value);
	}

	/**
	 * <p>Create a {@code NullableOptional} with an {@code Optional}.</p>
	 * @param optional the {@code Optional} to convert
	 * @param <T> the type of the value
	 * @return the created {@code NullableOptional}
	 * @since 1.1.0
	 */
	public static <T> NullableOptional<T> ofOptional(final Optional<T> optional) {
		if (null == optional) {
			throw new NullPointerException("Invalid optional (not null expected)");
		}
		return optional.map(NullableOptional::of).orElseGet(NullableOptional::empty);
	}
}