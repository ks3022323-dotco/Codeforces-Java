import java.io.*;
import java.util.*;

public class Main {

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

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

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            long sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }

            long res = 0;

            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }

            return res * sign;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    static long n;
    static long d;
    static int m;

    static long[] pos;
    static long[] val;

    static long getS(long x) {
        long blocks = x / n;
        long rem = x % n;

        int lo = 0;
        int hi = 2 * m;

        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;

            if (pos[mid] <= rem) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }

        long reward = val[lo];

        long base = n * d + val[2 * m];

        return blocks * base + rem * d + reward;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            n = fs.nextLong();
            m = fs.nextInt();
            d = fs.nextLong();

            if (m == 0) {
                out.append("NO\n");
                continue;
            }

            long[] p = new long[m + 1];
            long[] r = new long[m + 1];

            for (int i = 1; i <= m; i++) {
                p[i] = fs.nextLong();
                r[i] = fs.nextLong();
            }

            pos = new long[2 * m + 1];
            val = new long[2 * m + 1];

            for (int i = 1; i <= m; i++) {
                pos[i] = p[i];
                val[i] = val[i - 1] + r[i];
            }

            for (int i = 1; i <= m; i++) {
                pos[m + i] = n + p[i];
                val[m + i] = val[m] + val[i];
            }

            boolean ok = false;

            for (int i = 1; i <= m && !ok; i++) {
                int k = i;

                for (int j = 1; j <= m; j++) {
                    long target = p[i] + p[j] + 1;

                    while (k + 1 <= 2 * m && pos[k + 1] <= target) {
                        k++;
                    }

                    long left =
                            val[i] + p[i] * d
                            + val[j] + p[j] * d;

                    long right =
                            val[k] + target * d;

                    if (left > right) {
                        ok = true;
                        break;
                    }
                }
            }

            out.append(ok ? "YES\n" : "NO\n");
        }

        System.out.print(out);
    }
}