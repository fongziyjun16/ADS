import tree.AVLTree;
import tree.AVLTreeNode;

public class AVLTreeUtils {

    public static int getBalanceFactor(AVLTreeNode root) {
        // the height of left subtree - the height of right subtree
        return getHeight(root.left) - getHeight(root.right);
    }

    public static int getHeight(AVLTreeNode root) {
        return root == null ? 0 : root.height;
    }

    public static boolean isValid(AVLTree avlTree) {
        AVLTreeNode root = avlTree.getRoot();
        return isBST(avlTree.getRoot(), (long) Integer.MIN_VALUE - 1, (long) Integer.MAX_VALUE + 1) && isBalance(root);
    }

    private static boolean isBST(AVLTreeNode root, long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.key <= min || root.key >= max) {
            return false;
        }
        return isBST(root.left, min, root.key) && isBST(root.right, root.key, max);
    }

    private static boolean isBalance(AVLTreeNode root) {
        if (root == null) {
            return true;
        }
        if (Math.abs(getBalanceFactor(root)) > 1) {
            return false;
        }
        return isBalance(root.left) && isBalance(root.right);
    }

}
