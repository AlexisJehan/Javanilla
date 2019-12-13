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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>{@link LinkedTreeNode} unit tests.</p>
 */
final class LinkedTreeNodeTest extends AbstractTreeNodeTest {

	@Override
	<V> LinkedTreeNode<V> newTreeNode(final V value) {
		return new LinkedTreeNode<>(value);
	}

	@Test
	void testEqualsHashCodeToString() {
		final var treeNode = new LinkedTreeNode<>("foo").extend("foo1");
		treeNode.extend("foo11");
		assertThat(treeNode).isEqualTo(treeNode);
		assertThat(treeNode).isNotEqualTo(1);
		{
			final var otherTreeNode = new LinkedTreeNode<>("foo").extend("foo1");
			otherTreeNode.extend("foo11");
			assertThat(treeNode).isNotSameAs(otherTreeNode);
			assertThat(treeNode).isEqualTo(otherTreeNode);
			assertThat(treeNode).hasSameHashCodeAs(otherTreeNode);
			assertThat(treeNode).hasToString(otherTreeNode.toString());
		}
		{
			final var otherTreeNode = new LinkedTreeNode<>("foo").extend("bar1");
			otherTreeNode.extend("foo11");
			assertThat(treeNode).isNotSameAs(otherTreeNode);
			assertThat(treeNode).isNotEqualTo(otherTreeNode);
			assertThat(treeNode.hashCode()).isNotEqualTo(otherTreeNode.hashCode());
			assertThat(treeNode.toString()).isNotEqualTo(otherTreeNode.toString());
		}
		{
			final var otherTreeNode = new LinkedTreeNode<>("foo").extend("foo1");
			otherTreeNode.extend("bar11");
			assertThat(treeNode).isNotSameAs(otherTreeNode);
			assertThat(treeNode).isNotEqualTo(otherTreeNode);
			assertThat(treeNode.hashCode()).isNotEqualTo(otherTreeNode.hashCode());
			assertThat(treeNode.toString()).isNotEqualTo(otherTreeNode.toString());
		}
		{
			final var otherTreeNode = new LinkedTreeNode<>("foo").extend("foo1");
			otherTreeNode.extend("foo11").extend("foo111");
			assertThat(treeNode).isNotSameAs(otherTreeNode);
			assertThat(treeNode).isNotEqualTo(otherTreeNode);
			assertThat(treeNode.hashCode()).isNotEqualTo(otherTreeNode.hashCode());
			assertThat(treeNode.toString()).isNotEqualTo(otherTreeNode.toString());
		}
	}
}