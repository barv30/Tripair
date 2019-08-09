package com.example.dataUser;

import java.io.Serializable;
import java.util.ArrayList;

public class PartnerManager implements Serializable {
    public ArrayList<Partner> partnerList;

    public ArrayList<Partner> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(ArrayList<Partner> partnerList) {
        this.partnerList = partnerList;
    }
}
