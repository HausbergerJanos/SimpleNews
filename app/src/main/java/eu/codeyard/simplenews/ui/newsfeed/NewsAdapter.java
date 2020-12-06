package eu.codeyard.simplenews.ui.newsfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eu.codeyard.simplenews.R;
import eu.codeyard.simplenews.business.domain.model.Article;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Interaction interaction;

    public NewsAdapter(Interaction interaction) {
        this.interaction = interaction;
    }

    private DiffUtil.ItemCallback<Article> diffCallback = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }
    };

    private AsyncListDiffer<Article> differ = new AsyncListDiffer<>(this, diffCallback);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsFeedItemView(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.view_news_feed_item,
                        parent,
                        false
                ),
                interaction
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsFeedItemView) {
            ((NewsFeedItemView) holder).bind(differ.getCurrentList().get(position));
        }
    }

    public void submitList(List<Article> articles) {
        if (differ != null) {
            differ.submitList(articles);
        }
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public interface Interaction {
        void onItemSelected(Article article);
        void onItemBookmarked(Article article);
    }
}
