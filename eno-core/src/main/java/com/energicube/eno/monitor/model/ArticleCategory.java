package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 文章类别定义
 */
@Entity
@Table(name = "articleCategory")
public class ArticleCategory implements java.io.Serializable {

    private static final long serialVersionUID = 657887162576680971L;
    private long categoryId;
    private String name;
    private String description;
    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Set<Article> articles = new HashSet<Article>(0);

    public ArticleCategory() {
    }

    public ArticleCategory(long categoryId) {
        this.categoryId = categoryId;
    }

    public ArticleCategory(long categoryId, String name, String description,
                           Set<Article> articles) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.articles = articles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", unique = true, nullable = false)
    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 200)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "articleCategories")
    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

}
