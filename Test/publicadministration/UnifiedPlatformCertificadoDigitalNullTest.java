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

public class UnifiedPlatformCertificadoDigitalNullTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;

    @BeforeEach
    void setUp() {

        ss = new SSTest();

        datosCertificationAuth = new CertAuthorityTest();

        up = new UnifiedPlatform();
        up.setSs(ss);
        up.setCert(datosCertificationAuth);

    }

    @Test
    void selcheckCertDigitalNotValidPasswordTest() {
        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 1);
        up.selectAuthMethod((byte) 2);
        up.selectCertificate((byte) 1);

        assertThrows(NotValidPasswordException.class, () -> up.enterPassw(new Password("12a687sa1asa")));
    }

    @Test
    void selcheckCertDigitalDecryptThrowsTest() {
        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 1);
        up.selectAuthMethod((byte) 2);
        up.selectCertificate((byte) 1);

        assertThrows(DecryptationException.class, () -> up.decryptIDdata(null));

    }

    @Test
    void selcheckCertDigitalNotValidCertificateException() {
        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 1);
        up.selectAuthMethod((byte) 2);
        up.selectCertificate((byte) 1);

        assertThrows(NotValidCertificateException.class, () -> datosCertificationAuth.sendCertfAuth(null));

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
