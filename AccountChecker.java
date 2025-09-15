import java.net.*;
import java.util.Scanner;

public class AccountChecker {

    // Цвета ANSI
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public static void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите username для проверки:");
        String username = sc.nextLine().trim();

        checkAccount("Instagram", "https://www.instagram.com/" + username + "/");
        checkAccount("Facebook", "https://www.facebook.com/" + username);
        checkAccount("GitHub", "https://github.com/" + username);

        sc.close();
    }

    private static void checkAccount(String platform, String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setInstanceFollowRedirects(true);
            int code = conn.getResponseCode();

            if (code == 200) {
                System.out.println(GREEN + platform + ": ✅ аккаунт найден (" + urlStr + ")" + RESET);
            } else {
                System.out.println(RED + platform + ": ❌ аккаунт не найден" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + platform + ": ❌ ошибка при проверке (" + e.getMessage() + ")" + RESET);
        }
    }
}
