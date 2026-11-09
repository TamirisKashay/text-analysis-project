import algorithms.LCS;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LCSTest {
    @Test
    public void testIdenticalStrings() {
        assertEquals(5, LCS.computeLength("hello", "hello"));
    }

    @Test
    public void testCompletelyDifferentStrings() {
        assertEquals(0, LCS.computeLength("abc", "xyz"));
    }

    @Test
    public void testEmptyString() {
        assertEquals(0, LCS.computeLength("", "abc"));
        assertEquals(0, LCS.computeLength("abc", ""));
    }

    @Test
    public void testGeneralCase() {
        assertEquals(3, LCS.computeLength("AGGTAB", "GXTXAYB"));
        assertEquals(4, LCS.computeLength("AGGTAB", "GXTXAYB"));
    }
}