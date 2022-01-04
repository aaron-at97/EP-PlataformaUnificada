package publicadministration;

import data.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UnifiedPlatformClavePINOpenDocSearchTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    static Map<Nif, Date> listClave = new HashMap<>();
    static Map<Nif, String> telNum = new HashMap<>();
    static LaboralLifeDoc laboralLife;
    static MemberAccreditationDoc accreditationDoc;
    private static QuotePeriodsColl qPdC;
    static QuotePeriod qPd, qPd2, qPd3;


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

        laboralLife = new LaboralLifeDoc(new Nif("7854954N"), qPdC);
        accreditationDoc = new MemberAccreditationDoc(new Nif("7854954N"), new AccredNumb("252132563551"));

        ss = new SSTest();

        listClave.put(new Nif("78545954N"), new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));
        telNum.put(new Nif("78545954N"),"6123456789");


        datosCertificationAuth = new CertAuthorityTest();
        up = new UnifiedPlatform();
        up.setSs(ss);
        up.setCert(datosCertificationAuth);
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
    void openDocUnifiedPlatform() throws Exception {
        up.OpenDocument(new DocPath("src/Docs/"));
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
            return laboralLife;
        }
        @Override
        public MemberAccreditationDoc getMembAccred(Nif nif) {
            return accreditationDoc;
        }
    }
}
