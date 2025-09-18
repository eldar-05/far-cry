import java.io.*;
import java.net.*;
import java.util.*;

public class GithubSpy {
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";
    
    private static final int TIMEOUT = 15000;
    private static final String[] README_PATHS = {
        "README.md", "README.MD", "README.txt", "README", "readme.md", "Readme.md"
    };

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите GitHub username (можно несколько через запятую):");
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) {
            System.out.println("Ни одного username не введено.");
            return;
        }

        String[] users = line.split(",");
        String token = System.getenv("GITHUB_TOKEN");

        for (String rawUser : users) {
            String user = rawUser.trim();
            if (user.isEmpty()) continue;
            System.out.println("\n=== "+ GREEN + user + RESET + " ===");
            try {
                List<Repo> repos = fetchLatestRepos(user, token, 3);
                if (repos.isEmpty()) {
                    System.out.println(RED + "Нет репозиториев или пользователь не найден." + RESET);
                    continue;
                }
                for (Repo r : repos) {
                    System.out.println(GREEN + "\nНазвание: "+ RED + r.name + RESET);
                    System.out.println( "Ссылка: "+ YELLOW + r.htmlUrl + "/" + r.name + RESET);
                    System.out.println( "Дата последнего пуша: "+ YELLOW + r.pushedAt + RESET);
                    String readme = tryFetchReadme(user, r.name, token);
                    if (readme != null) {
                        String firstLine = firstNonEmptyLine(readme);
                        if (firstLine.length() > 400) {
                            firstLine = firstLine.substring(0, 400) + "...";
                        }
                        System.out.println("Описание (из README): " + firstLine);
                    } else {
                        System.out.println("Описание (README): отсутствует");
                    }
                }
            } catch (Exception ex) {
                System.out.println(RED + "Ошибка при обработке " + RESET + user + ": " + ex.getMessage());
            }
        }
        System.out.println(GREEN + "\nГотово." + RESET);
    }

    private static String firstNonEmptyLine(String s) {
        Scanner sc = new Scanner(s);
        while (sc.hasNextLine()) {
            String ln = sc.nextLine().trim();
            if (!ln.isEmpty()) return ln;
        }
        return s.length() > 200 ? s.substring(0, 200) + "..." : s;
    }

    private static List<Repo> fetchLatestRepos(String user, String token, int count) throws IOException {
        String api = String.format(
            "https://api.github.com/users/%s/repos?per_page=%d&sort=pushed",
            URLEncoder.encode(user, "UTF-8"), count
        );
        String json = httpGet(api, token);
        if (json == null || json.trim().isEmpty()) return Collections.emptyList();

        List<Repo> result = new ArrayList<>();
        int idx = 0;
        while (result.size() < count) {
            int namePos = json.indexOf("\"name\"", idx);
            if (namePos == -1) break;
            int nameColon = json.indexOf(':', namePos);
            int nameStart = json.indexOf('"', nameColon + 1);
            int nameEnd = json.indexOf('"', nameStart + 1);
            if (nameStart == -1 || nameEnd == -1) break;
            String name = json.substring(nameStart + 1, nameEnd);

            int htmlPos = json.indexOf("\"html_url\"", nameEnd);
            String html = "";
            if (htmlPos != -1) {
                int hc = json.indexOf(':', htmlPos);
                int hq1 = json.indexOf('"', hc + 1);
                int hq2 = json.indexOf('"', hq1 + 1);
                if (hq1 != -1 && hq2 != -1) html = json.substring(hq1 + 1, hq2);
            }

            int pushedPos = json.indexOf("\"pushed_at\"", nameEnd);
            String pushed = "";
            if (pushedPos != -1) {
                int pc = json.indexOf(':', pushedPos);
                int pq1 = json.indexOf('"', pc + 1);
                int pq2 = json.indexOf('"', pq1 + 1);
                if (pq1 != -1 && pq2 != -1) pushed = json.substring(pq1 + 1, pq2);
            }

            result.add(new Repo(name, html, pushed));
            idx = nameEnd + 1;
        }
        return result;
    }

    private static String tryFetchReadme(String owner, String repo, String token) {
        String[] branches = {"main", "master"};
        for (String branch : branches) {
            for (String readmeName : README_PATHS) {
                String raw = String.format(
                    "https://raw.githubusercontent.com/%s/%s/%s/%s",
                    urlEncode(owner), urlEncode(repo), branch, readmeName
                );
                try {
                    String content = httpGet(raw, token);
                    if (content != null && !content.trim().isEmpty()) {
                        return content;
                    }
                } catch (Exception ignored) {}
            }
        }
        return null;
    }

    private static String httpGet(String urlStr, String token) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(TIMEOUT);
        conn.setReadTimeout(TIMEOUT);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Java-GitHubLatestFetcher");
        if (token != null && !token.trim().isEmpty()) {
            conn.setRequestProperty("Authorization", "token " + token.trim());
        }
        int code = conn.getResponseCode();
        if (code == 200) {
            try (InputStream in = conn.getInputStream();
                 InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                 Scanner sc = new Scanner(isr)) {
                StringBuilder sb = new StringBuilder();
                while (sc.hasNextLine()) {
                    sb.append(sc.nextLine()).append("\n");
                }
                return sb.toString();
            }
        } else {
            return null;
        }
    }

    private static String urlEncode(String s) {
        try { 
            return URLEncoder.encode(s, "UTF-8").replace("+", "%20"); 
        } catch (Exception e) { 
            return s; 
        }
    }

    private static class Repo {
        String name;
        String htmlUrl;
        String pushedAt;
        Repo(String n, String h, String p) { 
            name = n; htmlUrl = h; pushedAt = p; 
        }
    }
}
