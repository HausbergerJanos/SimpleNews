package eu.codeyard.simplenews.ui.profile;

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
import org.w3c.dom.Text;

import eu.codeyard.simplenews.R;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends Fragment {

    @ViewById
    protected TextView profileTitle;

    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    protected void init() {
        profileViewModel.getText().observe(getViewLifecycleOwner(), s -> profileTitle.setText(s));
    }
}