package eu.codeyard.simplenews.business.interactors.bookmark;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.util.List;

import eu.codeyard.simplenews.BaseApplication;
import eu.codeyard.simplenews.business.data.cache.abstraction.NewsCacheDataSource;
import eu.codeyard.simplenews.business.data.cache.implementation.NewsCacheDataSourceImpl;
import eu.codeyard.simplenews.business.data.network.implementation.NewsNetworkDataSourceImpl;
import eu.codeyard.simplenews.business.domain.model.Article;

@EBean(scope = EBean.Scope.Singleton)
public class GetBookmarkedNewsInteractor {

    @App
    protected BaseApplication app;

    private NewsCacheDataSource newsCacheDataSource;

    @AfterInject
    protected void init() {
        newsCacheDataSource = new NewsCacheDataSourceImpl(app);
    }

    public LiveData<List<Article>> getBookmarkedNews(LifecycleOwner lifecycleOwner) {
        // Init LiveData which will be updated from cache
        MutableLiveData<List<Article>> articles = new MutableLiveData<>();

        // Get cache data source
        LiveData<List<Article>> newsCacheData = newsCacheDataSource.getAllBookmarkedNews();

        // Observe cached data
        newsCacheData.observe(lifecycleOwner, data -> {
            // Set news from cache
            articles.setValue(data);

            // We don't want to observe cached data anymore
            newsCacheData.removeObservers(lifecycleOwner);
        });

        return articles;
    }
}
