import java.io.*;
import java.util.*;

public class Main {
    static class FastScanner {
        private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer st;

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {
            int n = fs.nextInt();

            TreeMap<Long, Integer> map = new TreeMap<>();

            for (int i = 0; i < n; i++) {
                long x = fs.nextLong();
                map.put(x, map.getOrDefault(x, 0) + 1);
            }

            long current = 0;
            long[] answer = new long[n];
            boolean possible = true;

            for (int i = 0; i < n; i++) {
                long limit = 1 - current;
                Long x = map.ceilingKey(limit);

                if (x == null) {
                    possible = false;
                    break;
                }

                current += x;
                answer[i] = current;

                int count = map.get(x);

                if (count == 1) {
                    map.remove(x);
                } else {
                    map.put(x, count - 1);
                }
            }

            if (!possible) {
                out.append("-1\n");
            } else {
                for (int i = 0; i < n; i++) {
                    if (i > 0) {
                        out.append(" ");
                    }
                    out.append(answer[i]);
                }
                out.append("\n");
            }
        }

        System.out.print(out);
    }
}