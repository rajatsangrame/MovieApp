package com.rajatsangrame.movie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rajatsangrame.movie.data.model.Api;
import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.model.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static List<SearchResult> getSearchResult(Api<SearchResult> movieApi) {
        return movieApi.getResults();
    }

    public static List<Movie> getListResult(Api<Movie> movieApi) {
        return movieApi.getResults();
    }

    public static List<SearchResult> prepareListForSearchAdapter(List<SearchResult> movieList) {

        List<String> movieType = new ArrayList<>();

        for (SearchResult movie : movieList) {
            String type = movie.getMediaType();
            if (!movieType.contains(type)) {
                movieType.add(type);
            }
        }

        List<SearchResult> searchMovies = new ArrayList<>();

        for (String type : movieType) {
            SearchResult dummy = new SearchResult();
            if (type.equals("movie")) {
                dummy.setMediaTypeInt(Constants.MOVIE);
            } else if (type.equals("tv")) {
                dummy.setMediaTypeInt(Constants.TV);
            } else {
                dummy.setMediaTypeInt(-1);
            }
            searchMovies.add(dummy);

            for (SearchResult itr : movieList) {

                if (itr.getMediaType().equals(type)) {
                    searchMovies.add(itr);
                }
            }
        }
        return searchMovies;
    }
}
