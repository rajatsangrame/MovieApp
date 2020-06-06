package com.rajatsangrame.movie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.model.home.MovieDetail;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.rest.Category;
import com.rajatsangrame.movie.data.rest.RetrofitApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class Utils {

    private static final String TAG = "Utils";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void hideKeyboard(Context context) {
        // Check if no view has focus:
        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, 0);
    }

    public static List<Movie> getListResult(ApiResponse<Movie> apiResponse) {
        return apiResponse.getResults();
    }

    public static List<SearchResult> prepareListForSearchAdapter(ApiResponse<SearchResult> apiResponse) {
        List<SearchResult> outputResult = new ArrayList<>();
        if (apiResponse == null || apiResponse.getResults() == null) {
            return outputResult;
        }
        List<SearchResult> apiSearchList = clearRawEntries(apiResponse);
        List<String> itemType = new ArrayList<>();

        for (SearchResult movie : apiSearchList) {
            String type = movie.getMediaType();
            if (!itemType.contains(type)) {
                itemType.add(type);
            }
        }

        for (String type : itemType) {
            SearchResult dummy = new SearchResult();
            dummy.setItemType(Constants.HEADER);
            dummy.setMediaType(type);
            outputResult.add(dummy);
            for (SearchResult itr : apiSearchList) {
                itr.setItemType(Constants.ITEM);
                if (itr.getMediaType().equals(type)) {
                    outputResult.add(itr);
                }
            }
        }
        return outputResult;
    }

    private static List<SearchResult> clearRawEntries(ApiResponse<SearchResult> apiResponse) {
        List<SearchResult> list = new ArrayList<>();
        for (SearchResult itr : apiResponse.getResults()) {
            if (itr.getMediaType().equals("person") || itr.getBackdropPath() != null) {

                Log.i(TAG, "clearRawEntries: " + itr.getBackdropPath());
                list.add(itr);
            }
        }
        return list;
    }

    public static Single<ApiResponse<Movie>> getSingle(RetrofitApi retrofitApi, Category category, long key) {
        switch (category) {
            case POPULAR_TV:
                return retrofitApi.getPopularTv(key);
            case NOW_PLAYING:
                return retrofitApi.getNowPlaying(key);
            case TOP_RATED_MOVIE:
                return retrofitApi.getTopRatedMovie(key);
            case TOP_TV:
                return retrofitApi.getTopRatedTv(key);
            default:
                // POPULAR
                return retrofitApi.getPopularMovies(key);
        }
    }

    public static List<MovieDB> getMovieList(ApiResponse<Movie> apiResponse, Category category) {
        List<MovieDB> dbList = new ArrayList<>();
        if (apiResponse == null || apiResponse.getResults() == null)
            return dbList;

        for (Movie movie : apiResponse.getResults()) {

            MovieDB db = new MovieDB(movie.getId(),
                    movie.getTitle(),
                    category.name(),
                    movie.getPosterPath(),
                    movie.getPopularity(),
                    movie.getVoteAverage(),
                    System.currentTimeMillis());
            dbList.add(db);
        }
        return dbList;
    }

    public static MovieDB getMovieDetail(MovieDetail movieDetail, Repository repository) {
        return repository.getMovieDB(movieDetail);
    }
}
