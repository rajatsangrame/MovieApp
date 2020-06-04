package com.rajatsangrame.movie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.rajatsangrame.movie.data.model.Api;
import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.model.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Movie> getListResult(Api<Movie> apiResponse) {
        return apiResponse.getResults();
    }

    public static List<SearchResult> prepareListForSearchAdapter(Api<SearchResult> apiResponse) {
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

    private static List<SearchResult> clearRawEntries(Api<SearchResult> apiResponse) {
        List<SearchResult> list = new ArrayList<>();
        for (SearchResult itr : apiResponse.getResults()) {
            if (itr.getMediaType().equals("person") || itr.getBackdropPath() != null) {

                Log.i(TAG, "clearRawEntries: " + itr.getBackdropPath());
                list.add(itr);
            }
        }
        return list;
    }
}
