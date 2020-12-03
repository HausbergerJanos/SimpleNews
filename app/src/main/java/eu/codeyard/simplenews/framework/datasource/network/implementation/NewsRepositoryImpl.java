package eu.codeyard.simplenews.framework.datasource.network.implementation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.framework.datasource.network.abstraction.NewsRepository;
import eu.codeyard.simplenews.framework.datasource.network.api.NewsAPIService;
import eu.codeyard.simplenews.framework.datasource.network.mapper.ArticleNetworkMapper;
import eu.codeyard.simplenews.framework.datasource.network.model.NewsDTO;
import eu.codeyard.simplenews.network.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsRepositoryImpl extends Repository implements NewsRepository {

    private NewsAPIService newsAPIService;
    private ArticleNetworkMapper articleNetworkMapper;

    @Override
    public void init() {
        articleNetworkMapper = new ArticleNetworkMapper();
    }

    @Override
    public void initAPIService(Retrofit retrofit) {
        newsAPIService = retrofit.create(NewsAPIService.class);
    }

    @Override
    public LiveData<List<Article>> getTopNews() {
        MutableLiveData<List<Article>> newsData = new MutableLiveData<>();
        newsAPIService.getTopNews("hu", "df0796c1dec749f8a7296e59a2e4a1cd").enqueue(new Callback<NewsDTO>() {
            @Override
            public void onResponse(@NotNull Call<NewsDTO> call, @NotNull Response<NewsDTO> response) {
                if (response.body() != null) {
                    newsData.setValue(articleNetworkMapper.mapFromEntityList(response.body().getArticles()));
                }
            }

            @Override
            public void onFailure(Call<NewsDTO> call, Throwable t) {

            }
        });
        return newsData;
    }
}
