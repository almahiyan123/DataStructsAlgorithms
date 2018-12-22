import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Sort {

	/**
	 * Implement insertion sort.
	 * 
	 * It should be: inplace stable
	 * 
	 * Have a worst case running time of: O(n^2)
	 * 
	 * And a best case running time of: O(n)
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<T>> void insertionsort(T[] arr) {
		for (int i = 1; i < arr.length; ++i) {
			T next = arr[i];
			int j = i;
			while ((j > 0) && (arr[j - 1].compareTo(next) > 0)) {
				arr[j] = arr[j - 1];
				j--;
			}
			arr[j] = next;
		}
	}

	/**
	 * Implement quick sort.
	 * 
	 * Use the provided random object to select your pivots. For example if you
	 * need a pivot between a (inclusive) and b (exclusive) where b > a, use the
	 * following code:
	 * 
	 * int pivotIndex = r.nextInt(b - a) + a;
	 * 
	 * It should be: inplace
	 * 
	 * Have a worst case running time of: O(n^2)
	 * 
	 * And a best case running time of: O(n log n)
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<T>> void quicksort(T[] arr, Random r) {
		pivoter(arr, 0, arr.length - 1, r);
	}

	/**
	 * Recursive step for each step w/ pivot
	 * @param arr
	 * @param start
	 * @param end
	 * @param r
	 */
	private static <T extends Comparable<T>> void pivoter(T[] arr, int start,
			int end, Random r) {
		if (end - start <= 0)
			return;
		int index = r.nextInt(end - start) + start;
		swap(arr, index, end);
		int num = 1;
		int i;
		for (i = end; (i - num) >= start; i--) {
			if (arr[i].compareTo(arr[i - num]) > 0) {
				i++;
				num++;
			}
			else {
				T temp = arr[i - num];
				for (int j = i - num; j < i; j++) {
					arr[j] = arr[j + 1];
				}
				arr[i] = temp;
			}
		}
		pivoter(arr, start, i - 1, r);
		pivoter(arr, i + 1, end, r);
	}

	/**
	 * Swap items in pos1 and pos2
	 * @param arr
	 * @param pos1
	 * @param pos2
	 */
	public static <T extends Comparable<T>> void swap(T[] arr, int pos1, int pos2) {
		T temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;
	}

	/**
	 * Implement merge sort.
	 * 
	 * It should be: stable
	 * 
	 * Have a worst case running time of: O(n log n)
	 * 
	 * And a best case running time of: O(n log n)
	 * 
	 * @param arr
	 * @return
	 */
	public static <T extends Comparable<T>> T[] mergesort(T[] arr) {
		if (arr.length > 1) {
			// split two halves
			T[] left = leftHalf(arr);
			T[] right = rightHalf(arr);

			// sort two halves
			mergesort(left);
			mergesort(right);

			// merge sorted halves
			arr = merge(arr, left, right);
		}
		return arr;
	}

	/**
	 * Creates and returns left half of original array
	 * 
	 * @param arr
	 *            original array
	 * @return left half of original array
	 */
	private static <T extends Comparable<T>> T[] leftHalf(T[] arr) {
		int size = arr.length / 2;
		T[] left = (T[]) new Comparable[size];
		for (int i = 0; i < size; i++) {
			left[i] = arr[i];
		}
		return left;
	}

	/**
	 * Creates and returns right half of original array
	 * 
	 * @param arr
	 *            original array
	 * @return right half of original array
	 */
	private static <T extends Comparable<T>> T[] rightHalf(T[] arr) {
		int size = arr.length / 2;
		int sizeSecond = arr.length - size;
		T[] right = (T[]) new Comparable[sizeSecond];
		for (int i = 0; i < sizeSecond; i++) {
			right[i] = arr[i + size];
		}
		return right;
	}

	/**
	 * Merges sorted left and right arrays
	 * 
	 * @param result
	 * @param left
	 * @param right
	 * @return
	 */
	private static <T extends Comparable<T>> T[] merge(T[] result, T[] left,
			T[] right) {

		int pos1 = 0;
		int pos2 = 0;

		for (int i = 0; i < result.length; i++) { // this operator may be
			// reversed
			if (pos2 >= right.length
					|| (pos1 < left.length && (left[pos1]
							.compareTo(right[pos2])) <= 0)) {
				result[i] = left[pos1];
				pos1++;
			} else {
				result[i] = right[pos2];
				pos2++;
			}
		}

		return null;
	}

	/**
	 * Implement radix sort
	 * 
	 * Hint: You can use Integer.toString to get a string of the digits. Don't
	 * forget to account for negative integers, they will have a '-' at the
	 * front of the string.
	 * 
	 * It should be: stable
	 * 
	 * Have a worst case running time of: O(kn)
	 * 
	 * And a best case running time of: O(kn)
	 * 
	 * @param arr
	 * @return
	 */
	public static int[] radixsort(int[] arr) {

		int length = 0;
		int max = 0;

		for (int i = 0; i < arr.length; i++) {

			String str = Integer.toString(arr[i]);

			if (str.substring(0, 1).equals("-")) {
				length = str.length() - 1;
			} else {
				length = str.length();
			}

			if (length > max) {
				max = length;
			}

		}

		LinkedList<Integer>[] sort = new LinkedList[19];

		for (int i = 0; i < sort.length; i++) {
			sort[i] = new LinkedList<Integer>();
		}

		int i = 0;

		while (i < max) {

			for (int j = 0; j < arr.length; j++) {

				int currentNum = arr[j];
				String numString = Integer.toString(currentNum);
				int num = 0;
				String str = null;

				if ((numString.length() - i - 1) >= 0) {
					str = numString.substring(numString.length() - i - 1,
							numString.length() - i);
				}

				if (str != null && !str.equals("-")) {
					num = Integer.parseInt(str);
				}

				if (currentNum < 0) {
					num = num * (-1);
				}

				sort[num + 9].add(currentNum);

			}

			int x = 0;

			for (int k = 0; k < sort.length; k++) {

				while (!sort[k].isEmpty()) {
					int val = sort[k].getFirst();
					sort[k].removeFirst();
					arr[x++] = val;
				}

			}

			i++;

		}

		return arr;
	}
}