package publicadministration;

import data.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.exceptions.AnyKeyWordProcedureException;
import publicadministration.exceptions.AnyMobileRegisteredException;
import services.CertificationAuthority;
import services.SS;
import services.exceptions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UnifiedPlatformNullTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    static Map<Nif, Password> listPermanente = new HashMap<>();
    static Map<Nif, Date> listClave = new HashMap<>();
    static Map<Nif, String> telNum = new HashMap<>();

    @BeforeAll
    static void init() {

        ss = new SSTest();

        listClave.put(new Nif("78545954N"), new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));
        listPermanente.put(new Nif("59168954S"), new Password("S12a3v4652"));

        datosCertificationAuth = new CertAuthorityTest();
    }

    @BeforeEach
    void setUp() {
        up = new UnifiedPlatform(datosCertificationAuth, ss);
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
    void selReportTest() {

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
        assertThrows(IncorrectValDateException.class, () -> up.enterNIF_PINobt(null, new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30)));

    }

    @Test
    void selReportNifNotRegistrerTest() {

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
    void selReporAnyMobileTest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 0);
        assertThrows(AnyMobileRegisteredException.class, () -> up.enterNIF_PINobt(new Nif("78545954N"),
                new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30)));

    }

    @Test
    void selcheckCredTest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 1);
        assertThrows(NotValidCredException.class, () -> up.enterCred(new Nif("12345954N"),
                new Password("S12a3v4652")));
        assertThrows(NotValidCredException.class, () -> up.enterCred(new Nif("59168954S"), null));
        assertThrows(NotValidCredException.class, () -> up.enterCred(new Nif("59168954S"),
                new Password("a1757867687FDF5")));
        assertThrows(NotValidCredException.class, () -> up.enterCred(null, new Password("S12a3v4652")));

    }

    @Test
    void selcheckCredAnyMobileTest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 1);

        assertThrows(AnyMobileRegisteredException.class, () -> up.enterCred(new Nif("59168954S"),
                new Password("S12a3v4652")));

        telNum.put(new Nif("59168954S"), "612456789");

        assertThrows(NifNotRegisteredException.class, () -> up.enterCred(new Nif("59168954S"),
                new Password("S12a3v4652")));
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
        public byte checkCredent(Nif nif, Password passw) throws NotValidCredException, AnyMobileRegisteredException {
            if (!(listPermanente.containsKey(nif) && listPermanente.get(nif).equals(passw))) {
                throw new NotValidCredException("");
            } else if ((telNum.get(nif) == null)) {
                throw new AnyMobileRegisteredException("");
            }
            return 0;
        }

        @Override
        public EncryptedData sendCertfAuth(EncryptingKey pubKey) {
            return null;
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
