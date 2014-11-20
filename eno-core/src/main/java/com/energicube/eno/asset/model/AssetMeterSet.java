package com.energicube.eno.asset.model;

public class AssetMeterSet implements java.io.Serializable {

    private static final long serialVersionUID = -3149481485142969625L;
    private AssetMeter assetMeter;
    private Meter meter;

    public AssetMeter getAssetMeter() {
        return assetMeter;
    }

    public void setAssetMeter(AssetMeter assetMeter) {
        this.assetMeter = assetMeter;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

}
