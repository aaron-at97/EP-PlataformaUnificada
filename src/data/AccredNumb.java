package data;

import static java.lang.Character.isDigit;

final public class AccredNumb {

    private final String ssNum; //  ej SS:  25 21325635 51

    public AccredNumb(String code) {
        this.ssNum = code;
    }

    public String getssNum() throws Exception {
        if (!CompSSCode())
            throw new Exception("The SS num is not valid. \n");
        return ssNum;
    }

    public Boolean CompSSCode() {
        if (ssNum == null)
            return false;

        char[] codeArray = ssNum.toCharArray();
        if (ssNum.length() == 12) {
            for (int i = 0; i < 12; i++) {
                if (!isDigit(codeArray[i])) {
                    return false;
                }
            }
        }
        return ssNum.length() == 12;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccredNumb that = (AccredNumb) o;
        return ssNum.equals(that.ssNum);
    }

    @Override
    public int hashCode() {
        return ssNum.hashCode();
    }

    @Override
    public String toString() {
        return "AccredNumb{" +
                "ssNum='" + ssNum + '\'' +
                '}';
    }

}

