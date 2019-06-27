package com.add.wordpressdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.add.wordpressdroid.R;

import java.util.ArrayList;
import java.util.List;

public class RecentPostAdapter  extends RecyclerView.Adapter<RecentPostAdapter.RecentPostViewHolder> {

    private List<String> posts;
    private Context context;
    private LayoutInflater inflater;

    public  RecentPostAdapter(Context context){
        this.context= context;
        this.inflater = LayoutInflater.from(context);
        this.posts = new ArrayList<>();

    }


    // setup the list of category
    public void setPosts(List<String> posts) {
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
        holder.imgPost.setImageDrawable(context.getResources().getDrawable(R.drawable.coding));
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
