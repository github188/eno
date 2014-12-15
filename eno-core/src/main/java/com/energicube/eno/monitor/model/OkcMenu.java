package com.energicube.eno.monitor.model;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Entity
@Table(name = "OKCMENU")
public class OkcMenu implements java.io.Serializable {

    private static final long serialVersionUID = 3857959727331102320L;
    /**
     * 唯一标识
     */
    private Long okcmenuid;
    /**
     * 具有以下几种菜单类型值，Module、AppFilter、AppTool、AppTab。
     */
    private String menutype;

    /**
     * 当前ELEMENT所属的父级菜单，根据ELEMENTVALUE获得，模块菜单处于顶级所以字段值与ELEMENTVALUE一致
     */
    private String ownerelement;

    /**
     * 针对当前元素，说明菜单中的位置，需结合SUBPOSITION处理逻辑；POSITION与SUBPOSITION的值之间默认都以10为间隔；
     */
    private Integer position;

    /**
     * "当子位置为0时，当前项显示为父级菜单，根据POSITION确定排序位置；
     * 子位置大于0时，当前项显示为子级菜单，根据POSITION确定父级的排序，再对子位置排序；"
     */
    private Integer subposition;

    /**
     * 元素值，根据MENUTYPE、OWNERELEMENT、ELEMENTTYPE确定为模块、应用、子系统、设备类型等代码数据；
     */
    private String elementvalue;
    /**
     * 元素的类型，需根据MENUTYPE、OWNERELEMENT值复合逻辑处理；
     */
    private String elementtype;
    private String headerdescription;
    private String image;

    private String url;
    private Boolean visible = true;
    private Long menuid;

    //显示所在视图，如 LIST,STRUCTURE,PLAN，可以同时显示到多个视图
    private String views;
    //默认视图
    private String defaultView;
    //显示所在视图，如 设备列表,设备结构图，设备平面图 可以同时显示到多个视图
    private String viewnames;

    public OkcMenu() {
    }

    public OkcMenu(Long okcmenuid, String menutype, String headerdescription) {
        this.okcmenuid = okcmenuid;
        this.menutype = menutype;
        this.headerdescription = headerdescription;
    }

    public OkcMenu(Long okcmenuid, String menutype, String ownerelement,
                   Integer position, Integer subposition, String elementtype,
                   String headerdescription, String image, String elementvalue,
                   String url, Boolean visible) {
        this.okcmenuid = okcmenuid;
        this.menutype = menutype;
        this.ownerelement = ownerelement;
        this.position = position;
        this.subposition = subposition;
        this.elementtype = elementtype;
        this.headerdescription = headerdescription;
        this.image = image;
        this.elementvalue = elementvalue;
        this.url = url;
        this.visible = visible;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCMENUID", unique = true, nullable = false)
    public Long getOkcmenuid() {
        return this.okcmenuid;
    }

    public void setOkcmenuid(Long okcmenuid) {
        this.okcmenuid = okcmenuid;
    }

    // 增加String作为参数的Set方法，目的是为了在对对菜单配置做导入时，使用Java反射机制来统一函数原型  [ ChengKang 2014-07-26 ]
    public void setOkcmenuid(String okcmenuid) {
        if (!StringUtils.hasLength(okcmenuid)) {
            okcmenuid = "0";
        }
        this.okcmenuid = Long.valueOf(okcmenuid);
    }

    //@NotEmpty
    @Column(name = "MENUTYPE", nullable = false, length = 30)
    public String getMenutype() {
        return this.menutype;
    }

    public void setMenutype(String menutype) {
        this.menutype = menutype;
    }

    //@NotEmpty
    @Column(name = "OWNERELEMENT", length = 30)
    public String getOwnerelement() {
        return this.ownerelement;
    }

    public void setOwnerelement(String ownerelement) {
        this.ownerelement = ownerelement;
    }

    @NotNull
    @Column(name = "POSITION")
    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    // 增加String作为参数的Set方法，目的是为了在对对菜单配置做导入时，使用Java反射机制来统一函数原型  [ ChengKang 2014-07-26 ]
    public void setPosition(String position) {
        if (!StringUtils.hasLength(position)) {
            position = "0";
        }
        this.position = Integer.valueOf(position);
    }

    @NotNull
    @Column(name = "SUBPOSITION")
    public Integer getSubposition() {
        return this.subposition;
    }

    public void setSubposition(Integer subposition) {
        this.subposition = subposition;
    }

    // 增加String作为参数的Set方法，目的是为了在对对菜单配置做导入时，使用Java反射机制来统一函数原型  [ ChengKang 2014-07-26 ]
    public void setSubposition(String subposition) {
        if (!StringUtils.hasLength(subposition)) {
            subposition = "0";
        }
        this.subposition = Integer.valueOf(subposition);
    }

    //@NotEmpty
    @Column(name = "ELEMENTTYPE", length = 30)
    public String getElementtype() {
        return this.elementtype;
    }

    public void setElementtype(String elementtype) {
        this.elementtype = elementtype;
    }

    //@NotEmpty
    @Column(name = "HEADERDESCRIPTION", nullable = false, length = 50)
    public String getHeaderdescription() {
        return this.headerdescription;
    }

    public void setHeaderdescription(String headerdescription) {
        this.headerdescription = headerdescription;
    }

    @Column(name = "IMAGE", length = 50)
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //@NotEmpty
    @Column(name = "ELEMENTVALUE", length = 30)
    public String getElementvalue() {
        return this.elementvalue;
    }

    public void setElementvalue(String elementvalue) {
        this.elementvalue = elementvalue;
    }

    @Column(name = "URL", length = 250)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "VISIBLE")
    public Boolean getVisible() {
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setVisible(String visible) {
        if (visible.equals("TRUE") || visible.equals("True") || visible.equals("true") || visible.equals("1")) {
            this.visible = true;
        } else {
            this.visible = false;
        }
    }

    @Column(name = "MENUID")
    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    // 增加String作为参数的Set方法，目的是为了在对对菜单配置做导入时，使用Java反射机制来统一函数原型  [ ChengKang 2014-07-26 ]
    public void setMenuid(String menuid) {
        if (!StringUtils.hasLength(menuid)) {
            menuid = "0";
        }
        this.menuid = Long.valueOf(menuid);
    }

    @Column(name = "VIEWS")
    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    @Column(name = "DEFAULTVIEW")
    public String getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(String defaultView) {
        this.defaultView = defaultView;
    }

    @Column(name = "VIEWNAMES")
    public String getViewnames() {
        return viewnames;
    }

    public void setViewnames(String viewnames) {
        this.viewnames = viewnames;
    }

    @Override
    public String toString() {
        return "OkcMenu [okcmenuid=" + okcmenuid + ", menutype=" + menutype
                + ", ownerelement=" + ownerelement + ", position=" + position
                + ", subposition=" + subposition + ", elementvalue="
                + elementvalue + ", elementtype=" + elementtype
                + ", headerdescription=" + headerdescription + ", image="
                + image + ", url=" + url + ", visible=" + visible + ", menuid="
                + menuid + ", views=" + views + "]";
    }

    @Transient
    public boolean isNew() {
        return (this.okcmenuid == null);
    }

}
