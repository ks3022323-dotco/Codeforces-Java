import java.io.*;

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

            long result = 0;

            while (c > ' ') {
                result = result * 10 + c - '0';
                c = read();
            }

            return result;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            int n = fs.nextInt();

            long L = 0;
            long R = Long.MAX_VALUE;

            for (int i = 1; i <= n; i++) {
                long w = fs.nextLong();

                if (i % 2 == 0) {
                    L = Math.max(L, w);
                } else {
                    R = Math.min(R, w);
                }
            }

            if (n % 2 == 0 && L + 2 <= R) {
                out.append("YES\n");
            } else {
                out.append("NO\n");
            }
        }

        System.out.print(out);
    }
}