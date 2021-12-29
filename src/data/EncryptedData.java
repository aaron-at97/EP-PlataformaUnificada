package data;

import java.util.Objects;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

final public class EncryptedData {
    private final byte[] password;

    public EncryptedData(byte[] code) {
        this.password = code;
    }

    public byte[] getPassword() {
        return password;
    }

    /*public Boolean CompPasswordCode() {
        int digit = 0, alpha =0;
        if (password == null)
            return false;

        char[] codeArray = password.toCharArray();
        if (password.length() >= 7) {
            for (int i = 0; i < password.length(); i++) {
                if (!isDigit(codeArray[i])) {
                    digit++;
                }
                if (!isAlphabetic(codeArray[i])) {
                    alpha++;
                }
            }
        }
        return digit >= 1 && alpha >= 1;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptedData password1 = (EncryptedData) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        return "Password{" +
                "password='" + password + '\'' +
                '}';
    }
}

