import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] lucky = {
                4, 7,
                44, 47, 74, 77,
                444, 447, 474, 477,
                744, 747, 774, 777
        };

        boolean found = false;

        for (int num : lucky) {
            if (n % num == 0) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        sc.close();
    }
}
