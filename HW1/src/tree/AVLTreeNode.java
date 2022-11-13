package tree;

/**
 * AVL Tree Node
 * includes:
 * 1. key, the stored value
 * 2. height, the height of current tree whose root is current node
 * 3. left & right, left and right reference of left and right subtrees
 */
public class AVLTreeNode {
    public int key;
    public int height;
    public AVLTreeNode left;
    public AVLTreeNode right;

    public AVLTreeNode(int key) {
        this.key = key;
        height = 1;
    }
}
