package eu.codeyard.simplenews.ui.newsfeed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.interactors.SyncNewsInteractor;
import eu.codeyard.simplenews.business.interactors.SyncNewsInteractor_;

public class NewsViewModel extends AndroidViewModel {

    private SyncNewsInteractor syncNewsInteractor;

    private MutableLiveData<List<Article>> articles;

    public NewsViewModel(@NonNull Application application) {
        super(application);

        syncNewsInteractor = SyncNewsInteractor_.getInstance_(application);

        articles = new MutableLiveData<>();
    }

    public void syncNews(LifecycleOwner lifecycleOwner) {
        syncNewsInteractor.syncNews(lifecycleOwner).observe(lifecycleOwner, data -> articles.setValue(data));
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }
}