package com.add.wordpressdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.model.category.Category;
import com.add.wordpressdroid.ui.home.Home;
import com.add.wordpressdroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemsAdapter extends RecyclerView.Adapter<CategoryItemsAdapter.CategoryItemsViewpHolder> {


    // interface navigation action
    public interface OnSelectCategory {
        public void onSelectCategory(Category category);
    }

    private List<Category> categoriesList;
    private Context context;
    private LayoutInflater inflater;
    private OnSelectCategory selectCategory;


    public  CategoryItemsAdapter(Context context, Object home){
        this.context= context;
        this.inflater = LayoutInflater.from(context);
        this.categoriesList = new ArrayList<>();
        this.selectCategory = (OnSelectCategory) home;
    }

    // setup the list of category
    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList= categoriesList;
    }

    @NonNull
    @Override
    public CategoryItemsViewpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category_layout,parent, false);
        return new CategoryItemsViewpHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemsViewpHolder holder, int position) {
        holder.nameCategory.setText(categoriesList.get(position).getName());
        holder.nameCategory.setBackground(Utils.getRandomColor(context));

        // handle on Select One Category
        holder.itemCategory.setOnClickListener(v -> {
            selectCategory.onSelectCategory(categoriesList.get(position));
        });
    }



    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategoryItemsViewpHolder extends RecyclerView.ViewHolder {
        private TextView nameCategory;
        private View itemCategory;

        public CategoryItemsViewpHolder(@NonNull View itemView) {
            super(itemView);
            nameCategory = itemView.findViewById(R.id.name_category);
            itemCategory = itemView.findViewById(R.id.item_category);

        }
    }

}
