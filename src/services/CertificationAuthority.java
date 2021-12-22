package services;

import data.*;
import data.exceptions.IncorrectValDateException;
import data.exceptions.NifNotRegisteredException;
import data.exceptions.NotValidCredException;
import data.exceptions.NotValidPINException;
import publicadministration.exceptions.*;

import java.net.ConnectException;
import java.util.Date;

public interface CertificationAuthority {// External service that represents the different trusted certification entities
    boolean sendPIN (Nif nif, Date date) throws NifNotRegisteredException,
            IncorrectValDateException, AnyMobileRegisteredException, ConnectException;
    boolean checkPIN (Nif nif, PINcode pin) throws NotValidPINException,
            ConnectException;
    byte ckeckCredent (Nif nif, Password passw) throws NifNotRegisteredException,
            NotValidCredException, AnyMobileRegisteredException, ConnectException;
}
