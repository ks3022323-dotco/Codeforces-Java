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

                if (len <= 0) {
                    return -1;
                }
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;

            do {
                c = read();
            } while (c <= ' ');

            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }

            return sb.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {

        FastScanner sc = new FastScanner();
        StringBuilder output = new StringBuilder();

        int t = sc.nextInt();

        while (t-- > 0) {

            int n = sc.nextInt();
            String s = sc.next();

            int groups = 1;

            // Count groups in the original string
            for (int i = 1; i < n; i++) {
                if (s.charAt(i) != s.charAt(i - 1)) {
                    groups++;
                }
            }

            int answer = groups;

            // Try deleting every character except first and last
            for (int i = 1; i < n - 1; i++) {

                int current = groups;

                if (s.charAt(i) != s.charAt(i - 1)) {
                    current--;
                }

                if (s.charAt(i) != s.charAt(i + 1)) {
                    current--;
                }

                if (s.charAt(i - 1) != s.charAt(i + 1)) {
                    current++;
                }

                answer = Math.min(answer, current);
            }

            output.append(answer).append('\n');
        }

        System.out.print(output);
    }
}
