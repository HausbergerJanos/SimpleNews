package eu.codeyard.simplenews.framework.datasource.cache.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.codeyard.simplenews.framework.datasource.cache.model.ArticleCacheEntity;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ArticleCacheEntity article);

    @Query("SELECT * FROM articles ORDER BY published_at DESC")
    LiveData<List<ArticleCacheEntity>> getAllArticles();

    @Query("SELECT * FROM articles WHERE tile LIKE '%' || :query || '%' ORDER BY published_at DESC")
    LiveData<List<ArticleCacheEntity>> searchInNews(String query);

    @Query("SELECT * FROM articles WHERE tile LIKE '%' || :query || '%' AND is_bookmarked ORDER BY published_at DESC")
    LiveData<List<ArticleCacheEntity>> searchInBookmarkedNews(String query);

    @Query("SELECT * FROM articles WHERE is_bookmarked ORDER BY published_at DESC")
    LiveData<List<ArticleCacheEntity>> getAllBookmarkedArticles();

    @Query("UPDATE articles SET is_bookmarked = :isBookmarked WHERE tile = :title")
    void updateNewsBookmarkedState(String title, boolean isBookmarked);
}
