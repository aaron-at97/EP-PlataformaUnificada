package publicadministration;

import data.*;
import data.exceptions.*;
import publicadministration.exceptions.*;
import data.exceptions.NifNotRegisteredException;
import services.exceptions.NotAffiliatedException;


import java.net.ConnectException;
import java.util.Date;

public class UnifiedPlatform {
 //??? // The class members

    // Input events
    public void processSearcher () {  };
    public void enterKeyWords (String keyWord) throws AnyKeyWordProcedureException{  }
    public void selects () {  };
    public void selectCitizens () {  };
    public void selectReports () {  };
    public void selectCertificationReport (byte opc) {  };
    public void selectAuthMethod (byte opc) {  };
    public void enterNIF_PINobt (Nif nif, Date valDate) throws NifNotRegisteredException, IncorrectValDateException, AnyMobileRegisteredException, ConnectException {

    }
    public void enterPIN (PINcode pin) throws NotValidPINException, NotAffiliatedException, ConnectException { }
    public void enterCred (Nif nif, Password passw) throws NifNotRegisteredException, NotValidCredException, AnyMobileRegisteredException, ConnectException {  }
    private void printDocument () throws BadPathException, PrintingException {  } ;
    private void downloadDocument () {  };
    private void selectPath (DocPath path) throws BadPathException {  }
    // Other operations
    private String searchKeyWords (String keyWord) throws AnyKeyWordProcedureException { return keyWord; }
    private void OpenDocument (DocPath path) throws BadPathException {  }
    private void printDocument (DocPath path) throws BadPathException, PrintingException {  }
    private void downloadDocument (DocPath path) throws BadPathException {  }
 //??? // Possibly more operations
}
