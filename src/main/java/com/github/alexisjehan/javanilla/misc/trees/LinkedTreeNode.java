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
package com.github.alexisjehan.javanilla.misc.trees;

import com.github.alexisjehan.javanilla.lang.Strings;
import com.github.alexisjehan.javanilla.misc.quality.Ensure;
import com.github.alexisjehan.javanilla.misc.quality.Equals;
import com.github.alexisjehan.javanilla.misc.quality.HashCode;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>A {@link TreeNode} implementation which uses a {@link LinkedList} to store its children.</p>
 * <p><b>Note</b>: This class implements its own {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}
 * methods.</p>
 * @param <V> the value type
 * @since 1.2.0
 */
public final class LinkedTreeNode<V> implements TreeNode<V> {

	/**
	 * <p>Parent {@link TreeNode} or {@code null}.</p>
	 * @since 1.2.0
	 */
	private final TreeNode<V> parent;

	/**
	 * <p>{@link List} of children {@link TreeNode}s.</p>
	 * @since 1.2.0
	 */
	private final List<TreeNode<V>> children = new LinkedList<>();

	/**
	 * <p>Mutable value.</p>
	 * @since 1.2.0
	 */
	private V value;

	/**
	 * <p>Constructor of a new tree with his root {@link TreeNode} having the given value.</p>
	 * @param value the value of the root {@link TreeNode}
	 * @since 1.2.0
	 */
	public LinkedTreeNode(final V value) {
		this(null, value);
	}

	/**
	 * <p>Private constructor of a {@link TreeNode}.</p>
	 * @param parent the parent of the {@link TreeNode} or {@code null}
	 * @param value the value of the {@link TreeNode}
	 * @since 1.2.0
	 */
	private LinkedTreeNode(final LinkedTreeNode<V> parent, final V value) {
		this.parent = parent;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TreeNode<V> extend(final V value) {
		final var child = new LinkedTreeNode<>(this, value);
		children.add(child);
		return child;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(final TreeNode<V> descendant) {
		Ensure.notNull("descendant", descendant);
		if (children.remove(descendant)) {
			return true;
		}
		for (final var child : children) {
			if (child.remove(descendant)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		children.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public V getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(final V value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<TreeNode<V>> optionalParent() {
		return Optional.ofNullable(parent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TreeNode<V>> children() {
		return List.copyOf(children);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof TreeNode)) {
			return false;
		}
		final var other = (TreeNode<?>) object;
		return Equals.equals(getValue(), other.getValue())
				&& Equals.equals(children(), other.children());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCode.of(
				HashCode.hashCode(getValue()),
				HashCode.hashCode(children())
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return value + (!children.isEmpty() ? "{" + children.stream().map(TreeNode::toString).collect(Collectors.joining(", ")) + "}" : Strings.EMPTY);
	}
}