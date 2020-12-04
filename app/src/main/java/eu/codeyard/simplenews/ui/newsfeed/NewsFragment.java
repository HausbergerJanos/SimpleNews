package eu.codeyard.simplenews.ui.newsfeed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;

import static androidx.recyclerview.widget.RecyclerView.*;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_news)
public class NewsFragment extends Fragment {

    @ViewById
    protected RecyclerView newsRecyclerView;

    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    protected void init() {
        newsAdapter = new NewsAdapter();

        initRecyclerView();

        newsViewModel.getArticles().observe(getViewLifecycleOwner(), this::handleNews);
    }

    public void handleNews(List<Article> articles) {
        if (articles != null && !articles.isEmpty()) {
            newsAdapter.submitList(articles);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        newsViewModel.syncNews(getViewLifecycleOwner());
    }

    private void initRecyclerView() {
        LayoutManager layoutManager = new LinearLayoutManager(getContext());
        newsRecyclerView.setLayoutManager(layoutManager);

        newsRecyclerView.setAdapter(newsAdapter);
    }
}