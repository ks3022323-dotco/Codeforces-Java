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
                res = res * 10 + c - '0';
                c = read();
            }

            return res * sign;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {
            int n = fs.nextInt();
            int[] a = new int[n];

            ArrayList<Integer> ones = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                a[i] = fs.nextInt();

                if (a[i] == 1) {
                    ones.add(i);
                }
            }

            int bestL = -1;
            int bestR = -1;

            if (ones.isEmpty()) {
                int first = -1;
                int last = -1;

                for (int i = 0; i < n; i++) {
                    if (a[i] == -1) {
                        if (first == -1) {
                            first = i;
                        }
                        last = i;
                    }
                }

                if (first != -1) {
                    bestL = first;
                    bestR = last;
                }
            } else {
                bestL = ones.get(0);
                bestR = ones.get(0);

                int firstOne = ones.get(0);
                int lastOne = ones.get(ones.size() - 1);

                int firstUnknown = -1;

                for (int i = 0; i < firstOne; i++) {
                    if (a[i] == -1) {
                        firstUnknown = i;
                        break;
                    }
                }

                if (firstUnknown != -1 &&
                        firstOne - firstUnknown > bestR - bestL) {
                    bestL = firstUnknown;
                    bestR = firstOne;
                }

                for (int x = 0; x + 1 < ones.size(); x++) {
                    int left = ones.get(x);
                    int right = ones.get(x + 1);

                    if (right - left > bestR - bestL) {
                        bestL = left;
                        bestR = right;
                    }

                    int firstUnknownBetween = -1;
                    int lastUnknownBetween = -1;

                    for (int i = left + 1; i < right; i++) {
                        if (a[i] == -1) {
                            if (firstUnknownBetween == -1) {
                                firstUnknownBetween = i;
                            }
                            lastUnknownBetween = i;
                        }
                    }

                    if (lastUnknownBetween != -1 &&
                            lastUnknownBetween - left > bestR - bestL) {
                        bestL = left;
                        bestR = lastUnknownBetween;
                    }

                    if (firstUnknownBetween != -1 &&
                            right - firstUnknownBetween > bestR - bestL) {
                        bestL = firstUnknownBetween;
                        bestR = right;
                    }
                }

                int lastUnknown = -1;

                for (int i = n - 1; i > lastOne; i--) {
                    if (a[i] == -1) {
                        lastUnknown = i;
                        break;
                    }
                }

                if (lastUnknown != -1 &&
                        lastUnknown - lastOne > bestR - bestL) {
                    bestL = lastOne;
                    bestR = lastUnknown;
                }
            }

            if (bestL != -1) {
                a[bestL] = 1;
                a[bestR] = 1;

                for (int i = bestL + 1; i < bestR; i++) {
                    a[i] = 0;
                }
            }

            for (int i = 0; i < n; i++) {
                if (a[i] == -1) {
                    a[i] = 0;
                }

                if (i > 0) {
                    out.append(' ');
                }

                out.append(a[i]);
            }

            out.append('\n');
        }

        System.out.print(out);
    }
}