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
package com.github.alexisjehan.javanilla.misc.trees;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@SuppressWarnings("deprecation")
abstract class AbstractTreeNodeTest {

	abstract <V> TreeNode<V> newTreeNode(final V value);

	@Test
	void testExtend() {
		final var fooNode = newTreeNode("foo");
		assertThat(fooNode.degree()).isZero();
		final var foo1Node = fooNode.extend("foo1");
		assertThat(fooNode.degree()).isEqualTo(1L);
		assertThat(foo1Node.degree()).isZero();
		final var foo2Node = fooNode.extend("foo2");
		assertThat(fooNode.degree()).isEqualTo(2L);
		assertThat(foo1Node.degree()).isZero();
		assertThat(foo2Node.degree()).isZero();
		final var foo11Node = foo1Node.extend("foo11");
		assertThat(fooNode.degree()).isEqualTo(2L);
		assertThat(foo1Node.degree()).isEqualTo(1L);
		assertThat(foo2Node.degree()).isZero();
		assertThat(foo11Node.degree()).isZero();
	}

	@Test
	void testRemove() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.remove(fooNode)).isFalse();
		assertThat(fooNode.remove(foo3Node)).isTrue();
		assertThat(fooNode.remove(foo3Node)).isFalse();
		assertThat(fooNode.remove(foo2Node)).isTrue();
		assertThat(fooNode.remove(foo21Node)).isFalse();
		assertThat(foo2Node.remove(foo21Node)).isTrue();
		assertThat(foo1Node.remove(foo111Node)).isTrue();
		assertThat(foo1Node.remove(foo11Node)).isTrue();
		assertThat(foo1Node.remove(foo12Node)).isTrue();
		assertThat(fooNode.remove(foo1Node)).isTrue();
		assertThat(fooNode.remove(barNode)).isFalse();
	}

	@Test
	void testRemoveInvalid() {
		assertThatNullPointerException().isThrownBy(() -> newTreeNode("foo").remove(null));
	}

	@Test
	void testClear() {
		final var fooNode = newTreeNode("foo");
		fooNode.extend("foo1");
		assertThat(fooNode.degree()).isEqualTo(1L);
		fooNode.clear();
		assertThat(fooNode.degree()).isZero();
	}

	@Test
	void testIsRoot() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.isRoot()).isTrue();
		assertThat(foo1Node.isRoot()).isFalse();
		assertThat(foo2Node.isRoot()).isFalse();
		assertThat(foo3Node.isRoot()).isFalse();
		assertThat(foo11Node.isRoot()).isFalse();
		assertThat(foo12Node.isRoot()).isFalse();
		assertThat(foo21Node.isRoot()).isFalse();
		assertThat(foo111Node.isRoot()).isFalse();
		assertThat(barNode.isRoot()).isTrue();
	}

	@Test
	void testIsBranch() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.isBranch()).isTrue();
		assertThat(foo1Node.isBranch()).isTrue();
		assertThat(foo2Node.isBranch()).isTrue();
		assertThat(foo3Node.isBranch()).isFalse();
		assertThat(foo11Node.isBranch()).isTrue();
		assertThat(foo12Node.isBranch()).isFalse();
		assertThat(foo21Node.isBranch()).isFalse();
		assertThat(foo111Node.isBranch()).isFalse();
		assertThat(barNode.isBranch()).isFalse();
	}

	@Test
	void testIsLeaf() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.isLeaf()).isFalse();
		assertThat(foo1Node.isLeaf()).isFalse();
		assertThat(foo2Node.isLeaf()).isFalse();
		assertThat(foo3Node.isLeaf()).isTrue();
		assertThat(foo11Node.isLeaf()).isFalse();
		assertThat(foo12Node.isLeaf()).isTrue();
		assertThat(foo21Node.isLeaf()).isTrue();
		assertThat(foo111Node.isLeaf()).isTrue();
		assertThat(barNode.isLeaf()).isTrue();
	}

	@Test
	void testOptionalParent() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.optionalParent()).isEmpty();
		assertThat(foo1Node.optionalParent()).hasValue(fooNode);
		assertThat(foo2Node.optionalParent()).hasValue(fooNode);
		assertThat(foo3Node.optionalParent()).hasValue(fooNode);
		assertThat(foo11Node.optionalParent()).hasValue(foo1Node);
		assertThat(foo12Node.optionalParent()).hasValue(foo1Node);
		assertThat(foo21Node.optionalParent()).hasValue(foo2Node);
		assertThat(foo111Node.optionalParent()).hasValue(foo11Node);
		assertThat(barNode.optionalParent()).isEmpty();
	}

	@Test
	void testChildren() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.children()).containsExactly(foo1Node, foo2Node, foo3Node);
		assertThat(foo1Node.children()).containsExactly(foo11Node, foo12Node);
		assertThat(foo2Node.children()).containsExactly(foo21Node);
		assertThat(foo3Node.children()).isEmpty();
		assertThat(foo11Node.children()).containsExactly(foo111Node);
		assertThat(foo12Node.children()).isEmpty();
		assertThat(foo21Node.children()).isEmpty();
		assertThat(foo111Node.children()).isEmpty();
		assertThat(barNode.children()).isEmpty();
	}

	@Test
	void testSiblings() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.siblings()).isEmpty();
		assertThat(foo1Node.siblings()).containsExactly(foo2Node, foo3Node);
		assertThat(foo2Node.siblings()).containsExactly(foo1Node, foo3Node);
		assertThat(foo3Node.siblings()).containsExactly(foo1Node, foo2Node);
		assertThat(foo11Node.siblings()).containsExactly(foo12Node);
		assertThat(foo12Node.siblings()).containsExactly(foo11Node);
		assertThat(foo21Node.siblings()).isEmpty();
		assertThat(foo111Node.siblings()).isEmpty();
		assertThat(barNode.siblings()).isEmpty();
	}

	@Test
	void testBranches() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.branches()).containsExactly(foo1Node, foo2Node, foo11Node);
		assertThat(foo1Node.branches()).containsExactly(foo11Node);
		assertThat(foo2Node.branches()).isEmpty();
		assertThat(foo3Node.branches()).isEmpty();
		assertThat(foo11Node.branches()).isEmpty();
		assertThat(foo12Node.branches()).isEmpty();
		assertThat(foo21Node.branches()).isEmpty();
		assertThat(foo111Node.branches()).isEmpty();
		assertThat(barNode.branches()).isEmpty();
	}

	@Test
	void testLeaves() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.leaves()).containsExactly(foo3Node, foo12Node, foo21Node, foo111Node);
		assertThat(foo1Node.leaves()).containsExactly(foo12Node, foo111Node);
		assertThat(foo2Node.leaves()).containsExactly(foo21Node);
		assertThat(foo3Node.leaves()).isEmpty();
		assertThat(foo11Node.leaves()).containsExactly(foo111Node);
		assertThat(foo12Node.leaves()).isEmpty();
		assertThat(foo21Node.leaves()).isEmpty();
		assertThat(foo111Node.leaves()).isEmpty();
		assertThat(barNode.leaves()).isEmpty();
	}

	@Test
	void testDescendantsDepthFirst() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.descendantsDepthFirst()).containsExactly(foo1Node, foo11Node, foo111Node, foo12Node, foo2Node, foo21Node, foo3Node);
		assertThat(foo1Node.descendantsDepthFirst()).containsExactly(foo11Node, foo111Node, foo12Node);
		assertThat(foo2Node.descendantsDepthFirst()).containsExactly(foo21Node);
		assertThat(foo3Node.descendantsDepthFirst()).isEmpty();
		assertThat(foo11Node.descendantsDepthFirst()).containsExactly(foo111Node);
		assertThat(foo12Node.descendantsDepthFirst()).isEmpty();
		assertThat(foo21Node.descendantsDepthFirst()).isEmpty();
		assertThat(foo111Node.descendantsDepthFirst()).isEmpty();
		assertThat(barNode.descendantsDepthFirst()).isEmpty();
		final var exceptionIterator = barNode.descendantsDepthFirst().iterator();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(exceptionIterator::next);
	}

	@Test
	void testDescendantsBreadthFirst() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.descendantsBreadthFirst()).containsExactly(foo1Node, foo2Node, foo3Node, foo11Node, foo12Node, foo21Node, foo111Node);
		assertThat(foo1Node.descendantsBreadthFirst()).containsExactly(foo11Node, foo12Node, foo111Node);
		assertThat(foo2Node.descendantsBreadthFirst()).containsExactly(foo21Node);
		assertThat(foo3Node.descendantsBreadthFirst()).isEmpty();
		assertThat(foo11Node.descendantsBreadthFirst()).containsExactly(foo111Node);
		assertThat(foo12Node.descendantsBreadthFirst()).isEmpty();
		assertThat(foo21Node.descendantsBreadthFirst()).isEmpty();
		assertThat(foo111Node.descendantsBreadthFirst()).isEmpty();
		assertThat(barNode.descendantsBreadthFirst()).isEmpty();
		final var exceptionIterator = barNode.descendantsBreadthFirst().iterator();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(exceptionIterator::next);
	}

	@Test
	void testAncestors() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.ancestors()).isEmpty();
		assertThat(foo1Node.ancestors()).containsExactly(fooNode);
		assertThat(foo2Node.ancestors()).containsExactly(fooNode);
		assertThat(foo3Node.ancestors()).containsExactly(fooNode);
		assertThat(foo11Node.ancestors()).containsExactly(foo1Node, fooNode);
		assertThat(foo12Node.ancestors()).containsExactly(foo1Node, fooNode);
		assertThat(foo21Node.ancestors()).containsExactly(foo2Node, fooNode);
		assertThat(foo111Node.ancestors()).containsExactly(foo11Node, foo1Node, fooNode);
		assertThat(barNode.ancestors()).isEmpty();
		final var exceptionIterator = barNode.ancestors().iterator();
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(exceptionIterator::next);
	}

	@Test
	void testDegree() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.degree()).isEqualTo(3L);
		assertThat(foo1Node.degree()).isEqualTo(2L);
		assertThat(foo2Node.degree()).isEqualTo(1L);
		assertThat(foo3Node.degree()).isZero();
		assertThat(foo11Node.degree()).isEqualTo(1L);
		assertThat(foo12Node.degree()).isZero();
		assertThat(foo21Node.degree()).isZero();
		assertThat(foo111Node.degree()).isZero();
		assertThat(barNode.degree()).isZero();
	}

	@Test
	void testDepth() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.depth()).isZero();
		assertThat(foo1Node.depth()).isEqualTo(1L);
		assertThat(foo2Node.depth()).isEqualTo(1L);
		assertThat(foo3Node.depth()).isEqualTo(1L);
		assertThat(foo11Node.depth()).isEqualTo(2L);
		assertThat(foo12Node.depth()).isEqualTo(2L);
		assertThat(foo21Node.depth()).isEqualTo(2L);
		assertThat(foo111Node.depth()).isEqualTo(3L);
		assertThat(barNode.depth()).isZero();
	}

	@Test
	void testIterator() {
		final var fooNode = newTreeNode("foo");
		final var foo1Node = fooNode.extend("foo1");
		final var foo2Node = fooNode.extend("foo2");
		final var foo3Node = fooNode.extend("foo3");
		final var foo11Node = foo1Node.extend("foo11");
		final var foo12Node = foo1Node.extend("foo12");
		final var foo21Node = foo2Node.extend("foo21");
		final var foo111Node = foo11Node.extend("foo111");
		final var barNode = newTreeNode("bar");
		assertThat(fooNode.iterator()).toIterable().containsExactly(foo1Node, foo11Node, foo111Node, foo12Node, foo2Node, foo21Node, foo3Node);
		assertThat(foo1Node.iterator()).toIterable().containsExactly(foo11Node, foo111Node, foo12Node);
		assertThat(foo2Node.iterator()).toIterable().containsExactly(foo21Node);
		assertThat(foo3Node.iterator()).toIterable().isEmpty();
		assertThat(foo11Node.iterator()).toIterable().containsExactly(foo111Node);
		assertThat(foo12Node.iterator()).toIterable().isEmpty();
		assertThat(foo21Node.iterator()).toIterable().isEmpty();
		assertThat(foo111Node.iterator()).toIterable().isEmpty();
		assertThat(barNode.iterator()).toIterable().isEmpty();
	}

	@Test
	void testGetValueAndSetValue() {
		final var fooNode = newTreeNode("foo");
		assertThat(fooNode.getValue()).isEqualTo("foo");
		fooNode.setValue("bar");
		assertThat(fooNode.getValue()).isEqualTo("bar");
	}
}