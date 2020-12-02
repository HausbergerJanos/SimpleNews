package eu.codeyard.simplenews.business.interactors;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.List;
import java.util.UUID;

import eu.codeyard.simplenews.BaseApplication;
import eu.codeyard.simplenews.business.data.cache.abstraction.ArticlesCacheDataSource;
import eu.codeyard.simplenews.business.data.cache.implementation.ArticlesCacheDataSourceImpl;
import eu.codeyard.simplenews.business.domain.model.Article;

@EBean(scope = EBean.Scope.Singleton)
public class TestInsert {

    @App
    protected BaseApplication app;

    private ArticlesCacheDataSource articlesCacheDataSource;

    @AfterInject
    protected void init() {
        articlesCacheDataSource = new ArticlesCacheDataSourceImpl(app);
    }

    @Background
    public void executeTestInsertion() {
        Article article = new Article(
                "Ez egy teszt",
                "Lorem ipsum",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                184484L
        );

        articlesCacheDataSource.insert(article);
    }

    public LiveData<List<Article>> getAllArticles() {
        return articlesCacheDataSource.getAllArticle();
    }
}
