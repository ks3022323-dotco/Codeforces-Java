import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder out = new StringBuilder();

        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();

            int[] depth = new int[n + 1];

            for (int i = 2; i <= n; i++) {
                int parent = sc.nextInt();
                depth[i] = depth[parent] + 1;
            }

            int m = sc.nextInt();

            int[] dams = new int[m];

            for (int i = 0; i < m; i++) {
                dams[i] = sc.nextInt();
            }

            int keep = dams[0];

            for (int v : dams) {
                if (depth[v] < depth[keep]) {
                    keep = v;
                }
            }

            out.append(m - 1);

            for (int v : dams) {
                if (v != keep) {
                    out.append(' ').append(v);
                }
            }

            out.append('\n');
        }

        System.out.print(out);
    }
}