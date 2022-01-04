package publicadministration;

import java.util.Date;
import java.util.Objects;

public class QuotePeriod { // Represents a quote period as a registered worker
    private Date initDay;
    private int numDays;

    public QuotePeriod(Date date, int ndays) {
        this.initDay = date;
        this.numDays = ndays;
    }

    public Date getInitDay() {
        return initDay;
    }

    public int getNumDays() {
        return numDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuotePeriod that = (QuotePeriod) o;
        return numDays == that.numDays && initDay.equals(that.initDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initDay, numDays);
    }

    @Override
    public String toString() {
        return "QuotePeriod{" +
                "initDay=" + initDay +
                ", numDays=" + numDays +
                '}';
    }
}
