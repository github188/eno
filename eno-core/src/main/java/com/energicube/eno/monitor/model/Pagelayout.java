package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 页面布局
 */
@Entity
@Table(name = "PAGELAYOUT")
public class Pagelayout implements java.io.Serializable {
    private static final long serialVersionUID = -7848707994553419536L;
    private long pagelayoutuid;
    private String layoutid;
    private String menuid;
    private String menuname;
    private String ownerelement;
    private String pagetype;
    private String layouttype;
    private String layoutname;
    private String description;
    private Integer width = 1462;
    private Integer height = 800;
    private String background;
    private Integer pageindex = 0; // 当一个菜单上对应多个页面时的页码序号
    private Boolean hasPaging = false;
    private String preferences;
    private String planbg;
    private String listbg;
    private String configvalue; // 存放客流楼层对应配置表中的paramter#name
    private Integer deviceconfigid; // 存放资产对应的设置信息

    public Pagelayout() {
    }

    public Pagelayout(long pagelayoutuid, String layoutid, String menuid) {
        this.pagelayoutuid = pagelayoutuid;
        this.layoutid = layoutid;
        this.menuid = menuid;
    }

    public Pagelayout(long pagelayoutuid, String layoutid, String menuid,
                      String layouttype, String layoutname, String description,
                      Integer width, Integer height, String background,
                      Integer pageindex, String configvalue, int deviceconfigid) {
        this.pagelayoutuid = pagelayoutuid;
        this.layoutid = layoutid;
        this.menuid = menuid;
        this.layouttype = layouttype;
        this.layoutname = layoutname;
        this.description = description;
        this.width = width;
        this.height = height;
        this.background = background;
        this.pageindex = pageindex;
        this.configvalue = configvalue;
        this.deviceconfigid = deviceconfigid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAGELAYOUTUID", unique = true, nullable = false)
    public long getPagelayoutuid() {
        return this.pagelayoutuid;
    }

    public void setPagelayoutuid(long pagelayoutuid) {
        this.pagelayoutuid = pagelayoutuid;
    }

    @NotEmpty
    @Column(name = "LAYOUTID", nullable = false, length = 30)
    public String getLayoutid() {
        return this.layoutid;
    }

    public void setLayoutid(String layoutid) {
        this.layoutid = layoutid;
    }

    @NotEmpty
    @Column(name = "MENUID", nullable = false, length = 30)
    public String getMenuid() {
        return this.menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    @Column(name = "MENUNAME", length = 100)
    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    @NotEmpty
    @Column(name = "OWNERELEMENT", length = 30)
    public String getOwnerelement() {
        return ownerelement;
    }

    public void setOwnerelement(String ownerelement) {
        this.ownerelement = ownerelement;
    }

    @Column(name = "LAYOUTTYPE", length = 30)
    public String getLayouttype() {
        return this.layouttype;
    }

    public void setLayouttype(String layouttype) {
        this.layouttype = layouttype;
    }

    @Column(name = "LAYOUTNAME", length = 30)
    public String getLayoutname() {
        return this.layoutname;
    }

    public void setLayoutname(String layoutname) {
        this.layoutname = layoutname;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "WIDTH")
    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "HEIGHT")
    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Column(name = "BACKGROUND")
    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Transient
    public Boolean getHasBackground() {
        if (this.background != null && this.background.length() > 5)
            return true;
        return false;
    }

    @Column(name = "PAGEINDEX")
    public Integer getPageindex() {
        if (this.pageindex == null)
            this.pageindex = 0;
        return this.pageindex;
    }

    public void setPageindex(Integer pageindex) {
        this.pageindex = pageindex;
    }

    @Transient
    public Boolean getHasPaging() {
        if (this.pageindex != null && this.pageindex > 0)
            this.hasPaging = true;
        return hasPaging;
    }

    public void setHasPaging(Boolean hasPaging) {
        this.hasPaging = hasPaging;
    }

    @Column(name = "PAGETYPE")
    public String getPagetype() {
        return pagetype;
    }

    public void setPagetype(String pagetype) {
        this.pagetype = pagetype;
    }

    @Column(name = "PREFERENCES")
    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    @Column(name = "PLANBG")
    public String getPlanbg() {
        return planbg;
    }

    public void setPlanbg(String planbg) {
        this.planbg = planbg;
    }

    @Column(name = "LISTBG")
    public String getListbg() {
        return listbg;
    }

    public void setListbg(String listbg) {
        this.listbg = listbg;
    }

    @Column(name = "CONFIGVALUE")
    public String getConfigvalue() {
        return configvalue;
    }

    public void setConfigvalue(String configvalue) {
        this.configvalue = configvalue;
    }

    @Column(name = "DEVICECONFIGID")
    public Integer getDeviceconfigid() {
        return deviceconfigid;
    }

    public void setDeviceconfigid(Integer deviceconfigid) {
        this.deviceconfigid = deviceconfigid;
    }


    @Override
    public String toString() {
        return "Pagelayout [pagelayoutuid=" + pagelayoutuid + ", layoutid="
                + layoutid + ", menuid=" + menuid + ", menuname=" + menuname
                + ", ownerelement=" + ownerelement + ", pagetype=" + pagetype
                + ", layouttype=" + layouttype + ", layoutname=" + layoutname
                + ", description=" + description + ", width=" + width
                + ", height=" + height + ", background=" + background
                + ", pageindex=" + pageindex + ", hasPaging=" + hasPaging
                + ", preferences=" + preferences + ", planbg=" + planbg
                + ", listbg=" + listbg + ", configvalue=" + configvalue
                + ", setting=" + deviceconfigid + "]";
    }


}
