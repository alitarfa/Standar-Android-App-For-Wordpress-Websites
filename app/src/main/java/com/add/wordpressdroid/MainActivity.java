package com.add.wordpressdroid;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.add.wordpressdroid.adapter.CategoryItemsAdapter;
import com.add.wordpressdroid.model.category.Category;
import com.add.wordpressdroid.ui.selectedCategory.SelectedCategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements CategoryItemsAdapter.OnSelectCategory {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        // setup the Bottom Menu with Navigation
        setupBottomNavigatiom();

    }

    /**
     * Setup The Bottom Menu
     */
    public void setupBottomNavigatiom(){
        navController = Navigation.findNavController(this,R.id.main_nav_id);
        bottomNavigationView = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


    @Override
    public void onSelectCategory(Category category) {
        Intent intent = new Intent(this, SelectedCategory.class);
        intent.putExtra("categoryId",category.getID());
        intent.putExtra("categoryName",category.getName());
        startActivity(intent);
    }
}
