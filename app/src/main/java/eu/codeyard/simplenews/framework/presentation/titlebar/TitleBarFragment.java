package eu.codeyard.simplenews.framework.presentation.titlebar;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import eu.codeyard.simplenews.R;

import static eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarVisibilityState.*;

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

    @AfterViews
    protected void init() {
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (titleBarViewModel != null) {
                    titleBarViewModel.setSearchQuery(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                if (titleBarViewModel.isSearchViewVisible()) {
                    titleBarViewModel.setShouldPersistSearchResult(true);
                }
                break;
            case MORE:
                titleText = getResources().getString(R.string.title_more);
                searchIconVisible = false;
                titleBarViewModel.setShouldPersistSearchResult(false);
                break;
        }

        if (titleBarViewModel.isSearchViewVisible()) {
            closeSearchView(!titleBarViewModel.shouldPersistSearchResult());
        }

        title.setText(titleText);
        icSearch.setVisibility(searchIconVisible ? View.VISIBLE : View.GONE);

        titleBarViewModel.setTitleBarVisibilityState(titleBarVisibilityState);

        if (!titleBarPageState.equals(TitleBarPageState.DETAILS)) {
            titleBarViewModel.setShouldPersistSearchResult(false);
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

    private void closeSearchView(boolean clear) {
        if (isAdded()) {
            if (clear) {
                motionContainer.transitionToStart();
                titleBarViewModel.setSearchViewVisible(false);
                titleBarViewModel.setTitleBarVisibilityState(VISIBLE);
                titleBarViewModel.setShouldPersistSearchResult(false);

                searchText.setText("");
            }

            searchText.clearFocus();
            hideKeyboard();
        }
    }

    @Click(R.id.icSearch)
    protected void onSearchClick() {
        if (isAdded()) {
            titleBarViewModel.setSearchViewVisible(true);
            titleBarViewModel.setTitleBarVisibilityState(SEARCH);

            motionContainer.setTransition(R.id.searchTrans);
            motionContainer.transitionToEnd();

            searchText.requestFocus();
            showKeyboard();
        }
    }

    @Click(R.id.icClose)
    protected void onSearchCloseClick() {
        if (isAdded()) {
            closeSearchView(true);
        }
    }
}
