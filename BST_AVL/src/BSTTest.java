import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


/**
 * @author Jared Moore
 * @version Jan 22, 2013
 */
public class BSTTest {

	public BST<Integer> buildTree() {
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(50);
		array.add(25);
		array.add(75);
		array.add(13);
		array.add(87);
		BST<Integer> tree = new BST<Integer>();
		tree.addAll(array); // note that my addAll will call add
		return tree;
	}

	/**
	 * Test method for {@link BST#remove(java.lang.Comparable)}.
	 */
	@Test
	public void testRemove() {
		
		BST<Integer> tree = buildTree();
		assertEquals(null, tree.remove(13)); // no children
		assertEquals(null, tree.getRoot().getLeft().getLeft());
		
		tree.remove(75); // one child
		assertEquals(87, (int) tree.getRoot().getRight().getData());
		
		tree.remove(50); // two children
		assertEquals(25, (int) tree.getRoot().getData());
		
		tree = buildTree(); // two children being replaced with a node that has a child 
		// should work the same as one child being replaced with a node that has a child
		tree.add(37);
		tree.add(31);
		tree.remove(50);
		assertEquals(37, (int) tree.getRoot().getData());
		assertEquals(31, (int) tree.getRoot().getLeft().getRight().getData());
	}

	/**
	 * Test method for {@link BST#contains(java.lang.Comparable)}.
	 */
	@Test
	public void testContains() {
		
		BST<Integer> tree = buildTree();
		assertEquals(true, tree.contains(50));
		assertEquals(false, tree.contains(100));
		assertEquals(false, tree.contains(null));
		tree.add(null);
		assertEquals(true, tree.contains(null));
		assertEquals(true, tree.contains(13));
	}

	/**
	 * Test method for {@link BST#preOrder()}.
	 */
	@Test
	public void testPreOrder() {
		
		BST<Integer> tree = buildTree();
		tree.add(37);
		tree.add(56);
		java.util.List<Integer> list = tree.preOrder(); // there is already a list class in my project (I use one project for all the homework files)
		String s = "[ ";
		for (Integer i : list)
			s += i.toString() + " ";
		s += "]";
		assertEquals("[ 50 25 13 37 75 56 87 ]", s);
	}

	/**
	 * Test method for {@link BST#inOrder()}.
	 */
	@Test
	public void testInOrder() {
		
		BST<Integer> tree = buildTree();
		tree.add(37);
		tree.add(56);
		java.util.List<Integer> list = tree.inOrder(); 
		String s = "[ ";
		for (Integer i : list)
			s += i.toString() + " ";
		s += "]";
		assertEquals("[ 13 25 37 50 56 75 87 ]", s);
	}

	/**
	 * Test method for {@link BST#postOrder()}.
	 */
	@Test
	public void testPostOrder() {
		
		BST<Integer> tree = buildTree();
		tree.add(37);
		tree.add(56);
		java.util.List<Integer> list = tree.postOrder(); 
		String s = "[ ";
		for (Integer i : list)
			s += i.toString() + " ";
		s += "]";
		assertEquals("[ 13 37 25 56 87 75 50 ]", s);
	}

	/**
	 * Test method for {@link BST#reconstruct(java.util.List, java.util.List)}.
	 */
	@Test
	public void testReconstruct() {
		
		BST<Integer> tree = buildTree();
		tree.add(null); // why not
		java.util.List<Integer> inOrder = tree.inOrder(), preOrder = tree.preOrder();
		tree.add(46546); // to make sure it is cleared, this will also test a feature of add, when adding something
		// greater than everything else, but there is a null in the tree
		
		tree.reconstruct(preOrder, inOrder); // this also tests clear for me
		
		assertEquals(preOrder, tree.preOrder());
		assertEquals(inOrder, tree.inOrder());
	}

}