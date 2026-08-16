import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();
            String a = sc.next();
            String b = sc.next();

            int onesA = 0;
            int onesB = 0;
            int oddA = 0;
            int oddB = 0;

            for (int i = 0; i < n; i++) {
                if (a.charAt(i) == '1') {
                    onesA++;

                    if (i % 2 == 0) {
                        oddA++;
                    }
                }

                if (b.charAt(i) == '1') {
                    onesB++;

                    if (i % 2 == 0) {
                        oddB++;
                    }
                }
            }

            if (onesA == onesB && oddA == oddB) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }

        sc.close();
    }
}