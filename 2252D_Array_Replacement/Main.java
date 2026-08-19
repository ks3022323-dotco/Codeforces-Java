import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {
            int n = fs.nextInt();

            long[] a = new long[n];

            for (int i = 0; i < n; i++) {
                a[i] = fs.nextLong();
            }

            long[] d = new long[n - 1];

            for (int i = 0; i < n - 1; i++) {
                d[i] = a[i + 1] - a[i];
            }

            int start = 0;

            while (start < n - 1) {
                int end = start;

                while (end + 1 < n - 1 &&
                       ((d[end] & 1) == (d[end + 1] & 1))) {
                    end++;
                }

                Arrays.sort(d, start, end + 1);
                start = end + 1;
            }

            long current = a[0];

            out.append(current);

            for (int i = 0; i < n - 1; i++) {
                current += d[i];
                out.append(' ').append(current);
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

            boolean negative = false;

            if (c == '-') {
                negative = true;
                c = read();
            }

            long result = 0;

            while (c > ' ') {
                result = result * 10 + (c - '0');
                c = read();
            }

            return negative ? -result : result;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}