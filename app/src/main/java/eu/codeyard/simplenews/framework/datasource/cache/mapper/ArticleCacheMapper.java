package eu.codeyard.simplenews.framework.datasource.cache.mapper;

import java.util.ArrayList;
import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.domain.util.EntityMapper;
import eu.codeyard.simplenews.framework.datasource.cache.model.ArticleCacheEntity;

public class ArticleCacheMapper implements EntityMapper<ArticleCacheEntity, Article> {

    @Override
    public Article mapFromEntity(ArticleCacheEntity articleCacheEntity) {
        return new Article(
                articleCacheEntity.getTitle(),
                articleCacheEntity.getDescription(),
                articleCacheEntity.getUrl(),
                articleCacheEntity.getUrlToImage(),
                articleCacheEntity.getPublishedAt(),
                articleCacheEntity.getSource(),
                articleCacheEntity.getAuthor()
        );
    }

    @Override
    public ArticleCacheEntity mapToEntity(Article article) {
        return new ArticleCacheEntity(
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getPublishedAt(),
                article.getSource(),
                article.getAuthor()
        );
    }

    public List<Article> mapFromEntityList(List<ArticleCacheEntity> articleCacheEntityList) {
        ArrayList<Article> articles = new ArrayList<>();
        for (ArticleCacheEntity entity : articleCacheEntityList) {
            articles.add(mapFromEntity(entity));
        }
        return articles;
    }

    public List<ArticleCacheEntity> mapToEntity(List<Article> articleList) {
        ArrayList<ArticleCacheEntity> articleCacheEntities = new ArrayList<>();
        for (Article article : articleList) {
            articleCacheEntities.add(mapToEntity(article));
        }
        return articleCacheEntities;
    }
}
