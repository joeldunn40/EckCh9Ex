import textio.TextIO;

/**
 * Eck Exercise 9.7
 * Add to 9.6
 * Calculate derivatives
 * Add printInFix to create full parenthesized expression
 * 
 * COPIED FROM ECK SIMPLEPARSER3:
 * Added variable x as an expression node
 * Main evaluates at x=0,1,2,3
 * 
    This program reads standard expressions typed in by the user. 
    The program constructs an expression tree to represent the
    expression.  It then prints the value of the tree.  It also uses
    the tree to print out a list of commands that could be used
    on a stack machine to evaluate the expression.
    The expressions can use positive real numbers and
    the binary operators +, -, *, and /.  The unary minus operation
    is supported.  The expressions are defined by the BNF rules:

            <expression>  ::=  [ "-" ] <term> [ [ "+" | "-" ] <term> ]...

            <term>  ::=  <factor> [ [ "*" | "/" ] <factor> ]...

            <factor>  ::=  <number>  |  "(" <expression> ")"
	NOW:
			<factor>  ::=  <number>  |  <x-variable>  |  "(" <expression> ")"

    A number must begin with a digit (i.e., not a decimal point).
    A line of input must contain exactly one such expression.  If extra
    data is found on a line after an expression has been read, it is
    considered an error.

    In addition to the main program class, SimpleParser3, this program
    defines a set of four nested classes for implementing expression trees.

 */

public class SimpleParserDx {

    //    -------------------- Nested classes for Expression Trees ------------------------------


    /**
     *  An abstract class representing any node in an expression tree.
     *  The three concrete node classes are concrete subclasses.
     *  Two instance methods are specified, so that they can be used with
     *  any ExpNode.  The value() method returns the value of the
     *  expression.  The printStackCommands() method prints a list
     *  of commands that could be used to evaluate the expression on
     *  a stack machine (assuming that the value of the expression is
     *  to be left on the stack).
     */
    abstract private static class ExpNode {
        abstract double value(double xValue); 
        abstract void printStackCommands();
        abstract void printInFix();
        abstract ExpNode derivative();
    }

    /**
     * Represents an expression node that holds a variable
     */
    private static class VariableNode extends ExpNode {
    	VariableNode() {
    		// do nothing
    	}
    	double value(double xValue) {
    		return xValue;
    	}
    	void printStackCommands() {
    		System.out.println("  Push X");
    	}
		void printInFix() {
			System.out.print("x");
		}
		ExpNode derivative() {
			// TODO Auto-generated method stub
			return new ConstNode(1.0);
		}
    	
    } // end VariableNode class
    
    /**
     * Represents an expression node that holds a number.
     */
    private static class ConstNode extends ExpNode {
        double number;  // The number.
        ConstNode(double val) {
                // Construct a ConstNode containing the specified number.
            number = val;
        }
        double value(double xValue) {
                // The value of the node is the number that it contains.
            return number;
        }
        void printStackCommands() {
                // On a stack machine, just push the number onto the stack.
            System.out.println("  Push " + number); 
        }
		void printInFix() {
			System.out.print(number);
		}
		ExpNode derivative() {
			// TODO Auto-generated method stub
			return new ConstNode(0.0);
		}

    }


