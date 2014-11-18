package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.ArticleCategory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Long> {

    @Query("select a from ArticleCategory a")
    public List<ArticleCategory> getArticleCategorys() throws DataAccessException;

    public List<ArticleCategory> findByNameLike(String name);

    @Transactional
    @Modifying
    @Query("update ArticleCategory a set a.name = ?1 , a.description = ?2   where a.categoryId = ?3")
    public void updateArticleCategory(String name, String description, long categoryId);

    public List<ArticleCategory> findByNameContaining(String name);

}