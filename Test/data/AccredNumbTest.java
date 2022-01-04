package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccredNumbTest {

    static AccredNumb correct;
    static AccredNumb incorrect1;
    static AccredNumb incorrect2;
    static AccredNumb incorrect3;
    static AccredNumb incorrect4;

    @BeforeAll
    static void setupAll() {
        correct = new AccredNumb("252132563551");
        incorrect1 = new AccredNumb("");
        incorrect2 = new AccredNumb("12345678"); // menos de 12 digitos
        incorrect3 = new AccredNumb("123456789012345"); // mas de 12 digitos
        incorrect4 = new AccredNumb("ABCDEFGHIJKL"); //12 digitos caracteres
    }

    @Test
    public void getterProductID() throws Exception {

        Assertions.assertEquals(correct.getssNum(), "252132563551");
        assertThrows(Exception.class, () ->
                incorrect1.getssNum());
        assertThrows(Exception.class, () ->
                incorrect2.getssNum());
        assertThrows(Exception.class, () ->
                incorrect3.getssNum());

        assertThrows(Exception.class, () ->
                incorrect4.getssNum());

    }

    @Test
    public void erroresProductID() {

        Assertions.assertTrue(correct.compSSCode());
        Assertions.assertFalse(incorrect1.compSSCode());
        Assertions.assertFalse(incorrect2.compSSCode());
        Assertions.assertFalse(incorrect3.compSSCode());
        Assertions.assertFalse(incorrect4.compSSCode());

    }
}

