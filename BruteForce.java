
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class BruteForce {
    private static final String GREEN = "\u001B[32m";
    private static final String RED   = "\u001B[31m";
    private static final String YELLOW= "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    private static final int MIN_LEN = 4;
    private static final int MAX_LEN = 8;
    private static final long PRINT_EVERY = 1000L;

    public static void run() {
        Scanner sc = new Scanner(System.in);
        String target;
        while (true) {
            System.out.print("Введите пароль только цифры, длина от " + MIN_LEN + " до " + MAX_LEN + ": ");
            target = sc.nextLine().trim();
            if (!target.matches("\\d{" + MIN_LEN + "," + MAX_LEN + "}")) {
                System.out.println(RED + "Попробуйте ещё раз" + RESET);
                continue;
            }
            break;
        }

        final String targetFinal = target;

        System.out.println(YELLOW + "Начинаю брутфорс. Цель: " + targetFinal + RESET);
        System.out.println(YELLOW + "||       loading      ||" + RESET);
        try {
            for(int i = 0; i < 23; i++) {
                System.out.print("#");
                    Thread.sleep(45);
            }
             
        } catch (InterruptedException e) {
            System.out.println(RED + "Что то пошло не так =(((" + RESET);
        }
        long start = System.nanoTime();

        AtomicBoolean found = new AtomicBoolean(false);
        AtomicLong attempts = new AtomicLong(0);
        AtomicReference<String> foundValue = new AtomicReference<>(null);

        int threads = Math.max(1, Runtime.getRuntime().availableProcessors());
        ExecutorService exec = Executors.newFixedThreadPool(threads);
        ConcurrentLinkedQueue<Future<?>> futures = new ConcurrentLinkedQueue<>();

        try {
            for (int len = MIN_LEN; len <= MAX_LEN && !found.get(); len++) {
                final int length = len;
                final long max = (long) Math.pow(10, length);
                final int parts = Math.max(1, threads * 4);
                final long chunk = Math.max(1, max / parts);

                for (int p = 0; p < parts; p++) {
                    final long from = p * chunk;
                    final long to = (p == parts - 1) ? max : (from + chunk);

                    Future<?> f = exec.submit(() -> {
                        String fmt = "%0" + length + "d";
                        try {
                            for (long i = from; i < to && !found.get(); i++) {
                                if (Thread.currentThread().isInterrupted()) return;
                                String attempt = String.format(fmt, i);
                                long att = attempts.incrementAndGet();

                                if (att % PRINT_EVERY == 0) {
                                    double elapsedSec = (System.nanoTime() - start) / 1_000_000_000.0;
                                    double rate = att / Math.max(1e-9, elapsedSec);
                                    synchronized (System.out) {
                                        System.out.printf(YELLOW + "%s  |  скорость: %.0f попыт/сек" + RESET + "%n", attempt, rate);
                                    }
                                }

                                if (attempt.equals(targetFinal)) {
                                    if (found.compareAndSet(false, true)) {
                                        foundValue.set(attempt);
                                        synchronized (System.out) {
                                            System.out.println(GREEN + "НАЙДЕНО! Пароль = " + attempt + " (попытка №" + att + ")" + RESET);
                                        }
                                    }
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            synchronized (System.out) {
                                System.out.println(RED + "Исключение в потоке: " + e + RESET);
                                e.printStackTrace(System.out);
                            }
                        }
                    });
                    futures.add(f);
                }

                if (found.get()) break;
            }

            if (found.get()) {
                for (Future<?> fut : futures) fut.cancel(true);
                exec.shutdownNow();
            } else {
                exec.shutdown();
            }

            if (!exec.awaitTermination(2, TimeUnit.MINUTES)) {
                exec.shutdownNow();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(RED + "Выполнение прервано: " + e + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Ошибка: " + e + RESET);
            e.printStackTrace(System.out);
        } finally {
            long end = System.nanoTime();
            long totalAttempts = attempts.get();
            double elapsedSec = Math.max(1e-9, (end - start) / 1_000_000_000.0);

            System.out.println();
            if (foundValue.get() != null) {
                System.out.println(GREEN + "Успешно: найден пароль " + foundValue.get() + RESET);
            } else {
                System.out.println(RED + "Пароль не найден в диапазоне длины " + MIN_LEN + "-" + MAX_LEN + RESET);
            }
            System.out.printf("Попыток: %d%n", totalAttempts);
            System.out.printf("Время: %.3f сек, средняя скорость: %.0f попыт/сек%n", elapsedSec, totalAttempts / elapsedSec);
            
        }
        try {
            Thread.sleep(4000);
             
        } catch (InterruptedException e) {
            System.out.println(RED + "Что то пошло не так =(((" + RESET);
        }
    }
}
