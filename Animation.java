import java.util.*;
import java.io.*;
public class Animation {
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";
    
    public static void loading() {

        try {
            String text = "Welcome to far cry. This program is created by Neonix Team. Enjoy it!";
            for(int i = 0; i < text.length(); i++) {
                clearScreen();
                System.out.println(GREEN + text.substring(0, i) + RESET);
                Thread.sleep(25);
            }
            Thread.sleep(400);
             
        } catch (InterruptedException e) {
            System.out.println("Что то пошло не так =(((");
        }
    }

    private static void clearScreen() {
        for(int i = 0; i < 50; i++) {
            System.out.println("                                                                                                ");
        }
    }
}
