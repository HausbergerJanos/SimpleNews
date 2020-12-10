package eu.codeyard.simplenews.framework.datasource.cache.implementation;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.framework.datasource.cache.abstraction.NewsDaoService;
import eu.codeyard.simplenews.framework.datasource.cache.database.AppDatabase;
import eu.codeyard.simplenews.framework.datasource.cache.database.NewsDao;
import eu.codeyard.simplenews.framework.datasource.cache.mapper.ArticleCacheMapper;
import eu.codeyard.simplenews.framework.datasource.cache.model.ArticleCacheEntity;

public class NewsDaoServiceImpl implements NewsDaoService {

    private NewsDao newsDao;
    private ArticleCacheMapper articleCacheMapper;

    private NewsDaoServiceImpl() {
        // prevent initialization without context
    }

    public NewsDaoServiceImpl(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        newsDao = appDatabase.getArticlesDao();
        articleCacheMapper = new ArticleCacheMapper();
    }

    @Override
    public void insert(Article article) {
        newsDao.insert(
                articleCacheMapper.mapToEntity(article)
        );
    }

    @Override
    public void updateNewsBookmarkedState(String title, boolean isBookmarked) {
        newsDao.updateNewsBookmarkedState(title, isBookmarked);
    }

    @Override
    public LiveData<List<Article>> getAllNews() {
        return Transformations.map(newsDao.getAllArticles(), input ->
                articleCacheMapper.mapFromEntityList(input));
    }

    @Override
    public LiveData<List<Article>> searchInNews(String query) {
        return Transformations.map(newsDao.searchInNews(query), input ->
                articleCacheMapper.mapFromEntityList(input));
    }

    @Override
    public LiveData<List<Article>> getAllBookmarkedNews() {
        return Transformations.map(newsDao.getAllBookmarkedArticles(), input ->
                articleCacheMapper.mapFromEntityList(input));
    }
}
