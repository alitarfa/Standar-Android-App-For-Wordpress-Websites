package com.add.wordpressdroid.ui.consultCategory;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.adapter.CategoryItemsAdapter;
import com.add.wordpressdroid.adapter.RecentPostAdapter;
import com.add.wordpressdroid.model.category.Category;

import java.util.Arrays;

public class ConsultCategory extends Fragment implements CategoryItemsAdapter.OnSelectCategory {

    private ConsultCategoryViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView recycleCategoryList;

    public static ConsultCategory newInstance() {
        return new ConsultCategory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.consult_category_fragment, container, false);
        recycleCategoryList = view.findViewById(R.id.recycle_category_list);
        setupRecyele(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConsultCategoryViewModel.class);
        // TODO: Use the ViewModel

        setupCategoryItems();
     }

    public void setupRecyele(View view) {
        recyclerView = view.findViewById(R.id.recycle_recent_post);
        RecentPostAdapter adapter = new RecentPostAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        //adapter.setPosts(Arrays.asList("","","","","",""));

    }


    /**
     * Setup The Categories List
     */
    public void setupCategoryItems() {
        CategoryItemsAdapter categoryItemsAdapter = new CategoryItemsAdapter(getContext(), this);
        recycleCategoryList.setAdapter(categoryItemsAdapter);
        recycleCategoryList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
    }

    @Override
    public void onSelectCategory(Category category) {

    }
}
