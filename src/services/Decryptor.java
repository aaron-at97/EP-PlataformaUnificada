package services;

import data.EncryptedData;
import data.EncryptingKey;
import data.Nif;
import publicadministration.exceptions.DecryptationException;

import javax.crypto.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Decryptor {

    KeyGenerator keyGenerator;
    KeyPairGenerator keyPairGenerator;
    KeyPair keyPair;

    public Decryptor() throws NoSuchAlgorithmException {

        this.keyGenerator = KeyGenerator.getInstance("Blowfish");
        this.keyGenerator.init(448);
        this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        this.keyPairGenerator.initialize(1024);
        this.keyPair = keyPairGenerator.genKeyPair();

    }

    public Nif decryptIDdata(EncryptedData encrypData, EncryptingKey privKey) throws Exception {

        String decryptedText;

        try {
            decryptedText = getDecrypted(new String(encrypData.getData()), privKey);
        } catch (Exception e) {
            throw new Exception("");
        }

        if (decryptedText == null) {
            throw new DecryptationException("");
        }

        return new Nif(decryptedText);
    }

    public String getEncrypted(Nif nif, EncryptingKey pubKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(new String(pubKey.getKey().toByteArray()))));

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedbytes = cipher.doFinal(nif.getNif().getBytes());
        return new String(Base64.getEncoder().encode(encryptedbytes));

    }

    public String getDecrypted(String data, EncryptingKey priKey) throws Exception, DecryptationException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PrivateKey pk = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(new String(priKey.getKey().toByteArray()))));

        cipher.init(Cipher.DECRYPT_MODE, pk);

        return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes())));

    }

    public EncryptingKey getPublicKey() {

        String pubKey = new String(Base64.getEncoder().encode(this.keyPair.getPublic().getEncoded()));

        byte[] inputStringBytes = pubKey.getBytes();
        return new EncryptingKey(new BigInteger(inputStringBytes));
    }

    public EncryptingKey getPrivateKey(){

        String priKey = new String(Base64.getEncoder().encode(this.keyPair.getPrivate().getEncoded()));

        byte[] inputStringBytes = priKey.getBytes();
        return new EncryptingKey(new BigInteger(inputStringBytes));
    }

}
