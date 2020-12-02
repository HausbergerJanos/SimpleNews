package eu.codeyard.simplenews.ui.newsfeed;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.interactors.TestInsert;
import eu.codeyard.simplenews.business.interactors.TestInsert_;
import eu.codeyard.simplenews.network.NewsDTO;
import eu.codeyard.simplenews.network.NewsNetworkDataSource;
import eu.codeyard.simplenews.network.NewsNetworkDataSource_;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends AndroidViewModel {

    protected NewsNetworkDataSource newsNetworkDataSource;
    private TestInsert testInsert;

    private MutableLiveData<String> mText;

    private LiveData<List<Article>> articles;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        testInsert = TestInsert_.getInstance_(application);
        articles = testInsert.getAllArticles();
        mText.setValue("This is news fragment");
    }

    public void getNewsFeed() {
        newsNetworkDataSource.getTopNews("hu", "df0796c1dec749f8a7296e59a2e4a1cd", new Callback<NewsDTO>() {
            @Override
            public void onResponse(Call<NewsDTO> call, Response<NewsDTO> response) {
                Log.d("TAG", "onResponse: ");
                mText.setValue(response.body().getArticles().get(0).getTitle());
            }

            @Override
            public void onFailure(Call<NewsDTO> call, Throwable t) {
                Log.d("", "onFailure:");
            }
        });
    }

    public void getAllArticles() {
        articles = testInsert.getAllArticles();
    }

    public void testInsert() {
        testInsert.executeTestInsertion();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }
}