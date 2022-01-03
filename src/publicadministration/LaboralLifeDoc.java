package publicadministration;

import data.*;

import java.io.File;
import java.util.Objects;

public class LaboralLifeDoc extends PDFDocument { // Represents the laboral life
    private Nif nif;
    private QuotePeriodsColl quotePds;

    public LaboralLifeDoc (Nif nif, QuotePeriodsColl qtP) {
        super();
        this.nif=nif;
        this.quotePds=qtP;
    }

    public Nif getNif() {
        return nif;
    }

    public QuotePeriodsColl getQuotePds() {
        return quotePds;
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
        LaboralLifeDoc that = (LaboralLifeDoc) o;
        return Objects.equals(nif, that.nif) && Objects.equals(quotePds, that.quotePds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashCode(), nif, quotePds);
    }

    @Override
    public String toString() {
        return "LaboralLifeDoc{" +
                "nif=" + nif +
                ", quotePds=" + quotePds +
                '}';
    }
}
