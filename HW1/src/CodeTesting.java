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
        int time = 25;
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
                avlTree.Delete(randomNumbers.get(random.nextInt(randomNumbers.size())));
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

    public static void main(String[] args) {
        insertTest();
//        deleteTest();
    }
}
