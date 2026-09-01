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

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            int n = fs.nextInt();
            int k = fs.nextInt();

            if (n == 1) {
                if (k == 1) {
                    out.append("YES\n");
                    out.append("0\n");
                } else {
                    out.append("NO\n");
                }
                continue;
            }

            k ^= n;

            if (Integer.SIZE - Integer.numberOfLeadingZeros(k)
                    > Integer.SIZE - Integer.numberOfLeadingZeros(n - 1)) {
                out.append("NO\n");
                continue;
            }

            boolean[] used = new boolean[n];
            ArrayList<Integer> special = new ArrayList<>();

            if (k > 0 && k <= n - 1) {
                special.add(k);
                used[k] = true;
            } else if (k != 0) {
                special.add(n - 1);
                used[n - 1] = true;

                int x = (n - 1) ^ k;
                special.add(x);
                used[x] = true;
            }

            special.add(0);
            used[0] = true;

            ArrayList<Integer> ans = new ArrayList<>(n);

            for (int x : special) {
                ans.add(x);
            }

            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    ans.add(i);
                }
            }

            out.append("YES\n");

            for (int i = n - 1; i >= 0; i--) {
                out.append(ans.get(i));
                if (i > 0) {
                    out.append(' ');
                }
            }
            out.append('\n');
        }

        System.out.print(out);
    }
}