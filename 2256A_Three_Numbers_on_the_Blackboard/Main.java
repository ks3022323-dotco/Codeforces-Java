import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            long a = sc.nextLong();
            long b = sc.nextLong();
            long c = sc.nextLong();

            long[] arr = {a, b, c};
            Arrays.sort(arr);

            long x = arr[0];
            long y = arr[1];
            long z = arr[2];

            long answer = Math.min(z - x, y);

            System.out.println(answer);
        }

        sc.close();
    }
}