package publicadministration;

import data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.exceptions.*;
import services.*;
import services.exceptions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UnifiedPlatformPINNullTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    static Map<Nif, Date> listClave = new HashMap<>();
    static Map<Nif, String> telNum = new HashMap<>();


    @BeforeEach
    void setUp() {

        ss = new SSTest();

        listClave.put(new Nif("78545954N"), new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));

        datosCertificationAuth = new CertAuthorityTest();

        up = new UnifiedPlatform();
        up.setSs(ss);
        up.setCert(datosCertificationAuth);
    }

    @Test
    void sendTest() {
        System.setOut(new PrintStream(outContent));
        up.selectCitizens();
        assertEquals("Metodo que precede no realizado \n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void selectestOrgTest() {
        System.setOut(new PrintStream(outContent));
        up.selects();
        up.selectCitizens();
        up.selectReports();
        assertNotEquals("Metodo que precede no realizado \n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void procesSearcherKeyWordThrowsTest() {
        up.processSearcher();
        assertThrows(AnyKeyWordProcedureException.class, () -> up.enterKeyWords("lab"));
    }

    @Test
    void selPINIncorrectValDateTest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 0);

        assertThrows(IncorrectValDateException.class, () -> up.enterNIF_PINobt(new Nif("78545954N"),
                new Date(2021 - 1900, Calendar.AUGUST, 5, 4, 35)));

        assertThrows(IncorrectValDateException.class, () -> up.enterNIF_PINobt(new Nif("78545954N"), null));

        assertThrows(IncorrectValDateException.class, () -> up.enterNIF_PINobt(new Nif("12345954N"),
                new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30)));

        assertThrows(IncorrectValDateException.class, () -> up.enterNIF_PINobt(null,
                new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30)));

    }

    @Test
    void selPINNifNotRegistrerTest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 0);
        telNum.put(new Nif("78545954N"), "601234567");

        assertThrows(NifNotRegisteredException.class, () -> up.enterNIF_PINobt(new Nif("78545954N"),
                new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30)));
        telNum.clear();
    }

    @Test
    void selPINAnyMobileTest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 0);

        assertThrows(AnyMobileRegisteredException.class, () -> up.enterNIF_PINobt(new Nif("78545954N"),
                new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30)));

    }

    @Test
    void selchekPINTest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 0);
        up.setCheckPIN(true);
        assertThrows(NotValidPINException.class, () -> up.enterPIN(up.getPin()));
        up.setOpcion((byte) 1);
        assertThrows(NotValidPINException.class, () -> up.enterPIN(up.getPin()));

    }

    private static class CertAuthorityTest implements CertificationAuthority {
        @Override
        public boolean sendPIN(Nif nif, Date date) throws IncorrectValDateException, AnyMobileRegisteredException {
            if (!(listClave.containsKey(nif) && listClave.get(nif).equals(date))) {
                throw new IncorrectValDateException("");
            } else if ((telNum.get(nif) == null)) {
                throw new AnyMobileRegisteredException("");
            }
            return false;
        }
        @Override
        public boolean checkPIN(Nif nif, PINcode pin) {
            return false;
        }
        @Override
        public byte checkCredent(Nif nif, Password passw) {
            return 0;
        }
        @Override
        public EncryptedData sendCertfAuth(EncryptingKey pubKey) throws NotValidCertificateException {
            throw new NotValidCertificateException("");
        }
    }

    private static class SSTest implements SS {
        @Override
        public LaboralLifeDoc getLaboralLife(Nif nif) {
            return null;
        }
        @Override
        public MemberAccreditationDoc getMembAccred(Nif nif) {
            return null;
        }
    }
}
