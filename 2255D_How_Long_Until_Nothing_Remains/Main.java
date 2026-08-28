import java.io.*;
import java.util.*;

public class Main {

    static final long MOD_POWER = 1L << 30;

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

    /*
     * Check whether all elements can become zero
     * in exactly T operations.
     */
    static boolean possible(long[] a, int T) {

        int n = a.length;

        /*
         * We have powers:
         *
         * 1, 2, 4, ..., 2^(T-1)
         *
         * Every ai <= 1e9 < 2^30.
         *
         * Therefore powers >= 2^30 can each finish
         * any remaining element immediately.
         */

        int largePowers = Math.max(0, T - 30);

        /*
         * If there are at least n powers >= 2^30,
         * every element can be handled by one such power.
         */
        if (largePowers >= n) {
            return true;
        }

        /*
         * Sort the values.
         */
        long[] b = a.clone();
        Arrays.sort(b);

        /*
         * The largest 'largePowers' elements can be assigned
         * to the huge powers.
         *
         * We only need to solve for the remaining smaller ones.
         */
        int remaining = n - largePowers;

        /*
         * Max heap containing the remaining demands.
         */
        PriorityQueue<Long> pq =
                new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < remaining; i++) {
            pq.add(b[i]);
        }

        /*
         * Process the 30 small powers:
         *
         * 2^29, 2^28, ..., 2^0
         *
         * If T < 30, only use powers that actually exist.
         */
        int smallPowers = Math.min(T, 30);

        for (int bit = smallPowers - 1; bit >= 0; bit--) {

            if (pq.isEmpty()) {
                return true;
            }

            long power = 1L << bit;

            long x = pq.poll();

            /*
             * If x > 2 * power, even all smaller powers
             * together cannot cover the remaining amount.
             */
            if (x > 2L * power) {
                return false;
            }

            /*
             * If power >= x, this power completely satisfies x.
             */
            if (x <= power) {
                continue;
            }

            /*
             * power < x <= 2*power
             *
             * Give this power to x and put the remainder back.
             */
            x -= power;

            if (x > 0) {
                pq.add(x);
            }
        }

        return pq.isEmpty();
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();

        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {

            int n = fs.nextInt();

            long[] a = new long[n];

            for (int i = 0; i < n; i++) {
                a[i] = fs.nextInt();
            }

            /*
             * Every positive element must be selected at least once.
             * Therefore answer >= n.
             *
             * Since ai < 2^30, n + 30 operations are always enough.
             */
            int low = n;
            int high = n + 30;

            /*
             * Binary search for minimum T.
             */
            while (low < high) {

                int mid = low + (high - low) / 2;

                if (possible(a, mid)) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

            out.append(low).append('\n');
        }

        System.out.print(out);
    }
}