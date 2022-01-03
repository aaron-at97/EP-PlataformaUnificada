package publicadministration;

import data.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.exceptions.AnyKeyWordProcedureException;
import services.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UnifiedPlatformNormalTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    static Map<Nif, Password> listPermanente = new HashMap<>();
    static Map<Nif, Date> listClave = new HashMap<>();
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
        laboralLife = new LaboralLifeDoc(new Nif("7854954N"), qPdC);
        accreditationDoc = new MemberAccreditationDoc(new Nif("7854954N"), new AccredNumb("252132563551"));

        ss = new SSTest();

        listClave.put(new Nif("78545954N"), new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));
        listPermanente.put(new Nif("59168954S"), new Password("S12a3v4652"));
        listPermanente.put(new Nif("98748978T"), new Password("56835Da6825"));

        telNum.put(new Nif("78545954N"),"6123456789");
        telNum.put(new Nif("98748978T"),"665987123");
        telNum.put(new Nif("59168954S"),"656421789");

        listTypePermanente.put(new Nif("78545954N"), (byte) 0);
        listTypePermanente.put(new Nif("28148954S"), (byte) 0);
        listTypePermanente.put(new Nif("59168954S"), (byte) 1);
        listTypePermanente.put(new Nif("98748978T"), (byte) 2);
        datosCertificationAuth = new CertAuthorityTest();

    }

    @BeforeEach
    void setUp() {
        up = new UnifiedPlatform(datosCertificationAuth, ss);
    }

    @Test
    void procesSearcherKeyWordTest() throws Exception {
        up.processSearcher();
        System.setOut(new PrintStream(outContent));
        up.enterKeyWords("vida laboral");

        assertEquals("SS", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void procesSearcherKeyWordThrowsTest() {
        up.processSearcher();
        assertThrows(AnyKeyWordProcedureException.class, () -> up.enterKeyWords("lab"));
    }

    @Test
    void selClavePINTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 0);
        up.enterNIF_PINobt(new Nif("78545954N"),
                new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));
        up.enterPIN(new PINcode("123"));
        assertEquals(up.getLaboralLifeDoc(), up.getSs().getLaboralLife(up.nif));

    }

    @Test
    void selClavePINMemberAccredTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 1);
        up.selectAuthMethod((byte) 0);
        up.enterNIF_PINobt(new Nif("78545954N"),
                new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));
        up.enterPIN(new PINcode("123"));

        assertEquals(up.getAccreditationDoc(), up.getSs().getMembAccred(up.nif));
    }

    @Test
    void selcheckCredTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 1);
        up.enterCred(new Nif("59168954S"), new Password("S12a3v4652"));

        assertEquals(up.getLaboralLifeDoc(), up.getSs().getLaboralLife(up.nif));
    }

    @Test
    void selcheckCredAccreditationTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 1);
        up.selectAuthMethod((byte) 1);
        up.enterCred(new Nif("59168954S"), new Password("S12a3v4652"));

        assertEquals(up.getAccreditationDoc(), up.getSs().getMembAccred(up.nif));
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
        assertEquals(up.getLaboralLifeDoc(), up.getSs().getLaboralLife(up.nif));
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
        assertEquals(up.getAccreditationDoc(), up.getSs().getMembAccred(up.nif));
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
