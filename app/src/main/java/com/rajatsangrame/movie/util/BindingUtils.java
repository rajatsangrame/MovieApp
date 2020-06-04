package com.rajatsangrame.movie.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;

public class BindingUtils {

    public final static String IMAGE_URL = "https://image.tmdb.org/t/p/w185";

    @BindingAdapter({"bind:url"})
    public static void loadImage(ImageView imageView, String url) {
        Context context = imageView.getContext();
        final String URL = IMAGE_URL + url;

        Glide.with(imageView)
                .load(URL)
                .placeholder(context.getResources().getDrawable(R.color.cardBackground))
                .into(imageView);

    }

}


