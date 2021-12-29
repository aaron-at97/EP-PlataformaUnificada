package publicadministration;

import data.Nif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import publicadministration.exceptions.DuplicatedQuotedPeriodOrNullException;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LaboralLifeDocTest {

    static QuotePeriodsColl qPdC,qPdC2;
    static LaboralLifeDoc lLd, lLd2;

    @BeforeEach
    void init() throws DuplicatedQuotedPeriodOrNullException {

        QuotePeriod qPd = new QuotePeriod(new Date(2020-1900, Calendar.FEBRUARY, 18)  , 3);
        QuotePeriod qPd2 = new QuotePeriod(new Date(2020-1900, Calendar.JULY, 5) , 128);
        QuotePeriod qPd3 = new QuotePeriod(new Date(2018-1900, Calendar.AUGUST, 5) , 58);
        QuotePeriod qPd4 = new QuotePeriod(new Date(2020-1900, Calendar.SEPTEMBER, 6) , 5);
        qPdC = new QuotePeriodsColl();
        qPdC2 = new QuotePeriodsColl();
        qPdC.addQuotePeriod(qPd);
        qPdC.addQuotePeriod(qPd2);
        qPdC.addQuotePeriod(qPd3);
        qPdC2.addQuotePeriod(qPd2);
        qPdC2.addQuotePeriod(qPd4);

        lLd = new LaboralLifeDoc(new Nif("78545954N"),qPdC);
        lLd2 = new LaboralLifeDoc(new Nif("28148954S"),qPdC2);

    }

    @Test
    void getQuotePdsTest() {

        assertEquals(qPdC, lLd.getQuotePds());
        assertEquals(qPdC2, lLd2.getQuotePds());
        assertNotEquals(qPdC2,  lLd.getQuotePds());
        assertNotEquals(qPdC, lLd2.getQuotePds());

    }

    @Test
    void getNifTest() {

        assertEquals(new Nif("78545954N"), lLd.getNif());
        assertEquals(new Nif("28148954S"), lLd2.getNif());
        assertNotEquals(new Nif("63154697M"),  lLd.getNif());
        assertNotEquals(new Nif("15948954T"), lLd2.getNif());

    }


}
