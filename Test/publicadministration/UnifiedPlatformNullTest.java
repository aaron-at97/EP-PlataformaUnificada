package publicadministration;

import data.Nif;
import data.PINcode;
import data.Password;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.exceptions.AnyMobileRegisteredException;
import services.CertificationAuthority;
import services.SS;
import services.exceptions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UnifiedPlatformNullTest {

    static CertificationAuthority datosCertificationAuth;
    static SS ss;
    static UnifiedPlatform up;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    static Map<Nif, Password> listPermanente = new HashMap<>();
    static Map<Nif, Byte> listTypePermanente = new HashMap<>();
    static Map<Nif, Date> listClave = new HashMap<>();
    static Map<Nif, String> telNum = new HashMap<>();

    @BeforeAll
    static void init(){

        ss = new SSTest();

        listClave.put(new Nif("78545954N"), new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));
        listPermanente.put(new Nif("59168954S"), new Password("S12a3v4652"));
        listTypePermanente.put(new Nif("78545954N"), (byte) 0);
        telNum.put(new Nif("78545954N"), null);

        datosCertificationAuth = new CertAuthorityTest();
    }

    @BeforeEach
    static void setUp(){
        up = new UnifiedPlatform(datosCertificationAuth, ss);
    }


    @Test
    void sendTest() {
        System.setOut(new PrintStream(outContent));
        up.selectCitizens();
        assertEquals("Metodo que precede no realizado \n", outContent.toString());
        System.setOut(originalOut);


    }

    private static class CertAuthorityTest implements CertificationAuthority {

        @Override
        public boolean sendPIN(Nif nif, Date date) throws IncorrectValDateException, AnyMobileRegisteredException {
            if (!(listClave.containsKey(nif) && listClave.get(nif).equals(date))) {
                throw new IncorrectValDateException("");
            } else if (!(telNum.get(nif) == null)) {
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
            } else if (!(telNum.get(nif) == null)) {
                throw new AnyMobileRegisteredException("");
            }
            return 0;
        }
    }




    private static class SSTest implements SS {
        @Override
        public LaboralLifeDoc getLaboralLife(Nif nif){
            return null;
        }

        @Override
        public MemberAccreditationDoc getMembAccred(Nif nif) {
            return null;
        }
    }
}
