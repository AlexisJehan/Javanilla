/*
 * MIT License
 *
 * Copyright (c) 2018-2026 Alexis Jehan
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

import com.github.alexisjehan.javanilla.util.Iterables;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;

/**
 * A {@link TreeNode} represents a single node in a whole tree data structure which contains optional parent and
 * children nodes and a value.
 * @param <V> the value type
 * @see <a href="https://en.wikipedia.org/wiki/Tree_(data_structure)">https://en.wikipedia.org/wiki/Tree_(data_structure)</a>
 * @since 1.8.0
 */
public interface TreeNode<V> extends Iterable<TreeNode<V>> {

	/**
	 * Extend the tree by creating a child {@code TreeNode} to the current node.
	 *
	 * <p><b>Note</b>: A {@code null} value may be restricted depending on the implementation.</p>
	 * @param value the value of the child {@code TreeNode} to create
	 * @return the created child {@code TreeNode}
	 * @since 1.8.0
	 */
	TreeNode<V> extend(V value);

	/**
	 * Remove the descendant {@code TreeNode} of the current node from the tree.
	 *
	 * <p><b>Note</b>: The parent node of the descendant {@code TreeNode} will not be removed.</p>
	 * @param descendant the descendant {@code TreeNode} to remove
	 * @return {@code true} if the descendant {@code TreeNode} has been successfully removed
	 * @throws NullPointerException if the descendant {@code TreeNode} is {@code null}
	 * @since 1.8.0
	 */
	boolean remove(TreeNode<V> descendant);

	/**
	 * Remove all children of the current node and their descendants from the tree.
	 * @since 1.8.0
	 */
	void clear();

	/**
	 * Tell if the current node is the root of the tree.
	 * @return {@code true} if the current node is the root
	 * @since 1.8.0
	 */
	default boolean isRoot() {
		return !optionalParent().isPresent();
	}

	/**
	 * Tell if the current node is a branch in the tree.
	 * @return {@code true} if the current node is a branch
	 * @since 1.8.0
	 */
	default boolean isBranch() {
		return !children().isEmpty();
	}

	/**
	 * Tell if the current node is a leaf in the tree.
	 * @return {@code true} if the current node is a leaf
	 * @since 1.8.0
	 */
	default boolean isLeaf() {
		return children().isEmpty();
	}

	/**
	 * Optionally get the parent {@code TreeNode} of the current node.
	 * @return an {@link Optional} parent {@code TreeNode}
	 * @since 1.8.0
	 */
	Optional<TreeNode<V>> optionalParent();

	/**
	 * Get a {@link List} of children {@code TreeNode}s of the current node.
	 * @return a {@link List} of children {@code TreeNode}s
	 * @since 1.8.0
	 */
	List<TreeNode<V>> children();

	/**
	 * Create an {@link Iterable} of siblings {@code TreeNode}s of the current node.
	 * @return an {@link Iterable} of siblings {@code TreeNode}s
	 * @since 1.8.0
	 */
	default Iterable<TreeNode<V>> siblings() {
		return optionalParent()
				.map(parent -> Iterables.filter(parent.children(), node -> node != this))
				.orElseGet(Iterables::empty);
	}

	/**
	 * Create an {@link Iterable} of branches {@code TreeNode}s of the current node.
	 *
	 * <p><b>Note</b>: {@code TreeNode}s will be breadth first iterated.</p>
	 * @return an {@link Iterable} of branches {@code TreeNode}s
	 * @since 1.8.0
	 */
	default Iterable<TreeNode<V>> branches() {
		return Iterables.filter(descendantsBreadthFirst(), TreeNode::isBranch);
	}

	/**
	 * Create an {@link Iterable} of leaves {@code TreeNode}s of the current node.
	 *
	 * <p><b>Note</b>: {@code TreeNode}s will be breadth first iterated.</p>
	 * @return an {@link Iterable} of leaves {@code TreeNode}s
	 * @since 1.8.0
	 */
	default Iterable<TreeNode<V>> leaves() {
		return Iterables.filter(descendantsBreadthFirst(), TreeNode::isLeaf);
	}

