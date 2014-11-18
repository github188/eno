package com.energicube.eno.monitor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * 页面标签定义
 * <p/>
 * <p>
 * 主要用于定义各个页面包含的设备点信息，包括设备点ID，名称，以前在页面中的绝对位置
 * </p>
 *
 * @author CHENPING
 * @version 1.0
 */
@Entity
@Table(name = "PAGETAG")
public class Pagetag implements java.io.Serializable {

    private static final long serialVersionUID = 6389886947579778267L;
    private Long pagetagid; // 数据库主键，自增型
    private String tagid; // 设备ID,可以是TAGID、ASSETID、VideoID
    private String tagname; // 设备名称
    private int tagtype; // 设备值类型,1=开关量 2=模拟型 3=字符串
    private String tagval; // 设备原始值
    private String label; // 标签名称，中文描述，如： 总管冷冻出水温度
    private String comments; // 标签备注
    private String measureunitid; // 主量单位缩写，数据来源于Measureunit，如： ℃
    private String controlid; // 设备列表控件ID
    private String controlid2; // 结构图控件ID
    private String controlid3; // 平面图控件ID
    private String left; // 页面位置
    private String top; // 页面位置
    private String parentid; // 上级设备ID,针对具有级联关系的设备
    private Boolean children = false; // 是否有子级
    private String groupname; // 分组名称,当要求多个设备的状态反应到一个控件的时候，对设备进行分组。
    private String layoutid; // 所属系统菜单位，数据来源SYSMENU
    private String createby; // 创建人
    private DateTime createdate; // 创建日期
    private int pagetagtype; // 设备类型，0 TAG 1 MAP-AREA 2 VIDEO 3ASSET 4ALARM 5PASSENGER 99CONTROL
    private int usesetting; // 使用扩展设置?
    private String setting; // 扩展设置内容
    private String attrctrlid; // 属性控件ID
    private String showrange; // 显示范围 list/structure/plan
    private String coords; // 标签坐标
    private int isProgLinkage = 0;    //是否程序连接
    private String progLinkage = ""; //程序连接ID

    private String pagetab;
    private int zindex = 100; //设备点在界面上的位置,z-index

    private String scripts;//脚本
    private String expressions;//表达式

    private String classstructureid; // 分类id，用于对应设备列表和面板对应的显示值[2014-10-16, zzx]

    public Pagetag() {
    }

    public Pagetag(Long pagetagid, String tagid, String layoutid) {
        this.pagetagid = pagetagid;
        this.tagid = tagid;
        this.layoutid = layoutid;
    }

