package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.Article;
import com.energicube.eno.monitor.model.ArticleCategory;
import com.energicube.eno.monitor.repository.ArticleCategoryRepository;
import com.energicube.eno.monitor.repository.ArticleRepository;
import com.energicube.eno.monitor.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户登陆处理控制
 *
 * @author CHENPING
 */
@Controller
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 支持的文件类型
    String[] errorType = {".xls"};
    // 格式化日期
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @RequestMapping(value = "/articleCategory/articleCategoryList", method = RequestMethod.GET)
    public String initArticleCategoryList(Model model) {
        List<ArticleCategory> articleCategorys = articleService
                .getArticleCategorys();
        model.addAttribute("articleCategorys", articleCategorys);
        return "articleCategory/articleCategoryList";
    }

    @RequestMapping(value = "/article/articleList", method = RequestMethod.GET)
    public String initArticleList(Model model) {
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    /**
     * 新增用户信息
     */
    @RequestMapping(value = "/article/articleAdd", method = RequestMethod.GET)
    public String initArticleForm(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        return "article/articleAdd";
    }

    /**
     * 新增用户信息
     */
    @RequestMapping(value = "/articleCategory/articleCategoryAdd", method = RequestMethod.GET)
    public String initArticleCategoryForm(Model model) {
        ArticleCategory articleCategory = new ArticleCategory();
        model.addAttribute("articleCategory", articleCategory);
        return "articleCategory/articleCategoryAdd";
    }

    @RequestMapping(value = "/article/articleUpdate")
    public String initArticleUpdateForm(HttpServletRequest request,
                                        HttpServletResponse response, Model model) {
        String articleId = request.getParameter("id");
        Article article = articleRepository.findOne(Long.valueOf(articleId));
        model.addAttribute("article", article);
        String articleCategoryId = "";
        Set<ArticleCategory> articleCategoryList = article
                .getArticleCategories();
        if (articleCategoryList.size() > 0) {
            for (Iterator<ArticleCategory> it = articleCategoryList.iterator(); it
                    .hasNext(); ) {
                ArticleCategory articleCategory = it.next();
                String id = "'" + articleCategory.getCategoryId() + "'" + ",";
                articleCategoryId += id;
            }
            articleCategoryId = "["
                    + articleCategoryId.substring(0,
                    articleCategoryId.length() - 1) + "]";
        } else {
            articleCategoryId = "-1";
        }
        model.addAttribute("articleCategoryIds", articleCategoryId);
        return "article/articleUpdate";
    }

    @RequestMapping(value = "/articleCategory/articleCategoryUpdate")
    public String initArticleCategoryUpdate(HttpServletRequest request,
                                            HttpServletResponse response, Model model) {
        String categoryId = request.getParameter("categoryId");
        ArticleCategory articleCategory = articleCategoryRepository
                .findOne(Long.valueOf(categoryId));
        model.addAttribute("articleCategory", articleCategory);
        return "articleCategory/articleCategoryUpdate";
    }

    @RequestMapping(value = "/article/doSelect", method = RequestMethod.POST)
    public String doSelect(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        String title = request.getParameter("title");
        List<Article> articles = articleRepository.findByTitleContaining(title);
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @RequestMapping(value = "/articleCategory/doSelect", method = RequestMethod.POST)
    public String doArticleCategorySelect(HttpServletRequest request,
                                          HttpServletResponse response, Model model) {
        String name = request.getParameter("name");
        List<ArticleCategory> articleCategorys = articleCategoryRepository
                .findByNameContaining(name);
        model.addAttribute("articleCategorys", articleCategorys);
        return "articleCategory/articleCategoryList";
    }

    @RequestMapping(value = "/article/doInsert", method = RequestMethod.POST)
    public String doInsert(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            String ids = "";
            String id = "";
            Article article = new Article();
            article.setTitle(request.getParameter("title"));
            if (!request.getParameter("version").equals("")) {
                String version = request.getParameter("version");
                BigDecimal bd = new BigDecimal(version);
                article.setVersion(bd);
            }
            article.setUrlTitle(request.getParameter("urlTitle"));
            article.setType(request.getParameter("type"));
            article.setDescription(request.getParameter("description"));
            article.setContent(request.getParameter("content"));
            ids = request.getParameter("id");
            if (!ids.equals("")) {
                String[] strings = ids.split(",");
                for (int i = 0; i < strings.length; i++) {
                    id = strings[i];
                    ArticleCategory articleCategory1 = articleCategoryRepository
                            .findOne(Long.valueOf(id));
                    article.getArticleCategories().add(articleCategory1);
                }
            }
            articleRepository.save(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        return "redirect:articleList";
    }

    @RequestMapping(value = "/articleCategory/doInsert", method = RequestMethod.POST)
    public String doArticleCategoryInsert(HttpServletRequest request,
                                          HttpServletResponse response, Model model) {
        try {
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setName(request.getParameter("name"));
            articleCategory.setDescription(request.getParameter("description"));
            articleCategoryRepository.save(articleCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ArticleCategory> articleCategorys = articleService
                .getArticleCategorys();
        model.addAttribute("articleCategorys", articleCategorys);
        return "redirect:articleCategoryList";
    }

    @RequestMapping(value = "/article/doUpdate", method = RequestMethod.POST)
    public String doUpdate(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            BigDecimal version = null;
            String articleid = request.getParameter("articleid");
            if (!request.getParameter("version").equals("")) {
                String bd = request.getParameter("version");
                version = new BigDecimal(bd);
            }
            String id = "";
            String ids = "";
            String title = request.getParameter("title");
            String type = request.getParameter("type");
            String urlTitle = request.getParameter("urlTitle");
            String description = request.getParameter("description");
            String content = request.getParameter("content");
            Article article = new Article();
            article.setId(Long.valueOf(articleid));
            article.setVersion(version);
            article.setTitle(title);
            article.setType(type);
            article.setUrlTitle(urlTitle);
            article.setDescription(description);
            article.setContent(content);
            article.getArticleCategories().clear();
            ids = request.getParameter("id");
            if (!ids.equals("")) {
                String[] strings = ids.split(",");
                for (int i = 0; i < strings.length; i++) {
                    id = strings[i];
                    ArticleCategory articleCategory1 = articleCategoryRepository
                            .findOne(Long.valueOf(id));
                    article.getArticleCategories().add(articleCategory1);
                }
            }
            articleRepository.save(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        return "redirect:articleList";
    }

    @RequestMapping(value = "/articleCategory/doUpdate", method = RequestMethod.POST)
    public String doArticleCategoryUpdate(HttpServletRequest request,
                                          HttpServletResponse response, Model model) {
        try {
            String categoryId = request.getParameter("categoryId");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            articleService.updateArticleCategory(name, description,
                    Long.valueOf(categoryId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ArticleCategory> articleCategorys = articleService
                .getArticleCategorys();
        model.addAttribute("articleCategorys", articleCategorys);
        return "redirect:articleCategoryList";
    }

    @RequestMapping(value = "/article/deleteArticle")
    public String deleteArticle(HttpServletRequest request,
                                HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            articleRepository.delete(Long.valueOf(id));
            return "redirect:articleList";
        } catch (Exception e) {
            return "article/false";
        }
    }

    @RequestMapping(value = "/articleCategory/deleteArticleCategory")
    public String deleteArticleCategory(HttpServletRequest request,
                                        HttpServletResponse response) {
        String categoryId = request.getParameter("categoryId");
        try {
            articleCategoryRepository.delete(Long.valueOf(categoryId));
            return "redirect:articleCategoryList";
        } catch (Exception e) {
            return "article/false";
        }
    }

    @RequestMapping(value = "/article/exportExcel", method = RequestMethod.GET)
    public View processExportExcel(HttpServletRequest request,
                                   HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
                "attachment; filename=article.xls");
        Map<String, List<Article>> reportData = new HashMap<String, List<Article>>();
        List<Article> articles = articleService.getArticles();
        reportData.put("", articles);
        ReportView view = new ReportView();
        view.addStaticAttribute("reportData", reportData);
        return view;
    }

    @RequestMapping(value = "/articleCategory/getArticle")
    public
    @ResponseBody
    List<Article> getArticle(HttpServletRequest request,
                             HttpServletResponse response) {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }

    @RequestMapping(value = "/article/getArticleCategory")
    public
    @ResponseBody
    List<ArticleCategory> getArticleCategory(HttpServletRequest request,
                                             HttpServletResponse response) {
        List<ArticleCategory> articleCategorys = articleCategoryRepository
                .findAll();
        return articleCategorys;
    }
}
