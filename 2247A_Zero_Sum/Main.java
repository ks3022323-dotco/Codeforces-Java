import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();

            for (int i = 0; i < n; i++) {
                sc.nextInt();
            }

            System.out.println(n % 2 == 0 ? "YES" : "NO");
        }

        sc.close();
    }
}
