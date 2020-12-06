package eu.codeyard.simplenews.ui.newsfeed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.ui.titlebar.TitleBarViewModel;

import static androidx.recyclerview.widget.RecyclerView.*;
import static eu.codeyard.simplenews.ui.newsfeed.NewsAdapter.*;
import static eu.codeyard.simplenews.ui.titlebar.TitleBarPageState.*;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_news)
public class NewsFragment extends Fragment implements Interaction {

    @ViewById
    protected RecyclerView newsRecyclerView;

    @ViewById
    protected TextView emptyTitle;

    @ViewById
    protected LottieAnimationView emptyAnim;

    private NewsViewModel newsViewModel;
    private TitleBarViewModel titleBarViewModel;

    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);

        titleBarViewModel =
                ViewModelProviders.of(getActivity()).get(TitleBarViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    protected void init() {
        newsAdapter = new NewsAdapter(this);

        subscribeObservers();
        initRecyclerView();
    }

    private void subscribeObservers() {
        newsViewModel.getArticles().observe(getViewLifecycleOwner(), this::handleNews);
        titleBarViewModel.getSearchKeys().observe(getViewLifecycleOwner(), this::search);
    }

    public void handleNews(List<Article> articles) {
        if (articles != null) {
            newsAdapter.submitList(articles);
            handleEmptySearchScreen(articles.isEmpty());
        }
    }

    private void search(String key) {
        newsViewModel.searchNews(getViewLifecycleOwner(), key);
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpTitleBar();

        if (!titleBarViewModel.isSearchViewVisible()) {
            newsViewModel.syncNews(getViewLifecycleOwner());
        }
    }

    private void setUpTitleBar() {
        titleBarViewModel.setTitleBarPageState(FEED);
    }

    private void initRecyclerView() {
        LayoutManager layoutManager = new LinearLayoutManager(getContext());
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsAdapter);
    }

    private void handleEmptySearchScreen(boolean isEmpty) {
        if (isAdded()) {
            if (isEmpty) {
                if (emptyTitle.getVisibility() != View.VISIBLE) {
                    emptyTitle.setVisibility(View.VISIBLE);
                    emptyAnim.playAnimation();
                    emptyAnim.setVisibility(View.VISIBLE);
                }
            } else {
                if (emptyTitle.getVisibility() != View.GONE) {
                    emptyTitle.setVisibility(View.GONE);
                    emptyAnim.pauseAnimation();
                    emptyAnim.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onItemSelected(Article article) {
        if (isAdded() && article != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("article", article);

            NavHostFragment.findNavController(NewsFragment.this)
                    .navigate(R.id.action_navigation_news_to_newsDetailsFragment_, bundle);
        }
    }

    @Override
    public void onItemBookmarked(Article article) {
        if (isAdded() && article != null) {
            newsViewModel.updateNewsBookmarkedState(article.getTitle(), !article.isBookmarked());
        }
    }
}