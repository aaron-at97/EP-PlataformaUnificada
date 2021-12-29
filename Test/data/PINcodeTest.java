package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PINcodeTest {
    static PINcode correct;
    static PINcode incorrect1;
    static PINcode incorrect2;
    static PINcode incorrect3;
    static PINcode incorrect4;

    @BeforeAll
    static void setupAll() {
        correct = new PINcode("321");
        incorrect1 = new PINcode("");
        incorrect2 = new PINcode("14"); // menos de 3 digitos
        incorrect3 = new PINcode("123456789012345"); // mas de 3 digitos
        incorrect4 = new PINcode("ABC"); //3 digitos caracteres
    }

    @Test
    public void getterPINcode() throws Exception{

        Assertions.assertEquals(correct.getPin(), "321");
        assertThrows(Exception.class, () -> incorrect1.getPin());
        assertThrows(Exception.class, () -> incorrect2.getPin());
        assertThrows(Exception.class, () -> incorrect3.getPin());
        assertThrows(Exception.class, () -> incorrect4.getPin());

    }

    @Test
    public void erroresPINcode() {

        Assertions.assertTrue(correct.compPINCode());
        Assertions.assertFalse(incorrect1.compPINCode());
        Assertions.assertFalse(incorrect2.compPINCode());
        Assertions.assertFalse(incorrect3.compPINCode());
        Assertions.assertFalse(incorrect4.compPINCode());

    }

}
