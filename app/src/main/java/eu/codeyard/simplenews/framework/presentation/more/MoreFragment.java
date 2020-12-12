package eu.codeyard.simplenews.framework.presentation.more;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import eu.codeyard.simplenews.WebViewActivity_;
import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarViewModel;
import eu.codeyard.simplenews.framework.presentation.util.Constants;

import static eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarPageState.MORE;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_more)
public class MoreFragment extends Fragment {

    @ViewById
    protected TextView descriptionDetail;

    private MoreViewModel moreViewModel;
    private TitleBarViewModel titleBarViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moreViewModel =
                new ViewModelProvider(this).get(MoreViewModel.class);

        titleBarViewModel =
                ViewModelProviders.of(getActivity()).get(TitleBarViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void goToRepo() {
        if (isAdded()) {
            WebViewActivity_.intent(getActivity())
                    .url(Constants.GITHUB_REPO)
                    .start();
        }
    }

    @AfterViews
    protected void init() {
        subscribeObservers();
    }

    private void subscribeObservers() {
        moreViewModel.getText().observe(getViewLifecycleOwner(), s -> descriptionDetail.setText(s));
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpTitleBar();
    }

    @Click(R.id.goToRepoButton)
    public void onGoToRepoClick() {
        goToRepo();
    }

    private void setUpTitleBar() {
        titleBarViewModel.setTitleBarPageState(MORE);
    }
}