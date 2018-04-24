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
package com.github.alexisjehan.javanilla.util.collection.bags;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <p>A {@link Bag} implementation which uses a {@link Map} to store elements and occurrences.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)} and {@link #hashCode()} methods.</p>
 * @param <E> the element type
 * @since 1.0
 */
public final class MapBag<E> implements Bag<E> {

	/**
	 * <p>The delegated {@code Map}.</p>
	 * @since 1.0
	 */
	private final Map<E, LongAdder> map;

	/**
	 * <p>Cached size of the {@code Bag}.</p>
	 * @since 1.0
	 */
	private long size = 0L;

	/**
	 * <p>Default constructor, a {@code HashMap} is used.</p>
	 * @since 1.0
	 */
	public MapBag() {
		this(new HashMap<>());
	}

	/**
	 * <p>Constructor with a custom {@code Map} implementation.</p>
	 * @param mapSupplier the {@code Supplier} which provides the {@code Map}
	 * @throws NullPointerException if the {@code Supplier} is {@code null}
	 * @since 1.0
	 */
	public MapBag(final Supplier<? extends Map<E, LongAdder>> mapSupplier) {
		this(Objects.requireNonNull(mapSupplier, "Invalid map supplier (not null expected)").get());
	}

	/**
	 * <p>Constructor with elements from an existing {@code Collection}, a {@code HashMap} is used.</p>
	 * @param collection the collection to get elements from
	 * @throws NullPointerException if the collection is {@code null}
	 * @since 1.0
	 */
	public MapBag(final Collection<? extends E> collection) {
		if (null == collection) {
			throw new NullPointerException("Invalid collection (not null expected)");
		}
		map = new HashMap<>();
		if (!collection.isEmpty()) {
			for (final var element : collection) {
				add(element);
			}
		}
	}

	/**
	 * <p>Private constructor with the given {@code Map}, empty or not.</p>
	 * @param map the {@code Map} to get elements and occurrences from
	 * @throws NullPointerException if the {@code Map} is {@code null}
	 * @since 1.0
	 */
	private MapBag(final Map<E, LongAdder> map) {
		if (null == map) {
			throw new NullPointerException("Invalid map (not null expected)");
		}
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

	@Override
	public boolean add(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
		if (0L == quantity) {
			return false;
		}
		map.computeIfAbsent(element, e -> new LongAdder()).add(quantity);
		size += quantity;
		return true;
	}

	@Override
	public boolean remove(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
		if (0L == quantity) {
			return false;
		}
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
		return map.isEmpty();
	}

	@Override
	public boolean containsAny(final E element) {
		return map.containsKey(element);
	}

	@Override
	public boolean containsExactly(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
		final var adder = map.get(element);
		return (null == adder && 0L == quantity) || (null != adder && quantity == adder.longValue());
	}

	@Override
	public boolean containsAtLeast(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
		if (0L == quantity) {
			return true;
		}
		final var adder = map.get(element);
		return null != adder && quantity <= adder.longValue();
	}

	@Override
	public boolean containsAtMost(final E element, final long quantity) {
		if (0L > quantity) {
			throw new IllegalArgumentException("Invalid quantity: " + quantity + " (greater than or equal to 0 expected)");
		}
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
	public Optional<E> min() {
		return map.entrySet().stream()
				.min(Comparator.comparingDouble(e -> e.getValue().longValue()))
				.map(Map.Entry::getKey);
	}

	@Override
	public Optional<E> max() {
		return map.entrySet().stream()
				.max(Comparator.comparingDouble(e -> e.getValue().longValue()))
				.map(Map.Entry::getKey);
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
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MapBag)) {
			return false;
		}
		final var other = (MapBag<?>) object;
		if (size != other.size) {
			return false;
		}
		for (final var entry : map.entrySet()) {
			final var element = entry.getKey();
			final var adder = entry.getValue();
			final var otherAdder = other.map.get(element);
			if (null == otherAdder || adder.longValue() != otherAdder.longValue()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		var h = 0;
		for (final var entry : map.entrySet()) {
			h += Objects.hash(entry.getKey(), entry.getValue().longValue());
		}
		h += Objects.hash(size);
		return h;
	}

	@Override
	public String toString() {
		return map.toString();
	}
}