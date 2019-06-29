package com.add.wordpressdroid.ui.selectedCategory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.add.wordpressdroid.api.repository.Repository;
import com.add.wordpressdroid.model.posts.post.Post;

import java.util.List;

public class SelectedCategoryViewModel extends ViewModel {

    private Repository repository;

    public SelectedCategoryViewModel() {
        this.repository = new Repository();
    }

    public LiveData<List<Post>> getPostsByCategory(int id) {
        return this.repository.getPostsByCategory(id);
    }
}
