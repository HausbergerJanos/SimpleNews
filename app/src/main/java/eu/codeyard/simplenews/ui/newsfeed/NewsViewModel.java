package eu.codeyard.simplenews.ui.newsfeed;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import eu.codeyard.simplenews.network.NewsDTO;
import eu.codeyard.simplenews.network.NewsNetworkDataSource;
import eu.codeyard.simplenews.network.NewsNetworkDataSource_;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    protected NewsNetworkDataSource newsNetworkDataSource;

    private MutableLiveData<String> mText;

    public NewsViewModel() {
        mText = new MutableLiveData<>();
    }

    public void initViewModel(Context context) {
        newsNetworkDataSource = NewsNetworkDataSource_.getInstance_(context);
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

    public LiveData<String> getText() {
        return mText;
    }
}