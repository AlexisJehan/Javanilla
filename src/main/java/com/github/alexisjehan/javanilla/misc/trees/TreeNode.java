/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Alexis Jehan
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

import com.github.alexisjehan.javanilla.util.iteration.Iterables;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;

/**
 * <p>A {@code TreeNode} represents a single node in a whole tree data structure which contains optional parent and
 * children nodes and a value.</p>
 * @see <a href="https://en.wikipedia.org/wiki/Tree_(data_structure)">https://en.wikipedia.org/wiki/Tree_(data_structure)</a>
 * @param <V> the value type
 * @since 1.2.0
 */
public interface TreeNode<V> extends Iterable<TreeNode<V>> {

	/**
	 * <p>Extend the tree by creating a child {@code TreeNode} to the current node.</p>
	 * <p><b>Note</b>: A {@code null} value may be restricted depending of the implementation.</p>
	 * @param value the value of the child {@code TreeNode} to create
	 * @return the created child {@code TreeNode}
	 * @since 1.2.0
	 */
	TreeNode<V> extend(final V value);

	/**
	 * <p>Remove the descendant {@code TreeNode} of the current node from the tree.</p>
	 * <p><b>Note</b>: The parent node of the descendant {@code TreeNode} will not be removed.</p>
	 * @param descendant the descendant {@code TreeNode} to remove
	 * @return {@code true} if the descendant {@code TreeNode} has been successfully removed
	 * @throws NullPointerException if the descendant {@code TreeNode} is {@code null}
	 * @since 1.2.0
	 */
	boolean remove(final TreeNode<V> descendant);

	/**
	 * <p>Remove all children of the current node and their descendants from the tree.</p>
	 * @since 1.2.0
	 */
	void clear();

	/**
	 * <p>Tell if the current node is the root of the tree.</p>
	 * @return {@code true} if the current node is the root
	 * @since 1.2.0
	 */
	default boolean isRoot() {
		return !optionalParent().isPresent();
	}

	/**
	 * <p>Tell if the current node is a branch in the tree.</p>
	 * @return {@code true} if the current node is a branch
	 * @since 1.2.0
	 */
	default boolean isBranch() {
		return !children().isEmpty();
	}

	/**
	 * <p>Tell if the current node is a leaf in the tree.</p>
	 * @return {@code true} if the current node is a leaf
	 * @since 1.2.0
	 */
	default boolean isLeaf() {
		return children().isEmpty();
	}

	/**
	 * <p>Optionally get the parent {@code TreeNode} of the current node.</p>
	 * @return an {@code Optional} parent {@code TreeNode}
	 * @since 1.2.0
	 */
	Optional<TreeNode<V>> optionalParent();

	/**
	 * <p>Get a {@code List} of children {@code TreeNode}s of the current node.</p>
	 * @return a {@code List} of children {@code TreeNode}s
	 * @since 1.2.0
	 */
	List<TreeNode<V>> children();

	/**
	 * <p>Get the value of the current node.</p>
	 * @return the value of the current node
	 * @since 1.2.0
	 */
	V getValue();

	/**
	 * <p>Set the value of the current node.</p>
	 * @param value the new value of the current node
	 * @since 1.2.0
	 */
	void setValue(final V value);

	/**
	 * <p>Create an {@code Iterable} of siblings {@code TreeNode}s of the current node.</p>
	 * @return an {@code Iterable} of siblings {@code TreeNode}s
	 * @since 1.2.0
	 */
	default Iterable<TreeNode<V>> siblings() {
		return optionalParent()
				.map(parent -> Iterables.filter(parent.children(), node -> node != this))
				.orElseGet(Iterables::empty);
	}

	/**
	 * <p>Create an {@code Iterable} of branches {@code TreeNode}s of the current node.</p>
	 * <p><b>Note</b>: {@code TreeNode}s will be breadth first iterated.</p>
	 * @return an {@code Iterable} of branches {@code TreeNode}s
	 * @since 1.2.0
	 */
	default Iterable<TreeNode<V>> branches() {
		return Iterables.filter(descendantsBreadthFirst(), TreeNode::isBranch);
	}

