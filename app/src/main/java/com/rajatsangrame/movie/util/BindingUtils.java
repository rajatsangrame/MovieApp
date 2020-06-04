package com.rajatsangrame.movie.util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;

import java.util.List;

import static com.rajatsangrame.movie.util.Constants.getGenreFromId;

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

    @BindingAdapter({"bind:genre"})
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


