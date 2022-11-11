import tree.AVLTree;

import java.io.*;

public class avltree {
    public static void main(String[] args) throws IOException {
        AVLTree avlTree = new AVLTree();
        String filename = args[0];
        FileInputStream inputStream = new FileInputStream(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            int p1 = line.indexOf('(');
            String command = line.substring(0, p1);
            if (command.equals("Initialize")) {
                avlTree.initialize();
            }else if (command.equals("Search")) {
                int p2 = line.indexOf(',');
                if (p2 == -1) {
                    int param = Integer.parseInt(line.substring(p1 + 1, line.length() - 1));
                    avlTree.search(param);
                } else {
                    int param1 = Integer.parseInt(line.substring(p1 + 1, p2));
                    int param2 = Integer.parseInt(line.substring(p2 + 1, line.length() - 1));
                    avlTree.search(param1, param2);
                }
            } else { // Insert or Delete
                int param = Integer.parseInt(line.substring(p1 + 1, line.length() - 1));
                if (command.equals("Insert")) {
                    avlTree.insert(param);
                } else {
                    avlTree.delete(param);
                }
            }
        }
        inputStream.close();
        reader.close();

    }
}
