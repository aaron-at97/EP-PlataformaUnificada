package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class EncryptingKeyTest {
    static EncryptingKey correct;
    static EncryptingKey incorrect1;



    @BeforeAll
    static void setupAll() {
        correct = new EncryptingKey(BigInteger.valueOf(4556));
        incorrect1 = new EncryptingKey(null); // null

    }

    @Test
    void equalsTest() throws Exception {

        Assertions.assertEquals(correct.getKey(), BigInteger.valueOf(4556));
        Assertions.assertNotEquals(correct.getKey(), BigInteger.valueOf(123456));
        assertThrows(Exception.class, () ->
                incorrect1.getKey());

    }

    @Test
    void erroresTest() {

        Assertions.assertTrue(correct.compKeyCode());
        Assertions.assertFalse(incorrect1.compKeyCode());


    }
}
