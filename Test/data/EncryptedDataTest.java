package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class EncryptedDataTest {
    static EncryptedData correct;
    static EncryptedData correct1;
    static EncryptedData incorrect1;



    @BeforeAll
    static void setupAll() {
        correct = new EncryptedData("4Fi5r564ma".getBytes());
        correct1 = new EncryptedData("s6874gfA79AT".getBytes());
        incorrect1 = new EncryptedData(null); // null

    }

    @Test
    void equalsTest() throws Exception {

        assertEquals(Arrays.toString("4Fi5r564ma".getBytes()), Arrays.toString(correct.getData()));
        assertEquals(Arrays.toString("s6874gfA79AT".getBytes()), Arrays.toString(correct1.getData()));
        assertNotEquals(Arrays.toString("MBL".getBytes()),Arrays.toString(correct.getData()));
        assertNotEquals(Arrays.toString("OLI".getBytes()), Arrays.toString(correct1.getData()));
        assertThrows(Exception.class, () ->
                incorrect1.getData());

    }

    @Test
    void erroresTest() {

        Assertions.assertTrue(correct.compDataCode());
        Assertions.assertFalse(incorrect1.compDataCode());

    }
    @Test
    void toStringTest(){

        String Equals= ("EncryptedData{" + "data=" + Arrays.toString("4Fi5r564ma".getBytes()) + '}');
        String Equals2= ("EncryptedData{" + "data=" + Arrays.toString("s6874gfA79AT".getBytes()) + '}');

        assertEquals(Equals, correct.toString());
        assertEquals(Equals2, correct1.toString());
        assertNotEquals(Equals, correct1.toString());
        assertNotEquals(Equals2, correct.toString());
    }
}
