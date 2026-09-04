import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();

            if (n == 1) {
                System.out.println(1);
            } else if (n == 2) {
                System.out.println(-1);
            } else {
                long[] a = new long[n];

                a[0] = 1;
                a[1] = 2;
                a[2] = 3;

                for (int i = 3; i < n; i++) {
                    a[i] = a[i - 1] * 2;
                }

                for (int i = 0; i < n; i++) {
                    System.out.print(a[i]);
                    if (i + 1 < n) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }
}