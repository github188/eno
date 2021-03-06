package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UcPattern generated by hbm2java
 */
@Entity
@Table(name = "UC_pattern"
        
)
public class UcPattern implements java.io.Serializable {


    private String id;
    private String version;
    private String name;
    private String description;
    private String systemId;
    private String patternType;
    private String parentId;
    private String orderType;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private String isNew;
    private String createUser;
    private java.util.Date createDate;
    private String siteId;
    private String orgId;
    private String systemSecond;
    private String systemThree;
    private String defaultPattern;
    private Set<UcPatternAction> ucPatternActions = new HashSet<UcPatternAction>(0);
    private Set<UcPatternFactor> ucPatternFactors = new HashSet<UcPatternFactor>(0);
    private Set<UcCombinationPattern> ucCombinationPatterns = new HashSet<UcCombinationPattern>(0);

    public UcPattern() {
    }


    public UcPattern(String id) {
        this.id = id;
    }

    public UcPattern(String id, String name, String description, String systemId, String patternType, String parentId, String orderType, Date startDate, Date endDate, String isNew, String createUser, Date createDate, Set<UcPatternAction> ucPatternActions, Set<UcPatternFactor> ucPatternFactors, Set<UcCombinationPattern> ucCombinationPatterns) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.systemId = systemId;
        this.patternType = patternType;
        this.parentId = parentId;
        this.orderType = orderType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isNew = isNew;
        this.createUser = createUser;
        this.createDate = createDate;
        this.ucPatternActions = ucPatternActions;
        this.ucPatternFactors = ucPatternFactors;
        this.ucCombinationPatterns = ucCombinationPatterns;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "version_", length = 50)
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    @Column(name = "name", length = 300)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "description", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name = "systemId", length = 36)
    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }


    @Column(name = "patternType", length = 2)
    public String getPatternType() {
        return this.patternType;
    }

    public void setPatternType(String patternType) {
        this.patternType = patternType;
    }


    @Column(name = "parentId", length = 36)
    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    @Column(name = "orderType", length = 2)
    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startDate", length = 23)
    public java.util.Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endDate", length = 23)
    public java.util.Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }


    @Column(name = "isNew", length = 4)
    public String getIsNew() {
        return this.isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }


    @Column(name = "createUser", length = 50)
    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate", length = 23)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucPattern",  cascade ={CascadeType.REMOVE,CascadeType.DETACH})
    @OrderBy("baseTime ASC")
    public Set<UcPatternAction> getUcPatternActions() {
        return this.ucPatternActions;
    }

    public void setUcPatternActions(Set<UcPatternAction> ucPatternActions) {
        this.ucPatternActions = ucPatternActions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucPattern", cascade ={CascadeType.REMOVE,CascadeType.DETACH} )
    public Set<UcPatternFactor> getUcPatternFactors() {
        return this.ucPatternFactors;
    }

    public void setUcPatternFactors(Set<UcPatternFactor> ucPatternFactors) {
        this.ucPatternFactors = ucPatternFactors;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucPattern", cascade ={CascadeType.REMOVE,CascadeType.DETACH})
    public Set<UcCombinationPattern> getUcCombinationPatterns() {
        return this.ucCombinationPatterns;
    }

    public void setUcCombinationPatterns(Set<UcCombinationPattern> ucCombinationPatterns) {
        this.ucCombinationPatterns = ucCombinationPatterns;
    }

    @Column(name = "siteId", length = 36)
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Column(name = "orgId", length = 36)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Column(name = "systemSecond", length = 36)
    public String getSystemSecond() {
        return systemSecond;
    }

    public void setSystemSecond(String systemSecond) {
        this.systemSecond = systemSecond;
    }

    @Column(name = "systemThree", length = 36)
    public String getSystemThree() {
        return systemThree;
    }

    public void setSystemThree(String systemThree) {
        this.systemThree = systemThree;
    }

    @Column(name = "defaultPattern", length = 2)
    public String getDefaultPattern() {
        return defaultPattern;
    }

    public void setDefaultPattern(String defaultPattern) {
        this.defaultPattern = defaultPattern;
    }
}


