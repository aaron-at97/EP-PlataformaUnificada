package publicadministration;

import data.*;
import data.exceptions.*;
import publicadministration.exceptions.*;
import services.*;
import services.exceptions.*;


import java.net.ConnectException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class UnifiedPlatform {

    CertificationAuthority cert;
    SS ss;
    Nif nif;
    LaboralLifeDoc laboralLifeDoc;
    MemberAccreditationDoc accreditationDoc;
    PDFDocument doc;
    byte opcion, opAuth;
    PINcode pin;
    EncryptedData data;
    EncryptingKey key;
    EncryptingKey priKey;
    Password password;
    boolean searcher , selects, selCitizens, selReports, selAuth, checkPIN;

    public UnifiedPlatform(CertificationAuthority cert, SS ss) {
        this.cert = cert;
        this.ss = ss;
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
            String res = searchKeyWords(keyWord);
            System.out.print(res);
        } else {
            System.out.print("Metodo que precede no realizado \n");
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
        if (selReports) {
            selAuth = true;
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
        if (selAuth) {
            opAuth = opc;
        } else {
            System.out.print("Metodo que precede no realizado \n");
        }
    }

    //Cl@ve PIN
    public void enterNIF_PINobt(Nif nif, Date valDate) throws NifNotRegisteredException,
            IncorrectValDateException, AnyMobileRegisteredException, ConnectException {
        if (opAuth == 0) {
            try {
                if (nif == null) {
                    throw new IncorrectValDateException("No posible validar cuenta nif nulo");
                }
                if (valDate == null) {
                    throw new IncorrectValDateException("data nula");
                }
                if (!cert.sendPIN(nif, valDate)) {
                    throw new NifNotRegisteredException("No posible validar cuenta");
                }
                this.nif = nif;
                this.checkPIN = true;
            } catch (ConnectException ce) {
                throw new ConnectException();
            }
        }
    }

    //Cl@ve PIN & Cl@ve Permanente
    public void enterPIN(PINcode pin) throws NotValidPINException, NotAffiliatedException, ConnectException {
        if (checkPIN) {
            try {
                if (!cert.checkPIN(nif, pin)) {
                    throw new NotValidPINException("");
                }
                informes();
            } catch (ConnectException e) {
                throw new ConnectException("" + e);
            }
        }
    }

    // Opcional Cl@ve Permanente
    public void enterCred(Nif nif, Password passw) throws NifNotRegisteredException, NotValidCredException,
            AnyMobileRegisteredException, ConnectException {
        if (opAuth == 1) {
            try {
                byte metodo;
                if (nif == null) {
                    throw new NotValidCredException("No posible validar cuenta nif nulo");
                }
                if (passw == null) {
                    throw new NotValidCredException("Password nula");
                }
                metodo = cert.checkCredent(nif, passw);
                this.nif = nif;

                if (metodo == 0) {
                    throw new NifNotRegisteredException("NIF no registrado al sistema Permanente");
                } else if (metodo == 1) {
                    System.out.println(metodo);
                    informes();
                } else if (metodo == 2) {
                    this.checkPIN = true;
                }
            } catch (ConnectException | NotAffiliatedException e) {
                throw new ConnectException("" + e);
            }
        }
    }

    // Certificado Digital

    public void selectCertificate(byte opc) {
        if (opAuth == 2) {
            setPassword(checkCertificate(opc));
        }
    }

    public void enterPassw(Password pas) throws NotValidPasswordException {
        if (!getPassword().equals(pas)) {
            throw new NotValidPasswordException("");
        }
        try {
            Decryptor decryptor = new Decryptor();
            setKey(decryptor.getPublicKey());
            priKey = decryptor.getPrivateKey();
            System.out.println(getKey());
            data = cert.sendCertfAuth(getKey());

        } catch (NoSuchAlgorithmException | NotValidCertificateException | ConnectException e) {
            throw new NotValidPasswordException("" + e);
        }
    }

    Nif decryptIDdata(EncryptedData encrypData) throws DecryptationException {
        if (encrypData == null) {
            throw new DecryptationException("El nif encriptado es nulo ");
        }
        try {
            Decryptor decryptor = new Decryptor();

            this.nif = decryptor.decryptIDdata(encrypData, priKey);

            if (this.nif == null) {
                throw new DecryptationException(" Error al desencriptado del nif");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.nif;
    }

    // Fin Certificado Digital


    private void printDocument() throws BadPathException, PrintingException {}
    private void downloadDocument() {}
    private void selectPath(DocPath path) throws BadPathException {}
    private void printDocument(DocPath path) throws BadPathException, PrintingException {}
    private void downloadDocument(DocPath path) throws BadPathException {}

    // Other operations
    private String searchKeyWords(String keyWord) throws AnyKeyWordProcedureException {

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

        throw new AnyKeyWordProcedureException(" tramite no encontrado con la KeyWord ");
    }

    private Password checkCertificate(byte opc) {

        Map<Byte, Password> listCertificate = new HashMap<>();
        listCertificate.put((byte) 0, new Password("1654654646adsa"));
        listCertificate.put((byte) 1, new Password("68bb6bva1asa"));
        listCertificate.put((byte) 2, new Password("A4d1321dww12"));
        listCertificate.put((byte) 3, new Password("46as4d13asd"));

        if (listCertificate.containsKey(opc)) {
            return listCertificate.get(opc);
        }
        return null;
    }

    public void OpenDocument(DocPath path) throws BadPathException {

        if (path==null) {
            throw new BadPathException("Ruta tramites incorrecta");
        }

        try {
            doc.openDoc(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void informes() throws NotAffiliatedException, ConnectException {
        if (opcion == 0) {
            if (ss.getLaboralLife(nif) == null) {
                throw new NotAffiliatedException("");
            }
            setLaboralLifeDoc(ss.getLaboralLife(nif));
        } else if (opcion == 1) {
            if (ss.getMembAccred(nif) == null) {
                throw new NotAffiliatedException("");
            }
            setAccreditationDoc(ss.getMembAccred(nif));
        }
    }

    public void setCert(CertificationAuthority cert) {
        this.cert = cert;
    }

    public SS getSs() {
        return ss;
    }

    public Nif getNif() {
        return nif;
    }

    public void setNif(Nif nif) {
        this.nif = nif;
    }

    public PINcode getPin() {
        return pin;
    }

    public void setCheckPIN(boolean checkPIN) {
        this.checkPIN = checkPIN;
    }

    public void setOpcion(byte opcion) {
        this.opcion = opcion;
    }

    public LaboralLifeDoc getLaboralLifeDoc() {
        return laboralLifeDoc;
    }

    public void setLaboralLifeDoc(LaboralLifeDoc laboralLifeDoc) {
        this.laboralLifeDoc = laboralLifeDoc;
    }

    public MemberAccreditationDoc getAccreditationDoc() {
        return accreditationDoc;
    }

    public void setAccreditationDoc(MemberAccreditationDoc accreditationDoc) {
        this.accreditationDoc = accreditationDoc;
    }
    public EncryptedData getData() {
        return data;
    }

    public void setData(EncryptedData data) {
        this.data = data;
    }

    public EncryptingKey getKey() {
        return key;
    }

    public void setKey(EncryptingKey key) {
        this.key = key;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
