package data;

import java.math.BigInteger;
import java.util.Objects;

final public class EncryptingKey {
    private final BigInteger key;

    public EncryptingKey(BigInteger code) {
        this.key = code;
    }

    public BigInteger getKey() throws Exception {
        if (!compKeyCode()) {
            throw new Exception("The key is not valid. \n");
        }
        return key;
    }

    public Boolean compKeyCode() {
        return key != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptingKey that = (EncryptingKey) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "EncryptingKey{" +
                "key=" + key +
                '}';
    }
}

