package eu.codeyard.simplenews.framework.datasource.cache.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import eu.codeyard.simplenews.framework.datasource.cache.model.ArticleCacheEntity;

@Database(
        entities = {
                ArticleCacheEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String APP_DATABASE_NAME = "app_database";

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    APP_DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract ArticlesDao getArticlesDao();
}
