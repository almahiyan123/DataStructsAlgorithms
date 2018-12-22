import java.util.Collection;

/**
 * CS 1332 Fall 2013 AVL Tree
 * 
 * In this class, you will program an AVL Tree (Adelson Veskii-Landis Tree).
 * This is like a better version of a binary search tree in that it tries to
 * fill out every level of the tree as much as possible. It accomplishes this by
 * keeping track of each node's height and balance factor. As you recurse back
 * up from operations that modify the tree (like add or remove), you will update
 * the height and balance factor of the current node, and perform a rotation on
 * the current node if necessary. Keeping this in mind, let's get started!
 * 
 * **************************NOTE************************************* please
 * please please treat null as positive infinity!!!!!!!! PLEASE TREAT NULL AS
 * POSITIVE INFINITY!!!!
 * *************************NOTE**************************************
 * 
 * I STRONLY RECOMMEND THAT YOU IMPLEMENT THIS DATA STRUCTURE RECURSIVELY!
 * 
 * Please make any new internal classes, instance data, and methods private!!
 * 
 * DO NOT CHANGE ANY OF THE PUBLIC METHOD HEADERS
 */
public class AVL<T extends Comparable<T>> {

	private AVLNode<T> root;
	private int size;

	/**
	 * I promise you, this is just like the add() method you coded in the BST
	 * part of the homework! You will start off at the root and find the proper
	 * place to add the data. As you recurse back up the tree, you will have to
	 * update the heights and balance factors of each node that you visited
	 * while reaching the proper place to add your data. Immediately before you
	 * return out of each recursive step, you should update the height and
	 * balance factor of the current node and then call rotate on the current
	 * node. You will then return the node that comes from the rotate(). This
	 * way, the re-balanced subtrees will properly be added back to the whole
	 * tree. Also, don't forget to update the size of the tree as a whole.
	 * 
	 * PLEASE TREAT NULL AS POSITIVE INFINITY!!!!
	 * 
	 * @param data
	 *            The data do be added to the tree.
	 */
	public void add(T data) {
		if (root == null) {
			root = new AVLNode<T>(data);
			updateHeightAndBF(root);
			size++;
			return;
		}
		addHelper(null, root, data, false);
		size++;
	}

	/**
	 * Helper function for add
	 */
	private void addHelper(AVLNode<T> parent, AVLNode<T> current, T data,
			boolean isRight) {
		if (data == null
				|| (current.getData() != null && data.compareTo(current
						.getData()) > 0)) {
			if (current.getRight() == null) {
				current.setRight(new AVLNode<T>(data));
				updateHeightAndBF(current.getRight());
			} else {
				addHelper(current, current.getRight(), data, true);
			}
		} else if (current.getLeft() == null) {
			current.setLeft(new AVLNode<T>(data));
			updateHeightAndBF(current.getLeft());
		} else {
			addHelper(current, current.getLeft(), data, false);
		}

		updateHeightAndBF(current);
		rotate(current);
	}

	/**
	 * This is a pretty simple method. All you need to do is to get every
	 * element in the collection that is passed in into the tree.
	 * 
	 * Try to think about how you can combine a for-each loop and your add
	 * method to accomplish this.
	 * 
	 * @param c
	 *            A collection of elements to be added to the tree.
	 */
	public void addAll(Collection<? extends T> c) {
		for (T i : c) {
			add(i);
		}
	}

	/**
	 * All right, now for the remove method. Just like in the vanilla BST, you
	 * will have to traverse to find the data the user is trying to remove.
	 * 
	 * You will have three cases:
	 * 
	 * 1. Node to remove has zero children. 2. Node to remove has one child. 3.
	 * Node to remove has two children.
	 * 
	 * For the first case, you simply return null up the tree. For the second
	 * case, you return the non-null child up the tree.
	 * 
	 * Just as in add, you'll have to updateHeightAndBF() as well as rotate()
	 * just before you return out of each recursive step.
	 * 
	 * FOR THE THIRD CASE USE THE PREDECESSOR OR YOU WILL LOSE POINTS
	 * 
	 * @param data
	 *            The data to search in the tree.
	 * @return The data that was removed from the tree.
	 */
	public T remove(T data) {
		return removeHelper(null, root, data, false);
	}

