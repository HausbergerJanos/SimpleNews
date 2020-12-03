package eu.codeyard.simplenews.business.data.cache.implementation;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.codeyard.simplenews.business.data.cache.abstraction.NewsCacheDataSource;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.framework.datasource.cache.abstraction.ArticlesDaoService;
import eu.codeyard.simplenews.framework.datasource.cache.implementation.ArticlesDaoServiceImpl;

public class NewsCacheDataSourceImpl implements NewsCacheDataSource {

    private ArticlesDaoService articlesDaoService;

    private NewsCacheDataSourceImpl() {
        // prevent initialization without context
    }

    public NewsCacheDataSourceImpl(Application application) {
        articlesDaoService = new ArticlesDaoServiceImpl(application);
    }

    @Override
    public void insert(Article article) {
        articlesDaoService.insert(article);
    }

    @Override
    public LiveData<List<Article>> getAllNews() {
        return articlesDaoService.getAllNews();
    }
}
