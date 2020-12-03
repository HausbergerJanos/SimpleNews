package eu.codeyard.simplenews.business.data.network.abstraction;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;

public interface NewsNetworkDataSource {

    LiveData<List<Article>> getTopNews();
}
