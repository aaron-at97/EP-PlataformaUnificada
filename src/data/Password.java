package data;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

final public class Password {
    private final String password;

    public Password(String code) {
        this.password = code;
    }

    public String getPassword() throws Exception {
        if (!CompPasswordCode())
            throw new Exception("The password is not valid. \n");
        return password;
    }

    public Boolean CompPasswordCode() {
        int digit = 0, alpha =0;
        if (password == null)
            return false;

        char[] codeArray = password.toCharArray();
        if (password.length() == 7) {
            for (int i = 0; i < 7; i++) {
                if (!isDigit(codeArray[i])) {
                    digit++;
                }
                if (!isAlphabetic(codeArray[i])) {
                    alpha++;
                }
            }
        }
        return digit >= 1 && alpha >= 1;
    }

}

