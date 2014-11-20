package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 触发事件
 * <p/>
 * <p>
 * 联动触发的事件及条件。每个联动的执行都必须有一个触发条件。
 * </p>
 */
@Entity
@Table(name = "UCLC_condition")
public class UclcCondition implements java.io.Serializable {

    private static final long serialVersionUID = 4019930277663180645L;
    private Integer condid;
    private String condname;
    private String condcomment;
    private String condexp;
    private Date updatet;

    public UclcCondition() {
    }

    public UclcCondition(Integer condid, String condname) {
        this.condid = condid;
        this.condname = condname;
    }

    public UclcCondition(Integer condid, String condname, String condcomment,
                         String condexp, Date updatet) {
        this.condid = condid;
        this.condname = condname;
        this.condcomment = condcomment;
        this.condexp = condexp;
        this.updatet = updatet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONDID", unique = true, nullable = false)
    public Integer getCondid() {
        return this.condid;
    }

    public void setCondid(Integer condid) {
        this.condid = condid;
    }

    @NotEmpty
    @Column(name = "CONDNAME", nullable = false, length = 100)
    public String getCondname() {
        return this.condname;
    }

    public void setCondname(String condname) {
        this.condname = condname;
    }

    @Column(name = "CONDCOMMENT")
    public String getCondcomment() {
        return this.condcomment;
    }

    public void setCondcomment(String condcomment) {
        this.condcomment = condcomment;
    }

    @Column(name = "CONDEXP", length = 4000)
    public String getCondexp() {
        return this.condexp;
    }

    public void setCondexp(String condexp) {
        this.condexp = condexp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATET", length = 23)
    public Date getUpdatet() {
        return this.updatet;
    }

    public void setUpdatet(Date updatet) {
        this.updatet = updatet;
    }

}
