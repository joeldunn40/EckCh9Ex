
/** Eck Exercise 9.5
 * Create a random BT with 1023 nodes, real number items
 * Inserted using tree insert
 * Compute average & max depth of all the leaves
 * Recursive subroutines:
 * 	countLeaves
 * 	sumDepths
 * 	maxDepth
 * @author jd07
 *
 */
public class DepthBinaryTree {

	private static TreeNode root;

	public static void main(String[] args) {

		// Create random numbers & insert into root
		int numNodes = 1023;
		for (int i = 0; i < numNodes; i++) {
			treeInsert((double)Math.random()*100);
			//			treeInsert((double)(numNodes - i));
		} // end for

		//		// Test TreeNode
		//		root = new TreeNode(1.0);
		//		root.left = new TreeNode(2.0);
		//		root.right = new TreeNode(3.0);
		//		root.left.left = new TreeNode(4.0);
		//		root.left.right = new TreeNode(5.0); // Leaf
		//		root.right.right = new TreeNode(6.0); // Leaf
		//		root.left.left.left = new TreeNode(8.0); // Leaf
		//		root.left.left.right = new TreeNode(9.0); // Leaf


		int nodes = countNodes(root);
		System.out.println("Nodes: " + nodes);

		int leaves = countLeaves(root);
		System.out.println("Leaves: " + leaves);

		int leaves2 = countLeavesEck(root);
		System.out.println("Leaves[Eck]: " + leaves2);

		int sum1 = sumDepths(root);
		System.out.println("Sum of depths: " + sum1);

		int sum2 = sumDepths(root,0);
		System.out.println("Sum of depths[2]: " + sum2);

		int sum3 = sumOfLeafDepths(root,0);
		System.out.println("Sum of depths[Eck]: " + sum3);

		int max1 = maxDepth(root);
		System.out.println("Max depth: " + max1);

		int max2 = maxDepth(root, 0);
		System.out.println("Max depth[2]: " + max2);

		int max3 = maximumLeafDepth(root, 0);
		System.out.println("Max depth[Eck]: " + max3);
	} // end main

	/*
	 * Function to find the maximum depth of the leaves
	 */
	private static int maxDepth(TreeNode head, int depth) {
		if (head == null)
			return depth;
		if ( ( head.left !=null ) || ( head.right !=null ) ) 
			depth++;
		int left = maxDepth(head.left,depth);
		int right = maxDepth(head.right,depth);
		depth = right;
		if (left > right)
			depth = left;
		return depth;
	} // end maxDepth(,)

	/*
	 * Function to find the sum of depths of the leaves
	 * BASE CASE 1: empty tree
	 * BASE CASE 2: just a leaf
	 * OW: if either subtree not empty: recursive call
	 * Depth = depth to leaf - don't count subdepths twice...
	 */
	private static int sumDepths(TreeNode head, int depth) {
		if (head == null) {// empty tree
			return 0;
		}
		else if (( head.left == null ) && ( head.right == null )) {
			return depth;  // at a leaf
		}
		else {	
			// ( head.left !=null ) || ( head.right !=null )  
			// increase depth (go one deeper) and recall
			return sumDepths(head.left,depth+1) + sumDepths(head.right,depth+1);
		}
	} // end sumDepth(,)

	/** ECK Code
	 * When called as maximumLeafDepth(root,0), this will compute the
	 * max of the depths of all the leaves in the tree to which root
	 * points.  When called recursively, the depth parameter gives
	 * the depth of the node, and the routine returns the max of the
	 * depths of the leaves in the subtree to which node points.
	 * In each recursive call to this routine, depth goes up by one.
	 */
	static int maximumLeafDepth( TreeNode node, int depth ) {
		if ( node == null ) {
			// The tree is empty.  Return 0.
			return 0;
		}
		else if ( node.left == null && node.right == null) {
			// The node is a leaf, so the maximum depth in this
			// subtree is the depth of this node (the only leaf 
			// that it contains).
			return depth;
		}
		else {
			// Get the maximum depths for the two subtrees of this
			// node.  Return the larger of the two values, which
			// represents the maximum in the tree overall.
			int leftMax = maximumLeafDepth(node.left, depth + 1);
			int rightMax =  maximumLeafDepth(node.right, depth + 1);
			if (leftMax > rightMax)
				return leftMax;
			else
				return rightMax;
		}
	} // end maximumLeafDepth()

	//Eck code	
	static int countLeavesEck(TreeNode node) {
		if (node == null)
			return 0;
		else if (node.left == null && node.right == null)
			return 1;  // Node is a leaf.
		else
			return countLeavesEck(node.left) + countLeavesEck(node.right);
	} // end countNodes()

	/// Eck code
	static int sumOfLeafDepths( TreeNode node, int depth ) {
		if ( node == null ) {
			// Since the tree is empty and there are no leaves,
			// the sum is zero.
			return 0;
		}
		else if ( node.left == null && node.right == null) {
			// The node is a leaf, and there are no subtrees of node, so
			// the sum of the leaf depths is just the depth of this node.
			return depth;
		}
		else {
			// The node is not a leaf.  Return the sum of the
			// the depths of the leaves in the subtrees.
			return sumOfLeafDepths(node.left, depth + 1) 
					+ sumOfLeafDepths(node.right, depth + 1);
		}
	} // end sumOfLeafDepth()

	/* 
	 * Function to find maximum depth of tree
	 */
	private static int maxDepth(TreeNode head) {
		if (head == null) {
			return -1;
		} else {
			int left = maxDepth(head.left);
			int right = maxDepth(head.right);
			int max = right;
			if (left > right)
				max = left;
			return max + 1;
		}
	} // end maxDepth

	/*
	 * Function to sum the depths of all leaves
	 * Descend to each leaf counting the number of nodes passed through
	 * This doesn't work.
	 */
	private static int sumDepths(TreeNode head) {
		int depth;
		if (head == null)
			depth = -1;
		else
			depth = 1;
		if (head.left != null) 
			depth += sumDepths(head.left);
		if (head.right != null)
			depth +=  sumDepths(head.right);
		return depth;
	} // end sumDepths

	/*
	 * Function to count leaves on binary tree
	 * Descend down tree until no next node, return 1, then add
	 * Base Case: tree with BOTH L&R nodes null == LEAF
	 */
	private static int countLeaves(TreeNode head) {
		if (head == null)
			return 0;
		else {
			//			if ( (head.left == null) || (head.right == null) )
			if ( (head.left == null) && (head.right == null) )
				return 1;
		}	
		return countLeaves(head.left) +  countLeaves(head.right);
	}// end countLeaves

	/*
	 * Subroutine to count nodes of binary tree 
	 */
	private static int countNodes(TreeNode head) {
		if (head == null) {
			return 0;
		} else {
			int left = countNodes(head.left);
			int right = countNodes(head.right);
			return left + right + 1;
		} // end if
	} // end countNodes

	/**
	 * An object of type TreeNode represents one node in a binary tree of integers.
	 */
	private static class TreeNode {
		double item;      // The data in this node.
		TreeNode left;    // Pointer to left subtree.
		TreeNode right;   // Pointer to right subtree.
		TreeNode(double val) {
			// Constructor.  Make a node containing the specified integer.
			// Note that left and right pointers are initially null.
			item = val;
		}
	}  // end nested class TreeNode

	/**
	 * Add the item to the binary sort tree to which the global variable 
	 * "root" refers.  (Note that root can't be passed as a parameter to 
	 * this routine because the value of root might change, and a change 
	 * in the value of a formal parameter does not change the actual parameter.)
	 */
	private static void treeInsert(double newItem) {
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


} // end DepthBinaryTree
