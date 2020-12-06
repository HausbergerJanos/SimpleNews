package eu.codeyard.simplenews.business.interactors.common;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import eu.codeyard.simplenews.BaseApplication;
import eu.codeyard.simplenews.business.data.cache.abstraction.NewsCacheDataSource;
import eu.codeyard.simplenews.business.data.cache.implementation.NewsCacheDataSourceImpl;

@EBean(scope = EBean.Scope.Singleton)
public class UpdateBookmarkedStateInteractor {

    @App
    protected BaseApplication app;

    private NewsCacheDataSource newsCacheDataSource;

    @AfterInject
    protected void init() {
        newsCacheDataSource = new NewsCacheDataSourceImpl(app);
    }

    @Background
    public void updateNewsBookmarkedState(String title, boolean isBookmarked) {
        newsCacheDataSource.updateNewsBookmarkedState(title, isBookmarked);
    }
}
