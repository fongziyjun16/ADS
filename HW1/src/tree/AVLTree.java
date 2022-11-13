package tree;

import java.io.*;
import java.util.*;

public class AVLTree {

    private AVLTreeNode root;
    private final String outputFilename = "output_file.txt";

    /**
     * initial operation about creating output file
     */
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

    /**
     * basic operations
     */

    /**
     * Given a node of AVL tree, and Get its height
     */
    private int getHeight(AVLTreeNode root) {
        return root == null ? 0 : root.height;
    }

    /**
     * Given a node of AVL tree, and Update its height
     */
    private void updateHeight(AVLTreeNode root) {
        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    /**
     * Given a node of AVL tree, and Calculate its balance factor
     */
    private int getBalanceFactor(AVLTreeNode root) {
        // the height of left subtree - the height of right subtree
        return getHeight(root.left) - getHeight(root.right);
    }

    /**
     * right rotation of a node
     */
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

    /**
     * left rotation of a node
     */
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

    /**
     * keep a node balance
     */
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

    /**
     * print result to output file
     */
    private void print(String newline) {
        try (FileWriter writer = new FileWriter(outputFilename, true)) {
            writer.write(newline + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AVLTreeNode getRoot() {
        return root;
    }

    /**
     * Insert Operation
     */

    /**
     * Given a key, Insert it into AVL Tree (search + insert + re-balance)
     */
    public void insert(int key) {
        root = insert(root, key);
    }

    /**
     * Actual Insert Operation:
     * 1. Recursively find the target node having the key.
     * 2. add new node as a leaf.
     * 3. trace back re-balance.
     */
    private AVLTreeNode insert(AVLTreeNode root, int key) {
        if (root == null) {
            return new AVLTreeNode(key);
        }
        if (key == root.key) { // find an existing node
            return root;
        }
        if (key < root.key) { // target node may be in left subtree
            root.left = insert(root.left, key);
        } else { // key < root.key
            // target node may be in right subtree
            root.right = insert(root.right, key);
        }
        updateHeight(root);
        return keepBalance(root);
    }

    /**
     * Delete Operation
     */

    /**
     * Given a key, Delete it from AVL Tree (search + delete + re-balance)
     */
    public void delete(int key) {
        root = delete(root, key);
    }

    /**
     * Actual Delete Operation:
     * Recursively find the target node having the key.
     * Delete the node.
     * Trace back re-balance.
     */
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

    /**
     * Delete the smallest node when the deleted node has 2 children and the right child of it has 2 children:
     * (Find the inorder successor)
     * 1. Recursively find the smallest node of a given tree.
     * 2. Delete the leaf node.
     * 3. Trace back re-balance.
     */
    private AVLTreeNode deleteSmallest(AVLTreeNode root, AVLTreeNode[] smallestContainer) {
        if (root.left == null) {
            smallestContainer[0] = root;
            return root.right;
        }
        // the smallest node is in the left subtree
        root.left = deleteSmallest(root.left, smallestContainer);
        updateHeight(root);
        return keepBalance(root);
    }

    /**
     * Query Operations
     */

    /**
     * Query Operations for Specific Key
     */
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

    /**
     * Binary Search the Specific Key
     */
    private AVLTreeNode search(AVLTreeNode root, int key) {
        if (root == null || root.key == key) {
            return root;
        }
        return root.key > key ? search(root.left, key) : search(root.right, key);
    }

    /**
     * Query Operations for Keys in Specific Range
     */
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

    /**
     * Binary Search Keys in Specific Range
     */
    private void search(AVLTreeNode root, int key1, int key2, List<Integer> list) {
        if (root == null) {
            return ;
        }
        if (key1 <= root.key) {
            // there are nodes in the given range in the left subtree
            search(root.left, key1, key2, list);
        }
        if (key1 <= root.key && key2 >= root.key) {
            // current node is in the range, add to result list
            list.add(root.key);
        }
        if (key2 >= root.key) {
            // there are nodes in the given range in the right subtree
            search(root.right, key1, key2, list);
        }
    }

}