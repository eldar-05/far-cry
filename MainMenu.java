import java.util.Scanner;

public class MainMenu {

    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Главное меню ===");
            System.out.println("1. " + CYAN + "Hello World" + RESET);
            System.out.println("2. " + CYAN + "Калькулятор (заглушка)" + RESET);
            System.out.println("3. " + CYAN + "Генератор паролей" + RESET);
            System.out.println("4. " + CYAN + "Найти аккаунты через юзернеймы (Сталкеринг)" + RESET);
            System.out.println("0. " + RED + "Выход" + RESET);
            System.out.print("Выберите пункт: ");
            try {
                int choice = (int)(scanner.nextInt());
                switch (choice) {
                case 1:
                    HelloWorld.run();
                    break;
                case 2:
                    Calculator.run();
                    break;
                case 3:
                    PasswordGenerator.run();
                    break;
                case 4:
                    AccountChecker.run();
                case 0:
                    System.out.println("Выход...");
                    scanner.close();
                    return;
                default:
                    System.out.println(RED + "Неверный выбор!" + RESET);
            } 
            } catch (Exception e) {
                System.out.println( RED + "Ошибка ввода. Пожалуйста, введите число." + RESET);
                scanner.next();
                continue;
            }
        }
    }
}
