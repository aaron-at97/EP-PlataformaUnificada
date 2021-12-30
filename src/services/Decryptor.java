package services;

import data.EncryptingKey;
import data.Nif;

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

    public String getEncrypted(Nif nif, EncryptingKey pubKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(new String(pubKey.getKey().toByteArray()))));

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        System.out.println(nif.getNif().getBytes());
        byte[] encryptedbytes = cipher.doFinal(nif.getNif().getBytes());
        return new String(Base64.getEncoder().encode(encryptedbytes));

    }

    public String getDecrypted(String data, EncryptingKey priKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PrivateKey pk = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(new String(priKey.getKey().toByteArray()))));

        cipher.init(Cipher.DECRYPT_MODE, pk);

        return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes())));

    }

    public EncryptingKey getPublicKey() throws NoSuchAlgorithmException {
        /*BigInteger result = getPublicKey();
        System.out.println(result);
        System.out.println(new String(result.toByteArray()));*/

        String pubKey = new String(Base64.getEncoder().encode(this.keyPair.getPublic().getEncoded()));

        byte[] inputStringBytes = pubKey.getBytes();
        return new EncryptingKey(new BigInteger(inputStringBytes));
    }

    public EncryptingKey getPrivateKey() throws NoSuchAlgorithmException {

        String priKey = new String(Base64.getEncoder().encode(this.keyPair.getPrivate().getEncoded()));

        byte[] inputStringBytes = priKey.getBytes();
        return new EncryptingKey(new BigInteger(inputStringBytes));
    }

}
