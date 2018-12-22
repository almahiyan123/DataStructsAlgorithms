import java.util.Collection;

/**
 * This is a circular, singly linked list.
 * SHIV PATEL
 */
public class LinkedList<E> implements List<E> {

	protected Node<E> head;

	protected int size;
	
	@Override
	public void add(E e) {
		Node<E> newNode = new Node<E>(e);
		if (head == null)
		{
			head = newNode;
			head.setNext(head);
		}
		else
		{
			Node<E> current = head;
			while (current.getNext() != head)
			{
				current = current.getNext();
			}
			current.setNext(newNode);
			newNode.setNext(head);
		}
		size++;
	}

	/*
	 * You will want to look at Iterator, Iterable, and 
	 * how to use a for-each loop for this method.
	 */
	@Override
	public void addAll(Collection<? extends E> c) {
		for (E item : c)
		{
			add(item);
		}
	}

	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		if (indexOf(o) == -1)
		{
			return false;
		}
		return true;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index >= size || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		Node<E> current = head;
		for (int i = 0; i < index; i++)
		{
			current = current.getNext();
		}
		return current.getData();
	}

	@Override
	public int indexOf(Object o) throws NullPointerException {
		int index = 0;
		Node<E> current = head;
		
		if (o == null || isEmpty())
		{
			throw new NullPointerException();
		}
		
		E inputData = (E) o;
		
		while (!current.getNext().equals(head))
		{
			if(current.getData().equals(inputData))
			{
				return index;
			}
			current = current.getNext();
			index++;
		}
		
		if (current.getData().equals(inputData))
		{
			return index;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return (head == null);
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index >= size || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		Node<E> current = head;
		for (int i = 0; i < index - 1; i++)
		{
			current = current.getNext();
		}
		Node<E> toRemove = current.getNext();
		current.setNext(current.getNext().getNext());
		size--;
		return toRemove.getData();
	}

	@Override
	public E remove(Object o) throws IndexOutOfBoundsException {
		if (indexOf(o) == -1)
		{
			throw new IndexOutOfBoundsException();
		}
		return remove(indexOf(o));
	}

	@Override
	public E set(int index, E e) {
		if (index >= size || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		Node<E> current = head;
		for (int i = 0; i < index; i++)
		{
			current = current.getNext();
		}
		E oldData = current.getData();
		current.setData(e);
		return oldData;
	}

	@Override
	public int size() {
		return size;
	}
	
	/*
	 * Optional toString method to display LinkedList
	 */
	public String toString()
	{
		String result = "[";
		Node<E> current = head;
		if (!isEmpty())
		{
			for (int i = 0; i < size; i++)
			{
				result = result + current.getData() + ", ";
				current = current.getNext();
			}
		}
		result = result + "]";
		return result;
	}

	/*
	 * The following methods are for grading. Do not modify them, and do not use them.
	 */

	public void setSize(int size) {
		this.size = size;
	}

	public Node<E> getHead() {
		return head;
	}

	public void setHead(Node<E> head) {
		this.head = head;
	}
}
