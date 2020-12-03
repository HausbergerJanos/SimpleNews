package eu.codeyard.simplenews.business.data.network.implementation;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.codeyard.simplenews.business.data.network.abstraction.NewsNetworkDataSource;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.framework.datasource.network.abstraction.NewsRepository;
import eu.codeyard.simplenews.framework.datasource.network.implementation.NewsRepositoryImpl;

public class NewsNetworkDataSourceImpl implements NewsNetworkDataSource {

    private NewsRepository newsRepository;

    public NewsNetworkDataSourceImpl() {
        newsRepository = new NewsRepositoryImpl();
    }

    @Override
    public LiveData<List<Article>> getTopNews() {
        return newsRepository.getTopNews();
    }
}
