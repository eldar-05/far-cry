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
            for(int i = 0; i < 3; i++) {
                System.out.println("                                                                                                ");
            }
            banner();
            System.out.println("\n=== Главное меню ===");
            System.out.println("1. " + CYAN + "Инстурменты" + RESET);
            System.out.println("2. " + CYAN + "Cyber security" + RESET);
            System.out.println("3. " + CYAN + "Игры" + RESET);
            System.out.println("0. " + RED + "Выход" + RESET);
            System.out.print("Выберите пункт: ");
            int n = scanner.nextInt();
            showMenu(n);
        }
    }

    public static void clearScreen() {
        System.out.print("\u001b[H");
    }

    public static void showMenu(int n) {
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < 20; i++) {
                System.out.println("                                                                                                ");
        }
        banner();
        if(n == 1) {
            System.out.println("\n=== Главное меню ===");
            System.out.println(">>" + YELLOW + " Инстурменты" + RESET);
            System.out.println("|==  1. " + CYAN + "Calculator" + RESET);
            System.out.println("|==  2. " + CYAN + "Note" + RESET);
            System.out.println("0. " + RED + "Назад" + RESET);
            System.out.print("Выберите пункт: ");
            try {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    Calculator.run();
                } else if (choice == 2) {
                    Note.run();
                } else {
                    System.out.println(RED + "Неверный выбор!" + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "Ошибка ввода!" + RESET);
            }
        } else if(n == 2) {
            System.out.println("\n=== Главное меню ===");
            System.out.println(">>" + YELLOW + " Cyber security" + RESET);
            System.out.println("|==  1. " + CYAN + "Генератор паролей" + RESET);
            System.out.println("|==  2. " + CYAN + "Проверка Акк по username" + RESET);
            System.out.println("|==  3. " + CYAN + "GitHub spy" + RESET);
            System.out.println("|==  4. " + CYAN + "Brute Force" + RESET);
            System.out.println("0. " + RED + "Назад" + RESET);
            System.out.print("Выберите пункт: ");
            try {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    PasswordGenerator.run();
                } else if (choice == 2) {
                    AccountChecker.run();
                } else if (choice == 3) {
                    GithubSpy.run();
                } else if (choice == 4) {
                    BruteForce.run();
                } else {
                    System.out.println(RED + "Неверный выбор!" + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "Ошибка ввода!" + RESET);
            }
        } else if(n == 3) {
            System.out.println("\n=== Главное меню ===");
            System.out.println(">> " + YELLOW + "Игры" + RESET);
            System.out.println("|== 1. " + CYAN + "Крестики - Нолики" + RESET);
            System.out.println("0. " + RED + "Назад" + RESET);
            System.out.print("Выберите пункт: ");
            try {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    TickTackToe.run();
                } else {
                    System.out.println(RED + "Неверный выбор!" + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "Ошибка ввода!" + RESET);
            }
        } else if(n == 0) {
            System.out.println("Выход...");
            System.exit(0);
        } else {
            System.out.println(RED + "Неверный выбор!" + RESET);
        }
    }

    public static void banner() {
        System.out.println(GREEN);
        System.out.println("                " + RED + "     ______                       " + GREEN);
        System.out.println("              "+ RED + "    .-        -.                    " + GREEN);
        System.out.println("  ============ "+ RED + "  /            \\  " + GREEN + " ================  ");
        System.out.println(" ___         "+ RED + "   |              |  " + GREEN + "   ___             ");
        System.out.println("| __|_ _ _ _  " + RED  +"  |,  .-.  .-.  ,|  " + GREEN +"  / __|_ _ _  _    ");
        System.out.println("| _/ _` | '_| " + RED + "  | )(__/  \\__)( |  "+ GREEN +" | (__| '_| || |    " );
        System.out.println("|_|\\__,_|_|  " + RED  +"   |/     /\\     \\|  " + GREEN + "  \\___|_|  \\_, | ");
        System.out.println("              "+ RED +"  (_     ^^     _)       "+ GREEN +"      |__/    ");
        System.out.println(" =============  "+ RED + " \\__|IIIIII|__/ "+ GREEN +" ================== ");
        System.out.println("                "+ RED +"  | \\IIIIII/ |   "+ GREEN +" ");
        System.out.println("              " + RED + "    \\          / "+GREEN+" ");
        System.out.println("              " + RED + "     `--------`  "+GREEN+" ");
        System.out.print(RESET);
    }
}
