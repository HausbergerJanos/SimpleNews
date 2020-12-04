package eu.codeyard.simplenews.ui.newsfeed;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.domain.util.DateUtil;

import static eu.codeyard.simplenews.ui.newsfeed.NewsAdapter.*;
import static kotlin.io.ConstantsKt.DEFAULT_BUFFER_SIZE;

class NewsFeedItemView extends RecyclerView.ViewHolder {

    private Article article;

    private CardView cardView;

    private TextView title;
    private TextView source;
    private TextView publishedAt;

    private RoundedImageView coverImage;

    private Interaction interaction;

    public NewsFeedItemView(@NonNull View itemView, Interaction interaction) {
        super(itemView);

        this.interaction = interaction;

        title = itemView.findViewById(R.id.title);
        source = itemView.findViewById(R.id.source);
        publishedAt = itemView.findViewById(R.id.publishedAt);

        coverImage = itemView.findViewById(R.id.coverImage);

        cardView = itemView.findViewById(R.id.cardView);

        cardView.setOnClickListener(view -> {
            if (interaction != null) {
                interaction.onItemSelected(article);
            }
        });
    }

    public void bind(Article article) {
        this.article = article;
        title.setText(article.getTitle());
        source.setText(article.getSource());
        publishedAt.setText(DateUtil.convertMilliSecToTimeOnCard(article.getPublishedAt()));

        Glide.with(itemView)
                .load(article.getUrlToImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(coverImage);
    }
}
