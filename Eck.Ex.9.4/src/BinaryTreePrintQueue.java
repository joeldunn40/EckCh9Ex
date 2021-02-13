
/** Eck Exercise 9.4
 * Program to print binary tree using a queue instead of 
 * recursion. Use alogorithm from Eck:
 * Add the root node to an empty queue
 * while the queue is not empty:
 *  Get a node from the queue
 * 	Print the item in the node
 *  if node.left is not null:
 *      add it to the queue
 *   if node.right is not null:
 *      add it to the queue
 * @author jd07
 *
 */
public class BinaryTreePrintQueue {

	private static TreeNode root;
	
	public static void main(String[] args) {
		int numNodes = 10;
		// Create a random treeNode
		System.out.println("Original Order:");
		for (int i = 0; i < numNodes; i++) {
			int rVal = (int)(Math.random()*100);
			System.out.print(rVal + " ");
			treeInsert(rVal);
		}
		System.out.println();
		
		// Try recursive print options
		System.out.println("Pre order:");
		preorderPrint(root);
		System.out.println();
		System.out.println("Post order:");
		postorderPrint(root);
		System.out.println();
		System.out.println("In order:");
		inorderPrint(root);
		System.out.println();

		// Try new TreeQueue Class
		System.out.println("Tree Queue (aka LevelOrderPrint):");
		treeQueuePrint(root);
		// Use queue of treeNodes

	} // end main

	static void treeQueuePrint(TreeNode root) {
		if (root == null) 
			return;
		TreeQueue queue;
		queue = new TreeQueue();
		queue.enqueue(root);
		while (queue.isEmpty() == false ) {
			TreeNode node = queue.dequeue();
			System.out.print( node.item + " ");
			if (node.left != null ) 
				queue.enqueue(node.left);
			if (node.right != null)
				queue.enqueue(node.right);
		}
		
	}
	
	static void preorderPrint(TreeNode root) {
		if (root != null) {
			System.out.print(root.item + " ");
			preorderPrint(root.left);
			preorderPrint(root.right);			
		}
	}

	static void postorderPrint(TreeNode root) {
		if (root != null) {
			postorderPrint(root.left);
			postorderPrint(root.right);			
			System.out.print(root.item + " ");
		}
	}

	static void inorderPrint(TreeNode root) {
		if (root != null) {
			inorderPrint(root.left);
			System.out.print(root.item + " ");
			inorderPrint(root.right);			
		}
	}
	/**
	 * An object of type TreeNode represents one node in a binary tree of integers.
	 */
	private static class TreeNode {
		int item;      // The data in this node.
		TreeNode left;    // Pointer to left subtree.
		TreeNode right;   // Pointer to right subtree.
		TreeNode(int val) {
			// Constructor.  Make a node containing the specified integer.
			// Note that left and right pointers are initially null.
			item = val;
		}
	}  // end nested class TreeNode


	/**
	 * Adapted from Eck (DepthBreadth.java)
	 * enqueue adds node to tail of the queue
	 * dequeue removes node at the head & returns value
	 * normal queue - no right or left...
	 * @author jd07
	 *
	 */
	private static class TreeQueue{
		
		// Requires own node class
		// items are TreeNodes
		// next points to next Node
		private static class Node{
			TreeNode item;
			Node next;
		}
		
		private Node head = null;  // Points to first Node in the queue.
		private Node tail = null;  // Points to the last Node in the queue.
		
		void enqueue(TreeNode node) {
			// Add the specified item to the end of the queue.
			Node newTail = new Node();
			newTail.item = node;
			if (head == null) {
				head = newTail;
				tail = newTail;
			}
			else {
				tail.next = newTail;
				tail = newTail;
			}
		} // end enqueue

		TreeNode dequeue() {
			if (head == null)
				throw new IllegalStateException("Can't dequeue from empty queue");
			TreeNode firstItem = head.item;
			head = head.next;
			if (head == null)
				tail = null;
			return firstItem;
		} // end dequeue

		boolean isEmpty() {
			// Return true if the queue is empty.
			return head == null;
		}
	} // end TreeQueue

    /**
     * Add the item to the binary sort tree to which the global variable 
     * "root" refers.  (Note that root can't be passed as a parameter to 
     * this routine because the value of root might change, and a change 
     * in the value of a formal parameter does not change the actual parameter.)
     */
    private static void treeInsert(int newItem) {
        if ( root == null ) {
                // The tree is empty.  Set root to point to a new node containing
                // the new item.  This becomes the only node in the tree.
            root = new TreeNode( newItem );
            return;
        }
        TreeNode runner;  // Runs down the tree to find a place for newItem.
        runner = root;   // Start at the root.
        while (true) {
            if ( newItem < runner.item ) {
                    // Add to left if less than current
                    // If there
                    // is an open space at runner.left, add a new node there.
                    // Otherwise, advance runner down one level to the left.
                if ( runner.left == null ) {
                    runner.left = new TreeNode( newItem );
                    return;  // New item has been added to the tree.
                }
                else
                    runner = runner.left;
            }
            else {
                    // Add to right if more than current
                    // If there
                    // is an open space at runner.right, add a new node there.
                    // Otherwise, advance runner down one level to the right.
                if ( runner.right == null ) {
                    runner.right = new TreeNode( newItem );
                    return;  // New item has been added to the tree.
                }
                else
                    runner = runner.right;
            }
        } // end while
    }  // end treeInsert()

	
} // end BinaryTreePrintQueue
