package eu.codeyard.simplenews.ui.newsfeed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.interactors.common.SearchNewsInteractor;
import eu.codeyard.simplenews.business.interactors.common.SearchNewsInteractor_;
import eu.codeyard.simplenews.business.interactors.common.UpdateBookmarkedStateInteractor;
import eu.codeyard.simplenews.business.interactors.common.UpdateBookmarkedStateInteractor_;
import eu.codeyard.simplenews.business.interactors.newsfeed.SyncNewsInteractor;
import eu.codeyard.simplenews.business.interactors.newsfeed.SyncNewsInteractor_;

public class NewsViewModel extends AndroidViewModel {

    private SyncNewsInteractor syncNewsInteractor;
    private UpdateBookmarkedStateInteractor updateBookmarkedStateInteractor;
    private SearchNewsInteractor searchNewsInteractor;

    private MutableLiveData<List<Article>> articles;

    public NewsViewModel(@NonNull Application application) {
        super(application);

        syncNewsInteractor = SyncNewsInteractor_.getInstance_(application);
        updateBookmarkedStateInteractor = UpdateBookmarkedStateInteractor_.getInstance_(application);
        searchNewsInteractor = SearchNewsInteractor_.getInstance_(application);

        articles = new MutableLiveData<>();
    }

    public void syncNews(LifecycleOwner lifecycleOwner) {
        syncNewsInteractor.syncNews(lifecycleOwner).observe(lifecycleOwner, data -> articles.setValue(data));
    }

    public void updateNewsBookmarkedState(String title, boolean isBookmarked) {
        updateBookmarkedStateInteractor.updateNewsBookmarkedState(title, isBookmarked);
    }

    public void searchNews(LifecycleOwner lifecycleOwner, String key) {
        searchNewsInteractor.searchInNews(lifecycleOwner, key, false).observe(lifecycleOwner, data -> articles.setValue(data));
    }

    /**
     * Just for data observation
     */
    public LiveData<List<Article>> getArticles() {
        return articles;
    }
}