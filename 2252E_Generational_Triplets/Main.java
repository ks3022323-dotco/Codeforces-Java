import java.io.*;
import java.util.*;

public class Main {

    static final long MOD = 1_000_000_007L;

    static long n;
    static long[][][][] dp;

    static long solveDP(int bit, int cLess, int aLess, int need) {

        // All bits processed
        if (bit < 0) {
            // need must be 0
            // and a must be strictly smaller than c
            return (need == 0 && aLess == 1) ? 1 : 0;
        }

        if (dp[bit][cLess][aLess][need] != -1) {
            return dp[bit][cLess][aLess][need];
        }

        long ans = 0;

        int nBit = (int) ((n >> bit) & 1);

        // Try all possibilities for a_bit and c_bit
        for (int aBit = 0; aBit <= 1; aBit++) {

            for (int cBit = 0; cBit <= 1; cBit++) {

                // If c is still equal to n's prefix,
                // cBit cannot be greater than nBit.
                if (cLess == 0 && cBit > nBit) {
                    continue;
                }

                // If a and c prefixes are equal,
                // aBit cannot be greater than cBit.
                if (aLess == 0 && aBit > cBit) {
                    continue;
                }

                // Current AND bit must equal need
                if ((aBit & cBit) != need) {
                    continue;
                }

                // Update whether c is smaller than n
                int nextCLess = cLess;

                if (cLess == 0 && cBit < nBit) {
                    nextCLess = 1;
                }

                // Update whether a is smaller than c
                int nextALess = aLess;

                if (aBit < cBit) {
                    nextALess = 1;
                }

                // XOR of current bits becomes
                // required AND bit for the next lower bit
                int nextNeed = aBit ^ cBit;

                ans += solveDP(
                        bit - 1,
                        nextCLess,
                        nextALess,
                        nextNeed
                );

                ans %= MOD;
            }
        }

        return dp[bit][cLess][aLess][need] = ans;
    }

    static void solve() throws Exception {

        n = Long.parseLong(br.readLine().trim());

        dp = new long[61][2][2][2];

        for (int bit = 0; bit <= 60; bit++) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    Arrays.fill(dp[bit][i][j], -1);
                }
            }
        }

        /*
         * bit = 60
         *
         * cLess = 0
         *   c is initially equal to n
         *
         * aLess = 0
         *   a and c prefixes are initially equal
         *
         * need = 0
         */
        System.out.println(solveDP(60, 0, 0, 0));
    }

    static BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {

        int t = Integer.parseInt(br.readLine().trim());

        while (t-- > 0) {
            solve();
        }
    }
}