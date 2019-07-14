package com.add.wordpressdroid.ui.category;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.adapter.CategoryAdapter;

import java.util.Arrays;

public class CategoryFragment extends Fragment {

    private CategoryViewModel mViewModel;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private SwipeRefreshLayout refreshLayout;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        setupUI();
        setupData();
    }

    public void setupUI()  {
        recyclerView = getView().findViewById(R.id.recycle);
        refreshLayout = getView().findViewById(R.id.swipe_refresh);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this::setupData);
        categoryAdapter = new CategoryAdapter(getContext());
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
     }

    public void setupData() {
        mViewModel.getCategories().observe(this, res -> {
            this.categoryAdapter.setPosts(res);
            this.categoryAdapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        });
    }

}
