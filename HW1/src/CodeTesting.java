public class CodeTesting {
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        avlTree.Initialize();
        avlTree.Insert(50);
        avlTree.Insert(90);
        avlTree.Insert(70);
        avlTree.Insert(60);
        avlTree.Insert(40);
        avlTree.Insert(55);

        System.out.println(avlTree.IsAVLTree());
    }
}
