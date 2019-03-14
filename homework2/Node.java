/*
 * Node.java defines a class name Node that describes the node of a binary tree.
 *
 * Contributors: Klajdi Bodurri & Eirini Tsitsopoulou.
 *
*/

public class Node {

    private static int countObj = 0;
    
    private int id = 0;
    private String value;
    private Node leftChild, rightChild;

    /* A constructor for Node class 
     * Sets the a new value to the node and inits childs with null
    */
    public Node(String nodeValue) {
        value = nodeValue;
        leftChild = null;
        rightChild = null;
        id = countObj++; 
    }

    /* Returns the id of the node */
    public int getID() {
        return id;
    }

    /* Returns the value of the node */
    public String getValue() {
        return value;
    }

    /* Returns left child */
    public Node getLeftChild() {
        return leftChild;
    }

    /* Returns right child */
    public Node getRightChild() {
        return rightChild;
    }

    /* Sets left child to a new node */
    public void setLeftChild(Node child) {
        leftChild = child;
    }

    /* Sets right child to a new node */
    public void setRightChild(Node child) {
        rightChild = child;
    }
}
