package com.rajatsangrame.movie.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;

import java.util.List;

import static com.rajatsangrame.movie.util.Constants.getGenreFromId;

/**
 * Ref: https://developer.android.com/topic/libraries/data-binding/expressions
 */
public class BindingUtils {

    public final static String IMAGE_URL = "https://image.tmdb.org/t/p/w185";

    @BindingAdapter({"loadImageUrl"})
    public static void loadImage(ImageView imageView, String url) {

        Context context = imageView.getContext();
        final String URL = IMAGE_URL + url;

        Glide.with(imageView)
                .load(URL)
                .placeholder(context.getResources().getDrawable(R.color.cardBackground))
                .into(imageView);

    }

    @BindingAdapter({"toggleVisibility"})
    public static void visibility(View view, String mediaType) {

        switch (view.getId()) {
            case R.id.ll_movie:
                if (mediaType.equals("movie")) {
                    view.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_tv:
                if (mediaType.equals("tv")) {
                    view.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_person:
                if (mediaType.equals("person")) {
                    view.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @BindingAdapter({"genre"})
    public static void getGenreFromList(TextView textView, List<Integer> genreList) {

        final String dot = "  •  ";
        StringBuilder genre = new StringBuilder();

        if (genreList == null) {
            return;
        }

        if (genreList.size() > 3) {
            genreList.remove(genreList.size() - 1);
            getGenreFromList(textView, genreList);
        }

        for (int i = 0; i < genreList.size(); i++) {
            genre.append(getGenreFromId(genreList.get(i)));
            if (i < genreList.size() - 1) {
                genre.append(dot);
            }
        }
        textView.setText(genre.toString());
    }
}


