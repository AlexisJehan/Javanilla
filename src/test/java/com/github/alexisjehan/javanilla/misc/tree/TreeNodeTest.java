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
package com.github.alexisjehan.javanilla.misc.tree;

import java.util.List;
import java.util.Optional;

final class TreeNodeTest extends AbstractTreeNodeTest {

	@Override
	<V> TreeNode<V> newTreeNode(final V value) {
		return new TreeNode<>() {

			private final TreeNode<V> delegate = new LinkedTreeNode<>(value);

			@Override
			public TreeNode<V> extend(final V value) {
				return delegate.extend(value);
			}

			@Override
			public boolean remove(final TreeNode<V> descendant) {
				return delegate.remove(descendant);
			}

			@Override
			public void clear() {
				delegate.clear();
			}

			@Override
			public Optional<TreeNode<V>> optionalParent() {
				return delegate.optionalParent();
			}

			@Override
			public List<TreeNode<V>> children() {
				return delegate.children();
			}

			@Override
			public V getValue() {
				return delegate.getValue();
			}

			@Override
			public void setValue(final V value) {
				delegate.setValue(value);
			}

			@Override
			public boolean equals(final Object object) {
				return delegate.equals(object);
			}

			@Override
			public int hashCode() {
				return delegate.hashCode();
			}

			@Override
			public String toString() {
				return delegate.toString();
			}
		};
	}
}