package com.rajatsangrame.movie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.movie.Movie;
import com.rajatsangrame.movie.data.model.movie.MovieDetail;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.model.tv.TV;
import com.rajatsangrame.movie.data.model.tv.TvDetail;
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

    public static Single<ApiResponse<Movie>> getSingleMovie(RetrofitApi retrofitApi, Category category, long key) {
        switch (category) {
            case NOW_PLAYING:
                return retrofitApi.getNowPlaying(key);
            case TOP_RATED_MOVIE:
                return retrofitApi.getTopRatedMovie(key);
            default:
                //POPULAR
                return retrofitApi.getPopularMovies(key);
        }
    }

    public static Single<ApiResponse<TV>> getSingleTV(RetrofitApi retrofitApi, Category category, long key) {
        switch (category) {
            case TOP_TV:
                return retrofitApi.getTopRatedTv(key);
            default:
                //POPULAR
                return retrofitApi.getPopularTv(key);
        }
    }

    public static List<MovieDB> getMovieList(ApiResponse<Movie> apiResponse, Category category) {
        List<MovieDB> dbList = new ArrayList<>();
        if (apiResponse == null || apiResponse.getResults() == null)
            return dbList;

        for (Movie movie : apiResponse.getResults()) {
            MovieDB db = new MovieDB(
                    movie.getId(),
                    movie.getTitle(),
                    category.name(),
                    movie.getPosterPath(),
                    movie.getBackdropPath(),
                    movie.getOverview(),
                    movie.getPopularity(),
                    movie.getVoteAverage(),
                    System.currentTimeMillis());
            dbList.add(db);
        }
        return dbList;
    }

    public static List<TVDB> getTVList(ApiResponse<TV> apiResponse, Category category) {
        List<TVDB> dbList = new ArrayList<>();
        if (apiResponse == null || apiResponse.getResults() == null)
            return dbList;

        for (TV tv : apiResponse.getResults()) {
            TVDB db = new TVDB(
                    tv.getId(),
                    tv.getName(),
                    category.name(),
                    tv.getPosterPath(),
                    tv.getBackdropPath(),
                    tv.getOverview(),
                    tv.getPopularity(),
                    tv.getVoteAverage(),
                    System.currentTimeMillis()
            );
            dbList.add(db);
        }
        return dbList;
    }

    public static MovieDB getMovieDB(MovieDetail movieDetail) {
        int id = movieDetail.getId();
        MovieDB movieDB = new MovieDB(id);
        movieDB.setPosterPath(movieDetail.getPosterPath());
        movieDB.setBackdropPath(movieDetail.getBackdropPath());
        movieDB.setTitle(movieDetail.getTitle());
        movieDB.setOriginalTitle(movieDetail.getOriginalTitle());
        movieDB.setPopularity(movieDetail.getPopularity());
        movieDB.setVoteAverage(movieDetail.getVoteAverage());
        movieDB.setOverview(movieDetail.getOverview());
        movieDB.setGenres(movieDetail.getGenres());
        movieDB.setProductionCompanies(movieDetail.getProductionCompanies());
        movieDB.setSpokenLanguages(movieDetail.getSpokenLanguages());
        movieDB.setVoteCount(movieDetail.getVoteCount());
        movieDB.setRuntime(movieDetail.getRuntime());
        movieDB.setReleaseDate(movieDetail.getReleaseDate());
        movieDB.setImdbId(movieDetail.getImdbId());
        movieDB.setHomepage(movieDetail.getHomepage());
        return movieDB;
    }

    public static TVDB getTVDB(TvDetail tvDetail) {
        int id = tvDetail.getId();
        TVDB tvdb = new TVDB(id);
        tvdb.setPosterPath(tvDetail.getPosterPath());
        tvdb.setBackdropPath(tvDetail.getBackdropPath());
        tvdb.setName(tvDetail.getName());
        tvdb.setOriginalName(tvDetail.getOriginalName());
        tvdb.setPopularity(tvDetail.getPopularity());
        tvdb.setVoteAverage(tvDetail.getVoteAverage());
        tvdb.setOverview(tvDetail.getOverview());
        tvdb.setVoteCount(tvDetail.getVoteCount());
        tvdb.setGenres(tvDetail.getGenres());
        tvdb.setProductionCompanies(tvDetail.getProductionCompanies());
        tvdb.setFirstAirDate(tvDetail.getFirstAirDate());
        return tvdb;
    }

    public static MovieDB updateMovieDB(MovieDB oldItem, MovieDB newItem) {
        newItem.setFetchCategory(oldItem.getFetchCategory());
        newItem.setEntryTimeStamp(oldItem.getEntryTimeStamp());
        newItem.setSaved(oldItem.isSaved());
        return newItem;
    }

    public static TVDB updateTVDB(TVDB oldItem, TVDB newItem) {
        newItem.setFetchCategory(oldItem.getFetchCategory());
        newItem.setEntryTimeStamp(oldItem.getEntryTimeStamp());
        newItem.setSaved(oldItem.isSaved());
        return newItem;
    }

    public static MovieDB getMovieDetail(MovieDetail movieDetail) {
        return getMovieDB(movieDetail);
    }

    public static TVDB getTVDetail(TvDetail tvDetail) {
        return getTVDB(tvDetail);
    }
}
