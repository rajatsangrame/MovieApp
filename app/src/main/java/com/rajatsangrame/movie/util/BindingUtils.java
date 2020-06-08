package com.rajatsangrame.movie.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.adapter.ProductionCompaniesAdapter;
import com.rajatsangrame.movie.adapter.TvSeasonAdapter;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.model.movie.Genre;
import com.rajatsangrame.movie.data.model.movie.ProductionCompanies;
import com.rajatsangrame.movie.data.model.movie.SpokenLanguages;
import com.rajatsangrame.movie.data.model.tv.Seasons;

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
                .transform(new CenterCrop(), new RoundedCorners(8))
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

    @BindingAdapter({"productionCompanies"})
    public static void productionList(RecyclerView recyclerView, List<ProductionCompanies> companiesList) {
        if (companiesList == null) return;

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        ProductionCompaniesAdapter adapter = new ProductionCompaniesAdapter();
        filterCompaniesList(companiesList);
        adapter.setList(companiesList);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"season"})
    public static void seasonList(RecyclerView recyclerView, List<Seasons> seasonsList) {
        if (seasonsList == null) return;

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        TvSeasonAdapter adapter = new TvSeasonAdapter();
        adapter.setList(seasonsList);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"genreId"})
    public static void getGenreFromList(TextView textView, List<Integer> genreList) {

        if (genreList == null) return;

        final String dot = "  •  ";
        StringBuilder genre = new StringBuilder();

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

    @BindingAdapter({"genre"})
    public static void getGenre(TextView textView, List<Genre> genreList) {

        if (genreList == null) return;

        final String dot = "  •  ";
        StringBuilder genre = new StringBuilder();

        if (genreList.size() > 3) {
            genreList.remove(genreList.size() - 1);
            getGenre(textView, genreList);
        }

        for (int i = 0; i < genreList.size(); i++) {
            genre.append(genreList.get(i).getName());
            if (i < genreList.size() - 1) {
                genre.append(dot);
            }
        }
        textView.setText(genre.toString());
    }

    @BindingAdapter({"languages"})
    public static void getLanguages(TextView textView, List<SpokenLanguages> languages) {

        if (languages == null) return;

        filterLanguageList(languages);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < languages.size(); i++) {

            final String dot = "  •  ";
            builder.append(dot);
            builder.append(languages.get(i).getName());

            if (i != languages.size() - 1) {
                builder.append("\n\n");
            }
        }
        textView.setText(builder.toString());
    }

    @BindingAdapter({"getYearAndTime"})
    public static void getYearAndTime(TextView textView, MovieDB movie) {
        try {
            String[] date = movie.getReleaseDate().split("-");
            final String dot = "  •  ";
            int timeDuration = movie.getRuntime();
            String string = textView.getContext().getString(
                    R.string.movie_year_and_time,
                    date[0],
                    dot,
                    getRunTime(timeDuration)
            );
            textView.setText(string);
        } catch (NullPointerException e) {
            //ignored.
        }
    }

    @BindingAdapter({"setYearAndTime"})
    public static void setYearAndSeason(TextView textView, TVDB tv) {
        try {
            String[] date = tv.getFirstAirDate().split("-");
            final String dot = "  •  ";
            int season = tv.getNumberOfSeasons();
            int episodes = tv.getNumberOfEpisodes();
            String string = textView.getContext().getString(
                    R.string.tv_year_and_season,
                    date[0],
                    dot,
                    season,
                    episodes
            );
            textView.setText(string);
        } catch (NullPointerException e) {
            //ignored.
        }
    }

    @BindingAdapter({"like"})
    public static void like(TextView textView, int voteCount) {
        try {
            textView.setText(voteCount + "");
        } catch (NullPointerException e) {
            //ignored
        }
    }

    @BindingAdapter({"rating"})
    public static void rating(TextView textView, double voteAverage) {
        try {
            textView.setText(voteAverage + "");
        } catch (NullPointerException e) {
            //ignored
        }
    }

    @BindingAdapter({"save"})
    public static void updateSave(ImageView imageView, boolean isSaved) {

        if (isSaved)
            imageView.setImageResource(R.drawable.ic_saved);
        else imageView.setImageResource(R.drawable.ic_save);
    }

    private static void filterLanguageList(List<SpokenLanguages> list) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getName().isEmpty()) {

                list.remove(i);
                filterLanguageList(list);
                break;

            }
        }
    }

    private static void filterCompaniesList(List<ProductionCompanies> list) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getLogoPath() == null) {
                list.remove(i);
                filterCompaniesList(list);
                break;
            }
        }
    }

    private static String getRunTime(int time) {

        int hours = time / 60; //since both are ints, you get an int
        int minutes = time % 60;
        //System.out.printf("%d:%02d", hours, minutes);
        return String.format("%dh %02dmin", hours, minutes);
    }


}


