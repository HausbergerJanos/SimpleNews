package eu.codeyard.simplenews.framework.presentation.more;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eu.codeyard.simplenews.R;

public class MoreViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    public MoreViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue(application.getString(R.string.project_description));
    }

    public LiveData<String> getText() {
        return mText;
    }
}