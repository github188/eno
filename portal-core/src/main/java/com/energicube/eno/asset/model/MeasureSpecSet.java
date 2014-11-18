package com.energicube.eno.asset.model;

public class MeasureSpecSet implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private MeasureSpec measureSpec;
    private AssetAttribute assetAttribute;

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
