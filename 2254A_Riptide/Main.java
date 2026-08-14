import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            if (a == b || b == c || a == c) {
                System.out.println(0);
                continue;
            }

            int min = Math.min(a, Math.min(b, c));
            int max = Math.max(a, Math.max(b, c));

            int middle = a + b + c - min - max;

            int answer = Math.min(middle - min, max - middle);

            System.out.println(answer);
        }

        sc.close();
    }
}
