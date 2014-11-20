package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Article;
import com.energicube.eno.monitor.model.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 文章操作接口
 */
public interface ArticleService {

    /**
     * 获取所有文章列表
     */
    public List<Article> getArticles();

    public List<ArticleCategory> getArticleCategorys();

    public List<Article> getArticlesByTitle(String title);

    public List<ArticleCategory> getArticleCategorysByName(String name);

    public void updateArticle(String title, BigDecimal version, String urlTitle, String description, String content, long id);

    public void updateArticleCategory(String name, String description, long id);

//	public void updateCategoryArticle(long id,long categoryId);

    public Page<ArticleCategory> getArticleCategoryByPage(Pageable pageable);
}
