package services;

import data.AccredNumb;
import data.Nif;
import publicadministration.LaboralLifeDoc;
import publicadministration.MemberAccreditationDoc;
import publicadministration.QuotePeriodsColl;
import services.exceptions.NotAffiliatedException;

import java.net.ConnectException;
import java.util.*;

public class SSImpl implements SS {

    private List<LaboralLifeDoc> listLaboral = new ArrayList<>();
    private List<MemberAccreditationDoc> listMembAccred= new ArrayList<>();
    private QuotePeriodsColl qPdC;
    private QuotePeriodsColl qPdC2;
    private boolean flag;
    LaboralLifeDoc resultLaboral;
    MemberAccreditationDoc resultmAccred;

    public SSImpl (QuotePeriodsColl qPdC, QuotePeriodsColl qPdC2) {

        this.qPdC = qPdC;
        this.qPdC2 = qPdC2;

        LaboralLifeDoc lLd = new LaboralLifeDoc(new Nif("78545954N"),qPdC);
        LaboralLifeDoc lLd2 = new LaboralLifeDoc(new Nif("28148954S"), qPdC2);
        this.listLaboral.add(lLd);
        this.listLaboral.add(lLd2);
        MemberAccreditationDoc mAd = new MemberAccreditationDoc(new Nif("78545954N"),new AccredNumb("252132563551"));
        MemberAccreditationDoc mAd2 = new MemberAccreditationDoc(new Nif("28148954S"), new AccredNumb("360138569551"));
        this.listMembAccred.add(mAd);
        this.listMembAccred.add(mAd2);

    }


    public LaboralLifeDoc getLaboralLife(Nif nif) throws NotAffiliatedException, ConnectException {

        if (nif == null) {
            throw new NotAffiliatedException("Error nif");
        }
        try {
            resultLaboral = buscarLaboralLifeNif(nif);
        }
        catch (Exception ce){
            throw new ConnectException(""+ce);
        }
        if (resultLaboral == null) {
            throw new NotAffiliatedException("Error nif");
        }
        return resultLaboral;
    }


    public MemberAccreditationDoc getMembAccred(Nif nif) throws NotAffiliatedException, ConnectException {

        if (nif == null) {
            throw new NotAffiliatedException("Error nif");
        }
        try {
            resultmAccred = buscarMembAccredNif(nif);
        }
        catch (Exception ce){
            throw new ConnectException(""+ce);
        }
        if (resultmAccred == null) {
            throw new NotAffiliatedException("Error nif");
        }
        return resultmAccred;
    }

    private LaboralLifeDoc buscarLaboralLifeNif(Nif nif) {
        flag = false;
        for (LaboralLifeDoc laboralLifeDoc : listLaboral) {
            if (nif.equals(laboralLifeDoc.getNif())) {
                flag = true;
                return laboralLifeDoc;
            }
        }
        if (!flag) {
                return null;
        }
        return null;
    }
    private MemberAccreditationDoc buscarMembAccredNif(Nif nif) {
        flag = false;
        for (MemberAccreditationDoc memberAccreditationDoc : listMembAccred) {
            if (nif.equals(memberAccreditationDoc.getNif())) {
                flag = true;
                return memberAccreditationDoc;
            }
        }
        if (!flag) {
            return null;
        }
        return null;
    }
}
