# Assignment 4: Text Analysis — Dynamic Programming and String Algorithms

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6+

### Compilation and Running Benchmarks
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="Main"
```

### Running Unit Tests
```bash
mvn test
```

---

## 1. Problem Description and Algorithm Overview

This project implements and evaluates fundamental algorithms for text processing, sequence comparison, and pattern matching across three main categories:

### Part A: Dynamic Programming
1. **Longest Common Subsequence (LCS):** Finds the length of the longest subsequence present in two given strings in the same relative order.
    - **Recurrence & Correctness:** Let `dp[i][j]` be the LCS length of prefixes `X[0..i-1]` and `Y[0..j-1]`.
        - If `i = 0` or `j = 0`, `dp[i][j] = 0`.
        - If `X[i-1] == Y[j-1]`, `dp[i][j] = dp[i-1][j-1] + 1`.
        - If `X[i-1] != Y[j-1]`, `dp[i][j] = max(dp[i-1][j], dp[i][j-1])`.
    - *Why it is correct:* By optimal substructure, matching characters contribute 1 to the optimal subsequence. When characters differ, the optimal solution must exclude either the last character of `X` or `Y`, taking the maximum of the two subproblems.

2. **Edit Distance (Levenshtein Distance):** Computes the minimum number of single-character insertions, deletions, and substitutions required to transform string `X` into string `Y`.
    - **Recurrence & Correctness:** Let `dp[i][j]` represent the edit distance between prefixes `X[0..i-1]` and `Y[0..j-1]`.
        - If `j = 0`, `dp[i][j] = i` (all deletions).
        - If `i = 0`, `dp[i][j] = j` (all insertions).
        - If `X[i-1] == Y[j-1]`, `dp[i][j] = dp[i-1][j-1]`.
        - If `X[i-1] != Y[j-1]`, `dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])`.
    - *Why it is correct:* It systematically evaluates the minimum cost among substitution, deletion, and insertion at each step.

### Part B: String Matching
1. **Naive String Matching:** Slides the pattern window one index at a time across the text and compares characters sequentially.
2. **Knuth-Morris-Pratt (KMP):** Preprocesses the pattern into an LPS (Longest Prefix Suffix) array to skip redundant comparisons during mismatches, achieving `O(n + m)` time complexity.
3. **Rabin-Karp:** Uses polynomial rolling hashes to compare the pattern against text windows in `O(1)` average time, resolving collisions with character-by-character verification.

### Part C: Advanced String Algorithm
- **Kasai’s LCP Algorithm:** Constructs the Longest Common Prefix (LCP) array in `O(n)` time given a string and its Suffix Array, utilizing the property that the LCP value decreases by at most 1 when moving sequentially through sorted suffixes.

---

## 2. Implementation Decisions and Assumptions

- **Alphabet:** All randomly generated strings use a consistent 26-character lowercase English alphabet: `a-z`.
- **Empty Pattern Policy:** If `pattern.length() == 0` or `pattern.length() > text.length()`, the search algorithms return an empty list.
- **Indexing:** 0-based indexing is used throughout.
- **Data Structures:** Standard 2D primitive arrays (`int[][]`) are used for dynamic programming tables to ensure optimal cache locality.

---

## 3. Complexity Analysis

| Algorithm | Main Operation / Recurrence | Best-Case Time | Worst-Case Time | Auxiliary Space | Justification |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **LCS** | `dp[i][j]` transition | O(m * n) | O(m * n) | O(m * n) | Fills an `(m+1) x (n+1)` grid in constant time per cell. |
| **Edit Distance** | Three-way minimum transition | O(m * n) | O(m * n) | O(m * n) | Evaluates every cell of the distance matrix. |
| **Naive Search** | Sequential character comparison | O(n) | O((n - m + 1) * m) | O(1) | Worst case occurs on highly repetitive patterns/texts. |
| **KMP Search** | Prefix shift via LPS array | O(m + n) | O(m + n) | O(m) | Text pointer never moves backward; linear time guaranteed. |
| **Rabin-Karp** | Rolling hash evaluation | O(m + n) | O(m * n) | O(1) | Expected linear time unless frequent hash collisions occur. |
| **Kasai's LCP** | Amortized pointer increments (`h++`) | O(n) | O(n) | O(n) | Variable `h` decreases at most once per iteration, bounding total inner loop steps. |

---

## 4. Testing Strategy and Results

Unit tests were implemented using **JUnit 5**:
- **LCSTest:** Verified identical, completely different, and empty strings. (PASSED)
- **EditDistanceTest:** Verified identical strings, empty inputs, and mixed substitution/insertion/deletion cases. (PASSED)
- **StringMatcherTest:** Verified pattern positions (start, middle, end), absent patterns, and consistency across Naive, KMP, and Rabin-Karp. (PASSED)
- **KasaiLCPTest:** Verified correct LCP array generation against manually checked Suffix Array on `"banana"`. (PASSED)

---

## 5. Experimental Setup and Timing Methodology

- **Environment:** Apple MacBook Air, Java 17, Maven.
- **Reproducibility:** Seed `new Random(42)`, alphabet `a-z`.
- **Timing:** Measured via `System.nanoTime()` in milliseconds. Excludes input generation and I/O. Each test runs 2 warm-ups and 5 measured runs, taking the arithmetic mean.

---

## 6. Experimental Results

### Table 1: Dynamic Programming (LCS & Edit Distance)
| Input Size (m = n) | LCS Avg Time (ms) | Edit Distance Avg Time (ms) |
| :---: | :---: | :---: |
| **100** | 0.7241 | 0.9258 |
| **500** | 5.0506 | 2.3568 |
| **1,000** | 8.5709 | 3.8932 |

### Table 2: String Matching Algorithms (Pattern Length = 5)
| Text Length (n) | Algorithm | Avg Time (ms) |
| :---: | :---: | :---: |
| **10,000** | Naive | 0.5713 |
| **10,000** | KMP | 0.8232 |
| **10,000** | Rabin-Karp | 0.6322 |
| **50,000** | Naive | 1.0255 |
| **50,000** | KMP | 1.6242 |
| **50,000** | Rabin-Karp | 1.3728 |
| **100,000** | Naive | 0.1823 |
| **100,000** | KMP | 0.4808 |
| **100,000** | Rabin-Karp | 1.0870 |

### Table 3: Kasai's LCP Algorithm
| String Length (n) | Kasai LCP Avg Time (ms) |
| :---: | :---: |
| **1,000** | 0.1188 |
| **5,000** | 0.3876 |
| **10,000** | 0.3324 |

### Performance Plots
![DP Execution Time vs Input Size](/docs/plots/dp_comparison.png)
*Figure 1: Execution time vs. input size for LCS and Edit Distance.*

![String Matching Execution Time vs Text Length](/docs/plots/string_matching_comparison.png)
*Figure 2: Execution time vs. text length for Naive, KMP, and Rabin-Karp.*

---

## 7. Discussion: Theoretical vs. Experimental Comparison
- **Dynamic Programming:** Growth rates align closely with theoretical `O(m * n)` quadratic behavior as input sizes increase.
- **String Matching:** Naive and linear algorithms perform very efficiently on random 26-letter alphabets due to immediate character mismatches, while KMP and Rabin-Karp maintain predictable linear performance bounds.
- **Kasai's LCP:** Empirical results confirm the linear `O(n)` time complexity, showing minimal execution time even for large strings.

---

## 8. Conclusion
The implementation successfully fulfills all requirements of text analysis using dynamic programming, pattern matching, and suffix-based algorithms, demonstrating optimal theoretical and empirical trade-offs.