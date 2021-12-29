package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class DocPathTest {
    static DocPath correct;
    static DocPath incorrect1;

    @BeforeAll
    static void setupAll() {
        correct = new DocPath("/src/Docs/");
        incorrect1 = new DocPath(null); // null

    }

    @Test
    void equalsTest() throws Exception {

        Assertions.assertEquals(correct.getPath(), "/src/Docs/");
        Assertions.assertNotEquals(correct.getPath(), "/src/adasdas/");
        assertThrows(Exception.class, () ->
                incorrect1.getPath());
    }

    @Test
    void erroresTest() {

        Assertions.assertTrue(correct.compPathCode());
        Assertions.assertFalse(incorrect1.compPathCode());

    }

}
