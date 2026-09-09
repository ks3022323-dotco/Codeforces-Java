import java.io.*;
import java.util.*;

public class Main {
    static final long MOD = 998244353L;
    static int n;
    static int[] a;
    static long[][] comb;
    static long[][] g;
    static long[][][] f;

    static long F(int l, int r, int c) {
        if (l == r + 1) {
            return c == 0 ? 1 : 0;
        }
        return f[l][r][c];
    }

    static long G(int l, int r) {
        if (l == r + 1) {
            return 1;
        }
        return g[l][r];
    }

    static long W(int i, int k) {
        if (i == k) {
            return a[k] <= 0 ? 1 : 0;
        }

        if (a[k] != -1) {
            return f[i][k - 1][a[k]];
        }

        return g[i][k - 1];
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {
            n = fs.nextInt();
            a = new int[n + 1];

            long sum = 0;

            for (int i = 0; i < n; i++) {
                a[i] = fs.nextInt();

                if (a[i] > 0) {
                    sum += a[i];
                }
            }

            if (sum >= n) {
                out.append(0).append('\n');
                continue;
            }

            a[n] = -1;

            comb = new long[n + 1][n + 1];

            for (int i = 0; i <= n; i++) {
                comb[i][0] = 1;
                comb[i][i] = 1;

                for (int j = 1; j < i; j++) {
                    comb[i][j] =
                            (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
                }
            }

            g = new long[n + 1][n];
            f = new long[n + 1][n][];

            for (int r = 0; r < n; r++) {
                if (a[r + 1] != -1) {
                    for (int l = 0; l <= r; l++) {
                        f[l][r] = new long[a[r + 1] + 1];
                    }
                }
            }

            for (int i = 1; i <= n; i++) {
                f[i][i - 1] = new long[]{1};
                g[i][i - 1] = 1;
            }

            for (int l = n - 1; l >= 0; l--) {
                for (int r = l; r < n; r++) {

                    if (a[r + 1] == -1) {

                        for (int k = l; k <= r; k++) {
                            long ways = comb[r - l][k - l];

                            ways = ways * W(l, k) % MOD;
                            ways = ways * G(k + 1, r) % MOD;

                            g[l][r] += ways;

                            if (g[l][r] >= MOD) {
                                g[l][r] -= MOD;
                            }
                        }

                    } else {

                        for (int c = 1; c <= a[r + 1]; c++) {

                            for (int k = l; k <= r; k++) {

                                long ways = comb[r - l][k - l];

                                ways = ways * W(l, k) % MOD;
                                ways = ways * F(k + 1, r, c - 1) % MOD;

                                f[l][r][c] += ways;

                                if (f[l][r][c] >= MOD) {
                                    f[l][r][c] -= MOD;
                                }
                            }
                        }
                    }
                }
            }

            out.append(W(0, n)).append('\n');
        }

        System.out.print(out);
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;

                if (len <= 0) {
                    return -1;
                }
            }

            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }

            int res = 0;

            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }

            return res * sign;
        }
    }
}