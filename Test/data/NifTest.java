package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class NifTest {
    static Nif correct;
    static Nif incorrect1;
    static Nif incorrect2;
    static Nif incorrect3;
    static Nif incorrect4;
    static Nif incorrect5;


    @BeforeAll
    static void setupAll() {
        correct = new Nif("78198957N");
        incorrect1 = new Nif("12345678910N"); // mas de 10 digitos
        incorrect2 = new Nif("781989578"); // 0 caracteres
        incorrect3 = new Nif("12345678NA"); //mas caracteres
        incorrect4 = new Nif("1234567890AAAA"); //diferente orden
        incorrect5 = new Nif("");
    }

    @Test
    void equalsTest() throws Exception {

        Assertions.assertEquals(correct.getNif(), "78198957N");
        assertThrows(Exception.class, () ->
            incorrect1.getNif());
        assertThrows(Exception.class, () ->
            incorrect2.getNif());
        assertThrows(Exception.class, () ->
            incorrect3.getNif());
        assertThrows(Exception.class, () ->
            incorrect4.getNif());
        assertThrows(Exception.class, () ->
            incorrect5.getNif());
    }

    @Test
    void erroresTest() {

        Assertions.assertTrue(correct.codeNif());
        Assertions.assertFalse(incorrect1.codeNif());
        Assertions.assertFalse(incorrect2.codeNif());
        Assertions.assertFalse(incorrect3.codeNif());
        Assertions.assertFalse(incorrect4.codeNif());
        Assertions.assertFalse(incorrect5.codeNif());

    }

}
