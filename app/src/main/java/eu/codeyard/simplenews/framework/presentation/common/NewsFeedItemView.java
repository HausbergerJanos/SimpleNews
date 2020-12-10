package eu.codeyard.simplenews.framework.presentation.common;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.domain.util.DateUtil;
import eu.codeyard.simplenews.framework.presentation.util.AnimationUtils;

import static eu.codeyard.simplenews.framework.presentation.common.NewsAdapter.*;

public class NewsFeedItemView extends RecyclerView.ViewHolder {

    private Article article;

    private CardView cardView;

    private TextView title;
    private TextView source;
    private TextView publishedAt;

    private ImageView icBookmark;

    private RoundedImageView coverImage;

    private Interaction interaction;

    public NewsFeedItemView(@NonNull View itemView, Interaction interaction) {
        super(itemView);

        this.interaction = interaction;

        title = itemView.findViewById(R.id.title);
        source = itemView.findViewById(R.id.source);
        publishedAt = itemView.findViewById(R.id.publishedAt);

        coverImage = itemView.findViewById(R.id.coverImage);
        icBookmark = itemView.findViewById(R.id.icBookmark);

        cardView = itemView.findViewById(R.id.cardView);

        cardView.setOnClickListener(view -> {
            if (interaction != null) {
                interaction.onItemSelected(article);
            }
        });

        icBookmark.setOnClickListener(view -> {
            if (interaction != null) {
                interaction.onItemBookmarked(article);
                article.setBookmarked(!article.isBookmarked());
                Drawable bookmarkIcon = ContextCompat.getDrawable(itemView.getContext(),
                        article.isBookmarked() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark);
                AnimationUtils.swapImage(icBookmark, bookmarkIcon);
            }
        });
    }

    public void bind(Article article) {
        this.article = article;
        title.setText(article.getTitle());
        source.setText(article.getSource());
        publishedAt.setText(DateUtil.convertMilliSecToTimeOnCard(article.getPublishedAt()));

        icBookmark.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                article.isBookmarked() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark));

        Glide.with(itemView)
                .load(article.getUrlToImage())
                .into(coverImage);
    }
}