	/**
	 * Create a depth first {@link Iterable} of descendants {@code TreeNode}s of the current node.
	 * @return a depth first {@link Iterable} of descendants {@code TreeNode}s
	 * @since 1.8.0
	 */
	default Iterable<TreeNode<V>> descendantsDepthFirst() {
		return () -> {
			final var iterator = new Iterator<TreeNode<V>>() {

				/**
				 * Queue of descendants nodes.
				 * @since 1.8.0
				 */
				private final Deque<TreeNode<V>> deque = new LinkedList<>(children());

				/**
				 * Next node or {@code null}.
				 * @since 1.8.0
				 */
				private TreeNode<V> next;

				/**
				 * Prepare the next node.
				 * @since 1.8.0
				 */
				void prepareNext() {
					next = deque.poll();
					if (null != next) {
						final var listIterator = next.children().listIterator(StrictMath.toIntExact(next.degree()));
						while (listIterator.hasPrevious()) {
							deque.push(listIterator.previous());
						}
					}
				}

				/**
				 * {@inheritDoc}
				 */
				@Override
				public boolean hasNext() {
					return null != next;
				}

				/**
				 * {@inheritDoc}
				 */
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
			iterator.prepareNext();
			return iterator;
		};
	}

	/**
	 * Create a breadth first {@link Iterable} of descendants {@code TreeNode}s of the current node.
	 * @return a breadth first {@link Iterable} of descendants {@code TreeNode}s
	 * @since 1.8.0
	 */
	default Iterable<TreeNode<V>> descendantsBreadthFirst() {
		return () -> {
			final var iterator = new Iterator<TreeNode<V>>() {

				/**
				 * Queue of descendants nodes.
				 * @since 1.8.0
				 */
				private final Queue<TreeNode<V>> queue = new LinkedList<>(children());

				/**
				 * Next node or {@code null}.
				 * @since 1.8.0
				 */
				private TreeNode<V> next;

				/**
				 * Prepare the next node.
				 * @since 1.8.0
				 */
				void prepareNext() {
					next = queue.poll();
					if (null != next) {
						queue.addAll(next.children());
					}
				}

				/**
				 * {@inheritDoc}
				 */
				@Override
				public boolean hasNext() {
					return null != next;
				}

				/**
				 * {@inheritDoc}
				 */
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
			iterator.prepareNext();
			return iterator;
		};
	}

	/**
	 * Create an {@link Iterable} of ancestors {@code TreeNode}s of the current node.
	 * @return an {@link Iterable} of ancestors {@code TreeNode}s
	 * @since 1.8.0
	 */
	default Iterable<TreeNode<V>> ancestors() {
		return () -> new Iterator<>() {

			/**
			 * Next node or {@code null}.
			 * @since 1.8.0
			 */
			private TreeNode<V> next = optionalParent().orElse(null);

			/**
			 * Prepare the next node.
			 * @since 1.8.0
			 */
			private void prepareNext() {
				next = next.optionalParent().orElse(null);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean hasNext() {
				return null != next;
			}

			/**
			 * {@inheritDoc}
			 */
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
	 * Get the degree of the current node.
	 * @return the degree of the current node
	 * @since 1.8.0
	 */
	default long degree() {
		return children().size();
	}

	/**
	 * Get the depth of the current node.
	 * @return the depth of the current node
	 * @since 1.8.0
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Iterator<TreeNode<V>> iterator() {
		return descendantsDepthFirst().iterator();
	}

	/**
	 * Get the value of the current node.
	 * @return the value of the current node
	 * @since 1.8.0
	 */
	V getValue();

	/**
	 * Set the value of the current node.
	 * @param value the new value of the current node
	 * @since 1.8.0
	 */
	void setValue(V value);
}