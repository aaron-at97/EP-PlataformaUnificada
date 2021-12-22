package publicadministration;

import data.Nif;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaboralLifeDoc that = (LaboralLifeDoc) o;
        return Objects.equals(nif, that.nif) && Objects.equals(quotePds, that.quotePds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, quotePds);
    }

    @Override
    public String toString() {
        return "LaboralLifeDoc{" +
                "nif=" + nif +
                ", quotePds=" + quotePds +
                '}';
    }
}
