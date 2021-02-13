import java.util.ArrayList;

import textio.TextIO;

/** Eck Exercise 9.2
 * Read test file.
 * Store all words in alphabetical list using a sort tree
 * @author Joel
 *
 */
public class AlphabeticalListTree {

	private static TreeNode root; 

	
	public static void main(String[] args) {
		String word = new String(); // variable to capture new word
		String fileName = "src/randomText.txt"; 

		// With tree nodes
		// Read word
		TextIO.readFile(fileName);
		while (true) {
			word = readNextWord();
			if (word == null)
				break;
			word = word.toLowerCase();
			if ( treeContains(root, word) == false)
				treeInsert(word); // Only add word if not already included
		}
		System.out.println("Tree List: " + countNodes(root) + " items.");
		treeList(root);
		
		
		// Without tree nodes
		ArrayList<String> wordList = new ArrayList<String>(); // word list
		TextIO.readFile(fileName);
		while (true) {
			word = readNextWord();
			if (word == null)
				break;
			word = word.toLowerCase();
			if ( wordList.contains(word) == false)
				wordList.add(word); // Only add word if not already included
		}
		System.out.println("String List");
		System.out.println(wordList.size());
		wordList.sort(null);
		System.out.println(wordList);
		System.out.println("done");

	} // end main
	
	
	
	
	/**
	 * Read the next word from TextIO, if there is one.  First, skip past
	 * any non-letters in the input.  If an end-of-file is encountered before 
	 * a word is found, return null.  Otherwise, read and return the word.
	 * A word is defined as a sequence of letters.  Also, a word can include
	 * an apostrophe if the apostrophe is surrounded by letters on each side.
	 * @return the next word from TextIO, or null if an end-of-file is 
	 *     encountered
	 */
	private static String readNextWord() {
	   char ch = TextIO.peek(); // Look at next character in input.
	   while (ch != TextIO.EOF && ! Character.isLetter(ch)) {
	          // Skip past non-letters.
	      TextIO.getAnyChar();  // Read the character.
	      ch = TextIO.peek();   // Look at the next character.
	   }
	   if (ch == TextIO.EOF) // Encountered end-of-file
	      return null;
	   // At this point, we know the next character is a letter, so read a word.
	   String word = "";  // This will be the word that is read.
	   while (true) {
	      word += TextIO.getAnyChar();  // Append the letter onto word.
	      ch = TextIO.peek();  // Look at next character.
	      if ( ch == '\'' ) {
	            // The next character is an apostrophe.  Read it, and
	            // if the following character is a letter, add both the
	            // apostrophe and the letter onto the word and continue
	            // reading the word.  If the character after the apostrophe
	            // is not a letter, the word is done, so break out of the loop.
	         TextIO.getAnyChar();   // Read the apostrophe.
	         ch = TextIO.peek();    // Look at char that follows apostrophe.
	         if (Character.isLetter(ch)) {
	        	 word += '\'';
	        	 word += TextIO.getAnyChar();
//	            word += '\'' + TextIO.getAnyChar(); // Need to add sepately otherwise ASCII codes summed
	            ch = TextIO.peek();  // Look at next char.
	         }
	         else
	            break;

	      }
	      if ( ! Character.isLetter(ch) ) {
	            // If the next character is not a letter, the word is
	    	  // finished, so break out of the loop.
	          break;
	       }
	       // If we haven't broken out of the loop, next char is a letter.
	    }
	    return word;  // Return the word that has been read.
	 } // end readNextWord

	
	/***********************************************/
	/* BELOW COPIED FROM ECK SortTreeDemo.java ****/
	/***********************************************/

	/**
     * An object of type TreeNode represents one node in a binary tree of strings.
     */
    private static class TreeNode {
        String item;      // The data in this node.
        TreeNode left;    // Pointer to left subtree.
        TreeNode right;   // Pointer to right subtree.
        TreeNode(String str) {
                // Constructor.  Make a node containing the specified string.
                // Note that left and right pointers are initially null.
            item = str;
        }
    }  // end nested class TreeNode


    /**
     * Add the item to the binary sort tree to which the global variable 
     * "root" refers.  (Note that root can't be passed as a parameter to 
     * this routine because the value of root might change, and a change 
     * in the value of a formal parameter does not change the actual parameter.)
     */
    private static void treeInsert(String newItem) {
        if ( root == null ) {
                // The tree is empty.  Set root to point to a new node containing
                // the new item.  This becomes the only node in the tree.
            root = new TreeNode( newItem );
            return;
        }
        TreeNode runner;  // Runs down the tree to find a place for newItem.
        runner = root;   // Start at the root.
        while (true) {
            if ( newItem.compareTo(runner.item) < 0 ) {
                    // Since the new item is less than the item in runner,
                    // it belongs in the left subtree of runner.  If there
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
                    // Since the new item is greater than or equal to the item in
                    // runner it belongs in the right subtree of runner.  If there
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


    /**
     * Return true if item is one of the items in the binary
     * sort tree to which root points.  Return false if not.
     */
    static boolean treeContains( TreeNode root, String item ) {
        if ( root == null ) {
                // Tree is empty, so it certainly doesn't contain item.
            return false;
        }
        else if ( item.equals(root.item) ) {
                // Yes, the item has been found in the root node.
            return true;
        }
        else if ( item.compareTo(root.item) < 0 ) {
                // If the item occurs, it must be in the left subtree.
            return treeContains( root.left, item );
        }
        else {
                // If the item occurs, it must be in the right subtree.
            return treeContains( root.right, item );
        }
    }  // end treeContains()


    /**
     * Print the items in the tree in inorder, one item to a line.  
     * Since the tree is a sort tree, the output will be in increasing order.
     */
    private static void treeList(TreeNode node) {
        if ( node != null ) {
            treeList(node.left);             // Print items in left subtree.
            System.out.println("  " + node.item);  // Print item in the node.
            treeList(node.right);            // Print items in the right subtree.
        }
    } // end treeList()
    
    /**
     * Count the nodes in the binary tree.
     * @param node A pointer to the root of the tree.  A null value indicates
     * an empty tree.
     * @return the number of nodes in the tree to which node points.  For an
     * empty tree, the value is zero.
     */
    private static int countNodes(TreeNode node) {
        if ( node == null ) {
                // Tree is empty, so it contains no nodes.
            return 0;
        }
        else {
                // Add up the root node and the nodes in its two subtrees.
            int leftCount = countNodes( node.left );
            int rightCount = countNodes( node.right );
            return  1 + leftCount + rightCount;  
        }
    } // end countNodes()



}
