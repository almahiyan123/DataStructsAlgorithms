import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * CS 1332 Fall 2013 Binary Search Tree
 * 
 * In this assignment, you will be coding methods to make a functional binary
 * search tree. If you do this right, you will save a lot of time in the next
 * two assignments (since they are just augmenting the BST to make it
 * efficient). Let's get started!
 * 
 * **************************NOTE************************ YOU WILL HAVE TO
 * HANDLE NULL DATA IN THIS ASSIGNMENT!! PLEASE TREAT NULL AS POSITIVE
 * INFINITY!!!! **************************NOTE************************
 * 
 * DO NOT CHANGE ANY OF THE PUBLIC METHOD HEADERS
 * 
 * Please make any extra inner classes, instance fields, and methods private
 */
public class BST<T extends Comparable<T>> {

	private Node<T> root;
	private int size;

	/**
	 * Add data to the binary search tree. Remember to adhere to the BST
	 * Invariant: All data to the left of a node must be smaller and all data to
	 * the right of a node must be larger. Don't forget to update the size.
	 * 
	 * For this method, you will need to traverse the tree and find the
	 * appropriate location of the data. Depending on the data's value, you will
	 * either explore the right subtree or the left subtree. When you reach a
	 * dead end (you have reached a null value), simply return a new node with
	 * the data that was passed in.
	 * 
	 * PLEASE TREAT NULL DATA AS POSITIVE INFINITY!!!!
	 * 
	 * @param data
	 *            A comparable object to be added to the tree.
	 */
	public void add(T data) {

		Node<T> toAdd = new Node<T>(data);

		if (root == null) {
			root = toAdd;
			size++;
			return;
		}

		boolean condition = true;
		Node<T> current = root;

		while (condition) {
			if (data == null) {
				while (current.getRight() != null) {
					current = current.getRight();
				}
				current.setRight(toAdd);
			} else if (toAdd.getData().compareTo(current.getData()) > 0) {
				// toAdd is greater then current
				Node<T> currentLinker = current;
				current = current.getRight();
				if (current == null) {
					currentLinker.setRight(toAdd);
					condition = false;
				}
			} else {
				// toAdd is smaller then current
				Node<T> currentLinker = current;
				current = current.getLeft();
				if (current == null) {
					currentLinker.setLeft(toAdd);
					condition = false;
				}
			}
		}

		size++;
	}

	/**
	 * Add the contents of the collection to the BST. To do this method, notice
	 * that most every collection in the java collections API implements the
	 * iterable interface. This means that you can iterate through every element
	 * in these structures with a for-each loop. Don't forget to update the
	 * size.
	 * 
	 * @param collection
	 *            A collection of data to be added to the tree.
	 */
	public void addAll(Collection<? extends T> c) {
		for (T i : c) {
			add(i);
		}
	}

	/**
	 * Remove the data element from the tree.
	 * 
	 * PLEASE TREAT NULL DATA AS POSITIVE INFINITY!
	 * 
	 * @param data
	 *            The data element to be searched for.
	 * @return retData The data that was removed from the tree. Return null if
	 *         the data doesn't exist.
	 */
	public T remove(T data) {
		LinkedList<Node<T>> found = removeFinder(root, root, data);
		Node<T> parent = found.getFirst();
		Node<T> toRemove = found.getLast();
		Node<T> current = found.getLast();
		T result;

		// only 1 item in tree
		if (size == 1) {
			root = null;
			size--;
			return null;
		}

		// just in case we return
		// before reaching the end
		size--;

		if (current == null) {
			return null;
		}

		// no children
		if (toRemove.getRight() == null && toRemove.getLeft() == null) {
			if (parent.getLeft().equals(toRemove)) {
				parent.setLeft(null);
			} else {
				parent.setRight(null);
			}
			return null;
		}

		// one child
		else if (toRemove.getLeft() == null || toRemove.getRight() == null) {
			result = toRemove.getData();
			if (toRemove.getRight() != null) {
				toRemove.setData(toRemove.getRight().getData());
				toRemove.setRight(toRemove.getRight().getRight());
			} else {
				toRemove.setData(toRemove.getLeft().getData());
				toRemove.setLeft(toRemove.getLeft().getLeft());
			}
			return result;
		}

		// two children
		else if (toRemove.getLeft() != null || toRemove.getRight() != null) {
			LinkedList<Node<T>> maxNode = maxNode(toRemove, toRemove.getLeft());
			Node<T> largestNodeParent = maxNode.getFirst();
			Node<T> largestNode = maxNode.getLast();
			// Node<T> largestNodeParent = maxNode.getFirst();
			result = largestNode.getData();

			toRemove.setData(result);
			largestNodeParent.setRight(largestNode.getLeft());

			return result;
		}

		return null;

	}

	private LinkedList<Node<T>> maxNode(Node<T> parent, Node<T> current) {
		LinkedList<Node<T>> result = new LinkedList<Node<T>>();
		result.add(parent);
		if (current.getRight() == null) {
			result.add(current);
			return result;
		}
		return maxNode(current, current.getRight());
	}