	/**
	 * <p>Create an {@code Iterable} of leaves {@code TreeNode}s of the current node.</p>
	 * <p><b>Note</b>: {@code TreeNode}s will be breadth first iterated.</p>
	 * @return an {@code Iterable} of leaves {@code TreeNode}s
	 * @since 1.2.0
	 */
	default Iterable<TreeNode<V>> leaves() {
		return Iterables.filter(descendantsBreadthFirst(), TreeNode::isLeaf);
	}

	/**
	 * <p>Create a depth first {@code Iterable} of descendants {@code TreeNode}s of the current node.</p>
	 * @return a depth first {@code Iterable} of descendants {@code TreeNode}s
	 * @since 1.2.0
	 */
	default Iterable<TreeNode<V>> descendantsDepthFirst() {
		return () -> new Iterator<>() {
			private final Deque<TreeNode<V>> deque = new LinkedList<>(children());
			private TreeNode<V> next;
			{
				prepareNext();
			}

			/**
			 * <p>Prepare the next node.</p>
			 * @since 1.2.0
			 */
			private void prepareNext() {
				if (null != (next = deque.poll())) {
					final var listIterator = next.children().listIterator((int) next.degree());
					while (listIterator.hasPrevious()) {
						deque.push(listIterator.previous());
					}
				}
			}

			@Override
			public boolean hasNext() {
				return null != next;
			}

			@Override
			public TreeNode<V> next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				final var current = next;
				prepareNext();
				return current;
			}
		};
	}

	/**
	 * <p>Create a breadth first {@code Iterable} of descendants {@code TreeNode}s of the current node.</p>
	 * @return a breadth first {@code Iterable} of descendants {@code TreeNode}s
	 * @since 1.2.0
	 */
	default Iterable<TreeNode<V>> descendantsBreadthFirst() {
		return () -> new Iterator<>() {
			private final Queue<TreeNode<V>> queue = new LinkedList<>(children());
			private TreeNode<V> next;
			{
				prepareNext();
			}

			/**
			 * <p>Prepare the next node.</p>
			 * @since 1.2.0
			 */
			private void prepareNext() {
				if (null != (next = queue.poll())) {
					queue.addAll(next.children());
				}
			}

			@Override
			public boolean hasNext() {
				return null != next;
			}

			@Override
			public TreeNode<V> next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				final var current = next;
				prepareNext();
				return current;
			}
		};
	}

	/**
	 * <p>Create an {@code Iterable} of ancestors {@code TreeNode}s of the current node.</p>
	 * @return an {@code Iterable} of ancestors {@code TreeNode}s
	 * @since 1.2.0
	 */
	default Iterable<TreeNode<V>> ancestors() {
		return () -> new Iterator<>() {
			private TreeNode<V> next = optionalParent().orElse(null);

			/**
			 * <p>Prepare the next node.</p>
			 * @since 1.2.0
			 */
			private void prepareNext() {
				next = next.optionalParent().orElse(null);
			}

			@Override
			public boolean hasNext() {
				return null != next;
			}

			@Override
			public TreeNode<V> next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				final var current = next;
				prepareNext();
				return current;
			}
		};
	}

	/**
	 * <p>Get the degree of the current node.</p>
	 * @return the degree of the current node
	 * @since 1.2.0
	 */
	default long degree() {
		return children().size();
	}

	/**
	 * <p>Get the depth of the current node.</p>
	 * @return the depth of the current node
	 * @since 1.2.0
	 */
	default long depth() {
		var depth = 0L;
		var optionalParent = optionalParent();
		while (optionalParent.isPresent()) {
			optionalParent = optionalParent.get().optionalParent();
			++depth;
		}
		return depth;
	}

	@Override
	default Iterator<TreeNode<V>> iterator() {
		return descendantsDepthFirst().iterator();
	}
}