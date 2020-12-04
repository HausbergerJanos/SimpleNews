package eu.codeyard.simplenews.framework.datasource.cache.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.codeyard.simplenews.framework.datasource.cache.model.ArticleCacheEntity;

@Dao
public interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticleCacheEntity article);

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    LiveData<List<ArticleCacheEntity>> getAllArticles();
}
