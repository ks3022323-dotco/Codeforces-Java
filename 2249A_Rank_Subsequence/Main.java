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

    static int n;
    static int[] l;
    static int[] r;
    static int[] u;
    static int[] v;

    /*
     * Checks whether a valid subsequence of exactly length m
     * can be constructed.
     *
     * We greedily choose the earliest possible element for
     * every position of the subsequence.
     */
    static boolean canMake(int m) {

        int position = 0;

        for (int i = 0; i < n; i++) {

            // The next selected element will have left rank position + 1.
            int leftRank = position + 1;

            // Its right rank depends on the final length m.
            int rightRank = m - position;

            /*
             * The element is valid if:
             *
             * leftRank is NOT inside [l[i], r[i]]
             * AND
             * rightRank is NOT inside [u[i], v[i]]
             */
            boolean leftOkay =
                    leftRank < l[i] || leftRank > r[i];

            boolean rightOkay =
                    rightRank < u[i] || rightRank > v[i];

            if (leftOkay && rightOkay) {
                position++;

                // We have already selected all m elements.
                if (position == m) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();

        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {

            n = fs.nextInt();

            l = new int[n];
            r = new int[n];
            u = new int[n];
            v = new int[n];

            for (int i = 0; i < n; i++) {
                l[i] = fs.nextInt();
                r[i] = fs.nextInt();
                u[i] = fs.nextInt();
                v[i] = fs.nextInt();
            }

            int answer = 0;

            /*
             * Try the largest possible length first.
             *
             * For each fixed m, canMake(m) is O(n).
             *
             * Therefore total complexity is O(n^2).
             */
            for (int m = n; m >= 1; m--) {

                if (canMake(m)) {
                    answer = m;
                    break;
                }
            }

            out.append(answer).append('\n');
        }

        System.out.print(out);
    }
}