package eu.codeyard.simplenews.framework.datasource.cache.abstraction;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;

public interface ArticlesDaoService {

    void insert(Article article);

    LiveData<List<Article>> getAllNews();
}
