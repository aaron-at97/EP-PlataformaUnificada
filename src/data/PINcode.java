package data;

import static java.lang.Character.isDigit;

final public class PINcode {
    private final String pin;

    public PINcode(String code) {
        this.pin = code;
    }

    public String getPin() throws Exception {
        if (!CompPINCode())
            throw new Exception("The PIN is not valid. \n");
        return pin;
    }

    public Boolean CompPINCode() {

        if (pin == null)
            return false;

        char[] codeArray = pin.toCharArray();
        if (pin.length() == 3) {
            for (int i = 0; i < 3; i++) {
                if (!isDigit(codeArray[i])) {
                    return false;
                }
            }
        }
        return pin.length() == 3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PINcode piNcode = (PINcode) o;
        return pin.equals(piNcode.pin);
    }

    @Override
    public int hashCode() {
        return pin.hashCode();
    }

    @Override
    public String toString() {
        return "PINcode{" +
                "pin='" + pin + '\'' +
                '}';
    }
}

