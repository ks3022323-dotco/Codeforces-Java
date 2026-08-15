import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            long n = Long.parseLong(st.nextToken());
            long k = Long.parseLong(st.nextToken());

            long answer = 0;
            long bit = 1;

            while (bit <= n && n > 0) {
                long count = Math.min(k, n / bit);

                answer += count;
                n -= count * bit;

                bit *= 2;
            }

            out.append(answer).append('\n');
        }

        System.out.print(out);
    }
}