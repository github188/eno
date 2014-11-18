package com.energicube.eno.message.redis;

/**
 * 计量点信息
 */
public class AlarmsInfo implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String serialNo;
    private String tagID;
    private String almType;
    private String limitValue;
    private String almGroup;
    private String almPriority;
    private String tagName;
    private String tagComment;
    private String almComment;
    private String assetID;
    private String propertyID;
    private String groupName;
    private String groupPath;
    private String deviceName;
    private String eCode;
    private String almDirection;
    private String almTime;
    private String almValue;
    private String almOperator;
    private String ackTime;
    private String ackValue;
    private String ackOperator;
    private String retTime;
    private String retValue;
    private String retOperator;
    private String reviewTime;
    private String reviewContent;
    private String reviewer;

    public AlarmsInfo(String serialNo, String tagID, String almType,
                      String limitValue, String almGroup, String almPriority,
                      String tagName, String tagComment, String almComment,
                      String assetID, String propertyID, String groupName,
                      String groupPath, String deviceName, String eCode,
                      String almDirection, String almTime, String almValue,
                      String almOperator, String ackTime, String ackValue,
                      String ackOperator, String retTime, String retValue,
                      String retOperator, String reviewTime, String reviewContent,
                      String reviewer) {
        super();
        this.serialNo = serialNo;
        this.tagID = tagID;
        this.almType = almType;
        this.limitValue = limitValue;
        this.almGroup = almGroup;
        this.almPriority = almPriority;
        this.tagName = tagName;
        this.tagComment = tagComment;
        this.almComment = almComment;
        this.assetID = assetID;
        this.propertyID = propertyID;
        this.groupName = groupName;
        this.groupPath = groupPath;
        this.deviceName = deviceName;
        this.eCode = eCode;
        this.almDirection = almDirection;
        this.almTime = almTime;
        this.almValue = almValue;
        this.almOperator = almOperator;
        this.ackTime = ackTime;
        this.ackValue = ackValue;
        this.ackOperator = ackOperator;
        this.retTime = retTime;
        this.retValue = retValue;
        this.retOperator = retOperator;
        this.reviewTime = reviewTime;
        this.reviewContent = reviewContent;
        this.reviewer = reviewer;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getAlmType() {
        return almType;
    }

    public void setAlmType(String almType) {
        this.almType = almType;
    }

    public String getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(String limitValue) {
        this.limitValue = limitValue;
    }

    public String getAlmGroup() {
        return almGroup;
    }

    public void setAlmGroup(String almGroup) {
        this.almGroup = almGroup;
    }

    public String getAlmPriority() {
        return almPriority;
    }

    public void setAlmPriority(String almPriority) {
        this.almPriority = almPriority;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagComment() {
        return tagComment;
    }

    public void setTagComment(String tagComment) {
        this.tagComment = tagComment;
    }

    public String getAlmComment() {
        return almComment;
    }

    public void setAlmComment(String almComment) {
        this.almComment = almComment;
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String geteCode() {
        return eCode;
    }

    public void seteCode(String eCode) {
        this.eCode = eCode;
    }

    public String getAlmDirection() {
        return almDirection;
    }

    public void setAlmDirection(String almDirection) {
        this.almDirection = almDirection;
    }

    public String getAlmTime() {
        return almTime;
    }

    public void setAlmTime(String almTime) {
        this.almTime = almTime;
    }

    public String getAlmValue() {
        return almValue;
    }

    public void setAlmValue(String almValue) {
        this.almValue = almValue;
    }

    public String getAlmOperator() {
        return almOperator;
    }

    public void setAlmOperator(String almOperator) {
        this.almOperator = almOperator;
    }

    public String getAckTime() {
        return ackTime;
    }

    public void setAckTime(String ackTime) {
        this.ackTime = ackTime;
    }

    public String getAckValue() {
        return ackValue;
    }

    public void setAckValue(String ackValue) {
        this.ackValue = ackValue;
    }

    public String getAckOperator() {
        return ackOperator;
    }

    public void setAckOperator(String ackOperator) {
        this.ackOperator = ackOperator;
    }

    public String getRetTime() {
        return retTime;
    }

    public void setRetTime(String retTime) {
        this.retTime = retTime;
    }

    public String getRetValue() {
        return retValue;
    }

    public void setRetValue(String retValue) {
        this.retValue = retValue;
    }

    public String getRetOperator() {
        return retOperator;
    }

    public void setRetOperator(String retOperator) {
        this.retOperator = retOperator;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

}