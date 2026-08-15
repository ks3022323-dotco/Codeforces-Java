import java.io.*;
import java.util.*;

public class Main {
    static final long MOD = 998244353L;
    static final int MAX = 1000000;

    static long[] fact = new long[MAX + 1];
    static long[] invFact = new long[MAX + 1];

    static long power(long a, long b) {
        long result = 1;

        while (b > 0) {
            if ((b & 1) == 1) {
                result = result * a % MOD;
            }

            a = a * a % MOD;
            b >>= 1;
        }

        return result;
    }

    static long combination(int n, int r) {
        if (r < 0 || r > n) {
            return 0;
        }

        return fact[n] * invFact[r] % MOD * invFact[n - r] % MOD;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        fact[0] = 1;

        for (int i = 1; i <= MAX; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }

        invFact[MAX] = power(fact[MAX], MOD - 2);

        for (int i = MAX; i >= 1; i--) {
            invFact[i - 1] = invFact[i] * i % MOD;
        }

        int TestsNumT = Integer.parseInt(br.readLine());

        StringBuilder out = new StringBuilder();

        while (TestsNumT-- > 0) {
            int n = Integer.parseInt(br.readLine());
            String s = br.readLine();

            int zeros = 0;
            int ones = 0;
            int zeroRuns = 0;
            int oneRuns = 0;

            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '0') {
                    zeros++;
                } else {
                    ones++;
                }

                if (i == 0 || s.charAt(i) != s.charAt(i - 1)) {
                    if (s.charAt(i) == '0') {
                        zeroRuns++;
                    } else {
                        oneRuns++;
                    }
                }
            }

            if (zeroRuns == 0 || oneRuns == 0) {
                out.append(1).append('\n');
            } else {
                long waysZero = combination(zeros - 1, zeroRuns - 1);
                long waysOne = combination(ones - 1, oneRuns - 1);

                long answer = waysZero * waysOne % MOD;

                out.append(answer).append('\n');
            }
        }

        System.out.print(out);
    }
}