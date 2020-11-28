package eu.codeyard.simplenews.ui.favourites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import eu.codeyard.simplenews.R;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_favourites)
public class FavouritesFragment extends Fragment {

    @ViewById
    protected TextView favouritesTitle;

    private FavouritesViewModel favouritesViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        favouritesViewModel =
                new ViewModelProvider(this).get(FavouritesViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    protected void init() {
        favouritesViewModel.getText().observe(getViewLifecycleOwner(), s -> favouritesTitle.setText(s));
    }
}