	private T removeHelper(AVLNode<T> parent, AVLNode<T> current, T data,
			boolean isRight) {

		if (data == null && current.getData() == null) {

		} else if ((data == null && current.getData() != null)
				|| !data.equals(current.getData())) {
			if (data == null
					|| (current.getData() != null && data.compareTo(current
							.getData()) > 0)) {
				if (current.getRight() != null) {
					T result = removeHelper(current, current.getRight(), data,
							true);
					updateHeightAndBF(current);
					rotate(current);
					return result;
				} else {
					return null;
				}
			} else if (current.getLeft() != null) {
				T result = removeHelper(current, current.getLeft(), data, false);
				updateHeightAndBF(current);
				rotate(current);
				return result;
			} else {
				return null;
			}
		}

		T result = current.getData();

		// 0 children
		if (current.getLeft() == null && current.getRight() == null) {
			if (parent == null) {
				root = null;
			} else if (isRight) {
				parent.setRight(null);
			} else {
				parent.setLeft(null);
			}
		}
		// 1 child
		else if ((current.getLeft() == null && current.getRight() != null)
				|| (current.getLeft() != null && current.getRight() == null)) {
			if (current.getLeft() == null) {
				if (parent == null) {
					root = moveNode(current, current.getRight());
				} else if (isRight) {
					parent.setRight(moveNode(current, current.getRight()));
				} else {
					parent.setLeft(moveNode(current, current.getRight()));
				}
			} else if (parent == null) {
				root = moveNode(current, current.getLeft());
			} else if (isRight) {
				parent.setRight(moveNode(current, current.getLeft()));
			} else {
				parent.setLeft(moveNode(current, current.getLeft()));
			}
		}
		// 2 children
		else {
			if (parent == null) {
				root = moveNode(root, predecessor(current, true, true));
			} else if (isRight) {
				parent.setRight(moveNode(current,
						predecessor(current, true, false)));
			} else {
				parent.setLeft(moveNode(current,
						predecessor(current, true, false)));
			}
		}

		size--;
		updateHeightAndBF(current);
		rotate(current);

		return result;
	}

	private AVLNode<T> moveNode(AVLNode<T> toRemove, AVLNode<T> toMove) {
		if (toMove.getLeft() == null) {
			toMove.setLeft(toRemove.getLeft());
			if (toRemove.getRight() != toMove) {
				toMove.setRight(toRemove.getRight());
			}
		} else {
			toRemove.getLeft().setRight(toMove.getLeft());
			if (toRemove.getLeft() != toMove) {
				toMove.setLeft(toRemove.getLeft());
			}
			if (toRemove.getRight() != toMove) {
				toMove.setRight(toRemove.getRight());
			}
		}
		return toMove;
	}

	private AVLNode<T> predecessor(AVLNode<T> current, boolean isFirst,
			boolean isRoot) {
		if (isFirst) {
			if (current.getLeft().getRight() == null) {
				AVLNode<T> newNode = current.getLeft();
				if (!isRoot) {
					current.setLeft(null);
				}
				return newNode;
			}
			return predecessor(current.getLeft(), false, isRoot);
		} else {
			if (current.getRight().getRight() == null) {
				AVLNode<T> newNode = current.getRight();
				if (!isRoot) {
					current.setRight(null);
				}
				return newNode;
			}
			return predecessor(current.getRight(), false, isRoot);
		}
	}

	/**
	 * This method should be pretty simple, all you have to do is recurse to the
	 * left or to the right and see if the tree contains the data.
	 * 
	 * @param data
	 *            The data to search for in the tree.
	 * @return The boolean flag that indicates if the data was found in the tree
	 *         or not.
	 */
	public boolean contains(T data) {
		return containsHelper(root, data);
	}

