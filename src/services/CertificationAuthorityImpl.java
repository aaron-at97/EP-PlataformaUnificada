package services;

import data.*;
import services.exceptions.*;
import publicadministration.exceptions.AnyMobileRegisteredException;
import java.net.ConnectException;
import java.util.*;

public class CertificationAuthorityImpl implements CertificationAuthority {

    Map<Nif, Password> listPermanente = new HashMap<>();
    Map<Nif, Byte> listTypePermanente = new HashMap<>();
    Map<Nif, Date> listClave = new HashMap<>();
    Map<Nif, PINcode> generatePIN = new HashMap<>();
    Map<EncryptingKey, Nif > certDigital = new HashMap<>();
    Map<Nif, String> telNum = new HashMap<>();

    public CertificationAuthorityImpl(Map<EncryptingKey, Nif > certDigital) {
        cuentasCertDigital(certDigital);
    }
    public CertificationAuthorityImpl() {
        cuentasClave2();
    }

    @Override
    public boolean sendPIN(Nif nif, Date date) throws NifNotRegisteredException, IncorrectValDateException, AnyMobileRegisteredException, ConnectException {
        if (!this.listClave.containsKey(nif)) {
            throw new NifNotRegisteredException("");
        }
        if (telNum.get(nif) == null) {
            throw new AnyMobileRegisteredException("");
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

        if (!this.listTypePermanente.containsKey(nif)) {
            throw new NifNotRegisteredException("");
        }

        if (telNum.get(nif) == null) {
            throw new AnyMobileRegisteredException("");
        }

        if (this.listTypePermanente.get(nif)==0 ) {
            return 0;
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

    @Override
    public EncryptedData sendCertfAuth(EncryptingKey pubKey) throws NotValidCertificateException, ConnectException {

        if (!this.certDigital.containsKey(pubKey) || this.certDigital.get(pubKey) == null) {
            throw new NotValidCertificateException("");
        }
        try {

            Decryptor decryptor = new Decryptor();
            return new EncryptedData(decryptor.getEncrypted(this.certDigital.get(pubKey), pubKey).getBytes());

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

    private void cuentasCertDigital(Map<EncryptingKey, Nif > certDigital) {
        this.certDigital = certDigital;
    }

    private void cuentasClave2() {

        this.listClave.put(new Nif("78545954N"), new Date(2021 - 1900, Calendar.APRIL, 6, 17, 30));
        this.listClave.put(new Nif("28148954S"), new Date(2021 - 1900, Calendar.AUGUST, 7, 4, 8));
        this.listPermanente.put(new Nif("59168954S"), new Password("S12a3v4652"));
        this.listPermanente.put(new Nif("98748978T"), new Password("56835Da6825"));

        this.telNum.put(new Nif("78545954N"),"6123456789");
        this.telNum.put(new Nif("98748978T"),"665987123");
        this.telNum.put(new Nif("59168954S"),"656421789");

        this.listTypePermanente.put(new Nif("78545954N"), (byte) 0);
        this.listTypePermanente.put(new Nif("28148954S"), (byte) 0);
        this.listTypePermanente.put(new Nif("59168954S"), (byte) 1);
        this.listTypePermanente.put(new Nif("98748978T"), (byte) 2);

    }

}