    public Pagetag(Long pagetagid, String tagid, String tagname, String label,
                   String measureunitid, String controlid, String left, String top,
                   String layoutid, String createby, DateTime createdate) {
        this.pagetagid = pagetagid;
        this.tagid = tagid;
        this.tagname = tagname;
        this.label = label;
        this.measureunitid = measureunitid;
        this.controlid = controlid;
        this.left = left;
        this.top = top;
        this.layoutid = layoutid;
        this.createby = createby;
        this.createdate = createdate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAGETAGID", unique = true, nullable = false)
    public Long getPagetagid() {
        return this.pagetagid;
    }

    public void setPagetagid(Long pagetagid) {
        this.pagetagid = pagetagid;
    }

    @NotEmpty
    @Column(name = "TAGID", nullable = false)
    public String getTagid() {
        return this.tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    @NotEmpty
    @Column(name = "TAGNAME", length = 100)
    public String getTagname() {
        return this.tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Column(name = "TAGTYPE")
    public int getTagtype() {
        return tagtype;
    }

    public void setTagtype(int tagtype) {
        this.tagtype = tagtype;
    }

    @Column(name = "TAGVAL")
    public String getTagval() {
        return tagval;
    }

    public void setTagval(String tagval) {
        this.tagval = tagval;
    }

    @Column(name = "LABEL")
    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(name = "COMMENTS")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Column(name = "MEASUREUNITID", length = 16)
    public String getMeasureunitid() {
        return measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
    }

    @Column(name = "CONTROLID")
    public String getControlid() {
        return this.controlid;
    }

    public void setControlid(String controlid) {
        this.controlid = controlid;
    }

    @Column(name = "CONTROLID2")
    public String getControlid2() {
        return controlid2;
    }

    public void setControlid2(String controlid2) {
        this.controlid2 = controlid2;
    }

    @Column(name = "CONTROLID3")
    public String getControlid3() {
        return controlid3;
    }

    public void setControlid3(String controlid3) {
        this.controlid3 = controlid3;
    }

    @Column(name = "[LEFT]")
    public String getLeft() {
        return this.left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    @Column(name = "[TOP]")
    public String getTop() {
        return this.top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    @NotEmpty
    @Column(name = "LAYOUTID", nullable = false, length = 30)
    public String getLayoutid() {
        return layoutid;
    }

    public void setLayoutid(String layoutid) {
        this.layoutid = layoutid;
    }

    @Column(name = "CREATEBY", length = 30)
    public String getCreateby() {
        return this.createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    @JsonIgnore
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATEDATE", length = 23)
    public DateTime getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(DateTime createdate) {
        this.createdate = createdate;
    }

    @Column(name = "PARENTID")
    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    //是否有子级
    @Column(name = "CHILDREN")
    public Boolean getChildren() {
        return this.children;
    }

    public void setChildren(Boolean children) {
        this.children = children;
    }

    @Column(name = "GROUPNAME")
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "PAGETAGTYPE")
    public int getPagetagtype() {
        return pagetagtype;
    }

    public void setPagetagtype(int pagetagtype) {
        this.pagetagtype = pagetagtype;
    }

    @Column(name = "USESETTING")
    public int getUsesetting() {
        return usesetting;
    }

    public void setUsesetting(int usesetting) {
        this.usesetting = usesetting;
    }

    @Column(name = "SETTING")
    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    @Column(name = "SHOWRANGE")
    public String getShowrange() {
        return showrange;
    }

    public void setShowrange(String showrange) {
        this.showrange = showrange;
    }

    @Column(name = "COORDS")
    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }


    @Column(name = "ZINDEX")
    public int getZindex() {
        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }

    @Column(name = "SCRIPTS")
    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }

    @Column(name = "EXPRESSIONS")
    public String getExpressions() {
        return expressions;
    }

    public void setExpressions(String expressions) {
        this.expressions = expressions;
    }

    @Transient
    public String getPagetab() {
        return pagetab;
    }

    public void setPagetab(String pagetab) {
        this.pagetab = pagetab;
    }

    @Column(name = "ISPROGLINKAGE")
    public int getIsProgLinkage() {
        return isProgLinkage;
    }

    public void setIsProgLinkage(int isProgLinkage) {
        this.isProgLinkage = isProgLinkage;
    }

    @Column(name = "PROGLINKAGE")
    public String getProgLinkage() {
        return progLinkage;
    }

    public void setProgLinkage(String progLinkage) {
        this.progLinkage = progLinkage;
    }

    @Column(name = "ATTRCTRLID")
    public String getAttrctrlid() {
        return attrctrlid;
    }

    public void setAttrctrlid(String attrctrlid) {
        this.attrctrlid = attrctrlid;
    }

    @Column(name = "CLASSSTRUCTUREID")
    public String getClassstructureid() {
        return classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Override
    public String toString() {
        return "Pagetag [pagetagid=" + pagetagid + ", tagid=" + tagid
                + ", tagname=" + tagname + ", tagtype=" + tagtype + ", tagval="
                + tagval + ", label=" + label + ", comments=" + comments
                + ", measureunitid=" + measureunitid + ", controlid="
                + controlid + ", controlid2=" + controlid2 + ", controlid3="
                + controlid3 + ", left=" + left + ", top=" + top
                + ", parentid=" + parentid + ", children=" + children
                + ", groupname=" + groupname + ", layoutid=" + layoutid
                + ", createby=" + createby + ", createdate=" + createdate
                + ", pagetagtype=" + pagetagtype + ", usesetting=" + usesetting
                + ", setting=" + setting + ", attrctrlid=" + attrctrlid + ", showrange=" + showrange
                + ", coords=" + coords + ", pagetab=" + pagetab + ", zindex=" + zindex + ", classstructureid="
                + classstructureid + "]";
    }
}
