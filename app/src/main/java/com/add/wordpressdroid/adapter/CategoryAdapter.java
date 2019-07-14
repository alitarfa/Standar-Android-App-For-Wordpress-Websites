package com.add.wordpressdroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.model.category.Category;
import com.add.wordpressdroid.ui.selectedCategory.SelectedCategory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecentPostViewHolder> {

    private List<Category> categories;
    private Context context;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context){
        this.context= context;
        this.inflater = LayoutInflater.from(context);
        this.categories = new ArrayList<>();

    }

    // setup the list of category
    public void setPosts(List<Category> posts) {
        this.categories= posts;
    }

    @NonNull
    @Override
    public RecentPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category_layout_grid, parent, false);
        return new RecentPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentPostViewHolder holder, int position) {
        holder.categoryTitle.setText(categories.get(position).getName());
        holder.categoryCount.setText(String.valueOf(categories.get(position).getCount()));

        // handle action
        holder.categoryItem.setOnClickListener(v -> navigateToListPosts(categories.get(position).getID(),categories.get(position).getName()));

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class RecentPostViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryBackground;
        private TextView categoryTitle;
        private TextView categoryCount;
        private CardView categoryItem;

        public RecentPostViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryBackground = itemView.findViewById(R.id.categoryBackground);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryCount = itemView.findViewById(R.id.categoryCount);
            categoryItem = itemView.findViewById(R.id.categoryItem);

        }
    }

    public void navigateToListPosts(double idCategory, String name) {
        Intent intent = new Intent(context, SelectedCategory.class);
        intent.putExtra("categoryId", idCategory);
        intent.putExtra("categoryName", name);
        context.startActivity(intent);
    }

}
