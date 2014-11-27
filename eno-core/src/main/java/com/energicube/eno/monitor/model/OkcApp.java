package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * 应用定义
 */
@Entity
@Table(name = "OKCAPP", schema = "zclfsys")
public class OkcApp implements java.io.Serializable {

    private static final long serialVersionUID = -7130056703897216617L;
    private long okcappid;
    /**
     * 应用代码
     */
    private String appcode;
    /**
     * 应用类型
     */
    private String apptype;
    private String description;
    /**
     * 应用对应主业务对象（即数据库中的Table或View），
     * 如果用户在某应用上具有READ权限，则说明用户对此应用主表具有数据访问权限，
     * 相应也就能够看到应用对应的所有界面；
     * 如果用户在某个模块下所有应用都无READ权限，用户将无法看到此模块。
     */
    private String maintbname;
    /**
     * 应用查询记录时的缺省排序字段
     */
    private String orderby;
    /**
     * 应用查询缺省的限制子句，类似Select里Where的子句在and后部分的查询条件；默认情况下此字段空表示此应用自身无查询限制。
     */
    private String restrictions;

    public OkcApp() {
    }

    public OkcApp(long okcappid, String appcode) {
        this.okcappid = okcappid;
        this.appcode = appcode;
    }

    public OkcApp(long okcappid, String appcode, String apptype,
                  String description, String maintbname, String orderby,
                  String restrictions) {
        this.okcappid = okcappid;
        this.appcode = appcode;
        this.apptype = apptype;
        this.description = description;
        this.maintbname = maintbname;
        this.orderby = orderby;
        this.restrictions = restrictions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCAPPID", unique = true, nullable = false)
    public long getOkcappid() {
        return this.okcappid;
    }

    public void setOkcappid(long okcappid) {
        this.okcappid = okcappid;
    }

    @Column(name = "APPCODE", nullable = false, length = 20)
    public String getAppcode() {
        return this.appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    @Column(name = "APPTYPE", length = 5)
    public String getApptype() {
        return this.apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "MAINTBNAME", length = 30)
    public String getMaintbname() {
        return this.maintbname;
    }

    public void setMaintbname(String maintbname) {
        this.maintbname = maintbname;
    }

    @Column(name = "ORDERBY", length = 254)
    public String getOrderby() {
        return this.orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    @Column(name = "RESTRICTIONS", length = 254)
    public String getRestrictions() {
        return this.restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

}
