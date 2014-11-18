package com.energicube.eno.asset.model;

public class PMMeterSet implements java.io.Serializable {

    private static final long serialVersionUID = -6215746725448891911L;

    private AssetMeter assetMeter;
    private LocationMeter locationMeter;
    private LocMeterReading locMeterReading;
    private Meter meter;
    private PMMeter pmMeter;
    private PM pm;

    public AssetMeter getAssetMeter() {
        return assetMeter;
    }

    public void setAssetMeter(AssetMeter assetMeter) {
        this.assetMeter = assetMeter;
    }

    public LocationMeter getLocationMeter() {
        return locationMeter;
    }

    public void setLocationMeter(LocationMeter locationMeter) {
        this.locationMeter = locationMeter;
    }

    public LocMeterReading getLocMeterReading() {
        return locMeterReading;
    }

    public void setLocMeterReading(LocMeterReading locMeterReading) {
        this.locMeterReading = locMeterReading;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public PMMeter getPmMeter() {
        return pmMeter;
    }

    public void setPmMeter(PMMeter pmMeter) {
        this.pmMeter = pmMeter;
    }

    public PM getPm() {
        return pm;
    }

    public void setPm(PM pm) {
        this.pm = pm;
    }

}
