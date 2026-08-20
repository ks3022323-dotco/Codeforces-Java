import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {
            int n = fs.nextInt();
            int k = fs.nextInt();
            long m = fs.nextLong();

            if (k > m) {
                out.append("NO\n");
                continue;
            }

            out.append("YES\n");

            long special = m - k + 1;

            for (int i = 1; i <= n; i++) {
                if (i > 1) {
                    out.append(' ');
                }

                if (i % k == 0) {
                    out.append(special);
                } else {
                    out.append(1);
                }
            }

            out.append('\n');
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

                if (len == -1) {
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

            long value = 0;

            while (c > ' ') {
                value = value * 10 + (c - '0');
                c = read();
            }

            return value * sign;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}