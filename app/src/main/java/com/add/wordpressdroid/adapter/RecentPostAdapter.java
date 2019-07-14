package com.add.wordpressdroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.model.posts.post.Post;
import com.add.wordpressdroid.ui.postDetails.PostDetailsActivity;
import com.add.wordpressdroid.utils.AppConstant;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecentPostAdapter  extends RecyclerView.Adapter<RecentPostAdapter.RecentPostViewHolder> {



    private List<Post> posts;
    private Context context;
    private LayoutInflater inflater;

    public  RecentPostAdapter(Context context){
        this.context= context;
        this.inflater = LayoutInflater.from(context);
        this.posts = new ArrayList<>();

    }

    // setup the list of category
    public void setPosts(List<Post> posts) {
        this.posts= posts;
    }


    @NonNull
    @Override
    public RecentPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_post_layout, parent, false);
        return new RecentPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentPostViewHolder holder, int position) {
        final Post model = posts.get(position);

        String imgUrl = null;
        if (model.getEmbedded().getWpFeaturedMedias().size() > 0) {
            if (model.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails() != null) {
                if (model.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails().getSizes().getFullSize().getSourceUrl() != null) {
                    imgUrl = model.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails().getSizes().getFullSize().getSourceUrl();
                }
            }
        }

        if (imgUrl != null) {
            Glide.with(context)
                    .load(imgUrl)
                    .into(holder.imgPost);
        }

        // setting data over views
        String title = model.getTitle().getRendered();
        holder.tvPostTitle.setText(Html.fromHtml(title));
        holder.tvPostDate.setText(model.getFormattedDate());

        String category = null;

        if (model.getEmbedded().getWpTerms().size() >= 1) {
            category = model.getEmbedded().getWpTerms().get(0).get(0).getName();
        }

        if (category == null) {
            category = context.getResources().getString(R.string.default_str);
        }

        holder.tvPostCategory.setText(Html.fromHtml(category));


        // handle post actions
        // TODO: 6/29/19 this will be the test make sure to add the other Adapter

        holder.mCardView.setOnClickListener(v -> {
            // get the Post id
            int postId = posts.get(position).getID();
            Intent intent = new Intent(context, PostDetailsActivity.class);
            intent.putExtra(AppConstant.BUNDLE_KEY_POST_ID, postId);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class RecentPostViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPost;
        private TextView tvPostTitle, tvPostCategory, tvPostDate;
        private CardView mCardView;

        public RecentPostViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPost = (ImageView) itemView.findViewById(R.id.post_img);
            tvPostTitle = (TextView) itemView.findViewById(R.id.title_text);
            tvPostCategory = (TextView) itemView.findViewById(R.id.post_category);
            tvPostDate = (TextView) itemView.findViewById(R.id.date_text);
            mCardView = (CardView) itemView.findViewById(R.id.card_view_top);
        }
    }

}
