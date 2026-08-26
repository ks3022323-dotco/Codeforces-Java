import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();
            int m = sc.nextInt();

            Set<Character> available = new HashSet<>();


            for (int i = 0; i < n; i++) {
                String word = sc.next();
                available.add(Character.toUpperCase(word.charAt(0)));
            }

            String[] abbreviations = new String[m];

            for (int i = 0; i < m; i++) {
                abbreviations[i] = sc.next();
            }

            boolean[] created = new boolean[m];

            int createdCount = 0;

            boolean changed = true;

            while (changed) {
                changed = false;

                for (int i = 0; i < m; i++) {
                    if (created[i]) {
                        continue;
                    }

                    String s = abbreviations[i];

                    boolean possible = true;

                    
                    for (int j = 0; j < s.length(); j++) {
                        if (!available.contains(s.charAt(j))) {
                            possible = false;
                            break;
                        }
                    }

                    if (possible) {
                        created[i] = true;
                        createdCount++;
                        changed = true;
                        
                        available.add(s.charAt(0));
                    }
                }
            }

            if (createdCount == m) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }

        sc.close();
    }
}