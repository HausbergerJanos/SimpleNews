package eu.codeyard.simplenews.framework.presentation.newsdetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.interactors.common.UpdateBookmarkedStateInteractor;
import eu.codeyard.simplenews.business.interactors.common.UpdateBookmarkedStateInteractor_;
import eu.codeyard.simplenews.business.interactors.newsfeed.SyncNewsInteractor;
import eu.codeyard.simplenews.business.interactors.newsfeed.SyncNewsInteractor_;

public class NewsDetailsViewModel extends AndroidViewModel {

    private UpdateBookmarkedStateInteractor updateBookmarkedStateInteractor;

    public NewsDetailsViewModel(@NonNull Application application) {
        super(application);

        updateBookmarkedStateInteractor = UpdateBookmarkedStateInteractor_.getInstance_(application);
    }

    public void updateNewsBookmarkedState(String title, boolean isBookmarked) {
        updateBookmarkedStateInteractor.updateNewsBookmarkedState(title, isBookmarked);
    }
}