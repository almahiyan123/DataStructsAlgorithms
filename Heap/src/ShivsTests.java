import java.util.Arrays;
import java.util.Random;


public class ShivsTests {
	
	public static void main(String[] args) {
		
		System.out.println("----------------------------\nSORT TEST\n----------------------------");
		
		Random r = new Random();
		Sort sorter = new Sort();
		int[] arr = {3, 4, 2, 6, 9, 302, 4356, 1, 7, -4};
		System.out.println("Before: " + Arrays.toString(arr));
		sorter.radixsort(arr);
		System.out.println("After: " + Arrays.toString(arr));
		
		
		System.out.println("----------------------------\nBINARY HEAP TEST\n----------------------------");
		
		BinaryHeap<Integer> testOne = new BinaryHeap<Integer>();
		testOne.add(1);
		testOne.add(2);
		testOne.add(3);
		testOne.add(4);
		System.out.println(testOne.toString());
		testOne.remove();
		System.out.println(testOne.toString());
		testOne.add(1);
		System.out.println(testOne.toString());
		testOne.add(0);
		System.out.println(testOne.toString());
		testOne.add(6);
		testOne.add(7);
		testOne.add(8);
		testOne.add(9);
		testOne.add(1);
		System.out.println(testOne.toString());
		
	}
	
}
