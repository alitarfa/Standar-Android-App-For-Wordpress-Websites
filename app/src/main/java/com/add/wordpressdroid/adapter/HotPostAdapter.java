package com.add.wordpressdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.add.wordpressdroid.R;


public class HotPostAdapter  extends PagerAdapter {

    private LayoutInflater inflater;
    private Context mContext;

    public HotPostAdapter(Context context) {
        this.mContext = context;
         inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 3;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup view, final int position) {

        View rootView = inflater.inflate(R.layout.item_home_screen_view_pager, view, false);
        final ImageView mPagerImageView = (ImageView) rootView.findViewById(R.id.img_pager);
        final TextView mFeaturedPostTitleTextView = (TextView) rootView.findViewById(R.id.recent_post_title);


        mPagerImageView.setImageDrawable(mContext.getDrawable(R.drawable.coding));
        mFeaturedPostTitleTextView.setText("it's Simple to make it ");


        view.addView(rootView);
        return rootView;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
