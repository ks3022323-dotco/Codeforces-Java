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
                if (len <= 0) return -1;
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

    static class Run {
        int color;
        int length;

        Run(int color, int length) {
            this.color = color;
            this.length = length;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            int n = fs.nextInt();

            ArrayList<Run> runs = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int x = fs.nextInt();

                if (runs.isEmpty() || runs.get(runs.size() - 1).color != x) {
                    runs.add(new Run(x, 1));
                } else {
                    runs.get(runs.size() - 1).length++;
                }
            }

            int m = runs.size();

            for (int i = 0; i + 1 < m; i++) {
                if (runs.get(i).length > 1 && runs.get(i + 1).length > 1) {
                    out.append(m + 2).append('\n');
                    m = -1;
                    break;
                }
            }

            if (m == -1) {
                continue;
            }

            boolean bonusOne = false;

            for (int i = 0; i < runs.size(); i++) {
                if (runs.get(i).length <= 1) {
                    continue;
                }

                if (i < runs.size() - 1) {
                    if (i + 2 >= runs.size()
                            || runs.get(i + 2).color != runs.get(i).color) {
                        bonusOne = true;
                        break;
                    }
                }

                if (i > 0) {
                    if (i - 2 < 0
                            || runs.get(i - 2).color != runs.get(i).color) {
                        bonusOne = true;
                        break;
                    }
                }
            }

            out.append(m + (bonusOne ? 1 : 0)).append('\n');
        }

        System.out.print(out);
    }
}