    /**
     * An expression node representing a binary operator.
     */
    private static class BinOpNode extends ExpNode {
        char op;        // The operator.
        ExpNode left;   // The expression for its left operand.
        ExpNode right;  // The expression for its right operand.
        BinOpNode(char op, ExpNode left, ExpNode right) {
                // Construct a BinOpNode containing the specified data.
            assert op == '+' || op == '-' || op == '*' || op == '/';
            assert left != null && right != null;
            this.op = op;
            this.left = left;
            this.right = right;
        }
        double value(double xValue) {
                // The value is obtained by evaluating the left and right
                // operands and combining the values with the operator.
            double x = left.value(xValue);
            double y = right.value(xValue);
            switch (op) {
            case '+':  return x + y;
            case '-':  return x - y;
            case '*':  return x * y;
            case '/':  return x / y;
            default:   return Double.NaN;  // Bad operator!
            }
            
        }
		ExpNode derivative() {
			// TODO Auto-generated method stub
			ExpNode A = this.left;
			ExpNode B = this.right;
			ExpNode dA = this.left.derivative();
			ExpNode dB = this.right.derivative();
			switch (op) {
			case '+': return new BinOpNode('+',dA,dB);
			case '-': return new BinOpNode('-',dA,dB);
			case '*':
				BinOpNode t1m = new BinOpNode('*',A,dB);
				BinOpNode t2m = new BinOpNode('*',dA,B);
				return new BinOpNode('+',t1m,t2m);
			case '/': 
				BinOpNode t1d = new BinOpNode('*',A,dB);
				BinOpNode t2d = new BinOpNode('*',dA,B);
				BinOpNode t3d = new BinOpNode('-',t1d,t2d);
				BinOpNode t4d = new BinOpNode('*',B,B);
				return new BinOpNode('/',t3d,t4d);
			default: return null; // Bad operator!
			}
		}

        void  printStackCommands() {
                // To evaluate the expression on a stack machine, first do
                // whatever is necessary to evaluate the left operand, leaving
                // the answer on the stack.  Then do the same thing for the
                // second operand.  Then apply the operator (which means popping
                // the operands, applying the operator, and pushing the result).
            left.printStackCommands();
            right.printStackCommands();
            System.out.println("  Operator " + op);
        }
		void printInFix() {
			System.out.print("(");
			left.printInFix();
			System.out.print(op);
			right.printInFix();
			System.out.print(")");
		}
    }


    /**
     * An expression node to represent a unary minus operator.
     */
    private static class UnaryMinusNode extends ExpNode {
        ExpNode operand;  // The operand to which the unary minus applies.
        UnaryMinusNode(ExpNode operand) {
                // Construct a UnaryMinusNode with the specified operand.
            assert operand != null;
            this.operand = operand;
        }
        double value(double xValue) {
                // The value is the negative of the value of the operand.
            double neg = operand.value(xValue);
            return -neg;
        }
        void printStackCommands() {
                // To evaluate this expression on a stack machine, first do
                // whatever is necessary to evaluate the operand, leaving the
                // operand on the stack.  Then apply the unary minus (which means
                // popping the operand, negating it, and pushing the result).
            operand.printStackCommands();
            System.out.println("  Unary minus");
        }
		void printInFix() {
			System.out.print("(-");
            operand.printInFix();
			System.out.print(")");
		}
		ExpNode derivative() {
//            return new ConstNode(-1.0);
            return new UnaryMinusNode(operand.derivative());
		}

    }


    //    -------------------------------------------------------------------------------


    /**
     * An object of type ParseError represents a syntax error found in 
     * the user's input.
     */
    private static class ParseError extends Exception {
        ParseError(String message) {
            super(message);
        }
    } // end nested class ParseError


    public static void main(String[] args) {

        while (true) {
            System.out.println("\n\nEnter an expression, or press return to end.");
            System.out.print("\n?  ");
            TextIO.skipBlanks();
            if ( TextIO.peek() == '\n' )
                break;
            try {
                ExpNode exp = expressionTree();
                TextIO.skipBlanks();
                if ( TextIO.peek() != '\n' )
                    throw new ParseError("Extra data after end of expression.");
                TextIO.getln();
                for (double xValue = 0.0; xValue < 3.9; xValue ++) {
                	System.out.println("\nValue (x = " + xValue + " ) is " + exp.value(xValue));
                	System.out.println("Derivative (x = " + xValue + " ) is " + exp.derivative().value(xValue));
                }
//                System.out.println("\nOrder of postfix evaluation is:\n");
//                exp.printStackCommands();
                System.out.println("\nIn-fix equation is:\n");
                exp.printInFix();
                System.out.println("\nDerivative is:\n");
                exp.derivative().printInFix();
            }
            catch (ParseError e) {
                System.out.println("\n*** Error in input:    " + e.getMessage());
                System.out.println("*** Discarding input:  " + TextIO.getln());
            }
        }

        System.out.println("\n\nDone.");

    } // end main()


