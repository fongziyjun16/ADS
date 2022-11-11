package tree;

import java.io.*;
import java.util.*;

public class AVLTree {

    private AVLTreeNode root;
    private final String outputFilename = "output_file.txt";

    public void initialize() {
        root = null;
        try {
            File outputFile = new File(outputFilename);
            if (outputFile.exists()) {
                outputFile.delete();
            }
            outputFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // basic operations

    // Given a node of AVL tree, and Get its height
    private int getHeight(AVLTreeNode root) {
        return root == null ? 0 : root.height;
    }

    // Given a node of AVL tree, and Update its height
    private void updateHeight(AVLTreeNode root) {
        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    // Given a node of AVL tree, and Calculate its balance factor
    private int getBalanceFactor(AVLTreeNode root) {
        // the height of left subtree - the height of right subtree
        return getHeight(root.left) - getHeight(root.right);
    }

    // right rotation of a node
    private AVLTreeNode rightRotate(AVLTreeNode root) {
        // Left child becomes the new root
        // The right child of the left child of the root will become the left child of the root.
        // The root will become the right child of the left child of the root.
        AVLTreeNode newRoot = root.left;

        root.left = newRoot.right;
        updateHeight(root);

        newRoot.right = root;
        updateHeight(newRoot);
        return newRoot;
    }

    // left rotation of a node
    private AVLTreeNode leftRotate(AVLTreeNode root) {
        // Right child becomes the new root
        // The left child of the right child of the root will become the right child of the root.
        // The root will become the left child of the right child of the root.
        AVLTreeNode newRoot = root.right;

        root.right = newRoot.left;
        updateHeight(root);

        newRoot.left = root;
        updateHeight(newRoot);
        return newRoot;
    }

    // keep a node balance
    private AVLTreeNode keepBalance(AVLTreeNode root) {
        int bf = getBalanceFactor(root);
        if (Math.abs(bf) > 1) { // this node is not balanced
            if (bf > 1) { // type L
                if (getBalanceFactor(root.left) < 0) {
                    // type LR, left rotation of the left child of the root
                    root.left = leftRotate(root.left);
                    updateHeight(root);
                }
                // the last step of LL and RR is right rotation of the root
                root = rightRotate(root);
            } else { // type R
                if (getBalanceFactor(root.right) > 0) {
                    // type RL, right rotation of the right child of the root
                    root.right = rightRotate(root.right);
                    updateHeight(root);
                }
                // the last step of RR and RL is left rotation of the root
                root = leftRotate(root);
            }
        }
        return root;
    }

    private void print(String newline) {
        try (FileWriter writer = new FileWriter(outputFilename, true);) {
            writer.write(newline + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // is AVL tree verification
    public boolean isValid() {
        return isBST(root, (long) Integer.MIN_VALUE - 1, (long) Integer.MAX_VALUE + 1) && isBalance(root);
    }

    private boolean isBST(AVLTreeNode root, long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.key <= min || root.key >= max) {
            return false;
        }
        return isBST(root.left, min, root.key) && isBST(root.right, root.key, max);
    }

    private boolean isBalance(AVLTreeNode root) {
        if (root == null) {
            return true;
        }
        if (Math.abs(getBalanceFactor(root)) > 1) {
            return false;
        }
        return isBalance(root.left) && isBalance(root.right);
    }

    // insert operation
    public void insert(int key) {
        root = insert(root, key);
    }

    // recursively find the target node having the key.
    // add new node as a leaf.
    // trace back re-balance.
    private AVLTreeNode insert(AVLTreeNode root, int key) {
        if (root == null) {
            return new AVLTreeNode(key);
        }
        if (key == root.key) {
            return root;
        }
        if (key < root.key) {
            root.left = insert(root.left, key);
        } else { // key < root.key
            root.right = insert(root.right, key);
        }
        updateHeight(root);
        return keepBalance(root);
    }

    // delete operation
    public void delete(int key) {
        root = delete(root, key);
    }

    // recursively find the target node having the key.
    // delete the node.
    // trace back re-balance.
    private AVLTreeNode delete(AVLTreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.key > key) {
            root.left = delete(root.left, key);
            updateHeight(root);
            return keepBalance(root);
        } else if (root.key < key) {
            root.right = delete(root.right, key);
            updateHeight(root);
            return keepBalance(root);
        }

        // condition 1: deleted node has no children
        if (root.left == null && root.right == null) {
            return null;
        }

        // condition 2: deleted node only has one child
        // condition 2.1: deleted node only has left child
        if (root.right == null) {
            return root.left;
        }

        // condition 2.2: deleted node only has right child
        if (root.left == null) {
            return root.right;
        }

        // condition 4: deleted node has 2 children
        // condition 4.1: the right node of deleted node only has right child
        if (root.right.left == null) {
            AVLTreeNode newRoot = root.right;
            newRoot.left = root.left;
            updateHeight(newRoot);
            return keepBalance(newRoot);
        }

        // condition 4.2: the right node of deleted node has 2 children
        AVLTreeNode[] smallestContainer = new AVLTreeNode[1];
        AVLTreeNode newRight = deleteSmallest(root.right, smallestContainer);
        AVLTreeNode newRoot = smallestContainer[0];
        newRoot.left = root.left;
        newRoot.right = newRight;
        updateHeight(newRoot);
        return keepBalance(newRoot);
    }

    private AVLTreeNode deleteSmallest(AVLTreeNode root, AVLTreeNode[] smallestContainer) {
        if (root.left == null) {
            smallestContainer[0] = root;
            return root.right;
        }
        root.left = deleteSmallest(root.left, smallestContainer);
        updateHeight(root);
        return keepBalance(root);
    }

    // query operation

    public String search(int key) {
        AVLTreeNode node = search(root, key);
        String result = "";
        if (node == null) {
            result = "NULL";
        } else {
            result = String.valueOf(node.key);
        }
        print(result);
        return result;
    }

    private AVLTreeNode search(AVLTreeNode root, int key) {
        if (root == null || root.key == key) {
            return root;
        }
        return root.key > key ? search(root.left, key) : search(root.right, key);
    }

    public String search(int key1, int key2) {
        List<Integer> list = new ArrayList<>();
        search(root, key1, key2, list);
        String result = "";
        if (list.size() == 0) {
            result = "NULL";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    sb.append(list.get(i)).append(",");
                } else {
                    sb.append(list.get(i));
                }
            }
            result = sb.toString();
        }
        print(result);
        return result;
    }

    private void search(AVLTreeNode root, int key1, int key2, List<Integer> list) {
        if (root == null) {
            return ;
        }
        if (key1 <= root.key) {
            search(root.left, key1, key2, list);
        }
        if (key1 <= root.key && key2 >= root.key) {
            list.add(root.key);
        }
        if (key2 >= root.key) {
            search(root.right, key1, key2, list);
        }
    }

}
