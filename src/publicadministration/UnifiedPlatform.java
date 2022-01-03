package publicadministration;

import data.*;
import data.exceptions.*;
import publicadministration.exceptions.*;
import services.*;
import services.exceptions.*;

import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class UnifiedPlatform {
    //??? // The class members

    CertificationAuthority cert;
    SS ss;
    Nif nif;
    PDFDocument doc;
    byte opcion, selAuth;
    PINcode pin;
    boolean searcher, selects, selCitizens, selReports;

    public UnifiedPlatform(CertificationAuthority cert, SS ss) {
        this.searcher = false;
        this.selects = false;
        this.selCitizens = false;
        this.selReports = false;
        this.cert = cert;
        this.ss = ss;
        this.pin = null;
        this.doc = new PDFDocument();
    }

    // Input events
    public void processSearcher() {
        searcher = true;
        System.out.println("------------------------------");
        System.out.println("UnifiedPlatform");
        System.out.println("------------------------------");
    }

    public void enterKeyWords(String keyWord) throws AnyKeyWordProcedureException {
        if (searcher) {
            String res = byKeyWord(keyWord);
            if (res == null) {
                throw new AnyKeyWordProcedureException(" tramite no encontrado con la KeyWord ");
            }
            System.out.println(res);
        }
    }

    public void selects() {
        selects = true;
        System.out.println("Apartado SS ");
    }

    public void selectCitizens() {
        if (selects) {
            selCitizens = true;
        } else {
            System.out.print("Metodo que precede no realizado \n");
        }
    }

    public void selectReports() {
        if (selCitizens) {
            selReports = true;
            System.out.println("------------------------------");
            System.out.println("Selecciona el tramite a realizar: ");
            System.out.println("0: Obtener vida laboral ");
            System.out.println("1: Obtener número de afiliación ");
            System.out.println("------------------------------");
        } else {
            System.out.print("Metodo que precede no realizado \n");
        }
    }

    public void selectCertificationReport(byte opc) {
        if (selCitizens) {
            selReports = true;
            System.out.println("------------------------------");
            System.out.println("Selecciona el metodo de autentificacion ");
            System.out.println("0: Cl@ve PIN ");
            System.out.println("1: Cl@ve Permanente ");
            System.out.println("2: Certificado Digital ");
            System.out.println("------------------------------");
            opcion = opc;
        } else {
            System.out.print("Metodo que precede no realizado \n");
        }
    }

    public void selectAuthMethod(byte opc) {
        if (selCitizens) {
            selReports = true;
            selAuth = opc;
        } else {
            System.out.print("Metodo que precede no realizado \n");
        }

    }

    //Cl@ve PIN
    public void enterNIF_PINobt(Nif nif, Date valDate) throws NifNotRegisteredException,
            IncorrectValDateException, AnyMobileRegisteredException, ConnectException {
        if (selAuth==0) {
            try {
                if (nif == null) {
                    throw new NifNotRegisteredException("No posible validar cuenta nif nulo");
                }
                if (valDate == null) {
                    throw new IncorrectValDateException("data nula");
                }
                if (!cert.sendPIN(nif, valDate)) {
                    throw new NifNotRegisteredException("No posible validar cuenta");
                }
                this.nif = nif;
            } catch (ConnectException ce) {
                throw new ConnectException();
            }
        }
    }

    //Cl@ve PIN & Cl@ve Permanente
    public void enterPIN(PINcode pin) throws NotValidPINException, NotAffiliatedException, ConnectException {

        try {
            if (!cert.checkPIN(nif, pin)) {
                throw new NotValidPINException("");
            }
            informes();
        } catch (ConnectException e) {
            throw new ConnectException("" + e);
        }
    }

    // Opcional Cl@ve Permanente
    public void enterCred(Nif nif, Password passw) throws NifNotRegisteredException, NotValidCredException,
            AnyMobileRegisteredException, ConnectException {
        if (selAuth==1) {
            try {
                byte metodo;
                if (nif == null) {
                    throw new NifNotRegisteredException("No posible validar cuenta nif nulo");
                }
                if (passw == null) {
                    throw new NotValidCredException("Password nula");
                }
                metodo = cert.checkCredent(nif, passw);
                this.nif = nif;

                if (metodo == 0) {
                    throw new NifNotRegisteredException("NIF no registrado al sistema Permanente");
                } else if (metodo == 1) {
                    informes();
                } else if (metodo == 2) {
                    enterPIN(pin);
                }
            } catch (ConnectException | NotAffiliatedException | NotValidPINException e) {
                throw new ConnectException("" + e);
            }
        }
    }

    // Certificado Digital
    public void selectCertificate(byte opc){
        if (selAuth==2) {

        }
    }
    public void enterPassw(Password pas) throws NotValidPasswordException {

    }
    Nif decryptIDdata(EncryptedData encrypData) throws DecryptationException {
        return this.nif;
    }
    Nif decryptIDdata(EncryptedData encrypData, EncryptingKey privKey) throws DecryptationException{
        return this.nif;
    }

    // Fin Certificado Digital

    private void printDocument() throws BadPathException, PrintingException {
        if (doc.getPath() == null) {
            throw new BadPathException("Ruta tramites incorrecta");
        }

    }

    private void downloadDocument() {
    }

    private void selectPath(DocPath path) throws BadPathException {
        if (path==null) {
            throw new BadPathException("Ruta tramites incorrecta");
        }
    }

    // Other operations
    private String searchKeyWords(String keyWord) throws AnyKeyWordProcedureException {
        String res = byKeyWord(keyWord);
        if (res == null) {
            throw new AnyKeyWordProcedureException(" tramite no encontrado con la KeyWord ");
        }
        return res;
    }

    void OpenDocument(DocPath path) throws BadPathException {

        if (!doc.getPath().equals(path)) {
            throw new BadPathException("Ruta tramites incorrecta");
        }

        try {
            doc.openDoc(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void printDocument(DocPath path) throws BadPathException, PrintingException {

        if (!doc.getPath().equals(path)) {
            throw new BadPathException("Ruta tramites incorrecta");
        }

        System.out.println("Okay");
    }

    private void downloadDocument(DocPath path) throws BadPathException {
        if (!doc.getPath().equals(path)) {
            throw new BadPathException("Ruta tramites incorrecta");
        }
    }

    private String byKeyWord(String keyWord) {
        Map<String, String> listkeyWord = new HashMap<>();
        listkeyWord.put("vida laboral", "SS");
        listkeyWord.put("numero de afiliacion", "SS");
        listkeyWord.put("afiliacion", "SS");
        listkeyWord.put("laboral", "SS");
        listkeyWord.put("datos fiscales", "AEAT");
        listkeyWord.put("declaración de la renta", "AEAT");
        listkeyWord.put("puntos carnet", "DGT");
        listkeyWord.put("certificado de nacimiento", "MJ");

        if (listkeyWord.containsKey(keyWord)) {
            return listkeyWord.get(keyWord);
        }
        return null;
    }

    private void informes() throws NotAffiliatedException, ConnectException {
        if (opcion == 0) {
            if (ss.getLaboralLife(nif) == null) {
                throw new NotAffiliatedException("");
            }
        } else if (opcion == 1) {
            if (ss.getLaboralLife(nif) == null) {
                throw new NotAffiliatedException("");
            }
        }
    }

    public CertificationAuthority getCert() {
        return cert;
    }

    public void setCert(CertificationAuthority cert) {
        this.cert = cert;
    }

    public SS getSs() {
        return ss;
    }

    public void setSs(SS ss) {
        this.ss = ss;
    }

    public Nif getNif() {
        return nif;
    }

    public void setNif(Nif nif) {
        this.nif = nif;
    }

    public PDFDocument getDoc() {
        return doc;
    }

    public void setDoc(PDFDocument doc) {
        this.doc = doc;
    }

    public PINcode getPin() {
        return pin;
    }

    public void setPin(PINcode pin) {
        this.pin = pin;
    }


    //??? // Possibly more operations
}
