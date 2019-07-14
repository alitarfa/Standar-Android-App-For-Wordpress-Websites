package com.add.wordpressdroid.ui.postDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.add.wordpressdroid.MainActivity;
import com.add.wordpressdroid.R;
import com.add.wordpressdroid.adapter.CommentsAdapter;
import com.add.wordpressdroid.api.http.ApiUtils;
import com.add.wordpressdroid.api.params.HttpParams;
import com.add.wordpressdroid.model.FavouriteModel;
import com.add.wordpressdroid.model.posts.post.CommentsAndReplies;
import com.add.wordpressdroid.model.posts.post.PostDetails;
import com.add.wordpressdroid.ui.CommentDetailsActivity.CommentDetailsActivity;
import com.add.wordpressdroid.ui.comentList.CommentListActivity;
import com.add.wordpressdroid.utils.ActivityUtils;
import com.add.wordpressdroid.utils.AppConstant;
import com.add.wordpressdroid.utils.AppUtils;
import com.add.wordpressdroid.webengine.WebEngine;
import com.add.wordpressdroid.webengine.WebListener;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostDetailsActivity extends AppCompatActivity {

    // Variables
    private Activity mActivity;
    private Context mContext;

    // init views
    private RelativeLayout lytPostDetailsView, lytCommentDetails;
    private int clickedPostId;
    private ImageView imgPost;
    private TextView tvPostTitle, tvPostAuthor, tvPostDate, tvCommnentList;
    private WebView webView;
    private FloatingActionButton fabWriteAComment;
    private ImageButton imgBtnSpeaker, imgBtnFav, imgBtnShare;
    private PostDetails model = null;

    // Favourites view
    private List<FavouriteModel> favouriteList;

    private boolean isFavourite = false;

    // Comments view
    private List<CommentsAndReplies> commentList;
    private List<CommentsAndReplies> zeroParentComments;
    List<CommentsAndReplies> onlyThreeComments;
    private int mPerPage = 5;
    private RecyclerView rvComments;
    private CommentsAdapter commentsAdapter = null;

    private WebEngine webEngine;

    private boolean fromPush = false, fromAppLink = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initFunctionality();
        initListener();
    }

    private void initVar() {
        mActivity = PostDetailsActivity.this;
        mContext = mActivity.getApplicationContext();

        // Favourites view
        favouriteList = new ArrayList<>();
        // Comments view
        commentList = new ArrayList<>();
        zeroParentComments = new ArrayList<>();
        onlyThreeComments = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            clickedPostId = extras.getInt(AppConstant.BUNDLE_KEY_POST_ID, 0);
            Log.e("idPost", String.valueOf(clickedPostId));
            if(extras.containsKey(AppConstant.BUNDLE_FROM_PUSH)) {
                fromPush = extras.getBoolean(AppConstant.BUNDLE_FROM_PUSH);
            }
            if(extras.containsKey(AppConstant.BUNDLE_FROM_APP_LINK)) {
                fromAppLink = extras.getBoolean(AppConstant.BUNDLE_FROM_APP_LINK);
            }
        }

    }

    private void initView() {
        setContentView(R.layout.activity_post_details);

        lytPostDetailsView = (RelativeLayout) findViewById(R.id.lyt_post_details);
        lytCommentDetails = (RelativeLayout) findViewById(R.id.lyt_comment_list);
        //lytParentView.setVisibility(View.GONE);

        imgPost = (ImageView) findViewById(R.id.post_img);
        tvPostTitle = (TextView) findViewById(R.id.title_text);
        tvPostAuthor = (TextView) findViewById(R.id.post_author);
        tvPostDate = (TextView) findViewById(R.id.date_text);


        imgBtnShare = (ImageButton) findViewById(R.id.imgBtnShare);

        initWebEngine();

        tvCommnentList = (TextView) findViewById(R.id.comment_count);
        fabWriteAComment = (FloatingActionButton) findViewById(R.id.fab_new_comment);

        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(getApplication()));

        initToolbar();
        enableBackButton();

    }

    public void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void initWebEngine() {

        webView = (WebView) findViewById(R.id.web_view);

        webEngine = new WebEngine(webView, mActivity);
        webEngine.initWebView();


        webEngine.initListeners(new WebListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onLoaded() {

            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onNetworkError() {

            }

            @Override
            public void onPageTitle(String title) {
            }
        });
    }

    private void initFunctionality() {
        commentsAdapter = new CommentsAdapter(mActivity, (ArrayList) commentList, (ArrayList) onlyThreeComments);
        rvComments.setAdapter(commentsAdapter);
        loadPostDetails();
    }

    public void initListener() {

        imgBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, model.getPageUrl());
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                }
            }
        });

        commentsAdapter.setItemClickListener(new CommentsAdapter.ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                int id = view.getId();
                CommentsAndReplies clickedComment = zeroParentComments.get(position);
                switch (id) {
                    case R.id.list_item:
                       ActivityUtils.getInstance().invokeCommentDetails(mActivity, CommentDetailsActivity.class, (ArrayList) commentList, clickedPostId, clickedComment, false, false);
                        break;
                    case R.id.reply_text:
                        ActivityUtils.getInstance().invokeCommentDetails(mActivity, CommentDetailsActivity.class, (ArrayList) commentList, clickedPostId, clickedComment, true, false);
                        break;
                    default:
                        break;
                }
            }
        });

        tvCommnentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.getInstance().invokeCommentList(mActivity,
                        CommentListActivity.class,
                        (ArrayList) commentList,
                        (ArrayList) zeroParentComments,
                        clickedPostId,
                        false);
            }
        });

        fabWriteAComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
               /* WriteACommentFragment dialog = WriteACommentFragment.newInstance(clickedPostId, AppConstant.THIS_IS_COMMENT);
                dialog.show(manager, AppConstant.BUNDLE_KEY_DIALOG_FRAGMENT);*/
            }
        });
    }

    public void loadPostDetails() {
        ApiUtils.getApiInterface().getPostDetails(clickedPostId).enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                if (response.isSuccessful()) {
                    // bind data
                    model = response.body();
                    PostDetails m = model;

                    // visible parent view
                    lytPostDetailsView.setVisibility(View.VISIBLE);
                    loadCommentsAndReplies(model.getLinks().getRepliesList().get(0).getHref());
                   // setFavImage();
                    tvPostTitle.setText(Html.fromHtml(model.getTitle().getRendered()));


                    String imgUrl = null;
                    if (model.getEmbedded().getWpFeaturedMedias().size() > 0) {
                        if (model.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails() != null) {
                            if (model.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails().getSizes().getFullSize().getSourceUrl() != null) {
                                imgUrl = model.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails().getSizes().getFullSize().getSourceUrl();
                            }
                        }
                    }

                    if (imgUrl != null) {
                        Glide.with(getApplicationContext())
                                .load(imgUrl)
                                .into(imgPost);
                    }

                    String author = null;
                    if (model.getEmbedded().getAuthors().size() >= 1) {
                        author = model.getEmbedded().getAuthors().get(0).getName();
                    }

                    if (author == null) {
                        author = getString(R.string.admin);
                    }
                    tvPostAuthor.setText(Html.fromHtml(author));

                    String oldDate = model.getOldDate();
                    String newDate = AppUtils.getFormattedDate(oldDate);

                    if (newDate != null) {
                        tvPostDate.setText(Html.fromHtml(newDate));
                    }

                    String contentText = model.getContent().getRendered();

                    contentText = new StringBuilder().append(AppConstant.CSS_PROPERTIES).append(contentText).toString();
                    webEngine.loadHtml(contentText);

                } else {

                }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                t.printStackTrace();


            }
        });
    }

    public void loadCommentsAndReplies(final String commentsAndRepliesLink) {

        ApiUtils.getApiInterface().getCommentsAndReplies(commentsAndRepliesLink, mPerPage).enqueue(new Callback<List<CommentsAndReplies>>() {
            @Override
            public void onResponse(Call<List<CommentsAndReplies>> call, Response<List<CommentsAndReplies>> response) {
                if (response.isSuccessful()) {

                    int totalItems = Integer.parseInt(response.headers().get(HttpParams.HEADER_TOTAL_ITEM));
                    int totalPages = Integer.parseInt(response.headers().get(HttpParams.HEADER_TOTAL_PAGE));


                    if (totalPages > 1) {
                        mPerPage = mPerPage * totalPages;
                        loadCommentsAndReplies(commentsAndRepliesLink);

                    } else {
                        if (!commentList.isEmpty() || !zeroParentComments.isEmpty() || !onlyThreeComments.isEmpty()) {
                            commentList.clear();
                            zeroParentComments.clear();
                            onlyThreeComments.clear();
                        }

                        commentList.addAll(response.body());

                        lytCommentDetails.setVisibility(View.VISIBLE);

                        if (commentList.size() > 0) {
                            for (CommentsAndReplies commentsAndReplies : commentList) {
                                if (commentsAndReplies.getParent().intValue() == 0) {
                                    zeroParentComments.add(commentsAndReplies);
                                }
                            }

                            if (zeroParentComments.size() >= 3) {
                                for (int i = 0; i < 3; i++) {
                                    onlyThreeComments.add(zeroParentComments.get(i));
                                }
                            } else {
                                for (CommentsAndReplies commentsAndReplies : zeroParentComments) {
                                    onlyThreeComments.add(commentsAndReplies);
                                }
                            }
                            commentsAdapter.notifyDataSetChanged();
                            tvCommnentList.setText(String.format(getString(R.string.all_comment), commentList.size()));
                            tvCommnentList.setClickable(true);

                        } else {
                            tvCommnentList.setClickable(false);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CommentsAndReplies>> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                goToHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        goToHome();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        model = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /*@Override
    public void onComplete(Boolean isCommentSuccessful, CommentsAndReplies commentsAndReplies) {
        if (isCommentSuccessful) {
           // loadCommentsAndReplies(model.getLinks().getRepliesList().get(0).getHref());
        }
    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == AppConstant.REQUEST_CODE_COMMENT) {
            if (data == null) {
                return;
            }
            boolean isCommentSuccessful = CommentListActivity.wasCommentSuccessful(data);
            if (isCommentSuccessful) {
                loadCommentsAndReplies(model.getLinks().getRepliesList().get(0).getHref());
            }
        }
    }


    private void goToHome(){
        if(fromPush || fromAppLink) {
            Intent intent = new Intent(PostDetailsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }
}
