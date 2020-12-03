package eu.codeyard.simplenews.ui.newsfeed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.interactors.TestInsert;
import eu.codeyard.simplenews.business.interactors.TestInsert_;

public class NewsViewModel extends AndroidViewModel {

    private TestInsert testInsert;

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Article>> articles;

    public NewsViewModel(@NonNull Application application) {
        super(application);

        testInsert = TestInsert_.getInstance_(application);

        mText = new MutableLiveData<>();
        articles = new MutableLiveData<>();

        mText.setValue("This is news fragment");
    }

    public void insertArticle(Article article) {
        testInsert.executeTestInsertion(article);
    }

    public void getNewsFromCache(LifecycleOwner lifecycleOwner) {
        testInsert.getNewsFromCache().observe(lifecycleOwner, data -> articles.setValue(data));
    }

    public void getNewsFromNetwork(LifecycleOwner lifecycleOwner) {
        testInsert.getNewsFromNetwork().observe(lifecycleOwner, data -> articles.setValue(data));
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }
}