import algorithms.KMPMatcher;
import algorithms.NaiveMatcher;
import algorithms.RabinKarpMatcher;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class StringMatcherTest {

    @Test
    public void testAllMatchersConsistency() {
        String text = "AABAACAADAABAABA";
        String pattern = "AABA";

        List<Integer> naiveRes = NaiveMatcher.search(text, pattern);
        List<Integer> kmpRes = KMPMatcher.search(text, pattern);
        List<Integer> rkRes = RabinKarpMatcher.search(text, pattern);

        assertEquals(naiveRes, kmpRes);
        assertEquals(naiveRes, rkRes);
        assertEquals(List.of(0, 9, 12), naiveRes);
    }

    @Test
    public void testPositionsBeginMiddleEnd() {
        String text = "teststringtest";
        List<Integer> res = KMPMatcher.search(text, "test");
        assertEquals(List.of(0, 10), res);
    }

    @Test
    public void testAbsentPattern() {
        String text = "abcdef";
        List<Integer> res = KMPMatcher.search(text, "xyz");
        assertTrue(res.isEmpty());
    }

    @Test
    public void testPatternLongerThanText() {
        String text = "abc";
        List<Integer> res = KMPMatcher.search(text, "abcdef");
        assertTrue(res.isEmpty());
    }

    @Test
    public void testEmptyPattern() {
        String text = "abc";
        List<Integer> res = KMPMatcher.search(text, "");
        assertTrue(res.isEmpty());
    }
}