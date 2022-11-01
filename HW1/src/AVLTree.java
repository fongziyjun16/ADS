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
        return root;
    }

    public void Delete(int key) {

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
    }
}
