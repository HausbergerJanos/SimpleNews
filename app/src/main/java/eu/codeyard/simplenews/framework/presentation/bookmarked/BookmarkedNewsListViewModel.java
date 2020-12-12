package eu.codeyard.simplenews.framework.presentation.bookmarked;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.interactors.bookmark.GetBookmarkedNewsInteractor;
import eu.codeyard.simplenews.business.interactors.bookmark.GetBookmarkedNewsInteractor_;
import eu.codeyard.simplenews.business.interactors.common.SearchNewsInteractor;
import eu.codeyard.simplenews.business.interactors.common.SearchNewsInteractor_;
import eu.codeyard.simplenews.business.interactors.common.UpdateBookmarkedStateInteractor;
import eu.codeyard.simplenews.business.interactors.common.UpdateBookmarkedStateInteractor_;

public class BookmarkedNewsListViewModel extends AndroidViewModel {

    private GetBookmarkedNewsInteractor getBookmarkedNewsInteractor;
    private UpdateBookmarkedStateInteractor updateBookmarkedStateInteractor;
    private SearchNewsInteractor searchNewsInteractor;

    private MutableLiveData<List<Article>> articles;

    public BookmarkedNewsListViewModel(@NonNull Application application) {
        super(application);

        getBookmarkedNewsInteractor = GetBookmarkedNewsInteractor_.getInstance_(application);
        updateBookmarkedStateInteractor = UpdateBookmarkedStateInteractor_.getInstance_(application);
        searchNewsInteractor = SearchNewsInteractor_.getInstance_(application);

        articles = new MutableLiveData<>();
    }

    public void searchInBookmarkedNews(LifecycleOwner lifecycleOwner, String query) {
        searchNewsInteractor.searchInNews(lifecycleOwner, query, true).observe(lifecycleOwner, data -> articles.setValue(data));
    }

    public void getAllBookmarkedNews(LifecycleOwner lifecycleOwner) {
        getBookmarkedNewsInteractor.getBookmarkedNews(lifecycleOwner).observe(lifecycleOwner, data -> articles.setValue(data));
    }

    public void updateNewsBookmarkedState(String title, boolean isBookmarked) {
        updateBookmarkedStateInteractor.updateNewsBookmarkedState(title, isBookmarked);
    }

    /**
     * Just for data observation
     */
    public LiveData<List<Article>> getArticles() {
        return articles;
    }
}