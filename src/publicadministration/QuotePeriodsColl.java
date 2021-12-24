package publicadministration;

import java.util.ArrayList;
import java.util.List;

public class QuotePeriodsColl { // Represents the total quote periods known as a registered worker
    // Its components, that is, the set of quote periods
    List<QuotePeriod> listQuote;

    public QuotePeriodsColl() {
        this.listQuote = new ArrayList<>();
    } // Initializes the object

    public List<QuotePeriod> getListQuote() {
        return listQuote;
    }

// the getters

    public void addQuotePeriod(QuotePeriod qPd) {
        int len = listQuote.size();
        boolean flag = false;
        for (int i = 0; i < len; i++) {
            if (qPd.getInitDay().before(listQuote.get(i).getInitDay()) && !flag) {
                listQuote.add(i, qPd);
                flag = true;
            }
        }

        if (listQuote.size()==0 || !flag) {
            listQuote.add(qPd);
        }


    } // Adds a quote period, always respecting the sorting by date, from oldest to later in time

    @Override
    public String toString() {
        return "QuotePeriodsColl{" +
                "listQuote=" + listQuote +
                '}';
    }
    //public String toString () {} // Converts to String

}
