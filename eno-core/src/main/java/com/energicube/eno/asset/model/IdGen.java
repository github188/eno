package com.energicube.eno.asset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ID Generated
 */
@Entity
@Table(name = "ID_GEN", schema = "zclfsys")
public class IdGen implements java.io.Serializable {

    private static final long serialVersionUID = -565329315647791268L;
    /**
     * 键名称
     */
    private String genName;
    /**
     * 当前值
     */
    private long genVal;
    /**
     * 每个自增键使用的前缀，例如，编码规则要求生成自增键格式为JP-1001，则此处作业计划的编码自增键前缀为JP。
     */
    private String prefix;
    /**
     * 自增键数值位的最少位数，比如0001为至少4位；
     */
    private Integer seedlength;
    /**
     * 自增键初始化创建时的值，如0001说明值从1开始初始化；
     */
    private long seedstart;

    /**
     * 如果生成编码键需使用分隔符，此处定义分隔符仅在PREFIX之后使用，为空则无分隔符；
     */
    private String separator;
    /**
     * 要使用自增序列逻辑的自增键对应业务对象的字段名，例如对应作业计划业务编码字段：JPNUM，设备台帐：ASSETNUM
     */
    private String aifield;
    /**
     * "键值类型（引号部分可为空）
     * 0：仅使用自动键值，业务上即为流水号编码；
     * 1：“前缀”+“分隔符”+自动键值；
     * 2：“前缀”+“分隔符”+（自定义编码+自动键值），显示的键值根据绑定的业务类处理；"
     */
    private String aitype;
    private String langcode;
    private String orgid;
    private String siteid;

    public IdGen() {
    }

    public IdGen(String genName, long genVal, String prefix, long seedstart,
                 String aifield, String aitype, String langcode) {
        this.genName = genName;
        this.genVal = genVal;
        this.prefix = prefix;
        this.seedstart = seedstart;
        this.aifield = aifield;
        this.aitype = aitype;
        this.langcode = langcode;
    }

    public IdGen(String genName, long genVal, String prefix,
                 Integer seedlength, long seedstart, String separator,
                 String aifield, String aitype, String langcode, String orgid,
                 String siteid) {
        this.genName = genName;
        this.genVal = genVal;
        this.prefix = prefix;
        this.seedlength = seedlength;
        this.seedstart = seedstart;
        this.separator = separator;
        this.aifield = aifield;
        this.aitype = aitype;
        this.langcode = langcode;
        this.orgid = orgid;
        this.siteid = siteid;
    }

    @Id
    @Column(name = "GEN_NAME", unique = true, nullable = false, length = 100)
    public String getGenName() {
        return this.genName;
    }

    public void setGenName(String genName) {
        this.genName = genName;
    }

    @Column(name = "GEN_VAL", nullable = false)
    public long getGenVal() {
        return this.genVal;
    }

    public void setGenVal(long genVal) {
        this.genVal = genVal;
    }

    @Column(name = "PREFIX", nullable = false, length = 8)
    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Column(name = "SEEDLENGTH")
    public Integer getSeedlength() {
        return this.seedlength;
    }

    public void setSeedlength(Integer seedlength) {
        this.seedlength = seedlength;
    }

    @Column(name = "SEEDSTART", nullable = false)
    public long getSeedstart() {
        return this.seedstart;
    }

    public void setSeedstart(long seedstart) {
        this.seedstart = seedstart;
    }

    @Column(name = "SEPARATOR", length = 5)
    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Column(name = "AIFIELD", nullable = false, length = 50)
    public String getAifield() {
        return this.aifield;
    }

    public void setAifield(String aifield) {
        this.aifield = aifield;
    }

    @Column(name = "AITYPE", nullable = false, length = 5)
    public String getAitype() {
        return this.aitype;
    }

    public void setAitype(String aitype) {
        this.aitype = aitype;
    }

    @Column(name = "LANGCODE", nullable = false, length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

}