	private LinkedList<Node<T>> removeFinder(Node<T> parent, Node<T> current,
			T data) {
		// check current one
		LinkedList<Node<T>> result = new LinkedList<Node<T>>();
		result.add(parent);
		if (data == null && current.getData() == null) {
			result.add(current);
			return result;
		} else if (data.equals(current.getData())) {
			result.add(current);
			return result;
		} else {
			if (current.getData() != null
					& data.compareTo(current.getData()) > 0) {
				// data is bigger then current non-null
				if (current.getRight() != null) {
					return removeFinder(current, current.getRight(), data);
				}
				return null;
			} else {
				// data is smaller then current non-null
				if (current.getLeft() != null) {
					return removeFinder(current, current.getLeft(), data);
				}
				return null;
			}
		}
	}

	/**
	 * Get the data from the tree.
	 * 
	 * This method simply returns the data that was stored in the tree.
	 * 
	 * TREAT NULL DATA AS POSITIVE INFINITY!
	 * 
	 * @param data
	 *            The datum to search for in the tree.
	 * @return The data that was found in the tree. Return null if the data
	 *         doesn't exist.
	 */
	public T get(T data) {
		return data;
	}

	/**
	 * See if the tree contains the data.
	 * 
	 * TREAT NULL DATA AS POSITIVE INFINITY!
	 * 
	 * @param data
	 *            The data to search for in the tree.
	 * @return Return true if the data is in the tree, false otherwise.
	 */
	public boolean contains(T data) {
		return containsHelper(root, data);
	}

	private boolean containsHelper(Node<T> current, T data) {
		if (data == null) {
			while (current != null) {
				if (current.getData() == null) {
					return true;
				}
				current = current.getRight();
			}
			return false;
		}
		if (current == null) {
			return false;
		}
		if (data == null && current.getData() == null) {
			return true;
		} else if (data.equals(current.getData())) {
			return true;
		} else {
			if (current.getData() != null
					& data.compareTo(current.getData()) > 0) {
				// data is bigger then current non-null
				if (current.getRight() != null) {
					return containsHelper(current.getRight(), data);
				}
				return false;
			} else {
				// data is smaller then current non-null
				if (current.getLeft() != null) {
					return containsHelper(current.getLeft(), data);
				}
				return false;
			}
		}
	}

	/**
	 * Linearize the tree using the pre-order traversal.
	 * 
	 * @return A list that contains every element in pre-order.
	 */
	public List<T> preOrder() {
		List<T> list = new LinkedList<T>();
		preOrderHelper(root, list);
		return list;
	}

	/**
	 * Pre-order traversal helper function
	 * 
	 * @param current
	 * @param list
	 */
	private void preOrderHelper(Node<T> current, List<T> list) {
		if (current != null) {
			list.add(current.getData());
			preOrderHelper(current.getLeft(), list);
			preOrderHelper(current.getRight(), list);
		}
	}

	/**
	 * Linearize the tree using the in-order traversal.
	 * 
	 * @return A list that contains every element in-order.
	 */
	public List<T> inOrder() {
		List<T> list = new LinkedList<T>();
		inOrderHelper(root, list);
		return list;
	}

	/**
	 * In-order traversal helper function
	 * 
	 * @param current
	 * @param list
	 */
	private void inOrderHelper(Node<T> current, List<T> list) {
		if (current != null) {
			inOrderHelper(current.getLeft(), list);
			list.add(current.getData());
			inOrderHelper(current.getRight(), list);
		}
	}

	/**
	 * Linearize the tree using the post-order traversal.
	 * 
	 * @return A list that contains every element in post-order.
	 */
	public List<T> postOrder() {
		List<T> list = new LinkedList<T>();
		postOrderHelper(root, list);
		return list;
	}

	/**
	 * Post-order traversal helper function
	 * 
	 * @param current
	 * @param list
	 */
	private void postOrderHelper(Node<T> current, List<T> list) {
		if (current != null) {
			postOrderHelper(current.getLeft(), list);
			postOrderHelper(current.getRight(), list);
			list.add(current.getData());
		}
	}

	/**
	 * Test to see if the tree is empty.
	 * 
	 * @return Return true if the tree is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return (root == null);
	}

	/**
	 * 
	 * @return Return the number of elements in the tree.
	 */
	public int size() {
		return size;
	}

	/**
	 * Clear the tree. (ie. set root to null and size to 0)
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * Clear the existing tree, and rebuilds a unique binary search tree with
	 * the pre-order and post-order traversals that are passed in. Draw a tree
	 * out on paper and generate the appropriate traversals. See if you can
	 * manipulate these lists to generate the same tree.
	 * 
	 * TL;DR - at the end of this method, the tree better have the same
	 * pre-order and post-order as what was passed in.
	 * 
	 * @param preOrder
	 *            A list containing the data in a pre-order linearization.
	 * @param postOrder
	 *            A list containing the data in a post-order linearization.
	 */
	public void reconstruct(List<? extends T> preOrder,
			List<? extends T> postOrder) {
		clear();
		addAll(preOrder);
	}

	/**
	 * Converts all nodes in current tree to a string. The string consists of
	 * all elements, in order.
	 * 
	 * @return string
	 */
	public String toString() {
		StringBuilder string = new StringBuilder("[");
		helpToString(root, string);
		string.append("]");
		return string.toString();
	}

	/**
	 * Recursive help method for toString.
	 * 
	 * @param node
	 * @param string
	 */
	private void helpToString(Node<T> node, StringBuilder string) {
		if (node == null)
			return; // Tree is empty, so leave.

		if (node.getLeft() != null) {
			helpToString(node.getLeft(), string);
			string.append(", ");
		}

		string.append(node.getData());

		if (node.getRight() != null) {
			string.append(", ");
			helpToString(node.getRight(), string);
		}
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}
}
