package publicadministration;

import data.*;
import data.exceptions.*;
import publicadministration.exceptions.*;
import services.*;
import services.exceptions.NotAffiliatedException;


import java.io.IOException;
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
    Byte opcion;
    boolean searcher, selects, selCitizens, selReports;
    private final Date actual = new Date();

    public UnifiedPlatform(CertificationAuthority cert, SS ss) {
        this.searcher = false;
        this.selects = false;
        this.selCitizens = false;
        this.selReports = false;
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
            String res = byKeyWord(keyWord);
            if (res == null) {
                throw new AnyKeyWordProcedureException(" No existe el tramite ");
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
            System.out.println("Metodo que precede no realizado ");
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
            System.out.println("Metodo que precede no realizado ");
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
            System.out.println("Metodo que precede no realizado ");
        }
    }

    public void selectAuthMethod(byte opc) {
        if (selCitizens) {
            selReports = true;

        } else {
            System.out.println("Metodo que precede no realizado ");
        }

    }

    public void enterNIF_PINobt(Nif nif, Date valDate) throws NifNotRegisteredException,
            IncorrectValDateException, AnyMobileRegisteredException, ConnectException {
        try {
            if (!cert.sendPIN(nif, valDate)) {
                throw new NifNotRegisteredException("No posible validar cuenta");
            }
            this.nif=nif;
            if (actual.after(valDate)) {
                throw new IncorrectValDateException("nif valdate no valido");
            }

        } catch (ConnectException ce) {
            throw new ConnectException();
        }
    }

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

    //opcional
    public void enterCred(Nif nif, Password passw) throws NifNotRegisteredException, NotValidCredException,
            AnyMobileRegisteredException, ConnectException {
        try {
            byte metodo;
            metodo = cert.checkCredent(nif, passw);
            this.nif=nif;

            if (metodo == 0) {
                throw new NotValidCredException("ddf");
            } else if (metodo == 1) {
                informes();
            }
            else if (metodo == 2) {
                PINcode pin = null;
                enterPIN(pin);
            }
        } catch (ConnectException | NotAffiliatedException | NotValidPINException e) {
            throw new ConnectException("" + e);
        }
    } // clave permanente

    //
    private void printDocument() throws BadPathException, PrintingException {
        if (doc.getPath() == null) {
            throw new BadPathException("Ruta tramites incorrecta");
        }

    }

    private void downloadDocument() {
    }

    private void selectPath(DocPath path) throws BadPathException {
        if (doc.getPath() != path) {
            throw new BadPathException("Ruta tramites incorrecta");
        }
    }

    // Other operations
    private String searchKeyWords(String keyWord) throws AnyKeyWordProcedureException {
        String res = byKeyWord(keyWord);
        if (res == null) {
            throw new AnyKeyWordProcedureException(" No existe el tramite ");
        }

        return res;
    }

    void OpenDocument(DocPath path) throws BadPathException {

        if (!doc.getPath().equals(path)) {
            throw new BadPathException("Ruta tramites incorrecta");
        }

        try {
            doc.openDoc(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printDocument(DocPath path) throws BadPathException, PrintingException {

        if (!doc.getPath().equals(path)) {
            throw new BadPathException("Ruta tramites incorrecta");
        }
        if (opcion == null) {
            throw new PrintingException(" Error al printar, tramite vacio");
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
        for (int i = 0; i < listkeyWord.size(); i++) {
            if (listkeyWord.containsKey(keyWord)) {
                return listkeyWord.get(keyWord);
            }
        }
        return null;
    }

    private void informes() throws NotAffiliatedException, ConnectException {
        if (opcion == 0) {
            if (ss.getLaboralLife(nif)==null) {
                throw new NotAffiliatedException("");
            }
        } else if (opcion == 1) {
            if (ss.getLaboralLife(nif)==null) {
                throw new NotAffiliatedException("");
            }
        }
    }
    //??? // Possibly more operations
}
