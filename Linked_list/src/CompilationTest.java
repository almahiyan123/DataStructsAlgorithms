import java.util.Collections;


/**
 * This test just makes sue all of the methods exist and accept the correct
 * parameters. This test passes if it compiles, you should not run this method
 * as the results would have no meaning.
 */
public class CompilationTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		List<String> l1 = new LinkedList<>();
	        l1.add("A");
	        l1.add("C");
	        l1.add("X");
	        l1.add("Z");
	        System.out.println(l1);
	        l1.addAll(Collections.<String>emptySet());
	        //l1.clear();
	        System.out.println(l1);
	        System.out.println(l1.contains("C"));
	        System.out.println(l1.contains("G"));
	        System.out.println(l1.get(0));
	        System.out.println(l1.indexOf("Z"));
	        System.out.println(l1.isEmpty());
	        System.out.println((new LinkedList<>()).isEmpty());
	        System.out.println(l1.remove(3));
	        System.out.println(l1);
	        System.out.println(l1.size());
	        System.out.println(l1.remove("C"));
	        System.out.println(l1);
	        l1.set(0, "B");
	        System.out.println(l1);
	        //i = l1.size();
	        LinkedList<String> l2 = new LinkedList<>();
	        l2.setSize(0);
	        l2.setHead(new Node<String>("A"));
	        System.out.println(l2.getHead().getData());
	        TwistList<String> l3 = new TwistList<>();
	        l3.add("A");
	        System.out.println("Post-swing:" + l3);
	        l3.add("M");
	        System.out.println("Post-swing:" + l3);
	        l3.add("C");
	        System.out.println("Post-swing:" + l3);
	        l3.add("E");
	        System.out.println("Post-swing:" + l3);
	        l3.add("X");
	        System.out.println("Post-swing:" + l3);
	        l3.add("O");
	        System.out.println("Post-swing:" + l3);
	        l3.add("B");
	        System.out.println("Post-swing:" + l3);
	        //l3.reverse(1, 3);
	        System.out.println(l3);
	        l3.flipFlop(3);
	        System.out.println("flipflopdone");
	        //System.out.println(l3.getNode(0).getData());
	        System.out.println(l3);
	        l3 = new TwistList<>();
	        l3.add("C");
	        System.out.println("Post-swing:" + l3);
	        l3.add("D");
	        System.out.println("Post-swing:" + l3);
	        l3.add("A");
	        System.out.println("Post-swing:" + l3);
	        l3.set(0, "C");
	        l3.set(1, "D");
	        l3.set(2, "A");
	        System.out.println(l3);
	     	    
	        l3 = new TwistList<>();
	        System.out.println("runs");
	        l3.add("C");
	        l3.add("A");
	        System.out.println(l3.toString());
	        l3.add("D");
	        l3.add("B");
	        System.out.println(l3.toString());
	        System.out.println("SWAG");
	        l3.swing(1);
	        System.out.println(l3.get(0));
	        System.out.println(l3.get(1));
	        System.out.println(l3.get(2));
	        System.out.println(l3.get(3));
	        System.out.println("Post-swing:" + l3);
	        l3.reverse(0, 3);
	        System.out.println("Reversed:" + l3.toString());
	      /*
		TwistList<String> l3 = new TwistList<>();
		l3.add("D");
		l3.add("B");
		l3.add("A");
		System.out.println(l3.toString());
		
		List<String> l1 = new LinkedList<>();
		l1.add("A");
        l1.add("C");
        l1.add("X");
        l1.add("Z");
		System.out.println(l1.toString()); */
        
	}
}
