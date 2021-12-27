package services;

import data.Nif;
import data.PINcode;
import data.Password;
import services.exceptions.IncorrectValDateException;
import services.exceptions.NifNotRegisteredException;
import services.exceptions.NotValidCredException;
import services.exceptions.NotValidPINException;
import publicadministration.exceptions.AnyMobileRegisteredException;

import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CertificationAuthorityImpl implements CertificationAuthority {

    Map<Nif, Password> listPermanente = new HashMap<>();
    Map<Nif, Byte> listTypePermanente = new HashMap<>();
    Map<Nif, Date> listClave = new HashMap<>();
    Map<Nif, PINcode> generatePIN = new HashMap<>();

    public CertificationAuthorityImpl() {
        cuentasClave();
    }

    @Override
    public boolean sendPIN(Nif nif, Date date) throws NifNotRegisteredException, IncorrectValDateException, AnyMobileRegisteredException, ConnectException {
        if (!this.listClave.containsKey(nif)) {
            throw new NifNotRegisteredException("");
        }
        boolean res = buscarCheckNif(nif, date);

        if (!res) {
            throw new IncorrectValDateException("");
        }

        try {
            generatePIN(nif);

        } catch (Exception e) {
            throw new ConnectException("");
        }
        return res;
    }

    @Override
    public boolean checkPIN(Nif nif, PINcode pin) throws NotValidPINException, ConnectException {
        if (!this.generatePIN.containsKey(nif) || !this.generatePIN.get(nif).equals(pin)) {
            throw new NotValidPINException("");
        }

        try {
            return this.generatePIN.get(nif).equals(pin);
        } catch (Exception e) {
            throw new ConnectException("");
        }
    }

    @Override
    public byte checkCredent(Nif nif, Password passw) throws NifNotRegisteredException, NotValidCredException, AnyMobileRegisteredException, ConnectException {

        if (!this.listPermanente.containsKey(nif)) {
            throw new NifNotRegisteredException("");
        }

        if (!buscarCheckPermanent(nif, passw)) {
            throw new NotValidCredException("");
        }

        try {
            if (this.listTypePermanente.get(nif) == 2) {
                generatePIN(nif);
            }

            return this.listTypePermanente.get(nif);

        } catch (Exception e) {
            throw new ConnectException("");
        }

    }

    private boolean buscarCheckNif(Nif nif, Date date) {
        return this.listClave.containsKey(nif) && this.listClave.get(nif).equals(date);
    }

    private boolean buscarCheckPermanent(Nif nif, Password passw) {
        return this.listPermanente.get(nif).equals(passw);
    }

    private void generatePIN(Nif nif) {
        this.generatePIN.put(nif, new PINcode("123"));
    }

    private void cuentasClave() {

        this.listClave.put(new Nif("78545954N"), new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));
        this.listClave.put(new Nif("28148954S"), new Date(2021 - 1900, Calendar.AUGUST, 7, 4, 8));
        this.listPermanente.put(new Nif("59168954S"), new Password("S12a3v4652"));
        this.listPermanente.put(new Nif("98748978T"), new Password("56835Da6825"));

        this.listTypePermanente.put(new Nif("59168954S"), (byte) 1);
        this.listTypePermanente.put(new Nif("98748978T"), (byte) 2);

    }

}
