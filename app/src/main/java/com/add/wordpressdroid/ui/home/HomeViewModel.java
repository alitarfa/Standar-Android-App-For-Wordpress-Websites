package com.add.wordpressdroid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.add.wordpressdroid.api.repository.Repository;

import com.add.wordpressdroid.model.category.Category;
import com.add.wordpressdroid.model.posts.post.Post;

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

    /**
     * Return the Recent Posts
     * @return
     */
    public LiveData<List<Post>> getRecentPosts() {
        return this.repository.getRecentPosts();
    }

    /**
     * Return the Top Posts
     */
    public LiveData<List<Post>> getTopPosts() {
        return repository.getTopPosts();
    }
}
