package com.add.wordpressdroid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.add.wordpressdroid.api.repository.Repository;
import com.add.wordpressdroid.model.category.Category;

import java.util.List;

public class HomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private Repository repository;

    public HomeViewModel() {
        repository = new Repository();
    }

    /**
     * The List of Categories
     * @return
     */
    public LiveData<List<Category>> getCategories() {
        return this.repository.getCategories();
    }
}
