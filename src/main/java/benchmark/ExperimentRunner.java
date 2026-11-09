package benchmark;

import algorithms.*;
import java.util.*;

public class ExperimentRunner {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int WARMUP_RUNS = 2;
    private static final int MEASURED_RUNS = 5;

    public static void runAllExperiments() {
        Random random = new Random(42);

        System.out.println("=== 1. LCS Experiments ===");
        int[] lcsSizes = {100, 500, 1000};
        for (int size : lcsSizes) {
            String s1 = generateRandomString(size, random);
            String s2 = generateRandomString(size, random);

            // Прогрев
            for (int w = 0; w < WARMUP_RUNS; w++) {
                LCS.computeLength(s1, s2);
            }

            long totalTime = 0;
            for (int i = 0; i < MEASURED_RUNS; i++) {
                long start = System.nanoTime();
                LCS.computeLength(s1, s2);
                long end = System.nanoTime();
                totalTime += (end - start);
            }
            double avgTimeMs = (totalTime / (double) MEASURED_RUNS) / 1_000_000.0;
            System.out.printf("Size: %d | Avg Time: %.4f ms\n", size, avgTimeMs);
        }

        System.out.println("\n=== 2. Edit Distance Experiments ===");
        int[] edSizes = {100, 500, 1000};
        for (int size : edSizes) {
            String s1 = generateRandomString(size, random);
            String s2 = generateRandomString(size, random);

            for (int w = 0; w < WARMUP_RUNS; w++) {
                EditDistance.compute(s1, s2);
            }

            long totalTime = 0;
            for (int i = 0; i < MEASURED_RUNS; i++) {
                long start = System.nanoTime();
                EditDistance.compute(s1, s2);
                long end = System.nanoTime();
                totalTime += (end - start);
            }
            double avgTimeMs = (totalTime / (double) MEASURED_RUNS) / 1_000_000.0;
            System.out.printf("Size: %d | Avg Time: %.4f ms\n", size, avgTimeMs);
        }

        System.out.println("\n=== 3. String Matching Experiments (Naive, KMP, Rabin-Karp) ===");
        int[] textSizes = {10000, 50000, 100000};
        int patternSize = 5;

        for (int size : textSizes) {
            String text = generateRandomString(size, random);
            String pattern = generateRandomString(patternSize, random);

            runMatcherBenchmark("Naive", text, pattern);
            runMatcherBenchmark("KMP", text, pattern);
            runMatcherBenchmark("RabinKarp", text, pattern);
            System.out.println("-------------------------------------------");
        }

        System.out.println("\n=== 4. Kasai's LCP Algorithm Experiments ===");
        int[] kasaiSizes = {1000, 5000, 10000};
        for (int size : kasaiSizes) {
            String text = generateRandomString(size, random);
            int[] sa = computeSimpleSuffixArray(text);

            for (int w = 0; w < WARMUP_RUNS; w++) {
                KasaiLCP.computeLCP(text, sa);
            }

            long totalTime = 0;
            for (int i = 0; i < MEASURED_RUNS; i++) {
                long start = System.nanoTime();
                KasaiLCP.computeLCP(text, sa);
                long end = System.nanoTime();
                totalTime += (end - start);
            }
            double avgTimeMs = (totalTime / (double) MEASURED_RUNS) / 1_000_000.0;
            System.out.printf("String Length: %d | Avg Time: %.4f ms\n", size, avgTimeMs);
        }
    }

    private static void runMatcherBenchmark(String name, String text, String pattern) {
        long totalTime = 0;
        for (int i = 0; i < MEASURED_RUNS; i++) {
            long start = System.nanoTime();
            if (name.equals("Naive")) NaiveMatcher.search(text, pattern);
            else if (name.equals("KMP")) KMPMatcher.search(text, pattern);
            else if (name.equals("RabinKarp")) RabinKarpMatcher.search(text, pattern);
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        double avgTimeMs = (totalTime / (double) MEASURED_RUNS) / 1_000_000.0;
        System.out.printf("[%s] Text Length: %d | Avg Time: %.4f ms\n", name, text.length(), avgTimeMs);
    }

    private static String generateRandomString(int length, Random random) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    private static int[] computeSimpleSuffixArray(String s) {
        int n = s.length();
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        Arrays.sort(indices, (a, b) -> s.substring(a).compareTo(s.substring(b)));
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = indices[i];
        return sa;
    }
}