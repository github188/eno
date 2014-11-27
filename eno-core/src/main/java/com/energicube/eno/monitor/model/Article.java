package com.energicube.eno.monitor.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 文章模型
 */
@Entity
@Table(name = "article", schema = "zclfsys")
public class Article implements java.io.Serializable {

    private static final long serialVersionUID = 9058934674934486479L;
    private long id;
    private BigDecimal version;
    private String uuid;
    private String title;
    private String urlTitle;
    private String description;
    private String content;
    private String type;
    private Date displayDate;
    private Date expirationDate;
    private Boolean smallImage;
    private Long smallImageId;
    private String smallImageUrl;
    private Integer status;
    private Long statusByUserId;
    private String statusByUserName;
    private Date statusDate;
    private Long createUserId;
    private Date createDate;

    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Set<ArticleCategory> articleCategories = new HashSet<ArticleCategory>(
            0);

    public Article() {
    }

    public Article(long id) {
        this.id = id;
    }

    public Article(long id, String uuid, String title, String urlTitle,
                   String description, String content, String type, Date displayDate,
                   Date expirationDate, Boolean smallImage, Long smallImageId,
                   String smallImageUrl, Integer status, Long statusByUserId,
                   String statusByUserName, Date statusDate, Long createUserId,
                   Date createDate, Set<ArticleCategory> articleCategories) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.urlTitle = urlTitle;
        this.description = description;
        this.content = content;
        this.type = type;
        this.displayDate = displayDate;
        this.expirationDate = expirationDate;
        this.smallImage = smallImage;
        this.smallImageId = smallImageId;
        this.smallImageUrl = smallImageUrl;
        this.status = status;
        this.statusByUserId = statusByUserId;
        this.statusByUserName = statusByUserName;
        this.statusDate = statusDate;
        this.createUserId = createUserId;
        this.createDate = createDate;
        this.articleCategories = articleCategories;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Column(name = "version", precision = 2)
    public BigDecimal getVersion() {
        return this.version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    @Column(name = "uuid_", length = 75)
    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Column(name = "title", length = 50)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "urlTitle", length = 100)
    public String getUrlTitle() {
        return this.urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    @Column(name = "description", length = 200)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "content")
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "type_", length = 50)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "displayDate", length = 23)
    public Date getDisplayDate() {
        return this.displayDate;
    }

    public void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expirationDate", length = 23)
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "smallImage")
    public Boolean getSmallImage() {
        return this.smallImage;
    }

    public void setSmallImage(Boolean smallImage) {
        this.smallImage = smallImage;
    }

    @Column(name = "smallImageId")
    public Long getSmallImageId() {
        return this.smallImageId;
    }

    public void setSmallImageId(Long smallImageId) {
        this.smallImageId = smallImageId;
    }

    @Column(name = "smallImageUrl", length = 100)
    public String getSmallImageUrl() {
        return this.smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "statusByUserId")
    public Long getStatusByUserId() {
        return this.statusByUserId;
    }

    public void setStatusByUserId(Long statusByUserId) {
        this.statusByUserId = statusByUserId;
    }

    @Column(name = "statusByUserName", length = 50)
    public String getStatusByUserName() {
        return this.statusByUserName;
    }

    public void setStatusByUserName(String statusByUserName) {
        this.statusByUserName = statusByUserName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "statusDate", length = 23)
    public Date getStatusDate() {
        return this.statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    @Column(name = "createUserId")
    public Long getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate", length = 23)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categoryArticle", schema = "zclfsys", joinColumns = {@JoinColumn(name = "articleId", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "categoryId", nullable = false, updatable = false)})
    public Set<ArticleCategory> getArticleCategories() {
        return this.articleCategories;
    }

    public void setArticleCategories(Set<ArticleCategory> articleCategories) {
        this.articleCategories = articleCategories;
    }

}
