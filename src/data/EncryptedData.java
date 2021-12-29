package data;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

final public class EncryptedData {
    private final byte[] data;

    public EncryptedData(byte[] code) {
        this.data = code;
    }

    public byte[] getData() throws Exception {
        if (!compDataCode())
            throw new Exception("The encrypted data is not valid. \n");
        return data;
    }

    public Boolean compDataCode() {
        return data != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptedData that = (EncryptedData) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "EncryptedData{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}

