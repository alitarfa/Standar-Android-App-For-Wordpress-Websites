package com.add.wordpressdroid.ui.CommentDetailsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.adapter.CommentsAdapter;
import com.add.wordpressdroid.model.posts.post.CommentsAndReplies;
import com.add.wordpressdroid.ui.dialog.WriteACommentFragment;
import com.add.wordpressdroid.utils.ActivityUtils;
import com.add.wordpressdroid.utils.AppConstant;
import com.add.wordpressdroid.utils.AppUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CommentDetailsActivity extends AppCompatActivity implements WriteACommentFragment.OnCompleteListener {


    private Activity mActivity;
    private Context mContext;
    private FloatingActionButton fabWriteAComment;

    private RecyclerView rvComments;
    private CommentsAdapter commentsAdapter = null;
    private ImageView commentAuthorImage;
    private TextView commentAuthorName, commentDate, commentDetail, replyCount;
    private List<CommentsAndReplies> commentList;
    private List<CommentsAndReplies> commentsOfLatestParent;
    private List<CommentsAndReplies> commentsOfLatestParentReplica;
    private CommentsAndReplies clickedComment;
    private int clickedPostId;
    private boolean shouldDialogOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_details);

        initVar();
        initView();
        initFunctionality();
        initListener();
    }
    private void initVar() {
        mActivity = CommentDetailsActivity.this;
        mContext = mActivity.getApplicationContext();

        commentList = new ArrayList<>();
        commentsOfLatestParent = new ArrayList<>();
        commentsOfLatestParentReplica = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            commentList = getIntent().getParcelableArrayListExtra(AppConstant.BUNDLE_KEY_ALL_COMMENT);
            clickedComment = getIntent().getParcelableExtra(AppConstant.BUNDLE_KEY_CLICKED_COMMENT);
            clickedPostId = getIntent().getIntExtra(AppConstant.BUNDLE_KEY_POST_ID, 0);
            shouldDialogOpen = getIntent().getBooleanExtra(AppConstant.BUNDLE_KEY_SHOULD_DIALOG_OPEN, false);
        }

    }

    @SuppressLint("WrongConstant")
    private void initView() {

        fabWriteAComment = (FloatingActionButton) findViewById(R.id.fab_reply_btn);

        commentAuthorImage = (ImageView) findViewById(R.id.comnt_author_img);
        commentAuthorName = (TextView) findViewById(R.id.comnt_author_name);
        commentDate = (TextView) findViewById(R.id.comnt_date);
        commentDetail = (TextView) findViewById(R.id.comnt_details);
        replyCount = (TextView) findViewById(R.id.reply_count);
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

/*
        initToolbar();
        setToolbarTitle(getString(R.string.comment_details));
        enableBackButton();*/
    }


    private void initFunctionality() {

        setCommentDetailsAndGetReplies();

        setRepliesRecyclerView();

        if (shouldDialogOpen) {
            openWriteCommentDialog();
        }
    }

    public void setCommentDetailsAndGetReplies() {
        String imgUrl = null;
        if (clickedComment.getAuthorAvaterUrl().getSourceUrl() != null) {
            imgUrl = clickedComment.getAuthorAvaterUrl().getSourceUrl();
        }

        if (imgUrl != null) {
            Glide.with(CommentDetailsActivity.this)
                    .load(imgUrl)
                    .into(commentAuthorImage);
        }

        commentAuthorName.setText(Html.fromHtml(clickedComment.getAuthorName()));


        String oldDate = clickedComment.getOldDate();
        String newDate = AppUtils.getFormattedDate(oldDate);

        if (newDate != null) {
            commentDate.setText(Html.fromHtml(newDate));
        }

        commentDetail.setText(Html.fromHtml(clickedComment.getContent().getRendered()));

        int replyCount = 0;
        for (CommentsAndReplies commentsAndReplies : commentList) {
            if (clickedComment.getID().equals(commentsAndReplies.getParent())) {
                replyCount++;
                commentsOfLatestParent.add(commentsAndReplies);
            }
        }
        commentsOfLatestParentReplica.addAll(commentsOfLatestParent);
        if (replyCount == 1) {
            this.replyCount.setText(replyCount + AppConstant.EMPTY + getString(R.string.comment_reply));
        } else {
            this.replyCount.setText(replyCount + AppConstant.EMPTY + getString(R.string.comment_replies));
        }
    }


    public void setRepliesRecyclerView() {
        commentsAdapter = new CommentsAdapter(getApplicationContext(), (ArrayList) commentList, (ArrayList) commentsOfLatestParent);
        rvComments.setAdapter(commentsAdapter);
    }

    public void initListener() {

        fabWriteAComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWriteCommentDialog();
            }
        });

        commentsAdapter.setItemClickListener(new CommentsAdapter.ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                int id = view.getId();
                CommentsAndReplies clickedComment = commentsOfLatestParent.get(position);
                boolean isFound = false;
                for (CommentsAndReplies commentsAndReplies : commentsOfLatestParentReplica) {
                    if (clickedComment.getID().equals(commentsAndReplies.getID())) {
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
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
            }
        });
    }

    public void openWriteCommentDialog() {
      /*  FragmentManager manager = getSupportFragmentManager();
        WriteACommentFragment dialog = WriteACommentFragment.newInstance(clickedPostId, clickedComment.getID().intValue());
        dialog.show(manager, AppConstant.BUNDLE_KEY_DIALOG_FRAGMENT);*/
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onComplete(Boolean isCommentSuccessful, CommentsAndReplies commentsAndReplies) {
        if (isCommentSuccessful) {
            commentsOfLatestParent.add(commentsAndReplies);
            commentsAdapter.notifyDataSetChanged();
            setCommentSuccessResult(isCommentSuccessful);
        }
    }


    private void setCommentSuccessResult(boolean isCommentSuccessful) {
        Intent data = new Intent();
        data.putExtra(AppConstant.BUNDLE_KEY_COMMENT_STATUS, isCommentSuccessful);
        setResult(RESULT_OK, data);
    }

    public static boolean wasCommentSuccessful(Intent result) {
        return result.getBooleanExtra(AppConstant.BUNDLE_KEY_COMMENT_STATUS, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == AppConstant.REQUEST_CODE_COMMENT) {
            if (data == null) {
                return;
            }
            boolean isCommentSuccessful = CommentDetailsActivity.wasCommentSuccessful(data);
            if (isCommentSuccessful) {
                setCommentSuccessResult(isCommentSuccessful);
            }
        }
    }
}
