package services;

import data.AccredNumb;
import data.Nif;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import publicadministration.*;
import services.exceptions.NotAffiliatedException;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

public class SSImplTest {

    static SSImpl agenda;
    static MemberAccreditationDoc mAd;
    static MemberAccreditationDoc mAd2;
    static QuotePeriodsColl qPdC;
    static QuotePeriodsColl qPdC2;

    @BeforeAll
    static void init(){

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

        agenda = new SSImpl(qPdC, qPdC2);

        mAd = new MemberAccreditationDoc(new Nif("78545954N"),new AccredNumb("252132563551"));
        mAd2 = new MemberAccreditationDoc(new Nif("28148954S"), new AccredNumb("360138569551"));
    }
    @Test
    void getLaboralLife() throws Exception {

        assertEquals(agenda.getLaboralLife(new Nif("78545954N")), new LaboralLifeDoc(new Nif("78545954N"),qPdC));
        assertEquals(agenda.getLaboralLife(new Nif("28148954S")), new LaboralLifeDoc(new Nif("28148954S"),qPdC2));
        assertNotEquals(agenda.getLaboralLife(new Nif("78545954N")), new LaboralLifeDoc(new Nif("59168954S"),qPdC2));


        assertThrows(NotAffiliatedException.class, () -> agenda.getLaboralLife(null));
        assertThrows(NotAffiliatedException.class, () -> agenda.getLaboralLife(new Nif("59168954S")));
    }

    @Test
    void getMemberAccred() throws Exception {
        MemberAccreditationDoc mAdNot = new MemberAccreditationDoc(new Nif("78545954N"),new AccredNumb("123456789109"));

        assertEquals(mAd, agenda.getMembAccred(new Nif("78545954N")));
        assertEquals(mAd2, agenda.getMembAccred(new Nif("28148954S")));
        assertNotEquals(mAdNot ,agenda.getMembAccred(new Nif("78545954N")));

        assertThrows(NotAffiliatedException.class, () -> agenda.getMembAccred(null));
        assertThrows(NotAffiliatedException.class, () -> agenda.getMembAccred(new Nif("59168954S")));

    }

}
