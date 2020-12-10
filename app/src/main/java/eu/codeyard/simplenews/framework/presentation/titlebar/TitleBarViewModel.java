package eu.codeyard.simplenews.framework.presentation.titlebar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TitleBarViewModel extends ViewModel {

    private MutableLiveData<TitleBarPageState> titleBarPageState;
    private MutableLiveData<TitleBarVisibilityState> titleBarVisibilityState;
    private MutableLiveData<String> searchQuery;

    private boolean searchViewVisible;
    private boolean shouldPersistSearchResult;

    public TitleBarViewModel() {
        titleBarPageState = new MutableLiveData<>();
        titleBarVisibilityState = new MutableLiveData<>();
        searchQuery = new MutableLiveData<>();
    }

    public void setTitleBarPageState(TitleBarPageState desiredTitleBarPageState) {
        titleBarPageState.setValue(desiredTitleBarPageState);
    }

    public LiveData<TitleBarPageState> getTitleBarPageState() {
        return titleBarPageState;
    }

    public void setTitleBarVisibilityState(TitleBarVisibilityState visibilityState) {
        TitleBarVisibilityState actualState = titleBarVisibilityState.getValue();
        if (actualState != visibilityState) {
            titleBarVisibilityState.setValue(visibilityState);
        }
    }

    public LiveData<TitleBarVisibilityState> getTitleBarVisibilityState() {
        return titleBarVisibilityState;
    }

    public boolean isSearchViewVisible() {
        return searchViewVisible;
    }

    public void setSearchViewVisible(boolean searchViewVisible) {
        this.searchViewVisible = searchViewVisible;
    }

    public boolean shouldPersistSearchResult() {
        return shouldPersistSearchResult;
    }

    public void setShouldPersistSearchResult(boolean shouldPersistSearchResult) {
        this.shouldPersistSearchResult = shouldPersistSearchResult;
    }

    public void setSearchQuery(String key) {
        searchQuery.setValue(key);
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }
}
