package publicadministration;

import data.*;
import data.exceptions.*;
import publicadministration.exceptions.*;
import services.*;
import services.exceptions.NotAffiliatedException;


import java.io.IOException;
import java.net.ConnectException;
import java.util.Date;
import java.util.Objects;

public class UnifiedPlatform {
 //??? // The class members
    CertificationAuthority cert;
    SS ss;
    Nif nif;
    PDFDocument doc;
    MemberAccreditationDoc acreditacion;
    LaboralLifeDoc vidalab;
    Byte opcion;

    public UnifiedPlatform(){
        this.doc = new PDFDocument();
    }
    // Input events
    public void processSearcher () {  }
    public void enterKeyWords (String keyWord) throws AnyKeyWordProcedureException{

    }
    public void selects () {  }

    public void selectCitizens () {  }

    public void selectReports () {  }

    public void selectCertificationReport (byte opc) {  }

    public void selectAuthMethod (byte opc) { }

    public void enterNIF_PINobt (Nif nif, Date valDate) throws NifNotRegisteredException,
            IncorrectValDateException, AnyMobileRegisteredException, ConnectException
    {
        try {
            cert.sendPIN(nif, valDate);
        }
        catch (ConnectException ce){
            throw new ConnectException();
        }
    }
    public void enterPIN (PINcode pin) throws NotValidPINException, NotAffiliatedException, ConnectException {

        cert.checkPIN(nif, pin);
    }
    //opcional
    public void enterCred (Nif nif, Password passw) throws NifNotRegisteredException, NotValidCredException,
            AnyMobileRegisteredException, ConnectException {
        try {
            cert.checkCredent(nif, passw);
        } catch (ConnectException e) {
            throw new ConnectException();
        }

    } // clave permanente

    //
    private void printDocument () throws BadPathException, PrintingException {
        if (doc.getPath()==null) {
            throw new BadPathException("Ruta tramites incorrecta");
        }

    }
    private void downloadDocument () {  }
    private void selectPath (DocPath path) throws BadPathException {
        if (doc.getPath() != path) {
            throw new BadPathException("Ruta tramites incorrecta");
        }
    }

    // Other operations
    private String searchKeyWords (String keyWord) throws AnyKeyWordProcedureException { return keyWord; }
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

    private void printDocument (DocPath path) throws BadPathException, PrintingException {

        if (!doc.getPath().equals(path)) {
            throw new BadPathException("Ruta tramites incorrecta");
        }
        if (opcion == null) {
        throw new PrintingException(" Error al printar, tramite vacio");
    }
        System.out.println("Okay");
    }

    private void downloadDocument (DocPath path) throws BadPathException {
        if (!doc.getPath().equals(path)) {
            throw new BadPathException("Ruta tramites incorrecta");
        }
    }
 //??? // Possibly more operations
}
