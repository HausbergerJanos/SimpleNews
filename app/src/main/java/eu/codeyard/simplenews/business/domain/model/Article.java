package eu.codeyard.simplenews.business.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable {

    private String title;

    private String description;

    private String url;

    private String urlToImage;

    private Long publishedAt;

    private String source;

    private String author;

    public Article(String title, String description, String url, String urlToImage, Long publishedAt, String source, String author) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.source = source;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Long publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        try {
            Article article = (Article) o;
            return getTitle().equals(article.getTitle()) &&
                    getDescription().equals(article.getDescription()) &&
                    getUrl().equals(article.getUrl()) &&
                    Objects.equals(getUrlToImage(), article.getUrlToImage()) &&
                    getPublishedAt().equals(article.getPublishedAt()) &&
                    Objects.equals(getSource(), article.getSource());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getTitle(),
                getDescription(),
                getUrl(),
                getUrlToImage(),
                getPublishedAt(),
                getSource()
        );
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt=" + publishedAt +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
