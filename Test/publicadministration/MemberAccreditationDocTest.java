package publicadministration;

        import data.AccredNumb;
        import data.Nif;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MemberAccreditationDocTest {

    static MemberAccreditationDoc mAcrD, mAcrD2;

    @BeforeEach
    void init() {

        mAcrD = new MemberAccreditationDoc(new Nif("78545954N"), new AccredNumb("252132563551"));
        mAcrD2 = new MemberAccreditationDoc(new Nif("28148954S"), new AccredNumb("360138569551"));

    }

    @Test
    void getAccredNumbTest() {

        assertEquals(new AccredNumb("252132563551"), mAcrD.getNumAffil());
        assertEquals(new AccredNumb("360138569551"), mAcrD2.getNumAffil());
        assertNotEquals(new AccredNumb("265924565924"), mAcrD.getNumAffil());
        assertNotEquals(new AccredNumb("824138557721"), mAcrD2.getNumAffil());

    }

    @Test
    void getNifTest() {

        assertEquals(new Nif("78545954N"), mAcrD.getNif());
        assertEquals(new Nif("28148954S"), mAcrD2.getNif());
        assertNotEquals(new Nif("63154697M"), mAcrD.getNif());
        assertNotEquals(new Nif("15948954T"), mAcrD2.getNif());

    }
}
