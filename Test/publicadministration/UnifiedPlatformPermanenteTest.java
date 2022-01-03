package publicadministration;

import data.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.exceptions.AnyKeyWordProcedureException;
import services.CertificationAuthority;
import services.Decryptor;
import services.SS;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnifiedPlatformPermanenteTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;
    static Map<Nif, Password> listPermanente = new HashMap<>();
    static Map<Nif, String> telNum = new HashMap<>();
    static Map<Nif, Byte> listTypePermanente = new HashMap<>();
    static LaboralLifeDoc laboralLife;
    static MemberAccreditationDoc accreditationDoc;
    private static QuotePeriodsColl qPdC;
    private static QuotePeriod qPd, qPd2, qPd3;

    @BeforeAll
    static void init() throws Exception {

        qPd = new QuotePeriod(new Date(2020-1900, Calendar.FEBRUARY, 18)  , 3);
        qPd2 = new QuotePeriod(new Date(2020-1900, Calendar.JULY, 5) , 128);
        qPd3 = new QuotePeriod(new Date(2018-1900, Calendar.AUGUST, 5) , 58);
        qPdC = new QuotePeriodsColl();
        qPdC.addQuotePeriod(qPd);
        qPdC.addQuotePeriod(qPd2);
        qPdC.addQuotePeriod(qPd3);
    }

    @BeforeEach
    void setUp() {

        ss = new SSTest();

        listPermanente.put(new Nif("59168954S"), new Password("S12a3v4652"));
        listPermanente.put(new Nif("98748978T"), new Password("56835Da6825"));

        telNum.put(new Nif("98748978T"),"665987123");
        telNum.put(new Nif("59168954S"),"656421789");

        listTypePermanente.put(new Nif("59168954S"), (byte) 1);
        listTypePermanente.put(new Nif("98748978T"), (byte) 2);

        datosCertificationAuth = new CertAuthorityTest();

        up = new UnifiedPlatform(datosCertificationAuth, ss);
    }

    @Test
    void selcheckCredTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 1);
        up.enterCred(new Nif("59168954S"), new Password("S12a3v4652"));

        laboralLife = new LaboralLifeDoc(new Nif("59168954S"), qPdC);

        assertEquals(laboralLife, up.getSs().getLaboralLife(up.nif));
    }

    @Test
    void selcheckCredAccreditationTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 1);
        up.selectAuthMethod((byte) 1);
        up.enterCred(new Nif("59168954S"), new Password("S12a3v4652"));

        accreditationDoc = new MemberAccreditationDoc(new Nif("59168954S"), new AccredNumb("252132563551"));

        assertEquals(accreditationDoc, up.getSs().getMembAccred(up.nif));
    }

    @Test
    void selcheckReforzadaTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 1);
        up.enterCred(new Nif("98748978T"), new Password("56835Da6825"));
        up.enterPIN(new PINcode("123"));

        laboralLife = new LaboralLifeDoc(new Nif("98748978T"), qPdC);

        assertEquals(laboralLife, up.getSs().getLaboralLife(up.nif));

    }

    @Test
    void selcheckReforzadaMemberAccredTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 1);
        up.selectAuthMethod((byte) 1);
        up.enterCred(new Nif("98748978T"), new Password("56835Da6825"));
        up.enterPIN(new PINcode("123"));

        accreditationDoc = new MemberAccreditationDoc(new Nif("98748978T"), new AccredNumb("126984823551"));

        assertEquals(accreditationDoc, up.getSs().getMembAccred(up.nif));
    }


    private static class CertAuthorityTest implements CertificationAuthority {

        @Override
        public boolean sendPIN(Nif nif, Date date) {
            return true;
        }

        @Override
        public boolean checkPIN(Nif nif, PINcode pin) {
            return true;
        }

        @Override
        public byte checkCredent(Nif nif, Password passw) {
            return listTypePermanente.get(nif);
        }

        @Override
        public EncryptedData sendCertfAuth(EncryptingKey pubKey) {
            return null;
        }

    }


    private static class SSTest implements SS {
        @Override
        public LaboralLifeDoc getLaboralLife(Nif nif) {
            return laboralLife;
        }

        @Override
        public MemberAccreditationDoc getMembAccred(Nif nif) {
            return accreditationDoc;
        }
    }

}
