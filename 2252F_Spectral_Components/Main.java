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

            int res = 0;
            while (c > ' ') {
                res = res * 10 + c - '0';
                c = read();
            }
            return res;
        }
    }

    static int n, LOG;
    static int[] head, to, next;
    static int edgeCnt;

    static int[] parent, depth, tin, tout;
    static int[][] up;
    static int timer;

    static int[] cnt;

    static void addEdge(int u, int v) {
        to[edgeCnt] = v;
        next[edgeCnt] = head[u];
        head[u] = edgeCnt++;
    }

    static void buildLCA() {
        parent = new int[n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];

        Arrays.fill(parent, -1);

        int[] stack = new int[n];
        int[] edgeStack = new int[n];
        int top = 0;

        stack[0] = 0;
        edgeStack[0] = head[0];
        parent[0] = 0;

        timer = 0;
        tin[0] = timer++;

        while (top >= 0) {
            int v = stack[top];
            int e = edgeStack[top];

            while (e != -1 && to[e] == parent[v]) {
                e = next[e];
            }

            if (e == -1) {
                tout[v] = timer - 1;
                top--;
                continue;
            }

            edgeStack[top] = next[e];

            int u = to[e];

            if (u == parent[v]) {
                continue;
            }

            parent[u] = v;
            depth[u] = depth[v] + 1;
            tin[u] = timer++;

            top++;
            stack[top] = u;
            edgeStack[top] = head[u];
        }

        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        up = new int[LOG][n];

        for (int v = 0; v < n; v++) {
            up[0][v] = parent[v];
        }

        for (int j = 1; j < LOG; j++) {
            for (int v = 0; v < n; v++) {
                up[j][v] = up[j - 1][up[j - 1][v]];
            }
        }
    }

    static boolean isAncestor(int u, int v) {
        return tin[u] <= tin[v] && tout[v] <= tout[u];
    }

    static int lca(int u, int v) {
        if (isAncestor(u, v)) {
            return u;
        }

        if (isAncestor(v, u)) {
            return v;
        }

        for (int j = LOG - 1; j >= 0; j--) {
            int x = up[j][u];

            if (!isAncestor(x, v)) {
                u = x;
            }
        }

        return parent[u];
    }

    static long solveColor(ArrayList<Integer> list, int k) {
        int m = list.size();

        if (m == 1) {
            return 0;
        }

        int[] colored = new int[m];

        for (int i = 0; i < m; i++) {
            colored[i] = list.get(i);
        }

        Integer[] order = new Integer[m];

        for (int i = 0; i < m; i++) {
            order[i] = colored[i];
        }

        Arrays.sort(order, (a, b) -> Integer.compare(tin[a], tin[b]));

        int[] nodes = new int[2 * m - 1];

        for (int i = 0; i < m; i++) {
            nodes[i] = order[i];
        }

        for (int i = 0; i + 1 < m; i++) {
            nodes[m + i] = lca(order[i], order[i + 1]);
        }

        Integer[] all = new Integer[2 * m - 1];

        for (int i = 0; i < 2 * m - 1; i++) {
            all[i] = nodes[i];
        }

        Arrays.sort(all, (a, b) -> Integer.compare(tin[a], tin[b]));

        int unique = 0;

        for (int i = 0; i < all.length; i++) {
            if (i == 0 || !all[i].equals(all[i - 1])) {
                nodes[unique++] = all[i];
            }
        }

        Integer[] vtNodes = new Integer[unique];

        for (int i = 0; i < unique; i++) {
            vtNodes[i] = nodes[i];
        }

        Arrays.sort(vtNodes, (a, b) -> Integer.compare(tin[a], tin[b]));

        for (int v : colored) {
            cnt[v] = 1;
        }

        int[] st = new int[unique];
        int top = 0;

        st[0] = vtNodes[0];

        int[] edgeParent = new int[unique - 1];
        int[] edgeChild = new int[unique - 1];

        int edgeCount = 0;

        for (int i = 1; i < unique; i++) {
            int v = vtNodes[i];

            while (top >= 0 && !isAncestor(st[top], v)) {
                top--;
            }

            edgeParent[edgeCount] = st[top];
            edgeChild[edgeCount] = v;
            edgeCount++;

            st[++top] = v;
        }

        long totalDistance = 0;

        long[] weights = new long[edgeCount];
        long[] lengths = new long[edgeCount];

        for (int i = edgeCount - 1; i >= 0; i--) {
            int p = edgeParent[i];
            int v = edgeChild[i];

            cnt[p] += cnt[v];

            long inside = cnt[v];
            long outside = m - inside;

            long weight = Math.min(inside, outside);
            long length = depth[v] - depth[p];

            totalDistance += weight * length;

            weights[i] = weight;
            lengths[i] = length;
        }

        Integer[] ids = new Integer[edgeCount];

        for (int i = 0; i < edgeCount; i++) {
            ids[i] = i;
        }

        Arrays.sort(ids, (a, b) -> {
            if (weights[a] != weights[b]) {
                return Long.compare(weights[b], weights[a]);
            }

            return Long.compare(lengths[b], lengths[a]);
        });

        long need = k - 1L;
        long saved = 0;

        for (int id : ids) {
            if (need == 0) {
                break;
            }

            long take = Math.min(need, lengths[id]);

            saved += take * weights[id];
            need -= take;
        }

        for (int i = 0; i < unique; i++) {
            cnt[vtNodes[i]] = 0;
        }

        return totalDistance - saved;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder out = new StringBuilder();

        int t = fs.nextInt();

        while (t-- > 0) {

            n = fs.nextInt();

            ArrayList<Integer>[] colors = new ArrayList[n];

            for (int i = 0; i < n; i++) {
                colors[i] = new ArrayList<>();
            }

            for (int i = 0; i < n; i++) {
                int c = fs.nextInt() - 1;
                colors[c].add(i);
            }

            int[] k = new int[n];

            for (int i = 0; i < n; i++) {
                k[i] = fs.nextInt();
            }

            head = new int[n];
            Arrays.fill(head, -1);

            to = new int[2 * Math.max(0, n - 1)];
            next = new int[2 * Math.max(0, n - 1)];

            edgeCnt = 0;

            for (int i = 0; i < n - 1; i++) {
                int u = fs.nextInt() - 1;
                int v = fs.nextInt() - 1;

                addEdge(u, v);
                addEdge(v, u);
            }

            buildLCA();

            cnt = new int[n];

            long[] answer = new long[n];
            Arrays.fill(answer, -1);

            for (int c = 0; c < n; c++) {
                if (!colors[c].isEmpty()) {
                    answer[c] = solveColor(colors[c], k[c]);
                }
            }

            for (int i = 0; i < n; i++) {
                if (i > 0) {
                    out.append(' ');
                }

                out.append(answer[i]);
            }

            out.append('\n');
        }

        System.out.print(out);
    }
}