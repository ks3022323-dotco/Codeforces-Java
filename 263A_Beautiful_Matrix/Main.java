import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int row = 0;
        int col = 0;

        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {

                int x = sc.nextInt();

                if (x == 1) {
                    row = i;
                    col = j;
                }
            }
        }

        System.out.println(Math.abs(row - 3) + Math.abs(col - 3));
    }
}
