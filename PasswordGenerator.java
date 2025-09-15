import java.util.Scanner;
import java.util.Random;
public class PasswordGenerator {
    public static void run() {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Do you want to generate a password? 1 - yes, 0 - no");
        int choice = sc.nextInt();
        if(choice == 1) {
            System.out.println("Enter the desired length of the password:");
            int length = sc.nextInt();
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
            System.out.print("Generated password: ");
            for(int i = 0; i < length; i++) {
                int randomIndex = (int)(Math.random() * chars.length());
                System.out.print(chars.charAt(randomIndex));
            }
        } else {
            System.out.println("Exiting password generator.");
        }
    }
}
