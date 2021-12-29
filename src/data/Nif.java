package data;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

/**
 * Essential data classes
 */

final public class Nif {
    // The tax identification number in the Spanish state.

    private final String nif;

    public Nif (String code) { this.nif = code; }

    public String getNif () throws Exception{

        if(!codeNif()){
            throw new Exception("The Nif is not valid. \n");
        }
        return nif;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nif niff = (Nif) o;
        return nif.equals(niff.nif);
    }

    @Override
    public int hashCode () { return nif.hashCode(); }

    @Override
    public String toString () {
        return "Nif{" + "nif ciudadano='" + nif + '\'' + '}';
    }

    public Boolean codeNif(){

        if (nif == null)
            return false;

        char [] codeArray = nif.toCharArray();
        if (nif.length() != 9)
            return false;

        for (int i = 0; i < 8; i++){
            if(!isDigit(codeArray[i]))
                return false;
        }
        return isAlphabetic(codeArray[8]);
    }

}
