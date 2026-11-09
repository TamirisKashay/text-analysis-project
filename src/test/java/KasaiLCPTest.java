import algorithms.KasaiLCP;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KasaiLCPTest {

    @Test
    public void testKasaiManualVerification() {

        String s = "banana";
        int[] sa = {5, 3, 1, 0, 4, 2};
        int[] expectedLCP = {0, 1, 3, 0, 0, 2};

        int[] actualLCP = KasaiLCP.computeLCP(s, sa);

        assertArrayEquals(expectedLCP, actualLCP);
    }
}