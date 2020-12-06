package eu.codeyard.simplenews.framework.datasource.cache.abstraction;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;

public interface NewsDaoService {

    void insert(Article article);

    LiveData<List<Article>> getAllNews();

    LiveData<List<Article>> searchInNews(String key);

    LiveData<List<Article>> getAllBookmarkedNews();

    void updateNewsBookmarkedState(String title, boolean isBookmarked);
}
