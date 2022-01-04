package publicadministration;

import data.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.exceptions.AnyKeyWordProcedureException;
import publicadministration.exceptions.DecryptationException;
import publicadministration.exceptions.NotValidPasswordException;
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

public class UnifiedPlatformCertificadoDigitalTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;
    static Map<Nif, LaboralLifeDoc> laboralLife = new HashMap<>();
    static Map<Nif, MemberAccreditationDoc> accreditationDoc = new HashMap<>();
    private static QuotePeriodsColl qPdC;
    private static QuotePeriod qPd, qPd2, qPd3;
    static EncryptedData data;
    static Map<EncryptingKey, Nif> certDigital = new HashMap<>();
    static Decryptor decryptor;

    @BeforeAll
    static void init() throws Exception {
        decryptor = new Decryptor();
        qPd = new QuotePeriod(new Date(2020 - 1900, Calendar.FEBRUARY, 18), 3);
        qPd2 = new QuotePeriod(new Date(2020 - 1900, Calendar.JULY, 5), 128);
        qPd3 = new QuotePeriod(new Date(2018 - 1900, Calendar.AUGUST, 5), 58);
        qPdC = new QuotePeriodsColl();
        qPdC.addQuotePeriod(qPd);
        qPdC.addQuotePeriod(qPd2);
        qPdC.addQuotePeriod(qPd3);
    }

    @BeforeEach
    void setUp() {

        laboralLife.put(new Nif("98748978T"), new LaboralLifeDoc(new Nif("59168954S"), qPdC));
        accreditationDoc.put(new Nif("98748978T"), new MemberAccreditationDoc(new Nif("59168954S"), new AccredNumb("252132563551")));
        laboralLife.put(new Nif("19874897B"), new LaboralLifeDoc(new Nif("98748978T"), qPdC));
        accreditationDoc.put(new Nif("19874897B"), new MemberAccreditationDoc(new Nif("98748978T"), new AccredNumb("321498563551")));

        ss = new SSTest();
        datosCertificationAuth = new CertAuthorityTest();

        up = new UnifiedPlatform();
        up.setSs(ss);
        up.setCert(datosCertificationAuth);

    }

    @Test
    void selcheckCertDigitalTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 0);
        up.selectAuthMethod((byte) 2);
        up.selectCertificate((byte) 0);
        up.enterPassw(new Password("1654654646adsa"));

        certDigital.put(up.getKey(), new Nif("98748978T"));
        data = new EncryptedData(decryptor.getEncrypted(certDigital.get(up.getKey()), up.getKey()).getBytes());
        System.out.println(data);
        up.setData(new EncryptedData(decryptor.getEncrypted(new Nif("98748978T"), up.getKey()).getBytes()));

        assertEquals(new Nif("98748978T"), up.decryptIDdata(up.getData()));
        assertEquals(laboralLife.get(new Nif("98748978T")), up.getSs().getLaboralLife(up.nif));
    }

    @Test
    void selcheckCertDigitalMemberAccredTest() throws Exception {

        up.selects();
        up.selectCitizens();
        up.selectReports();
        up.selectCertificationReport((byte) 1);
        up.selectAuthMethod((byte) 2);
        up.selectCertificate((byte) 1);
        up.enterPassw(new Password("68bb6bva1asa"));

        certDigital.put(up.getKey(), new Nif("19874897B"));
        data = new EncryptedData(decryptor.getEncrypted(certDigital.get(up.getKey()), up.getKey()).getBytes());
        System.out.println(data);
        up.setData(new EncryptedData(decryptor.getEncrypted(new Nif("19874897B"), up.getKey()).getBytes()));

        assertEquals(new Nif("19874897B"), up.decryptIDdata(up.getData()));
        assertEquals(accreditationDoc.get(new Nif("19874897B")), up.getSs().getMembAccred(up.nif));
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
            return data;
        }
    }


    private static class SSTest implements SS {
        @Override
        public LaboralLifeDoc getLaboralLife(Nif nif) {
            return laboralLife.get(nif);
        }

        @Override
        public MemberAccreditationDoc getMembAccred(Nif nif) {
            return accreditationDoc.get(nif);
        }
    }
}
