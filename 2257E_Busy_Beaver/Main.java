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
                res = res * 10 + c - '0';
                c = read();
            }
            return res;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    static class Segment {
        long need;
        long gain;
        int len;
        int id;
        int index;

        Segment(long need, long gain, int len, int id, int index) {
            this.need = need;
            this.gain = gain;
            this.len = len;
            this.id = id;
            this.index = index;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            int n = fs.nextInt();
            long x = fs.nextLong();

            long[][] a = new long[n][];
            long[][] b = new long[n][];

            @SuppressWarnings("unchecked")
            ArrayList<Segment>[] segments = new ArrayList[n];

            int[] built = new int[n];

            PriorityQueue<Segment> pq = new PriorityQueue<>(
                (u, v) -> {
                    if (u.need != v.need)
                        return Long.compare(u.need, v.need);
                    return Integer.compare(u.id, v.id);
                }
            );

            for (int i = 0; i < n; i++) {
                int m = fs.nextInt();

                a[i] = new long[m];
                b[i] = new long[m];
                segments[i] = new ArrayList<>();

                for (int j = 0; j < m; j++)
                    a[i][j] = fs.nextLong();

                for (int j = 0; j < m; j++)
                    b[i][j] = fs.nextLong();

                int pos = 0;
                int segmentIndex = 0;

                while (pos < m) {
                    long need = 0;
                    long gain = 0;
                    int start = pos;

                    while (pos < m && gain <= 0) {
                        need = Math.max(need, a[i][pos] - gain);
                        gain += b[i][pos] - a[i][pos];
                        pos++;
                    }

                    if (gain > 0) {
                        segments[i].add(
                            new Segment(
                                need,
                                gain,
                                pos - start,
                                i,
                                segmentIndex++
                            )
                        );
                    } else {
                        break;
                    }
                }

                if (!segments[i].isEmpty())
                    pq.add(segments[i].get(0));
            }

            while (!pq.isEmpty()) {
                Segment cur = pq.poll();

                if (x < cur.need)
                    break;

                x += cur.gain;
                built[cur.id] += cur.len;

                int nextIndex = cur.index + 1;

                if (nextIndex < segments[cur.id].size()) {
                    pq.add(segments[cur.id].get(nextIndex));
                }
            }

            int answer = 0;
            int answerId = 1;

            for (int i = 0; i < n; i++) {
                long money = x;
                int height = built[i];

                while (height < a[i].length &&
                       money >= a[i][height]) {
                    money -= a[i][height];
                    money += b[i][height];
                    height++;
                }

                if (height > answer) {
                    answer = height;
                    answerId = i + 1;
                }
            }

            out.append(answer)
               .append(' ')
               .append(answerId)
               .append('\n');
        }

        System.out.print(out);
    }
}