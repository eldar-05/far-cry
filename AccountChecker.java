import java.net.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

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
                    Thread.sleep(90);
                }
            } else {
                for(int i = 0; i < 30; i++) {
                    System.out.print("#");
                    Thread.sleep(20);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(RED + "Что то пошло не так =(((" + RESET);
        }
        System.out.println(RESET);
        System.out.println();
        System.out.println(YELLOW + " Поиск аккаунтов на юзернейм: " + username + RESET);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();

        if(isDeep) {
            futures.add(executor.submit(() -> checkAccount("Instagram", "https://www.instagram.com/" + username + "/")));
            futures.add(executor.submit(() -> checkAccount("Facebook", "https://www.facebook.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("GitHub", "https://github.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("Twitter", "https://twitter.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("Reddit", "https://www.reddit.com/user/" + username)));
            futures.add(executor.submit(() -> checkAccount("LinkedIn", "https://www.linkedin.com/in/" + username)));
            futures.add(executor.submit(() -> checkAccount("Pinterest", "https://www.pinterest.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("YouTube", "https://www.youtube.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("Medium", "https://medium.com/@" + username)));
            futures.add(executor.submit(() -> checkAccount("Steam", "https://steamcommunity.com/id/" + username)));
            futures.add(executor.submit(() -> checkAccount("WordPress", "https://" + username + ".wordpress.com/")));
            futures.add(executor.submit(() -> checkAccount("Flickr", "https://www.flickr.com/people/" + username + "/")));
            futures.add(executor.submit(() -> checkAccount("Quora", "https://www.quora.com/profile/" + username)));
            futures.add(executor.submit(() -> checkAccount("Goodreads", "https://www.goodreads.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("DeviantArt", "https://www.deviantart.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("Imgur", "https://imgur.com/user/" + username)));
            futures.add(executor.submit(() -> checkAccount("Blogger", "https://" + username + ".blogspot.com/")));
            futures.add(executor.submit(() -> checkAccount("Tumblr", "https://" + username + ".tumblr.com/")));
            futures.add(executor.submit(() -> checkAccount("Xing", "https://www.xing.com/profile/" + username)));
            futures.add(executor.submit(() -> checkAccount("Ok.ru", "https://ok.ru/profile/" + username)));
        } else {
            futures.add(executor.submit(() -> checkAccount("Instagram", "https://www.instagram.com/" + username + "/")));
            futures.add(executor.submit(() -> checkAccount("Facebook", "https://www.facebook.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("GitHub", "https://github.com/" + username)));
            futures.add(executor.submit(() -> checkAccount("Telegram", "https://t.me/" + username)));
        }

        for (Future<?> f : futures) {
            try { f.get(); } catch (Exception ignored) {}
        }

        executor.shutdown();
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
            //Instagram
                if(platform.equals("Instagram")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";  
                    scanner.close();                      
                    if(content.contains("\"username\"")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                } 
            //Facebook
                else if(platform.equals("Facebook")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Telegram
                else if(platform.equals("Telegram")) {
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
                }
            //Steam
                else if(platform.equals("Steam")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();

                    boolean hasTitle = content.contains("username");
                    boolean hasDescription = content.contains("og:description") && !content.contains("content=\"\"");

                    if (hasTitle && hasDescription) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (нет tgme_page_title или описание пустое)");
                    }
                }
            //Medium
                else if(platform.equals("Medium")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //DeviantArt
                else if(platform.equals("DeviantArt")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Ok.ru
                else if(platform.equals("Ok.ru")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Blogger
                else if(platform.equals("Blogger")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Goodreads
                else if(platform.equals("Goodreads")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Tumblr
                else if(platform.equals("Tumblr")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Xing
                else if(platform.equals("Xing")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Quora
                else if(platform.equals("Quora")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Flickr
                else if(platform.equals("Flickr")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Imgur
                else if(platform.equals("Imgur")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Twitter
                else if(platform.equals("Twitter")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Pinterest
                else if(platform.equals("Pinterest")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("\"message\": \"Not Found\"")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //Reddit
                else if(platform.equals("Reddit")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
                    }
                }
            //WordPress    
                else if(platform.equals("WordPress")) {
                    Scanner scanner = new Scanner(conn.getInputStream());
                    String content = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                    if(content.contains("username")) {
                        System.out.println(GREEN + platform + " Success :" + RESET + " аккаунт найден! (" + urlStr + ")");
                    } else {
                        System.out.println(RED + platform + " Fail :" + RESET + " аккаунт не найден (страница существует, но юзернейм не найден)");
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
