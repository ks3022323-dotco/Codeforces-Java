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

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            long res = 0;

            while (c > ' ') {
                res = res * 10 + (c - '0');
                c = read();
            }

            return res;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    static class Pair {
        long value;
        int index;

        Pair(long value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder output = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            int n = fs.nextInt();

            Pair[] arr = new Pair[n];

            for (int i = 0; i < n; i++) {
                arr[i] = new Pair(fs.nextLong(), i);
            }

            Arrays.sort(arr, (p1, p2) -> Long.compare(p1.value, p2.value));

            if (arr[0].value != 0) {
                output.append("-1\n");
                continue;
            }

            long[] answer = new long[n];

            boolean possible = true;
            long previousValue = 0;

            int i = 0;

            while (i < n) {
                int j = i;

                while (j < n && arr[j].value == arr[i].value) {
                    j++;
                }

                long currentShadow = arr[i].value;
                int count = j - i;

                long value;

                if (j < n) {
                    long nextShadow = arr[j].value;
                    long difference = nextShadow - currentShadow;

                    if (difference % count != 0) {
                        possible = false;
                        break;
                    }

                    value = difference / count;

                    if (value <= previousValue || value <= 0) {
                        possible = false;
                        break;
                    }
                } else {
                    value = previousValue + 1;

                    if (value <= 0) {
                        possible = false;
                        break;
                    }
                }

                for (int k = i; k < j; k++) {
                    answer[arr[k].index] = value;
                }

                previousValue = value;
                i = j;
            }

            if (!possible) {
                output.append("-1\n");
            } else {
                for (int k = 0; k < n; k++) {
                    if (k > 0) output.append(" ");
                    output.append(answer[k]);
                }
                output.append("\n");
            }
        }

        System.out.print(output);
    }
}