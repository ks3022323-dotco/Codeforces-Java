import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.next();

        String target = "hello";
        int j = 0;

        for (int i = 0; i < s.length(); i++) {
            if (j < target.length() && s.charAt(i) == target.charAt(j)) {
                j++;
            }
        }

        if (j == target.length()) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        sc.close();
    }
}