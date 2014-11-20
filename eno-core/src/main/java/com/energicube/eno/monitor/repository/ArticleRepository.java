package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Article;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a")
    public List<Article> getArticles() throws DataAccessException;

    public List<Article> findByTitleLike(String title);

    @Transactional
    @Modifying
    @Query("update Article a set a.title = ?1 , a.version = ?2 , a.urlTitle = ?3 , a.description = ?4 , a.content = ?5 where a.id = ?6")
    public void updateArticle(String title, BigDecimal version, String urlTitle, String description, String content, long id);

    public List<Article> findByTitleContaining(String title);
}