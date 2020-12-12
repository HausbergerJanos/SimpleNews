package eu.codeyard.simplenews.framework.presentation.bookmarked;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import eu.codeyard.simplenews.framework.presentation.common.NewsAdapter;
import eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarViewModel;

import static eu.codeyard.simplenews.framework.presentation.common.NewsAdapter.*;
import static eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarPageState.BOOKMARK;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_bookmarked_news_list)
public class BookmarkedNewsListFragment extends Fragment implements Interaction {

    @ViewById
    protected RecyclerView bookmarkedNewsRecyclerView;

    @ViewById
    protected TextView emptyTitle;

    @ViewById
    protected LottieAnimationView emptyAnim;

    private BookmarkedNewsListViewModel bookmarkedNewsListViewModel;
    private TitleBarViewModel titleBarViewModel;

    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bookmarkedNewsListViewModel =
                new ViewModelProvider(this).get(BookmarkedNewsListViewModel.class);
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

    private void search(String query) {
        bookmarkedNewsListViewModel.searchInBookmarkedNews(getViewLifecycleOwner(), query);
    }

    private void subscribeObservers() {
        bookmarkedNewsListViewModel.getArticles().observe(getViewLifecycleOwner(), this::handleNews);
        titleBarViewModel.getSearchQuery().observe(getViewLifecycleOwner(), this::search);
    }

    public void handleNews(List<Article> articles) {
        if (articles != null) {
            newsAdapter.submitList(articles);
            handleEmptyScreen(articles.size() == 0);
        } else {
            handleEmptyScreen(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpTitleBar();

        if (!titleBarViewModel.isSearchViewVisible()) {
            bookmarkedNewsListViewModel.getAllBookmarkedNews(getViewLifecycleOwner());
        }
    }

    private void setUpTitleBar() {
        titleBarViewModel.setTitleBarPageState(BOOKMARK);
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        bookmarkedNewsRecyclerView.setLayoutManager(layoutManager);
        bookmarkedNewsRecyclerView.setAdapter(newsAdapter);
    }

    private void handleEmptyScreen(boolean isEmpty) {
        if (isAdded()) {
            if (titleBarViewModel.isSearchViewVisible()) {
                handleEmptySearchScreen(isEmpty);
            } else {
                handleEmptyBookmarkScreen(isEmpty);
            }
        }
    }

    private void handleEmptySearchScreen(boolean isEmpty) {
        if (isEmpty) {
            if (emptyTitle.getVisibility() != View.VISIBLE) {
                emptyTitle.setText(getResources().getString(R.string.empty_search_result));
                emptyTitle.setVisibility(View.VISIBLE);

                emptyAnim.setAnimation("empty_anim.json");
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

    private void handleEmptyBookmarkScreen(boolean isEmpty) {
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

    @Override
    public void onItemSelected(Article article) {
        if (isAdded() && article != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("article", article);

            NavHostFragment.findNavController(BookmarkedNewsListFragment.this)
                    .navigate(R.id.action_navigation_bookmarked_to_newsDetailsFragment_, bundle);
        }
    }

    @Override
    public void onItemBookmarked(Article article) {
        if (isAdded() && article != null) {
            bookmarkedNewsListViewModel.updateNewsBookmarkedState(article.getTitle(), !article.isBookmarked());
        }
    }
}