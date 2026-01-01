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
package com.github.alexisjehan.javanilla.misc.trees;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Deprecated
final class LinkedTreeNodeTest extends AbstractTreeNodeTest {

	@Override
	<V> LinkedTreeNode<V> newTreeNode(final V value) {
		return new LinkedTreeNode<>(value);
	}

	@Test
	void testEqualsAndHashCodeAndToString() {
		final var treeNode = new LinkedTreeNode<>("foo").extend("foo1");
		treeNode.extend("foo11");
		assertThat(treeNode.equals(treeNode)).isTrue();
		assertThat(treeNode).isNotEqualTo(new Object());
		AssertionsForClassTypes.assertThat(new LinkedTreeNode<>("foo").extend("foo1")).satisfies(otherTreeNode -> {
			otherTreeNode.extend("foo11");
			assertThat(otherTreeNode).isNotSameAs(treeNode);
			assertThat(otherTreeNode).isEqualTo(treeNode);
			assertThat(otherTreeNode).hasSameHashCodeAs(treeNode);
			assertThat(otherTreeNode).hasToString(treeNode.toString());
		});
		AssertionsForClassTypes.assertThat(new LinkedTreeNode<>("foo").extend("bar1")).satisfies(otherTreeNode -> {
			otherTreeNode.extend("foo11");
			assertThat(otherTreeNode).isNotSameAs(treeNode);
			assertThat(otherTreeNode).isNotEqualTo(treeNode);
			assertThat(otherTreeNode).doesNotHaveSameHashCodeAs(treeNode);
			assertThat(otherTreeNode).doesNotHaveToString(treeNode.toString());
		});
		AssertionsForClassTypes.assertThat(new LinkedTreeNode<>("foo").extend("foo1")).satisfies(otherTreeNode -> {
			otherTreeNode.extend("bar11");
			assertThat(otherTreeNode).isNotSameAs(treeNode);
			assertThat(otherTreeNode).isNotEqualTo(treeNode);
			assertThat(otherTreeNode).doesNotHaveSameHashCodeAs(treeNode);
			assertThat(otherTreeNode).doesNotHaveToString(treeNode.toString());
		});
		AssertionsForClassTypes.assertThat(new LinkedTreeNode<>("foo").extend("foo1")).satisfies(otherTreeNode -> {
			otherTreeNode.extend("foo11").extend("foo111");
			assertThat(otherTreeNode).isNotSameAs(treeNode);
			assertThat(otherTreeNode).isNotEqualTo(treeNode);
			assertThat(otherTreeNode).doesNotHaveSameHashCodeAs(treeNode);
			assertThat(otherTreeNode).doesNotHaveToString(treeNode.toString());
		});
	}
}