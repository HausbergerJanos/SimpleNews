package eu.codeyard.simplenews.framework.datasource.cache.implementation;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import org.androidannotations.annotations.EBean;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.framework.datasource.cache.abstraction.ArticlesDaoService;
import eu.codeyard.simplenews.framework.datasource.cache.database.AppDatabase;
import eu.codeyard.simplenews.framework.datasource.cache.database.ArticlesDao;
import eu.codeyard.simplenews.framework.datasource.cache.mapper.ArticleCacheMapper;
import eu.codeyard.simplenews.framework.datasource.cache.model.ArticleCacheEntity;

public class ArticlesDaoServiceImpl implements ArticlesDaoService {

    private ArticlesDao articlesDao;
    private ArticleCacheMapper articleCacheMapper;

    private ArticlesDaoServiceImpl() {
        // prevent initialization without context
    }

    public ArticlesDaoServiceImpl(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        articlesDao = appDatabase.getArticlesDao();
        articleCacheMapper = new ArticleCacheMapper();
    }

    @Override
    public void insert(Article article) {
        articlesDao.insert(
                articleCacheMapper.mapToEntity(article)
        );
    }

    @Override
    public LiveData<List<Article>> getAllArticles() {
        return Transformations.map(articlesDao.getAllArticles(), input ->
                articleCacheMapper.mapFromEntityList(input));
    }
}
