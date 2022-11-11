import java.util.*;

public class CodeTesting {

    public static int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    public static List<Integer> getRandomNumbers(int min, int max, int number) {
        Set<Integer> set = new HashSet<>();
        while (set.size() != number) {
            set.add(getRandomNumber(min, max));
        }
        Random random = new Random();
        List<Integer> randomNumbers = new ArrayList<>(set);
        for (int i = 0; i < number; i++) {
            int next = random.nextInt(number);
            int temp = randomNumbers.get(i);
            randomNumbers.set(i, randomNumbers.get(next));
            randomNumbers.set(next, temp);
        }
        return randomNumbers;
    }

    public static void insertTest() {
        int time = 1000;
        int curr = 1;
        int success = 0;
        int error = 0;
        while (curr <= time) {
            List<Integer> randomNumbers = getRandomNumbers(1, 4000, 3600);
            AVLTree avlTree = new AVLTree();
            avlTree.Initialize();
            for (Integer number : randomNumbers) {
                avlTree.Insert(number);
                if (!avlTree.IsValid()) {
                    error++;
                    break;
                }
            }
            success++;

            curr++;
            if (curr % 50 == 0) {
                System.out.println(curr);
            }
        }
        System.out.println("success: " + success + "; error: " + error);
    }

    public static void deleteTest() {
        int time = 1000;
        int curr = 1;
        int error = 0;
        while (curr <= time) {
            List<Integer> randomNumbers = getRandomNumbers(1, 4000, 3600);
            AVLTree avlTree = new AVLTree();
            avlTree.Initialize();
            for (Integer number : randomNumbers) {
                avlTree.Insert(number);
            }

            Random random = new Random();
            int deletingNumber = random.nextInt(randomNumbers.size());
            while (deletingNumber != 0) {
                int deletedElement = randomNumbers.get(random.nextInt(randomNumbers.size()));
                avlTree.Delete(deletedElement);
                if (!avlTree.IsValid()) {
                    error++;
                    break;
                }
                deletingNumber--;
            }

            if (curr % 5 == 0) {
                System.out.println(" finishes " + curr);
            }
            curr++;
        }
        System.out.println( " error: " + error);
    }

    public static void test() {
        AVLTree avlTree = new AVLTree();
        avlTree.Initialize();
        avlTree.Insert(21);
        avlTree.Insert(108);
        avlTree.Insert(5);
        avlTree.Insert(1897);
        avlTree.Insert(4325);
        avlTree.Delete(108);
        avlTree.Search(1897);
        avlTree.Insert(102);
        avlTree.Insert(65);
        avlTree.Delete(102);
        avlTree.Delete(21);
        avlTree.Insert(106);
        avlTree.Insert(23);
        avlTree.Search(23,99);
        avlTree.Insert(32);
        avlTree.Insert(220);
        avlTree.Search(33);
        avlTree.Search(21);
        avlTree.Delete(4325);
        avlTree.Search(32);
    }

    public static void main(String[] args) {
//        insertTest();
//        deleteTest();
        test();
    }
}
