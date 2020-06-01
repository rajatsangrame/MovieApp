package com.rajatsangrame.movie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rajatsangrame.movie.data.model.Api;
import com.rajatsangrame.movie.data.model.Movie;

import java.util.List;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static List<Movie> getListResult(Api<Movie> movieApi) {
        return movieApi.getMovies();
    }
}
