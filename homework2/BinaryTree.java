/*
 * BinaryTree.java implements the basic operations of a binary tree.
 *
 * Contributors: Klajdi Bodurri & Eirini Tsitsopoulou.
 *
*/

public class BinaryTree {
    
    private Node root;

    /* A constructor that sets the root to null */
    public BinaryTree() {
        root = null;
    }

    /* Implements the addition of a node to the binary tree. (recursively)
     * Returns the new currently added node. 
    */
    private Node recursiveAddition(Node currentNode, int value) {
        // create root 
        if (currentNode == null) {
            return new Node(value);
        }

        /* Try to add the node first to the left child */
        if (value < currentNode.getValue()) {
            currentNode.setLeftChild(recursiveAddition(currentNode.getLeftChild(), value));
        }
        else {
            currentNode.setRightChild(recursiveAddition(currentNode.getRightChild(), value));
        }
        return currentNode;
    }

    /* Add a new node to the tree */
    public void add(int value){
        root = recursiveAddition(root, value);
    }

    /* In order traversal of the tree */
    private void inOrderTraversal(Node currentNode) {
        if (currentNode != null){
            inOrderTraversal(currentNode.getLeftChild());
            System.out.print(" " + currentNode.getValue());
            inOrderTraversal(currentNode.getRightChild());
        }
    }

    /* Start the inOrder traversal. */
    public void inOrder() {
        inOrderTraversal(root);
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);
        bt.inOrder();
    }
}
