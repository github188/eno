package com.energicube.eno.asset.model;


import javax.persistence.*;

/**
 * 分类结构使用的对象
 */
@Entity
@Table(name = "CLASSUSEWITH")
public class ClassUseWith implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8448127962160604418L;
    private long classusewithid;
    private String classstructureid;
    private String description;
    private String objectname;
    private String objectvalue;

    public ClassUseWith() {
    }

    public ClassUseWith(long classusewithid) {
        this.classusewithid = classusewithid;
    }

    public ClassUseWith(long classusewithid, String classstructureid,
                        String description, String objectname, String objectvalue) {
        this.classusewithid = classusewithid;
        this.classstructureid = classstructureid;
        this.description = description;
        this.objectname = objectname;
        this.objectvalue = objectvalue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASSUSEWITHID", unique = true, nullable = false)
    public long getClassusewithid() {
        return this.classusewithid;
    }

    public void setClassusewithid(long classusewithid) {
        this.classusewithid = classusewithid;
    }

    @Column(name = "CLASSSTRUCTUREID", length = 20)
    public String getClassstructureid() {
        return this.classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "OBJECTNAME", length = 30)
    public String getObjectname() {
        return this.objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    @Column(name = "OBJECTVALUE", length = 30)
    public String getObjectvalue() {
        return this.objectvalue;
    }

    public void setObjectvalue(String objectvalue) {
        this.objectvalue = objectvalue;
    }

}
