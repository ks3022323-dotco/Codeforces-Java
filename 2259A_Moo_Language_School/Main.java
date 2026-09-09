import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();
            int k = sc.nextInt();
            String s = sc.next();

            int ans = 0;

            for (int i = 0; i < n; i += k) {
                boolean hasZero = false;

                for (int j = i; j < i + k; j++) {
                    if (s.charAt(j) == '0') {
                        hasZero = true;
                        break;
                    }
                }

                if (!hasZero) {
                    ans++;
                }
            }

            System.out.println(ans);
        }

        sc.close();
    }
}