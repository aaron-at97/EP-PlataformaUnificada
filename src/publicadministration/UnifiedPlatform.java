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
    MemberAccreditationDoc acreditacion;
    LaboralLifeDoc vidalab;
    Byte opcion;

    public UnifiedPlatform(){
        this.doc = new PDFDocument();
    }

    // Input events
    public void processSearcher () {

    }

    public void enterKeyWords (String keyWord) throws AnyKeyWordProcedureException{
        String res = byKeyWord(keyWord);
        if (res==null) {
            throw new AnyKeyWordProcedureException(" No existe el tramite ");
        }
        System.out.println(res);
   }

    public void selects () {

    }

    public void selectCitizens () {

    }

    public void selectReports () {

    }

    public void selectCertificationReport (byte opc) {

    }

    public void selectAuthMethod (byte opc) {

    }

    public void enterNIF_PINobt(Nif nif, Date valDate) throws NifNotRegisteredException,
            IncorrectValDateException, AnyMobileRegisteredException, ConnectException
    {
        try {
            cert.sendPIN(nif, valDate);
            this.nif=nif;
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
    private String searchKeyWords (String keyWord) throws AnyKeyWordProcedureException {
        String res = byKeyWord(keyWord);
        if (res==null) {
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
    private String byKeyWord(String keyWord) {
        Map<String, String> listkeyWord = new HashMap<>();
        listkeyWord.put("vida laboral", "SS");
        listkeyWord.put("numero de afiliacion", "SS");
        for (int i=0; i <listkeyWord.size(); i++) {
            if (listkeyWord.containsKey(keyWord)) {
                return listkeyWord.get(keyWord);
            }
        }
        return null;
    }
 //??? // Possibly more operations
}
