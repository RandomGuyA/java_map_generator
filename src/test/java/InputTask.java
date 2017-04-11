import java.util.Scanner;

/**
 * Created by Admin on 11/04/2017.
 */
public class InputTask {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int input = 0;
        do {

            System.out.println("Enter a number between 1 and 20");
            try {
                input = Integer.parseInt(scan.next());

                if (input != 0) {

                    if (input > 0 && input <= 20) {
                        break;
                    } else {
                        System.out.println("the number you entered is not between 1 and 20");
                    }

                } else {
                    System.out.println("Error: that is not a number");
                }
            } catch (NumberFormatException exp) {
                System.out.println("Error: that is not a number");
            }

        } while (true);

        if (input > 10) {
            System.out.println(input + " is between 10 and 20");
        } else {
            System.out.println(input + " is between 1 and 10");
        }
    }
}
