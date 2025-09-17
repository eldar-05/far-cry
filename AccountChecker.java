import java.net.*;
import java.util.Scanner;
import java.io.*;

public class AccountChecker {
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    public static void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите username для проверки:");
        String username = sc.nextLine().trim();
        System.out.println(GREEN + "Провести глубокий поиск? (y/n):" + RESET);
        System.out.println(YELLOW + "Глубокий поиск может занять больше времени" + RESET);
        String deepSearch = sc.nextLine().trim().toLowerCase();
        boolean isDeep = deepSearch.equals("y") || deepSearch.equals("yes");
        System.out.println(YELLOW + "|          loading            |" + RESET + GREEN);
        try {
            if(isDeep) {
                for(int i = 0; i < 30; i++) {
                    System.out.print("#");
                    Thread.sleep(135);
                }
            } else {
                for(int i = 0; i < 30; i++) {
                    System.out.print("#");
                    Thread.sleep(40);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(RED + "Что то пошло не так =(((" + RESET);
        }
        System.out.println(RESET);
        System.out.println();
        System.out.println(YELLOW + " Поиск аккаунтов на юзернейм: " + username + RESET);
        if(isDeep) {
            checkAccount("Instagram", "https://www.instagram.com/" + username + "/");
            checkAccount("Facebook", "https://www.facebook.com/" + username);
            checkAccount("GitHub", "https://github.com/" + username);
            checkAccount("Twitter", "https://twitter.com/" + username);
            checkAccount("Reddit", "https://www.reddit.com/user/" + username);
            checkAccount("TikTok", "https://www.tiktok.com/@" + username);
            checkAccount("LinkedIn", "https://www.linkedin.com/in/" + username);
            checkAccount("Pinterest", "https://www.pinterest.com/" + username + "/");
            checkAccount("YouTube", "https://www.youtube.com/" + username);
            checkAccount("Medium", "https://medium.com/@" + username);
            checkAccount("Steam", "https://steamcommunity.com/id/" + username);
            checkAccount("WordPress", "https://" + username + ".wordpress.com/");
            checkAccount("Flickr", "https://www.flickr.com/people/" + username + "/");
            checkAccount("Quora", "https://www.quora.com/profile/" + username);
            checkAccount("Vimeo", "https://vimeo.com/" + username);
            checkAccount("Goodreads", "https://www.goodreads.com/" + username);
            checkAccount("DeviantArt", "https://www.deviantart.com/" + username);
            checkAccount("SoundCloud", "https://soundcloud.com/" + username);
            checkAccount("Imgur", "https://imgur.com/user/" + username);
            checkAccount("Blogger", "https://" + username + ".blogspot.com/");
            checkAccount("Tumblr", "https://" + username + ".tumblr.com/");
            checkAccount("Xing", "https://www.xing.com/profile/" + username);
            checkAccount("Ok.ru", "https://ok.ru/profile/" + username);
        } else {
            checkAccount("Instagram", "https://www.instagram.com/" + username + "/");
            checkAccount("Facebook", "https://www.facebook.com/" + username);
            checkAccount("GitHub", "https://github.com/" + username);
            checkAccount("Telegram", "https://t.me/" + username);
        }
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
                if(platform.equals("Instagram")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";                        
                    if(content.contains("\"username\"")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                } else if(platform.equals("Facebook")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                } else if(platform.equals("Telegram")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();

                    boolean hasTitle = content.contains("tgme_page_title");
                    boolean hasDescription = content.contains("og:description") && !content.contains("content=\"\"");

                    if (hasTitle && hasDescription) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (нет tgme_page_title или описание пустое)");
                    }
                } else {
                    System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                }
            } else {
                System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (код " + code + ")");
            }
        } catch (Exception e) {
            System.out.println(RED + platform + " Fail: " + RESET + " ошибка при проверке (" + e.getMessage() + ")");
        }
    }
}
