package eu.codeyard.simplenews.ui.newsdetails;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import eu.codeyard.simplenews.NewsReaderActivity_;
import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.domain.util.DateUtil;
import eu.codeyard.simplenews.ui.titlebar.TitleBarViewModel;
import eu.codeyard.simplenews.ui.util.AnimationUtils;

import static eu.codeyard.simplenews.ui.titlebar.TitleBarPageState.DETAILS;

@EFragment(R.layout.fragment_news_details)
public class NewsDetailsFragment extends Fragment {

    @ViewById
    protected ImageView coverImage;

    @ViewById
    protected TextView descriptionTitle;

    @ViewById
    protected TextView detailsSource;

    @ViewById
    protected TextView descriptionPublishedAt;

    @ViewById
    protected TextView descriptionDetail;

    @ViewById
    protected TextView closedTitle;

    @ViewById
    protected ImageView icBookmark;

    private Article article;

    private NewsDetailsViewModel newsDetailsViewModel;
    private TitleBarViewModel titleBarViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            article = (Article) getArguments().getSerializable("article");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsDetailsViewModel =
                ViewModelProviders.of(this).get(NewsDetailsViewModel.class);

        titleBarViewModel =
                ViewModelProviders.of(getActivity()).get(TitleBarViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    protected void init() {
        if (article != null) {
            descriptionTitle.setText(article.getTitle());
            detailsSource.setText(article.getSource());
            descriptionPublishedAt.setText(DateUtil.convertMilliSecToTimeOnCard(article.getPublishedAt()));
            descriptionDetail.setText(article.getDescription());
            closedTitle.setText(article.getTitle());

            Drawable bookmarkIcon = ContextCompat.getDrawable(getContext(),
                    article.isBookmarked() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);
            icBookmark.setImageDrawable(bookmarkIcon);

            Glide.with(getContext())
                    .load(article.getUrlToImage())
                    .into(coverImage);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpTitleBar();
    }

    private void setUpTitleBar() {
        titleBarViewModel.setTitleBarPageState(DETAILS);
    }

    @Click(R.id.readNewsButton)
    public void onReadNewsClicked() {
        if (isAdded() && getContext() != null && getActivity() != null) {
            NewsReaderActivity_
                    .intent(getContext())
                    .newsUrl(article.getUrl())
                    .start();
        }
    }

    @Click(R.id.icBookmark)
    public void onBookmarkClicked() {
        if (isAdded() && getContext() != null) {
            article.setBookmarked(!article.isBookmarked());

            newsDetailsViewModel.updateNewsBookmarkedState(article.getTitle(), article.isBookmarked());

            Drawable bookmarkIcon = ContextCompat.getDrawable(getContext(),
                    article.isBookmarked() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);
            AnimationUtils.swapImage(icBookmark, bookmarkIcon);
        }
    }

    @Click(R.id.icBack)
    public void onBackClicked() {
        if (isAdded() && getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}
