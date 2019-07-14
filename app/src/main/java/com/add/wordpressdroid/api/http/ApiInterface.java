package com.add.wordpressdroid.api.http;


import com.add.wordpressdroid.api.params.HttpParams;
import com.add.wordpressdroid.model.category.Category;
import com.add.wordpressdroid.model.posts.post.CommentsAndReplies;
import com.add.wordpressdroid.model.posts.post.Post;
import com.add.wordpressdroid.model.posts.post.PostDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiInterface {

    @GET(HttpParams.API_CATEGORIES)
    Call<List<Category>> getCategories();

    @GET(HttpParams.API_FEATURED_POSTS)
    Call<List<Post>> getFeaturedPosts(@Query(HttpParams.API_TEXT_PAGE) int pageCount);

    @GET(HttpParams.API_RECENT_POSTS)
    Call<List<Post>> getRecentPosts(@Query(HttpParams.API_TEXT_PAGE) int pageCount);

   @GET(HttpParams.API_CATEGORISED_ALL_POST)
    Call<List<Post>> getPostsByCategory(@Query(HttpParams.API_TEXT_CATEGORIES) int categoryId);


    @GET(HttpParams.API_POST_DETAILS)
    Call<PostDetails> getPostDetails(@Path(HttpParams.API_TEXT_ID) int postId);

    @GET
    Call<List<CommentsAndReplies>> getCommentsAndReplies(@Url String url, @Query(HttpParams.API_TEXT_PER_PAGE) int pageCount);

    @FormUrlEncoded
    @POST(HttpParams.API_POST_A_COMMENT)
    Call<String> postAComment(@Field(HttpParams.COMMENT_AUTHOR_NAME) String name,
                              @Field(HttpParams.COMMENT_AUTHOR_EMAIL) String email,
                              @Field(HttpParams.COMMENT_CONTENT) String content,
                              @Query(HttpParams.API_POST) int postID);

    @FormUrlEncoded
    @POST(HttpParams.API_POST_A_COMMENT)
    Call<String> postAReply(@Field(HttpParams.COMMENT_AUTHOR_NAME) String name,
                            @Field(HttpParams.COMMENT_AUTHOR_EMAIL) String email,
                            @Field(HttpParams.COMMENT_CONTENT) String content,
                            @Query(HttpParams.API_POST) int postID,
                            @Query(HttpParams.API_PARENT) int commentParentID);


    @GET(HttpParams.API_SEARCHED_POSTS)
    Call<List<Post>> getSearchedPosts(@Query(HttpParams.API_TEXT_PAGE) int pageCount, @Query(HttpParams.API_TEXT_SEARCH) String searchText);

}
