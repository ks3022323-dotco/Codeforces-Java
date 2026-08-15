import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int TestsNumT = sc.nextInt();

        while (TestsNumT-- > 0) {
            int n = sc.nextInt();
            String s = sc.next();

            int answer = 0;

            for (int first = 0; first <= 1; first++) {
                for (int second = 0; second <= 1; second++) {
                    boolean valid = true;

                    int[] candidate = new int[n];

                    candidate[0] = first;
                    candidate[1] = second;

                    for (int i = 2; i < n; i++) {
                        candidate[i] = 1 - candidate[i - 2];
                    }

                    for (int i = 0; i < n; i++) {
                        if (s.charAt(i) != '?' &&
                            s.charAt(i) - '0' != candidate[i]) {
                            valid = false;
                            break;
                        }
                    }

                    if (valid) {
                        answer++;
                    }
                }
            }

            System.out.println(answer);
        }

        sc.close();
    }
}
