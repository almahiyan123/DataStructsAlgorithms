
/**
 * This class extends LinkedList, but tcurrent's a twist. Read the documentation
 * for each method. Note that the data current is Comparable.
 * SHIV PATEL
 */
public class TwistList<E extends Comparable<E>> extends LinkedList<E> {

	/**
	 * If the data is less than the head, add to the front of the list.
	 * Otherwise, find the first index wcurrent one of the two adjacent nodes
	 * are greater than the data, and the other is less than the data. If
	 * such an index does not exist, add the data to the end of the list.
	 * 
	 * When the above process is complete call swing with the index of the
	 * newly added data. 
	 */
	@Override
	public void add(E e) {
		Node<E> newNode = new Node<E>(e);
		if (head == null) {
			head = newNode;
			head.setNext(head);
			size++;
		} else {
			if (head.getData().compareTo(e) > 0) {		
				Node<E> currentNode = head;
				head = newNode;
				head.setNext(currentNode);
				for(int i = 1; i < size; i++) {
					currentNode = currentNode.getNext();
				}
				currentNode.setNext(head);
				size++;
			} else {
				Node<E> current = head;
				while (current.getNext().getData().compareTo(e) < 0 && current.getNext() != head) {
					current = current.getNext();
				}
				newNode.setNext(current.getNext());
				current.setNext(newNode);
				size++;
			}
		}
		swing(indexOf(e));
	}
	
	/**
	 * Reverses the order of the list between the start and stop index inclusively.
	 * 
	 * Assume the indices given are valid and start <= stop
	 * 
	 * @param start The beginning index of the sub section to be reversed
	 * @param stop The end index (inclusive) of the sub section to be reversed
	 */
	public void reverse(int start, int stop) {
		Node<E> left = head;
		Node<E> right = head;
		
		if (start == stop) {
			return;
		}
		
		boolean makeHeadEndNode = false;
		if (start == 0) {
			for (int i = 0; i < size - 1; i++)
				left = left.getNext(); 
			makeHeadEndNode = true;
		} else {
			for (int i = 0; i < start - 1; i++) {
				left = left.getNext();
			}
		}		
		for (int i = 0; i < stop - 1; i++) {
			right = right.getNext();
		}
		
		Node<E> endNode = head;
		for (int i = 0; i < stop; i++) {
			endNode = endNode.getNext();
		}
		
		if (start == 0 && stop == size - 1) {
			head = endNode;
			reverse(start + 1, stop);
		} else {
			right.setNext(endNode.getNext());
			endNode.setNext(left.getNext());
			if (makeHeadEndNode) {
				head = endNode;				
			}
			left.setNext(endNode);
			reverse(start + 1, stop);
		}
	}
	
	/**
	 * This method will take in an index and move everything after 
	 * that index to the front of the list
	 * 
	 * Assume the index given is valid
	 * 
	 * @param index The index at which to cut the list
	 */
	public void flipFlop(int index) {
		Node<E> current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		head = current.getNext();
	}
	
	/**
	 * This method will reverse the order of the first half of the list up to 
	 * the index argument (inclusive), and also reverse the second half of the 
	 * list from index + 1 to the end of the list
	 * 
	 * Assume the index given is valid, however the second half may be empty
	 * 
	 * @param index The index to swing around
	 */
	public void swing(int index) throws IndexOutOfBoundsException {
		if (index < 0) {
			throw new IndexOutOfBoundsException();
		}
		reverse(0, index);
		if (index <= size-2) {
			reverse(index+1, size-1);
		}
	}
}
