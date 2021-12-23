package publicadministration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class QuotePeriodTest {

    private QuotePeriod qPd, qPd2;


    @BeforeEach
    void init(){

        qPd = new QuotePeriod(new Date(2020-1900, Calendar.FEBRUARY, 18)  , 3);
        qPd2 = new QuotePeriod(new Date(2020-1900, Calendar.JULY, 5) , 128);
    }

    @Test
    void equalsTest() {
        QuotePeriod equals = new QuotePeriod(new Date(2020-1900, Calendar.FEBRUARY, 18)  , 3);
        QuotePeriod equals1 = new QuotePeriod(new Date(2020-1900, Calendar.JULY, 5) , 128);

        assertEquals(qPd, equals);
        assertEquals(qPd2, equals1);
    }

    @Test
    void notEqualsTest() {
        QuotePeriod notEqual = new QuotePeriod(new Date(2020-1900, Calendar.MARCH, 6) , 3);
        QuotePeriod notEqual2 = new QuotePeriod(new Date(2022-1900, Calendar.AUGUST, 5) , 11);


        assertNotEquals(qPd, notEqual);
        assertNotEquals(qPd2, notEqual2);

    }

    @Test
    void getNumDays() {
        assertEquals(qPd.getNumDays(), 3);
        assertEquals(qPd2.getNumDays(), 128);
        assertNotEquals(qPd.getNumDays(), 20);
        assertNotEquals(qPd2.getNumDays(), 11);
    }


    @Test
    void getInitDay() {
        assertEquals(qPd.getInitDay(), new Date(2020-1900, Calendar.FEBRUARY, 18));
        assertEquals(qPd2.getInitDay(), new Date(2020-1900, Calendar.JULY, 5));
        assertNotEquals(qPd.getInitDay(), new Date(2020-1900, Calendar.MARCH, 6));
        assertNotEquals(qPd2.getInitDay(), new Date(2022-1900, Calendar.AUGUST, 5));
        System.out.println(qPd.getInitDay());
    }



}
