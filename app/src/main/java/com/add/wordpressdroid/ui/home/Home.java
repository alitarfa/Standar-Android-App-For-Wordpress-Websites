package com.add.wordpressdroid.ui.home;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.adapter.CategoryItemsAdapter;
import com.add.wordpressdroid.adapter.HotPostAdapter;
import com.add.wordpressdroid.adapter.RecentPostAdapter;
import com.add.wordpressdroid.databinding.HomeFragmentBinding;
import com.add.wordpressdroid.ui.selectedCategory.SelectedCategory;

import java.util.Arrays;

public class Home extends Fragment implements CategoryItemsAdapter.OnSelectCategory {

    private HomeViewModel mViewModel;
    private HomeFragmentBinding binding;
    private RecyclerView recyclerViewRecentPosts;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static Home newInstance() {
        return new Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel

        // handle action of navigation
        setupUI();

        // setup the viewpager
        setupTheHotPosts();

        // setup List Categories
        setupCategoryItems();

        // setup Refreshing
        setupRefreshing();
    }


    public void setupUI() {
        recyclerViewRecentPosts = binding.getRoot().findViewById(R.id.rvPosts);
        RecentPostAdapter adapter = new RecentPostAdapter(getContext());
        adapter.setPosts(Arrays.asList("","","",""));
        recyclerViewRecentPosts.setAdapter(adapter);
        recyclerViewRecentPosts.setLayoutManager(new GridLayoutManager(getContext(),2));
    }

    public void setupTheHotPosts() {
        HotPostAdapter hotPostAdapter = new HotPostAdapter(getContext());
        binding.pagerFeaturedPost.setAdapter(hotPostAdapter);

    }

    /**
     * Setup The Categories List
     */
    public void setupCategoryItems() {
        //binding.recycleCategoryList = findViewById(R.id.recycle_category_list);
        CategoryItemsAdapter categoryItemsAdapter = new CategoryItemsAdapter(getContext(), this);
        binding.recycleCategoryList.setAdapter(categoryItemsAdapter);
        binding.recycleCategoryList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        // test data
        categoryItemsAdapter.setCategoriesList(Arrays.asList("Sport","Computer Science","Nature","Design" ,"Facebook"));
        categoryItemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelectCategory() {
       // ((MainActivity) Objects.requireNonNull(getActivity())).navigateToCategory();
        getContext().startActivity(new Intent(getContext(), SelectedCategory.class));
    }


    public void setupRefreshing() {
        swipeRefreshLayout = binding.getRoot().findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
