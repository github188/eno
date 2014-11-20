package com.energicube.eno.asset.model;

public class FailureListSet implements java.io.Serializable {

    private static final long serialVersionUID = 3791929574094515074L;

    private FailureList failureList;
    private FailureCode failureCode;

    public FailureList getFailureList() {
        return failureList;
    }

    public void setFailureList(FailureList failureList) {
        this.failureList = failureList;
    }

    public FailureCode getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(FailureCode failureCode) {
        this.failureCode = failureCode;
    }

    @Override
    public String toString() {
        return "FailureListSet [failureList=" + failureList + ", failureCode="
                + failureCode + "]";
    }


}
