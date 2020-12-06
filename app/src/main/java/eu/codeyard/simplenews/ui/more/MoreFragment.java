package eu.codeyard.simplenews.ui.more;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.ui.titlebar.TitleBarViewModel;

import static eu.codeyard.simplenews.ui.titlebar.TitleBarPageState.MORE;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_profile)
public class MoreFragment extends Fragment {

    @ViewById
    protected TextView profileTitle;

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

    @AfterViews
    protected void init() {
        subscribeObservers();
    }

    private void subscribeObservers() {
        moreViewModel.getText().observe(getViewLifecycleOwner(), s -> profileTitle.setText(s));
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpTitleBar();
    }

    private void setUpTitleBar() {
        titleBarViewModel.setTitleBarPageState(MORE);
    }
}