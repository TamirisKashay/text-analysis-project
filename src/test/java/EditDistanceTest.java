import algorithms.EditDistance;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {
    @Test
    public void testIdenticalStrings() {
        assertEquals(0, EditDistance.compute("algorithm", "algorithm"));
    }

    @Test
    public void testEmptyStrings() {
        assertEquals(5, EditDistance.compute("", "hello"));
        assertEquals(5, EditDistance.compute("hello", ""));
    }

    @Test
    public void testSubInsDel() {
        assertEquals(3, EditDistance.compute("kitten", "sitting"));
    }
}