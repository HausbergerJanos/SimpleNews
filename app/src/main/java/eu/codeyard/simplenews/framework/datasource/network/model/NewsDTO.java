package eu.codeyard.simplenews.framework.datasource.network.model;

import java.util.List;

public class NewsDTO {

    private int totalResults;

    private List<ArticleDTO> articles;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }
}
