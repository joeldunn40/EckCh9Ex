/** Eck Exercise 9.3
 * Make a copy of a linked list of integers in reverse order
 * @author Joel
 *
 */
public class ReverseCopyLinkedList {
	public static void main(String[] args) {

		// Create linked list of integers to copy
		int[][] intA = {{},{3},{6,14},{2,4,6,77,34,64,23,1},{32,23,56}};

		for (int ii = 0; ii < intA.length; ii++ ) {

			ListNode head = new ListNode();
			ListNode runner = head;
			head = runner;
			if ( intA[ii].length > 0 ) { // don't add anything if null array
				for (int i = 0; i < intA[ii].length; i++) {
					runner.item = intA[ii][i];
					if (i < (intA[ii].length -1) ) // don't add .next on final
						runner.next = new ListNode();
					runner = runner.next;
				} // end for i
			} // end if 
			// Print out head items
			System.out.print("Original     (");
			printNode(head);
			System.out.println(" )");

			ListNode headCopy = reverseCopyNode(head);
			System.out.print("Reverse Copy (");
			printNode(headCopy);
			System.out.println(" )");

		} //end for ii
	}// end main

	static class ListNode {
		int item;       // An item in the list.
		ListNode next;  // Pointer to the next node in the list.
	} // end class ListNode

	/*
	 * Eck's code: couldn't get recursive algorithm to work. 
	 */
	static ListNode reverseCopyNode(ListNode head) {
		ListNode rev = null;
		ListNode runner = head;
		while(runner != null) {
			ListNode newNode = new ListNode();
			newNode.item = runner.item;
			newNode.next = rev;
			rev = newNode;
			runner = runner.next;
		}
		return rev;
	} // end reverseCopyNonRecursive

	static void printNode(ListNode head) {

		// My recursive subroutine but ends up a bit messy
		if (head != null) {
			System.out.print(" " + head.item);
			printNode(head.next);
		}

		// Eck's code: neater output
		//		ListNode runner;
		//		runner = head;
		//		System.out.print(" ( ");
		//		while (runner != null) {
		//			System.out.print(" " + runner.item);
		//			runner = runner.next;
		//		}
		//		System.out.println(" ) ");

	} // end printNodel
}
