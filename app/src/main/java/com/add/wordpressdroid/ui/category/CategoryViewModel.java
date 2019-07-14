package com.add.wordpressdroid.ui.category;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.add.wordpressdroid.api.repository.Repository;
import com.add.wordpressdroid.model.category.Category;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private Repository repository;

    public CategoryViewModel() {
        this.repository = new Repository();
    }
    
    /**
     * Return the List of Categories
     * @return
     */
    public MutableLiveData<List<Category>> getCategories() {
        return this.repository.getCategories();
    }
}
