package com.energicube.eno.common.dto;

import java.io.Serializable;

/**
 * 客流系统-支持汇纳的对象
 * @author LiuGuanglu
 * @date 2014/6/3.
 */
public class PfeHuiNaDTO implements Serializable {
    private String siteKey;
    private long inSum;
    private long outSum;
    private String siteName;
    private String siteType;
    private String countDate;

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public long getInSum() {
        return inSum;
    }

    public void setInSum(long inSum) {
        this.inSum = inSum;
    }

    public long getOutSum() {
        return outSum;
    }

    public void setOutSum(long outSum) {
        this.outSum = outSum;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getCountDate() {
        return countDate;
    }

    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    @Override
    public String toString() {
        return "PfeHuiNaDTO{" +
                "siteKey='" + siteKey + '\'' +
                ", inSum=" + inSum +
                ", outSum=" + outSum +
                ", siteName='" + siteName + '\'' +
                ", siteType='" + siteType + '\'' +
                ", countDate='" + countDate + '\'' +
                '}';
    }
}
