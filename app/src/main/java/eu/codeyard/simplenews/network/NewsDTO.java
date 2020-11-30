package eu.codeyard.simplenews.network;

import java.util.List;

public class NewsDTO {

    protected List<ArticleDTO> articles;

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }
}
