package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.Article;
import com.energicube.eno.monitor.model.ArticleCategory;
import com.energicube.eno.monitor.repository.ArticleCategoryRepository;
import com.energicube.eno.monitor.repository.ArticleRepository;
import com.energicube.eno.monitor.service.ArticleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final static Log logger = LogFactory.getLog(ArticleServiceImpl.class);


    private ArticleRepository articleRepository;

    private ArticleCategoryRepository articleCategoryRepository;


    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleCategoryRepository articleCategoryRepository) {
        this.articleRepository = articleRepository;
        this.articleCategoryRepository = articleCategoryRepository;
    }


    public List<Article> getArticles() {
        List<Article> articles = articleRepository.getArticles();
        return articles;
    }

    public List<ArticleCategory> getArticleCategorys() {
        List<ArticleCategory> articleCategorys = articleCategoryRepository.getArticleCategorys();
        return articleCategorys;
    }

    public List<Article> getArticlesByTitle(String title) {
        List<Article> articles = articleRepository.findByTitleLike(title);
        return articles;
    }

    public List<ArticleCategory> getArticleCategorysByName(String name) {
        List<ArticleCategory> articleCategorys = articleCategoryRepository.findByNameLike(name);
        return articleCategorys;
    }

    public void updateArticle(String title, BigDecimal version, String urlTitle, String description, String content, long id) {
        articleRepository.updateArticle(title, version, urlTitle, description, content, id);
    }

    public void updateArticleCategory(String name, String description, long id) {
        articleCategoryRepository.updateArticleCategory(name, description, id);
    }

    public Page<ArticleCategory> getArticleCategoryByPage(Pageable pageable) {
        return articleCategoryRepository.findAll(pageable);
    }

//	public void updateCategoryArticle(long id,long categoryId){
//		articleCategoryRepository.updateCategoryArticle(id,categoryId);
//	}
}
