package eu.codeyard.simplenews.ui.newsfeed;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;
import eu.codeyard.simplenews.business.domain.util.DateUtil;

class NewsFeedItemView extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView source;
    private TextView publishedAt;

    private RoundedImageView coverImage;

    public NewsFeedItemView(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        source = itemView.findViewById(R.id.source);
        publishedAt = itemView.findViewById(R.id.publishedAt);

        coverImage = itemView.findViewById(R.id.coverImage);
    }

    public void bind(Article article) {
        title.setText(article.getTitle());
        source.setText(article.getSource());
        publishedAt.setText(DateUtil.convertMilliSecToTimeOnCard(article.getPublishedAt()));

        Glide.with(itemView)
                .load(article.getUrlToImage())
                .into(coverImage);
    }
}
