package publicadministration;

import data.DocPath;
import data.exceptions.BadPathException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class QuotePeriodCollTest {

    private QuotePeriod qPd, qPd2, qPd3;
    private QuotePeriodsColl qPdC;

    @BeforeEach
    void init(){

        qPd = new QuotePeriod(new Date(2020-1900, Calendar.FEBRUARY, 18)  , 3);
        qPd2 = new QuotePeriod(new Date(2020-1900, Calendar.JULY, 5) , 128);
        qPd3 = new QuotePeriod(new Date(2018-1900, Calendar.AUGUST, 5) , 58);
        qPdC = new QuotePeriodsColl();

    }

    @Test
    void addPeriodTest() {

        qPdC.addQuotePeriod(qPd);
        qPdC.addQuotePeriod(qPd2);
        qPdC.addQuotePeriod(qPd3);

        List<QuotePeriod> listQuote = new ArrayList<>();

        listQuote.add(qPd3);
        listQuote.add(qPd);
        listQuote.add(qPd2);

        assertEquals(listQuote, qPdC.getListQuote());

    }

    @Test
    void getInitDay() {

        assertEquals(qPd.getInitDay(), new Date(2020-1900, Calendar.FEBRUARY, 18));
        assertEquals(qPd2.getInitDay(), new Date(2020-1900, Calendar.JULY, 5));
        assertNotEquals(qPd.getInitDay(), new Date(2020-1900, Calendar.MARCH, 6));
        assertNotEquals(qPd2.getInitDay(), new Date(2022-1900, Calendar.AUGUST, 5));

    }



}
