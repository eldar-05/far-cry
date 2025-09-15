import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Главное меню ===");
            System.out.println("1. Hello World");
            System.out.println("2. Калькулятор (заглушка)");
            System.out.println("3. Генератор паролей");
            System.out.println("4. Найти аккаунты через юзернеймы (Сталкеринг)");
            System.out.println("0. Выход");
            System.out.print("Выберите пункт: ");

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
                    System.out.println("Неверный выбор!");
            }
        }
    }
}
