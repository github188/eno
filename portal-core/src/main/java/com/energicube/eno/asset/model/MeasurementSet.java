package com.energicube.eno.asset.model;


/**
 * 测点计量
 *
 * @author CHENPING
 */
public class MeasurementSet implements java.io.Serializable {

    private static final long serialVersionUID = 1369060218969329070L;

    private Measurement measurement;

    private MeasurePoint measurePoint;

    private MeasureSpec measureSpec;

    private AssetAttribute assetAttribute;

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public MeasurePoint getMeasurePoint() {
        return measurePoint;
    }

    public void setMeasurePoint(MeasurePoint measurePoint) {
        this.measurePoint = measurePoint;
    }

    public MeasureSpec getMeasureSpec() {
        return measureSpec;
    }

    public void setMeasureSpec(MeasureSpec measureSpec) {
        this.measureSpec = measureSpec;
    }

    public AssetAttribute getAssetAttribute() {
        return assetAttribute;
    }

    public void setAssetAttribute(AssetAttribute assetAttribute) {
        this.assetAttribute = assetAttribute;
    }

}
