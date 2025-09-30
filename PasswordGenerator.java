import java.util.Scanner;
import java.util.Random;
public class PasswordGenerator {
    
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    public static void run() {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Do you want to generate a password? 1 - yes, 0 - no");
        String choice = sc.nextLine();
        if(choice.equals("1") || choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter the desired length of the password:");
            int length = sc.nextInt();
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
            for(int i = 0; i < 20; i++) {
                System.out.println("                                                                                                ");
            }
            System.out.print("Generated password: " + GREEN);
            for(int i = 0; i < length; i++) {
                int randomIndex = (int)(Math.random() * chars.length());
                System.out.print(chars.charAt(randomIndex));
            }
            System.out.println(RESET);
        } else {
            System.out.println( RESET + "Exiting password generator.");
        }
    }
}
