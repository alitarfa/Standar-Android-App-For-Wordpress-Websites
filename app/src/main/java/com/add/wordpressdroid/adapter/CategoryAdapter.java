package com.add.wordpressdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.add.wordpressdroid.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecentPostViewHolder> {

    private List<String> posts;
    private Context context;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context){
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
        View view = inflater.inflate(R.layout.item_category_layout_grid, parent, false);
        return new RecentPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentPostViewHolder holder, int position) {
        holder.categoryBackground.setImageDrawable(context.getDrawable(R.drawable.coding));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class RecentPostViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryBackground;
        public RecentPostViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryBackground = itemView.findViewById(R.id.categoryBackground);
        }
    }

}
