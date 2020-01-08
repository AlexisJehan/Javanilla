/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Alexis Jehan
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
package com.github.alexisjehan.javanilla.util.collection.bags;

import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;
import com.github.alexisjehan.javanilla.util.NullableOptional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <p>A {@link Bag} implementation which uses a {@link Map} to store elements and quantities.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <E> the element type
 * @since 1.0.0
 */
public final class MapBag<E> implements Bag<E> {

	/**
	 * <p>Delegated {@code Map}.</p>
	 * @since 1.0.0
	 */
	private final Map<E, LongAdder> map;

	/**
	 * <p>Current size of the {@code Bag}.</p>
	 * @since 1.0.0
	 */
	private long size = 0L;

	/**
	 * <p>Default constructor, a {@code HashMap} is used.</p>
	 * @since 1.0.0
	 */
	public MapBag() {
		this(new HashMap<>());
	}

	/**
	 * <p>Constructor with a custom {@code Map} implementation.</p>
	 * @param mapSupplier the {@code Supplier} which provides the {@code Map}
	 * @throws NullPointerException if the {@code Map} {@code Supplier} is {@code null}
	 * @since 1.0.0
	 */
	public MapBag(final Supplier<? extends Map<E, LongAdder>> mapSupplier) {
		this(Ensure.notNull("mapSupplier", mapSupplier).get());
	}

	/**
	 * <p>Private constructor with the given {@code Map}, empty or not.</p>
	 * @param map the {@code Map} to get elements and occurrences from
	 * @throws NullPointerException if the {@code Map} is {@code null}
	 * @since 1.0.0
	 */
	private MapBag(final Map<E, LongAdder> map) {
		Ensure.notNull("map", map);
		this.map = map;
		if (!map.isEmpty()) {
			final var iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				final var entry = iterator.next();
				final var count = entry.getValue().longValue();
				if (0L < count) {
					size += count;
				} else {
					iterator.remove();
				}
			}
		}
	}

	/**
	 * <p>Constructor with elements from an existing {@code Collection}, a {@code HashMap} is used.</p>
	 * @param collection the {@code Collection} to get elements from
	 * @throws NullPointerException if the {@code Collection} is {@code null}
	 * @since 1.0.0
	 */
	public MapBag(final Collection<? extends E> collection) {
		Ensure.notNull("collection", collection);
		map = new HashMap<>();
		if (!collection.isEmpty()) {
			for (final var element : collection) {
				add(element);
			}
		}
	}

	@Override
	public void add(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		if (0L != quantity) {
			map.computeIfAbsent(element, e -> new LongAdder()).add(quantity);
			size += quantity;
		}
	}

	@Override
	public boolean remove(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		if (0L != quantity) {
			final var adder = map.get(element);
			if (null != adder) {
				final var count = adder.longValue();
				if (quantity >= count) {
					map.remove(element);
					size -= count;
				} else {
					adder.add(-quantity);
					size -= quantity;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(final E element) {
		final var adder = map.get(element);
		if (null != adder) {
			map.remove(element);
			size -= adder.longValue();
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		map.clear();
		size = 0L;
	}

	@Override
	public boolean isEmpty() {
		return 0L == size;
	}

	@Override
	public boolean containsAny(final E element) {
		return map.containsKey(element);
	}

	@Override
	public boolean containsExactly(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		final var adder = map.get(element);
		return null == adder ? 0L == quantity : quantity == adder.longValue();
	}

	@Override
	public boolean containsAtLeast(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		if (0L == quantity) {
			return true;
		}
		final var adder = map.get(element);
		return null != adder && quantity <= adder.longValue();
	}

	@Override
	public boolean containsAtMost(final E element, final long quantity) {
		Ensure.greaterThanOrEqualTo("quantity", quantity, 0L);
		final var adder = map.get(element);
		return null == adder || quantity >= adder.longValue();
	}

	@Override
	public long count(final E element) {
		final var adder = map.get(element);
		if (null != adder) {
			return adder.longValue();
		}
		return 0L;
	}

	@Override
	public long distinct() {
		return map.size();
	}

	@Override
	public long size() {
		return size;
	}

	@Override
	public NullableOptional<E> min() {
		Map.Entry<E, LongAdder> minEntry = null;
		for (final var entry : map.entrySet()) {
			if (null == minEntry || minEntry.getValue().longValue() > entry.getValue().longValue()) {
				minEntry = entry;
			}
		}
		if (null == minEntry) {
			return NullableOptional.empty();
		}
		return NullableOptional.of(minEntry.getKey());
	}

	@Override
	public NullableOptional<E> max() {
		Map.Entry<E, LongAdder> maxEntry = null;
		for (final var entry : map.entrySet()) {
			if (null == maxEntry || maxEntry.getValue().longValue() < entry.getValue().longValue()) {
				maxEntry = entry;
			}
		}
		if (null == maxEntry) {
			return NullableOptional.empty();
		}
		return NullableOptional.of(maxEntry.getKey());
	}

	@Override
	public Set<E> toSet() {
		return map.keySet();
	}

	@Override
	public Map<E, Long> toMap() {
		return map.entrySet().stream().collect(
				Collectors.toMap(
						Map.Entry::getKey,
						e -> e.getValue().longValue()
				)
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Bag)) {
			return false;
		}
		final var other = (Bag) object;
		if (distinct() != other.distinct() || size() != other.size()) {
			return false;
		}
		for (final var entry : map.entrySet()) {
			final var element = entry.getKey();
			final var quantity = entry.getValue().longValue();
			if (quantity != other.count(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		var hashCode = 0;
		for (final var entry : map.entrySet()) {
			final var element = entry.getKey();
			final var quantity = entry.getValue().longValue();
			hashCode += HashCode.of(
					HashCode.hashCode(element),
					HashCode.hashCode(quantity)
			);
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return map.toString();
	}
}