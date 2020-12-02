package eu.codeyard.simplenews.ui.newsfeed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_news)
public class NewsFragment extends Fragment {

    @ViewById
    protected TextView newsTitle;

    private NewsViewModel newsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    protected void init() {
        newsViewModel.getText().observe(getViewLifecycleOwner(), s -> newsTitle.setText(s));
        newsViewModel.getArticles().observe(getViewLifecycleOwner(), articles ->
                newsTitle.setText(articles.get(0).getTitle())
        );
        newsViewModel.getAllArticles();
    }
}