	private boolean containsHelper(AVLNode<T> current, T data) {
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
	 * Again, simply recurse through the tree and find the data that is passed
	 * in.
	 * 
	 * @param data
	 *            The data to fetch from the tree.
	 * @return The data that the user wants from the tree. Return null if not
	 *         found.
	 */
	public T get(T data) {
		if (contains(data)) {
			return data;
		}
		return null;
	}

	/**
	 * Test to see if the tree is empty.
	 * 
	 * @return A boolean flag that is true if the tree is empty.
	 */
	public boolean isEmpty() {
		return (root == null);
	}

	/**
	 * Return the number of data in the tree.
	 * 
	 * @return The number of data in the tree.
	 */
	public int size() {
		return size;
	}

	/**
	 * Reset the tree to its original state. Get rid of every element in the
	 * tree.
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	// The below methods are all private, so we will not be directly grading
	// them,
	// however we strongly recommend you not change them, and make use of them.

	/**
	 * Use this method to update the height and balance factor for a node.
	 * 
	 * @param node
	 *            The node whose height and balance factor need to be updated.
	 */
	private void updateHeightAndBF(AVLNode<T> current) {
		if (current.getRight() == null && current.getLeft() == null) {
			current.setHeight(1);
			current.setBF(0);
		} else if (current.getRight() == null) {
			current.setHeight(current.getLeft().getHeight() + 1);
			current.setBF(current.getLeft().getHeight());
		} else if (current.getLeft() == null) {
			current.setHeight(current.getRight().getHeight() + 1);
			current.setBF(current.getRight().getHeight() * -1);
		} else {
			current.setHeight(1 + Math.max(current.getLeft().getHeight(),
					current.getRight().getHeight()));
			current.setBF(-current.getRight().getHeight()
					+ current.getLeft().getHeight());
		}
	}

	/**
	 * In this method, you will check the balance factor of the node that is
	 * passed in and decide whether or not to perform a rotation. If you need to
	 * perform a rotation, simply call the rotation and return the new root of
	 * the balanced subtree. If there is no need for a rotation, simply return
	 * the node that was passed in.
	 * 
	 * @param node
	 *            - a potentially unbalanced node
	 * @return The new root of the balanced subtree.
	 */
	private AVLNode<T> rotate(AVLNode<T> node) {
		AVLNode<T> current = node;
		if (node != null) {
			updateHeightAndBF(node);
			if (node.getBf() < -1) {
				if (node.getRight().getBf() > 0) {
					current = rightLeftRotate(node);
				} else {
					current = leftRotate(node);
				}
			} else if (node.getBf() > 1) {
				if (node.getLeft().getBf() < 0) {
					current = leftRightRotate(node);
				} else {
					current = rightRotate(node);
				}
			}
			if (node == root) {
				root = current;
			}
			return current;
		}
		return null;
	}

	/**
	 * In this method, you will perform a left rotation. Remember, you perform a
	 * LEFT rotation when the sub-tree is RIGHT heavy. This moves more nodes
	 * over to the LEFT side of the node that is passed in so that the height
	 * differences between the LEFT and RIGHT subtrees differ by at most one.
	 * 
	 * HINT: DO NOT FORGET TO RE-CALCULATE THE HEIGHT OF THE NODES WHOSE
	 * CHILDREN HAVE CHANGED! YES, THIS DOES MAKE A DIFFERENCE!
	 * 
	 * @param node
	 *            - the current root of the subtree to rotate.
	 * @return The new root of the subtree
	 */
	private AVLNode<T> leftRotate(AVLNode<T> node) {
		AVLNode<T> nodeCopy = new AVLNode<T>(node.getData());
		AVLNode<T> rightNode = node.getRight();
		AVLNode<T> rightleftNode = rightNode.getLeft();
		
		node.setData(rightNode.getData());
		nodeCopy.setLeft(node.getLeft());
		nodeCopy.setRight(rightleftNode);
		node.setLeft(nodeCopy);
		node.setRight(rightNode.getRight());

		rotate(nodeCopy);
		rotate(node);

		return node;
	}

	/**
	 * In this method, you will perform a right rotation. Remember, you perform
	 * a RIGHT rotation when the sub-tree is LEFT heavy. THis moves more nodes
	 * over to the RIGHT side of the node that is passed in so that the height
	 * differences between the LEFT and RIGHT subtrees differ by at most one.
	 * 
	 * HINT: DO NOT FORGET TO RE-CALCULATE THE HEIGHT OF THE NODES WHOSE
	 * CHILDREN HAVE CHANGED! YES, THIS DOES MAKE A DIFFERENCE!
	 * 
	 * @param node
	 *            - The current root of the subtree to rotate.
	 * @return The new root of the rotated subtree.
	 */
	private AVLNode<T> rightRotate(AVLNode<T> node) {
		AVLNode<T> nodeCopy = new AVLNode<T>(node.getData());
		AVLNode<T> leftNode = node.getLeft();
		AVLNode<T> leftRightNode = leftNode.getRight();
		
		node.setData(leftNode.getData());
		nodeCopy.setRight(node.getRight());
		nodeCopy.setLeft(leftRightNode);
		node.setLeft(leftNode.getLeft());
		node.setRight(nodeCopy);

		rotate(nodeCopy);
		rotate(node);

		return node;
	}

	/**
	 * In this method, you will perform a left-right rotation. You can simply
	 * use the left and right rotation methods on the node and the node's child.
	 * Remember that you must perform the rotation on the node's child first,
	 * otherwise you will end up with a mangled tree (sad face). After rotating
	 * the child, remember to link up the new root of the that first rotation
	 * with the node that was passed in.
	 * 
	 * The whole point of heterogeneous rotations is to transform the node's
	 * subtree into one of the cases handled by the left and right rotations.
	 * 
	 * @param node
	 * @return The new root of the subtree.
	 */
	private AVLNode<T> leftRightRotate(AVLNode<T> node) {
		leftRotate(node.getLeft());
		return rightRotate(node);
	}

	/**
	 * In this method, you will perform a right-left rotation. You can simply
	 * use your right and left rotation methods on the node and the node's
	 * child. Remember that you must perform the rotation on the node's child
	 * first, otherwise you will end up with a mangled tree (super sad face).
	 * After rotating the node's child, remember to link up the new root of that
	 * first rotation with the node that was passed in.
	 * 
	 * Again, the whole point of the heterogeneous rotations is to first
	 * transform the node's subtree into one of the cases handled by the left
	 * and right rotations.
	 * 
	 * @param node
	 * @return The new root of the subtree.
	 */
	private AVLNode<T> rightLeftRotate(AVLNode<T> node) {
		rightRotate(node.getRight());
		return leftRotate(node);
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
	private void helpToString(AVLNode<T> node, StringBuilder string) {
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

	public AVLNode<T> getRoot() {
		return root;
	}

	public void setRoot(AVLNode<T> root) {
		this.root = root;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
