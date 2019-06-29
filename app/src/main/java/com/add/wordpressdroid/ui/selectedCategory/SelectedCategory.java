package com.add.wordpressdroid.ui.selectedCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.adapter.RecentPostAdapter;

import java.util.Arrays;
import java.util.Objects;

public class SelectedCategory extends AppCompatActivity {

    private RecyclerView recyclerViewPosts;
    private RecentPostAdapter recentPostAdapter;
    private SelectedCategoryViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        // setup the viewModel
        viewModel = ViewModelProviders.of(this).get(SelectedCategoryViewModel.class);

        // setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get the extra information from intent
        Intent intent = getIntent();
        String categoryName = Objects.requireNonNull(intent.getExtras()).getString("categoryName");
        int categoryId = (int) intent.getExtras().getDouble("categoryId");

        // setup the toolbar title
        getSupportActionBar().setTitle(categoryName);

        // setup the GUI
        setupUI();

        // setup the Data
        setupData(categoryId);
    }

    public void setupUI(){
        recyclerViewPosts = findViewById(R.id.recycle_recent_post);
        recentPostAdapter = new RecentPostAdapter(this);
        recyclerViewPosts.setAdapter(recentPostAdapter);
        recyclerViewPosts.setLayoutManager(new GridLayoutManager(getBaseContext(),2));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /**
     * get the Data from Repository
     * @param id
     */
    public void setupData(int id) {
        viewModel.getPostsByCategory(id).observe(this, result ->  {
            recentPostAdapter.setPosts(result);
            recentPostAdapter.notifyDataSetChanged();
        });
    }
}
