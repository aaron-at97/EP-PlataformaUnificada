package services;

        import data.*;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import services.exceptions.*;

        import java.math.BigInteger;
        import java.util.*;
        import static org.junit.jupiter.api.Assertions.*;

public class CertificationAuthorityImplTest {

    static CertificationAuthority agenda;
    Decryptor decrypt;
    Map<EncryptingKey, Nif > certDigital;
    EncryptingKey key;
    @BeforeEach
    void init() throws Exception {

        agenda = new CertificationAuthorityImpl();
        decrypt = new Decryptor();
        certDigital = new HashMap<>();
        key = new EncryptingKey(decrypt.getPublicKey().getKey());
        certDigital.put(key, new Nif("19874897B"));

    }

    @Test
    void sendTest() throws Exception {

        assertTrue(agenda.sendPIN(new Nif("78545954N"), new Date(2021-1900, Calendar.APRIL, 6, 17,30)));
        assertTrue(agenda.sendPIN(new Nif("28148954S"), new Date(2021 - 1900, Calendar.AUGUST, 7, 4, 8)));

        assertThrows(NifNotRegisteredException.class, () -> agenda.sendPIN(new Nif("87448231Y"),
                new Date(2021 - 1900, Calendar.AUGUST, 7, 4, 8)));

        assertThrows(IncorrectValDateException.class, () -> agenda.sendPIN(new Nif("28148954S"),
                new Date(2021 - 1900, Calendar.SEPTEMBER, 8, 1, 50)));

    }

    @Test
    void checkPinTest() throws Exception {
        agenda.sendPIN(new Nif("78545954N"), new Date(2021-1900, Calendar.APRIL, 6, 17,30));
        agenda.sendPIN(new Nif("28148954S"), new Date(2021 - 1900, Calendar.AUGUST, 7, 4, 8));
        assertTrue(agenda.checkPIN(new Nif("78545954N"), new PINcode("123")));
        assertTrue(agenda.checkPIN(new Nif("28148954S"), new PINcode("123")));

        assertThrows(NotValidPINException.class, () -> agenda.checkPIN(new Nif("78545954N"), new PINcode("568")));



    }

    @Test
    void creedTest() throws Exception {

        assertEquals(1, agenda.checkCredent(new Nif("59168954S"), new Password("S12a3v4652")));
        assertEquals(2, agenda.checkCredent(new Nif("98748978T"), new Password("56835Da6825")));
        assertEquals(0, agenda.checkCredent(new Nif("78545954N"), null));
        assertEquals(0, agenda.checkCredent(new Nif("28148954S"), null));

        assertThrows(NifNotRegisteredException.class, () -> agenda.checkCredent(new Nif("756668954S"), new Password("S12a3v4652")));
        assertThrows(NotValidCredException.class, () -> agenda.checkCredent(new Nif("98748978T"), new Password("14535Da6825")));

    }

    @Test
    void sendCertfAuthTest() throws Exception {

        agenda = new CertificationAuthorityImpl(certDigital);
        System.out.println(key);
        EncryptedData cipherText = agenda.sendCertfAuth(key);

        Decryptor decryptor = new Decryptor();
        System.out.println(key);

        System.out.println("CHIPHER:" + cipherText);
        String decryptedText = decrypt.getDecrypted(new String(cipherText.getData()), decrypt.getPrivateKey());
        System.out.println("DECRYPTED STRING:" + decryptedText);

    }
    @Test
    void sendCertfAuthErroresTest() {

        agenda = new CertificationAuthorityImpl(certDigital);
        assertThrows(NotValidCertificateException.class, () -> agenda.sendCertfAuth(new EncryptingKey(BigInteger.ONE)));
        assertThrows(NotValidCertificateException.class, () -> agenda.sendCertfAuth(null));

    }

}
