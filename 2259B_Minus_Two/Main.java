import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();

            int odd = 0;
            int divisibleBy4 = 0;
            int remainder2 = 0;

            for (int i = 0; i < n; i++) {
                long x = sc.nextLong();

                if (x % 2 == 1) {
                    odd++;
                } else if (x % 4 == 0) {
                    divisibleBy4++;
                } else {
                    remainder2++;
                }
            }

            System.out.println(Math.max(odd, Math.max(divisibleBy4, remainder2)));
        }

        sc.close();
    }
}
