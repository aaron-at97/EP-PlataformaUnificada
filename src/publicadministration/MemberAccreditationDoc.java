package publicadministration;

import data.*;

import java.util.Objects;

public class MemberAccreditationDoc extends PDFDocument {
    // Represents the member accreditation document
    private Nif nif;
    private AccredNumb numAffil;

    public MemberAccreditationDoc (Nif nif, AccredNumb nAff){
        this.nif=nif;
        this.numAffil=nAff;
    }

    public Nif getNif() {
        return nif;
    }

    public AccredNumb getNumAffil() {
        return numAffil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberAccreditationDoc that = (MemberAccreditationDoc) o;
        return Objects.equals(nif, that.nif) && Objects.equals(numAffil, that.numAffil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, numAffil);
    }

    @Override
    public String toString() {
        return "MemberAccreditationDoc{" +
                "nif=" + nif +
                ", numAffil=" + numAffil +
                '}';
    }

}
