package eu.codeyard.simplenews.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPIService {

    @GET("top-headlines")
    Call<NewsDTO> getTopNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
