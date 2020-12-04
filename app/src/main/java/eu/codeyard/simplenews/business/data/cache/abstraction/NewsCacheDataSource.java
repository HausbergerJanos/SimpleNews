package eu.codeyard.simplenews.business.data.cache.abstraction;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;

public interface NewsCacheDataSource {

    void insert(Article article);

    LiveData<List<Article>> getAllNews();
}