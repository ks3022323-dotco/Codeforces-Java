import java.io.*;
import java.util.*;

public class Main {

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len == -1) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            long res = 0;
            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return res;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    static class SegmentTree {
        int size;
        long[] sum;
        int[] count;
        long[] values;

        SegmentTree(long[] values) {
            this.values = values;

            size = 1;
            while (size < values.length) {
                size <<= 1;
            }

            sum = new long[size << 1];
            count = new int[size << 1];
        }

        void add(int pos, long value) {
            int p = pos + size;

            count[p]++;
            sum[p] += value;

            p >>= 1;

            while (p > 0) {
                count[p] = count[p << 1] + count[p << 1 | 1];
                sum[p] = sum[p << 1] + sum[p << 1 | 1];
                p >>= 1;
            }
        }

        /*
         * Returns the minimum number of largest elements whose
         * sum is at least target.
         *
         * Assumes total sum >= target.
         */
        int minElementsForSum(long target) {
            int node = 1;
            int left = 0;
            int right = size - 1;

            int answer = 0;

            while (left != right) {

                int mid = (left + right) >>> 1;

                int rightNode = node << 1 | 1;

                // Larger values are stored on the right side.
                if (sum[rightNode] >= target) {
                    node = rightNode;
                    left = mid + 1;
                } else {
                    target -= sum[rightNode];
                    answer += count[rightNode];

                    node = node << 1;
                    right = mid;
                }
            }

            // We are at one value.
            long value = values[left];

            long needed = (target + value - 1) / value;

            answer += (int) needed;

            return answer;
        }

        long totalSum() {
            return sum[1];
        }
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {

            int n = fs.nextInt();
            int m = fs.nextInt();

            long[] v = new long[n];

            for (int i = 0; i < n; i++) {
                v[i] = fs.nextLong();
            }

            /*
             * Store all matrix values.
             *
             * We need them for:
             * 1. coordinate compression
             * 2. processing rows from bottom to top
             */
            long[][] a = new long[n][m];

            int total = n * m;
            long[] all = new long[total];

            int ptr = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    a[i][j] = fs.nextLong();
                    all[ptr++] = a[i][j];
                }
            }

            // Coordinate compression.
            Arrays.sort(all);

            int unique = 0;

            for (int i = 0; i < total; i++) {
                if (i == 0 || all[i] != all[i - 1]) {
                    all[unique++] = all[i];
                }
            }

            long[] values = Arrays.copyOf(all, unique);

            SegmentTree st = new SegmentTree(values);

            long answer = m;

            /*
             * Process rows from bottom to top.
             *
             * After adding row i, the segment tree contains all
             * destabilization factors from rows i..n.
             */
            for (int i = n - 1; i >= 0; i--) {

                for (int j = 0; j < m; j++) {

                    long x = a[i][j];

                    int pos = Arrays.binarySearch(values, x);

                    st.add(pos, x);
                }

                /*
                 * If the total possible damage to level i is
                 * smaller than v[i], this level cannot collapse
                 * because of stability.
                 */
                if (st.totalSum() >= v[i]) {

                    int needed = st.minElementsForSum(v[i]);

                    answer = Math.min(answer, (long) needed);
                }
                
            }

            out.append(answer).append('\n');
        }

        System.out.print(out);
    }
}
