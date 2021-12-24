package services;

import data.Nif;
import data.PINcode;
import data.Password;
import data.exceptions.IncorrectValDateException;
import data.exceptions.NifNotRegisteredException;
import data.exceptions.NotValidCredException;
import data.exceptions.NotValidPINException;
import publicadministration.exceptions.AnyMobileRegisteredException;

import java.net.ConnectException;
import java.util.Date;

public class CertificationAuthorityImpl implements CertificationAuthority{

    @Override
    public boolean sendPIN(Nif nif, Date date) throws NifNotRegisteredException, IncorrectValDateException, AnyMobileRegisteredException, ConnectException {
        return false;
    }

    @Override
    public boolean checkPIN(Nif nif, PINcode pin) throws NotValidPINException, ConnectException {
        return false;
    }

    @Override
    public byte checkCredent(Nif nif, Password passw) throws NifNotRegisteredException, NotValidCredException, AnyMobileRegisteredException, ConnectException {
        return 0;
    }
}
