package com.energicube.eno.monitor.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 系统对 Global Data 或 Global Function 的必要全局授权项，例如门户、公告牌、日志、报表等
 */
@Entity
@Table(name = "GLOBALAUTH", schema = "zclfsys")
public class GlobalAuth implements java.io.Serializable {

    private static final long serialVersionUID = 2669215611282539710L;
    private long globalauthid;
    /**
     * 应用程序代码
     */
    private String appcode;
    /**
     * 方法名称
     */
    private String methodname;
    /**
     * 对应OKCSIGREG的应用和签名项。
     */
    private String signame;

    public GlobalAuth() {
    }

    public GlobalAuth(long globalauthid) {
        this.globalauthid = globalauthid;
    }

    public GlobalAuth(long globalauthid, String appcode, String methodname,
                      String signame) {
        this.globalauthid = globalauthid;
        this.appcode = appcode;
        this.methodname = methodname;
        this.signame = signame;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GLOBALAUTHID", unique = true, nullable = false)
    public long getGlobalauthid() {
        return this.globalauthid;
    }

    public void setGlobalauthid(long globalauthid) {
        this.globalauthid = globalauthid;
    }

    @NotEmpty
    @Column(name = "APPCODE", length = 10)
    public String getAppcode() {
        return this.appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    @NotEmpty
    @Column(name = "METHODNAME", length = 128)
    public String getMethodname() {
        return this.methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    @NotEmpty
    @Column(name = "SIGNAME", length = 25)
    public String getSigname() {
        return this.signame;
    }

    public void setSigname(String signame) {
        this.signame = signame;
    }

}
