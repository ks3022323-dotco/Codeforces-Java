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
                if (len <= 0) return -1;
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

    static long ceilDiv(long a, long b) {
        return -Math.floorDiv(-a, b);
    }

    static long solveLine(long[] values, long endpoint, long sum, int k) {
        int len = values.length + 1;

        if (k == 1) {
            return Math.max(0L, ceilDiv(sum - 2L * endpoint, len - 1L));
        }

        Arrays.sort(values);

        long vk = values[values.length - (k - 1)];

        long z1 = ceilDiv(sum - endpoint - vk, len - 2L);
        long z2 = ceilDiv(sum - 2L * endpoint, len - 1L);

        long z = Math.max(z1, z2);
        z = Math.max(z, 0L);

        long low = Math.max(
                0L,
                sum - 2L * endpoint - (len - 2L) * z
        );

        long high = Math.min(
                z,
                (len - 2L) * z - sum + 2L * vk
        );

        if (low <= high) {
            return z;
        }

        return Long.MAX_VALUE / 4;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            int n = fs.nextInt();
            int m = fs.nextInt();
            int k = fs.nextInt();

            long[][] a = new long[n][m];
            long[] rowSum = new long[n];
            long[] colSum = new long[m];
            long[] all = new long[n * m];

            long total = 0L;
            int pos = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    long x = fs.nextLong();
                    a[i][j] = x;
                    rowSum[i] += x;
                    colSum[j] += x;
                    total += x;
                    all[pos++] = x;
                }
            }

            if (n + m <= 3) {
                Arrays.sort(all);

                if (k == 1) {
                    if (n + m == 2 && all[0] < 0) {
                        out.append("-1\n");
                    } else {
                        out.append("0\n");
                    }
                } else {
                    out.append(Math.abs(all[0] - all[1])).append('\n');
                }

                continue;
            }

            long[] need = new long[n * m];
            pos = 0;

            long denominator = (long) n + m - 3;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    long deficit =
                            rowSum[i]
                            + colSum[j]
                            - 3L * a[i][j];

                    need[pos++] = Math.max(
                            0L,
                            ceilDiv(deficit, denominator)
                    );
                }
            }

            Arrays.sort(need);

            long answer = need[k - 1];

            if (n == 1 || m == 1) {
                long[] line = new long[n * m];

                for (int i = 0; i < n * m; i++) {
                    line[i] = all[i];
                }

                long leftEndpoint = line[0];
                long rightEndpoint = line[line.length - 1];

                long[] withoutLeft = new long[line.length - 1];
                long[] withoutRight = new long[line.length - 1];

                for (int i = 1; i < line.length; i++) {
                    withoutLeft[i - 1] = line[i];
                }

                for (int i = 0; i < line.length - 1; i++) {
                    withoutRight[i] = line[i];
                }

                long candidateLeft =
                        solveLine(
                                withoutLeft,
                                leftEndpoint,
                                total,
                                k
                        );

                long candidateRight =
                        solveLine(
                                withoutRight,
                                rightEndpoint,
                                total,
                                k
                        );

                answer = Math.min(answer, candidateLeft);
                answer = Math.min(answer, candidateRight);
            }

            out.append(answer).append('\n');
        }

        System.out.print(out);
    }
}