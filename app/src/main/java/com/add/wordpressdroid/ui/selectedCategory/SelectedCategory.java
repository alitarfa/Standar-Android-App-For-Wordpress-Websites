package com.add.wordpressdroid.ui.selectedCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.adapter.RecentPostAdapter;

import java.util.Arrays;

public class SelectedCategory extends AppCompatActivity {

    private RecyclerView recyclerViewPosts;
    private RecentPostAdapter recentPostAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupUI();
    }

    public void setupUI(){
        recyclerViewPosts = findViewById(R.id.recycle_recent_post);
        recentPostAdapter = new RecentPostAdapter(this);
        recentPostAdapter.setPosts(Arrays.asList("","","","","","","","",""));
        recyclerViewPosts.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
        recyclerViewPosts.setAdapter(recentPostAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
