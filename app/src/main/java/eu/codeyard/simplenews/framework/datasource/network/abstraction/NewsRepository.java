package eu.codeyard.simplenews.framework.datasource.network.abstraction;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;

public interface NewsRepository {

    LiveData<List<Article>> getTopNews();
}
