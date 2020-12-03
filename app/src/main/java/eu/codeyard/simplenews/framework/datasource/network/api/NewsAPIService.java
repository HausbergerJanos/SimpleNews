package eu.codeyard.simplenews.framework.datasource.network.api;

import eu.codeyard.simplenews.framework.datasource.network.model.NewsDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPIService {

    @GET("top-headlines")
    Call<NewsDTO> getTopNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
