package eu.codeyard.simplenews.framework.datasource.network.mapper;

import java.util.ArrayList;
import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.domain.util.DateUtil;
import eu.codeyard.simplenews.business.domain.util.EntityMapper;
import eu.codeyard.simplenews.framework.datasource.network.model.ArticleDTO;
import eu.codeyard.simplenews.framework.datasource.network.model.SourceDTO;

public class ArticleNetworkMapper implements EntityMapper<ArticleDTO, Article> {

    @Override
    public Article mapFromEntity(ArticleDTO articleDTO) {
        return new Article(
                articleDTO.getTitle(),
                articleDTO.getDescription(),
                articleDTO.getUrl(),
                articleDTO.getUrlToImage(),
                DateUtil.convertTimeToMilliSec(articleDTO.getPublishedAt()),
                articleDTO.getSource() != null ? articleDTO.getSource().getName() : "",
                articleDTO.getAuthor()
        );
    }

    @Override
    public ArticleDTO mapToEntity(Article article) {
        return new ArticleDTO(
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                DateUtil.convertMilliSecToTime(article.getPublishedAt()),
                new SourceDTO(article.getSource()),
                article.getAuthor()
        );
    }

    public List<Article> mapFromEntityList(List<ArticleDTO> articleDTOList) {
        ArrayList<Article> articles = new ArrayList<>();
        for (ArticleDTO entity : articleDTOList) {
            articles.add(mapFromEntity(entity));
        }
        return articles;
    }

    public List<ArticleDTO> mapToEntity(List<Article> articleList) {
        ArrayList<ArticleDTO> articleDTOs = new ArrayList<>();
        for (Article article : articleList) {
            articleDTOs.add(mapToEntity(article));
        }
        return articleDTOs;
    }
}
