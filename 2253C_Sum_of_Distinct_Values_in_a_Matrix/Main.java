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

            int num = 0;
            while (c > ' ') {
                num = num * 10 + (c - '0');
                c = read();
            }
            return num * sign;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            int n = fs.nextInt();
            int m = fs.nextInt();
            int x = fs.nextInt();
            int y = fs.nextInt();

            int limit = n + m;

            boolean[] inA = new boolean[limit + 1];
            boolean[] inB = new boolean[limit + 1];

            for (int i = 0; i < x; i++) {
                inA[fs.nextInt()] = true;
            }

            for (int i = 0; i < y; i++) {
                inB[fs.nextInt()] = true;
            }

            ArrayList<Integer> onlyA = new ArrayList<>();
            ArrayList<Integer> onlyB = new ArrayList<>();
            ArrayList<Integer> both = new ArrayList<>();

            for (int v = 1; v <= limit; v++) {
                if (inA[v] && inB[v]) {
                    both.add(v);
                } else if (inA[v]) {
                    onlyA.add(v);
                } else if (inB[v]) {
                    onlyB.add(v);
                }
            }

            Collections.sort(onlyA, Collections.reverseOrder());
            Collections.sort(onlyB, Collections.reverseOrder());
            Collections.sort(both, Collections.reverseOrder());

            int takeA = Math.min(n, onlyA.size());
            int takeB = Math.min(m, onlyB.size());

            ArrayList<Integer> remaining = new ArrayList<>();

            for (int i = 0; i < takeA; i++) {
                remaining.add(onlyA.get(i));
            }

            for (int i = 0; i < takeB; i++) {
                remaining.add(onlyB.get(i));
            }

            Collections.sort(remaining, Collections.reverseOrder());

            long[] prefixBoth = new long[both.size() + 1];
            for (int i = 0; i < both.size(); i++) {
                prefixBoth[i + 1] = prefixBoth[i] + both.get(i);
            }

            long[] prefixRemaining = new long[remaining.size() + 1];
            for (int i = 0; i < remaining.size(); i++) {
                prefixRemaining[i + 1] =
                        prefixRemaining[i] + remaining.get(i);
            }

            long answer = 0;

            for (int shared = 0; shared <= both.size(); shared++) {
                int capacity = n + m - 1 - shared;

                if (capacity < 0) {
                    continue;
                }

                int take = Math.min(capacity, remaining.size());

                long current =
                        prefixBoth[shared] + prefixRemaining[take];

                answer = Math.max(answer, current);
            }

            out.append(answer).append('\n');
        }

        System.out.print(out);
    }
}