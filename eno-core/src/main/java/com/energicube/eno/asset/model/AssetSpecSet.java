package com.energicube.eno.asset.model;

/**
 * 资产规范集
 */
public class AssetSpecSet implements java.io.Serializable {
    private static final long serialVersionUID = -7135716822899014745L;
    private Asset asset;
    private AssetSpec assetSpec;
    private ClassSpec classSpec;
    private ClassStructure classStructure;
    private AssetAttribute assetAttribute;
    private MeasureUnit measureUnit;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public AssetSpec getAssetSpec() {
        return assetSpec;
    }

    public void setAssetSpec(AssetSpec assetSpec) {
        this.assetSpec = assetSpec;
    }

    public ClassSpec getClassSpec() {
        return classSpec;
    }

    public void setClassSpec(ClassSpec classSpec) {
        this.classSpec = classSpec;
    }

    public ClassStructure getClassStructure() {
        return classStructure;
    }

    public void setClassStructure(ClassStructure classStructure) {
        this.classStructure = classStructure;
    }

    public AssetAttribute getAssetAttribute() {
        return assetAttribute;
    }

    public void setAssetAttribute(AssetAttribute assetAttribute) {
        this.assetAttribute = assetAttribute;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

}
