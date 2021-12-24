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
        assertEquals( 3,qPd.getNumDays());
        assertEquals( 128, qPd2.getNumDays());
        assertNotEquals(20, qPd.getNumDays());
        assertNotEquals(11, qPd.getNumDays());
    }


    @Test
    void getInitDay() {
        assertEquals(new Date(2020-1900, Calendar.FEBRUARY, 18), qPd.getInitDay());
        assertEquals(new Date(2020-1900, Calendar.JULY, 5), qPd2.getInitDay());
        assertNotEquals(new Date(2020-1900, Calendar.MARCH, 6), qPd.getInitDay());
        assertNotEquals(new Date(2022-1900, Calendar.AUGUST, 5), qPd2.getInitDay());
    }



}
