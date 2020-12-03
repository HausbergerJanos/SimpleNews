package eu.codeyard.simplenews.business.interactors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.util.List;

import eu.codeyard.simplenews.BaseApplication;
import eu.codeyard.simplenews.business.data.cache.abstraction.NewsCacheDataSource;
import eu.codeyard.simplenews.business.data.cache.implementation.NewsCacheDataSourceImpl;
import eu.codeyard.simplenews.business.data.network.abstraction.NewsNetworkDataSource;
import eu.codeyard.simplenews.business.data.network.implementation.NewsNetworkDataSourceImpl;
import eu.codeyard.simplenews.business.domain.model.Article;

@EBean(scope = EBean.Scope.Singleton)
public class TestInsert {

    @App
    protected BaseApplication app;

    private NewsCacheDataSource newsCacheDataSource;
    private NewsNetworkDataSource newsNetworkDataSource;

    @AfterInject
    protected void init() {
        newsCacheDataSource = new NewsCacheDataSourceImpl(app);
        newsNetworkDataSource = new NewsNetworkDataSourceImpl();
    }

    @Background
    public void executeTestInsertion(Article article) {
        newsCacheDataSource.insert(article);
    }

    public LiveData<List<Article>> getNewsFromNetwork() {
        return newsNetworkDataSource.getTopNews();
    }

    public LiveData<List<Article>> getNewsFromCache() {
        return newsCacheDataSource.getAllNews();
    }
}
