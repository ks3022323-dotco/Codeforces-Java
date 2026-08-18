import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int t = Integer.parseInt(br.readLine().trim());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());

            long sum = 0;
            HashMap<Integer, Integer> freq = new HashMap<>();

            for (int i = 0; i < n; i++) {
                int x = Integer.parseInt(st.nextToken());
                sum += x;
                freq.put(x, freq.getOrDefault(x, 0) + 1);
            }

            int maxFreq = 0;

            for (int count : freq.values()) {
                maxFreq = Math.max(maxFreq, count);
            }

            int other = n - maxFreq;

            if (maxFreq <= other + 1) {
                out.append(sum).append('\n');
            } else {
                int value = Integer.MAX_VALUE;

                for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
                    if (entry.getValue() == maxFreq) {
                        value = Math.min(value, entry.getKey());
                    }
                }

                long lost = (long) (maxFreq - other - 2) * value;
                out.append(sum - lost).append('\n');
            }
        }

        System.out.print(out);
    }
}