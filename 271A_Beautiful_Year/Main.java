import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int y = sc.nextInt();

        while (true) {
            y++;

            String year = String.valueOf(y);

            if (year.charAt(0) != year.charAt(1) &&
                year.charAt(0) != year.charAt(2) &&
                year.charAt(0) != year.charAt(3) &&
                year.charAt(1) != year.charAt(2) &&
                year.charAt(1) != year.charAt(3) &&
                year.charAt(2) != year.charAt(3)) {

                System.out.println(y);
                break;
            }
        }

        sc.close();
    }
}