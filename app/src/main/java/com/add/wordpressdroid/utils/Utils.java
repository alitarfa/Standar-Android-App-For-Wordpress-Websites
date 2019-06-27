package com.add.wordpressdroid.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.add.wordpressdroid.R;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static Drawable getRandomColor(Context context) {
        int random = (int) (Math.random() * 10);
        List<Drawable> colors = Arrays.asList(
                context.getResources().getDrawable(R.drawable.round_shape_with_color),
                context.getResources().getDrawable(R.drawable.round_shape_with_color2),
                context.getResources().getDrawable(R.drawable.round_shape_with_color3),
                context.getResources().getDrawable(R.drawable.round_shape_with_color4),
                context.getResources().getDrawable(R.drawable.round_shape_with_color1),
                context.getResources().getDrawable(R.drawable.round_shape_with_color0),
                context.getResources().getDrawable(R.drawable.round_shape_with_color5),
                context.getResources().getDrawable(R.drawable.round_shape_with_color6),
                context.getResources().getDrawable(R.drawable.round_shape_with_color7),
                context.getResources().getDrawable(R.drawable.round_shape_with_color8));
        return colors.get(random);
    }
}
