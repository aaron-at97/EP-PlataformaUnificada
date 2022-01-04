package publicadministration;

import data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.exceptions.AnyKeyWordProcedureException;
import publicadministration.exceptions.AnyMobileRegisteredException;
import publicadministration.exceptions.DecryptationException;
import publicadministration.exceptions.NotValidPasswordException;
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

public class UnifiedPlatformPermanenteNullTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;
    static Map<Nif, Password> listPermanente = new HashMap<>();
    static Map<Nif, String> telNum = new HashMap<>();


    @BeforeEach
    void setUp() {

        ss = new SSTest();

        listPermanente.put(new Nif("59168954S"), new Password("S12a3v4652"));

        datosCertificationAuth = new CertAuthorityTest();

        up = new UnifiedPlatform();
        up.setSs(ss);
        up.setCert(datosCertificationAuth);
    }

    @Test
    void selEnterCredNotValidCredest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 1);

        assertThrows(NotValidCredException.class, () -> up.enterCred(new Nif("12345954N"), new Password("S12a3v4652")));

        assertThrows(NotValidCredException.class, () -> up.enterCred(new Nif("59168954S"), null));

        assertThrows(NotValidCredException.class, () -> up.enterCred(new Nif("59168954S"), new Password("a1757867687FDF5")));

        assertThrows(NotValidCredException.class, () -> up.enterCred(null, new Password("S12a3v4652")));

    }

    @Test
    void selcheckCredAnyMobileTest() {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 1);

        assertThrows(AnyMobileRegisteredException.class, () -> up.enterCred(new Nif("59168954S"), new Password("S12a3v4652")));

        telNum.put(new Nif("59168954S"), "612456789");

        assertThrows(NifNotRegisteredException.class, () -> up.enterCred(new Nif("59168954S"), new Password("S12a3v4652")));
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
        public boolean sendPIN(Nif nif, Date date) {
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
