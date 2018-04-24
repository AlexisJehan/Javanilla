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
package com.github.alexisjehan.javanilla.util.collection;

import java.util.*;

/**
 * <p>An utility class that provides {@link Map} tools.</p>
 * @since 1.0
 */
public final class Maps {

	/**
	 * <p>Constructor not available.</p>
	 * @since 1.0
	 */
	private Maps() {
		// Not available
	}

	/**
	 * <p>Wrap a {@code Map} replacing {@code null} by an empty {@code Map}.</p>
	 * @param map a {@code Map} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-{@code null} {@code Map}
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> nullToEmpty(final Map<K, V> map) {
		return null != map ? map : Collections.emptyMap();
	}

	/**
	 * <p>Wrap a {@code SortedMap} replacing {@code null} by an empty {@code SortedMap}.</p>
	 * @param sortedMap a {@code SortedMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-{@code null} {@code SortedMap}
	 * @since 1.0
	 */
	public static <K, V> SortedMap<K, V> nullToEmpty(final SortedMap<K, V> sortedMap) {
		return null != sortedMap ? sortedMap : Collections.emptySortedMap();
	}

	/**
	 * <p>Wrap a {@code NavigableMap} replacing {@code null} by an empty {@code NavigableMap}.</p>
	 * @param navigableMap a {@code NavigableMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-{@code null} {@code NavigableMap}
	 * @since 1.0
	 */
	public static <K, V> NavigableMap<K, V> nullToEmpty(final NavigableMap<K, V> navigableMap) {
		return null != navigableMap ? navigableMap : Collections.emptyNavigableMap();
	}

	/**
	 * <p>Wrap a {@code Map} replacing an empty one by {@code null}.</p>
	 * @param map a {@code Map} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-empty {@code Map} or {@code null}
	 * @since 1.0
	 */
	public static <K, V> Map<K, V> emptyToNull(final Map<K, V> map) {
		return null != map && !map.isEmpty() ? map : null;
	}

	/**
	 * <p>Wrap a {@code SortedMap} replacing an empty one by {@code null}.</p>
	 * @param sortedMap a {@code SortedMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-empty {@code SortedMap} or {@code null}
	 * @since 1.0
	 */
	public static <K, V> SortedMap<K, V> emptyToNull(final SortedMap<K, V> sortedMap) {
		return null != sortedMap && !sortedMap.isEmpty() ? sortedMap : null;
	}

	/**
	 * <p>Wrap a {@code NavigableMap} replacing an empty one by {@code null}.</p>
	 * @param navigableMap a {@code NavigableMap} or {@code null}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return a non-empty {@code NavigableMap} or {@code null}
	 * @since 1.0
	 */
	public static <K, V> NavigableMap<K, V> emptyToNull(final NavigableMap<K, V> navigableMap) {
		return null != navigableMap && !navigableMap.isEmpty() ? navigableMap : null;
	}

	/**
	 * <p>Create an ordered {@code Map} with an array of entries.</p>
	 * <p><b>Note</b>: {@code null} value may be restricted depending of the {@code Map} implementation.</p>
	 * @param entries entries of the {@code Map}
	 * @param <K> the type of keys maintained by the map
	 * @param <V> the type of mapped values
	 * @return the created ordered {@code Map}
	 * @throws NullPointerException if the entries array is {@code null}
	 * @since 1.0
	 */
	@SafeVarargs
	public static <K, V> Map<K, V> ofEntriesOrdered(final Map.Entry<? extends K, ? extends V>... entries) {
		if (null == entries) {
			throw new NullPointerException("Invalid entries (not null expected)");
		}
		if (0 == entries.length) {
			return Collections.emptyMap();
		} else if (1 == entries.length) {
			return Collections.singletonMap(entries[0].getKey(), entries[0].getValue());
		}
		final var map = new LinkedHashMap<K, V>(entries.length);
		for (final var entry : entries) {
			map.put(entry.getKey(), entry.getValue());
		}
		return Collections.unmodifiableMap(map);
	}
}