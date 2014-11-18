package com.energicube.eno.monitor.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * 触发事件
 * <p/>
 * <p>
 * 联动触发的事件及条件。每个联动的执行都必须有一个触发条件。
 * </p>
 */
@Entity
@Table(name = "UCLC_condition")
public class UclcConditionZzx implements java.io.Serializable {

    private static final long serialVersionUID = 4019930277663180645L;
    private Integer condid;
    private String condname;
    private String condcomment;
    private String condexp;
    private DateTime updatet;

    public UclcConditionZzx() {
    }

    public UclcConditionZzx(Integer condid, String condname) {
        this.condid = condid;
        this.condname = condname;
    }

    public UclcConditionZzx(Integer condid, String condname, String condcomment,
                            String condexp, DateTime updatet) {
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

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "UPDATET", length = 23)
    public DateTime getUpdatet() {
        return this.updatet;
    }

    public void setUpdatet(DateTime updatet) {
        this.updatet = updatet;
    }

}
