import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String s = sc.next();

        String[] numbers = s.split("\\+");

        Arrays.sort(numbers);

        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i]);

            if (i != numbers.length - 1) {
                System.out.print("+");
            }
        }
    }
}
