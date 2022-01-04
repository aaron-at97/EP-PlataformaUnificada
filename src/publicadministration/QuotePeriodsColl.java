package publicadministration;

import publicadministration.exceptions.DuplicatedQuotedPeriodOrNullException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuotePeriodsColl { // Represents the total quote periods known as a registered worker
    // Its components, that is, the set of quote periods
    List<QuotePeriod> listQuote;

    public QuotePeriodsColl() {
        this.listQuote = new ArrayList<>();
    } // Initializes the object

    public List<QuotePeriod> getListQuote() {
        return listQuote;
    }


    public void addQuotePeriod(QuotePeriod qPd) throws DuplicatedQuotedPeriodOrNullException {
        int len = listQuote.size();
        boolean flag = false;
        if (qPd == null) {
            throw new DuplicatedQuotedPeriodOrNullException("Elemento nulo");
        }
        for (int i = 0; i < len; i++) {
            if (qPd.getInitDay().equals(listQuote.get(i).getInitDay())) {
                throw new DuplicatedQuotedPeriodOrNullException("Elemento duplicado");
            }
            if (qPd.getInitDay().before(listQuote.get(i).getInitDay()) && !flag) {
                listQuote.add(i, qPd);
                flag = true;
            }
        }

        if (listQuote.size() == 0 || !flag) {
            listQuote.add(qPd);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuotePeriodsColl that = (QuotePeriodsColl) o;
        return Objects.equals(listQuote, that.listQuote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listQuote);
    }

    @Override
    public String toString() {
        return "QuotePeriodsColl{" +
                "listQuote=" + listQuote +
                '}';
    }

}
