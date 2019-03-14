/* 
 * Implements the basic operations of an arithmetic calculator. Converts the 
 * expression into math syntax tree. Supports in-order traversal through the tree.
 * Exports the tree in a proper form for graphiz. Lastly, calculates the arithmetic expression.
 * 
 * Contributors: Klajdi Bodurri && Eirini Tsitsopoulou.
*/ 

import java.util.*;
import java.io.*;

public class ArithmeticCalculator {

    private String inputExpression;

    // The parser of the expression
    private Tokenizer tokenizer=null;

    // The root of the tree
    private Node root = null;

    public ArithmeticCalculator(String newExpression) {
        inputExpression = newExpression;
        tokenizer = new Tokenizer(inputExpression);
    }

    /* 
     * Converts the expression to a binary tree.
     * Returns the tree as string in a proper form for graphiz.
    */ 
    public String toDotString() {
        String graphvizString = "digraph ArithmeticExpressionTree {\nfontcolor=\"navy\";\nfontsize=20;\nlabelloc=\"t\";\nlabel=\"Arithmetic Expression\"\n";

        root = expressionToTree();
        graphvizString = graphvizString + inOrderTraversalGraphviz(root) + "}";
        return graphvizString;
    }

    public String toString(){
        return inOrderTraversal(root);
    }

    // builds up the tree from the expression.
    private Node expressionToTree() {
        return LowPriority();
    }

    // Checks if the current token is + or -. 
    private Node LowPriority() {
        Node node = null;

        // Check if the expression from the left is "()" or number.
        Node leftExpression = primaryExpression();

        Token token = tokenizer.peekToken();
    
        while (token.isOperator() && (token.getValue().equals("+") || token.getValue().equals("-"))) {
            // we found the left child.
            node = new Node(token.getValue());
            node.setLeftChild(leftExpression);

            // Skip the current token since we checked it, find the right subtree.
            tokenizer.skipToken();
            node.setRightChild(primaryExpression());

            // this node is the left subtree of the node that called it.
            leftExpression = node;
            token = tokenizer.peekToken();
        }
        return leftExpression;
    }

    /*
     * Checks if the current token is "(" or number.
     * Here is where the recursion happens because of "(".
     * Returns the root node of the subtree.
    */ 
    private Node primaryExpression() {
        Node node = null;
        Token token = tokenizer.peekToken();
    
        // The node recursion ends here becauses token is a number.
        if (token.isNumber()) {
            tokenizer.skipToken();
            node = new Node(token.getValue());
        }
        else { // There is a subexpression inside parenthesis. 
            if (token.getValue().equals("(")) {
                tokenizer.skipToken();
                node = expressionToTree();
                token = tokenizer.getToken();
            }
        }
        return node;
    }

    /*
     * Traverses the tree with in-order method and generates a mathematical expression.
    */
    private String inOrderTraversal(Node currentNode) {
        String leftExpression = null;
        String rightExpression = null;

        if (currentNode != null) {
            // get left child
            leftExpression = inOrderTraversal(currentNode.getLeftChild());
            
            // get right child
            rightExpression = inOrderTraversal(currentNode.getRightChild());

            // father returns the subexpression of his subtree
            if (leftExpression != null && rightExpression != null){
                return "("+leftExpression+currentNode.getValue()+rightExpression+")";
            }
            else {
                return currentNode.getValue();
            }
        }
        return null;
    }

    /*
     * Traverses the tree with in-order method and generates a string that can be
     * read by graphviz.
    */ 
    private String inOrderTraversalGraphviz(Node currentNode) {
        String leftExpression = null;
        String rightExpression = null;

        if (currentNode != null) {
            leftExpression = inOrderTraversalGraphviz(currentNode.getLeftChild());
            rightExpression = inOrderTraversalGraphviz(currentNode.getRightChild());
            String currentNodeCircle = String.format("%d [label=\"%s\", shape=circle, color=black]\n", currentNode.getID(), currentNode.getValue());
            if (leftExpression !=null && rightExpression != null){
                String leftChildConnection = String.format("%d -> %d\n", currentNode.getID(), currentNode.getLeftChild().getID());
                String rightChildConnection = String.format("%d -> %d\n", currentNode.getID(), currentNode.getRightChild().getID());
                return leftExpression + rightExpression + currentNodeCircle + leftChildConnection + rightChildConnection;
            }
            else {
                return currentNodeCircle;
            }
        }
        return null;       
    } 

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter expression: ");
        String expr = sc.nextLine();
        ArithmeticCalculator tree = new ArithmeticCalculator(expr);  // generate tree of nodes
        try {        
          PrintWriter pfile = new PrintWriter("ArithmeticExpression.dot");
          pfile.println(tree.toDotString());
          pfile.close();
          System.out.println("PRINT DOT FILE OK!");
                 
          Process p = Runtime.getRuntime().exec("dot -Tpng ArithmeticExpression.dot “ + -o ArithmeticExpression.png");
          p.waitFor();
          System.out.println("PRINT PNG FILE OK!");
        } catch(Exception ex) {
          System.err.println("Unable to write dotString!!!");
          ex.printStackTrace();
          System.exit(1);
        }
    }
}
