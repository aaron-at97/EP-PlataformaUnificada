package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordTest {

    static Password correct;
    static Password incorrect1;
    static Password incorrect2;
    static Password incorrect3;
    static Password incorrect4;

    @BeforeAll
    static void setupAll() {
        correct = new Password("S12a3v4652");
        incorrect1 = new Password("");
        incorrect2 = new Password("a5b6"); // menos de 7
        incorrect3 = new Password("123456789"); //solo digitos
        incorrect4 = new Password("ABCDEFFEFGHIJKL"); //solo digitos caracteres

    }

    @Test
    public void getterPassword() throws Exception {

        Assertions.assertEquals(correct.getPassword(), "S12a3v4652");
        assertThrows(Exception.class, () -> incorrect1.getPassword());
        assertThrows(Exception.class, () -> incorrect2.getPassword());
        assertThrows(Exception.class, () -> incorrect3.getPassword());
        assertThrows(Exception.class, () -> incorrect4.getPassword());

    }

    @Test
    public void erroresPassword() {

        Assertions.assertTrue(correct.compPasswordCode());
        Assertions.assertFalse(incorrect1.compPasswordCode());
        Assertions.assertFalse(incorrect2.compPasswordCode());
        Assertions.assertFalse(incorrect3.compPasswordCode());
        Assertions.assertFalse(incorrect4.compPasswordCode());

    }

}
