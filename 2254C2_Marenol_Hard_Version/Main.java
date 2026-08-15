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
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {
            int n = fs.nextInt();
            String a = fs.next();
            String b = fs.next();

            ArrayList<Integer> aOdd = new ArrayList<>();
            ArrayList<Integer> bOdd = new ArrayList<>();
            ArrayList<Integer> aEven = new ArrayList<>();
            ArrayList<Integer> bEven = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if (a.charAt(i) == '1') {
                    if (i % 2 == 0) {
                        aEven.add(i / 2);
                    } else {
                        aOdd.add(i / 2);
                    }
                }

                if (b.charAt(i) == '1') {
                    if (i % 2 == 0) {
                        bEven.add(i / 2);
                    } else {
                        bOdd.add(i / 2);
                    }
                }
            }

            if (aOdd.size() != bOdd.size() || aEven.size() != bEven.size()) {
                out.append(-1).append('\n');
                continue;
            }

            long answer = 0;

            for (int i = 0; i < aOdd.size(); i++) {
                answer += Math.abs(aOdd.get(i) - bOdd.get(i));
            }

            for (int i = 0; i < aEven.size(); i++) {
                answer += Math.abs(aEven.get(i) - bEven.get(i));
            }

            out.append(answer).append('\n');
        }

        System.out.print(out);
    }
}
