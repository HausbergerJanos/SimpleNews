package eu.codeyard.simplenews.ui.newsfeed;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.domain.util.DateUtil;

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

    private Article article;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Transition transition = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move);
        setSharedElementEnterTransition(transition);

        if (getArguments() != null) {
            article = (Article) getArguments().getSerializable("article");
        }
    }

    @AfterViews
    protected void init() {

        if (article != null) {
            descriptionTitle.setText(article.getTitle());
            detailsSource.setText(article.getSource());
            descriptionPublishedAt.setText(DateUtil.convertMilliSecToTimeOnCard(article.getPublishedAt()));
            descriptionDetail.setText(article.getDescription());

            Glide.with(getContext())
                    .load(article.getUrlToImage())
                    .into(coverImage);
        }
    }
}
