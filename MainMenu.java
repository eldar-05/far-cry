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
            System.out.println("1. " + CYAN + "Note" + RESET);
            System.out.println("2. " + CYAN + "Калькулятор (заглушка)" + RESET);
            System.out.println("3. " + CYAN + "Генератор паролей" + RESET);
            System.out.println("4. " + CYAN + "Найти аккаунты через юзернеймы (Сталкеринг)" + RESET);
            System.out.println("5. " + CYAN + "GitHub spy" + RESET);
            System.out.println("6. " + CYAN + "Крестики - Нолики" + RESET);
            System.out.println("0. " + RED + "Выход" + RESET);
            System.out.print("Выберите пункт: ");
            try {
                String input = scanner.nextLine().trim();   // читаем строку
                int choice = Integer.parseInt(input);       // переводим в число

                switch (choice) {
                    case 1 -> Note.run();
                    case 2 -> Calculator.run();
                    case 3 -> PasswordGenerator.run();
                    case 4 -> AccountChecker.run();
                    case 5 -> GithubSpy.run();
                    case 6 -> TickTackToe.run();
                    case 0 -> {
                        System.out.println("Выход...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println(RED + "Неверный выбор!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Ошибка ввода. Пожалуйста, введите число." + RESET);
            }
        }

    }

    public static void clearScreen() {
        System.out.print("\u001b[H");
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
