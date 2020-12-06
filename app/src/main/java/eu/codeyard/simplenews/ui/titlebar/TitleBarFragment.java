package eu.codeyard.simplenews.ui.titlebar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import eu.codeyard.simplenews.R;

import static eu.codeyard.simplenews.ui.titlebar.TitleBarVisibilityState.*;

@EFragment(R.layout.fragment_title_bar)
public class TitleBarFragment extends Fragment {

    @ViewById
    protected TextView title;

    @ViewById
    protected ImageView icSearch;

    @ViewById
    protected MotionLayout motionContainer;

    @ViewById
    protected EditText searchText;

    @ViewById
    protected View background;

    private TitleBarViewModel titleBarViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        titleBarViewModel =
                ViewModelProviders.of(getActivity()).get(TitleBarViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscribeObservers();
    }

    private void subscribeObservers() {
        titleBarViewModel.getTitleBarPageState().observe(getViewLifecycleOwner(), this::handleTitleBarState);
    }

    private void handleTitleBarState(TitleBarPageState titleBarPageState) {
        String titleText = "";
        boolean searchIconVisible = true;
        TitleBarVisibilityState titleBarVisibilityState = VISIBLE;

        switch (titleBarPageState) {
            case FEED:
                titleText = getResources().getString(R.string.title_news);
                break;
            case BOOKMARK:
                titleText = getResources().getString(R.string.title_bookmark);
                break;
            case DETAILS:
                titleBarVisibilityState = GONE;
                searchIconVisible = false;
                break;
            case MORE:
                titleText = getResources().getString(R.string.title_more);
                searchIconVisible = false;
                break;
        }

        title.setText(titleText);
        icSearch.setVisibility(searchIconVisible ? View.VISIBLE : View.GONE);

        titleBarViewModel.setTitleBarVisibilityState(titleBarVisibilityState);

        if (titleBarViewModel.isSearchViewVisible()) {
            closeSearchView();
        }
    }

    private void showKeyboard() {
        if (isAdded() && getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideKeyboard() {
        if (isAdded()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
        }
    }

    private void closeSearchView() {
        if (isAdded()) {
            motionContainer.transitionToStart();
            hideKeyboard();
            searchText.clearFocus();

            titleBarViewModel.setSearchViewVisible(false);
            //titleBarViewModel.setTitleBarVisibilityState(VISIBLE);
        }
    }

    @Click(R.id.icSearch)
    protected void onSearchClick() {
        if (isAdded()) {
            titleBarViewModel.setSearchViewVisible(true);
            //titleBarViewModel.setTitleBarVisibilityState(SEARCH);

            motionContainer.setTransition(R.id.searchTrans);
            motionContainer.transitionToEnd();

            searchText.requestFocus();
            showKeyboard();
        }
    }

    @Click(R.id.icClose)
    protected void onSearchCloseClick() {
        if (isAdded()) {
            closeSearchView();
        }
    }
}
