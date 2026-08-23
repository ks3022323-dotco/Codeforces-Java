import java.io.*;
import java.util.*;

public class Main {

    static int longestAlternating(String s, char first) {
        char need = first;
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == need) {
                count++;
                need = (need == '0') ? '1' : '0';
            }
        }

        return count;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int t = Integer.parseInt(br.readLine().trim());

        while (t-- > 0) {

            int n = Integer.parseInt(br.readLine().trim());
            String s = br.readLine().trim();

            int zeros = 0;
            int ones = 0;

            for (char c : s.toCharArray()) {
                if (c == '0')
                    zeros++;
                else
                    ones++;
            }

            int difference = zeros - ones;

            // Deleted 0s and 1s must differ by at most 1.
            // The remaining alternating string also has
            // 0s and 1s differing by at most 1.
            if (Math.abs(difference) > 2) {
                out.append("-1\n");
                continue;
            }

            int best = 0;

            // Try final alternating string starting with 0.
            int len0 = longestAlternating(s, '0');

            // Odd length -> difference of remaining counts is +1
            // Even length -> difference is 0.

            // Remaining difference = +1
            if (Math.abs(difference - 1) <= 1) {
                int len = len0;

                if (len % 2 == 0)
                    len--;

                best = Math.max(best, len);
            }

            // Remaining difference = 0
            if (Math.abs(difference) <= 1) {
                int len = len0;

                if (len % 2 == 1)
                    len--;

                best = Math.max(best, len);
            }

            // Try final alternating string starting with 1.
            int len1 = longestAlternating(s, '1');

            // Remaining difference = -1
            if (Math.abs(difference + 1) <= 1) {
                int len = len1;

                if (len % 2 == 0)
                    len--;

                best = Math.max(best, len);
            }

            // Remaining difference = 0
            if (Math.abs(difference) <= 1) {
                int len = len1;

                if (len % 2 == 1)
                    len--;

                best = Math.max(best, len);
            }

            if (best <= 0) {
                out.append("-1\n");
            } else {
                out.append(n - best).append('\n');
            }
        }

        System.out.print(out);
    }
}
