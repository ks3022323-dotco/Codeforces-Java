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

    static int n;
    static int[] p;
    static int[] last;
    static int[] tree;
    static int[] lazy;

    static void apply(int node, int value) {
        tree[node] += value;
        lazy[node] += value;
    }

    static void push(int node) {
        if (lazy[node] == 0) return;

        int value = lazy[node];
        apply(node << 1, value);
        apply(node << 1 | 1, value);
        lazy[node] = 0;
    }

    static void add(int node, int left, int right,
                    int ql, int qr, int value) {
        if (ql <= left && right <= qr) {
            apply(node, value);
            return;
        }

        push(node);

        int mid = (left + right) >>> 1;

        if (ql <= mid) {
            add(node << 1, left, mid, ql, qr, value);
        }

        if (qr > mid) {
            add(node << 1 | 1, mid + 1, right, ql, qr, value);
        }

        tree[node] = Math.max(tree[node << 1],
                              tree[node << 1 | 1]);
    }

    static void add(int left, int right, int value) {
        if (left <= right) {
            add(1, 1, n, left, right, value);
        }
    }

    static int findBad(int node, int left, int right) {
        if (left == right) {
            return left;
        }

        push(node);

        int mid = (left + right) >>> 1;

        if (tree[node << 1] > 2) {
            return findBad(node << 1, left, mid);
        }

        return findBad(node << 1 | 1, mid + 1, right);
    }

    static void remove(int node, int left, int right, int position) {
        if (left == right) {
            tree[node] = -1_000_000_000;
            lazy[node] = 0;
            return;
        }

        push(node);

        int mid = (left + right) >>> 1;

        if (position <= mid) {
            remove(node << 1, left, mid, position);
        } else {
            remove(node << 1 | 1, mid + 1, right, position);
        }

        tree[node] = Math.max(tree[node << 1],
                              tree[node << 1 | 1]);
    }

    static int solve(int[] input) {
        n = input.length - 1;
        p = input;
        last = new int[n + 2];
        tree = new int[4 * n + 10];
        lazy = new int[4 * n + 10];

        int answer = n;

        for (int r = 1; r < 2 * n; r++) {
            int L = Math.max(1, r - n + 1);
            int R = Math.min(r, n);

            int x = p[(r - 1) % n + 1];

            add(L, R, 1);

            if (x > 1 && last[x - 1] >= L) {
                add(L, Math.min(R, last[x - 1]), -1);
            }

            if (x < n && last[x + 1] >= L) {
                add(L, Math.min(R, last[x + 1]), -1);
            }

            last[x] = r;

            while (tree[1] > 2) {
                int bad = findBad(1, 1, n);
                remove(1, 1, n, bad);
                answer--;
            }
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int TestsNumT = fs.nextInt();

        while (TestsNumT-- > 0) {
            int size = fs.nextInt();
            int[] input = new int[size + 1];

            for (int i = 1; i <= size; i++) {
                input[i] = fs.nextInt();
            }

            out.append(solve(input)).append('\n');
        }

        System.out.print(out);
    }
}