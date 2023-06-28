package com.neo.buysell.model.dto;

import java.util.List;

public class TComments {
    private int count;
    private List<TComment> tComments;

    public TComments() {

    }

    public TComments(int count, List<TComment> tComments) {
        this.count = count;
        this.tComments = tComments;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TComment> gettComments() {
        return tComments;
    }

    public void settComments(List<TComment> tComments) {
        this.tComments = tComments;
    }
}
