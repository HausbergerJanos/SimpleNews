package eu.codeyard.simplenews.ui.newsfeed;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;

class NewsFeedItemView extends RecyclerView.ViewHolder {

    private TextView title;
    private RoundedImageView coverImage;

    public NewsFeedItemView(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        coverImage = itemView.findViewById(R.id.coverImage);
    }

    public void bind(Article article) {
        title.setText(article.getTitle());

        Glide.with(itemView)
                .load(article.getUrlToImage())
                .into(coverImage);
    }
}
