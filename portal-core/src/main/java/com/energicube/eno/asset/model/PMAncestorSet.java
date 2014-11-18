package com.energicube.eno.asset.model;

public class PMAncestorSet implements java.io.Serializable {

    private static final long serialVersionUID = -503835913828378801L;
    private PM pm;
    private PMAncestor pmAncestor;
    private Asset asset;
    private Locations locations;

    public PM getPm() {
        return pm;
    }

    public void setPm(PM pm) {
        this.pm = pm;
    }

    public PMAncestor getPmAncestor() {
        return pmAncestor;
    }

    public void setPmAncestor(PMAncestor pmAncestor) {
        this.pmAncestor = pmAncestor;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "PMAncestorSet [pm=" + pm + ", pmAncestor=" + pmAncestor
                + ", asset=" + asset + ", locations=" + locations + "]";
    }

}
