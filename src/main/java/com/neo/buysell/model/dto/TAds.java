package com.neo.buysell.model.dto;

import java.util.List;

public class TAds {
    private int count;
    private List<TAd> tAdList;

    public TAds() {

    }

    public TAds(int count, List<TAd> tAdList) {
        this.count = count;
        this.tAdList = tAdList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TAd> gettAdList() {
        return tAdList;
    }

    public void settAdList(List<TAd> tAdList) {
        this.tAdList = tAdList;
    }
}
