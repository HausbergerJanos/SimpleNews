package eu.codeyard.simplenews.business.data.cache.implementation;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.codeyard.simplenews.business.data.cache.abstraction.NewsCacheDataSource;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.framework.datasource.cache.abstraction.NewsDaoService;
import eu.codeyard.simplenews.framework.datasource.cache.implementation.NewsDaoServiceImpl;

public class NewsCacheDataSourceImpl implements NewsCacheDataSource {

    private NewsDaoService newsDaoService;

    private NewsCacheDataSourceImpl() {
        // prevent initialization without context
    }

    public NewsCacheDataSourceImpl(Application application) {
        newsDaoService = new NewsDaoServiceImpl(application);
    }

    @Override
    public void insert(Article article) {
        newsDaoService.insert(article);
    }

    @Override
    public LiveData<List<Article>> getAllNews() {
        return newsDaoService.getAllNews();
    }

    @Override
    public LiveData<List<Article>> searchInNews(String key) {
        return newsDaoService.searchInNews(key);
    }

    @Override
    public LiveData<List<Article>> getAllBookmarkedNews() {
        return newsDaoService.getAllBookmarkedNews();
    }

    @Override
    public void updateNewsBookmarkedState(String title, boolean isBookmarked) {
        newsDaoService.updateNewsBookmarkedState(title, isBookmarked);
    }
}
