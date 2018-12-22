import java.util.Arrays;
import java.util.Comparator;

/**
 * This is an implementation of a heap that is backed by an array.
 * 
 * This implementation will accept a comparator object that can be used to
 * define an ordering of the items contained in this heap, other than the
 * objects' default compareTo method (if they are comparable). This is useful if
 * you wanted to sort strings by their length rather than their lexicographic
 * ordering. That's just one example.
 * 
 * Null should be treated as positive infinity if no comparator is provided. If
 * a comparator is provided, you should let it handle nulls, which means it
 * could possibly throw a NullPointerException, which in this case would be
 * fine.
 * 
 * If a comparator is provided that should always be what you use to compare
 * objects. If no comparator is provided you may assume the objects are
 * Comparable and cast them to type Comparable<T> for comparisons. If they
 * happen to not be Comparable you do not need to handle anything, and you can
 * just let your cast throw a ClassCastException.
 * 
 * This is a minimum heap, so the smallest item should always be at the root.
 * 
 * @param <T>
 *            The type of objects in this heap
 */
public class BinaryHeap<T> implements Heap<T> {

	/**
	 * The comparator that should be used to order the elements in this heap
	 */
	private Comparator<T> comp;

	/**
	 * The backing array of this heap
	 */
	private T[] data;

	/**
	 * The number of elements that have been added to this heap, this is NOT the
	 * same as data.length
	 */
	private int size;

	/**
	 * Default constructor, this should initialize data to a default size (11 is
	 * normally a good choice)
	 * 
	 * This assumes that the generic objects are Comparable, you will need to
	 * cast them when comparing since there are no bounds on the generic
	 * parameter
	 */
	public BinaryHeap() {
		data = (T[]) new Comparable[11];
	}

	/**
	 * Constructor that accepts a comparator to use with this heap. Also
	 * initializes data to a default size.
	 * 
	 * When a comparator is provided it should be preferred over the objects'
	 * compareTo method
	 * 
	 * If the comparator given is null you should attempt to cast the objects to
	 * Comparable as if a comparator were not given
	 * 
	 * @param comp
	 */
	public BinaryHeap(Comparator<T> comp) {
		this.comp = comp;
		data = (T[]) new Comparable[11];
	}

	@Override
	public void add(T item) {
		int num = size + 1;
		if (num >= data.length)
			resize();
		data[num] = item;

		int parentIndex = parentIndex(num);
		T parent = data[parentIndex];

		while (parent != null && compare(item, parent)) {
			data[num] = parent;
			data[parentIndex] = item;
			num = parentIndex;
			parentIndex = parentIndex(num);
			parent = data[parentIndex];
		}

		size++;
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public T peek() {
		return data[1];
	}

	@Override
	public T remove() {
		T result = data[1];
		if (size != 0) {
			data[1] = data[size];
			data[size] = null;
		}
		size--;
		bubbleDown();
		return result;
	}

	@Override
	public int size() {
		return size;
	}

	public String toString() {
		return Arrays.toString(data);
	}

	private boolean compare(T num, T num2) {
		if (comp != null) {
			if (comp.compare(num, num2) < 0)
				return true;
			return false;
		}
		if (num == null && num2 == null) {
			return false;
		} else if (num2 == null) {
			return true;
		} else if (num == null) {
			return false;
		} else {
			if (((Comparable) num).compareTo((Comparable) num2) < 0) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Re-arrange items to obtain min-heap again (with open spot at top)
	 */
	private void bubbleDown() {
		int index = 1;
		while (hasLeftChild(index)) {
			int smaller = leftIndex(index);
			if (hasRightChild(index)
					&& ((Comparable) data[leftIndex(index)])
							.compareTo(data[rightIndex(index)]) > 0) {
				smaller = rightIndex(index);
			}
			if (((Comparable) data[index]).compareTo(data[smaller]) > 0) {
				swap(index, smaller);
			} else {
				break;
			}
			index = smaller;
		}
	}

	/**
	 * Swaps items in index pos1 and pos2
	 * 
	 * @param pos1
	 * @param pos2
	 */
	private void swap(int pos1, int pos2) {
		T holder = data[pos2];
		data[pos2] = data[pos1];
		data[pos1] = holder;
	}

	/**
	 * Resize heap array
	 */
	private void resize() {
		T[] result = (T[]) new Comparable[data.length * 2];
		for (int i = 1; i < data.length; i++) {
			result[i] = data[i];
		}
		data = result;
	}

	private boolean hasParent(int i) {
		return i > 1;
	}

	private int leftIndex(int i) {
		return i * 2;
	}

	private int rightIndex(int i) {
		return i * 2 + 1;
	}

	private boolean hasLeftChild(int i) {
		return leftIndex(i) <= size;
	}

	private boolean hasRightChild(int i) {
		return rightIndex(i) <= size;
	}

	private T parent(int i) {
		return data[parentIndex(i)];
	}

	private int parentIndex(int i) {
		return i / 2;
	}

}