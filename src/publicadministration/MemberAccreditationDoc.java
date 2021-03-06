package publicadministration;

import data.*;

import java.io.File;
import java.util.Objects;

public class MemberAccreditationDoc extends PDFDocument {
    // Represents the member accreditation document
    private Nif nif;
    private AccredNumb numAffil;

    public MemberAccreditationDoc(Nif nif, AccredNumb nAff) {
        super();
        this.nif = nif;
        this.numAffil = nAff;
    }

    public Nif getNif() {
        return nif;
    }

    public AccredNumb getNumAffil() {
        return numAffil;
    }

    @Override
    public File getFile() {
        return super.getFile();
    }

    @Override
    public DocPath getPath() {
        return super.getPath();
    }

    @Override
    public void setFile(File file) {
        super.setFile(file);
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
