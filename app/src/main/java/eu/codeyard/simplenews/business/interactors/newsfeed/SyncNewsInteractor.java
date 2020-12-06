package eu.codeyard.simplenews.business.interactors.newsfeed;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.List;
import java.util.Objects;

import eu.codeyard.simplenews.BaseApplication;
import eu.codeyard.simplenews.business.data.cache.abstraction.NewsCacheDataSource;
import eu.codeyard.simplenews.business.data.cache.implementation.NewsCacheDataSourceImpl;
import eu.codeyard.simplenews.business.data.network.abstraction.NewsNetworkDataSource;
import eu.codeyard.simplenews.business.data.network.implementation.NewsNetworkDataSourceImpl;
import eu.codeyard.simplenews.business.domain.model.Article;

@EBean(scope = EBean.Scope.Singleton)
public class SyncNewsInteractor {

    @App
    protected BaseApplication app;

    private NewsCacheDataSource newsCacheDataSource;
    private NewsNetworkDataSource newsNetworkDataSource;

    @AfterInject
    protected void init() {
        newsCacheDataSource = new NewsCacheDataSourceImpl(app);
        newsNetworkDataSource = new NewsNetworkDataSourceImpl();
    }

    public LiveData<List<Article>> syncNews(LifecycleOwner lifecycleOwner) {
        // Init LiveData which will be updated first from cache and after that from network
        MutableLiveData<List<Article>> articles = new MutableLiveData<>();

        // Get cached data source
        LiveData<List<Article>> newsCacheData = newsCacheDataSource.getAllNews();

        // Observe cached data
        newsCacheData.observe(lifecycleOwner, data -> {
            // Set news from cache
            articles.setValue(data);

            // We don't want to observe cached data anymore
            newsCacheData.removeObservers(lifecycleOwner);

            // Fetch news from network
            getNewsFromNetwork(lifecycleOwner, articles);
        });

        return articles;
    }

    private void getNewsFromNetwork(LifecycleOwner lifecycleOwner, MutableLiveData<List<Article>> liveData) {
        newsNetworkDataSource.getTopNews().observe(lifecycleOwner, articles -> {
            // Update cache with news from the network
            insertArticles(lifecycleOwner, articles, liveData);
        });
    }

    @Background
    protected void insertArticles(LifecycleOwner lifecycleOwner, List<Article> articlesFromNet, MutableLiveData<List<Article>> liveData) {
        for (Article article : articlesFromNet) {
            newsCacheDataSource.insert(article);
        }

        getCachedNewsOnUI(lifecycleOwner, liveData);
    }

    @UiThread
    protected void getCachedNewsOnUI(LifecycleOwner lifecycleOwner, MutableLiveData<List<Article>> liveData) {
        newsCacheDataSource.getAllNews().observe(lifecycleOwner, data -> {
            // Set news from cache
            liveData.setValue(data);

            // We don't want to observe cached data anymore
            liveData.removeObservers(lifecycleOwner);
        });
    }
}
