package com.add.wordpressdroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.add.wordpressdroid.R;
import com.add.wordpressdroid.model.posts.post.Post;
import com.add.wordpressdroid.model.posts.post.PostDetails;
import com.add.wordpressdroid.ui.postDetails.PostDetailsActivity;
import com.add.wordpressdroid.ui.selectedCategory.SelectedCategory;
import com.add.wordpressdroid.utils.AppConstant;
import com.add.wordpressdroid.utils.ListItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

public class HotPostAdapter  extends PagerAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private List<Post> postList;

    // Listener
    private ListItemClickListener mListener;


    public HotPostAdapter(Context context,  List<Post> posts) {
         this.mContext = context;
         inflater = LayoutInflater.from(context);
        this.postList = posts;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup view, final int position) {

        View rootView = inflater.inflate(R.layout.item_home_screen_view_pager, view, false);

        final ImageView mPagerImageView = (ImageView) rootView.findViewById(R.id.img_pager);
        final TextView mFeaturedPostTitleTextView = (TextView) rootView.findViewById(R.id.recent_post_title);
        final Post mPost = postList.get(position);

        String titleText = mPost.getTitle().getRendered();

        mFeaturedPostTitleTextView.setText(Html.fromHtml(titleText));

        String imgUrl = null;
        if (mPost.getEmbedded().getWpFeaturedMedias().size() >= 1) {
            imgUrl = mPost.getEmbedded().getWpFeaturedMedias().get(0).getMediaDetails().getSizes().getFullSize().getSourceUrl();
        }


        if (imgUrl != null) {
            Glide.with(mContext)
                    .load(imgUrl)
                    .into(mPagerImageView);
        }


        view.addView(rootView);


        mPagerImageView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PostDetailsActivity.class);
            intent.putExtra(AppConstant.BUNDLE_KEY_POST_ID, postList.get(position).getID());
            mContext.startActivity(intent);
        });


        return rootView;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


}