    /**
     * Reads an expression from the current line of input and builds
     * an expression tree that represents the expression.
     * @return an ExpNode which is a pointer to the root node of the 
     *    expression tree
     * @throws ParseError if a syntax error is found in the input
     */
    private static ExpNode expressionTree() throws ParseError {
        TextIO.skipBlanks();
        boolean negative;  // True if there is a leading minus sign.
        negative = false;
        if (TextIO.peek() == '-') {
            TextIO.getAnyChar();
            negative = true;
        }
        ExpNode exp;       // The expression tree for the expression.
        exp = termTree();  // Start with the first term.
        if (negative)
            exp = new UnaryMinusNode(exp);
        TextIO.skipBlanks();
        while ( TextIO.peek() == '+' || TextIO.peek() == '-' ) {
                // Read the next term and combine it with the
                // previous terms into a bigger expression tree.
            char op = TextIO.getAnyChar();
            ExpNode nextTerm = termTree();
            exp = new BinOpNode(op, exp, nextTerm);
            TextIO.skipBlanks();
        }
        return exp;
    } // end expressionTree()


    /**
     * Reads a term from the current line of input and builds
     * an expression tree that represents the expression.
     * @return an ExpNode which is a pointer to the root node of the 
     *    expression tree
     * @throws ParseError if a syntax error is found in the input
     */
    private static ExpNode termTree() throws ParseError {
        TextIO.skipBlanks();
        ExpNode term;  // The expression tree representing the term.
        term = factorTree();
        TextIO.skipBlanks();
        while ( TextIO.peek() == '*' || TextIO.peek() == '/' ) {
                // Read the next factor, and combine it with the
                // previous factors into a bigger expression tree.
            char op = TextIO.getAnyChar();
            ExpNode nextFactor = factorTree();
            term = new BinOpNode(op,term,nextFactor);
            TextIO.skipBlanks();
        }
        return term;
    } // end termValue()


    /** ADDED x-variable factor
     * Reads a factor from the current line of input and builds
     * an expression tree that represents the expression.
     * @return an ExpNode which is a pointer to the root node of the 
     *    expression tree
     * @throws ParseError if a syntax error is found in the input
     */
    private static ExpNode factorTree() throws ParseError {
        TextIO.skipBlanks();
        char ch = TextIO.peek();
        if ( Character.isDigit(ch) ) {
                // The factor is a number.  Return a ConstNode.
            double num = TextIO.getDouble();
            return new ConstNode(num);
        }
        else if ( Character.toLowerCase(ch) == 'x') {
        	// The factor is an x-variable - return a variable node
            TextIO.getAnyChar();  // Read the "x"
            return new VariableNode();
        }
        else if ( ch == '(' ) {
                // The factor is an expression in parentheses.
                // Return a tree representing that expression.
            TextIO.getAnyChar();  // Read the "("
            ExpNode exp = expressionTree();
            TextIO.skipBlanks();
            if ( TextIO.peek() != ')' )
                throw new ParseError("Missing right parenthesis.");
            TextIO.getAnyChar();  // Read the ")"
            return exp;
        }
        else if ( ch == '\n' )
            throw new ParseError("End-of-line encountered in the middle of an expression.");
        else if ( ch == ')' )
            throw new ParseError("Extra right parenthesis.");
        else if ( ch == '+' || ch == '-' || ch == '*' || ch == '/' )
            throw new ParseError("Misplaced operator.");
        else
            throw new ParseError("Unexpected character \"" + ch + "\" encountered.");
    }  // end factorTree()


} // end class SimpleParserDx
