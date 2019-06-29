package com.add.wordpressdroid.api.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.add.wordpressdroid.api.http.ApiUtils;
import com.add.wordpressdroid.model.category.Category;
import com.add.wordpressdroid.model.posts.post.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {


    /**
     * Get The List of Categories
     * @return
     */
    public MutableLiveData<List<Category>> getCategories() {

        MutableLiveData<List<Category>> data = new MutableLiveData<>();

        ApiUtils.getApiInterface().getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    Log.e("[LIST CATEGORIES]" ,"Received");
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("[LIST CATEGORIES]" ,"ERROR");
            }
        });

        return data;
    }


    /**
     * Get the Posts related to Category by using ID
     * @param categoryId
     * @return
     */
    public MutableLiveData<List<Post>> getPostsByCategory(int categoryId) {
        MutableLiveData<List<Post>> data = new MutableLiveData<>();

        ApiUtils.getApiInterface().getPostsByCategory(categoryId).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Log.e("[POSTS PER CATEGORIES]" ,"Received");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("[POST PER CATEGORIES]" ,"ERROR");
                Log.e("[POST PER CATEGORIES E]" ,t.getMessage());
                Log.e("[POST PER CATEGORIES E]" , String.valueOf(t.getCause()));
            }
        });

        return data;
    }



    public void getRecentPosts() {

    }

    public void getTopPosts() {

    }
}
