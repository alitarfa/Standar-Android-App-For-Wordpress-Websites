package com.add.wordpressdroid.ui.category;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        // TODO: Use the ViewModel

        setupUI();
    }

    public void setupUI()  {
        recyclerView = getView().findViewById(R.id.recycle);
        categoryAdapter = new CategoryAdapter(getContext());
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        categoryAdapter.setPosts(Arrays.asList("","","",""));
    }

}
