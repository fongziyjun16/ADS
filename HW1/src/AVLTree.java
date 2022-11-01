import java.io.*;
import java.util.*;

public class AVLTree {

    private AVLTreeNode root;
    private final String outputFilename = "output_file.txt";

    public void Initialize() {
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

    // insert operation

    public void Insert(int key) {
        root = insert(root, key);
    }

    private AVLTreeNode insert(AVLTreeNode root, int key) {
        if (root == null) {
            return new AVLTreeNode(key);
        }
        if (key < root.key) {
            root.left = insert(root.left, key);
        } else {
            root.right = insert(root.right, key);
        }
        root = keepBalance(root);
        return root;
    }

    private AVLTreeNode keepBalance(AVLTreeNode root) {
        if (!isBalance(root)) {
            // not balance, then keep balance
            if (getBalanceFactor(root) > 0) { // LL or LR
                if (getBalanceFactor(root.left) > 0) { // LL
                    root = rightRotate(root);
                } else { // LR
                    root.left = leftRotate(root.left);
                    root = rightRotate(root);
                }
            } else { // RR or RL
                if (getBalanceFactor(root.right) < 0) { // RR
                    root = leftRotate(root);
                } else { // RL
                    root.right = rightRotate(root.right);
                    root = leftRotate(root);
                }
            }
        }
        updateHeight(root);
        return root;
    }

    private boolean isBalance(AVLTreeNode root) {
        return getBalanceFactor(root) <= 1;
    }

    private int getBalanceFactor(AVLTreeNode root) {
        return Math.abs((root.left == null ? 0 : root.left.height) - (root.right == null ? 0 : root.right.height));
    }

    private void updateHeight(AVLTreeNode root) {
        root.height = Math.max(root.left == null ? 0 : root.left.height, root.right == null ? 0 : root.right.height) + 1;
    }

    private AVLTreeNode rightRotate(AVLTreeNode root) {
        AVLTreeNode newRoot = root.left;
        AVLTreeNode leftRight = root.left.right;
        newRoot.right = root;
        root.left = leftRight;
        updateHeight(root);
        updateHeight(newRoot);
        return newRoot;
    }

    private AVLTreeNode leftRotate(AVLTreeNode root) {
        AVLTreeNode newRoot = root.right;
        AVLTreeNode rightLeft = root.right.left;
        newRoot.left = root;
        root.right = rightLeft;
        updateHeight(root);
        updateHeight(newRoot);
        return newRoot;
    }

    // delete operation

    public void Delete(int key) {
        root = delete(root, key);
    }

    private AVLTreeNode delete(AVLTreeNode root, int key) {
        if (root == null) {
            return root;
        }
        if (root.key > key) {
            root.left = delete(root.left, key);
            root = keepBalance(root);
            return root;
        } else if (root.key < key) {
            root.right = delete(root.right, key);
            root =keepBalance(root);
            return root;
        }
        if (root.left == null && root.right == null) {
            // condition 1: deleted node has no children
            return null;
        }
        if (root.right == null) {
            // condition 2: deleted node only has left child
            return root.left;
        }
        if (root.left == null) {
            // condition 3: deleted node only has right child
            return root.right;
        }
        if (root.right.left == null) {
            // condition 4.1: the right child of the deleted node only has right child
            root.right.left = root.left;
            return root.right;
        }
        // condition 4.2: the right child of the deleted node has left and right children
        AVLTreeNode[] smallestContainer = new AVLTreeNode[1];
        AVLTreeNode newRoot = deleteSmallest(root, smallestContainer);
        newRoot.left = root.left;
        newRoot.right = root.right;
        keepBalance(newRoot);
        return newRoot;
    }

    private AVLTreeNode deleteSmallest(AVLTreeNode root, AVLTreeNode[] smallestContainer) {
        if (root.left == null) {
            smallestContainer[0] = root;
            return root.right;
        }
        root.left = deleteSmallest(root.left, smallestContainer);
        keepBalance(root);
        return root;
    }

    // query operation

    public String Search(int key) {
        AVLTreeNode node = search(root, key);
        String result = "";
        if (node == null) {
            result = "NULL";
        } else {
            result = String.valueOf(node.key);
        }
        append2OutputFile(result);
        return result;
    }

    private AVLTreeNode search(AVLTreeNode root, int key) {
        if (root == null || root.key == key) {
            return root;
        }
        return root.key > key ? search(root.left, key) : search(root.right, key);
    }

    public String Search(int key1, int key2) {
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
        append2OutputFile(result);
        return result;
    }

    private void search(AVLTreeNode root, int key1, int key2, List<Integer> list) {
        if (root == null) {
            return ;
        }
        if (root.key >= key1) {
            search(root.left, key1, key2, list);
        }
        if (root.key >= key1 && root.key <= key2) {
            list.add(root.key);
        }
        if (root.key <= key2) {
            search(root.right, key1, key2, list);
        }
    }

    private void append2OutputFile(String newline) {
        try (FileWriter writer = new FileWriter(outputFilename, true);) {
            writer.write(newline + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }

}

class AVLTreeNode {
    int key;
    int height;
    AVLTreeNode left;
    AVLTreeNode right;

    public AVLTreeNode(int key) {
        this.key = key;
        height = 1;
    }
}
